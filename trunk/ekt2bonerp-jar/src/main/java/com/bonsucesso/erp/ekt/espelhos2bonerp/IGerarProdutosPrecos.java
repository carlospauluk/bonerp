package com.bonsucesso.erp.ekt.espelhos2bonerp;



import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.BeanFactory;


public interface IGerarProdutosPrecos extends Serializable, Runnable {

	EntityManager getEntityManager();

	/**
	 * Injeção do contexto de persistência pelo Spring.
	 */
	void setEntityManager(EntityManager entityManager);

	BeanFactory getBeanFactory();

	void setBeanFactory(BeanFactory beanFactory);

	IGerarProdutosPrecos getThiz();

	void setThiz(IGerarProdutosPrecos thiz);

	int getInicio();

	void setInicio(int inicio);

	int getFim();

	void setFim(int fim);

	void corrigir(String mesano) throws Exception;

	void deleteTudoDoMesAno();

}
