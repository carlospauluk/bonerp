package com.bonsucesso.erp.crm.data;



import java.util.List;

import com.bonsucesso.erp.crm.model.CampanhaPromo;
import com.bonsucesso.erp.crm.model.Cliente;
import com.bonsucesso.erp.crm.model.Cupom;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade Cupom.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface CupomFinder extends BasicEntityFinder<Cupom> {

	public List<Cupom> findBy(Cliente cliente) throws ViewException;
	
	public Cupom findBy(Cliente cliente, CampanhaPromo campanha) throws ViewException;

	public Cupom findBy(String codigo) throws ViewException;

}
