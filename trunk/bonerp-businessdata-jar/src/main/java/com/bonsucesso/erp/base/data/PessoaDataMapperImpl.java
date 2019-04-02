package com.bonsucesso.erp.base.data;



import org.springframework.stereotype.Repository;

import com.bonsucesso.erp.base.model.Pessoa;
import com.ocabit.base.data.DataMapperImpl;
import com.ocabit.base.view.exception.ViewException;


@Repository("pessoaDataMapper")
public class PessoaDataMapperImpl extends DataMapperImpl<Pessoa> implements PessoaDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = 3633497825274824182L;

	@Override
	public Pessoa beforeSave(Pessoa pessoa) throws ViewException {
		pessoa.setDocumento(pessoa.getDocumento().replaceAll("[^\\d.]", ""));
		return pessoa;
	}

}
