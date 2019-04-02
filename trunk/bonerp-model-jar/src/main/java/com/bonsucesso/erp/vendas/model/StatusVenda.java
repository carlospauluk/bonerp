package com.bonsucesso.erp.vendas.model;



/**
 * Representa os possíveis status em que uma Movimentação se encontra.
 *
 * @author Pauluk
 */
public enum StatusVenda {
	PREVENDA("PV"),
	VENDA("V"),
	FINALIZADA("F"),
	CANCELADA("C");

	private String label;

	private StatusVenda(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
