package com.bonsucesso.erp.financeiro.data;



import org.springframework.stereotype.Repository;

import com.bonsucesso.erp.financeiro.model.Carteira;
import com.ocabit.base.data.DataMapperImpl;


/**
 * Implementação de DataMapper para a entidade Carteira.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("carteiraDataMapper")
public class CarteiraDataMapperImpl extends DataMapperImpl<Carteira> implements CarteiraDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = 4733210309825233937L;

}
