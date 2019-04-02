package com.bonsucesso.erp.estoque.view;



import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.estoque.data.UnidadeProdutoDataMapper;
import com.bonsucesso.erp.estoque.data.UnidadeProdutoFinder;
import com.bonsucesso.erp.estoque.model.UnidadeProduto;
import com.ocabit.base.view.AbstractEntityFormListView;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;


/**
 * View para a entidade UnidadeProduto.
 *
 * @author Carlos Eduardo Pauluk
 */
@Component("unidadeProdutoFormView")
@Scope("view")
public final class UnidadeProdutoFormListView extends
		AbstractEntityFormListView<UnidadeProduto, UnidadeProdutoDataMapper, UnidadeProdutoFinder> {

	/**
	 *
	 */
	private static final long serialVersionUID = 4398082475002456273L;

	protected static Logger logger = Logger.getLogger(UnidadeProdutoFormListView.class);

	/**
	 * Carrega a listagem inicial.
	 */
	@Override
	public void loadInitialList() {
		try {
			setList(getFinder().findAll("label"));
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	@Override
	public void afterSave() {
		loadInitialList();
		RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam("saved", true);
		context.addCallbackParam("dlgId", "dlg");
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

}
