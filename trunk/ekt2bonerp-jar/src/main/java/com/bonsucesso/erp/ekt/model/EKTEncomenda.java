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
 * Tabela espelho para o arquivo PED_D070.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "ekt_encomenda")
@EntityListeners({ UpperCaseListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class EKTEncomenda extends EntityIdImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8774244273967632247L;

	@Column(name = "RECORD_NUMBER")
	private Integer recordNumber;

	@Column(name = "NUMERO")
	private Double numero;

	@Column(name = "SERIE", length = 1)
	private String serie;

	@Column(name = "EMISSAO")
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
	private Date v1;

	@Column(name = "V2")
	private Date v2;

	@Column(name = "V3")
	private Date v3;

	@Column(name = "V4")
	private Date v4;

	@Column(name = "V5")
	private Date v5;

	@Column(name = "V6")
	private Date v6;

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

	@Column(name = "CLIENTE")
	private Double cliente;

	@Column(name = "FONE", length = 11)
	private String fone;

	@Column(name = "PRAZO", length = 3)
	private String prazo;

	@Column(name = "SDO_PAGAR")
	private Double sdoPagar;

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

	public Double getCliente() {
		return cliente;
	}

	public void setCliente(Double cliente) {
		this.cliente = cliente;
	}

	public String getFone() {
		return fone;
	}

	public void setFone(String fone) {
		this.fone = fone;
	}

	public String getPrazo() {
		return prazo;
	}

	public void setPrazo(String prazo) {
		this.prazo = prazo;
	}

	public Double getSdoPagar() {
		return sdoPagar;
	}

	public void setSdoPagar(Double sdoPagar) {
		this.sdoPagar = sdoPagar;
	}

	@Override
	public String toStringToView() {
		return "EKTEncomenda [numero=" + numero + ", emissao=" + emissao + ", vendedor=" + vendedor + ", subTotal="
				+ subTotal + ", total=" + total + "]";
	}
	
	

}
