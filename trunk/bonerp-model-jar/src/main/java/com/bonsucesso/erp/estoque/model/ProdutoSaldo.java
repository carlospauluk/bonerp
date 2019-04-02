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
 *
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "est_produto_saldo", uniqueConstraints = { @UniqueConstraint(columnNames = { "produto_id",
		"grade_tamanho_id" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class ProdutoSaldo extends EntityIdImpl implements Cloneable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1292336226036287479L;

	@ManyToOne(optional = false)
	@JoinColumn(name = "produto_id", nullable = false)
	@NotNull(message = "O campo 'Produto' deve ser informado")
	private Produto produto;

	@ManyToOne(optional = false)
	@JoinColumn(name = "grade_tamanho_id", nullable = false)
	@NotNull(message = "Campo 'Grade/Tamanho' precisa ser informado")
	private GradeTamanho gradeTamanho;

	@Column(name = "qtde", nullable = false)
	@NotNull(message = "Campo 'Qtde' precisa ser informado")
	private BigDecimal qtde;

	@Column(name = "selec", nullable = false)
	@NotNull(message = "Campo 'Selecionado' precisa ser informado")
	private Boolean selecionado = false;

	// -------------------------------------------------------

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public GradeTamanho getGradeTamanho() {
		return gradeTamanho;
	}

	public void setGradeTamanho(GradeTamanho gradeTamanho) {
		this.gradeTamanho = gradeTamanho;
	}

	public BigDecimal getQtde() {
		return qtde;
	}

	public void setQtde(BigDecimal qtde) {
		this.qtde = qtde;
	}

	public Boolean getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(Boolean selecionado) {
		this.selecionado = selecionado;
	}

	@Override
	public int hashCode() {
		final int prime = 709;
		final int result = 857;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).append(produto).append(gradeTamanho).toHashCode();
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
		final ProdutoSaldo iObj = (ProdutoSaldo) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(produto, iObj.produto)
				.append(gradeTamanho, iObj.gradeTamanho)
				.isEquals();
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
