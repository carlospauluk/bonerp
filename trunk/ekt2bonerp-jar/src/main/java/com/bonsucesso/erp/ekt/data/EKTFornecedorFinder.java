package com.bonsucesso.erp.ekt.data;



import java.util.Date;
import java.util.List;

import com.bonsucesso.erp.ekt.model.EKTFornecedor;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade EKTFornecedor.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface EKTFornecedorFinder extends BasicEntityFinder<EKTFornecedor> {

	public EKTFornecedor findByCodigo(Double codigo) throws ViewException;

	public List<EKTFornecedor> findAlterados(Date aPartirDe) throws ViewException;

}
