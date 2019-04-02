package com.bonsucesso.erp.estoque.model;



import java.math.BigDecimal;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
 * Entidade Subdepartamento.
 *
 * Um Subdepartamento sempre está sob um Departamento.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "est_subdepto",
		uniqueConstraints = { @UniqueConstraint(columnNames = { "codigo" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class Subdepartamento extends EntityIdImpl implements Comparable<Subdepartamento> {

	/**
	 *
	 */
	private static final long serialVersionUID = -696824755662198712L;

	@Column(nullable = false, length = 100)
	@Size(min = 2, max = 100, message = "'Nome' deve possuir entre 2 e 100 caracteres")
	private String nome;

	@Column(nullable = false, length = 5)
	@NotNull(message = "'Código' deve ser informado")
	private Integer codigo;

	@Column(name = "margem", nullable = true)
	@NotNull(message = "Campo 'Margem' precisa ser informado")
	private BigDecimal margem;

	@ManyToOne(optional = false)
	@JoinColumn(name = "depto_id", nullable = false)
	@NotNull(message = "O campo 'depto' deve ser informado")
	private Departamento depto;

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

	public BigDecimal getMargem() {
		return margem;
	}

	public void setMargem(BigDecimal margem) {
		this.margem = margem;
	}

	public Departamento getDepto() {
		return depto;
	}

	public void setDepto(Departamento depto) {
		this.depto = depto;
	}

	@Override
	public int hashCode() {
		final int prime = 101;
		final int result = 29;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result)
				.append(codigo)
				.toHashCode();
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
		final Subdepartamento iObj = (Subdepartamento) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(codigo, iObj.codigo)
				.isEquals();
	}

	@Override
	public String toString() {
		return "Subdepartamento [id=" + getId() + ", nome=" + nome + ", codigo=" + codigo + ", depto="
				+ depto + "]";
	}

	@Override
	public int compareTo(Subdepartamento o) {
		return new CompareToBuilder()
				.append(codigo, o.codigo)
				.toComparison();
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
