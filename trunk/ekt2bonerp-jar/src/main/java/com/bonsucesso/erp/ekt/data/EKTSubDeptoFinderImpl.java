package com.bonsucesso.erp.ekt.data;



import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.ekt.model.EKTSubDepto;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação de Finder para entidade Datatables.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("ektSubDeptoFinder")
public class EKTSubDeptoFinderImpl extends BasicEntityFinderImpl<EKTSubDepto> implements EKTSubDeptoFinder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5405356463083445511L;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public EKTSubDepto findByCodigo(Double codigo) throws ViewException {
		final String jpql = "FROM EKTSubDepto WHERE codigo = :codigo";
		return getThiz().findSingleResult(jpql, "codigo", codigo);
	}

}
