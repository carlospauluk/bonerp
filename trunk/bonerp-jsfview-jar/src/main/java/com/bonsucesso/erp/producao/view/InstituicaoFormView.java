package com.bonsucesso.erp.producao.view;



import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.crm.data.ClienteFinder;
import com.bonsucesso.erp.crm.model.Cliente;
import com.bonsucesso.erp.estoque.data.FornecedorFinder;
import com.bonsucesso.erp.estoque.model.Fornecedor;
import com.bonsucesso.erp.producao.data.InstituicaoDataMapper;
import com.bonsucesso.erp.producao.data.InstituicaoFilterFinder;
import com.bonsucesso.erp.producao.data.InstituicaoFinder;
import com.bonsucesso.erp.producao.model.Instituicao;
import com.ocabit.base.data.filter.FilterData;
import com.ocabit.base.data.filter.FilterType;
import com.ocabit.base.view.AbstractEntityFormListFilterView;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;


/**
 * View para a entidade Instituicao.
 *
 * @author Carlos Eduardo Pauluk
 */
@Component("instituicaoFormView")
@Scope("view")
public class InstituicaoFormView
		extends
		AbstractEntityFormListFilterView<Instituicao, InstituicaoDataMapper, InstituicaoFinder, InstituicaoFilterFinder> {

	/**
	 *
	 */
	private static final long serialVersionUID = -8545775754929956975L;

	protected static Logger logger = Logger.getLogger(InstituicaoFormView.class);

	private boolean criarVincularCliente = false;

	private boolean criarVincularFornecedor = false;

	@Autowired
	private ClienteFinder clienteFinder;

	@Autowired
	private FornecedorFinder fornecedorFinder;

	@PostConstruct
	public void init() {
		JSFUtils.execute("loadList()");
	}

	public boolean isCriarVincularCliente() {
		return criarVincularCliente;
	}

	public void setCriarVincularCliente(boolean criarVincularClienteNoSalvar) {
		criarVincularCliente = criarVincularClienteNoSalvar;
	}

	public boolean isCriarVincularFornecedor() {
		return criarVincularFornecedor;
	}

	public void setCriarVincularFornecedor(boolean criarVincularFornecedorNoSalvar) {
		criarVincularFornecedor = criarVincularFornecedorNoSalvar;
	}

	@Override
	public void validate() throws ViewException {
		if (isCriarVincularCliente()) {
			if (getE().getCliente() == null) {
				Cliente cliente = new Cliente();
				cliente.getPessoa().setNome(getE().getNome());
				cliente.setObs("Instituição: " + getE().getCodigo() + " - " + getE().getNome());
				getE().setCliente(cliente);
			}
		} else if (getE().getCliente() != null) {
			// se desmarcou a vinculação, deve remover o link
			getE().setCliente(null);
		}

		if (isCriarVincularFornecedor()) {
			if (getE().getFornecedor() == null) {
				Fornecedor fornecedor = new Fornecedor();
				fornecedor.getPessoa().setNomeFantasia(getE().getNome());
				fornecedor.setObs("Instituição: " + getE().getCodigo() + " - " + getE().getNome());
				getE().setFornecedor(fornecedor);
			}
		} else if (getE().getFornecedor() != null) {
			// se desmarcou a vinculação, deve remover o link
			getE().setFornecedor(null);
		}
	}

	@Override
	public void afterSetE(Instituicao e) {
		setCriarVincularCliente(getE().getCliente() != null);
		setCriarVincularFornecedor(getE().getFornecedor() != null);
	}

	public ClienteFinder getClienteFinder() {
		return clienteFinder;
	}

	public void setClienteFinder(ClienteFinder clienteFinder) {
		this.clienteFinder = clienteFinder;
	}

	public FornecedorFinder getFornecedorFinder() {
		return fornecedorFinder;
	}

	public void setFornecedorFinder(FornecedorFinder fornecedorFinder) {
		this.fornecedorFinder = fornecedorFinder;
	}

	@Override
	public void handleFilterDatas() {
		getFilterDatas().add(new FilterData("strPesquisa", FilterType.LIKE_ANYWHERE, "nome"));
	}

}
