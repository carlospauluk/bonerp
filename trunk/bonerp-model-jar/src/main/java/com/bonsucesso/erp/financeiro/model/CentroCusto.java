package com.bonsucesso.erp.financeiro.model;



import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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

import com.ocabit.base.model.EntityIdImpl;
import com.ocabit.jpa.listener.EntityIdListener;
import com.ocabit.jpa.listener.UpperCaseListener;


/**
 * Entidade CentroCusto.
 *
 * @author Pauluk
 *
 */
@Entity
@Table(name = "fin_centrocusto", uniqueConstraints = { @UniqueConstraint(columnNames = { "descricao" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class CentroCusto extends EntityIdImpl implements Comparable<CentroCusto> {

	/**
	 *
	 */
	private static final long serialVersionUID = 8453481984206679538L;

	@Column(name = "codigo", nullable = true)
	@NotNull(message = "Campo 'Código' precisa ser informado")
	private Integer codigo;

	@Column(name = "descricao", nullable = false, length = 40)
	@NotEmpty(message = "Campo 'Descrição' precisa ser informado")
	@NotNull(message = "Campo 'Descrição' precisa ser informado")
	private String descricao;

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

	@Override
	public int hashCode() {
		// Utiliza números primos
		final int prime = 37;
		final int result = 13;
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
		final CentroCusto iObj = (CentroCusto) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder().append(descricao, iObj.descricao).isEquals();
	}

	@Override
	public int compareTo(CentroCusto o) {
		return new CompareToBuilder().append(codigo, o.codigo)
				.toComparison();
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
