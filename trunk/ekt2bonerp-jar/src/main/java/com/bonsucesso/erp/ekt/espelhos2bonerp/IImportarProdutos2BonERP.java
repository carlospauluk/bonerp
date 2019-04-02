package com.bonsucesso.erp.ekt.espelhos2bonerp;



import java.io.Serializable;

import org.springframework.beans.factory.BeanFactory;

import com.bonsucesso.erp.ekt.model.EKTProduto;
import com.bonsucesso.erp.estoque.model.Departamento;
import com.bonsucesso.erp.estoque.model.Grade;
import com.bonsucesso.erp.estoque.model.Produto;
import com.bonsucesso.erp.estoque.model.Subdepartamento;
import com.bonsucesso.erp.estoque.model.UnidadeProduto;
import com.ocabit.base.view.exception.ViewException;


public interface IImportarProdutos2BonERP extends Serializable, Runnable {

	void importarProdutos();

	Produto cadastrarNovoProduto(EKTProduto ektProduto) throws ViewException;

	Produto salvarGrade(EKTProduto ektProduto, Produto produto) throws ViewException;

	Produto atualizaProduto(EKTProduto ektProduto, Produto produto) throws ViewException;

	void acertaPeriodosReduzidoEKT(Integer reduzidoEKT) throws ViewException;

	IImportarProdutos2BonERP getThiz();

	void setThiz(IImportarProdutos2BonERP thiz);

	Grade findGrade(EKTProduto ektProduto) throws ViewException;

	UnidadeProduto findUnidadeProduto(EKTProduto ektProduto) throws ViewException;

	UnidadeProduto getUnidadeProduto999999();

	void setUnidadeProduto999999(UnidadeProduto unidadeProduto999999);

	Departamento findDepto(EKTProduto ektProduto) throws ViewException;

	Subdepartamento findSubdepto(EKTProduto ektProduto) throws ViewException;

	Departamento getDepto999999();

	void setDepto999999(Departamento depto999999);

	Subdepartamento getSubdeptoZERO();

	void setSubdeptoZERO(Subdepartamento subdeptoZERO);

	BeanFactory getBeanFactory();

	void setBeanFactory(BeanFactory beanFactory);

	public int getInicio();

	public void setInicio(int inicio);

	public int getFim();

	public void setFim(int fim);

	public void show();

	void deletarSaldos();

	void acertarDeAteProdutos();

	String getLogFile();

	void setLogFile(String logFile);

	String getDirLogs();

	void setDirLogs(String dirLogs);

	void marcarProdutosInativos();

	void importarEKTProduto(EKTProduto ektProduto) throws ViewException;

}
