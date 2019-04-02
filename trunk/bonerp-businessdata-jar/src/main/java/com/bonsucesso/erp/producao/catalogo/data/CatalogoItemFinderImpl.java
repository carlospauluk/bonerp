package com.bonsucesso.erp.producao.catalogo.data;



import org.springframework.stereotype.Repository;

import com.bonsucesso.erp.producao.catalogo.model.CatalogoItem;
import com.ocabit.base.data.BasicEntityFinderImpl;


/**
 * Implementação de Finder para entidade CatalogoItem.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("catalogoItemFinder")
public class CatalogoItemFinderImpl extends BasicEntityFinderImpl<CatalogoItem>
		implements CatalogoItemFinder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4346709734752640122L;

}
