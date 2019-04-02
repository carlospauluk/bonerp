package com.bonsucesso.erp.vendas.model;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.bonsucesso.erp.crm.model.Cliente;
import com.bonsucesso.erp.fiscal.model.NotaFiscal;
import com.bonsucesso.erp.rh.model.Funcionario;
import com.ocabit.base.model.EntityIdImpl;
import com.ocabit.jpa.listener.EntityIdListener;
import com.ocabit.jpa.listener.UpperCaseListener;


/**
 * Classe para a entidade Venda.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "ven_venda",
		uniqueConstraints = { @UniqueConstraint(columnNames = { "pv", "mesano" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class Venda extends EntityIdImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = -1061407731787713786L;

	@ManyToOne(optional = false)
	@JoinColumn(name = "tipo_venda_id", nullable = false)
	@NotNull(message = "O campo 'Tipo' deve ser informado")
	private TipoVenda tipo;

	@Column(name = "pv", nullable = true)
	private Integer pv;

	@Column(name = "dt_venda", nullable = false)
	@NotNull(message = "O campo 'Dt Venda' deve ser informado")
	private Date dtVenda;

	/**
	 * Utilizado para fazer unique com o campo pv (YYYYMM)
	 */
	@Column(name = "mesano", length = 6, nullable = false)
	@NotNull(message = "O campo 'Mes/Ano' deve ser informado")
	private String mesano;

	@ManyToOne(optional = true)
	@JoinColumn(name = "cliente_id", nullable = true)
	private Cliente cliente;

	@ManyToOne(optional = false)
	@JoinColumn(name = "vendedor_id", nullable = false)
	@NotNull(message = "O campo 'Vendedor' deve ser informado")
	private Funcionario vendedor;

	@ManyToOne(optional = false)
	@JoinColumn(name = "plano_pagto_id", nullable = false)
	@NotNull(message = "O campo 'Plano Pagto' deve ser informado")
	private PlanoPagto planoPagto;

	@Column(name = "sub_total", nullable = false)
	@NotNull(message = "O campo 'Sub Total' deve ser informado")
	private BigDecimal subTotal;

	@Column(name = "desconto_plano", nullable = true)
	@NotNull(message = "O campo 'Desconto Plano' deve ser informado")
	private BigDecimal descontoPlano;

	@Column(name = "desconto_especial", nullable = true)
	@NotNull(message = "O campo 'Desconto Especial' deve ser informado")
	private BigDecimal descontoEspecial;

	@Column(name = "historicoDesconto", length = 2000)
	private String historicoDesconto;

	@Column(name = "valor_total", nullable = false)
	@NotNull(message = "O campo 'Valor Total' deve ser informado")
	private BigDecimal valorTotal;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "venda", orphanRemoval = true)
	private List<VendaItem> itens;

	@Column(name = "deletado")
	private Boolean deletado = Boolean.FALSE;

	@OneToOne(optional = true)
	@JoinTable(
			name = "fis_nf_venda",
			joinColumns = @JoinColumn(name = "venda_id"),
			inverseJoinColumns = @JoinColumn(name = "nota_fiscal_id"))
	private NotaFiscal notaFiscal;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = true, length = 30)
	@NotNull(message = "Campo 'Status' precisa ser informado")
	private StatusVenda status;

	public String getMesano() {
		return mesano;
	}

	public void setMesano(String mesano) {
		this.mesano = mesano;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public TipoVenda getTipo() {
		return tipo;
	}

	public void setTipo(TipoVenda tipo) {
		this.tipo = tipo;
	}

	public Integer getPv() {
		return pv;
	}

	public void setPv(Integer pv) {
		this.pv = pv;
	}

	public Date getDtVenda() {
		return dtVenda;
	}

	public void setDtVenda(Date dtVenda) {
		this.dtVenda = dtVenda;
	}

	public Funcionario getVendedor() {
		return vendedor;
	}

	public void setVendedor(Funcionario funcionario) {
		vendedor = funcionario;
	}

	public PlanoPagto getPlanoPagto() {
		return planoPagto;
	}

	public void setPlanoPagto(PlanoPagto planoPagto) {
		this.planoPagto = planoPagto;
	}

	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	public BigDecimal getDescontoPlano() {
		return descontoPlano;
	}

	public void setDescontoPlano(BigDecimal descontoPlano) {
		this.descontoPlano = descontoPlano;
	}

	public BigDecimal getDescontoEspecial() {
		return descontoEspecial;
	}

	public void setDescontoEspecial(BigDecimal descontoEspecial) {
		this.descontoEspecial = descontoEspecial;
	}

	public String getHistoricoDesconto() {
		return historicoDesconto;
	}

	public void setHistoricoDesconto(String historicoDesconto) {
		this.historicoDesconto = historicoDesconto;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public List<VendaItem> getItens() {
		if (itens == null) {
			itens = new ArrayList<VendaItem>();
		}
		return itens;
	}

	public void setItens(List<VendaItem> itens) {
		this.itens = itens;
	}

	public Boolean getDeletado() {
		return deletado;
	}

	public void setDeletado(Boolean deletado) {
		this.deletado = deletado;
	}

	public NotaFiscal getNotaFiscal() {
		return notaFiscal;
	}

	public void setNotaFiscal(NotaFiscal notaFiscal) {
		this.notaFiscal = notaFiscal;
	}
	
	public StatusVenda getStatus() {
		return status;
	}

	public void setStatus(StatusVenda status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 409;
		final int result = 487;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).append(dtVenda).append(vendedor).toHashCode();
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
		final Venda iObj = (Venda) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(dtVenda, iObj.dtVenda)
				.append(vendedor, iObj.vendedor)
				.isEquals();
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
