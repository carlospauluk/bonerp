package com.bonsucesso.erp.cortinas.model;



public enum OrientacaoTecido {

	HORIZONTAL("Horizontal"),
	VERTICAL("Vertical");

	//INDEPENDE_CEDE("Orientação Independe (porém tecido cede)"),
	//INDEPENDE_NAOCEDE("Orientação Independe");

	private String descricao;

	private OrientacaoTecido(String descricao) {
		setDescricao(descricao);
	}

	public String getDescricao() {
		return descricao;
	}

	private void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
