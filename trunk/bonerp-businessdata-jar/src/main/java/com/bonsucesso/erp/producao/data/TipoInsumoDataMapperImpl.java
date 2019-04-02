package com.bonsucesso.erp.producao.data;



import org.springframework.stereotype.Repository;

import com.bonsucesso.erp.producao.model.TipoInsumo;
import com.ocabit.base.data.DataMapperImpl;


/**
 * Implementação Data Mapper para a entidade TipoInsumo.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("tipoInsumoDataMapper")
public class TipoInsumoDataMapperImpl extends DataMapperImpl<TipoInsumo> implements TipoInsumoDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = -4641454906559689878L;

}
