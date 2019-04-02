package com.bonsucesso.erp.fiscal.model;



/**
 * Segundo o Manual_de_Orientacao_Contribuinte_v_6.00
 * 
 * 1=NF-e normal;
 * 2=NF-e complementar;
 * 3=NF-e de ajuste;
 * 4=Devolução de mercadoria.
 * 
 * @author Carlos Eduardo Pauluk
 *
 */
public enum FinalidadeNF {

	NORMAL(1, "NF-e normal"),
	COMPLEMENTAR(2, "NF-e complementar"),
	AJUSTE(3, "NF-e de ajuste"),
	DEVOLUCAO(4, "Devolução de mercadoria");

	private Integer codigo;

	private String label;

	private FinalidadeNF(int codigo, String label) {
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
