package com.bonsucesso.erp.ekt.data;



import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.ekt.model.EKTGrade;
import com.ocabit.base.data.DataMapperImpl;


/**
 * Implementação DataMapper para a entidade EKTGrade.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("ektGradeDataMapper")
public class EKTGradeDataMapperImpl extends DataMapperImpl<EKTGrade> implements EKTGradeDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = -8187838929591537013L;
	
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void truncateTable() {
		getEntityManager().createNativeQuery("TRUNCATE TABLE ekt_grade").executeUpdate();
	}

}
