package com.bonsucesso;

import com.ocabit.base.model.EntityId;
import com.ocabit.base.view.exception.ViewException;

public interface IMexerNoBD {

	void corrigir() throws ViewException;

	void corrigir2();
	
	IMexerNoBD getThiz();

	void setThiz(IMexerNoBD thiz);

	EntityId saveRow(EntityId row) throws ViewException;



}
