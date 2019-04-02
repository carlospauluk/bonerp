package com.bonsucesso.erp.rh.data;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.rh.model.AvaliacaoVendaResp;
import com.bonsucesso.erp.vendas.data.VendaDataMapper;
import com.ocabit.base.data.DataMapperImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação de DataMapper para a entidade AvaliacaoVendaResp.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("avaliacaoVendaRespDataMapper")
public class AvaliacaoVendaRespDataMapperImpl extends DataMapperImpl<AvaliacaoVendaResp> implements
		AvaliacaoVendaRespDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = -5974164171766037396L;

	@Autowired
	private VendaDataMapper vendaDataMapper;

	public VendaDataMapper getVendaDataMapper() {
		return vendaDataMapper;
	}

	public void setVendaDataMapper(VendaDataMapper vendaDataMapper) {
		this.vendaDataMapper = vendaDataMapper;
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, rollbackFor = {
			RuntimeException.class, ViewException.class })
	public AvaliacaoVendaResp save(AvaliacaoVendaResp avaliacaoVendaResp) throws ViewException {

		// if (avaliacaoVendaResp.getVenda() != null && avaliacaoVendaResp.getVenda().getId() == null) {
		// getVendaDataMapper().save(avaliacaoVendaResp.getVenda());
		// }

		return super.save(avaliacaoVendaResp);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED)
	public AvaliacaoVendaResp beforeSave(AvaliacaoVendaResp avaliacaoVendaResp) throws ViewException {
		getEntityIdHandler().handleEntityIdFilhos(avaliacaoVendaResp.getRespostas());
		return avaliacaoVendaResp;
	}

}
