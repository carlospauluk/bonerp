package com.bonsucesso.erp.ekt.espelhos2bonerp;



import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.ekt.model.EKTProduto;
import com.bonsucesso.erp.estoque.data.ProdutoDataMapper;
import com.bonsucesso.erp.estoque.data.ProdutoFinder;
import com.bonsucesso.erp.estoque.model.Produto;
import com.bonsucesso.erp.estoque.model.ProdutoPreco;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.security.spring.SecurityUtils;
import com.ocabit.utils.calendar.CalendarUtil;
import com.ocabit.utils.currency.CurrencyUtils;
import com.ocabit.utils.strings.StringUtils;


@Component("gerarProdutosPrecos")
@Scope("prototype") // para poder rodar com thread (não retorna sempre o mesmo bean)
public class GerarProdutosPrecos implements IGerarProdutosPrecos {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected static Logger logger = Logger.getLogger(GerarProdutosPrecos.class);

	public static ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

	public static ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");

	@Autowired
	private BeanFactory beanFactory;

	private EntityManager entityManager;

	private IGerarProdutosPrecos thiz;

	private static String mesAnoImport;

	private int inicio;

	private int fim;

	@Override
	public EntityManager getEntityManager() {
		// retorna o contexto de persistência
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

	public IGerarProdutosPrecos getThiz() {
		return thiz;
	}

	public void setThiz(IGerarProdutosPrecos thiz) {
		this.thiz = thiz;
	}

	@Override
	public void run() {
		try {
			getThiz().corrigir(mesAnoImport);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public int getInicio() {
		return inicio;
	}

	@Override
	public void setInicio(int inicio) {
		this.inicio = inicio;
	}

	public int getFim() {
		return fim;
	}

	public void setFim(int fim) {
		this.fim = fim;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void corrigir(final String mesano) throws Exception {

		logger.debug("Corrigindo para o mesano: " + mesano + ". THREAD: " + getInicio() + "/" + getFim());
		SecurityUtils.mockUser();

		// Pego todos os 'EKTProduto' do 'mesano'
		String sqlEktProduto = "SELECT * FROM ekt_produto WHERE mesano = :mesano ORDER BY id LIMIT :ini,:fim";

		ProdutoDataMapper produtoDataMapper = (ProdutoDataMapper) getBeanFactory().getBean("produtoDataMapper");
		ProdutoFinder produtoFinder = (ProdutoFinder) getBeanFactory().getBean("produtoFinder");
		Query qryEktProduto = produtoFinder.getEntityManager().createNativeQuery(sqlEktProduto, EKTProduto.class);
		qryEktProduto.setParameter("mesano", mesano);
		int ini = getThiz().getInicio();
		int fim = getThiz().getFim();
		qryEktProduto.setParameter("ini", ini);
		qryEktProduto.setParameter("fim", fim);
		List<EKTProduto> ektProdutos = qryEktProduto.getResultList();

		SimpleDateFormat sdfMesAno = new SimpleDateFormat("yyyyMM");
		Date dtMesAno = sdfMesAno.parse(mesano);

		for (EKTProduto ektProduto : ektProdutos) {

			Produto produtoBonERP = null;

			try {
				produtoBonERP = produtoFinder.findByReduzidoEkt(ektProduto.getReduzido().intValue(), dtMesAno).get(0);
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}

			if (produtoBonERP == null) {
				throw new IllegalStateException("produto null");
			}

			ProdutoPreco preco = new ProdutoPreco();
			preco.setMesano(dtMesAno);
			preco.setProduto(produtoBonERP);
			preco.setCoeficiente(CurrencyUtils.getBigDecimalScaled(ektProduto.getCoef(), 3));
			preco.setCustoOperacional(CurrencyUtils.getBigDecimalScaled(ektProduto.getMargemC(), 3));
			preco.setCustoFinanceiro(new BigDecimal("0.15"));
			preco.setMargem(CurrencyUtils.getBigDecimalScaled(ektProduto.getMargem(), 3));
			preco.setDtCusto(ektProduto.getDataPCusto() != null ? ektProduto.getDataPCusto() : dtMesAno);
			preco.setDtPrecoVenda(ektProduto.getDataPVenda() != null ? ektProduto.getDataPVenda() : dtMesAno);
			preco.setPrazo(ektProduto.getPrazo().intValue());
			preco.setPrecoCusto(CurrencyUtils.getBigDecimalCurrency(ektProduto.getpCusto()));
			preco.setPrecoPrazo(CurrencyUtils.getBigDecimalCurrency(ektProduto.getpPrazo()));
			preco.setPrecoPromo(CurrencyUtils.getBigDecimalCurrency(ektProduto.getpPromo()));
			preco.setPrecoVista(CurrencyUtils.getBigDecimalCurrency(ektProduto.getpVista()));

			if (!produtoBonERP.getPrecos().contains(preco)) {
				produtoBonERP.addPreco(preco);
				produtoBonERP = produtoDataMapper.save(produtoBonERP);
				produtoDataMapper.getEntityManager().flush();
			}

		}

	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class, ViewException.class })
	public void deleteTudoDoMesAno() {
		// Apaga tudo do mesano para em seguida reimportar
		// getEntityManager().createNativeQuery("TRUNCATE TABLE est_produto_preco").executeUpdate();
	}

	public static void doIt() {

		long ini = System.currentTimeMillis();

		taskExecutor.initialize();

		IGerarProdutosPrecos tDelete = (IGerarProdutosPrecos) context
				.getBean("gerarProdutosPrecos");
		tDelete.deleteTudoDoMesAno();

		int j = 500;
		for (int i = 0; i <= 12000; i += j) {
			IGerarProdutosPrecos t = (IGerarProdutosPrecos) context.getBean("gerarProdutosPrecos");
			t.setThiz(t);
			t.setInicio(i);
			t.setFim(j);
			taskExecutor.execute(t);
		}

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
	}

	public static void importarTodos() {
		
		Date mesAnterior = CalendarUtil.incMes(new Date(), -1);
		
		List<Date> lista = CalendarUtil
				.buildMesAnoList(CalendarUtil.getDate(01, 01, 2014), mesAnterior);

		for (Date d : lista) {
			mesAnoImport = new SimpleDateFormat("yyyyMM").format(d);
			System.out.println("mesano a importar: " + mesAnoImport + ". Confirma (S/N) ?");
			doIt();
		}

	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		String patternMesAno = "([1-2]{1}[0-9]{3}(0{1}[1-9]{1}|1{1}[0-2]{1}))";

		Date mesAnterior = CalendarUtil.incMes(new Date(), -1);
		String mesanoAnterior = new SimpleDateFormat("MM/yyyy").format(mesAnterior);
		
		while (true) {

			System.out.println("\n\n\n\n\n\n\nInforme o mesano (yyyyMM), ou 'TODOS' (para 01/2014 até " + mesanoAnterior + ") ou 'SAIR': ");

			mesAnoImport = scanner.nextLine();

			if ("TODOS".equals(mesAnoImport)) {
				importarTodos();
				System.exit(0);
			} else if (StringUtils.regexMatch(patternMesAno, mesAnoImport)) {
				System.out.println("mesano a importar: " + mesAnoImport + ". Confirma (S/N) ?");
				String sn = scanner.nextLine();
				if ("S".equalsIgnoreCase(sn)) {
					doIt();
					System.out.println("\n\n\n\n\nmesano importado com sucesso: " + mesAnoImport);
				}
				System.out.println(sn);
			} else if ("SAIR".equals(mesAnoImport)) {
				System.out.println("Saindo");
				scanner.close();
				System.exit(0);
			} else {
				System.out.println("???");
			}

		}

	}

}
