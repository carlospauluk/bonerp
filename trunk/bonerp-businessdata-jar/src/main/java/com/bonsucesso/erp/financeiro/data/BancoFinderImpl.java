package com.bonsucesso.erp.financeiro.data;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.financeiro.model.Banco;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação de Finder para entidade Banco.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("bancoFinder")
public class BancoFinderImpl extends BasicEntityFinderImpl<Banco> implements BancoFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = -8220135737478331515L;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Banco> findBy(String str, Boolean utilizado) throws ViewException {
		final String jpql;
		final Map<String, Object> params;
		String utilizadoSQL = "";
		if (utilizado.equals(Boolean.TRUE)) {
			utilizadoSQL = " AND utilizado = true ";
		}
		jpql = "FROM Banco b WHERE (b.nome LIKE :str OR TRIM(b.codigoBanco) LIKE :str) " + utilizadoSQL
				+ " ORDER BY b.codigoBanco";
		params = new HashMap<String, Object>();
		params.put("str", "%" + str + "%");
		return getThiz().findResults(jpql, params);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Banco findBy(Integer codigo) throws ViewException {
		final String jpql = "FROM Banco b WHERE b.codigoBanco = :codigo";
		return getThiz().findSingleResult(jpql, "codigo", codigo);
	}

}
