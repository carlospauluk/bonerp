package com.bonsucesso.erp.financeiro.data;



import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.financeiro.model.Movimentacao;
import com.bonsucesso.erp.financeiro.model.Parcelamento;
import com.ocabit.base.data.DataMapperImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação de DataMapper para a entidade Parcelamento.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("parcelamentoDataMapper")
public class ParcelamentoDataMapperImpl extends DataMapperImpl<Parcelamento>
		implements ParcelamentoDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = 4026281165660331224L;

	protected static Logger logger = Logger.getLogger(ParcelamentoDataMapperImpl.class);

	@Autowired
	private MovimentacaoDataMapper movimentacaoDataMapper;

	public MovimentacaoDataMapper getMovimentacaoDataMapper() {
		return movimentacaoDataMapper;
	}

	public void setMovimentacaoDataMapper(MovimentacaoDataMapper movimentacaoDataMapper) {
		this.movimentacaoDataMapper = movimentacaoDataMapper;
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public Parcelamento save(Parcelamento parcelamento) throws ViewException {

		List<Movimentacao> parcelas = parcelamento.getParcelas();

		parcelamento = super.save(parcelamento);

		// Não posso usar o persist automático do cascade pois para cada movimentação existem várias regras no beforeSave().
		for (Movimentacao parcela : parcelas) {
			parcela.setParcelamento(parcelamento);
			getMovimentacaoDataMapper().save(parcela);
		}

		return parcelamento;

	}

	/**
	 * ATENÇÃO: como a trigger "trg_ad_delete_cadeia" já se encarrega de deletar uma cadeia sem movimentações, então não é possível
	 * deleta-la via código.
	 * É necessário somente deletar todas as movimentações da cadeia, que a trigger se encarregará de deletar a cadeia automaticamente.
	 */
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, rollbackFor = {
			RuntimeException.class, ViewException.class })
	@Override
	public void delete(Parcelamento parcelamento) throws ViewException {

		logger.debug("Recarregando parcelamento...");

		parcelamento = getThiz().refresh(parcelamento);

		logger.debug("Ordenando parcelas ao contrário para começar deletando sempre pela última...");

		// tem que ordenar as parcelas ao contrário porque só permite deletar a última
		Collections.sort(parcelamento.getParcelas(), new Comparator<Movimentacao>() {

			@Override
			public int compare(Movimentacao o1, Movimentacao o2) {
				return new CompareToBuilder()
						.append(o2.getNumParcela(), o1.getNumParcela()).toComparison();
			}
		});

		logger.debug("Iniciando deleção de parcelas...");

		for (Movimentacao m : parcelamento.getParcelas()) {
			m = getMovimentacaoDataMapper().refresh(m);
			logger.debug("Deletando: " + m);
			getMovimentacaoDataMapper().delete(m);
			logger.debug("OK!");
			logger.debug("");
		}

		logger.debug("Finalizando deleção de parcelas... OK");

	}

}
