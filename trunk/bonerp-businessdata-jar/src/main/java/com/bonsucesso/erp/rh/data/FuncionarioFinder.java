package com.bonsucesso.erp.rh.data;



import java.util.Date;
import java.util.List;

import com.bonsucesso.erp.rh.model.Funcionario;
import com.bonsucesso.erp.rh.model.FuncionarioCargo;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade Funcionario.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface FuncionarioFinder extends BasicEntityFinder<Funcionario> {

	public List<Funcionario> findAtuais();

	public List<Funcionario> findAtivosNoMesAno(Date mesAno);

	public List<Funcionario> findVendedoresAtivosNoMesAno(Date mesAno) throws ViewException;

	public List<Funcionario> findByNome(String str);

	public Funcionario findByCpf(String cpf);

	public Funcionario findByCodigoAndNomeEkt(String nomeEkt, Integer codigo);

	public Funcionario findByNomeEktAndMesAno(String nomeEkt, Date mesAno);

	public FuncionarioCargo findCargoByMesAno(Funcionario funcionario, Date mesAno);

	public Funcionario findByCodigo(Integer codigo) throws ViewException;

	public FuncionarioCargo findCargoAtualBy(Funcionario funcionario) throws ViewException;

}
