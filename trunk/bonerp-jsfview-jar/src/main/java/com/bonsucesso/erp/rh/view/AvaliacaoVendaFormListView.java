package com.bonsucesso.erp.rh.view;



import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.rh.data.AvaliacaoVendaDataMapper;
import com.bonsucesso.erp.rh.data.AvaliacaoVendaFilterFinder;
import com.bonsucesso.erp.rh.data.AvaliacaoVendaFinder;
import com.bonsucesso.erp.rh.model.AvaliacaoVenda;
import com.bonsucesso.erp.rh.model.AvaliacaoVendaQuestao;
import com.ocabit.base.data.filter.FilterData;
import com.ocabit.base.data.filter.FilterType;
import com.ocabit.base.view.AbstractEntityFormListView;
import com.ocabit.jsf.utils.JSFUtils;


/**
 * ListView para a entidade AvaliacaoVenda.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("avaliacaoVendaFormListView")
@Scope("view")
public class AvaliacaoVendaFormListView extends
		AbstractEntityFormListView<AvaliacaoVenda, AvaliacaoVendaDataMapper, AvaliacaoVendaFinder> {

	/**
	 *
	 */
	private static final long serialVersionUID = 3552693107001465553L;

	protected static Logger logger = Logger.getLogger(AvaliacaoVendaFormListView.class);

	@Autowired
	private AvaliacaoVendaFilterFinder filterFinder;

	private AvaliacaoVendaQuestao questao;

	@PostConstruct
	public void init() {
		JSFUtils.execute("loadList()");
	}

	@Override
	public AvaliacaoVendaFilterFinder getFilterFinder() {
		return filterFinder;
	}

	public void setFilterFinder(AvaliacaoVendaFilterFinder filterFinder) {
		this.filterFinder = filterFinder;
	}

	@Override
	public void handleFilterDatas() {
		getFilterDatas().add(new FilterData("strPesquisa", FilterType.LIKE_ANYWHERE, "descricao"));
	}

	@Override
	public void afterSave() {
		// Utilizado somente quando o form é para o dialog
		RequestContext context = RequestContext.getCurrentInstance();
		String dlgId = "dlgAvaliacaoVenda";
		context.addCallbackParam("dlgId", dlgId);
		JSFUtils.execute("loadList()");
	}

	public AvaliacaoVendaQuestao getQuestao() {
		if (questao == null) {
			questao = new AvaliacaoVendaQuestao();
		}
		return questao;
	}

	public void setQuestao(AvaliacaoVendaQuestao questao) {
		this.questao = questao;
	}

	public void novoQuestao() {
		AvaliacaoVendaQuestao questao = new AvaliacaoVendaQuestao();
		questao.setAvaliacaoVenda(getE());
		setQuestao(questao);
	}

	public void saveQuestao() {
		try {
			if (getQuestao().getId() == null) {
				getE().getQuestoes().add(getQuestao());
			}
			RequestContext context = RequestContext.getCurrentInstance();
			if (save()) {
				JSFUtils.execute("loadList()");
			}
			// troca o dlgId
			context.addCallbackParam("dlgId", "dlgQuestao");
		} catch (Exception e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao salvar o questao");
		}
	}

	public void deleteQuestao(AvaliacaoVendaQuestao questao) {
		try {
			setQuestao(questao);
			getE().getQuestoes().remove(getQuestao());
			save();
			novoQuestao();
			JSFUtils.addInfoMsgRegistroDeletado();
		} catch (Exception e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao deletar");
		}
	}

}
