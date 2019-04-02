package com.bonsucesso.erp.producao.catalogo.view;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.Query;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.producao.catalogo.data.CatalogoDataMapper;
import com.bonsucesso.erp.producao.catalogo.data.CatalogoFinder;
import com.bonsucesso.erp.producao.catalogo.data.CatalogoItemArtigoDataMapper;
import com.bonsucesso.erp.producao.catalogo.data.CatalogoItemDataMapper;
import com.bonsucesso.erp.producao.catalogo.data.CatalogoItemFotoDataMapperImpl;
import com.bonsucesso.erp.producao.catalogo.model.Catalogo;
import com.bonsucesso.erp.producao.catalogo.model.CatalogoItem;
import com.bonsucesso.erp.producao.catalogo.model.CatalogoItemArtigo;
import com.bonsucesso.erp.producao.data.TipoArtigoFinder;
import com.bonsucesso.erp.producao.model.Confeccao;
import com.bonsucesso.erp.producao.model.Instituicao;
import com.bonsucesso.erp.producao.model.TipoArtigo;
import com.ocabit.base.view.AbstractEntityFormListView;
import com.ocabit.base.view.StoredViewInfoHandler;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.config.data.ConfigFinder;
import com.ocabit.jsf.utils.JSFUtils;


/**
 * View para a entidade Catalogo.
 *
 * @author Carlos Eduardo Pauluk
 */
@Component("catalogoFormListView")
@Scope("view")
public final class CatalogoFormListView extends
		AbstractEntityFormListView<Catalogo, CatalogoDataMapper, CatalogoFinder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3472350314342856182L;

	private CatalogoItem item;

	private List<TipoArtigo> tiposArtigosItem;

	private List<TipoArtigo> tiposArtigosSel;

	@Autowired
	private CatalogoItemArtigoDataMapper artigoDataMapper;

	@Autowired
	private CatalogoItemDataMapper itemDataMapper;

	@Autowired
	private TipoArtigoFinder tipoArtigoFinder;

	@Autowired
	private StoredViewInfoHandler storedViewInfoHandler;

	private String[] camposStored = new String[] { "item" };

	@Autowired
	private ConfigFinder configFinder;

	public final static String CHAVE_DIR_ARQUIVOS = "config.produto.catalogo.arquivosdir";

	@PostConstruct
	public void init() {
		try {
			List<Catalogo> todos = getFinder().findAll("descricao");
			if (todos != null && todos.size() > 0) {
				setE(todos.get(0));
			}

		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public void restoreViewInfo() {
		try {
			getStoredViewInfoHandler().processStoredViewInfo("catalogoFormListView", this); // aqui deve ser setado a instituicao, tipoArtigo e confeccao
			CatalogoItemFotoFormView fotoView = (CatalogoItemFotoFormView) getFinder().getBeanFactory()
					.getBean("catalogoItemFotoFormView");
			fotoView.setArquivos(null);
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public void storeViewInfo() {
		getStoredViewInfoHandler().store("catalogoFormListView", this, camposStored);
	}

	public void updateItem() {
		refreshItem();
		storeViewInfo();
	}

	/**
	 * 
	 */
	public void carregarInstituicoes() {

		try {

			getDataMapper().limparCatalogo();

			Query qry = getFinder().getEntityManager()
					.createQuery("FROM Instituicao WHERE codigo BETWEEN 501 AND 608 ORDER BY nome");

			@SuppressWarnings("unchecked")
			List<Instituicao> escolas = qry.getResultList();
			int i = 1;
			for (Instituicao instituicao : escolas) {
				CatalogoItem ci = new CatalogoItem();
				ci.setCatalogo(getE());
				ci.setDescricao(instituicao.getNome());
				ci.setOrdem(i++);
				ci.setInstituicao(instituicao);
				getE().getItens().add(ci);

				Query qryArtigos = getFinder().getEntityManager()
						.createQuery("FROM Confeccao WHERE instituicao = :instituicao GROUP BY tipoArtigo");
				qryArtigos.setParameter("instituicao", instituicao);
				@SuppressWarnings("unchecked")
				List<Confeccao> confeccoes = qryArtigos.getResultList();

				List<CatalogoItemArtigo> artigos = new ArrayList<CatalogoItemArtigo>();

				for (Confeccao confeccao : confeccoes) {
					CatalogoItemArtigo cia = new CatalogoItemArtigo();
					cia.setCatalogoItem(ci);
					cia.setDescricao(confeccao.getTipoArtigo().getDescricao());
					cia.setTipoArtigo(confeccao.getTipoArtigo());
					artigos.add(cia);
				}

				ci.setArtigos(artigos);
			}
			getDataMapper().saveItens(getE());
			getDataMapper().save(getE());
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public void onReorder() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "List Reordered", null));
	}

	/**
	 * Método que monta a lista dos possíveis TipoArtigo que podem ser selecionados (os que ainda não foram selecionados para o item).
	 */
	public void updateTiposArtigosSel() {
		try {
			List<TipoArtigo> todos = getTipoArtigoFinder().findAll("descricao");
			List<TipoArtigo> tiposArtigosSel = new ArrayList<TipoArtigo>();

			for (TipoArtigo tipoArtigo : todos) {
				boolean jaTem = false;
				for (CatalogoItemArtigo artigo : getItem().getArtigos()) {
					if (artigo.getTipoArtigo().equals(tipoArtigo)) {
						jaTem = true;
						break;
					}
				}
				if (!jaTem) {
					tiposArtigosSel.add(tipoArtigo);
				}
			}

			setTiposArtigosItem(null);
			refreshItem();
			for (CatalogoItemArtigo cia : getItem().getArtigos()) {
				getTiposArtigosItem().add(cia.getTipoArtigo());
			}

			setTiposArtigosSel(tiposArtigosSel);
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	/**
	 * Salva os novos tipos de artigos selecionados.
	 */
	public void saveArtigosItem() {
		try {
			getArtigoDataMapper().saveArtigosItem(getItem(), getTiposArtigosItem());
			refreshItem();
			final RequestContext context = RequestContext.getCurrentInstance();
			context.addCallbackParam("saved", true);
			context.addCallbackParam("dlgId", "dlgSelArtigos");
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	/**
	 * 
	 * @param artigo
	 */
	public void deletarArtigo(CatalogoItemArtigo artigo) {
		try {
			getArtigoDataMapper().delete(artigo);
			setItem(getItemDataMapper().refresh(getItem()));
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	/**
	 * Salva um artigo depois de ter sido editado diretamente no datatable.
	 * 
	 * @param artigo
	 */
	public void saveArtigo(CatalogoItemArtigo artigo) {
		try {
			getArtigoDataMapper().save(artigo);
			setItem(getItemDataMapper().refresh(getItem()));
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public void refreshItem() {
		try {
			setItem(getItemDataMapper().refresh(getItem()));
			if (getItem().getArtigos() != null) {
				getItem().getArtigos().size();
			}
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public void imprimirCatalogo() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			String dir = getConfigFinder().findConfigByChave(CatalogoItemFotoDataMapperImpl.CHAVE_DIR_ARQUIVOS)
					.getValor();

			params.put("arquivosDir", dir);
			getReportBuilder().imprimir(params, "producao/catalogo/rpCatalogo", "Catalogo");
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}	

	// -----------------------------------------------------

	public CatalogoItem getItem() {
		return item;
	}

	public void setItem(CatalogoItem item) {
		this.item = item;
	}

	public List<TipoArtigo> getTiposArtigosItem() {
		if (tiposArtigosItem == null) {
			tiposArtigosItem = new ArrayList<TipoArtigo>();
		}
		return tiposArtigosItem;
	}

	public void setTiposArtigosItem(List<TipoArtigo> tiposArtigosItem) {
		this.tiposArtigosItem = tiposArtigosItem;
	}

	public List<TipoArtigo> getTiposArtigosSel() {
		return tiposArtigosSel;
	}

	public void setTiposArtigosSel(List<TipoArtigo> tiposArtigosSel) {
		this.tiposArtigosSel = tiposArtigosSel;
	}

	public CatalogoItemArtigoDataMapper getArtigoDataMapper() {
		return artigoDataMapper;
	}

	public void setArtigoDataMapper(CatalogoItemArtigoDataMapper artigoDataMapper) {
		this.artigoDataMapper = artigoDataMapper;
	}

	public CatalogoItemDataMapper getItemDataMapper() {
		return itemDataMapper;
	}

	public void setItemDataMapper(CatalogoItemDataMapper itemDataMapper) {
		this.itemDataMapper = itemDataMapper;
	}

	public TipoArtigoFinder getTipoArtigoFinder() {
		return tipoArtigoFinder;
	}

	public void setTipoArtigoFinder(TipoArtigoFinder tipoArtigoFinder) {
		this.tipoArtigoFinder = tipoArtigoFinder;
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

	public ConfigFinder getConfigFinder() {
		return configFinder;
	}

	public void setConfigFinder(ConfigFinder configFinder) {
		this.configFinder = configFinder;
	}

}
