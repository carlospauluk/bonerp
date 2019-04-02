package com.bonsucesso.erp.ekt.data;



import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.ekt.model.EKTGrade;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação de Finder para entidade EKTGrade.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("ektGradeFinder")
public class EKTGradeFinderImpl extends BasicEntityFinderImpl<EKTGrade> implements EKTGradeFinder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1459166798243375775L;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public EKTGrade findByCodigo(Double codigo) throws ViewException {
		final String jpql = "FROM EKTGrade WHERE codigo = :codigo";
		return getThiz().findSingleResult(jpql, "codigo", codigo);
	}

}
