package com.bonsucesso.erp.crm.data;



import org.springframework.stereotype.Repository;

import com.bonsucesso.erp.crm.model.LoteCupom;
import com.ocabit.base.data.DataMapperImpl;


/**
 * Implementação Data Mapper para a entidade LoteCupom.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("loteCupomDataMapper")
public class LoteCupomDataMapperImpl extends DataMapperImpl<LoteCupom> implements LoteCupomDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = 6621496954051600877L;

}
