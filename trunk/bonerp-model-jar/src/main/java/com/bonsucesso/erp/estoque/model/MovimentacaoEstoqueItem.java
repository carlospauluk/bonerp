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
 * Entidade MovimentacaoEstoqueItem.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "est_movimentacao_item",
		uniqueConstraints = { @UniqueConstraint(columnNames = { "movimentacao_id", "produto_id" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class MovimentacaoEstoqueItem extends EntityIdImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1901210444593420380L;

	@ManyToOne(optional = false)
	@JoinColumn(name = "movimentacao_id", nullable = false)
	@NotNull(message = "O campo 'movimentacao_id' deve ser informado")
	private MovimentacaoEstoque movimentacaoEstoque;

	@ManyToOne(optional = false)
	@JoinColumn(name = "produto_id", nullable = false)
	@NotNull(message = "O campo 'Produto' deve ser informado")
	private Produto produto;

	@Column(nullable = false)
	@Size(min = 1, message = "'Ordem' deve ser um número inteiro maior que 0")
	private Integer ordem;

	@Column(name = "qtde", nullable = false)
	@NotNull(message = "Campo 'Qtde' precisa ser informado")
	private BigDecimal qtde;

	public MovimentacaoEstoque getMovimentacaoEstoque() {
		return movimentacaoEstoque;
	}

	public void setMovimentacaoEstoque(MovimentacaoEstoque movimentacaoEstoque) {
		this.movimentacaoEstoque = movimentacaoEstoque;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
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
		final int prime = 491;
		final int result = 191;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result)
				.append(movimentacaoEstoque)
				.append(produto)
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
		final MovimentacaoEstoqueItem iObj = (MovimentacaoEstoqueItem) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(movimentacaoEstoque, iObj.movimentacaoEstoque)
				.append(produto, iObj.produto)
				.isEquals();
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
