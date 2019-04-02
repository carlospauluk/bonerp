package com.bonsucesso.erp.financeiro.view;



import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.financeiro.data.OperadoraCartaoDataMapper;
import com.bonsucesso.erp.financeiro.data.OperadoraCartaoFinder;
import com.bonsucesso.erp.financeiro.model.OperadoraCartao;
import com.ocabit.base.view.AbstractEntityFormListView;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;


/**
 * View para a entidade OperadoraCartao.
 *
 * @author Carlos Eduardo Pauluk
 */
@Component("operadoraCartaoFormListView")
@Scope("view")
public final class OperadoraCartaoFormListView extends
		AbstractEntityFormListView<OperadoraCartao, OperadoraCartaoDataMapper, OperadoraCartaoFinder> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1763666090504259193L;
	
	protected static Logger logger = Logger.getLogger(OperadoraCartaoFormListView.class);
	
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

}
