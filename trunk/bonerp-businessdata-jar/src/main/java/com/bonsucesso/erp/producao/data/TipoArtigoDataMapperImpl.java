package com.bonsucesso.erp.producao.data;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.producao.model.TipoArtigo;
import com.ocabit.base.data.DataMapperImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação Data Mapper para a entidade TipoArtigo.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("tipoArtigoDataMapper")
public class TipoArtigoDataMapperImpl extends DataMapperImpl<TipoArtigo> implements TipoArtigoDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = 6327603323046436831L;
	@Autowired
	private TipoArtigoFinder tipoArtigoFinder;

	public TipoArtigoFinder getTipoArtigoFinder() {
		return tipoArtigoFinder;
	}

	public void setTipoArtigoFinder(TipoArtigoFinder tipoArtigoFinder) {
		this.tipoArtigoFinder = tipoArtigoFinder;
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED)
	public TipoArtigo beforeSave(TipoArtigo entity) throws ViewException {
		if (entity.getCodigo() == null) {
			entity.setCodigo(getTipoArtigoFinder().findNextCodigo());
		}
		return entity;
	}

}
