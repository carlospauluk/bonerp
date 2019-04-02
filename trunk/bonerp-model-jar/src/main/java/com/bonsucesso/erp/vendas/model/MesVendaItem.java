package com.bonsucesso.erp.vendas.model;



import java.math.BigDecimal;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.bonsucesso.erp.rh.model.Funcionario;
import com.ocabit.base.model.EntityIdImpl;
import com.ocabit.jpa.listener.EntityIdListener;
import com.ocabit.jpa.listener.UpperCaseListener;
import com.ocabit.utils.currency.CurrencyUtils;


/**
 * Classe para os itens de MesVenda.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "ven_mes_venda_item",
		uniqueConstraints = { @UniqueConstraint(columnNames = { "mes_venda_id", "vendedor_id" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class MesVendaItem extends EntityIdImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6269390533673809912L;

	@ManyToOne(optional = false)
	@JoinColumn(name = "mes_venda_id", nullable = false)
	@NotNull(message = "O campo 'Mês/Venda' deve ser informado")
	private MesVenda mesVenda;

	@ManyToOne(optional = false)
	@JoinColumn(name = "vendedor_id", nullable = false)
	@NotNull(message = "O campo 'Vendedor' deve ser informado")
	private Funcionario vendedor;

	@Column(name = "total_vendido", nullable = false)
	@NotNull(message = "Campo 'Total Vendido' precisa ser informado")
	private BigDecimal totalVendido;

	@Column(name = "considera_mes", nullable = false)
	@NotNull(message = "Campo 'Considera Mês' precisa ser informado")
	private Boolean consideraMes = false;

	@Column(name = "qtde_vendas", nullable = false)
	@NotNull(message = "Campo 'Qtde Vendas' precisa ser informado")
	private Integer qtdeVendas;

	@Column(name = "posicao", nullable = true)
	private Integer posicao;

	@Column(name = "media_posicoes_6meses")
	private Integer mediaPosicoes6meses;

	@Transient
	private Boolean atingiuMetaMinima;

	@Transient
	private Boolean atingiuMetaEsperada;

	@Transient
	private BigDecimal valorVendaMedio;
	
	@Transient
	private BigDecimal totalProvavel;

	public MesVenda getMesVenda() {
		return mesVenda;
	}

	public void setMesVenda(MesVenda mesVenda) {
		this.mesVenda = mesVenda;
	}

	public Funcionario getVendedor() {
		return vendedor;
	}

	public void setVendedor(Funcionario vendedor) {
		this.vendedor = vendedor;
	}

	public BigDecimal getTotalVendido() {
		return totalVendido;
	}

	public void setTotalVendido(BigDecimal totalVendido) {
		this.totalVendido = totalVendido;
	}
	public Boolean getConsideraMes() {
		return consideraMes;
	}

	public void setConsideraMes(Boolean consideraMes) {
		this.consideraMes = consideraMes;
	}

	public Integer getPosicao() {
		return posicao;
	}

	public void setPosicao(Integer posicao) {
		this.posicao = posicao;
	}

	public Integer getMediaPosicoes6meses() {
		return mediaPosicoes6meses;
	}

	public void setMediaPosicoes6meses(Integer mediaPosicoes6meses) {
		this.mediaPosicoes6meses = mediaPosicoes6meses;
	}

	public Integer getQtdeVendas() {
		return qtdeVendas;
	}

	public void setQtdeVendas(Integer qtdeVendas) {
		this.qtdeVendas = qtdeVendas;
	}

	public Boolean getAtingiuMetaMinima() {
		return atingiuMetaMinima;
	}

	public void setAtingiuMetaMinima(Boolean atingiuMetaMinima) {
		this.atingiuMetaMinima = atingiuMetaMinima;
	}

	public Boolean getAtingiuMetaEsperada() {
		return atingiuMetaEsperada;
	}

	public void setAtingiuMetaEsperada(Boolean atingiuMetaEsperada) {
		this.atingiuMetaEsperada = atingiuMetaEsperada;
	}

	public BigDecimal getValorVendaMedio() {
		return valorVendaMedio;
	}

	public void setValorVendaMedio(BigDecimal valorVendaMedio) {
		this.valorVendaMedio = valorVendaMedio;
	}

	@Override
	public int hashCode() {
		final int prime = 719;
		final int result = 977;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).append(mesVenda).append(vendedor).toHashCode();
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
		final MesVendaItem iObj = (MesVendaItem) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(mesVenda, iObj.mesVenda)
				.append(vendedor, iObj.vendedor)
				.isEquals();
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public BigDecimal getTotalProvavel() {
		if (totalProvavel == null) {
			if (getTotalVendido() != null && getMesVenda() != null && getMesVenda().getQtdeDiasUteisRestantes() != null
					&& getMesVenda().getQtdeDiasUteisAteHoje() != null) {
				BigDecimal mediaDiaria = CurrencyUtils.divideBD(getTotalVendido(), new BigDecimal(getMesVenda().getQtdeDiasUteisAteHoje()));
				totalProvavel = mediaDiaria.multiply(new BigDecimal(getMesVenda().getQtdeDiasUteisRestantes())).add(getTotalVendido());
			}
		}
		return totalProvavel;
	}

}
