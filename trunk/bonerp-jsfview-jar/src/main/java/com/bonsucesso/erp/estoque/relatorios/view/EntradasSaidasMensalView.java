package com.bonsucesso.erp.estoque.relatorios.view;



import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.estoque.business.VendaResultsBusiness;
import com.bonsucesso.erp.estoque.data.FornecedorFinder;
import com.bonsucesso.erp.estoque.data.ProdutoFinder;
import com.bonsucesso.erp.estoque.data.SubdeptoFinder;
import com.bonsucesso.erp.estoque.model.Fornecedor;
import com.bonsucesso.erp.estoque.model.Subdepartamento;
import com.ocabit.base.data.EntityIdHandler;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jsf.utils.JSFUtils;
import com.ocabit.utils.calendar.CalendarUtil;
import com.ocabit.utils.currency.CurrencyUtils;
import com.ocabit.utils.strings.StringUtils;


/**
 * 
 * 
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("entradasSaidasMensalView")
@Scope("view")
public class EntradasSaidasMensalView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1034386687893429300L;

	protected static Logger logger = Logger.getLogger(EntradasSaidasMensalView.class);

	private List<Fornecedor> fornecedores;

	private List<Fornecedor> fornecedoresSel;

	private List<Subdepartamento> subdeptos;

	private List<Subdepartamento> subdeptosSel;

	@Autowired
	private EntityIdHandler entityIdHandler;

	@Autowired
	private SubdeptoFinder subdeptoFinder;

	@Autowired
	private FornecedorFinder fornecedorFinder;

	@Autowired
	private ProdutoFinder produtoFinder;

	@Autowired
	private VendaResultsBusiness vendaResultsBusiness;

	private List<EntradasSaidasMensalRecord> list;

	/**
	 * Os relatórios são com dataTable e subTable. As orderedKeys são as linhas do dataTable (fornecedor ou subdepto).
	 */
	private List orderedKeys;

	private Map<Fornecedor, List<EntradasSaidasMensalRecord>> mapListFornecedor;

	private Map<Subdepartamento, List<EntradasSaidasMensalRecord>> mapListSubdepto;

	private BigDecimal[] totaisGerais;

	private String txtFornecedor;

	private Date dtMesAno;

	private Integer progress = 0;

	private Map<Fornecedor, EntradasSaidasMensalRecord> subTotalPorFornecedor;

	private Map<Subdepartamento, EntradasSaidasMensalRecord> subTotalPorSubdepto;

	/**
	 * 'F': Fornecedor.
	 * 'S': Subdepto.
	 */
	private String tipoRel = "F";

	/**
	 * 'Chave'
	 * 'Total'
	 * 'SaldoAtual'
	 * 'PorcentVendas'
	 */
	private String ordenarPor = "Chave";

	private Integer fornCodigoIni, fornCodigoFim;

	private Integer subdeptoCodigoIni, subdeptoCodigoFim;

	// Se for 'true', então a ordem é feita somente dentro dos itens da chave.
	private boolean ordenarApenasIntern = false;

	public void gerar() {
		long ini = System.currentTimeMillis();

		setList(null);
		setMapListFornecedor(null);
		setMapListSubdepto(null);

		Date mesAnoBase = getDtMesAno();

		Date mesAnoIni = CalendarUtil.incMes(mesAnoBase, -12);
		Date mesAnoFim = CalendarUtil.incMes(mesAnoBase, -1);

		// Já monta o array de totais gerais para exibir no rodapé
		BigDecimal[] totaisGerais = new BigDecimal[15];
		for (int i = 0; i < 15; i++) {
			totaisGerais[i] = CurrencyUtils.getBigDecimalCurrency("0.00");
		}
		setTotaisGerais(totaisGerais);

		switch (getTipoRel()) {
			case "F":
				gerarPorFornecedor(mesAnoIni, mesAnoFim);
				break;
			case "S":
				gerarPorSubdepto(mesAnoIni, mesAnoFim);
				break;
		}

		try {
			ordenarEBuildMapList();
		} catch (ViewException e) {
			JSFUtils.addHandledException(e);
		}

		finishProgress();

		logger.debug("Total: " + (System.currentTimeMillis() - ini) / 1000.0);
		logger.debug("Finalizando...");
	}

	/**
	 * Método principal.
	 */
	public void gerarPorFornecedor(Date mesAnoIni, Date mesAnoFim) {

		Map<Fornecedor, Map<Subdepartamento, Map<Date, BigDecimal>>> rFornecedores;
		try {
			rFornecedores = getVendaResultsBusiness().buildVendasResultsByFornecedor(mesAnoIni, mesAnoFim);
		} catch (ViewException e1) {
			JSFUtils.addHandledException(e1);
			return;
		}

		double totalForns = getFornecedoresSel().size();
		double j = 0;
		setProgress(0);

		for (Fornecedor f : getFornecedoresSel()) {

			j++;
			Double progress = j / totalForns * 100.0;
			if (progress > 99.0) {
				progress = 99.0;
			}
			setProgress(progress.intValue());
			logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> PROGRESS: " + progress.intValue());

			if (rFornecedores.containsKey(f)) {

				logger.debug("Gerando para " + f.getCodigo() + " - " + f.getPessoa().getNomeFantasia());

				Map<Subdepartamento, Map<Date, BigDecimal>> rSubdeptos = rFornecedores.get(f);

				for (Subdepartamento sd : getSubdeptosSel()) {
					try {
						if (rSubdeptos.containsKey(sd)) {

							Map<Date, BigDecimal> r = rSubdeptos.get(sd);

							logger.debug("PROCESSANDO O SUBDEPTO: " + sd.getCodigo() + " - " + sd.getNome());

							// Encontra a qtde de itens vendidos deste Fornecedor, neste Subdepto, entre mesAnoIni e mesAnoFim

							List<Date> mesesAnos = new ArrayList<Date>();
							mesesAnos.addAll(r.keySet());

							// Ordena somente pelo mês, ignorando o ano (para exibir de JAN..DEZ)
							Collections.sort(mesesAnos, new Comparator<Date>() {

								@Override
								public int compare(Date o1, Date o2) {
									int m1 = CalendarUtil.getCalendar(o1).get(Calendar.MONTH);
									int m2 = CalendarUtil.getCalendar(o2).get(Calendar.MONTH);
									return Integer.valueOf(m1).compareTo(m2);
								}
							});

							List<BigDecimal> totais = new ArrayList<BigDecimal>();
							BigDecimal subtotalMeses = BigDecimal.ZERO;
							for (Date mesAno : mesesAnos) {
								BigDecimal tMes = r.get(mesAno) == null ? BigDecimal.ZERO : r.get(mesAno);
								totais.add(tMes);
								subtotalMeses = subtotalMeses.add(tMes);
							}

							// Encontra a qtde (saldo) de produtos por Fornecedor e Subdepto
							BigDecimal saldoAtual = getProdutoFinder().findQtdeBy(f, sd);
							saldoAtual = saldoAtual == null ? BigDecimal.ZERO : saldoAtual;

							BigDecimal div = saldoAtual.add(subtotalMeses);
							div = (div == null || div.doubleValue() == 0.0) ? BigDecimal.ONE : div; // para evitar divisão por zero

							BigDecimal porcentVendas = subtotalMeses.divide(div, 4, RoundingMode.HALF_DOWN)
									.multiply(new BigDecimal("100.00"));

							if (subtotalMeses.doubleValue() != 0.0 || saldoAtual.doubleValue() != 0.0) {
								getList().add(new EntradasSaidasMensalRecord(f, sd, totais
										.toArray(new BigDecimal[] {}), subtotalMeses, saldoAtual, porcentVendas));
							}

							for (int i = 0; i < 12; i++) {
								totaisGerais[i] = totaisGerais[i].add(totais.get(i));
							}
							totaisGerais[12] = totaisGerais[12].add(subtotalMeses);
							totaisGerais[13] = totaisGerais[13].add(saldoAtual);

							logger.debug(r);
						}

					} catch (ViewException e) {
						JSFUtils.addErrorMsg("Erro ao gerar tabela de resultados.");
						throw new IllegalStateException(e);
					}
				}
			}

		}
		BigDecimal saldoAtualGeral = totaisGerais[13];
		BigDecimal totalGeral = totaisGerais[12] == null || totaisGerais[12].doubleValue() == 0.0 ? BigDecimal.ONE
				: totaisGerais[12];
		totaisGerais[14] = totalGeral.divide(saldoAtualGeral.add(totalGeral), 4, RoundingMode.HALF_DOWN)
				.multiply(new BigDecimal("100.00"));

	}

	/**
	 * Método principal.
	 */
	public void gerarPorSubdepto(Date mesAnoIni, Date mesAnoFim) {

		Map<Subdepartamento, Map<Fornecedor, Map<Date, BigDecimal>>> rSubdeptos;
		try {
			rSubdeptos = getVendaResultsBusiness().buildVendasResultsBySubdepto(mesAnoIni, mesAnoFim);
		} catch (ViewException e1) {
			JSFUtils.addHandledException(e1);
			return;
		}

		double totalSubdeptos = getSubdeptosSel().size();
		double j = 0;
		setProgress(0);

		for (Subdepartamento subdepto : getSubdeptosSel()) {

			j++;
			Double progress = j / totalSubdeptos * 100.0;
			if (progress > 99.0) {
				progress = 99.0;
			}
			setProgress(progress.intValue());
			logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> PROGRESS: " + progress.intValue());

			if (rSubdeptos.containsKey(subdepto)) {

				Map<Fornecedor, Map<Date, BigDecimal>> rFornecedores = rSubdeptos.get(subdepto);

				for (Fornecedor forn : getFornecedoresSel()) {
					try {
						if (rFornecedores.containsKey(forn)) {

							Map<Date, BigDecimal> r = rFornecedores.get(forn);

							// Encontra a qtde de itens vendidos deste Fornecedor, neste Subdepto, entre mesAnoIni e mesAnoFim

							List<Date> mesesAnos = new ArrayList<Date>();
							mesesAnos.addAll(r.keySet());

							// Ordena somente pelo mês, ignorando o ano (para exibir de JAN..DEZ)
							Collections.sort(mesesAnos, new Comparator<Date>() {

								@Override
								public int compare(Date o1, Date o2) {
									int m1 = CalendarUtil.getCalendar(o1).get(Calendar.MONTH);
									int m2 = CalendarUtil.getCalendar(o2).get(Calendar.MONTH);
									return Integer.valueOf(m1).compareTo(m2);
								}
							});

							List<BigDecimal> totais = new ArrayList<BigDecimal>();
							BigDecimal subtotalMeses = BigDecimal.ZERO;
							for (Date mesAno : mesesAnos) {
								BigDecimal tMes = r.get(mesAno) == null ? BigDecimal.ZERO : r.get(mesAno);
								totais.add(tMes);
								subtotalMeses = subtotalMeses.add(tMes);
							}

							// Encontra a qtde (saldo) de produtos por Fornecedor e Subdepto
							BigDecimal saldoAtual = getProdutoFinder().findQtdeBy(forn, subdepto);
							saldoAtual = saldoAtual == null ? BigDecimal.ZERO : saldoAtual;

							BigDecimal div = saldoAtual.add(subtotalMeses);
							div = (div == null || div.doubleValue() == 0.0) ? BigDecimal.ONE : div; // para evitar divisão por zero

							BigDecimal porcentVendas = subtotalMeses.divide(div, 4, RoundingMode.HALF_DOWN)
									.multiply(new BigDecimal("100.00"));

							if (subtotalMeses.doubleValue() != 0.0 || saldoAtual.doubleValue() != 0.0) {
								getList().add(new EntradasSaidasMensalRecord(forn, subdepto, totais
										.toArray(new BigDecimal[] {}), subtotalMeses, saldoAtual, porcentVendas));
							}

							for (int i = 0; i < 12; i++) {
								totaisGerais[i] = totaisGerais[i].add(totais.get(i));
							}
							totaisGerais[12] = totaisGerais[12].add(subtotalMeses);
							totaisGerais[13] = totaisGerais[13].add(saldoAtual);

							logger.debug(r);
						}

					} catch (ViewException e) {
						JSFUtils.addErrorMsg("Erro ao gerar tabela de resultados.");
						throw new IllegalStateException(e);
					}
				}
			}

		}
		BigDecimal saldoAtualGeral = totaisGerais[13];
		BigDecimal totalGeral = totaisGerais[12] == null || totaisGerais[12].doubleValue() == 0.0 ? BigDecimal.ONE
				: totaisGerais[12];
		totaisGerais[14] = totalGeral.divide(saldoAtualGeral.add(totalGeral), 4, RoundingMode.HALF_DOWN)
				.multiply(new BigDecimal("100.00"));
	}

	public void gerarTxtFornecedor() {
		String cabecalho = "999 - aaaaaaaaaaaaaaaaaaaaaaaaa Jan   Fev   Mar   Abr   Mai   Jun   Jul   Ago   Set   Out   Nov   Dez   Total   Sd At % Vda\r\n";

		StringBuilder sb = new StringBuilder();

		List<Fornecedor> forns = new ArrayList<Fornecedor>();
		forns.addAll(getMapListFornecedor().keySet());

		Collections.sort(forns, new Comparator<Fornecedor>() {

			@Override
			public int compare(Fornecedor o1, Fornecedor o2) {
				return o1.getCodigo().compareTo(o2.getCodigo());
			}
		});

		for (Fornecedor f : forns) {
			sb.append(cabecalho
					.replace("999", StringUtils.fillWith(f.getCodigo().toString(), 5, ' '))
					.replace("aaaaaaaaaaaaaaaaaaaaaaaaa", String.format("%-25.25s", f.getPessoa().getNomeFantasia())));

			List<EntradasSaidasMensalRecord> l = getMapListFornecedor().get(f);
			logger.debug("Gerando para " + f.getCodigo() + " - " + f.getPessoa().getNomeFantasia());

			for (EntradasSaidasMensalRecord r : l) {
				//   107 - CUECA AD                    8    12    10     2     6    17    16    13     5     8    17    22     136    91  59.91
				// %-5.5s - %-23.23s%6d%6d%6d%6d%6d%6d%6d%6d%6d%6d%6d%6d

				sb.append(String.format("%5.5s - %-23.23s%6d%6d%6d%6d%6d%6d%6d%6d%6d%6d%6d%6d%8d%6d%7.2f\r\n", r
						.getSubdepto()
						.getCodigo(), r.getSubdepto().getNome(), r.getTotaisVendasMeses()[0].intValue(), r
								.getTotaisVendasMeses()[1].intValue(), r.getTotaisVendasMeses()[2].intValue(), r
										.getTotaisVendasMeses()[3].intValue(), r.getTotaisVendasMeses()[4].intValue(), r
												.getTotaisVendasMeses()[5].intValue(), r.getTotaisVendasMeses()[6]
														.intValue(), r.getTotaisVendasMeses()[7]
																.intValue(), r.getTotaisVendasMeses()[8].intValue(), r
																		.getTotaisVendasMeses()[9].intValue(), r
																				.getTotaisVendasMeses()[10]
																						.intValue(), r
																								.getTotaisVendasMeses()[11]
																										.intValue(), r
																												.getTotalMeses()
																												.intValue(), r
																														.getTotalSaldosAtuais()
																														.intValue(), r
																																.getPorcentVendas()
																																.doubleValue()));
			}

			sb.append(String
					.format(" Subtotal                      %6d%6d%6d%6d%6d%6d%6d%6d%6d%6d%6d%6d%8d%6d%7.2f\r\n\r\n\r\n", getSubtotalByFornecedor(f, 0, 'M')
							.intValue(), getSubtotalByFornecedor(f, 1, 'M')
									.intValue(), getSubtotalByFornecedor(f, 2, 'M')
											.intValue(), getSubtotalByFornecedor(f, 3, 'M')
													.intValue(), getSubtotalByFornecedor(f, 4, 'M')
															.intValue(), getSubtotalByFornecedor(f, 5, 'M')
																	.intValue(), getSubtotalByFornecedor(f, 6, 'M')
																			.intValue(), getSubtotalByFornecedor(f, 7, 'M')
																					.intValue(), getSubtotalByFornecedor(f, 8, 'M')
																							.intValue(), getSubtotalByFornecedor(f, 9, 'M')
																									.intValue(), getSubtotalByFornecedor(f, 10, 'M')
																											.intValue(), getSubtotalByFornecedor(f, 11, 'M')
																													.intValue(), getSubtotalByFornecedor(f, null, 'T')
																															.intValue(), getSubtotalByFornecedor(f, null, 'S')
																																	.intValue(), getSubtotalByFornecedor(f, null, 'V')
																																			.doubleValue()));

		}

		sb.append(String
				.format("                               %6d%6d%6d%6d%6d%6d%6d%6d%6d%6d%6d%6d%8d%6d%7.2f\r\n\r\n\r\n", totaisGerais[0]
						.intValue(), totaisGerais[1].intValue(), totaisGerais[2].intValue(), totaisGerais[3]
								.intValue(), totaisGerais[4].intValue(), totaisGerais[5].intValue(), totaisGerais[6]
										.intValue(), totaisGerais[7].intValue(), totaisGerais[8]
												.intValue(), totaisGerais[9].intValue(), totaisGerais[10]
														.intValue(), totaisGerais[11].intValue(), totaisGerais[12]
																.intValue(), totaisGerais[13]
																		.intValue(), totaisGerais[14].doubleValue()

		));

		setTxtFornecedor(sb.toString());
	}

	/**
	 * Método chamado para ordenar os mapList conforme a seleção na tela. É chamado também ao trocar o tipo de ordenação na tela.
	 * 
	 * @throws ViewException
	 */
	public void ordenarEBuildMapList() throws ViewException {
		// 1- Primeiro monta os subtotais, pois eles são necessários para a ordenação
		setSubTotalPorFornecedor(null);
		setSubTotalPorSubdepto(null);
		for (EntradasSaidasMensalRecord r : getList()) {
			if (getTipoRel().equals("F")) {
				if (!getSubTotalPorFornecedor().containsKey(r.getFornecedor())) {
					BigDecimal[] totaisVendasMeses = new BigDecimal[12];
					for (int i = 0; i < 12; i++) {
						totaisVendasMeses[i] = CurrencyUtils
								.nullToZero(getSubtotalByFornecedor(r.getFornecedor(), i, 'M'));
					}
					BigDecimal totalMeses = CurrencyUtils
							.nullToZero(getSubtotalByFornecedor(r.getFornecedor(), null, 'T'));
					BigDecimal totalSaldosAtuais = CurrencyUtils
							.nullToZero(getSubtotalByFornecedor(r.getFornecedor(), null, 'S'));
					BigDecimal porcentVendas = CurrencyUtils
							.nullToZero(getSubtotalByFornecedor(r.getFornecedor(), null, 'V'));

					getSubTotalPorFornecedor().put(r.getFornecedor(), new EntradasSaidasMensalRecord(r
							.getFornecedor(), null, totaisVendasMeses, totalMeses, totalSaldosAtuais, porcentVendas));
				}
			} else if (getTipoRel().equals("S")) {
				// Monta os subtotais
				if (!getSubTotalPorSubdepto().containsKey(r.getSubdepto())) {
					BigDecimal[] totaisVendasMeses = new BigDecimal[12];
					for (int i = 0; i < 12; i++) {
						totaisVendasMeses[i] = CurrencyUtils
								.nullToZero(getSubtotalBySubdepto(r.getSubdepto(), i, 'M'));
					}
					BigDecimal totalMeses = CurrencyUtils
							.nullToZero(getSubtotalBySubdepto(r.getSubdepto(), null, 'T'));
					BigDecimal totalSaldosAtuais = CurrencyUtils
							.nullToZero(getSubtotalBySubdepto(r.getSubdepto(), null, 'S'));
					BigDecimal porcentVendas = CurrencyUtils
							.nullToZero(getSubtotalBySubdepto(r.getSubdepto(), null, 'V'));

					getSubTotalPorSubdepto().put(r.getSubdepto(), new EntradasSaidasMensalRecord(null, r
							.getSubdepto(), totaisVendasMeses, totalMeses, totalSaldosAtuais, porcentVendas));
				}
			} else {
				throw new ViewException("Tipo de relatório inválido");
			}
		}

		// 2- Depois ordena
		ordenarList();

		// 3- Depois monta os mapLists, que é onde vai montar as orderedKeys para poder fazer a ordenação lá nas telas.
		if (getTipoRel().equals("S")) {
			buildMapListSubdepto();
		} else {
			buildMapListFornecedor();
		}

		logger.debug("finalizando ordenarEBuildMapList()");
	}

	/**
	 * Ordena a lista de acordo com a seleção na tela.
	 */
	public void ordenarList() {

		if (getList() != null && !getList().isEmpty()) {

			/**
			 * ordenarPor:
			 * 'Chave'
			 * 'Total'
			 * 'SaldoAtual'
			 * 'PorcentVendas'
			 */

			Collections.sort(getList(), new Comparator<EntradasSaidasMensalRecord>() {

				@Override
				public int compare(EntradasSaidasMensalRecord o1, EntradasSaidasMensalRecord o2) {
					EntradasSaidasMensalRecord comp1;
					EntradasSaidasMensalRecord comp2;

					// Se a ordem for apenas interna à chave, então não se compara utilizando o subtotal
					if (getTipoRel().equals("F")) {
						comp1 = getSubTotalPorFornecedor().get(o1.getFornecedor());
						comp2 = getSubTotalPorFornecedor().get(o2.getFornecedor());
					} else {
						comp1 = getSubTotalPorSubdepto().get(o1.getSubdepto());
						comp2 = getSubTotalPorSubdepto().get(o2.getSubdepto());
					}

					CompareToBuilder ctb = new CompareToBuilder();
					if (isOrdenarApenasIntern()) {
						if (getTipoRel().equals("F")) {
							ctb = ctb.append(o1.getFornecedor().getCodigo(), o2.getFornecedor().getCodigo());
						} else {
							ctb = ctb.append(o1.getSubdepto().getCodigo(), o2.getSubdepto().getCodigo());
						}
					}

					switch (getOrdenarPor()) {
						case "Total":
							if (isOrdenarApenasIntern()) {
								return ctb.append(o2.getTotalMeses(), o1.getTotalMeses()).toComparison();
							} else {
								return ctb.append(comp2.getTotalMeses(), comp1.getTotalMeses()).toComparison();
							}
						case "SaldoAtual":
							if (isOrdenarApenasIntern()) {
								return ctb.append(o2.getTotalSaldosAtuais(), o1.getTotalSaldosAtuais()).toComparison();
							} else {
								return ctb.append(comp2.getTotalSaldosAtuais(), comp1.getTotalSaldosAtuais())
										.toComparison();
							}
						case "PorcentVendas":
							if (isOrdenarApenasIntern()) {
								return ctb.append(o2.getPorcentVendas(), o1.getPorcentVendas()).toComparison();
							} else {
								return ctb.append(comp2.getPorcentVendas(), comp1.getPorcentVendas()).toComparison();
							}
						default:
							return ctb.toComparison();
						//							if (getTipoRel().equals("F")) {
						//								return new CompareToBuilder()
						//										.append(o1.getFornecedor().getCodigo(), o2.getFornecedor().getCodigo())
						//										.toComparison();
						//							} else {
						//								return new CompareToBuilder()
						//										.append(o1.getSubdepto().getCodigo(), o2.getSubdepto().getCodigo())
						//										.toComparison();
						//							}

					}

				}
			});
		}
	}

	/**
	 * Constrói o mapa de Fornecedor para exibir nos datatables.
	 */
	public void buildMapListFornecedor() {
		setOrderedKeys(new ArrayList());
		setMapListFornecedor(new HashMap<Fornecedor, List<EntradasSaidasMensalRecord>>());

		for (EntradasSaidasMensalRecord r : getList()) {
			if (r.getTotalMeses().doubleValue() != 0.0) {
				if (!getMapListFornecedor().containsKey(r.getFornecedor())) {
					getOrderedKeys().add(r.getFornecedor());
					getMapListFornecedor().put(r.getFornecedor(), new ArrayList<EntradasSaidasMensalRecord>());
				}
				getMapListFornecedor().get(r.getFornecedor()).add(r);
			}
		}
	}

	/**
	 * Constrói o mapa de Subdepto para exibir nos datatables.
	 */
	public void buildMapListSubdepto() {
		setOrderedKeys(new ArrayList());
		setMapListSubdepto(new HashMap<Subdepartamento, List<EntradasSaidasMensalRecord>>());
		for (EntradasSaidasMensalRecord r : getList()) {
			if (r.getTotalMeses().doubleValue() != 0.0) {
				if (!getMapListSubdepto().containsKey(r.getSubdepto())) {
					getOrderedKeys().add(r.getSubdepto());
					getMapListSubdepto().put(r.getSubdepto(), new ArrayList<EntradasSaidasMensalRecord>());
				}
				getMapListSubdepto().get(r.getSubdepto()).add(r);
			}
		}
	}

	/**
	 * @param f
	 * 
	 * @param mes
	 * 
	 * @param tipoSubtotal
	 * 
	 *            M: Meses
	 *            T: Total
	 *            S: Saldo Atual
	 *            V: Porcentagem de vendas
	 * 
	 * @return
	 * 
	 * 
	 */
	public BigDecimal getSubtotalByFornecedor(Fornecedor f, Integer mes, char tipoSubtotal) {
		BigDecimal bd = BigDecimal.ZERO;
		for (EntradasSaidasMensalRecord r : getList()) {
			if (r.getFornecedor().equals(f)) {
				if (tipoSubtotal == 'M') {
					bd = bd.add(r.getTotaisVendasMeses()[mes]);
				} else if (tipoSubtotal == 'T') {
					// total dos subtotais
					for (int i = 0; i < 12; i++) {
						bd = bd.add(r.getTotaisVendasMeses()[i]);
					}
				} else if (tipoSubtotal == 'S') {
					bd = bd.add(r.getTotalSaldosAtuais());
				} else if (tipoSubtotal == 'V') {
					BigDecimal saldoAtual = getSubtotalByFornecedor(f, null, 'S');
					BigDecimal totalMeses = getSubtotalByFornecedor(f, null, 'T');
					BigDecimal div = saldoAtual.add(totalMeses);
					div = (div == null || div.doubleValue() == 0.0) ? BigDecimal.ONE : div; // para evitar divisão por zero
					BigDecimal porcentVendas = saldoAtual.divide(div, 4, RoundingMode.HALF_DOWN);
					porcentVendas = BigDecimal.ONE.subtract(porcentVendas).multiply(new BigDecimal("100.00"));
					return porcentVendas;
				}
			}
		}
		return bd;
	}

	public BigDecimal getSubtotalBySubdepto(Subdepartamento sd, Integer mes, char tipoSubtotal) {
		BigDecimal bd = BigDecimal.ZERO;
		for (EntradasSaidasMensalRecord r : getList()) {
			if (r.getSubdepto().equals(sd)) {
				if (tipoSubtotal == 'M') {
					bd = bd.add(r.getTotaisVendasMeses()[mes]);
				} else if (tipoSubtotal == 'T') {
					// total dos subtotais
					for (int i = 0; i < 12; i++) {
						bd = bd.add(r.getTotaisVendasMeses()[i]);
					}
				} else if (tipoSubtotal == 'S') {
					bd = bd.add(r.getTotalSaldosAtuais());
				} else if (tipoSubtotal == 'V') {
					BigDecimal saldoAtual = getSubtotalBySubdepto(sd, null, 'S');
					BigDecimal totalMeses = getSubtotalBySubdepto(sd, null, 'T');
					BigDecimal div = saldoAtual.add(totalMeses);
					div = (div == null || div.doubleValue() == 0.0) ? BigDecimal.ONE : div; // para evitar divisão por zero
					BigDecimal porcentVendas = saldoAtual.divide(div, 4, RoundingMode.HALF_DOWN);
					porcentVendas = BigDecimal.ONE.subtract(porcentVendas).multiply(new BigDecimal("100.00"));
					return porcentVendas;
				}
			}
		}
		return bd;
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

	public EntityIdHandler getEntityIdHandler() {
		return entityIdHandler;
	}

	public void setEntityIdHandler(EntityIdHandler entityIdHandler) {
		this.entityIdHandler = entityIdHandler;
	}

	public VendaResultsBusiness getVendaResultsBusiness() {
		return vendaResultsBusiness;
	}

	public void setVendaResultsBusiness(VendaResultsBusiness vendaResultsBusiness) {
		this.vendaResultsBusiness = vendaResultsBusiness;
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

	public SubdeptoFinder getSubdeptoFinder() {
		return subdeptoFinder;
	}

	public void setSubdeptoFinder(SubdeptoFinder subdeptoFinder) {
		this.subdeptoFinder = subdeptoFinder;
	}

	public List<EntradasSaidasMensalRecord> getList() {
		if (list == null) {
			list = new ArrayList<EntradasSaidasMensalRecord>();
		}
		return list;
	}

	public void setList(List<EntradasSaidasMensalRecord> list) {
		this.list = list;
	}

	public List getOrderedKeys() {
		return orderedKeys;
	}

	public void setOrderedKeys(List orderedKeys) {
		this.orderedKeys = orderedKeys;
	}

	public Map<Fornecedor, List<EntradasSaidasMensalRecord>> getMapListFornecedor() {
		return mapListFornecedor;
	}

	public void setMapListFornecedor(Map<Fornecedor, List<EntradasSaidasMensalRecord>> mapListFornecedor) {
		this.mapListFornecedor = mapListFornecedor;
	}

	public Map<Subdepartamento, List<EntradasSaidasMensalRecord>> getMapListSubdepto() {
		return mapListSubdepto;
	}

	public void setMapListSubdepto(Map<Subdepartamento, List<EntradasSaidasMensalRecord>> mapListSubdepto) {
		this.mapListSubdepto = mapListSubdepto;
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

	public String getTxtFornecedor() {
		return txtFornecedor;
	}

	public void setTxtFornecedor(String txtFornecedor) {
		this.txtFornecedor = txtFornecedor;
	}

	public Date getDtMesAno() {
		if (dtMesAno == null) {
			dtMesAno = new Date();
		}
		return dtMesAno;
	}

	public void setDtMesAno(Date dtMesAno) {
		this.dtMesAno = dtMesAno;
	}

	public BigDecimal[] getTotaisGerais() {
		return totaisGerais;
	}

	public void setTotaisGerais(BigDecimal[] totais) {
		this.totaisGerais = totais;
	}

	public Integer getProgress() {
		return progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
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

	public String getTipoRel() {
		return tipoRel;
	}

	public void setTipoRel(String tipoRel) {
		this.tipoRel = tipoRel;
	}

	public String getOrdenarPor() {
		return ordenarPor;
	}

	public void setOrdenarPor(String ordenarPor) {
		this.ordenarPor = ordenarPor;
	}

	public Integer getFornCodigoIni() {
		return fornCodigoIni;
	}

	public void setFornCodigoIni(Integer fornCodigoIni) {
		this.fornCodigoIni = fornCodigoIni;
	}

	public Integer getFornCodigoFim() {
		return fornCodigoFim;
	}

	public void setFornCodigoFim(Integer fornCodigoFim) {
		this.fornCodigoFim = fornCodigoFim;
	}

	public Integer getSubdeptoCodigoIni() {
		return subdeptoCodigoIni;
	}

	public void setSubdeptoCodigoIni(Integer subdeptoCodigoIni) {
		this.subdeptoCodigoIni = subdeptoCodigoIni;
	}

	public Integer getSubdeptoCodigoFim() {
		return subdeptoCodigoFim;
	}

	public void setSubdeptoCodigoFim(Integer subdeptoCodigoFim) {
		this.subdeptoCodigoFim = subdeptoCodigoFim;
	}

	public boolean isOrdenarApenasIntern() {
		return ordenarApenasIntern;
	}

	public void setOrdenarApenasIntern(boolean ordenarApenasIntern) {
		this.ordenarApenasIntern = ordenarApenasIntern;
	}

	public Map<Fornecedor, EntradasSaidasMensalRecord> getSubTotalPorFornecedor() {
		if (subTotalPorFornecedor == null) {
			subTotalPorFornecedor = new HashMap<Fornecedor, EntradasSaidasMensalRecord>();
		}
		return subTotalPorFornecedor;
	}

	public void setSubTotalPorFornecedor(Map<Fornecedor, EntradasSaidasMensalRecord> subTotalPorFornecedor) {
		this.subTotalPorFornecedor = subTotalPorFornecedor;
	}

	public Map<Subdepartamento, EntradasSaidasMensalRecord> getSubTotalPorSubdepto() {
		if (subTotalPorSubdepto == null) {
			subTotalPorSubdepto = new HashMap<Subdepartamento, EntradasSaidasMensalRecord>();
		}
		return subTotalPorSubdepto;
	}

	public void setSubTotalPorSubdepto(Map<Subdepartamento, EntradasSaidasMensalRecord> subTotalPorSubdepto) {
		this.subTotalPorSubdepto = subTotalPorSubdepto;
	}

	/**
	 * Classe auxiliar para exibir os itens no datatable.
	 * 
	 * @author Carlos Eduardo Pauluk
	 *
	 */
	public class EntradasSaidasMensalRecord {

		private Fornecedor fornecedor;

		private Subdepartamento subdepto;

		private BigDecimal[] totaisVendasMeses;

		private BigDecimal totalMeses;

		private BigDecimal totalSaldosAtuais;

		private BigDecimal porcentVendas;

		public EntradasSaidasMensalRecord() {

		}

		public EntradasSaidasMensalRecord(Fornecedor fornecedor, Subdepartamento subdepto,
				BigDecimal[] totaisVendasMeses, BigDecimal totalMeses, BigDecimal totalSaldosAtuais,
				BigDecimal porcentVendas) {
			super();
			this.fornecedor = fornecedor;
			this.subdepto = subdepto;
			this.totaisVendasMeses = totaisVendasMeses;
			this.totalMeses = totalMeses;
			this.totalSaldosAtuais = totalSaldosAtuais;
			this.porcentVendas = porcentVendas;
		}

		public Fornecedor getFornecedor() {
			return fornecedor;
		}

		public void setFornecedor(Fornecedor fornecedor) {
			this.fornecedor = fornecedor;
		}

		public Subdepartamento getSubdepto() {
			return subdepto;
		}

		public void setSubdepto(Subdepartamento subdepto) {
			this.subdepto = subdepto;
		}

		public BigDecimal[] getTotaisVendasMeses() {
			return totaisVendasMeses;
		}

		public void setTotaisVendasMeses(BigDecimal[] totaisMeses) {
			this.totaisVendasMeses = totaisMeses;
		}

		public BigDecimal getTotal() {
			BigDecimal bdTotal = BigDecimal.ZERO;
			for (BigDecimal v : totaisVendasMeses) {
				bdTotal = bdTotal.add(v);
			}
			return bdTotal;
		}

		public BigDecimal getTotalMeses() {
			return totalMeses;
		}

		public void setTotalMeses(BigDecimal totalLinha) {
			this.totalMeses = totalLinha;
		}

		public BigDecimal getTotalSaldosAtuais() {
			return totalSaldosAtuais;
		}

		public void setTotalSaldosAtuais(BigDecimal totalSaldosAtuais) {
			this.totalSaldosAtuais = totalSaldosAtuais;
		}

		public BigDecimal getPorcentVendas() {
			return porcentVendas;
		}

		public void setPorcentVendas(BigDecimal porcentVendas) {
			this.porcentVendas = porcentVendas;
		}

	}

}
