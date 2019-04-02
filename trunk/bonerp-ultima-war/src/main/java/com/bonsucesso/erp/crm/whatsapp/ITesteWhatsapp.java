package com.bonsucesso.erp.crm.whatsapp;

import com.bonsucesso.erp.crm.model.Cliente;
import com.bonsucesso.erp.crm.model.Cupom;
import com.ocabit.base.view.exception.ViewException;

public interface ITesteWhatsapp {

	void doIt() throws InterruptedException;

	boolean verificaSeTemWhatsapp(String nome) throws InterruptedException;

	ITesteWhatsapp getThiz();

	void setThiz(ITesteWhatsapp thiz);

	void saveCliente(Cliente cliente) throws ViewException;

	boolean enviarMensagem(String nome, String msg) throws InterruptedException;

	void saveCupom(Cupom cupom) throws ViewException;
	
}
