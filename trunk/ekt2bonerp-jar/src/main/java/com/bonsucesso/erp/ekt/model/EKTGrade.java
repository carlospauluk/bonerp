package com.bonsucesso.erp.ekt.model;



import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.ocabit.base.model.EntityIdImpl;
import com.ocabit.jpa.listener.UpperCaseListener;


/**
 * Tabela espelho para o arquivo EST_D005.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "ekt_grade")
@EntityListeners({ UpperCaseListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class EKTGrade extends EntityIdImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = -9009964086184917422L;

	@Column(name = "RECORD_NUMBER")
	private Integer recordNumber;

	@Column(name = "CODIGO")
	private Double codigo;

	@Column(name = "GRA01", length = 12)
	private String gra01;

	@Column(name = "GRA02", length = 12)
	private String gra02;

	@Column(name = "GRA03", length = 12)
	private String gra03;

	@Column(name = "GRA04", length = 12)
	private String gra04;

	@Column(name = "GRA05", length = 12)
	private String gra05;

	@Column(name = "GRA06", length = 12)
	private String gra06;

	@Column(name = "GRA07", length = 12)
	private String gra07;

	@Column(name = "GRA08", length = 12)
	private String gra08;

	@Column(name = "GRA09", length = 12)
	private String gra09;

	@Column(name = "GRA10", length = 12)
	private String gra10;

	@Column(name = "GRA11", length = 12)
	private String gra11;

	@Column(name = "GRA12", length = 12)
	private String gra12;

	@Column(name = "GRA13", length = 12)
	private String gra13;

	@Column(name = "GRA14", length = 12)
	private String gra14;

	@Column(name = "GRA15", length = 12)
	private String gra15;

	@Column(name = "DEC_", length = 12)
	private String dec;

	public Integer getRecordNumber() {
		return recordNumber;
	}

	public void setRecordNumber(Integer recordNumber) {
		this.recordNumber = recordNumber;
	}

	public Double getCodigo() {
		return codigo;
	}

	public void setCodigo(Double codigo) {
		this.codigo = codigo;
	}

	public String getGra01() {
		return gra01;
	}

	public void setGra01(String gra01) {
		this.gra01 = gra01;
	}

	public String getGra02() {
		return gra02;
	}

	public void setGra02(String gra02) {
		this.gra02 = gra02;
	}

	public String getGra03() {
		return gra03;
	}

	public void setGra03(String gra03) {
		this.gra03 = gra03;
	}

	public String getGra04() {
		return gra04;
	}

	public void setGra04(String gra04) {
		this.gra04 = gra04;
	}

	public String getGra05() {
		return gra05;
	}

	public void setGra05(String gra05) {
		this.gra05 = gra05;
	}

	public String getGra06() {
		return gra06;
	}

	public void setGra06(String gra06) {
		this.gra06 = gra06;
	}

	public String getGra07() {
		return gra07;
	}

	public void setGra07(String gra07) {
		this.gra07 = gra07;
	}

	public String getGra08() {
		return gra08;
	}

	public void setGra08(String gra08) {
		this.gra08 = gra08;
	}

	public String getGra09() {
		return gra09;
	}

	public void setGra09(String gra09) {
		this.gra09 = gra09;
	}

	public String getGra10() {
		return gra10;
	}

	public void setGra10(String gra10) {
		this.gra10 = gra10;
	}

	public String getGra11() {
		return gra11;
	}

	public void setGra11(String gra11) {
		this.gra11 = gra11;
	}

	public String getGra12() {
		return gra12;
	}

	public void setGra12(String gra12) {
		this.gra12 = gra12;
	}

	public String getGra13() {
		return gra13;
	}

	public void setGra13(String gra13) {
		this.gra13 = gra13;
	}

	public String getGra14() {
		return gra14;
	}

	public void setGra14(String gra14) {
		this.gra14 = gra14;
	}

	public String getGra15() {
		return gra15;
	}

	public void setGra15(String gra15) {
		this.gra15 = gra15;
	}

	public String getDec() {
		return dec;
	}

	public void setDec(String dec) {
		this.dec = dec;
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
