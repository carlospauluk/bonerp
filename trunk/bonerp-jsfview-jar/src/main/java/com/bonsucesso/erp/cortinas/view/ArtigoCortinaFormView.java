package com.bonsucesso.erp.cortinas.view;



import java.util.ArrayList;
import java.util.Date;

import javax.faces.event.AjaxBehaviorEvent;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.base.model.Configs;
import com.bonsucesso.erp.cortinas.data.ArtigoCortinaDataMapper;
import com.bonsucesso.erp.cortinas.data.ArtigoCortinaFinder;
import com.bonsucesso.erp.cortinas.model.ArtigoCortina;
import com.bonsucesso.erp.cortinas.model.Tecido;
import com.bonsucesso.erp.cortinas.model.TipoArtigoCortina;
import com.bonsucesso.erp.estoque.business.CalculoPreco;
import com.bonsucesso.erp.estoque.data.FornecedorFinder;
import com.bonsucesso.erp.estoque.data.ProdutoFinder;
import com.bonsucesso.erp.estoque.data.ProdutoPrecoDataMapper;
import com.bonsucesso.erp.estoque.model.Fornecedor;
import com.bonsucesso.erp.estoque.model.Produto;
import com.bonsucesso.erp.estoque.model.ProdutoPreco;
import com.bonsucesso.erp.estoque.view.FornecedorFormListView;
import com.ocabit.base.view.AbstractEntityFormView;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.config.data.ConfigFinder;
import com.ocabit.jsf.utils.JSFUtils;
import com.ocabit.utils.currency.CurrencyUtils;


/**
 * View para a entidade ArtigoCortina.
 *
 * @author Carlos Eduardo Pauluk
 */
@Component("artigoCortinaFormView")
@Scope("view")
public class ArtigoCortinaFormView extends AbstractEntityFormView<ArtigoCortina, ArtigoCortinaDataMapper> {

	/**
	 *
	 */
	private static final long serialVersionUID = -4347678258692403653L;

	protected static Logger logger = Logger.getLogger(ArtigoCortinaFormView.class);

	@Autowired
	private ArtigoCortinaFinder finder;

	@Autowired
	private ProdutoPrecoDataMapper produtoPrecoDataMapper;

	@Autowired
	private ArtigoCortinaListView artigoCortinaListView;

	@Autowired
	private CalculoPreco calculoPreco;

	@Autowired
	private FornecedorFinder fornecedorFinder;

	@Autowired
	private ConfigFinder configFinder;

	private ProdutoPreco preco;

	@Autowired
	private com.bonsucesso.erp.estoque.view.ListMaker lmEstoque;

	@Autowired
	private ProdutoFinder produtoFinder;

	@Autowired
	private FornecedorFormListView fornecedorFormListView;

	private Integer fornecedorCodigo;

	public com.bonsucesso.erp.estoque.view.ListMaker getLmEstoque() {
		return lmEstoque;
	}

	public void setLmEstoque(com.bonsucesso.erp.estoque.view.ListMaker lmEstoque) {
		this.lmEstoque = lmEstoque;
	}

	public void iniAlterarEmLote() {
		try {
			if ((artigoCortinaListView.getSelecteds() != null)
					&& (artigoCortinaListView.getSelecteds().size() >= 2)) {
				setE(new ArtigoCortina());
				setPreco(new ProdutoPreco());
				for (ArtigoCortina ac : getArtigoCortinaListView().getSelecteds()) {
					getFinder().refresh(ac);
				}
				RequestContext context = RequestContext.getCurrentInstance();
				context.addCallbackParam("dlgId", "dlgAlterarEmLote");
				context.addCallbackParam("openDlg", true);
			} else {
				JSFUtils.addErrorMsg("Ao menos 2 registros devem estar selecionados");
			}
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public ArtigoCortinaListView getArtigoCortinaListView() {
		return artigoCortinaListView;
	}

	public void setArtigoCortinaListView(ArtigoCortinaListView artigoCortinaListView) {
		this.artigoCortinaListView = artigoCortinaListView;
	}

	public CalculoPreco getCalculoPreco() {
		return calculoPreco;
	}

	public void setCalculoPreco(CalculoPreco calculoPreco) {
		this.calculoPreco = calculoPreco;
	}

	public ArtigoCortinaFinder getFinder() {
		return finder;
	}

	public void setFinder(ArtigoCortinaFinder finder) {
		this.finder = finder;
	}

	public FornecedorFinder getFornecedorFinder() {
		return fornecedorFinder;
	}

	public void setFornecedorFinder(FornecedorFinder fornecedorFinder) {
		this.fornecedorFinder = fornecedorFinder;
	}

	public ConfigFinder getConfigFinder() {
		return configFinder;
	}

	public void setConfigFinder(ConfigFinder configFinder) {
		this.configFinder = configFinder;
	}

	public ProdutoPrecoDataMapper getProdutoPrecoDataMapper() {
		return produtoPrecoDataMapper;
	}

	public void setProdutoPrecoDataMapper(ProdutoPrecoDataMapper produtoPrecoDataMapper) {
		this.produtoPrecoDataMapper = produtoPrecoDataMapper;
	}

	@Override
	public void afterSave() {
		// Utilizado somente quando o form é para o dialog
		RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam("dlgId", "dlgArtigoCortina");
		// JSFUtils.execute("loadList()");
	}

	public ProdutoPreco getPreco() {
		if (preco == null) {
			preco = new ProdutoPreco();
		}
		return preco;
	}

	public void setPreco(ProdutoPreco preco) {
		this.preco = preco;
	}

	public void novoPreco() {
		setPreco(new ProdutoPreco());
		calculoPreco.novoPreco(getPreco());
		getPreco().setDtCusto(new Date());
	}

	@Override
	public void afterNovo() {
		novoPreco();
		getLmEstoque().setDepto(null);
		setFornecedorCodigo(null);
		getE().setTipoArtigoCortina(TipoArtigoCortina.INDEFINIDO);
		//		Tecido tecido = new Tecido();
		//		getE().setTecido(tecido);
		//		carregarPadroesTecido(getE());
	}

	public void deletarPreco() {
		try {
			produtoPrecoDataMapper.delete(getPreco());
		} catch (final ViewException e) {
			JSFUtils.addErrorMsgErroAoDeletar();
		}
	}

	@Override
	public void afterSetE(ArtigoCortina e) {
		if ((e != null) && (e.getId() != null)) {
			updatingProduto();
			if (e.getProduto() != null && e.getProduto().getId() != null) {
				e.getProduto().getSaldos().size();
				e.getProduto().getPrecos().size();
			}
		}
	}

	/**
	 * O que deve ser feito quando altera o Produto do ArtigoCortina.
	 */
	public void updatingProduto() {
		try {
			// Alterar o auxiliar depto lá do ListMaker do estoque
			if ((getE() != null) && (getE().getProduto().getSubdepto() != null)
					&& (getE().getProduto().getSubdepto().getDepto() != null)) {
				getLmEstoque().setDepto(getE().getProduto().getSubdepto().getDepto());
			}

			// Setar o preço
			if (getE().getProduto().getPrecoAtual().getId() != null) {
				setPreco((ProdutoPreco) getE().getProduto().getPrecoAtual().clone());
			} else {
				novoPreco();
			}

			// Setar o auxiliar fornecedorCodigo
			if (getE().getProduto().getFornecedor() != null) {
				setFornecedorCodigo(getE().getProduto().getFornecedor().getCodigo());
			}
		} catch (CloneNotSupportedException e1) {
			logger.error(e1);
			JSFUtils.addErrorMsg("Erro ao tratar preço");
		}
	}

	public void updateTipoArtigoCortina() {
		if (getE().getTipoArtigoCortina().equals(TipoArtigoCortina.TECIDO)) {
			if (getE().getTecido() == null) {
				getE().setTecido(new Tecido());
				carregarPadroesTecido(getE());
			}
		}
	}

	public void saveLote() {
		try {
			alterarEmLote();
			getDataMapper().saveList(getArtigoCortinaListView().getSelecteds());
			JSFUtils.addInfoMsg(JSFUtils.MSG_REGISTRO_SALVO);

			logger.debug("setando parâmetro saved no contexto");
			// Adiciona um parâmetro de retorno para informar que foi salvo
			final RequestContext context = RequestContext.getCurrentInstance();
			context.addCallbackParam("saved", true);
			context.addCallbackParam("dlgId", "dlgAlterarEmLote");

			getArtigoCortinaListView().setList(new ArrayList<ArtigoCortina>());
		} catch (Exception e) {
			String msgErro = "Erro ao salvar";
			if (e.getCause() instanceof ConstraintViolationException) {
				msgErro = JSFUtils.erroDeValidacao((ConstraintViolationException) e.getCause());
			}
			if (e.getCause() instanceof PersistenceException) {
				msgErro = e.getMessage();
			}
			logger.error(e);
			JSFUtils.addErrorMsg(msgErro);
		}
	}

	@Override
	public ArtigoCortina getLoteE() {
		ArtigoCortina loteE = super.getLoteE();
		if (loteE.getProduto() == null) {
			loteE.setProduto(new Produto());
		}
		return loteE;
	}

	/**
	 * Só altera os valores na lista de selecteds. O saveList é chamado no método saveLote().
	 */
	private void alterarEmLote() {
		if ((artigoCortinaListView.getSelecteds() != null)
				&& (artigoCortinaListView.getSelecteds().size() > 0)) {

			ArtigoCortina artigoCortina = getLoteE();
			ProdutoPreco preco = getPreco();

			for (ArtigoCortina ac : artigoCortinaListView.getSelecteds()) {
				if ((artigoCortina.getProduto().getFornecedor() != null)
						&& (artigoCortina.getProduto().getFornecedor().getId() != null)) {
					ac.getProduto().setFornecedor(artigoCortina.getProduto().getFornecedor());
				}
				if (artigoCortina.getTipoArtigoCortina() != null) {
					ac.setTipoArtigoCortina(artigoCortina.getTipoArtigoCortina());
				}
				if ((preco.getPrecoCusto() != null) && (preco.getPrecoCusto().doubleValue() > 0.0)) {
					ac.getProduto().getPrecoAtual().setPrecoCusto(preco.getPrecoCusto());
				}
				if ((preco.getCoeficiente() != null) && (preco.getCoeficiente().doubleValue() > 0.0)) {
					ac.getProduto().getPrecoAtual().setCoeficiente(preco.getCoeficiente());
				}
				if ((preco.getCustoOperacional() != null) && (preco.getCustoOperacional().doubleValue() > 0.0)) {
					ac.getProduto().getPrecoAtual()
							.setCustoOperacional(preco.getCustoOperacional());
				}
				if (preco.getDtCusto() != null) {
					ac.getProduto().getPrecoAtual().setDtCusto(preco.getDtCusto());
				}
				if ((preco.getMargem() != null) && (preco.getMargem().doubleValue() > 0.0)) {
					ac.getProduto().getPrecoAtual().setMargem(preco.getMargem());
				}
				if ((preco.getPrazo() != null) && (preco.getPrazo() > 0.0)) {
					ac.getProduto().getPrecoAtual().setPrazo(preco.getPrazo());
				}
				if ((preco.getPrecoPromo() != null) && (preco.getPrecoPromo().doubleValue() > 0.0)) {
					ac.getProduto().getPrecoAtual().setPrecoPromo(preco.getPrecoPromo());
				}
				if (artigoCortina.getTipoArtigoCortina() != null) {
					ac.setTipoArtigoCortina(artigoCortina.getTipoArtigoCortina());

					if (TipoArtigoCortina.TECIDO.equals(artigoCortina.getTipoArtigoCortina())
							&& (artigoCortina.getTecido() != null)) {
						Tecido tecido = artigoCortina.getTecido();
						if (tecido.getAlturaBarraPadrao() != null) {
							ac.getTecido().setAlturaBarraPadrao(tecido.getAlturaBarraPadrao());
						}
						if (tecido.getAlturaMaxHorizontal() != null) {
							ac.getTecido().setAlturaMaxHorizontal(tecido.getAlturaMaxHorizontal());
						}
						if (tecido.getFatorPadrao() != null) {
							ac.getTecido().setFatorPadrao(tecido.getFatorPadrao());
						}
						if (tecido.getLargura() != null) {
							ac.getTecido().setLargura(tecido.getLargura());
						}
						if (tecido.getOrientacaoPadrao() != null) {
							ac.getTecido().setOrientacaoPadrao(tecido.getOrientacaoPadrao());
						}
					} else {
						ac.setTecido(null);
					}
				}

			}
		
		}
	}

	/**
	 * Override: chama o método para salvar também com o preço.
	 *
	 * @return
	 */
	@Override
	public boolean save() {
		boolean result = false;
		try {
			validate();
			setE(getDataMapper().saveComPreco(getE(), getPreco()));
			afterSave();
			afterSetId();
			JSFUtils.addInfoMsg(JSFUtils.MSG_REGISTRO_SALVO);
			final RequestContext context = RequestContext.getCurrentInstance();
			context.addCallbackParam("saved", true);
			result = true;
		} catch (final ViewException e) {
			JSFUtils.addHandledException(e);
			logger.error("Erro no save():", e);
		} catch (final Exception e) {
			JSFUtils.addErrorMsg(JSFUtils.MSG_ERRO_AO_SALVAR);
			logger.error("Erro no save():", e);
		}
		return result;
	}

	public void calcularCoeficiente(AjaxBehaviorEvent event) {
		try {
			calculoPreco.calcularCoeficiente(getPreco());
		} catch (Exception e) {
			// aqui tem um belo exemplo do porque usar Exceptions particulares do sistema: para conseguir saber quando usar a e.getMessage()
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public void calcularPrecos() {
		try {
			calculoPreco.calcularPrecos(getPreco());
		} catch (Exception e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public void updateReduzido() {
		try {
			boolean encontrou = false;
			ArtigoCortina artigoCortina = getFinder().findByReduzido(getE().getProduto().getReduzido());
			if (artigoCortina != null) {
				setE(artigoCortina);
				encontrou = true;
			} else {
				Produto produto = getProdutoFinder().findByReduzido(getE().getProduto().getReduzido());
				if (produto != null) {
					getE().setProduto(produto);
					updatingProduto();
					encontrou = true;
				}
			}
			if (encontrou) {
				JSFUtils.execute("updateOpnDlgArtigoCortina()");
			}
		} catch (ViewException e) {
			JSFUtils.addErrorMsg("Erro ao pesquisar pelo reduzido.");
			logger.error(e);
		}
	}

	public void selFornecedor(Fornecedor fornecedor) {
		try {
			getE().getProduto().setFornecedor(fornecedor);
		} catch (Exception e) {
			JSFUtils.addErrorMsg("Erro ao selecionar o fornecedor.");
			logger.error(e);
		}
	}

	@Override
	public String getDlgId() {
		return "dlgArtigoCortina";
	}

	public ProdutoFinder getProdutoFinder() {
		return produtoFinder;
	}

	public void setProdutoFinder(ProdutoFinder produtoFinder) {
		this.produtoFinder = produtoFinder;
	}

	//	public void setEByProduto(Produto produto) {
	//		try {
	//			produto = getProdutoFinder().refresh(produto);
	//			setE(produto.getArtigoCortina());
	//		} catch (ViewException e) {
	//			JSFUtils.addErrorMsg("Erro ao setar Artigo de Cortina pelo Produto");
	//			logger.error(e);
	//		}
	//	}

	public FornecedorFormListView getFornecedorFormListView() {
		return fornecedorFormListView;
	}

	public void setFornecedorFormListView(FornecedorFormListView fornecedorFormListView) {
		this.fornecedorFormListView = fornecedorFormListView;
	}

	public void updateFornecedorSelec(Fornecedor fornecedor) {		
		try {
			getE().getProduto().setFornecedor(fornecedor);
			setFornecedorCodigo(fornecedor.getCodigo());
		} catch (Exception e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao selecionar o fornecedor.");
		}
	}

	public Integer getFornecedorCodigo() {
		return fornecedorCodigo;
	}

	public void setFornecedorCodigo(Integer fornecedorCodigo) {
		this.fornecedorCodigo = fornecedorCodigo;
	}

	public void updateFornecedorByCodigo() {
		try {
			getE().getProduto().setFornecedor(fornecedorFinder.findByCodigo(getFornecedorCodigo()));
		} catch (ViewException e) {
			JSFUtils.addHandledException(e);
		}
	}

	public void updateFornecedor() {
		if (getE().getProduto().getFornecedor() != null) {
			setFornecedorCodigo(getE().getProduto().getFornecedor().getCodigo());
		}
	}

	public void updateFornecedor(Fornecedor fornecedor) {
		if (fornecedor != null) {
			getE().getProduto().setFornecedor(fornecedor);
			setFornecedorCodigo(fornecedor.getCodigo());
		}
	}

	public void updateGrade() {
		if ((getE().getId() == null) && (getE().getProduto().getGrade() != null)) {
			getE().getProduto().setUnidadeProduto(getE().getProduto().getGrade().getUnidadeProdutoPadrao());
		}
	}

	public void carregarPadroesTecido(ArtigoCortina artigoCortina) {
		try {
			artigoCortina.getTecido().setAlturaBarraPadrao(CurrencyUtils.getBigDecimalCurrency(getConfigFinder()
					.findConfigByChave(Configs.CRTN_ALTURA_BARRA_PADRAO.getChave()).getValor()));
			artigoCortina.getTecido().setAlturaMaxHorizontal(CurrencyUtils.getBigDecimalCurrency(getConfigFinder()
					.findConfigByChave(Configs.CRTN_ALTURA_MAX_HORIZONAL.getChave()).getValor()));
		} catch (Exception e) {
			JSFUtils.addErrorMsg("Erro ao carregar valores padrão de tecido.");
			logger.error(e);
		}
	}

	@Override
	public void setE(ArtigoCortina e) {
		super.setE(e);
	}

	@Override
	public void afterGetE(ArtigoCortina e) {
	}

}
