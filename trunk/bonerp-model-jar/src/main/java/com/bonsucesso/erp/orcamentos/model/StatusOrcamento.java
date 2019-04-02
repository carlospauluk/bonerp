package com.bonsucesso.erp.orcamentos.model;



/**
 * Enum para status de orçamento.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public enum StatusOrcamento {

	NOVO("Novo"),
	AGUARDANDO_APROVACAO("Aguardando aprovação do cliente"),
	PRODUCAO("Produção"),
	CANCELADO("Cancelado"),
	FINALIZADO("Finalizado");

	private String label;

	/**
	 * @param label
	 */
	private StatusOrcamento(String label) {
		setLabel(label);
	}

	public String getLabel() {
		return label;
	}

	private void setLabel(String label) {
		this.label = label;
	}

}
