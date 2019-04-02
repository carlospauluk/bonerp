package com.bonsucesso.erp.financeiro.data;



import org.springframework.stereotype.Repository;

import com.bonsucesso.erp.financeiro.model.Banco;
import com.ocabit.base.data.DataMapperImpl;


/**
 * Implementação de DataMapper para a entidade Banco.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("bancoDataMapper")
public class BancoDataMapperImpl extends DataMapperImpl<Banco> implements BancoDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = 7045284360836631742L;
}
