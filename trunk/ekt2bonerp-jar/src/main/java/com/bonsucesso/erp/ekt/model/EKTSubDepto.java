package com.bonsucesso.erp.ekt.model;



import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.bonsucesso.erp.estoque.model.Subdepartamento;
import com.ocabit.base.model.EntityIdImpl;
import com.ocabit.jpa.listener.UpperCaseListener;


/**
 * Tabela espelho para o arquivo EST_D004.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "ekt_subdepto")
@EntityListeners({ UpperCaseListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class EKTSubDepto extends EntityIdImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = -4263908314739062227L;

	@Column(name = "RECORD_NUMBER")
	private Integer recordNumber;

	@Column(name = "CODIGO")
	private Double codigo;

	@Column(name = "DESCRICAO", length = 25)
	private String descricao;

	@Column(name = "MARGEM")
	private Double margem;

	@Column(name = "PECAS_AC01")
	private Double pecasAc01;

	@Column(name = "PECAS_AC02")
	private Double pecasAc02;

	@Column(name = "PECAS_AC03")
	private Double pecasAc03;

	@Column(name = "PECAS_AC04")
	private Double pecasAc04;

	@Column(name = "PECAS_AC05")
	private Double pecasAc05;

	@Column(name = "PECAS_AC06")
	private Double pecasAc06;

	@Column(name = "PECAS_AC07")
	private Double pecasAc07;

	@Column(name = "PECAS_AC08")
	private Double pecasAc08;

	@Column(name = "PECAS_AC09")
	private Double pecasAc09;

	@Column(name = "PECAS_AC10")
	private Double pecasAc10;

	@Column(name = "PECAS_AC11")
	private Double pecasAc11;

	@Column(name = "PECAS_AC12")
	private Double pecasAc12;

	@Column(name = "VENDAS_AC01")
	private Double vendasAc01;

	@Column(name = "VENDAS_AC02")
	private Double vendasAc02;

	@Column(name = "VENDAS_AC03")
	private Double vendasAc03;

	@Column(name = "VENDAS_AC04")
	private Double vendasAc04;

	@Column(name = "VENDAS_AC05")
	private Double vendasAc05;

	@Column(name = "VENDAS_AC06")
	private Double vendasAc06;

	@Column(name = "VENDAS_AC07")
	private Double vendasAc07;

	@Column(name = "VENDAS_AC08")
	private Double vendasAc08;

	@Column(name = "VENDAS_AC09")
	private Double vendasAc09;

	@Column(name = "VENDAS_AC10")
	private Double vendasAc10;

	@Column(name = "VENDAS_AC11")
	private Double vendasAc11;

	@Column(name = "VENDAS_AC12")
	private Double vendasAc12;

	@Column(name = "SAZON", length = 1)
	private String sazon;

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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getMargem() {
		return margem;
	}

	public void setMargem(Double margem) {
		this.margem = margem;
	}

	public Double getPecasAc01() {
		return pecasAc01;
	}

	public void setPecasAc01(Double pecasAc01) {
		this.pecasAc01 = pecasAc01;
	}

	public Double getPecasAc02() {
		return pecasAc02;
	}

	public void setPecasAc02(Double pecasAc02) {
		this.pecasAc02 = pecasAc02;
	}

	public Double getPecasAc03() {
		return pecasAc03;
	}

	public void setPecasAc03(Double pecasAc03) {
		this.pecasAc03 = pecasAc03;
	}

	public Double getPecasAc04() {
		return pecasAc04;
	}

	public void setPecasAc04(Double pecasAc04) {
		this.pecasAc04 = pecasAc04;
	}

	public Double getPecasAc05() {
		return pecasAc05;
	}

	public void setPecasAc05(Double pecasAc05) {
		this.pecasAc05 = pecasAc05;
	}

	public Double getPecasAc06() {
		return pecasAc06;
	}

	public void setPecasAc06(Double pecasAc06) {
		this.pecasAc06 = pecasAc06;
	}

	public Double getPecasAc07() {
		return pecasAc07;
	}

	public void setPecasAc07(Double pecasAc07) {
		this.pecasAc07 = pecasAc07;
	}

	public Double getPecasAc08() {
		return pecasAc08;
	}

	public void setPecasAc08(Double pecasAc08) {
		this.pecasAc08 = pecasAc08;
	}

	public Double getPecasAc09() {
		return pecasAc09;
	}

	public void setPecasAc09(Double pecasAc09) {
		this.pecasAc09 = pecasAc09;
	}

	public Double getPecasAc10() {
		return pecasAc10;
	}

	public void setPecasAc10(Double pecasAc10) {
		this.pecasAc10 = pecasAc10;
	}

	public Double getPecasAc11() {
		return pecasAc11;
	}

	public void setPecasAc11(Double pecasAc11) {
		this.pecasAc11 = pecasAc11;
	}

	public Double getPecasAc12() {
		return pecasAc12;
	}

	public void setPecasAc12(Double pecasAc12) {
		this.pecasAc12 = pecasAc12;
	}

	public Double getVendasAc01() {
		return vendasAc01;
	}

	public void setVendasAc01(Double vendasAc01) {
		this.vendasAc01 = vendasAc01;
	}

	public Double getVendasAc02() {
		return vendasAc02;
	}

	public void setVendasAc02(Double vendasAc02) {
		this.vendasAc02 = vendasAc02;
	}

	public Double getVendasAc03() {
		return vendasAc03;
	}

	public void setVendasAc03(Double vendasAc03) {
		this.vendasAc03 = vendasAc03;
	}

	public Double getVendasAc04() {
		return vendasAc04;
	}

	public void setVendasAc04(Double vendasAc04) {
		this.vendasAc04 = vendasAc04;
	}

	public Double getVendasAc05() {
		return vendasAc05;
	}

	public void setVendasAc05(Double vendasAc05) {
		this.vendasAc05 = vendasAc05;
	}

	public Double getVendasAc06() {
		return vendasAc06;
	}

	public void setVendasAc06(Double vendasAc06) {
		this.vendasAc06 = vendasAc06;
	}

	public Double getVendasAc07() {
		return vendasAc07;
	}

	public void setVendasAc07(Double vendasAc07) {
		this.vendasAc07 = vendasAc07;
	}

	public Double getVendasAc08() {
		return vendasAc08;
	}

	public void setVendasAc08(Double vendasAc08) {
		this.vendasAc08 = vendasAc08;
	}

	public Double getVendasAc09() {
		return vendasAc09;
	}

	public void setVendasAc09(Double vendasAc09) {
		this.vendasAc09 = vendasAc09;
	}

	public Double getVendasAc10() {
		return vendasAc10;
	}

	public void setVendasAc10(Double vendasAc10) {
		this.vendasAc10 = vendasAc10;
	}

	public Double getVendasAc11() {
		return vendasAc11;
	}

	public void setVendasAc11(Double vendasAc11) {
		this.vendasAc11 = vendasAc11;
	}

	public Double getVendasAc12() {
		return vendasAc12;
	}

	public void setVendasAc12(Double vendasAc12) {
		this.vendasAc12 = vendasAc12;
	}

	public String getSazon() {
		return sazon;
	}

	public void setSazon(String sazon) {
		this.sazon = sazon;
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean equalsToSubdepartamento(Subdepartamento subDepto) {
		return new HashCodeBuilder(3, 7)
				.append(codigo)
				.append(descricao)
				.toHashCode() == new HashCodeBuilder(3, 7)
						.append(subDepto.getCodigo())
						.append(subDepto.getNome())
						.toHashCode();
	}

}
