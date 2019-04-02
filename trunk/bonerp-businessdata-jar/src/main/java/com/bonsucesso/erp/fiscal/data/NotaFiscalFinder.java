package com.bonsucesso.erp.fiscal.data;



import java.util.List;

import com.bonsucesso.erp.base.model.Pessoa;
import com.bonsucesso.erp.fiscal.model.NotaFiscal;
import com.bonsucesso.erp.fiscal.model.TipoNotaFiscal;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade NotaFiscal.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface NotaFiscalFinder extends BasicEntityFinder<NotaFiscal> {

	public NotaFiscal findBy(Integer numero, Integer serie, Pessoa pessoa) throws ViewException;

	public List<NotaFiscal> findBy(Integer status) throws ViewException;

	/**
	 * 
	 * @param producao
	 * @param serie
	 * @param nfce
	 * @return
	 * @throws ViewException
	 */
	public Integer findProxNumFiscal(boolean producao, int serie, TipoNotaFiscal nfce) throws ViewException;
	
	

}
