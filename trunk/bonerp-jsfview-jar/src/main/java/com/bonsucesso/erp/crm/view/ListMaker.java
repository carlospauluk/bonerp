package com.bonsucesso.erp.crm.view;



import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.crm.data.CampanhaPromoFinder;
import com.bonsucesso.erp.crm.data.CanalFinder;
import com.bonsucesso.erp.crm.model.CampanhaPromo;
import com.bonsucesso.erp.crm.model.Canal;
import com.bonsucesso.erp.crm.model.StatusCupom;
import com.ocabit.base.view.AbstractListMaker;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;


/**
 * ListMaker para as entidades do pacote com.bonsucesso.erp.crm.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("lmCrm")
@Scope("view")
public class ListMaker implements AbstractListMaker {

	/**
	 *
	 */
	private static final long serialVersionUID = -4038040310272956765L;

	protected static Logger logger = Logger.getLogger(ListMaker.class);

	@Autowired
	private CampanhaPromoFinder campanhaPromoFinder;

	@Autowired
	private CanalFinder canalFinder;

	private List<CampanhaPromo> campanhasPromo;

	private List<StatusCupom> statusCupom;

	private List<Canal> canais;

	public CampanhaPromoFinder getCampanhaPromoFinder() {
		return campanhaPromoFinder;
	}

	public void setCampanhaPromoFinder(CampanhaPromoFinder campanhaPromoFinder) {
		this.campanhaPromoFinder = campanhaPromoFinder;
	}

	public CanalFinder getCanalFinder() {
		return canalFinder;
	}

	public void setCanalFinder(CanalFinder canalFinder) {
		this.canalFinder = canalFinder;
	}

	public List<CampanhaPromo> getCampanhasPromo() {
		if (campanhasPromo == null) {
			try {
				campanhasPromo = getCampanhaPromoFinder().findAll("descricao");
			} catch (ViewException e) {
				logger.error(e);
				JSFUtils.addHandledException(e);
			}
		}
		return campanhasPromo;
	}

	public void setCampanhasPromo(List<CampanhaPromo> campanhasPromo) {
		this.campanhasPromo = campanhasPromo;
	}

	public List<StatusCupom> getStatusCupom() {
		statusCupom = Arrays.asList(StatusCupom.values());
		return statusCupom;
	}

	public void setStatusCupom(List<StatusCupom> statusCupom) {
		this.statusCupom = statusCupom;
	}

	public List<Canal> getCanais() {
		if (canais == null) {
			try {
				canais = getCanalFinder().findAll();
			} catch (ViewException e) {
				logger.error(e);
				JSFUtils.addHandledException(e);
			}
		}
		return canais;
	}

	public void setCanais(List<Canal> canais) {
		this.canais = canais;
	}

}
