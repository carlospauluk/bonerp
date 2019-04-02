package com.bonsucesso.erp.base.data;



import java.util.Date;
import java.util.List;

import com.bonsucesso.erp.base.model.DiaUtil;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade DiaUtil.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface DiaUtilFinder extends BasicEntityFinder<DiaUtil> {

	public List<DiaUtil> findDiasUteisBy(Date ini, Date fim, Boolean comercial, Boolean financeiro)
			throws ViewException;

	public List<DiaUtil> findDiasUteisBy(Date ini, Date fim) throws ViewException;

	public List<DiaUtil> findDiasUteisBy(Date mesAno) throws ViewException;

	public List<DiaUtil> findDiasUteisFinanceiroBy(Date ini, Date fim) throws ViewException;

	public List<DiaUtil> findDiasUteisFinanceiroBy(Date mesAno) throws ViewException;

	public List<DiaUtil> findDiasUteisComerciaisBy(Date ini, Date fim) throws ViewException;

	public List<DiaUtil> findDiasUteisComerciaisBy(Date mesAno) throws ViewException;

	public DiaUtil findBy(Date dia) throws ViewException;

	/**
	 * Encontra, por exemplo, o quinto (ordinal=5) dia útil.
	 *
	 * @param mesAno
	 * @param ordinal
	 * @return
	 */
	public DiaUtil findBy(Date mesAno, int ordinal) throws ViewException;

	public Date findProximoDiaUtilFinanceiro(Date dia) throws ViewException;

	public Date findProximoDiaUtilComercial(Date dia) throws ViewException;

	public Date findAnteriorDiaUtilFinanceiro(Date dia) throws ViewException;

	public Date findAnteriorDiaUtilComercial(Date dia) throws ViewException;

}
