package com.bonsucesso.erp.ekt.espelhos2bonerp;



import java.io.Serializable;

import com.bonsucesso.erp.ekt.model.EKTEncomenda;
import com.bonsucesso.erp.rh.model.Funcionario;
import com.bonsucesso.erp.vendas.model.Encomenda;
import com.ocabit.base.view.exception.ViewException;


public interface IImportarEncomendas2BonERP extends Serializable, Runnable {

	void importarEncomendas();

	void salvarEncomenda(EKTEncomenda ektEncomenda) throws ViewException;

	Funcionario handleVendedor(EKTEncomenda ektEncomenda) throws ViewException;

	public int getInicio();

	public void setInicio(int inicio);

	public int getFim();

	public void setFim(int fim);

	IImportarEncomendas2BonERP getThiz();

	void setThiz(IImportarEncomendas2BonERP thiz);

	void atualizarEncomenda(Encomenda encomenda, EKTEncomenda ektEncomenda) throws ViewException;

	void marcarEncomendasDeletadas();

	String getLogFile();

	void setLogFile(String logFile);

	String getDirLogs();

	void setDirLogs(String dirLogs);

	void deletarEncomenda(Encomenda encomenda) throws ViewException;

	void deletarEncomendas();

}
