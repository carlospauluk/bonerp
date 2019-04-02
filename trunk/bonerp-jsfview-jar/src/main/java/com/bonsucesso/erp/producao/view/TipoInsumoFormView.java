package com.bonsucesso.erp.producao.view;



import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.producao.data.TipoInsumoDataMapper;
import com.bonsucesso.erp.producao.data.TipoInsumoFinder;
import com.bonsucesso.erp.producao.model.TipoInsumo;
import com.ocabit.base.view.AbstractEntityFormListView;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;


/**
 * View para a entidade TipoInsumo.
 *
 * @author Carlos Eduardo Pauluk
 */
@Component("tipoInsumoFormView")
@Scope("view")
public class TipoInsumoFormView extends AbstractEntityFormListView<TipoInsumo, TipoInsumoDataMapper, TipoInsumoFinder> {

	private static final long serialVersionUID = 1L;

	protected static Logger logger = Logger.getLogger(TipoInsumoFormView.class);

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
	public String getDlgId() {
		return "dlgTipoInsumo";
	}

	@Override
	public void deletar() {
		try {
			getDataMapper().delete(getE());
			loadInitialList();
		} catch (ViewException e) {
			JSFUtils.addErrorMsg("Não foi possível deletar o registro");
		}
	}

	public void atualizar() {
		loadInitialList();
	}

}
