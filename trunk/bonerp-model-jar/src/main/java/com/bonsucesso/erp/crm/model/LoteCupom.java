package com.bonsucesso.erp.crm.model;



import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
 * Entidade Lote de Cupons.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "crm_promo_lote_cupom", uniqueConstraints = { @UniqueConstraint(columnNames = { "num_lote" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class LoteCupom extends EntityIdImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = -3769025668655610754L;

	@ManyToOne(optional = false)
	@JoinColumn(name = "campanha_promo_id", nullable = false)
	private CampanhaPromo campanhaPromo;

	@Column(name = "num_lote", nullable = false, unique = true)
	@NotNull(message = "'Lote' precisa ser informado")
	private String numLote;

	@Column(nullable = false, name = "emitido_por", length = 80)
	@Size(min = 2, max = 80, message = "O campo 'Preenchido por' deve ser informado (entre 2 e 80 caracteres)")
	@NotNull(message = "O campo 'Preenchido por' deve ser informado (entre 2 e 80 caracteres)")
	private String emitidoPor;

	/**
	 * Data em que o cupom foi impresso.
	 */
	@Column(nullable = true, name = "dt_emissao")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dtEmissao;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "loteCupom", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Cupom> cupons;

	public CampanhaPromo getCampanhaPromo() {
		return campanhaPromo;
	}

	public void setCampanhaPromo(CampanhaPromo campanhaPromo) {
		this.campanhaPromo = campanhaPromo;
	}

	public String getNumLote() {
		return numLote;
	}

	public void setNumLote(String lote) {
		numLote = lote;
	}

	public String getEmitidoPor() {
		return emitidoPor;
	}

	public void setEmitidoPor(String emitidoPor) {
		this.emitidoPor = emitidoPor;
	}

	public Date getDtEmissao() {
		return dtEmissao;
	}

	public void setDtEmissao(Date dtEmissao) {
		this.dtEmissao = dtEmissao;
	}

	public List<Cupom> getCupons() {
		return cupons;
	}

	public void setCupons(List<Cupom> cupons) {
		this.cupons = cupons;
	}

	@Override
	public int hashCode() {
		final int prime = 337;
		final int result = 617;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).append(numLote).toHashCode();
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
		final LoteCupom iObj = (LoteCupom) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(numLote, iObj.numLote)
				.isEquals();
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
