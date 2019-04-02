package com.bonsucesso.erp.producao.data;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.producao.model.LoteConfeccao;
import com.bonsucesso.erp.producao.model.LoteConfeccaoItem;
import com.ocabit.base.data.DataMapperImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação Data Mapper para a entidade LoteConfeccao.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("loteConfeccaoDataMapper")
public class LoteConfeccaoDataMapperImpl extends DataMapperImpl<LoteConfeccao> implements LoteConfeccaoDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = 4524916480793192108L;
	@Autowired
	private LoteConfeccaoFinder loteConfeccaoFinder;

	public LoteConfeccaoFinder getLoteConfeccaoFinder() {
		return loteConfeccaoFinder;
	}

	public void setLoteConfeccaoFinder(LoteConfeccaoFinder loteConfeccaoFinder) {
		this.loteConfeccaoFinder = loteConfeccaoFinder;
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED)
	public LoteConfeccao beforeSave(LoteConfeccao loteConfeccao) throws ViewException {
		if (loteConfeccao.getCodigo() == null) {
			loteConfeccao.setCodigo(getLoteConfeccaoFinder().findNextCodigo());
		}
		getEntityIdHandler().handleEntityIdFilhos(loteConfeccao.getItens());
		for (LoteConfeccaoItem lci : loteConfeccao.getItens()) {
			getEntityIdHandler().handleEntityIdFilhos(lci.getQtdesGrade());
		}
		return loteConfeccao;
	}

}
