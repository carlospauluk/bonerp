package com.bonsucesso.erp.rh.model;



import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.CompareToBuilder;
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
 * Classe para a entidade AvaliacaoVenda.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "rh_avaliacao_venda", uniqueConstraints = { @UniqueConstraint(columnNames = { "descricao" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class AvaliacaoVenda extends EntityIdImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = 8229561885672712499L;

	@Column(nullable = false, name = "descricao", length = 200)
	@Size(min = 2, max = 200, message = "'Descrição' deve ser informado (entre 2 e 200 caracteres)")
	@NotNull(message = "'Descrição' deve ser informado (entre 2 e 200 caracteres)")
	private String descricao;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "avaliacaoVenda")
	@Valid
	private List<AvaliacaoVendaQuestao> questoes;

	// -------------------------------------------------------

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<AvaliacaoVendaQuestao> getQuestoes() {
		if (questoes != null) {
			Collections.sort(questoes, new Comparator<AvaliacaoVendaQuestao>() {

				@Override
				public int compare(AvaliacaoVendaQuestao o1, AvaliacaoVendaQuestao o2) {
					return new CompareToBuilder()
							.append(o1.getOrdem(), o2.getOrdem())
							.toComparison();
				}
			});
		}
		return questoes;
	}

	public void setQuestoes(List<AvaliacaoVendaQuestao> questoes) {
		this.questoes = questoes;
	}

	@Override
	public int hashCode() {
		final int prime = 821;
		final int result = 787;
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
		final AvaliacaoVenda iObj = (AvaliacaoVenda) obj;
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
