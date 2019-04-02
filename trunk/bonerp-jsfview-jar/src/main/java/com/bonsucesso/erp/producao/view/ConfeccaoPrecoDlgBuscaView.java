package com.bonsucesso.erp.producao.view;



import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.producao.data.ConfeccaoPrecoFilterFinder;
import com.bonsucesso.erp.producao.model.ConfeccaoPreco;
import com.ocabit.base.data.filter.FilterData;
import com.ocabit.base.data.filter.FilterType;
import com.ocabit.base.view.AbstractDlgBuscaView;


@Component("confeccaoPrecoDlgBuscaView")
@Scope("view")
public class ConfeccaoPrecoDlgBuscaView extends AbstractDlgBuscaView<ConfeccaoPreco, ConfeccaoPrecoFilterFinder> {

	/**
	 *
	 */
	private static final long serialVersionUID = -6907048873630231088L;

	@Override
	public void handleFilterDatas() {
		// para não dar erro de nenhum filtro encontrado.
		if ((getFiltros() != null) && getFiltros().containsKey("strPesquisa")
				&& (getFiltros().get("strPesquisa") == null)) {
			getFiltros().put("strPesquisa", "");
		}
		getFilterDatas()
				.add(new FilterData("strPesquisa", FilterType.LIKE_ANYWHERE, "descricao", "confeccao.descricao", "confeccao.instituicao.nome", "confeccao.tipoArtigo.descricao"));

	}

}
