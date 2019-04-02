package com.bonsucesso.erp.financeiro.data;



import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.financeiro.model.Grupo;
import com.bonsucesso.erp.financeiro.model.GrupoItem;
import com.ocabit.base.data.DataMapperImpl;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.utils.calendar.CalendarUtil;


/**
 * Implementação de DataMapper para a entidade Grupo.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("grupoDataMapper")
public class GrupoDataMapperImpl extends DataMapperImpl<Grupo> implements GrupoDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = 7190489019734776272L;

	protected static Logger logger = Logger.getLogger(GrupoDataMapperImpl.class);

	@Autowired
	private GrupoItemFinder grupoItemFinder;

	public GrupoItemFinder getGrupoItemFinder() {
		return grupoItemFinder;
	}

	public void setGrupoItemFinder(GrupoItemFinder grupoItemFinder) {
		this.grupoItemFinder = grupoItemFinder;
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED)
	public GrupoItem gerarProximo(Grupo grupo) throws ViewException {

		GrupoItem ultimoGrupoItem = null;

		try {
			final String jpql = "FROM GrupoItem WHERE pai = :grupo ORDER BY dtVencto DESC";
			final Query qry = getEntityManager().createQuery(jpql);

			qry.setMaxResults(1);
			qry.setParameter("grupo", grupo);
			ultimoGrupoItem = (GrupoItem) qry.getSingleResult();
		} catch (NoResultException e) {
			logger.info("Nenhum item encontrado para este Grupo.");
		} catch (Exception e) {
			throw new ViewException(e);
		}

		Date dtIni = null;

		if (ultimoGrupoItem != null) {
			dtIni = ultimoGrupoItem.getDtVencto();
		} else {
			dtIni = new Date();
		}

		Date dtFim = CalendarUtil.incMes(dtIni, 1);

		return ((GrupoDataMapper) getThiz()).gerarNoMesAno(grupo, dtFim);

		//		GrupoDataMapper grupoDataMapper = (GrupoDataMapper) getBeanFactory().getBean("grupoDataMapper");
		//
		//		GrupoItem grupoItem = null;
		//
		//		try {
		//			final String jpql = "FROM GrupoItem WHERE pai = :grupo ORDER BY dtVencto DESC";
		//			final Query qry = getEntityManager().createQuery(jpql);
		//
		//			qry.setMaxResults(1);
		//			qry.setParameter("grupo", grupo);
		//			grupoItem = (GrupoItem) qry.getSingleResult();
		//		} catch (NoResultException e) {
		//			logger.info("Nenhum item encontrado para este Grupo.");
		//		} catch (Exception e) {
		//			throw new ViewException(e);
		//		}
		//
		//		GrupoItem novoItem = new GrupoItem();
		//		novoItem.setPai(grupo);
		//		novoItem.setFechado(false);
		//		novoItem.setValorInformado(0.0);
		//
		//		if (grupoItem != null) {
		//			novoItem.setAnterior(grupoItem);
		//			novoItem.setCarteiraPagante(grupoItem.getCarteiraPagante());
		//			// Incrementa em 1 mês
		//			Calendar cal = CalendarUtil.getCalendar(grupoItem.getDtVencto());
		//			cal.add(Calendar.MONTH, 1);
		//			novoItem.setDtVencto(CalendarUtil.zeroDate(cal.getTime()));
		//			grupo = grupoItem.getPai();
		//		} else {
		//			novoItem.setAnterior(null);
		//			novoItem.setCarteiraPagante(grupo.getCarteiraPagantePadrao());
		//			// Seta para o diaVencto deste mês
		//			Calendar cal = CalendarUtil.getCalendar(new Date());
		//			if (grupo.getDiaVencto() > 31) {
		//				cal.set(Calendar.DAY_OF_MONTH, 1);
		//				cal = CalendarUtil.ultimoDiaNoMesAno(cal);
		//			} else {
		//				cal.set(Calendar.DAY_OF_MONTH, grupo.getDiaVencto());
		//			}
		//			novoItem.setDtVencto(CalendarUtil.zeroDate(cal.getTime()));
		//		}
		//		novoItem.setDescricao(gerarDescricao(novoItem));
		//
		//
		//		// novoItem.setPai(null);
		//		grupo.getItens().add(novoItem);
		//
		//		grupo = grupoDataMapper.save(grupo);
		//
		//		if (grupoItem != null) {
		//			grupoItem = grupo.getItens().get(grupo.getItens().indexOf(grupoItem));
		//			novoItem = grupo.getItens().get(grupo.getItens().indexOf(novoItem));
		//			grupoItem.setProximo(novoItem);
		//			grupo = grupoDataMapper.save(grupo);
		//		}
		//
		//		return grupoItem;

	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED)
	public GrupoItem gerarNoMesAno(Grupo grupo, Date mesAno) throws ViewException {

		GrupoItem grupoItem = getGrupoItemFinder().findBy(grupo, mesAno);

		// Se já existe, não precisa gerar, só retorna.
		if (grupoItem != null) {
			return grupoItem;
		}

		// Verifica se existe algum anterior
		GrupoItem ultimoAnterior = getGrupoItemFinder().findUltimoAnterior(grupo, mesAno);
		Date dtIni = mesAno;
		if (ultimoAnterior != null) {
			dtIni = ultimoAnterior.getDtVencto();
		}

		Date dtFim = mesAno;
		GrupoItem primeiroPosterior = getGrupoItemFinder().findPrimeiroPosterior(grupo, mesAno);
		if (primeiroPosterior != null) {
			dtFim = primeiroPosterior.getDtVencto();
		}

		((GrupoDataMapper) getThiz()).gerarTodos(grupo, dtIni, dtFim);

		return getGrupoItemFinder().findBy(grupo, mesAno);

	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED)
	public GrupoItem gerarTodos(Grupo grupo, Date mesAnoIni, Date mesAnoFim) throws ViewException {

		mesAnoIni = CalendarUtil.primeiroDiaNoMesAno(mesAnoIni);
		mesAnoFim = CalendarUtil.ultimoDiaNoMesAno(mesAnoFim);

		if (CalendarUtil.dataMaiorQueData(mesAnoIni, mesAnoFim)) {
			throw new IllegalStateException("mesAnoIni > mesAnoFim");
		}

		GrupoItemDataMapper itemDataMapper = (GrupoItemDataMapper) getBeanFactory().getBean("grupoItemDataMapper");

		// Verifica se existe algum anterior ao mesAnoIni
		GrupoItem ultimoAnterior = getGrupoItemFinder().findUltimoAnterior(grupo, mesAnoIni);
		if (ultimoAnterior != null) {
			mesAnoIni = ultimoAnterior.getDtVencto();
			mesAnoIni = CalendarUtil.primeiroDiaNoMesAno(mesAnoIni);
		}

		// Verifica se existe algum posterior ao mesAnoFim
		GrupoItem primeiroPosterior = getGrupoItemFinder().findPrimeiroPosterior(grupo, mesAnoFim);
		if (primeiroPosterior != null) {
			mesAnoFim = primeiroPosterior.getDtVencto();
			mesAnoFim = CalendarUtil.ultimoDiaNoMesAno(mesAnoFim);
		}

		Date aux = mesAnoIni;

		getEntityManager().detach(grupo);

		GrupoItem grupoItemAnterior = null;

		do {

			GrupoItem novoItem = getGrupoItemFinder().findBy(grupo, aux);

			if (novoItem == null) {

				novoItem = new GrupoItem();
				novoItem.setPai(grupo);
				novoItem.setFechado(false);
				novoItem.setValorInformado(BigDecimal.ZERO);
				novoItem.setAnterior(grupoItemAnterior);
				novoItem.setCarteiraPagante(grupo.getCarteiraPagantePadrao());
				// Seta para o diaVencto do mesAno
				// Se o mesAno passado é anterior a dataAtual, será gerado no mês do passado
				Calendar cal = CalendarUtil.getCalendar(aux);
				if (grupo.getDiaVencto() > 31) {
					cal.set(Calendar.DAY_OF_MONTH, 1);
					cal = CalendarUtil.ultimoDiaNoMesAno(cal);
				} else {
					cal.set(Calendar.DAY_OF_MONTH, grupo.getDiaVencto());
				}
				novoItem.setDtVencto(CalendarUtil.zeroDate(cal.getTime()));
				novoItem.setDescricao(gerarDescricao(novoItem));

				novoItem = itemDataMapper.save(novoItem);

				//grupo.getItens().add(novoItem);

				//grupo = getThiz().save(grupo);

			} else {
				// RTA: não sei por qual motivo, mas se usar o novoItem que retornou do findBy ele vem com tudo null.
				// É alguma coisa relacionada aos lazy
				itemDataMapper.getEntityManager().detach(novoItem);
				novoItem = itemDataMapper.refresh(novoItem);

				if (grupoItemAnterior != null) {
					novoItem.setAnterior(grupoItemAnterior);
					novoItem = itemDataMapper.save(novoItem);
				}
			}

			if (grupoItemAnterior != null) {
				grupoItemAnterior.setProximo(novoItem);
				grupoItemAnterior = itemDataMapper.save(grupoItemAnterior);
				//grupo = getThiz().save(grupo);
			}

			grupoItemAnterior = novoItem;

			aux = CalendarUtil.incMes(aux, 1);
			aux = CalendarUtil.ultimoDiaNoMesAno(aux);

		} while (CalendarUtil.dataMenorIgualQueData(aux, mesAnoFim));

		return grupoItemAnterior;
	}

	/**
	 * Gera a descrição do GrupoItem.
	 */
	@Override
	public String gerarDescricao(GrupoItem grupoItem) {
		return grupoItem.getPai().getDescricao() + " - "
				+ new SimpleDateFormat("dd/MM/yyyy").format(grupoItem.getDtVencto());
	}

	@Override
	public Grupo beforeSave(Grupo grupo) throws ViewException {
		getEntityIdHandler().handleEntityIdFilhos(grupo.getItens());
		return grupo;
	}
	
	

}
