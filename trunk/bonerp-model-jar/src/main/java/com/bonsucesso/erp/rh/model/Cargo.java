package com.bonsucesso.erp.rh.model;



import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.ocabit.base.model.EntityIdImpl;
import com.ocabit.jpa.listener.EntityIdListener;
import com.ocabit.jpa.listener.UpperCaseListener;


/**
 * Entidade Cargo.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "rh_cargo",
		uniqueConstraints = { @UniqueConstraint(columnNames = { "descricao" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class Cargo extends EntityIdImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = -4454262618231575934L;

	@Column(nullable = true, length = 200, name = "descricao")
	@Size(min = 2, max = 200, message = "'Descrição' deve possuir entre 2 e 200 caracteres")
	private String descricao;

	@Column(name = "comissaoPorVendas", nullable = false)
	@NotNull(message = "Campo 'Comissão por Vendas' precisa ser informado")
	private Boolean comissaoPorVendas = Boolean.FALSE;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Boolean getComissaoPorVendas() {
		return comissaoPorVendas;
	}

	public void setComissaoPorVendas(Boolean comissaoPorVendas) {
		this.comissaoPorVendas = comissaoPorVendas;
	}

	@Override
	public int hashCode() {
		final int prime = 751;
		final int result = 241;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).append(descricao).toHashCode();
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
		final Cargo iObj = (Cargo) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(descricao, iObj.descricao)
				.isEquals();
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
