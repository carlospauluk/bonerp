package com.bonsucesso;



import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceProperty;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.ekt.model.EKTVendaItem;
import com.bonsucesso.erp.vendas.data.VendaItemDataMapper;
import com.bonsucesso.erp.vendas.model.VendaItem;
import com.ocabit.base.model.EntityId;
import com.ocabit.base.view.exception.ViewException;


@Component("mexerNoBD")
@Scope("prototype") // para poder rodar com thread (não retorna sempre o mesmo bean)
public class MexerNoBD implements IMexerNoBD {

	protected static Logger logger = Logger.getLogger(MexerNoBD.class);

	private IMexerNoBD thiz;

	@Autowired
	private BeanFactory beanFactory;

	@PersistenceContext(properties = @PersistenceProperty(name = "org.hibernate.flushMode", value = "COMMIT"))
	private EntityManager entityManager;

	public static void setup() {
		final Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		final org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User("carlos", "none", true, true, true, true, authorities);
		Authentication auth = new TestingAuthenticationToken(user, authorities);
		SecurityContextHolder.getContext().setAuthentication(auth);
		
	}
	
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void corrigir() throws ViewException {

		MexerNoBD.setup();
		
		String jpql = "SELECT vi.* FROM ven_venda_item vi WHERE vi.obs LIKE '%88888%' AND vi.nc_descricao IS NULL";
		Query qry = getEntityManager().createNativeQuery(jpql,VendaItem.class);
		qry.setMaxResults(10000);
		List<VendaItem> l = qry.getResultList();
		
				
		String jpqlEKTItem = "FROM EKTVendaItem WHERE numeroNF = :pv AND produto = 88888 AND mesano = :mesano AND vlrUnit = :vlrUnit";
		Query qryEKTItem = getEntityManager().createQuery(jpqlEKTItem);
		
		int x=0;
		Integer pv = null;
		for (VendaItem item : l) {
			
			logger.info(x++ + " processados");
			
			
			qryEKTItem.setParameter("pv", item.getVenda().getPv().doubleValue());
			qryEKTItem.setParameter("vlrUnit", item.getPrecoVenda().doubleValue());
			qryEKTItem.setParameter("mesano", item.getVenda().getMesano());
			
			List<EKTVendaItem> lE = qryEKTItem.getResultList();
			
			EKTVendaItem ektItem = null;
			
			if (lE == null || lE.size() == 0) { // || lE.size() > 1) {
				System.out.println("???");
				continue;
			} else {
				ektItem = lE.get(0);
			}
			
				
			
			item.setNcDescricao(ektItem.getDescricao());
			
			item.setNcGradeTamanho(ektItem.getTamanho());
			item.setNcReduzido(ektItem.getProduto().longValue());
			
			try {
				item = (VendaItem) getThiz().saveRow(item);
			} catch (ViewException e) {
				throw new ViewException(e);
			}
			getEntityManager().detach(item);
			
			getEntityManager().flush();

		}

	}
	

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void corrigir2() {

	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public EntityId saveRow(EntityId row) throws ViewException {
		try {
			VendaItemDataMapper vendaItemDataMapper = (VendaItemDataMapper) getBeanFactory().getBean("vendaItemDataMapper");
			vendaItemDataMapper.save((VendaItem) row);
			vendaItemDataMapper.getEntityManager().flush();
			return row;
		} catch (Exception e) {
			throw new ViewException(e);
		}
	}

	@Override
	public IMexerNoBD getThiz() {
		// aqui tive que fazer desta forma por causa das threads. o Thiz é passado no main.
		return thiz;
	}

	@Override
	public void setThiz(IMexerNoBD thiz) {
		this.thiz = thiz;
	}

	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {

		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

		IMexerNoBD t = (IMexerNoBD) context.getBean("mexerNoBD");
		t.setThiz(t);
		t.corrigir();

		System.exit(0);

	}

}
