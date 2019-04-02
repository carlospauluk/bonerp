package com.bonsucesso.erp.financeiro.data;



import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.financeiro.model.Carteira;
import com.bonsucesso.erp.financeiro.model.RegistroConferencia;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação de Finder para entidade RegistroConferencia.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("registroConferenciaFinder")
public class RegistroConferenciaFinderImpl extends BasicEntityFinderImpl<RegistroConferencia>
		implements RegistroConferenciaFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = -6991659016125829336L;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public RegistroConferencia findBy(String descricao) throws ViewException {
		final String jpql = "FROM RegistroConferencia WHERE descricao = :descricao";
		return getThiz().findSingleResult(jpql, "descricao", descricao);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public RegistroConferencia findBy(String descricao, Carteira carteira) throws ViewException {
		final String jpql = "FROM RegistroConferencia WHERE descricao = :descricao AND carteira = :carteira";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("descricao", descricao);
		params.put("carteira", carteira);
		return getThiz().findSingleResult(jpql, params);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public RegistroConferencia findBy(String descricao, Date dtRegistro) throws ViewException {
		final String jpql = "FROM RegistroConferencia WHERE descricao = :descricao AND dtRegistro = :dtRegistro";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("descricao", descricao);
		params.put("dtRegistro", dtRegistro);
		return getThiz().findSingleResult(jpql, params, false);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<String> findDescricaoBy(String str) throws ViewException {
		try {
			final String jpql = "SELECT distinct rc.descricao FROM RegistroConferencia rc WHERE rc.descricao LIKE :descricaoIni ORDER BY rc.descricao";
			Query qry = getEntityManager().createQuery(jpql);
			qry.setParameter("descricaoIni", "%" + str + "%");
			return qry.getResultList();
		} catch (Exception e) {
			throw new ViewException("Erro ao pesquisar descrições", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<String> findDistincts() throws ViewException {
		try {
			final String jpql = "SELECT distinct rc.descricao FROM RegistroConferencia rc ORDER BY rc.descricao";
			Query qry = getEntityManager().createQuery(jpql);
			return qry.getResultList();
		} catch (Exception e) {
			throw new ViewException("Erro ao pesquisar descrições", e);
		}
	}
	
	

}
