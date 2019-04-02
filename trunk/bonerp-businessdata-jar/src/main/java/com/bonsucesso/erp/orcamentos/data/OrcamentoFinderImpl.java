package com.bonsucesso.erp.orcamentos.data;



import org.springframework.stereotype.Component;

import com.bonsucesso.erp.orcamentos.model.Orcamento;
import com.ocabit.base.data.BasicEntityFinderImpl;


/**
 * Implementação de Finder para entidade Orcamento.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("orcamentoFinder")
public class OrcamentoFinderImpl extends BasicEntityFinderImpl<Orcamento> implements OrcamentoFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = 1627907000693877200L;

}
