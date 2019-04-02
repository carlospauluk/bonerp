package com.bonsucesso.erp.producao.catalogo.data;



import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.producao.catalogo.model.Catalogo;
import com.bonsucesso.erp.producao.catalogo.model.CatalogoItem;
import com.bonsucesso.erp.producao.catalogo.model.CatalogoItemArtigo;
import com.ocabit.base.data.DataMapperImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação de DataMapper para a entidade Catalogo .
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("catalogoDataMapper")
public class CatalogoDataMapperImpl extends DataMapperImpl<Catalogo> implements CatalogoDataMapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4461777820931461716L;

	@Autowired
	private CatalogoItemDataMapper itemDataMapper;

	@Autowired
	private CatalogoItemArtigoDataMapper itemArtigoDataMapper;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED)
	public Catalogo beforeSave(Catalogo catalogo) throws ViewException {
		getEntityIdHandler().handleEntityIdFilhos(catalogo.getItens());
		if (catalogo.getItens() != null) {
			for (CatalogoItem ci : catalogo.getItens()) {
				getEntityIdHandler().handleEntityId(ci);
			}
		}
		return catalogo;
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class, ViewException.class })
	public void saveItens(Catalogo catalogo) throws ViewException {
		for (CatalogoItem ci : catalogo.getItens()) {
			ci = getItemDataMapper().save(ci);
			if (ci.getArtigos() != null) {
				for (CatalogoItemArtigo cia : ci.getArtigos()) {
					cia.setCatalogoItem(ci);
					getItemArtigoDataMapper().save(cia);
				}
			}
		}
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void limparCatalogo() {
		Query qryDelete = getEntityManager().createNativeQuery("DELETE FROM prod_catalogo_item_artigo");
		qryDelete.executeUpdate();
		qryDelete = getEntityManager().createNativeQuery("DELETE FROM prod_catalogo_item");
		qryDelete.executeUpdate();
	}

	public CatalogoItemDataMapper getItemDataMapper() {
		return itemDataMapper;
	}

	public void setItemDataMapper(CatalogoItemDataMapper itemDataMapper) {
		this.itemDataMapper = itemDataMapper;
	}

	public CatalogoItemArtigoDataMapper getItemArtigoDataMapper() {
		return itemArtigoDataMapper;
	}

	public void setItemArtigoDataMapper(CatalogoItemArtigoDataMapper itemArtigoDataMapper) {
		this.itemArtigoDataMapper = itemArtigoDataMapper;
	}

}
