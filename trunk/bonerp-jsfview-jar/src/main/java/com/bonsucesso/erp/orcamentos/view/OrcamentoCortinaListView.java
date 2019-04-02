package com.bonsucesso.erp.orcamentos.view;



import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.crm.model.Cliente;
import com.bonsucesso.erp.orcamentos.data.OrcamentoDataMapper;
import com.bonsucesso.erp.orcamentos.data.OrcamentoFilterFinder;
import com.bonsucesso.erp.orcamentos.data.OrcamentoFinder;
import com.bonsucesso.erp.orcamentos.model.Orcamento;
import com.bonsucesso.erp.orcamentos.model.StatusOrcamento;
import com.bonsucesso.erp.orcamentos.model.TipoOrcamento;
import com.ocabit.base.data.FilterEntityFinder;
import com.ocabit.base.data.filter.FilterData;
import com.ocabit.base.data.filter.FilterType;
import com.ocabit.base.view.AbstractEntityListView;
import com.ocabit.jsf.utils.JSFUtils;


@Component("orcamentoCortinaListView")
@Scope("view")
public class OrcamentoCortinaListView extends AbstractEntityListView<Orcamento, OrcamentoDataMapper, OrcamentoFinder> {

	/**
	 *
	 */
	private static final long serialVersionUID = 8770542787285636154L;

	protected static Logger logger = Logger.getLogger(OrcamentoCortinaListView.class);

	@Autowired
	private OrcamentoFilterFinder filterFinder;

	@PostConstruct
	public void init() {
		getFiltros().put("status", new ArrayList<StatusOrcamento>());
		JSFUtils.execute("loadList()");
	}

	@Override
	public void handleFiltrosPadrao() {
		if (!getFiltros().containsKey("tipoOrcamento") || (getFiltros().get("tipoOrcamento") == null)) {
			getFiltros().put("tipoOrcamento", TipoOrcamento.CORTINAS);
		}
	}

	public void setFilterFinder(OrcamentoFilterFinder filterFinder) {
		this.filterFinder = filterFinder;
	}

	@Override
	protected FilterEntityFinder<Orcamento> getFilterFinder() {
		return filterFinder;
	}

	public void selCliente(Cliente cliente) {
		try {
			// getE().setCliente(cliente);
		} catch (Exception e) {
			JSFUtils.addErrorMsg("Erro ao selecionar o cliente.");
		}
	}

	@Override
	protected void handleFilterDatas() {
		getFilterDatas().add(new FilterData("strPesquisa", FilterType.LIKE_ANYWHERE, "preenchidoPor", "cabecalho"));
		
		getFilterDatas().add(new FilterData("tipoOrcamento", FilterType.EQUALS));

		getFilterDatas().add(new FilterData("status", FilterType.IN));

		getFilterDatas().add(new FilterData("obs", FilterType.LIKE_ANYWHERE));

		getFilterDatas().add(new FilterData("preenchidoPor", FilterType.LIKE_ANYWHERE));

		getFilterDatas()
				.add(new FilterData(new String[] { "dtPreenchimentoIni",
						"dtPreenchimentoFim" }, FilterType.BETWEEN, "dtPreenchimento"));

	}
}
