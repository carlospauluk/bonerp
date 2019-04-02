package com.bonsucesso.erp.financeiro.view;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.financeiro.business.MovimentacaoBusiness;
import com.bonsucesso.erp.financeiro.business.MovimentacaoImporter;
import com.bonsucesso.erp.financeiro.business.MovimentacaoImporter.TipoExtrato;
import com.bonsucesso.erp.financeiro.model.Carteira;
import com.bonsucesso.erp.financeiro.model.Categoria;
import com.bonsucesso.erp.financeiro.model.Cheque;
import com.bonsucesso.erp.financeiro.model.GrupoItem;
import com.bonsucesso.erp.financeiro.model.Movimentacao;
import com.bonsucesso.erp.financeiro.model.Status;
import com.google.common.collect.Lists;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;


/**
 * View para importações da entidade Movimentacao (linhas de extratos).
 *
 * @author Carlos Eduardo Pauluk
 */
@Component("movimentacaoImporterFormView")
@Scope("view")
public class MovimentacaoImporterFormView extends
		MovimentacaoFormView {

	/**
	 *
	 */
	private static final long serialVersionUID = -5141616402649099989L;

	protected static Logger logger = Logger.getLogger(MovimentacaoImporterFormView.class);

	@Autowired
	private MovimentacaoBusiness business;

	@Autowired
	private MovimentacaoImporter movimentacaoImporter;

	private String linhas;

	private boolean gerarSemRegras;

	private List<Movimentacao> importadas;

	@NotNull(message = "O campo 'Carteira Extrato' deve ser informado")
	private Carteira carteiraExtrato;

	private Carteira carteiraDestino;

	private BigDecimal total;

	@NotNull(message = "O campo 'Tipo Extrato' deve ser informado")
	private TipoExtrato tipoExtrato;

	private GrupoItem grupoItem;

	private List<Carteira> carteirasExtratos;

	@Autowired
	private ListMaker lmFinanc;

	// Campo do dialog dlgVerif
	private Map<String, Object> verifs = new HashMap<String, Object>();

	// Movimentações resultantes do método verificar()
	private List<Movimentacao> outrasVerifs;

	public MovimentacaoBusiness getBusiness() {
		return business;
	}

	public void setBusiness(MovimentacaoBusiness business) {
		this.business = business;
	}

	public MovimentacaoImporter getMovimentacaoImporter() {
		return movimentacaoImporter;
	}

	public void setMovimentacaoImporter(MovimentacaoImporter movimentacaoImporter) {
		this.movimentacaoImporter = movimentacaoImporter;
	}

	public String getLinhas() {
		return linhas;
	}

	public void setLinhas(String linhas) {
		this.linhas = linhas;
	}

	public Carteira getCarteiraExtrato() {
		return carteiraExtrato;
	}

	public void setCarteiraExtrato(Carteira carteira) {
		carteiraExtrato = carteira;
	}

	public Carteira getCarteiraDestino() {
		return carteiraDestino;
	}

	public void setCarteiraDestino(Carteira carteiraDestino) {
		this.carteiraDestino = carteiraDestino;
	}

	@Override
	public void afterNovo() {
		getE().setDtMoviment(new Date());
	}

	@Override
	public void afterSave() {
		JSFUtils.execute("loadList()");
	}

	@Override
	public void afterSetE(Movimentacao e) {
		super.afterSetE(e);
	}

	public List<Movimentacao> getImportadas() {
		if (importadas == null) {
			importadas = new ArrayList<Movimentacao>();
		}
		return importadas;
	}

	public void setImportadas(List<Movimentacao> importadas) {
		this.importadas = importadas;
	}

	public BigDecimal getTotal() {
		if ((getImportadas() != null) && (getImportadas().size() > 0)) {
			try {
				total = getBusiness().findTotal(getImportadas()).get("TOTAL");
			} catch (Exception e) {
				logger.error(e);
			}
		}
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	/**
	 * Método principal.
	 */
	public void importar() {
		try {
			List<String> linhas = new ArrayList<String>();
			if ((getLinhas() != null) && !"".equals(getLinhas().trim())) {
				String[] _linhas = getLinhas().split("\n");
				linhas = Lists.newArrayList(_linhas);

				setImportadas(getMovimentacaoImporter()
						.importar(getTipoExtrato(), linhas, getCarteiraExtrato(), getCarteiraDestino(), getGrupoItem(), isGerarSemRegras()));

				getMovimentacaoBusiness().handleDescricaoMontada(getImportadas());

				// O método importar() modificou o linhas para conter somente as não importadas
				setLinhas(StringUtils.join(linhas, "\n"));
			} else {
				JSFUtils.addWarnMsg("Informe as linhas para importação.");
			}
		} catch (Exception e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao importar linhas.");
		}
	}

	public void remover(Movimentacao m) {
		getImportadas().remove(m);
	}

	public void salvarTodos() {
		try {
			getDataMapper().saveList(getImportadas());
			// getImportadas().size();
			setLinhas(null);
			setImportadas(null);
			JSFUtils.addInfoMsg("Registro(s) salvo(s) com sucesso.");
		} catch (Exception e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	@Override
	public boolean save() {
		try {
			getImportadas().set(getImportadas().indexOf(getE()), getE());

			logger.info("Não faz nada. Só vai salvar no salvarTodos()");
			JSFUtils.addCallbackParam("dlgId", getDlgId());
			JSFUtils.addCallbackParam("saved", true);
			JSFUtils.addInfoMsg("Alterações realizadas com sucesso.");
			JSFUtils.addInfoMsg("Para salvar utilize o botão 'Salvar Todos'.");
			JSFUtils.execute("reLoadList()");
			return true;
		} catch (Exception e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao salvar movimentações.");
			return false;
		}
	}

	@Override
	public void iniEdit(Movimentacao movimentacao, String _tipoLancto) {
		super.iniEdit(movimentacao, _tipoLancto);

		try {
			movimentacao.setTipoLancto(getTipoLancto());
			// Ajusta a movimentação conforme o tipo de "edição" selecionada
			switch (getTipoLancto()) {
				case TRANSF_PROPRIA:
					movimentacao.setCategoria(getBusiness().getCategoriaFinder().findBy(299l));
					movimentacao.setStatus(Status.REALIZADA);
					break;
				case CHEQUE_PROPRIO:
					movimentacao.setStatus(Status.A_COMPENSAR);
					movimentacao.setModo(getBusiness().getModoFinder().findBy("CHEQUE PRÓPRIO"));
					movimentacao.setCheque(new Cheque());
					if (movimentacao.getCarteira() != null) {
						movimentacao.getCheque().setBanco(movimentacao.getCarteira().getBanco());
						movimentacao.getCheque().setAgencia(movimentacao.getCarteira().getAgencia());
						movimentacao.getCheque().setConta(movimentacao.getCarteira().getConta());
					}
					break;
				case CHEQUE_TERCEIROS:
					movimentacao.setCheque(new Cheque());
					movimentacao.setStatus(Status.A_COMPENSAR);
					movimentacao.setModo(getBusiness().getModoFinder().findBy("CHEQUE TERCEIROS"));
					break;
				default:
					break;
			}
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}

	}

	public boolean isGerarSemRegras() {
		return gerarSemRegras;
	}

	public void setGerarSemRegras(boolean gerarSemRegras) {
		this.gerarSemRegras = gerarSemRegras;
	}

	@Override
	public void alterarLote() {
		try {
			getMovimentacaoBusiness().alterarEmLote(getSelecteds(), getE());
			getMovimentacaoBusiness().handleDescricaoMontada(getSelecteds());
			setSelecteds(null);
			JSFUtils.addCallbackParam("dlgId", "dlgMovimentacaoLote");
			JSFUtils.addCallbackParam("saved", true);
			JSFUtils.execute("reLoadList()");
			JSFUtils.addInfoMsg("Registros alterados com sucesso.");
		} catch (Exception e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao salvar em lote.");
		}
	}

	@Override
	public void deletarLote() {
		try {
			getDataMapper().deleteList(getSelecteds());
			JSFUtils.addCallbackParam("dlgId", "dlgMovimentacaoLote");
			JSFUtils.addCallbackParam("saved", true);
			JSFUtils.addInfoMsg("Registros deletados com sucesso.");
			JSFUtils.execute("reLoadList()");
			importar();
		} catch (Exception e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao deletar o lote.");
		}
	}

	public TipoExtrato getTipoExtrato() {
		return tipoExtrato;
	}

	public void setTipoExtrato(TipoExtrato tipoExtrato) {
		this.tipoExtrato = tipoExtrato;
	}

	public void removerExistentes() {
		try {
			List<Movimentacao> novas = new ArrayList<Movimentacao>();
			for (Movimentacao m : getImportadas()) {
				if (m.getId() == null) {
					novas.add(m);
				}
			}
			setImportadas(novas);
			JSFUtils.addInfoMsg("Registros removidos com sucesso.");
		} catch (Exception e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao deletar o lote.");
		}
	}

	public void removerNovas() {
		try {
			List<Movimentacao> ms = new ArrayList<Movimentacao>();
			for (Movimentacao m : getImportadas()) {
				if (m.getId() != null) {
					ms.add(m);
				}
			}
			setImportadas(ms);
			JSFUtils.addInfoMsg("Registros removidos com sucesso.");
		} catch (Exception e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao deletar o lote.");
		}
	}

	@SuppressWarnings("unchecked")
	public void removerSelecionadas() {
		try {
			List<Movimentacao> ms = new ArrayList<Movimentacao>();
			ms.addAll(CollectionUtils.disjunction(getImportadas(), getSelecteds()));
			setImportadas(null);
			getImportadas().addAll(ms);
		} catch (Exception e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao deletar o lote.");
		}
	}

	public GrupoItem getGrupoItem() {
		return grupoItem;
	}

	public void setGrupoItem(GrupoItem grupoItem) {
		this.grupoItem = grupoItem;
	}

	public final String getBgColor(Movimentacao movimentacao) {
		try {
			if (movimentacao != null) {
				if (movimentacao.getModo() == null || movimentacao.getCategoria() == null
						|| movimentacao.getModo().getCodigo() == 99) {
					return "background-color: lightsalmon";
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

	//	private Movimentacao e;
	//
	//	public Movimentacao getE() {
	//		return e;
	//	}
	//
	//	public void setE(Movimentacao e) {
	//		this.e = e;
	//	}

	public ListMaker getLmFinanc() {
		return lmFinanc;
	}

	public void setLmFinanc(ListMaker lmFinanc) {
		this.lmFinanc = lmFinanc;
	}

	public Map<String, Object> getVerifs() {
		return verifs;
	}

	public void setVerifs(Map<String, Object> verifs) {
		this.verifs = verifs;
	}

	public List<Movimentacao> getOutrasVerifs() {
		return outrasVerifs;
	}

	public void setOutrasVerifs(List<Movimentacao> outrasVerifs) {
		this.outrasVerifs = outrasVerifs;
	}

	public List<Carteira> getCarteirasExtratos() {
		if (carteirasExtratos == null) {
			carteirasExtratos = new ArrayList<Carteira>();
		}
		return carteirasExtratos;
	}

	public void setCarteirasExtratos(List<Carteira> carteirasExtratos) {
		this.carteirasExtratos = carteirasExtratos;
	}

	/**
	 * Monta a lista de carteiras de acordo com o tipo de extrato selecionado.
	 */
	public void updateTipoExtrato() {
		List<Carteira> carteirasConcretas = getLmFinanc().getCarteiraConcretaValues();
		setCarteirasExtratos(null);
		for (Carteira c : carteirasConcretas) {
			if (tipoExtrato.equals(TipoExtrato.EXTRATO_CIELO_CREDITO) ||
					tipoExtrato.equals(TipoExtrato.EXTRATO_CIELO_DEBITO)) {
				if (c.getDescricao().contains("CIELO")) {
					getCarteirasExtratos().add(c);
				}
			} else if (tipoExtrato.equals(TipoExtrato.EXTRATO_RDCARD_CREDITO) ||
					tipoExtrato.equals(TipoExtrato.EXTRATO_RDCARD_DEBITO)) {
				if (c.getDescricao().contains("RDCARD")) {
					getCarteirasExtratos().add(c);
				}
			} else if (tipoExtrato.equals(TipoExtrato.EXTRATO_STONE_CREDITO) ||
					tipoExtrato.equals(TipoExtrato.EXTRATO_STONE_DEBITO)) {
				if (c.getDescricao().contains("STONE")) {
					getCarteirasExtratos().add(c);
				}
			} else {
				getCarteirasExtratos().add(c);
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void verificar() {
		try {
			Date dtIni = (Date) getVerifs().get("dtIni");
			Date dtFim = (Date) getVerifs().get("dtFim");

			Categoria categoria = (Categoria) getVerifs().get("categoria");

			List<Movimentacao> ms = getMovimentacaoImporter().getMovimentacaoFinder()
					.findBy(getCarteiraExtrato(), categoria, dtIni, dtFim);

			Collection outras = CollectionUtils.disjunction(ms, getImportadas());

			setImportadas(null);
			
			getBusiness().handleDescricaoMontada((List<Movimentacao>) outras);

			getImportadas().addAll(outras);

			//			outrasVerif.addAll(CollectionUtils.disjunction(ms, getImportadas()));
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao verificar movimentações.");
		}

	}

	/**
	 * Seleciona uma movimentação já previamente cadastrada no sistema para substituir a importada, porém setando nela os valores da
	 * importada para atualizá-la.
	 * 
	 * @param movimentacao
	 */
	public void selecMovimentacaoExistente(Movimentacao movimentacao) {
		try {
			// getE() >> a movimentação da lista de importadas
			// movimentacao >> a movimentação seleciona no movimentacaoDlgBusca

			movimentacao = getMovimentacaoBusiness().getMovimentacaoFinder().refresh(movimentacao);

			if (movimentacao.getDtPagto() == null) {
				movimentacao.setStatus(Status.REALIZADA);
				movimentacao.setDtPagto(getE().getDtPagto());
			}

			// como ele pode ter encontrado a movimentação em outra carteira, seta para carteira do extrato
			movimentacao.setCarteira(carteiraExtrato);
			movimentacao.setValor(getE().getValor());
			movimentacao.setValorTotal(getE().getValorTotal());

			movimentacao = getMovimentacaoBusiness().handleDescricaoMontada(movimentacao);

			// substitui na lista
			if (!getImportadas().contains(getE())) {
				throw new ViewException("Importação não encontrada nas movimentações já importadas.");
			}
			getImportadas().set(getImportadas().indexOf(getE()), movimentacao);

			JSFUtils.execute("PF('dlgBuscaMovimentacao').hide()");

		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}

	}

}
