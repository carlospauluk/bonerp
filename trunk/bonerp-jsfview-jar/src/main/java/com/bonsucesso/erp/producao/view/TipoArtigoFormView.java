package com.bonsucesso.erp.producao.view;



import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.producao.data.TipoArtigoDataMapper;
import com.bonsucesso.erp.producao.data.TipoArtigoFinder;
import com.bonsucesso.erp.producao.model.TipoArtigo;
import com.ocabit.base.view.AbstractEntityFormListView;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;


/**
 * View para a entidade TipoArtigo.
 *
 * @author Carlos Eduardo Pauluk
 */
@Component("tipoArtigoFormView")
@Scope("view")
public class TipoArtigoFormView extends AbstractEntityFormListView<TipoArtigo, TipoArtigoDataMapper, TipoArtigoFinder> {

	private static final long serialVersionUID = 1L;

	protected static Logger logger = Logger.getLogger(TipoArtigoFormView.class);

	@Autowired
	protected com.bonsucesso.erp.estoque.view.ListMaker lmEstoque;

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
	public void afterSetE(TipoArtigo e) {
		if ((e != null) && (e.getSubdepto() != null)) {
			lmEstoque.setDepto(e.getSubdepto().getDepto());
		}
	}

	@Override
	public String getDlgId() {
		return "dlgTipoArtigo";
	}

	public void atualizar() {
		loadInitialList();
	}

}
