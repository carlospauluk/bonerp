package com.bonsucesso.erp.fiscal.model;



import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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
@Table(name = "fis_ncm")
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@DynamicUpdate(true)
@DynamicInsert(true)
public class NCM extends EntityIdImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = -7344027993521224745L;

	@Column(name = "codigo", nullable = false)
	@Min(value = 1)
	@NotNull(message = "Campo 'Código' precisa ser informado")
	private Integer codigo;

	@Column(name = "descricao", nullable = false, length = 200)
	@NotEmpty(message = "Campo 'Descrição' precisa ser informado")
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
		final int prime = 739;
		final int result = 139;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).append(codigo).toHashCode();
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
		final NCM iObj = (NCM) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder().append(codigo, iObj.codigo).isEquals();
	}

}
