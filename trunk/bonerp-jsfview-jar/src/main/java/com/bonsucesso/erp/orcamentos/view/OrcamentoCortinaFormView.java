package com.bonsucesso.erp.orcamentos.view;



import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.ReorderEvent;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.base.model.Endereco;
import com.bonsucesso.erp.cortinas.business.CortinaCalculos;
import com.bonsucesso.erp.cortinas.business.CortinaLargurasCompar;
import com.bonsucesso.erp.cortinas.business.OrcamentoCortinaBusiness;
import com.bonsucesso.erp.cortinas.data.ArtigoCortinaFinder;
import com.bonsucesso.erp.cortinas.data.CortinaDataMapper;
import com.bonsucesso.erp.cortinas.data.CortinaFinder;
import com.bonsucesso.erp.cortinas.data.CortinaItemDataMapper;
import com.bonsucesso.erp.cortinas.model.ArtigoCortina;
import com.bonsucesso.erp.cortinas.model.Cortina;
import com.bonsucesso.erp.cortinas.model.CortinaItem;
import com.bonsucesso.erp.cortinas.model.OrientacaoTecido;
import com.bonsucesso.erp.cortinas.model.Tecido;
import com.bonsucesso.erp.cortinas.model.TipoArtigoCortina;
import com.bonsucesso.erp.crm.data.ClienteFinder;
import com.bonsucesso.erp.crm.model.Cliente;
import com.bonsucesso.erp.estoque.business.CalculoPreco;
import com.bonsucesso.erp.orcamentos.data.OrcamentoDataMapper;
import com.bonsucesso.erp.orcamentos.data.OrcamentoGrupoItemDataMapper;
import com.bonsucesso.erp.orcamentos.data.OrcamentoItemDataMapper;
import com.bonsucesso.erp.orcamentos.model.Orcamento;
import com.bonsucesso.erp.orcamentos.model.OrcamentoGrupoItem;
import com.bonsucesso.erp.orcamentos.model.OrcamentoItem;
import com.bonsucesso.erp.orcamentos.model.TipoOrcamento;
import com.ocabit.base.view.AbstractEntityFormView;
import com.ocabit.base.view.Clipboard;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.config.data.ConfigFinder;
import com.ocabit.jsf.utils.JSFUtils;
import com.ocabit.security.spring.SecurityUtils;
import com.ocabit.utils.calendar.CalendarUtil;
import com.ocabit.utils.currency.CurrencyUtils;
import com.ocabit.utils.reports.ReportBuilder;


/**
 * View para a entidade Orcamento (de Cortinas).
 *
 * @author Carlos Eduardo Pauluk
 */
@Component("orcamentoCortinaFormView")
@Scope("view")
public class OrcamentoCortinaFormView extends AbstractEntityFormView<Orcamento, OrcamentoDataMapper> {

	/**
	 *
	 */
	private static final long serialVersionUID = 4122984223583988256L;

	protected static Logger logger = Logger.getLogger(OrcamentoCortinaFormView.class);

	private String actualContent = "orcamentoCortinaFormB_cabecalho";

	@Autowired
	private ConfigFinder configFinder;

	private Cortina cortina;

	private CortinaItem cortinaItem;

	@Autowired
	private OrcamentoGrupoItemDataMapper grupoDataMapper;

	@Autowired
	private OrcamentoItemDataMapper orcamentoItemDataMapper;

	@Autowired
	private OrcamentoItemDataMapper itemDataMapper;

	@Autowired
	private CortinaDataMapper cortinaDataMapper;

	@Autowired
	private CortinaItemDataMapper cortinaItemDataMapper;

	@Autowired
	private ArtigoCortinaFinder artigoCortinaFinder;

	@Autowired
	private ClienteFinder clienteFinder;

	@Autowired
	private CortinaFinder cortinaFinder;

	@Autowired
	private OrcamentoCortinaBusiness business;

	private Integer qtdeLados = 2;

	private List<Integer> camadas;

	private List<Integer> camadasSelecionadas;

	private OrcamentoGrupoItem grupo;

	@Autowired
	private CortinaCalculos cortinaCalculos;

	@Autowired
	private ReportBuilder reportBuilder;

	private boolean selecionarCamadas = true;

	private OrcamentoItem item;

	// CAMADAS ou ITENS
	private String tipoExibicaoItens = "CAMADAS";

	private List<CortinaItem> itensAgrupados;

	private List<OrcamentoItem> selecteds;

	private List<CortinaLargurasCompar> comparativoLarguras;

	private CortinaLargurasCompar cortinaLargurasComparSelec;

	@Autowired
	private Clipboard clipboard;

	public List<SortMeta> getSortByItensCortina() {
		List<SortMeta> l = new ArrayList<>();
		SortMeta sm1 = new SortMeta();
		sm1.setSortField("joinDateTime");
		sm1.setSortOrder(SortOrder.ASCENDING);
		l.add(sm1);
		return l;
	}

	public int sortItensCortina(Object obj1, Object obj2) {
		return 0;
	}

	public Cortina getCortina() {
		if (cortina == null) {
			cortina = new Cortina();
		}
		return cortina;
	}

	public void setCortina(Cortina cortina) {
		this.cortina = cortina;
	}

	public CortinaItem getCortinaItem() {
		if (cortinaItem == null) {
			newCortinaItem();
		}
		return cortinaItem;
	}

	public void setCortinaItem(CortinaItem cortinaItem) {
		try {
			if (cortinaItem.getId() != null) {
				this.cortinaItem = getCortinaItemDataMapper().refresh(cortinaItem);
				this.cortinaItem.getArtigoCortina().getProduto().getSaldos().size();
				this.cortinaItem.getArtigoCortina().getProduto().getPrecos().size();
			} else {
				this.cortinaItem = cortinaItem;
			}
			// touch
			updateCamadas();
			List<Integer> camadasSelecionadas = new ArrayList<Integer>();
			camadasSelecionadas.add(cortinaItem.getCamada());
			setCamadasSelecionadas(camadasSelecionadas);
			if (cortinaItem.getId() != null
					&& cortinaItem.getArtigoCortina().getTipoArtigoCortina().equals(TipoArtigoCortina.TECIDO)
					&& !cortinaItem.getDrapeado() && !cortinaItem.getBando()) {
				updateOrientacaoTecidoOuFator();
			}
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public OrcamentoItem getItem() {
		return item;
	}

	public void setItem(OrcamentoItem item) {
		try {
			Cortina cortina = getCortinaFinder().findBy(item);
			// se por algum motivo o item do orçamento não tiver uma cortina vinculada, salva uma
			if (cortina == null) {
				cortina = new Cortina();
				cortina.setOrcamentoItem(item);
				getOrcamentoItemDataMapper().save(item);
			}
			setCortina(cortina);

		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao setar Cortina");
		}
	}

	public CortinaItemDataMapper getCortinaItemDataMapper() {
		return cortinaItemDataMapper;
	}

	public void setCortinaItemDataMapper(CortinaItemDataMapper cortinaItemDataMapper) {
		this.cortinaItemDataMapper = cortinaItemDataMapper;
	}

	@Override
	public Orcamento newE() {
		Orcamento orcamento = new Orcamento();
		orcamento.setDtPreenchimento(new Date());
		orcamento.setTipoOrcamento(TipoOrcamento.CORTINAS);
		Integer prazoValidade = 30;
		try {
			prazoValidade = Integer.valueOf(configFinder
					.findConfigByChave("bonsucesso.cortinas.prazoValidadeOrcamento").getValor());
		} catch (Exception e) {
			logger.debug("Erro ao buscar a chave: bonsucesso.cortinas.prazoValidadeOrcamento");
		}
		orcamento.setPreenchidoPor(SecurityUtils.getUsername());
		orcamento.setDtValidade(CalendarUtil.incDias(new Date(), prazoValidade));
		return orcamento;
	}

	/**
	 * Cria um novo item para o orçamento (uma cortina).
	 *
	 * @param grupo
	 */
	public void novoItem(OrcamentoGrupoItem grupo) {
		try {
			setGrupo(getGrupoDataMapper().refresh(grupo));
			OrcamentoItem orcamentoItem = new OrcamentoItem();
			orcamentoItem.setGrupo(getGrupo());
			// orcamentoItem.setOrcamento(getE());
			orcamentoItem.setValorUnit(BigDecimal.ZERO);
			orcamentoItem.setQtde(CurrencyUtils.getBigDecimalScaled(1.0, 3));
			Cortina cortina = new Cortina();
			cortina.setOrcamentoItem(orcamentoItem);
			setCortina(cortina);
			setActualContent("orcamentoCortinaFormB_cortina");
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public void saveCortina() {
		try {
			if (getCortina().getTotalLarguraLados().doubleValue() != getCortina().getLargura().doubleValue()) {
				JSFUtils.addErrorMsg("Soma dos lados não corresponde a largura da cortina.");
				return;
			}
			// mando salvar o orçamento para que possa executar a lógica do beforeSave (reordenações, etc).
			OrcamentoItem orcamentoItem = getOrcamentoItemDataMapper().save(getCortina().getOrcamentoItem());
			getCortina().setOrcamentoItem(orcamentoItem);
			// o valor unitário do item do orçamento é o valor total dos itens da cortina
			orcamentoItem.setValorUnit(getCortina().getValorTotal());
			setCortina(getCortinaDataMapper().save(getCortina()));
			setE(getDataMapper().refresh(getE()));
			RequestContext context = RequestContext.getCurrentInstance();
			// context.addCallbackParam("dlgId", "dlgCortina");
			context.addCallbackParam("saved", "true");
			JSFUtils.addInfoMsgRegistroSalvo();
		} catch (Exception e) {
			logger.error(e);
			JSFUtils.addErrorMsgErroAoSalvar();
		}
	}

	public void newCortinaItem() {
		CortinaItem cortinaItem = new CortinaItem();
		cortinaItem.setCortina(getCortina());
		cortinaItem.setArtigoCortina(new ArtigoCortina());
		setCortinaItem(cortinaItem);
		setCamadasSelecionadas(null);
		updateCamadas();
	}

	public void saveCortinaItem() {
		try {
			if (getCortinaItem().getId() == null) {
				if (isSelecionarCamadas()
						&& ((getCamadasSelecionadas() == null) || (getCamadasSelecionadas().size() == 0))) {
					throw new ViewException("Informe a(s) camada(s).");
				}
				// Como mais de uma camada pode ter sido selecionada, é preciso criar um item pra cada.
				for (Integer camada : getCamadasSelecionadas()) {
					CortinaItem cortinaItem = (CortinaItem) BeanUtils.cloneBean(getCortinaItem());
					//CortinaItem cortinaItem = (CortinaItem) Cloner.copy(getCortinaItem());
					cortinaItem.setCamada(camada);
					getCortinaItemDataMapper().save(cortinaItem);
				}
			} else {
				getCortinaItemDataMapper().save(getCortinaItem());
			}
			RequestContext context = RequestContext.getCurrentInstance();
			context.addCallbackParam("dlgId", "dlgCortinaItem");
			context.addCallbackParam("saved", "true");

			setCortina(getCortinaDataMapper().refresh(getCortinaItem().getCortina()));

			JSFUtils.addInfoMsgRegistroSalvo();
		} catch (final Exception e) {
			logger.error(e);
			JSFUtils.addErrorMsg(JSFUtils.handleSaveException(e));
		}
	}

	public void updateTipoArtigoCortina() {
		if ((getCortinaItem() != null) && (getCortinaItem().getArtigoCortina() != null)) {
			if (getCortinaItem().getArtigoCortina().getTipoArtigoCortina().equals(TipoArtigoCortina.TECIDO)) {
				if (getCortinaItem().getArtigoCortina().getTecido() == null) {
					getCortinaItem().getArtigoCortina().setTecido(new Tecido());
				}
			} else {
				if ((getCortinaItem().getArtigoCortina().getTecido() != null)
						&& (getCortinaItem().getArtigoCortina().getTecido().getId() == null)) {
					getCortinaItem().getArtigoCortina().setTecido(null);
				}
			}
		}
	}

	public void updateArtigoCortina() {
		try {
			Long reduzido = getCortinaItem().getArtigoCortina().getProduto().getReduzido();
			ArtigoCortina artigoCortina = getArtigoCortinaFinder().findByReduzido(reduzido);
			if (artigoCortina == null) {
				JSFUtils.addInfoMsg("Nenhum artigoCortina encontrado");
				getCortinaItem().setArtigoCortina(null);
			} else {
				getCortinaItem().setArtigoCortina(artigoCortina);
				// se for tecido, já seta os padrões
				if (TipoArtigoCortina.TECIDO.equals(artigoCortina.getTipoArtigoCortina())) {
					getCortinaItem().setFator(new BigDecimal(artigoCortina.getTecido().getFatorPadrao()));
					getCortinaItem().setAlturaBarra(artigoCortina.getTecido().getAlturaBarraPadrao());
					getCortinaItem().setOrientacao(artigoCortina.getTecido().getOrientacaoPadrao());
				}
				getCortinaItem().setPrecoPrazo(artigoCortina.getProduto().getPrecoAtual().getPrecoPrazo());
				getCortinaItem().setPrecoVista(artigoCortina.getProduto().getPrecoAtual().getPrecoVista());
				getCortinaItem().setPrecoPromo(artigoCortina.getProduto().getPrecoAtual().getPrecoPromo());
			}

			if (cortinaItem.getArtigoCortina().getTipoArtigoCortina()
					.equals(TipoArtigoCortina.MAO_DE_OBRA_COSTUREIRA_TECIDO)
					||
					cortinaItem.getArtigoCortina().getTipoArtigoCortina()
							.equals(TipoArtigoCortina.MAO_DE_OBRA_COSTUREIRA_DETALHES)
					||
					cortinaItem.getArtigoCortina().getTipoArtigoCortina()
							.equals(TipoArtigoCortina.MAO_DE_OBRA_ILHOS)
					||
					cortinaItem.getArtigoCortina().getTipoArtigoCortina()
							.equals(TipoArtigoCortina.MAO_DE_OBRA_INSTALACAO)
					||
					cortinaItem.getArtigoCortina().getTipoArtigoCortina()
							.equals(TipoArtigoCortina.SUPORTE_SIMPLES)
					||
					cortinaItem.getArtigoCortina().getTipoArtigoCortina().equals(TipoArtigoCortina.SUPORTE_DUPLO)
					||
					cortinaItem.getArtigoCortina().getTipoArtigoCortina().equals(TipoArtigoCortina.SUPORTE_TRIPLO)) {

				List<Integer> camadas = new ArrayList<Integer>();
				camadas.add(999);

				List<Integer> camadasSelecionadas = new ArrayList<Integer>();
				camadasSelecionadas.add(999);
				setCamadasSelecionadas(camadasSelecionadas);
			}
			updateCamadas();

		} catch (Exception e) {
			JSFUtils.addErrorMsg("Erro ao pesquisar artigoCortina pelo reduzido");
			logger.error(e);
		}
	}

	public void calcularQtdes() {
		try {
			setCortina(getCortinaFinder().refresh(getCortina()));
			cortinaCalculos.executar(getCortina());
			saveCortina();
			setCortina(getCortinaDataMapper().refresh(getCortina()));
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public void selArtigoCortina(ArtigoCortina artigoCortina) {
		try {
			getCortinaItem().setArtigoCortina(artigoCortina);
			updateArtigoCortina();
		} catch (Exception e) {
			JSFUtils.addErrorMsg("Erro ao selecionar o artigo de cortina.");
		}
	}

	public void selCliente(Cliente cliente) {
		try {
			getE().setCliente(cliente);
		} catch (Exception e) {
			JSFUtils.addErrorMsg("Erro ao selecionar o cliente.");
		}
	}

	public void updateLados() {
		getCortina().calcLargurasLados(getQtdeLados());
	}

	public void onDtLadosCellEdit(CellEditEvent event) {
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();

		if ((newValue != null) && !newValue.equals(oldValue)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue
					+ ", New:" + newValue);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public void deleteCortina(OrcamentoItem item) {
		try {
			setCortina(getCortinaFinder().findBy(item));
			if (getCortina() != null) {
				// Como é uma hierarquia, preciso dar um refresh em toda ela para poder excluir o elemento lá de baixo...
				//refreshE();
				getCortinaDataMapper().delete(getCortina());
			} else {
				getItemDataMapper().delete(item);
			}
			//			setGrupo(getGrupoDataMapper().refresh(item.getGrupo()));
			//			setItem(getItemDataMapper().refresh(item));
			//			getGrupo().getItens().remove(getItem());
			//			getCortina
			//			getItemDataMapper().delete(getItem());
			//			setItem(null);
			//			setGrupo(getGrupoDataMapper().refresh(getGrupo()));
			refreshE();
		} catch (Exception e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public void deleteCortinaItem(CortinaItem cortinaItem) {
		try {
			setCortinaItem(cortinaItem);
			setCortina(getCortinaDataMapper().refresh(getCortina()));
			getCortina().getItens().remove(getCortinaItem());
			getCortinaItem().setCortina(null);
			saveCortina();
		} catch (Exception e) {
			logger.error(e);
			JSFUtils.addErrorMsgErroAoDeletar();
		}
	}

	public void updateCamadas() {
		List<Integer> camadas = new ArrayList<Integer>();
		if ((getCortina() != null) && (getCortina().getQtdeCamadas() != null)) {
			for (int i = 1; i <= getCortina().getQtdeCamadas(); i++) {
				camadas.add(i);
			}
		}
		setCamadas(camadas);
	}

	/**
	 * Verifica se deve ser selecionada a camada ao salvar o item, pois alguns tipos de itens não são vinculados a uma camada específica
	 * (ver abaixo).
	 *
	 * @return
	 */
	public boolean isSelecionarCamadas() {
		selecionarCamadas = true;
		if ((cortinaItem != null) && (cortinaItem.getArtigoCortina() != null)
				&& (cortinaItem.getArtigoCortina().getTipoArtigoCortina() != null)) {
			if (cortinaItem.getArtigoCortina().getTipoArtigoCortina()
					.equals(TipoArtigoCortina.MAO_DE_OBRA_COSTUREIRA_TECIDO)
					||
					cortinaItem.getArtigoCortina().getTipoArtigoCortina()
							.equals(TipoArtigoCortina.MAO_DE_OBRA_COSTUREIRA_DETALHES)
					||
					cortinaItem.getArtigoCortina().getTipoArtigoCortina()
							.equals(TipoArtigoCortina.MAO_DE_OBRA_ILHOS)
					||
					cortinaItem.getArtigoCortina().getTipoArtigoCortina()
							.equals(TipoArtigoCortina.MAO_DE_OBRA_INSTALACAO)
					||
					cortinaItem.getArtigoCortina().getTipoArtigoCortina()
							.equals(TipoArtigoCortina.SUPORTE_SIMPLES)
					||
					cortinaItem.getArtigoCortina().getTipoArtigoCortina().equals(TipoArtigoCortina.SUPORTE_DUPLO)
					||
					cortinaItem.getArtigoCortina().getTipoArtigoCortina().equals(TipoArtigoCortina.SUPORTE_TRIPLO)) {
				selecionarCamadas = false;
			}
		}
		return selecionarCamadas;
	}

	public void setSelecionarCamadas(boolean selecionarCamadas) {
		this.selecionarCamadas = selecionarCamadas;
	}

	public List<Cortina> getCortinas() {
		try {
			return getCortinaFinder().findBy(getE());
		} catch (ViewException e) {
			JSFUtils.addHandledException(e);
			logger.error(e);
			return null;
		}
	}

	public void novoGrupo() {
		OrcamentoGrupoItem grupo = new OrcamentoGrupoItem();
		setGrupo(grupo);
	}

	public void saveGrupo() {
		RequestContext context = RequestContext.getCurrentInstance();
		try {
			refreshE();
			getGrupo().setOrcamento(getE());
			setGrupo(getGrupoDataMapper().save(getGrupo()));
			refreshE();
			context.addCallbackParam("saved", "true");
		} catch (Exception e) {
			if (getGrupo().getId() == null) {
				getE().getGrupos().remove(getGrupo());
			}
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao salvar item.");
		}
		context.addCallbackParam("dlgId", "dlgGrupo");
	}

	public void deleteGrupo(OrcamentoGrupoItem grupo) {
		try {
			getE().getGrupos().remove(grupo);
			save();
		} catch (Exception e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public void submitFormRelOrcamentoCortina2() {
		final RequestContext context = RequestContext.getCurrentInstance();
		final String dlgId = "dlgRelOrcamentoCortina2";
		final String btnId = "btnImprimirOrcamentoCortina2";
		context.addCallbackParam("dlgId", dlgId);
		context.addCallbackParam("saved", true);
		context.addCallbackParam("postFunction", "_$('" + btnId + "').click()");
	}

	public void imprimirOrcamentoCortina2() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("orcamentoId", getE().getId());
			params.put("agrupar_por_reduzido", Boolean.FALSE);
			// só exibe o rodapé de totalização de grupo caso tenha mais de 1
			// params.put("totalizarGrupo", getE().getGrupos().size() > 1);
			getReportBuilder().imprimir(params, "orcamentos/rpOrcamentoCortina2", "OrcamentoCortina2");
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public void submitFormRelOrcamentoCortina2PV() {
		final RequestContext context = RequestContext.getCurrentInstance();
		final String dlgId = "dlgRelOrcamentoCortina2";
		final String btnId = "btnImprimirOrcamentoCortina2";
		context.addCallbackParam("dlgId", dlgId);
		context.addCallbackParam("saved", true);
		context.addCallbackParam("postFunction", "_$('" + btnId + "').click()");
	}

	public void imprimirOrcamentoCortina2PV() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("orcamentoId", getE().getId());
			params.put("agrupar_por_reduzido", Boolean.TRUE);
			// só exibe o rodapé de totalização de grupo caso tenha mais de 1
			// params.put("totalizarGrupo", getE().getGrupos().size() > 1);
			getReportBuilder().imprimir(params, "orcamentos/rpOrcamentoCortina2", "OrcamentoCortina2");
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public void submitFormRelOrcamentoCortina2OrdemServico() {
		final RequestContext context = RequestContext.getCurrentInstance();
		final String dlgId = "dlgRelOrcamentoCortina2OrdemServico";
		final String btnId = "btnImprimirOrcamentoCortina2OrdemServico";
		context.addCallbackParam("dlgId", dlgId);
		context.addCallbackParam("saved", true);
		context.addCallbackParam("postFunction", "_$('" + btnId + "').click()");
	}

	public void imprimirOrcamentoCortina2OrdemServico() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("orcamentoId", getE().getId());
			// só exibe o rodapé de totalização de grupo caso tenha mais de 1
			// params.put("totalizarGrupo", getE().getGrupos().size() > 1);
			getReportBuilder()
					.imprimir(params, "orcamentos/rpOrcamentoCortina_costureira", "OrcamentoCortina2OrdemServico");
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public void submitFormRelPlanilhaCortina() {
		final RequestContext context = RequestContext.getCurrentInstance();
		final String dlgId = "dlgRelPlanilhaCortina";
		final String btnId = "btnImprimirPlanilhaCortina";
		context.addCallbackParam("dlgId", dlgId);
		context.addCallbackParam("saved", true);
		context.addCallbackParam("postFunction", "_$('" + btnId + "').click()");
	}

	public void imprimirPlanilhaCortina() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("orcamentoId", getE().getId());
			getReportBuilder().imprimir(params, "orcamentos/rpPlanilhaCortina", "PlanilhaCortina");
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public void gerarCabecalho() {
		StringBuilder sb = new StringBuilder("A/C ");
		try {
			if ((getE().getCliente() != null) && (getE().getCliente().getId() != null)) {
				Cliente cliente = getClienteFinder().refresh(getE().getCliente());
				sb.append(cliente.getPessoa().getNome());

				if ((cliente.getEnderecos() != null) && (cliente.getEnderecos().size() > 0)) {
					Endereco endereco = cliente.getEnderecos().get(0);
					sb.append("\r\n").append(endereco.getEnderecoMontado(true));
				}
				if (!StringUtils.isBlank(cliente.getEmail())) {
					sb.append("\r\n").append(cliente.getEmail().toLowerCase());
				}
				if (!StringUtils.isBlank(cliente.getFone1())) {
					sb.append("\r\n").append(cliente.getFone1());
					if (!StringUtils.isBlank(cliente.getFone2())) {
						sb.append(" / ").append(cliente.getFone2());
					}
					if (!StringUtils.isBlank(cliente.getFone3())) {
						sb.append(" / ").append(cliente.getFone3());
					}
				}
			}
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
		getE().setCabecalho(sb.toString());

	}

	public void gerarObsPadrao() {
		try {
			getE().setObs(getConfigFinder()
					.findConfigByChave("bonsucesso.orcamentos.obs_padrao").getValor());
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao setar a obs. padrão.");
		}
	}

	public void arredondarPreco(OrcamentoItem item) {
		try {
			item.setValorUnit(CalculoPreco.arredondar(item.getValorUnit()));
		} catch (Exception e) {
			JSFUtils.addErrorMsg("Erro ao arredondar valor.");
			logger.error(e);
		}
	}

	public void clonarItem(OrcamentoItem item) {
		try {
			try {
				Cortina cortina = getCortinaFinder().findBy(item);
				cortina.getItens().size(); // touch
				cortina.getLargurasLados().size(); // touch
				cortina = getBusiness().clonarCortina(cortina);
				refreshE();
			} catch (Exception e) {
				throw new ViewException("Erro ao pesquisar cortina pelo item do orçamento.", e);
			}
		} catch (Exception e) {
			JSFUtils.addErrorMsg("Erro ao clonar cortina.");
			logger.error(e);
		}
	}

	public void onRowReorder(ReorderEvent event) {
		try {
			getGrupoDataMapper().reorderSave(getE());
			refreshE();
		} catch (ViewException e) {
			JSFUtils.addErrorMsg("Erro ao reordenar lista.");
			logger.error(e);
		}
	}

	public void clonarOrcamento() {
		try {
			Orcamento clone = getBusiness().clonarOrcamentoCortina(getE());
			try {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("orcamentoCortinaForm.jsf?id=" + clone.getId());
			} catch (IOException e) {
				logger.error(e);
				JSFUtils.addErrorMsg("Erro ao redirecionar para orçamento clonado.");
			}
		} catch (Exception e) {
			JSFUtils.addErrorMsg("Erro ao reordenar lista.");
			logger.error(e);
		}
	}

	public void recarregarPadroesTecidoItem() {
		getCortinaItem().setAlturaBarra(getCortinaItem().getArtigoCortina().getTecido().getAlturaBarraPadrao());
		getCortinaItem().setFator(new BigDecimal(getCortinaItem().getArtigoCortina().getTecido().getFatorPadrao()));
		getCortinaItem().setOrientacao(getCortinaItem().getArtigoCortina().getTecido().getOrientacaoPadrao());
	}

	public void adicionarItensMaoDeObra() {
		try {
			List<CortinaItem> itensAdicionados = getCortinaCalculos().adicionarItensMaoDeObra(getCortina());
			for (CortinaItem ci : itensAdicionados) {
				setCortinaItem(ci);
				saveCortinaItem();
			}
		} catch (ViewException e) {
			JSFUtils.addErrorMsg("Erro ao adicionar itens de mão-de-obra.");
			JSFUtils.addHandledException(e);
			logger.error(e);
		}
	}

	/**
	 * Marca o item da cortina como drapeado (só pode ser marcado se for um tecido).
	 */
	public void setarDrapeado(boolean ehDrapeado) {
		if (getCortinaItem().getArtigoCortina().getTipoArtigoCortina() != TipoArtigoCortina.TECIDO
				&& getCortinaItem().getBando() == Boolean.FALSE) {
			JSFUtils.addErrorMsg("Somente tecidos podem ser marcados como drapeados.");
		} else {
			getCortinaItem().setDrapeado(ehDrapeado);
			if (!ehDrapeado) {
				getCortinaItem().setDrapeadoAltura1(null);
				getCortinaItem().setDrapeadoAltura2(null);
				getCortinaItem().setDrapeadoLargura(null);
				getCortinaItem().setDrapeadoLances(0);
			} else {
				// Padrão: metade da altura
				getCortinaItem().setDrapeadoAltura1(getCortinaItem().getCortina().getAltura()
						.divide(new BigDecimal("2"), RoundingMode.HALF_UP));
				getCortinaItem().setDrapeadoAltura2(getCortinaItem().getCortina().getAltura()
						.divide(new BigDecimal("2"), RoundingMode.HALF_UP));
				getCortinaItem().setDrapeadoLargura(getCortinaItem().getCortina().getLargura());
			}
		}
	}

	/**
	 * Marca o item da cortina como bandô (só pode ser marcado se for um tecido).
	 */
	public void setarBando(boolean ehBando) {
		if (getCortinaItem().getArtigoCortina().getTipoArtigoCortina() != TipoArtigoCortina.TECIDO
				&& getCortinaItem().getDrapeado() == Boolean.FALSE) {
			JSFUtils.addErrorMsg("Somente tecidos podem ser marcados como bandôs.");
		} else {
			getCortinaItem().setBando(ehBando);
		}
	}

	public List<CortinaItem> getItensAgrupados() {
		itensAgrupados = new ArrayList<CortinaItem>();
		for (CortinaItem ci : getCortina().getItens()) {
			boolean jaTem = false;
			for (CortinaItem ciAgrup : itensAgrupados) {
				if (ciAgrup.getArtigoCortina().equals(ci.getArtigoCortina())) {
					jaTem = true;
				}
			}
			if (!jaTem) {
				CortinaItem ciAgrup = SerializationUtils.clone(ci);
				ciAgrup.setId(null);
				ciAgrup.setQtde(BigDecimal.ZERO);
				itensAgrupados.add(ciAgrup);
			}
		}
		for (CortinaItem ci : getCortina().getItens()) {
			for (CortinaItem ciAgrup : itensAgrupados) {
				if (ciAgrup.getArtigoCortina().equals(ci.getArtigoCortina())) {
					ciAgrup.setQtde(ciAgrup.getQtde().add(ci.getQtde()));
				}
			}
		}

		for (CortinaItem ci : itensAgrupados) {
			ci.setValorTotal(ci.getQtde().multiply(ci.getPrecoPrazo()));
		}
		Collections.sort(itensAgrupados, new Comparator<CortinaItem>() {

			@Override
			public int compare(CortinaItem o1, CortinaItem o2) {
				return new CompareToBuilder()
						.append(o1.getArtigoCortina().getProduto().getDescricao(), o2.getArtigoCortina().getProduto()
								.getDescricao())
						.toComparison();
			}
		});
		return itensAgrupados;
	}

	public void clipboardCopyItens() {
		getClipboard().copy(getSelecteds());
	}

	@SuppressWarnings({ "unused", "unchecked" })
	public void clipboardPasteItens() {
		for (OrcamentoItem itemCopiado : (List<OrcamentoItem>) getClipboard().getObj()) {

		}
		final RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam("dlgId", "dlgColarItens");
		context.addCallbackParam("saved", true);

	}

	@SuppressWarnings("unchecked")
	public List<OrcamentoItem> getClipboardItens() {
		try {
			return (List<OrcamentoItem>) getClipboard().getObj();
		} catch (Exception e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao obter os itens do clipboard.");
			return null;
		}
	}

	public void updateOrientacaoTecidoOuFator() {
		try {
			if (getCortinaItem().getOrientacao().equals(OrientacaoTecido.VERTICAL)) {

				// no caso de orientação VERTICAL, monta a tabela com o comparativo para a seleção das opções
				setComparativoLarguras(getCortinaCalculos().buildCortinaLargurasCompar(getCortinaItem()));

				CortinaLargurasCompar clcSelec = null;

				// se já tem um fatorReal, seleciona ele
				if (getCortinaItem().getFatorReal() != null) {
					for (CortinaLargurasCompar clc : getComparativoLarguras()) {
						if (clc.getFatorReal().equals(getCortinaItem().getFatorReal())) {
							clcSelec = clc;
							break;
						}
					}
				}
				// se ainda não tem um fatorReal, ou se não achou pelo fatorReal salvo, seleciona o mais eficiente
				if (clcSelec == null) {
					for (CortinaLargurasCompar clc : getComparativoLarguras()) {
						if (clcSelec == null)
							clcSelec = clc;
						if (clc.getEficiencia().doubleValue() >= clcSelec.getEficiencia().doubleValue()) {
							clcSelec = clc;
						}
					}
				}
				setCortinaLargurasComparSelec(clcSelec);
				updateCortinaLargurasComparSelec();

			} else {
				setComparativoLarguras(null);
			}
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao montar o comparativo entre larguras/fator.");
		}
	}

	public void updateCortinaLargurasComparSelec() {
		getCortinaItem().setFatorReal(getCortinaLargurasComparSelec().getFatorReal());
		getCortinaItem().setLarguras(getCortinaLargurasComparSelec().getLarguras());
		try {
			getCortinaCalculos().calcularQtdeTecidos(getCortinaItem());
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao recalcular quantidade de tecidos.");
		}
	}

	@Override
	public void afterSetE(Orcamento orcamento) {
		try {
			for (OrcamentoGrupoItem g : orcamento.getGrupos()) {
				getGrupoDataMapper().refresh(g);
			}
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao carregar grupos e itens do orçamento");
		}
	}

	public OrcamentoCortinaBusiness getBusiness() {
		return business;
	}

	public void setBusiness(OrcamentoCortinaBusiness business) {
		this.business = business;
	}

	public List<CortinaLargurasCompar> getComparativoLarguras() {
		return comparativoLarguras;
	}

	public void setComparativoLarguras(List<CortinaLargurasCompar> comparativoLarguras) {
		this.comparativoLarguras = comparativoLarguras;
	}

	public CortinaLargurasCompar getCortinaLargurasComparSelec() {
		return cortinaLargurasComparSelec;
	}

	public void setCortinaLargurasComparSelec(CortinaLargurasCompar cortinaLargurasCompar) {
		this.cortinaLargurasComparSelec = cortinaLargurasCompar;
	}

	public ConfigFinder getConfigFinder() {
		return configFinder;
	}

	public void setConfigFinder(ConfigFinder configFinder) {
		this.configFinder = configFinder;
	}

	public ClienteFinder getClienteFinder() {
		return clienteFinder;
	}

	public void setClienteFinder(ClienteFinder clienteFinder) {
		this.clienteFinder = clienteFinder;
	}

	public CortinaCalculos getCortinaCalculos() {
		return cortinaCalculos;
	}

	public void setCortinaCalculos(CortinaCalculos cortinaCalculos) {
		this.cortinaCalculos = cortinaCalculos;
	}

	public OrcamentoItemDataMapper getOrcamentoItemDataMapper() {
		return orcamentoItemDataMapper;
	}

	public void setOrcamentoItemDataMapper(OrcamentoItemDataMapper orcamentoItemDataMapper) {
		this.orcamentoItemDataMapper = orcamentoItemDataMapper;
	}

	public OrcamentoGrupoItemDataMapper getGrupoDataMapper() {
		return grupoDataMapper;
	}

	public void setGrupoDataMapper(OrcamentoGrupoItemDataMapper grupoDataMapper) {
		this.grupoDataMapper = grupoDataMapper;
	}

	public OrcamentoItemDataMapper getItemDataMapper() {
		return itemDataMapper;
	}

	public void setItemDataMapper(OrcamentoItemDataMapper itemDataMapper) {
		this.itemDataMapper = itemDataMapper;
	}

	public CortinaDataMapper getCortinaDataMapper() {
		return cortinaDataMapper;
	}

	public void setCortinaDataMapper(CortinaDataMapper cortinaDataMapper) {
		this.cortinaDataMapper = cortinaDataMapper;
	}

	public void setItensAgrupados(List<CortinaItem> itensAgrupados) {
		this.itensAgrupados = itensAgrupados;
	}

	public List<OrcamentoItem> getSelecteds() {
		return selecteds;
	}

	public void setSelecteds(List<OrcamentoItem> selecteds) {
		this.selecteds = selecteds;
	}

	public Clipboard getClipboard() {
		return clipboard;
	}

	public void setClipboard(Clipboard clipboard) {
		this.clipboard = clipboard;
	}

	public String getTipoExibicaoItens() {
		return tipoExibicaoItens;
	}

	public void setTipoExibicaoItens(String tipoExibicaoItens) {
		this.tipoExibicaoItens = tipoExibicaoItens;
	}

	public Integer getQtdeLados() {
		return qtdeLados;
	}

	public void setQtdeLados(Integer qtdeLados) {
		this.qtdeLados = qtdeLados;
	}

	public OrcamentoGrupoItem getGrupo() {
		return grupo;
	}

	public void setGrupo(OrcamentoGrupoItem grupoItem) {
		grupo = grupoItem;
	}

	public void setCamadas(List<Integer> camadas) {
		this.camadas = camadas;
	}

	public List<Integer> getCamadas() {
		return camadas;
	}

	public List<Integer> getCamadasSelecionadas() {
		return camadasSelecionadas;
	}

	public void setCamadasSelecionadas(List<Integer> camadasSelecionadas) {
		this.camadasSelecionadas = camadasSelecionadas;
	}

	public CortinaFinder getCortinaFinder() {
		return cortinaFinder;
	}

	public void setCortinaFinder(CortinaFinder cortinaFinder) {
		this.cortinaFinder = cortinaFinder;
	}

	public ArtigoCortinaFinder getArtigoCortinaFinder() {
		return artigoCortinaFinder;
	}

	public void setArtigoCortinaFinder(ArtigoCortinaFinder produtoFinder) {
		artigoCortinaFinder = produtoFinder;
	}

	@Override
	public ReportBuilder getReportBuilder() {
		return reportBuilder;
	}

	@Override
	public void setReportBuilder(ReportBuilder reportBuilder) {
		this.reportBuilder = reportBuilder;
	}

	public String getActualContent() {
		return actualContent;
	}

	public void setActualContent(String actualContent) {
		this.actualContent = actualContent;
	}

}
