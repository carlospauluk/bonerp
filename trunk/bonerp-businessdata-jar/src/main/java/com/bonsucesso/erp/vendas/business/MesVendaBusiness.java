package com.bonsucesso.erp.vendas.business;



import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.base.data.DiaUtilFinder;
import com.bonsucesso.erp.base.model.DiaUtil;
import com.bonsucesso.erp.rh.model.Funcionario;
import com.bonsucesso.erp.vendas.data.MesVendaDataMapper;
import com.bonsucesso.erp.vendas.data.MesVendaFinder;
import com.bonsucesso.erp.vendas.data.VendaFinder;
import com.bonsucesso.erp.vendas.model.MesVenda;
import com.bonsucesso.erp.vendas.model.MesVendaItem;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.utils.calendar.CalendarUtil;
import com.ocabit.utils.currency.CurrencyUtils;


/**
 * Business para a entidade MesVenda.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("mesVendaBusiness")
public class MesVendaBusiness implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 8581494860634482860L;
	
	protected static Logger logger = Logger.getLogger(MesVendaBusiness.class);

	@Autowired
	private VendaFinder vendaFinder;

	@Autowired
	private MesVendaFinder mesVendaFinder;

	@Autowired
	private MesVendaDataMapper mesVendaDataMapper;

	@Autowired
	private DiaUtilFinder diaUtilFinder;

	@Autowired
	private BeanFactory beanFactory;

	private MesVendaBusiness thiz;

	/**
	 * Preenche os atributos transientes de um mesVenda.
	 *
	 * @param mesVenda
	 * @throws ViewException
	 */
	public void preencherMesVenda(MesVenda mesVenda) throws ViewException {
		if ((mesVenda == null) || (mesVenda.getMesAno() == null)) {
			throw new ViewException("mesVenda inválido");
		}

		getThiz().preencherMesVenda(mesVenda.getMesAno());

	}

	/**
	 * Verifica se tem todos os mesesVendas desde 01/2015 até o próximo mês.
	 *
	 * @param mesVenda
	 * @throws ViewException
	 */
	public void atualizarMesesVendas() throws ViewException {
		List<Date> mesesAnos = CalendarUtil
				.buildMesAnoList(CalendarUtil.getDate(01, 01, 2015), CalendarUtil.incMes(new Date(), 1));

		for (Date d : mesesAnos) {
			MesVenda mesVenda = getMesVendaFinder().findByMesAno(d);
			if (mesVenda == null) {
				getThiz().preencherMesVenda(d);
			}
		}

	}

	/**
	 * Preenche os atributos transientes de um mesVenda.
	 *
	 * @param mesVenda
	 * @throws ViewException
	 */
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void preencherMesVenda(Date mesano) throws ViewException {
		boolean mesFuturo = false;
		// Se está gerando para um mês futuro
		if (CalendarUtil.dataMaiorQueData(mesano, new Date())) {
			mesFuturo = true;
		}
		MesVenda mesVenda = getMesVendaFinder().findByMesAno(mesano);
		if (mesVenda == null) {
			mesVenda = new MesVenda();
		}

		mesVenda.setMesAno(mesano);

		List<MesVendaItem> itens = new ArrayList<MesVendaItem>();

		if (!mesFuturo) {
			
			mesVenda.setEhMesAtual(Boolean.TRUE);

			// setQtdeVendedores
			mesVenda.setQtdeVendedores(getMesVendaFinder().findQtdeVendedoresConsideraMes(mesVenda.getMesAno()));

			// setQtdeVendas
			mesVenda.setQtdeVendas(getVendaFinder().findQtdeVendasBy(mesano));

			Map<Funcionario, BigDecimal> totalVendedores = getVendaFinder().findTotalVendasFuncionariosTodos(mesano);

			BigDecimal totalVendidoVendedores = BigDecimal.ZERO;
			BigDecimal totalVendidoGlobal = BigDecimal.ZERO;

			for (Map.Entry<Funcionario, BigDecimal> e : totalVendedores.entrySet()) {
				if (e.getValue() == null)
					continue;

				Funcionario vendedor = e.getKey();
				logger.info("Somando para... " + vendedor.getCodigo() + " - " + vendedor.getNomeEkt() + " = " + e.getValue().doubleValue());

				MesVendaItem item = new MesVendaItem();

				item.setMesVenda(mesVenda);
				item.setTotalVendido(e.getValue());
				item.setVendedor(vendedor);
				item.setQtdeVendas(getVendaFinder().findQtdeVendasBy(mesano, vendedor));

				// Só entra na soma os vendedores abaixo do 90 ou o 99 ("FUNCIONÁRIOS")
				if (vendedor.getCodigo() < 90 || vendedor.getCodigo() == 99) {
					totalVendidoVendedores = totalVendidoVendedores.add(e.getValue());
					
					Integer mediaPosicoes6meses = getMesVendaFinder().findMediaUltimosMeses(vendedor, mesano, 6);
					item.setMediaPosicoes6meses(mediaPosicoes6meses);

					boolean consideraMes = getVendaFinder().findConsideraMes(mesano, vendedor);
					item.setConsideraMes(consideraMes);

					// se não considera mês, não entra no ranking
					if (consideraMes) {
						item.setPosicao(getVendaFinder().findPosicaoBy(mesano, vendedor));
					}
				} else {
					logger.info(vendedor.getCodigo() + " - " + vendedor.getNomeEkt() + " ... não entra na soma");
				}

				itens.add(item);
				totalVendidoGlobal = totalVendidoGlobal.add(e.getValue());
			}

			// setTotalVendidoVendedores			
			mesVenda.setTotalVendidoVendedores(totalVendidoVendedores);
			
			BigDecimal totalVendidoVendedoresAteOntem = getVendaFinder().findTotalSomenteVendedoresInternosBy(CalendarUtil.primeiroDiaNoMesAno(mesano), CalendarUtil.to235959(CalendarUtil.incDias(new Date(), -1)));
			mesVenda.setTotalVendidoVendedoresAteOntem(totalVendidoVendedoresAteOntem);
			
			// setTotalVendidoGlobal
			mesVenda.setTotalVendidoGlobal(getVendaFinder().findTotalBy(mesVenda.getMesAno()));
			if (mesVenda.getTotalVendidoGlobal() != null) {
				if (totalVendidoGlobal.doubleValue() != mesVenda.getTotalVendidoGlobal().doubleValue()) {
					System.out.println("ué");
				}
			}

			if (mesVenda.getTotalVendidoGlobal() != null && totalVendidoVendedores != null) {
				mesVenda.setTotalVendidoExternos(mesVenda.getTotalVendidoGlobal().subtract(totalVendidoVendedores));
			}

			// setTotalHistoricoExterno
			if (totalVendidoVendedores != null && totalVendidoGlobal != null) {
				mesVenda.setTotalHistoricoExternos(totalVendidoGlobal.subtract(totalVendidoVendedores));
			}
		} else {
			
			BigDecimal totalVendidoVendedores = getVendaFinder().findTotalSomenteVendedoresInternosBy(mesano);
			
			// setTotalVendidoVendedores			
			mesVenda.setTotalVendidoVendedores(totalVendidoVendedores);
			
		}

		BigDecimal bdInflacao = BigDecimal.ONE.add(mesVenda.getInflacao().setScale(5, RoundingMode.HALF_DOWN)
				.divide(new BigDecimal(100.0000), RoundingMode.HALF_DOWN));

		// setTotalHistorico
		Date umAnoAtras = CalendarUtil.incDate(mesVenda.getMesAno(), -1, Calendar.YEAR);
		BigDecimal totalHistorico = getVendaFinder().findTotalBy(umAnoAtras);
		if (totalHistorico != null) {
			mesVenda.setTotalHistorico(totalHistorico);
		} else {
			MesVenda mesVendaHistorico = getMesVendaFinder()
					.findByMesAno(CalendarUtil.incDate(mesVenda.getMesAno(), -1, Calendar.YEAR));
			if (mesVendaHistorico != null) {
				mesVenda.setTotalHistorico(mesVendaHistorico.getTotalVendidoGlobal());
			}
		}

		// setTotalHistoricoVendedores
		BigDecimal totalHistoricoVendedores = getVendaFinder().findTotalSomenteVendedoresInternosBy(umAnoAtras);
		if (totalHistoricoVendedores != null) {
			mesVenda.setTotalHistoricoVendedores(totalHistoricoVendedores);
		} else {
			mesVenda.setTotalHistoricoVendedores(mesVenda.getTotalHistorico());
		}
		
		
		
		// setTotalHistoricoVendedoresAteHoje
		int qt = mesVenda.getQtdeDiasUteisAteHoje() == null ? 0 : mesVenda.getQtdeDiasUteisAteHoje();
		qt = qt <= 0 ? 0 : qt - 1;
		DiaUtil mesmoDiaUtilDeHojeNoAnoAnterior = getDiaUtilFinder().findDiasUteisComerciaisBy(umAnoAtras).get(qt);
		Date primeiroDiaMesAnoPassado = CalendarUtil.zeroDate(umAnoAtras);
		BigDecimal totalVendidoAteMesmoDiaUtilMesAnoPassado = getVendaFinder().findTotalSomenteVendedoresInternosBy(primeiroDiaMesAnoPassado, mesmoDiaUtilDeHojeNoAnoAnterior.getDia());
		mesVenda.setTotalHistoricoVendedoresAteOntem(totalVendidoAteMesmoDiaUtilMesAnoPassado);

		// setTotalHistoricoExterno
		if (totalHistoricoVendedores != null && totalHistorico != null) {
			mesVenda.setTotalHistoricoExternos(totalHistorico.subtract(totalHistoricoVendedores));
		}

		BigDecimal totalConsidInflacao = mesVenda.getTotalHistorico()
				.multiply(BigDecimal.ONE.add(mesVenda.getInflacao()
						.divide(new BigDecimal("100.00"), 20, RoundingMode.HALF_DOWN)));

		if (mesVenda.getInflacao() == null) {
			mesVenda.setInflacao(BigDecimal.ZERO);
		}

		// setMetaMinima
		mesVenda.setMetaMinima(totalConsidInflacao);

		// setMetaEsperada
		BigDecimal metaEsperada = mesVenda.getTotalHistorico().multiply(bdInflacao);
		metaEsperada = metaEsperada.multiply(new BigDecimal(1.03));
		metaEsperada = metaEsperada.setScale(2, RoundingMode.HALF_DOWN);
		mesVenda.setMetaEsperada(metaEsperada);

		// setMetaMinimaVendedores
		if ((mesVenda.getMetaMinima() != null) && (mesVenda.getQtdeVendedores() != null)
				&& (mesVenda.getQtdeVendedores() > 0)) {
			BigDecimal metaMinimaVendedor = new BigDecimal("19900.00");
			mesVenda.setMetaMinimaVendedores(new BigDecimal(CurrencyUtils
					.multiplica(metaMinimaVendedor.doubleValue(), mesVenda.getQtdeVendedores().doubleValue())));
		}

		// setMetaMinimaExterna
		if (mesVenda.getTotalHistoricoExternos() != null && bdInflacao != null) {
			mesVenda.setMetaMinimaExternos(mesVenda.getTotalHistoricoExternos().multiply(bdInflacao));
		}

		// setMetaEsperadaVendedores
		if ((mesVenda.getQtdeVendedores() != null) && (mesVenda.getQtdeVendedores() > 0)) {
			BigDecimal metaEsperadaVendedores = mesVenda.getTotalHistoricoVendedores().multiply(bdInflacao)
					.multiply(new BigDecimal(1.03)).setScale(2, RoundingMode.HALF_DOWN);
			// Se por algum motivo acabar sendo menor que a mínima estipulada, então considera-se a meta mínima
			if (metaEsperadaVendedores.doubleValue() < mesVenda.getMetaMinimaVendedores().doubleValue()) {
				metaEsperadaVendedores = mesVenda.getMetaMinimaVendedores();
			}
			mesVenda.setMetaEsperadaVendedores(metaEsperadaVendedores);
		}

		// setMetaEsperadaExternos
		if (mesVenda.getTotalHistoricoExternos() != null) {
			BigDecimal bdMetaEsperadaExterno = mesVenda.getTotalHistoricoExternos().multiply(bdInflacao)
					.multiply(new BigDecimal(1.03)).setScale(2, RoundingMode.HALF_DOWN);
			mesVenda.setMetaEsperadaExternos(bdMetaEsperadaExterno);
		}

		mesVenda.setQtdeDiasUteisComerciais(getDiaUtilFinder().findDiasUteisComerciaisBy(mesVenda.getMesAno()).size());

		// setQtdeDiasUteisAteHoje
		if (CalendarUtil.isMesmoMesAno(mesVenda.getMesAno(), new Date())) {
			mesVenda.setQtdeDiasUteisAteHoje(getDiaUtilFinder()
					.findDiasUteisComerciaisBy(CalendarUtil.primeiroDiaNoMesAno(mesVenda.getMesAno()), new Date())
					.size());
		} else {
			mesVenda.setQtdeDiasUteisAteHoje(mesVenda.getQtdeDiasUteisComerciais());
		}

		// setQtdeDiasUteisRestantes
		if ((mesVenda.getQtdeDiasUteisComerciais() != null) && (mesVenda.getQtdeDiasUteisAteHoje() != null)) {
			mesVenda.setQtdeDiasUteisRestantes(mesVenda.getQtdeDiasUteisComerciais()
					- mesVenda.getQtdeDiasUteisAteHoje() + 1);
		}

		// setMediaDiaria
		if ((mesVenda.getTotalVendidoGlobal() != null) && (mesVenda.getQtdeDiasUteisAteHoje() != null)
				&& (mesVenda.getQtdeDiasUteisAteHoje().doubleValue() != 0.0)) {
			mesVenda.setMediaDiaria(mesVenda.getTotalVendidoGlobal()
					.divide(CurrencyUtils.getBigDecimalCurrency(mesVenda.getQtdeDiasUteisAteHoje()
							.doubleValue()), 2, RoundingMode.HALF_DOWN));
		}

		// setMediaDiariaAtingirHistorico
		if ((mesVenda.getTotalHistorico() != null) && (mesVenda.getTotalVendidoGlobal() != null)
				&& (mesVenda.getQtdeDiasUteisComerciais() != null)
				&& (mesVenda.getQtdeDiasUteisComerciais().doubleValue() > 0)) {

			mesVenda.setMediaDiariaAtingirHistorico(mesVenda.getTotalHistorico()
					.divide(new BigDecimal(mesVenda.getQtdeDiasUteisComerciais()
							.toString()), 20, RoundingMode.HALF_DOWN));
		}

		// setMediaDiariaAtingirMetaMinima
		if ((mesVenda.getTotalVendidoGlobal() != null) && (mesVenda.getQtdeDiasUteisComerciais() != null)
				&& (mesVenda.getQtdeDiasUteisComerciais().doubleValue() > 0)) {

			mesVenda.setMediaDiariaAtingirMetaMinima(mesVenda.getMetaMinima()
					.divide(new BigDecimal(mesVenda.getQtdeDiasUteisComerciais()
							.toString()), 20, RoundingMode.HALF_DOWN));
		}

		// setMediaDiariaAtingirMetaEsperada
		if ((mesVenda.getTotalVendidoGlobal() != null) && (mesVenda.getQtdeDiasUteisComerciais() != null)
				&& (mesVenda.getQtdeDiasUteisComerciais().doubleValue() > 0)) {
			mesVenda.setMediaDiariaAtingirMetaEsperada(mesVenda.getMetaEsperada()
					.divide(new BigDecimal(mesVenda.getQtdeDiasUteisComerciais()
							.toString()), 20, RoundingMode.HALF_DOWN));
		}

		// setMediaDiariaAtingirHistoricoVendedores
		if ((mesVenda.getTotalHistoricoVendedores() != null) && (mesVenda.getTotalVendidoVendedores() != null)
				&& (mesVenda.getQtdeDiasUteisComerciais() != null)
				&& (mesVenda.getQtdeDiasUteisComerciais().doubleValue() > 0)) {

			mesVenda.setMediaDiariaAtingirHistoricoVendedores(mesVenda.getTotalHistoricoVendedores()
					.divide(new BigDecimal(mesVenda.getQtdeDiasUteisComerciais()
							.toString()), 20, RoundingMode.HALF_DOWN));
		}

		// setMediaDiariaAtingirMetaMinimaVendedores
		if ((mesVenda.getMetaMinimaVendedores() != null) && (mesVenda.getTotalVendidoVendedores() != null)
				&& (mesVenda.getQtdeDiasUteisComerciais() != null)
				&& (mesVenda.getQtdeDiasUteisComerciais().doubleValue() > 0)) {

			mesVenda.setMediaDiariaAtingirMetaMinimaVendedores(mesVenda.getMetaMinimaVendedores()
					.divide(new BigDecimal(mesVenda.getQtdeDiasUteisComerciais()
							.toString()), 20, RoundingMode.HALF_DOWN));
		}

		// setMediaDiariaAtingirMetaEsperadaVendedores
		if ((mesVenda.getMetaEsperadaVendedores() != null) && (mesVenda.getTotalVendidoVendedores() != null)
				&& (mesVenda.getQtdeDiasUteisComerciais() != null)
				&& (mesVenda.getQtdeDiasUteisComerciais().doubleValue() > 0)) {
			mesVenda.setMediaDiariaAtingirMetaEsperadaVendedores(mesVenda.getMetaEsperadaVendedores()
					.divide(new BigDecimal(mesVenda.getQtdeDiasUteisComerciais()
							.toString()), 20, RoundingMode.HALF_DOWN));
		}

		// setMediaDiariaAtingirHistoricoExternos
		if ((mesVenda.getTotalHistoricoExternos() != null) && (mesVenda.getTotalVendidoExternos() != null)
				&& (mesVenda.getQtdeDiasUteisComerciais() != null)
				&& (mesVenda.getQtdeDiasUteisComerciais().doubleValue() > 0)) {

			mesVenda.setMediaDiariaAtingirHistoricoExternos(mesVenda.getTotalHistoricoExternos()
					.divide(new BigDecimal(mesVenda.getQtdeDiasUteisComerciais()
							.toString()), 20, RoundingMode.HALF_DOWN));
		}

		// setMediaDiariaAtingirMetaMinimaExternos
		if ((mesVenda.getTotalVendidoExternos() != null) && (mesVenda.getQtdeDiasUteisComerciais() != null)
				&& (mesVenda.getQtdeDiasUteisComerciais().doubleValue() > 0)) {

			mesVenda.setMediaDiariaAtingirMetaMinimaExternos(mesVenda.getMetaMinimaExternos()
					.divide(new BigDecimal(mesVenda.getQtdeDiasUteisComerciais()
							.toString()), 20, RoundingMode.HALF_DOWN));
		}

		// setMediaDiariaAtingirMetaEsperadaExternos
		if ((mesVenda.getTotalVendidoExternos() != null) && (mesVenda.getQtdeDiasUteisComerciais() != null)
				&& (mesVenda.getQtdeDiasUteisComerciais().doubleValue() > 0)) {
			mesVenda.setMediaDiariaAtingirMetaEsperadaExternos(mesVenda.getMetaEsperadaExternos()
					.divide(new BigDecimal(mesVenda.getQtdeDiasUteisComerciais()
							.toString()), 20, RoundingMode.HALF_DOWN));
		}

		if (mesVenda.getItens() != null) {
			mesVenda.getItens().clear();
			mesVenda = getMesVendaDataMapper().save(mesVenda);
			getMesVendaDataMapper().getEntityManager().flush();
		} else {
			mesVenda.setItens(new ArrayList<MesVendaItem>());
		}
		getMesVendaDataMapper().getEntityIdHandler().handleEntityIdFilhos(itens);

		mesVenda.getItens().addAll(itens);
		mesVenda = getMesVendaDataMapper().save(mesVenda);

	}
	
	public List<Map<String,Object>> getDiarias(Date dtIni, Date dtFim) throws ViewException {
		
		List<Map<String,Object>> l = new ArrayList<Map<String,Object>>();
		
		List<Date> dias = CalendarUtil.buildDiasList(dtIni, dtFim);
		
		for (Date dia : dias) {
			
			BigDecimal totalGeral = getVendaFinder().findTotalBy(dia, dia);
			if (totalGeral == null) continue;
			
			Map<String,Object> m = new HashMap<String,Object>();
			
			BigDecimal totalInterno = getVendaFinder().findTotalSomenteVendedoresInternosBy(dia, dia);
			BigDecimal totalExterno = totalGeral.subtract(totalInterno);
			
			m.put("dia", dia);
			m.put("total_interno", totalInterno);
			m.put("total_externo", totalExterno);
			m.put("total_geral", totalGeral);
			l.add(m);
		}
		
		return l;
		
	}
	
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void processSave() {

	}

	public void preencherMesVendaList(List<MesVenda> mesesVendas) throws ViewException {
		for (MesVenda mesVenda : mesesVendas) {
			preencherMesVenda(mesVenda);
		}
	}

	public VendaFinder getVendaFinder() {
		return vendaFinder;
	}

	public void setVendaFinder(VendaFinder vendaFinder) {
		this.vendaFinder = vendaFinder;
	}

	public MesVendaFinder getMesVendaFinder() {
		return mesVendaFinder;
	}

	public void setMesVendaFinder(MesVendaFinder mesVendaFinder) {
		this.mesVendaFinder = mesVendaFinder;
	}

	public MesVendaDataMapper getMesVendaDataMapper() {
		return mesVendaDataMapper;
	}

	public void setMesVendaDataMapper(MesVendaDataMapper mesVendaDataMapper) {
		this.mesVendaDataMapper = mesVendaDataMapper;
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

	public MesVendaBusiness getThiz() {
		if (thiz == null) {
			thiz = (MesVendaBusiness) getBeanFactory().getBean("mesVendaBusiness");
		}
		return thiz;
	}

	public void setThiz(MesVendaBusiness thiz) {
		this.thiz = thiz;
	}

}
