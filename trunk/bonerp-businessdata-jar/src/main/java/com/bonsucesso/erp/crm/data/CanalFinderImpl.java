package com.bonsucesso.erp.crm.data;



import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.crm.model.Canal;
import com.ocabit.base.data.BasicEntityFinderImpl;


/**
 * Implementação de Finder para entidade Canal.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("canalFinder")
public class CanalFinderImpl extends BasicEntityFinderImpl<Canal> implements CanalFinder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -816406597094684674L;

	protected static Logger logger = Logger.getLogger(CanalFinderImpl.class);

}
