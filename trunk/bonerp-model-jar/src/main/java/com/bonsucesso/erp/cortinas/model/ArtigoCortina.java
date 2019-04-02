package com.bonsucesso.erp.cortinas.model;



import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.bonsucesso.erp.estoque.model.Produto;
import com.ocabit.base.model.EntityIdImpl;
import com.ocabit.jpa.listener.EntityIdListener;
import com.ocabit.jpa.listener.UpperCaseListener;


/**
 *
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "crtn_artigo_cortina", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "produto_id" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@DynamicUpdate(true)
@DynamicInsert(true)
public class ArtigoCortina extends EntityIdImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = 2257010320808544565L;

	@OneToOne(optional = false, cascade = { CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER)
	@JoinColumn(name = "produto_id", nullable = false)
	@NotNull(message = "O campo 'Produto' deve ser informado")
	private Produto produto = new Produto();

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 100, name = "tipo_artigo_cortina")
	@NotNull(message = "O campo 'Tipo' deve ser informado")
	private TipoArtigoCortina tipoArtigoCortina;

	@OneToOne(optional = true, cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "tecido_id", nullable = true)
	private Tecido tecido;

	// -------------------------------------------------------

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public TipoArtigoCortina getTipoArtigoCortina() {
		return tipoArtigoCortina;
	}

	public void setTipoArtigoCortina(TipoArtigoCortina tipoArtigoCortina) {
		this.tipoArtigoCortina = tipoArtigoCortina;
	}

	public Tecido getTecido() {
		return tecido;
	}

	public void setTecido(Tecido tecido) {
		this.tecido = tecido;
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
		final ArtigoCortina iObj = (ArtigoCortina) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(produto, iObj.produto)
				.isEquals();
	}

	@Override
	public int hashCode() {
		final int prime = 19;
		final int result = 563;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).append(produto).toHashCode();
	}
	
	
	
	

	@Override
	public String toStringToView() {
		String s = "id: " + getId() + " >> ";
		if (getProduto() != null) {
			s += getProduto().getReduzido() + ": " + getProduto().getDescricao();
		}
		return s;
	}

}
