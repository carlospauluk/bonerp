package com.bonsucesso.erp.financeiro.model;



import java.util.ArrayList;
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
import javax.persistence.OrderBy;
import javax.persistence.Table;
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
import org.hibernate.validator.constraints.Range;

import com.ocabit.base.model.EntityIdImpl;
import com.ocabit.jpa.listener.EntityIdListener;
import com.ocabit.jpa.listener.UpperCaseListener;


/**
 * Entidade Grupo de Movimentação (utilizada para agrupar movimentações, como
 * nos casos de fatura de cartão de crédito, por exemplo).
 *
 * @author CarlosEduardo
 *
 */
@Entity
@Table(name = "fin_grupo", uniqueConstraints = { @UniqueConstraint(columnNames = { "descricao" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class Grupo extends EntityIdImpl implements Comparable<Grupo> {

	/**
	 *
	 */
	private static final long serialVersionUID = -1722593092304703269L;

	@Column(name = "descricao", nullable = false, length = 40)
	@NotEmpty(message = "Campo 'Descrição' precisa ser informado")
	@NotNull(message = "Campo 'Descrição' precisa ser informado")
	private String descricao;

	/**
	 * Dia de vencimento no mês.
	 */
	@Column(name = "dia_vencto", nullable = false)
	@Range(min = 1, max = 32)
	private Integer diaVencto = 1;

	/**
	 * Dia a partir do qual as movimentações são consideradas com vencimento
	 * para próximo mês.
	 */
	@Column(name = "dia_inicio", nullable = false)
	@Range(min = 1, max = 31)
	private Integer diaInicioAprox = 1;

	/**
	 * Se fechado, não deve ser possível associar ou desassociar alguma
	 * movimentação.
	 */
	@Column(name = "ativo", nullable = false)
	@NotNull(message = "Campo 'Ativo' precisa ser informado")
	private Boolean ativo = true;

	/**
	 * Por qual carteira este item será/foi pago.
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "carteira_pagante_id", nullable = false)
	@NotNull(message = "Campo 'Carteira' precisa ser informado")
	private Carteira carteiraPagantePadrao;

	/**
	 * Categoria padrão para os lançamentos desta carteira.
	 */
	@ManyToOne(optional = true)
	@JoinColumn(name = "categoria_padrao_id", nullable = true)
	private Categoria categoriaPadrao;

	/**
	 * Itens filho deste Grupo de Movimentação.
	 */
	@OneToMany(mappedBy = "pai", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("dtVencto DESC")
	private List<GrupoItem> itens;

	/**
	 * Controlado pelo JPA.
	 */

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getDiaVencto() {
		return diaVencto;
	}

	public void setDiaVencto(Integer diaVencto) {
		this.diaVencto = diaVencto;
	}

	/**
	 * Obtém os itens deste Grupo de Movimentação já ordenados.
	 *
	 * @return
	 */
	public List<GrupoItem> getItens() {
		if (itens == null) {
			itens = new ArrayList<GrupoItem>();
		}
		return itens;
	}

	public void setItens(List<GrupoItem> itens) {
		this.itens = itens;
	}

	public Integer getDiaInicioAprox() {
		return diaInicioAprox;
	}

	public void setDiaInicioAprox(Integer diaInicio) {
		diaInicioAprox = diaInicio;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Carteira getCarteiraPagantePadrao() {
		return carteiraPagantePadrao;
	}

	public void setCarteiraPagantePadrao(Carteira carteiraPagantePadrao) {
		this.carteiraPagantePadrao = carteiraPagantePadrao;
	}

	public Categoria getCategoriaPadrao() {
		return categoriaPadrao;
	}

	public void setCategoriaPadrao(Categoria categoriaPadrao) {
		this.categoriaPadrao = categoriaPadrao;
	}

	@Override
	public int hashCode() {
		// Utiliza números primos
		final int prime = 29;
		final int result = 67;
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
		final Grupo iObj = (Grupo) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder().append(descricao, iObj.descricao).isEquals();
	}

	@Override
	public int compareTo(Grupo o) {
		return new CompareToBuilder().append(descricao, o.descricao)
				.toComparison();
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
