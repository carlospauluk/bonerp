package com.bonsucesso.erp.estoque.model;



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
 * Entidade Grade.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "est_grade",
		uniqueConstraints = { @UniqueConstraint(columnNames = { "codigo" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class Grade extends EntityIdImpl implements Comparable<Grade> {

	/**
	 *
	 */
	private static final long serialVersionUID = 6036479899445703056L;

	@Column(nullable = false, length = 5)
	@NotNull(message = "'Código' deve ser informado")
	private Integer codigo;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "unidade_produto_id", nullable = false)
	@NotNull(message = "'Unidade Padrão' deve ser informado")
	private UnidadeProduto unidadeProdutoPadrao;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "grade", orphanRemoval = false)
	@OrderBy("ordem")
	private List<GradeTamanho> tamanhos;

	@Column(nullable = true, length = 5000, name = "obs")
	@Size(min=0,max=5000,message="Campo 'Obs' deve conter no máximo 5.000 caracteres")
	private String obs;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public UnidadeProduto getUnidadeProdutoPadrao() {
		return unidadeProdutoPadrao;
	}

	public void setUnidadeProdutoPadrao(UnidadeProduto unidadeProdutoPadrao) {
		this.unidadeProdutoPadrao = unidadeProdutoPadrao;
	}

	public List<GradeTamanho> getTamanhos() {
		return tamanhos;
	}

	public void setTamanhos(List<GradeTamanho> tamanhos) {
		this.tamanhos = tamanhos;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}
	
	public GradeTamanho byPosicao(Integer posicao) {
		if (getTamanhos() != null && getTamanhos().size() > 0) {
			for (GradeTamanho gt : getTamanhos()) {
				if (gt.getPosicao().equals(posicao)) {
					return gt;
				}
			}
		}
		return null;
	}

	//@Override
	@Override
	public int hashCode() {
		final int prime = 641;
		final int result = 281;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).append(codigo).toHashCode();
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
		final Grade iObj = (Grade) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(codigo, iObj.codigo)
				.isEquals();
	}

	@Override
	public int compareTo(Grade o) {
		return new CompareToBuilder()
				.append(codigo, o.codigo)
				.toComparison();
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return getCodigo() + " (" + getObs() + ")";
	}

}
