package com.bonsucesso.erp.rh.model;



import java.math.BigDecimal;
import java.math.RoundingMode;
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
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
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
import com.ocabit.utils.currency.CurrencyUtils;


/**
 *
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "rh_funcionario_cargo", uniqueConstraints = { @UniqueConstraint(columnNames = { "funcionario_id",
		"dt_inicio" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class FuncionarioCargo extends EntityIdImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = 3151991608124762508L;

	@ManyToOne(optional = false)
	@JoinColumn(name = "funcionario_id", nullable = false)
	@NotNull(message = "O campo 'Funcionário' deve ser informado")
	private Funcionario funcionario;

	@ManyToOne(optional = false)
	@JoinColumn(name = "cargo_id", nullable = false)
	@NotNull(message = "O campo 'Cargo' deve ser informado")
	private Cargo cargo;

	@Column(nullable = false, name = "dt_inicio")
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull(message = "'Dt Início' deve ser informado")
	private Date dtInicio;

	@Column(nullable = true, name = "dt_fim")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dtFim;

	@Column(name = "salario_piso", nullable = false)
	@NotNull(message = "Campo 'Salário Piso' precisa ser informado")
	@Min(value = 0, message = "Campo 'Salário Piso' precisa ser informado")
	private BigDecimal salarioPiso;

	@Column(name = "salario", nullable = false)
	@NotNull(message = "Campo 'Salário' precisa ser informado")
	@Min(value = 0, message = "Campo 'Salário' precisa ser informado")
	private BigDecimal salario;

	@Column(name = "comissao", nullable = false)
	@NotNull(message = "Campo 'Comissão' precisa ser informado")
	@Min(value = 0, message = "Campo 'Comissão' precisa ser informado")
	private BigDecimal comissao;

	@Column(name = "atual", nullable = false)
	@NotNull(message = "Campo 'Atual' precisa ser informado")
	private Boolean atual = Boolean.TRUE;

	@Transient
	private BigDecimal minimoVendasMes;

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public Date getDtInicio() {
		return dtInicio;
	}

	public void setDtInicio(Date dtInicio) {
		this.dtInicio = dtInicio;
	}

	public Date getDtFim() {
		return dtFim;
	}

	public void setDtFim(Date dtFim) {
		this.dtFim = dtFim;
	}

	public BigDecimal getSalarioPiso() {
		return salarioPiso;
	}

	public void setSalarioPiso(BigDecimal salarioPiso) {
		this.salarioPiso = salarioPiso;
	}

	public BigDecimal getSalario() {
		return salario;
	}

	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}

	public BigDecimal getComissao() {
		return comissao;
	}

	public void setComissao(BigDecimal comissao) {
		this.comissao = comissao;
	}

	public Boolean getAtual() {
		return atual;
	}

	public void setAtual(Boolean atual) {
		this.atual = atual;
	}

	public BigDecimal getMinimoVendasMes() {
		if ((getSalario() != null) && (getSalarioPiso() != null) && (getComissao() != null)
				&& (getComissao().doubleValue() > 0)) {
			try {
				BigDecimal comissao = getComissao().divide(CurrencyUtils.getBigDecimalScaled(100.0, 5));
				BigDecimal dif = getSalario().subtract(getSalarioPiso());
				minimoVendasMes = dif.divide(comissao, RoundingMode.HALF_DOWN);
			} catch (Exception e) {

			}
		}
		return minimoVendasMes;
	}

	public void setMinimoVendasMes(BigDecimal minimoVendasMes) {
		this.minimoVendasMes = minimoVendasMes;
	}

	// -------------------------------------------------------

	@Override
	public int hashCode() {
		final int prime = 337;
		final int result = 181;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).append(funcionario).append(dtInicio).toHashCode();
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
		final FuncionarioCargo iObj = (FuncionarioCargo) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(funcionario, iObj.funcionario)
				.append(dtInicio, iObj.dtInicio)
				.isEquals();
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
