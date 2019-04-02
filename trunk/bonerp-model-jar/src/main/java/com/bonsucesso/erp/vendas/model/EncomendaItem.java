package com.bonsucesso.erp.vendas.model;



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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.bonsucesso.erp.estoque.model.GradeTamanho;
import com.bonsucesso.erp.estoque.model.Produto;
import com.ocabit.base.model.EntityIdImpl;
import com.ocabit.jpa.listener.EntityIdListener;
import com.ocabit.jpa.listener.UpperCaseListener;


/**
 * Classe para a entidade Encomenda.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "ven_encomenda_item")
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class EncomendaItem extends EntityIdImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2240062772864137434L;

	@ManyToOne(optional = false)
	@JoinColumn(name = "encomenda_id", nullable = false)
	@NotNull(message = "O campo 'Encomenda' deve ser informado")
	private Encomenda encomenda;

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

	/**
	 * Aqui pode ter sido o PRECO_PRAZO ou PRECO_PROMO (ou ainda outro preço alterado na hora da compra).
	 */
	@Column(name = "preco_encomenda", nullable = false)
	@NotNull(message = "Campo 'Preço Encomenda' precisa ser informado")
	@Min(value = 0, message = "Campo 'Preço Encomenda' precisa ser informado")
	private BigDecimal precoEncomenda;

	@Column(name = "alteracao_preco", nullable = false)
	@NotNull(message = "Campo 'Alteração Preço' precisa ser informado")
	private Boolean alteracaoPreco = false;

	@Column(nullable = true, length = 5000, name = "obs")
	@Size(min=0,max=5000,message="Campo 'Obs' deve conter no máximo 5.000 caracteres")
	private String obs;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 50)
	@NotNull(message = "Campo 'Status' precisa ser informado")
	private StatusEncomenda status;

	@Column(name = "integrado_ao_estoque", nullable = false)
	@NotNull(message = "Campo 'Integrado' precisa ser informado")
	private Boolean integradoAoEstoque = false;

	public Encomenda getEncomenda() {
		return encomenda;
	}

	public void setEncomenda(Encomenda encomenda) {
		this.encomenda = encomenda;
	}

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

	public BigDecimal getPrecoEncomenda() {
		return precoEncomenda;
	}

	public void setPrecoEncomenda(BigDecimal precoEncomenda) {
		this.precoEncomenda = precoEncomenda;
	}

	public Boolean getAlteracaoPreco() {
		return alteracaoPreco;
	}

	public void setAlteracaoPreco(Boolean alteracaoPreco) {
		this.alteracaoPreco = alteracaoPreco;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public StatusEncomenda getStatus() {
		return status;
	}

	public void setStatus(StatusEncomenda status) {
		this.status = status;
	}

	public Boolean getIntegradoAoEstoque() {
		return integradoAoEstoque;
	}

	public void setIntegradoAoEstoque(Boolean integradoAoEstoque) {
		this.integradoAoEstoque = integradoAoEstoque;
	}

	@Override
	public int hashCode() {
		final int prime = 281;
		final int result = 277;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).append(encomenda).append(produto).append(gradeTamanho).toHashCode();
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
		final EncomendaItem iObj = (EncomendaItem) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(encomenda, iObj.encomenda)
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
