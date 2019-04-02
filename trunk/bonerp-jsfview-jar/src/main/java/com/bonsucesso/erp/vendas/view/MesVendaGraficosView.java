package com.bonsucesso.erp.vendas.view;



import java.io.Serializable;

import org.apache.log4j.Logger;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.vendas.business.MesVendaBusiness;
import com.bonsucesso.erp.vendas.model.MesVenda;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;


/**
 * View para a entidade MesVenda.
 *
 * @author Carlos Eduardo Pauluk
 */
@Component("mesVendaGraficosView")
@Scope("view")
public final class MesVendaGraficosView implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 4622468835736488666L;

	protected static Logger logger = Logger.getLogger(MesVendaGraficosView.class);

	@Autowired
	private MesVendaBusiness mesVendaBusiness;

	private HorizontalBarChartModel chartModelVendasVendedores = new HorizontalBarChartModel();

	public void updateMesVenda(MesVenda e) {
		try {
			getMesVendaBusiness().preencherMesVenda(e);

//			setResultMensalVendedores(getResultMensalBusiness().gerarResultMensalVendedoresAtivosBy(e.getMesAno()));

			gerarModelVendasVendedores();
		} catch (ViewException ve) {
			logger.error(ve);
			JSFUtils.addErrorMsg(ve);
		}
	}

	public void gerarModelVendasVendedores() {
		chartModelVendasVendedores = new HorizontalBarChartModel();

//		if (getResultMensalVendedores() != null) {
//			ChartSeries series = new ChartSeries();
//
//			List<ResultMensalVendedor> rs = getResultMensalVendedores();
//			Collections.sort(rs, new Comparator<ResultMensalVendedor>() {
//
//				@Override
//				public int compare(ResultMensalVendedor o1, ResultMensalVendedor o2) {
//					return new CompareToBuilder()
//							.append(o1.getQtdeVendas(), o2.getQtdeVendas())
//							.toComparison();
//				}
//			});
//
//			for (ResultMensalVendedor r : rs) {
//				if (r.getConsideraMes() == true) {
//					series.set(r.getVendedor().getNomeEkt(), r.getQtdeVendas());
//				}
//			}
//			chartModelVendasVendedores.addSeries(series);
//
//		}

		chartModelVendasVendedores.setAnimate(true);
		chartModelVendasVendedores.setMouseoverHighlight(true);
		chartModelVendasVendedores.setShadow(true);

		setChartModelVendasVendedores(chartModelVendasVendedores);
	}

	public MesVendaBusiness getMesVendaBusiness() {
		return mesVendaBusiness;
	}

	public void setMesVendaBusiness(MesVendaBusiness mesVendaBusiness) {
		this.mesVendaBusiness = mesVendaBusiness;
	}

	

	public HorizontalBarChartModel getChartModelVendasVendedores() {
		return chartModelVendasVendedores;
	}

	public void setChartModelVendasVendedores(HorizontalBarChartModel chartModelVendasVendedores) {
		this.chartModelVendasVendedores = chartModelVendasVendedores;
	}
	
}
