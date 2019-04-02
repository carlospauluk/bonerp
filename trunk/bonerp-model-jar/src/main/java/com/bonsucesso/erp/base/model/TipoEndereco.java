package com.bonsucesso.erp.base.model;



/**
 * Mantém os possíveis tipos de endereço.
 *
 * @author Carlos Eduardo Pauluk
 */
public enum TipoEndereco {
	RESIDENCIAL("Residencial"),
	COMERCIAL("Comercial"),
	OUTROS("Outros");

	private String label;

	public String getLabel() {
		return label;
	}

	private TipoEndereco(String label) {
		this.label = label;
	}

}
