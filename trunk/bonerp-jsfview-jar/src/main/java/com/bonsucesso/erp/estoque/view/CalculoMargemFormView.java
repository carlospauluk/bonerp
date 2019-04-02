package com.bonsucesso.erp.estoque.view;



import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.estoque.business.CalculoPreco;
import com.bonsucesso.erp.estoque.model.ProdutoPreco;
import com.ocabit.jsf.utils.JSFUtils;
import com.ocabit.utils.currency.CurrencyUtils;


/**
 * View para cálculos de preços.
 *
 * @author Carlos Eduardo Pauluk
 */
@Component("calculoMargemFormView")
@Scope("view")
public final class CalculoMargemFormView implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 718448408569330320L;

	protected static Logger logger = Logger.getLogger(CalculoMargemFormView.class);

	private Double valorTotalNota;

	private Double valorTotalProdutos;

	private Double precoCustoErrado;

	@Autowired
	private CalculoPreco calculoPreco;

	private ProdutoPreco preco;

	public CalculoPreco getCalculoPreco() {
		return calculoPreco;
	}

	public void setCalculoPreco(CalculoPreco calculoPreco) {
		this.calculoPreco = calculoPreco;
	}

	public ProdutoPreco getPreco() {
		if (preco == null) {
			preco = new ProdutoPreco();
			calculoPreco.novoPreco(preco);
		}
		return preco;
	}

	public void setPreco(ProdutoPreco preco) {
		this.preco = preco;
	}

	public Double getValorTotalNota() {
		return valorTotalNota;
	}

	public void setValorTotalNota(Double valorTotalNota) {
		this.valorTotalNota = valorTotalNota;
	}

	public Double getValorTotalProdutos() {
		return valorTotalProdutos;
	}

	public void setValorTotalProdutos(Double valorTotalProdutos) {
		this.valorTotalProdutos = valorTotalProdutos;
	}

	public Double getPrecoCustoErrado() {
		return precoCustoErrado;
	}

	public void setPrecoCustoErrado(Double precoCustoErrado) {
		this.precoCustoErrado = precoCustoErrado;
	}

	public void calcular() {
		try {

			BigDecimal bdValorTotalNota = CurrencyUtils.getBigDecimalCurrency(getValorTotalNota());
			BigDecimal bdValorTotalProdutos = CurrencyUtils.getBigDecimalCurrency(getValorTotalProdutos());

			BigDecimal icms = bdValorTotalNota.divide(bdValorTotalProdutos, 4, RoundingMode.HALF_DOWN);

			BigDecimal bdPrecoCustoErrado = CurrencyUtils.getBigDecimalCurrency(getPrecoCustoErrado());

			getPreco().setPrecoCusto(CurrencyUtils.getBigDecimalScaled(CurrencyUtils.multiplica(bdPrecoCustoErrado, icms),2));

			calculoPreco.calcularMargem(getPreco());
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			JSFUtils.addErrorMsg("Erro ao calcular o preço");
		}
	}

	public void limpar() {
		setPreco(null);
	}

}
