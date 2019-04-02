package com.bonsucesso.erp.financeiro.data;



import org.springframework.stereotype.Repository;

import com.bonsucesso.erp.financeiro.model.CentroCusto;
import com.ocabit.base.data.DataMapperImpl;


/**
 * Implementação de DataMapper para a entidade CentroCusto.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("centroCustoDataMapper")
public class CentroCustoDataMapperImpl extends DataMapperImpl<CentroCusto> implements CentroCustoDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = 5000404399789295731L;

}
