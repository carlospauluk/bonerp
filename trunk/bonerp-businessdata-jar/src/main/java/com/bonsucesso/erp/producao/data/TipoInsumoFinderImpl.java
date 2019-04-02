package com.bonsucesso.erp.producao.data;



import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.producao.model.TipoInsumo;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação de Finder para entidade TipoInsumo.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("tipoInsumoFinder")
public class TipoInsumoFinderImpl extends BasicEntityFinderImpl<TipoInsumo> implements TipoInsumoFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = -4562278902328539242L;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public TipoInsumo findByCodigo(Integer codigo) throws ViewException {
		final String jpql = "FROM TipoInsumo WHERE codigo = :codigo";
		return getThiz().findSingleResult(jpql, "codigo", codigo);
	}

}
