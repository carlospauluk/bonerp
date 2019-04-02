package com.bonsucesso.erp.ekt.data;



import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.ekt.model.EKTPlanoPagto;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação de Finder para entidade EKTPlanoPagto.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("ektPlanoPagtoFinder")
public class EKTPlanoPagtoFinderImpl extends BasicEntityFinderImpl<EKTPlanoPagto> implements EKTPlanoPagtoFinder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1422104983596112582L;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public EKTPlanoPagto findByCodigo(String codigo) throws ViewException {
		final String jpql = "FROM EKTPlanoPagto WHERE codigo = :codigo";
		return getThiz().findSingleResult(jpql, "codigo", codigo);
	}

}
