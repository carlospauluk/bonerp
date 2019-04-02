package com.bonsucesso.erp.vendas.view;



import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.rh.data.FuncionarioFinder;
import com.bonsucesso.erp.rh.model.Funcionario;
import com.bonsucesso.erp.vendas.business.MesVendaBusiness;
import com.bonsucesso.erp.vendas.data.MesVendaDataMapper;
import com.bonsucesso.erp.vendas.data.MesVendaFinder;
import com.bonsucesso.erp.vendas.data.VendaFinder;
import com.bonsucesso.erp.vendas.model.MesVenda;
import com.bonsucesso.erp.vendas.model.Venda;
import com.ocabit.base.view.AbstractEntityFormListView;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;
import com.ocabit.utils.calendar.CalendarUtil;


/**
 * View para a entidade MesVenda.
 *
 * @author Carlos Eduardo Pauluk
 */
@Component("mesVendaFormListView")
@Scope("view")
public final class MesVendaFormListView extends
		AbstractEntityFormListView<MesVenda, MesVendaDataMapper, MesVendaFinder> {

	/**
	 *
	 */
	private static final long serialVersionUID = -8438745294168074679L;

	protected static Logger logger = Logger.getLogger(MesVendaFormListView.class);

	@Autowired
	private FuncionarioFinder funcionarioFinder;

	@Autowired
	private VendaFinder vendaFinder;

	@Autowired
	private MesVendaBusiness mesVendaBusiness;

	private List<Venda> vendas;

	private Venda venda;

	private Date dtIni;

	private Date dtFim;

	private List<Map<String, Object>> diarias;

	@PostConstruct
	public void init() {
		JSFUtils.execute("loadList()");
		try {
			getMesVendaBusiness().atualizarMesesVendas();
			if (getE() == null || getE().getId() == null) {
				setE(getFinder().findByMesAno(new Date()));
			}

			setDtIni(CalendarUtil.primeiroDiaNoMesAno(getE().getMesAno()));
			setDtFim(CalendarUtil.ultimoDiaNoMesAno(getE().getMesAno()));

		} catch (ViewException e) {
			JSFUtils.addErrorMsg("Erro ao atualizar meses/vendas");
		}
	}

	public void checkId() {
		Object id = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest())
				.getAttribute("id");
		if (id == null && getE() != null && getE().getId() != null) {
			redirectToId();
		}
	}

	public void irPara(boolean proximo) {
		try {
			Date mesNavegar;
			if (proximo) {
				mesNavegar = CalendarUtil.getMesProximo(getE().getMesAno());
			} else {
				mesNavegar = CalendarUtil.getMesAnterior(getE().getMesAno());
			}
			setDtIni(CalendarUtil.primeiroDiaNoMesAno(mesNavegar));
			setDtFim(CalendarUtil.ultimoDiaNoMesAno(mesNavegar));
			setE(getFinder().findByMesAno(mesNavegar));
			redirectToId();
		} catch (ViewException e) {
			JSFUtils.addHandledException(e);
		}
	}

	@Override
	public void afterSetId() {
		updateDiarias();
	}

	public void updateDiarias() {
		try {
			setDtIni(CalendarUtil.primeiroDiaNoMesAno(getE().getMesAno()));
			setDtFim(CalendarUtil.ultimoDiaNoMesAno(getE().getMesAno()));

			setDiarias(getMesVendaBusiness().getDiarias(getDtIni(), getDtFim()));
		} catch (ViewException e) {
			JSFUtils.addHandledException(e);
		}
	}

	public void resetDiarias() {
		setDtIni(CalendarUtil.primeiroDiaNoMesAno(getE().getMesAno()));
		setDtFim(CalendarUtil.ultimoDiaNoMesAno(getE().getMesAno()));
		updateDiarias();
	}

	public BigDecimal getSomaDiariasBy(String coluna) {
		BigDecimal soma = BigDecimal.ZERO;
		if (getDiarias() != null) {
			for (Map<String, Object> r : getDiarias()) {
				if (r.get(coluna) != null) {
					soma = soma.add((BigDecimal) r.get(coluna));
				}
			}
		}
		return soma;
	}

	@Override
	public void afterSave() {
		// Utilizado somente quando o form é para o dialog
		RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam("dlgId", "dlgTotalMes");
		loadInitialList();
	}

	@Override
	public void deletar() {
		try {
			getDataMapper().delete(getE());
			loadInitialList();
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public List<Funcionario> getFuncionariosNoMesAno() {
		if ((getE() != null) && (getE().getMesAno() != null)) {
			return getFuncionarioFinder().findAtivosNoMesAno(getE().getMesAno());
		} else {
			return null;
		}
	}

	public void gerarProximoMes() {
		try {
			getDataMapper().gerarProximoMes();
			JSFUtils.addInfoMsg("Mês gerado com sucesso.");
		} catch (ViewException e) {
			JSFUtils.addErrorMsg("Não foi possível gerar o próximo mês.");
			JSFUtils.addHandledException(e);
			logger.error(e);
		}
	}

	public void atualizarMes() {
		try {
			getMesVendaBusiness().preencherMesVenda(getE());
			refreshE();
			logger.info("OK. Atualizado com sucesso.");
		} catch (ViewException e) {
			JSFUtils.addErrorMsg("Erro ao atualizar mês/venda");
			JSFUtils.addHandledException(e);
		}
	}

	public FuncionarioFinder getFuncionarioFinder() {
		return funcionarioFinder;
	}

	public void setFuncionarioFinder(FuncionarioFinder funcionarioFinder) {
		this.funcionarioFinder = funcionarioFinder;
	}

	public VendaFinder getVendaFinder() {
		return vendaFinder;
	}

	public void setVendaFinder(VendaFinder vendaFinder) {
		this.vendaFinder = vendaFinder;
	}

	public MesVendaBusiness getMesVendaBusiness() {
		return mesVendaBusiness;
	}

	public void setMesVendaBusiness(MesVendaBusiness mesVendaBusiness) {
		this.mesVendaBusiness = mesVendaBusiness;
	}

	@Override
	public Integer getQtdeRegistros() {
		Integer qtdeRegistros = super.getQtdeRegistros();
		if (qtdeRegistros == null) {
			qtdeRegistros = 13;
		}
		return qtdeRegistros;
	}

	public void updateVendasByTipo(String tipo) {
		try {
			if ("EXTERNOS-HISTORICO".equals(tipo)) {
				setVendas(getVendaFinder().findVendasSomenteExternosBy(CalendarUtil.incMes(getE().getMesAno(), -12)));
			} else if ("EXTERNOS-REALIZADO".equals(tipo)) {
				setVendas(getVendaFinder().findVendasSomenteExternosBy(getE().getMesAno()));
			}
		} catch (ViewException e) {
			JSFUtils.addHandledException(e);
		}
	}

	public void updateVendasByVendedor(Funcionario vendedor) {
		try {
			setVendas(getVendaFinder().findVendasBy(getE().getMesAno(), vendedor));
		} catch (ViewException e) {
			JSFUtils.addHandledException(e);
		}
	}

	public void updateVenda(Venda venda) {
		try {
			setVenda(getVendaFinder().refresh(venda));
		} catch (ViewException e) {
			JSFUtils.addHandledException(e);
		}
	}

	public List<Venda> getVendas() {
		return vendas;
	}

	public void setVendas(List<Venda> vendas) {
		this.vendas = vendas;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public Date getDtIni() {
		return dtIni;
	}

	public void setDtIni(Date dtIni) {
		this.dtIni = dtIni;
	}

	public Date getDtFim() {
		return dtFim;
	}

	public void setDtFim(Date dtFim) {
		this.dtFim = dtFim;
	}

	public List<Map<String, Object>> getDiarias() {
		return diarias;
	}

	public void setDiarias(List<Map<String, Object>> diarias) {
		this.diarias = diarias;
	}

}
