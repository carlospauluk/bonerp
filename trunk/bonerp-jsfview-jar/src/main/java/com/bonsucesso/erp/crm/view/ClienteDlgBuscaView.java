package com.bonsucesso.erp.crm.view;



import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.crm.data.ClienteFilterFinder;
import com.bonsucesso.erp.crm.model.Cliente;
import com.ocabit.base.data.filter.FilterData;
import com.ocabit.base.data.filter.FilterType;
import com.ocabit.base.view.AbstractDlgBuscaView;


@Component("clienteDlgBuscaView")
@Scope("view")
public class ClienteDlgBuscaView extends AbstractDlgBuscaView<Cliente, ClienteFilterFinder> {

	/**
	 *
	 */
	private static final long serialVersionUID = -6907048873630231088L;

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
