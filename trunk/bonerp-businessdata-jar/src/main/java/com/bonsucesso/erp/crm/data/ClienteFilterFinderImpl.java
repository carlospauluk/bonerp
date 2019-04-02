package com.bonsucesso.erp.crm.data;



import org.springframework.stereotype.Component;

import com.bonsucesso.erp.crm.model.Cliente;
import com.ocabit.base.data.FilterEntityFinderImpl;


/**
 * Implementação de Finder para entidade Cliente.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("clienteFilterFinder")
public class ClienteFilterFinderImpl extends FilterEntityFinderImpl<Cliente> implements ClienteFilterFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = 2608322512152458165L;

}
