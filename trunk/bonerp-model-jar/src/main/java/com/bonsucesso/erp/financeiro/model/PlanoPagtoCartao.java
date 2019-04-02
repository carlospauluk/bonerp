package com.bonsucesso.erp.financeiro.model;



/**
 * Enum que contém os tipos de planos de pagamento para cartões de débito ou crédito.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public enum PlanoPagtoCartao {
	DEBITO("Débito"),
	CREDITO_30DD("Crédito 30 dias"),
	CREDITO_PARCELADO("Crédito Parcelado"),
	N_A("N/A");

	private String label;

	private PlanoPagtoCartao(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
