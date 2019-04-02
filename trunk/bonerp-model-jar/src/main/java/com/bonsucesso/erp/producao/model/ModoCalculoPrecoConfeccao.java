package com.bonsucesso.erp.producao.model;



/**
 * Enum para os modos de cálculos de preços de confecção.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public enum ModoCalculoPrecoConfeccao {
	// ConfeccaoBusiness.calcularModo1()
	// 06-04-02 ; 10-08 ; 16-14-12 ; M-P-G ; XG ; SG ; SS
	MODO_1("06-04-02 ; 10-08 ; 16-14-12 ; M-P-G ; XG ; SG ; SS"),

	// ConfeccaoBusiness.calcularModo2()
	// 
	MODO_2("08-06-04-02 ; 16-14-12-10 ; M-P-G ; XG ; SG ; SS"),

	// ConfeccaoBusiness.calcularModo3()
	// 08-06-04-02 ; 16-14-12-10 ; G-M-P ; SS-SG-XG
	MODO_3("08-06-04-02 ; 16-14-12-10 ; G-M-P ; SS-SG-XG");

	private String label;

	private ModoCalculoPrecoConfeccao(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	@SuppressWarnings("unused")
	private void setLabel(String label) {
		this.label = label;
	}

}
