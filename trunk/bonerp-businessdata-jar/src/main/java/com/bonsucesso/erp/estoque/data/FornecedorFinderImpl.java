package com.bonsucesso.erp.estoque.data;



import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.base.model.Pessoa;
import com.bonsucesso.erp.estoque.model.Fornecedor;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação de Finder para entidade Fornecedor.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("fornecedorFinder")
public class FornecedorFinderImpl extends BasicEntityFinderImpl<Fornecedor> implements FornecedorFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = -3910408041548608212L;

	protected static Logger logger = Logger.getLogger(FornecedorFinderImpl.class);

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Fornecedor> findByStr(String nomes) throws ViewException {
		final String jpql = "FROM Fornecedor f WHERE f.codigo LIKE :codigo OR f.pessoa.nomeFantasia LIKE :nomes OR f.pessoa.nome LIKE :nomes";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nomes", "%" + nomes + "%");
		try {
			params.put("codigo", Integer.valueOf(nomes));
		} catch (NumberFormatException e) {
			params.put("codigo", -9999999);
		}
		return getThiz().findResults(jpql, params);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Fornecedor findByNomeFantasia(String nomeFantasia) throws ViewException {
		final String jpql = "FROM Fornecedor f WHERE f.pessoa.nomeFantasia LIKE :nomeFantasia";
		return getThiz().findSingleResult(jpql, "nomeFantasia", "%" + nomeFantasia + "%");
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Fornecedor findBy(Pessoa pessoa) throws ViewException {
		final String jpql = "FROM Fornecedor f WHERE f.pessoa = :pessoa";
		return getThiz().findSingleResult(jpql, "pessoa", pessoa);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Fornecedor findByCodigo(Integer codigo) throws ViewException {
		final String jpql = "FROM Fornecedor f WHERE f.codigo = :codigo";
		return getThiz().findSingleResult(jpql, "codigo", codigo);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Fornecedor findByDoc(String doc) throws ViewException {
		final String jpql = "FROM Fornecedor f WHERE f.pessoa.documento = :doc";
		return getThiz().findSingleResult(jpql, "doc", doc, false);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Fornecedor> findAtuaisByTipo(String tipoFornecedorDescricao) throws ViewException {
		final String sql = "SELECT f.* FROM "
				+ "est_fornecedor f, "
				+ "est_fornecedor_tipo tipo "
				+ "WHERE "
				+ "f.fornecedor_tipo_id = tipo.id AND "
				+ "tipo.descricao = :tipoFornecedorDescricao AND "
				+ "f.codigo_ekt_ate IS NULL "
				+ "ORDER BY f.codigo_ekt";
		
		Query qry = getEntityManager().createNativeQuery(sql, Fornecedor.class);
		qry.setParameter("tipoFornecedorDescricao", tipoFornecedorDescricao);
		
		return qry.getResultList();
	}

	/**
	 * Encontra o fornecedor que estava sob o codigoEKT em dtImportacao.
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Fornecedor findByCodigoEKT(Integer codigoEKT, Date dtImportacao) throws ViewException {
		final String jpql = "SELECT f.id FROM est_fornecedor f "
				+ "WHERE codigo_ekt = :codigoEKT AND "
				+ "codigo_ekt_desde <= :dtImportacao AND (codigo_ekt_ate IS NULL OR codigo_ekt_ate >= :dtImportacao)";

		Query qry = getEntityManager().createNativeQuery(jpql);
		qry.setParameter("codigoEKT", codigoEKT);
		qry.setParameter("dtImportacao", dtImportacao);

		List<BigInteger> rs = qry.getResultList();

		if (rs == null || rs.size() < 1) {
			throw new ViewException("Nenhum fornecedor encontrado.");
		} else {
			if (rs.size() > 1) {
				throw new ViewException("Mais de um fornecedor encontrado.");
			} else {
				return getThiz().findById(rs.get(0).longValue());
			}
		}
	}

}
