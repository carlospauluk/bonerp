package com.bonsucesso.erp.crm.view;



import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.base.model.Endereco;
import com.bonsucesso.erp.base.model.TipoPessoa;
import com.bonsucesso.erp.crm.data.ClienteDataMapper;
import com.bonsucesso.erp.crm.data.ClienteFilterFinder;
import com.bonsucesso.erp.crm.data.ClienteFinder;
import com.bonsucesso.erp.crm.model.Canal;
import com.bonsucesso.erp.crm.model.Cliente;
import com.ocabit.base.data.filter.FilterData;
import com.ocabit.base.data.filter.FilterType;
import com.ocabit.base.data.filter.OrderByParam;
import com.ocabit.base.data.filter.OrderByType;
import com.ocabit.base.view.AbstractEntityFormListView;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;

import br.com.caelum.stella.format.CNPJFormatter;
import br.com.caelum.stella.format.CPFFormatter;
import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;


/**
 * ListView para a entidade Cliente.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("clienteFormListView")
@Scope("view")
public class ClienteFormListView extends AbstractEntityFormListView<Cliente, ClienteDataMapper, ClienteFinder> {

	/**
	 *
	 */
	private static final long serialVersionUID = 3552693107001465553L;

	protected static Logger logger = Logger.getLogger(ClienteFormListView.class);

	private Endereco endereco;

	private String documento;

	private boolean salvandoEndereco;

	@Autowired
	private ClienteFilterFinder filterFinder;

	/**
	 * Informa se é para efetuar, na tela, cadastro de somente um tipo.
	 */
	private TipoPessoa cadastrarSomente;

	@PostConstruct
	public void init() {
		JSFUtils.execute("loadList()");
		getFiltros().put("canais", new ArrayList<Canal>());
	}

	public void novo(String strCadastrarSomente) {
		setCadastrarSomente(TipoPessoa.valueOf(strCadastrarSomente));
		novo();
		getE().getPessoa().setTipoPessoa(getCadastrarSomente());
	}

	@Override
	public void afterSave() {
		// Utilizado somente quando o form é para o dialog
		RequestContext context = RequestContext.getCurrentInstance();
		String dlgId = "dlgForm";
		if (isSalvandoEndereco()) {
			dlgId = "dlgEndereco";
		}
		context.addCallbackParam("dlgId", dlgId);
		super.afterSave();
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	@Override
	public void afterSetE(Cliente e) {
		if ((e != null) && (e.getId() != null)) {
			setDocumento(e.getPessoa().getDocumento());
			e.getEnderecos().size(); // touch
			e.getCanais().size(); // touch
		} else {
			setDocumento(null);
		}
	}

	public void updateDoc() {
		Cliente cliente = null;

		try {
			// Se o documento do setId for diferente do inputado (mudou o CPF/CNPJ)
			if ((getE().getPessoa().getDocumento() != null) &&
					!getE().getPessoa().getDocumento().equals(getDocumento())) {
				cliente = getFinder().findClienteByDoc(getE().getPessoa().getDocumento());
				if (cliente != null) {
					setE(cliente);
				}

				if (getE().getPessoa().getTipoPessoa().equals(TipoPessoa.PESSOA_FISICA)) {
					try {
						new CPFValidator().assertValid(new CPFFormatter().format(getE().getPessoa().getDocumento()));
					} catch (Exception e) {
						JSFUtils.addWarnMsg("CPF inválido");
					}
				} else {
					try {
						new CNPJValidator().assertValid(new CNPJFormatter().format(getE().getPessoa().getDocumento()));
					} catch (Exception e) {
						JSFUtils.addWarnMsg("CNPJ inválido");
					}

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
	public void validate() throws ViewException {
		if ((getE().getEndereco() != null) && (getE().getEndereco().getTipoEndereco() != null)
				&& (getE().getEndereco().getId() == null)) {
			getE().getEnderecos().add(getE().getEndereco());
		}
	}

	public TipoPessoa getCadastrarSomente() {
		return cadastrarSomente;
	}

	public void setCadastrarSomente(TipoPessoa cadastrarSomente) {
		this.cadastrarSomente = cadastrarSomente;
	}

	@Override
	public ClienteFilterFinder getFilterFinder() {
		return filterFinder;
	}

	public void setFilterFinder(ClienteFilterFinder filterFinder) {
		this.filterFinder = filterFinder;
	}

	@Override
	public void handleFilterDatas() {
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
					.add(new FilterData("strPesquisa", FilterType.LIKE_ANYWHERE, "pessoa.nomeFantasia", "pessoa.nome", "pessoa.documento"));
		}

		getFilterDatas()
				.add(new FilterData("canais", FilterType.NONE_UM));

	}

	@Override
	public List<Cliente> doFindByFilters() throws ViewException {

		if (getFiltros().containsKey("canais") && getFiltros().get("canais").getClass().isArray() && ((Object[]) getFiltros().get("canais")).length > 0 ) {

			CriteriaBuilder cb = getFilterFinder().getCriteriaBuilder();

			Expression<List<Canal>> pCanais = getFilterFinder().getFrom().get("canais");

			List<Predicate> ps = new ArrayList<Predicate>();

			for (Object obj : (Object[]) getFiltros().get("canais")) {
				Canal canal = (Canal) obj;
				ps.add( cb.isMember(canal, pCanais) );
			}

			Predicate p = cb.or(ps.toArray(new Predicate[] {}));
			
			return getFilterFinder()
					.findByFilters(getFilterDatas(), getQtdeRegistros(), new OrderByParam("iudt.updated", OrderByType.DESC), p, true);

		}
		return getFilterFinder().findByFilters(getFilterDatas(), getQtdeRegistros());
	}

}
