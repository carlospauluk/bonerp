package com.bonsucesso.erp.crm.data;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.crm.model.CampanhaPromo;
import com.bonsucesso.erp.crm.model.Cliente;
import com.bonsucesso.erp.crm.model.Cupom;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação de Finder para entidade Cupom.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("cupomFinder")
public class CupomFinderImpl extends BasicEntityFinderImpl<Cupom> implements CupomFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = 3246197392380647889L;

	protected static Logger logger = Logger.getLogger(CupomFinderImpl.class);

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Cupom> findBy(Cliente cliente) throws ViewException {
		final String jpql = "FROM Cupom c WHERE c.cliente = :cliente";
		return getThiz().findResults(jpql, "cliente", cliente);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Cupom findBy(String codigo) throws ViewException {
		final String jpql = "FROM Cupom c WHERE c.codigo = :codigo";
		return getThiz().findSingleResult(jpql, "codigo", codigo);
	}

	@Override
	public Cupom findBy(Cliente cliente, CampanhaPromo campanha) throws ViewException {
		final String jpql = "FROM Cupom c WHERE c.cliente = :cliente AND c.loteCupom.campanhaPromo = :campanha";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cliente", cliente);
		params.put("campanha", campanha);
		return getThiz().findSingleResult(jpql, params);
	}
}
