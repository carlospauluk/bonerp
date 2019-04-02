package com.bonsucesso.erp.financeiro.data;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.financeiro.model.Categoria;
import com.bonsucesso.erp.financeiro.model.RegraImportacaoLinha;
import com.bonsucesso.erp.financeiro.model.TipoLancto;
import com.ocabit.base.data.DataMapperImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação de DataMapper para a entidade RegraImportacaoLinha.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("regraImportacaoLinhaDataMapper")
public class RegraImportacaoLinhaDataMapperImpl extends DataMapperImpl<RegraImportacaoLinha> implements
		RegraImportacaoLinhaDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = 3832346549048115791L;

	@Autowired
	private CategoriaFinder categoriaFinder;

	public CategoriaFinder getCategoriaFinder() {
		return categoriaFinder;
	}

	public void setCategoriaFinder(CategoriaFinder categoriaFinder) {
		this.categoriaFinder = categoriaFinder;
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED)
	public RegraImportacaoLinha beforeSave(RegraImportacaoLinha regra) throws ViewException {
		if (regra.getTipoLancto().equals(TipoLancto.TRANSF_PROPRIA)) {
			Categoria categ299 = getCategoriaFinder().findBy(299l);
			regra.setCategoria(categ299);
		}
		return regra;
	}

}
