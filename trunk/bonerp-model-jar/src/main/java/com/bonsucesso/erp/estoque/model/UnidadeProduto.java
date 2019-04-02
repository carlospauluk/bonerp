package com.bonsucesso.erp.estoque.model;



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
 * Enumeração para os possíveis tipos de unidades de produtos.
 *
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "est_unidade_produto",
		uniqueConstraints = { @UniqueConstraint(columnNames = { "label" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@DynamicUpdate(true)
@DynamicInsert(true)
public class UnidadeProduto extends EntityIdImpl {

	//
	//	MT("MT","Metro"),
	//	KG("KG","Quilo"),
	//	CJ("CJ","Conjunto"),
	//	PC("PC","Peça"),
	//	PAR("PAR","Par"),
	//	;

	/**
	 *
	 */
	private static final long serialVersionUID = 5873744613961350039L;

	@Column(nullable = false, length = 5)
	@Size(min = 2, max = 5, message = "'Label' deve possuir entre 2 e 5 caracteres")
	private String label;

	@Column(nullable = false, length = 100)
	@Size(min = 2, max = 100, message = "'Descrição' deve possuir entre 2 e 100 caracteres")
	private String descricao;

	@Column(nullable = false)
	private Integer fator;

	@Column(name = "casas_decimais", nullable = false)
	@Size(min = 0, max = 5, message = "'Casas Decimais' deve ser informado (entre 0 e 5)")
	private Integer casasDecimais;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getFator() {
		return fator;
	}

	public void setFator(Integer fator) {
		this.fator = fator;
	}

	public Integer getCasasDecimais() {
		return casasDecimais;
	}

	public void setCasasDecimais(Integer casasDecimais) {
		this.casasDecimais = casasDecimais;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		final int result = 163;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).append(label).toHashCode();
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
		final UnidadeProduto iObj = (UnidadeProduto) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(label, iObj.label)
				.isEquals();
	}

	@Override
	public String toString() {
		return "UnidadeProduto [id=" + getId() + ", label=" + label + ", descricao=" + descricao + "]";
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
