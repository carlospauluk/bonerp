package com.bonsucesso.erp.financeiro.view;



import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.financeiro.business.MovimentacaoImporter.TipoExtrato;
import com.bonsucesso.erp.financeiro.data.BancoFinder;
import com.bonsucesso.erp.financeiro.data.BandeiraCartaoFinder;
import com.bonsucesso.erp.financeiro.data.CarteiraFinder;
import com.bonsucesso.erp.financeiro.data.CentroCustoFinder;
import com.bonsucesso.erp.financeiro.data.GrupoFinder;
import com.bonsucesso.erp.financeiro.data.GrupoItemFinder;
import com.bonsucesso.erp.financeiro.data.ModoFinder;
import com.bonsucesso.erp.financeiro.data.OperadoraCartaoFinder;
import com.bonsucesso.erp.financeiro.model.Banco;
import com.bonsucesso.erp.financeiro.model.BandeiraCartao;
import com.bonsucesso.erp.financeiro.model.Carteira;
import com.bonsucesso.erp.financeiro.model.CentroCusto;
import com.bonsucesso.erp.financeiro.model.Grupo;
import com.bonsucesso.erp.financeiro.model.GrupoItem;
import com.bonsucesso.erp.financeiro.model.Modo;
import com.bonsucesso.erp.financeiro.model.OperadoraCartao;
import com.bonsucesso.erp.financeiro.model.Recorrencia;
import com.bonsucesso.erp.financeiro.model.Status;
import com.bonsucesso.erp.financeiro.model.TipoLancto;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;


/**
 * Classe responsável por criar os diversos tipos de lista utilizados em
 * componentes de tela JSF como f:selectItems e ui:repeat.
 *
 * Possui o escopo de Session, portanto para refletir qualquer alteração nos
 * valores das listas é necessário reiniciar a sessão HTTP do cliente.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("lmFinanc")
@Scope("view")
public class ListMaker implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Logger logger = Logger.getLogger(ListMaker.class);

	@Autowired
	protected GrupoFinder grupoFinder;

	/**
	 * Variável auxiliar para telas onde precisa selecionar um GrupoItem.
	 */
	private Grupo grupo;

	@Autowired
	protected GrupoItemFinder grupoItemFinder;

	@Autowired
	protected ModoFinder modoFinder;

	@Autowired
	protected BandeiraCartaoFinder bandeiraCartaoFinder;

	@Autowired
	protected OperadoraCartaoFinder operadoraCartaoFinder;

	@Autowired
	protected CarteiraFinder carteiraFinder;

	@Autowired
	protected CentroCustoFinder centroCustoFinder;

	@Autowired
	protected BancoFinder bancoFinder;

	private List<Grupo> grupoValues;

	private List<Grupo> grupoAtivoValues;

	private List<GrupoItem> grupoItemValues;

	private List<Modo> modoValues;

	private List<BandeiraCartao> bandeiraCartaoValues;

	private List<OperadoraCartao> operadoraCartaoValues;

	private List<Modo> modoDeTransfPropriaValues;

	private List<Modo> modoDeTransfCaixaValues;

	private List<Carteira> carteiraValues;

	private List<Carteira> carteiraConcretaValues;

	private List<Carteira> carteiraCaixaValues;

	/**
	 * Carteiras que podem conter movimentações ABERTAS.
	 */
	private List<Carteira> carteiraAbertasValues;

	private List<Carteira> carteiraChequeValues;

	private List<Carteira> carteiraBancosValues;

	private Carteira carteiraDestinoPadraoTransfCaixa;

	private List<CentroCusto> centroCustoValues;

	private List<Recorrencia.Frequencia> recorrenciaFrequenciaValues;

	private List<Recorrencia.TipoRepeticao> recorrenciaTipoRepeticaoValues;

	private List<TipoLancto> tiposLancto;

	private List<TipoExtrato> tiposExtrato;

	/**
	 * Status de Movimentacao.
	 */
	private List<Status> statusValues;

	private String anoCorrente = new SimpleDateFormat("yyyy")
			.format(new Date());

	public void setGrupoItemValues(List<GrupoItem> grupoItemValues) {
		this.grupoItemValues = grupoItemValues;
	}

	public List<GrupoItem> getGrupoItemValues() {
		try {
			if (grupoItemValues == null) {
				grupoItemValues = grupoItemFinder.findAll("dtVencto DESC");
			}
			return grupoItemValues;
		} catch (ViewException e) {
			logger.error("Erro no ListFactoryFinanc - getGrupoItemValues");
			return null;
		}
	}

	public void setModoValues(List<Modo> modoValues) {
		this.modoValues = modoValues;
	}

	public List<Modo> getModoValues() {
		try {
			if (modoValues == null) {
				modoValues = modoFinder.findAll("codigo");
			}
			return modoValues;
		} catch (ViewException e) {
			logger.error("Erro no ListFactoryFinanc - getModoValues");
			return null;
		}
	}

	public List<BandeiraCartao> getBandeiraCartaoValues() {
		try {
			if (bandeiraCartaoValues == null) {
				bandeiraCartaoValues = getBandeiraCartaoFinder().findAll("descricao");
			}
			return bandeiraCartaoValues;
		} catch (ViewException e) {
			logger.error("Erro no ListFactoryFinanc - getBandeiraCartaoValues");
			return null;
		}
	}

	public void setBandeiraCartaoValues(List<BandeiraCartao> bandeiraCartaoValues) {
		this.bandeiraCartaoValues = bandeiraCartaoValues;
	}

	public List<OperadoraCartao> getOperadoraCartaoValues() {
		try {
			if (operadoraCartaoValues == null) {
				operadoraCartaoValues = getOperadoraCartaoFinder().findAll("descricao");
			}
			return operadoraCartaoValues;
		} catch (ViewException e) {
			logger.error("Erro no ListFactoryFinanc - getOperadoraCartaoValues");
			return null;
		}
	}

	public void setOperadoraCartaoValues(List<OperadoraCartao> operadoraCartaoValues) {
		this.operadoraCartaoValues = operadoraCartaoValues;
	}

	public List<Modo> getModoDeTransfPropriaValues() {
		if (modoDeTransfPropriaValues == null) {
			try {
				modoDeTransfPropriaValues = getModoFinder()
						.findAllTransfPropria();
			} catch (ViewException e) {
				logger.error(e);
				JSFUtils.addHandledException(e);
			}
		}
		return modoDeTransfPropriaValues;
	}

	public void setModoDeTransfPropriaValues(
			List<Modo> modoDeTransfPropriaValues) {
		this.modoDeTransfPropriaValues = modoDeTransfPropriaValues;
	}

	public List<Modo> getModoDeTransfCaixaValues() {
		if (modoDeTransfCaixaValues == null) {
			try {
				modoDeTransfCaixaValues = getModoFinder().findAllTransfCaixa();
			} catch (ViewException e) {
				logger.error(e);
				JSFUtils.addHandledException(e);
			}
		}
		return modoDeTransfCaixaValues;
	}

	public void setModoDeTransfCaixaValues(List<Modo> modoDeTransfCaixaValues) {
		this.modoDeTransfCaixaValues = modoDeTransfCaixaValues;
	}

	public void setCarteiraValues(List<Carteira> carteiraValues) {
		this.carteiraValues = carteiraValues;
	}

	public List<Carteira> getCarteiraValues() {
		try {
			if (carteiraValues == null) {
				carteiraValues = carteiraFinder.findAll("codigo");
			}
			return carteiraValues;
		} catch (ViewException e) {
			logger.error("Erro no ListFactoryFinanc - getCarteiraValues");
			return null;
		}
	}

	public CentroCustoFinder getCentroCustoFinder() {
		return centroCustoFinder;
	}

	public void setCentroCustoFinder(CentroCustoFinder centroCustoFinder) {
		this.centroCustoFinder = centroCustoFinder;
	}

	public List<CentroCusto> getCentroCustoValues() {
		if (centroCustoValues == null) {
			try {
				centroCustoValues = getCentroCustoFinder().findAll("codigo");
			} catch (ViewException e) {
				logger.error(e);
				JSFUtils.addHandledException(e);
			}
		}
		return centroCustoValues;
	}

	public void setCentroCustoValues(List<CentroCusto> centroCustoValues) {
		this.centroCustoValues = centroCustoValues;
	}

	public void setCarteiraConcretaValues(List<Carteira> carteiraConcretaValues) {
		this.carteiraConcretaValues = carteiraConcretaValues;
	}

	public List<Carteira> getCarteiraConcretaValues() {
		try {
			if (carteiraConcretaValues == null) {
				carteiraConcretaValues = carteiraFinder.findAllConcretas();
			}
			return carteiraConcretaValues;
		} catch (ViewException e) {
			logger.error("Erro no ListFactoryFinanc - getCarteiraConcretaValues");
			return null;
		}
	}

	public void setGrupoValues(List<Grupo> grupoValues) {
		this.grupoValues = grupoValues;
	}

	public List<Grupo> getGrupoValues() {
		try {
			if (grupoValues == null) {
				grupoValues = grupoFinder.findAll("descricao");
			}
			return grupoValues;
		} catch (ViewException e) {
			logger.error("Erro no ListFactoryFinanc - getGrupoValues");
			return null;
		}
	}

	public void setGrupoAtivoValues(List<Grupo> grupoAtivoValues) {
		this.grupoAtivoValues = grupoAtivoValues;
	}

	public List<Grupo> getGrupoAtivoValues() {
		try {
			if (grupoAtivoValues == null) {
				grupoAtivoValues = grupoFinder.findAtivos();
			}
			return grupoAtivoValues;
		} catch (ViewException e) {
			logger.error("Erro no ListFactoryFinanc - getGrupoAtivoValues");
			return null;
		}
	}

	public void setCarteiraBancosValues(List<Carteira> carteiraBancosValues) {
		this.carteiraBancosValues = carteiraBancosValues;
	}

	public List<Carteira> getCarteiraBancosValues() {
		if (carteiraBancosValues == null) {
			try {
				carteiraBancosValues = getCarteiraFinder().findAllBancos();
			} catch (ViewException e) {
				logger.error(e);
			}
		}
		return carteiraBancosValues;
	}

	public Grupo getGrupo() {
		if (grupo != null) {
			try {
				grupo = getGrupoFinder().refresh(grupo);
				grupo.getItens().size(); // touch
			} catch (Exception e) {
				JSFUtils.addErrorMsg("Erro ao setar grupo.");
				logger.error(e);
			}
		}
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public List<Carteira> getCarteiraCaixaValues() {
		try {
			if (carteiraCaixaValues == null) {
				carteiraCaixaValues = carteiraFinder.findAllCaixas();
			}
			return carteiraCaixaValues;
		} catch (ViewException e) {
			logger.error("Erro no ListFactoryFinanc - getCarteiraCaixaValues");
			return null;
		}
	}

	public void setCarteiraCaixaValues(List<Carteira> carteiraCaixaValues) {
		this.carteiraCaixaValues = carteiraCaixaValues;
	}

	public List<Carteira> getCarteiraAbertasValues() {
		try {
			if (carteiraAbertasValues == null) {
				carteiraAbertasValues = carteiraFinder.findAllAbertas();
			}
			return carteiraAbertasValues;
		} catch (ViewException e) {
			logger.error("Erro no ListFactoryFinanc - getCarteiraAbertasValues");
			return null;
		}
	}

	public void setCarteiraAbertasValues(List<Carteira> carteiraAbertaValues) {
		carteiraAbertasValues = carteiraAbertaValues;
	}

	public List<Carteira> getCarteiraChequeValues() {
		try {
			if (carteiraChequeValues == null) {
				carteiraChequeValues = carteiraFinder.findAllCheque();
			}
			return carteiraChequeValues;
		} catch (ViewException e) {
			logger.error("Erro no ListFactoryFinanc - getCarteiraChequeValues");
			return null;
		}
	}

	public void setCarteiraChequeValues(List<Carteira> carteiraChequeValues) {
		this.carteiraChequeValues = carteiraChequeValues;
	}

	public String getAnoCorrente() {
		return anoCorrente;
	}

	public void setAnoCorrente(String anoCorrente) {
		this.anoCorrente = anoCorrente;
	}

	public GrupoFinder getGrupoFinder() {
		return grupoFinder;
	}

	public void setGrupoFinder(GrupoFinder grupoFinder) {
		this.grupoFinder = grupoFinder;
	}

	public GrupoItemFinder getGrupoItemFinder() {
		return grupoItemFinder;
	}

	public void setGrupoItemFinder(GrupoItemFinder grupoItemFinder) {
		this.grupoItemFinder = grupoItemFinder;
	}

	public ModoFinder getModoFinder() {
		return modoFinder;
	}

	public void setModoFinder(ModoFinder modoFinder) {
		this.modoFinder = modoFinder;
	}

	public BandeiraCartaoFinder getBandeiraCartaoFinder() {
		return bandeiraCartaoFinder;
	}

	public void setBandeiraCartaoFinder(BandeiraCartaoFinder bandeiraCartaoFinder) {
		this.bandeiraCartaoFinder = bandeiraCartaoFinder;
	}

	public OperadoraCartaoFinder getOperadoraCartaoFinder() {
		return operadoraCartaoFinder;
	}

	public void setOperadoraCartaoFinder(OperadoraCartaoFinder operadoraCartaoFinder) {
		this.operadoraCartaoFinder = operadoraCartaoFinder;
	}

	public CarteiraFinder getCarteiraFinder() {
		return carteiraFinder;
	}

	public void setCarteiraFinder(CarteiraFinder carteiraFinder) {
		this.carteiraFinder = carteiraFinder;
	}

	public BancoFinder getBancoFinder() {
		return bancoFinder;
	}

	public void setBancoFinder(BancoFinder bancoFinder) {
		this.bancoFinder = bancoFinder;
	}

	public List<Banco> acBanco(String str) {
		try {
			// pesquisa somente os bancos utilizados (true)
			return getBancoFinder().findBy(str, true);
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao pesquisar bancos");
			return null;
		}
	}

	public List<Status> getStatusValues() {
		statusValues = Arrays.asList(Status.values());
		return statusValues;
	}

	public void setStatusValues(List<Status> statusValues) {
		this.statusValues = statusValues;
	}

	public List<Recorrencia.Frequencia> getRecorrenciaFrequenciaValues() {
		recorrenciaFrequenciaValues = Arrays.asList(Recorrencia.Frequencia.values());
		return recorrenciaFrequenciaValues;
	}

	public void setRecorrenciaFrequenciaValues(List<Recorrencia.Frequencia> recorrenciaFrequenciaValues) {
		this.recorrenciaFrequenciaValues = recorrenciaFrequenciaValues;
	}

	public List<Recorrencia.TipoRepeticao> getRecorrenciaTipoRepeticaoValues() {
		recorrenciaTipoRepeticaoValues = Arrays.asList(Recorrencia.TipoRepeticao.values());
		return recorrenciaTipoRepeticaoValues;
	}

	public void setRecorrenciaTipoRepeticaoValues(List<Recorrencia.TipoRepeticao> recorrenciaTipoRepeticaoValues) {
		this.recorrenciaTipoRepeticaoValues = recorrenciaTipoRepeticaoValues;
	}

	public List<TipoLancto> getTiposLancto() {
		tiposLancto = Arrays.asList(TipoLancto.values());
		return tiposLancto;
	}

	public void setTiposLancto(List<TipoLancto> tiposLancto) {
		this.tiposLancto = tiposLancto;
	}

	public List<TipoExtrato> getTiposExtrato() {
		tiposExtrato = Arrays.asList(TipoExtrato.values());
		return tiposExtrato;
	}

	public void setTiposExtrato(List<TipoExtrato> tiposExtrato) {
		this.tiposExtrato = tiposExtrato;
	}

	public Carteira getCarteiraDestinoPadraoTransfCaixa() {
		try {
			carteiraDestinoPadraoTransfCaixa = getCarteiraFinder().findBy("ESCRITÓRIO");
		} catch (ViewException e) {
			JSFUtils.addHandledException(e);
			logger.error(e);
		}
		return carteiraDestinoPadraoTransfCaixa;
	}

	public void setCarteiraDestinoPadraoTransfCaixa(Carteira carteiraDestinoPadraoTransfCaixa) {
		this.carteiraDestinoPadraoTransfCaixa = carteiraDestinoPadraoTransfCaixa;
	}

	public List<GrupoItem> getGrupoItemAbertosBy() {
		return getGrupoItemAbertosBy(getGrupo());
	}
	
	public List<GrupoItem> getGrupoItemAbertosBy(Grupo grupo) {
		try {
			return getGrupoItemFinder().findAllAbertosBy(grupo);
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
			return null;
		}
	}

}
