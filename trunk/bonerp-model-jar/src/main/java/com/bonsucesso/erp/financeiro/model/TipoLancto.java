package com.bonsucesso.erp.financeiro.model;



public enum TipoLancto {
	A_PAGAR_RECEBER("A Pagar/Receber"), // Uma conta a pagar ou a receber
	CHEQUE_PROPRIO("Cheque Próprio"), // Um pagamento feito com cheque
	CHEQUE_TERCEIROS("Cheque de Terceiros"), // Um recebimento feito com cheque
	GERAL("Geral"), // Uma movimentação onde é possível manipular todos os campos
	REALIZADA("Realizada"), // Uma movimentação já realizada
	TRANSF_CAIXA("Transferência de Entrada de Caixa"), // Uma transferência entre contas que na verdade é uma transferência/recolhimento de caixa (são feitas 3 movimentações)
	TRANSF_PROPRIA("Transferência entre Carteiras"), // Uma transferência entre carteiras próprias (são feitas 2 movimentações)
	MOVIMENTACAO_AGRUPADA("Movimentação Agrupada"),
	ESTORNO_CORRECAO("Estorno/Correção"), // 2 movimentações em cadeia, para estornar um valor e lançar na categoria correta
	PARCELA("Parcela"), // Utilizado para adicionar uma parcela a um parcelamento já existente
	PAGTO("Pagamento"); // Uma transferência entre carteiras próprias (são feitas 2 movimentações)

	private String label;

	private TipoLancto(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
