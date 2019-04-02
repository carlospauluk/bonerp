package com.bonsucesso.erp.producao.data;



import org.springframework.stereotype.Component;

import com.bonsucesso.erp.producao.model.Confeccao;
import com.ocabit.base.data.FilterEntityFinderImpl;


/**
 * Implementação de Finder para entidade Confeccao.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("confeccaoFilterFinder")
public class ConfeccaoFilterFinderImpl extends FilterEntityFinderImpl<Confeccao> implements ConfeccaoFilterFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = -7802101669837081385L;

}
