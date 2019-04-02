package com.bonsucesso.erp.financeiro.view;



import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.financeiro.data.CategoriaFinder;
import com.bonsucesso.erp.financeiro.data.RegraImportacaoLinhaDataMapper;
import com.bonsucesso.erp.financeiro.data.RegraImportacaoLinhaFilterFinder;
import com.bonsucesso.erp.financeiro.data.RegraImportacaoLinhaFinder;
import com.bonsucesso.erp.financeiro.model.Categoria;
import com.bonsucesso.erp.financeiro.model.RegraImportacaoLinha;
import com.bonsucesso.erp.financeiro.model.TipoLancto;
import com.ocabit.base.data.filter.FilterData;
import com.ocabit.base.data.filter.FilterType;
import com.ocabit.base.view.AbstractEntityFormListFilterView;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;


/**
 * View para a entidade RegraImportacaoLinha.
 *
 * @author Carlos Eduardo Pauluk
 */
@Component("regraImportacaoLinhaFormListView")
@Scope("view")
public final class RegraImportacaoLinhaFormListView extends
		AbstractEntityFormListFilterView<RegraImportacaoLinha, RegraImportacaoLinhaDataMapper, RegraImportacaoLinhaFinder, RegraImportacaoLinhaFilterFinder> {

	/**
	 *
	 */
	private static final long serialVersionUID = -6034423218753061155L;

	protected static Logger logger = Logger.getLogger(RegraImportacaoLinhaFormListView.class);

	@Autowired
	private CategoriaFinder categoriaFinder;
	
	@PostConstruct
	public void init() {
		JSFUtils.execute("loadList()");
	}

	public CategoriaFinder getCategoriaFinder() {
		return categoriaFinder;
	}

	public void setCategoriaFinder(CategoriaFinder categoriaFinder) {
		this.categoriaFinder = categoriaFinder;
	}

	/*
	 * Chamado através do categoriaDlgBusca.xhtml
	 */
	public void updateCategoria(Categoria categoria) {
		try {
			getE().setCategoria(categoria);
			JSFUtils.execute("updateOpnCategoria()");
		} catch (Exception e) {
			JSFUtils.addErrorMsg("Erro ao setar categoria.");
			logger.error(e);
		}
	}

	public void updateTipoLancto() {
		try {
			if (getE().getTipoLancto().equals(TipoLancto.TRANSF_PROPRIA)) {
				Categoria categ299 = getCategoriaFinder().findBy(299l);
				getE().setCategoria(categ299);
			} else {
				getE().setCategoria(null);
			}
		} catch (ViewException e) {
			JSFUtils.addErrorMsg("Erro ao setar categoria (2.99).");
		}
	}

	@Override
	public void handleFilterDatas() {
		getFilterDatas().add(new FilterData("strPesquisa", FilterType.LIKE_ANYWHERE, "regraRegexJava"));

	}

}
