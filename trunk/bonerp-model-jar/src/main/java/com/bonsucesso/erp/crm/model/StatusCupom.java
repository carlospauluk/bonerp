package com.bonsucesso.erp.crm.model;



public enum StatusCupom {

	NAO_VINCULADO("Não Vinculado"),
	VINCULADO("Vinculado"),
	CANCELADO("Cancelado"),
	UTILIZADO("Utilizado");

	private String label;

	/**
	 * @param label
	 */
	private StatusCupom(String label) {
		setLabel(label);
	}

	public String getLabel() {
		return label;
	}

	private void setLabel(String label) {
		this.label = label;
	}

}
