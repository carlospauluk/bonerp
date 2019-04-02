package com.bonsucesso.erp.producao.catalogo.view;



import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.producao.catalogo.data.CatalogoItemFotoDataMapper;
import com.bonsucesso.erp.producao.catalogo.model.CatalogoItemArtigo;
import com.bonsucesso.erp.producao.catalogo.model.CatalogoItemFoto;
import com.ocabit.base.view.AbstractEntityFormView;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.ArquivoUploadVO;
import com.ocabit.jsf.utils.JSFUtils;


/**
 * View para a entidade CatalogoItemArquivo.
 *
 * @author Carlos Eduardo Pauluk
 */
@Component("catalogoItemFotoFormView")
@Scope("view")
public class CatalogoItemFotoFormView
		extends AbstractEntityFormView<CatalogoItemFoto, CatalogoItemFotoDataMapper> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7508745800802457874L;

	protected static Logger logger = Logger.getLogger(CatalogoItemFotoFormView.class);

	private List<ArquivoUploadVO> arquivos;

	private List<CatalogoItemArtigo> artigos;

	@Autowired
	private CatalogoFormListView catalogoFormListView;

	@PostConstruct
	public void init() {
		setArquivos(null);
	}

	/**
	 * Faz o upload para o managed bean.
	 * 
	 * @param event
	 */
	public synchronized void uploadArquivo(FileUploadEvent event) {
		try {
			UploadedFile file = event.getFile();
			// não sei porque se não mandar o file.getInputstream, depois o JSF perde a referência...
			List<ArquivoUploadVO> arquivos = new ArrayList<ArquivoUploadVO>();
			arquivos.add(new ArquivoUploadVO(file, file.getInputstream()));
			// getArquivos().add(new ArquivoUploadVO(file, file.getInputstream()));
			// logger.info("Uploads: " + getArquivos().size());
			logger.info("Uploads: " + arquivos.size());
			JSFUtils.addInfoMsg("O arquivo " + event.getFile().getFileName() + " foi carregado com sucesso");
			// saveUpload();
			getDataMapper().uploadArquivos(getCatalogoFormListView().getItem(), arquivos);
			getCatalogoFormListView().refreshItem();
			
		} catch (Exception e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	/**
	 * Salva efetivamente o upload.
	 */
	public void saveUpload() {
		try {
			getDataMapper().uploadArquivos(getCatalogoFormListView().getItem(), getArquivos());
			getCatalogoFormListView().refreshItem();
			setArquivos(null);
		} catch (Exception e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	@Override
	public void deletar() {
		try {
			getDataMapper().deleleFileAndDelete(getE());
			getCatalogoFormListView().refreshItem();
			JSFUtils.addInfoMsgRegistroDeletado();
		} catch (final Exception e) {
			logger.error(e);
			JSFUtils.addErrorMsgErroAoDeletar();
		}
	}

	public void openDlgSelArtigosFoto(CatalogoItemFoto foto) {
		setArtigos(null);
		getCatalogoFormListView().refreshItem();
		if (getCatalogoFormListView().getItem().getArtigos() != null) {
			getCatalogoFormListView().getItem().getArtigos().size();
		}
		if (getCatalogoFormListView().getItem().getFotos() != null) {
			getCatalogoFormListView().getItem().getFotos().size();
		}
		setE(foto);
		if (getE().getArtigos() != null) {
			getE().getArtigos().size(); // touch
			getArtigos().addAll(getE().getArtigos());
		}
	}

	public void saveArtigosFoto() {
		try {
			getDataMapper().saveArtigosFoto(getE(), getArtigos());
			getCatalogoFormListView().refreshItem();
			final RequestContext context = RequestContext.getCurrentInstance();
			context.addCallbackParam("saved", true);
			context.addCallbackParam("dlgId", "dlgSelArtigosFoto");
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public CatalogoFormListView getCatalogoFormListView() {
		return catalogoFormListView;
	}

	public void setCatalogoFormListView(CatalogoFormListView catalogoFormListView) {
		this.catalogoFormListView = catalogoFormListView;
	}

	public List<ArquivoUploadVO> getArquivos() {
		if (arquivos == null) {
			arquivos = new ArrayList<ArquivoUploadVO>();
		}
		return arquivos;
	}

	public void setArquivos(List<ArquivoUploadVO> arquivos) {
		this.arquivos = arquivos;
	}

	public List<CatalogoItemArtigo> getArtigos() {
		if (artigos == null) {
			artigos = new ArrayList<CatalogoItemArtigo>();
		}
		return artigos;
	}

	public void setArtigos(List<CatalogoItemArtigo> artigos) {
		this.artigos = artigos;
	}

}
