package com.bonsucesso.erp.financeiro.data;



import java.util.Date;

import com.bonsucesso.erp.financeiro.model.Grupo;
import com.bonsucesso.erp.financeiro.model.GrupoItem;
import com.ocabit.base.data.DataMapper;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de DataMapper para entidade Grupo.
 *
 * @author Carlos Eduardo Pauluk
 */
public interface GrupoDataMapper extends DataMapper<Grupo> {

	public GrupoItem gerarProximo(Grupo grupo) throws ViewException;

	public GrupoItem gerarNoMesAno(Grupo grupo, Date mesAno) throws ViewException;

	public GrupoItem gerarTodos(Grupo grupo, Date mesAnoIni, Date mesAnoFim) throws ViewException;

	public String gerarDescricao(GrupoItem grupoItem);

}
