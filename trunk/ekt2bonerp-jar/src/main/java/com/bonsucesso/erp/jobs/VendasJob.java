package com.bonsucesso.erp.jobs;



import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;


@Component("vendasJob")
@Scope("prototype") // para poder rodar com thread (não retorna sempre o mesmo bean)
public class VendasJob implements IVendasJob {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected static Logger logger = Logger.getLogger(VendasJob.class);

	public static ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

	public static ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");

	@Autowired
	private BeanFactory beanFactory;

	private EntityManager entityManager;

	private IVendasJob thiz;

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	@PersistenceContext
	public void setEntityManager(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	@Override
	public IVendasJob getThiz() {
		// aqui tive que fazer desta forma por causa das threads. o Thiz é passado no main.
		return thiz;
	}

	@Override
	public void setThiz(IVendasJob thiz) {
		this.thiz = thiz;
	}

	@Override
	public void run() {
		try {
			getThiz().doJob();
		} catch (Exception e) {
			throw new IllegalStateException("Erro no run()", e);
		}
	}

	@Override
	public void doJob() {
		System.out.println("jobbbbbbbb");

	}

	public static void main(String[] args) {

		long ini = System.currentTimeMillis();

		taskExecutor.initialize();

		IVendasJob j = (IVendasJob) context.getBean("vendasJob");
		j.setThiz(j);
		taskExecutor.execute(j);

		for (;;) {
			int count = taskExecutor.getActiveCount();
			System.out.println("Active Threads : " + count);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (count == 0) {
				taskExecutor.shutdown();
				break;
			}
		}
		
		long fim = System.currentTimeMillis();
		double tempo = (fim - ini) / 1000;
		logger.info("Tempo de exec.: " + tempo + " segundos");
		
		System.exit(0);

	}

}
	