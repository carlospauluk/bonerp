package com.bonsucesso.erp.producao.data;



import java.math.BigInteger;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.producao.model.LoteConfeccaoItem;
import com.ocabit.base.data.DataMapperImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação Data Mapper para a entidade LoteConfeccaoItem.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("loteConfeccaoItemDataMapper")
public class LoteConfeccaoItemDataMapperImpl extends DataMapperImpl<LoteConfeccaoItem> implements
		LoteConfeccaoItemDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = -6007468414157456987L;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class, ViewException.class })
	public LoteConfeccaoItem beforeSave(LoteConfeccaoItem lci) throws ViewException {
		getEntityIdHandler().handleEntityIdFilhos(lci.getQtdesGrade());

		if (lci.getOrdem() == null || lci.getOrdem() < 1) {
			lci.setOrdem(((LoteConfeccaoItemDataMapper) getThiz()).findProximaOrdem(lci));
		}

		return lci;
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class, ViewException.class })
	public Integer findProximaOrdem(LoteConfeccaoItem lci) throws ViewException {
		try {
			Query qry = getEntityManager()
					.createNativeQuery("SELECT max(ordem)+1 FROM prod_lote_confeccao_item WHERE lote_confeccao_id = :lote_confeccao_id");
			qry.setParameter("lote_confeccao_id", lci.getLoteConfeccao().getId());
			BigInteger r = (BigInteger) qry.getSingleResult();
			return r != null ? r.intValue() : 1;
		} catch (Exception e) {
			throw new ViewException("Erro ao obter a próxima ordem do Item do Lote", e);
		}
	}

}
