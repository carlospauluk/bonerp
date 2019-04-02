package com.bonsucesso.erp.rh.view;



import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.rh.data.AvaliacaoVendaFinder;
import com.bonsucesso.erp.rh.data.AvaliacaoVendaRespDataMapper;
import com.bonsucesso.erp.rh.data.AvaliacaoVendaRespFilterFinder;
import com.bonsucesso.erp.rh.data.AvaliacaoVendaRespFinder;
import com.bonsucesso.erp.rh.model.AvaliacaoVendaResp;
import com.bonsucesso.erp.rh.model.AvaliacaoVendaRespQuestao;
import com.ocabit.base.data.filter.FilterData;
import com.ocabit.base.data.filter.FilterType;
import com.ocabit.base.view.AbstractEntityFormListView;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;


/**
 * ListView para a entidade AvaliacaoVendaResp.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("avaliacaoVendaRespFormListView")
@Scope("view")
public class AvaliacaoVendaRespFormListView extends
		AbstractEntityFormListView<AvaliacaoVendaResp, AvaliacaoVendaRespDataMapper, AvaliacaoVendaRespFinder> {

	/**
	 *
	 */
	private static final long serialVersionUID = -1386525999362927941L;

	protected static Logger logger = Logger.getLogger(AvaliacaoVendaRespFormListView.class);

	@Autowired
	private AvaliacaoVendaRespFilterFinder filterFinder;

	@Autowired
	private AvaliacaoVendaFinder avaliacaoVendaFinder;

	private Map<Integer, String> respostas;

	@PostConstruct
	public void init() {
		JSFUtils.execute("loadList()");
	}

	@Override
	public AvaliacaoVendaRespFilterFinder getFilterFinder() {
		return filterFinder;
	}

	public void setFilterFinder(AvaliacaoVendaRespFilterFinder filterFinder) {
		this.filterFinder = filterFinder;
	}

	public AvaliacaoVendaFinder getAvaliacaoVendaFinder() {
		return avaliacaoVendaFinder;
	}

	@Override
	public void afterNovo() {
		getE().setDtAvaliacao(new Date());
		setRespostas(null);
	}

	public void setAvaliacaoVendaFinder(AvaliacaoVendaFinder avaliacaoVendaFinder) {
		this.avaliacaoVendaFinder = avaliacaoVendaFinder;
	}

	@Override
	public void validate() throws ViewException {
		if (getE().getId() == null) {
			getE().setRespostas(new ArrayList<AvaliacaoVendaRespQuestao>());
			for (Map.Entry<Integer, String> e : getRespostas().entrySet()) {
				AvaliacaoVendaRespQuestao resposta = new AvaliacaoVendaRespQuestao();
				resposta.setAvaliacaoVendaResp(getE());
				resposta.setAvaliacaoVendaQuestao(getAvaliacaoVendaFinder().findBy(getE().getAvaliacaoVenda(), e
						.getKey()));
				resposta.setResposta(e.getValue());
				getE().getRespostas().add(resposta);
			}
		} else {
			for (Map.Entry<Integer, String> e : getRespostas().entrySet()) {

				for (AvaliacaoVendaRespQuestao r : getE().getRespostas()) {
					if (r.getAvaliacaoVendaQuestao().getOrdem().equals(e.getKey())) {
						r.setResposta(e.getValue());
						break;
					}
				}

			}
		}

		super.validate();
	}

	@Override
	public void handleFilterDatas() {
		getFilterDatas().add(new FilterData("strPesquisa", FilterType.LIKE_ANYWHERE, "descricao"));
	}

	@Override
	public void afterSave() {
		// Utilizado somente quando o form é para o dialog
		RequestContext context = RequestContext.getCurrentInstance();
		String dlgId = "dlgAvaliacaoVendaResp";
		context.addCallbackParam("dlgId", dlgId);
		JSFUtils.execute("loadList()");
	}

	@Override
	public void afterNotSave() {
		try {
			getE().setAvaliacaoVenda(getAvaliacaoVendaFinder().refresh(getE().getAvaliacaoVenda()));
			getE().getAvaliacaoVenda().getQuestoes().size(); // touch para lazy
		} catch (ViewException e) {
			JSFUtils.addErrorMsg("Erro ao recarregar entidade.");
		}
	}

	public void updateAvaliacaoVenda() {
		try {
			getE().setAvaliacaoVenda(getAvaliacaoVendaFinder().refresh(getE().getAvaliacaoVenda()));
			getE().getAvaliacaoVenda().getQuestoes().size(); // touch para lazy
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public Map<Integer, String> getRespostas() {
		if (respostas == null) {
			respostas = new HashMap<Integer, String>();
		}
		return respostas;
	}

	public void setRespostas(Map<Integer, String> respostas) {
		this.respostas = respostas;
	}

	@Override
	public void afterSetE(AvaliacaoVendaResp e) {

		if ((e != null) && (e.getRespostas() != null)) {
			e.getAvaliacaoVenda().getQuestoes().size(); // touch para lazy
			e.getRespostas().size(); // touch para lazy
			setRespostas(null);
			for (AvaliacaoVendaRespQuestao questao : e.getRespostas()) {
				getRespostas().put(questao.getAvaliacaoVendaQuestao().getOrdem(), questao.getResposta());
			}
		}

	}

}
