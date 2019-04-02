package com.bonsucesso.erp.cortinas.data;



import com.bonsucesso.erp.cortinas.model.ArtigoCortina;
import com.bonsucesso.erp.cortinas.model.TipoArtigoCortina;
import com.bonsucesso.erp.estoque.model.Produto;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade ArtigoCortina.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface ArtigoCortinaFinder extends BasicEntityFinder<ArtigoCortina> {

	public ArtigoCortina findByReduzido(Long reduzido) throws ViewException;

	public ArtigoCortina findByProduto(Produto produto) throws ViewException;

	public ArtigoCortina findBy(TipoArtigoCortina tipoArtigoCortina) throws ViewException;

}
