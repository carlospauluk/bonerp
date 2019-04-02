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
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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
@Table(name = "prod_confeccao_preco",
		uniqueConstraints = { @UniqueConstraint(columnNames = { "confeccao_id", "descricao" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class ConfeccaoPreco extends EntityIdImpl implements IPreco, Comparable<ConfeccaoPreco> {

	/**
	 *
	 */
	private static final long serialVersionUID = -3660456325162309957L;

	@ManyToOne(optional = false)
	@JoinColumn(name = "confeccao_id", nullable = false)
	@NotNull(message = "O campo 'Confecção' deve ser informado")
	private Confeccao confeccao;

	@Column(nullable = false, length = 200)
	@Size(min = 2, max = 200, message = "'Descrição' deve possuir entre 2 e 100 caracteres")
	private String descricao;

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

	@Transient
	private BigDecimal precoVigente;

	// -------------------------------------------------------

	/**
	 *
	 */
	public ConfeccaoPreco() {
		super();
	}

	public Confeccao getConfeccao() {
		return confeccao;
	}

	public void setConfeccao(Confeccao confeccao) {
		this.confeccao = confeccao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
		return precoVigente;
	}

	public void setPrecoVigente(BigDecimal precoVigente) {
		this.precoVigente = precoVigente;
	}

	@Override
	public int hashCode() {
		final int prime = 929;
		final int result = 787;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).append(confeccao).append(descricao).toHashCode();
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
		final ConfeccaoPreco iObj = (ConfeccaoPreco) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(confeccao, iObj.confeccao)
				.append(descricao, iObj.descricao)
				.isEquals();
	}

	@Override
	public int compareTo(ConfeccaoPreco o) {
		return new CompareToBuilder()
				.append(confeccao, o.confeccao)
				.append(descricao, o.descricao)
				.toComparison();
	}

	public void resetPadroesPreco() {
		setCustoOperacional(getConfeccao().getCustoOperacionalPadrao());
		setCustoFinanceiro(getConfeccao().getCustoFinanceiroPadrao());
		setMargem(getConfeccao().getMargemPadrao());
		setPrazo(getConfeccao().getPrazoPadrao());
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
