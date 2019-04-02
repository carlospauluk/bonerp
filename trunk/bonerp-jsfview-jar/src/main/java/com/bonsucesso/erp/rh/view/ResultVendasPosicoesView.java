package com.bonsucesso.erp.rh.view;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

import org.apache.log4j.Logger;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.LineChartModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.rh.data.FuncionarioFinder;
import com.bonsucesso.erp.rh.model.Funcionario;
import com.bonsucesso.erp.rh.view.GraficoVendasView.Ranking;
import com.bonsucesso.erp.vendas.data.MesVendaFinder;
import com.bonsucesso.erp.vendas.data.VendaFinder;
import com.ocabit.base.view.StoredViewInfoHandler;
import com.ocabit.jsf.utils.JSFUtils;
import com.ocabit.utils.calendar.CalendarUtil;


/**
 * View para resultados de vendas e posições de vendedores.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("resultVendasPosicoesView")
@Scope("view")
public class ResultVendasPosicoesView implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -6309299493680632678L;

	protected static Logger logger = Logger.getLogger(ResultVendasPosicoesView.class);

	@Autowired
	private StoredViewInfoHandler storeHandler;

	@Autowired
	private FuncionarioFinder funcionarioFinder;

	@Autowired
	private VendaFinder vendaFinder;

	@Autowired
	private MesVendaFinder mesVendaFinder;

	@NotNull(message = "A data do 'Período (ini)' deve ser informada")
	private Date periodoIni;

	@NotNull(message = "A data do 'Período (fim)' deve ser informada")
	private Date periodoFim;

	private Funcionario vendedor;

	private boolean somenteVendedoresAtuais = true;

	// Mapa para acomodar todos os gráficos de totais de vendas dos vendedores
	private Map<Funcionario, LineChartModel> totaisVendasModel;

	// Mapa para acomodar todos os gráficos de posições dos vendedores
	private Map<Funcionario, BarChartModel> posicoesModel;

	// Tipos de gráficos (T: total de vendas, P: posições)
	private String tipoGrafico = "T";

	private List<Ranking> rankingGeral;

	private boolean exibirRankingGeral;

	private List<Periodo> periodosSelect;

	private Periodo periodo = Periodo.SEIS_MESES;

	@PostConstruct
	public void init() {
		Calendar periodoIni = CalendarUtil.getCalendar(new Date());
		periodoIni.add(Calendar.MONTH, -18);
		setPeriodoIni(periodoIni.getTime());

		Calendar periodoFim = CalendarUtil.getCalendar(new Date());
		periodoFim.add(Calendar.MONTH, 1);
		setPeriodoFim(periodoFim.getTime());
	}

	/**
	 * Método principal.
	 */
	public void gerarDtListResultMensal() {

	}

	public List<Funcionario> getVendedores() {
		try {
			if (isSomenteVendedoresAtuais()) {
				return getFuncionarioFinder().findVendedoresAtivosNoMesAno(new Date());
			} else {
				return getFuncionarioFinder().findAll("pessoa.nome");
			}
		} catch (Exception e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao buscar vendedores");
			return null;
		}
	}

	// ----------------------------------------------------

	public StoredViewInfoHandler getStoreHandler() {
		return storeHandler;
	}

	public void setStoreHandler(StoredViewInfoHandler storeHandler) {
		this.storeHandler = storeHandler;
	}

	public String getTipoGrafico() {
		return tipoGrafico;
	}

	public void setTipoGrafico(String tipoGrafico) {
		this.tipoGrafico = tipoGrafico;
	}

	public List<Ranking> getRankingGeral() {
		if (rankingGeral == null) {
			rankingGeral = new ArrayList<Ranking>();
		}
		return rankingGeral;
	}

	public void setRankingGeral(List<Ranking> rankingGeral) {
		this.rankingGeral = rankingGeral;
	}

	public boolean isExibirRankingGeral() {
		return exibirRankingGeral;
	}

	public void setExibirRankingGeral(boolean exibirRankingGeral) {
		this.exibirRankingGeral = exibirRankingGeral;
	}

	public CartesianChartModel getPosicaoModelByVendedor() {
		return getPosicoesModel().get(getVendedor());
	}

	public CartesianChartModel getTotalVendasModelByVendedor() {
		return getTotaisVendasModel().get(getVendedor());
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	public FuncionarioFinder getFuncionarioFinder() {
		return funcionarioFinder;
	}

	public void setFuncionarioFinder(FuncionarioFinder funcionarioFinder) {
		this.funcionarioFinder = funcionarioFinder;
	}

	public VendaFinder getVendaFinder() {
		return vendaFinder;
	}

	public void setVendaFinder(VendaFinder vendaFinder) {
		this.vendaFinder = vendaFinder;
	}

	public MesVendaFinder getMesVendaFinder() {
		return mesVendaFinder;
	}

	public void setMesVendaFinder(MesVendaFinder mesVendaFinder) {
		this.mesVendaFinder = mesVendaFinder;
	}

	public List<Funcionario> getFuncionariosAtuais() {
		return getFuncionarioFinder().findAtuais();
	}

	public Funcionario getVendedor() {
		return vendedor;
	}

	public void setVendedor(Funcionario vendedor) {
		this.vendedor = vendedor;
	}

	public Date getPeriodoIni() {
		return periodoIni;
	}

	public void setPeriodoIni(Date periodoIni) {
		this.periodoIni = periodoIni;
	}

	public Date getPeriodoFim() {
		return periodoFim;
	}

	public void setPeriodoFim(Date periodoFim) {
		this.periodoFim = periodoFim;
	}

	public Map<Funcionario, LineChartModel> getTotaisVendasModel() {
		if (totaisVendasModel == null) {
			totaisVendasModel = new HashMap<Funcionario, LineChartModel>();
		}
		return totaisVendasModel;
	}

	public void setTotaisVendasModel(Map<Funcionario, LineChartModel> totaisVendasModel) {
		this.totaisVendasModel = totaisVendasModel;
	}

	public Map<Funcionario, BarChartModel> getPosicoesModel() {
		if (posicoesModel == null) {
			posicoesModel = new HashMap<Funcionario, BarChartModel>();
		}
		return posicoesModel;
	}

	public void setPosicoesModel(Map<Funcionario, BarChartModel> posicoesModel) {
		this.posicoesModel = posicoesModel;
	}

	public boolean isSomenteVendedoresAtuais() {
		return somenteVendedoresAtuais;
	}

	public void setSomenteVendedoresAtuais(boolean somenteVendedoresAtuais) {
		this.somenteVendedoresAtuais = somenteVendedoresAtuais;
	}

	public List<Periodo> getPeriodosSelect() {
		if (periodosSelect == null) {
			periodosSelect = Arrays.asList(Periodo.values());
		}
		return periodosSelect;
	}

	public void setPeriodosSelect(List<Periodo> periodosSelect) {
		this.periodosSelect = periodosSelect;
	}

	/**
	 * Seleção para informar o período em que serão geradas as informações sobre o vendedor.
	 *
	 * @author Carlos Eduardo Pauluk
	 */
	public enum Periodo {
		TRES_MESES("3 meses"),
		SEIS_MESES("6 meses"),
		DOZE_MESES("12 meses"),
		TODO_O_PERIODO_TRABALHADO("Todo o período trabalhado"),
		ESPECIFICO("Específico");

		private String label;

		public String getLabel() {
			return label;
		}

		private void setLabel(String label) {
			this.label = label;
		}

		private Periodo(String label) {
			setLabel(label);
		}

		public Date[] getDatePeriodo() {
			Date[] datePeriodo = new Date[2];

			datePeriodo[1] = CalendarUtil.ultimoDiaNoMesAno(new Date());

			switch (this) {
				case TRES_MESES:
					datePeriodo[0] = CalendarUtil.primeiroDiaNoMesAno(CalendarUtil.incMes(new Date(), -2));
					break;

				case SEIS_MESES:
					datePeriodo[0] = CalendarUtil.primeiroDiaNoMesAno(CalendarUtil.incMes(new Date(), -5));
					break;

				case DOZE_MESES:
					datePeriodo[0] = CalendarUtil.primeiroDiaNoMesAno(CalendarUtil.incMes(new Date(), -11));
					break;

			}

			return datePeriodo;
		}
	}

}
