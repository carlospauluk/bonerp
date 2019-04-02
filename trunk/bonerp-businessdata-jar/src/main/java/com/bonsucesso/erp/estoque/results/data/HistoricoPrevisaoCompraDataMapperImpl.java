package com.bonsucesso.erp.estoque.results.data;



import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.bonsucesso.erp.estoque.results.model.HistoricoPrevisaoCompra;
import com.ocabit.base.data.DataMapperImpl;


/**
 * Implementação Data Mapper para a entidade HistoricoPrevisaoCompra.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("historicoPrevisaoCompraDataMapper")
public class HistoricoPrevisaoCompraDataMapperImpl extends DataMapperImpl<HistoricoPrevisaoCompra>
		implements HistoricoPrevisaoCompraDataMapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3856056308838539333L;

	protected static Logger logger = Logger.getLogger(HistoricoPrevisaoCompraDataMapperImpl.class);

}
