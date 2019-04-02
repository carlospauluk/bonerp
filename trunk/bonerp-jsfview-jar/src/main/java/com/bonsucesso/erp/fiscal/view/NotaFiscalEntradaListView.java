package com.bonsucesso.erp.fiscal.view;



import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.fiscal.data.NotaFiscalDataMapper;
import com.bonsucesso.erp.fiscal.data.NotaFiscalFilterFinder;
import com.bonsucesso.erp.fiscal.data.NotaFiscalFinder;
import com.bonsucesso.erp.fiscal.model.NotaFiscal;
import com.ocabit.base.data.filter.FilterData;
import com.ocabit.base.data.filter.FilterType;
import com.ocabit.base.view.AbstractEntityListView;
import com.ocabit.jsf.utils.JSFUtils;


@Component("notaFiscalEntradaListView")
@Scope("view")
public class NotaFiscalEntradaListView
		extends AbstractEntityListView<NotaFiscal, NotaFiscalDataMapper, NotaFiscalFinder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2359560005660766317L;

	protected static Logger logger = Logger.getLogger(NotaFiscalEntradaListView.class);

	@Autowired
	private NotaFiscalFilterFinder filterFinder;

	@PostConstruct
	public void init() {
		JSFUtils.execute("loadList()");
	}

	@Override
	protected void handleFilterDatas() {
		getFilterDatas().add(new FilterData("strPesquisa", FilterType.LIKE_ANYWHERE, new String[] {
				"fornecedor.pessoa.documento", "fornecedor.pessoa.nome", "fornecedor.pessoa.nomeFantasia" }));
	}

	public NotaFiscalFilterFinder getFilterFinder() {
		return filterFinder;
	}

	public void setFilterFinder(NotaFiscalFilterFinder filterFinder) {
		this.filterFinder = filterFinder;
	}

}
