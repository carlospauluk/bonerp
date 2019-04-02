package com.bonsucesso.erp.estoque.data;



import java.util.List;

import com.bonsucesso.erp.estoque.model.Produto;
import com.bonsucesso.erp.estoque.model.ProdutoPreco;
import com.ocabit.base.data.DataMapper;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de DataMapper para entidade Produto.
 *
 * @author Carlos Eduardo Pauluk
 */
public interface ProdutoDataMapper extends DataMapper<Produto> {

	@Override
	public void saveList(List<Produto> lista) throws ViewException;

	public Produto saveComPreco(Produto produto, ProdutoPreco produtoPreco) throws ViewException;
}
