package com.bonsucesso.erp.producao.catalogo.view;



import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.producao.catalogo.data.CatalogoFinder;
import com.bonsucesso.erp.producao.catalogo.model.Catalogo;
import com.ocabit.base.view.AbstractListMaker;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;


/**
 * ListMaker para as entidades do pacote com.bonsucesso.erp.producao.catalogo.view.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("lmCatalogo")
@Scope("view")
public class ListMaker implements AbstractListMaker {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2954620430477482360L;

	protected static Logger logger = Logger.getLogger(ListMaker.class);

	@Autowired
	private CatalogoFinder catalogoFinder;

	private List<Catalogo> catalogos;

	public CatalogoFinder getCatalogoFinder() {
		return catalogoFinder;
	}

	public void setCatalogoFinder(CatalogoFinder catalogoFinder) {
		this.catalogoFinder = catalogoFinder;
	}

	public List<Catalogo> getCatalogos() {
		if (catalogos == null) {
			try {
				setCatalogos(getCatalogoFinder().findAll("descricao"));
			} catch (ViewException e) {
				JSFUtils.addHandledException(e);
			}
		}
		return catalogos;
	}

	public void setCatalogos(List<Catalogo> catalogos) {
		this.catalogos = catalogos;
	}

}
