package com.bonsucesso.erp.estoque.results.data;



import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.bonsucesso.erp.estoque.results.model.ProdutoVendaResults;
import com.ocabit.base.data.DataMapperImpl;


/**
 * Implementação Data Mapper para a entidade ProdutoVendaResults.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("produtoVendaResultsDataMapper")
public class ProdutoVendaResultsDataMapperImpl extends DataMapperImpl<ProdutoVendaResults>
		implements ProdutoVendaResultsDataMapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1953774517587617197L;

	protected static Logger logger = Logger.getLogger(ProdutoVendaResultsDataMapperImpl.class);

}
