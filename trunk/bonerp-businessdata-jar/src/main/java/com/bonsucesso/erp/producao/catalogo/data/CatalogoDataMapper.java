package com.bonsucesso.erp.producao.catalogo.data;



import com.bonsucesso.erp.producao.catalogo.model.Catalogo;
import com.ocabit.base.data.DataMapper;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de DataMapper para entidade Catalogo.
 *
 * @author Carlos Eduardo Pauluk
 */
public interface CatalogoDataMapper extends DataMapper<Catalogo> {

	public void saveItens(Catalogo catalogo) throws ViewException;

	public void limparCatalogo();

}
