package com.bonsucesso.erp.ekt.data;



import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.ekt.model.EKTDepto;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação de Finder para entidade Datatables.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("ektDeptoFinder")
public class EKTDeptoFinderImpl extends BasicEntityFinderImpl<EKTDepto> implements EKTDeptoFinder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2747677430002073438L;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public EKTDepto findByCodigo(Double codigo) throws ViewException {
		final String jpql = "FROM EKTDepto WHERE codigo = :codigo";
		return getThiz().findSingleResult(jpql, "codigo", codigo);
	}

}
