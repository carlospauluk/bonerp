package com.bonsucesso.erp.financeiro.data;



import java.util.Date;
import java.util.List;

import com.bonsucesso.erp.financeiro.model.Grupo;
import com.bonsucesso.erp.financeiro.model.GrupoItem;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade GrupoItem.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface GrupoItemFinder extends BasicEntityFinder<GrupoItem> {

	/**
	 *
	 * @param grupo
	 * @param mesAno
	 * @return
	 * @throws ViewException
	 */
	public GrupoItem findBy(Grupo grupo, Date mesAno) throws ViewException;

	/**
	 *
	 * @param grupo
	 * @return
	 * @throws ViewException
	 */
	public GrupoItem findUltimo(Grupo grupo) throws ViewException;

	/**
	 * Encontra o último grupoItem imediatamente anterior ao mesAno.
	 *
	 * @param grupo
	 * @param mesAno
	 * @return
	 * @throws ViewException
	 */
	public GrupoItem findUltimoAnterior(Grupo grupo, Date mesAno) throws ViewException;

	/**
	 * Encontra o primeiro grupoItem imediatamente porterior ao mesAno.
	 *
	 * @param grupo
	 * @param mesAno
	 * @return
	 * @throws ViewException
	 */
	public GrupoItem findPrimeiroPosterior(Grupo grupo, Date mesAno) throws ViewException;

	/**
	 * Encontra todos os GrupoItem de um mesAno.
	 *
	 * @param mesAno
	 * @return
	 * @throws ViewException
	 */
	public List<GrupoItem> findBy(Date mesAno) throws ViewException;

	/**
	 * Retorna todos os itens ainda abertos de um grupo.
	 * 
	 * @param grupo
	 * @return
	 * @throws ViewException
	 */
	public List<GrupoItem> findAllAbertosBy(Grupo grupo) throws ViewException;

}
