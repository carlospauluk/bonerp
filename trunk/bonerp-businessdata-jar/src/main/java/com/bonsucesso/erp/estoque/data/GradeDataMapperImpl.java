package com.bonsucesso.erp.estoque.data;



import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.estoque.model.Grade;
import com.bonsucesso.erp.estoque.model.GradeTamanho;
import com.ocabit.base.data.DataMapperImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação Data Mapper para a entidade Grade.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("gradeDataMapper")
public class GradeDataMapperImpl extends DataMapperImpl<Grade> implements GradeDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = -5068989891725153314L;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED)
	public Grade beforeSave(Grade grade) throws ViewException {
		if ((grade.getId() != null) && (grade.getTamanhos() != null)) {
			for (GradeTamanho gt : grade.getTamanhos()) {
				if (gt.getOrdem() == null) {
					gt.setOrdem(grade.getTamanhos().size());
				}
			}
		}
		getEntityIdHandler().handleEntityIdFilhos(grade.getTamanhos());
		
		return grade;
	}

}
