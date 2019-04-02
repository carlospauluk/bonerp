package com.bonsucesso.erp.rh.data;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.rh.model.Funcionario;
import com.bonsucesso.erp.rh.model.FuncionarioCargo;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jpa.utils.JPAUtil;
import com.ocabit.utils.calendar.CalendarUtil;


/**
 * Implementação de Finder para entidade Funcionario.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("funcionarioFinder")
public class FuncionarioFinderImpl extends BasicEntityFinderImpl<Funcionario> implements FuncionarioFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = 274000268058361487L;

	protected static Logger logger = Logger.getLogger(FuncionarioFinderImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Funcionario> findByNome(String str) {
		final String jpql = "FROM Funcionario f WHERE f.pessoa.nome LIKE :str";
		final Query qry = getEntityManager().createQuery(jpql);
		qry.setParameter("str", "%" + str + "%");
		JPAUtil.cacheOn(qry);
		return qry.getResultList();
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Funcionario findByCodigoAndNomeEkt(String nomeEkt, Integer codigo) {
		Query qry = getEntityManager()
				.createQuery("FROM Funcionario f WHERE f.nomeEkt LIKE :nomeEkt AND f.codigo = :codigo");
		qry.setParameter("codigo", codigo);
		qry.setParameter("nomeEkt", nomeEkt);
		JPAUtil.cacheOn(qry);

		try {
			Funcionario funcionario = (Funcionario) qry.getSingleResult();

			return funcionario;
		} catch (NoResultException e) {
			logger.info("Nenhum resultado encontrado.");
			return null;
		} catch (Exception e2) {
			throw new IllegalStateException(e2);
		}
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Funcionario findByCpf(String cpf) {
		Query qry = getEntityManager().createQuery("FROM Funcionario f WHERE f.pessoa.documento = :cpf");
		qry.setParameter("cpf", cpf);

		try {
			Funcionario funcionario = (Funcionario) qry.getSingleResult();

			return funcionario;
		} catch (NoResultException e) {
			logger.info("Nenhum resultado encontrado.");
			return null;
		} catch (Exception e2) {
			throw new IllegalStateException(e2);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Funcionario> findAtuais() {
		final String jpql = "SELECT c.funcionario FROM FuncionarioCargo c WHERE c.atual IS TRUE";
		final Query qry = getEntityManager().createQuery(jpql);
		JPAUtil.cacheOn(qry);
		return qry.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Funcionario> findAtivosNoMesAno(Date mesAno) {
		if (mesAno == null) {
			throw new IllegalStateException("mesAno deve ser informado");
		}
		Date dataIni = CalendarUtil.primeiroDiaNoMesAno(mesAno);
		Date dataFim = CalendarUtil.ultimoDiaNoMesAno(mesAno);
		final String jpql = "SELECT c.funcionario FROM FuncionarioCargo c WHERE " +
				"(c.dtInicio <= :dataIni OR c.dtFim BETWEEN :dataIni AND :dataFim) " +
				"AND " +
				"(c.dtFim >= :dataFim OR c.dtFim BETWEEN :dataIni AND :dataFim OR c.dtFim IS NULL)" +
				" ORDER BY c.funcionario.pessoa.nome";
		final Query qry = getEntityManager().createQuery(jpql);
		JPAUtil.cacheOn(qry);
		qry.setParameter("dataIni", dataIni);
		qry.setParameter("dataFim", dataFim);
		return qry.getResultList();
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	@Cacheable(value = "methodsCache")
	public List<Funcionario> findVendedoresAtivosNoMesAno(Date mesAno) throws ViewException {

		List<Funcionario> ativosNoMesAno = ((FuncionarioFinder) getThiz()).findAtivosNoMesAno(mesAno);

		List<Funcionario> vendedoresAtivosNoMesAno = new ArrayList<Funcionario>();

		for (Funcionario f : ativosNoMesAno) {
			FuncionarioCargo cargo = findCargoByMesAno(f, mesAno);
			if (cargo == null) {
				logger.info("Funcionário " + f.getPessoa().getNome() + " sem cargo atual");
				continue;
			}
			if (cargo.getCargo().getDescricao().equals("VENDEDOR(A)")
					|| cargo.getCargo().getDescricao().equals("AUXILIAR DE VENDAS")
					|| cargo.getCargo().getDescricao().equals("AUXILIAR DE SERVIÇOS GERAIS")) {
				vendedoresAtivosNoMesAno.add(f);
			}
		}

		return vendedoresAtivosNoMesAno;
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public FuncionarioCargo findCargoByMesAno(Funcionario funcionario, Date mesAno) {
		try {
			final String jpql = "FROM FuncionarioCargo WHERE "
					+
					"funcionario = :funcionario AND "
					+
					"(dtInicio <= :dataIni OR dtInicio BETWEEN :dataIni AND :dataFim) AND (dtFim >= :dataFim OR (dtFim BETWEEN :dataIni AND :dataFim) OR dtFim IS NULL)";
			final Query qry = getEntityManager().createQuery(jpql);
			qry.setParameter("funcionario", funcionario);
			qry.setParameter("dataIni", CalendarUtil.primeiroDiaNoMesAno(mesAno));
			Date dtFim = CalendarUtil.zeroDate(CalendarUtil.ultimoDiaNoMesAno(mesAno));
			qry.setParameter("dataFim", dtFim);
			JPAUtil.cacheOn(qry);
			return (FuncionarioCargo) qry.getSingleResult();
		} catch (NoResultException e) {
			logger.debug("Nenhum resultado encontrado");
			return null;
		} catch (NonUniqueResultException e) {
			logger.debug("Mais de um resultado encontrado");
			return null;
		} catch (Exception e) {
			logger.error(e);
			throw new IllegalStateException(e);
		}
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public Funcionario findByCodigo(Integer codigo) throws ViewException {
		Query qry = getEntityManager().createQuery("SELECT c.funcionario FROM FuncionarioCargo c WHERE c.funcionario.codigo = :codigo AND c.atual IS TRUE");
		qry.setParameter("codigo", codigo);

		try {
			JPAUtil.cacheOn(qry);

			Funcionario funcionario = (Funcionario) qry.getSingleResult();

			return funcionario;
		} catch (NoResultException e) {
			logger.info("Nenhum resultado encontrado.");
			return null;
		} catch (Exception e2) {
			throw new ViewException(e2);
		}
	}

	@Override
	public FuncionarioCargo findCargoAtualBy(Funcionario funcionario) throws ViewException {
		funcionario = getThiz().refresh(funcionario);

		for (FuncionarioCargo cargo : funcionario.getCargos()) {
			if (cargo.getAtual()) {
				return cargo;
			}
		}

		return null;
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Funcionario findByNomeEktAndMesAno(String nomeEkt, Date mesAno) {

		Date dataIni = CalendarUtil.primeiroDiaNoMesAno(mesAno);
		Date dataFim = CalendarUtil.ultimoDiaNoMesAno(mesAno);
		final String jpql = "SELECT c.funcionario FROM FuncionarioCargo c " +
				"WHERE c.funcionario.nomeEkt LIKE :nomeEkt AND " +
				"(c.dtInicio <= :dataIni OR c.dtInicio BETWEEN :dataIni AND :dataFim) " +
				"AND " +
				"(c.dtFim >= :dataFim OR c.dtFim BETWEEN :dataIni AND :dataFim OR c.dtFim IS NULL)";

		Query qry = getEntityManager()
				.createQuery(jpql);
		qry.setParameter("nomeEkt", nomeEkt);
		qry.setParameter("dataIni", dataIni);
		qry.setParameter("dataFim", dataFim);

		try {
			JPAUtil.cacheOn(qry);

			Funcionario funcionario = (Funcionario) qry.getSingleResult();

			return funcionario;
		} catch (NoResultException e) {
			logger.info("Nenhum resultado encontrado.");
			return null;
		} catch (Exception e2) {
			throw new IllegalStateException(e2);
		}
	}

}
