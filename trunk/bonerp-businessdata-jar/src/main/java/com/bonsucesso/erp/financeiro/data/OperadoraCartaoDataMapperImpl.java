package com.bonsucesso.erp.financeiro.data;



import org.springframework.stereotype.Repository;

import com.bonsucesso.erp.financeiro.model.OperadoraCartao;
import com.ocabit.base.data.DataMapperImpl;


/**
 * Implementação de DataMapper para a entidade OperadoraCartao.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("operadoraCartaoDataMapper")
public class OperadoraCartaoDataMapperImpl extends DataMapperImpl<OperadoraCartao>
		implements OperadoraCartaoDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = -7607870033614603521L;

}
