package com.bonsucesso.erp.crm.data;



import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.crm.model.Cupom;
import com.bonsucesso.erp.crm.model.LoteCupom;
import com.bonsucesso.erp.crm.model.StatusCupom;
import com.ocabit.base.data.DataMapperImpl;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.utils.strings.StringUtils;


/**
 * Implementação Data Mapper para a entidade Cupom.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("cupomDataMapper")
public class CupomDataMapperImpl extends DataMapperImpl<Cupom> implements CupomDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = -587540775081553888L;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void gerarCupons(LoteCupom lote, Integer numero) throws ViewException {
		for (int i = 0; i < numero; i++) {
			int ordem = i + 1;
			Cupom cupom = new Cupom();
			cupom.setLoteCupom(lote);
			cupom.setOrdem(ordem);
			cupom.setStatus(StatusCupom.NAO_VINCULADO);
			//cupom.setCodigo(gerarCodigoCupom(lote));
			cupom.setCodigo(lote.getNumLote() + StringUtils.zerofill("" + ordem, 5));
			save(cupom);
		}

	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED)
	public Cupom beforeSave(Cupom cupom) throws ViewException {
		return super.beforeSave(cupom);
	}
	
	

}
