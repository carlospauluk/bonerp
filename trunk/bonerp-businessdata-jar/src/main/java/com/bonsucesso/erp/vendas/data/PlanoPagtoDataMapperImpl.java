package com.bonsucesso.erp.vendas.data;



import org.springframework.stereotype.Repository;

import com.bonsucesso.erp.vendas.model.PlanoPagto;
import com.ocabit.base.data.DataMapperImpl;


/**
 * Implementação de DataMapper para a entidade PlanoPagto.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("planoPagtoDataMapper")
public class PlanoPagtoDataMapperImpl extends DataMapperImpl<PlanoPagto> implements PlanoPagtoDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = -4115480823360719839L;

}
