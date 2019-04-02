package com.bonsucesso.erp.ekt.espelhos2bonerp;



import java.text.ParseException;
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
import com.bonsucesso.erp.estoque.data.ProdutoFinder;
import com.bonsucesso.erp.estoque.model.Produto;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.security.spring.SecurityUtils;
import com.ocabit.utils.strings.StringUtils;


@Component("gerarProdutoSaldoHistorico")
@Scope("prototype") // para poder rodar com thread (não retorna sempre o mesmo bean)
public class GerarProdutoSaldoHistorico implements IGerarProdutoSaldoHistorico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected static Logger logger = Logger.getLogger(GerarProdutoSaldoHistorico.class);

	public static ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

	public static ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");

	@Autowired
	private BeanFactory beanFactory;

	private EntityManager entityManager;

	private IGerarProdutoSaldoHistorico thiz;

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

	@Override
	public IGerarProdutoSaldoHistorico getThiz() {
		// aqui tive que fazer desta forma por causa das threads. o Thiz é passado no main.
		return thiz;
	}

	@Override
	public void setThiz(IGerarProdutoSaldoHistorico thiz) {
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
		ProdutoFinder produtoFinder = (ProdutoFinder) getBeanFactory().getBean("produtoFinder");
		Query qryEktProduto = produtoFinder.getEntityManager().createNativeQuery(sqlEktProduto, EKTProduto.class);
		qryEktProduto.setParameter("mesano", mesano);
		int ini = getThiz().getInicio();
		int fim = getThiz().getFim();
		qryEktProduto.setParameter("ini", ini);
		qryEktProduto.setParameter("fim", fim);
		List<EKTProduto> ektProdutos = qryEktProduto.getResultList();

		String sqlInsert = "INSERT INTO est_produto_saldo_historico(id,inserted,updated,version,estabelecimento_id,user_inserted_id,user_updated_id,"
				+ "mesano,produto_id,saldo_mes) VALUES(null,now(),now(),0,1,1,1,:mesano,:produto_id,:saldo_mes)";
		Query qryInsert = getEntityManager().createNativeQuery(sqlInsert);

		SimpleDateFormat sdfMesAno = new SimpleDateFormat("yyyyMM");
		Date dtMesAno = sdfMesAno.parse(mesano);

		for (EKTProduto ektProduto : ektProdutos) {
			logger.debug("ektProduto: " + ektProduto.getReduzido());

			Produto produtoBonERP = null;

			try {
				// Encontro o est_produto para o EKTProduto em questão...
				List<Produto> produtos = produtoFinder.findByReduzidoEkt(ektProduto.getReduzido().intValue(), dtMesAno);
				if (produtos == null || produtos.size() == 0) {
					logger.info("Nenhum produto encontrado com o reduzido " + ektProduto.getReduzido().intValue()
							+ " em " + sdfMesAno.format(dtMesAno));
					continue;
				} else {
					produtoBonERP = produtos.get(0);
				}
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}

			// Salvo na est_produto_saldo_historico
			qryInsert.setParameter("mesano", dtMesAno);
			qryInsert.setParameter("produto_id", produtoBonERP.getId());
			qryInsert.setParameter("saldo_mes", ektProduto.getQtdeTotal());

			logger.debug("qryInsert.executeUpdate();");
			qryInsert.executeUpdate();
			logger.debug("OK");

		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class, ViewException.class })
	public void deleteTudoDoMesAno() {
		// Apaga tudo do mesano para em seguida reimportar
		try {
			Date dtMesAno = new SimpleDateFormat("yyyyMM").parse(mesAnoImport);
			SimpleDateFormat sdfSQL = new SimpleDateFormat("yyyy-MM-dd");
			getEntityManager().createNativeQuery("DELETE FROM est_produto_saldo_historico WHERE mesano = '"
					+ sdfSQL.format(dtMesAno) + "'").executeUpdate();
		} catch (ParseException e1) {
			throw new IllegalStateException(e1);
		}
	}

	public static void doIt() {

		long ini = System.currentTimeMillis();

		taskExecutor.initialize();

		IGerarProdutoSaldoHistorico tDelete = (IGerarProdutoSaldoHistorico) context
				.getBean("gerarProdutoSaldoHistorico");
		tDelete.deleteTudoDoMesAno();

		int j = 500;
		for (int i = 0; i <= 12000; i += j) {
			IGerarProdutoSaldoHistorico t = (IGerarProdutoSaldoHistorico) context.getBean("gerarProdutoSaldoHistorico");
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

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		String patternMesAno = "([1-2]{1}[0-9]{3}(0{1}[1-9]{1}|1{1}[0-2]{1}))";

		while (true) {

			System.out.println("\n\n\n\n\n\n\nInforme o mesano (yyyyMM) ou 'SAIR': ");

			mesAnoImport = scanner.nextLine();

			if (StringUtils.regexMatch(patternMesAno, mesAnoImport)) {
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
