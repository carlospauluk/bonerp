package com.bonsucesso.erp.financeiro.view;



import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.financeiro.model.Carteira;
import com.bonsucesso.erp.financeiro.model.Categoria;
import com.bonsucesso.erp.financeiro.model.Movimentacao;
import com.bonsucesso.erp.financeiro.model.Status;
import com.ocabit.base.data.filter.FilterData;
import com.ocabit.base.data.filter.FilterType;
import com.ocabit.base.data.filter.OrderByParam;
import com.ocabit.base.data.filter.OrderByType;
import com.ocabit.base.view.exception.ViewException;


@Component("movimentacaoListagemListView")
@Scope("view")
public class MovimentacaoListagemListView extends MovimentacaoListView {

	/**
	 *
	 */
	private static final long serialVersionUID = 2676779284132867468L;

	protected static Logger logger = Logger.getLogger(MovimentacaoListagemListView.class);

	@PostConstruct
	public void init() {
		processStoredFilterData();
		handleFiltrosPadrao();
	}

	@Override
	public void handleFiltrosPadrao() {
		if (!getFiltros().containsKey("status") || (getFiltros().get("status") == null)) {
			getFiltros().put("status", new Status[] { Status.A_COMPENSAR, Status.ABERTA, Status.REALIZADA });
		}
		if (!getFiltros().containsKey("carteiras") || (getFiltros().get("carteiras") == null)) {
			getFiltros().put("carteiras", new ArrayList<Carteira>());
		}
	}

	@Override
	public Integer getQtdeRegistros() {
		Integer qtdeRegistros = super.getQtdeRegistros();
		if ((qtdeRegistros == null) || (qtdeRegistros > 100)) {
			setQtdeRegistros(100);
		}
		return qtdeRegistros;
	}

	@Override
	public void handleFilterDatas() {

		// preciso adicionar este filtro aos filterDatas para que seja salvo no saveDatatable()
		getFilterDatas().add(new FilterData("strPesquisa", FilterType.NONE_UM));

		getFilterDatas().add(new FilterData("status", FilterType.IN));

		getFilterDatas().add(new FilterData("modo", FilterType.IN));

		getFilterDatas().add(new FilterData("carteiras", FilterType.IN, "carteira"));

		// preciso adicionar este filtro aos filterDatas para que seja salvo no saveDatatable()
		getFilterDatas().add(new FilterData("categoria", FilterType.NONE_UM));

		getFilterDatas().add(new FilterData(new String[] { "dtIni", "dtFim" }, FilterType.BETWEEN, "dtUtil"));

		getFilterDatas().add(new FilterData(new String[] { "valorIni", "valorFim" }, FilterType.BETWEEN, "valorTotal"));

	}

	/**
	 * Override para implementar filtro incomum.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Movimentacao> doFindByFilters() throws ViewException {
		// filtro incomum com LEFT JOIN em pessoa
		Predicate p = null;

		if (getFiltros().containsKey("strPesquisa")) {

			String strPesquisa = ((String) getFiltros().get("strPesquisa")).toUpperCase();

			Long pesquisaPorLong = null;
			Path<Long> pathId = getFilterFinder().getFrom().get("id");

			try {
				pesquisaPorLong = Long.parseLong(strPesquisa);
			} catch (NumberFormatException e) {
				// do nothing
			}

			strPesquisa = "%" + ((String) getFiltros().get("strPesquisa")).toUpperCase() + "%";
			CriteriaBuilder cb = getFilterFinder().getCriteriaBuilder();

			if (pesquisaPorLong != null) {
				p = cb.or(cb.equal(pathId, cb.literal(pesquisaPorLong)));
			} else {
				Join join = getFilterFinder().getFrom().join("pessoa", JoinType.LEFT);

				Path<String> pathDescricao = getFilterFinder().getFrom().get("descricao");
				Path<String> pathDocumentoFiscal = getFilterFinder().getFrom().get("documentoFiscal");
				Path<String> pathObs = getFilterFinder().getFrom().get("obs");
				Path<String> pathNumCheque = getFilterFinder().getFrom().get("cheque").get("numCheque");
				Path<String> pathPessoaNome = join.get("nome");
				Path<String> pathPessoaNomeFantasia = join.get("nomeFantasia");
				p = cb.or(cb.like(pathDescricao, cb.literal(strPesquisa)), cb
						.like(pathDocumentoFiscal, cb.literal(strPesquisa)), cb
								.like(pathObs, cb.literal(strPesquisa)), cb
										.like(pathNumCheque, cb.literal(strPesquisa)), cb
												.like(pathPessoaNome, cb.literal(strPesquisa)), cb
														.like(pathPessoaNomeFantasia, cb.literal(strPesquisa)));
			}

		}

		// Trata para pesquisar em todas as categorias e em suas subcategorias
		if (getFiltros().containsKey("categoria")) {

			Categoria categoria = (Categoria) getFiltros().get("categoria");
			categoria = getFinder().getEntityManager().find(Categoria.class, categoria.getId());

			CriteriaBuilder cb = getFilterFinder().getCriteriaBuilder();

			Path<Categoria> path = getFilterFinder().getFrom().get("categoria");

			if (p != null) {
				p = cb.and(p, cb.equal(path, categoria));
			} else {
				p = cb.equal(path, categoria);
			}

			List<Predicate> ps = new ArrayList<Predicate>();
			ps.add(p);

			if (categoria.getSubCategs() != null) {
				for (Categoria subCateg : categoria.getSubCategs()) {
					ps.add(cb.equal(path, subCateg));
				}
			}
			p = cb.or(ps.toArray(new Predicate[] {}));
		}
		
		if (p != null) {
			return getFilterFinder().findByFilters(getFilterDatas(), getQtdeRegistros(), new OrderByParam("dtUtil", OrderByType.DESC), p, true);
		} else {
			return getFilterFinder().findByFilters(getFilterDatas(), getQtdeRegistros(), new OrderByParam("iudt.updated", OrderByType.DESC));
		}

	}
}
