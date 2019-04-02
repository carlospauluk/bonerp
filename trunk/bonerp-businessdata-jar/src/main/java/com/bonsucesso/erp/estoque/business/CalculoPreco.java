package com.bonsucesso.erp.estoque.business;



import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.base.model.Configs;
import com.bonsucesso.erp.estoque.data.DepreciacaoPrecoFinder;
import com.bonsucesso.erp.estoque.model.IPreco;
import com.bonsucesso.erp.estoque.model.ProdutoPreco;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.config.data.ConfigFinder;
import com.ocabit.utils.currency.CurrencyUtils;


@Component("calculoPreco")
public class CalculoPreco implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -5537722557393250765L;

	protected static Logger logger = Logger.getLogger(CalculoPreco.class);

	@Autowired
	private DepreciacaoPrecoFinder depreciacaoPrecoFinder;

	@Autowired
	private ConfigFinder configFinder;

	public DepreciacaoPrecoFinder getDepreciacaoPrecoFinder() {
		return depreciacaoPrecoFinder;
	}

	public void setDepreciacaoPrecoFinder(DepreciacaoPrecoFinder depreciacaoPrecoFinder) {
		this.depreciacaoPrecoFinder = depreciacaoPrecoFinder;
	}

	public ConfigFinder getConfigFinder() {
		return configFinder;
	}

	public void setConfigFinder(ConfigFinder configFinder) {
		this.configFinder = configFinder;
	}

	public void novoPreco(IPreco preco) {
		try {
			String custoOperacionalStr = configFinder.findConfigByChave(Configs.VEN_CUSTO_OPERACIONAL.getChave())
					.getValor();
			Double custoOperacional = Double.valueOf(custoOperacionalStr) * 100;
			preco.setCustoOperacional(CurrencyUtils.getBigDecimalScaled(custoOperacional, 3));
			
			String custoFinanceiroStr = configFinder.findConfigByChave(Configs.VEN_CUSTO_FINANCEIRO.getChave())
					.getValor();
			Double custoFinanceiro = Double.valueOf(custoFinanceiroStr);
			preco.setCustoFinanceiro(CurrencyUtils.getBigDecimalScaled(custoFinanceiro, 3));
		} catch (Exception e1) {
			String erro = "Erro ao buscar custo operacional";
			logger.error(erro, e1);
			throw new IllegalStateException(erro);
		}
	}

	/**
	 * Cálcula o coeficiente operacional. 1 / (1 - (custoOperacional + margem))
	 * * depreciacaoPrazo
	 *
	 * @param preco
	 * @throws ViewException
	 */
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void calcularCoeficiente(IPreco preco) throws ViewException {

		// obtém o depreciacaoPrazo da base de dados
		if (preco == null) {
			throw new ViewException("Preço não pode ser nulo.");
		}
		if ((preco.getPrazo() == null) || (preco.getPrazo() < 0)) {
			throw new ViewException("Prazo deve ser um número inteiro positico.");
		}
		BigDecimal depreciacaoPrazo = getDepreciacaoPrecoFinder().findDepreciacaoByPrazo(preco.getPrazo());
		BigDecimal depreciacaoPrazoBD = CurrencyUtils.getBigDecimalScaled(depreciacaoPrazo.doubleValue(), 3);

		BigDecimal margemPorcent = preco.getMargem()
				.divide(CurrencyUtils.getBigDecimalCurrency(100.0));
		BigDecimal custoOperacPorcent = CurrencyUtils.getBigDecimalScaled(CurrencyUtils
				.divide(preco.getCustoOperacional(), CurrencyUtils.getBigDecimalCurrency(100.0)), 3);

		BigDecimal margemMaximaPorcent = CurrencyUtils
				.getBigDecimalScaled(1.0 - custoOperacPorcent.doubleValue() - 0.0001, 5, RoundingMode.HALF_DOWN);

		BigDecimal margemMaxima = margemMaximaPorcent.multiply(CurrencyUtils
				.getBigDecimalScaled(100.00, 5, RoundingMode.HALF_DOWN));

		if (margemPorcent.doubleValue() > margemMaximaPorcent.doubleValue()) {
			preco.setMargem(margemMaxima);
			throw new ViewException("Margem não pode ser superior a " + margemMaxima + " (C.O.: "
					+ custoOperacPorcent + ")");
		}

		BigDecimal one = CurrencyUtils.getBigDecimalScaled(1.0, 25);

		try {
			BigDecimal coefNaoDeflacionado = CurrencyUtils
					.getBigDecimalScaled(1.0 - (custoOperacPorcent.doubleValue()
							+ margemPorcent.doubleValue()), 5, RoundingMode.HALF_DOWN);

			coefNaoDeflacionado = one.divide(coefNaoDeflacionado, RoundingMode.HALF_DOWN);

			BigDecimal coeficienteBD = coefNaoDeflacionado.multiply(depreciacaoPrazoBD);
			coeficienteBD = coeficienteBD.setScale(3, RoundingMode.HALF_DOWN);

			// retorno
			preco.setCoeficiente(coeficienteBD);
		} catch (Exception e) {
			throw new ViewException("Erro ao calcular coeficiente.");
		}
	}

	/**
	 * Cálculo: precoPrazo = precoCusto * (1 / custoOperacional + margem) *
	 * deflacao * (1 / (1 - 15%)) ; precoVista = precoPrazo * (1 - 15%)
	 *
	 *
	 * @param preco
	 * @throws ViewException
	 */
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void calcularPrecos(IPreco preco) throws ViewException {

		calcularCoeficiente(preco);

		if ((preco == null) || (preco.getCoeficiente() == null)) {
			String erro = "É necessário o coeficiente para calcular os preços";
			logger.error(erro);
			throw new IllegalStateException(erro);
		}

		BigDecimal coeficienteBD = CurrencyUtils
				.getBigDecimalScaled(preco.getCoeficiente().doubleValue(), 3, RoundingMode.HALF_DOWN);

		BigDecimal one = CurrencyUtils.getBigDecimalScaled(1.0, 25);

		BigDecimal custoFinanceiroBD;
		try {
			
			BigDecimal custoFinanceiro = preco.getCustoFinanceiro();
			BigDecimal custoFinanceiroComplBD = CurrencyUtils
					.getBigDecimalScaled(1.0 - custoFinanceiro.doubleValue(), 3);
			custoFinanceiroBD = one.divide(custoFinanceiroComplBD, RoundingMode.HALF_DOWN);
			custoFinanceiroBD = custoFinanceiroBD.setScale(25, RoundingMode.HALF_DOWN);
		} catch (Exception e1) {
			String erro = "Erro ao buscar custo financeiro";
			logger.error(erro, e1);
			throw new IllegalStateException(erro);
		}

		BigDecimal precoCustoBD = preco.getPrecoCusto();
		
		if (precoCustoBD == null) {
			throw new ViewException("Preço de custo nulo.");
		}

		BigDecimal precoPrazoBD = precoCustoBD.multiply(custoFinanceiroBD);
		precoPrazoBD = precoPrazoBD.setScale(8, RoundingMode.HALF_DOWN);
		precoPrazoBD = precoPrazoBD.multiply(coeficienteBD);
		precoPrazoBD = precoPrazoBD.setScale(8, RoundingMode.HALF_DOWN);

		//BigDecimal precoPrazo = arredondar(CurrencyUtils.multiplica(precoCustoBD, coeficienteBD, custoFinanceiroBD));
		BigDecimal precoPrazo = CalculoPreco.arredondar(precoPrazoBD);

		Double descontoVista;
		try {
			String descontoAVistaStr = configFinder.findConfigByChave(Configs.VEN_DESCONTO_A_VISTA.getChave())
					.getValor();
			descontoVista = 1.00 - Double.valueOf(descontoAVistaStr);
		} catch (ViewException e1) {
			String erro = "Erro ao buscar desconto a vista";
			logger.error(erro, e1);
			throw new IllegalStateException(erro);
		}

		BigDecimal precoVista = CurrencyUtils
				.getBigDecimalCurrency(CurrencyUtils.multiplica(precoPrazo, descontoVista));

		preco.setPrecoPrazo(precoPrazo);
		preco.setPrecoVista(precoVista);

	}

	/**
	 * Calcula a margem a partir de um preço a prazo já dado.
	 * Fórmula: margem = 1 - (precoCusto * depreciacaoPrazo * custoFinanceiro / precoPrazo) - custoOperacional.
	 *
	 * @param preco
	 */
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void calcularMargem(IPreco preco) {
		try {
			BigDecimal depreciacaoPrazoBD = getDepreciacaoPrecoFinder().findDepreciacaoByPrazo(preco.getPrazo());

			BigDecimal one = CurrencyUtils.getBigDecimalScaled(1.0, 25);

			BigDecimal custoFinanceiroBD;
			try {
				Double custoFinanceiro = preco.getCustoFinanceiro().doubleValue();
				BigDecimal custoFinanceiroComplBD = CurrencyUtils.getBigDecimalScaled(1.0 - custoFinanceiro, 3);
				custoFinanceiroBD = one.divide(custoFinanceiroComplBD, RoundingMode.HALF_DOWN);
				custoFinanceiroBD = custoFinanceiroBD.setScale(25, RoundingMode.HALF_DOWN);
			} catch (Exception e1) {
				String erro = "Erro ao buscar custo financeiro";
				logger.error(erro, e1);
				throw new IllegalStateException(erro);
			}

			BigDecimal precoCustoBD = preco.getPrecoCusto();

			BigDecimal precoPrazoBD = preco.getPrecoPrazo();

			custoFinanceiroBD = custoFinanceiroBD.setScale(25, RoundingMode.HALF_DOWN);

			BigDecimal precoCusto_x_custoFinanceiro = precoCustoBD.multiply(custoFinanceiroBD);
			precoCusto_x_custoFinanceiro = precoCusto_x_custoFinanceiro.setScale(4, RoundingMode.HALF_DOWN);

			BigDecimal depreciacaoPrazo_DIV_precoPrazo = depreciacaoPrazoBD
					.divide(precoPrazoBD, 8, RoundingMode.HALF_UP);

			BigDecimal aux = precoCusto_x_custoFinanceiro.multiply(depreciacaoPrazo_DIV_precoPrazo);
			aux = aux.setScale(8, RoundingMode.UP);

			BigDecimal custoOperacionalBD = CurrencyUtils
					.getBigDecimalScaled(CurrencyUtils.divide(preco.getCustoOperacional(), new BigDecimal(100.0)), 2);

			BigDecimal margem = one.subtract(custoOperacionalBD).subtract(aux);
			margem = margem.setScale(5, RoundingMode.HALF_DOWN);
			margem = margem.multiply(new BigDecimal(100.0));

			preco.setMargem(margem);

			calcularCoeficiente(preco);

			Double descontoVista;
			try {
				String descontoAVistaStr = configFinder.findConfigByChave(Configs.VEN_DESCONTO_A_VISTA.getChave())
						.getValor();
				descontoVista = 1.00 - Double.valueOf(descontoAVistaStr);
			} catch (ViewException e1) {
				String erro = "Erro ao buscar desconto a vista";
				logger.error(erro, e1);
				throw new IllegalStateException(erro);
			}

			BigDecimal precoVista = CurrencyUtils.getBigDecimalCurrency(CurrencyUtils
					.multiplica(preco.getPrecoPrazo().doubleValue(), descontoVista));
			preco.setPrecoVista(precoVista);

		} catch (ViewException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws ViewException {

		ApplicationContext context = new ClassPathXmlApplicationContext("D:/ocabit/bonerp/trunk/bonerp-war/src/main/resources/applicationContext.xml");

		CalculoPreco calcIPreco = (CalculoPreco) context
				.getBean("calcIPreco");

		BigDecimal precoCusto = CurrencyUtils.getBigDecimalCurrency(678.54);

		IPreco preco = new ProdutoPreco();
		preco.setCustoOperacional(CurrencyUtils.getBigDecimalScaled(35.0, 3));
		preco.setPrazo(59);
		preco.setMargem(CurrencyUtils.getBigDecimalScaled(33.328, 3));
		preco.setPrecoCusto(precoCusto);
		calcIPreco.calcularPrecos(preco);

		System.out.println("\r\n\r\nPRAZO: " + preco.getPrazo());
		System.out.println("MARGEM: " + preco.getMargem());
		System.out.println("PRECO CUSTO: " + preco.getPrecoCusto());
		System.out.println("PRECO PRAZO: " + preco.getPrecoPrazo());

		IPreco precoSemMargem = new ProdutoPreco();
		precoSemMargem.setCustoOperacional(CurrencyUtils.getBigDecimalScaled(35.0, 3));
		precoSemMargem.setPrazo(59);
		precoSemMargem.setPrecoCusto(precoCusto);
		precoSemMargem.setPrecoPrazo(preco.getPrecoPrazo());
		calcIPreco.calcularMargem(precoSemMargem);

		System.out.println("\r\n\r\nPRAZO: " + precoSemMargem.getPrazo());
		System.out.println("MARGEM: " + precoSemMargem.getMargem());
		System.out.println("PRECO CUSTO: " + precoSemMargem.getPrecoCusto());
		System.out.println("PRECO PRAZO: " + precoSemMargem.getPrecoPrazo());

		IPreco preco3 = new ProdutoPreco();
		preco3.setCustoOperacional(CurrencyUtils.getBigDecimalScaled(35.0, 3));
		preco3.setPrazo(59);
		preco3.setMargem(precoSemMargem.getMargem());
		preco3.setPrecoCusto(precoCusto);
		calcIPreco.calcularPrecos(preco3);

		System.out.println("\r\n\r\nPRAZO: " + preco3.getPrazo());
		System.out.println("MARGEM: " + preco3.getMargem());
		System.out.println("PRECO CUSTO: " + preco3.getPrecoCusto());
		System.out.println("PRECO PRAZO: " + preco3.getPrecoPrazo());

	}

	/**
	 * Arredonda sempre de 10 em 10 centavos, sendo que 0,13=0,20 0,15=0,10 e
	 * 0,16=0,20
	 *
	 * @param val
	 * @return
	 */
	public static BigDecimal arredondar(BigDecimal bdVal) {
		bdVal = bdVal.setScale(6, RoundingMode.HALF_UP);
		bdVal = bdVal.divide(new BigDecimal(0.1), RoundingMode.HALF_UP);
		bdVal = CurrencyUtils.getBigDecimalScaled(bdVal.doubleValue(), 0, RoundingMode.HALF_UP);
		Double parteInteira = bdVal.longValue() + 0.0d;
		return CurrencyUtils.getBigDecimalCurrency(CurrencyUtils.multiplica(parteInteira, 0.1));
	}
}
