package com.bonsucesso.erp.orcamentos.model;



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
 * Entidade que guarda arquivos de orçamentos.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "orc_orcamento_arquivo",
		uniqueConstraints = { @UniqueConstraint(columnNames = { "orcamento_id", "nome_arquivo" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class OrcamentoArquivo extends EntityIdImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = -7311286225741359742L;

	@Column(nullable = false, length = 300, name = "nome_arquivo")
	@Size(min = 2, max = 100, message = "'Arquivo' deve ser informado")
	private String nomeArquivo;

	@Column(nullable = false, length = 300)
	@Size(min = 2, max = 100, message = "'Descrição' deve ser informado")
	@NotNull(message = "'Descrição' deve ser informado")
	private String descricao;

	@ManyToOne(optional = false)
	@JoinColumn(name = "orcamento_id", nullable = false)
	@NotNull(message = "O campo 'Orçamento' deve ser preenchido")
	private Orcamento orcamento;

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Orcamento getOrcamento() {
		return orcamento;
	}

	public void setOrcamento(Orcamento orcamento) {
		this.orcamento = orcamento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		final int result = 503;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).append(orcamento).append(nomeArquivo).toHashCode();
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
		final OrcamentoArquivo iObj = (OrcamentoArquivo) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(orcamento, iObj.orcamento)
				.append(nomeArquivo, iObj.nomeArquivo)
				.isEquals();
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
