package com.bonsucesso.erp.base.view;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ocabit.jsf.utils.JSFUtils;


@Component("testMB")
@Scope("view")
public class TestMB implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 2463416120592801345L;

	private List<Double> doublesList;

	private Map<Integer, Double> doublesMaps;

	private List<Integer> integersList;

	private List<Auxx> auxxList;

	private Double d = 123.456;

	private Integer i = 987;

	@PostConstruct
	public void init() {
		setDoublesList(new ArrayList<Double>());
		setIntegersList(new ArrayList<Integer>());
		setAuxxList(new ArrayList<Auxx>());

		setDoublesMaps(new HashMap<Integer, Double>());

		for (int i = 0; i < 7; i++) {
			getDoublesList().add(i + 0.123);
			getIntegersList().add(i == 3 ? null : i);
			getAuxxList().add(new Auxx((7 - i) + 0.999));
			getDoublesMaps().put(i, i + 0.321);
		}

	}

	public List<Double> getDoublesList() {
		return doublesList;
	}

	public void setDoublesList(List<Double> doublesList) {
		this.doublesList = doublesList;
	}

	public Map<Integer, Double> getDoublesMaps() {
		return doublesMaps;
	}

	public void setDoublesMaps(Map<Integer, Double> doublesMaps) {
		this.doublesMaps = doublesMaps;
	}

	public List<Integer> getIntegersList() {
		return integersList;
	}

	public void setIntegersList(List<Integer> integersList) {
		this.integersList = integersList;
	}

	public List<Auxx> getAuxxList() {
		return auxxList;
	}

	public void setAuxxList(List<Auxx> auxxList) {
		this.auxxList = auxxList;
	}

	public Double getD() {
		return d;
	}

	public void setD(Double d) {
		this.d = d;
	}

	public Integer getI() {
		return i;
	}

	public void setI(Integer i) {
		this.i = i;
	}

	public Double getValue(int i) {
		return getDoublesList().get(i);
	}

	public void doIt() {
		setD(Math.random());
		System.out.println(getD());
	}
	
	public void doItProgrammatically() {
		setD(Math.random());
		System.out.println(getD());
		JSFUtils.addCallbackParam("dlgId", "dlgMovimentacaoLote");
		JSFUtils.addCallbackParam("saved", true);
		JSFUtils.addInfoMsg("Registros salvos com sucesso.");
		JSFUtils.updateComponent("form");
	}

}
