package com.bonsucesso.erp.financeiro.model;



import java.math.BigDecimal;
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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotEmpty;

import com.ocabit.base.model.EntityIdImpl;
import com.ocabit.jpa.listener.EntityIdListener;
import com.ocabit.jpa.listener.UpperCaseListener;


/**
 * Entidade para manter registros de conferências mensais.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "fin_reg_conf", uniqueConstraints = { @UniqueConstraint(columnNames = { "descricao", "carteira_id",
		"dt_registro" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class RegistroConferencia extends EntityIdImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = 7452220827008063312L;

	@Column(name = "descricao", nullable = false, length = 200)
	@NotEmpty(message = "Campo 'Descrição' precisa ser informado")
	@NotNull(message = "Campo 'Descrição' precisa ser informado")
	private String descricao;

	@Column(name = "dt_registro", nullable = false)
	@Temporal(TemporalType.DATE)
	@NotNull(message = "Campo 'Dt Registro' precisa ser informado")
	private Date dtRegistro;

	@ManyToOne(optional = true)
	@JoinColumn(name = "carteira_id", nullable = true)
	private Carteira carteira;

	@Column(name = "valor", nullable = true)
	@Min(value = 0l, message = "Campo 'Valor Inf' precisa ser informado")
	private BigDecimal valor;

	@Column(nullable = true, length = 5000, name = "obs")
	@Size(min=0,max=5000,message="Campo 'Obs' deve conter no máximo 5.000 caracteres")
	private String obs;

	/**
	 * Controlado pelo JPA.
	 */

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDtRegistro() {
		return dtRegistro;
	}

	public void setDtRegistro(Date dtRegistro) {
		this.dtRegistro = dtRegistro;
	}

	public Carteira getCarteira() {
		return carteira;
	}

	public void setCarteira(Carteira carteira) {
		this.carteira = carteira;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	@Override
	public int hashCode() {
		// Utiliza números primos
		final int prime = 599;
		final int result = 877;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).append(descricao)
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// Verificação padrão
		if (obj == null) {
			return false;
		}
		// Verificação padrão
		if (obj == this) {
			return true;
		}
		// Verificação padrão
		if (obj.getClass() != getClass()) {
			return false;
		}
		final RegistroConferencia iObj = (RegistroConferencia) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder().append(descricao, iObj.descricao).isEquals();
	}

	@Override
	public String toStringToView() {
		return toString();
	}

}
