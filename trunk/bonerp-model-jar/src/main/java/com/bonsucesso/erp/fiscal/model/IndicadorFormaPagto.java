package com.bonsucesso.erp.fiscal.model;



/**
 * Segundo o Manual_Integracao_Contribuinte_4.01-NT2009.006 (3).pdf
 * 
 * @author Carlos
 *
 */
public enum IndicadorFormaPagto {

	VISTA(0, "A Vista"),
	PRAZO(1, "A Prazo"),
	OUTROS(2, "Outros");

	private Integer codigo;

	private String label;

	private IndicadorFormaPagto(int codigo, String label) {
		this.codigo = codigo;
		this.label = label;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getLabel() {
		return label;
	}

}
