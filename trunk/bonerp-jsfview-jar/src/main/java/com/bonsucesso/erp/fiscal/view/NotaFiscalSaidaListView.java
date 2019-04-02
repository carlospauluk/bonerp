package com.bonsucesso.erp.fiscal.view;



import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.fiscal.data.NotaFiscalDataMapper;
import com.bonsucesso.erp.fiscal.data.NotaFiscalFilterFinder;
import com.bonsucesso.erp.fiscal.data.NotaFiscalFinder;
import com.bonsucesso.erp.fiscal.model.NotaFiscal;
import com.bonsucesso.erp.fiscal.model.TipoNotaFiscal;
import com.ocabit.base.data.filter.FilterData;
import com.ocabit.base.data.filter.FilterType;
import com.ocabit.base.view.AbstractEntityListView;
import com.ocabit.jsf.utils.JSFUtils;


@Component("notaFiscalSaidaListView")
@Scope("view")
public class NotaFiscalSaidaListView
		extends AbstractEntityListView<NotaFiscal, NotaFiscalDataMapper, NotaFiscalFinder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8926325633510213452L;

	protected static Logger logger = Logger.getLogger(NotaFiscalSaidaListView.class);

	@Autowired
	private NotaFiscalFilterFinder filterFinder;

	@PostConstruct
	public void init() {
		JSFUtils.execute("loadList()");
	}

	@Override
	public void handleFiltrosPadrao() {
		getFiltros().put("pessoaEmitente.documento", "77498442000134");
		getFiltros().put("tipoNotaFiscal", new TipoNotaFiscal[]{TipoNotaFiscal.NFE});
	}

	public NotaFiscalFilterFinder getFilterFinder() {
		return filterFinder;
	}

	public void setFilterFinder(NotaFiscalFilterFinder filterFinder) {
		this.filterFinder = filterFinder;
	}

	@Override
	protected void handleFilterDatas() {

		// preciso adicionar este filtro aos filterDatas para que seja salvo no saveDatatable()
		getFilterDatas()
				.add(new FilterData("strPesquisa", FilterType.LIKE_ANYWHERE, "pessoaDestinatario.nome", "pessoaDestinatario.nomeFantasia"));

		getFilterDatas().add(new FilterData("pessoaEmitente.documento", FilterType.EQUALS));
		getFilterDatas().add(new FilterData("tipoNotaFiscal", FilterType.IN));

		getFilterDatas()
				.add(new FilterData(new String[] { "dtEmissaoIni",
						"dtEmissaoFim" }, FilterType.BETWEEN, "dtEmissao"));

	}

}
