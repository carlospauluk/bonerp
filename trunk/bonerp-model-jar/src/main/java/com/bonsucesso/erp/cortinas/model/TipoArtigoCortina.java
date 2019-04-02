package com.bonsucesso.erp.cortinas.model;



public enum TipoArtigoCortina {

	INDEFINIDO("Indefinido"),
	TECIDO("Tecido"),
	FRANJA("Franja"),
	// DRAPEADO("Drapeado"), >> não precisa ser um TipoArtigoCortina. É marcado como Drapeado direto no CortinaItem (que seja um TECIDO).
	RODIZIO("Rodízio"),
	TRILHO("Trilho"),
	TERMINAL("Terminal"),
	ENTRETELA("Entretela"),
	FRANZIDOR("Franzidor"),
	ILHOS("Ilhós"),
	MAO_DE_OBRA_ILHOS("Mão-de-obra Ilhós"),
	ARGOLA("Argola"),
	GANCHO("Gancho"),
	VARAO("Varão"),
	SUPORTE_SIMPLES("Suporte Simples para Varão"),
	SUPORTE_DUPLO("Suporte Duplo para Varão"),
	SUPORTE_TRIPLO("Suporte Triplo para Varão"),
	PONTEIRA("Ponteira"),
	ACESSORIO("Acessório"),
	MAO_DE_OBRA_COSTUREIRA_TECIDO("Mão-de-obra Costureira (Tecido)"),
	MAO_DE_OBRA_COSTUREIRA_DETALHES("Mão-de-obra Costureira (Detalhes)"), // para drapeado, bandô, rendinha (calcula-se pela largura da cortina)
	MAO_DE_OBRA_INSTALACAO("Mão-de-obra Instalação"),
	NULLED(""); // utilizado para os casos em que NULLABLE = true (e também para o cortinaItem.tipoFixacao, quando não tem fixação. Ex.: cortina direto no varão.)

	private String label;

	/**
	 * @param label
	 */
	private TipoArtigoCortina(String label) {
		setLabel(label);
	}

	public String getLabel() {
		return label;
	}

	private void setLabel(String label) {
		this.label = label;
	}

}
