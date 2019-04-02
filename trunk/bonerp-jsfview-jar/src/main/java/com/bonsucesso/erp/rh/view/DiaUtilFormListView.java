package com.bonsucesso.erp.rh.view;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.base.data.DiaUtilDataMapper;
import com.bonsucesso.erp.base.data.DiaUtilFinder;
import com.bonsucesso.erp.base.model.DiaUtil;
import com.ocabit.base.view.AbstractEntityFormListView;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;
import com.ocabit.utils.calendar.CalendarUtil;


/**
 * View para a entidade DiaUtil.
 *
 * @author Carlos Eduardo Pauluk
 */
@Component("diaUtilFormListView")
@Scope("view")
public final class DiaUtilFormListView extends
		AbstractEntityFormListView<DiaUtil, DiaUtilDataMapper, DiaUtilFinder> {

	/**
	 *
	 */
	private static final long serialVersionUID = -4679104634858596356L;

	protected static Logger logger = Logger.getLogger(DiaUtilFormListView.class);

	private Date mesAno;

	private Integer mes, ano;

	private Integer qtdeDiasUteisFinanceiro, qtdeDiasUteisComerciais;

	@PostConstruct
	public void init() {
		try {
			setMesAno(new Date());
			// loadInitialList();
		} catch (final Exception e) {
			final String msg = "Erro ao iniciar a tela";
			JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, msg);
			logger.error(msg, e);
		}
	}

	@Override
	public void loadInitialList() {
		try {
			setList(getFinder().findDiasUteisBy(getMesAno()));

			setQtdeDiasUteisComerciais(getFinder().findDiasUteisComerciaisBy(getMesAno()).size());
			setQtdeDiasUteisFinanceiro(getFinder().findDiasUteisFinanceiroBy(getMesAno()).size());

		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public Integer getQtdeDiasUteisFinanceiro() {
		return qtdeDiasUteisFinanceiro;
	}

	public void setQtdeDiasUteisFinanceiro(Integer qtdeDiasUteisFinanceiro) {
		this.qtdeDiasUteisFinanceiro = qtdeDiasUteisFinanceiro;
	}

	public Integer getQtdeDiasUteisComerciais() {
		return qtdeDiasUteisComerciais;
	}

	public void setQtdeDiasUteisComerciais(Integer qtdeDiasUteisComerciais) {
		this.qtdeDiasUteisComerciais = qtdeDiasUteisComerciais;
	}

	public Date getMesAno() {
		return mesAno;
	}

	public void setMesAno(Date mesAno) {
		this.mesAno = mesAno;
		Calendar calHj = CalendarUtil.getCalendar(mesAno);
		ano = calHj.get(Calendar.YEAR);
		mes = calHj.get(Calendar.MONTH) + 1;
	}

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
		// acerta o mesAno
		Calendar calHj = CalendarUtil.getCalendar(mesAno);
		calHj.set(Calendar.MONTH, mes - 1);
		mesAno = calHj.getTime();
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
		// acerta o mesAno
		Calendar calHj = CalendarUtil.getCalendar(mesAno);
		calHj.set(Calendar.YEAR, ano);
		mesAno = calHj.getTime();
	}

	@Override
	public void afterSave() {
		// Utilizado somente quando o form é para o dialog
		RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam("dlgId", "dlgTotalDiaVendedor");
		loadInitialList();
	}

	@Override
	public void deletar() {
		try {
			getDataMapper().delete(getE());
			loadInitialList();
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public List<Integer> getAnos() {
		Calendar calHj = Calendar.getInstance();
		calHj.setTime(new Date());
		Integer anoAtual = calHj.get(Calendar.YEAR);

		List<Integer> anos = new ArrayList<Integer>();

		for (int i = (anoAtual - 10); i <= (anoAtual + 10); i++) {
			anos.add(i);
		}

		return anos;
	}

	public void marcarTodos(boolean comercial) {
		try {
			getDataMapper().saveList_diasUteisComerciais(getSelecteds(), comercial);
			loadInitialList();
			setSelecteds(null);
		} catch (ViewException e) {
			JSFUtils.addHandledException(e);
			logger.error(e);
		}
	}

	public void save(DiaUtil diaUtil) {
		boolean comercial = diaUtil.getComercial();
		boolean financeiro = diaUtil.getFinanceiro();
		setE(diaUtil);
		getE().setComercial(comercial);
		getE().setFinanceiro(financeiro);
		save();
	}

	public void onCellEdit(CellEditEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		DiaUtil diaUtil = context.getApplication().evaluateExpressionGet(context, "#{_}", DiaUtil.class);

		String descricao = diaUtil.getDescricao();
		setE(diaUtil);
		getE().setDescricao(descricao);
		save();
		// loadInitialList();
	}

}
