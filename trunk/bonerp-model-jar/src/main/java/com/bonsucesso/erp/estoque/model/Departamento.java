package com.bonsucesso.erp.estoque.model;



import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.CompareToBuilder;
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
 * Entidade Departamento.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "est_depto",
		uniqueConstraints = { @UniqueConstraint(columnNames = { "nome" }), @UniqueConstraint(columnNames = { "codigo" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class Departamento extends EntityIdImpl implements Comparable<Departamento> {

	/**
	 *
	 */
	private static final long serialVersionUID = 810330331107854718L;

	@Column(nullable = false, length = 100)
	@Size(min = 2, max = 100, message = "'Nome' deve possuir entre 2 e 100 caracteres")
	private String nome;

	@Column(nullable = false, length = 5)
	@NotNull(message = "'Código' deve ser informado")
	private Integer codigo;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "depto", orphanRemoval = false)
	@OrderBy("nome")
	private List<Subdepartamento> subdeptos;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public List<Subdepartamento> getSubdeptos() {
		return subdeptos;
	}

	public void setSubdeptos(List<Subdepartamento> subdeptos) {
		this.subdeptos = subdeptos;
	}

	//@Override
	@Override
	public int hashCode() {
		final int prime = 43;
		final int result = 631;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).append(nome).toHashCode();
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
		final Departamento iObj = (Departamento) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(nome, iObj.nome)
				.isEquals();
	}

	@Override
	public int compareTo(Departamento o) {
		return new CompareToBuilder()
				.append(nome, o.nome)
				.toComparison();
	}

	@Override
	public String toStringToView() {
		return "[" + codigo + " - " + nome + "]";
	}

}
