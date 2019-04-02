package com.bonsucesso.erp.estoque.view;



import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.estoque.data.FornecedorFilterFinder;
import com.bonsucesso.erp.estoque.model.Fornecedor;
import com.ocabit.base.data.filter.FilterData;
import com.ocabit.base.data.filter.FilterType;
import com.ocabit.base.view.AbstractDlgBuscaView;


@Component("fornecedorDlgBuscaView")
@Scope("view")
public class FornecedorDlgBuscaView extends AbstractDlgBuscaView<Fornecedor, FornecedorFilterFinder> {

	/**
	 *
	 */
	private static final long serialVersionUID = 7430937509491913894L;

	@Override
	public void handleFilterDatas() {
		if ((getFiltros() != null) && getFiltros().containsKey("strPesquisa")
				&& (getFiltros().get("strPesquisa") == null)) {
			getFiltros().put("strPesquisa", "");
		}
		getFilterDatas()
				.add(new FilterData("strPesquisa", FilterType.LIKE_ANYWHERE, "pessoa.documento", "pessoa.nome", "pessoa.nomeFantasia"));

	}

}
