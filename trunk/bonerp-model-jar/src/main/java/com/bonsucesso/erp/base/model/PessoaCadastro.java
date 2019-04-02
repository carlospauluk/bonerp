package com.bonsucesso.erp.base.model;



/**
 * Mantém os possíveis tipos de cadastro onde a Pessoa pode estar (somente para efeito de consulta).
 *
 * @author Carlos Eduardo Pauluk
 */
public enum PessoaCadastro {
	CLIENTE("Cliente"),
	FUNCIONARIO("Funcionário"),
	FORNECEDOR("Fornecedor");

	private String label;

	public String getLabel() {
		return label;
	}

	private PessoaCadastro(String label) {
		this.label = label;
	}

}
