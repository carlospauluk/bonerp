package com.bonsucesso.erp.estoque.business;



import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.estoque.model.Fornecedor;
import com.bonsucesso.erp.estoque.model.Subdepartamento;
import com.bonsucesso.erp.vendas.data.VendaFinder;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.utils.calendar.CalendarUtil;


/**
 * Business para a entidades do pacote com.bonsucesso.erp.estoque.results.model.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("vendaResultsBusiness")
public class VendaResultsBusiness implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6268170908477188433L;

	@Autowired
	private VendaFinder vendaFinder;

	/**
	 * Monta a estrutura com chave principal sendo o Fornecedor.
	 * 
	 * @param mesAnoIni
	 * @param mesAnoFim
	 * @return
	 * @throws ViewException
	 */
	public Map<Fornecedor, Map<Subdepartamento, Map<Date, BigDecimal>>> buildVendasResultsByFornecedor(Date mesAnoIni,
			Date mesAnoFim) throws ViewException {

		// Monta um resultado com a seguinte "hierarquia"
		// - Fornecedor
		// -- Subdepto
		// --- mesano
		// ---- qtdeVendida

		Map<Fornecedor, Map<Subdepartamento, Map<Date, BigDecimal>>> r = new HashMap<Fornecedor, Map<Subdepartamento, Map<Date, BigDecimal>>>();

		// Retorna 4 colunas: r.fornecedor, r.subdepto, r.mesano, r.qtde
		List<Object[]> l = getVendaFinder().findQtdeItensVendidosBy(mesAnoIni, mesAnoFim);

		// Ordeno por fornecedor
		Collections.sort(l, new Comparator<Object[]>() {

			@Override
			public int compare(Object[] o1, Object[] o2) {
				Fornecedor f1 = (Fornecedor) o1[0];
				Fornecedor f2 = (Fornecedor) o2[0];
				return new CompareToBuilder()
						.append(f1.getId(), f2.getId())
						.toComparison();

			}
		});

		List<Date> mesAnoList = CalendarUtil.buildMesAnoList(mesAnoIni, mesAnoFim);

		for (Object[] i : l) {

			Fornecedor fornecedor = (Fornecedor) i[0];
			Subdepartamento subdepto = (Subdepartamento) i[1];
			Date mesano = CalendarUtil.primeiroDiaNoMesAno((Date) i[2]);
			BigDecimal qtde = (BigDecimal) i[3];
			qtde = qtde == null ? BigDecimal.ZERO : qtde;

			Map<Subdepartamento, Map<Date, BigDecimal>> linhaFornecedor;
			if (!r.containsKey(fornecedor)) {
				linhaFornecedor = new HashMap<Subdepartamento, Map<Date, BigDecimal>>();
				r.put(fornecedor, linhaFornecedor);
			} else {
				linhaFornecedor = r.get(fornecedor);
			}

			Map<Date, BigDecimal> linhaSubdepto;
			if (!linhaFornecedor.containsKey(subdepto)) {
				linhaSubdepto = new HashMap<Date, BigDecimal>();
				linhaFornecedor.put(subdepto, linhaSubdepto);
				// já preenche com todos os mesano da lista
				for (Date cMesAno : mesAnoList) {
					linhaSubdepto.put(cMesAno, BigDecimal.ZERO);
				}
			} else {
				linhaSubdepto = linhaFornecedor.get(subdepto);
			}

			if (!linhaSubdepto.containsKey(mesano)) {
				throw new ViewException("mesano já deveria estar na lista"); // já deveria ter sido preenchido no for acima
			}

			linhaSubdepto.put(mesano, qtde);

		}

		return r;

	}
	
	/**
	 * Monta a estrutura com chave principal sendo o Subdepto.
	 * 
	 * @param mesAnoIni
	 * @param mesAnoFim
	 * @return
	 * @throws ViewException
	 */
	public Map<Subdepartamento, Map<Fornecedor, Map<Date, BigDecimal>>> buildVendasResultsBySubdepto(Date mesAnoIni,
			Date mesAnoFim) throws ViewException {
		
		// Monta um resultado com a seguinte "hierarquia"
		// - Subdepto
		// -- Fornecedor
		// --- mesano
		// ---- qtdeVendida
		
		Map<Subdepartamento, Map<Fornecedor, Map<Date, BigDecimal>>> r = new HashMap<Subdepartamento, Map<Fornecedor, Map<Date, BigDecimal>>>();
		
		List<Object[]> l = getVendaFinder().findQtdeItensVendidosBy(mesAnoIni, mesAnoFim);
		
		// Ordeno por fornecedor
		Collections.sort(l, new Comparator<Object[]>() {
			
			@Override
			public int compare(Object[] o1, Object[] o2) {
				Subdepartamento f1 = (Subdepartamento) o1[1];
				Subdepartamento f2 = (Subdepartamento) o2[1];
				return new CompareToBuilder()
						.append(f1.getId(), f2.getId())
						.toComparison();
				
			}
		});
		
		List<Date> mesAnoList = CalendarUtil.buildMesAnoList(mesAnoIni, mesAnoFim);
		
		for (Object[] i : l) {
			
			Fornecedor fornecedor = (Fornecedor) i[0];
			Subdepartamento subdepto = (Subdepartamento) i[1];
			Date mesano = CalendarUtil.primeiroDiaNoMesAno((Date) i[2]);
			BigDecimal qtde = (BigDecimal) i[3];
			qtde = qtde == null ? BigDecimal.ZERO : qtde;
			
			Map<Fornecedor, Map<Date, BigDecimal>> linhaFornecedor;
			if (!r.containsKey(subdepto)) {
				linhaFornecedor = new HashMap<Fornecedor, Map<Date, BigDecimal>>();
				r.put(subdepto, linhaFornecedor);
			} else {
				linhaFornecedor = r.get(subdepto);
			}
			
			Map<Date, BigDecimal> linhaMesAnoQtde;
			if (!linhaFornecedor.containsKey(fornecedor)) {
				linhaMesAnoQtde = new HashMap<Date, BigDecimal>();
				linhaFornecedor.put(fornecedor, linhaMesAnoQtde);
				// já preenche com todos os mesano da lista
				for (Date cMesAno : mesAnoList) {
					linhaMesAnoQtde.put(cMesAno, BigDecimal.ZERO);
				}
			} else {
				linhaMesAnoQtde = linhaFornecedor.get(fornecedor);
			}
			
			if (!linhaMesAnoQtde.containsKey(mesano)) {
				throw new ViewException("mesano já deveria estar na lista"); // já deveria ter sido preenchido no for acima
			}
			
			linhaMesAnoQtde.put(mesano, qtde);
			
		}
		
		return r;
		
	}

	public VendaFinder getVendaFinder() {
		return vendaFinder;
	}

	public void setVendaFinder(VendaFinder vendaFinder) {
		this.vendaFinder = vendaFinder;
	}

}
