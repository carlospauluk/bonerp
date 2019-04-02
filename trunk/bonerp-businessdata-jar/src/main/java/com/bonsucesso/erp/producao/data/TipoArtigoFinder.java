package com.bonsucesso.erp.producao.data;



import java.util.List;

import com.bonsucesso.erp.producao.model.Instituicao;
import com.bonsucesso.erp.producao.model.TipoArtigo;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade TipoArtigo.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface TipoArtigoFinder extends BasicEntityFinder<TipoArtigo> {

	public TipoArtigo findByCodigo(Integer codigo) throws ViewException;

	public List<TipoArtigo> findByInstituicao(Instituicao instituicao) throws ViewException;

	public Integer findNextCodigo() throws ViewException;

}
