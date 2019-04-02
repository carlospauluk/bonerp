package com.bonsucesso.erp.financeiro.view;



import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.financeiro.model.Movimentacao;
import com.bonsucesso.erp.financeiro.model.Status;
import com.ocabit.base.data.filter.FilterData;
import com.ocabit.base.data.filter.FilterType;
import com.ocabit.base.data.filter.OrderByParam;
import com.ocabit.base.data.filter.OrderByType;
import com.ocabit.base.model.Mes;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;
import com.ocabit.utils.calendar.CalendarUtil;


@Component("movimentacaoRecorrenciaListView")
@Scope("view")
public class MovimentacaoRecorrenciaListView extends MovimentacaoListView {

	/**
	 *
	 */
	private static final long serialVersionUID = 51919677361158003L;

	protected static Logger logger = Logger.getLogger(MovimentacaoRecorrenciaListView.class);

	private List<String> dtListResults;

	@Autowired
	private ListMaker lmFinanc;

	public ListMaker getLmFinanc() {
		return lmFinanc;
	}

	public void setLmFinanc(ListMaker lmFinanc) {
		this.lmFinanc = lmFinanc;
	}

	@Override
	public Integer getQtdeRegistros() {
		return null;
	}

	@Override
	public void handleFiltrosPadrao() {
		if ((!getFiltros().containsKey("mes") || (getFiltros().get("mes") == null))
				|| (!getFiltros().containsKey("ano") || (getFiltros().get("ano") == null))) {
			Date data = new Date();
			getFiltros().put("mes", new SimpleDateFormat("MMMM").format(data).toUpperCase());
			getFiltros().put("ano", CalendarUtil.getCalendar(data).get(Calendar.YEAR));
		}
		getFiltros().put("recorrencia.recorrente", Boolean.TRUE);
		getFiltros().put("status", new Status[] { Status.A_COMPENSAR, Status.ABERTA, Status.REALIZADA });

		// preciso adicionar este filtro aos filterDatas para que seja salvo no saveDatatable()
		getFilterDatas().add(new FilterData("strPesquisa", FilterType.NONE_UM));
	}

	@Override
	public void handleFilterDatas() {
		getFilterDatas().add(new FilterData(new String[] { "mes", "ano" }, FilterType.BETWEEN_MESANO, "dtVencto"));
		getFilterDatas().add(new FilterData("recorrencia.recorrente", FilterType.EQUALS));

		getFilterDatas().add(new FilterData("carteira", FilterType.EQUALS));
		getFilterDatas().add(new FilterData("strPesquisa", FilterType.NONE_UM));
	}

	/**
	 * Override para implementar filtro incomum.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Movimentacao> doFindByFilters() throws ViewException {
		// filtro incomum com LEFT JOIN em pessoa
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

			Predicate p;

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

			return getFilterFinder().findByFilters(getFilterDatas(), getQtdeRegistros(), new OrderByParam("dtUtil", OrderByType.DESC), p, true);
		} else {
			return getFilterFinder().findByFilters(getFilterDatas(), getQtdeRegistros());

		}
	}

	public void processarRecorrentes() {

		setDtListResults(null);

		if ((getSelecteds() == null) || (getSelecteds().size() < 1)) {
			JSFUtils.addWarnMsg("É necessário selecionar ao menos uma movimentação.");
			return;
		}

		try {

			setDtListResults(getDataMapper().processarRecorrentes(getSelecteds()));

			JSFUtils.addInfoMsg("Movimentações geradas com sucesso.");

			updateMesAno(false);
		} catch (ViewException e) {
			JSFUtils.addErrorMsg("Erro ao processar recorrentes.");
			JSFUtils.addHandledException(e);
			logger.error(e);

		}
	}

	public void updateMesAno(boolean anterior) {
		try {
			Mes mes = Mes.valueOf(getFiltros().get("mes").toString().toUpperCase());
			Integer ano = Integer.parseInt(getFiltros().get("ano").toString());
			Date data = CalendarUtil.getDate(1, mes.getNumero(), ano, 0, 0, 0);
			if (anterior) {
				data = CalendarUtil.getMesAnterior(data);
			} else {
				data = CalendarUtil.getMesProximo(data);
			}
			getFiltros().put("mes", new SimpleDateFormat("MMMM").format(data).toUpperCase());
			getFiltros().put("ano", CalendarUtil.getCalendar(data).get(Calendar.YEAR));
			saveDatatable();
			pesquisar();
		} catch (Exception e) {
			JSFUtils.addErrorMsg("Erro ao atualizar Mês/Ano");
			logger.error(e);
		}
	}

	public List<String> getDtListResults() {
		return dtListResults;
	}

	public void setDtListResults(List<String> results) {
		dtListResults = results;
	}

}
