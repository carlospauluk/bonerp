package com.bonsucesso.erp.vendas.data;



import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.fiscal.business.NotaFiscalBusiness;
import com.bonsucesso.erp.vendas.model.StatusVenda;
import com.bonsucesso.erp.vendas.model.Venda;
import com.bonsucesso.erp.vendas.model.VendaItem;
import com.ocabit.base.data.DataMapperImpl;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.utils.calendar.CalendarUtil;


/**
 * Implementação de DataMapper para a entidade Venda.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("vendaDataMapper")
public class VendaDataMapperImpl extends DataMapperImpl<Venda> implements VendaDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = -2563784541986199219L;

	protected static Logger logger = Logger.getLogger(VendaDataMapperImpl.class);

	@Autowired
	private PlanoPagtoFinder planoPagtoFinder;

	@Autowired
	private MesVendaDataMapper mesVendaDataMapper;

	@Autowired
	private NotaFiscalBusiness notaFiscalBusiness;

	@Autowired
	private VendaFinder vendaFinder;

	/**
	 * Isolation.SERIALIZABLE
	 * 
	 * @param venda
	 * @return
	 * @throws ViewException
	 */
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED)
	public Venda beforeSave(Venda venda) throws ViewException {

		if (venda.getStatus() == null) {
			venda.setStatus(StatusVenda.PREVENDA);
		}

		if (venda.getMesano() == null || "".equals(venda.getMesano())) {
			venda.setMesano(new SimpleDateFormat("yyyyMM").format(venda.getDtVenda()));
		}

		if (venda.getPlanoPagto() == null) {
			venda.setPlanoPagto(getPlanoPagtoFinder().findBy("9.99"));
		}

		if (venda.getPv() == null) {
			if (venda.getMesano() == null || "".equals(venda.getMesano().trim())) {
				throw new ViewException("mesano null");
			}
			Integer proxPV = getVendaFinder().findProximoPV(venda.getMesano());
			venda.setPv(proxPV);
		}

		if (venda.getCliente() != null && venda.getCliente().getId() == null) {
			venda.setCliente(null);
		}

		// manda gerar os MesVenda até a data da venda atual (no método ele se preocupa em verificar a necessidade disto)
		getMesVendaDataMapper().gerarMesVendaAte(venda.getDtVenda());

		getEntityIdHandler().handleEntityIdFilhos(venda.getItens());

		int i=1;
		for (VendaItem item : venda.getItens()) {
			item.setNcmExistente(getNotaFiscalBusiness().findNCMExiste(item.getNcm()));
			if (item.getOrdem() == null) {
				item.setOrdem(i++);
			}
		}

		return venda;
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void deleteTodosBy(Date dtVenda) throws ViewException {
		try {
			final String jpql = "DELETE FROM Venda WHERE dtVenda = :dtVenda AND valorTotal != 0";
			final Query qry = getEntityManager().createQuery(jpql);
			qry.setParameter("dtVenda", CalendarUtil.zeroDate(dtVenda));
			qry.executeUpdate();
		} catch (Exception e) {
			logger.error(e);
			throw new ViewException("Erro ao deletar", e);
		}
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public Venda save_RN(Venda venda) throws ViewException {
		return getThiz().save(venda);
	}

	public MesVendaDataMapper getMesVendaDataMapper() {
		return mesVendaDataMapper;
	}

	public void setMesVendaDataMapper(MesVendaDataMapper mesVendaDataMapper) {
		this.mesVendaDataMapper = mesVendaDataMapper;
	}

	public PlanoPagtoFinder getPlanoPagtoFinder() {
		return planoPagtoFinder;
	}

	public void setPlanoPagtoFinder(PlanoPagtoFinder planoPagtoFinder) {
		this.planoPagtoFinder = planoPagtoFinder;
	}

	public VendaFinder getVendaFinder() {
		return vendaFinder;
	}

	public void setVendaFinder(VendaFinder vendaFinder) {
		this.vendaFinder = vendaFinder;
	}

	public NotaFiscalBusiness getNotaFiscalBusiness() {
		return notaFiscalBusiness;
	}

	public void setNotaFiscalBusiness(NotaFiscalBusiness notaFiscalBusiness) {
		this.notaFiscalBusiness = notaFiscalBusiness;
	}

}
