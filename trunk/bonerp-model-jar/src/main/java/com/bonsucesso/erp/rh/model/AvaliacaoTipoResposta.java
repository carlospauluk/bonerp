package com.bonsucesso.erp.rh.model;



/**
 * Tipos de Resposta para questões de avaliações.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public enum AvaliacaoTipoResposta {
	TEXTO("TEXTO", "Texto"),
	NOTA_0_10("NOTA_0_10", "Nota de 0 a 10"),
	SIM_NAO_NAOSEI("SIM_NAO_NAOSEI", "Sim / Não / Não sei"),
	SIM_NAO_MAISOUMENOS("SIM_NAO_MAISOUMENOS", "Sim / Não / Mais ou Menos");

	private String sigla;

	private String label;

	private AvaliacaoTipoResposta(String sigla, String label) {
		this.sigla = sigla;
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public String getSigla() {
		return sigla;
	}

}
