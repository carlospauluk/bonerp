package com.bonsucesso.erp.producao.data;



import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.crm.model.Cliente;
import com.bonsucesso.erp.estoque.model.Fornecedor;
import com.bonsucesso.erp.producao.model.Instituicao;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jpa.utils.JPAUtil;


/**
 * Implementação de Finder para entidade Instituicao.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("instituicaoFinder")
public class InstituicaoFinderImpl extends BasicEntityFinderImpl<Instituicao> implements InstituicaoFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = 8868292556199557467L;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Instituicao findByCodigo(Integer codigo) throws ViewException {
		final String jpql = "FROM Instituicao WHERE codigo = :codigo";
		return getThiz().findSingleResult(jpql, "codigo", codigo);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Instituicao> findInstituicoesFornecedores() throws ViewException {
		final String sql = "SELECT i.* FROM prod_instituicao i LEFT JOIN est_fornecedor f ON i.fornecedor_id = f.id WHERE i.fornecedor_id IS NOT NULL ORDER BY f.codigo";
		
		try {
			final Query qry = getEntityManager().createNativeQuery(sql, Instituicao.class);
			return qry.getResultList();
		} catch (NoResultException e) {
			logger.info("Nenhum resultado encontrado");
			return null;
		}
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Instituicao> findBy(String nome) throws ViewException {
		final String jpql = "FROM Instituicao WHERE nome LIKE :nome ORDER BY nome";
		return getThiz().findResults(jpql, "nome", "%" + nome + "%");
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Integer findNextCodigo() throws ViewException {
		final String jpql = "SELECT max(codigo)+1 FROM Instituicao";

		try {
			final Query qry = getEntityManager().createQuery(jpql);
			return (Integer) qry.getSingleResult();
		} catch (NoResultException e) {
			logger.info("Nenhum resultado encontrado");
			return null;
		} catch (NonUniqueResultException e) {
			logger.error("Mais de um resultado encontrado", e);
			throw new ViewException("Erro ao buscar elemento único (" + getGenericType().getSimpleName() + ")", e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Cliente> findVinculosClientesPossiveis(String str) throws ViewException {
		try {
			final String jpqlClientesJaVinculados = "SELECT i.cliente FROM Instituicao i WHERE i.cliente IS NOT NULL";
			final Query qryClientesJaVinculados = getEntityManager()
					.createQuery(jpqlClientesJaVinculados, Cliente.class);
			JPAUtil.cacheOn(qryClientesJaVinculados);

			List<Cliente> clientesJaVinculados = qryClientesJaVinculados.getResultList();
			List<Long> idsJaVinculados = new ArrayList<Long>();

			idsJaVinculados.add(-100000000000000l);
			// monta a lista de ids
			for (Cliente c : clientesJaVinculados) {
				idsJaVinculados.add(c.getId());
			}

			// pesquisa fazendo um not in

			final String jpql = "FROM Cliente WHERE (pessoa.nome LIKE :str OR pessoa.nomeFantasia LIKE :str) AND id NOT IN :idsJaVinculados";
			final Query qry = getEntityManager().createQuery(jpql);

			qry.setParameter("str", "%" + str + "%");
			qry.setParameter("idsJaVinculados", idsJaVinculados);

			JPAUtil.cacheOn(qry);

			return qry.getResultList();
		} catch (NoResultException e) {
			logger.info("Nenhum resultado encontrado");
			return null;
		} catch (NonUniqueResultException e) {
			logger.error("Mais de um resultado encontrado", e);
			throw new ViewException("Erro ao buscar elemento único (" + getGenericType().getSimpleName() + ")", e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Fornecedor> findVinculosFornecedoresPossiveis(String str) throws ViewException {
		try {
			final String jpqlFornecedoresJaVinculados = "SELECT i.fornecedor FROM Instituicao i WHERE i.fornecedor IS NOT NULL";
			final Query qryFornecedoresJaVinculados = getEntityManager()
					.createQuery(jpqlFornecedoresJaVinculados, Fornecedor.class);
			JPAUtil.cacheOn(qryFornecedoresJaVinculados);

			List<Fornecedor> fornecedoresJaVinculados = qryFornecedoresJaVinculados.getResultList();
			List<Long> idsJaVinculados = new ArrayList<Long>();
			idsJaVinculados.add(-100000000000000l);
			// monta a lista de ids
			for (Fornecedor f : fornecedoresJaVinculados) {
				idsJaVinculados.add(f.getId());
			}

			// pesquisa fazendo um not in
			final String jpql = "FROM Fornecedor WHERE (pessoa.nome LIKE :str OR pessoa.nomeFantasia LIKE :str) AND id NOT IN :idsJaVinculados";
			final Query qry = getEntityManager().createQuery(jpql);

			qry.setParameter("str", "%" + str + "%");
			qry.setParameter("idsJaVinculados", idsJaVinculados);

			JPAUtil.cacheOn(qry);

			return qry.getResultList();
		} catch (NoResultException e) {
			logger.info("Nenhum resultado encontrado");
			return null;
		} catch (NonUniqueResultException e) {
			logger.error("Mais de um resultado encontrado", e);
			throw new ViewException("Erro ao buscar elemento único (" + getGenericType().getSimpleName() + ")", e);
		}
	}

}
