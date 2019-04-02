package com.bonsucesso.erp.crm.model;



import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

import com.ocabit.base.model.EntityIdImpl;
import com.ocabit.jpa.listener.EntityIdListener;
import com.ocabit.jpa.listener.UpperCaseListener;


/**
 * Entidade Cupom.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "crm_promo_cupom", uniqueConstraints = { @UniqueConstraint(columnNames = { "codigo" }),
		@UniqueConstraint(columnNames = { "lote_cupom_id", "ordem" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class Cupom extends EntityIdImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = -3769025668655610754L;

	@ManyToOne(optional = false)
	@JoinColumn(name = "lote_cupom_id", nullable = false)
	private LoteCupom loteCupom;

	@Column(nullable = false)
	@NotNull(message = "'Ordem' precisa ser informado")
	@Min(value = 1, message = "'Ordem' precisa ser informado como um número positivo")
	private Integer ordem;

	@Column(nullable = false, unique = true)
	@NotNull(message = "'Código' precisa ser informado")
	@Min(value = 1, message = "'Código' precisa ser informado como um número positivo")
	private String codigo;

	@ManyToOne(optional = true)
	@JoinColumn(name = "cliente_id", nullable = true)
	private Cliente cliente;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 100, name = "status")
	private StatusCupom status;

	@Column(nullable = true, length = 5000, name = "obs")
	@Size(min=0,max=5000,message="Campo 'Obs' deve conter no máximo 5.000 caracteres")
	private String obs;

	@Column(nullable = true, name = "dt_vinculacao")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dtVinculacao;

	@Column(nullable = true, name = "dt_utilizacao")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dtUtilizacao;

	@Column(nullable = true, name = "dt_cancelamento")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dtCancelamento;

	public LoteCupom getLoteCupom() {
		return loteCupom;
	}

	public void setLoteCupom(LoteCupom loteCupom) {
		this.loteCupom = loteCupom;
	}

	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public StatusCupom getStatus() {
		return status;
	}

	public void setStatus(StatusCupom status) {
		this.status = status;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public Date getDtVinculacao() {
		return dtVinculacao;
	}

	public void setDtVinculacao(Date dtVinculacao) {
		this.dtVinculacao = dtVinculacao;
	}

	public Date getDtUtilizacao() {
		return dtUtilizacao;
	}

	public void setDtUtilizacao(Date dtUtilizacao) {
		this.dtUtilizacao = dtUtilizacao;
	}

	public Date getDtCancelamento() {
		return dtCancelamento;
	}

	public void setDtCancelamento(Date dtCancelamento) {
		this.dtCancelamento = dtCancelamento;
	}

	@Override
	public int hashCode() {
		final int prime = 11;
		final int result = 181;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).append(codigo).toHashCode();
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
		final Cupom iObj = (Cupom) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(codigo, iObj.codigo)
				.isEquals();
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
