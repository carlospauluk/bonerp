package com.bonsucesso.erp.estoque.view;



import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.base.model.Endereco;
import com.bonsucesso.erp.base.model.TipoPessoa;
import com.bonsucesso.erp.estoque.data.FornecedorDataMapper;
import com.bonsucesso.erp.estoque.data.FornecedorFilterFinder;
import com.bonsucesso.erp.estoque.data.FornecedorFinder;
import com.bonsucesso.erp.estoque.model.Fornecedor;
import com.ocabit.base.data.FilterEntityFinder;
import com.ocabit.base.data.filter.FilterData;
import com.ocabit.base.data.filter.FilterType;
import com.ocabit.base.view.AbstractEntityFormListView;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;


@Component("fornecedorFormListView")
@Scope("view")
public class FornecedorFormListView extends
		AbstractEntityFormListView<Fornecedor, FornecedorDataMapper, FornecedorFinder> {

	/**
	 *
	 */
	private static final long serialVersionUID = 3552693107001465553L;

	protected static Logger logger = Logger.getLogger(FornecedorFormListView.class);

	@Autowired
	private FornecedorFilterFinder filterFinder;

	private Endereco endereco;

	private boolean salvandoEndereco;

	/**
	 * Informa se é para efetuar, na tela, cadastro de somente um tipo.
	 */
	private TipoPessoa cadastrarSomente;

	@PostConstruct
	public void init() {
		JSFUtils.execute("loadList()");
	}

	public void setFilterFinder(FornecedorFilterFinder filterFinder) {
		this.filterFinder = filterFinder;
	}

	@Override
	protected FilterEntityFinder<Fornecedor> getFilterFinder() {
		return filterFinder;
	}

	@Override
	protected void handleFilterDatas() {
		boolean procurandoPorCodigo = false;
		try {
			Integer codigo = Integer.parseInt((String) getFiltros().get("strPesquisa"));
			if (codigo < 10000) {
				getFilterDatas().add(new FilterData("strPesquisa", FilterType.EQUALS, "codigo"));
				getFiltros().put("strPesquisa", codigo);
				procurandoPorCodigo = true;
			}
		} catch (NumberFormatException e) {
			logger.info("Não comparar com código");
		}
		if (!procurandoPorCodigo) {
			getFilterDatas()
					.add(new FilterData("strPesquisa", FilterType.LIKE_ANYWHERE, "pessoa.documento", "pessoa.nomeFantasia", "pessoa.nome", "representante"));
		}
	}

	public void updateDoc() {
		try {
			// Se o documento do setId for diferente do inputado (mudou o CPF/CNPJ)
			if (getE().getPessoa().getDocumento() != null) {
				Fornecedor fornecedor = getFinder().findByDoc(getE().getPessoa().getDocumento());
				if (fornecedor != null) { // não achou Fornecedor...
					setE(fornecedor);
					JSFUtils.addWarnMsg("Fornecedor já existente.");
				}
			}
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public Endereco getEndereco() {
		if (endereco == null) {
			endereco = new Endereco();
		}
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
		setSalvandoEndereco(true);
	}

	public void novoEndereco() {
		setEndereco(new Endereco());
		getE().getEnderecos().add(getEndereco());
		setSalvandoEndereco(true);
	}

	public boolean isSalvandoEndereco() {
		return salvandoEndereco;
	}

	public void setSalvandoEndereco(boolean salvandoEndereco) {
		this.salvandoEndereco = salvandoEndereco;
	}

	@Override
	public String getDlgId() {
		return "dlgFornecedor";
	}

	public void updateCodigo(Fornecedor fornecedor) {
		try {
			// Se o documento do setId for diferente do inputado (mudou o CPF/CNPJ)
			if (fornecedor.getCodigo() != null) {
				fornecedor = getFinder().findByCodigo(fornecedor.getCodigo());
			}
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public TipoPessoa getCadastrarSomente() {
		return cadastrarSomente;
	}

	public void setCadastrarSomente(TipoPessoa cadastrarSomente) {
		this.cadastrarSomente = cadastrarSomente;
	}

}
