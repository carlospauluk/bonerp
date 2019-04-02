package com.bonsucesso.erp.ekt.data;



import com.bonsucesso.erp.ekt.model.EKTVendedor;
import com.ocabit.base.data.BasicEntityFinder;


/**
 * Definição de Finder para a entidade EKTVendedor.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface EKTVendedorFinder extends BasicEntityFinder<EKTVendedor> {

	public EKTVendedor findByCodigo(Double codigo);

}
