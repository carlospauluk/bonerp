package com.bonsucesso.erp.crm.model;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
import com.ocabit.jpa.listener.NotUpperCase;
import com.ocabit.jpa.listener.UpperCaseListener;


/**
 * Entidade Campanha Promocional.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "crm_promo_campanha")
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class CampanhaPromo extends EntityIdImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = -3769025668655610754L;

	@Column(nullable = false, length = 300)
	@NotNull(message = "'Descrição' precisa ser informado")
	@Size(max = 300, message = "'Descrição' deve possuir no máximo 300 caracteres")
	private String descricao;

	@Column(nullable = true, length = 5000, name = "obs")
	@Size(min = 0, max = 5000, message = "Campo 'Obs' deve conter no máximo 5.000 caracteres")
	private String obs;

	@Column(name = "msg_cupom", nullable = true, length = 3000)
	@Size(max = 3000, message = "'Mensagem Cupom' deve possuir no máximo 3000 caracteres")
	@NotUpperCase
	private String msgCupom;

	@Column(nullable = true, name = "dt_inicio")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dtInicio;

	@Column(nullable = true, name = "dt_fim")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dtFim;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "campanhaPromo", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<LoteCupom> lotes;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public String getMsgCupom() {
		return msgCupom;
	}

	public void setMsgCupom(String msgCupom) {
		this.msgCupom = msgCupom;
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

	public List<LoteCupom> getLotes() {
		if (lotes == null) {
			lotes = new ArrayList<LoteCupom>();
		}
		return lotes;
	}

	public void setLotes(List<LoteCupom> lotes) {
		this.lotes = lotes;
	}

	@Override
	public int hashCode() {
		final int prime = 7;
		final int result = 181;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).append(descricao).toHashCode();
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
		final CampanhaPromo iObj = (CampanhaPromo) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(descricao, iObj.descricao)
				.isEquals();
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
