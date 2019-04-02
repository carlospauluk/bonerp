package com.bonsucesso.erp.financeiro.view;



import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.financeiro.business.MovimentacaoBusiness;
import com.bonsucesso.erp.financeiro.model.Categoria;
import com.bonsucesso.erp.financeiro.model.Movimentacao;
import com.ocabit.base.view.StoredViewInfoHandler;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;
import com.ocabit.utils.calendar.CalendarUtil;
import com.ocabit.utils.reports.ReportBuilder;


@Component("relatorioPorCategoriasView")
@Scope("view")
public class RelatorioPorCategoriasView implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 8501451742589722495L;

	protected static Logger logger = Logger.getLogger(RelatorioPorCategoriasView.class);

	@Autowired
	private MovimentacaoBusiness movimentacaoBusiness;

	private Date dtIni, dtFim;

	@Autowired
	private StoredViewInfoHandler storedViewInfoHandler;

	private String[] camposStored = new String[] { "dtIni", "dtFim" };

	private TreeNode arvoreCategorias;

	private List<Movimentacao> dtListMovimentacoes;

	@Autowired
	private ReportBuilder reportBuilder;

	@PostConstruct
	public void init() {
		setDtIni(CalendarUtil.primeiroDiaNoMesAno(new Date()));
		setDtFim(CalendarUtil.ultimoDiaNoMesAno(new Date()));
		try {
			getStoredViewInfoHandler().processStoredViewInfo("relatorioPorCategoriasView", this);
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao restaurar página.");
		}
		gerarRelatorio();
	}

	public MovimentacaoBusiness getMovimentacaoBusiness() {
		return movimentacaoBusiness;
	}

	public void setMovimentacaoBusiness(MovimentacaoBusiness movimentacaoBusiness) {
		this.movimentacaoBusiness = movimentacaoBusiness;
	}

	public StoredViewInfoHandler getStoredViewInfoHandler() {
		return storedViewInfoHandler;
	}

	public void setStoredViewInfoHandler(StoredViewInfoHandler storedViewInfoHandler) {
		this.storedViewInfoHandler = storedViewInfoHandler;
	}

	public String[] getCamposStored() {
		return camposStored;
	}

	public void setCamposStored(String[] camposStored) {
		this.camposStored = camposStored;
	}

	public Date getDtIni() {
		return dtIni;
	}

	public void setDtIni(Date dtIni) {
		this.dtIni = dtIni;
	}

	public Date getDtFim() {
		return dtFim;
	}

	public void setDtFim(Date dtFim) {
		this.dtFim = dtFim;
	}

	public void store() {
		getStoredViewInfoHandler().store("resumoConferenciaGeralView", this, camposStored);
	}

	public TreeNode getArvoreCategorias() {
		return arvoreCategorias;
	}

	public void setArvoreCategorias(TreeNode arvoreCategorias) {
		this.arvoreCategorias = arvoreCategorias;
	}

	/**
	 *
	 */
	public void gerarRelatorio() {
		try {
			setArvoreCategorias(new DefaultTreeNode());
			addFilhos(null, getArvoreCategorias());
			store();
		} catch (ViewException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void addFilhos(Categoria pai, TreeNode nodePai) throws ViewException {
		List<Categoria> pais = getMovimentacaoBusiness().getCategoriaFinder().findBy(pai);
		for (Categoria p : pais) {

			if (p.getTotalizavel().equals(Boolean.FALSE)) {
				continue;
			}

			BigDecimal totalCateg = getMovimentacaoBusiness().getMovimentacaoFinder()
					.findTotal(p, getDtIni(), getDtFim(), true, true, null);

			ResultadoPorCategoria r = new ResultadoPorCategoria(p, totalCateg);

			TreeNode filho = new DefaultTreeNode(r, nodePai);
			filho.setExpanded(true);
			if ((p.getSubCategs() != null) && (p.getSubCategs().size() > 0)) {
				addFilhos(p, filho);
			}
		}
	}

	public List<Movimentacao> getDtListMovimentacoes() {
		return dtListMovimentacoes;
	}

	public void setDtListMovimentacoes(List<Movimentacao> dtListMovimentacoes) {
		this.dtListMovimentacoes = dtListMovimentacoes;
	}

	public void updateDtListMovimentacoes(Categoria categoria) {
		try {
			setDtListMovimentacoes(getMovimentacaoBusiness().getMovimentacaoFinder()
					.findBy(categoria, getDtIni(), getDtFim()));
		} catch (ViewException e) {
			logger.error("Erro ao preencher dtListMovimentacoes", e);
			JSFUtils.addErrorMsg("Erro ao consultar movimentações.");
		}
	}

	/**
	 * Classe auxiliar para exibir os resultados por categorias na tree.
	 *
	 * @author Carlos Eduardo Pauluk
	 *
	 */
	public class ResultadoPorCategoria {

		private Categoria categoria;

		private BigDecimal total;

		public ResultadoPorCategoria(Categoria categoria, BigDecimal total) {
			super();
			this.categoria = categoria;
			this.total = total;
		}

		public Categoria getCategoria() {
			return categoria;
		}

		public void setCategoria(Categoria categoria) {
			this.categoria = categoria;
		}

		public BigDecimal getTotal() {
			return total;
		}

		public void setTotal(BigDecimal total) {
			this.total = total;
		}

	}

	public ReportBuilder getReportBuilder() {
		return reportBuilder;
	}

	public void setReportBuilder(ReportBuilder reportBuilder) {
		this.reportBuilder = reportBuilder;
	}

	public final void checkRelTotalPorCategoria() {
		if (getDtIni() != null && getDtFim() != null) {
			JSFUtils.addCallbackParam("dlgId", "dlgRelTotalPorCategoria");
			return;
		}
		JSFUtils.addErrorMsg("É necessário informar a 'Dt Ini' e a 'Dt Fim'");
	}

	public final void imprimirTotalPorCategoria() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("dtIni", getDtIni());
			params.put("dtFim", getDtFim());
			getReportBuilder().imprimir(params, "financeiro/rpTotalPorCategoria", "TotalPorCategoria");
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}
	
	public void incPeriodo(boolean proFuturo) {
		try {
			Date dtIni = getDtIni();
			Date dtFim = getDtFim();

			Long qtdeDias = CalendarUtil.difBetweenDates(dtIni, dtFim);

			// Se na tela foi informado um período relatorial...
			if (CalendarUtil.isPeriodoRelatorial(dtIni, dtFim)) {
				Date r[] = CalendarUtil.iteratePeriodoRelatorial(dtIni, dtFim, proFuturo);
				setDtIni(r[0]);
				setDtFim(r[1]);
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
				setDtIni(dtIni);
				setDtFim(dtFim);
			}

			gerarRelatorio();
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}
	
	
}
