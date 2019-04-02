package com.bonsucesso.erp.base.view;



import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.base.data.PessoaFinder;
import com.bonsucesso.erp.base.model.Pessoa;
import com.bonsucesso.erp.base.model.PessoaCadastro;
import com.ocabit.base.view.AbstractDlgBuscaView;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;


@Component("pessoaDlgBuscaView")
@Scope("view")
public class PessoaDlgBuscaView implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1623950893631596793L;

	protected static Logger logger = Logger.getLogger(AbstractDlgBuscaView.class);

	@Autowired
	private PessoaFinder finder;

	private List<Pessoa> list;

	private PessoaCadastro[] pessoasCadastro;

	private String strPesquisa;

	public PessoaFinder getFinder() {
		return finder;
	}

	public void setFinder(PessoaFinder finder) {
		this.finder = finder;
	}

	public List<Pessoa> getList() {
		return list;
	}

	public void setList(List<Pessoa> list) {
		this.list = list;
	}

	public PessoaCadastro[] getPessoasCadastro() {
		return pessoasCadastro;
	}

	public void setPessoasCadastro(PessoaCadastro[] pessoasCadastro) {
		this.pessoasCadastro = pessoasCadastro;
	}

	public void setPessoaCadastroBy(String pessoaCadastro) {
		if (pessoaCadastro != null && !"".equals(pessoaCadastro.trim())) {
			this.pessoasCadastro = new PessoaCadastro[] { PessoaCadastro.valueOf(pessoaCadastro) };
		}
	}	

	public String getStrPesquisa() {
		return strPesquisa;
	}

	public void setStrPesquisa(String strPesquisa) {
		this.strPesquisa = strPesquisa;
	}

	public final void pesquisar() {
		try {
			setList(getFinder().findPessoaByNome(getStrPesquisa(), getPessoasCadastro()));
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public void resetBusca() {
		setList(null);
		setStrPesquisa(null);
		setPessoasCadastro(null);
	}

}
