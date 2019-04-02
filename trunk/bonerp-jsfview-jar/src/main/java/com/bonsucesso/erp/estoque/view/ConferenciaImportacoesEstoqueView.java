package com.bonsucesso.erp.estoque.view;



import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.estoque.business.ConferenciaImportacoesEstoqueBusiness;
import com.bonsucesso.erp.estoque.business.ConferenciaImportacoesEstoqueRecord;
import com.bonsucesso.erp.vendas.data.VendaFinder;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;
import com.ocabit.utils.calendar.CalendarUtil;


@Component("conferenciaImportacoesEstoqueView")
@Scope("view")
public class ConferenciaImportacoesEstoqueView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 673812322822226161L;

	protected static Logger logger = Logger.getLogger(ConferenciaImportacoesEstoqueView.class);

	@Autowired
	private VendaFinder vendaFinder;

	@Autowired
	private ConferenciaImportacoesEstoqueBusiness business;

	private List<ConferenciaImportacoesEstoqueRecord> list;

	private final static Date dtIni = CalendarUtil.getDate(01, 01, 2014);

	private Date dtRegerar;

	@PostConstruct
	public void init() {

	}

	public void process() {

		List<Date> mesesAnos = CalendarUtil.buildMesAnoList(dtIni, new Date());

		try {

			List<ConferenciaImportacoesEstoqueRecord> l = getBusiness().buildRecords(mesesAnos);

			Collections.sort(l, new Comparator<ConferenciaImportacoesEstoqueRecord>() {

				@Override
				public int compare(ConferenciaImportacoesEstoqueRecord o1, ConferenciaImportacoesEstoqueRecord o2) {
					return CalendarUtil.dataMaiorIgualQueData(o2.getMesAno(), o1.getMesAno()) ? 1 : -1;
				}
			});

			setList(l);
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao construir lista de registros.");
		}

	}

	public void regerarRegistrosConferencia() {
		List<Date> mesesAnos = CalendarUtil.buildMesAnoList(getDtRegerar(), new Date());

		try {
			for (Date mesano : mesesAnos) {
				getBusiness().regerarRegistrosConferencia(mesano);
			}
			process();
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao regerar registros para conferência.");
		}
	}

	public VendaFinder getVendaFinder() {
		return vendaFinder;
	}

	public void setVendaFinder(VendaFinder vendaFinder) {
		this.vendaFinder = vendaFinder;
	}

	public List<ConferenciaImportacoesEstoqueRecord> getList() {
		return list;
	}

	public void setList(List<ConferenciaImportacoesEstoqueRecord> list) {
		this.list = list;
	}

	public ConferenciaImportacoesEstoqueBusiness getBusiness() {
		return business;
	}

	public void setBusiness(ConferenciaImportacoesEstoqueBusiness business) {
		this.business = business;
	}

	public Date getDtRegerar() {
		if (dtRegerar == null) {
			dtRegerar = CalendarUtil.primeiroDiaNoMesAno(new Date());
		}
		return dtRegerar;
	}

	public void setDtRegerar(Date dtRegerar) {
		this.dtRegerar = dtRegerar;
	}

}
