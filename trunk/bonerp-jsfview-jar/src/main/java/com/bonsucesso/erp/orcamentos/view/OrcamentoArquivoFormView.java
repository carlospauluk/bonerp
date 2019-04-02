package com.bonsucesso.erp.orcamentos.view;



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.orcamentos.data.OrcamentoArquivoDataMapper;
import com.bonsucesso.erp.orcamentos.model.Orcamento;
import com.bonsucesso.erp.orcamentos.model.OrcamentoArquivo;
import com.ocabit.base.view.AbstractEntityFormView;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;


/**
 * View para a entidade OrcamentoArquivo.
 *
 * @author Carlos Eduardo Pauluk
 */
@Component("orcamentoArquivoFormView")
@Scope("view")
public class OrcamentoArquivoFormView extends AbstractEntityFormView<OrcamentoArquivo, OrcamentoArquivoDataMapper> {

	/**
	 *
	 */
	private static final long serialVersionUID = -4121651548776556319L;

	protected static Logger logger = Logger.getLogger(OrcamentoArquivoFormView.class);

	private UploadedFile arquivoOrcamento;

	private InputStream uploadedFileInputStream;

	private List<OrcamentoArquivo> dataList;

	@Autowired
	private OrcamentoConfeccaoFormView orcamentoFormView;

	@PostConstruct
	public void init() {
		try {
			loadInitialList();
		} catch (Exception e) {
			String msg = "Erro ao iniciar a tela";
			JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, msg);
			logger.error(msg, e);
		}
	}

	/**
	 * Carrega a listagem inicial.
	 */
	public void loadInitialList() {
		try {
			if ((getOrcamentoFormView().getE() != null) && (getOrcamentoFormView().getE().getId() != null)) {
				Orcamento orcamento = getOrcamentoFormView().getDataMapper().refresh(getOrcamentoFormView().getE());
				dataList = orcamento.getArquivos();
			}
		} catch (final ViewException e) {
			JSFUtils.addErrorMsg("Erro ao preencher a lista");
		}
	}

	public List<OrcamentoArquivo> getDataList() {
		return dataList;
	}

	public void setDataList(List<OrcamentoArquivo> dataList) {
		this.dataList = dataList;
	}

	public OrcamentoConfeccaoFormView getOrcamentoFormView() {
		return orcamentoFormView;
	}

	public void setOrcamentoFormView(OrcamentoConfeccaoFormView orcamentoFormView) {
		this.orcamentoFormView = orcamentoFormView;
	}

	public UploadedFile getArquivoOrcamento() {
		return arquivoOrcamento;
	}

	public void setArquivoOrcamento(UploadedFile arquivoOrcamento) {
		this.arquivoOrcamento = arquivoOrcamento;
	}

	public InputStream getUploadedFileInputStream() {
		return uploadedFileInputStream;
	}

	public void setUploadedFileInputStream(InputStream uploadedFileInputStream) {
		this.uploadedFileInputStream = uploadedFileInputStream;
	}

	public void uploadArquivoOrcamento(FileUploadEvent event) {
		try {
			UploadedFile arquivoOrcamento = event.getFile();
			setArquivoOrcamento(arquivoOrcamento);
			setUploadedFileInputStream(arquivoOrcamento.getInputstream());
			JSFUtils.addInfoMsg("O arquivo " + event.getFile().getFileName() + " foi carregado com sucesso");
		} catch (IOException e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao fazer upload do arquivo.");
		}
	}

	public void saveUpload() {
		try {
			if (getUploadedFileInputStream() != null) {
				getE().setOrcamento(getOrcamentoFormView().getE());
				getE().setNomeArquivo(getArquivoOrcamento().getFileName());
				getDataMapper().upload(getE(), getUploadedFileInputStream());
				getOrcamentoFormView().refreshE();
				RequestContext context = RequestContext.getCurrentInstance();
				context.addCallbackParam("dlgId", "dlgArquivoOrcamento");
				context.addCallbackParam("saved", true);
				loadInitialList();
			} else {
				JSFUtils.addErrorMsg("O arquivo deve ser informado");
			}
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public StreamedContent downloadFile(OrcamentoArquivo orcamentoArquivo) {
		try {
			File file = getDataMapper().getFile(orcamentoArquivo);
			String contentType = java.nio.file.Files.probeContentType(file.toPath());

			return new DefaultStreamedContent(new FileInputStream(file), contentType, orcamentoArquivo
					.getNomeArquivo());
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
			return null;
		} catch (Exception e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao fazer o download do arquivo");
			return null;
		}
	}

	@Override
	public void deletar() {
		try {
			getDataMapper().deleleAndDelete(getE());
			JSFUtils.addInfoMsgRegistroDeletado();
			loadInitialList();

		} catch (final Exception e) {
			logger.error(e);
			JSFUtils.addErrorMsgErroAoDeletar();
		}
	}

	@Override
	public void novo() {
		super.novo();
		// touch para lazy
		if ((getOrcamentoFormView().getE() != null) && (getOrcamentoFormView().getE().getId() != null)) {
			getOrcamentoFormView().refreshE();
			orcamentoFormView.getE().getArquivos().size();
		}
	}

	public boolean verificarArquivo(OrcamentoArquivo orcamentoArquivo) {
		try {
			File file = getDataMapper().getFile(orcamentoArquivo);
			return file.exists();
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
			return false;
		}
	}

}
