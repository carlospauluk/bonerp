package com.bonsucesso.erp.producao.catalogo.data;



import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.producao.catalogo.model.CatalogoItem;
import com.bonsucesso.erp.producao.catalogo.model.CatalogoItemArtigo;
import com.bonsucesso.erp.producao.model.TipoArtigo;
import com.ocabit.base.data.DataMapperImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação de DataMapper para a entidade CatalogoItemArtigo.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("catalogoItemArtigoDataMapper")
public class CatalogoItemArtigoDataMapperImpl extends DataMapperImpl<CatalogoItemArtigo>
		implements CatalogoItemArtigoDataMapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2089548836102160496L;
	
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, rollbackFor = {
			RuntimeException.class, ViewException.class })
	@Override
	public void saveArtigosItem(CatalogoItem item, List<TipoArtigo> tiposArtigos) throws ViewException {
		try {
			for (TipoArtigo tipoArtigo : tiposArtigos) {
				CatalogoItemArtigo cia = new CatalogoItemArtigo();
				cia.setCatalogoItem(item);
				cia.setDescricao(tipoArtigo.getDescricao());
				cia.setTipoArtigo(tipoArtigo);
				save(cia);
			}
		} catch (Exception e) {
			throw new ViewException("Erro ao salvar os artigos do item", e);
		}

	}

}
