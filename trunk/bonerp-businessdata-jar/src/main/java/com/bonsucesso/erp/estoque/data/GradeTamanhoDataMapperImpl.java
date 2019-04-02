package com.bonsucesso.erp.estoque.data;



import org.springframework.stereotype.Repository;

import com.bonsucesso.erp.estoque.model.GradeTamanho;
import com.ocabit.base.data.DataMapperImpl;


/**
 * Implementação Data Mapper para a entidade GradeTamanho.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("gradeTamanhoDataMapper")
public class GradeTamanhoDataMapperImpl extends DataMapperImpl<GradeTamanho> implements GradeTamanhoDataMapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6386226548058995216L;

}
