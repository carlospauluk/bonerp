package com.bonsucesso.erp.financeiro.model;



/**
 * Representa os possíveis status em que uma Movimentação se encontra.
 *
 * @author Pauluk
 */
public enum Status {
	ABERTA("A pagar/receber", "A/PR", "waiting"), // É uma conta a pagar/receber
	A_COMPENSAR("A compensar", "A/CO", "waiting"), // Em caso de cheque, quando já foi dado porém ainda não compensou // FIXME: meio inútil isso, não?
	PREVISTA("Prevista", "PREV", "waiting"), // Movimentações com previsão para realização
	REALIZADA("Realizada", "REAL", "checked"); // Movimentação já realizada

	private String label;

	private String sigla;

	private String icone;

	private Status(String label, String sigla, String icone) {
		this.label = label;
		this.sigla = sigla;
		this.icone = icone;
	}

	public String getLabel() {
		return label;
	}

	public String getSigla() {
		return sigla;
	}

	public String getIcone() {
		return icone;
	}

}
