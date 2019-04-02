package com.bonsucesso.erp.ekt.espelhos2bonerp;



import java.io.Serializable;

import com.bonsucesso.erp.ekt.model.EKTVenda;
import com.bonsucesso.erp.rh.model.Funcionario;
import com.bonsucesso.erp.vendas.model.Venda;
import com.ocabit.base.view.exception.ViewException;


public interface IImportarVendas2BonERP extends Serializable, Runnable {

	void importarVendas();

	void salvarVenda(EKTVenda ektVenda, Venda venda) throws ViewException;

	Funcionario handleVendedor(EKTVenda ektVenda) throws ViewException;

	public int getInicio();

	public void setInicio(int inicio);

	public int getFim();

	public void setFim(int fim);

	IImportarVendas2BonERP getThiz();

	void setThiz(IImportarVendas2BonERP thiz);

	void marcarVendasDeletadas();

	String getLogFile();

	void setLogFile(String logFile);

	String getDirLogs();

	void setDirLogs(String dirLogs);

	void preencherMesVenda();

}
