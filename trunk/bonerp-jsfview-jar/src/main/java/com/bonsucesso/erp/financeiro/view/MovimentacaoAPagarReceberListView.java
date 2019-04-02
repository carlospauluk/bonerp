package com.bonsucesso.erp.financeiro.view;



import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.primefaces.event.data.SortEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.financeiro.model.Movimentacao;
import com.bonsucesso.erp.financeiro.model.Status;
import com.ocabit.base.data.filter.FilterData;
import com.ocabit.base.data.filter.FilterType;
import com.ocabit.base.data.filter.FilterUtils;
import com.ocabit.base.data.filter.OrderByParam;
import com.ocabit.base.data.filter.OrderByType;
import com.ocabit.base.view.StoredViewInfoHandler;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;
import com.ocabit.utils.calendar.CalendarUtil;


@Component("movimentacaoAPagarReceberListView")
@Scope("view")
public class MovimentacaoAPagarReceberListView extends MovimentacaoListView {

	/**
	 *
	 */
	private static final long serialVersionUID = -4390246219053193001L;

	protected static Logger logger = Logger.getLogger(MovimentacaoAPagarReceberListView.class);

	/**
	 * Todas as movimentações abertas anteriores a data inicial na tela (fora cheques).
	 */
	private List<Movimentacao> anteriores;

	/**
	 * Todas os cheques anteriores a data inicial na tela.
	 */
	private List<Movimentacao> chequesAnteriores;

	@Autowired
	private ListMaker lmFinanc;

	private String tituloAnteriores;

	private String tituloNoPeriodo;

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private Boolean exibirAnteriores = Boolean.FALSE;

	@Autowired
	private StoredViewInfoHandler storedViewInfoHandler;

	// Armazena quais campos devem ser salvos após salvar uma Movimentação, para setá-los quando for começar uma nova.
	private String[] camposStored = new String[] { "exibirAnteriores" };

	@PostConstruct
	public void init() {
		try {
			getStoredViewInfoHandler().processStoredViewInfo("movimentacaoAPagarReceberListView", this);
		} catch (ViewException e) {
			logger.error(e);
		}
	}

	@Override
	public void handleFiltrosPadrao() {
		// Sempre cheques a compensar ou contas em aberto
		getFiltros().put("status", new Status[] { Status.A_COMPENSAR, Status.ABERTA });

		getFiltros().put("carteiras", getLmFinanc().getCarteiraAbertasValues());
		
		getFiltros().put("categoria.codigoSuper", 2l);

		if (!getFiltros().containsKey("dtIni") || (getFiltros().get("dtIni") == null)) {
			Calendar calDtIni = CalendarUtil.getCalendar(new Date());
			calDtIni.add(Calendar.DAY_OF_MONTH, -3);
			getFiltros().put("dtIni", calDtIni.getTime());
		}
		if (!getFiltros().containsKey("dtFim") || (getFiltros().get("dtFim") == null)) {
			Calendar calDtFim = CalendarUtil.getCalendar(new Date());
			calDtFim.add(Calendar.DAY_OF_MONTH, 1);
			getFiltros().put("dtFim", calDtFim.getTime());
		}

	}

	@Override
	public void pesquisar(boolean stored) {
		super.pesquisar(stored);
		updateAnteriores();
		updateTitulos();
		totalizar();
		getStoredViewInfoHandler().store("movimentacaoAPagarReceberListView", this, camposStored);
	}

	/**
	 * Para não limitar por registros quando a listagem é de extrato.
	 */
	@Override
	public Integer getQtdeRegistros() {
		return null;
	}

	@Override
	public void handleFilterDatas() {
		getFilterDatas().add(new FilterData("status", FilterType.IN));
		getFilterDatas().add(new FilterData("carteiras", FilterType.IN, "carteira"));
		getFilterDatas().add(new FilterData("categoria.codigoSuper", FilterType.EQUALS));
		getFilterDatas().add(new FilterData(new String[] { "dtIni", "dtFim" }, FilterType.BETWEEN, "dtUtil"));
	}

	@Override
	public List<Movimentacao> doFindByFilters() throws ViewException {
		if (!getFiltros().containsKey("dtIni") || (getFiltros().get("dtIni") == null) ||
				!getFiltros().containsKey("dtFim") || (getFiltros().get("dtFim") == null)) {
			throw new ViewException("É necessário informar o período do extrato.");
		}

		List<OrderByParam> orders = new ArrayList<OrderByParam>();
		orders.add(new OrderByParam("carteira.id"));
		orders.add(new OrderByParam("dtUtil"));
		orders.add(new OrderByParam("valorTotal"));
		if ((getOrderByParam() != null) && (getOrderByParam().getField() != null)
				&& !"".equals(getOrderByParam().getField())) {
			orders.add(new OrderByParam(getOrderByParam().getFieldName(), getOrderByParam().getOrderByType()));
		} else {
			orders.add(new OrderByParam("iudt.updated", OrderByType.DESC));
		}

		return getFilterFinder().findByFilters(getFilterDatas(), getQtdeRegistros(), orders);
	}

	@Override
	public void onSort(SortEvent event) {
		super.onSort(event);
		pesquisar();
	}

	public void calcularTotaisSumario(Date dtVenctoEfetiva) {

		logger.debug(">>>>>>>>>>>>>>>>>>>>>> SUMÁRIO PARA :" + dtVenctoEfetiva);

		List<Movimentacao> mHoje = new ArrayList<Movimentacao>();

		for (Movimentacao m : getList()) {
			if (CalendarUtil.isSameDay(m.getDtVenctoEfetiva(), dtVenctoEfetiva)) {
				mHoje.add(m);
			}
		}

		Map<String, BigDecimal> totais = getMovimentacaoBusiness().findTotal(mHoje);

		getTotalizacoes().put("SUMARIO_TOTAL_DIA", totais.get("TOTAL"));

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void updateAnteriores() {
		try {
			Date dtIni = (Date) getFiltros().get("dtIni");
			// no update do JSF ele pode voltar como array
			List carteiras = FilterUtils.convertToList(getFiltros().get("carteiras"));
			List<Movimentacao> todasAnteriores = getFinder().findAbertasAnteriores(dtIni, carteiras);
			
			getMovimentacaoBusiness().handleDescricaoMontada(todasAnteriores);
			
			List<Movimentacao> anteriores = new ArrayList<Movimentacao>();
			List<Movimentacao> chequesAnteriores = new ArrayList<Movimentacao>();
			
			
			
			for (Movimentacao anterior : todasAnteriores) {
				if (anterior.getModo().getModoDeCheque()) {
					chequesAnteriores.add(anterior);
				} else {
					anteriores.add(anterior);
				}
			}
			
			setAnteriores(anteriores);
			setChequesAnteriores(chequesAnteriores);
			
		} catch (Exception e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao pesquisar as movimentações anteriores.");
		}
	}

	public void pesquisarQuinzena() {
		Calendar calDtIni = CalendarUtil.getCalendar(new Date());
		// estamos na primeira ou na segunda quinzena?
		if (calDtIni.get(Calendar.DAY_OF_MONTH) <= 15) {
			getFiltros().put("dtIni", CalendarUtil.primeiroDiaNoMesAno(new Date()));
			calDtIni.set(Calendar.DAY_OF_MONTH, 15);
			getFiltros().put("dtFim", calDtIni.getTime());
		} else {
			calDtIni.set(Calendar.DAY_OF_MONTH, 16);
			getFiltros().put("dtIni", calDtIni.getTime());
			getFiltros().put("dtFim", CalendarUtil.ultimoDiaNoMesAno(new Date()));
		}

		pesquisar();
	}

	@Override
	public void totalizar() {

		getTotalizacoes().put("TOTAL_ABERTAS_ANTERIORES", getMovimentacaoBusiness()
				.findTotal(getAnteriores(), Status.ABERTA).get("TOTAL"));
		getTotalizacoes().put("TOTAL_CHEQUES_ANTERIORES", getMovimentacaoBusiness()
				.findTotal(getAnteriores(), Status.A_COMPENSAR).get("TOTAL"));
		getTotalizacoes().put("TOTAL_GERAL_ANTERIORES", getMovimentacaoBusiness().findTotal(getAnteriores())
				.get("TOTAL"));

		getTotalizacoes().put("TOTAL_ABERTAS_NO_PERIODO", getMovimentacaoBusiness().findTotal(getList(), Status.ABERTA)
				.get("TOTAL"));
		getTotalizacoes().put("TOTAL_CHEQUES_NO_PERIODO", getMovimentacaoBusiness()
				.findTotal(getList(), Status.A_COMPENSAR).get("TOTAL"));
		getTotalizacoes().put("TOTAL_GERAL_NO_PERIODO", getMovimentacaoBusiness().findTotal(getList()).get("TOTAL"));

	}

	public void updateTitulos() {
		Date dtIni = (Date) getFiltros().get("dtIni");
		Date dtFim = (Date) getFiltros().get("dtFim");
		tituloAnteriores = "Anteriores a " + sdf.format(dtIni);

		if (CalendarUtil.dataMaiorQueData(dtFim, dtIni)) {
			tituloNoPeriodo = "Entre " + sdf.format(dtIni) + " e " + sdf.format(dtFim);
		} else {
			tituloNoPeriodo = "Em " + sdf.format(dtIni);

		}

	}

	public List<Movimentacao> getAnteriores() {
		return anteriores;
	}

	public void setAnteriores(List<Movimentacao> anteriores) {
		this.anteriores = anteriores;
	}

	public List<Movimentacao> getChequesAnteriores() {
		return chequesAnteriores;
	}

	public void setChequesAnteriores(List<Movimentacao> chequesAnteriores) {
		this.chequesAnteriores = chequesAnteriores;
	}

	public ListMaker getLmFinanc() {
		return lmFinanc;
	}

	public void setLmFinanc(ListMaker lmFinanc) {
		this.lmFinanc = lmFinanc;
	}

	public String getTituloAnteriores() {
		return tituloAnteriores;
	}

	public void setTituloAnteriores(String tituloAnteriores) {
		this.tituloAnteriores = tituloAnteriores;
	}

	public String getTituloNoPeriodo() {
		return tituloNoPeriodo;
	}

	public void setTituloNoPeriodo(String tituloNoPeriodo) {
		this.tituloNoPeriodo = tituloNoPeriodo;
	}

	public Boolean getExibirAnteriores() {
		return exibirAnteriores;
	}

	public void setExibirAnteriores(Boolean exibirAnteriores) {
		this.exibirAnteriores = exibirAnteriores;
	}

	public StoredViewInfoHandler getStoredViewInfoHandler() {
		return storedViewInfoHandler;
	}

	public void setStoredViewInfoHandler(StoredViewInfoHandler storedViewInfoHandler) {
		this.storedViewInfoHandler = storedViewInfoHandler;
	}

	public String[] getCamposStored() {
		return camposStored;
	}

	public void setCamposStored(String[] camposStored) {
		this.camposStored = camposStored;
	}

}
