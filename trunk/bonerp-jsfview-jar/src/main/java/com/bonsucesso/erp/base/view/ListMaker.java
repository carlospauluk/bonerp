package com.bonsucesso.erp.base.view;



import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.base.data.PessoaFinder;
import com.bonsucesso.erp.base.model.Pessoa;
import com.bonsucesso.erp.base.model.PessoaCadastro;
import com.bonsucesso.erp.base.model.Sexo;
import com.bonsucesso.erp.base.model.TipoEndereco;
import com.bonsucesso.erp.base.model.TipoPessoa;
import com.bonsucesso.erp.base.model.UF;
import com.ocabit.base.view.AbstractListMaker;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;


/**
 * ListMaker para as entidades do pacote com.bonsucesso.erp.base.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("lmBase")
@Scope("view")
public class ListMaker implements AbstractListMaker {

	/**
	 *
	 */
	private static final long serialVersionUID = 7419681096476694281L;

	protected static Logger logger = Logger.getLogger(ListMaker.class);

	private List<PessoaCadastro> pessoaCadastroValues;

	@Autowired
	private PessoaFinder pessoaFinder;

	private List<TipoEndereco> tipoEnderecoValues;

	private List<TipoPessoa> tipoPessoaValues;

	private List<UF> ufValues;

	private List<Sexo> sexoValues;

	public List<Pessoa> findPessoasBy(String str) {
		try {
			return getPessoaFinder().findBy(str);
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao pesquisar pessoa.");
			return null;
		}
	}

	public List<Pessoa> findPessoasBy(String str, PessoaCadastro pessoaCadastro) {
		try {
			return getPessoaFinder().findPessoaByNome(str, pessoaCadastro);
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao pesquisar pessoa.");
			return null;
		}
	}
	
	public List<PessoaCadastro> getPessoaCadastroValues() {
		if (pessoaCadastroValues == null) {
			pessoaCadastroValues = Arrays.asList(PessoaCadastro.values());
		}
		return pessoaCadastroValues;
	}

	public PessoaFinder getPessoaFinder() {
		return pessoaFinder;
	}

	public List<TipoEndereco> getTipoEnderecoValues() {
		if (tipoEnderecoValues == null) {
			tipoEnderecoValues = Arrays.asList(TipoEndereco.values());
		}
		return tipoEnderecoValues;
	}

	public List<TipoPessoa> getTipoPessoaValues() {
		if (tipoPessoaValues == null) {
			tipoPessoaValues = Arrays.asList(TipoPessoa.values());
		}
		return tipoPessoaValues;
	}

	public List<UF> getUfValues() {
		if (ufValues == null) {
			ufValues = Arrays.asList(UF.values());
		}
		return ufValues;
	}

	public List<Sexo> getSexoValues() {
		if (sexoValues == null) {
			sexoValues = Arrays.asList(Sexo.values());
		}
		return sexoValues;
	}

	public void setSexoValues(List<Sexo> sexoValues) {
		this.sexoValues = sexoValues;
	}

	public void setPessoaFinder(PessoaFinder pessoaFinder) {
		this.pessoaFinder = pessoaFinder;
	}

	public void setTipoEnderecoValues(List<TipoEndereco> tipoEnderecoValues) {
		this.tipoEnderecoValues = tipoEnderecoValues;
	}

	public void setTipoPessoaValues(List<TipoPessoa> tipoPessoaValues) {
		this.tipoPessoaValues = tipoPessoaValues;
	}

	public void setUfValues(List<UF> ufValues) {
		this.ufValues = ufValues;
	}
}
