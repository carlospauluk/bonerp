package com.bonsucesso.erp.financeiro.data;



import java.util.List;

import com.bonsucesso.erp.financeiro.model.Carteira;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade Carteira.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface CarteiraFinder extends BasicEntityFinder<Carteira> {

	public List<Carteira> findAllConcretas() throws ViewException;

	public List<Carteira> findAllBancos() throws ViewException;

	public Carteira findBy(String descricao) throws ViewException;

	public Carteira findBy(Integer codigo) throws ViewException;

	public List<Carteira> findAllCaixas() throws ViewException;

	public List<Carteira> findAllCheque() throws ViewException;

	public List<Carteira> findAllAbertas() throws ViewException;

}
