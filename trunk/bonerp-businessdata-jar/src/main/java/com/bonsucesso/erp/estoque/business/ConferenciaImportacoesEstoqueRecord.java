package com.bonsucesso.erp.estoque.business;



import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import com.bonsucesso.erp.financeiro.model.RegistroConferencia;


/**
 * Para exibir no datatable da cnferenciaImportacoesEstoqueView.
 * 
 * @author Carlos Eduardo Pauluk
 *
 */
public class ConferenciaImportacoesEstoqueRecord {

	private Date mesAno;

	private RegistroConferencia totalPecasInformado;
	private RegistroConferencia totalPecasImportado;
	private BigDecimal totalPecasDif;

	private RegistroConferencia totalCustoInformado;
	private RegistroConferencia totalCustoImportado;
	private BigDecimal totalCustoDif;

	private RegistroConferencia totalPrecoPrazoInformado;
	private RegistroConferencia totalPrecoPrazoImportado;
	private BigDecimal totalPrecoPrazoDif;

	private RegistroConferencia totalVendidoInformado;
	private RegistroConferencia totalVendidoImportado;
	private BigDecimal totalVendidoDif;

	public Date getMesAno() {
		return mesAno;
	}

	public void setMesAno(Date mesAno) {
		this.mesAno = mesAno;
	}

	public RegistroConferencia getTotalPecasInformado() {
		return totalPecasInformado;
	}

	public void setTotalPecasInformado(RegistroConferencia totalPecasInformado) {
		this.totalPecasInformado = totalPecasInformado;
	}

	public RegistroConferencia getTotalPecasImportado() {
		return totalPecasImportado;
	}

	public void setTotalPecasImportado(RegistroConferencia totalPecasImportado) {
		this.totalPecasImportado = totalPecasImportado;
	}

	public BigDecimal getTotalPecasDif() {
		if (getTotalPecasImportado() != null && getTotalPecasImportado().getValor() != null
				&& getTotalPecasInformado() != null && getTotalPecasInformado().getValor() != null) {
			this.totalPecasDif = getTotalPecasImportado().getValor().divide(getTotalPecasInformado().getValor(), 4, RoundingMode.HALF_DOWN);
		}
		return totalPecasDif;
	}

	public void setTotalPecasDif(BigDecimal totalPecasDif) {
		this.totalPecasDif = totalPecasDif;
	}

	public RegistroConferencia getTotalCustoInformado() {
		return totalCustoInformado;
	}

	public void setTotalCustoInformado(RegistroConferencia totalCustoInformado) {
		this.totalCustoInformado = totalCustoInformado;
	}

	public RegistroConferencia getTotalCustoImportado() {
		return totalCustoImportado;
	}

	public void setTotalCustoImportado(RegistroConferencia totalCustoImportado) {
		this.totalCustoImportado = totalCustoImportado;
	}

	public BigDecimal getTotalCustoDif() {
		if (getTotalCustoImportado() != null && getTotalCustoImportado().getValor() != null
				&& getTotalCustoInformado() != null && getTotalCustoInformado().getValor() != null) {
			this.totalCustoDif = getTotalCustoImportado().getValor().divide(getTotalCustoInformado().getValor(), 4, RoundingMode.HALF_DOWN);
		}
		return totalCustoDif;
	}

	public void setTotalCustoDif(BigDecimal totalCustoDif) {
		this.totalCustoDif = totalCustoDif;
	}

	public RegistroConferencia getTotalPrecoPrazoInformado() {
		return totalPrecoPrazoInformado;
	}

	public void setTotalPrecoPrazoInformado(RegistroConferencia totalPrecoPrazoInformado) {
		this.totalPrecoPrazoInformado = totalPrecoPrazoInformado;
	}

	public RegistroConferencia getTotalPrecoPrazoImportado() {
		return totalPrecoPrazoImportado;
	}

	public void setTotalPrecoPrazoImportado(RegistroConferencia totalPrecoPrazoImportado) {
		this.totalPrecoPrazoImportado = totalPrecoPrazoImportado;
	}

	public BigDecimal getTotalPrecoPrazoDif() {
		if (getTotalPrecoPrazoImportado() != null && getTotalPrecoPrazoImportado().getValor() != null
				&& getTotalPrecoPrazoInformado() != null && getTotalPrecoPrazoInformado().getValor() != null) {
			this.totalPrecoPrazoDif = getTotalPrecoPrazoImportado().getValor().divide(getTotalPrecoPrazoInformado().getValor(), 4, RoundingMode.HALF_DOWN);
		}
		return totalPrecoPrazoDif;
	}

	public void setTotalPrecoPrazoDif(BigDecimal totalPrecoPrazoDif) {
		this.totalPrecoPrazoDif = totalPrecoPrazoDif;
	}

	public RegistroConferencia getTotalVendidoInformado() {
		return totalVendidoInformado;
	}

	public void setTotalVendidoInformado(RegistroConferencia totalVendidoInformado) {
		this.totalVendidoInformado = totalVendidoInformado;
	}

	public RegistroConferencia getTotalVendidoImportado() {
		return totalVendidoImportado;
	}

	public void setTotalVendidoImportado(RegistroConferencia totalVendidoImportado) {
		this.totalVendidoImportado = totalVendidoImportado;
	}

	public BigDecimal getTotalVendidoDif() {
		if (getTotalVendidoImportado() != null && getTotalVendidoImportado().getValor() != null
				&& getTotalVendidoInformado() != null && getTotalVendidoInformado().getValor() != null) {
			this.totalVendidoDif = getTotalVendidoImportado().getValor().divide(getTotalVendidoInformado().getValor(), 4, RoundingMode.HALF_DOWN);
		}
		return totalVendidoDif;
	}

	public void setTotalVendidoDif(BigDecimal totalVendidoDif) {
		this.totalVendidoDif = totalVendidoDif;
	}
	
	public String getIconeByDif(BigDecimal dif) {
		if (dif == null) {
			return "attention";
		}
		if (dif != null && dif.abs().doubleValue() <= 1.001 && dif.abs().doubleValue() >= 0.990) {
			return "checked";
		} else {
			return "cancel";
		}
	}

}
