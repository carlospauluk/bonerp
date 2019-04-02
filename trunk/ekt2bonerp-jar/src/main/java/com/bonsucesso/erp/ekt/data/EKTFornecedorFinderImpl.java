package com.bonsucesso.erp.ekt.data;



import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.ekt.model.EKTFornecedor;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação de Finder para entidade EKTGrade.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("ektFornecedorFinder")
public class EKTFornecedorFinderImpl extends BasicEntityFinderImpl<EKTFornecedor> implements EKTFornecedorFinder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1832234010618587633L;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public EKTFornecedor findByCodigo(Double codigo) throws ViewException {
		final String jpql = "FROM EKTFornecedor WHERE codigo = :codigo";
		return getThiz().findSingleResult(jpql, "codigo", codigo);
	}

	@Override
	public List<EKTFornecedor> findAlterados(Date aPartirDe) throws ViewException {
		String jpql = "FROM EKTFornecedor WHERE dtUltAlt IS NOT NULL";

		Map<String, Object> params = null;

		if (aPartirDe != null) {
			jpql += " AND dtUltAlt >= :aPartirDe";
			params = new HashMap<String, Object>();
			params.put("aPartirDe", aPartirDe);
		}
		jpql += " ORDER BY codigo";

		return getThiz().findResults(jpql, params);
	}

}
