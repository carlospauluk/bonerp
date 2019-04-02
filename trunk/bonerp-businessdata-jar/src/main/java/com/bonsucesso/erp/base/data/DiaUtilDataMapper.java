package com.bonsucesso.erp.base.data;



import java.util.List;

import com.bonsucesso.erp.base.model.DiaUtil;
import com.ocabit.base.data.DataMapper;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de DataMapper para entidade DiaUtil.
 *
 * @author Carlos Eduardo Pauluk
 */
public interface DiaUtilDataMapper extends DataMapper<DiaUtil> {

	public void saveList_diasUteisComerciais(List<DiaUtil> lista, boolean comercial) throws ViewException;

}
