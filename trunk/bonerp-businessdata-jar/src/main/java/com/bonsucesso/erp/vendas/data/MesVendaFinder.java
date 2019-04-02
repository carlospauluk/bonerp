package com.bonsucesso.erp.vendas.data;



import java.math.BigDecimal;
import java.util.Date;

import com.bonsucesso.erp.rh.model.Funcionario;
import com.bonsucesso.erp.vendas.model.MesVenda;
import com.bonsucesso.erp.vendas.model.MesVendaItem;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade MesVenda.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface MesVendaFinder extends BasicEntityFinder<MesVenda> {

	public MesVenda findByMesAno(Date mesAno) throws ViewException;

	/**
	 * Calcula a meta mínima de determinado mesAno.
	 * A fórmula utilizada é: total no ano anterior + inflação
	 *
	 * @param mesAno
	 * @return
	 * @throws ViewException
	 */
	public BigDecimal findMetaMinima(Date mesAno) throws ViewException;

	/**
	 * Encontra o último mês lançado.
	 *
	 * @return
	 * @throws ViewException
	 */
	public MesVenda findUltimoMesCadastrado() throws ViewException;

	/**
	 * Retorna a média das posições alcançadas pelo vendedor nos últimos meses (arredonda sempre para cima).
	 * 
	 * @param vendedor
	 * @param desde
	 * @param qtdeMeses
	 * @return
	 * @throws ViewException
	 */
	public Integer findMediaUltimosMeses(Funcionario vendedor, Date desde, int qtdeMeses) throws ViewException;

	/**
	 * 
	 * @param mesAno
	 * @return
	 * @throws ViewException
	 */
	public Integer findQtdeVendedoresConsideraMes(Date mesAno) throws ViewException;
	
	/**
	 * 
	 * @param vendedor
	 * @param mesAno
	 * @return
	 * @throws ViewException
	 */
	public MesVendaItem findMesVendaItemBy(Funcionario vendedor, Date mesAno) throws ViewException;

}
