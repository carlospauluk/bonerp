package com.bonsucesso.erp.fiscal.model;



import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

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
 * Entidade MensagemRetornoRF (mensagens de retorno da Receita Federal).
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "fis_msg_retorno_rf",
		uniqueConstraints = { @UniqueConstraint(columnNames = { "codigo", "versao" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class MensagemRetornoRF extends EntityIdImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5337416575005525267L;

	@Column(name = "codigo", nullable = false)
	@NotNull(message = "'Código' deve ser informado")
	private Integer codigo;

	@Column(name = "versao", nullable = false, length = 10)
	@NotNull(message = "'Versão' deve ser informado")
	private String versao;

	@Column(name = "mensagem", nullable = false, length = 2000)
	@NotNull(message = "'Mensagem' deve ser informado")
	private String mensagem;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getVersao() {
		return versao;
	}

	public void setVersao(String versao) {
		this.versao = versao;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	//@Override
	@Override
	public int hashCode() {
		final int prime = 277;
		final int result = 269;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result)
				.append(codigo)
				.append(versao)
				.toHashCode();
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
		final MensagemRetornoRF iObj = (MensagemRetornoRF) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(codigo, iObj.codigo)
				.append(versao, iObj.versao)
				.isEquals();
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
