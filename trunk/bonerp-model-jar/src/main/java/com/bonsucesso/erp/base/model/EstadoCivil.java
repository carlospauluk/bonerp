package com.bonsucesso.erp.base.model;



/**
 * Mantém os tipos de Estado Civil
 *
 * @author Carlos Eduardo Pauluk
 */
public enum EstadoCivil {
	SOLTEIRO("Solteiro"),
	CASADO("Casado"),
	VIUVO("Viúvo"),
	DIVORCIADO("Divorciado"),
	SEPARADO("Separado"),
	UNIAO_ESTAVEL("União Estável");

	private String label;

	public String getLabel() {
		return label;
	}

	private EstadoCivil(String label) {
		this.label = label;
	}

}
