package com.bonsucesso.erp.rh.data;



import org.springframework.stereotype.Component;

import com.bonsucesso.erp.rh.model.Funcionario;
import com.ocabit.base.data.FilterEntityFinderImpl;


/**
 * Implementação de FilterFinder para entidade Funcionario.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("funcionarioFilterFinder")
public class FuncionarioFilterFinderImpl extends FilterEntityFinderImpl<Funcionario>
		implements FuncionarioFilterFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = -4404242484968647015L;

}
