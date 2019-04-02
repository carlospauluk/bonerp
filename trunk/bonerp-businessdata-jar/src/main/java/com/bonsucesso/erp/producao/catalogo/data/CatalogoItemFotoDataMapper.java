package com.bonsucesso.erp.producao.catalogo.data;



import java.util.List;

import com.bonsucesso.erp.producao.catalogo.model.CatalogoItem;
import com.bonsucesso.erp.producao.catalogo.model.CatalogoItemArtigo;
import com.bonsucesso.erp.producao.catalogo.model.CatalogoItemFoto;
import com.ocabit.base.data.DataMapper;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.ArquivoUploadVO;


/**
 * Definição de DataMapper para entidade CatalogoFoto.
 *
 * @author Carlos Eduardo Pauluk
 */
public interface CatalogoItemFotoDataMapper extends DataMapper<CatalogoItemFoto> {

	public void uploadArquivos(CatalogoItem catalogoItem, List<ArquivoUploadVO> arquivos)
			throws ViewException;

	public void deleleFileAndDelete(CatalogoItemFoto catalogoFoto) throws ViewException;

	public CatalogoItemFoto saveArtigosFoto(CatalogoItemFoto foto, List<CatalogoItemArtigo> artigos)
			throws ViewException;

}
