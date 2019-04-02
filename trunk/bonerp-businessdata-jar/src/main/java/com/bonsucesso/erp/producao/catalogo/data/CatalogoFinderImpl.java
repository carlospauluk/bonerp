package com.bonsucesso.erp.producao.catalogo.data;



import org.springframework.stereotype.Repository;

import com.bonsucesso.erp.producao.catalogo.model.Catalogo;
import com.ocabit.base.data.BasicEntityFinderImpl;


/**
 * Implementação de Finder para entidade Catalogo.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("catalogoFinder")
public class CatalogoFinderImpl extends BasicEntityFinderImpl<Catalogo>
		implements CatalogoFinder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6348120282983366069L;

}
