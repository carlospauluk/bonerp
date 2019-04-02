package com.bonsucesso.erp.financeiro.data;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.financeiro.model.Categoria;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação de Finder para entidade Categoria.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("categoriaFinder")
public class CategoriaFinderImpl extends BasicEntityFinderImpl<Categoria>
		implements CategoriaFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = -401312387103609985L;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Categoria> findBy(Categoria pai) throws ViewException {
		final String jpql;
		final Map<String, Object> params;
		if (pai != null) {
			jpql = "FROM Categoria c WHERE c.pai = :pai ORDER BY c.codigo";
			params = new HashMap<String, Object>();
			params.put("pai", pai);
		} else {
			jpql = "FROM Categoria c WHERE c.pai IS NULL ORDER BY c.codigo";
			params = null;
		}
		return getThiz().findResults(jpql, params);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Categoria> findBy(String descricao) throws ViewException {
		final String jpql;
		final Map<String, Object> params;
		jpql = "FROM Categoria c WHERE c.descricao LIKE :descricao OR TRIM(c.codigo) LIKE :codigo ORDER BY c.descricao";
		params = new HashMap<String, Object>();
		params.put("descricao", "%" + descricao + "%");
		params.put("codigo", descricao);
		return getThiz().findResults(jpql, params);
	}
	
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Categoria> findFolhasBy(String descricao) throws ViewException {
		final String jpql;
		final Map<String, Object> params;
		jpql = "FROM Categoria c WHERE (c.descricao LIKE :descricao OR TRIM(c.codigo) LIKE :codigo) AND id NOT IN (SELECT distinct(pai.id) FROM Categoria c2 WHERE pai.id IS NOT NULL) ORDER BY c.descricao";
		params = new HashMap<String, Object>();
		params.put("descricao", "%" + descricao + "%");
		params.put("codigo", descricao);
		return getThiz().findResults(jpql, params);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Categoria findBy(Long codigo) throws ViewException {
		final String jpql;
		final Map<String, Object> params;
		jpql = "FROM Categoria c WHERE c.codigo = :codigo";
		params = new HashMap<String, Object>();
		params.put("codigo", codigo);
		return getThiz().findSingleResult(jpql, params);
	}

}
