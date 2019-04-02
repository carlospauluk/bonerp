package com.bonsucesso.erp.producao.catalogo.data;



import org.springframework.stereotype.Repository;

import com.bonsucesso.erp.producao.catalogo.model.CatalogoItem;
import com.ocabit.base.data.DataMapperImpl;


/**
 * Implementação de DataMapper para a entidade CatalogoItem.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("catalogoItemDataMapper")
public class CatalogoItemDataMapperImpl extends DataMapperImpl<CatalogoItem> implements CatalogoItemDataMapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5843305170573495703L;

}
