package com.bonsucesso.erp.estoque.view;



import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.faces.event.AjaxBehaviorEvent;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.cortinas.data.ArtigoCortinaDataMapper;
import com.bonsucesso.erp.cortinas.data.ArtigoCortinaFinder;
import com.bonsucesso.erp.cortinas.model.ArtigoCortina;
import com.bonsucesso.erp.cortinas.model.TipoArtigoCortina;
import com.bonsucesso.erp.estoque.business.CalculoPreco;
import com.bonsucesso.erp.estoque.business.ProdutoBusiness;
import com.bonsucesso.erp.estoque.data.DeptoFinder;
import com.bonsucesso.erp.estoque.data.FornecedorFinder;
import com.bonsucesso.erp.estoque.data.ProdutoDataMapper;
import com.bonsucesso.erp.estoque.data.ProdutoFinder;
import com.bonsucesso.erp.estoque.data.ProdutoPrecoDataMapper;
import com.bonsucesso.erp.estoque.model.Departamento;
import com.bonsucesso.erp.estoque.model.Fornecedor;
import com.bonsucesso.erp.estoque.model.Produto;
import com.bonsucesso.erp.estoque.model.ProdutoPreco;
import com.bonsucesso.erp.estoque.model.ProdutoSaldo;
import com.ocabit.base.view.AbstractEntityFormView;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.config.data.ConfigFinder;
import com.ocabit.jsf.utils.JSFUtils;


/**
 * View para a entidade Produto.
 *
 * @author Carlos Eduardo Pauluk
 */
@Component("produtoFormView")
@Scope("view")
public class ProdutoFormView extends AbstractEntityFormView<Produto, ProdutoDataMapper> {

	/**
	 *
	 */
	private static final long serialVersionUID = -4347678258692403653L;

	protected static Logger logger = Logger.getLogger(ProdutoFormView.class);

	@Autowired
	private ProdutoFinder finder;

	private boolean alterandoPreco = true;

	@Autowired
	private ProdutoPrecoDataMapper produtoPrecoDataMapper;

	@Autowired
	private ProdutoListView produtoListView;

	@Autowired
	private ProdutoBusiness produtoBusiness;

	private Map<Integer, ProdutoSaldo> mapaSaldos;

	@Autowired
	private ArtigoCortinaFinder artigoCortinaFinder;

	@Autowired
	private ArtigoCortinaDataMapper artigoCortinaDataMapper;

	@Autowired
	private CalculoPreco calculoPreco;

	@Autowired
	private FornecedorFinder fornecedorFinder;

	@Autowired
	private ConfigFinder configFinder;

	// Datatables utilizado para salvar um novo produto em uma nova cotação
	private Departamento depto;

	@Autowired
	private DeptoFinder deptoFinder;

	@Autowired
	private ListMaker lmEstoque;

	private ProdutoPreco preco;

	private Map<Integer, Double> saldos;

	private Integer fornecedorCodigo;

	/**
	 * Marca para criar ArtigoCortina correspondente aos Produtos na alteração em lote.
	 */
	private boolean criarArtigosCortina = false;

	public boolean isCriarArtigosCortina() {
		return criarArtigosCortina;
	}

	public void setCriarArtigosCortina(boolean criarArtigosCortina) {
		this.criarArtigosCortina = criarArtigosCortina;
	}

	public void iniAlterarEmLote() {
		try {
			setCriarArtigosCortina(false);
			if ((produtoListView.getSelecteds() != null) && (produtoListView.getSelecteds().size() >= 2)) {
				setE(new Produto());
				setPreco(new ProdutoPreco());
				for (Produto produto : getProdutoListView().getSelecteds()) {
					getFinder().refresh(produto);
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

	public ProdutoListView getProdutoListView() {
		return produtoListView;
	}

	public void setProdutoListView(ProdutoListView produtoListView) {
		this.produtoListView = produtoListView;
	}

	public ArtigoCortinaFinder getArtigoCortinaFinder() {
		return artigoCortinaFinder;
	}

	public void setArtigoCortinaFinder(ArtigoCortinaFinder artigoCortinaFinder) {
		this.artigoCortinaFinder = artigoCortinaFinder;
	}

	public ArtigoCortinaDataMapper getArtigoCortinaDataMapper() {
		return artigoCortinaDataMapper;
	}

	public void setArtigoCortinaDataMapper(ArtigoCortinaDataMapper artigoCortinaDataMapper) {
		this.artigoCortinaDataMapper = artigoCortinaDataMapper;
	}

	public CalculoPreco getCalculoPreco() {
		return calculoPreco;
	}

	public void setCalculoPreco(CalculoPreco calculoPreco) {
		this.calculoPreco = calculoPreco;
	}

	public ProdutoFinder getFinder() {
		return finder;
	}

	public void setFinder(ProdutoFinder finder) {
		this.finder = finder;
	}

	public Departamento getDepto() {
		if (depto == null) {
			depto = new Departamento();
		}
		return depto;
	}

	public void setDepto(Departamento depto) {
		try {
			if ((depto != null) && (depto.getId() != null)) {
				depto = getDeptoFinder().refresh(depto);
				// touch para lazy
				depto.getSubdeptos().size();
			}
			this.depto = depto;
		} catch (ViewException e1) {
			logger.error(e1);
			JSFUtils.addErrorMsg(e1.getMessage());
		}
	}

	public DeptoFinder getDeptoFinder() {
		return deptoFinder;
	}

	public void setDeptoFinder(DeptoFinder deptoFinder) {
		this.deptoFinder = deptoFinder;
	}

	public ProdutoBusiness getProdutoBusiness() {
		return produtoBusiness;
	}

	public void setProdutoBusiness(ProdutoBusiness produtoBusiness) {
		this.produtoBusiness = produtoBusiness;
	}

	public ListMaker getLmEstoque() {
		return lmEstoque;
	}

	public void setLmEstoque(ListMaker lmEstoque) {
		this.lmEstoque = lmEstoque;
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
	public Produto newE() {
		setAlterandoPreco(true);
		return new Produto();
	}

	@Override
	public void afterSave() {
		// Utilizado somente quando o form é para o dialog
		RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam("dlgId", "dlgProduto");
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

	public Map<Integer, Double> getSaldos() {
		return saldos;
	}

	public void setSaldos(Map<Integer, Double> saldos) {
		this.saldos = saldos;
	}

	@Override
	public void afterNovo() {
		getLmEstoque().setDepto(null);
		setFornecedorCodigo(null);
		novoPreco();
	}

	public void deletarPreco() {
		try {
			produtoPrecoDataMapper.delete(getPreco());
		} catch (final ViewException e) {
			JSFUtils.addErrorMsgErroAoDeletar();
		}
	}

	public boolean isAlterandoPreco() {
		return alterandoPreco;
	}

	public void setAlterandoPreco(boolean alterandoPreco) {
		this.alterandoPreco = alterandoPreco;
	}

	@Override
	public void afterSetE(Produto e) {
		try {
			setAlterandoPreco(false);
			if (e.getId() != null) {
				setPreco((ProdutoPreco) getE().getPrecoAtual().clone());
				getLmEstoque().setDepto(e.getSubdepto().getDepto());
				if (getE().getFornecedor() != null) {
					setFornecedorCodigo(getE().getFornecedor().getCodigo());
				}
				setMapaSaldos(getProdutoBusiness().buildMapGradeSaldos(e));
				getProdutoBusiness().updateQtdeTotal(getE());
			}
		} catch (CloneNotSupportedException e1) {
			logger.error(e1);
			JSFUtils.addErrorMsg("Erro ao tratar preço");
		} catch (ViewException e1) {
			logger.error(e1);
			JSFUtils.addHandledException(e1);
		}
		try {
			if ((getE() != null) && (getE().getSubdepto() != null) && (getE().getSubdepto().getDepto() != null)) {
				setDepto(deptoFinder.refresh(getE().getSubdepto().getDepto()));
			}
		} catch (ViewException e1) {
			logger.error(e1);
			JSFUtils.addHandledException(e1);
		}

	}

	public void saveLote() {
		try {
			//			for (Produto pc : getLista()) {
			//				System.out.println(pc);
			//			}
			alterarEmLote();
			getDataMapper().saveList(getProdutoListView().getSelecteds());
			JSFUtils.addInfoMsg(JSFUtils.MSG_REGISTRO_SALVO);

			logger.debug("setando parâmetro saved no contexto");
			// Adiciona um parâmetro de retorno para informar que foi salvo
			final RequestContext context = RequestContext.getCurrentInstance();
			context.addCallbackParam("saved", true);
			context.addCallbackParam("dlgId", "dlgAlterarEmLote");

			getProdutoListView().setList(new ArrayList<Produto>());

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

	private void alterarEmLote() throws ViewException {
		if ((produtoListView.getSelecteds() != null) && (produtoListView.getSelecteds().size() > 0)) {

			Produto e = getE();
			ProdutoPreco preco = getPreco();

			for (Produto produto : produtoListView.getSelecteds()) {
				getFinder().refresh(produto);

				if ((e.getFornecedor() != null)
						&& (e.getFornecedor().getId() != null)) {
					produto.setFornecedor(e.getFornecedor());
				}
				if ((preco.getPrecoCusto() != null) && (preco.getPrecoCusto().doubleValue() > 0.0)) {
					produto.getPrecoAtual().setPrecoCusto(preco.getPrecoCusto());
				}
				if ((preco.getCoeficiente() != null) && (preco.getCoeficiente().doubleValue() > 0.0)) {
					produto.getPrecoAtual().setCoeficiente(preco.getCoeficiente());
				}
				if ((preco.getCustoOperacional() != null) && (preco.getCustoOperacional().doubleValue() > 0.0)) {
					produto.getPrecoAtual()
							.setCustoOperacional(preco.getCustoOperacional());
				}
				if (preco.getDtCusto() != null) {
					produto.getPrecoAtual().setDtCusto(preco.getDtCusto());
				}
				if ((preco.getMargem() != null) && (preco.getMargem().doubleValue() > 0.0)) {
					produto.getPrecoAtual().setMargem(preco.getMargem());
				}
				if ((preco.getPrazo() != null) && (preco.getPrazo() > 0.0)) {
					produto.getPrecoAtual().setPrazo(preco.getPrazo());
				}
				if ((preco.getPrecoPromo() != null) && (preco.getPrecoPromo().doubleValue() > 0.0)) {
					produto.getPrecoAtual().setPrecoPromo(preco.getPrecoPromo());
				}
				//				if (criarArtigosCortina == true) {
				//					if (produto.getArtigoCortina() == null) {
				//						ArtigoCortina ac = new ArtigoCortina();
				//						ac.setProduto(produto);
				//						ac.setTipoArtigoCortina(TipoArtigoCortina.INDEFINIDO);
				//						produto.setArtigoCortina(ac);
				//					} else {
				//						//produto.setArtigoCortina( getArtigoCortinaFinder().refresh(entity)
				//					}
				//				}

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
		final RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam("dlgId", "dlgProduto");
		try {
			validate();
			setE(getDataMapper().saveComPreco(getE(), getPreco()));
			afterSave();
			afterSetId();
			JSFUtils.addInfoMsg(JSFUtils.MSG_REGISTRO_SALVO);
			context.addCallbackParam("saved", true);
			JSFUtils.execute("loadList()");
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

	public void updateFornecedorByCodigo() {
		try {
			getE().setFornecedor(fornecedorFinder.findByCodigo(getFornecedorCodigo()));
		} catch (ViewException e) {
			JSFUtils.addHandledException(e);
		}
	}

	public void updateFornecedor(Fornecedor fornecedor) {
		if (fornecedor != null) {
			getE().setFornecedor(fornecedor);
			setFornecedorCodigo(fornecedor.getCodigo());
		}
	}

	public void updateReduzido() {
		try {
			Produto produto = getFinder().findByReduzido(getE().getReduzido());
			if (produto != null) {
				setE(produto);
				setPreco(getE().getPrecoAtual());
				JSFUtils.execute("updateOpnDlgProduto()");
			}
		} catch (ViewException e) {
			JSFUtils.addErrorMsg("Erro ao pesquisar pelo reduzido.");
			logger.error(e);
		}
	}

	public void updateGrade() {
		if ((getE().getId() == null) && (getE().getGrade() != null)) {
			getE().setUnidadeProduto(getE().getGrade().getUnidadeProdutoPadrao());
		}
	}

	public Integer getFornecedorCodigo() {
		return fornecedorCodigo;
	}

	public void setFornecedorCodigo(Integer fornecedorCodigo) {
		this.fornecedorCodigo = fornecedorCodigo;
	}

	public void updateFornecedor() {
		if (getE().getFornecedor() != null) {
			setFornecedorCodigo(getE().getFornecedor().getCodigo());
		}
	}

	public void criarArtigoCortina() {
		try {
			save();
			ArtigoCortina ac = new ArtigoCortina();
			ac.setProduto(getE());
			ac.setTipoArtigoCortina(TipoArtigoCortina.INDEFINIDO);
			getArtigoCortinaDataMapper().save(ac);
		} catch (ViewException e) {
			JSFUtils.addErrorMsg("Erro ao criar o artigo de cortina para este produto");
			JSFUtils.addHandledException(e);
		}

	}

	public Map<Integer, ProdutoSaldo> getMapaSaldos() {
		return mapaSaldos;
	}

	public void setMapaSaldos(Map<Integer, ProdutoSaldo> mapaSaldos) {
		this.mapaSaldos = mapaSaldos;
	}

	public ProdutoSaldo getProdutoSaldoFromMapa(Integer ordem) {
		if (getE().getId() == null)
			return null;
		else
			return getMapaSaldos().get(ordem);
	}

	public void irParaProximo() {
		try {
			Produto proximo = getFinder().findProximo(getE());
			setE(proximo);
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public void irParaAnterior() {
		try {
			Produto anterior = getFinder().findAnterior(getE());
			setE(anterior);
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}

	}

	public boolean produtoEhArtigoCortina(Produto produto) {
		try {
			return getProdutoBusiness().getArtigoCortinaFinder().findByProduto(produto) != null;
		} catch (ViewException e) {
			JSFUtils.addErrorMsg("Erro ao verificar artigo de cortina.");
			JSFUtils.addHandledException(e);
			return false;
		}
	}
	
	public void updateReferencia() {
		
	}
}
