package com.bonsucesso.erp.cortinas.view;



import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.cortinas.data.ArtigoCortinaFilterFinder;
import com.bonsucesso.erp.cortinas.model.ArtigoCortina;
import com.ocabit.base.data.filter.FilterData;
import com.ocabit.base.data.filter.FilterType;
import com.ocabit.base.view.AbstractDlgBuscaView;


@Component("artigoCortinaDlgBuscaView")
@Scope("view")
public class ArtigoCortinaDlgBuscaView extends AbstractDlgBuscaView<ArtigoCortina, ArtigoCortinaFilterFinder> {

	/**
	 *
	 */
	private static final long serialVersionUID = -6907048873630231088L;

	@Override
	public void handleFilterDatas() {
		getFilterDatas()
				.add(new FilterData("strPesquisa", FilterType.LIKE_ANYWHERE, "produto.descricao"));

		if (getFiltros().get("strPesquisa") == null) {
			getFiltros().put("strPesquisa", "");
		}

	}

}
