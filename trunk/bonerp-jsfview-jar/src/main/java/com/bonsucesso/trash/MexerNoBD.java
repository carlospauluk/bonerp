package com.bonsucesso.trash;



import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.financeiro.data.MovimentacaoDataMapper;
import com.bonsucesso.erp.financeiro.data.MovimentacaoFinder;
import com.bonsucesso.erp.financeiro.model.Movimentacao;
import com.ocabit.utils.strings.StringUtils;


@Component("mexerNoBD")
public class MexerNoBD {

	@Autowired
	private BeanFactory beanFactory;

	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		// retorna o contexto de persistência
		return entityManager;
	}

	/**
	 * Injeção do contexto de persistência pelo Spring.
	 */
	@PersistenceContext
	public void setEntityManager(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	public static void main(String[] args) throws Exception {

		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

		MexerNoBD t = (MexerNoBD) context.getBean("mexerNoBD");
		//t.corrigirDatas();
		t.corrigir();

	}

	@Transactional
	public void corrigir() throws Exception {

		MovimentacaoFinder f = (MovimentacaoFinder) getBeanFactory().getBean("movimentacaoFinder");
		MovimentacaoDataMapper dm = (MovimentacaoDataMapper) getBeanFactory().getBean("movimentacaoDataMapper");
		Query qry = f.getEntityManager().createQuery("SELECT id FROM Movimentacao WHERE unqControle is null");
		List ms = qry.getResultList();

		String sql = "UPDATE fin_movimentacao SET unq_controle = :unq_controle WHERE id = :id";
		Query qryUpdate = f.getEntityManager().createNativeQuery(sql);
		
		for (Object o : ms) {
			qryUpdate.setParameter("unq_controle", StringUtils.generateRandomString(15));
			qryUpdate.setParameter("id", (Long)o);
			qryUpdate.executeUpdate();
			f.getEntityManager().flush();
		}

	}
}
