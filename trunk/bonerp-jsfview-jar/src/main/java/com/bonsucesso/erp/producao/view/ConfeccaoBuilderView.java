package com.bonsucesso.erp.producao.view;



import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.event.ValueChangeEvent;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.estoque.business.CalculoPreco;
import com.bonsucesso.erp.estoque.data.GradeFinder;
import com.bonsucesso.erp.estoque.model.Grade;
import com.bonsucesso.erp.estoque.model.GradeTamanho;
import com.bonsucesso.erp.producao.business.ConfeccaoBusiness;
import com.bonsucesso.erp.producao.data.ConfeccaoDataMapper;
import com.bonsucesso.erp.producao.data.ConfeccaoFinder;
import com.bonsucesso.erp.producao.data.ConfeccaoItemDataMapper;
import com.bonsucesso.erp.producao.data.ConfeccaoPrecoDataMapper;
import com.bonsucesso.erp.producao.data.InstituicaoFinder;
import com.bonsucesso.erp.producao.data.InsumoFinder;
import com.bonsucesso.erp.producao.data.TipoArtigoFinder;
import com.bonsucesso.erp.producao.model.Confeccao;
import com.bonsucesso.erp.producao.model.ConfeccaoItem;
import com.bonsucesso.erp.producao.model.ConfeccaoItemQtde;
import com.bonsucesso.erp.producao.model.ConfeccaoPreco;
import com.bonsucesso.erp.producao.model.Instituicao;
import com.bonsucesso.erp.producao.model.Insumo;
import com.bonsucesso.erp.producao.model.TipoArtigo;
import com.bonsucesso.erp.producao.model.TipoInsumo;
import com.ocabit.base.view.Clipboard;
import com.ocabit.base.view.StoredViewInfoHandler;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;
import com.ocabit.utils.currency.CurrencyUtils;
import com.ocabit.utils.reports.ReportBuilder;


/**
 * View para construir e manipular confecções.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("confeccaoBuilderView")
@Scope("view")
public class ConfeccaoBuilderView implements Serializable {

	/**
	 *	
	 */
	private static final long serialVersionUID = -5201510239547723579L;

	protected static Logger logger = Logger.getLogger(ConfeccaoBuilderView.class);

	@Autowired
	private ConfeccaoBusiness confeccaoBusiness;

	@Autowired
	private InstituicaoFinder instituicaoFinder;

	@Autowired
	private ConfeccaoFinder confeccaoFinder;

	@Autowired
	private ConfeccaoItemDataMapper confeccaoItemDataMapper;

	@Autowired
	private ConfeccaoDataMapper confeccaoDataMapper;

	@Autowired
	private ConfeccaoPrecoDataMapper confeccaoPrecoDataMapper;

	@Autowired
	private TipoArtigoFinder tipoArtigoFinder;

	@Autowired
	private CalculoPreco calculoPreco;

	private Instituicao instituicao;

	private TipoArtigo tipoArtigo;

	private Confeccao confeccao;

	private List<TipoArtigo> tiposArtigos;

	private List<Confeccao> confeccoes;

	private ConfeccaoItem confeccaoItem;

	private ConfeccaoPreco preco;

	private Map<ConfeccaoItem, Boolean> itensSelecs;

	@Autowired
	private InsumoFinder insumoFinder;

	@Autowired
	private InsumoFormListView insumoFormListView;

	private Confeccao confeccaoClone;

	@Autowired
	private ReportBuilder reportBuilder;

	@Autowired
	private StoredViewInfoHandler storedViewInfoHandler;

	private String[] camposStored = new String[] { "instituicao", "tipoArtigo", "confeccao", "exibirConfeccoesOcultas" };

	private String viewName = "confeccaoBuilderView";

	/**
	 * Informa, ao salvar, que está alterando em lote.
	 */
	public boolean alterandoQtdesTamanhoEmLote = false;

	public ConfeccaoItem confeccaoItemAnterior;

	private boolean exibirSomenteTiposArtigosDaInstituicao = true;

	private boolean exibirConfeccoesOcultas = true;

	@Autowired
	private Clipboard clipboard;

	private List<TipoInsumo> tiposInsumosRelatorios;

	@Autowired
	private GradeFinder gradeFinder;

	@PostConstruct
	public void init() {
		setInstituicao(null);
		setTipoArtigo(null);
		setConfeccao(null);
		setConfeccaoItem(null);
		try {
			getStoredViewInfoHandler().processStoredViewInfo(viewName, this); // aqui deve ser setado a instituicao, tipoArtigo e confeccao
			if (getInstituicao() != null) {
				setInstituicao(getInstituicaoFinder().refresh(getInstituicao()));
				if (getTipoArtigo() != null) {
					setTipoArtigo(getTipoArtigoFinder().refresh(getTipoArtigo()));
					setConfeccoes(getConfeccaoFinder().findBy(getInstituicao(), getTipoArtigo()));
				}
				if (getConfeccao() != null) {
					setConfeccao(getConfeccaoFinder().refresh(getConfeccao()));
				}
			}
			if ((getConfeccao() != null) && (getConfeccao().getId() != null)) {
				buildConfeccaoTable();
			}
		} catch (ViewException e) {
			JSFUtils.addErrorMsg("Erro ao inicializar.");
			JSFUtils.addHandledException(e);
			logger.error(e);
		}
	}

	public void updateTipoInsumo() {
		try {
			if ((getConfeccaoItem() != null) && (getConfeccaoItem().getInsumo() != null)) {
				Insumo insumo = getInsumoFinder().refresh(getConfeccaoItem().getInsumo());
				// touch
				insumo.getPrecos().size();
				getConfeccaoItem().setInsumo(insumo);
			}
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public ConfeccaoItem getConfeccaoItem() {
		if (confeccaoItem == null) {
			confeccaoItem = new ConfeccaoItem();
			getConfeccaoDataMapper().getEntityIdHandler().handleEntityIdInserting(confeccaoItem);
		}
		if (confeccaoItem.getInsumo() == null) {
			confeccaoItem.setInsumo(new Insumo());
		}
		return confeccaoItem;
	}

	public void setConfeccaoItem(ConfeccaoItem confeccaoItem) {
		try {
			if ((confeccaoItem != null) && (confeccaoItem.getId() != null)) {
				confeccaoItem = getConfeccaoItemDataMapper().refresh(confeccaoItem);
				// para não mandar construir a tabela para a confecção inteira novamente, constrói somente os mapas do item a ser editado
				getConfeccaoBusiness().buildMapsItem(confeccaoItem);
			}
			// joga este flag para false, pois o mesmo dlg que altera confeccaoItem, tbm serve para alterar em lote.
			setAlterandoQtdesTamanhoEmLote(false);
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
		this.confeccaoItem = confeccaoItem;
	}

	public void storeViewInfo() {
		getStoredViewInfoHandler().store(viewName, this, camposStored);
	}

	public void updateInstituicao() {
		if ((getTiposArtigos() != null) && (getTiposArtigos().size() == 1)) {
			setTipoArtigo(getTiposArtigos().get(0));
			updateTipoArtigo();
		} else {
			setTipoArtigo(null);
		}

		setConfeccao(null);
		storeViewInfo();
	}

	public void updateTipoArtigo() {
		try {
			setConfeccoes(getConfeccaoFinder().findBy(getInstituicao(), getTipoArtigo(), isExibirConfeccoesOcultas()));
			// se só retornar 1, já deixa selecionado
			if ((getConfeccoes() != null) && (getConfeccoes().size() == 1)) {
				setConfeccao(getConfeccoes().get(0));
				buildConfeccaoTable();
			} else {
				setConfeccao(null);
				storeViewInfo();
			}
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public void deletarConfeccaoItem(ConfeccaoItem confeccaoItem) {
		try {
			setConfeccaoItem(confeccaoItem);
			getConfeccaoItemDataMapper().delete(getConfeccaoItem());
			setConfeccaoItem(null);
			buildConfeccaoTable();
			JSFUtils.addInfoMsgRegistroDeletado();
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addErrorMsgErroAoDeletar();
		}
	}

	/**
	 * Salva as qtdes/tamanho da confeccaoItem (ou manda salvar em lote).
	 */
	public void salvarQtdesConfeccaoItem() {
		try {
			// atualiza
			ConfeccaoItem confeccaoItem = getConfeccaoItemDataMapper().refresh(getConfeccaoItem());

			// limpa todas as qtdesGrade do item para em seguida reinserir...
			for (ConfeccaoItemQtde ciq : confeccaoItem.getQtdesGrade()) {
				ciq.setConfeccaoItem(null);
			}
			confeccaoItem.getQtdesGrade().clear();
			// salva (ou seja, deleta todos os qtdesGrade)
			confeccaoItem = getConfeccaoItemDataMapper().save(confeccaoItem);

			for (Map.Entry<GradeTamanho, BigDecimal> entry : getConfeccaoItem().getMapaGradeQtdes().entrySet()) {
				// só vai inserir nos qtdeGrades se não tiver sido passado vazio (null) ou 0.0
				if ((entry.getValue() != null) && ((((Number) entry.getValue()).doubleValue()) > 0.0)) {
					ConfeccaoItemQtde ciq = new ConfeccaoItemQtde();
					getConfeccaoItemDataMapper().getEntityIdHandler().handleEntityIdInserting(ciq);
					ciq.setGradeTamanho(entry.getKey());
					ciq.setQtde(CurrencyUtils.getBigDecimalScaled(((Number) entry.getValue()).doubleValue(), 3));
					confeccaoItem.addQtdeGrade(ciq);
				}
			}

			getConfeccaoItemDataMapper().save(confeccaoItem);
			setConfeccaoItem(null);
			setConfeccao(getConfeccaoFinder().refresh(getConfeccao()));
			buildConfeccaoTable();
			final RequestContext context = RequestContext.getCurrentInstance();
			context.addCallbackParam("saved", true);
			context.addCallbackParam("dlgId", "dlgQtdesTamanho");
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	/**
	 * Limpa todos os valores de qtdes/tamanho do opnDlgQtdesTamanho.
	 */
	public void limparQtdesTamanho() {
		for (Map.Entry<GradeTamanho, BigDecimal> e : getConfeccaoItem().getMapaGradeQtdes().entrySet()) {
			e.setValue(null);
		}

	}

	/**
	 * Iguala todos os valores de qtdes/tamanho do opnDlgQtdesTamanho se baseando pelo maior.
	 */
	public void igualarQtdesTamanho() {

		List<BigDecimal> valores = new ArrayList<BigDecimal>(getConfeccaoItem().getMapaGradeQtdes().values());

		Collections.sort(valores, new Comparator<BigDecimal>() {

			@Override
			public int compare(BigDecimal o1, BigDecimal o2) {
				return new CompareToBuilder()
						.append(o2, o1)
						.toComparison();
			}
		});

		BigDecimal maiorValor = valores.get(0);

		for (Map.Entry<GradeTamanho, BigDecimal> e : getConfeccaoItem().getMapaGradeQtdes().entrySet()) {
			e.setValue(maiorValor);
		}

	}

	public Map<ConfeccaoItem, Boolean> getItensSelecs() {
		if (itensSelecs == null) {
			itensSelecs = new HashMap<ConfeccaoItem, Boolean>();
			if ((getConfeccao() != null) && (getConfeccao().getItens() != null)) {
				for (ConfeccaoItem confeccaoItem : getConfeccao().getItens()) {
					itensSelecs.put(confeccaoItem, Boolean.FALSE);
				}
			}
		}
		return itensSelecs;
	}

	public void setItensSelecs(Map<ConfeccaoItem, Boolean> itensSelecs) {
		this.itensSelecs = itensSelecs;
	}

	public boolean hasItensSelecs() {
		for (Map.Entry<ConfeccaoItem, Boolean> e : getItensSelecs().entrySet()) {
			if (e.getValue() == Boolean.TRUE) {
				return true;
			}
		}
		return false;
	}

	public List<Insumo> acInsumo(String str) {
		try {
			return getInsumoFinder().findByStrAndNotInConfeccao(str, getConfeccao());
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
			return null;
		}
	}

	public void novoInsumo() {
		setConfeccaoItem(null);
	}

	public void salvarConfeccaoItem() {
		final RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam("dlgId", "dlgInsumoSel");
		try {
			getConfeccaoItem().setConfeccao(getConfeccao());

			setConfeccaoItem(getConfeccaoItemDataMapper().save(getConfeccaoItem()));

			setConfeccao(getConfeccaoFinder().refresh(getConfeccao()));

			buildConfeccaoTable();
			// setConfeccaoItem(null);
			context.addCallbackParam("saved", true);
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		} catch (Exception e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao salvar o item.");
		}
	}

	public void novaConfeccao() {
		Confeccao confeccao = new Confeccao();
		confeccao.setInstituicao(getInstituicao());
		confeccao.setTipoArtigo(getTipoArtigo());
		setConfeccao(confeccao);
	}

	public void salvarConfeccao() {
		final RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam("saved", true);
		try {
			Confeccao confeccao = getConfeccaoDataMapper().save(getConfeccao());
			// aqui o updateTipoArtigo() vai fazer setConfeccao(null), pois tem mais de um tipo de confecção agora...
			// então, reseto para a salva...
			setTipoArtigo(getConfeccao().getTipoArtigo());
			setInstituicao(getConfeccao().getInstituicao());
			updateTipoArtigo();
			setConfeccao(confeccao);
			buildConfeccaoTable();
			context.addCallbackParam("dlgId", "dlgConfeccao");
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public void deletarConfeccao() {
		try {
			getConfeccaoDataMapper().delete(getConfeccao());
			setConfeccao(null);
			updateTipoArtigo();
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public void salvarNovoInsumo() {
		if (getInsumoFormListView().save()) {
			ConfeccaoItem confeccaoItem = new ConfeccaoItem();
			getConfeccaoDataMapper().getEntityIdHandler().handleEntityIdInserting(confeccaoItem);
			confeccaoItem.setInsumo(getInsumoFormListView().getE());
			setConfeccaoItem(confeccaoItem);
			salvarConfeccaoItem();
			final RequestContext context = RequestContext.getCurrentInstance();
			context.addCallbackParam("saved", true);
			context.addCallbackParam("dlgId", "dlgInsumo");
		}
	}

	public void novoConfeccaoClone() {
		setConfeccaoClone(new Confeccao());
		getConfeccaoClone().setInstituicao(getInstituicao());
		getConfeccaoClone().setTipoArtigo(getTipoArtigo());
		getConfeccaoClone().setDescricao(getTipoArtigo().getDescricao() + " " + getInstituicao().getNome());
	}

	public void clonarConfeccao() {
		try {
			getConfeccaoClone().setCustoOperacionalPadrao(getConfeccao().getCustoOperacionalPadrao());
			getConfeccaoClone().setCustoFinanceiroPadrao(getConfeccao().getCustoFinanceiroPadrao());
			getConfeccaoClone().setMargemPadrao(getConfeccao().getMargemPadrao());
			getConfeccaoClone().setPrazoPadrao(getConfeccao().getPrazoPadrao());
			getConfeccaoClone().setGrade(getConfeccao().getGrade());
			getConfeccaoClone().setModoCalculo(getConfeccao().getModoCalculo());
			for (ConfeccaoItem item : getConfeccao().getItens()) {
				ConfeccaoItem itemClonado = new ConfeccaoItem();
				getConfeccaoDataMapper().getEntityIdHandler().handleEntityIdInserting(itemClonado);
				itemClonado.setInsumo(item.getInsumo());
				itemClonado.setConfeccao(getConfeccaoClone());
				for (ConfeccaoItemQtde ciq : item.getQtdesGrade()) {
					ConfeccaoItemQtde ciqClonado = new ConfeccaoItemQtde();
					getConfeccaoDataMapper().getEntityIdHandler().handleEntityIdInserting(ciqClonado);
					ciqClonado.setConfeccaoItem(itemClonado);
					ciqClonado.setGradeTamanho(ciq.getGradeTamanho());
					ciqClonado.setQtde(ciq.getQtde());
					itemClonado.addQtdeGrade(ciqClonado);
				}
				getConfeccaoClone().addItem(itemClonado);
			}

			setConfeccaoClone(getConfeccaoDataMapper().save(getConfeccaoClone()));
			// aqui o updateTipoArtigo() vai fazer setConfeccao(null), pois tem mais de um tipo de confecção agora...
			setInstituicao(getConfeccaoClone().getInstituicao());
			updateInstituicao();
			// então, reseto para a salva...
			setTipoArtigo(getConfeccaoClone().getTipoArtigo());
			updateTipoArtigo();
			setConfeccao(getConfeccaoClone());
			buildConfeccaoTable();
			final RequestContext context = RequestContext.getCurrentInstance();
			context.addCallbackParam("saved", true);
			context.addCallbackParam("dlgId", "dlgClonarConfeccao");

			setConfeccaoClone(null);
		} catch (Throwable e) {
			novoConfeccaoClone();
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao clonar confecção.");
		}
	}

	public void updateDescricaoClone() {
		getConfeccaoClone().setDescricao(getConfeccaoClone().getTipoArtigo().getDescricao() + " "
				+ getConfeccaoClone().getInstituicao().getNome());
	}

	public void buildConfeccaoTable() {
		try {
			if (getConfeccao() != null) {
				setItensSelecs(null);
				setConfeccao(getConfeccaoBusiness().buildConfeccaoTable(getConfeccao()));
				storeViewInfo();
			}
		} catch (Exception e) {
			JSFUtils.addErrorMsg("Erro ao construir tabela de confecção");
		}
	}

	public void criarPrecos() {
		try {
			setConfeccao(getConfeccaoFinder().refresh(getConfeccao()));
			getConfeccao().limparPrecos();
			Confeccao confeccao = getConfeccaoDataMapper().save(getConfeccao());
			buildConfeccaoTable();
			setConfeccao(getConfeccaoBusiness().calcularPrecos(confeccao));
			confeccao = getConfeccaoDataMapper().save(getConfeccao());
			setConfeccao(confeccao);
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public Double totalPorTipoInsumo(Confeccao confeccao, TipoInsumo tipoInsumo, GradeTamanho gradeTamanho) {
		// confeccao = getConfeccaoFinder().refresh(confeccao);
		return getConfeccaoBusiness().totalPorTipoInsumo(confeccao, tipoInsumo, gradeTamanho);

	}

	public void updateConfeccaoId(ValueChangeEvent event) {
		try {
			Long confeccaoId = Long.parseLong((String) event.getNewValue());
			if (confeccaoId != null) {
				Confeccao confeccao = getConfeccaoFinder().findById(confeccaoId);
				setConfeccao(confeccao);
				setInstituicao(getConfeccao().getInstituicao());
				setTipoArtigo(getConfeccao().getTipoArtigo());
				setConfeccoes(getConfeccaoFinder().findBy(getInstituicao(), getTipoArtigo()));
				buildConfeccaoTable();
			}
		} catch (Exception e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao setar o id.");
		}

	}

	public ReportBuilder getReportBuilder() {
		return reportBuilder;
	}

	public void setReportBuilder(ReportBuilder reportBuilder) {
		this.reportBuilder = reportBuilder;
	}

	public void submitFormRelatorio() {
		final RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam("dlgId", "dlgRelPlanilhaConfeccao");
		context.addCallbackParam("saved", true);
		context.addCallbackParam("postFunction", "_$('btnRelPlanilhaConfeccao').click()");
	}

	public void imprimirRelPlanilhaConfeccao() {
		try {

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("confeccaoId", getConfeccao().getId());

			for (int i = 1; i <= 14; i++) {
				params.put("p_tamanho_" + i, "");
			}

			for (GradeTamanho gt : getConfeccao().getGrade().getTamanhos()) {
				params.put("p_tamanho_" + gt.getOrdem(), gt.getTamanho());
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
			logger.debug("tiposInsumosIds: " + StringUtils.join(tiposInsumosIds, ","));
			logger.debug("confeccaoId: " + getConfeccao().getId());

			params.put("tiposInsumosIds", StringUtils.join(tiposInsumosIds, ","));

			getReportBuilder().imprimir(params, "producao/rpPlanilhaConfeccao", "PlanilhaConfeccao");
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public ConfeccaoPreco getPreco() {
		if (preco == null) {
			novoPreco();
		}
		return preco;
	}

	public void setPreco(ConfeccaoPreco preco) {
		this.preco = preco;
	}

	public void novoPreco() {
		ConfeccaoPreco preco = new ConfeccaoPreco();
		preco.setConfeccao(getConfeccao());
		setPreco(preco);
	}

	public void deletarPreco(ConfeccaoPreco preco) {
		final RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam("dlgId", "dlgPreco");
		try {
			Confeccao confeccao = getConfeccaoDataMapper().refresh(getConfeccao());
			confeccao.getPrecos().remove(preco);
			setConfeccao(getConfeccaoDataMapper().save(confeccao));
			context.addCallbackParam("saved", true);
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public void salvarPreco() {
		final RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam("dlgId", "dlgPreco");
		try {
			Confeccao confeccao = getConfeccaoDataMapper().refresh(getConfeccao());
			if (getPreco().getId() != null) {
				getConfeccaoPrecoDataMapper().save(getPreco());
				setConfeccao(getConfeccaoDataMapper().refresh(getConfeccao()));
			} else {
				confeccao.getPrecos().add(getPreco());
				getConfeccaoDataMapper().save(getConfeccao());
			}
			context.addCallbackParam("saved", true);
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public void calcularCoeficiente() {
		try {
			getCalculoPreco().calcularCoeficiente(getPreco());
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public void calcularPrecos() {
		try {
			getCalculoPreco().calcularPrecos(getPreco());
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<ConfeccaoItem> clipboardItens() {
		List<ConfeccaoItem> itensList = new ArrayList<ConfeccaoItem>();
		if ((getClipboard().getObj() != null) &&
				getClipboard().getObj().getClass().isAssignableFrom(getItensSelecs().getClass())) {

			Map<ConfeccaoItem, Boolean> itensCopiados = (Map<ConfeccaoItem, Boolean>) getClipboard().getObj();
			for (Map.Entry<ConfeccaoItem, Boolean> e : itensCopiados.entrySet()) {
				if (e.getValue() == Boolean.TRUE) {
					itensList.add(e.getKey());
				}
			}
		}
		return itensList;
	}

	public void clipboardCopyItens() {

		for (Map.Entry<ConfeccaoItem, Boolean> e : getItensSelecs().entrySet()) {
			if (e.getValue() == Boolean.TRUE) {
				getClipboard().copy(getItensSelecs());
				return;
			}
		}
		getClipboard().copy(null);
	}

	public void clipboardPasteItens() {
		for (ConfeccaoItem itemCopiado : clipboardItens()) {
			ConfeccaoItem novoItem = new ConfeccaoItem();
			getConfeccaoDataMapper().getEntityIdHandler().handleEntityIdInserting(novoItem);

			getConfeccaoItemDataMapper().getEntityIdHandler().handleEntityIdInserting(novoItem);
			novoItem.setConfeccao(getConfeccao());
			novoItem.setInsumo(itemCopiado.getInsumo());

			for (ConfeccaoItemQtde qtdeCopiada : itemCopiado.getQtdesGrade()) {
				ConfeccaoItemQtde novaQtde = new ConfeccaoItemQtde();
				getConfeccaoDataMapper().getEntityIdHandler().handleEntityIdInserting(novaQtde);
				getConfeccaoItemDataMapper().getEntityIdHandler().handleEntityIdInserting(novaQtde);
				novaQtde.setConfeccaoItem(novoItem);
				novaQtde.setGradeTamanho(qtdeCopiada.getGradeTamanho());
				novaQtde.setQtde(qtdeCopiada.getQtde());
				novaQtde.setValor(qtdeCopiada.getValor());
				novoItem.getQtdesGrade().add(novaQtde);
			}
			setConfeccaoItem(novoItem);
			salvarConfeccaoItem();
		}
		final RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam("dlgId", "dlgColarItens");
		context.addCallbackParam("saved", true);

	}

	public StoredViewInfoHandler getStoredViewInfoHandler() {
		return storedViewInfoHandler;
	}

	public void setStoredViewInfoHandler(StoredViewInfoHandler storedViewInfoHandler) {
		this.storedViewInfoHandler = storedViewInfoHandler;
	}

	public Instituicao getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}

	public TipoArtigo getTipoArtigo() {
		return tipoArtigo;
	}

	public void setTipoArtigo(TipoArtigo tipoArtigo) {
		this.tipoArtigo = tipoArtigo;
	}

	public Confeccao getConfeccao() {
		if (confeccao == null) {
			confeccao = new Confeccao();
		}
		return confeccao;
	}

	public void setConfeccao(Confeccao confeccao) {
		this.confeccao = confeccao;
	}

	public List<TipoArtigo> getTiposArtigos() {
		try {
			if (isExibirSomenteTiposArtigosDaInstituicao()) {
				tiposArtigos = getTipoArtigoFinder().findByInstituicao(getInstituicao());
			} else {
				tiposArtigos = getTipoArtigoFinder().findAll("descricao");
			}
		} catch (ViewException e) {
			JSFUtils.addErrorMsg("Erro ao buscar os tipos de artigos.");
			JSFUtils.addHandledException(e);
			logger.error(e);
		}
		return tiposArtigos;
	}

	public List<Grade> listarOutrasGrades() {
		try {
			List<Grade> todas = getGradeFinder().findAll("codigo");
			todas.remove(getConfeccao().getGrade());
			return todas;
		} catch (ViewException e) {
			JSFUtils.addHandledException(e);
			return null;
		}
	}

	public void migrarGrade(Grade novaGrade) {
		try {
			getConfeccaoBusiness().migrarGrade(getConfeccao(), novaGrade);
			buildConfeccaoTable();
			final RequestContext context = RequestContext.getCurrentInstance();
			context.addCallbackParam("saved", true);
			context.addCallbackParam("dlgId", "dlgMigrarGrade");
			JSFUtils.addInfoMsg("A grade foi migrada com sucesso.");
		} catch (ViewException e) {
			JSFUtils.addHandledException(e);
		}
	}

	public void setTiposArtigos(List<TipoArtigo> tiposArtigos) {
		this.tiposArtigos = tiposArtigos;
	}

	public List<Confeccao> getConfeccoes() {
		return confeccoes;
	}

	public void setConfeccoes(List<Confeccao> confeccoes) {
		this.confeccoes = confeccoes;
	}

	public ConfeccaoBusiness getConfeccaoBusiness() {
		return confeccaoBusiness;
	}

	public void setConfeccaoBusiness(ConfeccaoBusiness confeccaoBusiness) {
		this.confeccaoBusiness = confeccaoBusiness;
	}

	public InstituicaoFinder getInstituicaoFinder() {
		return instituicaoFinder;
	}

	public void setInstituicaoFinder(InstituicaoFinder instituicaoFinder) {
		this.instituicaoFinder = instituicaoFinder;
	}

	public ConfeccaoFinder getConfeccaoFinder() {
		return confeccaoFinder;
	}

	public void setConfeccaoFinder(ConfeccaoFinder confeccaoFinder) {
		this.confeccaoFinder = confeccaoFinder;
	}

	public ConfeccaoItemDataMapper getConfeccaoItemDataMapper() {
		return confeccaoItemDataMapper;
	}

	public void setConfeccaoItemDataMapper(ConfeccaoItemDataMapper confeccaoItemDataMapper) {
		this.confeccaoItemDataMapper = confeccaoItemDataMapper;
	}

	public ConfeccaoDataMapper getConfeccaoDataMapper() {
		return confeccaoDataMapper;
	}

	public void setConfeccaoDataMapper(ConfeccaoDataMapper confeccaoDataMapper) {
		this.confeccaoDataMapper = confeccaoDataMapper;
	}

	public ConfeccaoPrecoDataMapper getConfeccaoPrecoDataMapper() {
		return confeccaoPrecoDataMapper;
	}

	public void setConfeccaoPrecoDataMapper(ConfeccaoPrecoDataMapper confeccaoPrecoDataMapper) {
		this.confeccaoPrecoDataMapper = confeccaoPrecoDataMapper;
	}

	public TipoArtigoFinder getTipoArtigoFinder() {
		return tipoArtigoFinder;
	}

	public void setTipoArtigoFinder(TipoArtigoFinder tipoArtigoFinder) {
		this.tipoArtigoFinder = tipoArtigoFinder;
	}

	public CalculoPreco getCalculoPreco() {
		return calculoPreco;
	}

	public void setCalculoPreco(CalculoPreco calculoPreco) {
		this.calculoPreco = calculoPreco;
	}

	public InsumoFinder getInsumoFinder() {
		return insumoFinder;
	}

	public void setInsumoFinder(InsumoFinder insumoFinder) {
		this.insumoFinder = insumoFinder;
	}

	public boolean isAlterandoQtdesTamanhoEmLote() {
		return alterandoQtdesTamanhoEmLote;
	}

	public void setAlterandoQtdesTamanhoEmLote(boolean alterandoQtdesTamanhoEmLote) {
		this.alterandoQtdesTamanhoEmLote = alterandoQtdesTamanhoEmLote;
	}

	public Clipboard getClipboard() {
		return clipboard;
	}

	public void setClipboard(Clipboard clipboard) {
		this.clipboard = clipboard;
	}

	public ConfeccaoItem getConfeccaoItemAnterior() {
		return confeccaoItemAnterior;
	}

	public void setConfeccaoItemAnterior(ConfeccaoItem confeccaoItemAnterior) {
		this.confeccaoItemAnterior = confeccaoItemAnterior;
	}

	public boolean isExibirSomenteTiposArtigosDaInstituicao() {
		return exibirSomenteTiposArtigosDaInstituicao;
	}

	public void setExibirSomenteTiposArtigosDaInstituicao(boolean exibirSomenteTiposArtigosDaInstituicao) {
		this.exibirSomenteTiposArtigosDaInstituicao = exibirSomenteTiposArtigosDaInstituicao;
	}

	public boolean isExibirConfeccoesOcultas() {
		return exibirConfeccoesOcultas;
	}

	public void setExibirConfeccoesOcultas(boolean exibirConfeccoesOcultas) {
		this.exibirConfeccoesOcultas = exibirConfeccoesOcultas;
	}

	public InsumoFormListView getInsumoFormListView() {
		return insumoFormListView;
	}

	public void setInsumoFormListView(InsumoFormListView insumoFormListView) {
		this.insumoFormListView = insumoFormListView;
	}

	public Confeccao getConfeccaoClone() {
		return confeccaoClone;
	}

	public void setConfeccaoClone(Confeccao confeccaoClone) {
		this.confeccaoClone = confeccaoClone;
	}

	public List<TipoInsumo> getTiposInsumosRelatorios() {
		return tiposInsumosRelatorios;
	}

	public void setTiposInsumosRelatorios(List<TipoInsumo> tiposInsumosRelatorios) {
		this.tiposInsumosRelatorios = tiposInsumosRelatorios;
	}

	public GradeFinder getGradeFinder() {
		return gradeFinder;
	}

	public void setGradeFinder(GradeFinder gradeFinder) {
		this.gradeFinder = gradeFinder;
	}

}
