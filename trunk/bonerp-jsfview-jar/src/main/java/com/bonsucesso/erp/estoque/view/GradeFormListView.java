package com.bonsucesso.erp.estoque.view;



import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.estoque.data.GradeDataMapper;
import com.bonsucesso.erp.estoque.data.GradeFinder;
import com.bonsucesso.erp.estoque.data.GradeTamanhoDataMapper;
import com.bonsucesso.erp.estoque.model.Grade;
import com.bonsucesso.erp.estoque.model.GradeTamanho;
import com.ocabit.base.view.AbstractEntityFormListView;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;


/**
 * View para a entidade Grade.
 *
 * @author Carlos Eduardo Pauluk
 */
@Component("gradeFormListView")
@Scope("view")
public class GradeFormListView extends AbstractEntityFormListView<Grade, GradeDataMapper, GradeFinder> {

	private static final long serialVersionUID = 1L;

	protected static Logger logger = Logger.getLogger(GradeFormListView.class);

	private GradeTamanho tamanho;

	@Autowired
	private GradeTamanhoDataMapper gradeTamanhoDataMapper;

	@Override
	public void loadInitialList() {
		try {
			setList(getFinder().findAll("codigo"));
			if (getE() == null || getE().getId() == null) {
				setE(getList().get(0));
			}
			getE().getTamanhos().size();
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	@Override
	public String getDlgId() {
		return "dlgGrade";
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
			Grade grade = getFinder().refresh((Grade) event.getObject());
			// touch no lazy
			grade.getTamanhos().size();
			setE(grade);
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public GradeTamanho getTamanho() {
		if (tamanho == null) {
			tamanho = new GradeTamanho();
		}
		return tamanho;
	}

	public void setTamanho(GradeTamanho tamanho) {
		if (tamanho.getId() != null) {
			setE(tamanho.getGrade());
		}
		this.tamanho = tamanho;
	}

	public void novoTamanho() {
		setTamanho(new GradeTamanho());
	}

	public void saveTamanho() {
		final RequestContext context = RequestContext.getCurrentInstance();
		try {
			refreshE();
			getTamanho().setGrade(getE());
			setTamanho(getGradeTamanhoDataMapper().save(getTamanho()));
			refreshE();
			context.addCallbackParam("saved", "true");
		} catch (Exception e) {
			JSFUtils.addErrorMsg(JSFUtils.MSG_ERRO_AO_SALVAR);
			logger.error("Erro no save():");
			logger.error(e.getMessage());
		}
		context.addCallbackParam("dlgId", "dlgTamanho");
	}

	public void deletarTamanho(GradeTamanho gradeTamanho) {
		try {
			setTamanho(gradeTamanho);
			getE().getTamanhos().remove(getTamanho());
			save();
			JSFUtils.addInfoMsgRegistroDeletado();
		} catch (Exception e) {
			logger.error(e);
			JSFUtils.addErrorMsgErroAoDeletar();
		}
	}

	public void updateObs() {
		if ((getE() != null) && ((getE().getObs() == null) || "".equals(getE().getObs()))) {
			StringBuilder tams = new StringBuilder("");
			for (GradeTamanho gt : getE().getTamanhos()) {
				tams.append(gt.getTamanho()).append(" - ");
			}
			tams.replace(tams.length() - 3, tams.length(), "");
			getE().setObs(tams.toString());
		}
	}

	public GradeTamanhoDataMapper getGradeTamanhoDataMapper() {
		return gradeTamanhoDataMapper;
	}

	public void setGradeTamanhoDataMapper(GradeTamanhoDataMapper gradeTamanhoDataMapper) {
		this.gradeTamanhoDataMapper = gradeTamanhoDataMapper;
	}

}
