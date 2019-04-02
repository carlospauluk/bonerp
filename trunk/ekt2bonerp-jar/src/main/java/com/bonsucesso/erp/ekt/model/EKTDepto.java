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

import com.bonsucesso.erp.estoque.model.Departamento;
import com.ocabit.base.model.EntityIdImpl;
import com.ocabit.jpa.listener.UpperCaseListener;


/**
 * Tabela espelho para o arquivo EST_D003.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "ekt_depto")
@EntityListeners({ UpperCaseListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class EKTDepto extends EntityIdImpl {

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

	@Column(name = "PECAS_AC")
	private Double pecasAc;

	@Column(name = "VENDAS_AC")
	private Double vendasAc;

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

	public Double getPecasAc() {
		return pecasAc;
	}

	public void setPecasAc(Double pecasAc) {
		this.pecasAc = pecasAc;
	}

	public Double getVendasAc() {
		return vendasAc;
	}

	public void setVendasAc(Double vendasAc) {
		this.vendasAc = vendasAc;
	}
	
	/**
	 * Verifica se este EKTDepto corresponde a um depto (BonERP).
	 * @param depto
	 * @return
	 */
	public boolean equalsToDepartamento(Departamento depto) {
		return new HashCodeBuilder(3, 7)
				.append(codigo)
				.append(descricao)
				.toHashCode() == new HashCodeBuilder(3, 7)
						.append(depto.getCodigo())
						.append(depto.getNome())
						.toHashCode();
	}

	@Override
	public String toStringToView() {
		return "[" + this.codigo + " - " + this.descricao + "]";
	}

}
