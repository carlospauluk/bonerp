package com.bonsucesso.erp.estoque.model;



import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
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
 * Entidade Grade.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "est_fornecedor_tipo",
		uniqueConstraints = { @UniqueConstraint(columnNames = { "descricao" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class TipoFornecedor extends EntityIdImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4480585636427882156L;

	@Column(nullable = false, length = 100)
	@Size(min = 2, max = 100, message = "'Descrição' deve possuir entre 2 e 100 caracteres")
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	//@Override
	@Override
	public int hashCode() {
		final int prime = 67;
		final int result = 523;
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
		final TipoFornecedor iObj = (TipoFornecedor) obj;
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
