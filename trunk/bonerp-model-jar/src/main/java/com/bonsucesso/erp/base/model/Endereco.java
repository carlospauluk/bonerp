package com.bonsucesso.erp.base.model;



import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.ocabit.base.model.EntityIdImpl;
import com.ocabit.jpa.listener.EntityIdListener;
import com.ocabit.jpa.listener.UpperCaseListener;


@Entity
@Table(name = "bon_endereco")
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class Endereco extends EntityIdImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = -1216613868354117632L;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 100)
	@NotNull(message = "'Tipo Endereço' deve ser informado")
	@Valid
	private TipoEndereco tipoEndereco = TipoEndereco.OUTROS;

	@Column(nullable = true, length = 9)
	private String cep;

	@Column(nullable = true, length = 120)
	private String logradouro;

	@Column(nullable = true, length = 10)
	private Integer numero;

	@Column(nullable = true, length = 120)
	private String complemento;

	@Column(nullable = true, length = 120)
	private String bairro;

	@Column(nullable = true, length = 80)
	private String cidade;

	@Column(nullable = true, length = 2)
	private String estado;

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public TipoEndereco getTipoEndereco() {
		return tipoEndereco;
	}

	public void setTipoEndereco(TipoEndereco tipoEndereco) {
		this.tipoEndereco = tipoEndereco;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public int hashCode() {
		final int prime = 709;
		final int result = 359;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).append(cep).append(logradouro).append(numero).append(complemento)
				.append(bairro).append(cidade).append(estado).toHashCode();
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
		final Endereco iObj = (Endereco) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder().append(cep, iObj.cep).append(logradouro, iObj.logradouro).append(numero, iObj.numero)
				.append(complemento, iObj.complemento).append(bairro, iObj.bairro).append(cidade, iObj.cidade)
				.append(estado, iObj.estado)
				.isEquals();
	}

	public String getLogradouroMontado() {
		StringBuffer sb = new StringBuffer("");
		if (!StringUtils.isBlank(getLogradouro())) {
			sb.append(getLogradouro());
			if ((getNumero() != null) && !StringUtils.isBlank(getNumero().toString())) {
				sb.append(", ").append(getNumero());
			}
			if (!StringUtils.isBlank(getComplemento())) {
				sb.append(" - ").append(getComplemento());
			}
		}
		return sb.toString();
	}

	public String getEnderecoMontado(boolean emLinhas) {
		StringBuffer sb = new StringBuffer("");
		if (!StringUtils.isBlank(getLogradouroMontado())) {
			sb.append(getLogradouroMontado());
		}
		if (!StringUtils.isBlank(getBairro())) {
			sb.append(emLinhas ? "\r\n" : " | ");
			sb.append(getBairro());
		}
		if (!StringUtils.isBlank(getCep())) {
			sb.append(" - CEP: ").append(getCep());
		}
		if (!StringUtils.isBlank(getCidade())) {
			sb.append(emLinhas ? "\r\n" : " | ");
			sb.append(getCidade());
			if (!StringUtils.isBlank(getEstado())) {
				sb.append(" - ").append(getEstado());
			}
		}
		return sb.toString();
	}

	@Override
	public String toString() {
		return getEnderecoMontado(false);
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
