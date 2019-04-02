package com.bonsucesso.erp.rh.model;



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
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.ocabit.base.model.EntityIdImpl;
import com.ocabit.jpa.listener.EntityIdListener;
import com.ocabit.jpa.listener.UpperCaseListener;


/**
 * Classe para a entidade AvaliacaoVenda.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "rh_avaliacao_venda_resp"
// , uniqueConstraints = { @UniqueConstraint(columnNames = { "venda_id" }) }
)
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class AvaliacaoVendaResp extends EntityIdImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = 3028958406587812785L;

	@ManyToOne(optional = false)
	@JoinColumn(name = "avaliacao_venda_id", nullable = false)
	@NotNull(message = "O campo 'Avalição Venda' deve ser informado")
	private AvaliacaoVenda avaliacaoVenda;

	@Column(name = "dt_avaliacao", nullable = false)
	@NotNull(message = "O campo 'Dt Avaliação' deve ser informado")
	private Date dtAvaliacao;

	/**
	 * Uma venda só pode ter uma avaliação.
	 */
	// @OneToOne(optional = true)
	// @JoinColumn(name = "venda_id", nullable = true)
	// @NotNull(message = "O campo 'Venda' deve ser informado")   >>> tirei o not_null por causa das importações de vendas do EKT
	// private Venda venda;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "avaliacaoVendaResp")
	@Valid
	private List<AvaliacaoVendaRespQuestao> respostas;

	// -------------------------------------------------------

	public AvaliacaoVenda getAvaliacaoVenda() {
		return avaliacaoVenda;
	}

	public void setAvaliacaoVenda(AvaliacaoVenda avaliacaoVenda) {
		this.avaliacaoVenda = avaliacaoVenda;
	}

	public Date getDtAvaliacao() {
		return dtAvaliacao;
	}

	public void setDtAvaliacao(Date dtAvaliacao) {
		this.dtAvaliacao = dtAvaliacao;
	}

	//	public Venda getVenda() {
	//		return venda;
	//	}
	//
	//	public void setVenda(Venda venda) {
	//		this.venda = venda;
	//	}

	public List<AvaliacaoVendaRespQuestao> getRespostas() {
		return respostas;
	}

	public void setRespostas(List<AvaliacaoVendaRespQuestao> questoes) {
		respostas = questoes;
	}

	//	@Override
	//	public int hashCode() {
	//		final int prime = 577;
	//		final int result = 647;
	//		// Utiliza a API do apache-commons
	//		return new HashCodeBuilder(prime, result).append(venda).toHashCode();
	//	}
	//
	//	@Override
	//	public boolean equals(final Object obj) {
	//		if (obj == null) {
	//			return false;
	//		}
	//		if (obj == this) {
	//			return true;
	//		}
	//		if (obj.getClass() != getClass()) {
	//			return false;
	//		}
	//		final AvaliacaoVendaResp iObj = (AvaliacaoVendaResp) obj;
	//		// Utiliza a API do apache-commons
	//		return new EqualsBuilder()
	//				.append(venda, iObj.venda)
	//				.isEquals();
	//	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
