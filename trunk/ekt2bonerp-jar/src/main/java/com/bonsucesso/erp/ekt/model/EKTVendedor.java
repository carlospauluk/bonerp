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
 * Tabela espelho para o arquivo EST_D008.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "ekt_vendedor")
@EntityListeners({ UpperCaseListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class EKTVendedor extends EntityIdImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6686694266264466538L;

	@Column(name = "RECORD_NUMBER")
	private Integer recordNumber;

	@Column(name = "CODIGO")
	private Double codigo;

	@Column(name = "DESCRICAO", length = 25)
	private String nome;

	@Column(name = "COMIS_VIS")
	private Double comisVis;

	@Column(name = "COMIS_PRA")
	private Double comisPra;

	@Column(name = "FLAG_GER", length = 1)
	private String flagGer;

	@Column(name = "SENHA", length = 5)
	private String senha;

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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getComisVis() {
		return comisVis;
	}

	public void setComisVis(Double comisVis) {
		this.comisVis = comisVis;
	}

	public Double getComisPra() {
		return comisPra;
	}

	public void setComisPra(Double comisPra) {
		this.comisPra = comisPra;
	}

	public String getFlagGer() {
		return flagGer;
	}

	public void setFlagGer(String flagGer) {
		this.flagGer = flagGer;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
