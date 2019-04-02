package com.bonsucesso.erp.base.model;



/**
 * Mantém o tipo Sexo
 *
 * @author Carlos Eduardo Pauluk
 */
public enum Sexo {
	MASCULINO("Masculino"),
	FEMININO("Feminino");

	private String label;

	public String getLabel() {
		return label;
	}

	private Sexo(String label) {
		this.label = label;
	}

}
