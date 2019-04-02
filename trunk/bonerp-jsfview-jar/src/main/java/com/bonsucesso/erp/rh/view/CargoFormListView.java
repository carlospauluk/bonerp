package com.bonsucesso.erp.rh.view;



import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.rh.data.CargoDataMapper;
import com.bonsucesso.erp.rh.data.CargoFilterFinder;
import com.bonsucesso.erp.rh.data.CargoFinder;
import com.bonsucesso.erp.rh.model.Cargo;
import com.ocabit.base.data.filter.FilterData;
import com.ocabit.base.data.filter.FilterType;
import com.ocabit.base.view.AbstractEntityFormListFilterView;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;


/**
 * View para a entidade Cargo.
 *
 * @author Carlos Eduardo Pauluk
 */
@Component("cargoFormListView")
@Scope("view")
public final class CargoFormListView extends
		AbstractEntityFormListFilterView<Cargo, CargoDataMapper, CargoFinder, CargoFilterFinder> {

	/**
	 *
	 */
	private static final long serialVersionUID = 7432266686301900444L;

	protected static Logger logger = Logger.getLogger(CargoFormListView.class);

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
		getFilterDatas().add(new FilterData("strPesquisa", FilterType.LIKE_ANYWHERE, "nome"));

	}

}
