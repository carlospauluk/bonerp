package com.bonsucesso.erp.cortinas.data;



import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.cortinas.model.Cortina;
import com.bonsucesso.erp.orcamentos.model.Orcamento;
import com.bonsucesso.erp.orcamentos.model.OrcamentoItem;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação de Finder para entidade Cortina.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("cortinaFinder")
public class CortinaFinderImpl extends BasicEntityFinderImpl<Cortina> implements CortinaFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = 4406442873123137951L;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Cortina> findBy(Orcamento orcamento) throws ViewException {
		return getThiz().findResults("FROM Cortina WHERE orcamentoItem.grupo.orcamento = :orcamento", "orcamento", orcamento);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Cortina findBy(OrcamentoItem orcamentoItem) throws ViewException {
		return getThiz()
				.findSingleResult("FROM Cortina c WHERE c.orcamentoItem = :orcamentoItem", "orcamentoItem", orcamentoItem);
	}

}
