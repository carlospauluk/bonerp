package com.bonsucesso.erp.estoque.data;



import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.estoque.model.Departamento;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação de Finder para entidade Datatables.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("deptoFinder")
public class DeptoFinderImpl extends BasicEntityFinderImpl<Departamento> implements DeptoFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = 7428464117167769728L;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Departamento findByCodigo(Integer codigo) throws ViewException {
		final String jpql = "FROM Departamento WHERE codigo = :codigo";
		return getThiz().findSingleResult(jpql, "codigo", codigo);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Departamento> findDeptosArtigosCortina() throws ViewException {
		final String jpql = "FROM Departamento WHERE codigo IN (11,12)";
		return getThiz().findResults(jpql, null);
	}

}
