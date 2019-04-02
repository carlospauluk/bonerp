package com.bonsucesso.erp.fiscal.model;



/**
 * 
 * @author Carlos Eduardo Pauluk
 *
 */
public enum TipoNotaFiscal {

	NFE(55, "NFE"),
	NFCE(65, "NFCE");

	private Integer modelo;
	
	private String label;

	private TipoNotaFiscal(int modelo, String label) {
		this.modelo = modelo;
		this.label = label;
	}

	public Integer getModelo() {
		return modelo;
	}
	
	public String getLabel() {
		return label;
	}

}
