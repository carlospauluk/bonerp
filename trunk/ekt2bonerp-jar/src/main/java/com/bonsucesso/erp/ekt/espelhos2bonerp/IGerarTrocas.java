package com.bonsucesso.erp.ekt.espelhos2bonerp;



import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.BeanFactory;

import com.bonsucesso.erp.ekt.model.EKTProduto;
import com.bonsucesso.erp.estoque.model.GradeTamanho;
import com.bonsucesso.erp.estoque.model.Produto;
import com.bonsucesso.erp.rh.model.Funcionario;
import com.bonsucesso.erp.vendas.data.VendaDataMapper;
import com.bonsucesso.erp.vendas.model.PlanoPagto;
import com.bonsucesso.erp.vendas.model.TipoVenda;
import com.ocabit.base.view.exception.ViewException;


public interface IGerarTrocas extends Serializable, Runnable {

	EntityManager getEntityManager();

	/**
	 * Injeção do contexto de persistência pelo Spring.
	 */
	void setEntityManager(EntityManager entityManager);

	BeanFactory getBeanFactory();

	void setBeanFactory(BeanFactory beanFactory);

	IGerarTrocas getThiz();

	void setThiz(IGerarTrocas thiz);

	int getInicio();

	void setInicio(int inicio);
	
	int getFim();
	
	void setFim(int fim);

	void corrigir(String mesano) throws Exception;

	Double getAc(EKTProduto ektProduto, Integer mes);

	public void saveTroca(String mesano, BigDecimal dif, Produto produtoBonERP) throws ViewException;

	void deletarTrocasNoMesano() throws Exception;

	List<EKTProduto> findEKTProdutos(String mesano);

	void handleEKTProduto(EKTProduto ektProduto, String mesano) throws ViewException;

}
