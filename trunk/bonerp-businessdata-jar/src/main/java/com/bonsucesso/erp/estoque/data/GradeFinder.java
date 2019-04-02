package com.bonsucesso.erp.estoque.data;



import com.bonsucesso.erp.estoque.model.Grade;
import com.bonsucesso.erp.estoque.model.GradeTamanho;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade Grade.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface GradeFinder extends BasicEntityFinder<Grade> {

	public Grade findByCodigo(Integer codigo) throws ViewException;

	public GradeTamanho findByGradeAndOrdem(Grade grade, Integer ordem) throws ViewException;

	public GradeTamanho findByCodigoGradeAndTamanho(Integer grade, String tamanho) throws ViewException;

	public GradeTamanho findGradeTamanhoById(Long id) throws ViewException;

	public GradeTamanho findByCodigoGradeAndOrdem(Integer codigoGrade, Integer ordem) throws ViewException;

	public GradeTamanho findByGradeAndPosicao(Grade grade, Integer posicao) throws ViewException;

}
