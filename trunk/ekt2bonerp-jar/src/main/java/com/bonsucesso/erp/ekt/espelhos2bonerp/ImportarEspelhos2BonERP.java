package com.bonsucesso.erp.ekt.espelhos2bonerp;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.ekt.data.EKTSubDeptoFinder;
import com.bonsucesso.erp.ekt.model.EKTSubDepto;
import com.bonsucesso.erp.estoque.data.DeptoFinder;
import com.bonsucesso.erp.estoque.data.SubdeptoDataMapper;
import com.bonsucesso.erp.estoque.data.SubdeptoFinder;
import com.bonsucesso.erp.estoque.model.Subdepartamento;
import com.ocabit.base.model.InsertUpdateDateTime;
import com.ocabit.base.model.UserInfo;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.config.model.Estabelecimento;
import com.ocabit.security.model.User;
import com.ocabit.utils.currency.CurrencyUtils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;


/**
 * Classe para importações gerais (deptos, subdeptos, grades, etc).
 * 
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("importarEspelhos2BonERP")
public class ImportarEspelhos2BonERP implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2070017266874484530L;

	protected static Logger logger = Logger.getLogger(ImportarEspelhos2BonERP.class);

	/**
	 * Método principal.
	 */
	public void importar() {}

	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void importarSubdeptos() {
		try {
			List<EKTSubDepto> ektSubdeptos = getEktSubDeptoFinder().findAll();
			int i = 0;

			for (EKTSubDepto ektSubDepto : ektSubdeptos) {

				Subdepartamento subdepto = getSubdeptoFinder().findByCodigo(ektSubDepto.getCodigo().intValue());

				if (subdepto != null) {
					if (ektSubDepto.getDescricao() == null) { // para evitar null
						ektSubDepto.setDescricao("");
					}
					if (!"".equals(ektSubDepto.getDescricao()) && "".equals(subdepto.getNome())) {
						subdepto.setNome(ektSubDepto.getDescricao());
					}
					// *** ATENÇÃO: troquei o equals por startsWith por causa que tem subdeptos com nomes iguais no EKT. No BonERP eles receberam sufixo "2" 
					if (!subdepto.getNome().startsWith(ektSubDepto.getDescricao())) {
						throw new ViewException("Erro ao importar o subdepto: " + ektSubDepto.toStringToView());
					}
				}

				if (subdepto == null) {
					subdepto = new Subdepartamento();
					subdepto.setCodigo(ektSubDepto.getCodigo().intValue());
					subdepto.setNome(ektSubDepto.getDescricao());
					subdepto.setIudt(new InsertUpdateDateTime());
					subdepto.setMargem(CurrencyUtils.getBigDecimalScaled(ektSubDepto.getMargem(), 3));
					subdepto.setDepto(getDeptoFinder().findByCodigo(50)); // jogo no Depto "CONTAS A PAGAR" que não serve pra nada mesmo
					getSubdeptoDataMapper().save(subdepto);
					System.out.println(">>> SALVO: " + ++i);
				}

			}
		} catch (ViewException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static UserInfo createUserInfo() {
		UserInfo userInfo = new UserInfo();
		User user = new User();
		user.setId(1l);
		Estabelecimento estab = new Estabelecimento();
		estab.setId(1l);
		userInfo.setEstabelecimento(estab);
		userInfo.setUserInserted(user);
		userInfo.setUserUpdated(user);
		return userInfo;
	}

	@Autowired
	private EKTSubDeptoFinder ektSubDeptoFinder;

	@Autowired
	private SubdeptoDataMapper subdeptoDataMapper;

	@Autowired
	private DeptoFinder deptoFinder;

	@Autowired
	private SubdeptoFinder subdeptoFinder;

	@Autowired
	private BeanFactory beanFactory;

	public DeptoFinder getDeptoFinder() {
		return deptoFinder;
	}

	public void setDeptoFinder(DeptoFinder deptoFinder) {
		this.deptoFinder = deptoFinder;
	}

	public SubdeptoFinder getSubdeptoFinder() {
		return subdeptoFinder;
	}

	public void setSubdeptoFinder(SubdeptoFinder subdeptoFinder) {
		this.subdeptoFinder = subdeptoFinder;
	}

	public EKTSubDeptoFinder getEktSubDeptoFinder() {
		return ektSubDeptoFinder;
	}

	public void setEktSubDeptoFinder(EKTSubDeptoFinder ektSubDeptoFinder) {
		this.ektSubDeptoFinder = ektSubDeptoFinder;
	}

	public SubdeptoDataMapper getSubdeptoDataMapper() {
		return subdeptoDataMapper;
	}

	public void setSubdeptoDataMapper(SubdeptoDataMapper subdeptoDataMapper) {
		this.subdeptoDataMapper = subdeptoDataMapper;
	}

	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	public ImportarEspelhos2BonERP getThiz() {
		return (ImportarEspelhos2BonERP) getBeanFactory().getBean("importarEspelhos2BonERP");
	}

	/**
	 *
	 */
	public void apagarCaches() {
		List<Cache> cachesList = new ArrayList<Cache>();

		CacheManager manager = CacheManager.getInstance();

		for (String cacheName : manager.getCacheNames()) {
			cachesList.add(manager.getCache(cacheName));
		}

		for (Cache cache : cachesList) {
			manager.getCache(cache.getName()).removeAll();
		}
	}

	/**
	 * @param args
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

		ImportarEspelhos2BonERP t = (ImportarEspelhos2BonERP) context.getBean("importarEspelhos2BonERP");

		final Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

		final org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User("carlos", "none", true, true, true, true, authorities);
		Authentication auth = new TestingAuthenticationToken(user, authorities);
		SecurityContextHolder.getContext().setAuthentication(auth);

		t.importar();

		System.exit(0);
	}

}
