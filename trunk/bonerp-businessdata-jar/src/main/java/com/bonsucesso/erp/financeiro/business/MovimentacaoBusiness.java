package com.bonsucesso.erp.financeiro.business;



import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.base.data.DiaUtilFinder;
import com.bonsucesso.erp.financeiro.data.BancoFinder;
import com.bonsucesso.erp.financeiro.data.CadeiaDataMapper;
import com.bonsucesso.erp.financeiro.data.CarteiraFinder;
import com.bonsucesso.erp.financeiro.data.CategoriaFinder;
import com.bonsucesso.erp.financeiro.data.CentroCustoFinder;
import com.bonsucesso.erp.financeiro.data.GrupoDataMapper;
import com.bonsucesso.erp.financeiro.data.GrupoItemFinder;
import com.bonsucesso.erp.financeiro.data.ModoFinder;
import com.bonsucesso.erp.financeiro.data.MovimentacaoDataMapper;
import com.bonsucesso.erp.financeiro.data.MovimentacaoFinder;
import com.bonsucesso.erp.financeiro.data.OperadoraCartaoFinder;
import com.bonsucesso.erp.financeiro.data.ParcelamentoDataMapper;
import com.bonsucesso.erp.financeiro.data.RegraImportacaoLinhaFinder;
import com.bonsucesso.erp.financeiro.model.Cadeia;
import com.bonsucesso.erp.financeiro.model.Carteira;
import com.bonsucesso.erp.financeiro.model.Categoria;
import com.bonsucesso.erp.financeiro.model.Cheque;
import com.bonsucesso.erp.financeiro.model.GrupoItem;
import com.bonsucesso.erp.financeiro.model.Modo;
import com.bonsucesso.erp.financeiro.model.Movimentacao;
import com.bonsucesso.erp.financeiro.model.OperadoraCartao;
import com.bonsucesso.erp.financeiro.model.Parcelamento;
import com.bonsucesso.erp.financeiro.model.Status;
import com.bonsucesso.erp.financeiro.model.TipoLancto;
import com.ocabit.base.view.StoredViewInfoHandler;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;
import com.ocabit.security.model.User;
import com.ocabit.security.spring.SecurityUtils;
import com.ocabit.utils.beans.Cloner;
import com.ocabit.utils.calendar.CalendarUtil;
import com.ocabit.utils.currency.CurrencyUtils;
import com.ocabit.utils.strings.StringUtils;


/**
 * Classe de negócios a respeito da entidade Movimentacao e suas correlações.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("movimentacaoBusiness")
public class MovimentacaoBusiness implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 7476166773853686381L;

	protected static Logger logger = Logger.getLogger(MovimentacaoBusiness.class);

	@Autowired
	private ModoFinder modoFinder;

	@Autowired
	private CarteiraFinder carteiraFinder;

	@Autowired
	private CentroCustoFinder centroCustoFinder;

	@Autowired
	private CategoriaFinder categoriaFinder;

	@Autowired
	private MovimentacaoFinder movimentacaoFinder;

	@Autowired
	private ParcelamentoDataMapper parcelamentoDataMapper;

	@Autowired
	private GrupoItemFinder grupoItemFinder;

	@Autowired
	private GrupoDataMapper grupoDataMapper;

	@Autowired
	private MovimentacaoDataMapper movimentacaoDataMapper;

	@Autowired
	private RegraImportacaoLinhaFinder regraImportacaoLinhaFinder;

	@Autowired
	private StoredViewInfoHandler storedViewInfoHandler;

	@Autowired
	private DiaUtilFinder diaUtilFinder;

	@Autowired
	private BancoFinder bancoFinder;

	@Autowired
	private OperadoraCartaoFinder operadoraCartaoFinder;

	@Autowired
	private CadeiaDataMapper cadeiaDataMapper;

	@Autowired
	private SecurityUtils securityUtils;

	@Autowired
	private MovimentacaoSecurityCheck movimentacaoSecurityCheck;

	@Autowired
	private BeanFactory beanFactory;

	/**
	 * Prepara uma nova Movimentação de acordo com os vários tipos de lançamento
	 * possíveis.
	 *
	 * @param _tipoLancto
	 * @return
	 * @throws ViewException
	 */
	public Movimentacao prepararNova(Movimentacao movimentacao, TipoLancto tipoLancto, Carteira carteiraFiltro)
			throws ViewException {

		// nova Movimentacao
		if (movimentacao == null) {
			movimentacao = new Movimentacao();
			movimentacao.setUnqControle(com.ocabit.utils.strings.StringUtils.generateRandomString(15));
		}
		if (movimentacao.getDtMoviment() == null) {
			movimentacao.setDtMoviment(new Date());
		}

		if (movimentacao.getCentroCusto() == null) {
			movimentacao.setCentroCusto(getCentroCustoFinder().findBy(1)); // TODO: verificar o que fazer com este hardcoded aqui...
		}

		if (carteiraFiltro != null) {
			movimentacao.setCarteira(carteiraFiltro);
		}

		movimentacao.setTipoLancto(tipoLancto);

		switch (tipoLancto) {
			case A_PAGAR_RECEBER:
				movimentacao.setStatus(Status.ABERTA);
				if (carteiraFiltro == null) {
					movimentacao.setCarteira(getCarteiraFinder().findBy("INDEFINIDA"));
				}
				break;

			case CHEQUE_PROPRIO:
				movimentacao.setStatus(Status.A_COMPENSAR);
				movimentacao.setModo(getModoFinder().findBy("CHEQUE PRÓPRIO"));
				movimentacao.setCheque(new Cheque());
				if (movimentacao.getCarteira() != null) {
					movimentacao.getCheque().setBanco(movimentacao.getCarteira().getBanco());
					movimentacao.getCheque().setAgencia(movimentacao.getCarteira().getAgencia());
					movimentacao.getCheque().setConta(movimentacao.getCarteira().getConta());
				}
				break;
			case CHEQUE_TERCEIROS:
				movimentacao.setCheque(new Cheque());
				movimentacao.setStatus(Status.A_COMPENSAR);
				movimentacao.setModo(getModoFinder().findBy("CHEQUE TERCEIROS"));
				break;
			case TRANSF_PROPRIA:
				movimentacao.setCategoria(getCategoriaFinder().findBy(299l));
				movimentacao.setStatus(Status.REALIZADA);
				movimentacao.setDtVencto(null);
				movimentacao.setDtVenctoEfetiva(null);
				movimentacao.setDtPagto(null);
				break;
			case TRANSF_CAIXA:
				// Três movimentações são geradas 299 (origem), 199 (destino), 101 (origem)
				movimentacao.setCategoria(getCategoriaFinder().findBy(101l));
				movimentacao.setStatus(Status.REALIZADA);
				break;
			case REALIZADA:
				movimentacao.setStatus(Status.REALIZADA);
				break;
			case ESTORNO_CORRECAO:
				movimentacao.setCarteira(getCarteiraFinder().findBy(100)); // carteira ESTORNO/CORREÇÕES
				movimentacao.setStatus(Status.REALIZADA);
				movimentacao.setModo(getModoFinder().findBy("TRANSF. ENTRE CONTAS"));
				movimentacao.setCategoria(getCategoriaFinder().findBy(189l));
				break;
			case MOVIMENTACAO_AGRUPADA:
				if (movimentacao.getGrupoItem() == null) {
					throw new ViewException("Movimentação agrupada deve ter o grupoItem informado.");
				}
				movimentacao.setCategoria(movimentacao.getGrupoItem().getPai().getCategoriaPadrao());
				movimentacao.setStatus(Status.REALIZADA);
				movimentacao.setDtVencto(movimentacao.getGrupoItem().getDtVencto());
				movimentacao.setDtVenctoEfetiva(movimentacao.getGrupoItem().getDtVencto());
				movimentacao.setDtPagto(movimentacao.getGrupoItem().getDtVencto());
				movimentacao.setModo(getModoFinder().findBy(50)); // MOVIMENTAÇÃO AGRUPADA

				break;
			case PARCELA:

				if (movimentacao.getParcelamento() == null) {
					throw new ViewException("O parcelamento da parcela deve ser informado.");
				}

				Parcelamento parcelamento = getParcelamentoDataMapper().refresh(movimentacao.getParcelamento());

				Movimentacao ultimaParcela = parcelamento.getParcelas()
						.get(parcelamento.getParcelas().size() - 1);

				ultimaParcela = getMovimentacaoFinder().refresh(ultimaParcela);

				movimentacao = SerializationUtils.clone(ultimaParcela);

				movimentacao.setId(null);

				movimentacao.setDtVenctoEfetiva(null);

				movimentacao.setDtPagto(null);
				movimentacao.setStatus(Status.ABERTA);
				movimentacao.setNumParcela(ultimaParcela.getNumParcela() + 1);

				if (ultimaParcela.getGrupoItem() != null) {
					GrupoItem proximoGrupoItem = getGrupoDataMapper().gerarNoMesAno(movimentacao.getGrupoItem()
							.getPai(), CalendarUtil.incMes(ultimaParcela.getDtVencto(), 1));
					movimentacao.setGrupoItem(proximoGrupoItem);
					movimentacao.setDtVencto(null);
					// troca o tipoLancto
					movimentacao.setTipoLancto(TipoLancto.MOVIMENTACAO_AGRUPADA);
				} else {
					movimentacao.setDtVencto(CalendarUtil.incMes(ultimaParcela.getDtVencto(), 1));
					// troca o tipoLancto (pois o tipoLancto PARCELA só serve para as regras de tela)
					movimentacao.setTipoLancto(TipoLancto.A_PAGAR_RECEBER);
				}

				parcelamento.addParcela(movimentacao);

				break;
			default:
				break;
		}

		return movimentacao;
	}

	/**
	 * Salva uma cadeia de movimentações.
	 *
	 * @param movimentacao
	 * @return
	 */
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, rollbackFor = {
			RuntimeException.class, ViewException.class })
	public Cadeia saveCadeia(Movimentacao... movimentacoes) throws ViewException {

		if ((movimentacoes == null) || (movimentacoes.length < 2)) {
			throw new ViewException("Duas ou mais movimentações devem ser passadas.");
		}

		Cadeia cadeia = new Cadeia();
		cadeia.setFechada(true);
		cadeia.setVinculante(true);

		int i = 1;

		for (Movimentacao m : movimentacoes) {
			m.setCadeiaOrdem(i++);
			// sempre copia os campos da primeira movimentação

			m.setTipoLancto(movimentacoes[0].getTipoLancto());
			m.setDtMoviment(movimentacoes[0].getDtMoviment());
			m.setDtPagto(movimentacoes[0].getDtPagto());
			m.setCarteira(movimentacoes[0].getCarteira());
			m.setStatus(movimentacoes[0].getStatus());
			m.setModo(movimentacoes[0].getModo());
			m.setValorTotal(movimentacoes[0].getValorTotal());
			m.setCentroCusto(movimentacoes[0].getCentroCusto());
			m.setDescricao(movimentacoes[0].getDescricao());
			cadeia.addMovimentacao(m);
		}

		return getCadeiaDataMapper().saveCadeiaEMovimentacoes(cadeia);
	}

	/**
	 * Gera uma descrição automática para uma movimentação.
	 *
	 * @param movimentacao
	 * @return
	 */
	public void gerarDescricao(Movimentacao movimentacao) {
		if (movimentacao.getCategoria() != null) {
			movimentacao.setDescricao(movimentacao.getCategoria().getDescricaoPadraoMoviment());
		}
	}

	/**
	 * Retorna um mapa com 3 entradas: ENTRADAS, SAIDAS, TOTAL.
	 *
	 * @param movimentacoes
	 * @return
	 */
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public Map<String, BigDecimal> findTotal(List<Movimentacao> movimentacoes) {
		return getThiz().findTotal(movimentacoes, null);
	}

	/**
	 * Retorna um mapa com 3 entradas: ENTRADAS, SAIDAS, TOTAL.
	 *
	 * @param movimentacoes
	 * @return
	 */
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public Map<String, BigDecimal> findTotal(List<Movimentacao> movimentacoes, Status status) {

		Map<String, BigDecimal> result = new HashMap<String, BigDecimal>();

		BigDecimal bdTotal = CurrencyUtils.getBigDecimalCurrency(0.0);
		BigDecimal bdEntradas = CurrencyUtils.getBigDecimalCurrency(0.0);
		BigDecimal bdSaidas = CurrencyUtils.getBigDecimalCurrency(0.0);

		result.put("ENTRADAS", bdEntradas);
		result.put("SAIDAS", bdSaidas);
		result.put("TOTAL", bdTotal);

		if ((movimentacoes == null) || (movimentacoes.size() < 1)) {
			return result;
		}

		for (Movimentacao m : movimentacoes) {
			if ((status != null) && !status.equals(m.getStatus())) {
				continue;
			}
			// em casos como na importação de movimentações, a categoria pode não ter sido importada.
			if ((m.getCategoria() == null) || (m.getCategoria().getCodigo() == null)) {
				return result;
			}
			if (m.getCategoria().getCodigo().toString().startsWith("1")) {
				bdEntradas = bdEntradas.add(m.getValorTotal().abs());
			} else {
				bdSaidas = bdSaidas.add(m.getValorTotal().abs());
			}
		}

		bdTotal = bdEntradas.subtract(bdSaidas);

		result.put("ENTRADAS", bdEntradas);
		result.put("SAIDAS", bdSaidas);
		result.put("TOTAL", bdTotal);

		return result;

	}

	/**
	 * Altera em lote uma lista de movimentações.
	 *
	 * @param movimentacoes
	 * @param movimentacao
	 */
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class, ViewException.class })
	public List<Movimentacao> alterarEmLote(List<Movimentacao> movimentacoes, Movimentacao movimentacao) {

		try {

			movimentacoes = getThiz().refreshAll(movimentacoes);

			String[] campos = { "status", "tipoLancto", "carteira", "carteiraDestino", "centroCusto", "modo",
					"categoria",
					"descricao", "pessoa", "dtMoviment", "dtVencto", "dtVenctoEfetiva", "dtPagto", "documentoBanco",
					"grupoItem" };

			movimentacoes = getThiz().refreshAll(movimentacoes);
			for (Movimentacao m : movimentacoes) {
				Cloner.copy(m, movimentacao, campos);
			}
			return movimentacoes;
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
			return null;
		}

	}

	@Transactional
	public List<Movimentacao> refreshAll(List<Movimentacao> movimentacoes) throws ViewException {
		try {
			List<Movimentacao> rs = new ArrayList<Movimentacao>();
			for (Movimentacao m : movimentacoes) {
				if (m.getId() != null) {
					m = getMovimentacaoDataMapper().refresh(m);
					if (m.getParcelamento() != null) {
						m.setParcelamento(getParcelamentoDataMapper().refresh(m.getParcelamento()));
						m.getParcelamento().getId().toString(); // touch;
					}
				}
				rs.add(m);
			}
			return rs;
		} catch (ViewException e) {
			logger.error(e);
			throw new ViewException("Erro ao atualizar movimentações.", e);
		}
	}

	/**
	 * Gera parcelamentos de movimentações.
	 *
	 * @param primeiraParcela
	 * @param numParcelas
	 * @param valorTotalParcelas
	 * @return
	 * @throws ViewException
	 */
	public void gerarParcelas(Movimentacao primeiraParcela, Integer qtdeParcelas, boolean diaFixo)
			throws ViewException {

		Parcelamento parcelamento = primeiraParcela.getParcelamento();
		parcelamento.setParcelas(new ArrayList<Movimentacao>());

		BigDecimal valorTotal = primeiraParcela.getParcelamento().getValorTotal();

		BigDecimal valorParcela;
		if ((valorTotal != null) && (valorTotal.doubleValue() > 0)) {
			valorParcela = CurrencyUtils.getBigDecimalCurrency(CurrencyUtils.divide(valorTotal, CurrencyUtils
					.getBigDecimalCurrency(Integer.valueOf(qtdeParcelas).doubleValue())));
		} else {
			valorParcela = primeiraParcela.getValorTotal();
		}

		primeiraParcela.setNumParcela(1);
		primeiraParcela.setValor(valorParcela);
		primeiraParcela.setValorTotal(valorParcela);

		parcelamento.getParcelas().add(primeiraParcela);

		Date dt = primeiraParcela.getDtVencto();

		Integer numCheque = null;
		if ((primeiraParcela.getCheque() != null) && (primeiraParcela.getCheque().getNumCheque() != null)) {
			try {
				numCheque = Integer.parseInt(primeiraParcela.getCheque().getNumCheque());
			} catch (NumberFormatException e) {
				logger.error(e);
			}
		}

		// Em casos de grupos de itens...
		GrupoItem giAtual = primeiraParcela.getGrupoItem();
		if (giAtual != null) {
			giAtual = getGrupoItemFinder().refresh(giAtual);
			if (giAtual.getProximo() != null) {
				Long proximoId = giAtual.getProximo().getId();
				getGrupoItemFinder().getEntityManager().detach(giAtual);
				giAtual = getGrupoItemFinder().findById(proximoId); // já "incrementa" pois o for abaixo já começa na segunda parcela...
			} else {
				giAtual = getGrupoDataMapper().gerarProximo(giAtual.getPai());
			}
		}

		BigDecimal bdValorTotalSoma = valorParcela;

		for (int i = 1; i < qtdeParcelas; i++) {

			if (diaFixo) {
				dt = CalendarUtil.incMes(dt, 1);
			} else {
				dt = CalendarUtil.incDias(dt, 30);
			}
			Movimentacao p = SerializationUtils.clone(primeiraParcela);
			p.setUnqControle(StringUtils.generateRandomString(15));

			if (giAtual != null) {
				p.setGrupoItem(giAtual);

				if (giAtual.getProximo() != null) {
					Long proximoId = giAtual.getProximo().getId();
					getGrupoItemFinder().getEntityManager().detach(giAtual);
					giAtual = getGrupoItemFinder().findById(proximoId);
				} else {
					giAtual = getGrupoDataMapper().gerarProximo(giAtual.getPai());
				}
			}

			p.setParcelamento(parcelamento);
			p.setNumParcela(i + 1);
			p.setId(null);
			p.setDtVencto(dt);
			p.setDtVenctoEfetiva(getDiaUtilFinder().findProximoDiaUtilFinanceiro(dt));
			p.setValor(valorParcela);
			p.setValorTotal(valorParcela);

			if (numCheque != null) {
				numCheque++;
				p.getCheque().setNumCheque(numCheque.toString());
			}

			bdValorTotalSoma = CurrencyUtils.soma(bdValorTotalSoma, valorParcela);

			parcelamento.getParcelas().add(p);
		}

		parcelamento.setValorTotal(bdValorTotalSoma);

		logger.info("Parcelas geradas com sucesso.");
	}

	/**
	 * Reordena as parcelas conforme as datas de vencimento.
	 *
	 * @param parcelas
	 */
	public void reordenarParcelas(List<Movimentacao> parcelas) {

		// Reordena para reclassificar o núm de cada parcela.
		Collections.sort(parcelas, new Comparator<Movimentacao>() {

			@Override
			public int compare(Movimentacao m1, Movimentacao m2) {
				return new CompareToBuilder()
						.append(m1.getDtVencto(), m2.getDtVencto())
						.toComparison();
			}
		});

		int i = 1;

		for (Movimentacao p : parcelas) {
			p.setNumParcela(i++);
		}
	}

	/**
	 * Corrige os valores de OperadoraCartao.
	 *
	 * @param dtPagto
	 * @param carteira
	 * @throws ViewException
	 */
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public List<String> corrigirOperadoraCartaoMovimentacoesCartoesDebito(Date dtPagto, Carteira carteira)
			throws ViewException {
		Modo modo = getModoFinder().findBy("RECEB. CARTÃO DÉBITO");
		Categoria c101 = getCategoriaFinder().findBy(101l);

		List<String> results = new ArrayList<String>();

		Categoria c299 = getCategoriaFinder().findBy(299l);
		Categoria c199 = getCategoriaFinder().findBy(199l);

		// Pesquisa todas as 101 desta carteira, nesta dtPagto, com o modo "DÉBITO"
		List<Movimentacao> m101s = getMovimentacaoFinder().findBy(carteira, dtPagto, modo, c101);

		for (Movimentacao m101 : m101s) {
			Cadeia cadeia = m101.getCadeia();

			if (cadeia == null) {
				throw new ViewException("Movimentação sem cadeia.");
			} else {
				try {

					Movimentacao m299 = getMovimentacaoFinder().findBy(cadeia, c299);
					Movimentacao m199 = getMovimentacaoFinder().findBy(cadeia, c199);

					OperadoraCartao operadoraCartao;

					if (m199.getOperadoraCartao() == null) {
						operadoraCartao = getOperadoraCartaoFinder().findBy(m199.getCarteira());
						m199.setOperadoraCartao(operadoraCartao);
						m199 = getMovimentacaoDataMapper().save(m199);
						results.add("Operadora corrigida para '" + m199.getDescricao() + "' - R$ " + m199.getValor()
								+ " (1.99): " + operadoraCartao.getDescricao());
					} else {
						operadoraCartao = m199.getOperadoraCartao();
						getMovimentacaoDataMapper().detach(m199);
					}

					if (m299.getOperadoraCartao() == null) {
						// provavelmente TAMBÉM isso não deveria ser necessário, visto que na importação isto já deve ter sido acertado.
						m299.setOperadoraCartao(operadoraCartao);
						m299 = getMovimentacaoDataMapper().save(m299);
						results.add("Operadora corrigida para '" + m299.getDescricao() + "' - R$ " + m299.getValor()
								+ " (2.99): " + operadoraCartao.getDescricao());
					} else {
						getMovimentacaoDataMapper().detach(m299);
					}

					if (m101.getOperadoraCartao() == null) {
						// provavelmente isso não deveria ser necessário, visto que na importação isto já deve ter sido acertado.
						m101.setOperadoraCartao(operadoraCartao);
						m101 = getMovimentacaoDataMapper().save(m101);
						results.add("Operadora corrigida para '" + m101.getDescricao() + "' - R$ " + m101.getValor()
								+ " (1.01): " + operadoraCartao.getDescricao());
					} else {
						getMovimentacaoDataMapper().detach(m101);
					}

				} catch (Exception e) {
					logger.error(e);
					results.add("ERRO: Não foi possível consolidar " + m101.getDescricao() + " - R$ " + m101.getValor()
							+ " (" + e.getMessage() + ")");
				}

			}
		}
		getMovimentacaoDataMapper().getEntityManager().flush();

		return results;

	}

	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED)
	public Cadeia cadeiaSetVinculante(Cadeia cadeia, boolean vinculante) throws ViewException {
		// cadeia.setVersion(cadeia.getVersion() + 1);
		cadeia.setVinculante(vinculante);
		return getCadeiaDataMapper().save(cadeia);
	}

	/**
	 * Consolida as movimentações 101 lançadas manualmente com as 199/299 importadas pelo extrato.
	 *
	 * @param dtPagto
	 * @param carteira
	 * @throws ViewException
	 */
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public List<String> consolidarMovimentacoesCartoesDebito(Date dtPagto, Carteira carteira) throws ViewException {
		Modo modo = getModoFinder().findBy("RECEB. CARTÃO DÉBITO");
		Categoria c101 = getCategoriaFinder().findBy(101l);

		List<String> results = new ArrayList<String>();

		// Pesquisar todas as 101 de cartão de débito
		List<Movimentacao> m101s = getMovimentacaoFinder().findBy(carteira, dtPagto, modo, c101);

		for (Movimentacao m101 : m101s) {
			try {
				if (m101.getCadeia() == null) {
					results.add(getThiz().consolidarMovimentacaoDebito(m101, dtPagto, carteira));
					// Tenho que DETACHAR este objeto pois ele será alterado na outra transação e na hora do flush desta dará problema.
				}
				// getMovimentacaoDataMapper().getEntityManager().detach(m101);
			} catch (Exception e) {
				results.add("ERRO: Não foi possível consolidar " + m101.getDescricao() + " - R$ " + m101.getValor()
						+ " (" + e.getMessage() + ")");
			}
		}

		return results;

	}

	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED)
	public String consolidarMovimentacaoDebito(Movimentacao m101, Date dtMoviment, Carteira carteira)
			throws ViewException {

		String result;

		Categoria c299 = getCategoriaFinder().findBy(299l);

		// pesquisa movimentação 299 nesta
		// retorna uma lista pois pode encontrar mais de 1
		List<Movimentacao> m299s = getMovimentacaoFinder()
				.findBy(dtMoviment, m101.getValor(), carteira, m101.getBandeiraCartao(), c299);

		// Encontra a m299 que faça parte de uma cadeia com apenas 2 movimentações: 199 e 299 (para evitar de incluir 2 vezes uma 101 na mesma cadeia).
		Movimentacao m299 = null;
		for (Movimentacao _m299 : m299s) {
			getMovimentacaoFinder().getEntityManager().refresh(_m299.getCadeia());
			if (_m299.getCadeia().getMovimentacoes().size() == 2) {
				m299 = _m299;
				break;
			}
		}

		if (m299 == null) {
			result = "ERRO: Nenhuma movimentação 2.99 encontrada para '" + m101.getDescricao() + "' - R$ "
					+ m101.getValor();
			return result;
		}

		// Incluir na cadeia
		m101.setCadeia(m299.getCadeia());
		m101.setCadeiaOrdem(3);
		m101 = getMovimentacaoDataMapper().save(m101);

		// flush para o BD já...
		getMovimentacaoDataMapper().getEntityManager().flush();
		// ...para poder atualizar a m299 no entityManager, e dessa forma saber que ela já está em uma cadeia com 3 movimentações, pulando o if no for acima.
		m299 = getMovimentacaoDataMapper().refresh(m299);

		result = "SUCESSO: Movimentação consolidada >> '" + m101.getDescricao() + "' - R$ " + m101.getValor();

		return result;
	}

	/**
	 * Soma todos os campos 'valorTotal' de uma List de Movimentacao, considerando positivos e negativos conforme a categoria.
	 *
	 * @param dtList
	 * @return
	 */
	public BigDecimal totalizarDtList(List<Movimentacao> dtList, Long categoriaCodigoSuper) {
		BigDecimal total = CurrencyUtils.getBigDecimalCurrency(0.0);
		for (Movimentacao m : dtList) {
			if (categoriaCodigoSuper == null) {
				total = CurrencyUtils.soma(total, m.getCategoria().getCodigoSuper().equals(1l) ? m.getValorTotal() : m
						.getValorTotal().abs().negate());
			} else {
				if (m.getCategoria().getCodigoSuper().equals(categoriaCodigoSuper)) {
					total = CurrencyUtils.soma(total, m.getValorTotal());
				}
			}
		}
		return total;
	}

	/**
	 * Calcula a taxa bruta de desconto dos cartões de crédito/débito.
	 *
	 * @param carteira
	 * @param debito
	 * @param totalVendas
	 * @return
	 * @throws ViewException
	 */
	public BigDecimal calcularTaxaCartao(Carteira carteira, boolean debito, BigDecimal totalVendas, Date dtIni,
			Date dtFim)
					throws ViewException {
		Categoria cCustoOperacionalCartao;
		if (debito) {
			cCustoOperacionalCartao = getThiz().getCategoriaFinder().findBy(202005002l);
		} else {
			cCustoOperacionalCartao = getThiz().getCategoriaFinder().findBy(202005001l);
		}
		BigDecimal tCustoOperacionalCartao = getThiz().getMovimentacaoFinder()
				.findTotal(carteira, cCustoOperacionalCartao, dtIni, dtFim);

		BigDecimal bdTaxa = CurrencyUtils.getBigDecimalCurrency("0.0");

		if ((tCustoOperacionalCartao != null) && (totalVendas != null) && (tCustoOperacionalCartao.doubleValue() > 0)
				&& (totalVendas.doubleValue() > 0)) {
			Double taxaCartao = CurrencyUtils.divide(tCustoOperacionalCartao, totalVendas);
			bdTaxa = CurrencyUtils.getBigDecimalScaled(taxaCartao, 4)
					.multiply(CurrencyUtils.getBigDecimalScaled(100.0, 2));
		}
		return bdTaxa;
	}

	/**
	 * Desloca os vencimentos de um parcelamento a partir de uma parcela.
	 *
	 * @param parcela
	 * @param mes
	 * @throws ViewException
	 */
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, rollbackFor = {
			RuntimeException.class, ViewException.class })
	public Movimentacao deslocarVenctos(Movimentacao parcela, int direcao) throws ViewException {
		try {

			direcao = direcao > 0 ? 1 : -1;

			parcela = getMovimentacaoFinder().refresh(parcela);

			Parcelamento parcelamento = parcela.getParcelamento();

			int parcelaNum = parcelamento.getParcelas().indexOf(parcela);

			Movimentacao ret = null;

			for (int i = parcelaNum; i < parcelamento.getParcelas().size(); i++) {

				// Movimentacao p = getMovimentacaoFinder().refresh(parcelamento.getParcelas().get(i));
				Movimentacao p = parcelamento.getParcelas().get(i);

				Date proxDtVencto = CalendarUtil.incMes(p.getDtVencto(), direcao);

				// Se é parcelamento dentro de Grupo, então só incrementa pro próximo que o beforeSave() acerta tudo.
				if (p.getGrupoItem() != null) {
					GrupoItem proximo = getGrupoDataMapper().gerarNoMesAno(p.getGrupoItem().getPai(), proxDtVencto);
					p.setGrupoItem(proximo);
				} else {
					p.setDtVencto(proxDtVencto);
				}

				p = getMovimentacaoDataMapper().save(p);

				if (ret == null) {
					ret = p;
				}
				logger.info("OK");
			}

			logger.info("Finalizando");

			return ret;

		} catch (Exception e) {
			throw new ViewException("Erro ao deslocar os vencimentos.", e);
		}

	}

	/**
	 * Ajusta o campo transient 'descricaoMontada' de toda a lista.
	 * 
	 * @param ms
	 * @throws
	 */
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED)
	public void handleDescricaoMontada(List<Movimentacao> ms) throws ViewException {
		for (int i = 0; i < ms.size(); i++) {
			Movimentacao m = ms.get(i);
			m = getThiz().handleDescricaoMontada(m);
			ms.set(i, m);
		}
	}

	/**
	 * 
	 * @param m
	 * @throws
	 */
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED)
	public Movimentacao handleDescricaoMontada(Movimentacao m) throws ViewException {
		User userLogado = getSecurityUtils().getUser();
		return getThiz().handleDescricaoMontada(m, userLogado);
	}

	/**
	 * Ajusta o campo transient 'descricaoMontada' .
	 * 
	 * @param descricao
	 * @return
	 * @throws ViewException
	 */
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED)
	public Movimentacao handleDescricaoMontada(Movimentacao m, User userLogado) throws ViewException {

		String sufixo = "";
		if (m.getQtdeParcelas() != null) {
			//		if ((m.getNumParcela() != null) && (m.getParcelamento() != null)) {
			//
			//			Parcelamento parcelamento = m.getParcelamento();
			//
			//			if (m.getParcelamento().getId() != null) {
			//				// FIXME:
			//				// por que estava dando refresh aqui???? se der refresh, vai perder todas as possíveis alterações na movimentação
			//				//				parcelamento = getParcelamentoDataMapper().refresh(parcelamento);
			//			}

			int zeroFillQtde = m.getQtdeParcelas().toString().length() <= 2 ? 2
					: m.getQtdeParcelas().toString().length();

			sufixo += " (" + StringUtils.zerofill(m.getNumParcela().toString(), zeroFillQtde) + "/"
					+ StringUtils.zerofill(m.getQtdeParcelas().toString(), zeroFillQtde) + ")";
		}
		if (m.getDocumentoFiscal() != null && !"".equals(m.getDocumentoFiscal().trim())) {
			sufixo += " - (NF: " + m.getDocumentoFiscal() + ")";
		}
		if ((m.getCheque() != null) && (m.getCheque().getBanco() != null) && (m.getCheque().getNumCheque() != null)) {
			sufixo += " (CHQ: " + m.getCheque().getBanco().getNome() + " - nº "
					+ m.getCheque().getNumCheque() + ")";
		}
		if (m.getBandeiraCartao() != null) {
			sufixo += "<br />"
					+ "(Bandeira: " + m.getBandeiraCartao().getDescricao() + ")";
		}
		if (m.getOperadoraCartao() != null) {
			sufixo += "<br />"
					+ "(Operadora: " + m.getOperadoraCartao().getDescricao() + ")";
		}
		if (m.getGrupoItem() != null) {
			sufixo += "<br />" + m.getGrupoItem().getDescricao();
		}

		m.setDescricaoMontada(m.getDescricao() + sufixo);

		String descricaoMontadaRebuilded = getMovimentacaoSecurityCheck().rebuildDescricaoMontadaTratProp(m);
		m.setDescricaoMontada(descricaoMontadaRebuilded);

		getMovimentacaoDataMapper().detach(m);

		return m;
	}

	/**
	 * Encontra todos os erros de movimentações 1.99 e 2.99 e retorna com as descrições ajustadas.
	 * 
	 * @return
	 * @throws ViewException
	 */
	public List<Movimentacao> listarErrosTransferenciaEntreCarteiras(Date dtIni, Date dtFim) throws ViewException {
		List<Movimentacao> errosTransf = getMovimentacaoFinder().listarErrosTransferenciaEntreCarteiras(dtIni, dtFim);
		getThiz().handleDescricaoMontada(errosTransf);
		return errosTransf;
	}

	public MovimentacaoBusiness getThiz() {
		return (MovimentacaoBusiness) getBeanFactory().getBean("movimentacaoBusiness");
	}

	public DiaUtilFinder getDiaUtilFinder() {
		return diaUtilFinder;
	}

	public void setDiaUtilFinder(DiaUtilFinder diaUtilFinder) {
		this.diaUtilFinder = diaUtilFinder;
	}

	public ModoFinder getModoFinder() {
		return modoFinder;
	}

	public void setModoFinder(ModoFinder modoFinder) {
		this.modoFinder = modoFinder;
	}

	public CarteiraFinder getCarteiraFinder() {
		return carteiraFinder;
	}

	public void setCarteiraFinder(CarteiraFinder carteiraFinder) {
		this.carteiraFinder = carteiraFinder;
	}

	public CentroCustoFinder getCentroCustoFinder() {
		return centroCustoFinder;
	}

	public void setCentroCustoFinder(CentroCustoFinder centroCustoFinder) {
		this.centroCustoFinder = centroCustoFinder;
	}

	public CategoriaFinder getCategoriaFinder() {
		return categoriaFinder;
	}

	public void setCategoriaFinder(CategoriaFinder categoriaFinder) {
		this.categoriaFinder = categoriaFinder;
	}

	public StoredViewInfoHandler getStoredViewInfoHandler() {
		return storedViewInfoHandler;
	}

	public void setStoredViewInfoHandler(StoredViewInfoHandler storedViewInfoHandler) {
		this.storedViewInfoHandler = storedViewInfoHandler;
	}

	public MovimentacaoFinder getMovimentacaoFinder() {
		return movimentacaoFinder;
	}

	public void setMovimentacaoFinder(MovimentacaoFinder movimentacaoFinder) {
		this.movimentacaoFinder = movimentacaoFinder;
	}

	public ParcelamentoDataMapper getParcelamentoDataMapper() {
		return parcelamentoDataMapper;
	}

	public void setParcelamentoDataMapper(ParcelamentoDataMapper parcelamentoDataMapper) {
		this.parcelamentoDataMapper = parcelamentoDataMapper;
	}

	public GrupoItemFinder getGrupoItemFinder() {
		return grupoItemFinder;
	}

	public void setGrupoItemFinder(GrupoItemFinder grupoItemFinder) {
		this.grupoItemFinder = grupoItemFinder;
	}

	public GrupoDataMapper getGrupoDataMapper() {
		return grupoDataMapper;
	}

	public void setGrupoDataMapper(GrupoDataMapper grupoDataMapper) {
		this.grupoDataMapper = grupoDataMapper;
	}

	public MovimentacaoDataMapper getMovimentacaoDataMapper() {
		return movimentacaoDataMapper;
	}

	public void setMovimentacaoDataMapper(MovimentacaoDataMapper movimentacaoDataMapper) {
		this.movimentacaoDataMapper = movimentacaoDataMapper;
	}

	public RegraImportacaoLinhaFinder getRegraImportacaoLinhaFinder() {
		return regraImportacaoLinhaFinder;
	}

	public void setRegraImportacaoLinhaFinder(RegraImportacaoLinhaFinder regraImportacaoLinhaFinder) {
		this.regraImportacaoLinhaFinder = regraImportacaoLinhaFinder;
	}

	public BancoFinder getBancoFinder() {
		return bancoFinder;
	}

	public void setBancoFinder(BancoFinder bancoFinder) {
		this.bancoFinder = bancoFinder;
	}

	public OperadoraCartaoFinder getOperadoraCartaoFinder() {
		return operadoraCartaoFinder;
	}

	public void setOperadoraCartaoFinder(OperadoraCartaoFinder operadoraCartaoFinder) {
		this.operadoraCartaoFinder = operadoraCartaoFinder;
	}

	public CadeiaDataMapper getCadeiaDataMapper() {
		return cadeiaDataMapper;
	}

	public void setCadeiaDataMapper(CadeiaDataMapper cadeiaDataMapper) {
		this.cadeiaDataMapper = cadeiaDataMapper;
	}

	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	public MovimentacaoSecurityCheck getMovimentacaoSecurityCheck() {
		return movimentacaoSecurityCheck;
	}

	public void setMovimentacaoSecurityCheck(MovimentacaoSecurityCheck movimentacaoSecurityCheck) {
		this.movimentacaoSecurityCheck = movimentacaoSecurityCheck;
	}

	public SecurityUtils getSecurityUtils() {
		return securityUtils;
	}

	public void setSecurityUtils(SecurityUtils securityUtils) {
		this.securityUtils = securityUtils;
	}

}
