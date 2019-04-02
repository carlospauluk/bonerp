package com.bonsucesso.erp.rh.view;



import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.log4j.Logger;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.LineChartModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.rh.data.FuncionarioFinder;
import com.bonsucesso.erp.rh.model.Funcionario;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;
import com.ocabit.utils.calendar.CalendarUtil;
import com.ocabit.utils.currency.CurrencyUtils;


@Component("graficoVendasView")
@Scope("view")
public class GraficoVendasView implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 2522468834592529408L;

	protected static Logger logger = Logger.getLogger(GraficoVendasView.class);

	@Autowired
	private FuncionarioFinder funcionarioFinder;

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

	private Map<Funcionario, List<PosicaoMes>> posicoesMes;

	private Map<Funcionario, List<HistoricoVendas>> historicoVendas;

	private List<Ranking> rankingGeral;

	private boolean exibirRankingGeral;

	@PostConstruct
	public void init() {
		Calendar periodoIni = CalendarUtil.getCalendar(new Date());
		periodoIni.add(Calendar.MONTH, -18);
		setPeriodoIni(periodoIni.getTime());

		Calendar periodoFim = CalendarUtil.getCalendar(new Date());
		periodoFim.add(Calendar.MONTH, 1);
		setPeriodoFim(periodoFim.getTime());
		processar();
	}

	public FuncionarioFinder getFuncionarioFinder() {
		return funcionarioFinder;
	}

	public void setFuncionarioFinder(FuncionarioFinder funcionarioFinder) {
		this.funcionarioFinder = funcionarioFinder;
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

	public Integer getMediaPosicoes(Funcionario vendedor) {
		// Também poderia ir buscar pelo finder...
		for (Ranking rg : getRankingGeral()) {
			if (rg.getVendedor().equals(vendedor)) {
				return rg.getMediaPosicoes();
			}
		}
		return null;
	}

	public Double processMediaPorcentMeta(Funcionario vendedor) {
		try {
			if ((getHistoricoVendas() != null) && getHistoricoVendas().containsKey(vendedor)) {
				List<HistoricoVendas> historicoVendas = getHistoricoVendas().get(vendedor);
				double qtde = 0.0;
				BigDecimal soma = CurrencyUtils.getBigDecimalCurrency(0.0);
				for (HistoricoVendas h : historicoVendas) {
					if ((h.getPorcent() != null) && h.isMesConsidera()) {
						qtde++;
						soma = CurrencyUtils.soma(soma, CurrencyUtils.getBigDecimalCurrency(h.getPorcent()));
					}
				}
				if (qtde > 0) {
					return CurrencyUtils.divide(soma.doubleValue(), qtde);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Double getMediaPorcentMeta(Funcionario vendedor) {
		for (Ranking rg : getRankingGeral()) {
			if (rg.getVendedor().equals(vendedor)) {
				return rg.getMediaPorcentMeta();
			}
		}
		return null;
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

	public Map<Funcionario, List<PosicaoMes>> getPosicoesMes() {
		if (posicoesMes == null) {
			posicoesMes = new HashMap<Funcionario, List<PosicaoMes>>();
		}
		return posicoesMes;
	}

	public void setPosicoesMes(Map<Funcionario, List<PosicaoMes>> posicoesMes) {
		this.posicoesMes = posicoesMes;
	}

	public Map<Funcionario, List<HistoricoVendas>> getHistoricoVendas() {
		if (historicoVendas == null) {
			historicoVendas = new HashMap<Funcionario, List<HistoricoVendas>>();
		}
		return historicoVendas;
	}

	public void setHistoricoVendas(Map<Funcionario, List<HistoricoVendas>> historicoVendas) {
		this.historicoVendas = historicoVendas;
	}

	public CartesianChartModel getPosicaoModelByVendedor() {
		return getPosicoesModel().get(getVendedor());
	}

	public CartesianChartModel getTotalVendasModelByVendedor() {
		return getTotaisVendasModel().get(getVendedor());
	}

	/**
	 * Método principal.
	 */
	public void processar() {
		try {
			setTotaisVendasModel(null);
			setPosicoesModel(null);
			setRankingGeral(null);
			// Já percorre todos os vendedores e monta os gráficos (para não ficar processando toda vez que trocar de funcionário).
			for (Funcionario f : getVendedores()) {
				// Monta o gráfico de totais de vendas.
				getTotaisVendasModel().put(f, gerarGraficoTotaisVendas(f));
				getPosicoesModel().put(f, gerarGraficoPosicoes(f));

				Ranking rankingGeral = new Ranking();
				rankingGeral.setVendedor(f);
				rankingGeral.setMediaPorcentMeta(processMediaPorcentMeta(f));
				Double mediaPosicoes = null; // getTotalMesVendedorFinder().findMediaPosicoes(f, getPeriodoIni(), getPeriodoFim());
				if (mediaPosicoes != null) {
					rankingGeral.setMediaPosicoes(CurrencyUtils.getBigDecimalScaled(mediaPosicoes, 0).intValue());
				}

				getRankingGeral().add(rankingGeral);
			}

			Collections.sort(getRankingGeral(), new Comparator<Ranking>() {

				@Override
				public int compare(Ranking o1, Ranking o2) {
					return new CompareToBuilder()
							.append(o2.getMediaPorcentMeta(), o1.getMediaPorcentMeta())
							.toComparison();
				}
			});

		} catch (ViewException e) {
			e.printStackTrace();
			logger.error(e);
			JSFUtils.addHandledException(e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao gerar dados");
		}
	}

	private LineChartModel gerarGraficoTotaisVendas(Funcionario vendedor) throws ViewException {
		//		try {
		//			LineChartModel totalVendasModel = new LineChartModel();
		//			totalVendasModel.setTitle("Total Vendas");
		//			totalVendasModel.setLegendPosition("ne");
		//			totalVendasModel.setShowPointLabels(true);
		//			totalVendasModel.getAxes().put(AxisType.X, new CategoryAxis("Mês/Ano"));
		//			Axis yAxis = totalVendasModel.getAxes().get(AxisType.Y);
		//			yAxis.setLabel("Vendas");
		//
		//			Calendar mesAno = CalendarUtil.getCalendar(getPeriodoIni());
		//
		//			// linha para total vendido
		//			ChartSeries totalVendasSeries = new ChartSeries();
		//			// linha para o mínimo que deveria vender para cobrir o gap da comissão
		//			ChartSeries minimoVendasComissSeries = new ChartSeries();
		//			// linha para a meta segundo o histórico
		//			ChartSeries historicoSeries = new ChartSeries();
		//
		//			// padrão ex. mai/2014
		//			SimpleDateFormat sdf = new SimpleDateFormat("MMM/yyyy");
		//
		//			// Monta a lista de HistoricoVendas para exibir abaixo do gráfico
		//			List<HistoricoVendas> historicosVendas = new ArrayList<HistoricoVendas>();
		//			getHistoricoVendas().put(vendedor, historicosVendas);
		//
		//			// Percorre todos os meses do período
		//			while (true) {
		//
		//				MesVenda totalMes = getMesVendaFinder().findByMesAno(mesAno.getTime());
		//
		//				HistoricoVendas historicoVendas = new HistoricoVendas();
		//				historicoVendas.setMesAno(mesAno.getTime());
		//
		//				if (totalMes != null) {
		//					if (totalMes.getMetaVendedor() != null) {
		//						historicoSeries.set(sdf.format(mesAno.getTime()), totalMes.getMetaVendedor());
		//						historicoVendas.setMeta(totalMes.getMetaVendedor());
		//					} else {
		//						historicoSeries.set(sdf.format(mesAno.getTime()), 0.0);
		//						historicoVendas.setMeta(0.0);
		//					}
		//				}
		//
		//				// Pega os dados do vendedor no mês
		//				TotalMesVendedor totalMesVendedor = getTotalMesVendedorFinder()
		//						.findByVendedorAndMesAno(vendedor, mesAno.getTime());
		//
		//				if (totalMesVendedor != null) {
		//					historicoVendas.setMesConsidera(totalMesVendedor.getConsideraMes());
		//				} else {
		//					historicoVendas.setMesConsidera(false);
		//				}
		//
		//				// pega o mínimo de vendas para cobrir comissão
		//				FuncionarioCargo cargo = getFuncionarioFinder().findCargoByMesAno(vendedor, mesAno.getTime());
		//				Double minimoVendasMes = 0.0;
		//				if (cargo != null) {
		//					minimoVendasMes = cargo.getMinimoVendasMes().doubleValue();
		//				}
		//				minimoVendasComissSeries.set(sdf.format(mesAno.getTime()), minimoVendasMes);
		//
		//				Double totalVendas = 0.0;
		//				if (totalMesVendedor != null) {
		//					totalVendas = totalMesVendedor.getTotalVendas();
		//				}
		//				totalVendasSeries.set(sdf.format(mesAno.getTime()), totalVendas);
		//
		//				historicoVendas.setTotalVendas(totalVendas);
		//
		//				// Adiciona um registro na lista de histórico de vendas para exibir abaixo do gráfico
		//				getHistoricoVendas().get(vendedor).add(historicoVendas);
		//
		//				mesAno.add(Calendar.MONTH, 1);
		//				if (CalendarUtil.dataMaiorIgualQueData(mesAno.getTime(), getPeriodoFim())) {
		//					break;
		//				}
		//
		//			}
		//			totalVendasSeries.setLabel("Total Vendas");
		//			minimoVendasComissSeries.setLabel("Mínimo Vendas Comissões");
		//			historicoSeries.setLabel("Meta Histórico");
		//			totalVendasModel.addSeries(totalVendasSeries);
		//			totalVendasModel.addSeries(minimoVendasComissSeries);
		//			totalVendasModel.addSeries(historicoSeries);
		//
		//			return totalVendasModel;
		//		} catch (Exception e) {
		//			throw new ViewException("Erro ao gerar gráfico de vendas");
		//		}

		return null;
	}

	private BarChartModel gerarGraficoPosicoes(Funcionario vendedor) {
		//		BarChartModel posicoesModel = new BarChartModel();
		//		posicoesModel.setTitle("Posições Vendas");
		//		posicoesModel.setLegendPosition("ne");
		//		posicoesModel.setShowPointLabels(true);
		//		Axis xAxis = posicoesModel.getAxis(AxisType.X);
		//		xAxis.setLabel("Mês/Ano");
		//		xAxis.setMin(0);
		//		xAxis.setMax(200);
		//
		//		Axis yAxis = posicoesModel.getAxis(AxisType.Y);
		//		yAxis.setLabel("Posição");
		//		yAxis.setMin(-12);
		//		yAxis.setMax(0);
		//
		//		Calendar mesAno = CalendarUtil.getCalendar(getPeriodoIni());
		//
		//		ChartSeries posicaoSeries = new ChartSeries();
		//
		//		SimpleDateFormat sdf = new SimpleDateFormat("MMM/yyyy");
		//
		//		List<PosicaoMes> posicoesMes = new ArrayList<PosicaoMes>();
		//		getPosicoesMes().put(vendedor, posicoesMes);
		//
		//		// Percorre todos os meses do período
		//		while (true) {
		//			// Pega os dados do vendedor no mês
		//			TotalMesVendedor totalMesVendedor = getTotalMesVendedorFinder()
		//					.findByVendedorAndMesAno(vendedor, mesAno.getTime());
		//
		//			PosicaoMes posicaoMes = new PosicaoMes();
		//
		//			if (totalMesVendedor != null) {
		//				posicaoMes.setMesConsidera(totalMesVendedor.getConsideraMes());
		//				posicaoMes.setPosicao(totalMesVendedor.getPosicao());
		//			} else {
		//				posicaoMes.setMesConsidera(false);
		//			}
		//
		//			if ((totalMesVendedor == null) || (totalMesVendedor.getPosicao() == null)) {
		//				posicaoSeries.set(sdf.format(mesAno.getTime()), null);
		//			} else {
		//				// Inverte para melhor exibir no gráfico
		//				posicaoSeries.set(sdf.format(mesAno.getTime()), totalMesVendedor.getPosicao() * -1);
		//			}
		//			posicaoMes.setMesAno(mesAno.getTime());
		//			getPosicoesMes().get(vendedor).add(posicaoMes);
		//
		//			mesAno.add(Calendar.MONTH, 1);
		//			if (CalendarUtil.dataMaiorIgualQueData(mesAno.getTime(), getPeriodoFim())) {
		//				break;
		//			}
		//		}
		//		posicoesModel.addSeries(posicaoSeries);

		return null; // posicoesModel;
	}

	public class PosicaoMes {

		private Date mesAno;

		private Integer posicao;

		private boolean mesConsidera;

		public Date getMesAno() {
			return mesAno;
		}

		public void setMesAno(Date mesAno) {
			this.mesAno = mesAno;
		}

		public Integer getPosicao() {
			return posicao;
		}

		public void setPosicao(Integer posicao) {
			this.posicao = posicao;
		}

		public boolean isMesConsidera() {
			return mesConsidera;
		}

		public void setMesConsidera(boolean mesConsidera) {
			this.mesConsidera = mesConsidera;
		}

	}

	public class HistoricoVendas {

		private Date mesAno;

		private Double historico;

		private Double meta;

		private Double totalVendas;

		private Double porcent;

		private boolean mesConsidera;

		public Date getMesAno() {
			return mesAno;
		}

		public void setMesAno(Date mesAno) {
			this.mesAno = mesAno;
		}

		public Double getHistorico() {
			return historico;
		}

		public void setHistorico(Double historico) {
			this.historico = historico;
		}

		public Double getMeta() {
			return meta;
		}

		public void setMeta(Double meta) {
			this.meta = meta;
		}

		public Double getTotalVendas() {
			return totalVendas;
		}

		public void setTotalVendas(Double totalVendas) {
			this.totalVendas = totalVendas;
		}

		public Double getPorcent() {
			if ((getTotalVendas() != null) && (getMeta() != null) && (getMeta() > 0)) {
				porcent = (CurrencyUtils.divide(getTotalVendas(), getMeta()) - 1) * 100;
			}
			return porcent;
		}

		public void setPorcent(Double porcent) {
			this.porcent = porcent;
		}

		public boolean isMesConsidera() {
			return mesConsidera;
		}

		public void setMesConsidera(boolean mesConsidera) {
			this.mesConsidera = mesConsidera;
		}

	}

	public class Ranking {

		private Integer mediaPosicoes;

		private Double mediaPorcentMeta;

		private Funcionario vendedor;

		public Integer getMediaPosicoes() {
			return mediaPosicoes;
		}

		public void setMediaPosicoes(Integer mediaPosicoes) {
			this.mediaPosicoes = mediaPosicoes;
		}

		public Double getMediaPorcentMeta() {
			return mediaPorcentMeta;
		}

		public void setMediaPorcentMeta(Double mediaPorcentMeta) {
			this.mediaPorcentMeta = mediaPorcentMeta;
		}

		public Funcionario getVendedor() {
			return vendedor;
		}

		public void setVendedor(Funcionario vendedor) {
			this.vendedor = vendedor;
		}

	}

}
