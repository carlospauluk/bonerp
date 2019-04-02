package com.bonsucesso.erp.producao.view;



import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.producao.data.ConfeccaoDataMapper;
import com.bonsucesso.erp.producao.data.ConfeccaoFilterFinder;
import com.bonsucesso.erp.producao.data.ConfeccaoFinder;
import com.bonsucesso.erp.producao.model.Confeccao;
import com.bonsucesso.erp.producao.model.LoteConfeccao;
import com.ocabit.base.data.FilterEntityFinder;
import com.ocabit.base.data.filter.FilterData;
import com.ocabit.base.data.filter.FilterType;
import com.ocabit.base.view.AbstractEntityListView;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;


/**
 * ListView para a entidade Confeccao.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("confeccaoListView")
@Scope("view")
public class ConfeccaoListView extends AbstractEntityListView<Confeccao, ConfeccaoDataMapper, ConfeccaoFinder> {

	/**
	 *
	 */
	private static final long serialVersionUID = 3244916011022804570L;

	protected static Logger logger = Logger.getLogger(ConfeccaoListView.class);

	@Autowired
	private ConfeccaoFilterFinder confeccaoFilterFinder;

	@PostConstruct
	public void init() {
		JSFUtils.execute("loadList()");
	}

	public ConfeccaoFilterFinder getConfeccaoFilterFinder() {
		return confeccaoFilterFinder;
	}

	public void setConfeccaoFilterFinder(ConfeccaoFilterFinder confeccaoFilterFinder) {
		this.confeccaoFilterFinder = confeccaoFilterFinder;
	}

	@Override
	protected FilterEntityFinder<Confeccao> getFilterFinder() {
		return getConfeccaoFilterFinder();
	}
	
	public List<LoteConfeccao> lotesPorConfeccao() {
		if (getE() == null) return null;
		try {			
			return getFinder().findLotesConfeccaoBy(getE());
		} catch (ViewException e) {
			logger.error("Erro ao pequisar lotesPorConfeccao", e);
			JSFUtils.addErrorMsg("Erro ao pequisar lotesPorConfeccao");
			return null;
		}
	}

	@Override
	public void handleFilterDatas() {

		getFilterDatas()
				.add(new FilterData("strPesquisa", FilterType.LIKE_ANYWHERE, new String[] { "descricao",
						"instituicao.nome", "tipoArtigo.descricao" }));

		getFilterDatas()
				.add(new FilterData("descricao", FilterType.LIKE_ANYWHERE, new String[] { "descricao",
						"instituicao.nome" }));
		getFilterDatas()
				.add(new FilterData("tipoArtigo", FilterType.EQUALS, new String[] { "tipoArtigo" }));
	}

}
