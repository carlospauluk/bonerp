package com.bonsucesso.erp.estoque.model;



import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
 * Entidade GradeTamanho.
 *
 * Contém os tamanhos incluídos numa grade.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "est_grade_tamanho",
		uniqueConstraints = { @UniqueConstraint(columnNames = { "tamanho", "grade_id" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class GradeTamanho extends EntityIdImpl implements Comparable<GradeTamanho> {

	/**
	 *
	 */
	private static final long serialVersionUID = 2912358569709656431L;

	@ManyToOne(optional = false)
	@JoinColumn(name = "grade_id", nullable = false)
	@NotNull(message = "O campo 'Grade' deve ser informado")
	private Grade grade;

	@Column(nullable = false, length = 100)
	@Size(min = 1, max = 100, message = "'Tamanho' deve possuir entre 2 e 100 caracteres")
	private String tamanho;

	@Column(nullable = false)
	@Min(value = 1, message = "'Ordem' deve ser um número inteiro entre 1 e 14")
	@Max(value = 14, message = "'Ordem' deve ser um número inteiro entre 1 e 14")
	private Integer ordem;

	@Column(nullable = false)
	@Min(value = 1, message = "'Posição' deve ser um número inteiro entre 1 e 14")
	@Max(value = 14, message = "'Posição' deve ser um número inteiro entre 1 e 14")
	private Integer posicao;

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public String getTamanho() {
		return tamanho;
	}

	public void setTamanho(String tamanho) {
		this.tamanho = tamanho;
	}

	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

	public Integer getPosicao() {
		return posicao;
	}

	public void setPosicao(Integer posicao) {
		this.posicao = posicao;
	}

	@Override
	public int hashCode() {
		final int prime = 101;
		final int result = 29;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).append(grade).append(tamanho).toHashCode();
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
		final GradeTamanho iObj = (GradeTamanho) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(grade, iObj.grade)
				.append(tamanho, iObj.tamanho)
				.isEquals();
	}

	@Override
	public int compareTo(GradeTamanho o) {
		return new CompareToBuilder()
				.append(ordem, o.ordem)
				.toComparison();
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
