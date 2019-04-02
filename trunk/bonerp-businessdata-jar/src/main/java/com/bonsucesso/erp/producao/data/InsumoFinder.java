package com.bonsucesso.erp.producao.data;



import java.util.List;

import com.bonsucesso.erp.producao.model.Confeccao;
import com.bonsucesso.erp.producao.model.Insumo;
import com.bonsucesso.erp.producao.model.TipoInsumo;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade Insumo.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface InsumoFinder extends BasicEntityFinder<Insumo> {

	public Insumo findByCodigo(Integer codigo) throws ViewException;

	public List<Insumo> findByTipoInsumo(TipoInsumo tipoInsumo) throws ViewException;

	public List<Insumo> findByStr(String str) throws ViewException;

	public List<Insumo> findByStrAndNotInConfeccao(String str, Confeccao confeccao) throws ViewException;

	public Integer findNextCodigo() throws ViewException;

}
