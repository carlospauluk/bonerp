package com.bonsucesso.erp.rh.view;



import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.base.model.TipoPessoa;
import com.bonsucesso.erp.rh.data.FuncionarioDataMapper;
import com.bonsucesso.erp.rh.data.FuncionarioFilterFinder;
import com.bonsucesso.erp.rh.data.FuncionarioFinder;
import com.bonsucesso.erp.rh.model.Funcionario;
import com.bonsucesso.erp.rh.model.FuncionarioCargo;
import com.ocabit.base.data.filter.FilterData;
import com.ocabit.base.data.filter.FilterType;
import com.ocabit.base.view.AbstractEntityFormListView;
import com.ocabit.jsf.utils.JSFUtils;


/**
 * ListView para a entidade Funcionario.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("funcionarioFormListView")
@Scope("view")
public class FuncionarioFormListView extends
		AbstractEntityFormListView<Funcionario, FuncionarioDataMapper, FuncionarioFinder> {

	/**
	 *
	 */
	private static final long serialVersionUID = 3552693107001465553L;

	protected static Logger logger = Logger.getLogger(FuncionarioFormListView.class);

	@Autowired
	private FuncionarioFilterFinder filterFinder;

	private FuncionarioCargo cargo;

	private String alterarSenha;

	/**
	 * Informa se é para efetuar, na tela, cadastro de somente um tipo.
	 */
	private TipoPessoa cadastrarSomente = TipoPessoa.PESSOA_FISICA;

	@PostConstruct
	public void init() {
		//		getFiltros().put("aindaTrabalhando", new Boolean(true));
		//		getFiltros().put("clt", new Boolean(true));
		JSFUtils.execute("loadList()");
	}

	@Override
	public FuncionarioFilterFinder getFilterFinder() {
		return filterFinder;
	}

	public void setFilterFinder(FuncionarioFilterFinder filterFinder) {
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
					.add(new FilterData("strPesquisa", FilterType.LIKE_ANYWHERE, "pessoa.nome", "nomeEkt"));
		}

		getFilterDatas()
				.add(new FilterData("aindaTrabalhando", FilterType.NONE_UM));

		getFilterDatas()
				.add(new FilterData("clt", FilterType.EQUALS));

	}

	@Override
	public void afterSetList() {
		List<Funcionario> filtrados = new ArrayList<Funcionario>();
		if (getFiltros().containsKey("aindaTrabalhando")) {
			Boolean aindaTrabalhando = getFiltros().containsKey("aindaTrabalhando");

			for (Funcionario f : getList()) {
				boolean ehAtual = false;
				for (FuncionarioCargo c : f.getCargos()) {
					if (c.getAtual()) {
						ehAtual = true;
						break;
					}
				}
				if (ehAtual == aindaTrabalhando) {
					filtrados.add(f);
				}

			}
			this.list = filtrados;
		}
	}

	@Override
	public void afterSave() {
		// Utilizado somente quando o form é para o dialog
		RequestContext context = RequestContext.getCurrentInstance();
		String dlgId = "dlgFuncionario";
		context.addCallbackParam("dlgId", dlgId);
		JSFUtils.execute("loadList()");
		setAlterarSenha(null);
	}

	public FuncionarioCargo getCargo() {
		if (cargo == null) {
			cargo = new FuncionarioCargo();
		}
		return cargo;
	}

	public void setCargo(FuncionarioCargo cargo) {
		this.cargo = cargo;
	}

	public void novoCargo() {
		FuncionarioCargo cargo = new FuncionarioCargo();
		cargo.setFuncionario(getE());
		setCargo(cargo);
	}

	public void saveCargo() {
		try {
			if (getCargo().getId() == null) {
				getE().getCargos().add(getCargo());
			}
			save();
			RequestContext context = RequestContext.getCurrentInstance();
			context.addCallbackParam("dlgId", "dlgCargo");
			context.addCallbackParam("saved", true);
			JSFUtils.execute("loadList()");
		} catch (Exception e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao salvar o cargo");
		}
	}

	public void deleteCargo(FuncionarioCargo cargo) {
		try {
			setCargo(cargo);
			getE().getCargos().remove(getCargo());
			save();
			novoCargo();
			JSFUtils.addInfoMsgRegistroDeletado();
		} catch (Exception e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao deletar");
		}
	}

	public String getAlterarSenha() {
		return alterarSenha;
	}

	public void setAlterarSenha(String alterarSenha) {
		this.alterarSenha = alterarSenha;
	}

	@Override
	public void validate() {
		if ((getAlterarSenha() != null) && !"".equals(getAlterarSenha())) {
			final PasswordEncoder newEncoder = new BCryptPasswordEncoder();
			getE().setSenha(newEncoder.encode(getAlterarSenha()));
			newEncoder.matches(getAlterarSenha(), getE().getSenha());
		}
	}

	@Override
	public void afterSetE(Funcionario e) {
		if ((e != null) && (e.getCargos() != null)) {
			e.getCargos().size(); // touch para lazy
			for (FuncionarioCargo cargo : e.getCargos()) {
				cargo.hashCode();
			}
		}
		super.afterSetE(e);
	}

	public TipoPessoa getCadastrarSomente() {
		return cadastrarSomente;
	}

	public void setCadastrarSomente(TipoPessoa cadastrarSomente) {
		this.cadastrarSomente = cadastrarSomente;
	}

}
