package com.bonsucesso.erp.financeiro.view;



import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.financeiro.data.GrupoDataMapper;
import com.bonsucesso.erp.financeiro.data.GrupoFinder;
import com.bonsucesso.erp.financeiro.data.GrupoItemDataMapper;
import com.bonsucesso.erp.financeiro.model.Grupo;
import com.bonsucesso.erp.financeiro.model.GrupoItem;
import com.ocabit.base.view.AbstractEntityFormListView;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;


/**
 * View para as entidades Grupo e GrupoItem.
 *
 * @author Carlos Eduardo Pauluk
 */
@Component("grupoFormListView")
@Scope("view")
public final class GrupoFormListView extends
		AbstractEntityFormListView<Grupo, GrupoDataMapper, GrupoFinder> {

	/**
	 *
	 */
	private static final long serialVersionUID = -6571735346010041382L;

	protected static Logger logger = Logger.getLogger(GrupoFormListView.class);

	@Autowired
	private GrupoItemDataMapper grupoItemDataMapper;

	@Autowired
	private ListMaker lmFinanc;

	private GrupoItem grupoItem;

	private boolean somenteAtivos = true;

	/**
	 * Carrega a listagem inicial.
	 */
	@Override
	public void loadInitialList() {
		try {
			setList(getFinder().findAll("descricao"));
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public GrupoItemDataMapper getGrupoItemDataMapper() {
		return grupoItemDataMapper;
	}

	public void setGrupoItemDataMapper(GrupoItemDataMapper grupoItemDataMapper) {
		this.grupoItemDataMapper = grupoItemDataMapper;
	}

	public ListMaker getLmFinanc() {
		return lmFinanc;
	}

	public void setLmFinanc(ListMaker lmFinanc) {
		this.lmFinanc = lmFinanc;
	}

	public boolean isSomenteAtivos() {
		return somenteAtivos;
	}

	public void setSomenteAtivos(boolean somenteAtivos) {
		this.somenteAtivos = somenteAtivos;
	}

	@Override
	public String getDlgId() {
		return "dlgGrupo";
	}

	@Override
	public void afterSave() {
		getLmFinanc().setGrupoAtivoValues(null);
		getLmFinanc().setGrupoValues(null);
	}

	public GrupoItem getGrupoItem() {
		return grupoItem;
	}

	public void setGrupoItem(GrupoItem grupoItem) {
		setE(grupoItem.getPai());
		grupoItem = getE().getItens().get(getE().getItens().indexOf(grupoItem));
		this.grupoItem = grupoItem;
	}

	public void gerarProximoGrupoItem() {
		try {
			refreshE();
			setE(getDataMapper().gerarProximo(getE()).getPai());
			save();
		} catch (ViewException e) {
			JSFUtils.addErrorMsg("Erro ao gerar o próximo Item do Grupo.");
			logger.error(e);
		}
		// logger.info("Salvo >> " + getE().getItens().size());
	}

	public void updateGrupo() {

	}

	public void deletarGrupoItem(GrupoItem grupoItem) {
		try {
			if ((grupoItem.getProximo() != null) && (grupoItem.getProximo().getId() != null)) {
				throw new ViewException("Não é possível excluir um item intermediário.");
			}
			if (grupoItem.getAnterior() != null) {
				grupoItem.getAnterior().setProximo(null);
			}
			getE().getItens().remove(grupoItem);
			save();
		} catch (ViewException e) {
			JSFUtils.addHandledException(e);
			JSFUtils.addErrorMsg("Erro ao deletar o Item do Grupo.");
			logger.error(e);
		} catch (Exception e) {
			JSFUtils.addErrorMsg("Erro ao deletar o Item do Grupo.");
			logger.error(e);
		}
	}

	public void saveGrupoItem() {
		try {
			save();
			JSFUtils.addCallbackParam("dlgId", "dlgGrupoItem");
		} catch (Exception e) {
			JSFUtils.addErrorMsg("Erro ao salvar o Item do Grupo.");
			logger.error(e);
		}
	}

	public void gerarDescricaoGrupoItem() {
		try {
			getGrupoItem().setDescricao(getDataMapper().gerarDescricao(getGrupoItem()));
		} catch (Exception e) {
			JSFUtils.addErrorMsg("Erro ao gerar descrição para o Item do Grupo.");
			logger.error(e);
		}
	}

}
