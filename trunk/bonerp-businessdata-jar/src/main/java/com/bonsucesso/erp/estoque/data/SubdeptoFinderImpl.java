package com.bonsucesso.erp.estoque.data;



import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.estoque.model.Departamento;
import com.bonsucesso.erp.estoque.model.Fornecedor;
import com.bonsucesso.erp.estoque.model.Subdepartamento;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jpa.utils.JPAUtil;


/**
 * Implementação de Finder para entidade Subdepartamento.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("subdeptoFinder")
public class SubdeptoFinderImpl extends BasicEntityFinderImpl<Subdepartamento> implements SubdeptoFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = -8999285779761040578L;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<SubdeptoFinder> findSubdeptosByDepto(Departamento depto) {
		final String jpql = "FROM Subdepartamento WHERE depto = :depto";
		final Query qry = getEntityManager().createQuery(jpql);
		JPAUtil.cacheOn(qry);
		qry.setParameter("depto", depto);
		return qry.getResultList();
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Subdepartamento findByNome(String nome) throws ViewException {
		final String jpql = "FROM Subdepartamento WHERE nome LIKE :nome";
		return getThiz().findSingleResult(jpql, "nome", nome);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Subdepartamento findByCodigo(Integer codigo) throws ViewException {
		final String jpql = "FROM Subdepartamento WHERE codigo = :codigo";
		return getThiz().findSingleResult(jpql, "codigo", codigo);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Subdepartamento> findByFornecedor(Fornecedor fornecedor) throws ViewException {
		try {
			final String jpql = "SELECT distinct(p.subdepto) FROM Produto p WHERE p.fornecedor = :fornecedor ORDER BY p.subdepto.codigo";
			final Query qry = getEntityManager().createQuery(jpql);
			JPAUtil.cacheOn(qry);
			qry.setParameter("fornecedor", fornecedor);
			return qry.getResultList();
		} catch (Exception e) {
			logger.error(e);
			throw new ViewException(e);
		}
	}

}
