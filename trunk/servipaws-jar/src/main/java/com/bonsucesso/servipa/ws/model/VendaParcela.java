package com.bonsucesso.servipa.ws.model;



import java.io.Serializable;
import java.util.Date;

import javax.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * Entidade que representa os dados de uma parcela de venda da base do PREV2000.
 * 
 * @author Carlos Eduardo Pauluk
 * 
 */
public class VendaParcela implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5082223214035555009L;

	// campos da tabela CRD_VENDAS
	private Integer codVenda;
	private Integer codCliente;
	private String nome;
	private String cpf;
	private Integer pv;
	private Integer ano;
	private Boolean cartao;
	private Integer fatura;
	private Date dtCompra;
	private Double vlrTotalCompra;

	// campos da tabela CRD_VENDAS_PARCELAS
	private Integer numParcela;
	private Date dtVencto;
	private Double vlrParcela;
	private Date dtPagto;
	private Double juros;
	private Double desconto;
	private Double vlrPago;
	private Date dtBaixa;
	private String motivoBaixa;

	@Transient
	private String situacao;

	public Integer getCodVenda() {
		return codVenda;
	}

	public void setCodVenda(Integer codVenda) {
		this.codVenda = codVenda;
	}

	public Integer getCodCliente() {
		return codCliente;
	}

	public void setCodCliente(Integer codCliente) {
		this.codCliente = codCliente;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Integer getPv() {
		return pv;
	}

	public void setPv(Integer pv) {
		this.pv = pv;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Boolean getCartao() {
		return cartao;
	}

	public void setCartao(Boolean cartao) {
		this.cartao = cartao;
	}

	public Integer getFatura() {
		return fatura;
	}

	public void setFatura(Integer fatura) {
		this.fatura = fatura;
	}

	public Date getDtCompra() {
		return dtCompra;
	}

	public void setDtCompra(Date dtCompra) {
		this.dtCompra = dtCompra;
	}

	public Double getVlrTotalCompra() {
		return vlrTotalCompra;
	}

	public void setVlrTotalCompra(Double vlrTotalCompra) {
		this.vlrTotalCompra = vlrTotalCompra;
	}

	public Integer getNumParcela() {
		return numParcela;
	}

	public void setNumParcela(Integer numParcela) {
		this.numParcela = numParcela;
	}

	public Date getDtVencto() {
		return dtVencto;
	}

	public void setDtVencto(Date dtVencto) {
		this.dtVencto = dtVencto;
	}

	public Double getVlrParcela() {
		return vlrParcela;
	}

	public void setVlrParcela(Double vlrParcela) {
		this.vlrParcela = vlrParcela;
	}

	public Date getDtPagto() {
		return dtPagto;
	}

	public void setDtPagto(Date dtPagto) {
		this.dtPagto = dtPagto;
	}

	public Double getJuros() {
		return juros;
	}

	public void setJuros(Double juros) {
		this.juros = juros;
	}

	public Double getDesconto() {
		return desconto;
	}

	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}

	public Double getVlrPago() {
		return vlrPago;
	}

	public void setVlrPago(Double vlrPago) {
		this.vlrPago = vlrPago;
	}

	public Date getDtBaixa() {
		return dtBaixa;
	}

	public void setDtBaixa(Date dtBaixa) {
		this.dtBaixa = dtBaixa;
	}

	public String getMotivoBaixa() {
		return motivoBaixa;
	}

	public void setMotivoBaixa(String motivoBaixa) {
		this.motivoBaixa = motivoBaixa;
	}

	@Override
	public int hashCode() {
		final int prime = 881;
		final int result = 811;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).
				append(codVenda).
				append(numParcela).
				toHashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		final VendaParcela iObj = (VendaParcela) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(codVenda, iObj.codVenda)
				.append(numParcela, iObj.numParcela)
				.isEquals();
	}

	public String getSituacao() {
		if (getDtPagto() == null && getDtBaixa() == null) {
			situacao = "PENDENTE";
		} else if (getDtBaixa() != null) {
			situacao = "BAIXADO";
		} else {
			situacao = "PAGO";
		}
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

}
