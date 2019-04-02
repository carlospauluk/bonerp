package com.bonsucesso.erp.rh.model;



import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
 *
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "rh_avaliacao_venda_resp_questao", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"avaliacao_venda_resp_id",
		"avaliacao_venda_questao_id" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class AvaliacaoVendaRespQuestao extends EntityIdImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = 5992407800384643290L;

	@ManyToOne(optional = false)
	@JoinColumn(name = "avaliacao_venda_resp_id", nullable = false)
	@NotNull(message = "O campo 'Avalição Venda Resp.' deve ser informado")
	private AvaliacaoVendaResp avaliacaoVendaResp;

	@ManyToOne(optional = false)
	@JoinColumn(name = "avaliacao_venda_questao_id", nullable = false)
	@NotNull(message = "O campo 'Questão' deve ser informado")
	private AvaliacaoVendaQuestao avaliacaoVendaQuestao;

	@Column(nullable = false, name = "resposta", length = 2000)
	@Size(min = 1, max = 2000, message = "'Resposta' deve ser informado (entre 1 e 2000 caracteres)")
	@NotNull(message = "'Resposta' deve ser informado (entre 1 e 2000 caracteres)")
	private String resposta;

	// -------------------------------------------------------

	public AvaliacaoVendaResp getAvaliacaoVendaResp() {
		return avaliacaoVendaResp;
	}

	public void setAvaliacaoVendaResp(AvaliacaoVendaResp avaliacaoVendaResp) {
		this.avaliacaoVendaResp = avaliacaoVendaResp;
	}

	public AvaliacaoVendaQuestao getAvaliacaoVendaQuestao() {
		return avaliacaoVendaQuestao;
	}

	public void setAvaliacaoVendaQuestao(AvaliacaoVendaQuestao avaliacaoVendaQuestao) {
		this.avaliacaoVendaQuestao = avaliacaoVendaQuestao;
	}

	public String getResposta() {
		return resposta;
	}

	public void setResposta(String resposta) {
		this.resposta = resposta;
	}

	@Override
	public int hashCode() {
		final int prime = 557;
		final int result = 389;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).append(avaliacaoVendaResp).append(avaliacaoVendaQuestao).toHashCode();
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
		final AvaliacaoVendaRespQuestao iObj = (AvaliacaoVendaRespQuestao) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(avaliacaoVendaResp, iObj.avaliacaoVendaResp)
				.append(avaliacaoVendaQuestao, iObj.avaliacaoVendaQuestao)
				.isEquals();
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
