package com.bonsucesso.erp.ekt.data;



import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.ekt.model.EKTVendedor;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.jpa.utils.JPAUtil;

import org.apache.log4j.Logger;


/**
 * Implementação de Finder para entidade EKTVendedor.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("ektVendedorFinder")
public class EKTVendedorFinderImpl extends BasicEntityFinderImpl<EKTVendedor> implements EKTVendedorFinder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4702562317096864857L;
	
	protected static Logger logger = Logger.getLogger(EKTVendedorFinderImpl.class);

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public EKTVendedor findByCodigo(Double codigo) {
		try { 
			final String jpql = "FROM EKTVendedor WHERE codigo = :codigo";
			final Query qry = getEntityManager().createQuery(jpql);
			qry.setParameter("codigo", codigo);
			JPAUtil.cacheOn(qry);
			return (EKTVendedor) qry.getSingleResult();
		} catch (javax.persistence.NoResultException e) {
			logger.info("Nenhum vendedor encontrado para o código '" + codigo + "'", e);
			return null;
		}
	}

}
