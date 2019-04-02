package com.bonsucesso.erp.estoque.data;



import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.estoque.model.MarcacaoMercadoria;
import com.ocabit.base.data.BasicEntityFinderImpl;


/**
 * Implementação de Finder para entidade EntradaMercadoria.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("entradaMercadoriaFinder")
public class EntradaMercadoriaFinderImpl extends BasicEntityFinderImpl<MarcacaoMercadoria> implements
		EntradaMercadoriaFinder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6504815342262206577L;

	protected static Logger logger = Logger.getLogger(EntradaMercadoriaFinderImpl.class);

}
