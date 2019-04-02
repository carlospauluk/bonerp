package com.bonsucesso.erp.base.model;



import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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
import com.ocabit.utils.calendar.CalendarUtil;


/**
 * Entidade Dia Útil.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "bon_dia_util",
		uniqueConstraints = { @UniqueConstraint(columnNames = { "dia" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class DiaUtil extends EntityIdImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = -7028477527086615167L;

	@Column(nullable = false, name = "dia")
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull(message = "'Dia' deve ser informado")
	private Date dia;

	@Column(name = "comercial", nullable = false)
	@NotNull(message = "Campo 'Comercial' precisa ser informado")
	private Boolean comercial = true;

	@Column(name = "financeiro", nullable = false)
	@NotNull(message = "Campo 'Financeiro' precisa ser informado")
	private Boolean financeiro = true;

	@Column(name = "descricao", nullable = true, length = 40)
	private String descricao;

	public Date getDia() {
		return dia;
	}

	public void setDia(Date dia) {
		this.dia = CalendarUtil.zeroDate(dia);
	}

	public Boolean getComercial() {
		return comercial;
	}

	public void setComercial(Boolean comercial) {
		this.comercial = comercial;
	}

	public Boolean getFinanceiro() {
		return financeiro;
	}

	public void setFinanceiro(Boolean financeiro) {
		this.financeiro = financeiro;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public int hashCode() {
		final int prime = 877;
		final int result = 563;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).append(dia).toHashCode();
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
		final DiaUtil iObj = (DiaUtil) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(dia, iObj.dia)
				.isEquals();
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
