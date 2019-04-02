package com.bonsucesso.erp.financeiro.data;



import org.springframework.stereotype.Repository;

import com.bonsucesso.erp.financeiro.model.BandeiraCartao;
import com.ocabit.base.data.DataMapperImpl;


/**
 * Implementação de DataMapper para a entidade BandeiraCartao.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("bandeiraCartaoDataMapper")
public class BandeiraCartaoDataMapperImpl extends DataMapperImpl<BandeiraCartao> implements BandeiraCartaoDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = -4283562478391954754L;

}
