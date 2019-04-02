package com.bonsucesso.erp.producao.model;



import java.math.BigDecimal;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.bonsucesso.erp.estoque.model.GradeTamanho;
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
@Table(name = "prod_confeccao_item_qtde",
		uniqueConstraints = { @UniqueConstraint(columnNames = { "confeccao_item_id", "grade_tamanho_id" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class ConfeccaoItemQtde extends EntityIdImpl implements Comparable<ConfeccaoItemQtde> {

	/**
	 *
	 */
	private static final long serialVersionUID = 8397730427470263907L;

	@ManyToOne(optional = false)
	@JoinColumn(name = "confeccao_item_id", nullable = false)
	@NotNull(message = "O campo 'Confecção Item' deve ser informado")
	private ConfeccaoItem confeccaoItem;

	@ManyToOne(optional = false)
	@JoinColumn(name = "grade_tamanho_id", nullable = false)
	@NotNull(message = "O campo 'Grade/Tamanho' deve ser informado")
	private GradeTamanho gradeTamanho;

	@Column(name = "qtde", nullable = false)
	@NotNull(message = "Campo 'Coeficiente' precisa ser informado")
	@Min(value = 0, message = "Campo 'Qtde' precisa ser informado")
	private BigDecimal qtde;

	@Transient
	private BigDecimal valor;

	// -------------------------------------------------------

	/**
	 *
	 */
	public ConfeccaoItemQtde() {
		super();
	}

	public ConfeccaoItem getConfeccaoItem() {
		return confeccaoItem;
	}

	public void setConfeccaoItem(ConfeccaoItem confeccaoItem) {
		this.confeccaoItem = confeccaoItem;
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

	@Override
	public int hashCode() {
		final int prime = 173;
		final int result = 179;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).append(confeccaoItem).append(gradeTamanho).toHashCode();
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
		final ConfeccaoItemQtde iObj = (ConfeccaoItemQtde) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(confeccaoItem, iObj.confeccaoItem)
				.append(gradeTamanho, iObj.gradeTamanho)
				.isEquals();
	}

	@Override
	public int compareTo(ConfeccaoItemQtde o) {
		return new CompareToBuilder()
				.append(confeccaoItem, o.confeccaoItem)
				.append(gradeTamanho, o.gradeTamanho)
				.toComparison();
	}

	public BigDecimal getValor() {
		try {
			valor = getQtde().multiply(getConfeccaoItem().getInsumo().getPrecoAtual()
					.getPrecoCusto());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
