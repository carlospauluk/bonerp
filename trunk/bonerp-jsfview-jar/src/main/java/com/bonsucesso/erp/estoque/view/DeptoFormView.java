package com.bonsucesso.erp.estoque.view;



import java.util.List;

import javax.faces.application.FacesMessage;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.estoque.data.DeptoDataMapper;
import com.bonsucesso.erp.estoque.data.DeptoFinder;
import com.bonsucesso.erp.estoque.data.SubdeptoDataMapper;
import com.bonsucesso.erp.estoque.data.SubdeptoFinder;
import com.bonsucesso.erp.estoque.model.Departamento;
import com.bonsucesso.erp.estoque.model.Subdepartamento;
import com.ocabit.base.view.AbstractEntityFormView;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;


/**
 * View para a entidade Depto.
 *
 * @author Carlos Eduardo Pauluk
 */
@Component("deptoFormView")
@Scope("view")
public class DeptoFormView extends AbstractEntityFormView<Departamento, DeptoDataMapper> {

	private static final long serialVersionUID = 1L;

	protected static Logger logger = Logger.getLogger(DeptoFormView.class);

	private List<Departamento> dtList;

	private DeptoFinder finder;

	private SubdeptoFinder subdeptoFinder;

	private Subdepartamento subdepto;

	@Autowired
	private SubdeptoDataMapper subdeptoDataMapper;

	@Autowired
	public DeptoFormView(DeptoDataMapper dataMapper, DeptoFinder finder, SubdeptoFinder subdeptoFinder) {
		try {
			setSubdeptoFinder(subdeptoFinder);
			setDataMapper(dataMapper);
			setFinder(finder);
			loadInitialList();
		} catch (Exception e) {
			String msg = "Erro ao iniciar a tela";
			JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, msg);
			logger.error(msg, e);
		}
	}

	public void loadInitialList() {
		try {
			setDtList(getFinder().findAll("codigo"));
			setE(getDtList().get(0));
			// touch no lazy
			getE().getSubdeptos().size();
			getSubdeptoFinder().findSubdeptosByDepto(getE());
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public SubdeptoDataMapper getSubdeptoDataMapper() {
		return subdeptoDataMapper;
	}

	public void setSubdeptoDataMapper(SubdeptoDataMapper subdeptoDataMapper) {
		this.subdeptoDataMapper = subdeptoDataMapper;
	}

	public List<Departamento> getDtList() {
		return dtList;
	}

	public void setDtList(List<Departamento> dtList) {
		this.dtList = dtList;
	}

	public SubdeptoFinder getSubdeptoFinder() {
		return subdeptoFinder;
	}

	public void setSubdeptoFinder(SubdeptoFinder subdeptoFinder) {
		this.subdeptoFinder = subdeptoFinder;
	}

	public DeptoFinder getFinder() {
		return finder;
	}

	public void setFinder(DeptoFinder finder) {
		this.finder = finder;
	}

	@Override
	public void afterSave() {
		loadInitialList();
		RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam("saved", true);
		context.addCallbackParam("dlgId", "dlgCadastro");
		//context.addCallbackParam("postFunction", "executandoDepois");
		//context.addCallbackParam("postFunctionParams", "1,'carlos'");
		JSFUtils.addInfoMsg(JSFUtils.MSG_REGISTRO_SALVO);
	}

	@Override
	public void deletar() {
		try {
			getDataMapper().delete(getE());
			loadInitialList();
		} catch (ViewException e) {
			JSFUtils.addErrorMsg("Não foi possível deletar o registro");
		}
	}

	public void atualizar() {
		loadInitialList();
	}

	public void onRowSelect(SelectEvent event) {
		try {
			Departamento depto = getFinder().refresh((Departamento) event.getObject());
			// touch no lazy
			depto.getSubdeptos().size();
			setE(depto);
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public Subdepartamento getSubdepto() {
		if (subdepto == null) {
			subdepto = new Subdepartamento();
		}
		return subdepto;
	}

	public void setSubdepto(Subdepartamento subdepartamento) {
		subdepto = subdepartamento;
	}

	public void novoSubdepto() {
		setSubdepto(new Subdepartamento());
	}

	public void saveSubdepto() {
		try {
			Departamento depto = getFinder().refresh(getE());
			getSubdepto().setDepto(depto);
			subdeptoDataMapper.save(getSubdepto());
			final RequestContext context = RequestContext.getCurrentInstance();
			context.addCallbackParam("saved", true);
			context.addCallbackParam("dlgId", "dlgCadastroSubdepto");
			JSFUtils.addInfoMsg(JSFUtils.MSG_REGISTRO_SALVO);

			depto = getFinder().refresh(getE());
			setE(depto);
			//loadInitialList();
		} catch (Exception e) {
			JSFUtils.addErrorMsg(JSFUtils.MSG_ERRO_AO_SALVAR);
			logger.error("Erro no save():");
			logger.error(e.getMessage());
		}
	}

	public void deletarSubdepto(Subdepartamento subdepto) {
		try {
			setSubdepto(subdepto);
			subdeptoDataMapper.delete(getSubdepto());
		} catch (Exception e) {
			logger.error(e);
			JSFUtils.addErrorMsgErroAoDeletar();
		}
	}

}
