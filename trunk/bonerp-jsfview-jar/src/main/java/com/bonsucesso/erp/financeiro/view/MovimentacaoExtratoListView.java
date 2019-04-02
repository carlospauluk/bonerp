package com.bonsucesso.erp.financeiro.view;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.primefaces.event.data.SortEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.financeiro.data.CarteiraFinder;
import com.bonsucesso.erp.financeiro.data.MovimentacaoFinderImpl.TipoSaldo;
import com.bonsucesso.erp.financeiro.model.Carteira;
import com.bonsucesso.erp.financeiro.model.Movimentacao;
import com.bonsucesso.erp.financeiro.model.Status;
import com.ocabit.base.data.filter.FilterData;
import com.ocabit.base.data.filter.FilterType;
import com.ocabit.base.data.filter.OrderByParam;
import com.ocabit.base.data.filter.OrderByType;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;
import com.ocabit.utils.calendar.CalendarUtil;


@Component("movimentacaoExtratoListView")
@Scope("view")
public class MovimentacaoExtratoListView extends MovimentacaoListView {

	/**
	 *
	 */
	private static final long serialVersionUID = 4981862930466969416L;

	protected static Logger logger = Logger.getLogger(MovimentacaoExtratoListView.class);

	// Para uso no dialog que exibe os cheques ainda não compensados
	private List<Movimentacao> anterioresSummary; // dtListChequesNaoCompensados;

	// Para uso no dialog que exibe os cheques ainda não compensados
	//	private BigDecimal totalChequesNaoCompensados;

	private List<Movimentacao> anteriores;

	@Override
	public void handleFiltrosPadrao() {
		if (!getFiltros().containsKey("status") || (getFiltros().get("status") == null)) {
			getFiltros().put("status", new Status[] { Status.A_COMPENSAR, Status.ABERTA, Status.REALIZADA });
		}
		if (!getFiltros().containsKey("dtIni") || (getFiltros().get("dtIni") == null)) {
			Calendar calDtIni = CalendarUtil.getCalendar(new Date());
			calDtIni.add(Calendar.DAY_OF_MONTH, -3);
			getFiltros().put("dtIni", calDtIni.getTime());
		}
		if (!getFiltros().containsKey("dtFim") || (getFiltros().get("dtFim") == null)) {
			Calendar calDtFim = CalendarUtil.getCalendar(new Date());
			calDtFim.add(Calendar.DAY_OF_MONTH, 1);
			getFiltros().put("dtFim", calDtFim.getTime());
		}
	}

	@Override
	public void pesquisar(boolean stored) {
		super.pesquisar(stored);
		totalizar();
		findAnteriores();
	}

	public List<Movimentacao> getAnteriores() {
		return anteriores;
	}

	public void setAnteriores(List<Movimentacao> anteriores) {
		if (anteriores != null) {
			try {
				getMovimentacaoBusiness().handleDescricaoMontada(anteriores);
			} catch (ViewException e) {
				logger.error(e);
				JSFUtils.addHandledException(e);
			}
		}
		this.anteriores = anteriores;
	}

	/**
	 * Para não limitar por registros quando a listagem é de extrato.
	 */
	@Override
	public Integer getQtdeRegistros() {
		return null;
	}

	@Override
	public void handleFilterDatas() {
		getFilterDatas().add(new FilterData("status", FilterType.IN));
		getFilterDatas().add(new FilterData("carteira", FilterType.EQUALS));
		getFilterDatas().add(new FilterData(new String[] { "dtIni", "dtFim" }, FilterType.BETWEEN, "dtUtil"));
	}

	@Override
	public List<Movimentacao> doFindByFilters() throws ViewException {
		if (!getFiltros().containsKey("dtIni") || (getFiltros().get("dtIni") == null) ||
				!getFiltros().containsKey("dtFim") || (getFiltros().get("dtFim") == null)) {
			throw new ViewException("É necessário informar o período do extrato.");
		}

		// Pode não estar selecionado os status A_COMPENSAR e ABERTA
		List<OrderByParam> orders = new ArrayList<OrderByParam>();
		orders.add(new OrderByParam("dtUtil"));
		orders.add(new OrderByParam("categoria.codigoSuper"));
		orders.add(new OrderByParam("valorTotal"));
		if ((getOrderByParam() != null) && (getOrderByParam().getField() != null)
				&& !"".equals(getOrderByParam().getField())) {
			orders.add(new OrderByParam(getOrderByParam().getFieldName(), getOrderByParam().getOrderByType()));
		} else {
			orders.add(new OrderByParam("iudt.updated", OrderByType.DESC));
		}

		return getFilterFinder().findByFilters(getFilterDatas(), getQtdeRegistros(), orders);
	}

	@Override
	public void onSort(SortEvent event) {
		super.onSort(event);
		pesquisar();
	}

	public void calcularSaldosSumario(Date dtVenctoEfetiva) {
		if (getFiltros().containsKey("carteira")
				&& (getFiltros().get("carteira") != null)) {
			try {
				Carteira carteira = (Carteira) getFiltros().get("carteira");
				carteira = ((CarteiraFinder) getDataMapper().getBeanFactory().getBean("carteiraFinder"))
						.refresh(carteira);
				logger.debug(">>>>>>>>>>>>>>>>>>>>>> SUMÁRIO PARA :" + dtVenctoEfetiva);

				// Seta os saldos.
				getTotalizacoes().put("SUMARIO_SALDO_POSTERIOR", getFinder()
						.findSaldo(dtVenctoEfetiva, carteira, TipoSaldo.SALDO_POSTERIOR_REALIZADAS));

				BigDecimal saldoPosteriorComCheques = getFinder()
						.findSaldo(dtVenctoEfetiva, carteira, TipoSaldo.SALDO_POSTERIOR_COM_CHEQUES);

				getTotalizacoes().put("SUMARIO_SALDO_POSTERIOR_COM_CHEQUES", saldoPosteriorComCheques);


				if (carteira.getLimite() != null) {
					BigDecimal disponivel = BigDecimal.ZERO;
					disponivel = saldoPosteriorComCheques.subtract(carteira.getLimite().negate());
					disponivel = disponivel == null ? BigDecimal.ZERO : disponivel;
					getTotalizacoes().put("SUMARIO_SALDO_DISPONIVEL", disponivel);
				}

			} catch (Exception e) {
				JSFUtils.addErrorMsg("Erro ao calcular saldo posterior sumarizado.");
				logger.error(e);
			}
		}

	}

	public List<Movimentacao> getAnterioresSummary() {
		return anterioresSummary;
	}

	public void setAnterioresSummary(List<Movimentacao> dtListChequesNaoCompensados) {
		anterioresSummary = dtListChequesNaoCompensados;
	}

	public void updateAnterioresSummary(Date dtVenctoEfetiva) {
		try {
			Carteira carteira = (Carteira) getFiltros().get("carteira");
			List<Movimentacao> l = getFinder().findAnterioresExtrato(dtVenctoEfetiva, carteira);
			getMovimentacaoBusiness().handleDescricaoMontada(l);
			setAnterioresSummary(l);
			getTotalizacoes().put("TOTAL_ANTERIORES_SUMMARY", getMovimentacaoBusiness()
					.findTotal(getAnterioresSummary()).get("TOTAL"));
		} catch (Exception e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao pesquisar as movimentações anteriores.");
		}
	}

	/**
	 * Pesquisa todas as movimentações abertas anteriores a dtIni para exibir num dataTable acima do dataTable principal.
	 */
	public void findAnteriores() {
		try {
			Date dtIni = (Date) getFiltros().get("dtIni");
			Carteira carteira = (Carteira) getFiltros().get("carteira");
			setAnteriores(getFinder().findAnterioresExtrato(dtIni, carteira));
			getTotalizacoes()
					.put("TOTAL_ANTERIORES", getMovimentacaoBusiness().findTotal(getAnteriores()).get("TOTAL"));
		} catch (Exception e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao pesquisar as movimentações anteriores.");
		}
	}

	//	public void incDia(int qtde) {
	//		try {
	//			Date dtPagto = (Date) getFiltros().get("dtIni");
	//			if (qtde == 1) {
	//				dtPagto = getMovimentacaoBusiness().getDiaUtilFinder()
	//						.findProximoDiaUtilComercial(CalendarUtil.incDias(dtPagto, 1));
	//			} else {
	//				dtPagto = getMovimentacaoBusiness().getDiaUtilFinder().findAnteriorDiaUtilComercial(dtPagto);
	//			}
	//			getFiltros().put("dtIni", dtPagto);
	//			getFiltros().put("dtFim", dtPagto);
	//			pesquisar();
	//		} catch (ViewException e) {
	//			logger.error(e);
	//			JSFUtils.addHandledException(e);
	//		}
	//	}
}
