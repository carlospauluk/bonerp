package com.bonsucesso.erp.producao.model;



import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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
@Table(name = "prod_lote_confeccao_item_qtde",
		uniqueConstraints = { @UniqueConstraint(
				columnNames = { "lote_confeccao_item_id", "grade_tamanho_id" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class LoteConfeccaoItemQtde extends EntityIdImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = -2623175394052916502L;

	@ManyToOne(optional = false)
	@JoinColumn(name = "lote_confeccao_item_id", nullable = false)
	@NotNull(message = "O campo 'Item do Lote' deve ser informado")
	private LoteConfeccaoItem loteConfeccaoItem;

	@ManyToOne(optional = false)
	@JoinColumn(name = "grade_tamanho_id", nullable = false)
	@NotNull(message = "O campo 'Grade/Tamanho' deve ser informado")
	private GradeTamanho gradeTamanho;

	@Column(name = "qtde", nullable = false)
	@NotNull(message = "Campo 'Qtde' precisa ser informado")
	@Min(value = 0, message = "Campo 'Qtde' precisa ser informado")
	private Integer qtde;

	// -------------------------------------------------------

	public LoteConfeccaoItem getLoteConfeccaoItem() {
		return loteConfeccaoItem;
	}

	public void setLoteConfeccaoItem(LoteConfeccaoItem loteConfeccaoItem) {
		this.loteConfeccaoItem = loteConfeccaoItem;
	}

	public GradeTamanho getGradeTamanho() {
		return gradeTamanho;
	}

	public void setGradeTamanho(GradeTamanho gradeTamanho) {
		this.gradeTamanho = gradeTamanho;
	}

	public Integer getQtde() {
		return qtde;
	}

	public void setQtde(Integer qtde) {
		this.qtde = qtde;
	}

	@Override
	public int hashCode() {
		final int prime = 283;
		final int result = 7;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).append(loteConfeccaoItem).append(gradeTamanho).toHashCode();
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
		final LoteConfeccaoItemQtde iObj = (LoteConfeccaoItemQtde) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(loteConfeccaoItem, iObj.loteConfeccaoItem)
				.append(gradeTamanho, iObj.gradeTamanho)
				.isEquals();
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
