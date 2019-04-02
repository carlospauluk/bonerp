package com.bonsucesso.erp.financeiro.model;



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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotEmpty;

import com.ocabit.base.model.EntityIdImpl;
import com.ocabit.jpa.listener.EntityIdListener;
import com.ocabit.jpa.listener.UpperCaseListener;


/**
 * Entidade Carteira.
 *
 * @author Pauluk
 *
 */
@Entity
@Table(name = "fin_carteira", uniqueConstraints = { @UniqueConstraint(columnNames = { "descricao" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class Carteira extends EntityIdImpl implements Comparable<Carteira> {

	/**
	 *
	 */
	private static final long serialVersionUID = 4049278785507086863L;

	@Column(name = "codigo", nullable = true)
	@NotNull(message = "Campo 'Código' precisa ser informado")
	private Integer codigo;

	@Column(name = "descricao", nullable = false, length = 40)
	@NotEmpty(message = "Campo 'Descrição' precisa ser informado")
	private String descricao;

	/**
	 * Movimentações desta carteira não poderão ter suas datas alteradas para antes desta.
	 */
	@Column(name = "dt_consolidado", nullable = false)
	@Temporal(TemporalType.DATE)
	@NotNull(message = "Campo 'Dt Consolidado' precisa ser informado")
	private Date dtConsolidado;

	/*
	 * Uma Carteira concreta é aquela em que podem ser efetuados créditos e
	 * débitos, como uma conta corrente. Um Grupo de Movimentação só pode ser
	 * filho de uma Carteira concreta. Uma movimentação que contenha um grupo de
	 * movimentação, precisa ter sua carteira igual a carteira do grupo de
	 * movimentação.
	 */
	@Column(name = "concreta", nullable = false)
	@NotNull(message = "Campo 'Concreta' precisa ser informado")
	private Boolean concreta = false;

	/*
	 * Informa se esta carteira pode conter movimentações com status ABERTA.
	 * Útil principalmente para o relatório de contas a pagar/receber, para não considerar movimentações de outras carteiras.
	 */
	@Column(name = "abertas", nullable = false)
	@NotNull(message = "Campo 'Abertas' precisa ser informado")
	private Boolean abertas = false;

	/*
	 * Informa se esta carteira é um caixa (ex.: caixa a vista, caixa a prazo).
	 */
	@Column(name = "caixa", nullable = false)
	@NotNull(message = "Campo 'Caixa' precisa ser informado")
	private Boolean caixa = false;

	/**
	 * Informa se esta carteira possui talão de cheques.
	 */
	@Column(name = "cheque", nullable = false)
	@NotNull(message = "Campo 'Cheque' precisa ser informado")
	private Boolean cheque = false;

	/**
	 * Código do Banco (conforme códigos oficiais).
	 */
	@ManyToOne(optional = true)
	@JoinColumn(name = "banco_id", nullable = true)
	private Banco banco;

	/**
	 * Código da agência (sem o dígito verificador).
	 */
	@Column(name = "agencia", nullable = true, length = 30)
	private String agencia;

	/**
	 * Número da conta no banco (não segue um padrão).
	 */
	@Column(name = "conta", nullable = true, length = 30)
	private String conta;

	@Column(name = "limite", nullable = true, precision = 15, scale = 2)
	private BigDecimal limite;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDtConsolidado() {
		return dtConsolidado;
	}

	public void setDtConsolidado(Date dtConsolidado) {
		this.dtConsolidado = dtConsolidado;
	}

	public Boolean getConcreta() {
		return concreta;
	}

	public void setConcreta(Boolean concreta) {
		this.concreta = concreta;
	}

	public Boolean getAbertas() {
		return abertas;
	}

	public void setAbertas(Boolean abertas) {
		this.abertas = abertas;
	}

	public Boolean getCaixa() {
		return caixa;
	}

	public void setCaixa(Boolean caixa) {
		this.caixa = caixa;
	}

	public Boolean getCheque() {
		return cheque;
	}

	public void setCheque(Boolean cheque) {
		this.cheque = cheque;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public BigDecimal getLimite() {
		return limite;
	}

	public void setLimite(BigDecimal limite) {
		this.limite = limite;
	}

	@Override
	public int hashCode() {
		// Utiliza números primos
		final int prime = 37;
		final int result = 227;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).append(descricao)
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// Verificação padrão
		if (obj == null) {
			return false;
		}
		// Verificação padrão
		if (obj == this) {
			return true;
		}
		// Verificação padrão
		if (obj.getClass() != getClass()) {
			return false;
		}
		final Carteira iObj = (Carteira) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder().append(descricao, iObj.descricao).isEquals();
	}

	@Override
	public int compareTo(Carteira carteira) {
		return new CompareToBuilder().append(codigo, carteira.codigo)
				.toComparison();
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
