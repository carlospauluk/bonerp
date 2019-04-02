package com.bonsucesso.erp.financeiro.data;



import com.bonsucesso.erp.financeiro.model.RegistroConferencia;
import com.ocabit.base.data.DataMapper;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de DataMapper para entidade RegistroConferencia.
 *
 * @author Carlos Eduardo Pauluk
 */
public interface RegistroConferenciaDataMapper extends DataMapper<RegistroConferencia> {

	RegistroConferencia gerarProximo(RegistroConferencia registroConferencia) throws ViewException;

}
