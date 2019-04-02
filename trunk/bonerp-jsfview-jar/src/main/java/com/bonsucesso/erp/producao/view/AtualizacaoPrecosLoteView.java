package com.bonsucesso.erp.producao.view;



import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.producao.business.ConfeccaoBusiness;
import com.bonsucesso.erp.producao.data.ConfeccaoDataMapper;
import com.bonsucesso.erp.producao.data.ConfeccaoFinder;
import com.bonsucesso.erp.producao.data.InstituicaoFinder;
import com.bonsucesso.erp.producao.model.Instituicao;
import com.ocabit.base.view.StoredViewInfoHandler;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;


/**
 * View para construir e manipular confecções.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("atualizacaoPrecosLoteView")
@Scope("view")
public class AtualizacaoPrecosLoteView implements Serializable {

	/**
	 *	
	 */
	private static final long serialVersionUID = -5201510239547723579L;

	protected static Logger logger = Logger.getLogger(AtualizacaoPrecosLoteView.class);

	@Autowired
	private ConfeccaoBusiness confeccaoBusiness;

	@Autowired
	private ConfeccaoFinder confeccaoFinder;

	@Autowired
	private InstituicaoFinder instituicaoFinder;

	@Autowired
	private ConfeccaoDataMapper confeccaoDataMapper;

	private List<Instituicao> instituicoes;

	private List<Instituicao> instituicoesSel;

	@Autowired
	private StoredViewInfoHandler storedViewInfoHandler;

	public static final String[] camposStored = new String[] { "instituicoesSel" };

	private String viewName = "atualizacaoPrecosLoteView";

	@PostConstruct
	public void init() {
		try {
			getStoredViewInfoHandler().processStoredViewInfo(viewName, this);
		} catch (ViewException e) {
			JSFUtils.addErrorMsg("Erro ao inicializar.");
			JSFUtils.addHandledException(e);
			logger.error(e);
		}
	}

	public void recriarPrecos() {
		try {
			for (Instituicao instituicao : getInstituicoesSel()) {
				getConfeccaoBusiness().calcularPrecos(instituicao);
			}
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public List<Instituicao> getInstituicoes() {
		if (instituicoes == null) {
			try {
				instituicoes = getInstituicaoFinder().findInstituicoesFornecedores();
			} catch (ViewException e) {
				JSFUtils.addHandledException(e);
			}
		}
		return instituicoes;
	}

	public void setInstituicoes(List<Instituicao> instituicoes) {
		this.instituicoes = instituicoes;
	}

	public List<Instituicao> getInstituicoesSel() {
		return instituicoesSel;
	}

	public void setInstituicoesSel(List<Instituicao> instituicoesSel) {
		this.instituicoesSel = instituicoesSel;
	}

	public ConfeccaoFinder getConfeccaoFinder() {
		return confeccaoFinder;
	}

	public void setConfeccaoFinder(ConfeccaoFinder confeccaoFinder) {
		this.confeccaoFinder = confeccaoFinder;
	}

	public InstituicaoFinder getInstituicaoFinder() {
		return instituicaoFinder;
	}

	public void setInstituicaoFinder(InstituicaoFinder instituicaoFinder) {
		this.instituicaoFinder = instituicaoFinder;
	}

	public StoredViewInfoHandler getStoredViewInfoHandler() {
		return storedViewInfoHandler;
	}

	public void setStoredViewInfoHandler(StoredViewInfoHandler storedViewInfoHandler) {
		this.storedViewInfoHandler = storedViewInfoHandler;
	}

	public ConfeccaoBusiness getConfeccaoBusiness() {
		return confeccaoBusiness;
	}

	public void setConfeccaoBusiness(ConfeccaoBusiness confeccaoBusiness) {
		this.confeccaoBusiness = confeccaoBusiness;
	}

	public ConfeccaoDataMapper getConfeccaoDataMapper() {
		return confeccaoDataMapper;
	}

	public void setConfeccaoDataMapper(ConfeccaoDataMapper confeccaoDataMapper) {
		this.confeccaoDataMapper = confeccaoDataMapper;
	}

}
