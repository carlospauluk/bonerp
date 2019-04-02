package com.bonsucesso.erp.ekt.model;



import java.util.Date;

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
 * Tabela espelho para o arquivo PED_D020.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "ekt_pedido")
@EntityListeners({ UpperCaseListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class EKTPedido extends EntityIdImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 226976007899771805L;

	@Column(name = "RECORD_NUMBER")
	private Integer recordNumber;

	@Column(name = "PEDIDO")
	private Double pedido;

	@Column(name = "EMISSAO")
	private Date emissao;

	@Column(name = "FORNEC")
	private Double fornec;

	@Column(name = "DD1")
	private Double dd1;

	@Column(name = "DD2")
	private Double dd2;

	@Column(name = "DD3")
	private Double dd3;

	@Column(name = "DD4")
	private Double dd4;

	@Column(name = "DD5")
	private Double dd5;

	@Column(name = "ENTREGA")
	private Date entrega;

	@Column(name = "TOTAL")
	private Double total;

	@Column(name = "SUB_DEPTO")
	private Double subDepto;

	@Column(name = "QTDE")
	private Double qtde;

	@Column(name = "DESCNF")
	private Double descNf;

	@Column(name = "DESCDP")
	private Double descDp;

	@Column(name = "MES_ENT")
	private Double mesEnt;

	@Column(name = "ANO_ENT")
	private Double anoEnt;

	@Column(name = "PUNIT")
	private Double pUnit;

	@Column(name = "PTOTAL")
	private Double pTotal;

	@Column(name = "PRAZO")
	private Double prazo;

	@Column(name = "QTDEBX")
	private Double qtdeBx;

	@Column(name = "PTOTALBX")
	private Double pTotalBx;

	public Integer getRecordNumber() {
		return recordNumber;
	}

	public void setRecordNumber(Integer recordNumber) {
		this.recordNumber = recordNumber;
	}

	public Double getPedido() {
		return pedido;
	}

	public void setPedido(Double pedido) {
		this.pedido = pedido;
	}

	public Date getEmissao() {
		return emissao;
	}

	public void setEmissao(Date emissao) {
		this.emissao = emissao;
	}

	public Double getFornec() {
		return fornec;
	}

	public void setFornec(Double fornec) {
		this.fornec = fornec;
	}

	public Double getDd1() {
		return dd1;
	}

	public void setDd1(Double dd1) {
		this.dd1 = dd1;
	}

	public Double getDd2() {
		return dd2;
	}

	public void setDd2(Double dd2) {
		this.dd2 = dd2;
	}

	public Double getDd3() {
		return dd3;
	}

	public void setDd3(Double dd3) {
		this.dd3 = dd3;
	}

	public Double getDd4() {
		return dd4;
	}

	public void setDd4(Double dd4) {
		this.dd4 = dd4;
	}

	public Double getDd5() {
		return dd5;
	}

	public void setDd5(Double dd5) {
		this.dd5 = dd5;
	}

	public Date getEntrega() {
		return entrega;
	}

	public void setEntrega(Date entrega) {
		this.entrega = entrega;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Double getSubDepto() {
		return subDepto;
	}

	public void setSubDepto(Double subDepto) {
		this.subDepto = subDepto;
	}

	public Double getQtde() {
		return qtde;
	}

	public void setQtde(Double qtde) {
		this.qtde = qtde;
	}

	public Double getDescNf() {
		return descNf;
	}

	public void setDescNf(Double descNf) {
		this.descNf = descNf;
	}

	public Double getDescDp() {
		return descDp;
	}

	public void setDescDp(Double descDp) {
		this.descDp = descDp;
	}

	public Double getMesEnt() {
		return mesEnt;
	}

	public void setMesEnt(Double mesEnt) {
		this.mesEnt = mesEnt;
	}

	public Double getAnoEnt() {
		return anoEnt;
	}

	public void setAnoEnt(Double anoEnt) {
		this.anoEnt = anoEnt;
	}

	public Double getpUnit() {
		return pUnit;
	}

	public void setpUnit(Double pUnit) {
		this.pUnit = pUnit;
	}

	public Double getpTotal() {
		return pTotal;
	}

	public void setpTotal(Double pTotal) {
		this.pTotal = pTotal;
	}

	public Double getPrazo() {
		return prazo;
	}

	public void setPrazo(Double prazo) {
		this.prazo = prazo;
	}

	public Double getQtdeBx() {
		return qtdeBx;
	}

	public void setQtdeBx(Double qtdeBx) {
		this.qtdeBx = qtdeBx;
	}

	public Double getpTotalBx() {
		return pTotalBx;
	}

	public void setpTotalBx(Double pTotalBx) {
		this.pTotalBx = pTotalBx;
	}

}
