package com.bonsucesso.erp.producao.view;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.estoque.model.Grade;
import com.bonsucesso.erp.estoque.model.GradeTamanho;
import com.bonsucesso.erp.producao.data.ConfeccaoFinder;
import com.bonsucesso.erp.producao.data.LoteConfeccaoDataMapper;
import com.bonsucesso.erp.producao.data.LoteConfeccaoFilterFinder;
import com.bonsucesso.erp.producao.data.LoteConfeccaoFinder;
import com.bonsucesso.erp.producao.data.LoteConfeccaoItemDataMapper;
import com.bonsucesso.erp.producao.model.Confeccao;
import com.bonsucesso.erp.producao.model.LoteConfeccao;
import com.bonsucesso.erp.producao.model.LoteConfeccaoItem;
import com.bonsucesso.erp.producao.model.TipoInsumo;
import com.ocabit.base.data.filter.FilterData;
import com.ocabit.base.data.filter.FilterType;
import com.ocabit.base.view.AbstractEntityFormListView;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;
import com.ocabit.utils.reports.ReportBuilder;


@Component("loteConfeccaoFormListView")
@Scope("view")
public class LoteConfeccaoFormListView extends
		AbstractEntityFormListView<LoteConfeccao, LoteConfeccaoDataMapper, LoteConfeccaoFinder> {

	/**
	 *
	 */
	private static final long serialVersionUID = 2929106561666395655L;

	protected static Logger logger = Logger.getLogger(LoteConfeccaoFormListView.class);

	@Autowired
	private LoteConfeccaoFilterFinder filterFinder;

	private LoteConfeccaoItem item;

	@Autowired
	private LoteConfeccaoItemDataMapper loteConfeccaoItemDataMapper;

	@Autowired
	private ConfeccaoFinder confeccaoFinder;

	private Map<LoteConfeccaoItem, Boolean> itensLoteSelecionados;

	private List<TipoInsumo> tiposInsumosRelatorios;

	@Autowired
	private ReportBuilder reportBuilder;

	private String tipoRelatorio;

	private List<Grade> grades;

	private Map<Grade, List<LoteConfeccaoItem>> itensLotePorGrade;

	@PostConstruct
	public void init() {
		JSFUtils.execute("loadList()");
	}

	@Override
	public void handleFilterDatas() {

		boolean procurandoPorCodigo = false;
		try {
			Integer codigo = Integer.parseInt((String) getFiltros().get("strPesquisa"));
			getFilterDatas().add(new FilterData("strPesquisa", FilterType.EQUALS, "codigo"));
			getFiltros().put("strPesquisa", codigo);
			procurandoPorCodigo = true;
		} catch (NumberFormatException e) {
			logger.info("Não comparar com código");
		}
		if (!procurandoPorCodigo) {
			getFilterDatas()
					.add(new FilterData("strPesquisa", FilterType.LIKE_ANYWHERE, new String[] { "descricao" }));

		}

		// preciso adicionar este filtro aos filterDatas para que seja salvo no saveDatatable() E PARA QUE SEJA CHAMADO O doFindByFilters caso só tenha ele
		getFilterDatas().add(new FilterData("confeccao", FilterType.NONE_UM));

		getFilterDatas()
				.add(new FilterData(new String[] { "dtLoteIni", "dtLoteFim" }, FilterType.BETWEEN, "dtLote"));
	}

	/**
	 * Override para implementar filtro incomum.
	 */
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<LoteConfeccao> doFindByFilters() throws ViewException {
		// Retorna os resultados buscados pelos filtros
		List<LoteConfeccao> r = super.doFindByFilters();
		// Se não tem filtros, retornará null
		if (r == null) {
			r = new ArrayList<LoteConfeccao>();
		}
		// Se está buscando pelo filtro de confecção e encontrar resultados, une-os.
		if (getFiltros().containsKey("confeccao")) {
			Confeccao confeccao = (Confeccao) getFiltros().get("confeccao");
			List<LoteConfeccao> comConfeccao = getFinder().findComConfeccao(confeccao);
			r = (List<LoteConfeccao>) CollectionUtils.union(r, comConfeccao);
		}
		return r;
	}

	public LoteConfeccaoItem getItem() {
		if (item == null) {
			item = new LoteConfeccaoItem();
		}
		return item;
	}

	public void setItem(LoteConfeccaoItem item) {
		if ((item != null) && (item.getId() != null) && (item.getQtdesGrade() != null)
				&& (item.getQtdesGrade().size() > 0)) {
			item.buildMapaGradeTamanho();
		}
		this.item = item;
	}

	public void newItem() {
		setItem(new LoteConfeccaoItem());
	}

	public void saveItem() {
		try {
			getItem().setLoteConfeccao(getE());
			getItem().getQtdesGrade().clear();
			Map<GradeTamanho, Integer> mapaGrade = getItem().getMapaGradeQtdes();
			setItem(getLoteConfeccaoItemDataMapper().save(getItem()));
			getItem().setMapaGradeQtdes(mapaGrade);
			getItem().mapaGrade2QtdesGrade();
			setItem(getLoteConfeccaoItemDataMapper().save(getItem()));
			refreshE();
			final RequestContext context = RequestContext.getCurrentInstance();
			context.addCallbackParam("saved", true);
			context.addCallbackParam("dlgId", "dlgItem");
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public void deleteItem(LoteConfeccaoItem item) {
		try {
			setItem(item);
			getLoteConfeccaoItemDataMapper().delete(getItem());
			refreshE();
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addErrorMsgErroAoDeletar();
		}
	}

	public List<Confeccao> acConfeccao(String str) {
		try {
			return getConfeccaoFinder().findBy(str);
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
			return null;
		}
	}

	public void updateConfeccao() {
		if (getItem() != null && getItem().getConfeccao() != null) {
			try {
				Confeccao confeccao = getConfeccaoFinder().refresh(getItem().getConfeccao());
				confeccao.getGrade().getTamanhos().size(); // touch
				getItem().setConfeccao(confeccao);
			} catch (ViewException e) {
				logger.error(e);
				JSFUtils.addHandledException(e);
			}
		}
	}

	@Override
	public void afterSetE(LoteConfeccao e) {
		for (LoteConfeccaoItem item : e.getItens()) {
			item.buildMapaGradeTamanho();
		}

		// Remonto o mapa que divide os dataTables por grades
		setItensLotePorGrade(new HashMap<Grade, List<LoteConfeccaoItem>>());
		setGrades(new ArrayList<Grade>());

		setItensLoteSelecionados(null);

		selecionarTodos(true);

	}

	/**
	 * Método utilizado para montar o cabeçalho dos dataTables (se tem a ordem na tal grade, retorna).
	 * 
	 * @param grade
	 * @param ordem
	 * @return
	 */
	public GradeTamanho getTamanhoNaGradeSeExistir(Grade grade, Integer ordem) {
		if (grade != null && ordem != null) {
			grade = getConfeccaoFinder().getEntityManager().find(Grade.class, grade.getId());
			for (GradeTamanho gt : grade.getTamanhos()) {
				if (gt.getOrdem().equals(ordem)) {
					return gt;
				}
			}
		}
		return new GradeTamanho();
	}

	public void checkSelecionados(String tipoRelatorio) {
		if ((getItensLoteSelecionados() != null) && (getItensLoteSelecionados().size() > 0)) {
			setTipoRelatorio(tipoRelatorio);
			final RequestContext context = RequestContext.getCurrentInstance();

			context.addCallbackParam("dlgId", "dlgRelRelatorios");
		} else {
			JSFUtils.addErrorMsg("É necessário selecionar ao menos 1 item do lote.");
		}
	}

	public void validateImprimirRelatorios() {
		final RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam("dlgId", "dlgRelatorios");
		if ((getTiposInsumosRelatorios() == null)
				|| (getTiposInsumosRelatorios().size() == 0)) {
			JSFUtils.addErrorMsg("É necessário selecionar ao menos 1 tipo de insumo");

		} else {
			context.addCallbackParam("saved", true);
			context.addCallbackParam("postFunction", "_$('btnRelatorios').click()");
		}
	}

	public void imprimirRelatorios() {

		try {
			// pego todos os IDs dos lotes selecionados para passar como parâmetro ao report
			List<Long> loteItensIds = new ArrayList<Long>();
			for (Map.Entry<LoteConfeccaoItem, Boolean> e : getItensLoteSelecionados().entrySet()) {
				if (e.getValue() == Boolean.TRUE) {
					loteItensIds.add(e.getKey().getId());
				}
			}

			List<Long> tiposInsumosIds = new ArrayList<Long>();
			// Se não selecionou nenhum tipoInsumo, adiciona todos.
			if ((getTiposInsumosRelatorios() == null)
					|| (getTiposInsumosRelatorios().size() == 0)) {
				JSFUtils.addErrorMsg("É necessário selecionar ao menos 1 tipo de insumo");
				return;
			} else {
				// pego todos os IDs de tipos de insumo selecionados para passar como parâmetro ao report
				for (TipoInsumo tipoInsumo : getTiposInsumosRelatorios()) {
					if (tipoInsumo != null) {
						tiposInsumosIds.add(tipoInsumo.getId());
					}
				}
			}

			logger.debug("Chamando relatório: " + getTipoRelatorio());
			logger.debug("loteItensIds: " + StringUtils.join(loteItensIds, ","));
			logger.debug("tiposInsumosIds: " + StringUtils.join(tiposInsumosIds, ","));

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("loteItensIds", StringUtils.join(loteItensIds, ","));
			params.put("tiposInsumosIds", StringUtils.join(tiposInsumosIds, ","));

			getReportBuilder().imprimir(params, "producao/rp" + getTipoRelatorio(), "Relatorios");
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}

	}

	public List<GradeTamanho> getTamanhosBy(LoteConfeccaoItem item) {
		if (item != null && item.getConfeccao() != null && item.getConfeccao().getGrade() != null) {
			return item.getConfeccao().getGrade().getTamanhos();
		} else {
			return null;
		}
	}

	@Override
	public LoteConfeccaoFilterFinder getFilterFinder() {
		return filterFinder;
	}

	public void setFilterFinder(LoteConfeccaoFilterFinder filterFinder) {
		this.filterFinder = filterFinder;
	}

	public LoteConfeccaoItemDataMapper getLoteConfeccaoItemDataMapper() {
		return loteConfeccaoItemDataMapper;
	}

	public void setLoteConfeccaoItemDataMapper(LoteConfeccaoItemDataMapper loteConfeccaoItemDataMapper) {
		this.loteConfeccaoItemDataMapper = loteConfeccaoItemDataMapper;
	}

	public ConfeccaoFinder getConfeccaoFinder() {
		return confeccaoFinder;
	}

	public void setConfeccaoFinder(ConfeccaoFinder confeccaoFinder) {
		this.confeccaoFinder = confeccaoFinder;
	}

	public List<TipoInsumo> getTiposInsumosRelatorios() {
		if (tiposInsumosRelatorios == null) {
			tiposInsumosRelatorios = new ArrayList<TipoInsumo>();
		}
		return tiposInsumosRelatorios;
	}

	public void setTiposInsumosRelatorios(List<TipoInsumo> tiposInsumosRelatorios) {
		this.tiposInsumosRelatorios = tiposInsumosRelatorios;
	}

	public String getTipoRelatorio() {
		return tipoRelatorio;
	}

	public void setTipoRelatorio(String tipoRelatorio) {
		this.tipoRelatorio = tipoRelatorio;
	}

	@Override
	public ReportBuilder getReportBuilder() {
		return reportBuilder;
	}

	@Override
	public void setReportBuilder(ReportBuilder reportBuilder) {
		this.reportBuilder = reportBuilder;
	}

	public Map<Grade, List<LoteConfeccaoItem>> getItensLotePorGrade() {
		return itensLotePorGrade;
	}

	public void setItensLotePorGrade(Map<Grade, List<LoteConfeccaoItem>> itensLotePorGrade) {
		this.itensLotePorGrade = itensLotePorGrade;
	}

	/**
	 * Informa quais grades tem no lote.
	 * 
	 * @return
	 */
	public List<Grade> getGrades() {
		if (grades == null) {
			this.grades = new ArrayList<Grade>();
			Grade grade = new Grade();
			grade.setTamanhos(new ArrayList<GradeTamanho>());
			for (int i = 0; i < 14; i++) {
				grade.getTamanhos().add(null);
			}
			this.grades.add(grade);
		}
		return grades;
	}

	public void setGrades(List<Grade> grades) {
		this.grades = grades;
	}

	public Map<LoteConfeccaoItem, Boolean> getItensLoteSelecionados() {
		if (itensLoteSelecionados == null) {
			itensLoteSelecionados = new HashMap<LoteConfeccaoItem, Boolean>();
		}
		return itensLoteSelecionados;
	}

	public void setItensLoteSelecionados(Map<LoteConfeccaoItem, Boolean> itensLoteSelecionados) {
		this.itensLoteSelecionados = itensLoteSelecionados;
	}

	public void selecionarTodos(boolean todos) {
		if (!todos) {
			setItensLoteSelecionados(null);
		} else {
			for (LoteConfeccaoItem item : getE().getItens()) {
				getItensLoteSelecionados().put(item, Boolean.TRUE);

				if (!getGrades().contains(item.getConfeccao().getGrade())) {
					Grade grade = item.getConfeccao().getGrade();
					getGrades().add(grade);
				}
				if (!getItensLotePorGrade().containsKey(item.getConfeccao().getGrade())) {
					getItensLotePorGrade().put(item.getConfeccao().getGrade(), new ArrayList<LoteConfeccaoItem>());
				}
				getItensLotePorGrade().get(item.getConfeccao().getGrade()).add(item);
			}
		}
	}

}
