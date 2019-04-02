package com.bonsucesso.erp.estoque.results.model;



import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.bonsucesso.erp.estoque.model.Fornecedor;
import com.bonsucesso.erp.estoque.model.Subdepartamento;
import com.ocabit.base.model.EntityIdImpl;
import com.ocabit.jpa.listener.EntityIdListener;
import com.ocabit.jpa.listener.UpperCaseListener;


/**
 * Tabela contendo históricos de até 5 anos atrás para montar previsões de compra.
 * Esta tabela deve ser atualizada diariamente.
 * Esta tabela é uma representação "horizontal" (de até 5 anos da tabela) est_produto_venda_results afim de facilitar e agilizar as
 * pesquisas.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "est_historico_previsao_compra",
		uniqueConstraints = { @UniqueConstraint(columnNames = { "fornecedor_id", "subdepto_id" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class HistoricoPrevisaoCompra extends EntityIdImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8013426760578703616L;

	@ManyToOne(optional = false)
	@JoinColumn(name = "subdepto_id", nullable = false)
	@NotNull(message = "O campo 'Subdepto' deve ser informado")
	private Subdepartamento subdepto;

	@ManyToOne(optional = false)
	@JoinColumn(name = "fornecedor_id", nullable = false)
	@NotNull(message = "O campo 'Fornecedor' deve ser informado")
	private Fornecedor fornecedor;

	@Column(name = "saldo_atual")
	private BigDecimal saldoAtual;

	@Column(name = "vendas_restantes_mes")
	private BigDecimal vendasRestantesMes;

	@Column(name = "qtde_em_pedidos")
	private BigDecimal qtdeEmPedidos;

	/**
	 * Marcar qual é o primeiro mês/ano da previsão para poder montar as colunas dinamicamente.
	 * Resolvi só guardar o primeiro, pois sempre serão 12 * 5.
	 */
	@Column(name = "primeiro_mesano")
	private Date primeiroMesAno;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "hpc", orphanRemoval = true, cascade = { CascadeType.ALL })
	private List<HistoricoPrevisaoCompraItem> linhas;

	@Column(name = "media_mes1")
	private BigDecimal mediaMes1;
	@Column(name = "media_mes2")
	private BigDecimal mediaMes2;
	@Column(name = "media_mes3")
	private BigDecimal mediaMes3;
	@Column(name = "media_mes4")
	private BigDecimal mediaMes4;
	@Column(name = "media_mes5")
	private BigDecimal mediaMes5;
	@Column(name = "media_mes6")
	private BigDecimal mediaMes6;
	@Column(name = "media_mes7")
	private BigDecimal mediaMes7;
	@Column(name = "media_mes8")
	private BigDecimal mediaMes8;
	@Column(name = "media_mes9")
	private BigDecimal mediaMes9;
	@Column(name = "media_mes10")
	private BigDecimal mediaMes10;
	@Column(name = "media_mes11")
	private BigDecimal mediaMes11;
	@Column(name = "media_mes12")
	private BigDecimal mediaMes12;

	@Column(name = "saldo_mes1")
	private BigDecimal saldoMes1;
	@Column(name = "saldo_mes2")
	private BigDecimal saldoMes2;
	@Column(name = "saldo_mes3")
	private BigDecimal saldoMes3;
	@Column(name = "saldo_mes4")
	private BigDecimal saldoMes4;
	@Column(name = "saldo_mes5")
	private BigDecimal saldoMes5;
	@Column(name = "saldo_mes6")
	private BigDecimal saldoMes6;
	@Column(name = "saldo_mes7")
	private BigDecimal saldoMes7;
	@Column(name = "saldo_mes8")
	private BigDecimal saldoMes8;
	@Column(name = "saldo_mes9")
	private BigDecimal saldoMes9;
	@Column(name = "saldo_mes10")
	private BigDecimal saldoMes10;
	@Column(name = "saldo_mes11")
	private BigDecimal saldoMes11;
	@Column(name = "saldo_mes12")
	private BigDecimal saldoMes12;

	public HistoricoPrevisaoCompra() {
		super();
	}

	public Subdepartamento getSubdepto() {
		return subdepto;
	}

	public void setSubdepto(Subdepartamento subdepto) {
		this.subdepto = subdepto;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public BigDecimal getSaldoAtual() {
		if (saldoAtual == null) {
			saldoAtual = BigDecimal.ZERO;
		}
		return saldoAtual;
	}

	public void setSaldoAtual(BigDecimal saldoAtual) {
		this.saldoAtual = saldoAtual;
	}

	public BigDecimal getVendasRestantesMes() {
		if (vendasRestantesMes == null) {
			vendasRestantesMes = BigDecimal.ZERO;
		}
		return vendasRestantesMes;
	}

	public void setVendasRestantesMes(BigDecimal vendasRestantesMes) {
		this.vendasRestantesMes = vendasRestantesMes;
	}

	public BigDecimal getQtdeEmPedidos() {
		return qtdeEmPedidos;
	}

	public void setQtdeEmPedidos(BigDecimal qtdeEmPedidos) {
		this.qtdeEmPedidos = qtdeEmPedidos;
	}

	public Date getPrimeiroMesAno() {
		return primeiroMesAno;
	}

	public void setPrimeiroMesAno(Date primeiroMesAno) {
		this.primeiroMesAno = primeiroMesAno;
	}

	public BigDecimal getMediaMes1() {
		return mediaMes1;
	}

	public void setMediaMes1(BigDecimal mediaMes1) {
		this.mediaMes1 = mediaMes1;
	}

	public BigDecimal getMediaMes2() {
		return mediaMes2;
	}

	public void setMediaMes2(BigDecimal mediaMes2) {
		this.mediaMes2 = mediaMes2;
	}

	public BigDecimal getMediaMes3() {
		return mediaMes3;
	}

	public void setMediaMes3(BigDecimal mediaMes3) {
		this.mediaMes3 = mediaMes3;
	}

	public BigDecimal getMediaMes4() {
		return mediaMes4;
	}

	public void setMediaMes4(BigDecimal mediaMes4) {
		this.mediaMes4 = mediaMes4;
	}

	public BigDecimal getMediaMes5() {
		return mediaMes5;
	}

	public void setMediaMes5(BigDecimal mediaMes5) {
		this.mediaMes5 = mediaMes5;
	}

	public BigDecimal getMediaMes6() {
		return mediaMes6;
	}

	public void setMediaMes6(BigDecimal mediaMes6) {
		this.mediaMes6 = mediaMes6;
	}

	public BigDecimal getMediaMes7() {
		return mediaMes7;
	}

	public void setMediaMes7(BigDecimal mediaMes7) {
		this.mediaMes7 = mediaMes7;
	}

	public BigDecimal getMediaMes8() {
		return mediaMes8;
	}

	public void setMediaMes8(BigDecimal mediaMes8) {
		this.mediaMes8 = mediaMes8;
	}

	public BigDecimal getMediaMes9() {
		return mediaMes9;
	}

	public void setMediaMes9(BigDecimal mediaMes9) {
		this.mediaMes9 = mediaMes9;
	}

	public BigDecimal getMediaMes10() {
		return mediaMes10;
	}

	public void setMediaMes10(BigDecimal mediaMes10) {
		this.mediaMes10 = mediaMes10;
	}

	public BigDecimal getMediaMes11() {
		return mediaMes11;
	}

	public void setMediaMes11(BigDecimal mediaMes11) {
		this.mediaMes11 = mediaMes11;
	}

	public BigDecimal getMediaMes12() {
		return mediaMes12;
	}

	public void setMediaMes12(BigDecimal mediaMes12) {
		this.mediaMes12 = mediaMes12;
	}

	public BigDecimal getSaldoMes1() {
		return saldoMes1;
	}

	public void setSaldoMes1(BigDecimal saldoMes1) {
		this.saldoMes1 = saldoMes1;
	}

	public BigDecimal getSaldoMes2() {
		return saldoMes2;
	}

	public void setSaldoMes2(BigDecimal saldoMes2) {
		this.saldoMes2 = saldoMes2;
	}

	public BigDecimal getSaldoMes3() {
		return saldoMes3;
	}

	public void setSaldoMes3(BigDecimal saldoMes3) {
		this.saldoMes3 = saldoMes3;
	}

	public BigDecimal getSaldoMes4() {
		return saldoMes4;
	}

	public void setSaldoMes4(BigDecimal saldoMes4) {
		this.saldoMes4 = saldoMes4;
	}

	public BigDecimal getSaldoMes5() {
		return saldoMes5;
	}

	public void setSaldoMes5(BigDecimal saldoMes5) {
		this.saldoMes5 = saldoMes5;
	}

	public BigDecimal getSaldoMes6() {
		return saldoMes6;
	}

	public void setSaldoMes6(BigDecimal saldoMes6) {
		this.saldoMes6 = saldoMes6;
	}

	public BigDecimal getSaldoMes7() {
		return saldoMes7;
	}

	public void setSaldoMes7(BigDecimal saldoMes7) {
		this.saldoMes7 = saldoMes7;
	}

	public BigDecimal getSaldoMes8() {
		return saldoMes8;
	}

	public void setSaldoMes8(BigDecimal saldoMes8) {
		this.saldoMes8 = saldoMes8;
	}

	public BigDecimal getSaldoMes9() {
		return saldoMes9;
	}

	public void setSaldoMes9(BigDecimal saldoMes9) {
		this.saldoMes9 = saldoMes9;
	}

	public BigDecimal getSaldoMes10() {
		return saldoMes10;
	}

	public void setSaldoMes10(BigDecimal saldoMes10) {
		this.saldoMes10 = saldoMes10;
	}

	public BigDecimal getSaldoMes11() {
		return saldoMes11;
	}

	public void setSaldoMes11(BigDecimal saldoMes11) {
		this.saldoMes11 = saldoMes11;
	}

	public BigDecimal getSaldoMes12() {
		return saldoMes12;
	}

	public void setSaldoMes12(BigDecimal saldoMes12) {
		this.saldoMes12 = saldoMes12;
	}

	@Override
	public int hashCode() {
		final int prime = 919;
		final int result = 911;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result)
				.append(fornecedor)
				.append(subdepto)
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
		final HistoricoPrevisaoCompra iObj = (HistoricoPrevisaoCompra) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(fornecedor, iObj.fornecedor)
				.append(subdepto, iObj.subdepto)
				.isEquals();
	}

}
