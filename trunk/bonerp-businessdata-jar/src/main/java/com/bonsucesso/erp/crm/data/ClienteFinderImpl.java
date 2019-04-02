package com.bonsucesso.erp.crm.data;



import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.base.model.Pessoa;
import com.bonsucesso.erp.crm.model.Cliente;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação de Finder para entidade Cliente.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("clienteFinder")
public class ClienteFinderImpl extends BasicEntityFinderImpl<Cliente> implements ClienteFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = 4244747995892903100L;

	protected static Logger logger = Logger.getLogger(ClienteFinderImpl.class);

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Cliente> findByStr(String str) throws ViewException {
		final String jpql = "FROM Cliente c WHERE " +
				"c.pessoa.nome LIKE :str OR " +
				"c.pessoa.nomeFantasia LIKE :str OR " +
				"c.pessoa.documento LIKE :str OR " +
				"c.codigo LIKE :str";
		return getThiz().findResults(jpql, "str", "%" + str + "%");
	}
	
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Cliente> findByNome(String nome) throws ViewException {
		final String jpql = "FROM Cliente c WHERE " +
				"c.pessoa.nome LIKE :nome OR " +
				"c.pessoa.nomeFantasia LIKE :nome";
		return getThiz().findResults(jpql, "nome", nome);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Cliente findClienteByDoc(String doc) throws ViewException {
		final String jpql = "FROM Cliente c WHERE c.pessoa.documento = :doc";
		return getThiz().findSingleResult(jpql, "doc", doc);
	}
	
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Cliente findByPessoa(Pessoa pessoa) throws ViewException {
		final String jpql = "FROM Cliente c WHERE c.pessoa = :pessoa";
		return getThiz().findSingleResult(jpql, "pessoa", pessoa);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Cliente> findSomenteComCelular() throws ViewException {
		try {
			final String sql = "SELECT * FROM crm_cliente WHERE "
					+ "fone1 regexp '^((0){0,1}([0-9]{0,2})(6|7|8|9){1}([0-9]{7,8}))$' || "
					+ "fone2 regexp '^((0){0,1}([0-9]{0,2})(6|7|8|9){1}([0-9]{7,8}))$' || "
					+ "fone3 regexp '^((0){0,1}([0-9]{0,2})(6|7|8|9){1}([0-9]{7,8}))$' || "
					+ "fone4 regexp '^((0){0,1}([0-9]{0,2})(6|7|8|9){1}([0-9]{7,8}))$'";
			
			final Query qry = getEntityManager().createNativeQuery(sql, Cliente.class);
			return qry.getResultList();
		} catch (NoResultException e) {
			logger.info("Nenhum resultado encontrado");
			return null;
		}
	}
}
