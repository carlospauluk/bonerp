package com.bonsucesso.erp.producao.catalogo.model;



import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
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
 * Entidade que representa um catálogo.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "prod_catalogo",
		uniqueConstraints = { @UniqueConstraint(columnNames = { "descricao" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class Catalogo extends EntityIdImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = 4514450323562936279L;

	@Column(nullable = false, length = 200)
	@Size(min = 2, max = 200, message = "'Descrição' deve possuir entre 2 e 100 caracteres")
	private String descricao;

	@OneToMany(mappedBy = "catalogo", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<CatalogoItem> itens;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<CatalogoItem> getItens() {
		return itens;
	}

	public void setItens(List<CatalogoItem> itens) {
		this.itens = itens;
	}

	@Override
	public int hashCode() {
		final int prime = 449;
		final int result = 823;
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
		final Catalogo iObj = (Catalogo) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(descricao, iObj.descricao)
				.isEquals();
	}

}
