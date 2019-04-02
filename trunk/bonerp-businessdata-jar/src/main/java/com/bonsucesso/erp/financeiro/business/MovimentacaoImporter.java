package com.bonsucesso.erp.financeiro.business;



import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.base.data.DiaUtilFinder;
import com.bonsucesso.erp.financeiro.data.BancoFinder;
import com.bonsucesso.erp.financeiro.data.BandeiraCartaoFinder;
import com.bonsucesso.erp.financeiro.data.CarteiraFinder;
import com.bonsucesso.erp.financeiro.data.CategoriaFinder;
import com.bonsucesso.erp.financeiro.data.CentroCustoFinder;
import com.bonsucesso.erp.financeiro.data.GrupoDataMapper;
import com.bonsucesso.erp.financeiro.data.GrupoFinder;
import com.bonsucesso.erp.financeiro.data.GrupoItemFinder;
import com.bonsucesso.erp.financeiro.data.ModoFinder;
import com.bonsucesso.erp.financeiro.data.MovimentacaoFinder;
import com.bonsucesso.erp.financeiro.data.OperadoraCartaoFinder;
import com.bonsucesso.erp.financeiro.data.RegraImportacaoLinhaFinder;
import com.bonsucesso.erp.financeiro.model.BandeiraCartao;
import com.bonsucesso.erp.financeiro.model.Carteira;
import com.bonsucesso.erp.financeiro.model.Categoria;
import com.bonsucesso.erp.financeiro.model.CentroCusto;
import com.bonsucesso.erp.financeiro.model.Cheque;
import com.bonsucesso.erp.financeiro.model.Grupo;
import com.bonsucesso.erp.financeiro.model.GrupoItem;
import com.bonsucesso.erp.financeiro.model.Modo;
import com.bonsucesso.erp.financeiro.model.Movimentacao;
import com.bonsucesso.erp.financeiro.model.OperadoraCartao;
import com.bonsucesso.erp.financeiro.model.Parcelamento;
import com.bonsucesso.erp.financeiro.model.PlanoPagtoCartao;
import com.bonsucesso.erp.financeiro.model.RegraImportacaoLinha;
import com.bonsucesso.erp.financeiro.model.Status;
import com.bonsucesso.erp.financeiro.model.TipoLancto;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.utils.calendar.CalendarUtil;
import com.ocabit.utils.currency.CurrencyUtils;
import com.ocabit.utils.strings.RegexUtils;
import com.ocabit.utils.strings.StringUtils;


/**
 * Classe de negócios a respeito da entidade Movimentacao e suas correlações.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("movimentacaoImporter")
public class MovimentacaoImporter implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 7476166773853686381L;

	protected static Logger logger = Logger.getLogger(MovimentacaoImporter.class);

	final static String TXT_LINHA_NAO_IMPORTADA = "<<< LINHAS NÃO IMPORTADAS >>>";

	final static String TXT_LINHA_IMPORTADA = "<<< LINHAS IMPORTADAS >>>";

	@Autowired
	private ModoFinder modoFinder;

	@Autowired
	private CentroCustoFinder centroCustoFinder;

	@Autowired
	private CategoriaFinder categoriaFinder;

	@Autowired
	private CarteiraFinder carteiraFinder;

	@Autowired
	private MovimentacaoFinder movimentacaoFinder;

	@Autowired
	private GrupoFinder grupoFinder;

	@Autowired
	private GrupoItemFinder grupoItemFinder;

	@Autowired
	private GrupoDataMapper grupoDataMapper;

	@Autowired
	private RegraImportacaoLinhaFinder regraImportacaoLinhaFinder;

	@Autowired
	private BancoFinder bancoFinder;

	@Autowired
	private BandeiraCartaoFinder bandeiraCartaoFinder;

	@Autowired
	private OperadoraCartaoFinder operadoraCartaoFinder;

	@Autowired
	private DiaUtilFinder diaUtilFinder;

	@Autowired
	private MovimentacaoBusiness movimentacaoBusiness;

	@Autowired
	private BeanFactory beanFactory;

	private List<Movimentacao> movimentacaoImportadas;

	// Para não importar duas vezes para a mesma 101 no caso das de CARTÃO DE DÉBITO
	private List<Movimentacao> movs101JaImportadas;

	private MovimentacaoImporter thiz;

	private List<String> linhas;

	private List<Integer> linhasComplementares;

	/**
	 * Método principal, decide como será executada a importação das linhas.
	 * 
	 * @param tipoExtrato
	 * @param linhas
	 * @param carteiraExtrato
	 * @param carteiraDestino
	 * @param grupoItem
	 * @param gerarSemRegras
	 * @return
	 * @throws ViewException
	 */
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public List<Movimentacao> importar(TipoExtrato tipoExtrato, List<String> linhas, Carteira carteiraExtrato,
			Carteira carteiraDestino,
			GrupoItem grupoItem,
			boolean gerarSemRegras)
			throws ViewException {

		getThiz().setLinhas(linhas);

		setLinhasComplementares(null);

		switch (tipoExtrato) {
			case EXTRATO_COMPRA_BNDES_BB:
				return getThiz().importExtratoCompraBndesBB();
			case EXTRATO_GRUPO_MOVIMENTACOES:
				return getThiz().importGrupoMovimentacao(grupoItem);
			default:
				return getThiz().importarPadrao(tipoExtrato, carteiraExtrato, carteiraDestino, gerarSemRegras);
		}
	}

	/**
	 *
	 * @param linhas
	 * @param carteiraExtrato
	 * @param gerarSemRegras
	 * @return
	 * @throws ViewException
	 */
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	private List<Movimentacao> importarPadrao(TipoExtrato tipoExtrato, Carteira carteiraExtrato,
			Carteira carteiraDestino,
			boolean gerarSemRegras)
			throws ViewException {

		List<String> linhas = getThiz().getLinhas();

		setMovimentacaoImportadas(new ArrayList<Movimentacao>());
		setMovs101JaImportadas(new ArrayList<Movimentacao>());

		List<String> linhasNaoImportadas = new ArrayList<String>();
		List<String> linhasImportadas = new ArrayList<String>();

		for (int numLinha = 0; numLinha < linhas.size(); numLinha++) {

			String linha = linhas.get(numLinha);

			if (linha == null || linha.equals(MovimentacaoImporter.TXT_LINHA_NAO_IMPORTADA)
					|| linha.equals(MovimentacaoImporter.TXT_LINHA_IMPORTADA)
					|| "".equals(linha.trim())) {
				continue;
			}

			// Verifica se é uma linha (de descrição) complementar já importada 
			if (getLinhasComplementares().contains(Integer.valueOf(numLinha))) {
				linhasImportadas.add(linha);
				continue;
			}

			logger.debug(">>> Importando a linha: [" + linha + "]");
			Movimentacao importada = null;
			try {
				importada = getThiz()
						.importLinha(tipoExtrato, numLinha, carteiraExtrato, carteiraDestino, gerarSemRegras);

				if (getMovimentacaoBusiness().getMovimentacaoDataMapper().getEntityManager().contains(importada)) {
					getMovimentacaoBusiness().getMovimentacaoDataMapper().getEntityManager().detach(importada);
				}

				if (importada != null) {
					getMovimentacaoImportadas().add(importada);
					linhasImportadas.add(linha);
				} else {
					linhasNaoImportadas.add(linha);
				}
			} catch (Exception e) {
				logger.error(e);
				linhasNaoImportadas.add(linha);
			}
		}

		List<String> _linhasNaoImportadas = new ArrayList<String>();

		for (String l : linhasNaoImportadas) {
			if (!l.contains(TXT_LINHA_IMPORTADA) && !l.contains(TXT_LINHA_NAO_IMPORTADA) && !"".equals(l.trim())) {
				_linhasNaoImportadas.add(l);
			}
		}

		linhas.clear();
		linhas.add(TXT_LINHA_NAO_IMPORTADA);
		linhas.add("");
		linhas.addAll(_linhasNaoImportadas);
		linhas.add("");
		linhas.add("");
		linhas.add(TXT_LINHA_IMPORTADA);
		linhas.add("");
		linhas.addAll(linhasImportadas);

		return getMovimentacaoImportadas();
	}

	/**
	 * Importa uma linha.
	 *
	 * @param linha
	 * @return
	 * @throws ViewException
	 */
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	private Movimentacao importLinha(TipoExtrato tipoExtrato, int numLinha, Carteira carteiraExtrato,
			Carteira carteiraDestino,
			boolean gerarSemRegras) throws ViewException {
		try {

			Map<String, Object> camposLinha = new HashMap<String, Object>();

			switch (tipoExtrato) {
				case EXTRATO_SIMPLES:
					camposLinha = importLinhaExtratoSimples(numLinha);
					break;
				case EXTRATO_RDCARD_CREDITO:
					camposLinha = importLinhaExtratoRdcardCredito(numLinha);
					break;
				case EXTRATO_RDCARD_DEBITO:
					camposLinha = importLinhaExtratoRdcardDebito(numLinha);
					break;
				case EXTRATO_MODERNINHA_DEBITO:
					camposLinha = importLinhaExtratoModerninhaDebito(numLinha);
					break;
				case EXTRATO_CIELO_DEBITO:
					camposLinha = importLinhaExtratoCieloDebito(numLinha);
					break;
				case EXTRATO_CIELO_CREDITO:
					camposLinha = importLinhaExtratoCieloCredito(numLinha);
					break;
				case EXTRATO_CIELO_DEBITO_NOVO:
					camposLinha = importLinhaExtratoCieloDebitoNovo(numLinha);
					break;
				case EXTRATO_CIELO_CREDITO_NOVO:
					camposLinha = importLinhaExtratoCieloCreditoNovo(numLinha);
					break;
				case EXTRATO_STONE_DEBITO:
					camposLinha = importLinhaExtratoStoneDebito(numLinha);
					break;
				case EXTRATO_STONE_CREDITO:
					camposLinha = importLinhaExtratoStoneCredito(numLinha);
					break;
				default:
					throw new ViewException("Tipo de extrato inválido.");
			}

			if (camposLinha == null) {
				throw new ViewException("Erro ao extrair camposLinha");
			}

			// Para extratos de cartões de débito, o procedimento é outro
			if (tipoExtrato.equals(TipoExtrato.EXTRATO_CIELO_DEBITO)
					|| tipoExtrato.equals(TipoExtrato.EXTRATO_RDCARD_DEBITO)
					|| tipoExtrato.equals(TipoExtrato.EXTRATO_STONE_DEBITO)
					|| tipoExtrato.equals(TipoExtrato.EXTRATO_MODERNINHA_DEBITO)
					|| tipoExtrato.equals(TipoExtrato.EXTRATO_CIELO_DEBITO_NOVO)) {
				return getThiz().handleLinhaImportadaDebito(tipoExtrato, carteiraExtrato, carteiraDestino, camposLinha);
			} else {
				return getThiz()
						.handleLinhaImportadaPadrao(tipoExtrato, getThiz().getLinhas()
								.get(numLinha), carteiraExtrato, carteiraDestino, gerarSemRegras, camposLinha);
			}

		} catch (Exception e) {
			logger.error(e);
			throw new ViewException("Erro ao importar a linha: [" + getThiz().getLinhas().get(numLinha) + "]");
		}
	}

	/**
	 * Trata do que fazer com os dados importados de uma linha de extrato de cartão de débito (obtida pelo site da Cielo, Redecard, etc).
	 *
	 * @param carteiraExtrato
	 * @param carteiraDestino
	 * @param camposLinha
	 * @throws ViewException
	 */
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	private Movimentacao handleLinhaImportadaDebito(TipoExtrato tipoExtrato, Carteira carteiraExtrato,
			Carteira carteiraDestino,
			Map<String, Object> camposLinha) throws ViewException {

		String descricao = (String) camposLinha.get("descricao");
		Date dtMoviment = (Date) camposLinha.get("dtMoviment");
		Date dtVenctoEfetiva = (Date) camposLinha.get("dtVenctoEfetiva");
		BigDecimal valor = (BigDecimal) camposLinha.get("valor");
		BigDecimal desconto = (BigDecimal) camposLinha.get("desconto");
		BigDecimal valorTotal = (BigDecimal) camposLinha.get("valorTotal");

		BandeiraCartao bandeiraCartao = (BandeiraCartao) camposLinha.get("bandeiraCartao");

		Categoria categ101 = getCategoriaFinder().findBy(101l);
		//		Categoria categ199 = getCategoriaFinder().findBy(199l);
		Categoria categ299 = getCategoriaFinder().findBy(299l);

		String modo = "RECEB. CARTÃO DÉBITO";

		// carteiraDestino aqui tem que ser sempre o Caixa a Vista (que é o caixa que primeiro recebeu o valor da compra).

		Movimentacao mov101 = null;

		// Primeiro tento encontrar a movimentação original do cartão, que é a movimentação de entrada (101) no caixa a vista (anotado na folhinha de fechamento de caixa, lançado manualmente).
		List<Movimentacao> movs101 = getMovimentacaoFinder()
				.findBy(dtMoviment, valor, carteiraDestino, bandeiraCartao, categ101);
		movs101 = (List<Movimentacao>) CollectionUtils.removeAll(movs101, getMovs101JaImportadas());

		// Se não encontrou no dia em questão, procura no próximo dia útil
		if ((movs101 == null) || (movs101.size() < 1)) {
			Date proximoDiaUtil = getDiaUtilFinder().findProximoDiaUtilComercial(CalendarUtil.incDias(dtMoviment, 1));
			movs101 = getMovimentacaoFinder().findBy(proximoDiaUtil, valor, carteiraDestino, bandeiraCartao, categ101);
			movs101 = (List<Movimentacao>) CollectionUtils.removeAll(movs101, getMovs101JaImportadas());
		}

		// Se não encontrou nem no dia, nem no próximo, dá erro...
		if ((movs101 == null) || (movs101.size() < 1)) {
			throw new ViewException("Movimentação original não encontrada: " + descricao + ", valor: " + valorTotal);
		}

		// Preciso encontrar uma movimentação que ainda não esteja em cadeia, pois no mesmo dia pode ter dois recebimentos iguais (valor, bandeira, etc).
		for (Movimentacao _m : movs101) {
			if (_m.getCadeia() == null) {
				mov101 = _m;
			}
		}

		// Nenhuma movimentação sem cadeia
		if (mov101 == null) {
			mov101 = movs101.get(0);
			// throw new ViewException("Movimentação provavelmente já importada.");
		}

		getMovs101JaImportadas().add(mov101);

		// Se encontrou, então, a movimentação original, verifico se já existe a movimentação importada (199) (esta)
		Movimentacao movimentacao299 = null;

		// Aqui as carteiras são invertidas, pois é a 299 (a destino do método é a do extrato, e a destino da importação é a 'origem' no método)
		List<Movimentacao> movs299 = getMovimentacaoFinder()
				.findBy(mov101.getDtMoviment(), valor, carteiraDestino, carteiraExtrato, bandeiraCartao, categ299);

		// Remove as já importadas para resolver o bug de ter duas movimentações de mesma bandeira e mesmo valor no mesmo dia
		movs299 = (List<Movimentacao>) CollectionUtils.removeAll(movs299, getMovimentacaoImportadas());

		if (movs299 != null) {
			if (movs299.size() > 0) {
				// se encontrou uma ou mais, pega a primeira.
				movimentacao299 = movs299.get(0);
			}
		}

		// Se já existir, só retorna pois já encontrou a que já tinha sido importada...
		if (movimentacao299 != null) {
			return movimentacao299;
		} else {
			// Caso contrário, ainda não foi importada, então cria uma nova.
			if ((mov101.getCadeia() != null) && (mov101.getCadeia().getMovimentacoes() != null)
					&& (mov101.getCadeia().getMovimentacoes().size() > 0)) {
				// A movimentação original ainda não pode fazer parte de uma cadeia.
				throw new ViewException("Inconsistência: movimentação original já possui uma cadeia e a movimentação que se está tentando importar não faz parte dela.");
			}

			// Crio as movimentações 299 (no caixa AV) e 199 (na carteira extrato)

			movimentacao299 = new Movimentacao();

			// aqui se inverte as carteiras, pois para salvar uma transferência entre carteiras se deve sempre começar pela 299 (ver como funciona o MovimentacaoDataMapperImpl.processSave)
			movimentacao299.setCarteira(carteiraDestino); // vai debitar no 'CAIXA A VISTA'
			movimentacao299.setCarteiraDestino(carteiraExtrato); // vai creditar na carteira do cartão (199)
			movimentacao299.setCategoria(categ299);
			movimentacao299.setValor(valor);
			movimentacao299.setDescontos(desconto);
			movimentacao299.setValorTotal(valorTotal);
			descricao = StringUtils.replaceAll("  ", " ", descricao);
			movimentacao299.setDescricao(descricao);
			movimentacao299.setTipoLancto(TipoLancto.TRANSF_PROPRIA); // para gerar as duas (299+199)
			movimentacao299.setStatus(Status.REALIZADA);
			movimentacao299.setModo(getModoFinder().findBy(modo));
			movimentacao299.setCentroCusto(getCentroCustoFinder().findBy(1));

			movimentacao299.setDtMoviment(mov101.getDtMoviment());
			movimentacao299.setDtVencto(dtVenctoEfetiva);
			movimentacao299.setDtVenctoEfetiva(dtVenctoEfetiva); // por questão de informação, a data efetiva em que o cartão pagou o valor fica na dt vencto nossa
			// tenho que deixar a dtPagto como a dtMoviment porque a 299
			// no caixa a vista tem que ser com a mesma data da 101 (que foi lançada através do fechamento de caixa diário).
			// e não posso ter uma 199 com data diferente da 299 correspondente
			movimentacao299.setDtPagto(mov101.getDtMoviment());

			movimentacao299.setBandeiraCartao(bandeiraCartao);

			OperadoraCartao operadoraCartao = getOperadoraCartaoFinder().findBy(carteiraExtrato);
			movimentacao299.setOperadoraCartao(operadoraCartao);

			return movimentacao299;

		}

	}

	/**
	 * Trata do que fazer com os dados importados de uma linha padrão (basicamente aquela que não diz respeito a um extrato de cartão de
	 * débito).
	 *
	 * @param linha
	 * @param carteiraExtrato
	 * @param carteiraDestino
	 * @param gerarSemRegras
	 * @param camposLinha
	 * @return
	 * @throws ViewException
	 */
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	private Movimentacao handleLinhaImportadaPadrao(TipoExtrato tipoExtrato, String linha, Carteira carteiraExtrato,
			Carteira carteiraDestino,
			boolean gerarSemRegras, Map<String, Object> camposLinha) throws ViewException {

		String descricao = (String) camposLinha.get("descricao");
		Date dtMoviment = (Date) camposLinha.get("dtMoviment");
		Date dtVenctoEfetiva = (Date) camposLinha.get("dtVenctoEfetiva");
		BigDecimal valor = (BigDecimal) camposLinha.get("valor");
		BigDecimal desconto = (BigDecimal) camposLinha.get("desconto");
		BigDecimal valorTotal = (BigDecimal) camposLinha.get("valorTotal");
		Long entradaOuSaida = (Long) camposLinha.get("entradaOuSaida");
		Modo modo = (Modo) camposLinha.get("modo");
		Long categoriaCodigo = (Long) camposLinha.get("categoriaCodigo");
		PlanoPagtoCartao planoPagtoCartao = (PlanoPagtoCartao) camposLinha.get("planoPagtoCartao");
		String numCheque = null;

		// Aqui o camposLinha.get("valor") ainda pode ser negativo, para poder setar a categoria como 2 ou 1 quando não tiver categoria, para poder fazer a seleção correta de cor na linha
		boolean valorNegativo = valor.doubleValue() < 0.00;
		valor = valor.abs();

		RegraImportacaoLinha regra = null;

		logger.debug("Verificando se tem alguma REGEX java que bate com a linha: '" + descricao + "'");
		for (RegraImportacaoLinha r : getRegraImportacaoLinhaFinder().findAllBy(carteiraExtrato)) {
			logger.debug("REGRA: " + r.getRegraRegexJava() + "...");

			if (r.getRegraRegexJava() != null && !"".equals(r.getRegraRegexJava())) {
				if (StringUtils.regexMatch(r.getRegraRegexJava(), descricao)) {
					if (r.getSinalValor() == 0 || (r.getSinalValor() == -1 && valorNegativo)
							|| (r.getSinalValor() == 1 && !valorNegativo)) {
						regra = r;
						logger.debug("... bate. OK");
						break;
					}
				}
			}
			logger.debug("... não bate.");
		}
		if (regra == null) {
			logger.debug("Nenhuma regra para esta linha.");
		}

		if (regra != null && regra.getRegraRegexJava() != null && !"".equals(regra.getRegraRegexJava())) {
			// linha contendo compensação de um cheque
			numCheque = StringUtils.getGroupFromRegex(linha, regra.getRegraRegexJava(), "NUMCHEQUE");
			if (numCheque != null) {
				numCheque = numCheque.replaceAll("[^\\d]", "");
				// convertendo para integer para remover zeros a esquerda
				Integer numChequeInt = Integer.parseInt(numCheque);
				numCheque = numChequeInt.toString();
			}
		}

		if (tipoExtrato.equals(TipoExtrato.EXTRATO_CIELO_DEBITO)
				|| tipoExtrato.equals(TipoExtrato.EXTRATO_RDCARD_DEBITO)) {

			modo = getModoFinder().findBy(10); // RECEB. CARTÃO DÉBITO

		} else if (tipoExtrato.equals(TipoExtrato.EXTRATO_CIELO_CREDITO)
				|| tipoExtrato.equals(TipoExtrato.EXTRATO_RDCARD_CREDITO)) {

			modo = getModoFinder().findBy(9); // RECEB. CARTÃO CRÉDITO

		} else if (numCheque != null && valorNegativo) {
			modo = getModoFinder().findBy(3); // CHEQUE PRÓPRIO
		} else if (numCheque != null && !valorNegativo) {
			modo = getModoFinder().findBy(4); // CHEQUE TERCEIROS
		}

		BandeiraCartao bandeiraCartao = null;
		if (camposLinha.containsKey("bandeiraCartao")) {
			bandeiraCartao = (BandeiraCartao) camposLinha.get("bandeiraCartao");
		}

		// Verificar aqui, dependendo do tipo do extrato, o modo correto
		// Primeiro tenta encontrar movimentações em aberto de qualquer carteira, com o mesmo valor e dtVencto
		List<Status> abertos = new ArrayList<Status>();
		abertos.add(Status.A_COMPENSAR);
		abertos.add(Status.ABERTA);
		List<Movimentacao> movsAbertas = getMovimentacaoFinder()
				.findBy(dtVenctoEfetiva, valor, entradaOuSaida, modo, abertos, null, numCheque);

		// Depois tenta encontrar movimentações de qualquer status somente da carteira do extrato
		List<Carteira> carteiras = new ArrayList<Carteira>();
		carteiras.add(carteiraExtrato);
		List<Movimentacao> movsTodas = getMovimentacaoFinder()
				.findBy(dtVenctoEfetiva, valor, entradaOuSaida, modo, null, carteiras, numCheque);

		// Junto os dois resultados (ele já ignora as possíveis duplicações)
		List<Movimentacao> movs = (List<Movimentacao>) CollectionUtils.union(movsAbertas, movsTodas);

		// Ignora as movimentações que já foram importadas
		//		movs = removerJaImportadas(movs);
		movs = (List<Movimentacao>) CollectionUtils.removeAll(movs, getMovimentacaoImportadas());

		//getMovimentacaoImportadas()

		Movimentacao movimentacao = null;

		// Se achou alguma movimentação já lançada, pega a primeira
		if ((movs != null) && (!movs.isEmpty())) {
			movimentacao = movs.get(0);
			movimentacao = getMovimentacaoFinder().refresh(movimentacao);
			if (movimentacao.getParcelamento() != null) {
				movimentacao.getParcelamento().getParcelas().size(); // touch
			}
		}

		if (movimentacao != null) {
			if (movimentacao.getPessoa() != null) {
				movimentacao.getPessoa().hashCode(); // touch
			}

			if (movimentacao.getDtPagto() == null) {
				movimentacao.setStatus(Status.REALIZADA);
				movimentacao.setDtPagto(dtVenctoEfetiva);
			}

			// como ele pode ter encontrado a movimentação em outra carteira, seta para carteira do extrato
			movimentacao.setCarteira(carteiraExtrato);

			return movimentacao;
		} else {

			if (regra != null) {

				movimentacao = new Movimentacao();
				movimentacao.setUnqControle(com.ocabit.utils.strings.StringUtils.generateRandomString(15));

				Carteira carteiraOrigem = regra.getCarteira() != null ? regra.getCarteira() : carteiraExtrato;
				carteiraDestino = regra.getCarteiraDestino() != null ? regra.getCarteiraDestino() : carteiraDestino;

				// se for regra para TRANS_PROPRIA
				if (regra.getTipoLancto().equals(TipoLancto.TRANSF_PROPRIA)) {
					// Nas transferências entre contas próprias, a regra informa a carteira de origem.
					// A de destino, se não for informada na regra, será a do extrato.

					if (!regra.getCategoria().getCodigo().equals(299l)) {
						throw new ViewException("Regras para transferências entre carteiras próprias devem ser apenas com categoria 2.99");
					}

					// Se a regra informar a carteira da 299, prevalesce
					Carteira cart299 = regra.getCarteira() != null ? regra.getCarteira() : carteiraExtrato;

					// 
					Carteira cart199 = regra.getCarteiraDestino();
					if ((cart199 == null) || cart199.getCodigo().equals(99)) {
						cart199 = carteiraExtrato;
					}

					movimentacao.setCarteira(cart299);
					carteiraDestino = cart199;
					movimentacao.setCarteiraDestino(carteiraDestino);

					// se NÃO for regra para TRANSF_PROPRIA
				} else {
					// se é uma regra para um cheque próprio, tenta encontrá-lo
					if (regra.getTipoLancto().equals(TipoLancto.CHEQUE_PROPRIO)) {

						movimentacao = getMovimentacaoFinder().findBy(valor, carteiraExtrato, numCheque);

						if (getMovimentacaoImportadas().contains(movimentacao)) {
							movimentacao = null;
						}

						// Se achou a movimentação deste cheque, só seta a dtPagto
						if (movimentacao != null) {
							movimentacao = getMovimentacaoFinder().refresh(movimentacao);
							movimentacao.setDtPagto(dtVenctoEfetiva);
							if (movimentacao.getParcelamento() != null) {
								movimentacao.getParcelamento().getParcelas().size(); // touch
							}
							return movimentacao;
						} else {

							movimentacao = new Movimentacao();
							movimentacao.setUnqControle(com.ocabit.utils.strings.StringUtils.generateRandomString(15));

							Cheque cheque = new Cheque();
							cheque.setNumCheque(numCheque);
							cheque.setBanco(regra.getCarteira().getBanco());
							cheque.setAgencia(regra.getCarteira().getAgencia());
							cheque.setConta(regra.getCarteira().getConta());

							movimentacao.setCheque(cheque);
						}
						// Se for regra para cheque de terceiros, trata...
					} else if (regra.getTipoLancto().equals(TipoLancto.CHEQUE_TERCEIROS)) {
						Cheque cheque = new Cheque();

						cheque.setNumCheque(numCheque);

						if (regra.getCheque() != null) {
							cheque.setAgencia(regra.getCheque().getAgencia());
							cheque.setConta(regra.getCheque().getConta());
							cheque.setBanco(regra.getCheque().getBanco());
						} else {
							cheque.setAgencia("????");
							cheque.setConta("????");
							cheque.setBanco(getBancoFinder().findBy(999));
						}
						movimentacao.setCheque(cheque);
					}
				}

				movimentacao.setTipoLancto(regra.getTipoLancto());

				movimentacao.setCarteira(carteiraOrigem);
				if (movimentacao.getTipoLancto().equals(TipoLancto.TRANSF_PROPRIA)) {
					movimentacao.setCarteiraDestino(carteiraDestino);
				}

				movimentacao.setDescricao(String.format(regra.getPadraoDescricao(), descricao));

				movimentacao.setCategoria(regra.getCategoria());
				movimentacao.setCentroCusto(regra.getCentroCusto());

				movimentacao.setDtMoviment(dtVenctoEfetiva);
				movimentacao.setDtVencto(dtVenctoEfetiva);

				movimentacao.setStatus(regra.getStatus());

				movimentacao.setModo(regra.getModo());
				movimentacao.setValor(valor);
				movimentacao.setValorTotal(valor);

				if (regra.getStatus().equals(Status.REALIZADA)) {
					movimentacao.setDtPagto(dtVenctoEfetiva);
				}

				movimentacao.setPlanoPagtoCartao(planoPagtoCartao);

				if (numCheque != null) {
					Cheque cheque = new Cheque();
					cheque.setNumCheque(numCheque);
					movimentacao.setCheque(cheque);
				}

				return movimentacao;

			} else if (gerarSemRegras) {
				// se for pra gerar movimentações que não se encaixem nas regras...
				movimentacao = new Movimentacao();
				movimentacao.setUnqControle(com.ocabit.utils.strings.StringUtils.generateRandomString(15));
				movimentacao.setCarteira(carteiraExtrato);
				movimentacao.setValor(valor);
				movimentacao.setDescontos(desconto);
				movimentacao.setValorTotal(valorTotal);
				descricao = StringUtils.replaceAll("  ", " ", descricao);
				movimentacao.setDescricao(descricao);

				movimentacao.setTipoLancto(TipoLancto.REALIZADA);
				movimentacao.setStatus(Status.REALIZADA);
				movimentacao.setModo(modo);
				movimentacao.setCentroCusto(getCentroCustoFinder().findBy(1));
				movimentacao.setDtMoviment(dtMoviment);
				movimentacao.setDtVencto(dtVenctoEfetiva);
				movimentacao.setDtVenctoEfetiva(dtVenctoEfetiva);
				movimentacao.setDtPagto(dtVenctoEfetiva);

				movimentacao.setBandeiraCartao(bandeiraCartao);

				movimentacao.setPlanoPagtoCartao(planoPagtoCartao);

				if (categoriaCodigo != null) {
					movimentacao.setCategoria(getCategoriaFinder().findBy(categoriaCodigo));
				} else {
					if (valorNegativo) {
						movimentacao.setCategoria(getCategoriaFinder().findBy(2l));
					} else {
						movimentacao.setCategoria(getCategoriaFinder().findBy(1l));
					}
				}

				return movimentacao;
			} else {
				logger.warn("Nenhuma movimentação gerada.");
				return null;
			}
		}
	}

	//	/**
	//	 * Remove da lista todas as movimentações que já foram importadas previamente.
	//	 * Este método serve para os casos onde tem 2 lançamentos iguais.
	//	 * Se não fosse removidos, o sistema entenderia que as duas movimentações se tratam da mesma já existente na base.
	//	 * 
	//	 * @param movs
	//	 * @return
	//	 */
	//	private List<Movimentacao> removerJaImportadas(List<Movimentacao> movs) {
	//		List<Movimentacao> results = new ArrayList<Movimentacao>();
	//		for (Movimentacao mR : movs) {
	//			boolean jaImportou = false;
	//			for (Movimentacao m : getMovimentacaoImportadas()) {
	//				if (m.getId() != null && m.getId().equals(mR.getId())) {
	//					jaImportou = true;
	//					break;
	//				}
	//			}
	//			if (!jaImportou) {
	//				results.add(mR);
	//			}
	//		}
	//		return results;
	//	}

	/**
	 * Verifica se uma linha está no formato DATA DESCRIÇÃO VALOR ou se é uma linha de SALDO (pois nestes casos não irá pegar a descrição
	 * complementar que poderia estar na segunda linha).
	 * 
	 * @param linha
	 * @return
	 */
	public boolean ehLinhaExtratoSimplesOuSaldo(String linha) {

		if (linha.replace(" ", "").contains("SALDO")) {
			return true;
		}

		if (StringUtils
				.regexMatch("\\s*" + RegexUtils.patternData + "(.)+" + RegexUtils.patternMoney + "\\s*", linha)) {
			return true;
		}

		return false;
	}

	/**
	 * Importa uma linha de extrato simples (DATA DESCRIÇÃO VALOR).
	 *
	 * @param linha
	 * @return
	 * @throws ViewException
	 */
	private Map<String, Object> importLinhaExtratoSimples(int numLinha) throws ViewException {

		List<String> linhas = getThiz().getLinhas();

		String linhaOriginal = getLinhas().get(numLinha);
		String linha = linhaOriginal.trim();

		Map<String, Object> camposLinha = new HashMap<String, Object>();

		String dataStr = StringUtils.substringByRegex(linha, RegexUtils.patternData);
		boolean dataIncompleta = false;
		boolean dataPequena = true; // dd/mm/yyyy
		// se só veio dd/mm, acrescenta o ano
		if (dataStr.length() == 5) {
			dataStr += "/" + new SimpleDateFormat("yyyy").format(new Date());
			dataIncompleta = true;
		} else if (dataStr.length() == 8) {
			dataPequena = true;
		} else if (dataStr.length() != 10) {
			throw new ViewException("Erro ao ler a data na linha: [" + linha + "]");
		}
		Date dtVenctoEfetiva = null;
		try {
			if (dataPequena) {
				dtVenctoEfetiva = new SimpleDateFormat("dd/MM/yy").parse(dataStr);
			} else {
				dtVenctoEfetiva = new SimpleDateFormat("dd/MM/yyyy").parse(dataStr);
			}
		} catch (ParseException e) {
			throw new ViewException(e);
		}

		// Quando vira o ano, e estou importando de novembro ou dezembro, ele tem que entender como o ano passado
		if (dataIncompleta && CalendarUtil.difBetweenDatesInMonths(new Date(), dtVenctoEfetiva) > 2) {
			dtVenctoEfetiva = CalendarUtil.incMes(dtVenctoEfetiva, -12);
		}

		String valorStr = StringUtils.substringByRegex(linha, RegexUtils.patternMoney + "[DC\\s]*$"); // adiciono o "\\s*$" para pegar o último valor encontrado, para pular se tiver algum parecido com valor no meio da descrição.
		// valorStr = valorStr.replace(" ", "");

		// verifica negativos no formato BANCO DO BRASIL (Ex.: 1.234,56D ou 1.234,56 D) ou outros que tenham o sinal de "-" depois do valor
		try {
			String valorComSufixo = linha.substring(linha.indexOf(valorStr)).replaceAll("[^0-9,-.D]", "");
			char sufixo = valorComSufixo.charAt(valorComSufixo.length() - 1);
			if ((sufixo == 'D') || (sufixo == '-')) {
				// if (linha.charAt(linha.indexOf(valorStr) + valorStr.length()) == 'D' || linha.charAt(linha.indexOf(valorStr) + valorStr.length() + 1) == 'D') {
				valorStr = "-" + valorStr;
			}
		} catch (Exception e1) {
			logger.info("Não é linha de extrato do BB");
		}

		valorStr = valorStr.replaceAll("[^0-9\\.\\,-]", "");

		Double dValor = null;
		try {
			dValor = NumberFormat.getNumberInstance(new Locale("pt", "BR")).parse(valorStr)
					.doubleValue();
		} catch (ParseException e) {
			throw new ViewException(e);
		}

		Long entradaOuSaida = dValor < 0 ? 2l : 1l; // 2: SAÍDA / 1: ENTRADA

		// dValor = Math.abs(dValor);

		BigDecimal valor = CurrencyUtils.getBigDecimalCurrency(dValor);

		String dataNaLinha = StringUtils.substringByRegex(linha, RegexUtils.patternData);
		String valorNaLinha = StringUtils.substringByRegex(linha, RegexUtils.patternMoney);

		String descricao = linha.replaceAll(dataNaLinha, "").replaceAll(valorNaLinha, "").trim();

		// Se não for a última linha...
		if (numLinha < linhas.size() - 1) {
			// ...verifica se a próxima linha é uma linha completa (DATA DESCRIÇÃO VALOR), ou se é uma linha de complemento da linha anterior
			String linhaComplementar = linhas.get(numLinha + 1);
			if (!ehLinhaExtratoSimplesOuSaldo(linhaComplementar)) {
				getLinhasComplementares().add(numLinha + 1);
				descricao += " (" + linhaComplementar.trim().replaceAll("\\s{2}", " ") + ")";
			}
		}

		// do que restou da descrição, pega uma String que:
		// comece com DUAS letras ou número, + tudo no meio, + até acabar com uma letra ou número
		// os grupos tem que ser desativados para retornar apenas um grupo, senão a função substringByRegex não pega
		//String pDescricao = "((?:\\w|\\d){2}(?:.)*(?:\\w|\\d){1})";

		//descricao = StringUtils.substringByRegex(descricao, pDescricao);

		descricao = descricao.replaceAll("(\\s){2,}", " ");

		camposLinha.put("descricao", descricao);
		camposLinha.put("dtVenctoEfetiva", dtVenctoEfetiva);
		camposLinha.put("dtMoviment", dtVenctoEfetiva); // passo o mesmo por se tratar de extrato simples (diferente de extrato de cartão).
		camposLinha.put("valor", valor);
		camposLinha.put("desconto", null);
		camposLinha.put("valorTotal", valor);
		camposLinha.put("entradaOuSaida", entradaOuSaida);

		// Modo modo = getModoFinder().findBy(99); // INDEFINIDO
		camposLinha.put("modo", null);

		return camposLinha;

	}

	/**
	 * Importa uma linha de extrato simples (DATA DESCRIÇÃO VALOR).
	 *
	 * @param linhasImportadas
	 * @param linhasNaoImportadas
	 *
	 * @param linha
	 * @return
	 * @throws ViewException
	 */
	private List<Movimentacao> importExtratoCompraBndesBB() throws ViewException {

		List<String> linhas = getThiz().getLinhas();

		List<Movimentacao> movs = new ArrayList<Movimentacao>();

		// Grupo (LINHA 0)
		String linha0 = linhas.get(0).trim();

		String finalCartao = linha0.substring(linha0.length() - 4).trim();
		Grupo grupo = getGrupoFinder().findBy("BB BNDES%" + finalCartao);

		if (grupo == null) {
			throw new ViewException("Grupo não encontrado para cartão final " + finalCartao);
		}

		// Fornecedor (LINHA 1)
		String linha2 = linhas.get(2).trim();
		String fornecedorStr = linha2.substring(linha2.indexOf("Fornecedor") + 10).trim();

		// Data da Compra (LINHA 4)
		String dtCompraStr = StringUtils.substringByRegex(linhas.get(4), RegexUtils.patternData);
		Date dtCompra = null;
		try {
			dtCompra = new SimpleDateFormat("dd/MM/yyyy").parse(dtCompraStr);
		} catch (ParseException e) {
			throw new ViewException(e);
		}

		// Obs
		StringBuilder sbObs = new StringBuilder("");
		sbObs.append(linhas.get(6));
		sbObs.append(linhas.get(7));
		sbObs.append(linhas.get(9));
		sbObs.append(linhas.get(10));
		sbObs.append(linhas.get(11));
		sbObs.append(linhas.get(12));

		Parcelamento parcelamento = new Parcelamento();

		BigDecimal bdTotalParcelas = CurrencyUtils.getBigDecimalCurrency(0.0);

		for (int i = 23; i < linhas.size(); i++) {
			String[] campos = null;
			try {
				campos = linhas.get(i).split("\t");
				if (campos.length < 6) {
					continue;
				}
			} catch (Exception e1) {
				logger.warn("Erro ao processar a linha [" + linhas.get(i) + "]");
				continue;
			}

			for (int j = 0; j < campos.length; j++) {
				campos[j] = campos[j].trim();
			}

			Movimentacao m = new Movimentacao();
			m.setUnqControle(com.ocabit.utils.strings.StringUtils.generateRandomString(15));

			m.setTipoLancto(TipoLancto.MOVIMENTACAO_AGRUPADA);

			parcelamento.addParcela(m);

			Date dtVencto = null;
			try {
				dtVencto = new SimpleDateFormat("dd/MM/yyyy").parse(campos[1]);
			} catch (ParseException e) {
				throw new ViewException(e);
			}

			GrupoItem grupoItem = getGrupoItemFinder().findBy(grupo, dtVencto);
			if (grupoItem == null) {
				grupoItem = getGrupoDataMapper().gerarNoMesAno(grupo, dtVencto);
			}

			m.setGrupoItem(grupoItem);

			Double dValorAmortiz = null;
			Double dValorJuros = null;
			Double dValorParcela = null;
			try {
				dValorAmortiz = NumberFormat.getNumberInstance(new Locale("pt", "BR")).parse(campos[2])
						.doubleValue();
				dValorJuros = NumberFormat.getNumberInstance(new Locale("pt", "BR")).parse(campos[3])
						.doubleValue();
				dValorParcela = NumberFormat.getNumberInstance(new Locale("pt", "BR")).parse(campos[4])
						.doubleValue();

				bdTotalParcelas = CurrencyUtils.soma(bdTotalParcelas, CurrencyUtils
						.getBigDecimalCurrency(dValorParcela));
			} catch (ParseException e) {
				throw new ViewException(e);
			}

			m.setValor(CurrencyUtils.getBigDecimalCurrency(dValorAmortiz));
			m.setAcrescimos(CurrencyUtils.getBigDecimalCurrency(dValorJuros));
			m.setValorTotal(CurrencyUtils.getBigDecimalCurrency(dValorParcela));

			m.setDescricao(fornecedorStr);

			m.setDtMoviment(dtCompra);

			Categoria categ101 = getCategoriaFinder().findBy(202001l); // 2.02.001 - CUSTOS DE MERCADORIAS
			m.setCategoria(categ101);

			CentroCusto centroCustoCTPL = getCentroCustoFinder().findBy(1); // CTPL
			m.setCentroCusto(centroCustoCTPL);

			Modo modo = getModoFinder().findBy(50); // MOVIMENTAÇÃO AGRUPADA
			m.setModo(modo);

			m.setNumParcela(Integer.parseInt(campos[0]));

			m.setObs(sbObs.toString());

			m.setStatus(Status.REALIZADA);

			movs.add(m);
		}

		// parcelamento.setQtdeParcelas(qtdeParcelas);
		parcelamento.setValorTotal(bdTotalParcelas);

		return movs;

	}

	/**
	 * Importa as linhas de uma fatura de grupo de movimentação.
	 *
	 * @param linhas
	 * @param grupoItem
	 * @return
	 */
	public List<Movimentacao> importGrupoMovimentacao(GrupoItem grupoItem) {

		List<String> linhas = getThiz().getLinhas();

		List<Movimentacao> movimentacoes = new ArrayList<Movimentacao>();

		List<String> linhasNaoImportadas = new ArrayList<String>();
		final String txtLinhaNaoImportada = "<<< LINHAS NÃO IMPORTADAS >>>";

		List<String> linhasImportadas = new ArrayList<String>();
		final String txtLinhaImportada = "<<< LINHAS IMPORTADAS >>>";

		for (int i = 0; i < linhas.size(); i++) {

			String linha = linhas.get(i);

			if (linha.equals(MovimentacaoImporter.TXT_LINHA_NAO_IMPORTADA)
					|| linha.equals(MovimentacaoImporter.TXT_LINHA_IMPORTADA)
					|| linha.length() == 0) {
				continue;
			}

			Movimentacao importada = null;
			try {
				Map<String, Object> camposLinha = new HashMap<String, Object>();
				camposLinha = importLinhaExtratoSimples(i);

				String descricao = (String) camposLinha.get("descricao");
				Date dtMoviment = (Date) camposLinha.get("dtMoviment");
				Date dtVenctoEfetiva = (Date) camposLinha.get("dtVenctoEfetiva");
				BigDecimal valor = (BigDecimal) camposLinha.get("valor");
				BigDecimal desconto = (BigDecimal) camposLinha.get("desconto");
				BigDecimal valorTotal = (BigDecimal) camposLinha.get("valorTotal");

				// Tenta encontrar uma movimentação com as características passadas.
				List<Movimentacao> movs = getMovimentacaoFinder()
						.findBy(dtMoviment, valor, grupoItem);

				importada = null;

				if ((movs != null) && (movs.size() > 0)) {
					importada = movs.get(0);
				}

				if (importada != null) {
					if (importada.getPessoa() != null) {
						importada.getPessoa().hashCode(); // touch
					}

					if (importada.getDtPagto() == null) {
						importada.setStatus(Status.REALIZADA);
						importada.setDtPagto(dtVenctoEfetiva);
					}
				} else {
					importada = new Movimentacao();
					importada.setUnqControle(com.ocabit.utils.strings.StringUtils.generateRandomString(15));

					importada.setGrupoItem(grupoItem);

					Categoria categ101 = getCategoriaFinder().findBy(202001l); // 2.02.001 - CUSTOS DE MERCADORIAS
					importada.setCategoria(categ101);

					CentroCusto centroCustoCTPL = getCentroCustoFinder().findBy(1); // CTPL
					importada.setCentroCusto(centroCustoCTPL);

					Modo modo = getModoFinder().findBy(50); // MOVIMENTAÇÃO AGRUPADA
					importada.setModo(modo);

					importada.setValor(valor);
					importada.setDescontos(desconto);
					importada.setValorTotal(valorTotal);
					descricao = StringUtils.replaceAll("  ", " ", descricao);
					importada.setDescricao(descricao);

					importada.setTipoLancto(TipoLancto.MOVIMENTACAO_AGRUPADA);
					importada.setStatus(Status.REALIZADA);

					importada.setDtMoviment(dtMoviment);
					importada.setDtVencto(dtVenctoEfetiva);
					importada.setDtVenctoEfetiva(dtVenctoEfetiva);
					importada.setDtPagto(dtVenctoEfetiva);

					importada.setBandeiraCartao(null);

				}

				movimentacoes.add(importada);

				linhasImportadas.add(linha);
			} catch (Exception e) {
				logger.error(e);
				linhasNaoImportadas.add(linha);
			}
		}

		List<String> _linhasNaoImportadas = new ArrayList<String>();

		for (String l : linhasNaoImportadas) {
			if (!l.contains(txtLinhaImportada) && !l.contains(txtLinhaNaoImportada) && !"".equals(l.trim())) {
				_linhasNaoImportadas.add(l);
			}
		}

		linhas.clear();
		linhas.add(txtLinhaNaoImportada);
		linhas.add("");
		linhas.addAll(_linhasNaoImportadas);
		linhas.add("");
		linhas.add("");
		linhas.add(txtLinhaImportada);
		linhas.add("");
		linhas.addAll(linhasImportadas);

		return movimentacoes;

	}

	/**
	 * Importa uma linha de extrato da Rede Card de cartões de crédito.
	 *
	 * @param linha
	 * @return
	 * @throws ViewException
	 */
	private Map<String, Object> importLinhaExtratoRdcardCredito(int numLinha) throws ViewException {

		String linha = getLinhas().get(numLinha);

		Map<String, Object> camposLinha = new HashMap<String, Object>();

		String[] campos = linha.split("\t");

		if (campos.length < 9) {
			logger.warn("Linha inválida.");
			return null;
		}

		Date dtVenda = null;
		Date dtReceb = null;
		try {
			dtVenda = new SimpleDateFormat("dd/MM/yyyy").parse(campos[1]);
			dtReceb = new SimpleDateFormat("dd/MM/yyyy").parse(campos[2]);
		} catch (ParseException e) {
			throw new ViewException(e);
		}

		Double dValorBruto = null;
		String strValorBruto = campos[7].replaceAll("[^0-9\\.\\,]", "");;

		Double dValorLiq = null;
		String strValorLiq = campos[8].replaceAll("[^0-9\\.\\,]", "");;

		try {
			dValorBruto = NumberFormat.getNumberInstance(new Locale("pt", "BR")).parse(strValorBruto)
					.doubleValue();
			dValorBruto = Math.abs(dValorBruto);

			dValorLiq = NumberFormat.getNumberInstance(new Locale("pt", "BR")).parse(strValorLiq)
					.doubleValue();
			dValorLiq = Math.abs(dValorLiq);
		} catch (ParseException e) {
			throw new ViewException(e);
		}

		Long entradaOuSaida = dValorBruto < 0 ? 2l : 1l;

		BigDecimal valorBruto = CurrencyUtils.getBigDecimalCurrency(dValorBruto);
		// BigDecimal valorLiq = CurrencyUtils.getBigDecimalCurrency(dValorLiq);

		/**
		 * 0) Nº do Estabelec.
		 * 1) Data da Venda
		 * 2) Data de Receb.
		 * 3) Prazo de Receb.
		 * 4) Resumo de Vendas
		 * 5) Quantidade de Vendas
		 * 6) Bandeira
		 * 7) Valor Bruto (R$)
		 * 8) Valor Líquido (R$)
		 */

		String resumoVendas = campos[4];
		String qtdeVendas = campos[5];
		String bandeira = campos[6].trim();

		Modo modo = getModoFinder().findBy(9); // "RECEB. CARTÃO CRÉDITO";
		camposLinha.put("modo", modo);

		BandeiraCartao bandeiraCartao = null;
		if (bandeira != null && !"".equals(bandeira.trim())) {
			bandeiraCartao = getBandeiraCartaoFinder().findByLabelsAndModo(bandeira, modo);
		}

		if (bandeiraCartao == null) {
			throw new ViewException("Bandeira de cartão não encontrada");
		}

		// FIXME: não tem como saber pelo relatório da REDECARD se é parcelado ou não
		PlanoPagtoCartao planoPagtoCartao = null;
		planoPagtoCartao = PlanoPagtoCartao.CREDITO_30DD;
		camposLinha.put("planoPagtoCartao", planoPagtoCartao);

		camposLinha.put("bandeiraCartao", bandeiraCartao);

		String descricao = bandeira + " - " + resumoVendas + " (" + qtdeVendas + ")";

		camposLinha.put("descricao", descricao);
		camposLinha.put("dtMoviment", dtVenda);
		camposLinha.put("dtVenctoEfetiva", dtReceb);
		camposLinha.put("valor", valorBruto); // VALOR_BRUTO
		camposLinha.put("valorTotal", valorBruto);
		camposLinha.put("entradaOuSaida", entradaOuSaida);

		camposLinha.put("categoriaCodigo", 101l);

		return camposLinha;

	}

	/**
	 * Importa uma linha de extrato da Rede Card de cartões de DÉBITO.
	 *
	 * @param linha
	 * @return
	 * @throws ViewException
	 */
	private Map<String, Object> importLinhaExtratoRdcardDebito(int numLinha) throws ViewException {

		String linha = getLinhas().get(numLinha);

		Map<String, Object> camposLinha = new HashMap<String, Object>();

		String[] campos = linha.split("\t");

		if (campos.length < 8) {
			logger.warn("Linha inválida.");
			return null;
		}

		String vendaCancelada = campos[7].trim();
		if ((vendaCancelada == null) || !"NAO".equals(vendaCancelada)) {
			logger.warn("Linha com venda cancelada ou inválida.");
			return null;
		}

		Date dtVenda = null;
		try {
			dtVenda = new SimpleDateFormat("dd/MM/yyyy").parse(campos[3]);
		} catch (ParseException e) {
			throw new ViewException(e);
		}

		Double dValorBruto = null;
		String strValorBruto = campos[5].replaceAll("[^0-9\\.\\,]", "");
		try {
			dValorBruto = NumberFormat.getNumberInstance(new Locale("pt", "BR")).parse(strValorBruto)
					.doubleValue();
			dValorBruto = Math.abs(dValorBruto);
		} catch (ParseException e) {
			throw new ViewException(e);
		}
		Long entradaOuSaida = dValorBruto < 0 ? 2l : 1l;

		BigDecimal valorBruto = CurrencyUtils.getBigDecimalCurrency(dValorBruto);

		/**
		 * 0 TID
		 * 1 Nº do Comprovante de Venda (NSU)
		 * 2 Nº Cartão(Últimos 4 dig.)
		 * 3 Data da Venda
		 * 4 Hora
		 * 5 Valor Bruto
		 * 6 Nº Estabelec.
		 * 7 Venda Cancelada
		 * 8 Bandeira (esta coluna tem de ser adicionada manualmente, pois o valor fica no 'cabeçalho' do excel)
		 */

		String comprovanteNSU = campos[1];
		String numCartao = campos[2];
		String horaVenda = campos[4];
		String bandeira = campos[8].trim();

		Modo modo = getModoFinder().findBy(10); // "RECEB. CARTÃO DÉBITO";
		camposLinha.put("modo", modo);

		BandeiraCartao bandeiraCartao = getBandeiraCartaoFinder().findByLabelsAndModo(bandeira, modo);
		if (bandeiraCartao == null) {
			throw new ViewException("Bandeira de cartão não encontrada");
		}
		camposLinha.put("bandeiraCartao", bandeiraCartao);

		String descricao = bandeira + " - " + comprovanteNSU + " - " + numCartao + " (" + horaVenda + ")";

		descricao = descricao.replace("\n", "").replace("\t", "").replace("\r", "");

		PlanoPagtoCartao planoPagtoCartao = PlanoPagtoCartao.DEBITO;
		camposLinha.put("planoPagtoCartao", planoPagtoCartao);

		camposLinha.put("descricao", descricao);
		camposLinha.put("dtMoviment", dtVenda);
		camposLinha.put("dtVenctoEfetiva", dtVenda);
		camposLinha.put("valor", valorBruto); // VALOR_BRUTO
		camposLinha.put("valorTotal", valorBruto);
		camposLinha.put("entradaOuSaida", entradaOuSaida);
		camposLinha.put("categoriaCodigo", 199l);

		return camposLinha;

	}

	/**
	 * Importa uma linha de extrato da Rede Card de cartões de DÉBITO.
	 *
	 * @param linha
	 * @return
	 * @throws ViewException
	 */
	private Map<String, Object> importLinhaExtratoModerninhaDebito(int numLinha) throws ViewException {

		/**
		 * 0 - Data_Transacao
		 * 1 - "MODERNINHA"
		 * 2 - Tipo_Pagamento
		 * 3 - Transacao_ID
		 * 4 - Valor_Bruto
		 */

		String linha = getLinhas().get(numLinha);

		Map<String, Object> camposLinha = new HashMap<String, Object>();

		String[] campos = linha.split("\t");

		if (campos.length < 4) {
			logger.warn("Linha inválida.");
			return null;
		}

		Date dtVenda = null;
		try {
			dtVenda = new SimpleDateFormat("dd/MM/yyyy").parse(campos[0]);
		} catch (ParseException e) {
			throw new ViewException(e);
		}

		Double dValorBruto = null;
		String strValorBruto = campos[4].replaceAll("[^0-9\\.\\,]", "");
		try {
			dValorBruto = NumberFormat.getNumberInstance(new Locale("pt", "BR")).parse(strValorBruto)
					.doubleValue();
			dValorBruto = Math.abs(dValorBruto);
		} catch (ParseException e) {
			throw new ViewException(e);
		}
		Long entradaOuSaida = dValorBruto < 0 ? 2l : 1l;

		BigDecimal valorBruto = CurrencyUtils.getBigDecimalCurrency(dValorBruto);

		String descricao = campos[1] + " - " + campos[3] + " (" + campos[2] + ")";
		descricao = descricao.replace("\n", "").replace("\t", "").replace("\r", "");

		String bandeira = "N INF DÉB";

		Modo modo = getModoFinder().findBy(10); // "RECEB. CARTÃO DÉBITO";
		camposLinha.put("modo", modo);

		BandeiraCartao bandeiraCartao = getBandeiraCartaoFinder().findByLabelsAndModo(bandeira, modo);
		if (bandeiraCartao == null) {
			throw new ViewException("Bandeira de cartão não encontrada");
		}
		camposLinha.put("bandeiraCartao", bandeiraCartao);

		PlanoPagtoCartao planoPagtoCartao = PlanoPagtoCartao.DEBITO;
		camposLinha.put("planoPagtoCartao", planoPagtoCartao);

		camposLinha.put("descricao", descricao);
		camposLinha.put("dtMoviment", dtVenda);
		camposLinha.put("dtVenctoEfetiva", dtVenda);
		camposLinha.put("valor", valorBruto); // VALOR_BRUTO
		camposLinha.put("valorTotal", valorBruto);
		camposLinha.put("entradaOuSaida", entradaOuSaida);
		camposLinha.put("categoriaCodigo", 199l);

		return camposLinha;

	}

	/**
	 * Importa uma linha de extrato da Rede Card de cartões de DÉBITO.
	 *
	 * @param linha
	 * @return
	 * @throws ViewException
	 */
	private Map<String, Object> importLinhaExtratoCieloDebito(int numLinha) throws ViewException {

		String linha = getLinhas().get(numLinha);

		Map<String, Object> camposLinha = new HashMap<String, Object>();

		String[] campos = linha.split("\t");

		if (campos.length < 8) {
			logger.warn("Linha inválida.");
			return null;
		}

		String vendaCancelada = campos.length >= 10 ? campos[9].trim() : "Não";

		if ((vendaCancelada == null) || "".equals(vendaCancelada)) {
			vendaCancelada = "Não";
		}

		if (!"Não".equals(vendaCancelada)) {
			logger.warn("Linha com venda rejeitada ou inválida.");
			return null;
		}

		Date dtVenda = null;
		Date dtPagto = null;
		try {
			dtVenda = new SimpleDateFormat("dd/MM/yyyy").parse(campos[0]);
			dtPagto = new SimpleDateFormat("dd/MM/yyyy").parse(campos[1]);
		} catch (ParseException e) {
			throw new ViewException(e);
		}

		Double dValorBruto = null;
		// remove tudo o que não for número, ponto ou vírgula
		//		String strValorBruto = campos[7].replaceAll("[^0-9\\.\\,]", "");
		String strValorBruto = campos[8].replaceAll("[^0-9\\.\\,]", "");
		try {
			dValorBruto = NumberFormat.getNumberInstance(new Locale("pt", "BR")).parse(strValorBruto)
					.doubleValue();
			dValorBruto = Math.abs(dValorBruto);
		} catch (ParseException e) {
			throw new ViewException(e);
		}
		Long entradaOuSaida = dValorBruto < 0 ? 2l : 1l;

		BigDecimal valorBruto = CurrencyUtils.getBigDecimalCurrency(dValorBruto);

		/**
		 * 0 Data da venda
		 * 1 Data prevista de pagamento
		 * 2 Descrição
		 * 3 Resumo de Operação
		 * 4 N° do cartão / TID
		 * 5 NSU / DOC
		 * 6 Código Autorização
		 * 7 Valor Bruto de Vendas (R$)
		 */

		String _descricao = campos[2];

		Modo modo = getModoFinder().findBy(10); // "RECEB. CARTÃO DÉBITO";
		camposLinha.put("modo", modo);

		BandeiraCartao bandeiraCartao = getBandeiraCartaoFinder().findByLabelsAndModo(_descricao, modo);

		if (bandeiraCartao == null) {
			throw new ViewException("Bandeira de cartão não encontrada");
		}

		camposLinha.put("bandeiraCartao", bandeiraCartao);

		PlanoPagtoCartao planoPagtoCartao = PlanoPagtoCartao.DEBITO;
		camposLinha.put("planoPagtoCartao", planoPagtoCartao);

		String resumoOperacao = campos[3];
		String numCartao = campos[4];
		String comprovanteNSU = campos[6];
		String codigoAutorizacao = campos[7];

		String descricao = _descricao + " - " + resumoOperacao + " - " + numCartao + " - " + comprovanteNSU + " - "
				+ codigoAutorizacao;

		descricao = descricao.replace("\n", "").replace("\t", "").replace("\r", "");

		camposLinha.put("descricao", descricao);
		camposLinha.put("dtMoviment", dtVenda);
		camposLinha.put("dtVenctoEfetiva", dtPagto);
		camposLinha.put("valor", valorBruto); // VALOR_BRUTO
		camposLinha.put("valorTotal", valorBruto);
		camposLinha.put("entradaOuSaida", entradaOuSaida);

		return camposLinha;
	}

	/**
	 * Importa uma linha de extrato da Rede Card de cartões de DÉBITO.
	 *
	 * @param linha
	 * @return
	 * @throws ViewException
	 */
	private Map<String, Object> importLinhaExtratoCieloDebitoNovo(int numLinha) throws ViewException {

		String linha = getLinhas().get(numLinha);

		Map<String, Object> camposLinha = new HashMap<String, Object>();

		String[] campos = linha.split("\t");

		/**
		 * 0 Data da venda
		 * 1 Data da autorização
		 * 2 Bandeira
		 * 3 Forma de pagamento
		 * 4 Quantidade de parcelas
		 * 5 Valor da venda
		 * 6 Taxa de administração (%)
		 * 7 Valor descontado
		 * 8 Previsão de pagamento
		 * 9 Valor líquido da venda
		 * 10 Número Lógico
		 */

		if (campos.length < 10) {
			logger.warn("Linha inválida.");
			return null;
		}

		Date dtVenda = null;
		Date dtPagto = null;
		try {
			dtVenda = new SimpleDateFormat("dd/MM/yyyy").parse(campos[0]);
			dtPagto = new SimpleDateFormat("dd/MM/yyyy").parse(campos[8]);
		} catch (ParseException e) {
			throw new ViewException(e);
		}

		Double dValorBruto = null;
		// remove tudo o que não for número, ponto ou vírgula
		String strValorBruto = campos[5].replaceAll("[^0-9\\.\\,]", "");
		try {
			dValorBruto = NumberFormat.getNumberInstance(new Locale("pt", "BR")).parse(strValorBruto)
					.doubleValue();
			dValorBruto = Math.abs(dValorBruto);
		} catch (ParseException e) {
			throw new ViewException(e);
		}
		Long entradaOuSaida = dValorBruto < 0 ? 2l : 1l;

		BigDecimal valorBruto = CurrencyUtils.getBigDecimalCurrency(dValorBruto);

		String descricao = "DÉBITO " + campos[2]; // + " " + campos[1] + " (" + campos[4] + ")";

		Modo modo = getModoFinder().findBy(10); // "RECEB. CARTÃO DÉBITO";
		camposLinha.put("modo", modo);

		BandeiraCartao bandeiraCartao = getBandeiraCartaoFinder().findByLabelsAndModo(campos[2], modo);

		if (bandeiraCartao == null) {
			throw new ViewException("Bandeira de cartão não encontrada");
		}

		camposLinha.put("bandeiraCartao", bandeiraCartao);

		PlanoPagtoCartao planoPagtoCartao = PlanoPagtoCartao.DEBITO;
		camposLinha.put("planoPagtoCartao", planoPagtoCartao);

		descricao = descricao.replace("\n", "").replace("\t", "").replace("\r", "");

		camposLinha.put("descricao", descricao);
		camposLinha.put("dtMoviment", dtVenda);
		camposLinha.put("dtVenctoEfetiva", dtPagto);
		camposLinha.put("valor", valorBruto); // VALOR_BRUTO
		camposLinha.put("valorTotal", valorBruto);
		camposLinha.put("entradaOuSaida", entradaOuSaida);

		return camposLinha;
	}

	/**
	 * Importa uma linha de extrato da Rede Card de cartões de CRÉDITO.
	 *
	 * É quase igual ao débito, muda pouca coisa nas colunas.
	 *
	 * @param linha
	 * @return
	 * @throws ViewException
	 */
	private Map<String, Object> importLinhaExtratoCieloCredito(int numLinha) throws ViewException {

		String linha = getLinhas().get(numLinha);

		Map<String, Object> camposLinha = new HashMap<String, Object>();

		String[] campos = linha.split("\t");

		/**
		 * 0 Data de Pagamento
		 * 1 Data da venda
		 * 2 Descrição
		 * 3 Resumo de Operação
		 * 4 N° do cartão / TID
		 * 5 NSU / DOC
		 * 6 Código Autorização
		 * 7 Valor Bruto de Vendas (R$)
		 * 8 Rejeitado
		 */

		String strDtVenda = campos[1].trim();
		String strDtPrevistaPagto = campos[0].trim();
		String strDescricao = campos[2].trim();
		String strValorBruto = campos[8].replaceAll("[^0-9\\.\\,]", "");

		String resumoOperacao = campos[3];
		String numCartao = campos[4];
		String comprovanteNSU = campos[6];
		String codigoAutorizacao = campos[7];

		if (campos.length < 9) {
			logger.warn("Linha inválida.");
			return null;
		}

		Date dtVenda = null;
		try {
			dtVenda = new SimpleDateFormat("dd/MM/yyyy").parse(strDtVenda);
		} catch (ParseException e) {
			throw new ViewException(e);
		}

		Date dtPrevistaPagto = null;
		try {
			dtPrevistaPagto = new SimpleDateFormat("dd/MM/yyyy").parse(strDtPrevistaPagto);
		} catch (ParseException e) {
			throw new ViewException(e);
		}

		Double dValorBruto = null;
		// remove tudo o que não for número, ponto ou vírgula
		try {
			dValorBruto = NumberFormat.getNumberInstance(new Locale("pt", "BR")).parse(strValorBruto)
					.doubleValue();
			dValorBruto = Math.abs(dValorBruto);
		} catch (ParseException e) {
			throw new ViewException(e);
		}
		Long entradaOuSaida = dValorBruto < 0 ? 2l : 1l;

		BigDecimal valorBruto = CurrencyUtils.getBigDecimalCurrency(dValorBruto);

		Modo modo = getModoFinder().findBy(9); // "RECEB. CARTÃO CRÉDITO";
		camposLinha.put("modo", modo);

		BandeiraCartao bandeiraCartao = getBandeiraCartaoFinder().findByLabelsAndModo(strDescricao, modo);

		if (bandeiraCartao == null) {
			throw new ViewException("Bandeira de cartão não encontrada");
		}

		camposLinha.put("bandeiraCartao", bandeiraCartao);

		PlanoPagtoCartao planoPagtoCartao = null;

		if (strDescricao.contains("parcelado")) {
			planoPagtoCartao = PlanoPagtoCartao.CREDITO_PARCELADO;
		} else if (strDescricao.contains("crédito")) {
			planoPagtoCartao = PlanoPagtoCartao.CREDITO_30DD;
		}

		camposLinha.put("planoPagtoCartao", planoPagtoCartao);

		String descricao = strDescricao + " - " + resumoOperacao + " - " + numCartao + " - " + comprovanteNSU + " - "
				+ codigoAutorizacao;

		descricao = descricao.replace("\n", "").replace("\t", "").replace("\r", "");

		camposLinha.put("descricao", descricao);
		camposLinha.put("dtMoviment", dtVenda);
		camposLinha.put("dtVenctoEfetiva", dtPrevistaPagto);
		camposLinha.put("valor", valorBruto); // VALOR_BRUTO
		camposLinha.put("valorTotal", valorBruto);
		camposLinha.put("entradaOuSaida", entradaOuSaida);

		camposLinha.put("categoriaCodigo", 101l);

		return camposLinha;
	}

	/**
	 * Importa uma linha de extrato da Rede Card de cartões de CRÉDITO.
	 *
	 * É quase igual ao débito, muda pouca coisa nas colunas.
	 *
	 * @param linha
	 * @return
	 * @throws ViewException
	 */
	private Map<String, Object> importLinhaExtratoCieloCreditoNovo(int numLinha) throws ViewException {

		String linha = getLinhas().get(numLinha);

		Map<String, Object> camposLinha = new HashMap<String, Object>();

		String[] campos = linha.split("\t");

		/**
		 * 0 Data de pagamento
		 * 1 Data de venda
		 * 2 Forma de pagamento
		 * 3 NSU
		 * 4 Número do cartão
		 * 5 Valor bruto
		 * 6 Status
		 * 7 Valor líquido
		 * 8 TID
		 * 9 Taxa
		 * 10 Número do EC
		 * 11 Bandeira
		 */

		String strDtVenda = campos[1].trim();
		String strDtPrevistaPagto = campos[0].trim();
		String strNumeroDoCartao = campos[4].trim();
		String strTID = campos[8].trim();
		String strValorBruto = campos[5].replaceAll("[^0-9\\.\\,]", "");

		String strCodigoAutorizacao = campos[10].trim();

		String strBandeira = campos[11].trim();
		String strFormaDePagamento = campos[2].trim();

		if (campos.length < 9) {
			logger.warn("Linha inválida.");
			return null;
		}

		Date dtVenda = null;
		try {
			dtVenda = new SimpleDateFormat("dd/MM/yyyy").parse(strDtVenda);
		} catch (ParseException e) {
			throw new ViewException(e);
		}

		Date dtPrevistaPagto = null;
		try {
			dtPrevistaPagto = new SimpleDateFormat("dd/MM/yyyy").parse(strDtPrevistaPagto);
		} catch (ParseException e) {
			throw new ViewException(e);
		}

		Double dValorBruto = null;
		// remove tudo o que não for número, ponto ou vírgula
		try {
			dValorBruto = NumberFormat.getNumberInstance(new Locale("pt", "BR")).parse(strValorBruto)
					.doubleValue();
			dValorBruto = Math.abs(dValorBruto);
		} catch (ParseException e) {
			throw new ViewException(e);
		}
		Long entradaOuSaida = dValorBruto < 0 ? 2l : 1l;

		BigDecimal valorBruto = CurrencyUtils.getBigDecimalCurrency(dValorBruto);

		Modo modo = getModoFinder().findBy(9); // "RECEB. CARTÃO CRÉDITO";
		camposLinha.put("modo", modo);

		BandeiraCartao bandeiraCartao = getBandeiraCartaoFinder().findByLabelsAndModo(strBandeira, modo);

		if (bandeiraCartao == null) {
			throw new ViewException("Bandeira de cartão não encontrada");
		}

		camposLinha.put("bandeiraCartao", bandeiraCartao);

		PlanoPagtoCartao planoPagtoCartao = null;

		if (strFormaDePagamento.toLowerCase().contains("parc")) {
			planoPagtoCartao = PlanoPagtoCartao.CREDITO_PARCELADO;
		} else {
			planoPagtoCartao = PlanoPagtoCartao.CREDITO_30DD;
		}

		camposLinha.put("planoPagtoCartao", planoPagtoCartao);

		String descricao = strFormaDePagamento + " - " + strBandeira + " - " + strNumeroDoCartao + " ("
				+ strCodigoAutorizacao + ") " + strTID;

		descricao = descricao.replace("\n", "").replace("\t", "").replace("\r", "");

		camposLinha.put("descricao", descricao);
		camposLinha.put("dtMoviment", dtVenda);
		camposLinha.put("dtVenctoEfetiva", dtPrevistaPagto);
		camposLinha.put("valor", valorBruto); // VALOR_BRUTO
		camposLinha.put("valorTotal", valorBruto);
		camposLinha.put("entradaOuSaida", entradaOuSaida);

		camposLinha.put("categoriaCodigo", 101l);

		return camposLinha;
	}

	/**
	 * Importa uma linha de extrato da Stone de cartões de CRÉDITO.
	 *
	 * @param linha
	 * @return
	 * @throws ViewException
	 */
	private Map<String, Object> importLinhaExtratoStoneCredito(int numLinha) throws ViewException {

		String linha = getLinhas().get(numLinha);

		Map<String, Object> camposLinha = new HashMap<String, Object>();

		String[] campos = linha.split("\t");

		/**
		 * 0 CATEGORIA
		 * 1 HORA DA VENDA
		 * 2 DATA DE VENCIMENTO
		 * 3 TIPO
		 * 4 Nº DA PARCELA
		 * 5 QTD DE PARCELAS
		 * 6 BANDEIRA
		 * 7 STONE ID
		 * 8 N° CARTÃO
		 * 9 VALOR BRUTO
		 * 10 VALOR LÍQUIDO
		 * 11 ÚLTIMO STATUS
		 * 12 DATA DO ÚLTIMO STATUS
		 * 
		 */

		String strDtVenda = campos[1].trim().substring(0, 10);
		String strDtPrevistaPagto = campos[2].trim().substring(0, 10);
		String tipo = campos[3].trim();
		String bandeira = campos[6];
		String strDescricao = campos[0].trim() + " - " + tipo + " - " + bandeira + " (" + campos[4].trim() + "/"
				+ campos[5].trim() + ") " + campos[8].trim();
		String strValorBruto = campos[9].replaceAll("[^0-9\\.\\,]", "");

		if (campos.length < 12) {
			logger.warn("Linha inválida.");
			return null;
		}

		Date dtVenda = null;
		try {
			dtVenda = new SimpleDateFormat("dd/MM/yyyy").parse(strDtVenda);
		} catch (ParseException e) {
			throw new ViewException(e);
		}

		Date dtPrevistaPagto = null;
		try {
			dtPrevistaPagto = new SimpleDateFormat("dd/MM/yyyy").parse(strDtPrevistaPagto);
		} catch (ParseException e) {
			throw new ViewException(e);
		}

		Double dValorBruto = null;
		// remove tudo o que não for número, ponto ou vírgula
		try {
			dValorBruto = NumberFormat.getNumberInstance(new Locale("pt", "BR")).parse(strValorBruto)
					.doubleValue();
			dValorBruto = Math.abs(dValorBruto);
		} catch (ParseException e) {
			throw new ViewException(e);
		}
		Long entradaOuSaida = dValorBruto < 0 ? 2l : 1l;

		BigDecimal valorBruto = CurrencyUtils.getBigDecimalCurrency(dValorBruto);

		Modo modo;
		if (tipo.toUpperCase().contains("CRÉDITO")) {
			modo = getModoFinder().findBy(9);
		} else {
			modo = getModoFinder().findBy(10);
		}
		camposLinha.put("modo", modo);

		BandeiraCartao bandeiraCartao = getBandeiraCartaoFinder().findByLabelsAndModo(bandeira, modo);

		if (bandeiraCartao == null) {
			throw new ViewException("Bandeira de cartão não encontrada");
		}

		camposLinha.put("bandeiraCartao", bandeiraCartao);

		PlanoPagtoCartao planoPagtoCartao = PlanoPagtoCartao.DEBITO;

		if (strDescricao.contains("parcelado")) {
			planoPagtoCartao = PlanoPagtoCartao.CREDITO_PARCELADO;
		} else if (strDescricao.contains("crédito")) {
			planoPagtoCartao = PlanoPagtoCartao.CREDITO_30DD;
		}

		camposLinha.put("planoPagtoCartao", planoPagtoCartao);

		camposLinha.put("descricao", strDescricao);
		camposLinha.put("dtMoviment", dtVenda);
		camposLinha.put("dtVenctoEfetiva", dtPrevistaPagto);
		camposLinha.put("valor", valorBruto); // VALOR_BRUTO
		camposLinha.put("valorTotal", valorBruto);
		camposLinha.put("entradaOuSaida", entradaOuSaida);

		camposLinha.put("categoriaCodigo", 101l);

		return camposLinha;
	}

	/**
	 * Importa uma linha de extrato da Stone de cartões de DÉBITO.
	 *
	 * @param linha
	 * @return
	 * @throws ViewException
	 */
	private Map<String, Object> importLinhaExtratoStoneDebito(int numLinha) throws ViewException {

		/**
		 * 0 HORA DA VENDA
		 * 1 TIPO
		 * 2 BANDEIRA
		 * 3 MEIO DE CAPTURA
		 * 4 STONE ID
		 * 5 VALOR BRUTO
		 * 6 VALOR LÍQUIDO
		 * 7 N° CARTÃO
		 * 8 SERIAL NUMBER
		 * 9 ÚLTIMO STATUS
		 * 10 DATA DO ÚLTIMO STATUS
		 */

		String linha = getLinhas().get(numLinha);

		Map<String, Object> camposLinha = new HashMap<String, Object>();

		String[] campos = linha.split("\t");

		if (campos.length < 9) {
			logger.warn("Linha inválida.");
			return null;
		}

		Date dtVenda = null;
		try {
			dtVenda = new SimpleDateFormat("dd/MM/yyyy").parse(campos[0]);
		} catch (ParseException e) {
			throw new ViewException(e);
		}

		Double dValorBruto = null;
		String strValorBruto = campos[5].replaceAll("[^0-9\\.\\,]", "");
		try {
			dValorBruto = NumberFormat.getNumberInstance(new Locale("pt", "BR")).parse(strValorBruto)
					.doubleValue();
			dValorBruto = Math.abs(dValorBruto);
		} catch (ParseException e) {
			throw new ViewException(e);
		}
		Long entradaOuSaida = dValorBruto < 0 ? 2l : 1l;

		BigDecimal valorBruto = CurrencyUtils.getBigDecimalCurrency(dValorBruto);

		Modo modo = getModoFinder().findBy(10); // "RECEB. CARTÃO DÉBITO";
		camposLinha.put("modo", modo);

		String bandeira = campos[2].trim();
		BandeiraCartao bandeiraCartao = getBandeiraCartaoFinder().findByLabelsAndModo(bandeira, modo);
		if (bandeiraCartao == null) {
			throw new ViewException("Bandeira de cartão não encontrada");
		}
		camposLinha.put("bandeiraCartao", bandeiraCartao);

		String descricao = campos[1] + " - " + campos[4] + " - " + campos[7];
		descricao = descricao.replace("\n", "").replace("\t", "").replace("\r", "");

		PlanoPagtoCartao planoPagtoCartao = PlanoPagtoCartao.DEBITO;
		camposLinha.put("planoPagtoCartao", planoPagtoCartao);

		camposLinha.put("descricao", descricao);
		camposLinha.put("dtMoviment", dtVenda);
		camposLinha.put("dtVenctoEfetiva", dtVenda);
		camposLinha.put("valor", valorBruto); // VALOR_BRUTO
		camposLinha.put("valorTotal", valorBruto);
		camposLinha.put("entradaOuSaida", entradaOuSaida);
		camposLinha.put("categoriaCodigo", 199l);

		return camposLinha;

	}

	/**
	 * Tipos de extratos possíveis.
	 *
	 * @author Carlos Eduardo Pauluk
	 */
	public enum TipoExtrato {
		EXTRATO_SIMPLES("EXTRATO SIMPLES"),
		EXTRATO_GRUPO_MOVIMENTACOES("EXTRATO GRUPO DE MOVIMENTAÇÕES"),
		EXTRATO_COMPRA_BNDES_BB("EXTRATO COMPRA BNDES BB"),
		EXTRATO_RDCARD_CREDITO("EXTRATO RDCARD - CRÉDITO"),
		EXTRATO_RDCARD_DEBITO("EXTRATO RDCARD - DÉBITO"),
		EXTRATO_MODERNINHA_DEBITO("EXTRATO MODERNINHA - DÉBITO"),
		EXTRATO_CIELO_CREDITO("EXTRATO CIELO - CRÉDITO"),
		EXTRATO_CIELO_DEBITO("EXTRATO CIELO - DÉBITO"),
		EXTRATO_CIELO_CREDITO_NOVO("EXTRATO CIELO - CRÉDITO (NOVO)"),
		EXTRATO_CIELO_DEBITO_NOVO("EXTRATO CIELO - DÉBITO (NOVO)"),
		EXTRATO_STONE_CREDITO("EXTRATO STONE - CRÉDITO"),
		EXTRATO_STONE_DEBITO("EXTRATO STONE - DÉBITO");

		private String label;

		private TipoExtrato(String label) {
			this.label = label;
		}

		public String getLabel() {
			return label;
		}
	}

	public MovimentacaoFinder getMovimentacaoFinder() {
		return movimentacaoFinder;
	}

	public void setMovimentacaoFinder(MovimentacaoFinder movimentacaoFinder) {
		this.movimentacaoFinder = movimentacaoFinder;
	}

	public RegraImportacaoLinhaFinder getRegraImportacaoLinhaFinder() {
		return regraImportacaoLinhaFinder;
	}

	public void setRegraImportacaoLinhaFinder(RegraImportacaoLinhaFinder regraImportacaoLinhaFinder) {
		this.regraImportacaoLinhaFinder = regraImportacaoLinhaFinder;
	}

	public GrupoFinder getGrupoFinder() {
		return grupoFinder;
	}

	public void setGrupoFinder(GrupoFinder grupoFinder) {
		this.grupoFinder = grupoFinder;
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

	public BancoFinder getBancoFinder() {
		return bancoFinder;
	}

	public void setBancoFinder(BancoFinder bancoFinder) {
		this.bancoFinder = bancoFinder;
	}

	public BandeiraCartaoFinder getBandeiraCartaoFinder() {
		return bandeiraCartaoFinder;
	}

	public void setBandeiraCartaoFinder(BandeiraCartaoFinder bandeiraCartaoFinder) {
		this.bandeiraCartaoFinder = bandeiraCartaoFinder;
	}

	public OperadoraCartaoFinder getOperadoraCartaoFinder() {
		return operadoraCartaoFinder;
	}

	public void setOperadoraCartaoFinder(OperadoraCartaoFinder operadoraCartaoFinder) {
		this.operadoraCartaoFinder = operadoraCartaoFinder;
	}

	public DiaUtilFinder getDiaUtilFinder() {
		return diaUtilFinder;
	}

	public void setDiaUtilFinder(DiaUtilFinder diaUtilFinder) {
		this.diaUtilFinder = diaUtilFinder;
	}

	public MovimentacaoBusiness getMovimentacaoBusiness() {
		return movimentacaoBusiness;
	}

	public void setMovimentacaoBusiness(MovimentacaoBusiness movimentacaoBusiness) {
		this.movimentacaoBusiness = movimentacaoBusiness;
	}

	public ModoFinder getModoFinder() {
		return modoFinder;
	}

	public void setModoFinder(ModoFinder modoFinder) {
		this.modoFinder = modoFinder;
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

	public CarteiraFinder getCarteiraFinder() {
		return carteiraFinder;
	}

	public void setCarteiraFinder(CarteiraFinder carteiraFinder) {
		this.carteiraFinder = carteiraFinder;
	}

	public List<Movimentacao> getMovimentacaoImportadas() {
		return movimentacaoImportadas;
	}

	public void setMovimentacaoImportadas(List<Movimentacao> movimentacaoImportadas) {
		this.movimentacaoImportadas = movimentacaoImportadas;
	}

	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	public MovimentacaoImporter getThiz() {
		if (thiz == null) {
			thiz = (MovimentacaoImporter) getBeanFactory().getBean("movimentacaoImporter");
		}
		return thiz;
	}

	public List<String> getLinhas() {
		return linhas;
	}

	public void setLinhas(List<String> linhas) {
		this.linhas = linhas;
	}

	public List<Integer> getLinhasComplementares() {
		if (linhasComplementares == null) {
			linhasComplementares = new ArrayList<Integer>();
		}
		return linhasComplementares;
	}

	public void setLinhasComplementares(List<Integer> linhasComplementares) {
		this.linhasComplementares = linhasComplementares;
	}

	public List<Movimentacao> getMovs101JaImportadas() {
		return movs101JaImportadas;
	}

	public void setMovs101JaImportadas(List<Movimentacao> movs101JaImportadas) {
		this.movs101JaImportadas = movs101JaImportadas;
	}

}
