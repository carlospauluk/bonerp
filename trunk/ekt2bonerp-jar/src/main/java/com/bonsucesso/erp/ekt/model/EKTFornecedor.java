package com.bonsucesso.erp.ekt.model;



import java.util.Date;

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

import com.bonsucesso.erp.estoque.model.Fornecedor;
import com.ocabit.base.model.EntityIdImpl;
import com.ocabit.jpa.listener.UpperCaseListener;


/**
 * Tabela espelho para o arquivo EST_D002.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "ekt_fornecedor")
@EntityListeners({ UpperCaseListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class EKTFornecedor extends EntityIdImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = 6020069883279335209L;
	
	@Column(name = "mesano", length = 6)
	private String mesano;

	public String getMesano() {
		return mesano;
	}

	public void setMesano(String mesano) {
		this.mesano = mesano;
	}

	//	RECORD_NUMBER	4	INTEGER
	@Column(name = "RECORD_NUMBER")
	private Integer recordNumber;

	//	CODIGO	3	DECIMAL
	@Column(name = "CODIGO")
	private Double codigo;

	//	RAZAO	12	VARCHAR	35
	@Column(name = "RAZAO", length = 35)
	private String razao;

	//	NOME_FANTASIA	12	VARCHAR	20
	@Column(name = "NOME_FANTASIA", length = 20)
	private String nomeFantasia;

	//	CGC	12	VARCHAR	20
	@Column(name = "CGC", length = 20)
	private String cgc;

	//	INSC	12	VARCHAR	20
	@Column(name = "INSC", length = 20)
	private String insc;

	//	DATA_CAD	9	DATE	10
	@Column(name = "DATA_CAD")
	private Date dataCad;

	//	ENDERECO	12	VARCHAR	40
	@Column(name = "ENDERECO", length = 40)
	private String endereco;

	//	BAIRRO	12	VARCHAR	20
	@Column(name = "BAIRRO", length = 20)
	private String bairro;

	//	MUNICIPIO	12	VARCHAR	20
	@Column(name = "MUNICIPIO", length = 20)
	private String municipio;

	//	UF	12	VARCHAR	2
	@Column(name = "UF", length = 2)
	private String uf;

	//	CEP	12	VARCHAR	9
	@Column(name = "CEP", length = 9)
	private String cep;

	//	DDD_FONE	12	VARCHAR	4
	@Column(name = "DDD_FONE", length = 4)
	private String dddFone;

	//	FONE	12	VARCHAR	9
	@Column(name = "FONE", length = 9)
	private String fone;

	//	DDD_FAX	12	VARCHAR	4
	@Column(name = "DDD_FAX", length = 4)
	private String dddFax;

	//	FAX	12	VARCHAR	9
	@Column(name = "FAX", length = 9)
	private String fax;

	//	CONTATO	12	VARCHAR	20
	@Column(name = "CONTATO", length = 20)
	private String contato;

	//	NOME_REPRES	12	VARCHAR	30
	@Column(name = "NOME_REPRES", length = 30)
	private String nomeRepres;

	//	DDD_REPRES	12	VARCHAR	4
	@Column(name = "DDD_REPRES", length = 4)
	private String dddRepres;

	//	FONE_REPRES	12	VARCHAR	9
	@Column(name = "FONE_REPRES", length = 9)
	private String foneRepres;

	//	COMPRAS_AC	3	DECIMAL	10
	@Column(name = "COMPRAS_AC")
	private Double comprasAc;

	//	DATA_ULT_COMP	9	DATE	10
	@Column(name = "DATA_ULT_COMP")
	private Date dataUltComp;

	//	PECAS_AC	3	DECIMAL	10
	@Column(name = "PECAS_AC")
	private Double pecasAc;

	//	TIPO	12	VARCHAR	1
	@Column(name = "TIPO", length = 1)
	private String tipo;

	//	DT_ULTALT	6	DATE
	@Column(name = "DT_ULTALT")
	private Date dtUltAlt;

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

	public String getRazao() {
		return razao;
	}

	public void setRazao(String razao) {
		this.razao = razao;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getCgc() {
		return cgc;
	}

	public void setCgc(String cgc) {
		this.cgc = cgc;
	}

	public String getInsc() {
		return insc;
	}

	public void setInsc(String insc) {
		this.insc = insc;
	}

	public Date getDataCad() {
		return dataCad;
	}

	public void setDataCad(Date dataCad) {
		this.dataCad = dataCad;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getDddFone() {
		return dddFone;
	}

	public void setDddFone(String dddFone) {
		this.dddFone = dddFone;
	}

	public String getFone() {
		return fone;
	}

	public void setFone(String fone) {
		this.fone = fone;
	}

	public String getDddFax() {
		return dddFax;
	}

	public void setDddFax(String dddFax) {
		this.dddFax = dddFax;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public String getNomeRepres() {
		return nomeRepres;
	}

	public void setNomeRepres(String nomeRepres) {
		this.nomeRepres = nomeRepres;
	}

	public String getDddRepres() {
		return dddRepres;
	}

	public void setDddRepres(String dddRepres) {
		this.dddRepres = dddRepres;
	}

	public String getFoneRepres() {
		return foneRepres;
	}

	public void setFoneRepres(String foneRepres) {
		this.foneRepres = foneRepres;
	}

	public Double getComprasAc() {
		return comprasAc;
	}

	public void setComprasAc(Double comprasAc) {
		this.comprasAc = comprasAc;
	}

	public Date getDataUltComp() {
		return dataUltComp;
	}

	public void setDataUltComp(Date dataUltComp) {
		this.dataUltComp = dataUltComp;
	}

	public Double getPecasAc() {
		return pecasAc;
	}

	public void setPecasAc(Double pecasAc) {
		this.pecasAc = pecasAc;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Date getDtUltAlt() {
		return dtUltAlt;
	}

	public void setDtUltAlt(Date dtUltAlt) {
		this.dtUltAlt = dtUltAlt;
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return this.codigo + " - " + this.nomeFantasia;
	}

	public boolean equalsTo(Fornecedor fornecedor) {
		return new HashCodeBuilder(3, 7)
				//				.append(codigo.intValue())
				//				.append(razao)
				.append(nomeFantasia)
				//				.append(cgc != null ? cgc.replaceAll("[^0-9]", "") : null)
				//				.append(insc != null ? insc.replaceAll("[^0-9]", "") : insc)
				.toHashCode() == new HashCodeBuilder(3, 7)
						//						.append(fornecedor.getCodigo())
						//						.append(fornecedor.getPessoa().getNome())
						.append(fornecedor.getPessoa().getNomeFantasia())
						//						.append(fornecedor.getPessoa().getDocumento())
						//						.append(fornecedor.getInscricaoEstadual())
						.toHashCode();
	}

}
