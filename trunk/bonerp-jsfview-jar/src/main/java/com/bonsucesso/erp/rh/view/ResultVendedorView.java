package com.bonsucesso.erp.rh.view;



import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.base.data.DiaUtilFinder;
import com.bonsucesso.erp.base.model.DiaUtil;
import com.bonsucesso.erp.rh.data.FuncionarioFinder;
import com.bonsucesso.erp.rh.model.Funcionario;
import com.bonsucesso.erp.vendas.data.MesVendaFinder;
import com.bonsucesso.erp.vendas.data.VendaFinder;
import com.bonsucesso.erp.vendas.model.MesVenda;
import com.bonsucesso.erp.vendas.model.MesVendaItem;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;
import com.ocabit.security.spring.SecurityUtils;
import com.ocabit.utils.calendar.CalendarUtil;


/**
 * View para a entidade TotalMes.
 *
 * @author Carlos Eduardo Pauluk
 */
@Component("resultVendedorView")
@Scope("view")
public final class ResultVendedorView implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -2266522869618358981L;

	protected static Logger logger = Logger.getLogger(ResultVendedorView.class);

	private Integer codigoVendedor;

	private Funcionario vendedor;

	private String senha;

	@Autowired
	private DiaUtilFinder diaUtilFinder;

	@Autowired
	private VendaFinder vendaFinder;

	@Autowired
	private MesVendaFinder mesVendaFinder;

	@Autowired
	private FuncionarioFinder funcionarioFinder;

	private Date dtBase = new Date();

	private List<SelectItem> mesesSelectItems;

	private MesVenda mesVenda;

	private MesVendaItem mvi;

	private List dtListTotaisDias;

	private List dtListMesesAnteriores;

	public void consultar() {
		try {
			verificarSenha();
		} catch (Exception e) {
			JSFUtils.addErrorMsg("Senha inválida.");
			logger.error(e);
			return;
		}

		try {
			setMesVenda(getMesVendaFinder().findByMesAno(getDtBase()));
		} catch (ViewException e) {
			JSFUtils.addErrorMsg("Erro ao buscar dados.");
		}

		try {
			setMvi(getMesVendaFinder().findMesVendaItemBy(getVendedor(), getDtBase()));
		} catch (ViewException e) {
			JSFUtils.addErrorMsg("Erro ao buscar dados.");
		}

		try {
			List<DiaUtil> diasUteisComerciais = getDiaUtilFinder().findDiasUteisComerciaisBy(getDtBase());
			setDtListTotaisDias(null);
			for (DiaUtil dia : diasUteisComerciais) {
				BigDecimal total = getVendaFinder().findTotalBy(dia.getDia(), dia.getDia(), getVendedor());
				getDtListTotaisDias().add(new Object[] { dia.getDia(), total });
			}

		} catch (ViewException e) {
			JSFUtils.addErrorMsg("Erro ao buscar vendas diárias.");
		}

		try {
			setDtListMesesAnteriores(null);
			List<Date> mesesAnteriores = CalendarUtil
					.buildMesAnoList(CalendarUtil.incMes(getDtBase(), -6), CalendarUtil.incMes(getDtBase(), -1));

			for (int i = mesesAnteriores.size() - 1; i >= 0; i--) {
				Date m = mesesAnteriores.get(i);
				MesVendaItem mvi = getMesVendaFinder().findMesVendaItemBy(getVendedor(), m);

				if (mvi != null) {
					Boolean bateuMetaMinima = mvi.getTotalVendido().doubleValue() >= mvi.getMesVenda()
							.getMetaMinimaVendedor().doubleValue();
					Boolean bateuMetaEsperada = mvi.getTotalVendido().doubleValue() >= mvi.getMesVenda()
							.getMetaEsperadaVendedor().doubleValue();
					
					getDtListMesesAnteriores().add(new Object[] {
							m,
							mvi.getTotalVendido(),
							mvi.getMesVenda().getMetaEsperadaVendedor(),
							bateuMetaMinima,
							bateuMetaEsperada,
							mvi.getPosicao()
					});
				}

			}
		} catch (ViewException e) {
			JSFUtils.addErrorMsg("Erro ao buscar dados dos meses anteriores.");
		}

		logger.info("OK");

	}

	public void irPara(boolean proximo) {
		if (proximo) {
			setDtBase(CalendarUtil.getMesProximo(getDtBase()));
		} else {
			setDtBase(CalendarUtil.getMesAnterior(getDtBase()));
		}
		consultar();
	}

	private void verificarSenha() throws ViewException {

		setVendedor(getFuncionarioFinder().refresh(getVendedor()));

		if (SecurityUtils.isUserInRole("ROLE_VENDAS_ADMIN")) {
			return;
		}

		if ((getSenha() != null) && !"".equals(getSenha())) {
			if ((getVendedor() != null) && (getVendedor().getSenha() != null)) {

				final PasswordEncoder newEncoder = new BCryptPasswordEncoder();
				if (!newEncoder.matches(getSenha(), getVendedor().getSenha())) {
					throw new ViewException("Senha inválida");
				} else {
					return;
				}
			}
		}
		throw new ViewException("Senha inválida");
	}

	public void updateVendedor() {
		try {
			setVendedor(getFuncionarioFinder().findByCodigo(getCodigoVendedor()));
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao pesquisar vendedor.");
		}
	}

	public void limpar() {
		setCodigoVendedor(null);
		setDtBase(new Date());
		setSenha(null);
		setVendedor(null);
		setMvi(null);
		setDtListMesesAnteriores(null);
		setDtListTotaisDias(null);
	}

	public Date getUltimoDiaLancado() {
		try {
			return getVendaFinder().findUltimoDiaComVendas();
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao pesquisar último dia lançado");
			return null;
		}
	}

	public Integer getCodigoVendedor() {
		return codigoVendedor;
	}

	public void setCodigoVendedor(Integer codigoVendedor) {
		this.codigoVendedor = codigoVendedor;
	}

	public Funcionario getVendedor() {
		return vendedor;
	}

	public void setVendedor(Funcionario vendedor) {
		this.vendedor = vendedor;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public DiaUtilFinder getDiaUtilFinder() {
		return diaUtilFinder;
	}

	public void setDiaUtilFinder(DiaUtilFinder diaUtilFinder) {
		this.diaUtilFinder = diaUtilFinder;
	}

	public VendaFinder getVendaFinder() {
		return vendaFinder;
	}

	public void setVendaFinder(VendaFinder vendaFinder) {
		this.vendaFinder = vendaFinder;
	}

	public FuncionarioFinder getFuncionarioFinder() {
		return funcionarioFinder;
	}

	public void setFuncionarioFinder(FuncionarioFinder funcionarioFinder) {
		this.funcionarioFinder = funcionarioFinder;
	}

	public Date getDtBase() {
		return dtBase;
	}

	public void setDtBase(Date dtBase) {
		this.dtBase = dtBase;
	}

	public List<SelectItem> getMesesSelectItems() {
		if (mesesSelectItems == null) {
			mesesSelectItems = new ArrayList<SelectItem>();
			SimpleDateFormat sdf = new SimpleDateFormat("MMMMM/yyyy");
			Date data = new Date();
			// Se não for dia 1º, pega o dia de ontem...
			if (CalendarUtil.getCalendar(data).get(Calendar.DATE) > 1) {
				data = CalendarUtil.incDias(new Date(), -1);
			}
			data = CalendarUtil.to235959(data);
			setDtBase(data);
			mesesSelectItems.add(new SelectItem(data, "MÊS ATUAL"));
			for (int i = 0; i < 6; i++) {
				Calendar cData = CalendarUtil.getCalendar(data);
				cData.add(Calendar.MONTH, -1);
				cData = CalendarUtil.ultimoDiaNoMesAno(cData);
				data = cData.getTime();
				mesesSelectItems.add(new SelectItem(cData.getTime(), sdf.format(cData.getTime())));
			}
		}
		return mesesSelectItems;
	}

	public void setMesesSelectItems(List<SelectItem> mesesSelectItems) {
		this.mesesSelectItems = mesesSelectItems;
	}

	public MesVendaFinder getMesVendaFinder() {
		return mesVendaFinder;
	}

	public void setMesVendaFinder(MesVendaFinder mesVendaFinder) {
		this.mesVendaFinder = mesVendaFinder;
	}

	public MesVendaItem getMvi() {
		return mvi;
	}

	public void setMvi(MesVendaItem mvi) {
		this.mvi = mvi;
	}

	public List getDtListTotaisDias() {
		if (dtListTotaisDias == null) {
			dtListTotaisDias = new ArrayList();
		}
		return dtListTotaisDias;
	}

	public void setDtListTotaisDias(List dtListTotaisDias) {
		this.dtListTotaisDias = dtListTotaisDias;
	}

	public List getDtListMesesAnteriores() {
		if (dtListMesesAnteriores == null) {
			dtListMesesAnteriores = new ArrayList();
		}
		return dtListMesesAnteriores;
	}

	public void setDtListMesesAnteriores(List dtListMesesAnteriores) {
		this.dtListMesesAnteriores = dtListMesesAnteriores;
	}

	public MesVenda getMesVenda() {
		return mesVenda;
	}

	public void setMesVenda(MesVenda mv) {
		this.mesVenda = mv;
	}

}
