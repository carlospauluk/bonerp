package com.bonsucesso.erp.fiscal.data;



import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.fiscal.model.MensagemRetornoRF;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação de Finder para entidade MensagemRetornoRF.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("mensagemRetornoRFFinder")
public class MensagemRetornoRFFinderImpl extends BasicEntityFinderImpl<MensagemRetornoRF> implements
		MensagemRetornoRFFinder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5391081014042632676L;

	protected static Logger logger = Logger.getLogger(MensagemRetornoRFFinderImpl.class);

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public MensagemRetornoRF findBy(Integer codigo) throws ViewException {
		final String jpql = "FROM MensagemRetornoRF WHERE codigo = :codigo";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codigo", codigo);
		return getThiz().findSingleResult(jpql, params, false);
	}

}
