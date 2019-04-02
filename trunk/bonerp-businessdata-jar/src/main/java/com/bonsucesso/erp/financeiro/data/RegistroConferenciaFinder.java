package com.bonsucesso.erp.financeiro.data;



import java.util.Date;
import java.util.List;

import com.bonsucesso.erp.financeiro.model.Carteira;
import com.bonsucesso.erp.financeiro.model.RegistroConferencia;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade RegistroConferencia.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface RegistroConferenciaFinder extends BasicEntityFinder<RegistroConferencia> {

	public RegistroConferencia findBy(String descricao) throws ViewException;

	public RegistroConferencia findBy(String descricao, Carteira carteira) throws ViewException;

	public RegistroConferencia findBy(String descricao, Date dtRegistro) throws ViewException;

	public List<String> findDescricaoBy(String str) throws ViewException;

	public List<String> findDistincts() throws ViewException;

}
