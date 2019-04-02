package com.bonsucesso.erp.producao.data;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.producao.model.Confeccao;
import com.bonsucesso.erp.producao.model.ConfeccaoItem;
import com.bonsucesso.erp.producao.model.Insumo;
import com.bonsucesso.erp.producao.model.TipoInsumo;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação de Finder para entidade Insumo.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("insumoFinder")
public class InsumoFinderImpl extends BasicEntityFinderImpl<Insumo> implements InsumoFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = -8826109086972044332L;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Insumo findByCodigo(Integer codigo) throws ViewException {
		final String jpql = "FROM Insumo WHERE codigo = :codigo";
		return getThiz().findSingleResult(jpql, "codigo", codigo);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Insumo> findByTipoInsumo(TipoInsumo tipoInsumo) throws ViewException {
		final String jpql = "FROM Insumo WHERE tipoInsumo = :tipoInsumo ORDER BY descricao";
		return getThiz().findResults(jpql, "tipoInsumo", tipoInsumo);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Insumo> findByStr(String str) throws ViewException {
		final String jpql = "FROM Insumo WHERE tipoInsumo.descricao LIKE :str OR descricao LIKE :str ORDER BY descricao";
		return getThiz().findResults(jpql, "str", "%" + str + "%");
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Insumo> findByStrAndNotInConfeccao(String str, Confeccao confeccao) throws ViewException {
		String jpql = "FROM Insumo WHERE (tipoInsumo.descricao LIKE :str OR descricao LIKE :str)";
		List<Long> notInIds = new ArrayList<Long>();
		for (ConfeccaoItem ci : confeccao.getItens()) {
			notInIds.add(ci.getInsumo().getId());
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("str", "%" + str + "%");

		if (notInIds.size() > 0) {
			jpql += " AND id NOT IN :ids";
			params.put("ids", notInIds);
		}

		jpql += " ORDER BY descricao";

		return getThiz().findResults(jpql, params);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Integer findNextCodigo() throws ViewException {
		final String jpql = "SELECT max(codigo)+1 FROM Insumo";

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

}
