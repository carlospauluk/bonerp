package com.bonsucesso.erp.financeiro.view;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.bonsucesso.erp.financeiro.business.MovimentacaoBusiness;
import com.bonsucesso.erp.financeiro.data.MovimentacaoDataMapper;
import com.bonsucesso.erp.financeiro.data.MovimentacaoFilterFinder;
import com.bonsucesso.erp.financeiro.data.MovimentacaoFinder;
import com.bonsucesso.erp.financeiro.data.MovimentacaoFinderImpl.TipoSaldo;
import com.bonsucesso.erp.financeiro.model.Carteira;
import com.bonsucesso.erp.financeiro.model.Movimentacao;
import com.ocabit.base.data.filter.FilterUtils;
import com.ocabit.base.model.Mes;
import com.ocabit.base.view.AbstractEntityListView;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;
import com.ocabit.utils.calendar.CalendarUtil;


public abstract class MovimentacaoListView
		extends
		AbstractEntityListView<Movimentacao, MovimentacaoDataMapper, MovimentacaoFinder> {

	/**
	 *
	 */
	private static final long serialVersionUID = 2676779284132867468L;

	protected static Logger logger = Logger.getLogger(MovimentacaoListView.class);

	@Autowired
	private MovimentacaoFilterFinder filterFinder;

	@Autowired
	private MovimentacaoBusiness movimentacaoBusiness;

	private Map<String, BigDecimal> totalizacoes;

	@Override
	public final MovimentacaoFilterFinder getFilterFinder() {
		return filterFinder;
	}

	public final void setFilterFinder(MovimentacaoFilterFinder filterFinder) {
		this.filterFinder = filterFinder;
	}

	public MovimentacaoBusiness getMovimentacaoBusiness() {
		return movimentacaoBusiness;
	}

	public void setMovimentacaoBusiness(MovimentacaoBusiness movimentacaoBusiness) {
		this.movimentacaoBusiness = movimentacaoBusiness;
	}

	public final String getRowStyleClass(Movimentacao movimentacao) {
		try {
			if (movimentacao != null) {

				if ((movimentacao.getCategoria() != null)
						&& (movimentacao.getCategoria().getCodigoM() != null)) {

					if (movimentacao.getCategoria().getCodigoM().startsWith("1")) {
						return "movimentEntrada";
					} else {
						return "movimentSaida";
					}
				} else {
					return null;
				}
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	public final Map<String, BigDecimal> getTotalizacoes() {
		if (totalizacoes == null) {
			totalizacoes = new HashMap<String, BigDecimal>();
		}
		return totalizacoes;
	}

	public final void setTotalizacoes(Map<String, BigDecimal> totalizacoes) {
		this.totalizacoes = totalizacoes;
	}

	public void totalizar() {
		// Só totaliza se tiver sido filtrado.
		if (getFiltros() != null) {
			Date dtIni = null;
			Date dtFim = null;
			Carteira carteira = (Carteira) (getFiltros().containsKey("carteira")
					&& (getFiltros().get("carteira") != null) ? getFiltros().get("carteira") : null);

			// Se está filtrando por mesAno (como é o caso da MovimentacaoRecorrenciaListView), então monta a dtIni e dtFim.
			if ((getFiltros().containsKey("mes") && (getFiltros().get("mes") != null))
					&& (getFiltros().containsKey("ano") && (getFiltros().get("ano") != null))) {

				Mes mes = Mes.valueOf(getFiltros().get("mes").toString().toUpperCase());
				Integer ano = Integer.parseInt(getFiltros().get("ano").toString());

				Date mesAno = CalendarUtil.getDate(1, mes.getNumero(), ano, 0, 0, 0);

				getFiltros().put("dtIni", mesAno);
				getFiltros().put("dtFim", CalendarUtil.ultimoDiaNoMesAno(mesAno));

			}

			if (getFiltros().containsKey("dtIni")) {
				dtIni = (Date) getFiltros().get("dtIni");
			}
			if (getFiltros().containsKey("dtFim")) {
				dtFim = (Date) getFiltros().get("dtFim");
			}

			try {
				// Se está passando filtro de carteira, informa saldo anterior e posterior
				if (carteira != null) {

					if (dtIni != null) {
						getTotalizacoes().put("SALDO_ANTERIOR", getFinder()
								.findSaldo(dtIni, carteira, TipoSaldo.SALDO_ANTERIOR_REALIZADAS));
						getTotalizacoes().put("SALDO_ANTERIOR_COM_CHEQUES", getFinder()
								.findSaldo(dtIni, carteira, TipoSaldo.SALDO_ANTERIOR_COM_CHEQUES));
					}
					if (dtFim != null) {
						getTotalizacoes().put("SALDO_POSTERIOR", getFinder()
								.findSaldo(dtFim, carteira, TipoSaldo.SALDO_POSTERIOR_REALIZADAS));
						getTotalizacoes().put("SALDO_POSTERIOR_COM_CHEQUES", getFinder()
								.findSaldo(dtFim, carteira, TipoSaldo.SALDO_POSTERIOR_COM_CHEQUES));
					}
				}
				//if ((dtIni != null) && (dtFim != null)) {
				//					getTotalizacoes().put("TOTAL_ENTRADAS", getFinder().findTotalEntradas(dtIni, dtFim, carteira));
				//					getTotalizacoes().put("TOTAL_SAIDAS", getFinder().findTotalSaidas(dtIni, dtFim, carteira));
				//					getTotalizacoes().put("TOTAL_MOVIMENTADO", getFinder().findTotalMovimentado(dtIni, dtFim, carteira));

				Map<String, BigDecimal> totais = getMovimentacaoBusiness().findTotal(getList());

				getTotalizacoes().put("TOTAL_ENTRADAS", totais.get("ENTRADAS"));
				getTotalizacoes().put("TOTAL_SAIDAS", totais.get("SAIDAS"));
				getTotalizacoes().put("TOTAL_MOVIMENTADO", totais.get("TOTAL"));

				//}
			} catch (ViewException e) {
				JSFUtils.addHandledException(e);
				logger.error(e);
			}

		}
	}

	public final void checkRelContasAbertas() {
		if (getFiltros().containsKey("dtIni") && getFiltros().containsKey("dtFim") &&
				(getFiltros().get("dtIni") != null) && (getFiltros().get("dtFim") != null)) {
			if ((getFiltros().get("dtIni") instanceof Date) && (getFiltros().get("dtFim") instanceof Date)) {
				JSFUtils.addCallbackParam("dlgId", "dlgRelContasAbertas");
				return;
			}
		}
		JSFUtils.addErrorMsg("É necessário informar a 'Dt Ini' e a 'Dt Fim'");
	}

	public final void imprimirContasAbertas() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("dtIni", getFiltros().get("dtIni"));
			params.put("dtFim", getFiltros().get("dtFim"));
			@SuppressWarnings("rawtypes")
			List carteiras = FilterUtils.convertToList(getFiltros().get("carteiras"));

			List<Long> carteirasIds = new ArrayList<Long>();
			for (Object c : carteiras) {
				carteirasIds.add(((Carteira) c).getId());
			}

			logger.debug("carteirasIds: " + StringUtils.join(carteirasIds, ","));

			params.put("carteirasIds", StringUtils.join(carteirasIds, ","));
			getReportBuilder().imprimir(params, "financeiro/rpContasAbertas", "ContasAbertas");
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	@Override
	public void pesquisar(boolean stored) {
		super.pesquisar(stored);
		totalizar();
	}

	public void pesquisarHoje() {
		getFiltros().put("dtIni", CalendarUtil.zeroDate(new Date()));
		getFiltros().put("dtFim", CalendarUtil.to235959(new Date()));
		pesquisar();
	}

	@Override
	public void afterDeletar() {
		JSFUtils.execute("loadList()");
	}

	public void incPeriodo(boolean proFuturo) {
		try {
			Date dtIni = (Date) getFiltros().get("dtIni");
			Date dtFim = (Date) getFiltros().get("dtFim");

			Long qtdeDias = CalendarUtil.difBetweenDates(dtIni, dtFim);

			// Se na tela foi informado um período relatorial...
			if (CalendarUtil.isPeriodoRelatorial(dtIni, dtFim)) {
				Date r[] = CalendarUtil.iteratePeriodoRelatorial(dtIni, dtFim, proFuturo);
				getFiltros().put("dtIni", r[0]);
				getFiltros().put("dtFim", r[1]);

			} else {

				if (qtdeDias == 0) {
					if (proFuturo) {
						dtIni = getMovimentacaoBusiness().getDiaUtilFinder()
								.findProximoDiaUtilComercial(CalendarUtil.incDias(dtIni, 1));
					} else {
						dtIni = getMovimentacaoBusiness().getDiaUtilFinder().findAnteriorDiaUtilComercial(dtIni);
					}
					dtFim = dtIni;
				} else {
					if (!proFuturo) {
						dtIni = CalendarUtil.incDias(dtIni, -qtdeDias.intValue());
						dtFim = CalendarUtil.incDias(dtFim, -qtdeDias.intValue());
					} else {
						dtIni = CalendarUtil.incDias(dtIni, qtdeDias.intValue());
						dtFim = CalendarUtil.incDias(dtFim, qtdeDias.intValue());
					}

				}
				getFiltros().put("dtIni", dtIni);
				getFiltros().put("dtFim", dtFim);

			}

			pesquisar();
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	@Override
	public void afterSetList() {
		try {
			getMovimentacaoBusiness().handleDescricaoMontada(getList());
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

}
