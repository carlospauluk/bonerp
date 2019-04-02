package com.bonsucesso.erp.producao.data;



import java.util.List;

import com.bonsucesso.erp.producao.model.Confeccao;
import com.bonsucesso.erp.producao.model.LoteConfeccao;
import com.bonsucesso.erp.producao.model.LoteConfeccaoItem;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade LoteConfeccao.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface LoteConfeccaoFinder extends BasicEntityFinder<LoteConfeccao> {

	public LoteConfeccao findBy(Integer codigo) throws ViewException;

	public LoteConfeccaoItem findBy(Integer loteCodigo, Integer instituicaoCodigo, Integer tipoArtigoCodigo)
			throws ViewException;

	public Integer findNextCodigo() throws ViewException;

	public List<LoteConfeccao> findComConfeccao(Confeccao confeccao);

}
