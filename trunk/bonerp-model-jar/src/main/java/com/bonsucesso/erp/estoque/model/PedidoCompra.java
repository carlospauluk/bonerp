package com.bonsucesso.erp.estoque.model;



import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

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
 * Entidade NotaFiscal.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "est_pedido_compra",
		uniqueConstraints = { @UniqueConstraint(columnNames = { "codigo" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class PedidoCompra extends EntityIdImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6074436170407721273L;

	@Column(nullable = false, length = 18)
	@NotNull(message = "'Código' deve ser informado")
	private Long codigo;

	@ManyToOne(optional = false)
	@JoinColumn(name = "fornecedor_id", nullable = false)
	@NotNull(message = "O campo 'Fornecedor' deve ser informado")
	private Fornecedor fornecedor;

	@Column(nullable = true, name = "dt_emissao")
	@Temporal(TemporalType.DATE)
	private Date dtEmissao;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Date getDtEmissao() {
		return dtEmissao;
	}

	public void setDtEmissao(Date dtEmissao) {
		this.dtEmissao = dtEmissao;
	}

	@Override
	public int hashCode() {
		final int prime = 857;
		final int result = 593;
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
		final PedidoCompra iObj = (PedidoCompra) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(codigo, iObj.codigo)
				.isEquals();
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
