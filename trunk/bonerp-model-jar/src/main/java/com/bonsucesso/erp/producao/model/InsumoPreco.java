package com.bonsucesso.erp.producao.model;



import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.bonsucesso.erp.estoque.model.Fornecedor;
import com.bonsucesso.erp.estoque.model.IPreco;
import com.ocabit.base.model.EntityIdImpl;
import com.ocabit.jpa.listener.EntityIdListener;
import com.ocabit.jpa.listener.UpperCaseListener;


/**
 *
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "prod_insumo_preco", uniqueConstraints = { @UniqueConstraint(columnNames = { "insumo_id",
		"dt_custo", "fornecedor_id" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class InsumoPreco extends EntityIdImpl implements IPreco, Cloneable {

	/**
	 *
	 */
	private static final long serialVersionUID = -8251762759100786178L;

	@ManyToOne(optional = false)
	@JoinColumn(name = "insumo_id", nullable = false)
	@NotNull(message = "O campo 'Insumo' deve ser informado")
	private Insumo insumo;

	@ManyToOne(optional = true)
	@JoinColumn(name = "fornecedor_id", nullable = true)
	private Fornecedor fornecedor;

	@Column(name = "preco_custo", nullable = false)
	@NotNull(message = "Campo 'Preço Custo' precisa ser informado")
	@Min(value = 0, message = "Campo 'Preço Custo' precisa ser informado")
	private BigDecimal precoCusto;

	@Column(name = "coeficiente", nullable = false)
	@NotNull(message = "Campo 'Coeficiente' precisa ser informado")
	@Min(value = 0, message = "Campo 'Coeficiente' precisa ser informado")
	private BigDecimal coeficiente;

	@Column(name = "margem", nullable = false)
	@NotNull(message = "Campo 'Margem' precisa ser informado")
	private BigDecimal margem;

	@Column(name = "custo_operacional", nullable = false)
	@NotNull(message = "Campo 'Custo Operacional' precisa ser informado")
	@Min(value = 0, message = "Campo 'Custo Operacional' precisa ser informado")
	private BigDecimal custoOperacional;
	
	@Column(name = "custo_financeiro", nullable = true)
	@NotNull(message = "Campo 'Custo Financeiro' precisa ser informado")
	@Min(value = 0, message = "Campo 'Custo Financeiro' não pode ser negativo")
	private BigDecimal custoFinanceiro;

	@Column(name = "prazo", nullable = false)
	@NotNull(message = "Campo 'Prazo' precisa ser informado")
	@Min(value = 0, message = "Campo 'Prazo' precisa ser informado")
	private Integer prazo;

	@Column(name = "preco_prazo", nullable = false)
	@NotNull(message = "Campo 'Preço Prazo' precisa ser informado")
	@Min(value = 0, message = "Campo 'Preço Prazo' precisa ser informado")
	private BigDecimal precoPrazo;

	@Column(name = "preco_vista", nullable = false)
	@NotNull(message = "Campo 'Preço Vista' precisa ser informado")
	@Min(value = 0, message = "Campo 'Preço Vista' precisa ser informado")
	private BigDecimal precoVista;

	@Column(nullable = false, name = "dt_custo")
	@Temporal(TemporalType.DATE)
	@NotNull(message = "Campo 'Dt Custo' precisa ser informado")
	private Date dtCusto;
	
	@Column(name = "atual", nullable = false)
	@NotNull(message = "Campo 'Atual' precisa ser informado")
	private Boolean atual = false;

	@Transient
	private BigDecimal precoVigente;

	public InsumoPreco() {

	}

	public InsumoPreco(Insumo insumo, BigDecimal precoCusto, BigDecimal coeficiente,
			Date dtCusto) {
		setInsumo(insumo);
		setPrecoCusto(precoCusto);
		setCoeficiente(coeficiente);
		setDtCusto(dtCusto);
	}

	/**
	 * A fins de testes.
	 *
	 * @param precoPrazo
	 */
	public InsumoPreco(Insumo insumo, BigDecimal precoPrazo) {
		super();
		setPrecoPrazo(precoPrazo);
		setInsumo(insumo);
	}

	public Insumo getInsumo() {
		return insumo;
	}

	public void setInsumo(Insumo insumo) {
		this.insumo = insumo;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	@Override
	public BigDecimal getPrecoCusto() {
		return precoCusto;
	}

	@Override
	public void setPrecoCusto(BigDecimal precoCusto) {
		this.precoCusto = precoCusto;
	}

	@Override
	public BigDecimal getCoeficiente() {
		return coeficiente;
	}

	@Override
	public void setCoeficiente(BigDecimal coeficiente) {
		this.coeficiente = coeficiente;
	}

	@Override
	public BigDecimal getMargem() {
		return margem;
	}

	@Override
	public void setMargem(BigDecimal margem) {
		this.margem = margem;
	}

	@Override
	public BigDecimal getCustoOperacional() {
		return custoOperacional;
	}

	@Override
	public void setCustoOperacional(BigDecimal custoOperacional) {
		this.custoOperacional = custoOperacional;
	}
	
	@Override
	public BigDecimal getCustoFinanceiro() {
		return custoFinanceiro;
	}

	@Override
	public void setCustoFinanceiro(BigDecimal custoFinanceiro) {
		this.custoFinanceiro = custoFinanceiro;
	}

	@Override
	public Integer getPrazo() {
		return prazo;
	}

	@Override
	public void setPrazo(Integer prazo) {
		this.prazo = prazo;
	}

	@Override
	public BigDecimal getPrecoPrazo() {
		return precoPrazo;
	}

	@Override
	public void setPrecoPrazo(BigDecimal precoPrazo) {
		this.precoPrazo = precoPrazo;
	}

	@Override
	public BigDecimal getPrecoVista() {
		return precoVista;
	}

	@Override
	public void setPrecoVista(BigDecimal precoVista) {
		this.precoVista = precoVista;
	}

	@Override
	public Date getDtCusto() {
		return dtCusto;
	}

	@Override
	public void setDtCusto(Date dtCusto) {
		this.dtCusto = dtCusto;
	}

	public BigDecimal getPrecoVigente() {
		if ((getPrecoPrazo() != null) && (getPrecoPrazo().doubleValue() > 0.0)) {
			setPrecoVigente(getPrecoPrazo());
		} else {
			precoVigente = BigDecimal.ZERO;
		}
		return precoVigente;
	}

	public void setPrecoVigente(BigDecimal precoVigente) {
		this.precoVigente = precoVigente;
	}

	// -------------------------------------------------------

	@Override
	public int hashCode() {
		final int prime = 223;
		final int result = 227;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).append(insumo).append(dtCusto).toHashCode();
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
		final InsumoPreco iObj = (InsumoPreco) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(insumo, iObj.insumo)
				.append(dtCusto, iObj.dtCusto)
				.isEquals();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}
}
