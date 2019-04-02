package com.bonsucesso.erp.financeiro.view;



import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.financeiro.data.CarteiraDataMapper;
import com.bonsucesso.erp.financeiro.data.CarteiraFilterFinder;
import com.bonsucesso.erp.financeiro.data.CarteiraFinder;
import com.bonsucesso.erp.financeiro.model.Carteira;
import com.ocabit.base.data.filter.FilterData;
import com.ocabit.base.data.filter.FilterType;
import com.ocabit.base.view.AbstractEntityFormListFilterView;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;
import com.ocabit.utils.calendar.CalendarUtil;


/**
 * View para a entidade Carteira.
 *
 * @author Carlos Eduardo Pauluk
 */
@Component("carteiraFormListView")
@Scope("view")
public final class CarteiraFormListView extends
		AbstractEntityFormListFilterView<Carteira, CarteiraDataMapper, CarteiraFinder, CarteiraFilterFinder> {

	/**
	 *
	 */
	private static final long serialVersionUID = 4521783301385935844L;

	protected static Logger logger = Logger.getLogger(CarteiraFormListView.class);

	@PostConstruct
	public void init() {
		JSFUtils.execute("loadList()");
	}

	/**
	 * Carrega a listagem inicial.
	 */
	@Override
	public void loadInitialList() {
		try {
			setList(getFinder().findAll("descricao"));
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	@Override
	public void handleFilterDatas() {
		getFilterDatas().add(new FilterData("strPesquisa", FilterType.LIKE_ANYWHERE, "descricao"));

	}

	public void consolidarOntem(Carteira carteira) {
		setE(carteira);
		getE().setDtConsolidado(CalendarUtil.incDias(new Date(), -1));
		save();
	}
	
	public void desconsolidar(Carteira carteira) {
		setE(carteira);
		getE().setDtConsolidado(CalendarUtil.getDate(01,01,1000));
		save();
	}

	public void abrirDtConsolidao(Carteira carteira) {
		setE(carteira);
		getE().setDtConsolidado(CalendarUtil.getDate(01, 01, 1900));
		save();
	}

}
