package com.bonsucesso.erp.ekt.data;



import com.bonsucesso.erp.ekt.model.EKTPlanoCrediario;
import com.ocabit.base.data.DataMapper;


/**
 * Definição de DataMapper para entidade EKTPlanoCrediario.
 *
 * @author Carlos Eduardo Pauluk
 */
public interface EKTPlanoCrediarioDataMapper extends DataMapper<EKTPlanoCrediario> {

	public void truncateTable();

}
