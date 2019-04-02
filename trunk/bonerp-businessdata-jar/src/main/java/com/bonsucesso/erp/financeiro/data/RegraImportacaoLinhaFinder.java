package com.bonsucesso.erp.financeiro.data;



import java.util.List;

import com.bonsucesso.erp.financeiro.model.Carteira;
import com.bonsucesso.erp.financeiro.model.RegraImportacaoLinha;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade RegraImportacaoLinha.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface RegraImportacaoLinhaFinder extends BasicEntityFinder<RegraImportacaoLinha> {

	public List<RegraImportacaoLinha> findAllBy(Carteira carteira) throws ViewException;
	
}
