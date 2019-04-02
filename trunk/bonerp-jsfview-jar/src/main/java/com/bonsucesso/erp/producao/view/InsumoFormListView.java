package com.bonsucesso.erp.producao.view;



import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.estoque.data.UnidadeProdutoFinder;
import com.bonsucesso.erp.producao.data.ConfeccaoFinder;
import com.bonsucesso.erp.producao.data.InsumoDataMapper;
import com.bonsucesso.erp.producao.data.InsumoFilterFinder;
import com.bonsucesso.erp.producao.data.InsumoFinder;
import com.bonsucesso.erp.producao.model.Confeccao;
import com.bonsucesso.erp.producao.model.Insumo;
import com.bonsucesso.erp.producao.model.InsumoPreco;
import com.bonsucesso.erp.producao.model.TipoInsumo;
import com.ocabit.base.data.filter.FilterData;
import com.ocabit.base.data.filter.FilterType;
import com.ocabit.base.view.AbstractEntityFormListView;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;


@Component("insumoFormListView")
@Scope("view")
public class InsumoFormListView extends
		AbstractEntityFormListView<Insumo, InsumoDataMapper, InsumoFinder> {

	/**
	 *
	 */
	private static final long serialVersionUID = 2929106561666395655L;

	protected static Logger logger = Logger.getLogger(InsumoFormListView.class);

	private InsumoPreco preco;

	@Autowired
	private UnidadeProdutoFinder unidadeProdutoFinder;

	@Autowired
	private ConfeccaoFinder confeccaoFinder;

	@Autowired
	private InsumoFilterFinder filterFinder;

	// Confeccções que utilizam o insumo...
	private List<Confeccao> confeccoesRefInsumo;

	@PostConstruct
	public void init() {
		JSFUtils.execute("loadList()");
	}

	public ConfeccaoFinder getConfeccaoFinder() {
		return confeccaoFinder;
	}

	public void setConfeccaoFinder(ConfeccaoFinder confeccaoFinder) {
		this.confeccaoFinder = confeccaoFinder;
	}

	public UnidadeProdutoFinder getUnidadeProdutoFinder() {
		return unidadeProdutoFinder;
	}

	public void setUnidadeProdutoFinder(UnidadeProdutoFinder unidadeProdutoFinder) {
		this.unidadeProdutoFinder = unidadeProdutoFinder;
	}

	public InsumoPreco getPreco() {
		if (preco == null) {
			preco = new InsumoPreco();
		}
		return preco;
	}

	public void setPreco(InsumoPreco preco) {
		this.preco = preco;
	}

	@Override
	public Insumo newE() {
		Insumo insumo = new Insumo();
		try {
			insumo.setUnidadeProduto(getUnidadeProdutoFinder().findByLabel("UN"));
		} catch (ViewException e) {
			JSFUtils.addErrorMsg("Erro ao setar a unidade");
			logger.error(e);
		}
		InsumoPreco insumoPreco = new InsumoPreco();
		insumoPreco.setInsumo(insumo);
		insumoPreco.setDtCusto(new Date());
		setPreco(insumoPreco);
		return insumo;
	}

	public List<Confeccao> acConfeccao(String str) {
		try {
			return getConfeccaoFinder().findBy(str);
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
			return null;
		}
	}

	/**
	 * Override: chama o método para salvar também com o preço.
	 *
	 * @return
	 */
	@Override
	public boolean save() {
		boolean result = false;
		final RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam("dlgId", getDlgId());
		try {
			validate();
			setE(getDataMapper().saveComPreco(getE(), getPreco()));
			afterSave();
			afterSetId();
			JSFUtils.addInfoMsg(JSFUtils.MSG_REGISTRO_SALVO);
			context.addCallbackParam("saved", true);
			result = true;
		} catch (final ViewException e) {
			JSFUtils.addHandledException(e);
			logger.error("Erro no save():", e);
		} catch (final Exception e) {
			JSFUtils.addErrorMsg(JSFUtils.MSG_ERRO_AO_SALVAR);
			logger.error("Erro no save():", e);
		}
		return result;
	}

	@Override
	public void afterSetE(Insumo e) {
		if (e.getId() != null) {
			setPreco(e.getPrecoAtual());
		}
	}

	@Override
	public void handleFilterDatas() {
		getFilterDatas()
				.add(new FilterData("strPesquisa", FilterType.LIKE_ANYWHERE, new String[] { "descricao",
						"tipoInsumo.descricao" }));

		getFilterDatas()
				.add(new FilterData("tipoInsumo", FilterType.EQUALS));

		getFilterDatas()
				.add(new FilterData(new String[] { "descricao" }, FilterType.LIKE_ANYWHERE));
	}

	@Override
	public InsumoFilterFinder getFilterFinder() {
		return filterFinder;
	}

	public void setFilterFinder(InsumoFilterFinder filterFinder) {
		this.filterFinder = filterFinder;
	}

	@Override
	public String getDlgId() {
		return "dlgInsumo";
	}

	public void imprimirRelatorio() {
		try {
			//		logger.debug("Chamando relatório: " + getTipoRelatorio());
			//		logger.debug("loteItensIds: " + StringUtils.join(loteItensIds, ","));
			//		logger.debug("tiposInsumosIds: " + StringUtils.join(tiposInsumosIds, ","));

			Map<String, Object> params = new HashMap<String, Object>();

			if (getFiltros().containsKey("tipoInsumo")) {
				TipoInsumo tipoInsumo = (TipoInsumo) getFiltros().get("tipoInsumo");
				params.put("tiposInsumosIds", tipoInsumo.getId().toString());
			} else {
				ListMaker lmProducao = (ListMaker) getDataMapper().getBeanFactory().getBean("lmProducao");
				List<String> tiposInsumosIds = new ArrayList<String>();
				for (TipoInsumo t : lmProducao.getTiposInsumo()) {
					tiposInsumosIds.add(t.getId().toString());
				}
				params.put("tiposInsumosIds", StringUtils.join(tiposInsumosIds, ","));
			}

			params.put("descricao", getFiltros().containsKey("descricao")
					? "%" + getFiltros().get("descricao") + "%" : "%");

			String orderBy = "";

			if (getOrderByParam() != null && getOrderByParam().getField() != null) {

				switch (getOrderByParam().getField()) {
					case "#{_.codigo}":
						orderBy = "i.codigo";
						break;
					case "#{_.tipoInsumo.descricao}":
						orderBy = "tipo.descricao";
						break;
					case "#{_.descricao}":
						orderBy = "i.descricao";
						break;
					case "#{_.precoAtual.precoCusto}":
						orderBy = "preco.preco_custo";
						break;
					case "#{_.precoAtual.dtCusto}":
						orderBy = "preco.dt_custo";
						break;
				}
				params.put("orderBy", orderBy + " " + getOrderByParam().getOrderByType());
			}


			getReportBuilder().imprimir(params, "producao/rpInsumos", "Insumos");
		} catch (

		ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public void updateConfeccoesRefInsumo(Insumo insumo) {
		try {
			setE(insumo);
			setConfeccoesRefInsumo(getConfeccaoFinder().findBy(getE()));
		} catch (ViewException e) {
			JSFUtils.addHandledException(e);
		}
	}

	public List<Confeccao> getConfeccoesRefInsumo() {
		return confeccoesRefInsumo;
	}

	public void setConfeccoesRefInsumo(List<Confeccao> confeccoesRefInsumo) {
		this.confeccoesRefInsumo = confeccoesRefInsumo;
	}

}
