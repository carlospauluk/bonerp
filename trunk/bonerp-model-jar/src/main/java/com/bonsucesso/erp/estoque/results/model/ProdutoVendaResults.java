package com.bonsucesso.erp.estoque.results.model;



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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
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
@Table(name = "est_produto_venda_results",
		uniqueConstraints = { @UniqueConstraint(columnNames = { "produto_id", "mesano" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class ProdutoVendaResults extends EntityIdImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3094718914251898703L;

	@ManyToOne(optional = false)
	@JoinColumn(name = "produto_id", nullable = false)
	@NotNull(message = "O campo 'Produto' deve ser informado")
	private Produto produto;

	@Column(name = "mesano", nullable = false)
	@NotNull(message = "O campo 'Mês/Ano' deve ser informado")
	private String mesano;

	@Column(name = "qtde", nullable = false)
	@NotNull(message = "Campo 'Qtde' precisa ser informado")
	private BigDecimal qtde;

	public ProdutoVendaResults() {
		super();
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public String getMesano() {
		return mesano;	
	}

	public void setMesano(String mesano) {
		this.mesano = mesano;
	}

	public BigDecimal getQtde() {
		return qtde;
	}

	public void setQtde(BigDecimal qtde) {
		this.qtde = qtde;
	}

	@Override
	public int hashCode() {
		final int prime = 919;
		final int result = 911;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).append(produto).append(mesano).toHashCode();
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
		final ProdutoVendaResults iObj = (ProdutoVendaResults) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(produto, iObj.produto)
				.append(mesano, iObj.mesano)
				.isEquals();
	}

	
}
