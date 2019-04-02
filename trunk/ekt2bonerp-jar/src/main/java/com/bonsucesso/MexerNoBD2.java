package com.bonsucesso;



import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
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

import com.bonsucesso.erp.vendas.business.MesVendaBusiness;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.utils.calendar.CalendarUtil;


@Component("mexerNoBD2")
@Scope("prototype") // para poder rodar com thread (não retorna sempre o mesmo bean)
public class MexerNoBD2 implements IMexerNoBD2 {

	protected static Logger logger = Logger.getLogger(MexerNoBD2.class);

	private IMexerNoBD2 thiz;

	@Autowired
	private MesVendaBusiness mesVendaBusiness;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void corrigir() {

		final Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		final org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User("carlos", "none", true, true, true, true, authorities);
		Authentication auth = new TestingAuthenticationToken(user, authorities);
		SecurityContextHolder.getContext().setAuthentication(auth);

		// Percorrer de 01/01/2014 até hoje e ir chamando o MesVendaBusiness.preencherMesVenda()

		List<Date> lista = CalendarUtil.buildMesAnoList(CalendarUtil.getDate(01, 01, 2014), new Date());
		
		for (Date mesano : lista) {
			try {
				getMesVendaBusiness().preencherMesVenda(mesano);
			} catch (ViewException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public IMexerNoBD2 getThiz() {
		// aqui tive que fazer desta forma por causa das threads. o Thiz é passado no main.
		return thiz;
	}

	@Override
	public void setThiz(IMexerNoBD2 thiz) {
		this.thiz = thiz;
	}

	public MesVendaBusiness getMesVendaBusiness() {
		return mesVendaBusiness;
	}

	public void setMesVendaBusiness(MesVendaBusiness mesVendaBusiness) {
		this.mesVendaBusiness = mesVendaBusiness;
	}

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		IMexerNoBD2 t = (IMexerNoBD2) context.getBean("mexerNoBD2");
		t.setThiz(t);
		t.corrigir();
		System.exit(0);

	}

}
