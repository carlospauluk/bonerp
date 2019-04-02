package com.bonsucesso.erp.producao.catalogo.data;



import java.util.List;

import com.bonsucesso.erp.producao.catalogo.model.CatalogoItem;
import com.bonsucesso.erp.producao.catalogo.model.CatalogoItemArtigo;
import com.bonsucesso.erp.producao.model.TipoArtigo;
import com.ocabit.base.data.DataMapper;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de DataMapper para entidade CatalogoItemArtigo.
 *
 * @author Carlos Eduardo Pauluk
 */
public interface CatalogoItemArtigoDataMapper extends DataMapper<CatalogoItemArtigo> {

	public void saveArtigosItem(CatalogoItem item, List<TipoArtigo> tiposArtigos) throws ViewException;

}
