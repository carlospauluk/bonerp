package com.bonsucesso.erp.base.view;



public class Auxx {

	private Double d;

	public Auxx() {

	}

	public Auxx(Double d) {
		setD(d);
	}

	public Double getD() {
		return d;
	}

	public void setD(Double d) {
		this.d = d;
	}

	@Override
	public String toString() {
		return d != null ? d.toString() : null;
	}

}
