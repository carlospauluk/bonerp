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
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.bonsucesso.erp.crm.model.Cliente;
import com.bonsucesso.erp.rh.model.Funcionario;
import com.ocabit.base.model.EntityIdImpl;
import com.ocabit.jpa.listener.EntityIdListener;
import com.ocabit.jpa.listener.UpperCaseListener;


/**
 * Classe para a entidade Encomenda.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "ven_encomenda",
		uniqueConstraints = { @UniqueConstraint(columnNames = { "numero" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class Encomenda extends EntityIdImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8584919095192095407L;

	@Column(name = "numero", nullable = true)
	private Integer numero;

	@Column(name = "dt_encomenda", nullable = false)
	@NotNull(message = "O campo 'Dt Encomenda' deve ser informado")
	private Date dtEncomenda;

	@ManyToOne(optional = true)
	@JoinColumn(name = "cliente_id", nullable = true)
	private Cliente cliente;

	@ManyToOne(optional = false)
	@JoinColumn(name = "vendedor_id", nullable = false)
	@NotNull(message = "O campo 'Vendedor' deve ser informado")
	private Funcionario vendedor;

	@Column(name = "sub_total", nullable = false)
	@NotNull(message = "O campo 'Sub Total' deve ser informado")
	private BigDecimal subTotal;

	@Column(name = "desconto_especial", nullable = true)
	@NotNull(message = "O campo 'Desconto Especial' deve ser informado")
	private BigDecimal descontoPlano;

	@Column(name = "historicoDesconto", length = 2000)
	private String historicoDesconto;

	@Column(name = "valor_total", nullable = false)
	@NotNull(message = "O campo 'Valor Total' deve ser informado")
	private BigDecimal valorTotal;

	/**
	 * Valor deixado pelo cliente como adiantamento/sinal.
	 */
	@Column(name = "valor_pago", nullable = false)
	@NotNull(message = "O campo 'Valor Pago' deve ser informado")
	private BigDecimal valorPago;

	@Transient
	private BigDecimal saldoPagar;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "encomenda", orphanRemoval = true)
	private List<EncomendaItem> itens;

	@Column(name = "deletado")
	private Boolean deletado = Boolean.FALSE;

	@Column(nullable = true, length = 5000, name = "obs")
	@Size(min=0,max=5000,message="Campo 'Obs' deve conter no máximo 5.000 caracteres")
	private String obs;

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer pv) {
		this.numero = pv;
	}

	public Date getDtEncomenda() {
		return dtEncomenda;
	}

	public void setDtEncomenda(Date dtEncomenda) {
		this.dtEncomenda = dtEncomenda;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Funcionario getVendedor() {
		return vendedor;
	}

	public void setVendedor(Funcionario funcionario) {
		vendedor = funcionario;
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

	public void setDescontoPlano(BigDecimal descontoEspecial) {
		this.descontoPlano = descontoEspecial;
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

	public BigDecimal getValorPago() {
		return valorPago;
	}

	public void setValorPago(BigDecimal valorPago) {
		this.valorPago = valorPago;
	}

	public BigDecimal getSaldoPagar() {
		try {
			BigDecimal valorTotal = getValorTotal() == null ? BigDecimal.ZERO : getValorTotal();
			BigDecimal valorPago = getValorPago() == null ? BigDecimal.ZERO : getValorPago();
			saldoPagar = valorTotal.add(valorPago);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		return saldoPagar;
	}

	public void setSaldoPagar(BigDecimal saldoPagar) {
		this.saldoPagar = saldoPagar;
	}

	public List<EncomendaItem> getItens() {
		if (itens == null) {
			itens = new ArrayList<EncomendaItem>();
		}
		return itens;
	}

	public void setItens(List<EncomendaItem> itens) {
		this.itens = itens;
	}

	public Boolean getDeletado() {
		return deletado;
	}

	public void setDeletado(Boolean deletado) {
		this.deletado = deletado;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	@Override
	public int hashCode() {
		final int prime = 97;
		final int result = 811;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).append(dtEncomenda).append(vendedor).toHashCode();
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
		final Encomenda iObj = (Encomenda) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(dtEncomenda, iObj.dtEncomenda)
				.append(vendedor, iObj.vendedor)
				.isEquals();
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
