package com.bonsucesso.erp.financeiro.model;



import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;


/**
 * Entidade embedded em Movimentacao.
 *
 * @author Carlos Eduardo Pauluk.
 */
@Embeddable
public class Cheque implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Código do Banco (conforme códigos oficiais).
	 */
	@ManyToOne(optional = true)
	@JoinColumn(name = "cheque_banco_id", nullable = true)
	private Banco banco;

	/**
	 * Código da agência (sem o dígito verificador).
	 */
	@Column(name = "cheque_agencia", nullable = true, length = 30)
	private String agencia;

	/**
	 * Número da conta no banco (não segue um padrão).
	 */
	@Column(name = "cheque_conta", nullable = true, length = 30)
	private String conta;

	/**
	 * Número do cheque (geralmente um número com 6 dígitos).
	 */
	@Column(name = "cheque_num_cheque", nullable = true, length = 20)
	@Size(max = 20, message = "Núm. Cheque deve conter no máximo 20 caracteres")
	private String numCheque;

	/**
	 * Alinea atual do cheque (para cheques devolvidos).
	 */
	@Column(name = "alinea", nullable = true)
	private Integer alinea;

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public String getNumCheque() {
		return numCheque;
	}

	public void setNumCheque(String numCheque) {
		this.numCheque = numCheque;
	}

	public Integer getAlinea() {
		return alinea;
	}

	public void setAlinea(Integer alinea) {
		this.alinea = alinea;
	}

}
