package com.bonsucesso.erp.vendas.data;



import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.vendas.model.MesVenda;
import com.ocabit.base.data.DataMapperImpl;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.utils.calendar.CalendarUtil;


/**
 * Implementação Data Mapper para a entidade MesVenda.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("mesVendaDataMapper")
public class MesVendaDataMapperImpl extends DataMapperImpl<MesVenda> implements MesVendaDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = -4638818911938530194L;

	protected static Logger logger = Logger.getLogger(MesVendaDataMapperImpl.class);

	@Autowired
	private MesVendaFinder mesVendaFinder;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, rollbackFor = {
			RuntimeException.class, ViewException.class })
	public void gerarProximoMes() throws ViewException {
		MesVenda ultimo = getMesVendaFinder().findUltimoMesCadastrado();
		MesVenda proximo = new MesVenda();
		proximo.setMesAno(CalendarUtil.incMes(ultimo.getMesAno(), 1));
		getThiz().save(proximo);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, rollbackFor = {
			RuntimeException.class, ViewException.class })
	public void gerarMesVendaAte(Date mesAno) throws ViewException {

		MesVenda ultimo = getMesVendaFinder().findUltimoMesCadastrado();

		if (ultimo == null) {
			throw new ViewException("Nenhum Mês/Venda encontrado.");
		} else if (CalendarUtil.dataMaiorIgualQueData(CalendarUtil.ultimoDiaNoMesAno(ultimo.getMesAno()), CalendarUtil
				.ultimoDiaNoMesAno(mesAno))) {
			// se o ultimo MesVenda já é igual ou posterior ao mesAno passado...
			logger.info("Mês/Venda já existente");
			return;
		}

		while (!CalendarUtil.isMesmoMesAno(ultimo.getMesAno(), mesAno)) {
			MesVenda proximo = new MesVenda();

			// incrementa o ultimo mes + 1
			proximo.setMesAno(CalendarUtil.incMes(ultimo.getMesAno(), 1));

			getThiz().save(proximo);

			ultimo = getMesVendaFinder().findUltimoMesCadastrado();

		}

	}

	public MesVendaFinder getMesVendaFinder() {
		return mesVendaFinder;
	}

	public void setMesVendaFinder(MesVendaFinder mesVendaFinder) {
		this.mesVendaFinder = mesVendaFinder;
	}

}
