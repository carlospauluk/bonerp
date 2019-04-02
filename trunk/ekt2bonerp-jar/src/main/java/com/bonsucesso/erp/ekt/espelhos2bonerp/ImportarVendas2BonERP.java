package com.bonsucesso.erp.ekt.espelhos2bonerp;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.base.model.Pessoa;
import com.bonsucesso.erp.ekt.data.EKTProdutoFinder;
import com.bonsucesso.erp.ekt.data.EKTVendaFinder;
import com.bonsucesso.erp.ekt.data.EKTVendaItemFinder;
import com.bonsucesso.erp.ekt.data.EKTVendedorFinder;
import com.bonsucesso.erp.ekt.model.EKTProduto;
import com.bonsucesso.erp.ekt.model.EKTVenda;
import com.bonsucesso.erp.ekt.model.EKTVendaItem;
import com.bonsucesso.erp.ekt.model.EKTVendedor;
import com.bonsucesso.erp.estoque.data.GradeFinder;
import com.bonsucesso.erp.estoque.data.ProdutoDataMapper;
import com.bonsucesso.erp.estoque.data.ProdutoFinder;
import com.bonsucesso.erp.estoque.model.Grade;
import com.bonsucesso.erp.estoque.model.GradeTamanho;
import com.bonsucesso.erp.estoque.model.Produto;
import com.bonsucesso.erp.estoque.model.ProdutoPreco;
import com.bonsucesso.erp.fiscal.business.NotaFiscalBusiness;
import com.bonsucesso.erp.rh.data.FuncionarioDataMapper;
import com.bonsucesso.erp.rh.data.FuncionarioFinder;
import com.bonsucesso.erp.rh.model.Funcionario;
import com.bonsucesso.erp.vendas.business.MesVendaBusiness;
import com.bonsucesso.erp.vendas.data.PlanoPagtoFinder;
import com.bonsucesso.erp.vendas.data.TipoVendaFinder;
import com.bonsucesso.erp.vendas.data.VendaDataMapper;
import com.bonsucesso.erp.vendas.data.VendaFinder;
import com.bonsucesso.erp.vendas.model.PlanoPagto;
import com.bonsucesso.erp.vendas.model.StatusVenda;
import com.bonsucesso.erp.vendas.model.Venda;
import com.bonsucesso.erp.vendas.model.VendaItem;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.utils.currency.CurrencyUtils;
import com.ocabit.utils.strings.Mailer;
import com.ocabit.utils.strings.StringUtils;


@Component("importarVendas2BonERP")
@Scope("prototype") // para poder rodar com thread (não retorna sempre o mesmo bean)
public class ImportarVendas2BonERP implements IImportarVendas2BonERP {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8516699836227113828L;

	protected static Logger logger = Logger.getLogger(ImportarVendas2BonERP.class);

	public static SimpleDateFormat sdfLog = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

	@Value("${config.logsdir}")
	private String dirLogs;

	private String logFile;

	public static PrintWriter w;

	@Autowired
	private BeanFactory beanFactory;

	// ------ DATA DO MÊS SENDO IMPORTADO
	private static Date dtMesImport;

	private static String mesImport;

	public static SimpleDateFormat sdfMesAno = new SimpleDateFormat("yyyyMM");

	private IImportarVendas2BonERP thiz;

	private int inicio;
	private int fim;

	@Autowired
	private ProdutoFinder produtoFinder;

	@Autowired
	private EKTProdutoFinder ektProdutoFinder;

	@Autowired
	private ProdutoDataMapper produtoDataMapper;

	@Autowired
	private EKTVendaFinder ektVendaFinder;

	@Autowired
	private EKTVendaItemFinder ektVendaItemFinder;

	@Autowired
	private VendaDataMapper vendaDataMapper;

	@Autowired
	private VendaFinder vendaFinder;

	@Autowired
	private PlanoPagtoFinder planoPagtoFinder;

	@Autowired
	private FuncionarioFinder funcionarioFinder;

	@Autowired
	private FuncionarioDataMapper funcionarioDataMapper;

	@Autowired
	private EKTVendedorFinder ektVendedorFinder;

	@Autowired
	private GradeFinder gradeFinder;

	@Autowired
	private TipoVendaFinder tipoVendaFinder;

	@Autowired
	private NotaFiscalBusiness notaFiscalBusiness;

	@Autowired
	private MesVendaBusiness mesVendaBusiness;

	@Override
	public void run() {
		final Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		final org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User("carlos", "none", true, true, true, true, authorities);
		Authentication auth = new TestingAuthenticationToken(user, authorities);
		SecurityContextHolder.getContext().setAuthentication(auth);
		getThiz().importarVendas();
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void importarVendas() {

		try {
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  INICIANDO IMPORTAÇÃO DE VENDAS. [THREAD:" + getInicio()
					+ "]");
			List<EKTVenda> ektVendas = getEktVendaFinder().findAllValidas(getInicio(), getFim(), mesImport);
			//List<EKTVenda> ektVendas = new ArrayList<EKTVenda>();
			//ektVendas.add(getEktVendaFinder().findBy(692.0, "201707"));

			// A linha abaixo é para tentar importar por uma venda em específico.

			int i = 0;
			int c = 0;
			int j = 0;

			// percorre todos os produtos do EKT
			for (EKTVenda ektVenda : ektVendas) {

				logger.info(ektVenda.toStringToView());

				// Se tiver com problema no registro
				if (ektVenda.getEmissao() == null || ektVenda.getVendedor() == 0.0) {
					continue;
				}

				Venda venda = getVendaFinder().findBy(ektVenda.getNumero().intValue(), ektVenda.getEmissao());
				//				if (venda == null || !ektVenda.getTotal().equals(venda.getValorTotal().doubleValue())) {
				getThiz().salvarVenda(ektVenda, venda);
				//					logger.info(c++ + " venda(s) processada(s)");
				//				} else {
				//					logger.info(j++ + " venda(s) já salva(s)");
				//				}
				i++;

			}

			logger.info(i + " venda(s) processada(s)");

			w.println();
			w.println();
			w.println("VENDAS PROCESSADAS [THREAD:" + getInicio() + "] .................................. " + i);
			w.println("VENDAS JÁ SALVAS [THREAD:" + getInicio() + "] .................................... " + j);
			w.println("VENDAS CADASTRADAS [THREAD:" + getInicio() + "] .................................. " + c);
			w.println();
			w.println();

		} catch (Exception e) {
			String err = "ERRO AO IMPORTAR VENDAS [THREAD:" + getInicio() + "]";
			logger.error(err, e);
			w.println(err);
			w.println(StringUtils.stackTraceToString(e));
			w.println();
			throw new IllegalStateException(err, e);
		}

		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  FINALIZANDO IMPORTAÇÃO DE VENDAS [THREAD:" + getInicio()
				+ "]");

	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void salvarVenda(EKTVenda ektVenda, Venda venda) throws ViewException {
		try {

			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> SALVANDO VENDA: " + ektVenda.getNumero().intValue()
					+ " [THREAD:"
					+ getInicio() + "]");

			if (venda == null) {
				venda = new Venda();
			} else {
				// limpa todos os filhos
				venda = getVendaFinder().refresh(venda);
				venda.getItens().clear();
				venda = getVendaDataMapper().save(venda);
				getVendaDataMapper().getEntityManager().flush();
				venda = getVendaFinder().refresh(venda);
			}
			getVendaDataMapper().getEntityManager().flush();

			venda.setStatus(StatusVenda.FINALIZADA);

			venda.setTipo(getTipoVendaFinder().findById(1l));
			venda.setMesano(sdfMesAno.format(dtMesImport));
			venda.setPv(ektVenda.getNumero().intValue());
			venda.setDtVenda(ektVenda.getEmissao());

			// Encontra o plano de pagto
			PlanoPagto planoPagto = getPlanoPagtoFinder().findBy(ektVenda.getCondPag());
			venda.setPlanoPagto(planoPagto);

			// Encontra o vendedor (ou insere um novo)
			venda.setVendedor(getThiz().handleVendedor(ektVenda));

			// Encontra todos os itens da venda
			List<EKTVendaItem> ektItens = getEktVendaItemFinder().findBy(ektVenda.getNumero(), mesImport);

			BigDecimal subTotalVenda = CurrencyUtils.getBigDecimalCurrency(0.0);

			GradeTamanho gradeTamanhoPara88888 = getGradeFinder().findByCodigoGradeAndTamanho(11, "UN");

			// Para cada item da venda...
			for (EKTVendaItem ektItem : ektItens) {
				logger.info("SALVANDO ITEM: " + ektItem.getProduto().intValue() + " - " + ektItem.getDescricao());

				if (ektItem.getQtde() == 0.0) {
					logger.info("************* PULANDO ITEM COM QTDE = 0.0");
					continue;
				}

				if (ektItem.getDescricao() == null) {
					logger.info("************* PULANDO ITEM COM DESCRICAO = 'NULL'");
					continue;
				}

				VendaItem item = new VendaItem();
				item.setVenda(venda);

				Produto produto;
				GradeTamanho gradeTamanho = null;

				Integer reduzido = ektItem.getProduto().intValue();

				BigDecimal bdPrecoVenda = CurrencyUtils.getBigDecimalCurrency(ektItem.getVlrUnit());
				BigDecimal bdQtde = CurrencyUtils.getBigDecimalScaled(ektItem.getQtde(), 3);
				BigDecimal bdValorTotal = CurrencyUtils.getBigDecimalCurrency(CurrencyUtils
						.multiplica(bdPrecoVenda.doubleValue(), bdQtde.doubleValue()));

				item.setPrecoVenda(bdPrecoVenda);

				item.setQtde(CurrencyUtils.getBigDecimalScaled(ektItem.getQtde(), 3));

				// Para NCs
				if (reduzido == 88888) {
					item.setGradeTamanho(null);
					item.setNcReduzido(reduzido.longValue());

					item.setNcDescricao(ektItem.getDescricao());
					item.setNcGradeTamanho(ektItem.getTamanho());
					item.setGradeTamanho(gradeTamanhoPara88888);

					item.setObs("NC 88888");
				} else {
					// Pego o produto bonerp pelo reduzido EKT + mesano
					try {
						List<Produto> prods = getProdutoFinder()
								.findByReduzidoEkt(reduzido, dtMesImport);
						if (prods.isEmpty()) {
							throw new ViewException("Produto não encontrado");
						}
						produto = prods.get(0);
					} catch (Exception e2) {
						String err = "Erro ao pesquisar produto EKT no período: " + reduzido
								+ " - " + dtMesImport;
						logger.error(err, e2);
						w.println(err);
						w.println(StringUtils.stackTraceToString(e2));
						w.println();
						throw new IllegalStateException(err, e2);
					}

					// Pego o ekt produto
					EKTProduto ektProduto = null;
					try {
						ektProduto = getEktProdutoFinder().findByReduzido(ektItem.getProduto().intValue(), mesImport);
					} catch (Exception e) {
						String err = "Erro ao pesquisar produto EKT";
						logger.error(err, e);
						w.println(err);
						w.println(StringUtils.stackTraceToString(e));
						w.println();
						throw new IllegalStateException(err, e);
					}

					// verifica se por acaso as descrições estão diferentes (NÃO DEVERIAM)
					if (!produto.getDescricao().equals(ektProduto.getDescricao())) {
						logger.info(">>>>>>>>>>>>>>>>>>>>>>> DESCRIÇÕES NÃO BATEM");
						logger.info("PRODUTO: " + produto.getDescricao());
						logger.info("EKT PRODUTO: " + ektProduto.getDescricao());
					}

					try {
						if (ektProduto.getGrade() == null) {
							throw new ViewException("Grade NULL");
						} else {
							if (ektItem.getTamanho() == null || "".equals(ektItem.getTamanho())) {
								// se o tamanho não foi informado, seleciona o primeiro da grade.
								Grade grade = getGradeFinder().findByCodigo(11);
								ektItem.setTamanho(grade.getTamanhos().get(0).getTamanho()); // grade "UN"
							}
						}

						gradeTamanho = getGradeFinder()
								.findByCodigoGradeAndTamanho(ektProduto.getGrade().intValue(), ektItem.getTamanho());

						if (gradeTamanho == null) {

							Grade grade = getGradeFinder().findByCodigo(ektProduto.getGrade().intValue());
							if (grade == null) {
								throw new ViewException("grade/tamanho == null");
							} else {
								// caso dê problema na grade, pega o primeiro tamanho
								gradeTamanho = grade.getTamanhos().get(0);
							}
						}

					} catch (Exception e1) {
						String err = "Erro ao pesquisar grade/tamanho";
						logger.error(err, e1);
						w.println(err);
						w.println(StringUtils.stackTraceToString(e1));
						w.println();
						throw new IllegalStateException(err, e1);
					}

					item.setGradeTamanho(gradeTamanho);

					if (bdValorTotal.doubleValue() != ektItem.getVlrTotal()) {
						logger.info("********** ATENÇÃO: erro em total de produto. Total Produto EKT: "
								+ ektItem.getVlrTotal() + ". Total Calculado: " + bdValorTotal.doubleValue());
						// throw new ViewException("Erro no valor total do produto");
					}

					// Aqui verifica se o preço utilizado na venda era o mesmo do produto (para ver se houve alteração).
					boolean achou = false;
					for (ProdutoPreco preco : produto.getPrecos()) {
						if (preco.getPrecoPrazo().equals(item.getPrecoVenda())
								|| (preco.getPrecoPromo() != null
										&& preco.getPrecoPromo().equals(item.getPrecoVenda()))) {
							achou = true;
							break;
						}
					}

					if (achou) {
						item.setAlteracaoPreco(false);
					} else {
						logger.info("PREÇO ALTERADO: item: " + bdPrecoVenda.doubleValue());
						for (ProdutoPreco preco : produto.getPrecos()) {
							logger.info("Produto (prazo): " + preco.getPrecoPrazo() + " | (promo): "
									+ preco.getPrecoPromo());
						}
						item.setAlteracaoPreco(true);
					}

					// Se foi mexida na descrição do produto, copia-a para o campo obs
					if (ektItem.getDescricao() != null && !ektItem.getDescricao().equals(produto.getDescricao())) {
						item.setObs(produto.getDescricao());
					}

					item.setProduto(produto);
					String ncm = produto.getNcm();
					item.setNcm(ncm);
					item.setNcmExistente(getNotaFiscalBusiness().findNCMExiste(ncm));

				}

				venda.getItens().add(item);

				// Verifica se o valor total do item bate

				subTotalVenda = subTotalVenda.add(bdValorTotal);
			}

			if (ektVenda.getDescAcres() == null) {
				ektVenda.setDescAcres(0.0);
			}
			if (ektVenda.getDescEspecial() == null) {
				ektVenda.setDescEspecial(0.0);
			}

			venda.setDescontoPlano(CurrencyUtils.getBigDecimalCurrency(ektVenda.getDescAcres()));
			venda.setDescontoEspecial(CurrencyUtils.getBigDecimalCurrency(ektVenda.getDescEspecial()));
			venda.setHistoricoDesconto(ektVenda.getHistDesc());

			if (subTotalVenda.doubleValue() != ektVenda.getSubTotal()) {
				logger.info("********** ATENÇÃO: erro em SUB TOTAL VENDA: " + ektVenda.getSubTotal() + ". TOTAL SOMA: "
						+ subTotalVenda.doubleValue());
			}

			venda.setSubTotal(subTotalVenda);

			BigDecimal totalVenda = venda.getSubTotal().add(venda.getDescontoPlano()).add(venda.getDescontoEspecial());

			if (totalVenda.doubleValue() != ektVenda.getTotal()) {
				logger.info("********** ATENÇÃO: erro em TOTAL VENDA: " + ektVenda.getTotal() + ". TOTAL SOMA: "
						+ totalVenda.doubleValue());
			}

			venda.setSubTotal(subTotalVenda);

			// venda.setValorTotal(valorTotalVenda);
			// seto o valor da soma do Edson, pra não dar problema com os históricos do total mês.
			venda.setValorTotal(CurrencyUtils.getBigDecimalCurrency(ektVenda.getTotal()));
			
			// Mandando alterar pra ver se força o hibernate a atualizar (bug: qd alterava só o planoPagto, não estava dando o UPDATE)
			getVendaDataMapper().getEntityIdHandler().handleEntityId(venda);
			
			venda = getVendaDataMapper().save(venda);

			getVendaDataMapper().getEntityManager().flush();

			logger.info(">>>>>>>>>>>>>>>> VENDA SALVA:  [THREAD:" + getInicio() + "]");

		} catch (Exception e) {
			String err = "Erro ao salvar venda: " + ektVenda.toStringToView();
			logger.error(err, e);
			w.println(err);
			w.println(StringUtils.stackTraceToString(e));
			w.println();
			throw new IllegalStateException(err, e);
		}

	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void marcarVendasDeletadas() {

		try {
			int todas = getEktVendaFinder().getEntityManager()
					.createNativeQuery("UPDATE ven_venda SET deletado = false WHERE mesano = '" + mesImport + "'")
					.executeUpdate();

			getEktVendaFinder().getEntityManager().flush();

			int deletadas = getEktVendaFinder().getEntityManager()
					.createNativeQuery("UPDATE ven_venda SET deletado = true WHERE mesano = '" + mesImport
							+ "' AND pv NOT IN (SELECT numero FROM ekt_venda WHERE mesano = '" + mesImport + "')")
					.executeUpdate();
			
			// Também marca como 'deletado' os PVs que foram cancelados (a partir de set/2017 eles não são mais deletados do ekt)
			int canceladas = getEktVendaFinder().getEntityManager()
					.createNativeQuery("UPDATE ven_venda SET deletado = true WHERE plano_pagto_id = 158")
					.executeUpdate();

			getEktVendaFinder().getEntityManager().flush();

			logger.info("_______________________________" + deletadas + " venda(s) deletada(s) de um total de "
					+ todas);
			w.println();
			w.println();
			w.println("VENDAS DELETADAS .................................. " + deletadas);
			w.println("VENDAS CANCELADAS .................................. " + canceladas);
		} catch (Exception e) {
			String err = "Erro ao marcar vendas deletadas";
			logger.error(err, e);
			w.println(err);
			w.println(StringUtils.stackTraceToString(e));
			w.println();
			throw new IllegalStateException(err, e);
		}
	}

	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED)
	public Funcionario handleVendedor(EKTVenda ektVenda) throws ViewException {

		try {
			Integer codigoVendedor = ektVenda.getVendedor().intValue();
			EKTVendedor ektVendedor = getEktVendedorFinder().findByCodigo(ektVenda.getVendedor());

			if (ektVendedor.getNome() == null || "".equals(ektVendedor.getNome().trim())) {
				ektVendedor.setNome("<<< ERRO DE IMPORTAÇÃO (nome não informado) >>>");
			}

			getEktProdutoFinder().getEntityManager().detach(ektVendedor);

			Funcionario vendedor = getFuncionarioFinder()
					.findByCodigoAndNomeEkt(ektVendedor.getNome(), codigoVendedor);

			if (vendedor == null) {

				vendedor = new Funcionario();
				Pessoa pessoa = new Pessoa();

				String cpf = new SimpleDateFormat("YYMM").format(dtMesImport)
						+ StringUtils.zerofill(codigoVendedor.toString(), 7);
				pessoa.setDocumento(cpf);

				pessoa.setNome(ektVendedor.getNome());
				vendedor.setNomeEkt(ektVendedor.getNome());
				vendedor.setCodigo(ektVendedor.getCodigo().intValue());

				vendedor.setPessoa(pessoa);

				vendedor.setVendedorComissionado(true);
				vendedor.setClt(true);
				vendedor = getFuncionarioDataMapper().save(vendedor);

			}

			return vendedor;
		} catch (Exception e) {
			String err = "Erro ao salvar vendedor: " + ektVenda.toStringToView();
			logger.error(err, e);
			w.println(err);
			w.println(StringUtils.stackTraceToString(e));
			w.println();
			throw new IllegalStateException(err, e);
		}
	}

	@Override
	public void preencherMesVenda() {
		final Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		final org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User("carlos", "none", true, true, true, true, authorities);
		Authentication auth = new TestingAuthenticationToken(user, authorities);
		SecurityContextHolder.getContext().setAuthentication(auth);

		w.println();
		w.println("mesVendaBusiness.preencherMesVenda('" + ImportarVendas2BonERP.dtMesImport + "')");
		try {
			getMesVendaBusiness().preencherMesVenda(ImportarVendas2BonERP.dtMesImport);
		} catch (ViewException e) {
			w.println("Erro");
			w.println(StringUtils.stackTraceToString(e));
			return;
		}
		w.println("OK");
	}

	@Override
	public IImportarVendas2BonERP getThiz() {
		// aqui tive que fazer desta forma por causa das threads. o Thiz é passado no main.
		return thiz;
	}

	@Override
	public void setThiz(IImportarVendas2BonERP thiz) {
		this.thiz = thiz;
	}

	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	public ProdutoFinder getProdutoFinder() {
		return produtoFinder;
	}

	public void setProdutoFinder(ProdutoFinder produtoFinder) {
		this.produtoFinder = produtoFinder;
	}

	public EKTVendaFinder getEktVendaFinder() {
		return ektVendaFinder;
	}

	public void setEktVendaFinder(EKTVendaFinder ektVendaFinder) {
		this.ektVendaFinder = ektVendaFinder;
	}

	public VendaDataMapper getVendaDataMapper() {
		return vendaDataMapper;
	}

	public void setVendaDataMapper(VendaDataMapper vendaDataMapper) {
		this.vendaDataMapper = vendaDataMapper;
	}

	public VendaFinder getVendaFinder() {
		return vendaFinder;
	}

	public void setVendaFinder(VendaFinder vendaFinder) {
		this.vendaFinder = vendaFinder;
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

	public FuncionarioDataMapper getFuncionarioDataMapper() {
		return funcionarioDataMapper;
	}

	public void setFuncionarioDataMapper(FuncionarioDataMapper funcionarioDataMapper) {
		this.funcionarioDataMapper = funcionarioDataMapper;
	}

	public EKTVendedorFinder getEktVendedorFinder() {
		return ektVendedorFinder;
	}

	public void setEktVendedorFinder(EKTVendedorFinder ektVendedorFinder) {
		this.ektVendedorFinder = ektVendedorFinder;
	}

	public EKTVendaItemFinder getEktVendaItemFinder() {
		return ektVendaItemFinder;
	}

	public void setEktVendaItemFinder(EKTVendaItemFinder ektVendaItemFinder) {
		this.ektVendaItemFinder = ektVendaItemFinder;
	}

	public GradeFinder getGradeFinder() {
		return gradeFinder;
	}

	public void setGradeFinder(GradeFinder gradeFinder) {
		this.gradeFinder = gradeFinder;
	}

	public EKTProdutoFinder getEktProdutoFinder() {
		return ektProdutoFinder;
	}

	public void setEktProdutoFinder(EKTProdutoFinder ektProdutoFinder) {
		this.ektProdutoFinder = ektProdutoFinder;
	}

	public ProdutoDataMapper getProdutoDataMapper() {
		return produtoDataMapper;
	}

	public void setProdutoDataMapper(ProdutoDataMapper produtoDataMapper) {
		this.produtoDataMapper = produtoDataMapper;
	}

	public int getInicio() {
		return inicio;
	}

	public void setInicio(int inicio) {
		this.inicio = inicio;
	}

	public int getFim() {
		return fim;
	}

	public void setFim(int fim) {
		this.fim = fim;
	}

	@Override
	public String getLogFile() {
		return logFile;
	}

	@Override
	public void setLogFile(String logFile) {
		this.logFile = logFile;
	}

	@Override
	public String getDirLogs() {
		return dirLogs;
	}

	@Override
	public void setDirLogs(String dirLogs) {
		this.dirLogs = dirLogs;
	}

	public TipoVendaFinder getTipoVendaFinder() {
		return tipoVendaFinder;
	}

	public void setTipoVendaFinder(TipoVendaFinder tipoVendaFinder) {
		this.tipoVendaFinder = tipoVendaFinder;
	}

	public NotaFiscalBusiness getNotaFiscalBusiness() {
		return notaFiscalBusiness;
	}

	public void setNotaFiscalBusiness(NotaFiscalBusiness notaFiscalBusiness) {
		this.notaFiscalBusiness = notaFiscalBusiness;
	}

	public MesVendaBusiness getMesVendaBusiness() {
		return mesVendaBusiness;
	}

	public void setMesVendaBusiness(MesVendaBusiness mesVendaBusiness) {
		this.mesVendaBusiness = mesVendaBusiness;
	}

	/**
	 * @param args
	 * @throws FileNotFoundException
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) {

		try {
			long ini = System.currentTimeMillis();

			System.out.println("Informe o mês import. Formato: YYYYMM");

			if (args.length < 1) {
				args = new String[] { sdfMesAno.format(new Date()) };
			}

			mesImport = args[0];

			try {
				if (mesImport.length() > 6) {
					throw new IllegalStateException();
				}
				Integer.parseInt(args[0]);
				ImportarVendas2BonERP.dtMesImport = sdfMesAno.parse(mesImport);
			} catch (Exception e1) {
				System.out.println("Erro ao parsear o mesImport");
				System.out.println("Informe o mês import. Formato: MM-YYYY");
				System.exit(0);
			}

			System.out.println("Importando para o mês/ano: " + mesImport);

			ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

			// Não posso mais deletar por causa da relação com as Notas Fiscais
			//		IImportarVendas2BonERP tDeletar = (IImportarVendas2BonERP) context.getBean("importarVendas2BonERP");
			//		tDeletar.deletarVendas();

			IImportarVendas2BonERP t = (IImportarVendas2BonERP) context.getBean("importarVendas2BonERP");

			String sufixo = sdfLog.format(new Date()) + "_VENDAS.txt";

			t.setLogFile(t.getDirLogs() + sufixo);
			w = new PrintWriter(t.getLogFile());
			w.println("<<< IMPORTAÇÃO DE VENDAS >>>");
			w.println(t.getLogFile());

			ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");

			int j = 500;
			for (int i = 0; i <= 6000; i += j) {
										//	 for (int i = 0; i < 500; i += j) {
				IImportarVendas2BonERP t1 = (IImportarVendas2BonERP) context.getBean("importarVendas2BonERP");
				t1.setInicio(i);
				t1.setFim(j);
				t1.setThiz(t1);
				taskExecutor.execute(t1);
			}

			for (;;) {
				int count = taskExecutor.getActiveCount();
				System.out.println("Active Threads : " + count);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (count == 0) {
					taskExecutor.shutdown();
					break;
				}
			}

			t.marcarVendasDeletadas();

			t.preencherMesVenda();

			long fim = System.currentTimeMillis();
			double tempo = (fim - ini) / 1000;
			logger.info("Tempo de exec.: " + tempo + " segundos");
			logger.info("Log: " + t.getLogFile());

			w.flush();

			try {
				Mailer.sendMail("casabonsucesso@gmail.com", sufixo, StringUtils
						.inputStreamToString(new FileInputStream(new File(t.getLogFile()))));
			} catch (ViewException e) {
				logger.error("Erro ao enviar o e-mail para 'carlospauluk@gmail.com' (" + sufixo + ")");
			}

			w.println();
			w.println();
			w.println(tempo);
		} catch (Exception e) {
			e.printStackTrace();
			if (w != null) {
				e.printStackTrace(w);
			}
			System.out.println("Erro ao importar vendas");
			System.exit(-1);
		} finally {
			if (w != null) {
				w.close();
			}
		}

		System.exit(0);
	}

}
