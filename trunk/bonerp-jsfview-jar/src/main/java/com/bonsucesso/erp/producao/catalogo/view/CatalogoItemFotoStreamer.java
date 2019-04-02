package com.bonsucesso.erp.producao.catalogo.view;



import java.io.File;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.producao.catalogo.data.CatalogoItemFotoDataMapper;
import com.bonsucesso.erp.producao.catalogo.data.CatalogoItemFotoDataMapperImpl;
import com.bonsucesso.erp.producao.catalogo.model.CatalogoItemFoto;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.config.data.ConfigFinder;
import com.ocabit.jsf.utils.JSFImageUtil;
import com.ocabit.jsf.utils.JSFUtils;


@Component("catalogoItemFotoStreamer")
@Scope("application")
public class CatalogoItemFotoStreamer {

	protected static Logger logger = Logger.getLogger(CatalogoItemFotoStreamer.class);

	@Autowired
	private CatalogoItemFotoDataMapper dataMapper;

	@Autowired
	private ConfigFinder configFinder;

	public StreamedContent getImage() {
		try {
			FacesContext context = FacesContext.getCurrentInstance();

			if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
				// So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
				return new DefaultStreamedContent();
			} else {
				String catalogoItemFotoId = context.getExternalContext().getRequestParameterMap().get("id");
				CatalogoItemFoto foto = getDataMapper().findById(Long.parseLong(catalogoItemFotoId));
				String dir = getConfigFinder().findConfigByChave(CatalogoItemFotoDataMapperImpl.CHAVE_DIR_ARQUIVOS)
						.getValor();
				File file = new File(dir + foto.getNomeArquivo());
				return JSFImageUtil.getImage(file);
			}
		} catch (NumberFormatException e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro no parâmetro passado para a foto.");
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
		return null;
	}

	public StreamedContent getThumbnail() {
		try {
			FacesContext context = FacesContext.getCurrentInstance();

			if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
				return new DefaultStreamedContent();
			} else {
				String catalogoItemFotoId = context.getExternalContext().getRequestParameterMap().get("id");
				String widthStr = context.getExternalContext().getRequestParameterMap().get("width");
				
				int width = Integer.parseInt(widthStr);
				
				CatalogoItemFoto foto = getDataMapper().findById(Long.parseLong(catalogoItemFotoId));
				String dir = getConfigFinder().findConfigByChave(CatalogoItemFotoDataMapperImpl.CHAVE_DIR_ARQUIVOS)
						.getValor();
				// dir = "D:\\home\\ocabit\\bonerp\\producao\\catalogo\\";
				File sourceFile = new File(dir + foto.getNomeArquivo());
				return JSFImageUtil.getThumbnail(sourceFile, width);
			}
		} catch (NumberFormatException e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro no parâmetro passado para a foto.");
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
		return null;
	}

	public CatalogoItemFotoDataMapper getDataMapper() {
		return dataMapper;
	}

	public void setDataMapper(CatalogoItemFotoDataMapper dataMapper) {
		this.dataMapper = dataMapper;
	}

	public ConfigFinder getConfigFinder() {
		return configFinder;
	}

	public void setConfigFinder(ConfigFinder configFinder) {
		this.configFinder = configFinder;
	}

}
