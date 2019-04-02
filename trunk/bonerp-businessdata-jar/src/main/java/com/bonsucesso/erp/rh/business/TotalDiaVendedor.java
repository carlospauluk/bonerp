package com.bonsucesso.erp.rh.business;



import java.math.BigDecimal;
import java.util.Date;

import com.bonsucesso.erp.rh.model.Funcionario;


/**
 *
 * Classe auxiliar para exibição no dataTable da view.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public class TotalDiaVendedor {

	private Funcionario vendedor;

	private Date dia;

	private BigDecimal total;

	public Funcionario getVendedor() {
		return vendedor;
	}

	public void setVendedor(Funcionario vendedor) {
		this.vendedor = vendedor;
	}

	public Date getDia() {
		return dia;
	}

	public void setDia(Date dia) {
		this.dia = dia;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

}
