package com.bonsucesso.erp.base.data;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.Query;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.base.model.Pessoa;
import com.bonsucesso.erp.base.model.PessoaCadastro;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Repositório DAO para a entidade Pessoa.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("pessoaFinder")
public class PessoaFinderImpl extends BasicEntityFinderImpl<Pessoa> implements PessoaFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = 1388431523803598225L;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Pessoa> findPessoaByNome(final String value) throws ViewException {
		final String jpql = "FROM Pessoa p WHERE p.nome LIKE :value ORDER BY p.nome";
		return getThiz().findResults(jpql, "value", "%" + value.toUpperCase() + "%");
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Pessoa> findPessoaByNome(final String value, final PessoaCadastro[] pessoasCadastro)
			throws ViewException {

		Map<String, Object> params = new HashMap<String, Object>();

		String jpql;

		params.put("value", "%" + value + "%");

		List<Pessoa> r = new ArrayList<Pessoa>();

		// Se não passar nenhum, ou se passar todos...
		if (pessoasCadastro == null || pessoasCadastro.length == 0
				|| pessoasCadastro.length == PessoaCadastro.values().length) {
			jpql = "FROM Pessoa p WHERE p.nome LIKE :value OR p.nomeFantasia LIKE :value ORDER BY p.nome";
			r.addAll(getThiz().findResults(jpql, params));
		} else {
			String procurarPorCodigo = "";
			if (NumberUtils.isDigits(value)) {
				procurarPorCodigo = " OR _.codigo = :codigo ";
				params.put("codigo", NumberUtils.toInt(value));
			}

			for (PessoaCadastro pc : pessoasCadastro) {

				jpql = "SELECT _.pessoa FROM " + pc.getLabel()
						+ " _ WHERE " +
						"_.pessoa.nome LIKE :value OR " +
						"_.pessoa.nomeFantasia LIKE :value " + procurarPorCodigo +
						"ORDER BY _.pessoa.nome";

				r.addAll(getThiz().findResults(jpql, params));
			}
		}

		return r;
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Pessoa> findPessoaByNome(final String value, final PessoaCadastro pessoaCadastro)
			throws ViewException {
		return getThiz().findPessoaByNome(value, new PessoaCadastro[] { pessoaCadastro });
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Pessoa findPessoaByDocumento(final String documento) throws ViewException {
		final String jpql = "FROM Pessoa p WHERE p.documento = :documento ORDER BY iudt.inserted";
		List<Pessoa> l = getThiz().findResults(jpql, "documento", documento);
		if (l != null) {
			return l.get(0);
		}
		return null;
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Pessoa findPessoaByDocumento(final String documento, final PessoaCadastro pessoaCadastro)
			throws ViewException {

		String jpql = "SELECT _.pessoa FROM " + pessoaCadastro.getLabel()
				+ " _ WHERE " +
				"_.pessoa.documento LIKE :documento " +
				"ORDER BY _.pessoa.nome";

		return getThiz().findSingleResult(jpql, "documento", documento);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public boolean isPessoaReferenciada(final Pessoa pessoa, final String tipoAtual) {

		// Verifica se o registro da pessoa é referenciado em alguma 'subentidade'
		boolean result = false;

		final Map<String, String> sqls = new ConcurrentHashMap<String, String>();
		// FIXME: remover isto daqui
		sqls.put("Cliente", "FROM Cliente c WHERE c.pessoa = :pessoa");
		sqls.put("Fornecedor", "FROM Fornecedor c WHERE c.pessoa = :pessoa");
		sqls.put("Funcionario", "FROM Funcionario c WHERE c.pessoa = :pessoa");

		for (final Map.Entry<String, String> entry : sqls.entrySet()) {
			if (!entry.getKey().equals(tipoAtual)) {
				// pesquisa para cada item do sqls
				final Query qry = getEntityManager().createQuery(entry.getValue());
				// a pessoa
				qry.setParameter("pessoa", pessoa);
				if ((qry.getResultList() != null) && (qry.getResultList().size() > 0)) {
					result = true;
					break;
				}
			}
		}

		return result;

	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Pessoa> findBy(final String str) throws ViewException {
		final String jpql = "FROM Pessoa p WHERE p.nome LIKE :str OR p.nomeFantasia LIKE :str OR p.documento LIKE :str";
		return getThiz().findResults(jpql, "str", "%" + str + "%");
	}

	@Override
	public PessoaFinder getThiz() {
		return (PessoaFinder) super.getThiz();
	}

}
