package com.bonsucesso.erp.financeiro.data;



import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.financeiro.model.RegistroConferencia;
import com.ocabit.base.data.DataMapperImpl;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.utils.calendar.CalendarUtil;


/**
 * Implementação de DataMapper para a entidade RegistroConferencia.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("registroConferenciaDataMapper")
public class RegistroConferenciaDataMapperImpl extends DataMapperImpl<RegistroConferencia> implements
		RegistroConferenciaDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = -8396625447223811161L;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public RegistroConferencia gerarProximo(RegistroConferencia registroConferencia) throws ViewException {

		RegistroConferencia ultimo = null;

		try {
			final String jpql = "FROM RegistroConferencia WHERE descricao = :descricao AND carteira = :carteira ORDER BY dtRegistro DESC";
			final Query qry = getEntityManager().createQuery(jpql);

			qry.setMaxResults(1);
			qry.setParameter("descricao", registroConferencia.getDescricao());
			qry.setParameter("carteira", registroConferencia.getCarteira());
			ultimo = (RegistroConferencia) qry.getSingleResult();
		} catch (Exception e) {
			throw new ViewException(e);
		}

		if (ultimo == null) {
			throw new ViewException("Último não encontrado");
		}

		Date dtProximo;
		if (CalendarUtil.isSameDay(registroConferencia.getDtRegistro(), CalendarUtil
				.ultimoDiaNoMesAno(registroConferencia.getDtRegistro()))) {
			dtProximo = CalendarUtil.ultimoDiaNoMesAno(CalendarUtil.incMes(ultimo.getDtRegistro(), 1));
		} else {
			dtProximo = CalendarUtil.incMes(ultimo.getDtRegistro(), 1);
		}

		RegistroConferencia proximo = new RegistroConferencia();
		proximo.setCarteira(registroConferencia.getCarteira());
		proximo.setDescricao(registroConferencia.getDescricao());
		proximo.setDtRegistro(dtProximo);
		proximo.setValor(new BigDecimal("0.00"));

		return ((RegistroConferenciaDataMapper) getThiz()).save(proximo);

	}

}
