package com.bonsucesso.erp.orcamentos.view;



import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.orcamentos.model.StatusOrcamento;
import com.ocabit.base.view.AbstractListMaker;


/**
 * ListMaker para as entidades do pacote com.bonsucesso.erp.estoque.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("lmOrcamentos")
@Scope("view")
public class ListMaker implements AbstractListMaker {

	/**
	 *
	 */
	private static final long serialVersionUID = -5056648932201283099L;

	protected static Logger logger = Logger.getLogger(ListMaker.class);

	private List<StatusOrcamento> statusOrcamento;

	public List<StatusOrcamento> getStatusOrcamento() {
		statusOrcamento = Arrays.asList(StatusOrcamento.values());
		return statusOrcamento;
	}

	public void setStatusOrcamento(List<StatusOrcamento> statusOrcamento) {
		this.statusOrcamento = statusOrcamento;
	}

}
