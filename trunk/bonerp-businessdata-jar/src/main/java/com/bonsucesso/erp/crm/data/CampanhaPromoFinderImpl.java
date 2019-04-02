package com.bonsucesso.erp.crm.data;



import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.crm.model.CampanhaPromo;
import com.ocabit.base.data.BasicEntityFinderImpl;


/**
 * Implementação de Finder para entidade CampanhaPromo.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("campanhaPromoFinder")
public class CampanhaPromoFinderImpl extends BasicEntityFinderImpl<CampanhaPromo> implements CampanhaPromoFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = 2935865275471112212L;

	protected static Logger logger = Logger.getLogger(CampanhaPromoFinderImpl.class);

}
