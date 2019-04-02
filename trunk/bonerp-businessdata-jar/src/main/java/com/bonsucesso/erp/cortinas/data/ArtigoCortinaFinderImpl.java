package com.bonsucesso.erp.cortinas.data;



import java.util.List;

import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.cortinas.model.ArtigoCortina;
import com.bonsucesso.erp.cortinas.model.TipoArtigoCortina;
import com.bonsucesso.erp.estoque.model.Produto;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.utils.strings.StringUtils;


/**
 * Implementação de Finder para entidade ArtigoCortina.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("artigoCortinaFinder")
public class ArtigoCortinaFinderImpl extends BasicEntityFinderImpl<ArtigoCortina> implements ArtigoCortinaFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = -4436556606238834695L;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public ArtigoCortina findByReduzido(Long reduzido) throws ViewException {
		try {
			final String sql = "SELECT ac.* FROM crtn_artigo_cortina ac, est_produto p WHERE p.id = ac.produto_id AND p.reduzido LIKE :reduzido AND p.reduzido_ekt_ate IS NULL";
			Query qry = getEntityManager().createNativeQuery(sql, ArtigoCortina.class);
			qry.setParameter("reduzido", "%" + StringUtils.zerofill(reduzido.toString(), 6));
			return (ArtigoCortina) qry.getSingleResult();
		} catch (NonUniqueResultException e) {
			logger.error(e);
			throw new ViewException("Mais de um produto encontrado.");
		} catch (Exception e) {
			logger.error(e);
			throw new ViewException(e);
		}
	}
	
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public ArtigoCortina findBy(TipoArtigoCortina tipoArtigoCortina) throws ViewException {
		List<ArtigoCortina> r = getThiz().findResults("FROM ArtigoCortina WHERE tipoArtigoCortina = :tipoArtigoCortina", "tipoArtigoCortina", tipoArtigoCortina);
		if (r == null) {
			throw new ViewException("Nenhum registro encontrado.");
		}
		return r.get(0);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public ArtigoCortina findByProduto(Produto produto) throws ViewException {
		return getThiz().findSingleResult("FROM ArtigoCortina WHERE produto.id = :produto", "produto", produto.getId());
	}
}
