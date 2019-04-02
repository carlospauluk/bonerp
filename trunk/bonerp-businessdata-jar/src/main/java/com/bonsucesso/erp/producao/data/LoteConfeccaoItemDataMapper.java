package com.bonsucesso.erp.producao.data;



import com.bonsucesso.erp.producao.model.LoteConfeccaoItem;
import com.ocabit.base.data.DataMapper;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de DataMapper para entidade LoteConfeccaoItem.
 *
 * @author Carlos Eduardo Pauluk
 */
public interface LoteConfeccaoItemDataMapper extends DataMapper<LoteConfeccaoItem> {

	public Integer findProximaOrdem(LoteConfeccaoItem lci) throws ViewException;

}
