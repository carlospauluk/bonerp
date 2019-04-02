package com.bonsucesso.erp.estoque.relatorios.view;



import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ocabit.base.model.EntityId;
import com.ocabit.utils.calendar.CalendarUtil;


/**
 * Classe auxiliar para exibir os itens no datatable de Previsão de Compra.
 * 
 * @author Carlos Eduardo Pauluk
 *
 */
public class PrevisaoCompraRecord {

	/**
	 * Aqui pode ser um Fornecedor, Subdepartamento, Produto, etc.
	 */
	private EntityId entityId;

	private List<PrevisaoCompraRecord> filhos;

	private BigDecimal saldoNaDtBase;

	private BigDecimal saldoNaDtEntrega;

	private BigDecimal qtdePedido;

	// Marca a qtde de vendas até tais datas
	private List<DadosVendas> dadosVendas;

	// Para facilitar saber se, por exemplo, começa em Nov e vai até Abr (a ordem é 11, 12, 01, 02, 03, 04).
	private List<Integer> mesesEmOrdem;

	public PrevisaoCompraRecord() {
		super();
	}

	public PrevisaoCompraRecord(EntityId entityId) {
		super();
		this.entityId = entityId;
	}

	/**
	 * 
	 * @param ano
	 * @param mesAno
	 * @return
	 */
	public BigDecimal qtdeASerVendidaPrevista(Integer ano, Date mesAno) {
		int mes = CalendarUtil.getCalendar(mesAno).get(Calendar.MONTH) + 1;
		return getDadosVendaBy(mes).getVendasPassado().get(ano);
	}

	/**
	 * 
	 * @param ano
	 * @param mesAno
	 * @return
	 */
	public BigDecimal qtdeAcumuladaASerVendidaPrevista(Integer ano, Date mesAno) {
		int mes = CalendarUtil.getCalendar(mesAno).get(Calendar.MONTH) + 1;

		// Atenção: mesesAnosPrevisao tem que estar ordenado para funcionar
		BigDecimal acumulado = BigDecimal.ZERO;

		// Vai percorrendo os meses enquanto não for o mesmo mês
		for (Integer _mes : getMesesEmOrdem()) {
			BigDecimal qt = qtdeASerVendidaPrevista(ano, CalendarUtil.getDate(01, _mes, 9999)); // tanto faz o ano
			if (qt == null)
				qt = BigDecimal.ZERO;
			acumulado = acumulado.add(qt);
			if (_mes == mes) {
				break;
			}
		}

		return acumulado;
	}

	/**
	 * 
	 * @param ano
	 * @param mesAno
	 * @return
	 */
	public BigDecimal mediaVendas(Date mesAno) {
		int mes = CalendarUtil.getCalendar(mesAno).get(Calendar.MONTH) + 1;
		BigDecimal qtdeNotNull = BigDecimal.ZERO;
		BigDecimal totalVendas = BigDecimal.ZERO;
		for (BigDecimal venda : getDadosVendaBy(mes).getVendasPassado().values()) {
			if (venda != null) {
				qtdeNotNull = qtdeNotNull.add(BigDecimal.ONE);
				totalVendas = totalVendas.add(venda);
			}
		}
		qtdeNotNull = qtdeNotNull == null || qtdeNotNull.equals(BigDecimal.ZERO) ? BigDecimal.ONE : qtdeNotNull;
		return totalVendas.divide(qtdeNotNull, 0, RoundingMode.HALF_UP);
	}

	/**
	 * 
	 * @param ano
	 * @param mesAno
	 * @return
	 */
	public BigDecimal mediaVendasAcumulada(Date mesAno) {
		int mes = CalendarUtil.getCalendar(mesAno).get(Calendar.MONTH) + 1;

		// Atenção: mesesAnosPrevisao tem que estar ordenado para funcionar
		BigDecimal acumulado = BigDecimal.ZERO;

		// Vai percorrendo os meses enquanto não for o mesmo mês
		for (Integer _mes : getMesesEmOrdem()) {
			BigDecimal qt = mediaVendas(CalendarUtil.getDate(01, _mes, 9999)); // tanto faz o ano
			if (qt == null)
				qt = BigDecimal.ZERO;
			acumulado = acumulado.add(qt);
			if (_mes == mes) {
				break;
			}
		}
		return acumulado;
	}
	
	public EntityId getEntityId() {
		return entityId;
	}

	public void setEntityId(EntityId entityId) {
		this.entityId = entityId;
	}

	public DadosVendas getDadosVendaBy(Integer mes) {
		for (DadosVendas dv : getDadosVendas()) {
			if (mes == dv.getMesPrevisao()) {
				return dv;
			}
		}
		return null;
	}

	public List<PrevisaoCompraRecord> getFilhos() {
		if (filhos == null) {
			filhos = new ArrayList<PrevisaoCompraRecord>();
		}
		return filhos;
	}

	public void setFilhos(List<PrevisaoCompraRecord> filhos) {
		this.filhos = filhos;
	}

	public BigDecimal getSaldoNaDtBase() {
		return saldoNaDtBase;
	}

	public void setSaldoNaDtBase(BigDecimal saldoNaDtBase) {
		this.saldoNaDtBase = saldoNaDtBase;
	}

	public BigDecimal getSaldoNaDtEntrega() {
		return saldoNaDtEntrega;
	}

	public void setSaldoNaDtEntrega(BigDecimal saldoNaDtEntrega) {
		this.saldoNaDtEntrega = saldoNaDtEntrega;
	}

	public BigDecimal getQtdePedido() {
		return qtdePedido;
	}

	public void setQtdePedido(BigDecimal qtdePedido) {
		this.qtdePedido = qtdePedido;
	}

	public List<DadosVendas> getDadosVendas() {
		if (dadosVendas == null) {
			dadosVendas = new ArrayList<DadosVendas>();
		}
		return dadosVendas;
	}

	public void setDadosVendas(List<DadosVendas> dadosVendas) {
		this.dadosVendas = dadosVendas;
	}

	public List<Integer> getMesesEmOrdem() {
		if (mesesEmOrdem == null) {
			mesesEmOrdem = new ArrayList<Integer>();
		}

		return mesesEmOrdem;
	}

	public void setMesesEmOrdem(List<Integer> mesesEmOrdem) {
		this.mesesEmOrdem = mesesEmOrdem;
	}

}



/**
 * Classe para substituir os maps de maps...
 * 
 * @author Carlos
 *
 */
class DadosVendas {

	private Integer mesPrevisao;

	// Vendas por ano de previsão
	private Map<Integer, BigDecimal> vendasPassado;

	public Integer getMesPrevisao() {
		return mesPrevisao;
	}

	public void setMesPrevisao(Integer mesPrevisao) {
		this.mesPrevisao = mesPrevisao;
	}

	public Map<Integer, BigDecimal> getVendasPassado() {
		if (vendasPassado == null) {
			vendasPassado = new HashMap<Integer, BigDecimal>();
		}
		return vendasPassado;
	}

	public void setVendasPassado(Map<Integer, BigDecimal> vendasPassado) {
		this.vendasPassado = vendasPassado;
	}

}
