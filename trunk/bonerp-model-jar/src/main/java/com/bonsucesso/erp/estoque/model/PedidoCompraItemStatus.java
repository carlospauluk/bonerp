package com.bonsucesso.erp.estoque.model;



/**
 * Representa os possíveis status em que se encontra um item de Pedido de Compra.
 *
 * @author Pauluk
 */
public enum PedidoCompraItemStatus {
	AGUARDANDO_ENTREGA(
			"Aguardando entrega", "waiting"), // É uma conta a pagar/receber
	ENTREGUE(
			"Entregue", "checked"), // Em caso de cheque, quando já foi dado porém ainda não compensou // FIXME: meio inútil isso, não?
	CANCELADO(
			"Cancelado", "cancel"); // Movimentação já realizada

	private String label;

	private String icone;

	private PedidoCompraItemStatus(String label, String icone) {
		this.label = label;
		this.icone = icone;
	}

	public String getLabel() {
		return label;
	}

	public String getIcone() {
		return icone;
	}

}
