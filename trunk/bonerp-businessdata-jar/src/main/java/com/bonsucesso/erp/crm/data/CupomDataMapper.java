package com.bonsucesso.erp.crm.data;



import com.bonsucesso.erp.crm.model.Cupom;
import com.bonsucesso.erp.crm.model.LoteCupom;
import com.ocabit.base.data.DataMapper;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de DataMapper para entidade Cupom.
 *
 * @author Carlos Eduardo Pauluk
 */
public interface CupomDataMapper extends DataMapper<Cupom> {

	public void gerarCupons(LoteCupom lote, Integer numero) throws ViewException;

}
