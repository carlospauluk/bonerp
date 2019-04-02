package com.bonsucesso.erp.financeiro.view;



import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.financeiro.data.BancoDataMapper;
import com.bonsucesso.erp.financeiro.data.BancoFilterFinder;
import com.bonsucesso.erp.financeiro.data.BancoFinder;
import com.bonsucesso.erp.financeiro.model.Banco;
import com.ocabit.base.data.filter.FilterData;
import com.ocabit.base.data.filter.FilterType;
import com.ocabit.base.view.AbstractEntityFormListFilterView;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;


/**
 * View para a entidade Banco.
 *
 * @author Carlos Eduardo Pauluk
 */
@Component("bancoFormListView")
@Scope("view")
public final class BancoFormListView extends
		AbstractEntityFormListFilterView<Banco, BancoDataMapper, BancoFinder, BancoFilterFinder> {

	/**
	 *
	 */
	private static final long serialVersionUID = 7432266686301900444L;

	protected static Logger logger = Logger.getLogger(BancoFormListView.class);
	
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
			setList(getFinder().findAll("nome"));
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	@Override
	public void handleFilterDatas() {
		getFilterDatas().add(new FilterData("strPesquisa", FilterType.LIKE_ANYWHERE, "nome"));
	}

}
