package com.bonsucesso.erp.ekt.espelhos2bonerp;



import java.io.PrintWriter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.estoque.data.FornecedorFinder;
import com.bonsucesso.erp.estoque.data.ProdutoFinder;
import com.bonsucesso.erp.estoque.model.Produto;
import com.bonsucesso.erp.estoque.model.ProdutoPreco;
import com.bonsucesso.erp.estoque.model.ProdutoSaldo;
import com.bonsucesso.erp.estoque.model.Subdepartamento;
import com.ocabit.utils.currency.CurrencyUtils;
import com.ocabit.utils.strings.StringUtils;


@Component("checkProdutos")
public class CheckProdutos implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4231640947401785463L;

	protected static Logger logger = Logger.getLogger(CheckProdutos.class);

	public static String[] meses;

	@Autowired
	private BeanFactory beanFactory;

	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	public void checkProdutos() {

		try {
			ProdutoFinder produtoFinder = (ProdutoFinder) getBeanFactory().getBean("produtoFinder");

			List<Produto> produtos = produtoFinder.findAll();

			BigDecimal bdTotalPecas = BigDecimal.ZERO;
			BigDecimal bdTotalPrecoCusto = BigDecimal.ZERO;
			BigDecimal bdTotalPrecoPrazo = BigDecimal.ZERO;

			for (Produto produto : produtos) {

				BigDecimal bdTotalPecasProd = BigDecimal.ZERO;
				BigDecimal bdTotalPrecoCustoProd = BigDecimal.ZERO;
				BigDecimal bdTotalPrecoPrazoProd = BigDecimal.ZERO;

				logger.info("Verificando se o último preço bate com o deste mês");
				Collections.sort(produto.getPrecos(), new Comparator<ProdutoPreco>() {

					@Override
					public int compare(ProdutoPreco o1, ProdutoPreco o2) {
						return o2.getDtCusto().compareTo(o1.getDtCusto());
					}
				});

				for (ProdutoSaldo ps : produto.getSaldos()) {
					bdTotalPecasProd = bdTotalPecasProd.add(ps.getQtde());
				}

				if (bdTotalPecasProd.doubleValue() > 0) {
					bdTotalPecas = bdTotalPecas.add(bdTotalPecasProd);

					bdTotalPrecoCustoProd = CurrencyUtils.getBigDecimalCurrency(CurrencyUtils
							.multiplica(bdTotalPecasProd, produto.getPrecos().get(0).getPrecoCusto()));
					bdTotalPrecoPrazoProd = CurrencyUtils.getBigDecimalCurrency(CurrencyUtils
							.multiplica(bdTotalPecasProd, produto.getPrecos().get(0).getPrecoPrazo()));

					bdTotalPrecoCusto = bdTotalPrecoCusto.add(bdTotalPrecoCustoProd);
					bdTotalPrecoPrazo = bdTotalPrecoPrazo.add(bdTotalPrecoPrazoProd);

				}
			}

			System.out.println("QTDE PEÇAS: " + bdTotalPecas.doubleValue());
			System.out.println("PRECO CUSTO: " + bdTotalPrecoCusto.doubleValue());
			System.out.println("PRECO PRAZO: " + bdTotalPrecoPrazo.doubleValue());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void checkAcumulados() {

		logger.info("INICIANDO VERIFICANDO DOS ACUMULADOS");

		try {
			// pesquisa todos os fornecedores

			FornecedorFinder fornecedorFinder = ((FornecedorFinder) getBeanFactory().getBean("fornecedorFinder"));

			Query qryFIDS = fornecedorFinder.getEntityManager()
					.createQuery("SELECT id, pessoa.nomeFantasia, codigo FROM Fornecedor WHERE codigo <= 999 AND pessoa.nomeFantasia NOT LIKE '%NÃO INFORMADO%' ORDER BY codigo");
			List<Object[]> forns = qryFIDS.getResultList();

			// pesquisa todos os subdeptos do fornecedor
			String sqlSubdeptos = "SELECT distinct(s) FROM Produto p, Fornecedor f, Subdepartamento s WHERE f = p.fornecedor AND p.subdepto = s AND f.id = :f ORDER BY s.codigo";

			String sqlTotalVendido = "SELECT "
					+ "DATE_FORMAT(v.dt_venda, '%Y-%m') AS mesano, "
					+ "SUM(qtde) AS qtde, "
					+ "s.codigo, "
					+ "s.nome, "
					+ "pess.nome_fantasia "
					+ "FROM est_produto p, ven_venda v, ven_venda_item i, est_fornecedor f, bon_pessoa pess, est_subdepto s "
					+ "WHERE i.venda_id = v.id "
					+ "AND p.id = i.produto_id "
					+ "AND p.fornecedor_id = f.id "
					+ "AND f.pessoa_id = pess.id "
					+ "AND p.subdepto_id = s.id "
					+ "AND f.id = :forn_id "
					+ "AND s.codigo = :subdepto_codigo "
					+ "GROUP BY s.codigo, mesano";

			PrintWriter writer = new PrintWriter("D:/saldos.txt", "UTF-8");

			Query qrySaldoAtual = fornecedorFinder.getEntityManager().createNativeQuery("select "
					+ "SUM(saldo.qtde) as qtde "
					+ "from "
					+ "est_produto p, "
					+ "est_produto_saldo saldo, "
					+ "est_grade_tamanho gt, "
					+ "est_fornecedor f, "
					+ "est_subdepto s "
					+ "WHERE "
					+ "p.fornecedor_id = f.id AND "
					+ "p.id = saldo.produto_id AND "
					+ "saldo.grade_tamanho_id = gt.id AND "
					+ "p.subdepto_id = s.id AND "
					+ "f.id = :forn_id AND "
					+ "s.codigo = :subdepto_codigo");
			
			List<Integer> subtotais_GERAL = new ArrayList<Integer>();
			for (int i = 0; i < 12; i++) {
				subtotais_GERAL.add(i, 0);
			}
			Integer totalzao_GERAL = 0;
			Integer saldozao_GERAL = 0;

			for (Object[] forn : forns) {

				Long fornId = (Long) forn[0];
				String nomeFantasia = forn[1].toString();
				String fornCodigo = forn[2].toString();
				logger.info("");
				logger.info("");
				logger.info(">>>>>>>>>>> FORNECEDOR: " + fornId + " - " + nomeFantasia + " (" + fornCodigo + ") ");

				Query qry = fornecedorFinder.getEntityManager().createQuery(sqlSubdeptos, Subdepartamento.class);
				qry.setParameter("f", fornId);
				List<Subdepartamento> subdeptos = qry.getResultList();

				writer.println(StringUtils.padLeft(fornCodigo, 5) + " - " + StringUtils.padRight(nomeFantasia, 26) +
						"Jan   Fev   Mar   Abr   Mai   Jun   Jul   Ago   Set   Out   Nov   Dez   Total   Sd At % Vda");
				
				List<Integer> subtotais = new ArrayList<Integer>();
				for (int i = 0; i < 12; i++) {
					subtotais.add(i, 0);
				}
				
				Integer totalzao = 0;
				Integer saldozao = 0;
				BigDecimal porcentVendasTotal = BigDecimal.ZERO;

				for (Subdepartamento s : subdeptos) {
					logger.info(">>>>>>>>>>>>>>>>>>>>>> SUBDEPTO: " + s.getNome());
					
					Integer totalSubdepto = 0;
					
					Query qryTotalVendido = fornecedorFinder.getEntityManager().createNativeQuery(sqlTotalVendido);
					qryTotalVendido.setParameter("forn_id", fornId);
					qryTotalVendido.setParameter("subdepto_codigo", s.getCodigo());
					List<Object[]> totalVendido = qryTotalVendido.getResultList();

					writer.print(StringUtils.padLeft(s.getCodigo().toString(), 5) + " - "
							+ StringUtils.padRight(s.getNome(), 23));

					for (int i = 0; i < 12; i++) {
						Integer qtde = getQtde(totalVendido, meses[i]);
						totalSubdepto += qtde;
						subtotais.set(i, subtotais.get(i) + qtde);
						writer.print(StringUtils.padLeft(qtde.toString(), 6));
						totalzao += qtde;
						subtotais_GERAL.set(i, subtotais.get(i));
					}
					

					qrySaldoAtual.setParameter("forn_id", fornId);
					qrySaldoAtual.setParameter("subdepto_codigo", s.getCodigo());
					BigDecimal saldoAtual = (BigDecimal) qrySaldoAtual.getSingleResult();
					if (saldoAtual == null) {
						saldoAtual = BigDecimal.ZERO;
					}
					saldoAtual = saldoAtual.setScale(0, RoundingMode.HALF_UP);
					saldozao += saldoAtual.intValue();

					writer.print(StringUtils.padLeft(totalSubdepto.toString(), 8));

					writer.print(StringUtils.padLeft(saldoAtual.toString(), 6));

					String porcentVendas = "0";

					writer.println(StringUtils.padLeft(porcentVendas, 7));
					
					

				}
				totalzao_GERAL += totalzao;
				saldozao_GERAL += saldozao;

				writer.print(" Subtotal                      ");
				for (int i = 0; i < 12; i++) {
					writer.print(StringUtils.padLeft(subtotais.get(i).toString(), 6));
				}
				writer.print(StringUtils.padLeft(totalzao.toString(), 8));

				writer.print(StringUtils.padLeft(saldozao.toString(), 6));
				
				writer.println("");
				writer.println("");
				writer.println("");
				
				writer.flush();

			}
			
			writer.print("                               ");
			for (int i = 0; i < 12; i++) {
				writer.print(StringUtils.padLeft(subtotais_GERAL.get(i).toString(), 6));
			}
			
			writer.println(StringUtils.padLeft(totalzao_GERAL.toString(), 8));

			writer.println(StringUtils.padLeft(saldozao_GERAL.toString(), 6));
			
			writer.println("");
			writer.println("");
			writer.println("");

			writer.close();

			// para cada mês, vai pegando o total de vendas
		} catch (Exception e) {
			throw new IllegalStateException();
		}
	}

	private Integer getQtde(List<Object[]> totais, String mesAno) {
		for (Object[] totalMes : totais) {
			if (totalMes[0].equals(mesAno)) {
				BigDecimal r = new BigDecimal(totalMes[1].toString());
				r.setScale(0, RoundingMode.HALF_UP);
				return r.intValue();
			}
		}
		return 0;
	}

	/**
	 * @param args
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) {

		long ini = System.currentTimeMillis();

		CheckProdutos.meses = new String[] {
				"2016-01",
				"2015-02",
				"2015-03",
				"2015-04",
				"2015-05",
				"2015-06",
				"2015-07",
				"2015-08",
				"2015-09",
				"2015-10",
				"2015-11",
				"2015-12"
		};

		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

		CheckProdutos checkProdutos = (CheckProdutos) context.getBean("checkProdutos");

		checkProdutos.checkAcumulados();

		long fim = System.currentTimeMillis();
		double tempo = (fim - ini) / 1000;
		System.out.println("tempo de exec.: " + tempo + " segundos");
		System.exit(0);
	}

}
