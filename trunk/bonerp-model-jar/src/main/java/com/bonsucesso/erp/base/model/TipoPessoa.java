package com.bonsucesso.erp.base.model;



/**
 * Mantém os dois tipos de Pessoas que o sistema utiliza: Física e Jurídica.
 *
 * @author Carlos Eduardo Pauluk
 */
public enum TipoPessoa {
	PESSOA_FISICA("F", "PESSOA FÍSICA"),
	PESSOA_JURIDICA("J", "PESSOA JURÍDICA");

	private String inicial;

	private String label;

	public String getLabel() {
		return label;
	}

	public String getInicial() {
		return inicial;
	}

	private TipoPessoa(String inicial, String label) {
		this.label = label;
		this.inicial = inicial;
	}

}
