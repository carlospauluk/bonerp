package com.bonsucesso.erp.cortinas.data;



import java.util.List;

import com.bonsucesso.erp.cortinas.model.Cortina;
import com.bonsucesso.erp.orcamentos.model.Orcamento;
import com.bonsucesso.erp.orcamentos.model.OrcamentoItem;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade Cortina.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface CortinaFinder extends BasicEntityFinder<Cortina> {

	public List<Cortina> findBy(Orcamento orcamento) throws ViewException;

	public Cortina findBy(OrcamentoItem orcamentoItem) throws ViewException;

}
