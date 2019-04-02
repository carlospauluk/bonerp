package com.bonsucesso.erp.ekt.data;



import com.bonsucesso.erp.ekt.model.EKTGrade;
import com.ocabit.base.data.DataMapper;


/**
 * Definição de DataMapper para entidade EKTGrade.
 *
 * @author Carlos Eduardo Pauluk
 */
public interface EKTGradeDataMapper extends DataMapper<EKTGrade> {
	
	public void truncateTable();

}
