package com.bonsucesso.erp.fiscal.model;



/**
 * Segundo o Manual_Integracao_Contribuinte_4.01-NT2009.006 (3).pdf
 * 
 * 0- Por conta do emitente;
 * 1- Por conta do destinatário/remetente;
 * 2- Por conta de terceiros;
 * 9- Sem frete. (V2.0)
 * 
 * @author Carlos
 *
 */
public enum ModalidadeFrete {

	EMITENTE(0, "Por conta do Emitente"),
	DESTINATARIO(1, "Por conta do Destinatário"),
	TERCEIROS(2, "Por conta de Terceiros"),
	SEM_FRETE(9, "Sem frete");

	private Integer codigo;

	private String label;

	private ModalidadeFrete(int codigo, String label) {
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
