package com.bonsucesso.erp.estoque.model;



import java.math.BigDecimal;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
 * Entidade PedidoCompraItem.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "est_pedido_compra_item")
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class PedidoCompraItem extends EntityIdImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6257425969336351613L;

	@ManyToOne(optional = false)
	@JoinColumn(name = "pedido_compra_id", nullable = false)
	@NotNull(message = "O campo 'pedido_compra_id' deve ser informado")
	private PedidoCompra pedidoCompra;

	@ManyToOne(optional = false)
	@JoinColumn(name = "subdepto_id", nullable = false)
	@NotNull(message = "O campo 'Subdepto' deve ser informado")
	private Subdepartamento subdepto;

	/**
	 * Só é preenchido nos casos onde é uma recompra de um item já existente no estoque.
	 */
	@ManyToOne(optional = true)
	@JoinColumn(name = "produto", nullable = true)
	private Produto produto;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 50)
	@NotNull(message = "Campo 'Status' precisa ser informado")
	private PedidoCompraItemStatus status;

	@Column(nullable = false)
	@Size(min = 1, message = "'Ordem' deve ser um número inteiro maior que 0")
	private Integer ordem;

	@Column(name = "qtde", nullable = false)
	@NotNull(message = "Campo 'Qtde' precisa ser informado")
	private BigDecimal qtde;

	public PedidoCompra getPedidoCompra() {
		return pedidoCompra;
	}

	public void setPedidoCompra(PedidoCompra pedidoCompra) {
		this.pedidoCompra = pedidoCompra;
	}

	public Subdepartamento getSubdepto() {
		return subdepto;
	}

	public void setSubdepto(Subdepartamento subdepto) {
		this.subdepto = subdepto;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public PedidoCompraItemStatus getStatus() {
		return status;
	}

	public void setStatus(PedidoCompraItemStatus status) {
		this.status = status;
	}

	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

	public BigDecimal getQtde() {
		return qtde;
	}

	public void setQtde(BigDecimal qtde) {
		this.qtde = qtde;
	}

	@Override
	public int hashCode() {
		final int prime = 641;
		final int result = 701;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result)
				.append(pedidoCompra)
				.append(ordem)
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
		final PedidoCompraItem iObj = (PedidoCompraItem) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(pedidoCompra, iObj.pedidoCompra)
				.append(ordem, iObj.ordem)
				.isEquals();
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
