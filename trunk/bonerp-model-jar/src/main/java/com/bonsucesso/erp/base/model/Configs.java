package com.bonsucesso.erp.base.model;



/**
 * Chaves de Config para o módulo cortinas.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public enum Configs {

	VEN_CUSTO_FINANCEIRO("bonsucesso.vendas.custoFinanceiro"),
	VEN_CUSTO_OPERACIONAL("bonsucesso.vendas.custoOperacional"),
	VEN_DESCONTO_A_VISTA("bonsucesso.vendas.descontoAVista"),
	CRTN_ALTURA_BARRA_PADRAO("bonsucesso.cortinas.alturaBarraPadrao"),
	CRTN_ALTURA_MAX_HORIZONAL("bonsucesso.cortinas.alturaMaxHorizontal"),
	CRTN_PRAZO_VALIDADE_ORCAMENTO("bonsucesso.cortinas.prazoValidadeOrcamentos"),
	RH_PORCENT_META_MINIMA("bonsucesso.rh.porcentMetaMinima");

	private String chave;

	private Configs(String chave) {
		setChave(chave);
	}

	public String getChave() {
		return chave;
	}

	private void setChave(String chave) {
		this.chave = chave;
	}

}
