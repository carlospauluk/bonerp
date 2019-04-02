package com.bonsucesso.erp.ekt.espelhos2bonerp;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.ekt.data.EKTProdutoFinder;
import com.bonsucesso.erp.ekt.model.EKTProduto;
import com.bonsucesso.erp.estoque.business.ProdutoBusiness;
import com.bonsucesso.erp.estoque.data.DeptoFinder;
import com.bonsucesso.erp.estoque.data.FornecedorFinder;
import com.bonsucesso.erp.estoque.data.GradeFinder;
import com.bonsucesso.erp.estoque.data.ProdutoDataMapper;
import com.bonsucesso.erp.estoque.data.ProdutoFinder;
import com.bonsucesso.erp.estoque.data.SubdeptoFinder;
import com.bonsucesso.erp.estoque.data.UnidadeProdutoFinder;
import com.bonsucesso.erp.estoque.model.Departamento;
import com.bonsucesso.erp.estoque.model.Fornecedor;
import com.bonsucesso.erp.estoque.model.Grade;
import com.bonsucesso.erp.estoque.model.GradeTamanho;
import com.bonsucesso.erp.estoque.model.Produto;
import com.bonsucesso.erp.estoque.model.ProdutoPreco;
import com.bonsucesso.erp.estoque.model.ProdutoSaldo;
import com.bonsucesso.erp.estoque.model.Subdepartamento;
import com.bonsucesso.erp.estoque.model.UnidadeProduto;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.utils.calendar.CalendarUtil;
import com.ocabit.utils.currency.CurrencyUtils;
import com.ocabit.utils.strings.Mailer;
import com.ocabit.utils.strings.StringUtils;


@Component("importarProdutos2BonERP")
@Scope("prototype") // para poder rodar com thread (não retorna sempre o mesmo bean)
public class ImportarProdutos2BonERP implements IImportarProdutos2BonERP {

	/**
	 *
	 */
	private static final long serialVersionUID = 8041341995624483984L;

	protected static Logger logger = Logger.getLogger(ImportarProdutos2BonERP.class);

	public static SimpleDateFormat sdfLog = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

	@Value("${config.logsdir}")
	private String dirLogs;

	private String logFile;

	public static PrintWriter w;

	@Autowired
	private BeanFactory beanFactory;

	// ------ DATA DO MÊS SENDO IMPORTADO
	private static Date dtMesImport;

	private static String mesImport;

	public static SimpleDateFormat sdfMesAno = new SimpleDateFormat("yyyyMM");

	private IImportarProdutos2BonERP thiz;

	private int inicio;
	private int fim;

	@Autowired
	private ProdutoFinder produtoFinder;

	@Autowired
	private ProdutoDataMapper produtoDataMapper;

	@Autowired
	private EKTProdutoFinder ektProdutoFinder;

	@Autowired
	private ProdutoBusiness produtoBusiness;

	public static int g = 0; // geral
	public static int c = 0; // cadastrados
	public static int a = 0; // atualizados
	public static int s = 0; // substituídos

	@Override
	public void run() {
		final Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		final org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User("carlos", "none", true, true, true, true, authorities);
		Authentication auth = new TestingAuthenticationToken(user, authorities);
		SecurityContextHolder.getContext().setAuthentication(auth);

		getThiz().importarProdutos();
	}

	@Override
	public void show() {
		logger.info("inicio: " + getInicio());
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, timeout = 15)
	public void deletarSaldos() {
		try {
			getProdutoDataMapper().getEntityManager().createNativeQuery("TRUNCATE TABLE est_produto_saldo")
					.executeUpdate();
			getProdutoDataMapper().getEntityManager().flush();
		} catch (Exception e) {
			String err = "ERRO AO DELETAR SALDOS";
			logger.error(err, e);
			w.println(err);
			w.println(StringUtils.stackTraceToString(e));
			w.println();
			throw new IllegalStateException(err, e);
		}
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, timeout = 15)
	public void importarProdutos() {

		try {
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  INICIANDO IMPORTAÇÃO DE PRODUTOS [THREAD:"
					+ getInicio()
					+ "]");
			logger.info(sdfMesAno.format(dtMesImport));

			List<EKTProduto> ektProdutos = getEktProdutoFinder().findAllLimit(getInicio(), getFim(), mesImport);
			// A linha abaixo é para tentar importar um produto em específico.
			// List<EKTProduto> ektProdutos = java.util.Arrays.asList(getEktProdutoFinder().findByReduzido(4612, mesImport));

			// percorre todos os produtos do EKT
			for (EKTProduto ektProduto : ektProdutos) {
				getThiz().importarEKTProduto(ektProduto);
				getProdutoDataMapper().getEntityManager().flush();
			}

			w.println("PRODUTOS PROCESSADOS [THREAD:" + getInicio() + "] ............................ " + g);
			w.println("PRODUTOS CADASTRADOS [THREAD:" + getInicio() + "] ............................ " + c);
			w.println("PRODUTOS ATUALIZADOS [THREAD:" + getInicio() + "] ............................ " + a);
			w.println("PRODUTOS SUBSTITUÍDOS [THREAD:" + getInicio() + "] ........................... " + s);
			w.println();
			w.println();
			w.println();

		} catch (Exception e) {
			String err = "ERRO AO IMPORTAR PRODUTOS SALDOS [THREAD:" + getInicio() + "]";
			logger.error(err, e);
			w.println(err);
			w.println(StringUtils.stackTraceToString(e));
			w.println();
			throw new IllegalStateException(err, e);
		}

		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  FINALIZANDO IMPORTAÇÃO DE PRODUTOS [THREAD:" + getInicio()
				+ "]");

	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, timeout = 360)
	public void importarEKTProduto(EKTProduto ektProduto) throws ViewException {
		logger.info(ektProduto.toStringToView());

		// Verifica produtos com mesmo reduzidoEKT
		List<Produto> produtosComMesmoReduzidoEKT = getProdutoFinder()
				.findByReduzidoEkt(ektProduto.getReduzido().intValue(), null);

		// Se não tem nenhum, cadastra o novo produto.
		if ((produtosComMesmoReduzidoEKT == null) || (produtosComMesmoReduzidoEKT.size() == 0)) {
			getThiz().cadastrarNovoProduto(ektProduto);
			c++;
		} else {
			// se já tem, verifica se algum deles é o mesmo sendo importado novamente
			boolean achouMesmo = false;
			for (Produto mesmoReduzido : produtosComMesmoReduzidoEKT) {
				if (ektProduto.equalsTo(mesmoReduzido) ||
						(mesmoReduzido.getReduzidoEktAte() == null
								&& mesmoReduzido.getReduzidoEktDesde().equals(dtMesImport))) {
					logger.info("PRODUTO JÁ EXISTENTE");
					achouMesmo = true;
					mesmoReduzido = getThiz().atualizaProduto(ektProduto, mesmoReduzido);

					mesmoReduzido = getThiz().salvarGrade(ektProduto, mesmoReduzido);

					a++;
					logger.info("QTDE NO EKT: " + ektProduto.getQtdeTotal());
					logger.info("updateQtdeTotal(mesmoReduzido = '" + mesmoReduzido + "');");
					mesmoReduzido = getProdutoBusiness().updateQtdeTotal(mesmoReduzido);
					logger.info("QTDE SALVA: " + mesmoReduzido.getQtdeTotal());
					if (ektProduto.getQtdeTotal().doubleValue() != mesmoReduzido.getQtdeTotal().doubleValue()) {
						logger.info("************ ATENÇÃO: qtdes divergindo");
					}
					getThiz().acertaPeriodosReduzidoEKT(ektProduto.getReduzido().intValue());
					break;
				}
			}
			if (!achouMesmo) {
				// não é o mesmo produto mas já tem outro(s) com o mesmo reduzido
				Produto produto = getThiz().cadastrarNovoProduto(ektProduto);

				produto = getThiz().salvarGrade(ektProduto, produto);

				getThiz().acertaPeriodosReduzidoEKT(ektProduto.getReduzido().intValue());

				getProdutoBusiness().updateQtdeTotal(produto);

				logger.info("QTDE NO EKT: " + ektProduto.getQtdeTotal());
				logger.info("QTDE SALVA: " + produto.getQtdeTotal());

				if (ektProduto.getQtdeTotal().doubleValue() != produto.getQtdeTotal().doubleValue()) {
					logger.info("************ ATENÇÃO: qtdes divergindo");
				}
				s++;
			}
		}

		logger.info(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + g++
				+ " produto(s) processado(s) >> [THREAD:" + getInicio() + "]");
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED) // , timeout = 15)
	public void acertarDeAteProdutos() {
		try {
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> acertarDeAteProdutos");
			logger.info(sdfMesAno.format(dtMesImport));

			final Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			final org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User("carlos", "none", true, true, true, true, authorities);
			Authentication auth = new TestingAuthenticationToken(user, authorities);
			SecurityContextHolder.getContext().setAuthentication(auth);

			// Encontro todos os produtos que sejam do mesmo REDUZIDO_EKT e que nenhum deles tenha reduzido_ekt_ate = NULL
			String sql = "SELECT DISTINCT(reduzido_ekt) FROM est_produto WHERE reduzido_ekt IS NOT NULL"; // WHERE reduzido_ekt = 2083";

			final Query qry = getProdutoFinder().getEntityManager().createNativeQuery(sql);
			@SuppressWarnings("rawtypes")
			List reduzidosEkt = qry.getResultList();

			logger.info("Produtos sem 'reduzido_ekt_ate' encontrados: " + reduzidosEkt.size());

			// percorre todos os produtos do EKT
			for (Object reduzidoEkt : reduzidosEkt) {

				logger.info("Acertando " + reduzidoEkt);

				// procuro o mesmo produto na tabela do EKT
				List<Produto> produtos = getProdutoFinder().findByReduzidoEkt((Integer) reduzidoEkt, null);

				// Se tiver mais de um produto
				if (produtos.size() > 1) {
					for (int i = 0; i < produtos.size() - 1; i++) {

						Produto atual = produtos.get(i);
						Produto proximo = produtos.get(i + 1);

						Date ultimoDiaMesAnterior = CalendarUtil
								.ultimoDiaNoMesAno(CalendarUtil.incMes(proximo.getReduzidoEktDesde(), -1));

						// Se o reduzidoEktAte do atual estiver inconsistente com o reduzidoEktDesde do proximo, trabalha na correção
						if (atual.getReduzidoEktAte() == null
								|| CalendarUtil.difBetweenDates(atual.getReduzidoEktAte(), ultimoDiaMesAnterior) != 0) {

							SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

							// Pega todos os EKTProduto no intervalo dos mesesAnos
							List<EKTProduto> ektProdsErr = getEktProdutoFinder().findBetweenMesesAnos(atual
									.getReduzidoEkt(), atual.getReduzidoEktDesde(), proximo.getReduzidoEktDesde());
							for (EKTProduto ektProdErr : ektProdsErr) {
								if (!ektProdErr.equalsTo(atual)) {
									try {
										Date ultimoDiaMesAnteriorr = CalendarUtil
												.ultimoDiaNoMesAno(CalendarUtil
														.incMes(sdf.parse(ektProdErr.getMesano()), -1));

										atual.setReduzidoEktAte(ultimoDiaMesAnteriorr);
										getProdutoDataMapper().save(atual);
										break;
									} catch (ParseException e) {
										throw new IllegalStateException(e);
									}
								}
							}
						}

					}
					Collections.sort(produtos, new Comparator<Produto>() {

						@Override
						public int compare(Produto o1, Produto o2) {
							return new CompareToBuilder()
									.append(o2.getReduzidoEktDesde(), o1.getReduzidoEktDesde())
									.toComparison();
						}
					});
				}

				// como ordenei ao contrário, pego o "primeiro" e marco o reduzido_ekt_ate como null
				Produto ultimo = produtos.get(0);
				if (ultimo.getReduzidoEktAte() != null) {
					ultimo.setReduzidoEktAte(null);
					getProdutoDataMapper().save(ultimo);
				}
			}

		} catch (ViewException e) {
			String err = "ERRO AO acertarDeAteProdutos()";
			logger.error(err, e);
			w.println(err);
			w.println(StringUtils.stackTraceToString(e));
			w.println();
			throw new IllegalStateException(err, e);
		}
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, timeout = 15)
	public void marcarProdutosInativos() {
		try {
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> marcarProdutosInativos [THREAD:"
					+ getInicio()
					+ "]");
			logger.info(sdfMesAno.format(dtMesImport));

			final Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			final org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User("carlos", "none", true, true, true, true, authorities);
			Authentication auth = new TestingAuthenticationToken(user, authorities);
			SecurityContextHolder.getContext().setAuthentication(auth);

			@SuppressWarnings("unchecked")
			List<Object[]> prods = getProdutoFinder().getEntityManager()
					.createNativeQuery("SELECT id, reduzido_ekt FROM est_produto WHERE reduzido_ekt_ate IS NULL")
					.getResultList();
			logger.info("Produtos encontrados: " + prods.size());

			int i = 0;

			// percorre todos os produtos do BonERP
			for (Object[] p : prods) {

				BigInteger produtoId = (BigInteger) p[0];
				Integer reduzidoEkt = (Integer) p[1];

				logger.info("PESQUISANDO produtoId: " + produtoId + " - reduzidoEkt: " + reduzidoEkt);

				// procuro o mesmo produto na tabela do EKT
				EKTProduto ektProduto = null;
				try {
					ektProduto = getEktProdutoFinder().findByReduzido(reduzidoEkt, mesImport);
				} catch (Exception e) {
					logger.error("Erro findByReduzido(reduzidoEkt, mesImport) >> " + reduzidoEkt + " : " + mesImport);
					continue;
				}

				if (ektProduto == null) {
					logger.info("Produto: " + reduzidoEkt + " não encontrado");
					Produto produto = getProdutoFinder().findById(produtoId.longValue());
					produto.setReduzidoEktAte(CalendarUtil.ultimoDiaNoMesAno(CalendarUtil.incMes(dtMesImport, -1)));
					getProdutoDataMapper().save(produto);
					i++;
				}

			}

			logger.info(i + " produto(s) foram 'inativados'");

		} catch (Exception e) {
			String err = "ERRO AO marcarProdutosInativos() [THREAD:" + getInicio() + "]";
			logger.error(err, e);
			w.println(err);
			w.println(StringUtils.stackTraceToString(e));
			w.println();
			throw new IllegalStateException(err, e);
		}

		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> marcarProdutosInativos [THREAD:" + getInicio()
				+ "]");
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, timeout = 15)
	public Produto cadastrarNovoProduto(EKTProduto ektProduto) throws ViewException {

		try {
			logger.info(" ________________________ CADASTRANDO NOVO PRODUTO ");

			Produto produto = new Produto();

			produto.setDeptoImportado(getThiz().findDepto(ektProduto));
			produto.setSubdepto(getThiz().findSubdepto(ektProduto));
			produto.setSubdeptoErr("" + ektProduto.getSubdepto().intValue());

			Fornecedor fornecedor = getFornecedorFinder()
					.findByCodigoEKT(ektProduto.getFornec().intValue(), dtMesImport);
			produto.setFornecedor(fornecedor);

			produto.setDescricao(ektProduto.getDescricao());
			produto.setDtUltVenda(ektProduto.getDataUltVenda());
			produto.setGrade(getThiz().findGrade(ektProduto));
			produto.setGradeErr("" + ektProduto.getGrade().intValue());

			String reduzido = new SimpleDateFormat("YYMM").format(dtMesImport)
					+ StringUtils.zerofill("" + ektProduto.getReduzido().intValue(), 6);
			produto.setReduzido(Long.parseLong(reduzido));
			produto.setReduzidoEkt(ektProduto.getReduzido().intValue());
			produto.setReduzidoEktDesde(dtMesImport);

			produto.setReferencia(ektProduto.getReferencia());
			produto.setUnidadeProduto(getThiz().findUnidadeProduto(ektProduto));
			produto.setUnidadeProdutoErr(ektProduto.getUnidade());

			produto.setCst("102"); // ektProduto.getCst());
			produto.setIcms(0); // ektProduto.getIcms().intValue());
			produto.setNcm(ektProduto.getNcm() != null ? ektProduto.getNcm() : "62179000");
			produto.setFracionado(ektProduto.getFracionado() == null || ektProduto.getFracionado().equals("S"));
			produto.setTipoTributacao("T"); // ektProduto.getTipoTrib());

			// salvar o preço
			ProdutoPreco preco = new ProdutoPreco();
			preco.setMesano(dtMesImport);
			preco.setProduto(produto);
			preco.setCoeficiente(CurrencyUtils.getBigDecimalScaled(ektProduto.getCoef(), 3));
			preco.setCustoOperacional(CurrencyUtils.getBigDecimalScaled(ektProduto.getMargemC(), 3));
			preco.setCustoFinanceiro(new BigDecimal("0.15"));
			preco.setMargem(CurrencyUtils.getBigDecimalScaled(ektProduto.getMargem(), 3));
			preco.setDtCusto(ektProduto.getDataPCusto() != null ? ektProduto.getDataPCusto() : dtMesImport);
			preco.setDtPrecoVenda(ektProduto.getDataPVenda() != null ? ektProduto.getDataPVenda() : dtMesImport);
			preco.setPrazo(ektProduto.getPrazo() != null ? ektProduto.getPrazo().intValue() : 0);
			preco.setPrecoCusto(CurrencyUtils.getBigDecimalCurrency(ektProduto.getpCusto()));
			preco.setPrecoPrazo(CurrencyUtils.getBigDecimalCurrency(ektProduto.getpPrazo()));
			preco.setPrecoPromo(CurrencyUtils.getBigDecimalCurrency(ektProduto.getpPromo()));
			preco.setPrecoVista(CurrencyUtils.getBigDecimalCurrency(ektProduto.getpVista()));
			produto.addPreco(preco);

			logger.info(" ________________________ save no CADASTRANDO NOVO PRODUTO ");
			produto = getProdutoDataMapper().save(produto);
			logger.info(" ________________________ OK ");

			if (produto.getSaldos().size() > 0) {
				logger.info(" PRODUTO JÁ TEM SALDO?????????");
			}

			return produto;

		} catch (Exception e) {
			String err = "ERRO AO CADASTRAR PRODUTO: " + ektProduto.toStringToView() + " [THREAD:" + getInicio() + "]";
			logger.error(err, e);
			w.println(err);
			w.println(StringUtils.stackTraceToString(e));
			w.println();
			throw new IllegalStateException(err, e);
		}

	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, timeout = 15)
	public Produto salvarGrade(EKTProduto ektProduto, Produto produto) throws ViewException {
		try {
			logger.info(">>>>>>>>>>>>>>>>> SALVANDO GRADE PRODUTO: " + ektProduto.toStringToView() + " [THREAD:"
					+ getInicio()
					+ "]");
			// logger.info("refresh no produto...");
			// produto = getProdutoFinder().refresh(produto);
			// logger.info("ok...");;

			if (!produto.getSaldos().isEmpty()) {
				throw new ViewException("Já tem saldo: " + produto.toStringToView());
			}

			logger.info(">>>>>>>>>>>>>>>>> 111");
			// tive que dar refresh pois estava com um comportamente estranho, algumas vezes duplicava ou triplicava os GradeTamanho dentro do "tamanhos" da grade
			//			logger.info("refresh na grade...");
			//			Grade grade = getGradeFinder().refresh(produto.getGrade());
			//			logger.info("ok");;
			// tirando o refresh porque parecia que estava dando um lock
			int qtdeTamanhos = produto.getGrade().getTamanhos().size();
			//			getGradeFinder().getEntityManager().detach(grade);

			// As vezes tem quantidades em tamanhos além daqueles da grade. 
			// Verifico isso e adiciono o acumulado no primeiro tamanho que encontrar.
			double acumulado = 0.0;
			if (qtdeTamanhos < 12 && ((ektProduto.getQt12() != null) && (ektProduto.getQt12() != 0.0))) {
				acumulado += ektProduto.getQt12();
				ektProduto.setQt12(null);
			}
			if (qtdeTamanhos < 11 && ((ektProduto.getQt11() != null) && (ektProduto.getQt11() != 0.0))) {
				acumulado += ektProduto.getQt11();
				ektProduto.setQt11(null);
			}
			if (qtdeTamanhos < 10 && ((ektProduto.getQt10() != null) && (ektProduto.getQt10() != 0.0))) {
				acumulado += ektProduto.getQt10();
				ektProduto.setQt10(null);
			}
			if (qtdeTamanhos < 9 && ((ektProduto.getQt09() != null) && (ektProduto.getQt09() != 0.0))) {
				acumulado += ektProduto.getQt09();
				ektProduto.setQt09(null);
			}
			if (qtdeTamanhos < 8 && ((ektProduto.getQt08() != null) && (ektProduto.getQt08() != 0.0))) {
				acumulado += ektProduto.getQt08();
				ektProduto.setQt08(null);
			}
			if (qtdeTamanhos < 7 && ((ektProduto.getQt07() != null) && (ektProduto.getQt07() != 0.0))) {
				acumulado += ektProduto.getQt07();
				ektProduto.setQt07(null);
			}
			if (qtdeTamanhos < 6 && ((ektProduto.getQt06() != null) && (ektProduto.getQt06() != 0.0))) {
				acumulado += ektProduto.getQt06();
				ektProduto.setQt06(null);
			}
			if (qtdeTamanhos < 5 && ((ektProduto.getQt05() != null) && (ektProduto.getQt05() != 0.0))) {
				acumulado += ektProduto.getQt05();
				ektProduto.setQt05(null);
			}
			if (qtdeTamanhos < 4 && ((ektProduto.getQt04() != null) && (ektProduto.getQt04() != 0.0))) {
				acumulado += ektProduto.getQt04();
				ektProduto.setQt04(null);
			}
			if (qtdeTamanhos < 3 && ((ektProduto.getQt03() != null) && (ektProduto.getQt03() != 0.0))) {
				acumulado += ektProduto.getQt03();
				ektProduto.setQt03(null);
			}
			if (qtdeTamanhos < 2 && ((ektProduto.getQt02() != null) && (ektProduto.getQt02() != 0.0))) {
				acumulado += ektProduto.getQt02();
				ektProduto.setQt02(null);
			}

			logger.info(">>>>>>>>>>>>>>>>> 222");

			if (ektProduto.getQt01() != null) {
				logger.info(">>>>>>>>>>>>>>>>> getQt01()");
				GradeTamanho gt = getGradeFinder().findByCodigoGradeAndOrdem(ektProduto.getGrade().intValue(), 1);
				if (gt == null) {
					throw new ViewException("GradeTamanho 01 null");
				}
				logger.info(">>>>>>>>>>>>>>>>> getQt01() gt");
				ProdutoSaldo saldo = new ProdutoSaldo();
				saldo.setProduto(produto);
				saldo.setGradeTamanho(gt);
				saldo.setQtde(CurrencyUtils.getBigDecimalScaled(ektProduto.getQt01(), 2));
				saldo.setQtde(saldo.getQtde().add(CurrencyUtils.getBigDecimalScaled(acumulado, 2)));
				saldo.setSelecionado("S".equals(ektProduto.getF1()));
				acumulado = 0.0;
				produto.getSaldos().add(saldo);
				logger.info(">>>>>>>>>>>>>>>>> getQt01()... OK");
			}

			if (qtdeTamanhos >= 2 && ektProduto.getQt02() != null) {
				logger.info(">>>>>>>>>>>>>>>>> getQt02()");
				GradeTamanho gt = getGradeFinder().findByCodigoGradeAndOrdem(ektProduto.getGrade().intValue(), 2);
				if (gt == null) {
					throw new ViewException("GradeTamanho null");
				}
				logger.info(">>>>>>>>>>>>>>>>> getQt02() gt");
				ProdutoSaldo saldo = new ProdutoSaldo();
				saldo.setProduto(produto);
				saldo.setGradeTamanho(gt);
				saldo.setQtde(CurrencyUtils.getBigDecimalScaled(ektProduto.getQt02(), 2));
				saldo.setQtde(saldo.getQtde().add(CurrencyUtils.getBigDecimalScaled(acumulado, 2)));
				saldo.setSelecionado("S".equals(ektProduto.getF2()));
				acumulado = 0.0;
				produto.getSaldos().add(saldo);
				logger.info(">>>>>>>>>>>>>>>>> getQt02()... OK");
			}

			if (qtdeTamanhos >= 3 && ektProduto.getQt03() != null) {
				logger.info(">>>>>>>>>>>>>>>>> getQt03()");
				GradeTamanho gt = getGradeFinder().findByCodigoGradeAndOrdem(ektProduto.getGrade().intValue(), 3);
				if (gt == null) {
					throw new ViewException("GradeTamanho null");
				}
				logger.info(">>>>>>>>>>>>>>>>> getQt03() gt");
				ProdutoSaldo saldo = new ProdutoSaldo();
				saldo.setProduto(produto);
				saldo.setGradeTamanho(gt);
				saldo.setQtde(CurrencyUtils.getBigDecimalScaled(ektProduto.getQt03(), 2));
				saldo.setQtde(saldo.getQtde().add(CurrencyUtils.getBigDecimalScaled(acumulado, 2)));
				saldo.setSelecionado("S".equals(ektProduto.getF3()));
				acumulado = 0.0;
				produto.getSaldos().add(saldo);
				logger.info(">>>>>>>>>>>>>>>>> getQt03()... OK");

			}

			if (qtdeTamanhos >= 4 && ektProduto.getQt04() != null) {
				logger.info(">>>>>>>>>>>>>>>>> getQt04()");
				GradeTamanho gt = getGradeFinder().findByCodigoGradeAndOrdem(ektProduto.getGrade().intValue(), 4);
				if (gt == null) {
					throw new ViewException("GradeTamanho null");
				}
				logger.info(">>>>>>>>>>>>>>>>> getQt04() gt");
				ProdutoSaldo saldo = new ProdutoSaldo();
				saldo.setProduto(produto);
				saldo.setGradeTamanho(gt);
				saldo.setQtde(CurrencyUtils.getBigDecimalScaled(ektProduto.getQt04(), 2));
				saldo.setQtde(saldo.getQtde().add(CurrencyUtils.getBigDecimalScaled(acumulado, 2)));
				saldo.setSelecionado("S".equals(ektProduto.getF4()));
				acumulado = 0.0;
				produto.getSaldos().add(saldo);
				logger.info(">>>>>>>>>>>>>>>>> getQt04()... OK");
			}

			if (qtdeTamanhos >= 5 && ektProduto.getQt05() != null) {
				logger.info(">>>>>>>>>>>>>>>>> getQt05()");
				GradeTamanho gt = getGradeFinder().findByCodigoGradeAndOrdem(ektProduto.getGrade().intValue(), 5);
				if (gt == null) {
					throw new ViewException("GradeTamanho null");
				}
				logger.info(">>>>>>>>>>>>>>>>> getQt05() gt");
				ProdutoSaldo saldo = new ProdutoSaldo();
				saldo.setProduto(produto);
				saldo.setGradeTamanho(gt);
				saldo.setQtde(CurrencyUtils.getBigDecimalScaled(ektProduto.getQt05(), 2));
				saldo.setQtde(saldo.getQtde().add(CurrencyUtils.getBigDecimalScaled(acumulado, 2)));
				saldo.setSelecionado("S".equals(ektProduto.getF5()));
				acumulado = 0.0;
				produto.getSaldos().add(saldo);
				logger.info(">>>>>>>>>>>>>>>>> getQt05()... OK");
			}

			if (qtdeTamanhos >= 6 && ektProduto.getQt06() != null) {
				logger.info(">>>>>>>>>>>>>>>>> getQt06()");
				GradeTamanho gt = getGradeFinder().findByCodigoGradeAndOrdem(ektProduto.getGrade().intValue(), 6);
				if (gt == null) {
					throw new ViewException("GradeTamanho null");
				}
				logger.info(">>>>>>>>>>>>>>>>> getQt06() gt");
				ProdutoSaldo saldo = new ProdutoSaldo();
				saldo.setProduto(produto);
				saldo.setGradeTamanho(gt);
				saldo.setQtde(CurrencyUtils.getBigDecimalScaled(ektProduto.getQt06(), 2));
				saldo.setQtde(saldo.getQtde().add(CurrencyUtils.getBigDecimalScaled(acumulado, 2)));
				saldo.setSelecionado("S".equals(ektProduto.getF6()));
				acumulado = 0.0;
				produto.getSaldos().add(saldo);
				logger.info(">>>>>>>>>>>>>>>>> getQt06()... OK");
			}

			if (qtdeTamanhos >= 7 && ektProduto.getQt07() != null) {
				logger.info(">>>>>>>>>>>>>>>>> getQt07()");
				GradeTamanho gt = getGradeFinder().findByCodigoGradeAndOrdem(ektProduto.getGrade().intValue(), 7);
				if (gt == null) {
					throw new ViewException("GradeTamanho 07 null");
				}
				logger.info(">>>>>>>>>>>>>>>>> getQt07() gt");
				ProdutoSaldo saldo = new ProdutoSaldo();
				saldo.setProduto(produto);
				saldo.setGradeTamanho(gt);
				saldo.setQtde(CurrencyUtils.getBigDecimalScaled(ektProduto.getQt07(), 2));
				saldo.setQtde(saldo.getQtde().add(CurrencyUtils.getBigDecimalScaled(acumulado, 2)));
				saldo.setSelecionado("S".equals(ektProduto.getF7()));
				acumulado = 0.0;
				produto.getSaldos().add(saldo);
				logger.info(">>>>>>>>>>>>>>>>> getQt07()... OK");
			}

			if (qtdeTamanhos >= 8 && ektProduto.getQt08() != null) {
				logger.info(">>>>>>>>>>>>>>>>> getQt08()");
				GradeTamanho gt = getGradeFinder().findByCodigoGradeAndOrdem(ektProduto.getGrade().intValue(), 8);
				if (gt == null) {
					throw new ViewException("GradeTamanho 08 null");
				}
				logger.info(">>>>>>>>>>>>>>>>> getQt08() gt");
				ProdutoSaldo saldo = new ProdutoSaldo();
				saldo.setProduto(produto);
				saldo.setGradeTamanho(gt);
				saldo.setQtde(CurrencyUtils.getBigDecimalScaled(ektProduto.getQt08(), 2));
				saldo.setQtde(saldo.getQtde().add(CurrencyUtils.getBigDecimalScaled(acumulado, 2)));
				saldo.setSelecionado("S".equals(ektProduto.getF8()));
				acumulado = 0.0;
				produto.getSaldos().add(saldo);
				logger.info(">>>>>>>>>>>>>>>>> getQt08()... OK");
			}

			if (qtdeTamanhos >= 9 && ektProduto.getQt09() != null) {
				logger.info(">>>>>>>>>>>>>>>>> getQt09()");
				GradeTamanho gt = getGradeFinder().findByCodigoGradeAndOrdem(ektProduto.getGrade().intValue(), 9);
				if (gt == null) {
					throw new ViewException("GradeTamanho null");
				}
				logger.info(">>>>>>>>>>>>>>>>> getQt09() gt");
				ProdutoSaldo saldo = new ProdutoSaldo();
				saldo.setProduto(produto);
				saldo.setGradeTamanho(gt);
				saldo.setQtde(CurrencyUtils.getBigDecimalScaled(ektProduto.getQt09(), 2));
				saldo.setQtde(saldo.getQtde().add(CurrencyUtils.getBigDecimalScaled(acumulado, 2)));
				saldo.setSelecionado("S".equals(ektProduto.getF9()));
				acumulado = 0.0;
				produto.getSaldos().add(saldo);
				logger.info(">>>>>>>>>>>>>>>>> getQt09()... OK");
			}

			if (qtdeTamanhos >= 10 && ektProduto.getQt10() != null) {
				logger.info(">>>>>>>>>>>>>>>>> getQt10()");
				GradeTamanho gt = getGradeFinder().findByCodigoGradeAndOrdem(ektProduto.getGrade().intValue(), 10);
				if (gt == null) {
					throw new ViewException("GradeTamanho null");
				}
				logger.info(">>>>>>>>>>>>>>>>> getQt10() gt");
				ProdutoSaldo saldo = new ProdutoSaldo();
				saldo.setProduto(produto);
				saldo.setGradeTamanho(gt);
				saldo.setQtde(CurrencyUtils.getBigDecimalScaled(ektProduto.getQt10(), 2));
				saldo.setQtde(saldo.getQtde().add(CurrencyUtils.getBigDecimalScaled(acumulado, 2)));
				saldo.setSelecionado("S".equals(ektProduto.getF10()));
				acumulado = 0.0;
				produto.getSaldos().add(saldo);
				logger.info(">>>>>>>>>>>>>>>>> getQt10()... OK");
			}

			if (qtdeTamanhos >= 11 && ektProduto.getQt11() != null) {
				logger.info(">>>>>>>>>>>>>>>>> getQt11()");
				GradeTamanho gt = getGradeFinder().findByCodigoGradeAndOrdem(ektProduto.getGrade().intValue(), 11);
				if (gt == null) {
					throw new ViewException("GradeTamanho null");
				}
				logger.info(">>>>>>>>>>>>>>>>> getQt11() gt");
				ProdutoSaldo saldo = new ProdutoSaldo();
				saldo.setProduto(produto);
				saldo.setGradeTamanho(gt);
				saldo.setQtde(CurrencyUtils.getBigDecimalScaled(ektProduto.getQt11(), 2));
				saldo.setQtde(saldo.getQtde().add(CurrencyUtils.getBigDecimalScaled(acumulado, 2)));
				saldo.setSelecionado("S".equals(ektProduto.getF11()));
				acumulado = 0.0;
				produto.getSaldos().add(saldo);
				logger.info(">>>>>>>>>>>>>>>>> getQt11()... OK");
			}

			if (qtdeTamanhos >= 12 && ektProduto.getQt12() != null) {
				logger.info(">>>>>>>>>>>>>>>>> getQt12()");
				GradeTamanho gt = getGradeFinder().findByCodigoGradeAndOrdem(ektProduto.getGrade().intValue(), 12);
				if (gt == null) {
					throw new ViewException("GradeTamanho null");
				}
				logger.info(">>>>>>>>>>>>>>>>> getQt12() gt");
				ProdutoSaldo saldo = new ProdutoSaldo();
				saldo.setProduto(produto);
				saldo.setGradeTamanho(gt);
				saldo.setQtde(CurrencyUtils.getBigDecimalScaled(ektProduto.getQt12(), 2));
				saldo.setQtde(saldo.getQtde().add(CurrencyUtils.getBigDecimalScaled(acumulado, 2)));
				saldo.setSelecionado("S".equals(ektProduto.getF12()));
				acumulado = 0.0;
				produto.getSaldos().add(saldo);
				logger.info(">>>>>>>>>>>>>>>>> getQt12()... OK");
			}

			logger.info(">>>>>>>>>>>>>>>>> handleEntityIdFilhosInserting: " + ektProduto.toStringToView() + " [THREAD:"
					+ getInicio()
					+ "]");
			getProdutoDataMapper().getEntityIdHandler().handleEntityIdFilhosInserting(produto.getSaldos());
			logger.info(">>>>>>>>>>>>>>>>> SAVE ... SALVANDO GRADE: " + ektProduto.toStringToView() + " [THREAD:"
					+ getInicio()
					+ "]");
			produto = getProdutoDataMapper().save(produto);
			logger.info(">>>>>>>>>>>>>>>>> OK ... [THREAD:" + getInicio() + "]");

			return produto;

		} catch (Exception e) {
			String err = "ERRO AO SALVAR GRADE. EKTPRODUTO: " + ektProduto.toStringToView() + ". PRODUTO: "
					+ produto.toStringToView() + " [THREAD:" + getInicio() + "]";
			logger.error(err, e);
			w.println(err);
			w.println(StringUtils.stackTraceToString(e));
			w.println();
			throw new IllegalStateException(err, e);
		}

	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, timeout = 15)
	public Produto atualizaProduto(EKTProduto ektProduto, Produto produto) throws ViewException {
		// Atualiza campos do produto
		logger.info(">>>>>>>>>>>>>>>>> ATUALIZANDO PRODUTO: " + ektProduto.toStringToView() + " [THREAD:" + getInicio()
				+ "]");
		try {
			// logger.info("getProdutoFinder().refresh(produto = '" + produto.getId() + "')");
			// produto = getProdutoFinder().refresh(produto);

			produto.setDeptoImportado(getThiz().findDepto(ektProduto));
			produto.setSubdepto(getThiz().findSubdepto(ektProduto));
			produto.setSubdeptoErr("" + ektProduto.getSubdepto().intValue());

			// caso tenha vindo como 0, mantem o mesmo fornecedor de antes
			if (ektProduto.getFornec().intValue() > 0) {
				if (ektProduto.getFornec().intValue() != produto.getFornecedor().getCodigo()) {
					logger.info("FORNECEDOR ALTERADO:");
					Fornecedor fornecedor = getFornecedorFinder()
							.findByCodigoEKT(ektProduto.getFornec().intValue(), dtMesImport);
					produto.setFornecedor(fornecedor);
				}
			}

			produto.setDescricao(ektProduto.getDescricao());
			produto.setDtUltVenda(ektProduto.getDataUltVenda());

			produto.setReferencia(ektProduto.getReferencia());
			produto.setUnidadeProduto(getThiz().findUnidadeProduto(ektProduto));
			produto.setUnidadeProdutoErr(ektProduto.getUnidade());

			produto.setCst(ektProduto.getCst());
			produto.setIcms(ektProduto.getIcms().intValue());
			produto.setNcm(ektProduto.getNcm());
			produto.setFracionado(ektProduto.getFracionado() == null || ektProduto.getFracionado().equals("S"));
			produto.setTipoTributacao(ektProduto.getTipoTrib());

			// Verifica se houve alteração de preço
			ProdutoPreco preco = new ProdutoPreco();
			preco.setMesano(dtMesImport);
			preco.setProduto(produto);
			preco.setCoeficiente(CurrencyUtils.getBigDecimalScaled(ektProduto.getCoef(), 3));
			preco.setCustoOperacional(CurrencyUtils.getBigDecimalScaled(ektProduto.getMargemC(), 3));
			preco.setCustoFinanceiro(new BigDecimal("0.15"));
			preco.setMargem(CurrencyUtils.getBigDecimalScaled(ektProduto.getMargem(), 3));
			preco.setDtCusto(ektProduto.getDataPCusto() != null ? ektProduto.getDataPCusto() : dtMesImport);
			preco.setDtPrecoVenda(ektProduto.getDataPVenda() != null ? ektProduto.getDataPVenda() : dtMesImport);
			preco.setPrazo(ektProduto.getPrazo().intValue());
			preco.setPrecoCusto(CurrencyUtils.getBigDecimalCurrency(ektProduto.getpCusto()));
			preco.setPrecoPrazo(CurrencyUtils.getBigDecimalCurrency(ektProduto.getpPrazo()));
			preco.setPrecoPromo(CurrencyUtils.getBigDecimalCurrency(ektProduto.getpPromo()));
			preco.setPrecoVista(CurrencyUtils.getBigDecimalCurrency(ektProduto.getpVista()));

			boolean temPrecoIgual = false;
			for (ProdutoPreco pp : produto.getPrecos()) {
				logger.info(pp.getDtCusto() + " | " + pp.getPrecoCusto() + " | " + pp.getPrecoPrazo());
				if (pp.equals(preco)) {
					temPrecoIgual = true;
					break;
				}
			}
			if (!temPrecoIgual) {
				// Se alterou o preço, só adiciono na tabela de preços do produto que ali embaixo ele vai ser salvo.
				logger.info("PREÇO ALTERADO:");
				logger.info(preco.getDtCusto() + " | " + preco.getPrecoCusto() + " | " + preco.getPrecoPrazo());
				produto.addPreco(preco);
			} else {
				// se já tem o mesmo preço, só acerta o updated pra marcar como se tivesse "resalvado" o preço
				ProdutoPreco ppJa = produto.getPrecos().get(produto.getPrecos().indexOf(preco));
				if (ppJa != null) {
					ppJa.getIudt().setUpdated(new Date());
				} else {
					logger.info("WTF?????????????????????????????");
				}
			}

			// Ordeno para pegar o preço mais recente (DT_CUSTO DESC)
			Collections.sort(produto.getPrecos(), new Comparator<ProdutoPreco>() {

				@Override
				public int compare(ProdutoPreco o1, ProdutoPreco o2) {
					return new CompareToBuilder()
							.append(o2.getDtCusto(), o1.getDtCusto())
							.append(o2.getId(), o1.getId())
							.toComparison();
				}
			});
			ProdutoPreco maisRecente = produto.getPrecos().get(0);

			// Se o preço importado não é o mais recente, incrementa em 1 dia além do mais recente
			if (!maisRecente.equals(preco)) {
				preco = produto.getPrecos().get(produto.getPrecos().indexOf(preco));
				preco.setDtCusto(CalendarUtil.incDias(maisRecente.getDtCusto(), 1));
			}

			produto.setGrade(getThiz().findGrade(ektProduto));
			produto.setGradeErr("" + ektProduto.getGrade().intValue());

			logger.info(">>>>>>>>>>>>>>>>> SALVANDO NO ATUALIZA PRODUTO: " + ektProduto.toStringToView() + " [THREAD:"
					+ getInicio()
					+ "]");
			produto = getProdutoDataMapper().save(produto);
			logger.info(">>>>>>>>>>>>>>>>> OK ... [THREAD:" + getInicio() + "]");
			return produto;

		} catch (Exception e) {
			String err = "ERRO AO ATUALIZAR O PRODUTO [THREAD:" + getInicio() + "]";
			logger.error(err, e);
			w.println(err);
			w.println(StringUtils.stackTraceToString(e));
			w.println();
			throw new IllegalStateException(err, e);

		}
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, timeout = 15)
	public void acertaPeriodosReduzidoEKT(Integer reduzidoEKT) throws ViewException {
		if (CalendarUtil.difBetweenDatesInMonths(dtMesImport, new Date()) != 0) {
			// não dá pra acertar periodos do EKT quando importando de meses anteriores, senão dá cagada...
			return;
		}
		logger.info(">>>>>>>> ACERTANDO REDUZIDOS EKT: " + reduzidoEKT);
		try {
			// Pega todos os produtos que tenham o mesmo reduzido EKT
			List<Produto> produtosComMesmoReduzidoEKT = getProdutoFinder()
					.findByReduzidoEkt(reduzidoEKT, null);

			// Ordena
			Collections.sort(produtosComMesmoReduzidoEKT, new Comparator<Produto>() {

				@Override
				public int compare(Produto o1, Produto o2) {
					return new CompareToBuilder()
							.append(o1.getReduzidoEktDesde(), o2.getReduzidoEktDesde())
							.toComparison();
				}
			});

			// força o reduzidoEktAte do último para null
			Produto ultimo = produtosComMesmoReduzidoEKT.get(produtosComMesmoReduzidoEKT.size() - 1);
			ultimo.setReduzidoEktAte(null);
			logger.info(">>>>>>>> save no ACERTANDO REDUZIDOS EKT: " + reduzidoEKT);
			ultimo = getProdutoDataMapper().save(ultimo);
			logger.info(">>>>>>>> OK ");
			int qtdeComAteNull = 0;
			for (Produto p : produtosComMesmoReduzidoEKT) {
				if (p.getReduzidoEktAte() == null) {
					qtdeComAteNull++;
				}
			}
			// aqui tem que ter sempre 2, pois antes de chamar este método já foi inserido o próximo produto
			if (qtdeComAteNull > 2) {
				throw new ViewException("Mais de dois produto com reduzidoEktAte nulo");
			}

			if (produtosComMesmoReduzidoEKT.size() > 1) {
				// Date mesPassado = CalendarUtil.incMes(dtMesImport, -1);

				Date mesAnterior = CalendarUtil.ultimoDiaNoMesAno(CalendarUtil.incMes(produtosComMesmoReduzidoEKT
						.get(produtosComMesmoReduzidoEKT.size() - 1).getReduzidoEktDesde(), -1));

				// O produto imediatamente anterior deve ser atualizado para reduzidoEktAte mês passado
				Produto umAntes = produtosComMesmoReduzidoEKT.get(produtosComMesmoReduzidoEKT.size() - 2);
				umAntes.setReduzidoEktAte(mesAnterior);
				logger.info(">>>>>>>> save 'umAntes' no ACERTANDO REDUZIDOS EKT: " + reduzidoEKT);
				umAntes = getProdutoDataMapper().save(umAntes);
				logger.info(">>>>>>>> OK ");
			}

			logger.info(">>>>>>>>>>>> REDUZIDO EKT ATE CORRIGIDO");
		} catch (Exception e) {
			String err = "ERRO AO ACERTAR PERÍODOS DO REDUZIDO EKT: " + reduzidoEKT + " [THREAD:" + getInicio() + "]";
			logger.error(err, e);
			w.println(err);
			w.println(StringUtils.stackTraceToString(e));
			w.println();
			throw new IllegalStateException(err, e);
		}
	}

	@Override
	public IImportarProdutos2BonERP getThiz() {
		// aqui tive que fazer desta forma por causa das threads. o Thiz é passado no main.
		return thiz;
	}

	@Override
	public void setThiz(IImportarProdutos2BonERP thiz) {
		this.thiz = thiz;
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, timeout = 15)
	public Grade findGrade(EKTProduto ektProduto) throws ViewException {
		Grade g = getGradeFinder().findByCodigo(ektProduto.getGrade().intValue());
		if (g == null) {
			throw new ViewException("Grade não encontrada");
		}
		return g;
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, timeout = 15)
	public UnidadeProduto findUnidadeProduto(EKTProduto ektProduto) throws ViewException {
		String unidade = ektProduto.getUnidade();
		if (unidade == null || "".equals(unidade) || unidade.contains("PC")) {
			unidade = "UN";
		}
		UnidadeProduto g = getUnidadeProdutoFinder().findByLabel(unidade);
		if (g == null) {
			g = getUnidadeProduto999999();
		}
		return g;
	}

	private UnidadeProduto unidadeProduto999999;

	@Override
	public UnidadeProduto getUnidadeProduto999999() {
		if (unidadeProduto999999 == null) {
			try {
				unidadeProduto999999 = getUnidadeProdutoFinder().findByLabel("ERRO");
			} catch (ViewException e) {
				throw new IllegalStateException("UnidadeProduto 999999 não encontrado");
			}
		}
		return unidadeProduto999999;
	}

	@Override
	public void setUnidadeProduto999999(UnidadeProduto unidadeProduto999999) {
		this.unidadeProduto999999 = unidadeProduto999999;
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, timeout = 15)
	public Departamento findDepto(EKTProduto ektProduto) throws ViewException {
		Departamento d = getDeptoFinder().findByCodigo(ektProduto.getDepto().intValue());
		if (d == null) {
			d = getDepto999999();
		}
		return d;
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, timeout = 15)
	public Subdepartamento findSubdepto(EKTProduto ektProduto) throws ViewException {
		Subdepartamento s = getSubdeptoFinder().findByCodigo(ektProduto.getSubdepto().intValue());
		if (s == null) {
			s = getSubdeptoZERO();
		}
		return s;
	}

	private Departamento depto999999;

	@Override
	public Departamento getDepto999999() {
		if (depto999999 == null) {
			try {
				depto999999 = getDeptoFinder().findByCodigo(999999);
			} catch (ViewException e) {
				throw new IllegalStateException("Departamento 999999 não encontrado");
			}
		}
		return depto999999;
	}

	@Override
	public void setDepto999999(Departamento depto999999) {
		this.depto999999 = depto999999;
	}

	private Subdepartamento subdeptoZERO;

	@Override
	public Subdepartamento getSubdeptoZERO() {
		if (subdeptoZERO == null) {
			try {
				subdeptoZERO = getSubdeptoFinder().findByCodigo(0);
			} catch (ViewException e) {
				throw new IllegalStateException("Subdepartamento 0 (ZERO) não encontrado");
			}
		}
		return subdeptoZERO;
	}

	@Override
	public void setSubdeptoZERO(Subdepartamento subdeptoZERO) {
		this.subdeptoZERO = subdeptoZERO;
	}

	@Autowired
	private DeptoFinder deptoFinder;

	@Autowired
	private SubdeptoFinder subdeptoFinder;

	@Autowired
	private GradeFinder gradeFinder;

	@Autowired
	private UnidadeProdutoFinder unidadeProdutoFinder;

	@Autowired
	private FornecedorFinder fornecedorFinder;

	@Override
	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	public ProdutoFinder getProdutoFinder() {
		return produtoFinder;
	}

	public void setProdutoFinder(ProdutoFinder produtoFinder) {
		this.produtoFinder = produtoFinder;
	}

	public ProdutoDataMapper getProdutoDataMapper() {
		return produtoDataMapper;
	}

	public void setProdutoDataMapper(ProdutoDataMapper produtoDataMapper) {
		this.produtoDataMapper = produtoDataMapper;
	}

	public EKTProdutoFinder getEktProdutoFinder() {
		return ektProdutoFinder;
	}

	public void setEktProdutoFinder(EKTProdutoFinder ektProdutoFinder) {
		this.ektProdutoFinder = ektProdutoFinder;
	}

	public ProdutoBusiness getProdutoBusiness() {
		return produtoBusiness;
	}

	public void setProdutoBusiness(ProdutoBusiness produtoBusiness) {
		this.produtoBusiness = produtoBusiness;
	}

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

	public GradeFinder getGradeFinder() {
		return gradeFinder;
	}

	public void setGradeFinder(GradeFinder gradeFinder) {
		this.gradeFinder = gradeFinder;
	}

	public UnidadeProdutoFinder getUnidadeProdutoFinder() {
		return unidadeProdutoFinder;
	}

	public void setUnidadeProdutoFinder(UnidadeProdutoFinder unidadeProdutoFinder) {
		this.unidadeProdutoFinder = unidadeProdutoFinder;
	}

	public FornecedorFinder getFornecedorFinder() {
		return fornecedorFinder;
	}

	public void setFornecedorFinder(FornecedorFinder fornecedorFinder) {
		this.fornecedorFinder = fornecedorFinder;
	}

	@Override
	public int getInicio() {
		return inicio;
	}

	@Override
	public void setInicio(int inicio) {
		this.inicio = inicio;
	}

	@Override
	public int getFim() {
		return fim;
	}

	@Override
	public void setFim(int fim) {
		this.fim = fim;
	}

	@Override
	public String getLogFile() {
		return logFile;
	}

	@Override
	public void setLogFile(String logFile) {
		this.logFile = logFile;
	}

	@Override
	public String getDirLogs() {
		return dirLogs;
	}

	@Override
	public void setDirLogs(String dirLogs) {
		this.dirLogs = dirLogs;
	}

	/**
	 * @param args
	 * @throws FileNotFoundException
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) {

		try {
			long ini = System.currentTimeMillis();

			System.out.println("Informe o mês import. Formato: YYYYMM");

			if (args.length < 1) {
				args = new String[] { sdfMesAno.format(new Date()) };
			}

			mesImport = args[0];

			try {
				if (mesImport.length() > 6) {
					throw new IllegalStateException();
				}
				Integer.parseInt(args[0]);
				ImportarProdutos2BonERP.dtMesImport = sdfMesAno.parse(mesImport);
			} catch (Exception e1) {
				System.out.println("Erro ao parsear o mesImport");
				System.out.println("Informe o mês import. Formato: YYYYMM");
				System.exit(0);
			}

			System.out.println("Importando para o mês/ano: " + mesImport);

			
			ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

			//		tF.marcarProdutosInativos();
			IImportarProdutos2BonERP t = (IImportarProdutos2BonERP) context.getBean("importarProdutos2BonERP");
			t.setThiz(t);
			
			String sufixo = sdfLog.format(new Date()) + "_PRODUTOS.txt";
			t.setLogFile(t.getDirLogs() + sufixo);
			w = new PrintWriter(t.getLogFile());
			w.println("<<< IMPORTAÇÃO DE PRODUTOS >>>");
			w.println(t.getLogFile());

			// Se passar o argumento DEATE, só corrige e já sai do programa
			if (Arrays.binarySearch(args, "DEATE") >= 0) {
				t.acertarDeAteProdutos();
			} else {



				ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");

				w.println();
				w.println("DELETANDO SALDOS...");
				w.println();

				t.deletarSaldos();

				w.println("INICIANDO THREADS...");
				w.println();

				int j = 500;

				for (int i = 0; i < 15000; i += j) {
					//		for (int i = 0; i < 500; i += j) {
					IImportarProdutos2BonERP t1 = (IImportarProdutos2BonERP) context.getBean("importarProdutos2BonERP");
					t1.setInicio(i);
					t1.setFim(j);
					t1.setThiz(t1);
					taskExecutor.execute(t1);
				}

				for (;;) {
					int count = taskExecutor.getActiveCount();
					System.out.println("Active Threads : " + count);
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (count == 0) {
						taskExecutor.shutdown();
						break;
					}
				}

			}

			long fim = System.currentTimeMillis();
			double tempo = (fim - ini) / 1000;
			logger.info("Tempo de exec.: " + tempo + " segundos");
			logger.info("Log: " + t.getLogFile());

			w.flush();

			try {
				Mailer.sendMail("casabonsucesso@gmail.com", sufixo, StringUtils
						.inputStreamToString(new FileInputStream(new File(t.getLogFile()))));
			} catch (ViewException e) {
				logger.error("Erro ao enviar o e-mail para 'carlospauluk@gmail.com' (" + sufixo + ")");
			}

			System.out.println();

			w.println();
			w.println();
			w.println(tempo);
		} catch (Exception e) {
			e.printStackTrace();
			if (w != null) {
				e.printStackTrace(w);
			}
			System.out.println("Erro ao importar produtos");
			System.exit(-1);
		} finally {
			if (w != null) {
				w.close();
			}
		}

		System.exit(0);
	}

}
