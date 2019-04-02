package com.bonsucesso.erp.estoque.view;



import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.estoque.data.DeptoFinder;
import com.bonsucesso.erp.estoque.data.ProdutoDataMapper;
import com.bonsucesso.erp.estoque.data.ProdutoFilterFinder;
import com.bonsucesso.erp.estoque.data.ProdutoFinder;
import com.bonsucesso.erp.estoque.model.Departamento;
import com.bonsucesso.erp.estoque.model.Produto;
import com.ocabit.base.data.filter.FilterData;
import com.ocabit.base.data.filter.FilterType;
import com.ocabit.base.view.AbstractEntityListView;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;


@Component("produtoListView")
@Scope("view")
public class ProdutoListView extends
		AbstractEntityListView<Produto, ProdutoDataMapper, ProdutoFinder> {

	/**
	 *
	 */
	private static final long serialVersionUID = -414640456433765954L;

	protected static Logger logger = Logger.getLogger(ProdutoListView.class);

	private List<Produto> selecteds;

	@Autowired
	private ProdutoFilterFinder filterFinder;

	@Autowired
	private DeptoFinder deptoFinder;
	
	@PostConstruct
	public void init() {
		JSFUtils.execute("loadList()");
	}

	@Override
	public List<Produto> getSelecteds() {
		return selecteds;
	}

	@Override
	public void setSelecteds(List<Produto> selecteds) {
		this.selecteds = selecteds;
	}

	@Override
	public ProdutoFilterFinder getFilterFinder() {
		return filterFinder;
	}

	public void setFilterFinder(ProdutoFilterFinder filterFinder) {
		this.filterFinder = filterFinder;
	}

	@Override
	public void handleFilterDatas() {

		getFilterDatas().add(new FilterData("descricao", FilterType.LIKE_ANYWHERE));
		getFilterDatas().add(new FilterData("reduzido", FilterType.LIKE_ANYWHERE_NUMERIC));
		getFilterDatas()
				.add(new FilterData("fornecedor", FilterType.LIKE_ANYWHERE, "fornecedor.pessoa.nomeFantasia", "fornecedor.pessoa.nome"));

		getFilterDatas().add(new FilterData("subdepto.depto", FilterType.EQUALS));
		getFilterDatas().add(new FilterData("subdepto", FilterType.EQUALS));
		
		getFilterDatas().add(new FilterData("reduzidoEktAte", FilterType.IS_NULL));

		if (getFiltros().containsKey("artigoCortina")) {
			if (getFiltros().get("artigoCortina").equals(true)) {
				getFilterDatas().add(new FilterData("artigoCortina", FilterType.IS_NOT_NULL));
			} else {
				getFilterDatas().add(new FilterData("artigoCortina", FilterType.IS_NULL));
			}
		}
		

		try {
			Integer.parseInt((String) getFiltros().get("strPesquisa"));
			getFilterDatas().add(new FilterData("strPesquisa", FilterType.LIKE_ANYWHERE_NUMERIC, "reduzido"));
			
		} catch (NumberFormatException e) {
			getFilterDatas().add(new FilterData("strPesquisa", FilterType.LIKE_ANYWHERE, "descricao"));
			logger.info("Não comparar com reduzido");
		}

	}
	
	

	@Override
	public List<Produto> doFindByFilters() throws ViewException {
		
		return super.doFindByFilters();

	}

	@Override
	public String getDatatableName() {
		return "produtoListView";
	}

	public DeptoFinder getDeptoFinder() {
		return deptoFinder;
	}

	public void setDeptoFinder(DeptoFinder deptoFinder) {
		this.deptoFinder = deptoFinder;
	}

	public void updateDepto() {
		if ((getFiltros() != null) && (getFiltros().get("subdepto.depto") != null)) {
			try {
				Departamento depto = getDeptoFinder().refresh((Departamento) getFiltros().get("subdepto.depto"));
				depto.getSubdeptos().size();
				getFiltros().put("subdepto.depto", depto);
			} catch (ViewException e) {
				JSFUtils.addErrorMsg("Erro ao atualizar depto/subdepto");
				logger.error(e);
			}
		}
	}

	@Override
	public void updatePesquisa() {
		updateDepto();
	}

}
