package com.bonsucesso.erp.financeiro.view;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.financeiro.business.MovimentacaoBusiness;
import com.bonsucesso.erp.financeiro.data.MovimentacaoDataMapper;
import com.bonsucesso.erp.financeiro.data.MovimentacaoFilterFinder;
import com.bonsucesso.erp.financeiro.data.MovimentacaoFinder;
import com.bonsucesso.erp.financeiro.data.MovimentacaoFinderImpl.TipoSaldo;
import com.bonsucesso.erp.financeiro.model.Carteira;
import com.bonsucesso.erp.financeiro.model.Categoria;
import com.bonsucesso.erp.financeiro.model.Movimentacao;
import com.bonsucesso.erp.financeiro.model.TipoLancto;
import com.ocabit.base.data.filter.FilterData;
import com.ocabit.base.data.filter.FilterType;
import com.ocabit.base.view.AbstractEntityFormListView;
import com.ocabit.base.view.StoredViewInfoHandler;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;
import com.ocabit.utils.calendar.CalendarUtil;


@Component("movimentacaoLanctosCaixaFormListView")
@Scope("view")
public class MovimentacaoLanctosCaixaFormListView extends
		AbstractEntityFormListView<Movimentacao, MovimentacaoDataMapper, MovimentacaoFinder> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1524534225868294494L;

	protected static Logger logger = Logger.getLogger(MovimentacaoLanctosCaixaFormListView.class);

	@Autowired
	private MovimentacaoFilterFinder filterFinder;

	@Autowired
	private MovimentacaoBusiness business;

	@Autowired
	private ListMaker lmFinanc;

	@Autowired
	private StoredViewInfoHandler storedViewInfoHandler;

	private Carteira carteiraTela;

	private boolean exibirCampoBandeiraCartao;

	// Armazena quais campos devem ser salvos após salvar uma Movimentação, para setá-los quando for começar uma nova.
	private String[] camposStored = new String[] { "tipoLancto", "descricao", "categoria", "modo" };

	private List<SelectItem> siTiposLancto;

	@Autowired
	private MovimentacaoBusiness movimentacaoBusiness;

	private List<String> resultadoConsolidacao;

	// datalists para datatables

	private List<Movimentacao> dtListCartoes;

	private List<Movimentacao> dtListEntradas;

	private List<Movimentacao> dtListDespesas;

	private List<Movimentacao> dtListRetiradas;

	private List<Movimentacao> dtListOutros;

	@PostConstruct
	public void init() {
		getFiltros().put("carteira", getLmFinanc().getCarteiraCaixaValues().get(0));
		getFiltros().put("dtPagto", new Date());

		getSiTiposLancto().add(new SelectItem(TipoLancto.REALIZADA, TipoLancto.REALIZADA.getLabel()));
		getSiTiposLancto().add(new SelectItem(TipoLancto.CHEQUE_TERCEIROS, TipoLancto.CHEQUE_TERCEIROS.getLabel()));
		getSiTiposLancto().add(new SelectItem(TipoLancto.TRANSF_PROPRIA, TipoLancto.TRANSF_PROPRIA.getLabel()));
		getSiTiposLancto().add(new SelectItem(TipoLancto.TRANSF_CAIXA, TipoLancto.TRANSF_CAIXA.getLabel()));

		// pesquisar(true);

		loadList();

	}

	private Map<String, BigDecimal> totalizacoes;

	public void setFilterFinder(MovimentacaoFilterFinder filterFinder) {
		this.filterFinder = filterFinder;
	}

	@Override
	protected MovimentacaoFilterFinder getFilterFinder() {
		return filterFinder;
	}

	public MovimentacaoBusiness getBusiness() {
		return business;
	}

	public void setBusiness(MovimentacaoBusiness business) {
		this.business = business;
	}

	public StoredViewInfoHandler getStoredViewInfoHandler() {
		return storedViewInfoHandler;
	}

	public void setStoredViewInfoHandler(StoredViewInfoHandler storedViewInfoHandler) {
		this.storedViewInfoHandler = storedViewInfoHandler;
	}

	public MovimentacaoBusiness getMovimentacaoBusiness() {
		return movimentacaoBusiness;
	}

	public void setMovimentacaoBusiness(MovimentacaoBusiness movimentacaoBusiness) {
		this.movimentacaoBusiness = movimentacaoBusiness;
	}

	public String[] getCamposStored() {
		return camposStored;
	}

	public void setCamposStored(String[] camposStored) {
		this.camposStored = camposStored;
	}

	public ListMaker getLmFinanc() {
		return lmFinanc;
	}

	public void setLmFinanc(ListMaker lmFinanc) {
		this.lmFinanc = lmFinanc;
	}

	@Override
	protected void handleFilterDatas() {
		getFilterDatas().add(new FilterData("dtPagto", FilterType.EQUALS));
		getFilterDatas().add(new FilterData("carteira", FilterType.EQUALS));
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

	@Override
	public final void pesquisar(boolean stored) {
		super.pesquisar(stored);
		updateDtLists();
		totalizar();
	}

	public void updateDtLists() {
		setDtListCartoes(new ArrayList<Movimentacao>());
		setDtListDespesas(new ArrayList<Movimentacao>());
		setDtListRetiradas(new ArrayList<Movimentacao>());
		setDtListEntradas(new ArrayList<Movimentacao>());
		setDtListOutros(new ArrayList<Movimentacao>());

		for (Movimentacao m : getList()) {

			if (m.getModo().getCodigo().equals(10)) {
				// CARTÕES
				// tudo o que for modo: 10 - RECEB. CARTÃO DÉBITO
				getDtListCartoes().add(m);
			} else if (m.getCategoria().getCodigoSuper().equals(2l) && (!m.getCategoria().getCodigo().equals(299l))) {
				// DESPESAS
				// tudo o que for categoria 2XXXX mas não 2.99
				getDtListDespesas().add(m);
			} else if (m.getCategoria().getCodigo().equals(299l)) {
				// RETIRADA
				// tudo o que for categoria 2.99
				getDtListRetiradas().add(m);
			} else if (m.getCategoria().getCodigoSuper().equals(1l) && !m.getCategoria().getCodigo().equals(199l)) {
				// ENTRADAS
				// tudo o que for categoria 1XXXX (exceto os 199)
				getDtListEntradas().add(m);
			} else {
				// OUTROS
				getDtListOutros().add(m);
			}

		}

		try {
			getMovimentacaoBusiness().handleDescricaoMontada(getDtListCartoes());
			getMovimentacaoBusiness().handleDescricaoMontada(getDtListDespesas());
			getMovimentacaoBusiness().handleDescricaoMontada(getDtListRetiradas());
			getMovimentacaoBusiness().handleDescricaoMontada(getDtListEntradas());
			getMovimentacaoBusiness().handleDescricaoMontada(getDtListOutros());
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}

	}

	@Override
	public void novo() {
		// quando clica direto no botão na tela
		novo(null);
	}

	public void novo(TipoLancto tipoLancto) {
		try {
			Date dtPagto = (Date) getFiltros().get("dtPagto");
			Carteira carteira = (Carteira) getFiltros().get("carteira");

			Movimentacao movimentacao = new Movimentacao();
			getStoredViewInfoHandler().processStoredViewInfo("movimentacaoLanctosCaixaFormListView", movimentacao);

			// se não está passando um tipoLancto no método, mas tem um salvo no stored
			if (tipoLancto == null) {
				if ((movimentacao != null) && (movimentacao.getTipoLancto() != null)) {
					tipoLancto = movimentacao.getTipoLancto();
				} else {
					tipoLancto = TipoLancto.REALIZADA;
				}
			}

			setE(getMovimentacaoBusiness().prepararNova(movimentacao, tipoLancto, carteira));

			getE().setDtMoviment(dtPagto);
			getE().setDtPagto(dtPagto);
			getE().setDtVencto(dtPagto);
			getE().setDtVenctoEfetiva(dtPagto);

			// Se o tipoLancto for de 'transferência/recolhimento de caixa', a carteira Destino é ESCRITÓRIO
			if (tipoLancto == TipoLancto.TRANSF_PROPRIA) {
				// FIXME: verificar como deixar isto parametrizável
				getE().setCarteiraDestino(getLmFinanc().getCarteiraDestinoPadraoTransfCaixa());
			}

			updateDescricao();

		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public void updateTipoLancto() {
		novo(getE().getTipoLancto());
	}

	public void totalizar() {
		try {
			Map<String, BigDecimal> totais = getBusiness().findTotal(getList());

			Date dtPagto = (Date) getFiltros().get("dtPagto");
			Carteira carteira = (Carteira) getFiltros().get("carteira");

			getTotalizacoes().put("SALDO_ANTERIOR", getFinder()
					.findSaldo(dtPagto, carteira, TipoSaldo.SALDO_ANTERIOR_REALIZADAS));
			getTotalizacoes().put("SALDO_POSTERIOR", getFinder()
					.findSaldo(dtPagto, carteira, TipoSaldo.SALDO_POSTERIOR_REALIZADAS));

			getTotalizacoes().put("TOTAL_ENTRADAS", totais.get("ENTRADAS"));
			getTotalizacoes().put("TOTAL_SAIDAS", totais.get("SAIDAS"));
			getTotalizacoes().put("TOTAL_MOVIMENTADO", totais.get("TOTAL"));

			// totalizações separadas por dtList

			getTotalizacoes().put("TOTAL_ENTRADAS_CARTOES", getBusiness().totalizarDtList(getDtListCartoes(), 1l));
			getTotalizacoes().put("TOTAL_SAIDAS_CARTOES", getBusiness().totalizarDtList(getDtListCartoes(), 2l));
			getTotalizacoes().put("TOTAL_MOVIMENTADO_CARTOES", getBusiness().totalizarDtList(getDtListCartoes(), null));

			getTotalizacoes().put("TOTAL_MOVIMENTADO_ENTRADAS", getBusiness()
					.totalizarDtList(getDtListEntradas(), 1l));

			getTotalizacoes().put("TOTAL_MOVIMENTADO_DESPESAS", getBusiness().totalizarDtList(getDtListDespesas(), 2l));

			getTotalizacoes().put("TOTAL_MOVIMENTADO_RETIRADAS", getBusiness()
					.totalizarDtList(getDtListRetiradas(), 2l));

		} catch (ViewException e) {
			JSFUtils.addHandledException(e);
			logger.error(e);
		}
	}

	public void updateCategoria(Categoria categoria) {
		try {
			getE().setCategoria(categoria);
			JSFUtils.execute("updateOpnCategoria()");
		} catch (Exception e) {
			JSFUtils.addErrorMsg("Erro ao setar categoria.");
			logger.error(e);
		}
	}

	@Override
	public String getDlgId() {
		return "dlgMovimentacao";
	}

	@Override
	public void validate() throws ViewException {
		if (getE().getTipoLancto().equals(TipoLancto.TRANSF_PROPRIA)) {
			getE().setCarteiraDestino(getLmFinanc().getCarteiraDestinoPadraoTransfCaixa());
		}
	}

	@Override
	public void afterSave() {
		getStoredViewInfoHandler().store("movimentacaoLanctosCaixaFormListView", getE(), camposStored);
		loadList();
	}

	public List<SelectItem> getSiTiposLancto() {
		if (siTiposLancto == null) {
			siTiposLancto = new ArrayList<SelectItem>();
		}
		return siTiposLancto;
	}

	public void setSiTiposLancto(List<SelectItem> siTiposLancto) {
		this.siTiposLancto = siTiposLancto;
	}

	/**
	 * Implemento pois devo chamar outra ação.
	 */
	@Override
	public void doSave() throws ViewException {
		setE(getDataMapper().processSaves(getE()));
	}

	public void incDia(int qtde) {
		try {
			Date dtPagto = (Date) getFiltros().get("dtPagto");
			if (qtde == 1) {
				dtPagto = getMovimentacaoBusiness().getDiaUtilFinder()
						.findProximoDiaUtilComercial(CalendarUtil.incDias(dtPagto, 1));
			} else {
				dtPagto = getMovimentacaoBusiness().getDiaUtilFinder().findAnteriorDiaUtilComercial(dtPagto);
			}
			getFiltros().put("dtPagto", dtPagto);
			pesquisar();
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public List<String> acDescricao(String str) {
		try {
			Carteira carteira = (Carteira) getFiltros().get("carteira");
			return getFinder().findDescricaoBy(carteira, str);
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
			return null;
		}
	}

	@Override
	public Integer getQtdeRegistros() {
		return null;
	}

	public Carteira getCarteiraTela() {
		if ((getFiltros() != null) && getFiltros().containsKey("carteira")) {
			carteiraTela = (Carteira) getFiltros().get("carteira");
		}
		return carteiraTela;
	}

	public void setCarteiraTela(Carteira carteiraTela) {
		this.carteiraTela = carteiraTela;
	}

	public boolean isExibirCampoBandeiraCartao() {
		if ((getCarteiraTela() != null) && (getE() != null) && (getE().getModo() != null)) {
			exibirCampoBandeiraCartao = getCarteiraTela().getCaixa()
					&& (getE().getModo().getId().equals(9l) || getE().getModo().getId().equals(10l));
		} else {
			exibirCampoBandeiraCartao = false;
		}
		return exibirCampoBandeiraCartao;
	}

	public void setExibirCampoBandeiraCartao(boolean exibirCampoBandeiraCartao) {
		this.exibirCampoBandeiraCartao = exibirCampoBandeiraCartao;
	}

	public void consolidarMovimentacoesCartoesDebito() {
		try {
			Date dtPagto = (Date) getFiltros().get("dtPagto");
			Carteira carteira = (Carteira) getFiltros().get("carteira");
			List<String> results = getBusiness().consolidarMovimentacoesCartoesDebito(dtPagto, carteira);

			List<String> resultsOperadora = getBusiness()
					.corrigirOperadoraCartaoMovimentacoesCartoesDebito(dtPagto, carteira);

			results.addAll(resultsOperadora);

			if ((results == null) || (results.size() == 0)) {
				JSFUtils.addWarnMsg("Nenhuma movimentação encontrada para consolidação.");
			} else {
				Collections.sort(results);
				setResultadoConsolidacao(results);
				RequestContext context = RequestContext.getCurrentInstance();
				context.addCallbackParam("dlgId", "dlgResultadoConsolidacao");
				context.addCallbackParam("openDlg", true);
			}
			pesquisar();
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		} catch (Exception e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao consolidar as movimentações.");
		}
	}

	public List<String> getResultadoConsolidacao() {
		return resultadoConsolidacao;
	}

	public void setResultadoConsolidacao(List<String> resultadoConsolidacao) {
		this.resultadoConsolidacao = resultadoConsolidacao;
	}

	public List<Movimentacao> getDtListCartoes() {
		return dtListCartoes;
	}

	public void setDtListCartoes(List<Movimentacao> mCartoes) {
		dtListCartoes = mCartoes;
	}

	public List<Movimentacao> getDtListEntradas() {
		return dtListEntradas;
	}

	public void setDtListEntradas(List<Movimentacao> dtListEntradas) {
		this.dtListEntradas = dtListEntradas;
	}

	public List<Movimentacao> getDtListDespesas() {
		return dtListDespesas;
	}

	public void setDtListDespesas(List<Movimentacao> mDespesas) {
		dtListDespesas = mDespesas;
	}

	public List<Movimentacao> getDtListRetiradas() {
		return dtListRetiradas;
	}

	public void setDtListRetiradas(List<Movimentacao> mRetiradas) {
		dtListRetiradas = mRetiradas;
	}

	public List<Movimentacao> getDtListOutros() {
		return dtListOutros;
	}

	public void setDtListOutros(List<Movimentacao> dtListOutros) {
		this.dtListOutros = dtListOutros;
	}

	@Override
	public void afterDeletar() {
		loadList();
	}

	public void updateDescricao() {
		String descricao = "";
		if (getE() != null && getE().getModo() != null && getE().getModo().getCodigo() != null) {
			if (getE().getModo().getCodigo().equals(9) || getE().getModo().getCodigo().equals(10)) {
				if (getE().getBandeiraCartao() != null) {
					descricao = getE().getBandeiraCartao().getDescricao();
				}
			}
		}
		getE().setDescricao(descricao);
	}

}
