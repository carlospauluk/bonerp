package com.bonsucesso.erp.ekt.data;



import java.util.Date;

import com.bonsucesso.erp.ekt.model.EKTPedido;
import com.ocabit.base.data.DataMapper;


/**
 * Definição de DataMapper para entidade EKTPedido.
 *
 * @author Carlos Eduardo Pauluk
 */
public interface EKTPedidoDataMapper extends DataMapper<EKTPedido> {

	public void truncateTable();

	public void deleteEmissaoMaiorQue(Date emissao);

}
