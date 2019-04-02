package com.bonsucesso.erp.ekt.data;



import java.util.Date;
import java.util.List;

import com.bonsucesso.erp.ekt.model.EKTProduto;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade EKTProduto.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface EKTProdutoFinder extends BasicEntityFinder<EKTProduto> {

	public EKTProduto findByReduzido(Integer reduzido, String mesano) throws ViewException;

	public List<EKTProduto> findAllLimit(int inicio, int fim, String mesano) throws ViewException;

	public List<EKTProduto> findAtualizadosLimit(Date dtUltAlt, int inicio, int fim, String mesano) throws ViewException;

	public List<EKTProduto> findBetweenMesesAnos(Integer reduzido, Date mesAnoIni, Date mesAnoFim) throws ViewException;

}
