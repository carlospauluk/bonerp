package com.bonsucesso.erp.financeiro.view;



import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.financeiro.business.MovimentacaoBusiness;
import com.bonsucesso.erp.financeiro.data.RegistroConferenciaFinder;
import com.bonsucesso.erp.financeiro.model.Carteira;
import com.bonsucesso.erp.financeiro.model.Categoria;
import com.bonsucesso.erp.financeiro.model.GrupoItem;
import com.bonsucesso.erp.financeiro.model.Modo;
import com.bonsucesso.erp.financeiro.model.Movimentacao;
import com.bonsucesso.erp.financeiro.model.OperadoraCartao;
import com.bonsucesso.erp.financeiro.model.RegistroConferencia;
import com.bonsucesso.erp.vendas.data.VendaFinder;
import com.ocabit.base.view.StoredViewInfoHandler;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;
import com.ocabit.utils.calendar.CalendarUtil;
import com.ocabit.utils.currency.CurrencyUtils;


@Component("resumoConferenciaGeralView")
@Scope("view")
public class ResumoConferenciaGeralView implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -7141845783224987516L;

	protected static Logger logger = Logger.getLogger(ResumoConferenciaGeralView.class);

	@Autowired
	private MovimentacaoBusiness movimentacaoBusiness;

	@Autowired
	private VendaFinder vendaFinder;

	@Autowired
	private RegistroConferenciaFinder registroConferenciaFinder;

	private Date dtIni, dtFim;

	@Autowired
	private StoredViewInfoHandler storedViewInfoHandler;

	private String[] camposStored = new String[] { "dtIni", "dtFim" };

	private List<Movimentacao> errosTransf;

	@PostConstruct
	public void init() {
		setDtIni(CalendarUtil.primeiroDiaNoMesAno(new Date()));
		setDtFim(CalendarUtil.ultimoDiaNoMesAno(new Date()));

		try {
			getStoredViewInfoHandler().processStoredViewInfo("resumoConferenciaGeralView", this);
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addErrorMsg("Erro ao restaurar página.");
		}
	}

	public void store() {
		getStoredViewInfoHandler().store("resumoConferenciaGeralView", this, camposStored);
	}

	public ItemResumo addLinhaRegistroConferencia(String rcDescricao, BigDecimal totalComparado) throws ViewException {
		totalComparado = totalComparado != null ? totalComparado : new BigDecimal("0.00");
		RegistroConferencia rc = getRegistroConferenciaFinder().findBy(rcDescricao, getDtFim());
		// Se não tem o registroConferencia lançado...
		if (rc == null) {
			return new ItemResumo(rcDescricao + " (INFORMADO)", BigDecimal.ZERO, chooseIcon(totalComparado, rc), "");
			// return null;
		} else {
			BigDecimal rcValor;
			if (rc.getValor() != null) {
				rcValor = rc.getValor();
			} else {
				rcValor = new BigDecimal("0.00");
			}
			BigDecimal diferenca = rcValor.subtract(totalComparado);
			return new ItemResumo(rcDescricao + " (INFORMADO)", rcValor, chooseIcon(totalComparado, rc), rc.getObs()
					+ " (DIF: "
					+ CurrencyUtils.asString(diferenca) + ")");
		}
	}

	/**
	 * Resumo de DÉBITOS CIELO.
	 *
	 * @return
	 * @throws ViewException
	 */
	public List<ItemResumo> gerarCaixas() throws ViewException {
		List<ItemResumo> l = new ArrayList<ItemResumo>();

		// ------- CAIXA A VISTA (BONSUCESSO) -------
		Carteira caixaAVista = getMovimentacaoBusiness().getCarteiraFinder().findBy("CAIXA A VISTA");
		Categoria c101 = getMovimentacaoBusiness().getCategoriaFinder().findBy(101l);
		Categoria c102 = getMovimentacaoBusiness().getCategoriaFinder().findBy(102l);

		BigDecimal tCaixaAvista101 = CurrencyUtils.nullToZero(getMovimentacaoBusiness().getMovimentacaoFinder()
				.findTotal(caixaAVista, c101, getDtIni(), getDtFim()));
		l.add(new ItemResumo("TOTAL ENTRADAS (1.01) - CAIXA A VISTA (BONSUCESSO)", tCaixaAvista101));

		BigDecimal tCaixaAvista102 = CurrencyUtils.nullToZero(getMovimentacaoBusiness().getMovimentacaoFinder()
				.findTotal(caixaAVista, c102, getDtIni(), getDtFim()));
		l.add(new ItemResumo("TOTAL ENTRADAS (1.02) - CAIXA A VISTA (BONSUCESSO)", tCaixaAvista102));

		// Linha para Registro de Conferência
		l.add(addLinhaRegistroConferencia("TOTAL CAIXA A VISTA (BONSUCESSO)", tCaixaAvista101));

		Categoria cAjustesDeCaixaPos = getMovimentacaoBusiness().getCategoriaFinder().findBy(151l);
		Categoria cAjustesDeCaixaNeg = getMovimentacaoBusiness().getCategoriaFinder().findBy(251l);
		BigDecimal tAjustesCaixaAvistaPos = CurrencyUtils.nullToZero(getMovimentacaoBusiness().getMovimentacaoFinder()
				.findTotal(caixaAVista, cAjustesDeCaixaPos, getDtIni(), getDtFim()));
		BigDecimal tAjustesCaixaAvistaNeg = CurrencyUtils.nullToZero(getMovimentacaoBusiness().getMovimentacaoFinder()
				.findTotal(caixaAVista, cAjustesDeCaixaNeg, getDtIni(), getDtFim()));

		String obs = CurrencyUtils.asString(tAjustesCaixaAvistaPos) + " (+) - "
				+ CurrencyUtils.asString(tAjustesCaixaAvistaNeg) + " (-)";

		BigDecimal tAjustesAvista = CurrencyUtils.nullToZero(tAjustesCaixaAvistaPos.subtract(tAjustesCaixaAvistaNeg));
		String icone = tAjustesAvista.compareTo(BigDecimal.ZERO) == 0 ? "checked" : "attention";
		l.add(new ItemResumo("TOTAL AJUSTES - CAIXA A VISTA (BONSUCESSO)", tAjustesAvista, icone, obs));

		BigDecimal tVendasEKT = CurrencyUtils
				.nullToZero(getVendaFinder().findTotalAVistaEKT(getDtIni(), getDtFim(), true));
		if (tVendasEKT != null) {
			BigDecimal difTVendasEKT = CurrencyUtils.nullToZero(tCaixaAvista101.subtract(tVendasEKT));
			icone = tVendasEKT.equals(tCaixaAvista101) ? "checked" : "attention";
			l.add(new ItemResumo("TOTAL EKT - CAIXA A VISTA (BONSUCESSO)", tVendasEKT, icone, "(DIF: "
					+ CurrencyUtils.asString(difTVendasEKT) + ")"));
		}

		l.add(new ItemResumo("", null));

		// ------- CAIXA A VISTA (IPÊ) ------- 

		if (CalendarUtil.dataMenorIgualQueData(new Date(), CalendarUtil.getDate(01, 01, 2017))) {

			Carteira caixaAVistaIPE = getMovimentacaoBusiness().getCarteiraFinder().findBy("CAIXA A VISTA - IPE");

			BigDecimal tCaixaAvistaIPE = CurrencyUtils.nullToZero(getMovimentacaoBusiness().getMovimentacaoFinder()
					.findTotal(caixaAVistaIPE, c101, getDtIni(), getDtFim()));

			l.add(new ItemResumo("TOTAL ENTRADAS (1.01) - CAIXA A VISTA (IPÊ)", tCaixaAvistaIPE));

			// Linha para Registro de Conferência
			l.add(addLinhaRegistroConferencia("TOTAL CAIXA A VISTA (IPÊ)", tCaixaAvistaIPE));

			BigDecimal tAjustesCaixaAvistaPosIPE = CurrencyUtils
					.nullToZero(getMovimentacaoBusiness().getMovimentacaoFinder()
							.findTotal(caixaAVistaIPE, cAjustesDeCaixaPos, getDtIni(), getDtFim()));
			BigDecimal tAjustesCaixaAvistaNegIPE = CurrencyUtils
					.nullToZero(getMovimentacaoBusiness().getMovimentacaoFinder()
							.findTotal(caixaAVistaIPE, cAjustesDeCaixaNeg, getDtIni(), getDtFim()));

			String obsIPE = CurrencyUtils.asString(tAjustesCaixaAvistaPosIPE) + " (+) - "
					+ CurrencyUtils.asString(tAjustesCaixaAvistaNegIPE) + " (-)";

			BigDecimal tAjustesAvistaIPE = CurrencyUtils
					.nullToZero(tAjustesCaixaAvistaPosIPE.subtract(tAjustesCaixaAvistaNegIPE));
			String iconeIPE = tAjustesAvistaIPE.compareTo(BigDecimal.ZERO) == 0 ? "checked" : "attention";
			l.add(new ItemResumo("TOTAL AJUSTES - CAIXA A VISTA (IPÊ)", tAjustesAvistaIPE, iconeIPE, obsIPE));

			BigDecimal tVendasEKTIPE = CurrencyUtils
					.nullToZero(getVendaFinder().findTotalAVistaEKT(getDtIni(), getDtFim(), false));
			if (tVendasEKTIPE != null) {
				BigDecimal difTVendasEKT = CurrencyUtils.nullToZero(tCaixaAvistaIPE.subtract(tVendasEKTIPE));
				iconeIPE = tVendasEKTIPE.equals(tCaixaAvistaIPE) ? "checked" : "attention";
				l.add(new ItemResumo("TOTAL EKT - CAIXA A VISTA (IPÊ)", tVendasEKTIPE, iconeIPE, "(DIF: "
						+ CurrencyUtils.asString(difTVendasEKT) + ")"));
			}

			l.add(new ItemResumo("", null));
		}

		// ------- CAIXA A PRAZO -------

		Carteira caixaAPrazo = getMovimentacaoBusiness().getCarteiraFinder().findBy("CAIXA A PRAZO");

		BigDecimal tCaixaAprazo = CurrencyUtils.nullToZero(getMovimentacaoBusiness().getMovimentacaoFinder()
				.findTotal(caixaAPrazo, c101, getDtIni(), getDtFim()));

		BigDecimal tCaixaAprazoExternas = CurrencyUtils.nullToZero(getMovimentacaoBusiness().getMovimentacaoFinder()
				.findTotal(caixaAPrazo, c102, getDtIni(), getDtFim()));

		l.add(new ItemResumo("TOTAL ENTRADAS (1.01) - CAIXA A PRAZO", tCaixaAprazo));

		// Linha para Registro de Conferência
		l.add(addLinhaRegistroConferencia("TOTAL CAIXA A PRAZO - SERVIPA", tCaixaAprazo));

		l.add(new ItemResumo("TOTAL ENTRADAS (1.02) - CAIXA A PRAZO", tCaixaAprazoExternas));

		l.add(addLinhaRegistroConferencia("TOTAL CAIXA A PRAZO - OUTROS RECEB", tCaixaAprazoExternas));

		BigDecimal tAjustesCaixaAprazoPos = CurrencyUtils.nullToZero(getMovimentacaoBusiness().getMovimentacaoFinder()
				.findTotal(caixaAPrazo, cAjustesDeCaixaPos, getDtIni(), getDtFim()));
		BigDecimal tAjustesCaixaAprazoNeg = CurrencyUtils.nullToZero(getMovimentacaoBusiness().getMovimentacaoFinder()
				.findTotal(caixaAPrazo, cAjustesDeCaixaNeg, getDtIni(), getDtFim()));

		String obsAjustesCaixaPrazo = CurrencyUtils.asString(tAjustesCaixaAprazoPos) + " (+) - "
				+ CurrencyUtils.asString(tAjustesCaixaAprazoNeg) + " (-)";

		BigDecimal tAjustesAprazo = CurrencyUtils.nullToZero(tAjustesCaixaAprazoPos.subtract(tAjustesCaixaAprazoNeg));
		icone = tAjustesAprazo.equals(BigDecimal.ZERO) ? "checked" : "attention";

		l.add(new ItemResumo("TOTAL AJUSTES - CAIXA A PRAZO", tAjustesAprazo, icone, obsAjustesCaixaPrazo));

		return l;
	}

	/**
	 * Resumo de DÉBITOS REDECARD.
	 *
	 * @return
	 * @throws ViewException
	 */
	public List<ItemResumo> gerarResumoDebitosRedecard() throws ViewException {
		List<ItemResumo> l = new ArrayList<ItemResumo>();

		Carteira caixaAVista = getMovimentacaoBusiness().getCarteiraFinder().findBy("CAIXA A VISTA");
		Carteira carteiraRedecard = getMovimentacaoBusiness().getCarteiraFinder().findBy("RDCARD 003457796");

		Categoria c101 = getMovimentacaoBusiness().getCategoriaFinder().findBy(101l);
		Categoria c199 = getMovimentacaoBusiness().getCategoriaFinder().findBy(199l);

		OperadoraCartao operadoraRedecard = getMovimentacaoBusiness().getOperadoraCartaoFinder().findBy("REDECARD");

		Modo modoDebito = getMovimentacaoBusiness().getModoFinder().findBy("RECEB. CARTÃO DÉBITO");

		BigDecimal tCaixaAvista = CurrencyUtils.nullToZero(getMovimentacaoBusiness().getMovimentacaoFinder()
				.findTotal(caixaAVista, c101, operadoraRedecard, modoDebito, getDtIni(), getDtFim()));

		BigDecimal tCarteiraRedeCard = CurrencyUtils.nullToZero(getMovimentacaoBusiness().getMovimentacaoFinder()
				.findTotal(carteiraRedecard, c199, operadoraRedecard, modoDebito, getDtIni(), getDtFim()));

		String icone = null;
		if (tCaixaAvista != null) {
			icone = tCaixaAvista.compareTo(tCarteiraRedeCard) == 0 ? "checked" : "cancel";
		}

		l.add(new ItemResumo("TOTAL - CAIXA A VISTA", tCaixaAvista, icone));
		l.add(new ItemResumo("TOTAL - REDECARD (DÉBITOS)", tCarteiraRedeCard, icone));

		ItemResumo informado = addLinhaRegistroConferencia("TOTAL RDCARD - DÉBITOS", tCarteiraRedeCard);
		if (informado.getValor().doubleValue() == 0.0 || informado.getValor() == null || informado.getValor() == null) {
			return null;
		}
		l.add(informado);

		BigDecimal taxa = CurrencyUtils.nullToZero(getMovimentacaoBusiness()
				.calcularTaxaCartao(carteiraRedecard, true, tCarteiraRedeCard, getDtIni(), getDtFim()));

		icone = "cancel";
		if (taxa != null) {
			if (tCarteiraRedeCard.doubleValue() == 0.0 && taxa.doubleValue() == 0.0) {
				icone = "checked";
			} else {
				icone = taxa.doubleValue() > 0.01 ? "checked" : "cancel";
			}
		}
		l.add(new ItemResumo("TAXA", taxa, icone));

		return l;
	}

	/**
	 * Resumo de DÉBITOS CIELO CTPL.
	 *
	 * @return
	 * @throws ViewException
	 */
	public List<ItemResumo> gerarResumoDebitosCielo() throws ViewException {
		List<ItemResumo> l = new ArrayList<ItemResumo>();

		Categoria c101 = getMovimentacaoBusiness().getCategoriaFinder().findBy(101l);
		Categoria c102 = getMovimentacaoBusiness().getCategoriaFinder().findBy(102l);
		Categoria c199 = getMovimentacaoBusiness().getCategoriaFinder().findBy(199l);

		OperadoraCartao operadoraCielo = getMovimentacaoBusiness().getOperadoraCartaoFinder().findBy("CIELO");
		Modo modoDebito = getMovimentacaoBusiness().getModoFinder().findBy("RECEB. CARTÃO DÉBITO");

		Carteira carteiraCielo = getMovimentacaoBusiness().getCarteiraFinder().findBy("CIELO 1005923369");

		Carteira caixaAVistaBonsucesso = getMovimentacaoBusiness().getCarteiraFinder().findBy("CAIXA A VISTA");
		Carteira caixaAVistaIPE = getMovimentacaoBusiness().getCarteiraFinder().findBy("CAIXA A VISTA - IPE");

		BigDecimal tCarteiraCielo = CurrencyUtils.nullToZero(getMovimentacaoBusiness().getMovimentacaoFinder()
				.findTotal(carteiraCielo, c199, operadoraCielo, modoDebito, getDtIni(), getDtFim()));

		BigDecimal tCaixaAvistaBonsucesso101 = CurrencyUtils
				.nullToZero(getMovimentacaoBusiness().getMovimentacaoFinder()
						.findTotal(caixaAVistaBonsucesso, c101, operadoraCielo, modoDebito, getDtIni(), getDtFim()));

		BigDecimal tCaixaAvistaBonsucesso102 = CurrencyUtils
				.nullToZero(getMovimentacaoBusiness().getMovimentacaoFinder()
						.findTotal(caixaAVistaBonsucesso, c102, operadoraCielo, modoDebito, getDtIni(), getDtFim()));

		BigDecimal tCaixaAvistaBonsucesso = CurrencyUtils
				.nullToZero(tCaixaAvistaBonsucesso101.add(tCaixaAvistaBonsucesso102));

		BigDecimal tCaixaAvistaIPE101 = CurrencyUtils.nullToZero(getMovimentacaoBusiness().getMovimentacaoFinder()
				.findTotal(caixaAVistaIPE, c101, operadoraCielo, modoDebito, getDtIni(), getDtFim()));

		BigDecimal tCaixaAvistaIPE102 = CurrencyUtils.nullToZero(getMovimentacaoBusiness().getMovimentacaoFinder()
				.findTotal(caixaAVistaIPE, c102, operadoraCielo, modoDebito, getDtIni(), getDtFim()));

		BigDecimal tCaixaAvistaIPE = CurrencyUtils.nullToZero(tCaixaAvistaIPE101.add(tCaixaAvistaIPE102));

		BigDecimal tCaixaAvistaJuntos = tCaixaAvistaBonsucesso.add(tCaixaAvistaIPE);

		String icone = null;
		if (tCaixaAvistaJuntos != null) {
			icone = tCarteiraCielo.compareTo(tCaixaAvistaJuntos) == 0 ? "checked" : "cancel";
		}

		l.add(new ItemResumo("TOTAL - CIELO (DÉBITOS)", tCarteiraCielo, icone));

		l.add(new ItemResumo("TOTAL - CAIXA A VISTA (BONSUCESSO)", tCaixaAvistaBonsucesso, null));
		l.add(new ItemResumo("TOTAL - CAIXA A VISTA (IPÊ)", tCaixaAvistaIPE, null));
		l.add(new ItemResumo("TOTAL - CAIXA A VISTA (SOMA)", tCaixaAvistaJuntos, null));

		ItemResumo informado = addLinhaRegistroConferencia("TOTAL CIELO CTPL - DÉBITOS", tCaixaAvistaJuntos);
		if (informado.getValor().doubleValue() == 0.0 || informado.getValor() == null || informado.getValor() == null) {
			return null;
		}
		l.add(informado);

		BigDecimal taxa = CurrencyUtils.nullToZero(getMovimentacaoBusiness()
				.calcularTaxaCartao(carteiraCielo, true, tCarteiraCielo, getDtIni(), getDtFim()));

		icone = "cancel";
		if (taxa != null) {
			if (tCarteiraCielo.doubleValue() == 0.0 && taxa.doubleValue() == 0.0) {
				icone = "checked";
			} else {
				icone = taxa.doubleValue() > 0.01 ? "checked" : "cancel";
			}
		}
		l.add(new ItemResumo("TAXA", taxa, icone));

		return l;
	}

	/**
	 * Resumo de DÉBITOS CIELO MSP.
	 *
	 * @return
	 * @throws ViewException
	 */
	public List<ItemResumo> gerarResumoDebitosCieloMSP() throws ViewException {
		List<ItemResumo> l = new ArrayList<ItemResumo>();

		Carteira caixaAVista = getMovimentacaoBusiness().getCarteiraFinder().findBy("CAIXA A VISTA");
		Carteira carteiraCieloMSP = getMovimentacaoBusiness().getCarteiraFinder().findBy("CIELO MSP 1037223052");

		Categoria c101 = getMovimentacaoBusiness().getCategoriaFinder().findBy(101l);
		Categoria c102 = getMovimentacaoBusiness().getCategoriaFinder().findBy(102l);
		Categoria c199 = getMovimentacaoBusiness().getCategoriaFinder().findBy(199l);

		OperadoraCartao operadoraCieloMSP = getMovimentacaoBusiness().getOperadoraCartaoFinder().findBy("CIELO MSP");

		Modo modoDebito = getMovimentacaoBusiness().getModoFinder().findBy("RECEB. CARTÃO DÉBITO");

		BigDecimal tCaixaAvista101 = CurrencyUtils.nullToZero(getMovimentacaoBusiness().getMovimentacaoFinder()
				.findTotal(caixaAVista, c101, operadoraCieloMSP, modoDebito, getDtIni(), getDtFim()));

		BigDecimal tCaixaAvista102 = CurrencyUtils.nullToZero(getMovimentacaoBusiness().getMovimentacaoFinder()
				.findTotal(caixaAVista, c102, operadoraCieloMSP, modoDebito, getDtIni(), getDtFim()));

		BigDecimal tCaixaAvista = CurrencyUtils.nullToZero(tCaixaAvista101.add(tCaixaAvista102));

		BigDecimal tCarteiraCielo = CurrencyUtils.nullToZero(getMovimentacaoBusiness().getMovimentacaoFinder()
				.findTotal(carteiraCieloMSP, c199, operadoraCieloMSP, modoDebito, getDtIni(), getDtFim()));

		String icone = null;
		if (tCaixaAvista != null) {
			icone = tCaixaAvista.compareTo(tCarteiraCielo) == 0 ? "checked" : "cancel";
		}

		l.add(new ItemResumo("TOTAL - CAIXA A VISTA", tCaixaAvista, icone));
		l.add(new ItemResumo("TOTAL - CIELO MSP", tCarteiraCielo, icone));

		ItemResumo informado = addLinhaRegistroConferencia("TOTAL CIELO MSP - DÉBITOS", tCarteiraCielo);
		if (informado.getValor().doubleValue() == 0.0 || informado.getValor() == null) {
			return null;
		}
		l.add(informado);

		BigDecimal taxa = CurrencyUtils.nullToZero(getMovimentacaoBusiness()
				.calcularTaxaCartao(carteiraCieloMSP, true, tCarteiraCielo, getDtIni(), getDtFim()));

		icone = "cancel";
		if (taxa != null) {
			if (tCarteiraCielo.doubleValue() == 0.0 && taxa.doubleValue() == 0.0) {
				icone = "checked";
			} else {
				icone = taxa.doubleValue() > 0.01 ? "checked" : "cancel";
			}
		}
		l.add(new ItemResumo("TAXA", taxa, icone));

		return l;
	}

	/**
	 * Resumo de CRÉDITOS REDECARD.
	 *
	 * @return
	 * @throws ViewException
	 */
	public List<ItemResumo> gerarResumoCreditosRedecard() throws ViewException {
		List<ItemResumo> l = new ArrayList<ItemResumo>();

		Categoria c101 = getMovimentacaoBusiness().getCategoriaFinder().findBy(101l);

		Carteira carteiraRedecard = getMovimentacaoBusiness().getCarteiraFinder().findBy("RDCARD 003457796");

		Modo modoCredito = getMovimentacaoBusiness().getModoFinder().findBy("RECEB. CARTÃO CRÉDITO");

		BigDecimal tCreditosRedecard = CurrencyUtils.nullToZero(getMovimentacaoBusiness().getMovimentacaoFinder()
				.findTotal(carteiraRedecard, c101, null, modoCredito, getDtIni(), getDtFim()));

		l.add(new ItemResumo("TOTAL - REDECARD (CRÉDITOS)", tCreditosRedecard));

		ItemResumo informado = addLinhaRegistroConferencia("TOTAL RDCARD - CRÉDITOS", tCreditosRedecard);
		if (informado.getValor().doubleValue() == 0.0 || informado.getValor() == null) {
			return null;
		}
		l.add(informado);

		BigDecimal taxa = CurrencyUtils.nullToZero(getMovimentacaoBusiness()
				.calcularTaxaCartao(carteiraRedecard, false, tCreditosRedecard, getDtIni(), getDtFim()));

		String icone = "cancel";
		if (taxa != null) {
			if (tCreditosRedecard.doubleValue() == 0.0 && taxa.doubleValue() == 0.0) {
				icone = "checked";
			} else {
				icone = taxa.doubleValue() > 0.01 ? "checked" : "cancel";
			}
		}
		l.add(new ItemResumo("TAXA", taxa, icone));

		return l;
	}

	/**
	 * Resumo de CRÉDITOS CIELO CTPL.
	 *
	 * @return
	 * @throws ViewException
	 */
	public List<ItemResumo> gerarResumoCreditosCielo() throws ViewException {
		List<ItemResumo> l = new ArrayList<ItemResumo>();

		Categoria c101 = getMovimentacaoBusiness().getCategoriaFinder().findBy(101l);

		Carteira carteiraCielo = getMovimentacaoBusiness().getCarteiraFinder().findBy("CIELO 1005923369");

		Modo modoCredito = getMovimentacaoBusiness().getModoFinder().findBy("RECEB. CARTÃO CRÉDITO");

		BigDecimal tCreditosCielo = CurrencyUtils.nullToZero(getMovimentacaoBusiness().getMovimentacaoFinder()
				.findTotal(carteiraCielo, c101, null, modoCredito, getDtIni(), getDtFim()));

		l.add(new ItemResumo("TOTAL - CIELO (CRÉDITOS)", tCreditosCielo));

		ItemResumo informado = addLinhaRegistroConferencia("TOTAL CIELO CTPL - CRÉDITOS", tCreditosCielo);
		if (informado.getValor().doubleValue() == 0.0 || informado.getValor() == null) {
			return null;
		}
		l.add(informado);

		BigDecimal taxa = CurrencyUtils.nullToZero(getMovimentacaoBusiness()
				.calcularTaxaCartao(carteiraCielo, false, tCreditosCielo, getDtIni(), getDtFim()));

		String icone = "cancel";
		if (taxa != null) {
			if (tCreditosCielo.doubleValue() == 0.0 && taxa.doubleValue() == 0.0) {
				icone = "checked";
			} else {
				icone = taxa.doubleValue() > 0.01 ? "checked" : "cancel";
			}
		}
		l.add(new ItemResumo("TAXA", taxa, icone));

		return l;
	}

	/**
	 * Resumo de CRÉDITOS CIELO MSP.
	 *
	 * @return
	 * @throws ViewException
	 */
	public List<ItemResumo> gerarResumoCreditosCieloMSP() throws ViewException {
		List<ItemResumo> l = new ArrayList<ItemResumo>();

		Categoria c101 = getMovimentacaoBusiness().getCategoriaFinder().findBy(101l);

		Carteira carteiraCieloMSP = getMovimentacaoBusiness().getCarteiraFinder().findBy("CIELO MSP 1037223052");

		Modo modoCredito = getMovimentacaoBusiness().getModoFinder().findBy("RECEB. CARTÃO CRÉDITO");

		BigDecimal tCreditosCieloMSP = CurrencyUtils.nullToZero(getMovimentacaoBusiness().getMovimentacaoFinder()
				.findTotal(carteiraCieloMSP, c101, null, modoCredito, getDtIni(), getDtFim()));

		l.add(new ItemResumo("TOTAL - CIELO MSP (CRÉDITOS)", tCreditosCieloMSP));

		ItemResumo informado = addLinhaRegistroConferencia("TOTAL CIELO MSP - CRÉDITOS", tCreditosCieloMSP);
		if (informado.getValor().doubleValue() == 0.0 || informado.getValor() == null) {
			return null;
		}
		l.add(informado);

		BigDecimal taxa = CurrencyUtils.nullToZero(getMovimentacaoBusiness()
				.calcularTaxaCartao(carteiraCieloMSP, false, tCreditosCieloMSP, getDtIni(), getDtFim()));

		String icone = "cancel";
		if (taxa != null) {
			if (tCreditosCieloMSP.doubleValue() == 0.0 && taxa.doubleValue() == 0.0) {
				icone = "checked";
			} else {
				icone = taxa.doubleValue() > 0.01 ? "checked" : "cancel";
			}
		}
		l.add(new ItemResumo("TAXA", taxa, icone));

		return l;
	}

	/**
	 * Resumo de CRÉDITOS PAGSEGURO MODERNINHA IPÊ.
	 *
	 * @return
	 * @throws ViewException
	 */
	public List<ItemResumo> gerarResumoCreditosPagseguroModerninhaIpe() throws ViewException {
		List<ItemResumo> l = new ArrayList<ItemResumo>();

		Categoria c101 = getMovimentacaoBusiness().getCategoriaFinder().findBy(101l);
		Categoria c102 = getMovimentacaoBusiness().getCategoriaFinder().findBy(102l);

		Carteira carteiraPagseguro = getMovimentacaoBusiness().getCarteiraFinder().findBy(33);

		Modo modoCredito = getMovimentacaoBusiness().getModoFinder().findBy("RECEB. CARTÃO CRÉDITO");

		BigDecimal tCreditosPagseguro101 = CurrencyUtils.nullToZero(getMovimentacaoBusiness().getMovimentacaoFinder()
				.findTotal(carteiraPagseguro, c101, null, modoCredito, getDtIni(), getDtFim()));
		BigDecimal tCreditosPagseguro102 = CurrencyUtils.nullToZero(getMovimentacaoBusiness().getMovimentacaoFinder()
				.findTotal(carteiraPagseguro, c102, null, modoCredito, getDtIni(), getDtFim()));

		BigDecimal tCreditos = tCreditosPagseguro101.add(tCreditosPagseguro102);

		l.add(new ItemResumo("TOTAL PAGSEGURO MODERNINHA IPÊ - CRÉDITOS", tCreditos));

		ItemResumo informado = addLinhaRegistroConferencia("TOTAL PAGSEGURO MODERNINHA IPÊ - CRÉDITOS", tCreditos);
		if (informado.getValor().doubleValue() == 0.0 || informado.getValor() == null) {
			return null;
		}
		l.add(informado);

		BigDecimal taxa = CurrencyUtils.nullToZero(getMovimentacaoBusiness()
				.calcularTaxaCartao(carteiraPagseguro, false, tCreditos, getDtIni(), getDtFim()));

		String icone = "cancel";
		if (taxa != null) {
			if (tCreditos.doubleValue() == 0.0 && taxa.doubleValue() == 0.0) {
				icone = "checked";
			} else {
				icone = taxa.doubleValue() > 0.01 ? "checked" : "cancel";
			}
		}
		l.add(new ItemResumo("TAXA", taxa, icone));

		return l;
	}

	/**
	 * Resumo de DÉBITOS PAGSEGURO MODERNINHA IPÊ.
	 *
	 * @return
	 * @throws ViewException
	 */
	public List<ItemResumo> gerarResumoDebitosPagseguroModerninhaIpe() throws ViewException {
		List<ItemResumo> l = new ArrayList<ItemResumo>();

		Categoria c101 = getMovimentacaoBusiness().getCategoriaFinder().findBy(101l);
		Categoria c102 = getMovimentacaoBusiness().getCategoriaFinder().findBy(102l);

		Carteira carteiraPagseguro = getMovimentacaoBusiness().getCarteiraFinder().findBy(33);

		Modo modoCredito = getMovimentacaoBusiness().getModoFinder().findBy("RECEB. CARTÃO DÉBITO");

		BigDecimal tCreditosPagseguro101 = CurrencyUtils.nullToZero(getMovimentacaoBusiness().getMovimentacaoFinder()
				.findTotal(carteiraPagseguro, c101, null, modoCredito, getDtIni(), getDtFim()));
		BigDecimal tCreditosPagseguro102 = CurrencyUtils.nullToZero(getMovimentacaoBusiness().getMovimentacaoFinder()
				.findTotal(carteiraPagseguro, c102, null, modoCredito, getDtIni(), getDtFim()));

		BigDecimal tCreditos = tCreditosPagseguro101.add(tCreditosPagseguro102);

		l.add(new ItemResumo("TOTAL PAGSEGURO MODERNINHA IPÊ - DÉBITOS", tCreditos));

		ItemResumo informado = addLinhaRegistroConferencia("TOTAL PAGSEGURO MODERNINHA IPÊ - DÉBITOS", tCreditos);
		if (informado.getValor().doubleValue() == 0.0 || informado.getValor() == null) {
			return null;
		}
		l.add(informado);

		BigDecimal taxa = CurrencyUtils.nullToZero(getMovimentacaoBusiness()
				.calcularTaxaCartao(carteiraPagseguro, true, tCreditos, getDtIni(), getDtFim()));

		String icone = "cancel";
		if (taxa != null) {
			if (tCreditos.doubleValue() == 0.0 && taxa.doubleValue() == 0.0) {
				icone = "checked";
			} else {
				icone = taxa.doubleValue() > 0.01 ? "checked" : "cancel";
			}
		}
		l.add(new ItemResumo("TAXA", taxa, icone));

		return l;
	}

	/**
	 * Resumo de CRÉDITOS STONE
	 *
	 * @return
	 * @throws ViewException
	 */
	public List<ItemResumo> gerarResumoCreditosStone() throws ViewException {
		List<ItemResumo> l = new ArrayList<ItemResumo>();

		Categoria c101 = getMovimentacaoBusiness().getCategoriaFinder().findBy(101l);

		Carteira carteiraStone = getMovimentacaoBusiness().getCarteiraFinder().findBy("STONE");

		Modo modoCredito = getMovimentacaoBusiness().getModoFinder().findBy("RECEB. CARTÃO CRÉDITO");

		BigDecimal tCreditosStone = CurrencyUtils.nullToZero(getMovimentacaoBusiness().getMovimentacaoFinder()
				.findTotal(carteiraStone, c101, null, modoCredito, getDtIni(), getDtFim()));

		l.add(new ItemResumo("TOTAL - STONE (CRÉDITOS)", tCreditosStone));

		ItemResumo informado = addLinhaRegistroConferencia("TOTAL STONE - CRÉDITOS", tCreditosStone);
		if (informado.getValor().doubleValue() == 0.0 || informado.getValor() == null) {
			return null;
		}
		l.add(informado);

		BigDecimal taxa = CurrencyUtils.nullToZero(getMovimentacaoBusiness()
				.calcularTaxaCartao(carteiraStone, false, tCreditosStone, getDtIni(), getDtFim()));

		String icone = "cancel";
		if (taxa != null) {
			if (tCreditosStone.doubleValue() == 0.0 && taxa.doubleValue() == 0.0) {
				icone = "checked";
			} else {
				icone = taxa.doubleValue() > 0.01 ? "checked" : "cancel";
			}
		}
		l.add(new ItemResumo("TAXA", taxa, icone));

		return l;
	}

	/**
	 * Resumo de DÉBITOS STONE.
	 *
	 * @return
	 * @throws ViewException
	 */
	public List<ItemResumo> gerarResumoDebitosStoneMSP() throws ViewException {
		List<ItemResumo> l = new ArrayList<ItemResumo>();

		Carteira caixaAVista = getMovimentacaoBusiness().getCarteiraFinder().findBy("CAIXA A VISTA");
		Carteira carteiraStone = getMovimentacaoBusiness().getCarteiraFinder().findBy("STONE MSP");

		Categoria c101 = getMovimentacaoBusiness().getCategoriaFinder().findBy(101l);
		Categoria c199 = getMovimentacaoBusiness().getCategoriaFinder().findBy(199l);

		OperadoraCartao operadoraStone = getMovimentacaoBusiness().getOperadoraCartaoFinder().findBy("STONE MSP");

		Modo modoDebito = getMovimentacaoBusiness().getModoFinder().findBy("RECEB. CARTÃO DÉBITO");

		// Encontra tudo o que vendeu (101s) no caixaAVista no débito, desta operadora, neste período
		BigDecimal tCaixaAvista = CurrencyUtils.nullToZero(getMovimentacaoBusiness().getMovimentacaoFinder()
				.findTotal(caixaAVista, c101, operadoraStone, modoDebito, getDtIni(), getDtFim()));

		// Encontra tudo o que entrou na carteiraStone como 199
		BigDecimal tCarteiraStone = CurrencyUtils.nullToZero(getMovimentacaoBusiness().getMovimentacaoFinder()
				.findTotal(carteiraStone, c199, operadoraStone, modoDebito, getDtIni(), getDtFim()));

		String icone = null;
		if (tCaixaAvista != null) {
			icone = tCaixaAvista.compareTo(tCarteiraStone) == 0 ? "checked" : "cancel";
		}

		l.add(new ItemResumo("TOTAL - CAIXA A VISTA", tCaixaAvista, icone));
		l.add(new ItemResumo("TOTAL - STONE MSP (DÉBITOS)", tCarteiraStone, icone));

		ItemResumo informado = addLinhaRegistroConferencia("TOTAL STONE MSP - DÉBITOS", tCarteiraStone);
		if (informado.getValor().doubleValue() == 0.0 || informado.getValor() == null) {
			return null;
		}
		l.add(informado);

		BigDecimal taxa = CurrencyUtils.nullToZero(getMovimentacaoBusiness()
				.calcularTaxaCartao(carteiraStone, true, tCarteiraStone, getDtIni(), getDtFim()));

		icone = "cancel";
		if (taxa != null) {
			if (tCarteiraStone.doubleValue() == 0.0 && taxa.doubleValue() == 0.0) {
				icone = "checked";
			} else {
				icone = taxa.doubleValue() > 0.01 ? "checked" : "cancel";
			}
		}
		l.add(new ItemResumo("TAXA", taxa, icone));

		return l;
	}
	/**
	 * Resumo de CRÉDITOS STONE
	 *
	 * @return
	 * @throws ViewException
	 */
	public List<ItemResumo> gerarResumoCreditosStoneMSP() throws ViewException {
		List<ItemResumo> l = new ArrayList<ItemResumo>();
		
		Categoria c101 = getMovimentacaoBusiness().getCategoriaFinder().findBy(101l);
		
		Carteira carteiraStone = getMovimentacaoBusiness().getCarteiraFinder().findBy("STONE MSP");
		
		Modo modoCredito = getMovimentacaoBusiness().getModoFinder().findBy("RECEB. CARTÃO CRÉDITO");
		
		BigDecimal tCreditosStone = CurrencyUtils.nullToZero(getMovimentacaoBusiness().getMovimentacaoFinder()
				.findTotal(carteiraStone, c101, null, modoCredito, getDtIni(), getDtFim()));
		
		l.add(new ItemResumo("TOTAL - STONE MSP (CRÉDITOS)", tCreditosStone));
		
		ItemResumo informado = addLinhaRegistroConferencia("TOTAL STONE MSP - CRÉDITOS", tCreditosStone);
		if (informado.getValor().doubleValue() == 0.0 || informado.getValor() == null) {
			return null;
		}
		l.add(informado);
		
		BigDecimal taxa = CurrencyUtils.nullToZero(getMovimentacaoBusiness()
				.calcularTaxaCartao(carteiraStone, false, tCreditosStone, getDtIni(), getDtFim()));
		
		String icone = "cancel";
		if (taxa != null) {
			if (tCreditosStone.doubleValue() == 0.0 && taxa.doubleValue() == 0.0) {
				icone = "checked";
			} else {
				icone = taxa.doubleValue() > 0.01 ? "checked" : "cancel";
			}
		}
		l.add(new ItemResumo("TAXA", taxa, icone));
		
		return l;
	}
	
	/**
	 * Resumo de DÉBITOS STONE.
	 *
	 * @return
	 * @throws ViewException
	 */
	public List<ItemResumo> gerarResumoDebitosStone() throws ViewException {
		List<ItemResumo> l = new ArrayList<ItemResumo>();
		
		Carteira caixaAVista = getMovimentacaoBusiness().getCarteiraFinder().findBy("CAIXA A VISTA");
		Carteira carteiraStone = getMovimentacaoBusiness().getCarteiraFinder().findBy("STONE");
		
		Categoria c101 = getMovimentacaoBusiness().getCategoriaFinder().findBy(101l);
		Categoria c199 = getMovimentacaoBusiness().getCategoriaFinder().findBy(199l);
		
		OperadoraCartao operadoraStone = getMovimentacaoBusiness().getOperadoraCartaoFinder().findBy("STONE");
		
		Modo modoDebito = getMovimentacaoBusiness().getModoFinder().findBy("RECEB. CARTÃO DÉBITO");
		
		// Encontra tudo o que vendeu (101s) no caixaAVista no débito, desta operadora, neste período
		BigDecimal tCaixaAvista = CurrencyUtils.nullToZero(getMovimentacaoBusiness().getMovimentacaoFinder()
				.findTotal(caixaAVista, c101, operadoraStone, modoDebito, getDtIni(), getDtFim()));
		
		// Encontra tudo o que entrou na carteiraStone como 199
		BigDecimal tCarteiraStone = CurrencyUtils.nullToZero(getMovimentacaoBusiness().getMovimentacaoFinder()
				.findTotal(carteiraStone, c199, operadoraStone, modoDebito, getDtIni(), getDtFim()));
		
		String icone = null;
		if (tCaixaAvista != null) {
			icone = tCaixaAvista.compareTo(tCarteiraStone) == 0 ? "checked" : "cancel";
		}
		
		l.add(new ItemResumo("TOTAL - CAIXA A VISTA", tCaixaAvista, icone));
		l.add(new ItemResumo("TOTAL - STONE (DÉBITOS)", tCarteiraStone, icone));
		
		ItemResumo informado = addLinhaRegistroConferencia("TOTAL STONE - DÉBITOS", tCarteiraStone);
		if (informado.getValor().doubleValue() == 0.0 || informado.getValor() == null) {
			return null;
		}
		l.add(informado);
		
		BigDecimal taxa = CurrencyUtils.nullToZero(getMovimentacaoBusiness()
				.calcularTaxaCartao(carteiraStone, true, tCarteiraStone, getDtIni(), getDtFim()));
		
		icone = "cancel";
		if (taxa != null) {
			if (tCarteiraStone.doubleValue() == 0.0 && taxa.doubleValue() == 0.0) {
				icone = "checked";
			} else {
				icone = taxa.doubleValue() > 0.01 ? "checked" : "cancel";
			}
		}
		l.add(new ItemResumo("TAXA", taxa, icone));
		
		return l;
	}

	/**
	 * Resumo de Grupos de Movimentações.
	 *
	 * @return
	 * @throws ViewException
	 */
	public List<ItemResumo> gerarResumoGrupos() throws ViewException {
		List<ItemResumo> l = new ArrayList<ItemResumo>();

		List<GrupoItem> gruposItens = getMovimentacaoBusiness().getGrupoItemFinder().findBy(getDtFim());
		// Percorre todos os grupos
		for (GrupoItem gi : gruposItens) {
			if (gi == null) {
				continue;
			}

			BigDecimal valorLanctos = CurrencyUtils.nullToZero(gi.getValorLanctos());
			BigDecimal valorInformado = CurrencyUtils.nullToZero(gi.getValorInformado());

			String icone = valorLanctos.compareTo(gi.getValorInformado()) == 0 ? "checked" : "cancel";

			l.add(new ItemResumo("TOTAL LANÇADO - " + gi.getPai().getDescricao(), valorLanctos, icone));
			l.add(new ItemResumo("TOTAL INFORMADO - " + gi.getPai().getDescricao(), valorInformado, icone));

			l.add(new ItemResumo(null, null));
		}

		return l;
	}

	/**
	 * Resumo de TRANSFERÊNCIAS ENTRE CARTEIRAS.
	 *
	 * @return
	 * @throws ViewException
	 */
	public List<ItemResumo> gerarResumoTransferenciaEntreCarteiras() throws ViewException {
		List<ItemResumo> l = new ArrayList<ItemResumo>();

		Categoria c299 = getMovimentacaoBusiness().getCategoriaFinder().findBy(299l);
		Categoria c199 = getMovimentacaoBusiness().getCategoriaFinder().findBy(199l);

		BigDecimal t299 = CurrencyUtils.nullToZero(getMovimentacaoBusiness().getMovimentacaoFinder()
				.findTotal(c299, getDtIni(), getDtFim(), false, false, "dt_moviment"));
		BigDecimal t199 = CurrencyUtils.nullToZero(getMovimentacaoBusiness().getMovimentacaoFinder()
				.findTotal(c199, getDtIni(), getDtFim(), false, false, "dt_moviment"));

		String icone = t199.compareTo(t299) == 0 ? "checked" : "cancel";

		l.add(new ItemResumo("TOTAL - " + c299.getDescricao(), t299, icone));
		l.add(new ItemResumo("TOTAL - " + c199.getDescricao(), t199, icone));

		return l;
	}

	public String chooseIcon(BigDecimal valor, RegistroConferencia rc) {
		String icone = null;
		if ((valor != null) && (rc != null)) {
			if (valor.doubleValue() == rc.getValor().doubleValue()) {
				icone = "checked";
			} else {
				if (rc.getObs() != null) {
					if (rc.getObs().length() > 0) {
						icone = "attention";

					} else {
						icone = "cancel";
					}
				} else {
					icone = "checked";
				}
			}
		}
		return icone;

	}

	public void incPeriodo(boolean proFuturo) {
		try {
			Date dtIni = getDtIni();
			Date dtFim = getDtFim();

			// Se na tela foi informado um período relatorial...
			if (CalendarUtil.isPeriodoRelatorial(dtIni, dtFim)) {
				Date r[] = CalendarUtil.iteratePeriodoRelatorial(dtIni, dtFim, proFuturo);
				setDtIni(r[0]);
				setDtFim(r[1]);

			} else {

				Long qtdeDias = CalendarUtil.difBetweenDates(dtIni, dtFim) + 1;
				if (qtdeDias == 0) {
					if (proFuturo) {
						dtIni = getMovimentacaoBusiness().getDiaUtilFinder()
								.findProximoDiaUtilComercial(CalendarUtil.incDias(dtIni, 1));
					} else {
						dtIni = getMovimentacaoBusiness().getDiaUtilFinder().findAnteriorDiaUtilComercial(dtIni);
					}
				} else {
					if (proFuturo) {
						dtIni = CalendarUtil.incDias(dtIni, qtdeDias.intValue());
						dtFim = CalendarUtil.incDias(dtFim, qtdeDias.intValue());
					} else {
						dtIni = CalendarUtil.incDias(dtIni, -qtdeDias.intValue());
						dtFim = CalendarUtil.incDias(dtFim, -qtdeDias.intValue());
					}

				}

				setDtIni(dtIni);
				setDtFim(dtFim);

			}

			store();
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	/**
	 * Monta a lista com as movimentações de transferência entre carteiras (1.99 e 2.99) com divergência de valores.
	 */
	public void listarErrosTransferenciaEntreCarteiras() {
		try {
			setErrosTransf(getMovimentacaoBusiness().listarErrosTransferenciaEntreCarteiras(getDtIni(), getDtFim()));
		} catch (ViewException e) {
			logger.error(e);
			JSFUtils.addHandledException(e);
		}
	}

	public MovimentacaoBusiness getMovimentacaoBusiness() {
		return movimentacaoBusiness;
	}

	public void setMovimentacaoBusiness(MovimentacaoBusiness movimentacaoBusiness) {
		this.movimentacaoBusiness = movimentacaoBusiness;
	}

	public StoredViewInfoHandler getStoredViewInfoHandler() {
		return storedViewInfoHandler;
	}

	public void setStoredViewInfoHandler(StoredViewInfoHandler storedViewInfoHandler) {
		this.storedViewInfoHandler = storedViewInfoHandler;
	}

	public String[] getCamposStored() {
		return camposStored;
	}

	public void setCamposStored(String[] camposStored) {
		this.camposStored = camposStored;
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

	public RegistroConferenciaFinder getRegistroConferenciaFinder() {
		return registroConferenciaFinder;
	}

	public void setRegistroConferenciaFinder(RegistroConferenciaFinder registroConferenciaFinder) {
		this.registroConferenciaFinder = registroConferenciaFinder;
	}

	public VendaFinder getVendaFinder() {
		return vendaFinder;
	}

	public void setVendaFinder(VendaFinder vendaFinder) {
		this.vendaFinder = vendaFinder;
	}

	public List<Movimentacao> getErrosTransf() {
		return errosTransf;
	}

	public void setErrosTransf(List<Movimentacao> errosTransf) {
		this.errosTransf = errosTransf;
	}

	/**
	 * Classe auxiliar para exibições na view.
	 *
	 * @author Carlos Eduardo Pauluk
	 *
	 */
	public class ItemResumo {

		private String descricao;
		private BigDecimal valor;
		private String icone;
		private String obs;

		public ItemResumo(String descricao, BigDecimal valor) {
			super();
			this.descricao = descricao;
			this.valor = valor;
		}

		public ItemResumo(String descricao, BigDecimal valor, String icone) {
			super();
			this.descricao = descricao;
			this.icone = icone;
			this.valor = valor;
		}

		public ItemResumo(String descricao, BigDecimal valor, String icone, String obs) {
			super();
			this.descricao = descricao;
			this.icone = icone;
			this.valor = valor;
			this.obs = obs;
		}

		public String getDescricao() {
			return descricao;
		}

		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}

		public String getIcone() {
			return icone;
		}

		public void setIcone(String icone) {
			this.icone = icone;
		}

		public BigDecimal getValor() {
			return valor;
		}

		public void setValor(BigDecimal valor) {
			this.valor = valor;
		}

		public String getObs() {
			return obs;
		}

		public void setObs(String obs) {
			this.obs = obs;
		}

	}

}
