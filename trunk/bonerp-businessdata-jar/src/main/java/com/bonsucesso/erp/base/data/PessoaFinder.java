package com.bonsucesso.erp.base.data;



import java.util.List;

import com.bonsucesso.erp.base.model.Pessoa;
import com.bonsucesso.erp.base.model.PessoaCadastro;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Finder para a entidade Pessoa.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface PessoaFinder extends BasicEntityFinder<Pessoa> {

	public List<Pessoa> findPessoaByNome(String value) throws ViewException;

	public Pessoa findPessoaByDocumento(String documento) throws ViewException;

	public boolean isPessoaReferenciada(Pessoa pessoa, String tipoAtual) throws ViewException;

	public List<Pessoa> findBy(String str) throws ViewException;

	public List<Pessoa> findPessoaByNome(String value, PessoaCadastro[] pessoasCadastro) throws ViewException;

	public List<Pessoa> findPessoaByNome(String value, PessoaCadastro pessoaCadastro) throws ViewException;

	public Pessoa findPessoaByDocumento(String documento, PessoaCadastro pessoaCadastro) throws ViewException;

}
