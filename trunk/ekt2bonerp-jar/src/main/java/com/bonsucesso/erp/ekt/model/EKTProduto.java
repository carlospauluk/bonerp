package com.bonsucesso.erp.ekt.model;



import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.bonsucesso.erp.estoque.model.Produto;
import com.ocabit.base.model.EntityIdImpl;
import com.ocabit.jpa.listener.UpperCaseListener;
import com.ocabit.utils.currency.CurrencyUtils;
import com.ocabit.utils.strings.StringSimilarity;


/**
 * Tabela espelho para o arquivo EST_D006.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "ekt_produto")
@EntityListeners({ UpperCaseListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class EKTProduto extends EntityIdImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = -11132298512592881L;

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

	//	OVL_PROD	12	VARCHAR	11
	@Column(name = "OVL_PROD", length = 11)
	private String ovlProd;

	//	FORNEC	3	DECIMAL	4
	@Column(name = "FORNEC")
	private Double fornec;

	//	REFERENCIA	12	VARCHAR	8
	@Column(name = "REFERENCIA", length = 8)
	private String referencia;

	//	GRADE	3	DECIMAL	2
	@Column(name = "GRADE")
	private Double grade;

	//	DEPTO	3	DECIMAL	2
	@Column(name = "DEPTO")
	private Double depto;

	//	SUBDEPTO	3	DECIMAL	4
	@Column(name = "SUBDEPTO")
	private Double subdepto;

	//	REDUZIDO	3	DECIMAL	8
	@Column(name = "REDUZIDO")
	private Double reduzido;

	//	DESCRICAO	12	VARCHAR	35
	@Column(name = "DESCRICAO", length = 35)
	private String descricao;

	//	DATA_PCUSTO	9	DATE	10
	@Column(name = "DATA_PCUSTO")
	@Temporal(TemporalType.DATE)
	private Date dataPCusto;

	//	PCUSTO	3	DECIMAL	10
	@Column(name = "PCUSTO")
	private Double pCusto;

	//	DATA_PVENDA	9	DATE	10
	@Column(name = "DATA_PVENDA")
	@Temporal(TemporalType.DATE)
	private Date dataPVenda;

	//	PVISTA	3	DECIMAL	10
	@Column(name = "PVISTA")
	private Double pVista;

	//	PPRAZO	3	DECIMAL	10
	@Column(name = "PPRAZO")
	private Double pPrazo;

	//	PPROMO	3	DECIMAL	10
	@Column(name = "PPROMO")
	private Double pPromo;

	//	DATA_ULT_VENDA	9	DATE	10
	@Column(name = "DATA_ULT_VENDA")
	@Temporal(TemporalType.DATE)
	private Date dataUltVenda;

	//	PRAZO	3	DECIMAL	4
	@Column(name = "PRAZO")
	private Double prazo;

	//	MARGEM	3	DECIMAL	4
	@Column(name = "MARGEM")
	private Double margem;

	//	MARGEMC	3	DECIMAL	4
	@Column(name = "MARGEMC")
	private Double margemC;

	//	COEF	3	DECIMAL	8
	@Column(name = "COEF")
	private Double coef;

	//	QT01	3	DECIMAL	10
	@Column(name = "QT01")
	private Double qt01;

	//	QT02	3	DECIMAL	10
	@Column(name = "QT02")
	private Double qt02;

	//	QT03	3	DECIMAL	10
	@Column(name = "QT03")
	private Double qt03;

	//	QT04	3	DECIMAL	10
	@Column(name = "QT04")
	private Double qt04;

	//	QT05	3	DECIMAL	10
	@Column(name = "QT05")
	private Double qt05;

	//	QT06	3	DECIMAL	10
	@Column(name = "QT06")
	private Double qt06;

	//	QT07	3	DECIMAL	10
	@Column(name = "QT07")
	private Double qt07;

	//	QT08	3	DECIMAL	10
	@Column(name = "QT08")
	private Double qt08;

	//	QT09	3	DECIMAL	10
	@Column(name = "QT09")
	private Double qt09;

	//	QT10	3	DECIMAL	10
	@Column(name = "QT10")
	private Double qt10;

	//	QT11	3	DECIMAL	10
	@Column(name = "QT11")
	private Double qt11;

	//	QT12	3	DECIMAL	10
	@Column(name = "QT12")
	private Double qt12;

	//	QT13	3	DECIMAL	10
	@Column(name = "QT13")
	private Double qt13;

	//	QT14	3	DECIMAL	10
	@Column(name = "QT14")
	private Double qt14;

	//	QT15	3	DECIMAL	10
	@Column(name = "QT15")
	private Double qt15;

	//	AC01	3	DECIMAL	10
	@Column(name = "AC01")
	private Double ac01;

	//	AC02	3	DECIMAL	10
	@Column(name = "AC02")
	private Double ac02;

	//	AC03	3	DECIMAL	10
	@Column(name = "AC03")
	private Double ac03;

	//	AC04	3	DECIMAL	10
	@Column(name = "AC04")
	private Double ac04;

	//	AC05	3	DECIMAL	10
	@Column(name = "AC05")
	private Double ac05;

	//	AC06	3	DECIMAL	10
	@Column(name = "AC06")
	private Double ac06;

	//	AC07	3	DECIMAL	10
	@Column(name = "AC07")
	private Double ac07;

	//	AC08	3	DECIMAL	10
	@Column(name = "AC08")
	private Double ac08;

	//	AC09	3	DECIMAL	10
	@Column(name = "AC09")
	private Double ac09;

	//	AC10	3	DECIMAL	10
	@Column(name = "AC10")
	private Double ac10;

	//	AC11	3	DECIMAL	10
	@Column(name = "AC11")
	private Double ac11;

	//	AC12	3	DECIMAL	10
	@Column(name = "AC12")
	private Double ac12;

	//	STATUS	12	VARCHAR	1
	@Column(name = "STATUS", length = 1)
	private String status;

	//	UNIDADE	12	VARCHAR	5
	@Column(name = "UNIDADE", length = 5)
	private String unidade;

	//	UNIDADE	12	VARCHAR	5
	@Column(name = "UNIDADE_CORR", length = 5)
	private String unidadeCorrigida;

	//	DATA_CAD	9	DATE	10
	@Column(name = "DATA_CAD")
	@Temporal(TemporalType.DATE)
	private Date dataCad;

	//	MODELO	12	VARCHAR	3
	@Column(name = "MODELO", length = 3)
	private String modelo;

	//	QTDE_MES	3	DECIMAL	10
	@Column(name = "QTDE_MES")
	private Double qtdeMes;

	//	F1	12	VARCHAR	1
	@Column(name = "F1", length = 1)
	private String f1;

	//	F2	12	VARCHAR	1
	@Column(name = "F2", length = 1)
	private String f2;

	//	F3	12	VARCHAR	1
	@Column(name = "F3", length = 1)
	private String f3;

	//	F4	12	VARCHAR	1
	@Column(name = "F4", length = 1)
	private String f4;

	//	F5	12	VARCHAR	1
	@Column(name = "F5", length = 1)
	private String f5;

	//	F6	12	VARCHAR	1
	@Column(name = "F6", length = 1)
	private String f6;

	//	F7	12	VARCHAR	1
	@Column(name = "F7", length = 1)
	private String f7;

	//	F8	12	VARCHAR	1
	@Column(name = "F8", length = 1)
	private String f8;

	//	F9	12	VARCHAR	1
	@Column(name = "F9", length = 1)
	private String f9;

	//	F10	12	VARCHAR	1
	@Column(name = "F10", length = 1)
	private String f10;

	//	F11	12	VARCHAR	1
	@Column(name = "F11", length = 1)
	private String f11;

	//	F12	12	VARCHAR	1
	@Column(name = "F12", length = 1)
	private String f12;

	//	ULT_VENDER	3	DECIMAL	4
	@Column(name = "ULT_VENDER")
	private Double ultVender;

	@Column(name = "ICMS")
	private Double icms;

	@Column(name = "NCM", length = 8)
	private String ncm;

	@Column(name = "FRACIONADO", length = 1)
	private String fracionado;

	@Column(name = "CST", length = 3)
	private String cst;

	@Column(name = "TIPO_TRIB", length = 1)
	private String tipoTrib;

	@Column(name = "DT_ULTALT")
	private Date dtUltAlt;

	@Transient
	private BigDecimal qtdeTotal;

	public Integer getRecordNumber() {
		return recordNumber;
	}

	public void setRecordNumber(Integer recordNumber) {
		this.recordNumber = recordNumber;
	}

	public String getOvlProd() {
		return ovlProd;
	}

	public void setOvlProd(String ovlProd) {
		this.ovlProd = ovlProd;
	}

	public Double getFornec() {
		return fornec;
	}

	public void setFornec(Double fornec) {
		this.fornec = fornec;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public Double getGrade() {
		return grade;
	}

	public void setGrade(Double grade) {
		this.grade = grade;
	}

	public Double getDepto() {
		return depto;
	}

	public void setDepto(Double depto) {
		this.depto = depto;
	}

	public Double getSubdepto() {
		return subdepto;
	}

	public void setSubdepto(Double subdepto) {
		this.subdepto = subdepto;
	}

	public Double getReduzido() {
		return reduzido;
	}

	public void setReduzido(Double reduzido) {
		this.reduzido = reduzido;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataPCusto() {
		return dataPCusto;
	}

	public void setDataPCusto(Date dataPCusto) {
		this.dataPCusto = dataPCusto;
	}

	public Double getpCusto() {
		return pCusto;
	}

	public void setpCusto(Double pCusto) {
		this.pCusto = pCusto;
	}

	public Date getDataPVenda() {
		return dataPVenda;
	}

	public void setDataPVenda(Date dataPVenda) {
		this.dataPVenda = dataPVenda;
	}

	public Double getpVista() {
		return pVista;
	}

	public void setpVista(Double pVista) {
		this.pVista = pVista;
	}

	public Double getpPrazo() {
		return pPrazo;
	}

	public void setpPrazo(Double pPrazo) {
		this.pPrazo = pPrazo;
	}

	public Double getpPromo() {
		return pPromo;
	}

	public void setpPromo(Double pPromo) {
		this.pPromo = pPromo;
	}

	public Date getDataUltVenda() {
		return dataUltVenda;
	}

	public void setDataUltVenda(Date dataUltVenda) {
		this.dataUltVenda = dataUltVenda;
	}

	public Double getPrazo() {
		return prazo;
	}

	public void setPrazo(Double prazo) {
		this.prazo = prazo;
	}

	public Double getMargem() {
		return margem;
	}

	public void setMargem(Double margem) {
		this.margem = margem;
	}

	public Double getMargemC() {
		return margemC;
	}

	public void setMargemC(Double margemC) {
		this.margemC = margemC;
	}

	public Double getCoef() {
		return coef;
	}

	public void setCoef(Double coef) {
		this.coef = coef;
	}

	public Double getQt01() {
		return qt01;
	}

	public void setQt01(Double qt01) {
		this.qt01 = qt01;
	}

	public Double getQt02() {
		return qt02;
	}

	public void setQt02(Double qt02) {
		this.qt02 = qt02;
	}

	public Double getQt03() {
		return qt03;
	}

	public void setQt03(Double qt03) {
		this.qt03 = qt03;
	}

	public Double getQt04() {
		return qt04;
	}

	public void setQt04(Double qt04) {
		this.qt04 = qt04;
	}

	public Double getQt05() {
		return qt05;
	}

	public void setQt05(Double qt05) {
		this.qt05 = qt05;
	}

	public Double getQt06() {
		return qt06;
	}

	public void setQt06(Double qt06) {
		this.qt06 = qt06;
	}

	public Double getQt07() {
		return qt07;
	}

	public void setQt07(Double qt07) {
		this.qt07 = qt07;
	}

	public Double getQt08() {
		return qt08;
	}

	public void setQt08(Double qt08) {
		this.qt08 = qt08;
	}

	public Double getQt09() {
		return qt09;
	}

	public void setQt09(Double qt09) {
		this.qt09 = qt09;
	}

	public Double getQt10() {
		return qt10;
	}

	public void setQt10(Double qt10) {
		this.qt10 = qt10;
	}

	public Double getQt11() {
		return qt11;
	}

	public void setQt11(Double qt11) {
		this.qt11 = qt11;
	}

	public Double getQt12() {
		return qt12;
	}

	public void setQt12(Double qt12) {
		this.qt12 = qt12;
	}

	public Double getQt13() {
		return qt13;
	}

	public void setQt13(Double qt13) {
		this.qt13 = qt13;
	}

	public Double getQt14() {
		return qt14;
	}

	public void setQt14(Double qt14) {
		this.qt14 = qt14;
	}

	public Double getQt15() {
		return qt15;
	}

	public void setQt15(Double qt15) {
		this.qt15 = qt15;
	}

	public Double getAc01() {
		return ac01;
	}

	public void setAc01(Double ac01) {
		this.ac01 = ac01;
	}

	public Double getAc02() {
		return ac02;
	}

	public void setAc02(Double ac02) {
		this.ac02 = ac02;
	}

	public Double getAc03() {
		return ac03;
	}

	public void setAc03(Double ac03) {
		this.ac03 = ac03;
	}

	public Double getAc04() {
		return ac04;
	}

	public void setAc04(Double ac04) {
		this.ac04 = ac04;
	}

	public Double getAc05() {
		return ac05;
	}

	public void setAc05(Double ac05) {
		this.ac05 = ac05;
	}

	public Double getAc06() {
		return ac06;
	}

	public void setAc06(Double ac06) {
		this.ac06 = ac06;
	}

	public Double getAc07() {
		return ac07;
	}

	public void setAc07(Double ac07) {
		this.ac07 = ac07;
	}

	public Double getAc08() {
		return ac08;
	}

	public void setAc08(Double ac08) {
		this.ac08 = ac08;
	}

	public Double getAc09() {
		return ac09;
	}

	public void setAc09(Double ac09) {
		this.ac09 = ac09;
	}

	public Double getAc10() {
		return ac10;
	}

	public void setAc10(Double ac10) {
		this.ac10 = ac10;
	}

	public Double getAc11() {
		return ac11;
	}

	public void setAc11(Double ac11) {
		this.ac11 = ac11;
	}

	public Double getAc12() {
		return ac12;
	}

	public void setAc12(Double ac12) {
		this.ac12 = ac12;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public String getUnidadeCorrigida() {
		return unidadeCorrigida;
	}

	public void setUnidadeCorrigida(String unidadeCorrigida) {
		this.unidadeCorrigida = unidadeCorrigida;
	}

	public Date getDataCad() {
		return dataCad;
	}

	public void setDataCad(Date dataCad) {
		this.dataCad = dataCad;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public Double getQtdeMes() {
		return qtdeMes;
	}

	public void setQtdeMes(Double qtdeMes) {
		this.qtdeMes = qtdeMes;
	}

	public String getF1() {
		return f1;
	}

	public void setF1(String f1) {
		this.f1 = f1;
	}

	public String getF2() {
		return f2;
	}

	public void setF2(String f2) {
		this.f2 = f2;
	}

	public String getF3() {
		return f3;
	}

	public void setF3(String f3) {
		this.f3 = f3;
	}

	public String getF4() {
		return f4;
	}

	public void setF4(String f4) {
		this.f4 = f4;
	}

	public String getF5() {
		return f5;
	}

	public void setF5(String f5) {
		this.f5 = f5;
	}

	public String getF6() {
		return f6;
	}

	public void setF6(String f6) {
		this.f6 = f6;
	}

	public String getF7() {
		return f7;
	}

	public void setF7(String f7) {
		this.f7 = f7;
	}

	public String getF8() {
		return f8;
	}

	public void setF8(String f8) {
		this.f8 = f8;
	}

	public String getF9() {
		return f9;
	}

	public void setF9(String f9) {
		this.f9 = f9;
	}

	public String getF10() {
		return f10;
	}

	public void setF10(String f10) {
		this.f10 = f10;
	}

	public String getF11() {
		return f11;
	}

	public void setF11(String f11) {
		this.f11 = f11;
	}

	public String getF12() {
		return f12;
	}

	public void setF12(String f12) {
		this.f12 = f12;
	}

	public Double getUltVender() {
		return ultVender;
	}

	public void setUltVender(Double ultVender) {
		this.ultVender = ultVender;
	}

	public Double getIcms() {
		return icms;
	}

	public void setIcms(Double icms) {
		this.icms = icms;
	}

	public String getNcm() {
		return ncm;
	}

	public void setNcm(String ncm) {
		this.ncm = ncm;
	}

	public String getFracionado() {
		return fracionado;
	}

	public void setFracionado(String fracionado) {
		this.fracionado = fracionado;
	}

	public String getCst() {
		return cst;
	}

	public void setCst(String cst) {
		this.cst = cst;
	}

	public String getTipoTrib() {
		return tipoTrib;
	}

	public void setTipoTrib(String tipoTrib) {
		this.tipoTrib = tipoTrib;
	}

	public Date getDtUltAlt() {
		return dtUltAlt;
	}

	public void setDtUltAlt(Date dtUltAlt) {
		this.dtUltAlt = dtUltAlt;
	}

	/**
	 * Verifico a similaridade das strings da descrição do produto.
	 * 
	 * @param mesmoReduzido
	 * @return
	 */
	public boolean equalsTo(Produto mesmoReduzido) {

		double simi = StringSimilarity.similarity(descricao.trim(), mesmoReduzido.getDescricao().trim());

		if (simi < 0.75) {
			return false;
		} else if (simi < 0.8) {
			return true;
		} else if (simi < 0.9) {
			return true;
		} else if (simi < 0.95) {
			return true;
		} else if (simi < 0.99) {
			return true;
		} else {
			return true;
		}

	}

	@Override
	public String toStringToView() {
		return this.reduzido + " - " + this.descricao;
	}

	public BigDecimal getQtdeTotal() {
		qtdeTotal = CurrencyUtils.getBigDecimalCurrency(ObjectUtils.firstNonNull(getQt01(), 0.0) +
				ObjectUtils.firstNonNull(getQt02(), 0.0) +
				ObjectUtils.firstNonNull(getQt03(), 0.0) +
				ObjectUtils.firstNonNull(getQt04(), 0.0) +
				ObjectUtils.firstNonNull(getQt05(), 0.0) +
				ObjectUtils.firstNonNull(getQt06(), 0.0) +
				ObjectUtils.firstNonNull(getQt07(), 0.0) +
				ObjectUtils.firstNonNull(getQt08(), 0.0) +
				ObjectUtils.firstNonNull(getQt09(), 0.0) +
				ObjectUtils.firstNonNull(getQt10(), 0.0) +
				ObjectUtils.firstNonNull(getQt11(), 0.0) +
				ObjectUtils.firstNonNull(getQt12(), 0.0));
		return qtdeTotal == null ? BigDecimal.ZERO : qtdeTotal;
	}

	public void setQtdeTotal(BigDecimal qtdeTotal) {
		this.qtdeTotal = qtdeTotal;
	}

}
