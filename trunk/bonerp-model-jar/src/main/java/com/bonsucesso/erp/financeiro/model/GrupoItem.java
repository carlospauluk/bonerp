package com.bonsucesso.erp.financeiro.model;



import java.math.BigDecimal;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
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
import com.ocabit.utils.currency.CurrencyUtils;


/**
 * Entidade que representa um item de um Grupo de Movimentações (como a fatura
 * de um mês do cartão de crédito, por exemplo).
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "fin_grupo_item", uniqueConstraints = { @UniqueConstraint(columnNames = { "descricao" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class GrupoItem extends EntityIdImpl implements Comparable<GrupoItem> {

	/**
	 *
	 */
	private static final long serialVersionUID = -6850929189728281860L;

	/**
	 * Grupo de Movimentação.
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "grupo_pai_id", nullable = false)
	private Grupo pai;

	@Column(name = "descricao", nullable = false, length = 40)
	@NotEmpty(message = "Campo 'Descrição' precisa ser informado")
	@NotNull(message = "Campo 'Descrição' precisa ser informado")
	private String descricao;

	@Column(name = "dt_vencto", nullable = false)
	@Temporal(TemporalType.DATE)
	@NotNull(message = "Campo 'Dt Vencto' precisa ser informado")
	private Date dtVencto;

	/**
	 * Para efeitos de navegação.
	 */
	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH,
			CascadeType.DETACH }, optional = true)
	@JoinColumn(name = "anterior_id", nullable = true)
	private GrupoItem anterior;

	/**
	 * Para efeitos de navegação.
	 */
	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH,
			CascadeType.DETACH }, optional = true)
	@JoinColumn(name = "proximo_id", nullable = true)
	private GrupoItem proximo;

	/**
	 * Qual o valor informado (deve sempre bater com a soma das movimentações).
	 */
	@Column(name = "valor_informado", nullable = true)
	@Min(value = 0l, message = "Campo 'Valor Inf' precisa ser informado")
	private BigDecimal valorInformado;

	// Calcula o valor total a partir dos lançamentos
	@Transient
	private BigDecimal valorLanctos;

	@Transient
	private BigDecimal diferenca;

	/**
	 * Quais as movimentações.
	 */
	@OneToMany(mappedBy = "grupoItem", fetch = FetchType.LAZY)
	private List<Movimentacao> movimentacoes;

	/**
	 * Por qual carteira este item será/foi pago.
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "carteira_pagante_id", nullable = false)
	@NotNull(message = "Campo 'Carteira' precisa ser informado")
	private Carteira carteiraPagante;

	/**
	 * Se fechado, não deve ser possível associar ou desassociar alguma
	 * movimentação.
	 */
	@Column(name = "fechado", nullable = false)
	@NotNull(message = "Campo 'Fechado' precisa ser informado")
	private Boolean fechado = false;

	/**
	 * Controlado pelo JPA.
	 */

	public Grupo getPai() {
		return pai;
	}

	public void setPai(Grupo pai) {
		this.pai = pai;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * getter de dtVencto.
	 *
	 * @return
	 */
	public Date getDtVencto() {
		// nunca usa o Date interno (por questões de segurança - não perder
		// controle para fora do objeto)
		return dtVencto;
	}

	/**
	 * setter para dtVencto (criando novo objeto por questões de segurança)
	 *
	 * @param dtVencto
	 */
	public void setDtVencto(Date dtVencto) {
		this.dtVencto = dtVencto == null ? null : new Date(dtVencto.getTime());
	}

	public GrupoItem getAnterior() {
		return anterior;
	}

	public void setAnterior(GrupoItem anterior) {
		this.anterior = anterior;
	}

	public GrupoItem getProximo() {
		return proximo;
	}

	public void setProximo(GrupoItem proximo) {
		this.proximo = proximo;
	}

	public BigDecimal getValorInformado() {
		return valorInformado;
	}

	public void setValorInformado(BigDecimal valorInformado) {
		this.valorInformado = valorInformado;
	}

	public BigDecimal getValorLanctos() {

		try {
			if ((getMovimentacoes() != null) && (getMovimentacoes().size() > 0)) {
				try {
					BigDecimal bdValor = CurrencyUtils.getBigDecimalCurrency(0.0);
					for (Movimentacao m : getMovimentacoes()) {
						if (m.getCategoria().getCodigo().toString().startsWith("1")) {
							bdValor = bdValor.add(m.getValorTotal());
						} else {
							bdValor = bdValor.subtract(m.getValorTotal());
						}
					}
					valorLanctos = bdValor.abs();
				} catch (Exception e) {
					throw new IllegalStateException(e);
				}
			}
		} catch (Exception e) {

		}

		return valorLanctos;
	}

	public void setValorLanctos(BigDecimal valorLanctos) {
		this.valorLanctos = valorLanctos;
	}

	public BigDecimal getDiferenca() {
		if (diferenca == null) {
			if ((getValorLanctos() != null) && (getValorInformado() != null)) {
				diferenca = getValorLanctos().subtract(getValorInformado());
			}
		}
		return diferenca;
	}

	public void setDiferenca(BigDecimal diferenca) {
		this.diferenca = diferenca;
	}

	public List<Movimentacao> getMovimentacoes() {
		return movimentacoes;
	}

	public void setMovimentacoes(List<Movimentacao> movimentacoes) {
		this.movimentacoes = movimentacoes;
	}

	public Carteira getCarteiraPagante() {
		return carteiraPagante;
	}

	public void setCarteiraPagante(Carteira carteiraPagante) {
		this.carteiraPagante = carteiraPagante;
	}

	public Boolean getFechado() {
		return fechado;
	}

	public void setFechado(Boolean fechado) {
		this.fechado = fechado;
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
		final GrupoItem iObj = (GrupoItem) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder().append(descricao, iObj.descricao).isEquals();
	}

	@Override
	public int compareTo(GrupoItem o) {
		return new CompareToBuilder().append(descricao, o.descricao)
				.toComparison();
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
