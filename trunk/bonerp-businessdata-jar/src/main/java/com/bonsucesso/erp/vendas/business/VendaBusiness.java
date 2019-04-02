package com.bonsucesso.erp.vendas.business;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.base.data.PessoaFinder;
import com.bonsucesso.erp.estoque.data.GradeFinder;
import com.bonsucesso.erp.estoque.data.ProdutoFinder;
import com.bonsucesso.erp.estoque.model.GradeTamanho;
import com.bonsucesso.erp.estoque.model.Produto;
import com.bonsucesso.erp.fiscal.business.NotaFiscalBusiness;
import com.bonsucesso.erp.fiscal.data.NotaFiscalDataMapper;
import com.bonsucesso.erp.fiscal.data.NotaFiscalFinder;
import com.bonsucesso.erp.fiscal.model.IndicadorFormaPagto;
import com.bonsucesso.erp.fiscal.model.ModalidadeFrete;
import com.bonsucesso.erp.fiscal.model.NotaFiscal;
import com.bonsucesso.erp.fiscal.model.NotaFiscalItem;
import com.bonsucesso.erp.fiscal.model.TipoNotaFiscal;
import com.bonsucesso.erp.rh.data.FuncionarioFinder;
import com.bonsucesso.erp.rh.model.Funcionario;
import com.bonsucesso.erp.vendas.data.PlanoPagtoFinder;
import com.bonsucesso.erp.vendas.data.TipoVendaFinder;
import com.bonsucesso.erp.vendas.data.VendaDataMapper;
import com.bonsucesso.erp.vendas.data.VendaFinder;
import com.bonsucesso.erp.vendas.model.PlanoPagto;
import com.bonsucesso.erp.vendas.model.StatusVenda;
import com.bonsucesso.erp.vendas.model.Venda;
import com.bonsucesso.erp.vendas.model.VendaItem;
import com.bonsucesso.erp.vendas.spartacus.business.ISpartacusHandler;
import com.google.common.io.Files;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.config.data.ConfigFinder;
import com.ocabit.jsf.utils.JSFUtils;
import com.ocabit.utils.calendar.CalendarUtil;
import com.ocabit.utils.currency.CurrencyUtils;

@Component("vendaBusiness")
public class VendaBusiness implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9062911424794550539L;

	protected static Logger logger = Logger.getLogger(VendaBusiness.class);

	@Value("${pastaArquivosEktFiscal}")
	private String pastaArquivosEktFiscal;

	@Autowired
	private VendaFinder vendaFinder;

	@Autowired
	private PessoaFinder pessoaFinder;

	@Autowired
	private VendaDataMapper vendaDataMapper;

	@Autowired
	private NotaFiscalDataMapper notaFiscalDataMapper;

	@Autowired
	private NotaFiscalBusiness notaFiscalBusiness;

	@Autowired
	private NotaFiscalFinder notaFiscalFinder;

	@Autowired
	private TipoVendaFinder tipoVendaFinder;

	@Autowired
	private PlanoPagtoFinder planoPagtoFinder;

	@Autowired
	private FuncionarioFinder funcionarioFinder;

	@Autowired
	private GradeFinder gradeFinder;

	@Autowired
	private ProdutoFinder produtoFinder;

	@Autowired
	private ISpartacusHandler spartacusHandler;

	@Autowired
	private ConfigFinder configFinder;

	public VendaBusiness getThiz() {
		return (VendaBusiness) getVendaFinder().getBeanFactory().getBean("vendaBusiness");
	}

	/**
	 * Lê todos os arquivos da pasta do 10.1.1.100:/opt/bon-lnx/spartacus/, salva
	 * como Venda e deleta o arquivo.
	 * 
	 * @throws ViewException
	 */
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void processarTXTsEKTeApagarArquivos() throws ViewException {

		getThiz().processarTXTsEKT();

		File folder = new File(getPastaArquivosEktFiscal());
		File[] files = folder.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.startsWith("pv") && name.endsWith("txt");
			}
		});

		if (files != null) {
			try {
				for (File f : files) {
					try {
						FileUtils.forceDelete(f);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				throw new ViewException("Erro ao ler arquivos TXT do EKT.");
			}
		}

		logger.info("Finalizando");

	}

	/**
	 * Lê todos os arquivos da pasta do 10.1.1.100:/opt/bon-lnx/spartacus/, salva
	 * como Venda e deleta o arquivo.
	 * 
	 * @throws ViewException
	 */
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED)
	private void processarTXTsEKT() throws ViewException {

		try {
			File folder = new File(getPastaArquivosEktFiscal());

			File[] files = folder.listFiles(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					return name.startsWith("pv") && name.endsWith("txt");
				}
			});

			if (files != null) {

				for (File f : files) {

					List<String> linhas;
					try {
						linhas = Files.readLines(f, Charset.defaultCharset());
					} catch (IOException e1) {
						throw new ViewException("Erro ao ler arquivo: " + f.getName());
					}

					String[] cabecalho = linhas.get(0).split("\\|");

					Integer pv = Integer.parseInt(cabecalho[1]);

					Date dtVenda;
					try {
						dtVenda = new SimpleDateFormat("dd/MM/yyyy").parse(cabecalho[7]);
					} catch (ParseException e1) {
						throw new ViewException("Erro ao obter data da venda");
					}

					Venda venda = null;
					try {
						venda = getVendaFinder().findBy(dtVenda, pv);
						if (venda != null) {
							venda = getVendaFinder().refresh(venda); // pq o findBy acima está com propagation =
																		// Propagation.REQUIRES_NEW
						}
					} catch (ViewException e) {
						throw new ViewException("Erro ao pesquisar venda.");
					}
					if (venda == null) {

						venda = new Venda();
						venda.setPv(pv);
						venda.setDtVenda(dtVenda);

						venda.setTipo(getTipoVendaFinder().findById(1l)); // Sempre 1 - VENDA NORMAL

						PlanoPagto planoPagto = null;
						try {
							planoPagto = getPlanoPagtoFinder().findByDescricao(cabecalho[3]);
						} catch (Exception e3) {
							logger.error("Plano de pagto não encontrado para '" + cabecalho[3] + "'");
						}
						if (planoPagto == null) {
							venda.setPlanoPagto(getPlanoPagtoFinder().findById(1l));
						} else {
							venda.setPlanoPagto(planoPagto);
						}

						Integer codigoVendedor = Integer.parseInt(cabecalho[2]);
						try {
							Funcionario vendedor = getFuncionarioFinder().findByCodigo(codigoVendedor);
							venda.setVendedor(vendedor);
						} catch (Exception e3) {
							throw new ViewException("Erro ao importar dados do vendedor.");
						}

						venda.setDeletado(Boolean.FALSE);

						NumberFormat numberFormat = NumberFormat.getInstance(new Locale("pt", "BR"));

						try {
							Number nDesconto = numberFormat.parse(cabecalho[6]);
							Number nValorTotal = numberFormat.parse(cabecalho[5]);
							BigDecimal valorDesconto = CurrencyUtils.getBigDecimalCurrency(nDesconto.toString());
							BigDecimal valorTotal = CurrencyUtils.getBigDecimalCurrency(nValorTotal.toString());
							BigDecimal subTotal = valorTotal.add(valorDesconto);

							venda.setDescontoPlano(valorDesconto);
							venda.setSubTotal(subTotal);
							venda.setValorTotal(valorTotal);

							venda.setDescontoEspecial(BigDecimal.ZERO);
						} catch (ParseException e) {
							throw new ViewException("Erro ao obter valores da venda", e);
						}

						GradeTamanho gradeTamanhoPara88888 = getGradeFinder().findByCodigoGradeAndTamanho(11, "UN");

						for (int i = 1; i < linhas.size(); i++) {

							String[] itemLinha = linhas.get(i).split("\\|");
							Integer ordem = Integer.parseInt(itemLinha[1]);
							Long reduzido = Long.parseLong(itemLinha[2]);
							String descricao = itemLinha[3];

							Integer gradeCodigo = Integer.parseInt(itemLinha[8]);
							String tamanho = itemLinha[7].trim();

							String ncm = itemLinha[4];

							VendaItem vendaItem = new VendaItem();
							venda.getItens().add(vendaItem);

							vendaItem.setVenda(venda);
							vendaItem.setNcm(ncm);
							vendaItem.setNcmExistente(getNotaFiscalBusiness().findNCMExiste(ncm));

							vendaItem.setOrdem(ordem);

							try {
								Number nQtde = numberFormat.parse(itemLinha[5]);
								BigDecimal qtde = CurrencyUtils.getBigDecimalCurrency(nQtde.doubleValue());

								Number nValorUnit = numberFormat.parse(itemLinha[6]);
								BigDecimal valorUnit = CurrencyUtils.getBigDecimalCurrency(nValorUnit.doubleValue());

								vendaItem.setPrecoVenda(valorUnit);
								vendaItem.setQtde(qtde);

							} catch (ParseException e) {
								throw new ViewException("Erro ao obter valores do item: " + descricao, e);
							}

							Produto produto;

							// Para NCs
							if (reduzido == 88888) {
								vendaItem.setGradeTamanho(null);
								vendaItem.setNcReduzido(reduzido);

								descricao = descricao == null || "".equals(descricao) ? "88888" : descricao;

								vendaItem.setNcDescricao(descricao);
								vendaItem.setNcGradeTamanho(tamanho);

								vendaItem.setNcm("62179000"); // Vestuário e seus acessórios, exceto de malha
								vendaItem.setNcmExistente(true);

								vendaItem.setGradeTamanho(gradeTamanhoPara88888);
							} else {
								// Pego o produto bonerp pelo reduzido EKT + mesano
								try {
									List<Produto> prods = getProdutoFinder().findByReduzidoEkt(reduzido.intValue(),
											dtVenda);
									if (prods.isEmpty()) {
										throw new ViewException("Produto não encontrado");
									}
									produto = prods.get(0);
								} catch (Exception e2) {
									String err = "Erro ao pesquisar produto EKT no período: " + reduzido + " - "
											+ dtVenda;
									logger.error(err, e2);
									throw new IllegalStateException(err, e2);
								}

								vendaItem.setProduto(produto);

								GradeTamanho gradeTamanho = null;
								try {
									gradeTamanho = getGradeFinder().findByCodigoGradeAndTamanho(gradeCodigo, tamanho);
								} catch (Exception e1) {
									logger.error("Grade/Tamanho não encontrada para " + gradeCodigo + "-" + tamanho);
								}

								if (gradeTamanho == null) {
									vendaItem.setGradeTamanho(gradeTamanhoPara88888);
								} else {
									vendaItem.setGradeTamanho(gradeTamanho);
								}

								vendaItem.setAlteracaoPreco(!produto.getPrecoAtual().equals(vendaItem.getPrecoVenda()));

							}

						}

					}
					venda.setStatus(StatusVenda.PREVENDA);
					venda = getVendaDataMapper().save(venda);
					venda.getItens().size();

				}
			}
		} catch (Exception e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao ler arquivos exportados do EKT.");
		}

	}

	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public NotaFiscal saveNotaFiscalVenda(Venda venda, TipoNotaFiscal tipoNotaFiscal) throws ViewException {

		venda = getVendaFinder().refresh(venda);

		boolean editando = false;
		if (venda.getNotaFiscal() != null && venda.getNotaFiscal().getId() != null) {
			editando = true;
		}

		NotaFiscal nf;
		if (editando) {
			nf = getNotaFiscalFinder().refresh(venda.getNotaFiscal());
			nf.getItens().size();
		} else {
			nf = new NotaFiscal();

		}

		String ambiente = getConfigFinder().findValorByChave("bonsucesso.fiscal.ambiente");

		if (ambiente == null || "".equals(ambiente)) {
			throw new ViewException("Ambiente não encontrado");
		}

		nf.setAmbiente(ambiente);
		nf.setTranspModalidadeFrete(ModalidadeFrete.SEM_FRETE);

		nf.setIndicadorFormaPagto(venda.getPlanoPagto().getCodigo().equals("1.00") ? IndicadorFormaPagto.VISTA
				: IndicadorFormaPagto.PRAZO);

		Date dtEmissao = new Date();
		// atrasa 4 minutos
		dtEmissao = CalendarUtil.incDate(dtEmissao, ((4) * -1), Calendar.MINUTE);

		nf.setDtEmissao(dtEmissao);

		try {
			// O Spartacus está utilizando o campo SERIE para decidir quais notas imprimir
			// automaticamente
			// Serie 2 (Bonsucesso), Serie 3 (Ipê)
			String chave = "bonsucesso.fiscal." + tipoNotaFiscal.toString().toLowerCase() + ".serie";
			String serie = getConfigFinder().findValorByChave(chave);
			if (serie == null || "".equals(serie)) {
				throw new ViewException();
			}
			nf.setSerie(Integer.parseInt(serie));
		} catch (Exception e) {
			throw new ViewException("Erro ao obter valor para 'bonsucesso.fiscal.nfce.serie'", e);
		}

		if (!editando) {
			Integer nnf = getNotaFiscalFinder().findProxNumFiscal("PROD".equals(ambiente), nf.getSerie(),
					tipoNotaFiscal);
			nf.setNumero(nnf);
		}

		nf.setPessoaEmitente(getPessoaFinder().findPessoaByDocumento("77498442000134"));

		// Se foi informado o cliente na venda, salva como pessoa destinatário da nf
		if (venda.getCliente() != null) {
			nf.setPessoaDestinatario(venda.getCliente().getPessoa());
		}

		try {
			String serie = getConfigFinder().findValorByChave("bonsucesso.fiscal.nfce.serie");
			if (serie == null || "".equals(serie)) {
				throw new ViewException();
			}
			nf.setSerie(Integer.parseInt(serie));
		} catch (Exception e) {
			throw new ViewException("Erro ao obter valor para 'bonsucesso.fiscal.nfce.serie'", e);
		}

		nf.setEntrada(false);
		nf.setTipoNotaFiscal(tipoNotaFiscal);

		if (nf.getItens() != null && nf.getItens().size() > 0) {
			for (NotaFiscalItem item : nf.getItens()) {
				item.setNotaFiscal(null);
			}
			nf.getItens().clear();
			nf = getNotaFiscalDataMapper().save(nf);
		} else {
			// nf.setItens(null);
		}

		// getNotaFiscalDataMapper().getEntityManager().flush();

		// nf = getNotaFiscalFinder().refresh(venda.getNotaFiscal());

		BigDecimal fatorDesconto = BigDecimal.ONE
				.subtract(venda.getValorTotal().divide(venda.getSubTotal(), 4, RoundingMode.HALF_DOWN));
		BigDecimal somaDescontosItens = CurrencyUtils.getBigDecimalCurrency("0.00");

		if (venda.getItens() != null && !venda.getItens().isEmpty()) {
			Collections.sort(venda.getItens(), new Comparator<VendaItem>() {

				@Override
				public int compare(VendaItem o1, VendaItem o2) {
					return o1.getOrdem().compareTo(o2.getOrdem());
				}
			});

			Integer ultimaOrdem = 0;

			for (VendaItem vendaItem : venda.getItens()) {

				// Esta verificação é necessária pois o campo NCM na ven_venda_item está como
				// NULLABLE. O mais correto seria transforma-lo em NOT NULL.
				if (vendaItem.getNcm() == null || "".equals(vendaItem.getNcm())) {
					throw new ViewException(
							"É necessário informar o NCM do produto com reduzido: '" + vendaItem.getReduzido() + "'");
				}

				NotaFiscalItem nfItem = new NotaFiscalItem();
				nfItem.setNotaFiscal(nf);

				String ncm = vendaItem.getNcm();
				nfItem.setNcm(ncm);
				boolean ncmExistente = getNotaFiscalBusiness().findNCMExiste(ncm);
				nfItem.setNcmExistente(ncmExistente);

				nfItem.setOrdem(++ultimaOrdem);
				nfItem.setQtde(vendaItem.getQtde());
				nfItem.setValorUnit(vendaItem.getPrecoVenda());
				nfItem.setValorTotal(vendaItem.getTotalItem());

				BigDecimal vDesconto = vendaItem.getPrecoVenda().multiply(vendaItem.getQtde()).multiply(fatorDesconto);
				vDesconto = vDesconto.setScale(2, RoundingMode.HALF_DOWN);
				nfItem.setValorDesconto(vDesconto);

				somaDescontosItens = somaDescontosItens.add(vDesconto);

				nfItem.setSubTotal(CurrencyUtils.multiplicaBD(vendaItem.getQtde(), vendaItem.getPrecoVenda()));

				nfItem.setIcmsAliquota(BigDecimal.ZERO);
				nfItem.setCfop("5102");
				nfItem.setUnidade(
						vendaItem.getGradeTamanho().getTamanho() != null ? vendaItem.getGradeTamanho().getTamanho()
								: vendaItem.getNcGradeTamanho());

				if (vendaItem.getProduto() != null) {
					nfItem.setCodigo(vendaItem.getProduto().getReduzido().toString());
					nfItem.setDescricao(vendaItem.getProduto().getDescricao() + " ("
							+ vendaItem.getGradeTamanho().getTamanho() + ")");
				} else {
					nfItem.setCodigo(vendaItem.getNcReduzido().toString());
					nfItem.setDescricao(vendaItem.getNcDescricao() + " (" + vendaItem.getNcGradeTamanho() + ")");
				}

				nf.getItens().add(nfItem);
			}
		}

		BigDecimal totalDescontos = venda.getSubTotal().subtract(venda.getValorTotal());

		nf.setSubTotal(venda.getSubTotal());
		nf.setValorTotal(venda.getValorTotal());
		nf.setTotalDescontos(totalDescontos);

		// Se deu diferença em centavos, aplica a diferença ao desconto no primeiro item
		// para poder fechar a conta.
		if (totalDescontos.doubleValue() != somaDescontosItens.doubleValue()) {
			BigDecimal diferenca = totalDescontos.subtract(somaDescontosItens);
			nf.getItens().get(0).setValorDesconto(nf.getItens().get(0).getValorDesconto().add(diferenca));
			nf.getItens().get(0).calculaTotais();
		}

		nf = getNotaFiscalDataMapper().save(nf);
		getNotaFiscalDataMapper().getEntityManager().flush();

		return nf;

	}

	public Venda finalizarVenda(Venda venda) throws ViewException {
		venda = getVendaFinder().refresh(venda);
		venda.setStatus(StatusVenda.FINALIZADA);
		return getVendaDataMapper().save(venda);
	}

	public Venda recalcularTotal(Venda venda) throws ViewException {
		try {
			venda = getVendaFinder().refresh(venda);
			BigDecimal bdTotalItens = BigDecimal.ZERO;
			for (VendaItem item : venda.getItens()) {
				bdTotalItens = bdTotalItens.add(item.getTotalItem());
			}
			BigDecimal totalVenda = bdTotalItens.subtract(venda.getDescontoPlano().abs())
					.subtract(venda.getDescontoEspecial().abs());
			venda.setSubTotal(bdTotalItens);
			venda.setValorTotal(totalVenda);
			venda = getVendaDataMapper().save(venda);
			return venda;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ViewException("Erro ao recalcular o total", e);
		}
	}

	public boolean permiteReimpressao(Venda venda) {
		if (venda != null && venda.getNotaFiscal() != null && venda.getNotaFiscal().getId() != null) {
			if (venda.getNotaFiscal().getSpartacusStatus() == 100
					|| venda.getNotaFiscal().getSpartacusStatus() == 204) {
				return true;
			} else {
				if (venda.getNotaFiscal().getSpartacusStatus() == 0
						&& venda.getNotaFiscal().getSpartacusMensRetornoReceita().contains("DUPLICIDADE DE NF")) {
					return true;
				}
			}
		}
		return false;
	}

	public String getPastaArquivosEktFiscal() {
		return pastaArquivosEktFiscal;
	}

	public void setPastaArquivosEktFiscal(String pastaArquivosEktFiscal) {
		this.pastaArquivosEktFiscal = pastaArquivosEktFiscal;
	}

	public VendaFinder getVendaFinder() {
		return vendaFinder;
	}

	public void setVendaFinder(VendaFinder vendaFinder) {
		this.vendaFinder = vendaFinder;
	}

	public VendaDataMapper getVendaDataMapper() {
		return vendaDataMapper;
	}

	public void setVendaDataMapper(VendaDataMapper vendaDataMapper) {
		this.vendaDataMapper = vendaDataMapper;
	}

	public TipoVendaFinder getTipoVendaFinder() {
		return tipoVendaFinder;
	}

	public void setTipoVendaFinder(TipoVendaFinder tipoVendaFinder) {
		this.tipoVendaFinder = tipoVendaFinder;
	}

	public PlanoPagtoFinder getPlanoPagtoFinder() {
		return planoPagtoFinder;
	}

	public void setPlanoPagtoFinder(PlanoPagtoFinder planoPagtoFinder) {
		this.planoPagtoFinder = planoPagtoFinder;
	}

	public FuncionarioFinder getFuncionarioFinder() {
		return funcionarioFinder;
	}

	public void setFuncionarioFinder(FuncionarioFinder funcionarioFinder) {
		this.funcionarioFinder = funcionarioFinder;
	}

	public GradeFinder getGradeFinder() {
		return gradeFinder;
	}

	public void setGradeFinder(GradeFinder gradeFinder) {
		this.gradeFinder = gradeFinder;
	}

	public ProdutoFinder getProdutoFinder() {
		return produtoFinder;
	}

	public void setProdutoFinder(ProdutoFinder produtoFinder) {
		this.produtoFinder = produtoFinder;
	}

	public ISpartacusHandler getSpartacusHandler() {
		return spartacusHandler;
	}

	public void setSpartacusHandler(ISpartacusHandler spartacusHandler) {
		this.spartacusHandler = spartacusHandler;
	}

	public NotaFiscalDataMapper getNotaFiscalDataMapper() {
		return notaFiscalDataMapper;
	}

	public void setNotaFiscalDataMapper(NotaFiscalDataMapper notaFiscalDataMapper) {
		this.notaFiscalDataMapper = notaFiscalDataMapper;
	}

	public NotaFiscalBusiness getNotaFiscalBusiness() {
		return notaFiscalBusiness;
	}

	public void setNotaFiscalBusiness(NotaFiscalBusiness notaFiscalBusiness) {
		this.notaFiscalBusiness = notaFiscalBusiness;
	}

	public NotaFiscalFinder getNotaFiscalFinder() {
		return notaFiscalFinder;
	}

	public void setNotaFiscalFinder(NotaFiscalFinder notaFiscalFinder) {
		this.notaFiscalFinder = notaFiscalFinder;
	}

	public PessoaFinder getPessoaFinder() {
		return pessoaFinder;
	}

	public void setPessoaFinder(PessoaFinder pessoaFinder) {
		this.pessoaFinder = pessoaFinder;
	}

	public ConfigFinder getConfigFinder() {
		return configFinder;
	}

	public void setConfigFinder(ConfigFinder configFinder) {
		this.configFinder = configFinder;
	}

}
