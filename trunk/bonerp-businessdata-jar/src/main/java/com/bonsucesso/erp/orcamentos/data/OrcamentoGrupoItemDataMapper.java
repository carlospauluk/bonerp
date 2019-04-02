package com.bonsucesso.erp.orcamentos.data;



import com.bonsucesso.erp.orcamentos.model.Orcamento;
import com.bonsucesso.erp.orcamentos.model.OrcamentoGrupoItem;
import com.ocabit.base.data.DataMapper;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de DataMapper para entidade OrcamentoGrupoItem.
 *
 * @author Carlos Eduardo Pauluk
 */
public interface OrcamentoGrupoItemDataMapper extends DataMapper<OrcamentoGrupoItem> {

	public void reorderSave(Orcamento orcamento) throws ViewException;

}
