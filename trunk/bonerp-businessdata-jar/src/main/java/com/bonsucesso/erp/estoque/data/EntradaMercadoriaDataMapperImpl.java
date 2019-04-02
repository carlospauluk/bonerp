package com.bonsucesso.erp.estoque.data;



import org.springframework.stereotype.Repository;

import com.bonsucesso.erp.estoque.model.MarcacaoMercadoria;
import com.ocabit.base.data.DataMapperImpl;


/**
 * Implementação Data Mapper para a entidade EntradaMercadoria.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("entradaMercadoriaDataMapper")
public class EntradaMercadoriaDataMapperImpl extends DataMapperImpl<MarcacaoMercadoria> implements
		EntradaMercadoriaDataMapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2894438764872376047L;

}
