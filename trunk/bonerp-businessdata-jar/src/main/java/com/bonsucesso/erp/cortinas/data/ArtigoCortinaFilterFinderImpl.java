package com.bonsucesso.erp.cortinas.data;



import org.springframework.stereotype.Component;

import com.bonsucesso.erp.cortinas.model.ArtigoCortina;
import com.ocabit.base.data.FilterEntityFinderImpl;


/**
 * Implementação de FilterFinder para entidade ArtigoCortina.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("artigoCortinaFilterFinder")
public class ArtigoCortinaFilterFinderImpl extends FilterEntityFinderImpl<ArtigoCortina> implements
		ArtigoCortinaFilterFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = -1310673717077525381L;

}
