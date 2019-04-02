package com.bonsucesso.erp.estoque.results.model;



import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bonsucesso.erp.estoque.model.Fornecedor;
import com.bonsucesso.erp.estoque.model.Subdepartamento;
import com.ocabit.jsf.utils.JSFUtils;
import com.ocabit.utils.calendar.CalendarUtil;


/**
 * Classe que fornece uma estrutura de dados para lidar mais rapidamente com os dados de previsões de compra.
 * 
 * Contém duplicações úteis para que seja mais fácil buscar os dados organizados.
 * 
 * @author Carlos Eduardo Pauluk
 *
 */
public class ListHistoricoPrevisaoCompra {

	public Map<Integer, Integer> indiceAnos;

	public Map<Integer, Date> indiceMeses;

	private List<HistoricoPrevisaoCompra> list;

	private List<Fornecedor> fornecedores;

	private List<Subdepartamento> subdeptos;

	private Map<Fornecedor, List<HistoricoPrevisaoCompra>> mapByFornecedor;

	private Map<Subdepartamento, List<HistoricoPrevisaoCompra>> mapBySubdepto;

	public void addHistoricoPrevisaoCompra(HistoricoPrevisaoCompra hpc) {
		getList().add(hpc);
		if (!getFornecedores().contains(hpc.getFornecedor())) {
			getFornecedores().add(hpc.getFornecedor());
		}
		if (!getSubdeptos().contains(hpc.getSubdepto())) {
			getSubdeptos().add(hpc.getSubdepto());
		}

		if (!getMapByFornecedor().containsKey(hpc.getFornecedor())) {
			getMapByFornecedor().put(hpc.getFornecedor(), new ArrayList<HistoricoPrevisaoCompra>());
		}
		getMapByFornecedor().get(hpc.getFornecedor()).add(hpc);

		if (!getMapBySubdepto().containsKey(hpc.getSubdepto())) {
			getMapBySubdepto().put(hpc.getSubdepto(), new ArrayList<HistoricoPrevisaoCompra>());
		}
		getMapBySubdepto().get(hpc.getSubdepto()).add(hpc);
	}

	public List<HistoricoPrevisaoCompra> getList() {
		if (list == null) {
			list = new ArrayList<HistoricoPrevisaoCompra>();
		}
		return list;
	}

	public void setList(List<HistoricoPrevisaoCompra> list) {
		this.list = list;
	}

	public List<Fornecedor> getFornecedores() {
		if (fornecedores == null) {
			fornecedores = new ArrayList<Fornecedor>();
		}
		return fornecedores;
	}

	public void setFornecedores(List<Fornecedor> fornecedores) {
		this.fornecedores = fornecedores;
	}

	public List<Subdepartamento> getSubdeptos() {
		if (subdeptos == null) {
			subdeptos = new ArrayList<Subdepartamento>();
		}
		return subdeptos;
	}

	public void setSubdeptos(List<Subdepartamento> subdeptos) {
		this.subdeptos = subdeptos;
	}

	public Map<Fornecedor, List<HistoricoPrevisaoCompra>> getMapByFornecedor() {
		if (mapByFornecedor == null) {
			mapByFornecedor = new HashMap<Fornecedor, List<HistoricoPrevisaoCompra>>();
		}
		return mapByFornecedor;
	}

	public void setMapByFornecedor(Map<Fornecedor, List<HistoricoPrevisaoCompra>> mapByFornecedor) {
		this.mapByFornecedor = mapByFornecedor;
	}

	public Map<Subdepartamento, List<HistoricoPrevisaoCompra>> getMapBySubdepto() {
		if (mapBySubdepto == null) {
			mapBySubdepto = new HashMap<Subdepartamento, List<HistoricoPrevisaoCompra>>();
		}
		return mapBySubdepto;
	}

	public void setMapBySubdepto(Map<Subdepartamento, List<HistoricoPrevisaoCompra>> mapBySubdepto) {
		this.mapBySubdepto = mapBySubdepto;
	}

	public void addAll(List<HistoricoPrevisaoCompra> l, List<Fornecedor> fornecedores,
			List<Subdepartamento> subdeptos) {
		for (HistoricoPrevisaoCompra hpc : l) {
			if (fornecedores.contains(hpc.getFornecedor()) && subdeptos.contains(hpc.getSubdepto())) {
				addHistoricoPrevisaoCompra(hpc);
			}
		}
	}

	public Map<Integer, Integer> getIndiceAnos() {
		if (indiceAnos == null) {
			indiceAnos = new HashMap<Integer, Integer>();
		}
		// Se ainda não foi montado, monta...
		if (indiceAnos.size() == 0) {
			if (getList() != null && !getList().isEmpty()) {
				Integer primeiroAno = CalendarUtil.getCalendar(getList().get(0).getPrimeiroMesAno()).get(Calendar.YEAR);
				for (int i = 0; i < 5; i++) {
					indiceAnos.put(i, primeiroAno+i);
				}
			}
		}
		return indiceAnos;
	}
	
	public Integer getIndiceAnoByAno(Integer ano) {
		for (Map.Entry<Integer, Integer> e : getIndiceAnos().entrySet()) {
			if (e.getValue().equals(ano)) {
				return e.getKey();
			}
		}
		return null;
	}

	public void setIndiceAnos(Map<Integer, Integer> indiceAnos) {
		this.indiceAnos = indiceAnos;
	}

	public Map<Integer, Date> getIndiceMeses() {
		if (indiceMeses == null) {
			indiceMeses = new HashMap<Integer, Date>();
		}
		return indiceMeses;
	}

	public void setIndiceMeses(Map<Integer, Date> indiceMeses) {
		this.indiceMeses = indiceMeses;
	}

	public BigDecimal returnBy(HistoricoPrevisaoCompra hpc, Integer ano, Integer indiceMes) {
		try {
			Integer indiceAno = getIndiceAnoByAno(ano) + 1; // começa em 0
			Method get = HistoricoPrevisaoCompra.class
					.getDeclaredMethod("getAno" + indiceAno + "mes" + indiceMes);
			BigDecimal r = (BigDecimal) get.invoke(hpc);
			return r;
		} catch (Exception e) {
			JSFUtils.addErrorMsg("Erro ao retornar para fornecedor '"
					+ hpc.getFornecedor().getPessoa().getNomeFantasia() + ", subdepto '" + hpc.getSubdepto().getNome()
					+ "', ano = '" + ano + "', indiceMes = '" + indiceMes + "'");
			JSFUtils.addHandledException(e);
			return null;
		}
	}

}
