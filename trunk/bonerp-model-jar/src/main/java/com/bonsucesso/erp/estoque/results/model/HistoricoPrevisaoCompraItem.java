package com.bonsucesso.erp.estoque.results.model;



import java.math.BigDecimal;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.ocabit.base.model.EntityIdImpl;
import com.ocabit.jpa.listener.EntityIdListener;
import com.ocabit.jpa.listener.UpperCaseListener;


/**
 * Itens (linhas) dos HistoricoPrevisaoCompra.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "est_historico_previsao_compra_item",
		uniqueConstraints = { @UniqueConstraint(columnNames = { "hpc_id", "ano" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class HistoricoPrevisaoCompraItem extends EntityIdImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8013426760578703616L;

	@ManyToOne(optional = false)
	@JoinColumn(name = "hpc_id", nullable = false)
	@NotNull(message = "O campo 'HistoricoPrevisaoCompra' deve ser informado")
	private HistoricoPrevisaoCompra hpc;

	@Column(name = "ano")
	private String ano;

	@Column(name = "mes1")
	private BigDecimal mes1;

	@Column(name = "mes2")
	private BigDecimal mes2;

	@Column(name = "mes3")
	private BigDecimal mes3;

	@Column(name = "mes4")
	private BigDecimal mes4;

	@Column(name = "mes5")
	private BigDecimal mes5;

	@Column(name = "mes6")
	private BigDecimal mes6;

	@Column(name = "mes7")
	private BigDecimal mes7;

	@Column(name = "mes8")
	private BigDecimal mes8;

	@Column(name = "mes9")
	private BigDecimal mes9;

	@Column(name = "mes10")
	private BigDecimal mes10;

	@Column(name = "mes11")
	private BigDecimal mes11;

	@Column(name = "mes12")
	private BigDecimal mes12;

	public HistoricoPrevisaoCompra getHpc() {
		return hpc;
	}

	public void setHpc(HistoricoPrevisaoCompra hpc) {
		this.hpc = hpc;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public BigDecimal getMes1() {
		return mes1;
	}

	public void setMes1(BigDecimal mes1) {
		this.mes1 = mes1;
	}

	public BigDecimal getMes2() {
		return mes2;
	}

	public void setMes2(BigDecimal mes2) {
		this.mes2 = mes2;
	}

	public BigDecimal getMes3() {
		return mes3;
	}

	public void setMes3(BigDecimal mes3) {
		this.mes3 = mes3;
	}

	public BigDecimal getMes4() {
		return mes4;
	}

	public void setMes4(BigDecimal mes4) {
		this.mes4 = mes4;
	}

	public BigDecimal getMes5() {
		return mes5;
	}

	public void setMes5(BigDecimal mes5) {
		this.mes5 = mes5;
	}

	public BigDecimal getMes6() {
		return mes6;
	}

	public void setMes6(BigDecimal mes6) {
		this.mes6 = mes6;
	}

	public BigDecimal getMes7() {
		return mes7;
	}

	public void setMes7(BigDecimal mes7) {
		this.mes7 = mes7;
	}

	public BigDecimal getMes8() {
		return mes8;
	}

	public void setMes8(BigDecimal mes8) {
		this.mes8 = mes8;
	}

	public BigDecimal getMes9() {
		return mes9;
	}

	public void setMes9(BigDecimal mes9) {
		this.mes9 = mes9;
	}

	public BigDecimal getMes10() {
		return mes10;
	}

	public void setMes10(BigDecimal mes10) {
		this.mes10 = mes10;
	}

	public BigDecimal getMes11() {
		return mes11;
	}

	public void setMes11(BigDecimal mes11) {
		this.mes11 = mes11;
	}

	public BigDecimal getMes12() {
		return mes12;
	}

	public void setMes12(BigDecimal mes12) {
		this.mes12 = mes12;
	}

	@Override
	public int hashCode() {
		final int prime = 317;
		final int result = 911;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result)
				.append(hpc)
				.append(ano)
				.toHashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		final HistoricoPrevisaoCompraItem iObj = (HistoricoPrevisaoCompraItem) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(hpc, iObj.hpc)
				.append(ano, iObj.ano)
				.isEquals();
	}

}
