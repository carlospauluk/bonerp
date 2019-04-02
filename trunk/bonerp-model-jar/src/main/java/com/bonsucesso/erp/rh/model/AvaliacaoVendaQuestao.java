package com.bonsucesso.erp.rh.model;



import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
 *
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "rh_avaliacao_venda_questao", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"avaliacao_venda_id",
		"ordem" }), @UniqueConstraint(columnNames = { "avaliacao_venda_id", "questao" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class AvaliacaoVendaQuestao extends EntityIdImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = 4942894160435356472L;

	@ManyToOne(optional = false)
	@JoinColumn(name = "avaliacao_venda_id", nullable = false)
	@NotNull(message = "O campo 'Avalição Venda' deve ser informado")
	private AvaliacaoVenda avaliacaoVenda;

	@Column(nullable = false, name = "questao", length = 200)
	@Size(min = 2, max = 200, message = "'Questão' deve ser informado (entre 2 e 200 caracteres)")
	@NotNull(message = "'Questão' deve ser informado (entre 2 e 200 caracteres)")
	private String questao;

	@Column(name = "ordem", nullable = true)
	@NotNull(message = "'Ordem' deve ser informado")
	@Min(value = 1, message = "'Ordem' deve ser informado")
	private Integer ordem;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_resposta", nullable = false, length = 50)
	@NotNull(message = "Campo 'Tipo Resposta' precisa ser informado")
	private AvaliacaoTipoResposta tipoResposta;

	// -------------------------------------------------------

	public AvaliacaoVenda getAvaliacaoVenda() {
		return avaliacaoVenda;
	}

	public void setAvaliacaoVenda(AvaliacaoVenda avaliacaoVenda) {
		this.avaliacaoVenda = avaliacaoVenda;
	}

	public String getQuestao() {
		return questao;
	}

	public void setQuestao(String questao) {
		this.questao = questao;
	}

	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

	public AvaliacaoTipoResposta getTipoResposta() {
		return tipoResposta;
	}

	public void setTipoResposta(AvaliacaoTipoResposta tipoResposta) {
		this.tipoResposta = tipoResposta;
	}

	@Override
	public int hashCode() {
		final int prime = 151;
		final int result = 587;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).append(questao).toHashCode();
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
		final AvaliacaoVendaQuestao iObj = (AvaliacaoVendaQuestao) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(questao, iObj.questao)
				.isEquals();
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
