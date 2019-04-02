package com.bonsucesso.erp.vendas.model;



/**
 * Representa os possíveis status em que uma Movimentação se encontra.
 *
 * @author Pauluk
 */
public enum StatusEncomenda {
	CONFECCAO("EM CONFECÇÃO"),
	PARCIAL("PARCIAL"), // somente algumas (da qtde total) estão prontas
	DISPONIVEL("DISPONÍVEL"), // todas (da qtde total) estão prontas
	ENTREGUE("ENTREGUE"),
	CANCELADA("CANCELADA"),
	ERRO("ERRO"); // erro ao importar do EKT

	private String label;

	private StatusEncomenda(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
