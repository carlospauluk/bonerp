package com.bonsucesso.erp.financeiro.view;



import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.financeiro.data.GrupoItemFinder;
import com.bonsucesso.erp.financeiro.model.GrupoItem;
import com.bonsucesso.erp.financeiro.model.Movimentacao;
import com.ocabit.base.data.filter.FilterData;
import com.ocabit.base.data.filter.FilterDataHandler;
import com.ocabit.base.data.filter.FilterType;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;


@Component("movimentacaoExtratoGrupoListView")
@Scope("view")
public class MovimentacaoExtratoGrupoListView extends MovimentacaoListView {

	/**
	 *
	 */
	private static final long serialVersionUID = 4564790901643754267L;

	protected static Logger logger = Logger.getLogger(MovimentacaoExtratoGrupoListView.class);

	@Autowired
	private ListMaker lmFinanc;

	@Autowired
	private GrupoItemFinder grupoItemFinder;

	private GrupoItem grupoItem;

	private Long grupoItemId;

	@Override
	public void processStoredFilterData() {
		super.processStoredFilterData();
		if (getFiltros().containsKey("grupoItem")) {
			GrupoItem grupoItem = (GrupoItem) getFiltros().get("grupoItem");
			// setando no lmFinanc para o select na tela ficar correto
			getLmFinanc().setGrupo(grupoItem.getPai());
		}
	}

	public void updateGrupo() {
		try {
			GrupoItem grupoItem = getGrupoItemFinder().findBy(getLmFinanc().getGrupo(), new Date());
			if (grupoItem == null) {
				grupoItem = getGrupoItemFinder().findUltimoAnterior(getLmFinanc().getGrupo(), new Date());
				if (grupoItem == null) {
					grupoItem = getLmFinanc().getGrupo().getItens().get(0);
				}
			}
			getFiltros().put("grupoItem", grupoItem);

			updateGrupoItem(0);
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao pesquisar Item de Grupo atual");
		}
	}

	@Override
	public void totalizar() {
		if (getFiltros().containsKey("grupoItem")) {
			try {
				GrupoItem grupoItem = (GrupoItem) getFiltros().get("grupoItem");
				getTotalizacoes().put("TOTAL_MOVIMENTADO", getFinder().findSaldo(grupoItem));
			} catch (Exception e) {
				logger.error(e);
				JSFUtils.addErrorMsg("Erro ao totalizar grupo.");
			}
		}
	}

	@Override
	public void loadInitialList() {
		// não faz nada. Evita carregar tudo.
	}

	public ListMaker getLmFinanc() {
		return lmFinanc;
	}

	public void setLmFinanc(ListMaker lmFinanc) {
		this.lmFinanc = lmFinanc;
	}

	/**
	 * Para não limitar por registros quando a listagem é de extrato.
	 */
	@Override
	public Integer getQtdeRegistros() {
		return null;
	}

	@Override
	public void handleFilterDatas() {
		getFilterDatas().add(new FilterData("grupoItem", FilterType.EQUALS));
	}

	public void updateGrupoItem(Integer i) {
		GrupoItem grupoItem = (GrupoItem) getFiltros().get("grupoItem");

		if ((i != null) && (i != 0)) {
			if (i > 0) {
				if (grupoItem.getProximo() != null) {
					getFiltros().put("grupoItem", grupoItem.getProximo());
				} else {
					JSFUtils.addWarnMsg("Nenhum item de grupo anterior a este.");
				}
			} else {
				if (grupoItem.getAnterior() != null) {
					getFiltros().put("grupoItem", grupoItem.getAnterior());
				} else {
					JSFUtils.addWarnMsg("Nenhum item de grupo posterior a este.");
				}
			}
		}

		try {
			FacesContext
					.getCurrentInstance()
					.getExternalContext()
					.redirect("movimentacaoList_extratoGrupo.jsf?grupoItemId="
							+ ((GrupoItem) getFiltros().get("grupoItem")).getId());
		} catch (IOException e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao redirecionar página.");
		}

		// JSFUtils.execute("loadList()");
	}

	public GrupoItemFinder getGrupoItemFinder() {
		return grupoItemFinder;
	}

	public void setGrupoItemFinder(GrupoItemFinder grupoItemFinder) {
		this.grupoItemFinder = grupoItemFinder;
	}

	public Long getGrupoItemId() {
		return grupoItemId;
	}

	public void setGrupoItemId(Long grupoItemId) {
		try {
			processStoredFilterData();

			GrupoItem grupoItem = getGrupoItemFinder().findById(grupoItemId);
			getLmFinanc().setGrupo(grupoItem.getPai());
			getFiltros().put("grupoItem", grupoItem);

			FilterDataHandler.handleList(getFiltros(), getFilterDatas());
			saveDatatable(); // mando salvar com o grupoItem alterado para o que veio na queryString para quando o loadList() for chamado pelo remoteCommand ele não substitua pelo grupoItem salvo na base

			// pesquisar();
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao carregar o grupo item inicial.");
		}
		this.grupoItemId = grupoItemId;
	}

	public GrupoItem getGrupoItem() {
		if (getFiltros() != null && getFiltros().containsKey("grupoItem")) {
			try {
				this.grupoItem = getGrupoItemFinder().refresh((GrupoItem) getFiltros().get("grupoItem"));
			} catch (ViewException e) {
				logger.error(e);
				JSFUtils.addHandledException(e);
			}
		}
		return this.grupoItem;
	}
	
	public final void imprimirMovimentacoes() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("titulo", ((GrupoItem) getFiltros().get("grupoItem")).getDescricao());

			List<Long> movimentacoesIds = new ArrayList<Long>();
			for (Object m : getList()) {
				movimentacoesIds.add(((Movimentacao) m).getId());
			}
			

			BigDecimal total = getMovimentacaoBusiness().totalizarDtList(getList(), null);
			
			logger.debug("movimentacoesIds: " + StringUtils.join(movimentacoesIds, ","));

			params.put("totalMovimentacoes", total.doubleValue());
			// params.put("movimentacoesIds", StringUtils.join(movimentacoesIds, ","));
			params.put("movimentacoesIds", movimentacoesIds);
			getReportBuilder().imprimir(params, "financeiro/rpMovimentacoes", "Movimentacoes");
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

}
