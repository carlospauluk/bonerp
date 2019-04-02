package com.bonsucesso.erp.financeiro.data;



import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.financeiro.model.Cadeia;
import com.bonsucesso.erp.financeiro.model.Movimentacao;
import com.ocabit.base.data.DataMapperImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação de DataMapper para a entidade Cadeia.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("cadeiaDataMapper")
public class CadeiaDataMapperImpl extends DataMapperImpl<Cadeia> implements CadeiaDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = 3070452206250105132L;

	protected static Logger logger = Logger.getLogger(CadeiaDataMapper.class);

	@Autowired
	private MovimentacaoDataMapper movimentacaoDataMapper;

	public MovimentacaoDataMapper getMovimentacaoDataMapper() {
		return movimentacaoDataMapper;
	}

	public void setMovimentacaoDataMapper(MovimentacaoDataMapper movimentacaoDataMapper) {
		this.movimentacaoDataMapper = movimentacaoDataMapper;
	}

	/**
	 * ATENÇÃO: como a trigger "trg_ad_delete_cadeia" já se encarrega de deletar uma cadeia sem movimentações, então não é possível
	 * deleta-la via código.
	 * É necessário somente deletar todas as movimentações da cadeia, que a trigger se encarregará de deletar a cadeia automaticamente.
	 */
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	@Override
	public void delete(Cadeia cadeia) throws ViewException {

		for (Movimentacao m : cadeia.getMovimentacoes()) {
			getMovimentacaoDataMapper().delete(m);
		}

	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, rollbackFor = {
			RuntimeException.class, ViewException.class })
	public Cadeia saveCadeiaEMovimentacoes(Cadeia cadeia) throws ViewException {
		try {
			// abre a cadeia
			cadeia.setFechada(false);
			cadeia = super.save(cadeia);
			getEntityManager().flush();
						

			// inclui as movimentações
			for (Movimentacao m : cadeia.getMovimentacoes()) {
				m.setCadeia(cadeia);
				getMovimentacaoDataMapper().save(m);
			}
			getEntityManager().flush();

			// fecha a cadeia
			cadeia.setFechada(true);
			cadeia = super.save(cadeia);
			getEntityManager().flush();
			return cadeia;
		} catch (Exception e) {
			logger.error(e);
			throw new ViewException("Erro ao salvar a cadeia", e);
		}

	}

}
