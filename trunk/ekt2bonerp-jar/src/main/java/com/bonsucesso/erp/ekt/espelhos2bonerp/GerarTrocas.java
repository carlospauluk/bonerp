package com.bonsucesso.erp.ekt.espelhos2bonerp;



import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import com.bonsucesso.erp.estoque.data.GradeFinder;
import com.bonsucesso.erp.estoque.data.ProdutoFinder;
import com.bonsucesso.erp.estoque.model.GradeTamanho;
import com.bonsucesso.erp.estoque.model.Produto;
import com.bonsucesso.erp.rh.data.FuncionarioFinder;
import com.bonsucesso.erp.rh.model.Funcionario;
import com.bonsucesso.erp.vendas.data.PlanoPagtoFinder;
import com.bonsucesso.erp.vendas.data.TipoVendaFinder;
import com.bonsucesso.erp.vendas.data.VendaDataMapper;
import com.bonsucesso.erp.vendas.data.VendaFinder;
import com.bonsucesso.erp.vendas.model.PlanoPagto;
import com.bonsucesso.erp.vendas.model.TipoVenda;
import com.bonsucesso.erp.vendas.model.Venda;
import com.bonsucesso.erp.vendas.model.VendaItem;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.security.spring.SecurityUtils;
import com.ocabit.utils.calendar.CalendarUtil;
import com.ocabit.utils.currency.CurrencyUtils;
import com.ocabit.utils.strings.StringUtils;


@Component("gerarTrocas")
@Scope("prototype") // para poder rodar com thread (não retorna sempre o mesmo bean)
public class GerarTrocas implements IGerarTrocas {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected static Logger logger = Logger.getLogger(GerarTrocas.class);

	public static ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

	public static ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");

	@Autowired
	private BeanFactory beanFactory;

	private EntityManager entityManager;

	private IGerarTrocas thiz;

	private static String mesAnoImport;

	private int inicio;

	private int fim;

	public static int totalTrocasSalvas = 0;

	@Autowired
	private ProdutoFinder produtoFinder;

	@Autowired
	private VendaDataMapper vendaDataMapper;

	@Autowired
	private VendaFinder vendaFinder;

	@Autowired
	private GradeFinder gradeFinder;

	@Autowired
	private FuncionarioFinder funcionarioFinder;

	@Autowired
	private PlanoPagtoFinder planoPagtoFinder;

	@Autowired
	private TipoVendaFinder tipoVendaFinder;

	private GradeTamanho gradeTamanhoKG;

	private Funcionario vendedor99;

	private PlanoPagto planoPagtoTroca;

	private TipoVenda tipoVendaTroca;

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
	public IGerarTrocas getThiz() {
		// aqui tive que fazer desta forma por causa das threads. o Thiz é passado no main.
		return thiz;
	}

	@Override
	public void setThiz(IGerarTrocas thiz) {
		this.thiz = thiz;
	}

	@Override
	public void run() {
		try {
			getThiz().corrigir(mesAnoImport);
		} catch (Exception e) {
			throw new IllegalStateException("Erro no run()", e);
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
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void deletarTrocasNoMesano() throws Exception {
		logger.debug("Deletando trocas do mesano: " + mesAnoImport);

		String sqlDeletaTrocasNoMesano = "DELETE FROM ven_venda WHERE tipo_venda_id = 2 AND mesano = '" + mesAnoImport
				+ "'";
		getProdutoFinder().getEntityManager().createNativeQuery(sqlDeletaTrocasNoMesano).executeUpdate();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void corrigir(final String mesano) throws Exception {

		logger.debug("Corrigindo para o mesano: " + mesano + ". THREAD: " + getInicio() + "/" + getFim());
		SecurityUtils.mockUser();

		// Primeira venda no BonERP: 2014-01-02

		// Seleciona todos os ekt_produto do mesano

		// Verifica se AC99 do mesano tem mais ou menos do que o sum(qtde) dos ekt_venda_item
		// >> mais: problema
		// >> menos: houve trocas
		// >>>>>>>>>> gerar venda com negativo para o produto

		// Pego todos os 'EKTProduto' do 'mesano'
		List<EKTProduto> ektProdutos = getThiz().findEKTProdutos(mesano);

		for (EKTProduto ektProduto : ektProdutos) {
			try {
				getThiz().handleEKTProduto(ektProduto, mesano);
			} catch (Exception e) {
				logger.error("Erro no handleEKTProduto(EKTProduto ektProduto: " + ektProduto.getReduzido()
						+ ", String mesano: " + mesano
						+ ")", e);

			}
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = ViewException.class)
	public void handleEKTProduto(EKTProduto ektProduto, String mesano) throws ViewException {

		if (ektProduto == null || ektProduto.getReduzido() == null || (!(ektProduto.getReduzido() instanceof Double))) {
			throw new ViewException("Reduzido errado.");
		}

		// Verifica a qtde total vendida no EKT
		String sqlTotalVendasEktProduto = "SELECT sum(qtde) FROM EKTVendaItem WHERE produto = :produto AND mesano = :mesano";
		Query qryTotalVendasEktProduto = getEntityManager().createQuery(sqlTotalVendasEktProduto);

		logger.info("handleEKTProduto(EKTProduto ektProduto: " + ektProduto.getReduzido() + ", String mesano: " + mesano
				+ ")");

		// mesano é passado como, ex.: 201402
		Date dtMesAno = null;
		try {
			dtMesAno = new SimpleDateFormat("yyyyMM").parse(mesano);
		} catch (ParseException e) {
			throw new IllegalStateException(e);
		}

		final Date dtMesAnoAnterior = CalendarUtil.ultimoDiaNoMesAno(CalendarUtil.incMes(dtMesAno, -1));
		final String mesanoAnterior = new SimpleDateFormat("yyyyMM").format(dtMesAnoAnterior);
		logger.debug("mesanoanterior: " + mesanoAnterior);

		qryTotalVendasEktProduto.setParameter("produto", ektProduto.getReduzido());
		qryTotalVendasEktProduto.setParameter("mesano", new SimpleDateFormat("yyyyMM").format(dtMesAnoAnterior));
		// Pega o total vendido do produto no mesano anterior no EKT
		Double totalVendido = (Double) qryTotalVendasEktProduto.getSingleResult();

		logger.debug("EKT PRODUTO: " + ektProduto.getReduzido().intValue() + " - " + ektProduto.getDescricao()
				+ " >> TOTAL VENDIDO: " + totalVendido);

		if (totalVendido == null) {
			logger.debug("NULL");
			return;
		} else {
			BigDecimal bdTotalVendido = CurrencyUtils.getBigDecimalCurrency(totalVendido);
			// Pega a qtde AC do mesano anterior no EKT
			Double qtdeAcNoMesAnoAnterior = getAc(ektProduto, CalendarUtil.getCalendar(dtMesAnoAnterior)
					.get(Calendar.MONTH) + 1);
			BigDecimal bdQtdeAcNoMesAnoAnterior = CurrencyUtils.getBigDecimalCurrency(qtdeAcNoMesAnoAnterior);

			BigDecimal dif = bdTotalVendido.subtract(bdQtdeAcNoMesAnoAnterior); // basicament informa a qtde de trocas

			logger.debug("QTDE AC MÊS ANTERIOR: " + bdQtdeAcNoMesAnoAnterior.doubleValue());
			logger.debug("TOTAL VENDIDO MÊS ANTERIOR: " + bdTotalVendido.doubleValue());

			// Verifica a relação entre a qtdeAC e a qtdeVendida do EKT...

			// Se tem mais vendido que acumulado, é porque houve troca
			if (bdQtdeAcNoMesAnoAnterior.doubleValue() < bdTotalVendido.doubleValue()) { // dif > 1

				logger.debug("................................ TROCA");

				// Pega o produto BonERP
				logger.debug("getProdutoFinder().findByReduzidoEkt(ektProduto.getReduzido().intValue()='"
						+ ektProduto.getReduzido().intValue() + "', dtMesAno='" + dtMesAno + ");");
				List<Produto> produtos = getProdutoFinder()
						.findByReduzidoEkt(ektProduto.getReduzido().intValue(), dtMesAno);
				logger.debug("OK");
				if (produtos == null || produtos.isEmpty() || produtos.size() > 1) {
					throw new IllegalStateException("Produto (BonERP) não encontrado para o reduzido "
							+ ektProduto.getReduzido().intValue() + " no mesano " + mesano);
				}
				Produto produtoBonERP = produtos.get(0);

				logger.debug("PRODUTO BONERP: " + produtoBonERP.getReduzido() + " - "
						+ produtoBonERP.getDescricao());

				// Pega a qtdeVendida no BonERP
				logger.debug("getVendaFinder().findQtdeItensVendidosBy(produtoBonERP='" + produtoBonERP.getReduzido()
						+ "', dtMesAnoAnterior='" + dtMesAnoAnterior + "')");
				BigDecimal qtdeProdutosVendidosBonERP = getVendaFinder()
						.findQtdeItensVendidosBy(produtoBonERP, dtMesAnoAnterior);
				logger.debug("OK");
				qtdeProdutosVendidosBonERP = qtdeProdutosVendidosBonERP == null ? BigDecimal.ZERO
						: qtdeProdutosVendidosBonERP;

				// Verifica se a qtde vendida no EKT é diferente da do BONERP (pois já pode ter sido corrigido em execução anterior)
				if (qtdeProdutosVendidosBonERP.doubleValue() > bdQtdeAcNoMesAnoAnterior.doubleValue()) {

					logger.debug("saveTroca(mesano='" + mesano + "', dif='" + dif + "', produtoBonERP='"
							+ produtoBonERP.getReduzido() + "')");
					getThiz()
							.saveTroca(mesano, dif, produtoBonERP);
					logger.debug("... OK!!!");

				} else if (qtdeProdutosVendidosBonERP.doubleValue() < bdQtdeAcNoMesAnoAnterior.doubleValue()) {
					logger.debug("ERRO, qtdeProdutosVendidos no BonERP não pode ser menor que o ACUMULADO NO EKT (Foi importadas todas as vendas do mesano? Ou foi geradas trocas a mais?");
				} else {
					logger.debug("TUDO CERTO aqui...");
				}

			} else if (bdQtdeAcNoMesAnoAnterior.doubleValue() > bdTotalVendido.doubleValue()) { // dif < 1
				logger.debug(ektProduto.getReduzido() + " - " + ektProduto.getDescricao());
				logger.debug("ERRO, qtdeAC mês anterior não pode ser maior que o total vendido");
			} else { // dif == 0
				logger.debug("TUDO CERTO, próximo...");
			}
			logger.debug("----------------------------------------------------------------");
		}

		//			

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public List<EKTProduto> findEKTProdutos(final String mesano) {
		String sqlEktProduto = "SELECT * FROM ekt_produto WHERE mesano = :mesano ORDER BY id LIMIT :ini,:fim";

		Query qryEktProduto = getProdutoFinder().getEntityManager().createNativeQuery(sqlEktProduto, EKTProduto.class);
		qryEktProduto.setParameter("mesano", mesano);
		int ini = getThiz().getInicio();
		int fim = getThiz().getFim();
		qryEktProduto.setParameter("ini", ini);
		qryEktProduto.setParameter("fim", fim);
		@SuppressWarnings("unchecked")
		List<EKTProduto> ektProdutos = qryEktProduto.getResultList();
		return ektProdutos;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveTroca(String mesano, BigDecimal dif, Produto produtoBonERP)
			throws ViewException {
		logger.debug("GERANDO 'VENDA DE TROCA'");

		// Gerar "Venda de Troca"
		Venda troca = new Venda();
		try {
			troca.setDtVenda(new SimpleDateFormat("yyyyMM").parse(mesano));
		} catch (ParseException e) {
			throw new ViewException("Erro ao parsear o mesano");
		}
		troca.setMesano(mesano);
		troca.setPlanoPagto(getPlanoPagtoTroca());
		troca.setDeletado(false);
		troca.setTipo(getTipoVendaTroca());
		troca.setSubTotal(BigDecimal.ZERO);
		troca.setValorTotal(BigDecimal.ZERO);
		troca.setVendedor(getVendedor99());
		troca.setDescontoEspecial(BigDecimal.ZERO);
		troca.setDescontoPlano(BigDecimal.ZERO);

		VendaItem vendaItem = new VendaItem();
		vendaItem.setVenda(troca);
		vendaItem.setProduto(produtoBonERP);
		vendaItem.setAlteracaoPreco(false);
		vendaItem.setGradeTamanho(getGradeTamanhoKG());
		vendaItem.setQtde(dif);
		vendaItem.setPrecoVenda(BigDecimal.ZERO);

		troca.getItens().add(vendaItem);

		logger.debug("SALVANDO 'VENDA DE TROCA'...");
		getVendaDataMapper().save(troca);
		getVendaDataMapper().getEntityManager().flush();
		totalTrocasSalvas++;
	}

	@Override
	public Double getAc(EKTProduto ektProduto, Integer mes) {
		switch (mes) {
			case 1:
				return ektProduto.getAc01();
			case 2:
				return ektProduto.getAc02();
			case 3:
				return ektProduto.getAc03();
			case 4:
				return ektProduto.getAc04();
			case 5:
				return ektProduto.getAc05();
			case 6:
				return ektProduto.getAc06();
			case 7:
				return ektProduto.getAc07();
			case 8:
				return ektProduto.getAc08();
			case 9:
				return ektProduto.getAc09();
			case 10:
				return ektProduto.getAc10();
			case 11:
				return ektProduto.getAc11();
			case 12:
				return ektProduto.getAc12();
			default:
				throw new IllegalStateException("mes deve ser entre 1 e 12");
		}
	}

	public ProdutoFinder getProdutoFinder() {
		return produtoFinder;
	}

	public void setProdutoFinder(ProdutoFinder produtoFinder) {
		this.produtoFinder = produtoFinder;
	}

	public VendaDataMapper getVendaDataMapper() {
		return vendaDataMapper;
	}

	public void setVendaDataMapper(VendaDataMapper vendaDataMapper) {
		this.vendaDataMapper = vendaDataMapper;
	}

	public VendaFinder getVendaFinder() {
		return vendaFinder;
	}

	public void setVendaFinder(VendaFinder vendaFinder) {
		this.vendaFinder = vendaFinder;
	}

	public GradeFinder getGradeFinder() {
		return gradeFinder;
	}

	public void setGradeFinder(GradeFinder gradeFinder) {
		this.gradeFinder = gradeFinder;
	}

	public FuncionarioFinder getFuncionarioFinder() {
		return funcionarioFinder;
	}

	public void setFuncionarioFinder(FuncionarioFinder funcionarioFinder) {
		this.funcionarioFinder = funcionarioFinder;
	}

	public PlanoPagtoFinder getPlanoPagtoFinder() {
		return planoPagtoFinder;
	}

	public void setPlanoPagtoFinder(PlanoPagtoFinder planoPagtoFinder) {
		this.planoPagtoFinder = planoPagtoFinder;
	}

	public TipoVendaFinder getTipoVendaFinder() {
		return tipoVendaFinder;
	}

	public void setTipoVendaFinder(TipoVendaFinder tipoVendaFinder) {
		this.tipoVendaFinder = tipoVendaFinder;
	}

	public GradeTamanho getGradeTamanhoKG() {
		if (gradeTamanhoKG == null) {
			try {
				this.gradeTamanhoKG = getGradeFinder().findByCodigoGradeAndOrdem(12, 1);
			} catch (ViewException e) {
				logger.error(e);
				throw new IllegalStateException(e);
			}
		}
		return gradeTamanhoKG;
	}

	public void setGradeTamanhoKG(GradeTamanho gradeTamanhoKG) {
		this.gradeTamanhoKG = gradeTamanhoKG;
	}

	public Funcionario getVendedor99() {
		if (vendedor99 == null) {
			try {
				this.vendedor99 = getFuncionarioFinder().findByCodigo(50);
			} catch (ViewException e) {
				logger.error(e);
				throw new IllegalStateException(e);
			}
		}
		return vendedor99;
	}

	public void setVendedor99(Funcionario vendedor99) {
		this.vendedor99 = vendedor99;
	}

	public PlanoPagto getPlanoPagtoTroca() {
		if (planoPagtoTroca == null) {
			try {
				this.planoPagtoTroca = getPlanoPagtoFinder().findBy("10.00");
			} catch (ViewException e) {
				logger.error(e);
				throw new IllegalStateException(e);
			}
		}
		return planoPagtoTroca;
	}

	public void setPlanoPagtoTroca(PlanoPagto planoPagtoTroca) {
		this.planoPagtoTroca = planoPagtoTroca;
	}

	public TipoVenda getTipoVendaTroca() {
		if (tipoVendaTroca == null) {
			try {
				this.tipoVendaTroca = getTipoVendaFinder().findById(2l);
			} catch (ViewException e) {
				logger.error(e);
				throw new IllegalStateException(e);
			}
		}
		return tipoVendaTroca;
	}

	public void setTipoVendaTroca(TipoVenda tipoVendaTroca) {
		this.tipoVendaTroca = tipoVendaTroca;
	}

	public static void doIt() {

		long ini = System.currentTimeMillis();

		taskExecutor.initialize();

		try {
			IGerarTrocas tDeletar = (IGerarTrocas) context.getBean("gerarTrocas");
			tDeletar.setThiz(tDeletar);
			tDeletar.deletarTrocasNoMesano();
		} catch (Exception e1) {
			System.out.println();
		}

		GerarTrocas.totalTrocasSalvas = 0;

		int j = 500;
		for (int i = 0; i <= 12000; i += j) {
			//		for (int i = 0; i < 500; i += j) {
			IGerarTrocas t = (IGerarTrocas) context.getBean("gerarTrocas");
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

		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>> TOTAL TROCAS: " + totalTrocasSalvas);

		long fim = System.currentTimeMillis();
		double tempo = (fim - ini) / 1000;
		logger.info("Tempo de exec.: " + tempo + " segundos");
	}

	public static void main(String[] args) {

		if (args != null && args.length > 0) {
			mesAnoImport = args[0];

			try {
				if (mesAnoImport.length() > 6) {
					throw new IllegalStateException();
				}
				Integer.parseInt(mesAnoImport);
			} catch (Exception e1) {
				System.out.println("Erro ao parsear o mesImport");
				System.out.println("Informe o mês import. Formato: YYYYMM");
				System.exit(0);
			}
		}

		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

		String patternMesAno = "([1-2]{1}[0-9]{3}(0{1}[1-9]{1}|1{1}[0-2]{1}))";

		while (true) {

			System.out.println("\n\n\n\n\n\n\nInforme o mesano (yyyyMM) ou 'SAIR': ");

			if (mesAnoImport == null) {
				mesAnoImport = scanner.nextLine();

				String mesAno = mesAnoImport;

				if (StringUtils.regexMatch(patternMesAno, mesAnoImport)) {
					System.out.println("mesano a importar: " + mesAnoImport + ". Confirma (S/N) ?");
					String sn = scanner.nextLine();
					if ("S".equalsIgnoreCase(sn)) {
						doIt();
						System.out.println("\n\n\n\n\nmesano importado com sucesso: " + mesAno);
					}
					System.out.println(sn);
				} else if ("SAIR".equals(mesAnoImport)) {
					System.out.println("Saindo");
					System.exit(0);
				} else {
					System.out.println("???");
				}
			} else {
				// se passou por linha de comando
				doIt();
				System.out.println("Saindo");
				System.exit(0);
			}

		}

	}

}
