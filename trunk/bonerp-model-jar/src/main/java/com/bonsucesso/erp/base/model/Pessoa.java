package com.bonsucesso.erp.base.model;



import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;
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
 * Entidade abstrata Pessoa.
 *
 * @author Carlos Eduardo Pauluk
 */
@Entity
@Table(name = "bon_pessoa")
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class Pessoa extends EntityIdImpl implements Comparable<Pessoa> {

	/**
	 *
	 */
	private static final long serialVersionUID = -7384219388700348577L;

	@Enumerated(EnumType.STRING)
	@Column(nullable = true, length = 15, name = "tipo_pessoa")
	private TipoPessoa tipoPessoa = TipoPessoa.PESSOA_JURIDICA;

	/**
	 * CPF ou CNPJ, somente números (não usar pontuação).
	 */
	@Column(nullable = true, length = 50, name = "documento")
	private String documento;

	/**
	 * Para Pessoa Jurídica é a Razão Social.
	 */
	@Column(nullable = false, length = 300, name = "nome")
	@NotEmpty(message = "'Razão Social/Nome' deve possuir entre 2 e 300 caracteres")
	@Size(min = 2, max = 300, message = "'Razão Social/Nome' deve possuir entre 2 e 300 caracteres")
	private String nome;

	/**
	 * Somente para pessoa jurídica.
	 */
	@Column(nullable = true, length = 300, name = "nome_fantasia")
	@Size(min = 0, max = 300, message = "'Razão Social/Nome' deve possuir entre 2 e 300 caracteres")
	private String nomeFantasia;

	@Transient
	private String nomeExibicao;
	
	public Pessoa() {
		
	}
	
	public Pessoa(TipoPessoa tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public TipoPessoa getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(TipoPessoa tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(final String documento) {
		this.documento = documento != null ? documento.replaceAll("[^\\d.]", "") : null;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(final String nome) {
		this.nome = nome;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getNomeExibicao() {
		nomeExibicao = "";
		if (getTipoPessoa() != null && getTipoPessoa().equals(TipoPessoa.PESSOA_JURIDICA)) {
			if ((getNome() != null) && !"".equals(getNome())) {
				nomeExibicao = getNome();
				if ((getNomeFantasia() != null) && !"".equals(getNomeFantasia())) {
					nomeExibicao += " (" + getNomeFantasia() + ")";
				}
			}
		} else {
			if ((getNome() != null) && !"".equals(getNome())) {
				nomeExibicao = getNome();
			}
		}

		return nomeExibicao;
	}

	public void setNomeExibicao(String nomeExibicao) {
		this.nomeExibicao = nomeExibicao;
	}

	@Override
	public int hashCode() {
		// Usar somente números primos.
		final int prime = 293;
		final int result = 157;
		// usa API do apache-commons
		return new HashCodeBuilder(prime, result)
				.append(documento)
				.append(nome)
				.append(nomeFantasia)
				.toHashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		// verificação padrão
		if (obj == null) {
			return false;
		}
		// verificação padrão
		if (obj == this) {
			return true;
		}
		// verificação padrão
		if (obj.getClass() != getClass()) {
			return false;
		}
		final Pessoa iObj = (Pessoa) obj;
		// usa API do apache-commons
		return new EqualsBuilder()
				.append(documento, iObj.documento)
				.append(nome, iObj.nome)
				.append(nomeFantasia, iObj.nomeFantasia)
				.isEquals();
	}

	@Override
	public String toString() {
		// somente para fins de debug
		return "Pessoa [documento=" + documento + ", nome=" + nome + "]";
	}

	@Override
	public int compareTo(final Pessoa o) {
		return equals(o) ? 0 : -1;
	}

	@Override
	public String toStringToView() {
		return getDocumento() + " - " + getNomeExibicao();
	}

}
