package com.bonsucesso.erp.orcamentos.model;



/**
 * Enum para tipo de orçamento.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public enum TipoOrcamento {

	CORTINAS("Cortinas"),
	CONFECCOES("Confeccoes");

	private String label;

	/**
	 * @param label
	 */
	private TipoOrcamento(String label) {
		setLabel(label);
	}

	public String getLabel() {
		return label;
	}

	private void setLabel(String label) {
		this.label = label;
	}

}
