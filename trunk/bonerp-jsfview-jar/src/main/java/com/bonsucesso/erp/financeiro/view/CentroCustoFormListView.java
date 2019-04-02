package com.bonsucesso.erp.financeiro.view;



import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.financeiro.data.CentroCustoDataMapper;
import com.bonsucesso.erp.financeiro.data.CentroCustoFilterFinder;
import com.bonsucesso.erp.financeiro.data.CentroCustoFinder;
import com.bonsucesso.erp.financeiro.model.CentroCusto;
import com.ocabit.base.data.filter.FilterData;
import com.ocabit.base.data.filter.FilterType;
import com.ocabit.base.view.AbstractEntityFormListFilterView;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;


/**
 * View para a entidade CentroCusto.
 *
 * @author Carlos Eduardo Pauluk
 */
@Component("centroCustoFormListView")
@Scope("view")
public final class CentroCustoFormListView
		extends
		AbstractEntityFormListFilterView<CentroCusto, CentroCustoDataMapper, CentroCustoFinder, CentroCustoFilterFinder> {

	/**
	 *
	 */
	private static final long serialVersionUID = 6879580099934652399L;

	protected static Logger logger = Logger.getLogger(CentroCustoFormListView.class);
	
	@PostConstruct
	public void init() {
		JSFUtils.execute("loadList()");
	}

	/**
	 * Carrega a listagem inicial.
	 */
	@Override
	public void loadInitialList() {
		try {
			setList(getFinder().findAll("descricao"));
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	@Override
	public void handleFilterDatas() {
		getFilterDatas().add(new FilterData("strPesquisa", FilterType.LIKE_ANYWHERE, "descricao"));

	}

}
