package com.bonsucesso.erp.estoque.model;



import java.math.BigDecimal;
import java.util.Date;


/**
 * Interface padrão para todos os "tipos" de preços: ProdutoPreco, InsumoPreco, ConfeccaoPreco.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface IPreco {

	public BigDecimal getPrecoCusto();

	public void setPrecoCusto(BigDecimal precoCusto);

	public BigDecimal getCoeficiente();

	public void setCoeficiente(BigDecimal coeficiente);

	public BigDecimal getMargem();

	public void setMargem(BigDecimal margem);

	public BigDecimal getCustoOperacional();

	public void setCustoOperacional(BigDecimal custoOperacional);
	
	public BigDecimal getCustoFinanceiro();
	
	public void setCustoFinanceiro(BigDecimal custoFinanceiro);

	public Integer getPrazo();

	public void setPrazo(Integer prazo);

	public BigDecimal getPrecoPrazo();

	public void setPrecoPrazo(BigDecimal precoPrazo);

	public BigDecimal getPrecoVista();

	public void setPrecoVista(BigDecimal precoVista);

	public Date getDtCusto();

	public void setDtCusto(Date dtCusto);
}
