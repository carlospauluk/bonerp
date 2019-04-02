package com.bonsucesso.erp.financeiro.model;



import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
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


/**
 * Entidade Modo de Movimentação. Informa se a movimentação foi em 'espécie',
 * 'cheque', 'boleto', etc.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "fin_modo", uniqueConstraints = { @UniqueConstraint(columnNames = { "descricao" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class Modo extends EntityIdImpl implements Comparable<Modo> {

	/**
	 *
	 */
	private static final long serialVersionUID = -5945902257106111886L;

	@Column(name = "codigo", nullable = true)
	@NotNull(message = "Campo 'Código' precisa ser informado")
	private Integer codigo;

	/**
	 * 'EM ESPÉCIE', 'CHEQUE', 'BOLETO', etc.
	 */
	@Column(name = "descricao", nullable = false, length = 40)
	@NotEmpty(message = "Campo 'Descrição' precisa ser informado")
	@NotNull(message = "Campo 'Descrição' precisa ser informado")
	private String descricao;

	/**
	 * Informa se este modo é aceito para transferências próprias (entre
	 * carteiras).
	 */
	@Column(name = "transf_propria", nullable = false)
	@NotNull(message = "Campo 'Transf. Própria' precisa ser informado")
	private Boolean modoDeTransfPropria;

	/**
	 * Informa se este modo é aceito para transferências próprias (entre
	 * carteiras).
	 */
	@Column(name = "moviment_agrup", nullable = false)
	@NotNull(message = "Campo 'Movimentação Agrupada' precisa ser informado")
	private Boolean modoDeMovimentAgrup;

	/**
	 * Informa se este modo é aceito para transferências próprias (entre
	 * carteiras).
	 */
	@Column(name = "modo_cartao", nullable = false)
	@NotNull(message = "Campo 'Modo Cartão' precisa ser informado")
	private Boolean modoDeCartao;

	/**
	 * Informa se este modo é aceito para transferências próprias (entre
	 * carteiras).
	 */
	@Column(name = "modo_cheque", nullable = false)
	@NotNull(message = "Campo 'Modo Cheque' precisa ser informado")
	private Boolean modoDeCheque;

	/**
	 * Informa se este modo é aceito para transferência/recolhimento de caixas.
	 */
	@Column(name = "transf_caixa", nullable = false)
	@NotNull(message = "Campo 'Transf. Caixa' precisa ser informado")
	private Boolean modoDeTransfCaixa;

	@Column(name = "com_banco_origem", nullable = false)
	@NotNull(message = "Campo 'Com Banco Origem' precisa ser informado")
	private Boolean modoComBancoOrigem;

	/**
	 * Controlado pelo JPA.
	 */

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Boolean getModoDeTransfPropria() {
		return modoDeTransfPropria;
	}

	public void setModoDeTransfPropria(Boolean modoDeTransfPropria) {
		this.modoDeTransfPropria = modoDeTransfPropria;
	}

	public Boolean getModoDeMovimentAgrup() {
		return modoDeMovimentAgrup;
	}

	public void setModoDeMovimentAgrup(Boolean modoDeMovimentAgrup) {
		this.modoDeMovimentAgrup = modoDeMovimentAgrup;
	}

	public Boolean getModoDeCartao() {
		return modoDeCartao;
	}

	public void setModoDeCartao(Boolean modoDeCartao) {
		this.modoDeCartao = modoDeCartao;
	}

	public Boolean getModoDeCheque() {
		return modoDeCheque;
	}

	public void setModoDeCheque(Boolean modoDeCheque) {
		this.modoDeCheque = modoDeCheque;
	}

	public Boolean getModoDeTransfCaixa() {
		return modoDeTransfCaixa;
	}

	public void setModoDeTransfCaixa(Boolean modoDeTransfCaixa) {
		this.modoDeTransfCaixa = modoDeTransfCaixa;
	}

	public Boolean getModoComBancoOrigem() {
		return modoComBancoOrigem;
	}

	public void setModoComBancoOrigem(Boolean modoComBancoOrigem) {
		this.modoComBancoOrigem = modoComBancoOrigem;
	}

	@Override
	public int hashCode() {
		// Utiliza números primos
		final int prime = 37;
		final int result = 227;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).append(descricao)
				.toHashCode();
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
		final Modo iObj = (Modo) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder().append(descricao, iObj.descricao).isEquals();
	}

	@Override
	public int compareTo(Modo o) {
		return new CompareToBuilder().append(codigo, o.codigo)
				.toComparison();
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
