package com.bonsucesso.erp.ekt.model;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.ocabit.base.model.EntityIdImpl;
import com.ocabit.jpa.listener.UpperCaseListener;


/**
 * Tabela espelho para o arquivo VEN_D060.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "ekt_venda")
@EntityListeners({ UpperCaseListener.class })
@DynamicUpdate(true)
@DynamicInsert(true)
public class EKTVenda extends EntityIdImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 692178006049824114L;
	
	@Column(name = "mesano", length = 4)
	private String mesano;
	
	public String getMesano() {
		return mesano;
	}

	public void setMesano(String mesano) {
		this.mesano = mesano;
	}

	@Column(name = "RECORD_NUMBER")
	private Integer recordNumber;

	@Column(name = "NUMERO")
	private Double numero;

	@Column(name = "SERIE", length = 1)
	private String serie;

	@Column(name = "EMISSAO")
	@Temporal(TemporalType.DATE)
	private Date emissao;

	@Column(name = "VENDEDOR")
	private Double vendedor;

	@Column(name = "COD_PLANO")
	private Double codPlano;

	@Column(name = "PLANO", length = 50)
	private String plano;

	@Column(name = "MENSAGEM", length = 50)
	private String mensagem;

	@Column(name = "HIST_DESC", length = 40)
	private String histDesc;

	@Column(name = "SUB_TOTAL")
	private Double subTotal;

	@Column(name = "DESC_ACRES")
	private Double descAcres;

	@Column(name = "DESC_ESPECIAL")
	private Double descEspecial;

	@Column(name = "TOTAL")
	private Double total;

	@Column(name = "NOME_CLIENTE", length = 15)
	private String nomeCliente;

	@Column(name = "COND_PAG", length = 4)
	private String condPag;

	@Column(name = "FLAG_DV", length = 1)
	private String flagDV;

	@Column(name = "EMITIDO", length = 1)
	private String emitido;

	@Column(name = "V1")
	@Temporal(TemporalType.DATE)
	private Date v1;

	@Column(name = "V2")
	@Temporal(TemporalType.DATE)
	private Date v2;

	@Column(name = "V3")
	@Temporal(TemporalType.DATE)
	private Date v3;

	@Column(name = "V4")
	@Temporal(TemporalType.DATE)
	private Date v4;

	@Column(name = "V5")
	@Temporal(TemporalType.DATE)
	private Date v5;

	@Column(name = "V6")
	@Temporal(TemporalType.DATE)
	private Date v6;

	@Column(name = "V7")
	@Temporal(TemporalType.DATE)
	private Date v7;

	@Column(name = "P1")
	private Double p1;

	@Column(name = "P2")
	private Double p2;

	@Column(name = "P3")
	private Double p3;

	@Column(name = "P4")
	private Double p4;

	@Column(name = "P5")
	private Double p5;

	@Column(name = "P6")
	private Double p6;

	@Column(name = "P7")
	private Double p7;

	@Column(name = "CLIENTE")
	private Double cliente;

	@Column(name = "V8")
	@Temporal(TemporalType.DATE)
	private Date v8;

	@Column(name = "V9")
	@Temporal(TemporalType.DATE)
	private Date v9;

	@Column(name = "V10")
	@Temporal(TemporalType.DATE)
	private Date v10;

	@Column(name = "V11")
	@Temporal(TemporalType.DATE)
	private Date v11;

	@Column(name = "V12")
	@Temporal(TemporalType.DATE)
	private Date v12;

	@Column(name = "V13")
	@Temporal(TemporalType.DATE)
	private Date v13;

	@Column(name = "P8")
	private Double p8;

	@Column(name = "P9")
	private Double p9;

	@Column(name = "P10")
	private Double p10;

	@Column(name = "P11")
	private Double p11;

	@Column(name = "P12")
	private Double p12;

	@Column(name = "P13")
	private Double p13;

	public Integer getRecordNumber() {
		return recordNumber;
	}

	public void setRecordNumber(Integer recordNumber) {
		this.recordNumber = recordNumber;
	}

	public Double getNumero() {
		return numero;
	}

	public void setNumero(Double numero) {
		this.numero = numero;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public Date getEmissao() {
		return emissao;
	}

	public void setEmissao(Date emissao) {
		this.emissao = emissao;
	}

	public Double getVendedor() {
		return vendedor;
	}

	public void setVendedor(Double vendedor) {
		this.vendedor = vendedor;
	}

	public Double getCodPlano() {
		return codPlano;
	}

	public void setCodPlano(Double codPlano) {
		this.codPlano = codPlano;
	}

	public String getPlano() {
		return plano;
	}

	public void setPlano(String plano) {
		this.plano = plano;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getHistDesc() {
		return histDesc;
	}

	public void setHistDesc(String histDesc) {
		this.histDesc = histDesc;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	public Double getDescAcres() {
		return descAcres;
	}

	public void setDescAcres(Double descAcres) {
		this.descAcres = descAcres;
	}

	public Double getDescEspecial() {
		return descEspecial;
	}

	public void setDescEspecial(Double descEspecial) {
		this.descEspecial = descEspecial;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getCondPag() {
		return condPag;
	}

	public void setCondPag(String condPag) {
		this.condPag = condPag;
	}

	public String getFlagDV() {
		return flagDV;
	}

	public void setFlagDV(String flagDV) {
		this.flagDV = flagDV;
	}

	public String getEmitido() {
		return emitido;
	}

	public void setEmitido(String emitido) {
		this.emitido = emitido;
	}

	public Date getV1() {
		return v1;
	}

	public void setV1(Date v1) {
		this.v1 = v1;
	}

	public Date getV2() {
		return v2;
	}

	public void setV2(Date v2) {
		this.v2 = v2;
	}

	public Date getV3() {
		return v3;
	}

	public void setV3(Date v3) {
		this.v3 = v3;
	}

	public Date getV4() {
		return v4;
	}

	public void setV4(Date v4) {
		this.v4 = v4;
	}

	public Date getV5() {
		return v5;
	}

	public void setV5(Date v5) {
		this.v5 = v5;
	}

	public Date getV6() {
		return v6;
	}

	public void setV6(Date v6) {
		this.v6 = v6;
	}

	public Date getV7() {
		return v7;
	}

	public void setV7(Date v7) {
		this.v7 = v7;
	}

	public Double getP1() {
		return p1;
	}

	public void setP1(Double p1) {
		this.p1 = p1;
	}

	public Double getP2() {
		return p2;
	}

	public void setP2(Double p2) {
		this.p2 = p2;
	}

	public Double getP3() {
		return p3;
	}

	public void setP3(Double p3) {
		this.p3 = p3;
	}

	public Double getP4() {
		return p4;
	}

	public void setP4(Double p4) {
		this.p4 = p4;
	}

	public Double getP5() {
		return p5;
	}

	public void setP5(Double p5) {
		this.p5 = p5;
	}

	public Double getP6() {
		return p6;
	}

	public void setP6(Double p6) {
		this.p6 = p6;
	}

	public Double getP7() {
		return p7;
	}

	public void setP7(Double p7) {
		this.p7 = p7;
	}

	public Double getCliente() {
		return cliente;
	}

	public void setCliente(Double cliente) {
		this.cliente = cliente;
	}

	public Date getV8() {
		return v8;
	}

	public void setV8(Date v8) {
		this.v8 = v8;
	}

	public Date getV9() {
		return v9;
	}

	public void setV9(Date v9) {
		this.v9 = v9;
	}

	public Date getV10() {
		return v10;
	}

	public void setV10(Date v10) {
		this.v10 = v10;
	}

	public Date getV11() {
		return v11;
	}

	public void setV11(Date v11) {
		this.v11 = v11;
	}

	public Date getV12() {
		return v12;
	}

	public void setV12(Date v12) {
		this.v12 = v12;
	}

	public Date getV13() {
		return v13;
	}

	public void setV13(Date v13) {
		this.v13 = v13;
	}

	public Double getP8() {
		return p8;
	}

	public void setP8(Double p8) {
		this.p8 = p8;
	}

	public Double getP9() {
		return p9;
	}

	public void setP9(Double p9) {
		this.p9 = p9;
	}

	public Double getP10() {
		return p10;
	}

	public void setP10(Double p10) {
		this.p10 = p10;
	}

	public Double getP11() {
		return p11;
	}

	public void setP11(Double p11) {
		this.p11 = p11;
	}

	public Double getP12() {
		return p12;
	}

	public void setP12(Double p12) {
		this.p12 = p12;
	}

	public Double getP13() {
		return p13;
	}

	public void setP13(Double p13) {
		this.p13 = p13;
	}

	@Override
	public String toStringToView() {
		return "EKTVenda [mesano=" + mesano + ", numero=" + numero + ", serie=" + serie + ", emissao=" + emissao + "]";
	}

}
