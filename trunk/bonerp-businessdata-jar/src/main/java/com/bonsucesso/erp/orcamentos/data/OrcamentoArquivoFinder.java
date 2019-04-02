package com.bonsucesso.erp.orcamentos.data;



import java.util.List;

import com.bonsucesso.erp.orcamentos.model.Orcamento;
import com.bonsucesso.erp.orcamentos.model.OrcamentoArquivo;
import com.ocabit.base.data.BasicEntityFinder;


/**
 * Definição de Finder para a entidade OrcamentoArquivo.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface OrcamentoArquivoFinder extends BasicEntityFinder<OrcamentoArquivo> {

	public List<OrcamentoArquivo> findBy(Orcamento orcamento);

}
