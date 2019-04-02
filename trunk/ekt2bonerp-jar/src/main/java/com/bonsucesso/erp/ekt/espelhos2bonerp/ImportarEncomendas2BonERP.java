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

import javax.persistence.Query;

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
import com.bonsucesso.erp.ekt.data.EKTEncomendaFinder;
import com.bonsucesso.erp.ekt.data.EKTEncomendaItemFinder;
import com.bonsucesso.erp.ekt.data.EKTProdutoFinder;
import com.bonsucesso.erp.ekt.data.EKTVendedorFinder;
import com.bonsucesso.erp.ekt.model.EKTEncomenda;
import com.bonsucesso.erp.ekt.model.EKTEncomendaItem;
import com.bonsucesso.erp.ekt.model.EKTProduto;
import com.bonsucesso.erp.ekt.model.EKTVendedor;
import com.bonsucesso.erp.estoque.data.GradeFinder;
import com.bonsucesso.erp.estoque.data.ProdutoDataMapper;
import com.bonsucesso.erp.estoque.data.ProdutoFinder;
import com.bonsucesso.erp.estoque.model.Grade;
import com.bonsucesso.erp.estoque.model.GradeTamanho;
import com.bonsucesso.erp.estoque.model.Produto;
import com.bonsucesso.erp.estoque.model.ProdutoPreco;
import com.bonsucesso.erp.rh.data.FuncionarioDataMapper;
import com.bonsucesso.erp.rh.data.FuncionarioFinder;
import com.bonsucesso.erp.rh.model.Funcionario;
import com.bonsucesso.erp.vendas.data.EncomendaDataMapper;
import com.bonsucesso.erp.vendas.data.EncomendaFinder;
import com.bonsucesso.erp.vendas.data.PlanoPagtoFinder;
import com.bonsucesso.erp.vendas.model.Encomenda;
import com.bonsucesso.erp.vendas.model.EncomendaItem;
import com.bonsucesso.erp.vendas.model.StatusEncomenda;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.utils.currency.CurrencyUtils;
import com.ocabit.utils.strings.Mailer;
import com.ocabit.utils.strings.StringUtils;


@Component("importarEncomendas2BonERP")
@Scope("prototype") // para poder rodar com thread (não retorna sempre o mesmo bean)
public class ImportarEncomendas2BonERP implements IImportarEncomendas2BonERP {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7276695260772242987L;

	protected static Logger logger = Logger.getLogger(ImportarEncomendas2BonERP.class);

	public static SimpleDateFormat sdfLog = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

	@Value("${config.logsdir}")
	private String dirLogs;

	private String logFile;

	public static PrintWriter w;

	@Autowired
	private BeanFactory beanFactory;

	public static SimpleDateFormat sdfMesAno = new SimpleDateFormat("yyyyMM");

	private IImportarEncomendas2BonERP thiz;

	private int inicio;
	private int fim;

	@Autowired
	private ProdutoFinder produtoFinder;

	@Autowired
	private EKTProdutoFinder ektProdutoFinder;

	@Autowired
	private ProdutoDataMapper produtoDataMapper;

	@Autowired
	private EKTEncomendaFinder ektEncomendaFinder;

	@Autowired
	private EKTEncomendaItemFinder ektEncomendaItemFinder;

	@Autowired
	private EncomendaDataMapper encomendaDataMapper;

	@Autowired
	private EncomendaFinder encomendaFinder;

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

	@Override
	public void run() {
		final Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		final org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User("carlos", "none", true, true, true, true, authorities);
		Authentication auth = new TestingAuthenticationToken(user, authorities);
		SecurityContextHolder.getContext().setAuthentication(auth);
		getThiz().importarEncomendas();
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void deletarEncomendas() {
		try {
			getEncomendaDataMapper().getEntityManager().createNativeQuery("DELETE FROM ven_encomenda_item")
					.executeUpdate();
			getEncomendaDataMapper().getEntityManager().createNativeQuery("DELETE FROM ven_encomenda")
					.executeUpdate();
			getEncomendaDataMapper().getEntityManager().flush();
		} catch (Exception e) {
			String err = "ERRO AO DELETAR ENCOMENDAS";
			logger.error(err, e);
			w.println(err);
			w.println(StringUtils.stackTraceToString(e));
			w.println();
			throw new IllegalStateException(err, e);
		}
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void importarEncomendas() {

		try {
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  INICIANDO IMPORTAÇÃO DE ENCOMENDAS. [THREAD:"
					+ getInicio()
					+ "]");
			List<EKTEncomenda> ektEncomendas = getEktEncomendaFinder().findAllValidas(getInicio(), getFim());
			int i = 0;
			int c = 0;
			int a = 0;

			List<Integer> numeros = new ArrayList<Integer>();

			// percorre todos os produtos do EKT
			for (EKTEncomenda ektEncomenda : ektEncomendas) {

				getEktEncomendaFinder().getEntityManager().detach(ektEncomenda);

				numeros.add(ektEncomenda.getNumero().intValue());

				logger.info(ektEncomenda.toStringToView());

				Encomenda encomenda = getEncomendaFinder().findBy(ektEncomenda.getNumero().intValue());

				if (encomenda != null) {

					int qtdeItens = encomenda.getItens() != null ? encomenda.getItens().size() : 0;
					getEncomendaFinder().getEntityManager().detach(encomenda);

					// se a encomenda não tiver itens, apaga e resalva (fiz isso pq tinha muita encomenda sem itens sendo importadas)
					if (qtdeItens == 0) {
						getThiz().deletarEncomenda(encomenda);
						getThiz().salvarEncomenda(ektEncomenda);
						c++;
					} else {
						getEncomendaFinder().getEntityManager().detach(encomenda);
						getThiz().atualizarEncomenda(encomenda, ektEncomenda);
						a++;
					}
				} else {
					getThiz().salvarEncomenda(ektEncomenda);
					c++;
				}

				i++;

			}

			logger.info(i + " encomenda(s) processada(s)");

			w.println();
			w.println();
			w.println("ENCOMENDAS PROCESSADAS [THREAD:" + getInicio() + "] .................................. " + i);
			w.println("ENCOMENDAS ATUALIZADAS [THREAD:" + getInicio() + "] .................................... " + a);
			w.println("ENCOMENDAS CADASTRADAS [THREAD:" + getInicio() + "] .................................. " + c);
			w.println();
			w.println();

		} catch (Exception e) {
			String err = "ERRO AO IMPORTAR ENCOMENDAS [THREAD:" + getInicio() + "]";
			logger.error(err, e);
			w.println(err);
			w.println(StringUtils.stackTraceToString(e));
			w.println();
			throw new IllegalStateException(err, e);
		}

		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  FINALIZANDO IMPORTAÇÃO DE ENCOMENDAS [THREAD:" + getInicio()
				+ "]");
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void deletarEncomenda(Encomenda encomenda) throws ViewException {
		encomenda = getEncomendaFinder().refresh(encomenda);
		getEncomendaDataMapper().delete(encomenda);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void atualizarEncomenda(Encomenda encomenda, EKTEncomenda ektEncomenda) throws ViewException {

		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ATUALIZANDO ENCOMENDA: " + ektEncomenda.getNumero().intValue()
				+ " [THREAD:"
				+ getInicio() + "]");

		encomenda = getEncomendaFinder().refresh(encomenda);

		List<EKTEncomendaItem> ektItens = getEktEncomendaItemFinder().findBy(ektEncomenda.getNumero());

		// verifica se foi mudada o valor total dos itens
		if (encomenda.getItens().size() != ektItens.size()) {
			throw new ViewException("Número de itens alterados: ENCOMENDA " + encomenda.getNumero());
		}

		// verifica se foi alterado o valor total dos itens
		BigDecimal ektTotalItens = CurrencyUtils.getBigDecimalCurrency("0.0");
		for (EKTEncomendaItem ektItem : ektItens) {

			BigDecimal bdPrecoEncomenda = CurrencyUtils.getBigDecimalCurrency(ektItem.getVlrUnit());
			BigDecimal bdQtde = CurrencyUtils.getBigDecimalScaled(ektItem.getQtde(), 3);
			BigDecimal bdValorTotal = CurrencyUtils.getBigDecimalCurrency(CurrencyUtils
					.multiplica(bdPrecoEncomenda.doubleValue(), bdQtde.doubleValue()));
			ektTotalItens = ektTotalItens.add(bdValorTotal);
		}
		if (!encomenda.getSubTotal().equals(ektTotalItens)) {
			throw new ViewException("Valor total dos itens alterado");
		}

		logger.info("TUDO CERTO................... ATUALIZANDO FLAGS");

		// Se estiver tudo certo, só acerta os flags
		for (EncomendaItem item : encomenda.getItens()) {

			for (EKTEncomendaItem ektItem : ektItens) {

				if (item.getProduto().getReduzidoEkt().equals(ektItem.getProduto().intValue())) {

					item.setIntegradoAoEstoque("S".equals(ektItem.getFlagInt()));

					if (ektItem.getFlag() == null || ektItem.getFlag().equals("NULL")) {
						item.setStatus(StatusEncomenda.CONFECCAO);
					} else {

						switch (ektItem.getFlag()) {
							case "D":
								item.setStatus(StatusEncomenda.DISPONIVEL);
								break;
							case "C":
								item.setStatus(StatusEncomenda.CANCELADA);
								break;
							case "E":
								item.setStatus(StatusEncomenda.ENTREGUE);
								break;
							case "P":
								item.setStatus(StatusEncomenda.PARCIAL);
								break;
							default:
								item.setStatus(StatusEncomenda.ERRO);
								break;
						}
					}

				}

			}
		}

		encomenda = getEncomendaDataMapper().save(encomenda);
		getEncomendaDataMapper().getEntityManager().flush();
		logger.info("ENCOMENDA ATUALIZADA [THREAD:" + getInicio() + "]");

	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void salvarEncomenda(EKTEncomenda ektEncomenda) throws ViewException {
		try {

			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> SALVANDO ENCOMENDA: " + ektEncomenda.getNumero().intValue()
					+ " [THREAD:"
					+ getInicio() + "]");

			if (ektEncomenda.getNumero() == 1044.0) {
				logger.info("DEBUGAR");
			}

			Encomenda encomenda = new Encomenda();
			encomenda.setNumero(ektEncomenda.getNumero().intValue());

			// Em algumas vezes deu problema ao importar a Data de Emissão do EKT
			// GAMBIARRA: caso isso aconteça, vou procurando nas encomendas anteriores uma que tenha data de emissão válida.
			// Uso essa data.
			if (ektEncomenda.getEmissao() != null) {
				encomenda.setDtEncomenda(ektEncomenda.getEmissao());
			} else {
				Date emissaoDeUmaAnterior = null;
				
				for (int i = 1; i < 20; i++) {
					try {
						EKTEncomenda anterior = getEktEncomendaFinder().findBy(ektEncomenda.getNumero() - i);
						if (anterior != null) {
							if (anterior.getEmissao() != null) {
								emissaoDeUmaAnterior = anterior.getEmissao();
								encomenda.setDtEncomenda(emissaoDeUmaAnterior);
								ektEncomenda.setEmissao(emissaoDeUmaAnterior);
								encomenda.setObs("ATENÇÃO: não foi possível identificar a data de emissão no EKT. Verificar voltando os backups.");
								break;
							}
						}
					} catch (Exception e) {
						logger.info("Erro ao tentar procurar encomendas anteriores.");
					}
				}
				if (emissaoDeUmaAnterior == null) {
					throw new IllegalStateException("Erro ao setar dtEncomenda");
				}
			}

			// Encontra o vendedor (ou insere um novo)
			encomenda.setVendedor(getThiz().handleVendedor(ektEncomenda));

			// Encontra todos os itens da encomenda
			List<EKTEncomendaItem> ektItens = getEktEncomendaItemFinder().findBy(ektEncomenda.getNumero());

			BigDecimal subTotalEncomenda = CurrencyUtils.getBigDecimalCurrency(0.0);

			// Para cada item da encomenda...
			for (EKTEncomendaItem ektItem : ektItens) {
				logger.info("SALVANDO ITEM: " + ektItem.getProduto().intValue() + " - " + ektItem.getDescricao());

				EncomendaItem item = new EncomendaItem();
				item.setEncomenda(encomenda);

				if (ektItem.getFlag() == null || ektItem.getFlag().equals("NULL")) {
					item.setStatus(StatusEncomenda.CONFECCAO);
				} else {

					switch (ektItem.getFlag()) {
						case "D":
							item.setStatus(StatusEncomenda.DISPONIVEL);
							break;
						case "C":
							item.setStatus(StatusEncomenda.CANCELADA);
							break;
						case "E":
							item.setStatus(StatusEncomenda.ENTREGUE);
							break;
						case "P":
							item.setStatus(StatusEncomenda.PARCIAL);
							break;
						default:
							item.setStatus(StatusEncomenda.ERRO);
							break;
					}
				}

				item.setIntegradoAoEstoque("S".equals(ektItem.getFlagInt()));

				Produto produto;
				GradeTamanho gradeTamanho = null;

				if (ektItem.getProduto().intValue() == 88888) {
					// Se for um 88888, só busca ele (que já devia ter sido previamente cadastrado na mão)
					produto = getProdutoFinder().findByReduzidoEkt(ektItem.getProduto().intValue(), null).get(0);
					item.setObs(ektItem.getDescricao());
					Grade grade = getGradeFinder().findById(12l);
					gradeTamanho = grade.getTamanhos().get(0);
				} else {
					// Pego o produto bonerp pelo reduzido EKT + mesano
					try {
						List<Produto> prods = getProdutoFinder()
								.findByReduzidoEkt(ektItem.getProduto().intValue(), ektEncomenda.getEmissao());
						if (prods != null) {
							produto = prods.get(0);
						} else {
							// se não achou ali em cima, deve ser por causa da "emissao" (mesano)
							Query qryProdutoPorDescricao = getEktProdutoFinder().getEntityManager()
									.createNativeQuery("SELECT * FROM est_produto WHERE reduzido_ekt = :reduzido_ekt AND descricao LIKE :descricao ORDER BY updated DESC LIMIT 1", Produto.class);
							qryProdutoPorDescricao.setParameter("reduzido_ekt", ektItem.getProduto());
							qryProdutoPorDescricao.setParameter("descricao", ektItem.getDescricao());
							produto = (Produto) qryProdutoPorDescricao.getSingleResult();
						}

					} catch (Exception e2) {
						// Se não achar o produto, seta como 88888 mesmo e bola pra frente
						produto = getProdutoFinder().findByReduzidoEkt(88888, null).get(0);
						item.setObs(ektItem.getProduto() + " - " + ektItem.getDescricao());
						Grade grade = getGradeFinder().findById(12l);
						gradeTamanho = grade.getTamanhos().get(0);

						// throw new ViewException("Erro ao pesquisar produto EKT no período");
					}

					// Pego o ekt produto
					EKTProduto ektProduto = null;
					try {
						ektProduto = getEktProdutoFinder().findByReduzido(ektItem.getProduto().intValue(), sdfMesAno
								.format(ektEncomenda.getEmissao()));
						if (ektProduto == null) {
							Query qryEktProduto = getEktProdutoFinder().getEntityManager()
									.createNativeQuery("SELECT * FROM ekt_produto WHERE reduzido = :reduzido ORDER BY updated DESC LIMIT 1", EKTProduto.class);
							qryEktProduto.setParameter("reduzido", ektItem.getProduto());
							ektProduto = (EKTProduto) qryEktProduto.getSingleResult();
						}
					} catch (Exception e) {
						String err = "ERRO AO IMPORTAR PESQUISAR PRODUTO EKT [THREAD:" + getInicio() + "]";
						logger.error(err, e);
						w.println(err);
						w.println(StringUtils.stackTraceToString(e));
						w.println();
						throw new IllegalStateException(err, e);
					}

					if (ektProduto == null) {
						logger.error("ektProduto não encontrado");
						throw new ViewException("Erro ao pesquisar ektProduto");
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
								ektItem.setTamanho(grade.getTamanhos().get(0).getTamanho());
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
						String err = "Erro ao pesquisar grade/tamanho [THREAD:" + getInicio() + "]";
						logger.error(err, e1);
						w.println(err);
						w.println(StringUtils.stackTraceToString(e1));
						w.println();
						throw new IllegalStateException(err, e1);
					}
				}

				item.setGradeTamanho(gradeTamanho);

				BigDecimal bdPrecoEncomenda = CurrencyUtils.getBigDecimalCurrency(ektItem.getVlrUnit());
				BigDecimal bdQtde = CurrencyUtils.getBigDecimalScaled(ektItem.getQtde(), 3);
				BigDecimal bdValorTotal = CurrencyUtils.getBigDecimalCurrency(CurrencyUtils
						.multiplica(bdPrecoEncomenda.doubleValue(), bdQtde.doubleValue()));
				if (bdValorTotal.doubleValue() != ektItem.getVlrTotal()) {
					logger.info("********** ATENÇÃO: erro em total de produto. Total Produto EKT: "
							+ ektItem.getVlrTotal() + ". Total Calculado: " + bdValorTotal.doubleValue());
					// throw new ViewException("Erro no valor total do produto");
				}

				item.setPrecoEncomenda(bdPrecoEncomenda);

				// Aqui verifica se o preço utilizado na encomenda era o mesmo do produto (para ver se houve alteração).
				boolean achou = false;
				for (ProdutoPreco preco : produto.getPrecos()) {
					if (preco.getPrecoPrazo().equals(item.getPrecoEncomenda())
							|| preco.getPrecoPromo().equals(item.getPrecoEncomenda())) {
						achou = true;
						break;
					}
				}

				if (achou) {
					item.setAlteracaoPreco(false);
				} else {
					logger.info("PREÇO ALTERADO: item: " + bdPrecoEncomenda.doubleValue());
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

				item.setQtde(CurrencyUtils.getBigDecimalScaled(ektItem.getQtde(), 3));
				encomenda.getItens().add(item);

				// Verifica se o valor total do item bate

				subTotalEncomenda = subTotalEncomenda.add(bdValorTotal);
			} // for

			encomenda.setSubTotal(subTotalEncomenda);

			// >>>>>>>>>>>>>> DESCONTO DO PLANO (OU OUTRO)
			if (ektEncomenda.getDescAcres() == null) {
				ektEncomenda.setDescAcres(0.0);
			}
			encomenda.setDescontoPlano(CurrencyUtils.getBigDecimalCurrency(ektEncomenda.getDescAcres() * -1));

			// >>>>>>>>>>>>>> TOTAL DA ENCOMENDA (SUBTOTAL - DESCONTO DO PLANO)
			BigDecimal totalEncomenda = encomenda.getSubTotal().add(encomenda.getDescontoPlano());
			encomenda.setValorTotal(totalEncomenda);

			// >>>>>>>>>>>>>> SINAL
			if (ektEncomenda.getDescEspecial() == null) {
				ektEncomenda.setDescEspecial(0.0);
			}
			encomenda.setDescontoPlano(CurrencyUtils.getBigDecimalCurrency(ektEncomenda.getDescEspecial() * -1));
			encomenda.setHistoricoDesconto(ektEncomenda.getHistDesc());

			if (ektEncomenda.getSubTotal() == 0.0 && ektEncomenda.getTotal() == 0.0) {
				encomenda.setValorPago(totalEncomenda.negate());
			} else {
				encomenda.setValorPago(CurrencyUtils.getBigDecimalCurrency(ektEncomenda.getDescEspecial() * -1));
			}

			if (ektEncomenda.getSdoPagar() != encomenda.getSaldoPagar().doubleValue()) {
				logger.info("NUMERO ENCOMENDA: " + ektEncomenda.getNumero());
				logger.info("********** ATENÇÃO: erro em SALDO A PAGAR: " + ektEncomenda.getSdoPagar()
						+ ". TOTAL CALCULADO: "
						+ encomenda.getSaldoPagar().doubleValue());
			}

			encomenda = getEncomendaDataMapper().save(encomenda);
			getEncomendaDataMapper().getEntityManager().flush();

			logger.info(">>>>>>>>>>>>>>>> ENCOMENDA SALVA:  [THREAD:" + getInicio() + "]");

		} catch (Exception e) {
			String err = "Erro ao salvar encomenda [THREAD:" + getInicio() + "] " + ektEncomenda.toStringToView();
			logger.error(err, e);
			w.println(err);
			w.println(StringUtils.stackTraceToString(e));
			w.println();
			throw new IllegalStateException(err, e);
		}

	}

	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED)
	public Funcionario handleVendedor(EKTEncomenda ektEncomenda) throws ViewException {

		try {
			Integer codigoVendedor = ektEncomenda.getVendedor().intValue();
			EKTVendedor ektVendedor = getEktVendedorFinder().findByCodigo(ektEncomenda.getVendedor());

			if (ektVendedor.getNome() == null || "".equals(ektVendedor.getNome().trim())) {
				ektVendedor.setNome("<<< ERRO DE IMPORTAÇÃO (nome não informado) >>>");
			}

			getEktProdutoFinder().getEntityManager().detach(ektVendedor);

			Funcionario vendedor = getFuncionarioFinder()
					.findByCodigoAndNomeEkt(ektVendedor.getNome(), codigoVendedor);

			if (vendedor == null) {

				vendedor = new Funcionario();
				Pessoa pessoa = new Pessoa();

				String cpf = new SimpleDateFormat("YYMM").format(ektEncomenda.getEmissao())
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
			String err = "ERRO AO SALVAR VENDEDOR [THREAD:" + getInicio() + "] " + ektEncomenda.toStringToView();
			logger.error(err, e);
			w.println(err);
			w.println(StringUtils.stackTraceToString(e));
			w.println();
			throw new IllegalStateException(err, e);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void marcarEncomendasDeletadas() {
		try {
			int d = 0;
			List<Double> ektEncomendas = getEktEncomendaFinder().getEntityManager()
					.createNativeQuery("SELECT numero FROM ekt_encomenda").getResultList();
			// verifica se alguma pode ter sido excluída
			List<Encomenda> encomendas = getEncomendaFinder().findAll();
			for (Encomenda encomenda : encomendas) {
				if (!ektEncomendas.contains(encomenda.getNumero().doubleValue())) {
					encomenda.setDeletado(true);
					encomenda = getEncomendaDataMapper().save(encomenda);
					getEncomendaDataMapper().getEntityManager().flush();
					d++;
				}
			}
			logger.info("_______________________________" + d + " encomenda(s) deletada(s)");
			w.println();
			w.println();
			w.println("ENCOMENDAS DELETADAS .................................. " + d);
		} catch (Exception e) {
			String err = "ERRO AO MARCAR ENCOMENDAS DELETADAS";
			logger.error(err, e);
			w.println(err);
			w.println(StringUtils.stackTraceToString(e));
			w.println();
			throw new IllegalStateException(err, e);
		}
	}

	@Override
	public IImportarEncomendas2BonERP getThiz() {
		// aqui tive que fazer desta forma por causa das threads. o Thiz é passado no main.
		return thiz;
	}

	@Override
	public void setThiz(IImportarEncomendas2BonERP thiz) {
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

	public EKTEncomendaFinder getEktEncomendaFinder() {
		return ektEncomendaFinder;
	}

	public void setEktEncomendaFinder(EKTEncomendaFinder ektEncomendaFinder) {
		this.ektEncomendaFinder = ektEncomendaFinder;
	}

	public EncomendaDataMapper getEncomendaDataMapper() {
		return encomendaDataMapper;
	}

	public void setEncomendaDataMapper(EncomendaDataMapper encomendaDataMapper) {
		this.encomendaDataMapper = encomendaDataMapper;
	}

	public EncomendaFinder getEncomendaFinder() {
		return encomendaFinder;
	}

	public void setEncomendaFinder(EncomendaFinder encomendaFinder) {
		this.encomendaFinder = encomendaFinder;
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

	public EKTEncomendaItemFinder getEktEncomendaItemFinder() {
		return ektEncomendaItemFinder;
	}

	public void setEktEncomendaItemFinder(EKTEncomendaItemFinder ektEncomendaItemFinder) {
		this.ektEncomendaItemFinder = ektEncomendaItemFinder;
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

	/**
	 * @param args
	 * @throws FileNotFoundException
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) {

		try {
			long ini = System.currentTimeMillis();

			ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

			// DELETA TUDO ANTES E DEPOIS REIMPORTA TUDO
			IImportarEncomendas2BonERP tDeletar = (IImportarEncomendas2BonERP) context.getBean("importarEncomendas2BonERP");
			tDeletar.deletarEncomendas();

			IImportarEncomendas2BonERP t = (IImportarEncomendas2BonERP) context.getBean("importarEncomendas2BonERP");
			t.setThiz(t);
			
			String sufixo = sdfLog.format(new Date()) + "_ENCOMENDAS.txt";
			
			t.setLogFile(t.getDirLogs() + sufixo);
			w = new PrintWriter(t.getLogFile());
			w.println("<<< IMPORTAÇÃO DE ENCOMENDAS >>>");
			w.println(t.getLogFile());

			ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");

			int j = 500;
			for (int i = 0; i <= 18000; i += j) {
				IImportarEncomendas2BonERP t1 = (IImportarEncomendas2BonERP) context.getBean("importarEncomendas2BonERP");
				t1.setInicio(i);
				t1.setFim(j);
				t1.setThiz(t1);
				taskExecutor.execute(t1);
			}

			for (;;) {
				int count = taskExecutor.getActiveCount();
				System.out.println("Active Threads : " + count);
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (count == 0) {
					taskExecutor.shutdown();
					break;
				}
			}

			final Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			final org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User("carlos", "none", true, true, true, true, authorities);
			Authentication auth = new TestingAuthenticationToken(user, authorities);
			SecurityContextHolder.getContext().setAuthentication(auth);

			// Já que agora estou deletando tudo, não precisa mais disso...
			// t.marcarEncomendasDeletadas();

			long fim = System.currentTimeMillis();
			double tempo = (fim - ini) / 1000;
			logger.info("Tempo de exec.: " + tempo + " segundos");
			logger.info("Log: " + t.getLogFile());
			
			w.flush();
			
			try {
				Mailer.sendMail("casabonsucesso@gmail.com", sufixo, StringUtils.inputStreamToString(new FileInputStream(new File(t.getLogFile()))));
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
			System.out.println("Erro ao importar encomendas");
			System.exit(-1);
		} finally {
			if (w != null) {
				w.close();
			}
		}

		System.exit(0);
	}

}
