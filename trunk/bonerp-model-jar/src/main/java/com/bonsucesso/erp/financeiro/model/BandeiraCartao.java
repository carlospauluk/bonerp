package com.bonsucesso.erp.financeiro.model;



import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
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
 * Entidade Bandeira de Cartão.
 * Ex.: MASTER MAESTRO, MASTER, VISA ELECTRON, VISA
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "fin_bandeira_cartao", uniqueConstraints = { @UniqueConstraint(columnNames = { "descricao" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class BandeiraCartao extends EntityIdImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = -8903575364046753782L;

	@Column(name = "descricao", nullable = false, length = 40)
	@NotEmpty(message = "Campo 'Descrição' precisa ser informado")
	@NotNull(message = "Campo 'Descrição' precisa ser informado")
	private String descricao;

	@ManyToOne(optional = false)
	@JoinColumn(name = "modo_id", nullable = false)
	@NotNull(message = "Campo 'Modo' precisa ser informado")
	@Valid
	private Modo modo;

	/**
	 * Campo utilizado para informar os diversos tipos de descrições desta bandeira (por questão de relatórios de importações).
	 */
	@Column(name = "labels", nullable = false, length = 2000)
	@NotEmpty(message = "Campo 'Labels' precisa ser informado")
	@NotNull(message = "Campo 'Labels' precisa ser informado")
	private String labels;

	/**
	 * Controlado pelo JPA.
	 */

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Modo getModo() {
		return modo;
	}

	public void setModo(Modo modo) {
		this.modo = modo;
	}

	public String getLabels() {
		return labels;
	}

	public void setLabels(String labels) {
		this.labels = labels;
	}

	@Override
	public int hashCode() {
		// Utiliza números primos
		final int prime = 3;
		final int result = 227;
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
		final BandeiraCartao iObj = (BandeiraCartao) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder().append(descricao, iObj.descricao).isEquals();
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
