package com.bonsucesso.erp.cortinas.view;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.cortinas.data.ArtigoCortinaDataMapper;
import com.bonsucesso.erp.cortinas.data.ArtigoCortinaFilterFinder;
import com.bonsucesso.erp.cortinas.data.ArtigoCortinaFinder;
import com.bonsucesso.erp.cortinas.model.ArtigoCortina;
import com.bonsucesso.erp.cortinas.model.TipoArtigoCortina;
import com.bonsucesso.erp.estoque.business.ProdutoBusiness;
import com.bonsucesso.erp.estoque.data.DeptoFinder;
import com.bonsucesso.erp.estoque.model.Departamento;
import com.bonsucesso.erp.estoque.model.Produto;
import com.ocabit.base.data.filter.FilterData;
import com.ocabit.base.data.filter.FilterType;
import com.ocabit.base.view.AbstractEntityListView;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;


@Component("artigoCortinaListView")
@Scope("view")
public class ArtigoCortinaListView extends
		AbstractEntityListView<ArtigoCortina, ArtigoCortinaDataMapper, ArtigoCortinaFinder> {

	/**
	 *
	 */
	private static final long serialVersionUID = 2929106561666395655L;

	protected static Logger logger = Logger.getLogger(ArtigoCortinaListView.class);

	private List<ArtigoCortina> selecteds;

	@Autowired
	private DeptoFinder deptoFinder;

	@Autowired
	private ArtigoCortinaFilterFinder filterFinder;

	@Autowired
	private ProdutoBusiness produtoBusiness;

	@PostConstruct
	public void init() {
		getFiltros().put("tipoArtigoCortina", new ArrayList<TipoArtigoCortina>());
		JSFUtils.execute("loadList()");

		try {

			if (1 > 2) {
				Query qry = getDataMapper().getEntityManager()
						.createQuery("FROM Produto p WHERE p.subdepto.codigo IN :subdeptos");
				qry.setParameter("subdeptos", Arrays.asList(951));
				List<Produto> prods = qry.getResultList();
				for (Produto p : prods) {
					if (getFinder().findByProduto(p) == null) {
						ArtigoCortina ac = new ArtigoCortina();
						ac.setProduto(p);
						ac.setTipoArtigoCortina(TipoArtigoCortina.TECIDO);
						ac = getDataMapper().save(ac);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<ArtigoCortina> getSelecteds() {
		return selecteds;
	}

	@Override
	public void setSelecteds(List<ArtigoCortina> selecteds) {
		this.selecteds = selecteds;
	}

	@Override
	public ArtigoCortinaFilterFinder getFilterFinder() {
		return filterFinder;
	}

	public void setFilterFinder(ArtigoCortinaFilterFinder filterFinder) {
		this.filterFinder = filterFinder;
	}

	@Override
	public void handleFilterDatas() {

		getFilterDatas().add(new FilterData("produto.descricao", FilterType.LIKE_ANYWHERE));
		getFilterDatas().add(new FilterData("produto.reduzido", FilterType.LIKE_ANYWHERE_NUMERIC));
		getFilterDatas()
				.add(new FilterData("produto.fornecedor", FilterType.LIKE_ANYWHERE, "produto.fornecedor.pessoa.nomeFantasia", "produto.fornecedor.pessoa.nome"));

		getFilterDatas().add(new FilterData("produto.subdepto.depto", FilterType.EQUALS));
		getFilterDatas().add(new FilterData("produto.subdepto", FilterType.EQUALS));
		getFilterDatas().add(new FilterData("tipoArtigoCortina", FilterType.IN));

		getFilterDatas().add(new FilterData("produto.reduzidoEktAte", FilterType.IS_NULL));

		getFiltros().put("produto.reduzidoEktAte", true); // Só exibe os produtos atuais

		try {
			Integer reduzido = Integer.parseInt((String) getFiltros().get("strPesquisa"));
			getFilterDatas()
					.add(new FilterData("strPesquisa", FilterType.LIKE_ANYWHERE_NUMERIC, "produto.reduzido"));
			getFiltros().put("strPesquisa", reduzido);
		} catch (NumberFormatException e) {
			logger.info("Não comparar com reduzido");
		}

	}

	public DeptoFinder getDeptoFinder() {
		return deptoFinder;
	}

	public void setDeptoFinder(DeptoFinder deptoFinder) {
		this.deptoFinder = deptoFinder;
	}

	public void updateDepto() {
		if ((getFiltros() != null) && (getFiltros().get("produto.subdepto.depto") != null)) {
			try {
				Departamento depto = getDeptoFinder()
						.refresh((Departamento) getFiltros().get("produto.subdepto.depto"));
				depto.getSubdeptos().size();
				getFiltros().put("produto.subdepto.depto", depto);
			} catch (ViewException e) {
				JSFUtils.addErrorMsg("Erro ao atualizar depto/subdepto");
				logger.error(e);
			}
		}
	}

	@Override
	public void updatePesquisa() {
		updateDepto();
	}

	public ProdutoBusiness getProdutoBusiness() {
		return produtoBusiness;
	}

	public void setProdutoBusiness(ProdutoBusiness produtoBusiness) {
		this.produtoBusiness = produtoBusiness;
	}

}
