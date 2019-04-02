package com.bonsucesso.erp.cortinas.data;



import java.util.List;

import com.bonsucesso.erp.cortinas.model.ArtigoCortina;
import com.bonsucesso.erp.estoque.model.ProdutoPreco;
import com.ocabit.base.data.DataMapper;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de DataMapper para entidade ArtigoCortina.
 *
 * @author Carlos Eduardo Pauluk
 */
public interface ArtigoCortinaDataMapper extends DataMapper<ArtigoCortina> {

	@Override
	public void saveList(List<ArtigoCortina> lista) throws ViewException;

	public ArtigoCortina saveComPreco(ArtigoCortina artigoCortina, ProdutoPreco produtoPreco) throws ViewException;

}
