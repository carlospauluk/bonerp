package com.bonsucesso.erp.rh.view;



import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.rh.business.TotalDiaAnaliticoImporter;
import com.bonsucesso.erp.rh.business.TotalDiaVendedor;
import com.bonsucesso.erp.rh.business.TotalDiaVendedorBusiness;
import com.bonsucesso.erp.vendas.data.PlanoPagtoFinder;
import com.bonsucesso.erp.vendas.data.VendaDataMapper;
import com.bonsucesso.erp.vendas.data.VendaFinder;
import com.bonsucesso.erp.vendas.model.Venda;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;
import com.ocabit.utils.currency.CurrencyUtils;


/**
 * View para a entidade TotalDiaVendedor.
 *
 * @author Carlos Eduardo Pauluk
 */
@Component("totalDiaAnaliticoImporterView")
@Scope("view")
public final class TotalDiaAnaliticoImporterView implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 4202626176905528715L;

	protected static Logger logger = Logger.getLogger(TotalDiaAnaliticoImporterView.class);

	private Date dia;

	private BigDecimal totalMes;

	private List<Venda> dtListVenda;

	private List<TotalDiaVendedor> dtListTotalDia;

	private String linhas;

	private BigDecimal totalDia;

	@Autowired
	private VendaDataMapper vendaDataMapper;

	@Autowired
	private VendaFinder vendaFinder;

	@Autowired
	private TotalDiaAnaliticoImporter totalDiaAnaliticoImporter;

	@Autowired
	private PlanoPagtoFinder planoPagtoFinder;

	@Autowired
	private TotalDiaVendedorBusiness totalDiaVendedorBusiness;

	@PostConstruct
	public void init() {
		setDia(new Date());
		updateDia();
	}

	public void updateDia() {
		try {
			setDtListVenda(getVendaFinder().findVendasBy(getDia()));
			setDtListTotalDia(getTotalDiaVendedorBusiness().buildDtListTotalDiaVendedor(getDtListVenda()));
			calcTotalDia();

			setTotalMes(getVendaFinder().findTotalBy(getDia()));
		} catch (ViewException e) {
			logger.error(e);
		}
	}

	/**
	 * Método que processa as linhas e gera a dtList com os resultados.
	 */
	public void processarArquivo() {
		try {
			StringBuilder erros = new StringBuilder("");
			List<String> linhas = Arrays.asList(getLinhas().split("\\r\\n"));
			setDtListVenda((getTotalDiaAnaliticoImporter().importar(linhas, erros)));

			setDtListTotalDia(getTotalDiaVendedorBusiness().buildDtListTotalDiaVendedor(getDtListVenda()));
			calcTotalDia();

			setTotalMes(getVendaFinder().findTotalBy(getDia()));
			logger.info("Processado.");
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	/**
	 * Método que salva todos os registros da dtList importada.
	 */
	public void salvarTudo() {
		try {
			Date dia = getDtListVenda().get(0).getDtVenda();
			setDia(dia);
			getVendaDataMapper().deleteTodosBy(dia);

			for (Venda v : getDtListVenda()) {
				v.setPlanoPagto(getPlanoPagtoFinder().refresh(v.getPlanoPagto()));
				System.out.println(getVendaFinder().getEntityManager().contains(v.getPlanoPagto()));
			}
			getVendaDataMapper().saveList(getDtListVenda());

			setLinhas(null);

			updateDia();

			JSFUtils.addInfoMsgRegistroSalvo();
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		} catch (Exception e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao salvar lista");
		}
	}

	/**
	 *
	 */
	public void calcTotalDia() {
		BigDecimal totalDia = CurrencyUtils.getBigDecimalCurrency("0.0");
		for (TotalDiaVendedor tdv : getDtListTotalDia()) {
			totalDia = CurrencyUtils.soma(totalDia, tdv.getTotal());
		}
		setTotalDia(totalDia);
	}

	public BigDecimal getTotalMes() {
		return totalMes;
	}

	public void setTotalMes(BigDecimal totalMes) {
		this.totalMes = totalMes;
	}

	public String getLinhas() {
		return linhas;
	}

	public void setLinhas(String linhas) {
		this.linhas = linhas;
	}

	public TotalDiaAnaliticoImporter getTotalDiaAnaliticoImporter() {
		return totalDiaAnaliticoImporter;
	}

	public void setTotalDiaAnaliticoImporter(TotalDiaAnaliticoImporter totalDiaAnaliticoImporter) {
		this.totalDiaAnaliticoImporter = totalDiaAnaliticoImporter;
	}

	public VendaFinder getVendaFinder() {
		return vendaFinder;
	}

	public void setVendaFinder(VendaFinder vendaFinder) {
		this.vendaFinder = vendaFinder;
	}

	public Date getDia() {
		return dia;
	}

	public void setDia(Date data) {
		dia = data;
	}

	public List<Venda> getDtListVenda() {
		if (dtListVenda == null) {
			dtListVenda = new ArrayList<Venda>();
		}
		return dtListVenda;
	}

	public void setDtListVenda(List<Venda> dtListVenda) {
		this.dtListVenda = dtListVenda;
	}

	public List<TotalDiaVendedor> getDtListTotalDia() {
		if (dtListTotalDia == null) {
			dtListTotalDia = new ArrayList<TotalDiaVendedor>();
		}
		return dtListTotalDia;
	}

	public void setDtListTotalDia(List<TotalDiaVendedor> dtListTotalDia) {
		this.dtListTotalDia = dtListTotalDia;
	}

	public VendaDataMapper getVendaDataMapper() {
		return vendaDataMapper;
	}

	public void setVendaDataMapper(VendaDataMapper vendaDataMapper) {
		this.vendaDataMapper = vendaDataMapper;
	}

	public BigDecimal getTotalDia() {
		return totalDia;
	}

	public void setTotalDia(BigDecimal totalDia) {
		this.totalDia = totalDia;
	}

	public PlanoPagtoFinder getPlanoPagtoFinder() {
		return planoPagtoFinder;
	}

	public void setPlanoPagtoFinder(PlanoPagtoFinder planoPagtoFinder) {
		this.planoPagtoFinder = planoPagtoFinder;
	}

	public TotalDiaVendedorBusiness getTotalDiaVendedorBusiness() {
		return totalDiaVendedorBusiness;
	}

	public void setTotalDiaVendedorBusiness(TotalDiaVendedorBusiness totalDiaVendedorBusiness) {
		this.totalDiaVendedorBusiness = totalDiaVendedorBusiness;
	}

}
