package com.bonsucesso.erp.financeiro.view;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.base.data.DiaUtilFinder;
import com.bonsucesso.erp.base.model.Pessoa;
import com.bonsucesso.erp.base.model.PessoaCadastro;
import com.bonsucesso.erp.base.view.ListMaker;
import com.bonsucesso.erp.financeiro.business.MovimentacaoBusiness;
import com.bonsucesso.erp.financeiro.data.CadeiaDataMapper;
import com.bonsucesso.erp.financeiro.data.MovimentacaoDataMapper;
import com.bonsucesso.erp.financeiro.data.ParcelamentoDataMapper;
import com.bonsucesso.erp.financeiro.model.Cadeia;
import com.bonsucesso.erp.financeiro.model.Carteira;
import com.bonsucesso.erp.financeiro.model.Categoria;
import com.bonsucesso.erp.financeiro.model.Cheque;
import com.bonsucesso.erp.financeiro.model.GrupoItem;
import com.bonsucesso.erp.financeiro.model.Movimentacao;
import com.bonsucesso.erp.financeiro.model.Parcelamento;
import com.bonsucesso.erp.financeiro.model.Status;
import com.bonsucesso.erp.financeiro.model.TipoLancto;
import com.ocabit.base.view.AbstractEntityFormView;
import com.ocabit.base.view.StoredViewInfoHandler;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;
import com.ocabit.utils.currency.CurrencyUtils;


/**
 * View para a entidade Movimentacao.
 *
 * @author Carlos Eduardo Pauluk
 */
@Component("movimentacaoFormView")
@Scope("view")
public class MovimentacaoFormView extends
		AbstractEntityFormView<Movimentacao, MovimentacaoDataMapper> {

	/**
	 *
	 */
	private static final long serialVersionUID = -4347678258692403653L;

	protected static Logger logger = Logger.getLogger(MovimentacaoFormView.class);

	private Date hoje;

	private TipoLancto tipoLancto = TipoLancto.GERAL;

	@Autowired
	private MovimentacaoBusiness movimentacaoBusiness;

	@Autowired
	private ListMaker lmBase;

	@Autowired
	private MovimentacaoExtratoListView listView;

	@Autowired
	private MovimentacaoExtratoGrupoListView movimentacaoExtratoGrupoListView;

	@Autowired
	private StoredViewInfoHandler storedViewInfoHandler;

	// Armazena quais campos devem ser salvos após salvar uma Movimentação, para setá-los quando for começar uma nova.
	private String[] camposStored = new String[] { "carteira", "categoria", "centroCusto", "dtMoviment", "modo",
			"descricao" };

	// Utilizado no autoComplete de pessoas
	private PessoaCadastro pessoaCadastro = PessoaCadastro.FORNECEDOR;

	@Autowired
	private DiaUtilFinder diaUtilFinder;

	private List<Movimentacao> selecteds;

	private boolean parcelamento = false;

	private Integer qtdeParcelas;

	@Autowired
	private ParcelamentoDataMapper parcelamentoDataMapper;

	@Autowired
	private CadeiaDataMapper cadeiaDataMapper;

	// Utilizada na tela movimentacaoForm_ESTORNOS_CORRECOES.xhtml como sendo a outra movimentação da cadeia
	private Movimentacao movimentacaoOposta;

	private Map<String, Object> paramsRel;

	@Override
	public void afterNovo() {
		getE().setDtMoviment(new Date());
	}

	public void novo(String _tipoLancto) {
		try {
			Carteira carteiraFiltro = null;

			if (getListView().getFiltros().containsKey("carteira")
					&& (getListView().getFiltros().get("carteira") instanceof Carteira)) {
				carteiraFiltro = (Carteira) getListView().getFiltros().get("carteira");
				getE().setCarteira(carteiraFiltro);
			} else if (getE().getCarteira() != null) {
				carteiraFiltro = getE().getCarteira();
			}

			Movimentacao movimentacao = new Movimentacao();
			getStoredViewInfoHandler().processStoredViewInfo("movimentacaoFormView", movimentacao);

			TipoLancto tipoLancto = TipoLancto.valueOf(_tipoLancto);

			setTipoLancto(tipoLancto);

			if (tipoLancto.equals(TipoLancto.MOVIMENTACAO_AGRUPADA)) {
				if (getMovimentacaoExtratoGrupoListView().getFiltros().containsKey("grupoItem")) {
					try {
						GrupoItem grupoItem = (GrupoItem) getMovimentacaoExtratoGrupoListView().getFiltros()
								.get("grupoItem");
						movimentacao.setGrupoItem(grupoItem);
					} catch (Exception e) {
						throw new ViewException("Erro ao setar o GrupoItem.");
					}
				}
			} else if (tipoLancto.equals(TipoLancto.PARCELA)) {
				movimentacao.setParcelamento(getE().getParcelamento());
			}

			// Método principal que prepara uma nova Movimentação
			setE(getMovimentacaoBusiness().prepararNova(movimentacao, getTipoLancto(), carteiraFiltro));

			// Se o tipoLancto for de 'transferência/recolhimento de caixa', a carteira Destino é ESCRITÓRIO
			if (tipoLancto == TipoLancto.TRANSF_CAIXA) {
				// FIXME: verificar como deixar isto parametrizável
				getE().setCarteiraDestino(getMovimentacaoBusiness()
						.getCarteiraFinder().findById(1l));
			} else if (tipoLancto == TipoLancto.TRANSF_PROPRIA) {
				getE().setCarteiraDestino(carteiraFiltro);
			}

		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public void updateChequeByCarteira() {
		try {
			if (getE().getModo() != null && getE().getModo().getModoDeCheque().equals(Boolean.TRUE)) {
				if (getE().getCheque() == null) {
					getE().setCheque(new Cheque());
				}
				getE().getCheque().setBanco(getE().getCarteira().getBanco());
				getE().getCheque().setAgencia(getE().getCarteira().getAgencia());
				getE().getCheque().setConta(getE().getCarteira().getConta());
			}
		} catch (Exception e) {
			JSFUtils.addErrorMsg("Erro ao setar dados bancários.");
			logger.error(e);
		}
	}

	public void updateCategoria(Categoria categoria) {
		try {
			getE().setCategoria(categoria);
			JSFUtils.execute("updateOpnCategoria_" + getTipoLancto().toString()
					+ "()");
		} catch (Exception e) {
			JSFUtils.addErrorMsg("Erro ao setar categoria.");
			logger.error(e);
		}
	}

	@Override
	public void afterSave() {
		JSFUtils.execute("loadList()");
		getStoredViewInfoHandler().store("movimentacaoFormView", getE(), camposStored);
	}

	@Override
	public String getDlgId() {
		return "dlgMovimentacao_" + getTipoLancto().toString();
	}

	/**
	 * Inicia um pagamento.
	 *
	 * @param movimentacao
	 */
	public void iniPagto(Movimentacao movimentacao) {
		setE(movimentacao);
		getE().setDtPagto(getE().getDtVenctoEfetiva());
		getE().setStatus(Status.REALIZADA);
		setTipoLancto(TipoLancto.PAGTO);
	}

	public List<Pessoa> acPessoa(String str) {
		return getLmBase().findPessoasBy(str, getPessoaCadastro());
	}

	public void updatePessoa(Pessoa pessoa) {
		getE().setPessoa(pessoa);
	}

	public void iniEdit(Movimentacao movimentacao) {
		if (movimentacao.getStatus().equals(Status.ABERTA)) {
			iniEdit(movimentacao, "A_PAGAR_RECEBER");
		} else {
			iniEdit(movimentacao, "GERAL");
		}
	}

	public void iniEdit(Movimentacao movimentacao, String _tipoLancto) {
		setTipoLancto(TipoLancto.valueOf(_tipoLancto));
		setE(movimentacao);
		if (getE().getParcelamento() != null && getE().getParcelamento().getParcelas() != null) {
			try {
				getMovimentacaoBusiness().handleDescricaoMontada(getE().getParcelamento().getParcelas());
			} catch (ViewException e) {
				logger.error(e);
				JSFUtils.addHandledException(e);
			}
		}
		if (getE().getCadeia() != null && getE().getCadeia().getMovimentacoes() != null) {
			try {
				getMovimentacaoBusiness().handleDescricaoMontada(getE().getCadeia().getMovimentacoes());
			} catch (ViewException e) {
				logger.error(e);
				JSFUtils.addHandledException(e);
			}
		}
		logger.debug("iniEdit fim");
	}

	public void updateDtVenctoEfetiva() {
		if ((getE().getDtVenctoEfetiva() == null) && (getE().getDtVencto() != null)) {
			try {
				getE().setDtVenctoEfetiva(getDiaUtilFinder().findProximoDiaUtilFinanceiro((getE().getDtVencto())));
			} catch (ViewException e) {
				logger.error(e);
				JSFUtils.addErrorMsg("Erro ao pesquisar dias úteis");
			}
		}
	}

	public void gerarDescricao() {
		getMovimentacaoBusiness().gerarDescricao(getE());
	}

	@Override
	public void beforeSetE(Movimentacao e) {
		setParcelamento(false);
	}

	@Override
	public void afterSetE(Movimentacao e) {
		if (e != null) {
			// editando
			if (e.getId() != null) {
				if (e.getParcelamento() != null && e.getParcelamento().getId() != null) {
					e.getParcelamento().getId().toString(); // touch somente no id
				}
				if (e.getPessoa() != null) {
					e.getPessoa().hashCode();
				}
				if (e.getModo().getModoDeCheque().equals(Boolean.TRUE)) {
					if (e.getCheque() == null || e.getCheque().getAgencia() == null || e.getCheque().getBanco() == null
							|| e.getCheque().getConta() == null) {
						updateChequeByCarteira();
					}
				}
				// novo
			} else {
				if (e.getModo() != null && e.getModo().getModoDeCheque().equals(Boolean.TRUE)
						&& e.getCheque() == null) {
					e.setCheque(new Cheque());
				}
				if (e.getUnqControle() == null) {
					e.setUnqControle(com.ocabit.utils.strings.StringUtils.generateRandomString(15));
				}
			}
			getDataMapper().detach(e);
		}
	}

	@Override
	public void validate() throws ViewException {
		// FIXME: estou fazendo isto pois no afterSetE() tive que inicializar. Ver como resolver este problema de atributos-entidades null.
		if ((getE().getCheque() != null) && (getE().getCheque().getBanco() == null)) {
			getE().setCheque(null);
		}
		if ((getE().getPessoa() != null) && (getE().getPessoa().getId() == null)) {
			getE().setPessoa(null);
		}
	}

	public void iniAlterarEmLote() {
		if ((getSelecteds() != null)
				&& (getSelecteds().size() >= 2)) {
			setE(new Movimentacao());
			RequestContext context = RequestContext.getCurrentInstance();
			context.addCallbackParam("dlgId", "dlgMovimentacaoLote");
			context.addCallbackParam("openDlg", true);
		} else {
			JSFUtils.addErrorMsg("Ao menos 2 registros devem estar selecionados");
		}
	}

	public void alterarLote() {
		try {
			getDataMapper().alterarLoteSave(getSelecteds(), getE());
			JSFUtils.addCallbackParam("dlgId", "dlgMovimentacaoLote");
			JSFUtils.addCallbackParam("saved", true);
			JSFUtils.addInfoMsg("Registros salvos com sucesso.");
			JSFUtils.execute("loadList()");
		} catch (Exception e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao salvar em lote.");
			JSFUtils.addHandledException(e);
		}
	}

	public void deletarLote() {
		try {
			getDataMapper().deleteList(getSelecteds());
			JSFUtils.addCallbackParam("dlgId", "dlgMovimentacaoLote");
			JSFUtils.addCallbackParam("saved", true);
			JSFUtils.addInfoMsg("Registros deletados com sucesso.");
			JSFUtils.execute("loadList()");
		} catch (Exception e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao deletar o lote.");
		}
	}

	public void gerarParcelas(boolean diaFixo) {
		try {
			if (getE().getDtVenctoEfetiva() == null) {
				updateDtVenctoEfetiva();
			}
			getMovimentacaoBusiness().gerarParcelas(getE(), getQtdeParcelas(), diaFixo);
		} catch (Exception e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao gerar parcelas");
		}
	}

	public void corrigirDtVenctosEfetivas() {
		for (Movimentacao m : getE().getParcelamento().getParcelas()) {
			try {
				m.setDtVenctoEfetiva(getDiaUtilFinder().findProximoDiaUtilFinanceiro(m.getDtVencto()));
			} catch (ViewException e) {
				logger.error(e);
				JSFUtils.addHandledException(e);
			}
		}
	}

	public void onParcelaEdit(Movimentacao m) {
		m.setValor(m.getValorTotal());
		BigDecimal valorTotalParcelas = BigDecimal.ZERO;

		getMovimentacaoBusiness().reordenarParcelas(getE().getParcelamento().getParcelas());

		for (Movimentacao p : getE().getParcelamento().getParcelas()) {
			valorTotalParcelas = CurrencyUtils.soma(valorTotalParcelas, p.getValorTotal());
		}

		m.getParcelamento().setValorTotal(valorTotalParcelas);

		if (m.getDtVenctoEfetiva() == null) {
			try {
				m.setDtVenctoEfetiva(getDiaUtilFinder().findProximoDiaUtilFinanceiro(m.getDtVencto()));
			} catch (ViewException e) {
				logger.error(e);
				JSFUtils.addHandledException(e);
			}
		}

	}

	public void checkSave() {
		// Se for parcelamento, antes de salvar pede a confirmação
		// if (getE().getId() == null && getE().getParcelamento() != null && getE().getParcelamento().getParcelas().size() > 1) {
		if (isParcelamento()) {
			JSFUtils.execute("PF('cdlgConfirmSave_" + getTipoLancto() + "').show()");
		} else {
			save();
		}
	}

	@Override
	public boolean save() {
		if (isParcelamento()) {
			try {
				getParcelamentoDataMapper().save(getE().getParcelamento());
				JSFUtils.addCallbackParam("dlgId", "dlgMovimentacao_" + getTipoLancto());
				JSFUtils.addCallbackParam("saved", true);
				JSFUtils.addInfoMsg("Parcelas salvas com sucesso.");
				JSFUtils.execute("loadList()");
				getStoredViewInfoHandler().store("movimentacaoFormView", getE(), camposStored);
				return true;
			} catch (Exception e) {
				logger.error(e);
				JSFUtils.addErrorMsg("Erro ao salvar em lote.");
				return false;
			}
		} else {
			if (getTipoLancto().equals(TipoLancto.ESTORNO_CORRECAO)) {
				try {
					final RequestContext context = RequestContext.getCurrentInstance();
					getMovimentacaoBusiness().saveCadeia(getE(), getMovimentacaoOposta());
					afterSave();
					JSFUtils.addInfoMsg(JSFUtils.MSG_REGISTRO_SALVO);
					context.addCallbackParam("saved", true);
					if (getDlgId() != null) {
						context.addCallbackParam("dlgId", getDlgId());
					}
					logger.debug("return result;");
					setMovimentacaoOposta(null);
					return true;
				} catch (ViewException e) {
					logger.error(e);
					JSFUtils.addHandledException(e);
					return false;
				}
			} else {
				return super.save();
			}
		}
	}

	/**
	 * Implemento pois devo chamar outra ação.
	 */
	@Override
	public void doSave() throws ViewException {
		setE(getDataMapper().processSaves(getE()));
	}

	public final void checkRelFichaMovimentacao() {
		if ((getSelecteds() != null) && (getSelecteds().size() > 0)) {
			JSFUtils.addCallbackParam("dlgId", "dlgRelFichaMovimentacao");
			return;
		} else {
			JSFUtils.addErrorMsg("É necessário selecionar pelo menos 1 registro.");
		}
	}

	public final void imprimirFichaMovimentacao() {
		try {
			List<Long> ids = new ArrayList<Long>();
			for (Movimentacao m : getSelecteds()) {
				ids.add(m.getId());
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("ids", StringUtils.join(ids, ","));
			getReportBuilder().imprimir(params, "financeiro/rpFichaMovimentacao", "FichaMovimentacao");
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public void updateParcelamento() {
		if (isParcelamento()) {
			Parcelamento parcelamento = new Parcelamento();
			getE().setParcelamento(parcelamento);
		} else {
			getE().setParcelamento(null);
		}
	}

	public void deletarCadeia(Cadeia cadeia) {
		try {
			getCadeiaDataMapper().delete(cadeia);
			JSFUtils.execute("loadList()");
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addErrorMsgErroAoDeletar();
		}
	}

	public void deletarParcelamento(Parcelamento parcelamento) {
		try {
			getParcelamentoDataMapper().delete(parcelamento);
			JSFUtils.execute("loadList()");
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public void updateGrupoItem() {
		getE().setDtVencto(getE().getGrupoItem().getDtVencto());
		updateDtVenctoEfetiva();
	}

	/**
	 * Método específico para deletar Movimentacao que é parcela (através do movimentacaoForm_dlg.xhtml)
	 *
	 * @param parcela
	 */
	public void deletarParcela(Movimentacao parcela) {
		// Verifica existem mais parcelas
		if (parcela.getParcelamento() != null) {
			Movimentacao primeira = parcela.getParcelamento().getParcelas().get(0);
			boolean temMaisParcelas = parcela.getParcelamento().getQtdeParcelas() > 1;

			deletar(parcela);

			if (temMaisParcelas) {
				setE(primeira);
				try {
					((ParcelamentoDataMapper) getDataMapper().getBeanFactory().getBean("parcelamentoDataMapper"))
							.refresh(getE().getParcelamento());
				} catch (BeansException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ViewException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				JSFUtils.execute("PF('dlgParcelamento').hide();");
			}
			JSFUtils.execute("loadList();");

		}
	}

	public boolean isUltimaParcela(Movimentacao parcela) {
		if (parcela.getParcelamento() != null) {
			return parcela.getNumParcela() == parcela.getQtdeParcelas();
		}
		return false;
	}

	public final void imprimirPlanilhaRecorrente() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("cadeia_id", getE().getCadeia().getId());
			params.put("dtIni", getParamsRel().get("dtIni"));
			params.put("dtFim", getParamsRel().get("dtFim"));
			getReportBuilder().imprimir(params, "financeiro/rpPlanilhaRecorrente", "PlanilhaRecorrente");
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public final void imprimirPlanilhaParcelamento() {
		try {

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("parcelamento_id", getE().getParcelamento().getId());
			getReportBuilder().imprimir(params, "financeiro/rpPlanilhaParcelamento", "PlanilhaParcelamento");
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public void deslocarVencto(Movimentacao parcela, int direcao) {
		try {
			parcela = getMovimentacaoBusiness().deslocarVenctos(parcela, direcao);
			setE(parcela);
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public void limparDatas() {
		getE().setDtVencto(null);
		getE().setDtVenctoEfetiva(null);
		getE().setDtPagto(null);
	}

	public Movimentacao getMovimentacaoOposta() {
		if (movimentacaoOposta == null) {
			movimentacaoOposta = new Movimentacao();
		}
		return movimentacaoOposta;
	}

	public void setMovimentacaoOposta(Movimentacao movimentacaoOposta) {
		this.movimentacaoOposta = movimentacaoOposta;
	}

	public Integer getQtdeParcelas() {
		return qtdeParcelas;
	}

	public void setQtdeParcelas(Integer qtdeParcelas) {
		this.qtdeParcelas = qtdeParcelas;
	}

	public ListMaker getLmBase() {
		return lmBase;
	}

	public void setLmBase(ListMaker lmBase) {
		this.lmBase = lmBase;
	}

	public PessoaCadastro getPessoaCadastro() {
		return pessoaCadastro;
	}

	public void setPessoaCadastro(PessoaCadastro pessoaCadastro) {
		this.pessoaCadastro = pessoaCadastro;
	}

	public CadeiaDataMapper getCadeiaDataMapper() {
		return cadeiaDataMapper;
	}

	public void setCadeiaDataMapper(CadeiaDataMapper cadeiaDataMapper) {
		this.cadeiaDataMapper = cadeiaDataMapper;
	}

	public Date getHoje() {
		return hoje;
	}

	public void setHoje(Date hoje) {
		this.hoje = hoje;
	}

	public DiaUtilFinder getDiaUtilFinder() {
		return diaUtilFinder;
	}

	public void setDiaUtilFinder(DiaUtilFinder diaUtilFinder) {
		this.diaUtilFinder = diaUtilFinder;
	}

	public MovimentacaoBusiness getMovimentacaoBusiness() {
		return movimentacaoBusiness;
	}

	public void setMovimentacaoBusiness(
			MovimentacaoBusiness movimentacaoBusiness) {
		this.movimentacaoBusiness = movimentacaoBusiness;
	}

	public ParcelamentoDataMapper getParcelamentoDataMapper() {
		return parcelamentoDataMapper;
	}

	public void setParcelamentoDataMapper(ParcelamentoDataMapper parcelamentoDataMapper) {
		this.parcelamentoDataMapper = parcelamentoDataMapper;
	}

	public TipoLancto getTipoLancto() {
		return tipoLancto;
	}

	public void setTipoLancto(TipoLancto tipoLancto) {
		this.tipoLancto = tipoLancto;
	}

	public MovimentacaoExtratoListView getListView() {
		return listView;
	}

	public void setListView(MovimentacaoExtratoListView listView) {
		this.listView = listView;
	}

	public MovimentacaoExtratoGrupoListView getMovimentacaoExtratoGrupoListView() {
		return movimentacaoExtratoGrupoListView;
	}

	public void setMovimentacaoExtratoGrupoListView(MovimentacaoExtratoGrupoListView movimentacaoExtratoGrupoListView) {
		this.movimentacaoExtratoGrupoListView = movimentacaoExtratoGrupoListView;
	}

	@Override
	public StoredViewInfoHandler getStoredViewInfoHandler() {
		return storedViewInfoHandler;
	}

	@Override
	public void setStoredViewInfoHandler(StoredViewInfoHandler storedViewInfoHandler) {
		this.storedViewInfoHandler = storedViewInfoHandler;
	}

	public List<Movimentacao> getSelecteds() {
		if (selecteds == null) {
			selecteds = new ArrayList<Movimentacao>();
		}
		return selecteds;
	}

	public void setSelecteds(List<Movimentacao> selecteds) {
		this.selecteds = selecteds;
	}

	public boolean isParcelamento() {
		return parcelamento;
	}

	public void setParcelamento(boolean parcelamento) {
		this.parcelamento = parcelamento;
	}

	public Map<String, Object> getParamsRel() {
		if (paramsRel == null) {
			paramsRel = new HashMap<String, Object>();
		}
		return paramsRel;
	}

	public void setParamsRel(Map<String, Object> paramsRel) {
		this.paramsRel = paramsRel;
	}

}
