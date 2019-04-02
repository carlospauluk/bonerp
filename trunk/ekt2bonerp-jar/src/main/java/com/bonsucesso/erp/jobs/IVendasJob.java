package com.bonsucesso.erp.jobs;



import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.BeanFactory;


public interface IVendasJob extends Serializable, Runnable {

	EntityManager getEntityManager();

	/**
	 * Injeção do contexto de persistência pelo Spring.
	 */
	void setEntityManager(EntityManager entityManager);

	BeanFactory getBeanFactory();

	void setBeanFactory(BeanFactory beanFactory);

	IVendasJob getThiz();

	void setThiz(IVendasJob thiz);
	
	void doJob();

	

}
