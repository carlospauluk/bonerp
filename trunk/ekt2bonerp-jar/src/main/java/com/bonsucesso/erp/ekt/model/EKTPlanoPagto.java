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

import com.bonsucesso.erp.vendas.model.PlanoPagto;
import com.ocabit.base.model.EntityIdImpl;
import com.ocabit.jpa.listener.UpperCaseListener;


/**
 * Tabela espelho para o arquivo VEN_D062.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "ekt_plano_pagto")
@EntityListeners({ UpperCaseListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class EKTPlanoPagto extends EntityIdImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1475489809654524879L;

	@Column(name = "RECORD_NUMBER")
	private Integer recordNumber;

	@Column(name = "CODIGO", length = 4)
	private String codigo;

	@Column(name = "DESCRICAO", length = 30)
	private String descricao;

	@Column(name = "COD_CREDIARIO", length = 5)
	private String codCrediario;

	public Integer getRecordNumber() {
		return recordNumber;
	}

	public void setRecordNumber(Integer recordNumber) {
		this.recordNumber = recordNumber;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCodCrediario() {
		return codCrediario;
	}

	public void setCodCrediario(String codCrediario) {
		this.codCrediario = codCrediario;
	}

	@Override
	public String toStringToView() {
		return "[" + this.codigo + " - " + this.descricao + "]";
	}

	/**
	 * Verifica se este EKTDepto corresponde a um depto (BonERP).
	 * 
	 * @param depto
	 * @return
	 */
	public boolean equalsTo(PlanoPagto planoPagto) {
		return new HashCodeBuilder(3, 7)
				.append(codigo.trim())
				.append(descricao.trim())
				.toHashCode() == new HashCodeBuilder(3, 7)
						.append(planoPagto.getCodigo().trim())
						.append(planoPagto.getDescricao().trim())
						.toHashCode();
	}

}
