package com.bonsucesso.erp.estoque.relatorios.view;



import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.estoque.business.HistoricoPrevisaoCompraBusiness;
import com.bonsucesso.erp.estoque.business.VendaResultsBusiness;
import com.bonsucesso.erp.estoque.data.FornecedorFinder;
import com.bonsucesso.erp.estoque.data.PedidoCompraFinder;
import com.bonsucesso.erp.estoque.data.ProdutoFinder;
import com.bonsucesso.erp.estoque.data.SubdeptoFinder;
import com.bonsucesso.erp.estoque.model.Fornecedor;
import com.bonsucesso.erp.estoque.model.Subdepartamento;
import com.bonsucesso.erp.estoque.results.model.ListHistoricoPrevisaoCompra;
import com.ocabit.base.view.StoredViewInfoHandler;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;
import com.ocabit.utils.calendar.CalendarUtil;


/**
 * View para previsões de compra.
 * 
 * 
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("previsaoCompraView")
@Scope("view")
public class PrevisaoCompraView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7242454086432026430L;

	protected static Logger logger = Logger.getLogger(PrevisaoCompraView.class);

	@Autowired
	private HistoricoPrevisaoCompraBusiness historicoPrevisaoCompraBusiness;

	/**
	 * Parâmetros de entrada:
	 */

	private List<Fornecedor> fornecedores;

	private List<Fornecedor> fornecedoresSel;

	private List<Subdepartamento> subdeptos;

	private List<Subdepartamento> subdeptosSel;

	private Date dtEntrega;

	private int qtdeMesesPrever = 12;

	private Integer[] anosAnterioresConsiderar;

	// informa se o relatório deve ser agrupado por subdepto, ou exibido cada produto em separado
	private boolean detalhado = false;

	/**
	 * 'Chave'
	 */
	private String ordenarPor = "Chave";

	// Se for 'true', então a ordem é feita somente dentro dos itens da chave.
	private boolean ordenarApenasIntern = false;

	// >>>>>>>>>> Montados

	// Lista dos mesesAnos exibidos na previsão (dtBase + qtdeMesesPrever) (ex.: "nov/16, dez/16, jan/17")
	private List<Date> mesesAnosPrevisao;

	// ------------------------------------------------

	// Mantém as listas e mapas que serão usados para acessar mais facilmente pela view
	private ListHistoricoPrevisaoCompra listHPC;

	@Autowired
	private SubdeptoFinder subdeptoFinder;

	@Autowired
	private FornecedorFinder fornecedorFinder;

	@Autowired
	private ProdutoFinder produtoFinder;

	@Autowired
	private PedidoCompraFinder pedidoCompraFinder;

	private List<PrevisaoCompraRecord> listPorFornecedor;

	private List<PrevisaoCompraRecord> listPorSubdepto;

	@Autowired
	private VendaResultsBusiness vendaResultsBusiness;

	/**
	 * 'F': Fornecedor.
	 * 'S': Subdepto.
	 */
	private String tipoRel = "F";

	private Integer progress = 0;

	private List<Integer> anosAnterioresParaSelecionar;

	@Autowired
	private StoredViewInfoHandler storedViewInfoHandler;

	protected String[] camposStored = new String[] { "fornecedoresSel", "subdeptosSel", "dtEntrega",
			"ordenarPor", "ordenarApenasIntern", "detalhado",
			"qtdeMesesPrever", "anosAnterioresConsiderar" };

	@PostConstruct
	public void init() {
		try {
			getStoredViewInfoHandler().processStoredViewInfo("previsaoCompraView", this);
		} catch (ViewException e) {
			JSFUtils.addErrorMsg("Erro ao inicializar.");
			JSFUtils.addHandledException(e);
			logger.error(e);
		}
	}

	/**
	 * Método principal.
	 * 
	 */
	public void gerar() {

		if (CalendarUtil.isPassado(getDtEntrega())) {
			JSFUtils.addErrorMsg("Dt Entrega deve ser hoje ou no futuro.");
			setDtEntrega(new Date());
			// finishProgress();
			// return;
		}

		long ini = System.currentTimeMillis();

		getStoredViewInfoHandler().store("previsaoCompraView", this, camposStored);

		setListPorFornecedor(null);
		setListPorSubdepto(null);
		setMesesAnosPrevisao(null);

		try {
			// PRINCIPAL
			setListHPC(getHistoricoPrevisaoCompraBusiness().returnList(getFornecedoresSel(), getSubdeptosSel()));
		} catch (ViewException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//		switch (getTipoRel()) {
		//			case "F":
		//				gerarPorFornecedor();
		//				break;
		//			case "S":
		//				gerarPorSubdepto();
		//				break;
		//		}
		//
		//		try {
		//			ordenarEBuildMapList();
		//		} catch (ViewException e) {
		//			JSFUtils.addHandledException(e);
		//		}

		finishProgress();

		logger.debug("Total: " + (System.currentTimeMillis() - ini) / 1000.0);
		logger.debug("Finalizando...");
	}

	public void regerarTabelaHPC() {
		try {
			logger.debug("Iniciando geração dos registros na est_historico_previsao_compra");
			getHistoricoPrevisaoCompraBusiness().gerar();
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public void debugMapMesAnoBigDecimal(Map<Date, BigDecimal> map) {
		List<Date> keys = new ArrayList<>(map.keySet());
		Collections.sort(keys);
		for (Date d : keys) {
			logger.debug(new SimpleDateFormat("MM/yyyy").format(d) + " >> " + map.get(d));
		}
	}

	public void selecionarFornecedores(boolean todos) {
		if (todos) {
			getFornecedoresSel().addAll(getFornecedores());
		} else {
			setFornecedoresSel(null);
		}

	}

	public void selecionarSubdeptos(boolean todos) {
		if (todos) {
			getSubdeptosSel().addAll(getSubdeptos());
		} else {
			setSubdeptosSel(null);
		}
	}

	public void finishProgress() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			logger.error(e);
		}
		logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> PROGRESS: " + progress.intValue());
		setProgress(0);
		JSFUtils.execute("PF('dlgStatus').hide()");
		JSFUtils.execute("PF('pbAjax').cancel()");
	}

	public SubdeptoFinder getSubdeptoFinder() {
		return subdeptoFinder;
	}

	public void setSubdeptoFinder(SubdeptoFinder subdeptoFinder) {
		this.subdeptoFinder = subdeptoFinder;
	}

	public FornecedorFinder getFornecedorFinder() {
		return fornecedorFinder;
	}

	public void setFornecedorFinder(FornecedorFinder fornecedorFinder) {
		this.fornecedorFinder = fornecedorFinder;
	}

	public ProdutoFinder getProdutoFinder() {
		return produtoFinder;
	}

	public void setProdutoFinder(ProdutoFinder produtoFinder) {
		this.produtoFinder = produtoFinder;
	}

	public Date getDtEntrega() {
		return dtEntrega;
	}

	public void setDtEntrega(Date dtEntrega) {
		this.dtEntrega = dtEntrega;
	}

	public List<Fornecedor> getFornecedores() {
		if (fornecedores == null) {
			try {
				fornecedores = getFornecedorFinder().findAtuaisByTipo("ESTOQUE");
			} catch (ViewException e) {
				JSFUtils.addHandledException(e);
			}
		}
		return fornecedores;
	}

	public void setFornecedores(List<Fornecedor> fornecedores) {
		this.fornecedores = fornecedores;
	}

	public List<Fornecedor> getFornecedoresSel() {
		return fornecedoresSel;
	}

	public void setFornecedoresSel(List<Fornecedor> fornecedoresSel) {
		this.fornecedoresSel = fornecedoresSel;
	}

	public List<Subdepartamento> getSubdeptos() {
		if (subdeptos == null) {
			try {
				subdeptos = getSubdeptoFinder().findAll("codigo");
			} catch (ViewException e) {
				JSFUtils.addHandledException(e);
			}
		}
		return subdeptos;
	}

	public void setSubdeptos(List<Subdepartamento> subdeptos) {
		this.subdeptos = subdeptos;
	}

	public List<Subdepartamento> getSubdeptosSel() {
		return subdeptosSel;
	}

	public void setSubdeptosSel(List<Subdepartamento> subdeptosSel) {
		this.subdeptosSel = subdeptosSel;
	}

	public int getQtdeMesesPrever() {
		return qtdeMesesPrever;
	}

	public void setQtdeMesesPrever(int qtdeMesesPrever) {
		if (qtdeMesesPrever < 1)
			qtdeMesesPrever = 1;
		else if (qtdeMesesPrever > 12)
			qtdeMesesPrever = 12;
		this.qtdeMesesPrever = qtdeMesesPrever;
	}

	public String getTipoRel() {
		return tipoRel;
	}

	public void setTipoRel(String tipoRel) {
		this.tipoRel = tipoRel;
	}

	public Integer getProgress() {
		return progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}

	public List<Integer> getAnosAnterioresParaSelecionar() {
		if (anosAnterioresParaSelecionar == null) {
			anosAnterioresParaSelecionar = new ArrayList<Integer>();
			int anoAtual = CalendarUtil.getCalendar(new Date()).get(Calendar.YEAR);
			// FIXME: como só importei dados de Jan/2014 pra frente, deixei fixo aqui.
			for (int i = 2014; i < anoAtual; i++) {
				anosAnterioresParaSelecionar.add(i);
			}
		}
		return anosAnterioresParaSelecionar;
	}

	public void setAnosAnterioresParaSelecionar(List<Integer> anosAnterioresParaSelecionar) {
		this.anosAnterioresParaSelecionar = anosAnterioresParaSelecionar;
	}

	public VendaResultsBusiness getVendaResultsBusiness() {
		return vendaResultsBusiness;
	}

	public void setVendaResultsBusiness(VendaResultsBusiness vendaResultsBusiness) {
		this.vendaResultsBusiness = vendaResultsBusiness;
	}

	public Integer[] getAnosAnterioresConsiderar() {
		return anosAnterioresConsiderar;
	}

	public void setAnosAnterioresConsiderar(Integer[] anosAnterioresConsiderar) {
		this.anosAnterioresConsiderar = anosAnterioresConsiderar;
	}

	public List<Date> getMesesAnosPrevisao() {
		if (mesesAnosPrevisao == null) {
			Date dt = new Date();
			// Monta a lista com base nos parâmetros de entrada
			mesesAnosPrevisao = new ArrayList<Date>();
			for (int i = 0; i < getQtdeMesesPrever(); i++) {
				mesesAnosPrevisao.add(CalendarUtil.primeiroDiaNoMesAno(CalendarUtil.incMes(dt, i)));
			}
		}
		return mesesAnosPrevisao;
	}

	public void setMesesAnosPrevisao(List<Date> mesesAnosPrevisao) {
		this.mesesAnosPrevisao = mesesAnosPrevisao;
	}

	public PedidoCompraFinder getPedidoCompraFinder() {
		return pedidoCompraFinder;
	}

	public void setPedidoCompraFinder(PedidoCompraFinder pedidoCompraFinder) {
		this.pedidoCompraFinder = pedidoCompraFinder;
	}

	public StoredViewInfoHandler getStoredViewInfoHandler() {
		return storedViewInfoHandler;
	}

	public void setStoredViewInfoHandler(StoredViewInfoHandler storedViewInfoHandler) {
		this.storedViewInfoHandler = storedViewInfoHandler;
	}

	public boolean isDetalhado() {
		return detalhado;
	}

	public void setDetalhado(boolean detalhado) {
		this.detalhado = detalhado;
	}

	public String getOrdenarPor() {
		return ordenarPor;
	}

	public void setOrdenarPor(String ordenarPor) {
		this.ordenarPor = ordenarPor;
	}

	public boolean isOrdenarApenasIntern() {
		return ordenarApenasIntern;
	}

	public void setOrdenarApenasIntern(boolean ordenarApenasIntern) {
		this.ordenarApenasIntern = ordenarApenasIntern;
	}

	public List<PrevisaoCompraRecord> getListPorFornecedor() {
		if (listPorFornecedor == null) {
			listPorFornecedor = new ArrayList<PrevisaoCompraRecord>();
		}
		return listPorFornecedor;
	}

	public void setListPorFornecedor(List<PrevisaoCompraRecord> listPorFornecedor) {
		this.listPorFornecedor = listPorFornecedor;
	}

	public List<PrevisaoCompraRecord> getListPorSubdepto() {
		if (listPorSubdepto == null) {
			listPorSubdepto = new ArrayList<PrevisaoCompraRecord>();
		}
		return listPorSubdepto;
	}

	public void setListPorSubdepto(List<PrevisaoCompraRecord> listPorSubdepto) {
		this.listPorSubdepto = listPorSubdepto;
	}

	public HistoricoPrevisaoCompraBusiness getHistoricoPrevisaoCompraBusiness() {
		return historicoPrevisaoCompraBusiness;
	}

	public void setHistoricoPrevisaoCompraBusiness(HistoricoPrevisaoCompraBusiness gerarHistoricoPrevisaoCompra) {
		this.historicoPrevisaoCompraBusiness = gerarHistoricoPrevisaoCompra;
	}

	public ListHistoricoPrevisaoCompra getListHPC() {
		return listHPC;
	}

	public void setListHPC(ListHistoricoPrevisaoCompra listHPC) {
		this.listHPC = listHPC;
	}

}
