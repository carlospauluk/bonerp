package com.bonsucesso.erp.financeiro.view;



import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.financeiro.data.RegistroConferenciaDataMapper;
import com.bonsucesso.erp.financeiro.data.RegistroConferenciaFilterFinder;
import com.bonsucesso.erp.financeiro.data.RegistroConferenciaFinder;
import com.bonsucesso.erp.financeiro.model.RegistroConferencia;
import com.ocabit.base.data.filter.FilterData;
import com.ocabit.base.data.filter.FilterType;
import com.ocabit.base.view.AbstractEntityFormListFilterView;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;


/**
 * View para a entidade RegistroConferencia.
 *
 * @author Carlos Eduardo Pauluk
 */
@Component("registroConferenciaFormListView")
@Scope("view")
public final class RegistroConferenciaFormListView
		extends
		AbstractEntityFormListFilterView<RegistroConferencia, RegistroConferenciaDataMapper, RegistroConferenciaFinder, RegistroConferenciaFilterFinder> {

	/**
	 *
	 */
	private static final long serialVersionUID = 3371708288238303461L;

	protected static Logger logger = Logger.getLogger(RegistroConferenciaFormListView.class);
	
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
		getFilterDatas().add(new FilterData("strPesquisa", FilterType.EQUALS, "descricao"));
	}

	public List<String> acDescricao(String str) {
		try {
			return getFinder().findDescricaoBy(str);
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
			return null;
		}
	}

	public void gerarProximo(RegistroConferencia registroConferencia) {
		try {
			getDataMapper().gerarProximo(registroConferencia);
			JSFUtils.execute("loadList()");
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

}
