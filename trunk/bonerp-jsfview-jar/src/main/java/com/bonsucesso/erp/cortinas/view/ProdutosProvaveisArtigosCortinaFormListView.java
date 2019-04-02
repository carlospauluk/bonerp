package com.bonsucesso.erp.cortinas.view;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
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
import com.bonsucesso.erp.estoque.data.ProdutoFinder;
import com.bonsucesso.erp.estoque.data.SubdeptoFinder;
import com.bonsucesso.erp.estoque.model.Produto;
import com.bonsucesso.erp.estoque.model.Subdepartamento;
import com.ocabit.base.data.filter.FilterUtils;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.config.data.ConfigFinder;
import com.ocabit.jsf.utils.JSFUtils;
import com.ocabit.utils.currency.CurrencyUtils;


@Component("produtosProvaveisArtigosCortinaFormListView")
@Scope("view")
public class ProdutosProvaveisArtigosCortinaFormListView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5891742474767232953L;

	protected static Logger logger = Logger.getLogger(ProdutosProvaveisArtigosCortinaFormListView.class);

	private List<Produto> selecteds;
	private List<ArtigoCortina> selectedsArtigosCortina;

	private List<Produto> list;

	private ArtigoCortina artigoCortina;

	private Map<String, Object> filtros;

	@Autowired
	private ProdutoFinder produtoFinder;

	@Autowired
	private ArtigoCortinaFinder artigoCortinaFinder;

	@Autowired
	private ArtigoCortinaDataMapper artigoCortinaDataMapper;

	@Autowired
	private SubdeptoFinder subdeptoFinder;

	@Autowired
	private ConfigFinder configFinder;

	@PostConstruct
	public void init() {
		try {
			Subdepartamento sd903_CORTINADO = getSubdeptoFinder().findByCodigo(903);
			Subdepartamento sd908_RENDACORTINA = getSubdeptoFinder().findByCodigo(908);
			Subdepartamento sd951_ACESSORIOSPCORTINA = getSubdeptoFinder().findByCodigo(951);

			getFiltros().put("subdeptos", new Subdepartamento[] { sd903_CORTINADO, sd908_RENDACORTINA,
					sd951_ACESSORIOSPCORTINA });
		} catch (ViewException e) {
			JSFUtils.addHandledException("Erro ao setar filtros de subdeptos.", e);
		}
	}

	@SuppressWarnings("unchecked")
	public void pesquisar() {
		try {
			setList(getProdutoFinder().findNaoArtigosCortinaBy((String) getFiltros()
					.get("strPesquisa"), FilterUtils.convertToList((getFiltros().get("subdeptos")))));
		} catch (ViewException e) {
			JSFUtils.addHandledException("Erro ao buscar produtos.", e);
		}
	}

	/**
	 * Para abrir o dlg já com o artigoCortina setado
	 * 
	 * @param produto
	 */
	public void updateArtigoCortina(Produto produto) {
		try {
			produto = getProdutoFinder().refresh(produto);
			ArtigoCortina artigoCortina = getArtigoCortinaFinder().findByProduto(produto);
			if (artigoCortina == null) {
				artigoCortina = new ArtigoCortina();
				artigoCortina.setProduto(produto);
				produto.getPrecos().size();
				produto.getSaldos().size();
			} else {
				artigoCortina.getProduto().getPrecos().size();
				artigoCortina.getProduto().getSaldos().size();
			}
			setArtigoCortina(artigoCortina);
		} catch (ViewException e) {
			JSFUtils.addHandledException("Erro ao setar o Artigo de Cortina", e);
		}
	}

	public void updateTipoArtigoCortina() {
		if (getArtigoCortina().getTipoArtigoCortina().equals(TipoArtigoCortina.TECIDO)) {
			if (getArtigoCortina().getTecido() == null) {
				getArtigoCortina().setTecido(new Tecido());
				carregarPadroesTecido(getArtigoCortina());
			}
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

	public boolean saveArtigoCortina() {
		boolean result = false;
		final RequestContext context = RequestContext.getCurrentInstance();
		try {
			setArtigoCortina(getArtigoCortinaDataMapper().save(getArtigoCortina()));
			logger.debug("afterSave()");
			logger.debug("JSFUtils.addInfoMsg(JSFUtils.MSGeREGISTROeSALVO);");
			// Informa em tela que o registro foi salvo com sucesso
			JSFUtils.addInfoMsg(JSFUtils.MSG_REGISTRO_SALVO);
			logger.debug("setando parâmetro saved no contexto");
			result = true;
			logger.debug("result = true;");
		} catch (Exception e) {
			logger.error("Erro no save():", e);
			JSFUtils.addHandledException(e);
		}
		context.addCallbackParam("saved", result);
		context.addCallbackParam("dlgId", "dlgArtigoCortina");
		pesquisar();
		logger.debug("return result;");
		return result;
	}

	public void iniAlterarEmLote() {
		if ((getSelecteds() != null)
				&& (getSelecteds().size() >= 2)) {
			setArtigoCortina(new ArtigoCortina());
			RequestContext context = RequestContext.getCurrentInstance();
			context.addCallbackParam("dlgId", "dlgAlterarEmLote");
			context.addCallbackParam("openDlg", true);
		} else {
			JSFUtils.addErrorMsg("Ao menos 2 registros devem estar selecionados");
		}
	}

	/**
	 * Só altera os valores na lista de selecteds. O saveList é chamado no método saveLote().
	 * 
	 * @throws ViewException
	 */
	private void alterarEmLote() throws ViewException {

		if ((getSelecteds() != null)
				&& (getSelecteds().size() > 0)) {

			ArtigoCortina artigoCortina = getArtigoCortina();

			setSelectedsArtigosCortina(null);

			for (Produto p : getSelecteds()) {

				p = getProdutoFinder().refresh(p);
				p.getSaldos().size();
				p.getPrecos().size();

				ArtigoCortina ac = new ArtigoCortina();
				ac.setProduto(p);
				getSelectedsArtigosCortina().add(ac);

				if (artigoCortina.getTipoArtigoCortina() != null) {
					ac.setTipoArtigoCortina(artigoCortina.getTipoArtigoCortina());
				}
				if (artigoCortina.getTipoArtigoCortina() != null) {
					ac.setTipoArtigoCortina(artigoCortina.getTipoArtigoCortina());

					if (TipoArtigoCortina.TECIDO.equals(artigoCortina.getTipoArtigoCortina())
							&& (artigoCortina.getTecido() != null)) {

						ac.setTecido(new Tecido());

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

	public void saveLote() {
		try {
			alterarEmLote();
			getArtigoCortinaDataMapper().saveList(getSelectedsArtigosCortina());
			JSFUtils.addInfoMsg(JSFUtils.MSG_REGISTRO_SALVO);

			logger.debug("setando parâmetro saved no contexto");
			// Adiciona um parâmetro de retorno para informar que foi salvo
			final RequestContext context = RequestContext.getCurrentInstance();
			context.addCallbackParam("saved", true);
			context.addCallbackParam("dlgId", "dlgAlterarEmLote");
			pesquisar();

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

	public List<Produto> getList() {
		return list;
	}

	public void setList(List<Produto> list) {
		this.list = list;
	}

	public List<Produto> getSelecteds() {
		return selecteds;
	}

	public void setSelecteds(List<Produto> selecteds) {
		this.selecteds = selecteds;
	}

	public List<ArtigoCortina> getSelectedsArtigosCortina() {
		if (selectedsArtigosCortina == null) {
			selectedsArtigosCortina = new ArrayList<ArtigoCortina>();
		}
		return selectedsArtigosCortina;
	}

	public void setSelectedsArtigosCortina(List<ArtigoCortina> selectedsArtigosCortina) {
		this.selectedsArtigosCortina = selectedsArtigosCortina;
	}

	public SubdeptoFinder getSubdeptoFinder() {
		return subdeptoFinder;
	}

	public void setSubdeptoFinder(SubdeptoFinder subdeptoFinder) {
		this.subdeptoFinder = subdeptoFinder;
	}

	public ProdutoFinder getProdutoFinder() {
		return produtoFinder;
	}

	public void setProdutoFinder(ProdutoFinder produtoFinder) {
		this.produtoFinder = produtoFinder;
	}

	public ArtigoCortina getArtigoCortina() {
		return artigoCortina;
	}

	public void setArtigoCortina(ArtigoCortina artigoCortina) {
		this.artigoCortina = artigoCortina;
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

	public ConfigFinder getConfigFinder() {
		return configFinder;
	}

	public void setConfigFinder(ConfigFinder configFinder) {
		this.configFinder = configFinder;
	}

	public Map<String, Object> getFiltros() {
		if (filtros == null) {
			filtros = new HashMap<String, Object>();
		}
		return filtros;
	}

	public void setFiltros(Map<String, Object> filtros) {
		this.filtros = filtros;
	}

	/**
	 * Limpa dados de filtros (inclusive da sessão) e carrega a listagem
	 * inicial.
	 */
	public void limparFiltrosECarregar() {
		setFiltros(null);
		init();
		pesquisar();
	}

}
