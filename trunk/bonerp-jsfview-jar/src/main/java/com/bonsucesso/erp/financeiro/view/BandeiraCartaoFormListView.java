package com.bonsucesso.erp.financeiro.view;



import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.financeiro.data.BandeiraCartaoDataMapper;
import com.bonsucesso.erp.financeiro.data.BandeiraCartaoFilterFinder;
import com.bonsucesso.erp.financeiro.data.BandeiraCartaoFinder;
import com.bonsucesso.erp.financeiro.model.BandeiraCartao;
import com.ocabit.base.data.filter.FilterData;
import com.ocabit.base.data.filter.FilterType;
import com.ocabit.base.view.AbstractEntityFormListFilterView;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;


/**
 * View para a entidade BandeiraCartao.
 *
 * @author Carlos Eduardo Pauluk
 */
@Component("bandeiraCartaoFormListView")
@Scope("view")
public final class BandeiraCartaoFormListView extends
		AbstractEntityFormListFilterView<BandeiraCartao, BandeiraCartaoDataMapper, BandeiraCartaoFinder, BandeiraCartaoFilterFinder> {

	/**
	 *
	 */
	private static final long serialVersionUID = 4357200612654455969L;

	protected static Logger logger = Logger.getLogger(BandeiraCartaoFormListView.class);

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
