package com.bonsucesso.erp.orcamentos.data;



import com.bonsucesso.erp.orcamentos.model.OrcamentoGrupoItem;
import com.bonsucesso.erp.orcamentos.model.OrcamentoItem;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade OrcamentoItem.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface OrcamentoItemFinder extends BasicEntityFinder<OrcamentoItem> {

	public Integer findProximaOrdem(OrcamentoGrupoItem grupo) throws ViewException;

}
