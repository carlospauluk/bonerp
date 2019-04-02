package com.bonsucesso.erp.rh.business;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.rh.data.FuncionarioFinder;
import com.bonsucesso.erp.rh.model.Funcionario;
import com.bonsucesso.erp.vendas.data.PlanoPagtoFinder;
import com.bonsucesso.erp.vendas.data.VendaDataMapper;
import com.bonsucesso.erp.vendas.data.VendaFinder;
import com.bonsucesso.erp.vendas.model.PlanoPagto;
import com.bonsucesso.erp.vendas.model.Venda;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.utils.currency.CurrencyUtils;
import com.ocabit.utils.strings.RegexUtils;
import com.ocabit.utils.strings.StringUtils;


@Component("totalDiarioImporterEkt")
public class TotalDiaAnaliticoImporter implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -8642712225323977257L;

	protected static Logger logger = Logger.getLogger(TotalDiaAnaliticoImporter.class);

	@Autowired
	private FuncionarioFinder funcionarioFinder;

	@Autowired
	private PlanoPagtoFinder planoPagtoFinder;

	@Autowired
	private VendaFinder vendaFinder;

	@Autowired
	private VendaDataMapper vendaDataMapper;

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

	public VendaFinder getVendaFinder() {
		return vendaFinder;
	}

	public void setVendaFinder(VendaFinder vendaFinder) {
		this.vendaFinder = vendaFinder;
	}

	public VendaDataMapper getVendaDataMapper() {
		return vendaDataMapper;
	}

	public void setVendaDataMapper(VendaDataMapper vendaDataMapper) {
		this.vendaDataMapper = vendaDataMapper;
	}

	/**
	 * Corrige o relatório do EKT adicionando o "3.00" na linha de totalização dos cartões (para poder identificar no método process());
	 *
	 * @param linhas
	 */
	public void corrigirTotalCartoes(List<String> linhas) {
		for (int i = 0; i < linhas.size(); i++) {
			if (linhas.get(i).equals("*") &&
					linhas.get(i + 1).equals("     ") &&
					linhas.get(i + 2).startsWith("                      =")) {

				linhas.set(i + 1, " 3.00");

			}
		}
	}

	/**
	 *
	 * @param linhas
	 * @param erros
	 * @param mesAno
	 * @return
	 * @throws ViewException
	 */
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, rollbackFor = {
			RuntimeException.class, ViewException.class })
	public List<Venda> importar(List<String> linhas, StringBuilder erros) throws ViewException {

		corrigirTotalCartoes(linhas);

		List<Venda> list = new ArrayList<Venda>();

		erros = new StringBuilder("");

		String linhaDia = linhas.get(1).substring(21);

		Date dia;
		try {
			dia = new SimpleDateFormat("dd/MM/yyyy").parse(linhaDia);
		} catch (ParseException e1) {
			throw new ViewException("Erro ao extrair data: " + linhaDia);
		}

		Funcionario vendedor = null;
		// "  00120  CINTIA             104.85"
		String patternLinhaVenda = "^(\\s)+(?<PV>\\d{5})(\\s)+(?<VENDEDOR>[a-zA-Z_0-9\\s]{0,15})(\\s)+(?<VALOR>[-\\.,0-9]+)$";
		// " 4.02"
		// " CRED - S/ENT + 2 X   =     262.70"

		String patternLinhaPlanoPagto = "(\\s){1}(?<PLANO>\\d{1}\\.[\\d\\s]{1,2})";

		String patternLinhaTotalPlanoPagto = "([a-zA-Z_0-9\\-\\+/" + RegexUtils.WHITESPACE_CHARS
				+ "]+)(=\\s+)(?<TOTAL>[0-9\\.,-]+)";

		int ultLinhaComPlanoPagto = 0;

		BigDecimal totalGeral = CurrencyUtils.getBigDecimalCurrency("0.0");

		for (int i = 0; i < linhas.size(); i++) {
			String linha = linhas.get(i);

			logger.info(linha);

			if ((linha == null) || linha.trim().equals("")) {
				continue;
			}

			if (linha.matches(patternLinhaVenda)) {

				Integer pv = Integer.parseInt(StringUtils.getGroupFromRegex(linha, patternLinhaVenda, "PV"));
				String vendedorNome = StringUtils.getGroupFromRegex(linha, patternLinhaVenda, "VENDEDOR").trim();

				if ("".equals(vendedorNome.trim())) {
					vendedorNome = "MARIZA";
				}

				String valorStr = StringUtils.getGroupFromRegex(linha, patternLinhaVenda, "VALOR");
				BigDecimal valor = CurrencyUtils.getBigDecimalCurrency(Double.valueOf(valorStr.trim()
						.replace(",", "")));

				try {
					vendedor = getFuncionarioFinder().findByNomeEktAndMesAno(vendedorNome, dia);
					if (vendedor == null) {
						throw new IllegalStateException();
					}
				} catch (Exception e) {
					throw new ViewException("Vendedor não encontrado. NOME: " + vendedorNome + ". DIA: " + dia);
				}

				Venda venda = new Venda();
				venda.setDtVenda(dia);
				venda.setVendedor(vendedor);
				venda.setPv(pv);
				venda.setValorTotal(valor);

				list.add(venda);

			} else if (linha.matches(patternLinhaPlanoPagto)) {

				String codigoPlano = StringUtils.getGroupFromRegex(linha, patternLinhaPlanoPagto, "PLANO");
				codigoPlano = codigoPlano.trim();

				PlanoPagto planoPagto = getPlanoPagtoFinder().findBy(codigoPlano);

				if (planoPagto == null) {
					throw new ViewException("Plano de Pagto não encontrado: " + codigoPlano);
				}

				logger.info(planoPagto); // touch

				BigDecimal totalSoma = CurrencyUtils.getBigDecimalCurrency("0.0");

				for (int j = ultLinhaComPlanoPagto; j < list.size(); j++) {
					totalSoma = CurrencyUtils.soma(list.get(j).getValorTotal(), totalSoma);
					totalGeral = CurrencyUtils.soma(list.get(j).getValorTotal(), totalGeral);
					list.get(j).setPlanoPagto(planoPagto);
				}

				ultLinhaComPlanoPagto = list.size();

				String linhaTotal = linhas.get(i + 1);
				String totalStr = StringUtils.getGroupFromRegex(linhaTotal, patternLinhaTotalPlanoPagto, "TOTAL");
				totalStr = totalStr.replace(",", "");
				BigDecimal totalInform = CurrencyUtils.getBigDecimalCurrency(totalStr);
				if (totalSoma.compareTo(totalInform) != 0) {
					logger.error("TOTAL SOMA: " + totalSoma.doubleValue());
					logger.error("TOTAL: " + totalInform);
					throw new ViewException("TOTAL ERRADO. TOTAL SOMA: " + totalSoma.doubleValue()
							+ ". TOTAL INFORM.: " + totalInform);
				}

			}

		}

		logger.info("****************** ERROS ******************");
		logger.info(erros);
		logger.info("*******************************************");

		logger.info("TOTAL GERAL: " + totalGeral.doubleValue());

		return list;
	}

	/**
	 *
	 * @param inputStream
	 * @param erros
	 * @param mesAno
	 * @return
	 * @throws ViewException
	 */
	public List<Venda> process(InputStream inputStream, StringBuilder erros)
			throws ViewException {

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			String linha;

			List<String> linhas = new ArrayList<String>();

			while ((linha = br.readLine()) != null) {
				if ((linha == null) || linha.equals("")) {
					continue;
				}
				linhas.add(linha);
			}

			return importar(linhas, erros);
		} catch (IOException e) {
			logger.error(e);
			throw new ViewException("Erro ao importar");
		}
	}

	/**
	 *
	 * @param args
	 * @throws FileNotFoundException
	 * @throws ViewException
	 */
	public static void main(String[] args) throws FileNotFoundException, ViewException {
		//
		//		// String patternLinhaVenda = "^(\\s)+(?<PV>\\d{5})(\\s)+(?<VENDEDOR>[a-zA-Z_0-9\\s]{0,15})(\\s)+(?<VALOR>[\\.0-9]+)$";
		//		// String linha = "  00120  CINTIA             104.85";
		//
		//		// " 4.06"
		//		String strPattern = "^(\\s){1}(?<PLANO>\\d{1}\\.\\d{2})$";
		//
		//		String linha = " 4.06";
		//
		//		//		|  00120  CINTIA             104.85|
		//		//		| |
		//		//		  |00120|
		//		//		        | |
		//		//		          |CINTIA         |
		//		//		                          | |
		//		//		AQUI                             |5|
		//
		//		Pattern pattern = Pattern.compile(strPattern);
		//		Matcher matcher = pattern.matcher(linha);
		//		if (matcher.find()) {
		//			for (int i = 1; i <= matcher.groupCount(); i++) {
		//				System.out.println("|" + matcher.group(i) + "|");
		//			}
		//		}
		//
		//		System.out.println("AQUI |" + StringUtils.getGroupFromRegex(linha, strPattern, "PLANO") + "|");

		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

		TotalDiaAnaliticoImporter t = (TotalDiaAnaliticoImporter) context.getBean("totalDiarioImporterEkt");

		FileInputStream fis = new FileInputStream(new File("D:/ocabit/bonerp/vendasAnalitico.txt"));
		StringBuilder sb = new StringBuilder("");
		t.process(fis, sb);


	}

}
