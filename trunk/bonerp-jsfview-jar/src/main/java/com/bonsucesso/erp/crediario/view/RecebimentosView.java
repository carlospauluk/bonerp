package com.bonsucesso.erp.crediario.view;



import java.io.Serializable;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.crediario.business.Prev2000Handler;
import com.bonsucesso.servipa.ws.ServerWsrcs;
import com.bonsucesso.servipa.ws.ServerWsrcsLocator;
import com.bonsucesso.servipa.ws.ServerWsrcsPortType;
import com.bonsucesso.servipa.ws.TRetornoRecebimentoDia;
import com.bonsucesso.servipa.ws.model.RecebimentoDia;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.config.data.ConfigFinder;
import com.ocabit.jsf.utils.JSFUtils;
import com.ocabit.utils.calendar.CalendarUtil;
import com.ocabit.utils.currency.CurrencyUtils;


@Component("recebimentosView")
@Scope("view")
public class RecebimentosView implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 2329310753661617780L;

	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	protected static Logger logger = Logger.getLogger(RecebimentosView.class);

	private ServerWsrcsPortType service;

	private List<Date> listDatas;

	private Map<Date, List<RecebimentoDia>> listRecebimentos;

	private Double totalSummary;

	@Autowired
	private ConfigFinder configFinder;

	@Autowired
	private BeanFactory beanFactory;

	private Prev2000Handler prev2000Handler;

	private RecebimentoDia recebimentoDia;

	// período para consultar recebimentos de acordos
	private Date periodoIni, periodoFim;

	private Boolean exibirSomenteExternos = true;

	public ConfigFinder getConfigFinder() {
		return configFinder;
	}

	public void setConfigFinder(ConfigFinder configFinder) {
		this.configFinder = configFinder;
	}

	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	public Prev2000Handler getPrev2000Handler() {
		if (prev2000Handler == null) {
			this.prev2000Handler = (Prev2000Handler) getBeanFactory().getBean("atualizaPrev2000");
		}
		return prev2000Handler;
	}

	public void setPrev2000Handler(Prev2000Handler prev2000Handler) {
		this.prev2000Handler = prev2000Handler;
	}

	public Date getPeriodoIni() {
		return periodoIni;
	}

	public void setPeriodoIni(Date periodoIni) {
		this.periodoIni = periodoIni;
	}

	public Date getPeriodoFim() {
		if (periodoFim == null) {
			setPeriodoFim(periodoIni);
		}
		return periodoFim;
	}

	public void setPeriodoFim(Date periodoFim) {
		this.periodoFim = periodoFim;
	}

	public Boolean getExibirSomenteExternos() {
		return exibirSomenteExternos;
	}

	public void setExibirSomenteExternos(Boolean exibirSomenteExternos) {
		this.exibirSomenteExternos = exibirSomenteExternos;
	}

	public List<Date> getListDatas() {
		return listDatas;
	}

	public void setListDatas(List<Date> listDatas) {
		this.listDatas = listDatas;
	}

	public Map<Date, List<RecebimentoDia>> getListRecebimentos() {
		return listRecebimentos;
	}

	public void setListRecebimentos(Map<Date, List<RecebimentoDia>> listRecebimentos) {
		this.listRecebimentos = listRecebimentos;
	}

	public ServerWsrcsPortType getService() {
		if (service == null) {
			try {
				ServerWsrcs locator = new ServerWsrcsLocator();
				String endpoint = configFinder.findValorByChave("bonsucesso.crediario.servipa_ws_endpoint");
				((ServerWsrcsLocator) locator).setServerWsrcsPortEndpointAddress(endpoint);
				service = locator.getServerWsrcsPort();
			} catch (ServiceException e) {
				JSFUtils.addErrorMsg("Erro na conexão");
				logger.error(e);
			} catch (ViewException e) {
				JSFUtils.addHandledException(e);
				logger.error(e);
			}
		}
		return service;
	}

	public void setService(ServerWsrcsPortType service) {
		this.service = service;
	}

	public void consultar() {
		try {
			logger.info("Iniciando consulta...");

			setListDatas(new ArrayList<Date>());
			setListRecebimentos(new HashMap<Date, List<RecebimentoDia>>());

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			logger.info("Período: " + sdf.format(getPeriodoIni()) + " - " + sdf.format(getPeriodoFim()));

			Date d = getPeriodoIni();
			do {
				List<RecebimentoDia> list = new ArrayList<RecebimentoDia>();
				logger.info("Consultando dia: " + sdf.format(d));
				list = consultarPorDia(d);
				if (list == null) {
					logger.info("Nenhum retorno.");
				} else {
					logger.info("Retornando: " + list.size() + " item(s)");
					listDatas.add(d);
					listRecebimentos.put(d, list);
					if (CalendarUtil.dataMaiorIgualQueData(d, getPeriodoFim())) {
						logger.info("Encerrando...");
						break;
					}
				}
				d = CalendarUtil.incDias(d, 1);
			} while (true);
			//setList(list);
			logger.info("Fechando conexões.");
			getPrev2000Handler().done();
		} catch (ViewException e) {
			JSFUtils.addHandledException(e);
			logger.error(e);
		}
		logger.info("Finalizando consulta com SUCESSO.");

	}

	public List<RecebimentoDia> consultarPorDia(Date data) throws ViewException {
		List<RecebimentoDia> list = new ArrayList<RecebimentoDia>();
		String paramApp = null;
		String instituicao = "3";
		String loja = "15090";
		String usuario = "wsbonsucesso";
		String senha = "ws$321%^ers";

		String senhaWS = null;
		String IP = null;
		String dadosCliente = null;
		String nomeURL = null;

		int dataAlteracao = Integer.parseInt(sdf.format(data));
		try {
			TRetornoRecebimentoDia recDia = getService()
					.fncListaRecebimentosDia(paramApp, instituicao, loja, usuario, senha, dataAlteracao, senhaWS, IP, dadosCliente, nomeURL);
			list = RecebimentoDia.convert(recDia.getListaRecebimento());

			// Se foi marcado para só retornar o que foi recebido externamente...
			if (Boolean.TRUE.equals(getExibirSomenteExternos())) {
				List<RecebimentoDia> _list = new ArrayList<RecebimentoDia>();
				for (RecebimentoDia rd : list) {
					// se não foi recebido na loja, adiciona...
					if (Boolean.FALSE.equals(rd.getRecebidoLoja())) {
						_list.add(rd);
					}
				}
				list = _list;
			}

			for (RecebimentoDia rd : list) {
				//if (Boolean.FALSE.equals(rd.getRecebidoLoja())) {
				getPrev2000Handler().findVendaParcela(rd);
				//}
			}
			return list;
		} catch (RemoteException e) {
			JSFUtils.addErrorMsg("Erro ao consultar.");
			logger.error(e);
			throw new ViewException("Erro ao consultar.");
		} catch (ParseException e) {
			JSFUtils.addErrorMsg("Erro ao converter listas.");
			logger.error(e);
			throw new ViewException("Erro ao converter listas.");
		}

	}

	public Double getTotalSummary() {
		return totalSummary;
	}

	public void setTotalSummary(Double totalSummary) {
		this.totalSummary = totalSummary;
	}

	public void calculateTotal(Date data, Object o) {
		BigDecimal total = CurrencyUtils.getBigDecimalCurrency(0.0);
		for (RecebimentoDia r : getListRecebimentos().get(data)) {
			if (r.getRecebidoLoja().equals(o)) {
				total = total.add(CurrencyUtils.getBigDecimalCurrency(r.getTotal()));
			}
		}
		setTotalSummary(total.doubleValue());
	}

	public RecebimentoDia getRecebimentoDia() {
		return recebimentoDia;
	}

	public void setRecebimentoDia(RecebimentoDia recebimentoDia) {
		this.recebimentoDia = recebimentoDia;
	}

	public void baixarParcela() {
		final RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam("dlgId", "dlgForm");
		if ((getRecebimentoDia().getVendaParcela().getDtBaixa() == null) &&
				(getRecebimentoDia().getVendaParcela().getDtPagto() == null)) {
			try {
				if (getPrev2000Handler().baixarParcela(getRecebimentoDia())) {
					context.addCallbackParam("saved", true);
					JSFUtils.addInfoMsg("Parcela baixada com sucesso.");
					getPrev2000Handler().findVendaParcela(getRecebimentoDia());
				}
			} catch (ViewException e) {
				logger.error(e);
				JSFUtils.addHandledException(e);
			}
		}

	}

}
