package com.bonsucesso.erp.producao.model;



import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

import com.bonsucesso.erp.estoque.model.Subdepartamento;
import com.ocabit.base.model.EntityIdImpl;
import com.ocabit.jpa.listener.EntityIdListener;
import com.ocabit.jpa.listener.UpperCaseListener;


/**
 * Entidade TipoArtigo.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "prod_tipo_artigo",
		uniqueConstraints = { @UniqueConstraint(columnNames = { "descricao" }),
				@UniqueConstraint(columnNames = { "codigo" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class TipoArtigo extends EntityIdImpl implements Comparable<TipoArtigo> {

	/**
	 *
	 */
	private static final long serialVersionUID = 5341907574426782271L;

	@ManyToOne(optional = false)
	@JoinColumn(name = "subdepto_id", nullable = false)
	@NotNull(message = "O campo 'Subdepto' deve ser informado")
	private Subdepartamento subdepto;

	@Column(nullable = false, length = 100)
	@NotNull(message = "'Descrição' deve possuir entre 2 e 100 caracteres")
	@Size(min = 2, max = 100, message = "'Descrição' deve possuir entre 2 e 100 caracteres")
	private String descricao;

	@Column(nullable = false, length = 5)
	@NotNull(message = "'Código' deve ser informado")
	private Integer codigo;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 15, name = "modo_calculo")
	@NotNull(message = "'Modo Cálculo' deve ser informado")
	private ModoCalculoPrecoConfeccao modoCalculo = ModoCalculoPrecoConfeccao.MODO_1;

	public Subdepartamento getSubdepto() {
		return subdepto;
	}

	public void setSubdepto(Subdepartamento subdepto) {
		this.subdepto = subdepto;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String nome) {
		descricao = nome;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public ModoCalculoPrecoConfeccao getModoCalculo() {
		return modoCalculo;
	}

	public void setModoCalculo(ModoCalculoPrecoConfeccao modoCalculo) {
		this.modoCalculo = modoCalculo;
	}

	//@Override
	@Override
	public int hashCode() {
		final int prime = 199;
		final int result = 211;
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
		final TipoArtigo iObj = (TipoArtigo) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(descricao, iObj.descricao)
				.isEquals();
	}

	@Override
	public int compareTo(TipoArtigo o) {
		return new CompareToBuilder()
				.append(descricao, o.descricao)
				.toComparison();
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
