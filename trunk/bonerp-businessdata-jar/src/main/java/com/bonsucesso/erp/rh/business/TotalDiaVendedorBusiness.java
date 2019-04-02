package com.bonsucesso.erp.rh.business;



import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.base.data.DiaUtilFinder;
import com.bonsucesso.erp.base.model.DiaUtil;
import com.bonsucesso.erp.rh.model.Funcionario;
import com.bonsucesso.erp.vendas.data.VendaFinder;
import com.bonsucesso.erp.vendas.model.Venda;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.utils.calendar.CalendarUtil;
import com.ocabit.utils.currency.CurrencyUtils;


@Component("totalDiaVendedorBusiness")
public class TotalDiaVendedorBusiness implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -6542208526118276160L;

	@Autowired
	private VendaFinder vendaFinder;

	@Autowired
	private DiaUtilFinder diaUtilFinder;

	@Autowired
	private BeanFactory beanFactory;

	/**
	 * Transforma uma dtListVenda que contém vendas de vários vendedores em um dia e sumariza em dtListTotalDiaVendedor.
	 *
	 * @param dtListVenda
	 * @return
	 */
	public List<TotalDiaVendedor> buildDtListTotalDiaVendedor(List<Venda> dtListVenda) {
		List<TotalDiaVendedor> dtListTotalDiaVendedor = new ArrayList<TotalDiaVendedor>();

		for (Venda venda : dtListVenda) {
			// ignoro as SAÍDAS AUTORIZADAS para a sumarização
			if (venda.getPlanoPagto().getCodigo().equals("6.00")) {
				continue;
			}
			boolean existe = false;
			for (TotalDiaVendedor tdv : dtListTotalDiaVendedor) {
				if (tdv.getVendedor().equals(venda.getVendedor())) {
					tdv.setTotal(tdv.getTotal().add(venda.getValorTotal()));
					existe = true;
					break;
				}
			}
			if (!existe) {
				TotalDiaVendedor tdv = new TotalDiaVendedor();
				tdv.setDia(venda.getDtVenda());
				tdv.setTotal(venda.getValorTotal());
				tdv.setVendedor(venda.getVendedor());
				dtListTotalDiaVendedor.add(tdv);
			}
		}

		return dtListTotalDiaVendedor;
	}

	/**
	 * Constrói uma list com os totais das vendas do vendedor por dia.
	 *
	 * @param vendedor
	 * @param ini
	 * @param fim
	 * @return
	 * @throws ViewException
	 */
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<TotalDiaVendedor> buildDtListTotaisDiasVendedor(Funcionario vendedor, Date ini, Date fim)
			throws ViewException {

		List<TotalDiaVendedor> dtListTotaisDiasVendedor = new ArrayList<TotalDiaVendedor>();

		List<DiaUtil> dias = getDiaUtilFinder().findDiasUteisComerciaisBy(ini, fim);

		for (DiaUtil dia : dias) {
			TotalDiaVendedor tdv = new TotalDiaVendedor();
			tdv.setDia(dia.getDia());

			BigDecimal totalVendasDia = getVendaFinder().findTotalBy(dia.getDia(), dia.getDia(), vendedor);

			tdv.setTotal(totalVendasDia);
			tdv.setVendedor(vendedor);
			dtListTotaisDiasVendedor.add(tdv);

		}

		return dtListTotaisDiasVendedor;
	}

	/**
	 * Overload.
	 *
	 * @param vendedor
	 * @param mesAno
	 * @return
	 * @throws ViewException
	 */
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<TotalDiaVendedor> buildDtListTotaisDiasVendedor(Funcionario vendedor, Date mesAno)
			throws ViewException {
		return getThiz().buildDtListTotaisDiasVendedor(vendedor, CalendarUtil.primeiroDiaNoMesAno(mesAno), CalendarUtil
				.ultimoDiaNoMesAno(mesAno));
	}

	/**
	 * Calcula o total em uma list de TotalDiaVendedor.
	 *
	 * @param list
	 * @return
	 */
	public BigDecimal calcTotalVendido(List<TotalDiaVendedor> list) {
		BigDecimal bdTotal = CurrencyUtils.getBigDecimalCurrency("0.0");
		for (TotalDiaVendedor tdv : list) {
			bdTotal = bdTotal.add(tdv.getTotal());
		}
		return bdTotal;
	}

	public VendaFinder getVendaFinder() {
		return vendaFinder;
	}

	public void setVendaFinder(VendaFinder vendaFinder) {
		this.vendaFinder = vendaFinder;
	}

	public DiaUtilFinder getDiaUtilFinder() {
		return diaUtilFinder;
	}

	public void setDiaUtilFinder(DiaUtilFinder diaUtilFinder) {
		this.diaUtilFinder = diaUtilFinder;
	}

	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	public TotalDiaVendedorBusiness getThiz() {
		return (TotalDiaVendedorBusiness) getBeanFactory().getBean("totalDiaVendedorBusiness");
	}

}
