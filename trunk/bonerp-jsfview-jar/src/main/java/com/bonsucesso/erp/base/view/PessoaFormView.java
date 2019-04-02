package com.bonsucesso.erp.base.view;



import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.base.data.PessoaDataMapper;
import com.bonsucesso.erp.base.model.Pessoa;
import com.ocabit.base.view.AbstractEntityFormView;


/**
 * View para a entidade Movimentacao.
 *
 * @author Carlos Eduardo Pauluk
 */
@Component("pessoaFormView")
@Scope("view")
public class PessoaFormView extends AbstractEntityFormView<Pessoa, PessoaDataMapper> {

	/**
	 *
	 */
	private static final long serialVersionUID = -4347678258692403653L;

	protected static Logger logger = Logger.getLogger(PessoaFormView.class);

}
