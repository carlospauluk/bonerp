package com.bonsucesso.erp.estoque.relatorios.view;



import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.estoque.business.HistoricoPrevisaoCompraBusiness;
import com.bonsucesso.erp.estoque.business.VendaResultsBusiness;
import com.bonsucesso.erp.estoque.data.FornecedorFinder;
import com.bonsucesso.erp.estoque.data.PedidoCompraFinder;
import com.bonsucesso.erp.estoque.data.ProdutoFinder;
import com.bonsucesso.erp.estoque.data.SubdeptoFinder;
import com.bonsucesso.erp.estoque.model.Fornecedor;
import com.bonsucesso.erp.estoque.model.Produto;
import com.bonsucesso.erp.estoque.model.Subdepartamento;
import com.bonsucesso.erp.estoque.results.model.ListHistoricoPrevisaoCompra;
import com.ocabit.base.view.StoredViewInfoHandler;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;
import com.ocabit.utils.calendar.CalendarUtil;
import com.ocabit.utils.currency.CurrencyUtils;


/**
 * View para previsões de compra.
 * 
 * 
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("previsaoCompraView2")
@Scope("view")
public class PrevisaoCompraView2 implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7242454086432026430L;

	protected static Logger logger = Logger.getLogger(PrevisaoCompraView2.class);

	@Autowired
	private HistoricoPrevisaoCompraBusiness gerarHistoricoPrevisaoCompra;

	/**
	 * Parâmetros de entrada:
	 */

	private List<Fornecedor> fornecedores;

	private List<Fornecedor> fornecedoresSel;

	private List<Subdepartamento> subdeptos;

	private List<Subdepartamento> subdeptosSel;

	// Pode ser hoje ou futura
	private Date dtBase = new Date();

	private Date dtEntrega;

	private int qtdeMesesPrever = 12;

	private Integer[] anosAnterioresConsiderar;

	// informa se o relatório deve ser agrupado por subdepto, ou exibido cada produto em separado
	private boolean detalhado = false;

	/**
	 * 'Chave'
	 */
	private String ordenarPor = "Chave";

	// Se for 'true', então a ordem é feita somente dentro dos itens da chave.
	private boolean ordenarApenasIntern = false;

	// >>>>>>>>>> Montados

	// Lista dos mesesAnos exibidos na previsão (dtBase + qtdeMesesPrever) (ex.: "nov/16, dez/16, jan/17")
	private List<Date> mesesAnosPrevisao;

	// ------------------------------------------------

	private ListHistoricoPrevisaoCompra listHPC;

	@Autowired
	private SubdeptoFinder subdeptoFinder;

	@Autowired
	private FornecedorFinder fornecedorFinder;

	@Autowired
	private ProdutoFinder produtoFinder;

	@Autowired
	private PedidoCompraFinder pedidoCompraFinder;

	private List<PrevisaoCompraRecord> listPorFornecedor;

	private List<PrevisaoCompraRecord> listPorSubdepto;

	@Autowired
	private VendaResultsBusiness vendaResultsBusiness;

	/**
	 * 'F': Fornecedor.
	 * 'S': Subdepto.
	 */
	private String tipoRel = "F";

	private Integer progress = 0;

	private List<Integer> anosAnterioresParaSelecionar;

	@Autowired
	private StoredViewInfoHandler storedViewInfoHandler;

	protected String[] camposStored = new String[] { "fornecedoresSel", "subdeptosSel", "dtEntrega",
			"ordenarPor", "ordenarApenasIntern", "detalhado",
			"qtdeMesesPrever", "anosAnterioresConsiderar" };

	@PostConstruct
	public void init() {
		try {
			getStoredViewInfoHandler().processStoredViewInfo("previsaoCompraView", this);
		} catch (ViewException e) {
			JSFUtils.addErrorMsg("Erro ao inicializar.");
			JSFUtils.addHandledException(e);
			logger.error(e);
		}
	}

	/**
	 * Método principal.
	 * 
	 */
	public void gerar() {

		if (CalendarUtil.isPassado(getDtBase())) {
			JSFUtils.addErrorMsg("Dt Base deve ser hoje ou no futuro.");
			finishProgress();
			return;
		}
		if (CalendarUtil.isPassado(getDtEntrega())) {
			JSFUtils.addErrorMsg("Dt Entrega deve ser hoje ou no futuro.");
			finishProgress();
			return;
		}

		long ini = System.currentTimeMillis();

		getStoredViewInfoHandler().store("previsaoCompraView", this, camposStored);

		setListPorFornecedor(null);
		setListPorSubdepto(null);
		setMesesAnosPrevisao(null);

		try {
			setListHPC(getGerarHistoricoPrevisaoCompra().returnList(getFornecedoresSel(), getSubdeptosSel()));
		} catch (ViewException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//		switch (getTipoRel()) {
		//			case "F":
		//				gerarPorFornecedor();
		//				break;
		//			case "S":
		//				gerarPorSubdepto();
		//				break;
		//		}
		//
		//		try {
		//			ordenarEBuildMapList();
		//		} catch (ViewException e) {
		//			JSFUtils.addHandledException(e);
		//		}

		finishProgress();

		logger.debug("Total: " + (System.currentTimeMillis() - ini) / 1000.0);
		logger.debug("Finalizando...");
	}

	/**
	 * Método principal.
	 */
	public void gerarPorFornecedor() {

		try {
			// Primeiro monta a tabela de qtde vendas em cada mês do passado que será utilizado para a previsão 
			// (na verdade pega meses a mais, visto que faz o intervalo entre o primeiro mes e o ultimo, mas por via de facilidade, não tem problema)
			Map<Fornecedor, Map<Subdepartamento, Map<Date, BigDecimal>>> vendasPorMeses;
			try {
				Arrays.sort(getAnosAnterioresConsiderar());

				int mes = CalendarUtil.getCalendar(getDtBase()).get(Calendar.MONTH);

				Date ini = CalendarUtil.getDate(1, mes, getAnosAnterioresConsiderar()[0]);
				Date fim = CalendarUtil.getDate(1, mes + getQtdeMesesPrever()
						- 1, getAnosAnterioresConsiderar()[getAnosAnterioresConsiderar().length - 1]);
				vendasPorMeses = getVendaResultsBusiness()
						.buildVendasResultsByFornecedor(ini, fim);
			} catch (ViewException e1) {
				JSFUtils.addHandledException(e1);
				return;
			}

			double totalForns = getFornecedoresSel().size();
			double j = 0;
			setProgress(0);

			// Para cada fornecedor selecionado
			for (Fornecedor f : getFornecedoresSel()) {

				PrevisaoCompraRecord rForn = new PrevisaoCompraRecord(f);
				getListPorFornecedor().add(rForn);

				j++;
				Double progress = j / totalForns * 100.0;
				if (progress > 99.0) {
					progress = 99.0;
				}
				setProgress(progress.intValue());
				logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> PROGRESS: " + progress.intValue());

				logger.debug("Gerando para " + f.getCodigo() + " - " + f.getPessoa().getNomeFantasia());

				// Para cada subdepto
				for (Subdepartamento sd : getSubdeptosSel()) {

					PrevisaoCompraRecord rSubdepto = new PrevisaoCompraRecord(sd);
					rForn.getFilhos().add(rSubdepto);
					rSubdepto.setEntityId(sd);

					try {
						// FIXME: no caso de dtBase diferente de hoje, como fica????
						// Se o pedido foi entregue parcialmente, como fica?						
						rSubdepto.setQtdePedido(getPedidoCompraFinder().findQtdePedidaBy(f, sd));
					} catch (ViewException e) {
						String err = "Erro ao obter a qtdePedido";
						logger.error(err, e);
						// throw new IllegalStateException(err, e);
					}

					// Se for detalhado, monta os registros para cada produto
					if (isDetalhado()) {
						List<Produto> produtos = getProdutoFinder().findAtuaisBy(f, sd);
						montaDetalhado(produtos, rSubdepto);
					}

					// Obtém o saldo atual. Tem que ser a última coisa a ser feita, pois vai utilizar os dados obtidos na previsão.
					try {
						if (CalendarUtil.isFuturo(getDtBase())) {
							// se a dtBase é no futuro, a lógica para o saldoAtual é diferente

						} else {
							BigDecimal saldoAtual = getProdutoFinder().findQtdeBy(f, sd);
							rSubdepto.setSaldoNaDtBase(saldoAtual);
						}
					} catch (ViewException e) {
						String err = "Erro ao obter o saldoAtual";
						logger.error(err, e);
						throw new IllegalStateException(err, e);
					}

				}

			}
		} catch (Exception e) {
			logger.error(e);
		}
		logger.debug("Finalizando gerarPorFornecedor() ...");

	}

	/**
	 * 
	 * @param f
	 * @param sd
	 * @return
	 * @throws ViewException
	 */
	public void montaDetalhado(List<Produto> produtos, PrevisaoCompraRecord pPai) throws ViewException {
		try {

			for (Produto p : produtos) {

				// Para testes, só vou usar esse produto
				if (p.getReduzidoEkt() != 10938)
					continue;

				logger.debug(p.getReduzidoEkt() + " - " + p.getDescricao());

				PrevisaoCompraRecord rProduto = new PrevisaoCompraRecord();
				pPai.getFilhos().add(rProduto);
				rProduto.setEntityId(p);

				try {
					rProduto.setQtdePedido(getPedidoCompraFinder().findQtdePedidaBy(p));
				} catch (ViewException e) {
					String err = "Erro ao obter a qtdePedido";
					logger.error(err, e);
					// throw new IllegalStateException(err, e);
				}

				// Obtém o saldo atual
				try {
					BigDecimal saldoAtual = getProdutoFinder().findQtdeBy(p);
					rProduto.setSaldoNaDtBase(saldoAtual);
				} catch (ViewException e) {
					String err = "Erro ao obter o saldoAtual";
					logger.error(err, e);
					throw new IllegalStateException(err, e);
				}

				int primeiroMes = CalendarUtil.getCalendar(getMesesAnosPrevisao().get(0)).get(Calendar.MONTH) + 1;

				int diaDaDtBase = CalendarUtil.getCalendar(getDtBase()).get(Calendar.DAY_OF_MONTH);

				for (Date mesAnoPrevisao : getMesesAnosPrevisao()) {

					DadosVendas dadosVenda = new DadosVendas();
					rProduto.getDadosVendas().add(dadosVenda);
					int mes = CalendarUtil.getCalendar(mesAnoPrevisao).get(Calendar.MONTH) + 1; // corrige para bater com o numeral do mês (melhor de visualizar nos debugs)
					dadosVenda.setMesPrevisao(mes);

					rProduto.getMesesEmOrdem().add(mes);

					for (Integer ano : getAnosAnterioresConsiderar()) {
						Date mesAno = CalendarUtil.getDate(01, mes, ano);

						BigDecimal totalVendido = getVendaResultsBusiness().getVendaFinder()
								.findQtdeItensVendidosBy(p, mesAno);

						if (totalVendido != null) {

							// Para os mesesAnos que são do primeiro mês da previsão (ex.: nov/2014, nov/2015, nov/2016)
							if (mes == primeiroMes) {

								Date dtParaPrimeiroMes = CalendarUtil.getDate(diaDaDtBase, mes, ano);
								Long diasRestantes = CalendarUtil.difBetweenDates(dtParaPrimeiroMes, CalendarUtil
										.ultimoDiaNoMesAno(dtParaPrimeiroMes));
								BigDecimal bdDiasRestantes = CurrencyUtils
										.getBigDecimalScaled(diasRestantes.doubleValue(), 3);
								Long diasNoMes = CalendarUtil.getQtdeDiasNoMes(dtParaPrimeiroMes);
								BigDecimal bdDiasNoMes = CurrencyUtils.getBigDecimalScaled(diasNoMes.doubleValue(), 3);

								BigDecimal totalQueVaiVenderAteFinalDoMes = totalVendido
										.multiply(bdDiasRestantes.divide(bdDiasNoMes, RoundingMode.HALF_UP));

								//								BigDecimal totalQueJaVendeu = getVendaResultsBusiness().getVendaFinder()
								//										.findQtdeItensVendidosBy(p, getDtBase());
								//
								//								BigDecimal totalPrevisaoPrimeiroMes = totalQueVaiVenderAteFinalDoMes
								//										.add(totalQueJaVendeu);
								//								
								//								totalVendido = totalPrevisaoPrimeiroMes;

								totalVendido = totalQueVaiVenderAteFinalDoMes;
							}

							totalVendido = totalVendido.setScale(0, RoundingMode.HALF_UP);
						}
						dadosVenda.getVendasPassado().put(ano, totalVendido);
					}
				}

				logger.debug("Finalizando para rProduto...");
			}

		} catch (ViewException e) {
			throw new ViewException("Erro montaDetalhado", e);
		}
	}

	public void debugMapMesAnoBigDecimal(Map<Date, BigDecimal> map) {
		List<Date> keys = new ArrayList<>(map.keySet());
		Collections.sort(keys);
		for (Date d : keys) {
			logger.debug(new SimpleDateFormat("MM/yyyy").format(d) + " >> " + map.get(d));
		}
	}

	public void gerarPorSubdepto() {

	}

	public void selecionarFornecedores(boolean todos) {
		if (todos) {
			getFornecedoresSel().addAll(getFornecedores());
		} else {
			setFornecedoresSel(null);
		}

	}

	public void selecionarSubdeptos(boolean todos) {
		if (todos) {
			getSubdeptosSel().addAll(getSubdeptos());
		} else {
			setSubdeptosSel(null);
		}
	}

	/**
	 * Método chamado para ordenar os mapList conforme a seleção na tela. É chamado também ao trocar o tipo de ordenação na tela.
	 * 
	 * @throws ViewException
	 */
	public void ordenarEBuildMapList() throws ViewException {

		logger.debug("finalizando ordenarEBuildMapList()");
	}

	/**
	 * Ordena a lista de acordo com a seleção na tela.
	 */
	public void ordenarList() {

	}

	public void finishProgress() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			logger.error(e);
		}
		logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> PROGRESS: " + progress.intValue());
		setProgress(0);
		JSFUtils.execute("PF('dlgStatus').hide()");
		JSFUtils.execute("PF('pbAjax').cancel()");
	}

	public SubdeptoFinder getSubdeptoFinder() {
		return subdeptoFinder;
	}

	public void setSubdeptoFinder(SubdeptoFinder subdeptoFinder) {
		this.subdeptoFinder = subdeptoFinder;
	}

	public FornecedorFinder getFornecedorFinder() {
		return fornecedorFinder;
	}

	public void setFornecedorFinder(FornecedorFinder fornecedorFinder) {
		this.fornecedorFinder = fornecedorFinder;
	}

	public ProdutoFinder getProdutoFinder() {
		return produtoFinder;
	}

	public void setProdutoFinder(ProdutoFinder produtoFinder) {
		this.produtoFinder = produtoFinder;
	}

	public Date getDtBase() {
		dtBase = new Date();
		return dtBase;
	}

	public void setDtBase(Date dtBase) {
		// Não pode ser setada para o passado
		if (!CalendarUtil.isHojeOuFuturo(dtBase)) {
			dtBase = CalendarUtil.zeroDate(new Date());
		}
		this.dtBase = CalendarUtil.zeroDate(dtBase);
	}

	public Date getDtEntrega() {
		return dtEntrega;
	}

	public void setDtEntrega(Date dtEntrega) {
		this.dtEntrega = dtEntrega;
	}

	public List<Fornecedor> getFornecedores() {
		if (fornecedores == null) {
			try {
				fornecedores = getFornecedorFinder().findAtuaisByTipo("ESTOQUE");
			} catch (ViewException e) {
				JSFUtils.addHandledException(e);
			}
		}
		return fornecedores;
	}

	public void setFornecedores(List<Fornecedor> fornecedores) {
		this.fornecedores = fornecedores;
	}

	public List<Fornecedor> getFornecedoresSel() {
		return fornecedoresSel;
	}

	public void setFornecedoresSel(List<Fornecedor> fornecedoresSel) {
		this.fornecedoresSel = fornecedoresSel;
	}

	public List<Subdepartamento> getSubdeptos() {
		if (subdeptos == null) {
			try {
				subdeptos = getSubdeptoFinder().findAll("codigo");
			} catch (ViewException e) {
				JSFUtils.addHandledException(e);
			}
		}
		return subdeptos;
	}

	public void setSubdeptos(List<Subdepartamento> subdeptos) {
		this.subdeptos = subdeptos;
	}

	public List<Subdepartamento> getSubdeptosSel() {
		return subdeptosSel;
	}

	public void setSubdeptosSel(List<Subdepartamento> subdeptosSel) {
		this.subdeptosSel = subdeptosSel;
	}

	public int getQtdeMesesPrever() {
		return qtdeMesesPrever;
	}

	public void setQtdeMesesPrever(int qtdeMesesPrever) {
		if (qtdeMesesPrever < 1)
			qtdeMesesPrever = 1;
		else if (qtdeMesesPrever > 12)
			qtdeMesesPrever = 12;
		this.qtdeMesesPrever = qtdeMesesPrever;
	}

	public String getTipoRel() {
		return tipoRel;
	}

	public void setTipoRel(String tipoRel) {
		this.tipoRel = tipoRel;
	}

	public Integer getProgress() {
		return progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}

	public List<Integer> getAnosAnterioresParaSelecionar() {
		if (anosAnterioresParaSelecionar == null) {
			anosAnterioresParaSelecionar = new ArrayList<Integer>();
			int anoAtual = CalendarUtil.getCalendar(new Date()).get(Calendar.YEAR);
			// FIXME: como só importei dados de Jan/2014 pra frente, deixei fixo aqui.
			for (int i = 2014; i < anoAtual; i++) {
				anosAnterioresParaSelecionar.add(i);
			}
		}
		return anosAnterioresParaSelecionar;
	}

	public void setAnosAnterioresParaSelecionar(List<Integer> anosAnterioresParaSelecionar) {
		this.anosAnterioresParaSelecionar = anosAnterioresParaSelecionar;
	}

	public VendaResultsBusiness getVendaResultsBusiness() {
		return vendaResultsBusiness;
	}

	public void setVendaResultsBusiness(VendaResultsBusiness vendaResultsBusiness) {
		this.vendaResultsBusiness = vendaResultsBusiness;
	}

	public Integer[] getAnosAnterioresConsiderar() {
		return anosAnterioresConsiderar;
	}

	public void setAnosAnterioresConsiderar(Integer[] anosAnterioresConsiderar) {
		this.anosAnterioresConsiderar = anosAnterioresConsiderar;
	}

	public List<Date> getMesesAnosPrevisao() {
		if (mesesAnosPrevisao == null) {
			// Monta a lista com base nos parâmetros de entrada
			mesesAnosPrevisao = new ArrayList<Date>();
			for (int i = 0; i < getQtdeMesesPrever(); i++) {
				mesesAnosPrevisao.add(CalendarUtil.primeiroDiaNoMesAno(CalendarUtil.incMes(getDtBase(), i)));
			}
		}
		return mesesAnosPrevisao;
	}

	public void setMesesAnosPrevisao(List<Date> mesesAnosPrevisao) {
		this.mesesAnosPrevisao = mesesAnosPrevisao;
	}

	//	public List<Date> getMesesAnosPassadoConsiderar() {
	//		if (mesesAnosPassadoConsiderar == null) {
	//			// Monta a lista com base nos parâmetros de entrada
	//			mesesAnosPassadoConsiderar = new ArrayList<Date>();
	//			
	//			Integer mesInicioPrevisoes = CalendarUtil.getCalendar(getDtBase()).get(Calendar.MONTH) + 1;
	//			
	//			for (Object oAno : getAnosAnterioresConsiderar()) {
	//				Integer ano = (Integer) oAno;
	//				Date ini = CalendarUtil.getDate(1, mesInicioPrevisoes, ano);
	//				Date fim = CalendarUtil.primeiroDiaNoMesAno(CalendarUtil.incMes(ini, getQtdeMesesPrever()-1)); 
	//				List<Date> l = CalendarUtil.buildMesAnoList(ini, fim);
	//				mesesAnosPassadoConsiderar.addAll(l);
	//			}
	//			Collections.sort(mesesAnosPassadoConsiderar);
	//		}
	//		return mesesAnosPassadoConsiderar;
	//	}
	//
	//	public void setMesesAnosPassadoConsiderar(List<Date> mesesAnosPassadoConsiderar) {
	//		this.mesesAnosPassadoConsiderar = mesesAnosPassadoConsiderar;
	//	}

	public PedidoCompraFinder getPedidoCompraFinder() {
		return pedidoCompraFinder;
	}

	public void setPedidoCompraFinder(PedidoCompraFinder pedidoCompraFinder) {
		this.pedidoCompraFinder = pedidoCompraFinder;
	}

	public StoredViewInfoHandler getStoredViewInfoHandler() {
		return storedViewInfoHandler;
	}

	public void setStoredViewInfoHandler(StoredViewInfoHandler storedViewInfoHandler) {
		this.storedViewInfoHandler = storedViewInfoHandler;
	}

	public boolean isDetalhado() {
		return detalhado;
	}

	public void setDetalhado(boolean detalhado) {
		this.detalhado = detalhado;
	}

	public String getOrdenarPor() {
		return ordenarPor;
	}

	public void setOrdenarPor(String ordenarPor) {
		this.ordenarPor = ordenarPor;
	}

	public boolean isOrdenarApenasIntern() {
		return ordenarApenasIntern;
	}

	public void setOrdenarApenasIntern(boolean ordenarApenasIntern) {
		this.ordenarApenasIntern = ordenarApenasIntern;
	}

	public List<PrevisaoCompraRecord> getListPorFornecedor() {
		if (listPorFornecedor == null) {
			listPorFornecedor = new ArrayList<PrevisaoCompraRecord>();
		}
		return listPorFornecedor;
	}

	public void setListPorFornecedor(List<PrevisaoCompraRecord> listPorFornecedor) {
		this.listPorFornecedor = listPorFornecedor;
	}

	public List<PrevisaoCompraRecord> getListPorSubdepto() {
		if (listPorSubdepto == null) {
			listPorSubdepto = new ArrayList<PrevisaoCompraRecord>();
		}
		return listPorSubdepto;
	}

	public void setListPorSubdepto(List<PrevisaoCompraRecord> listPorSubdepto) {
		this.listPorSubdepto = listPorSubdepto;
	}

	public HistoricoPrevisaoCompraBusiness getGerarHistoricoPrevisaoCompra() {
		return gerarHistoricoPrevisaoCompra;
	}

	public void setGerarHistoricoPrevisaoCompra(HistoricoPrevisaoCompraBusiness gerarHistoricoPrevisaoCompra) {
		this.gerarHistoricoPrevisaoCompra = gerarHistoricoPrevisaoCompra;
	}

	public ListHistoricoPrevisaoCompra getListHPC() {
		return listHPC;
	}

	public void setListHPC(ListHistoricoPrevisaoCompra listHPC) {
		this.listHPC = listHPC;
	}

}
