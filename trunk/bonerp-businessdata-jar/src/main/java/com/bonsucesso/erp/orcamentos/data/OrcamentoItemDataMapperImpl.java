package com.bonsucesso.erp.orcamentos.data;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.orcamentos.model.OrcamentoItem;
import com.ocabit.base.data.DataMapperImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação Data Mapper para a entidade OrcamentoItem.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("orcamentoItemDataMapper")
public class OrcamentoItemDataMapperImpl extends DataMapperImpl<OrcamentoItem> implements
		OrcamentoItemDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = 8964202453262907994L;

	@Autowired
	private OrcamentoItemFinder finder;

	@Autowired
	private OrcamentoGrupoItemDataMapper grupoFinder;

	public OrcamentoItemFinder getFinder() {
		return finder;
	}

	public void setFinder(OrcamentoItemFinder finder) {
		this.finder = finder;
	}

	public OrcamentoGrupoItemDataMapper getGrupoFinder() {
		return grupoFinder;
	}

	public void setGrupoFinder(OrcamentoGrupoItemDataMapper grupoFinder) {
		this.grupoFinder = grupoFinder;
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED)
	public OrcamentoItem beforeSave(OrcamentoItem item) throws ViewException {
		// se está salvando um novo item
		if (item.getId() == null) {
			item.setGrupo(getGrupoFinder().refresh(item.getGrupo()));

			// se já existem outros itens no grupo
			item.setOrdem(getFinder().findProximaOrdem(item.getGrupo()));
		}
		return item;
	}

	@Override
	public void afterDelete(OrcamentoItem item) throws ViewException {
		int ultimaOrdem = 1;
		for (OrcamentoItem _item : item.getGrupo().getItens()) {
			_item.setOrdem(ultimaOrdem++);
			save(_item);
		}
	}

}
