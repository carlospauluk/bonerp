package com.bonsucesso.erp.estoque.view;



import java.io.Serializable;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.estoque.business.CalculoPreco;
import com.bonsucesso.erp.estoque.model.ProdutoPreco;
import com.ocabit.jsf.utils.JSFUtils;


/**
 * View para cálculos de preços.
 *
 * @author Carlos Eduardo Pauluk
 */
@Component("calculoPrecoFormView")
@Scope("view")
public final class CalculoPrecoFormView implements Serializable {

	protected static Logger logger = Logger.getLogger(CalculoPrecoFormView.class);

	/**
	 *
	 */
	private static final long serialVersionUID = -4390105187867329496L;

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

	public void calcular() {
		try {
			calculoPreco.calcularCoeficiente(getPreco());
			calculoPreco.calcularPrecos(getPreco());
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
