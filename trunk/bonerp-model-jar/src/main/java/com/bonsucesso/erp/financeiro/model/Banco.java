package com.bonsucesso.erp.financeiro.model;



import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.CompareToBuilder;
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
import com.ocabit.utils.strings.StringUtils;


/**
 * Entidade Carteira.
 *
 * @author Pauluk
 *
 */
@Entity
@Table(name = "fin_banco", uniqueConstraints = { @UniqueConstraint(columnNames = { "codigo_banco" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@DynamicUpdate(true)
@DynamicInsert(true)
public class Banco extends EntityIdImpl implements Comparable<Banco> {

	/**
	 *
	 */
	private static final long serialVersionUID = 8642965997350586300L;

	@Column(name = "codigo_banco", nullable = false)
	@Min(value = 1)
	@NotNull(message = "Campo 'Código' precisa ser informado")
	private Integer codigoBanco;

	@Column(name = "nome", nullable = false, length = 200)
	@NotEmpty(message = "Campo 'Nome' precisa ser informado")
	private String nome;

	/*
	 * Campo para informar se este banco é utilizado no sistema (devido ao grande número de bancos na lista; para não carregar bancos inúteis nos autocompletes).
	 */
	@Column(name = "utilizado", nullable = false)
	@NotNull(message = "Campo 'Utilizado' precisa ser informado")
	private Boolean utilizado = false;

	@Transient
	private String descricaoMontada;

	public Integer getCodigoBanco() {
		return codigoBanco;
	}

	public void setCodigoBanco(Integer codigoBanco) {
		this.codigoBanco = codigoBanco;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Boolean getUtilizado() {
		return utilizado;
	}

	public void setUtilizado(Boolean utilizado) {
		this.utilizado = utilizado;
	}

	@Override
	public int hashCode() {
		// Utiliza números primos
		final int prime = 37;
		final int result = 227;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).append(nome).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// Verificação padrão
		if (obj == null) {
			return false;
		}
		// Verificação padrão
		if (obj == this) {
			return true;
		}
		// Verificação padrão
		if (obj.getClass() != getClass()) {
			return false;
		}
		final Banco iObj = (Banco) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder().append(nome, iObj.nome).isEquals();
	}

	@Override
	public int compareTo(Banco o) {
		return new CompareToBuilder().append(codigoBanco, o.codigoBanco)
				.toComparison();
	}

	public String getDescricaoMontada() {
		if ((getCodigoBanco() != null) && (getNome() != null)) {
			descricaoMontada = StringUtils.zerofill(getCodigoBanco().toString(), 3)
					+ " - " + getNome();
		}
		return descricaoMontada;
	}

	public void setDescricaoMontada(String descricaoMontada) {
		this.descricaoMontada = descricaoMontada;
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
