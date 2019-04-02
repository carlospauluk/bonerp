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
 * Tabela espelho para o arquivo VEN_D061.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "ekt_venda_item")
@EntityListeners({ UpperCaseListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class EKTVendaItem extends EntityIdImpl {

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

	@Column(name = "NUMERO_NF")
	private Double numeroNF;

	@Column(name = "SERIE", length = 1)
	private String serie;

	@Column(name = "TELA")
	private Double tela;

	@Column(name = "PRODUTO")
	private Double produto;

	@Column(name = "QTDE")
	private Double qtde;

	@Column(name = "UNIDADE", length = 10)
	private String unidade;

	@Column(name = "DESCRICAO", length = 40)
	private String descricao;

	@Column(name = "TAMANHO", length = 3)
	private String tamanho;

	@Column(name = "VLR_UNIT")
	private Double vlrUnit;

	@Column(name = "VLR_TOTAL")
	private Double vlrTotal;

	@Column(name = "WIN")
	private Double win;

	@Column(name = "PRECO_CUSTO")
	private Double precoCusto;

	@Column(name = "PRECO_VISTA")
	private Double precoVista;

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer getRecordNumber() {
		return recordNumber;
	}

	public void setRecordNumber(Integer recordNumber) {
		this.recordNumber = recordNumber;
	}

	public Double getNumeroNF() {
		return numeroNF;
	}

	public void setNumeroNF(Double numeroNF) {
		this.numeroNF = numeroNF;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public Double getTela() {
		return tela;
	}

	public void setTela(Double tela) {
		this.tela = tela;
	}

	public Double getProduto() {
		return produto;
	}

	public void setProduto(Double produto) {
		this.produto = produto;
	}

	public Double getQtde() {
		return qtde;
	}

	public void setQtde(Double qtde) {
		this.qtde = qtde;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getTamanho() {
		return tamanho;
	}

	public void setTamanho(String tamanho) {
		this.tamanho = tamanho;
	}

	public Double getVlrUnit() {
		return vlrUnit;
	}

	public void setVlrUnit(Double vlrUnit) {
		this.vlrUnit = vlrUnit;
	}

	public Double getVlrTotal() {
		return vlrTotal;
	}

	public void setVlrTotal(Double vlrTotal) {
		this.vlrTotal = vlrTotal;
	}

	public Double getWin() {
		return win;
	}

	public void setWin(Double win) {
		this.win = win;
	}

	public Double getPrecoCusto() {
		return precoCusto;
	}

	public void setPrecoCusto(Double precoCusto) {
		this.precoCusto = precoCusto;
	}

	public Double getPrecoVista() {
		return precoVista;
	}

	public void setPrecoVista(Double precoVista) {
		this.precoVista = precoVista;
	}

}
