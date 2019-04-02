package com.bonsucesso.erp.estoque.model;



import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
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
import com.ocabit.utils.calendar.CalendarUtil;


/**
 *
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "est_produto_preco", uniqueConstraints = { @UniqueConstraint(columnNames = { "produto_id",
		"dt_custo", "preco_custo", "preco_prazo" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class ProdutoPreco extends EntityIdImpl implements IPreco, Cloneable {

	/**
	 *
	 */
	private static final long serialVersionUID = -6923384277242649208L;

	@ManyToOne(optional = false)
	@JoinColumn(name = "produto_id", nullable = false)
	@NotNull(message = "O campo 'Produto' deve ser informado")
	private Produto produto;

	/**
	 * Incluído para saber de quando foi importado este preço.
	 */
	@Column(name = "mesano", nullable = true)
	@Temporal(TemporalType.DATE)
	@NotNull(message = "Campo 'mesano' precisa ser informado")
	private Date mesano;

	@Column(nullable = false, name = "dt_custo")
	@Temporal(TemporalType.DATE)
	@NotNull(message = "Campo 'Dt Custo' precisa ser informado")
	private Date dtCusto;

	@Column(nullable = false, name = "dt_preco_venda")
	@Temporal(TemporalType.DATE)
	@NotNull(message = "Campo 'Dt Preço Venda' precisa ser informado")
	private Date dtPrecoVenda;

	@Column(name = "preco_custo", nullable = false)
	@NotNull(message = "Campo 'Preço Custo' precisa ser informado")
	@Min(value = 0, message = "Campo 'Preço Custo' precisa ser informado")
	private BigDecimal precoCusto;

	@Column(name = "coeficiente", nullable = false)
	@NotNull(message = "Campo 'Coeficiente' precisa ser informado")
	@Min(value = 0, message = "Campo 'Coeficiente' precisa ser informado")
	private BigDecimal coeficiente;

	@Column(name = "margem", nullable = false)
	@NotNull(message = "Campo 'Margem' precisa ser informado")
	private BigDecimal margem;

	@Column(name = "custo_operacional", nullable = false)
	@NotNull(message = "Campo 'Custo Operacional' precisa ser informado")
	@Min(value = 0, message = "Campo 'Custo Operacional' precisa ser informado")
	private BigDecimal custoOperacional;

	@Column(name = "custo_financeiro", nullable = false)
	@NotNull(message = "Campo 'Custo Financeiro' precisa ser informado")
	@Min(value = 0, message = "Campo 'Custo Financeiro' não pode ser negativo")
	private BigDecimal custoFinanceiro;

	@Column(name = "prazo", nullable = false)
	@NotNull(message = "Campo 'Prazo' precisa ser informado")
	private Integer prazo;

	@Column(name = "preco_prazo", nullable = false)
	@NotNull(message = "Campo 'Preço Prazo' precisa ser informado")
	@Min(value = 0, message = "Campo 'Preço Prazo' precisa ser informado")
	private BigDecimal precoPrazo;

	@Column(name = "preco_vista", nullable = false)
	@NotNull(message = "Campo 'Preço Vista' precisa ser informado")
	@Min(value = 0, message = "Campo 'Preço Vista' precisa ser informado")
	private BigDecimal precoVista;

	@Column(name = "preco_promo", nullable = true)
	@Min(value = 0, message = "Preço deve ser positivo")
	private BigDecimal precoPromo;

	@Transient
	private BigDecimal precoVigente;

	public ProdutoPreco() {

	}

	public ProdutoPreco(Produto produto, BigDecimal precoCusto, BigDecimal coeficiente, BigDecimal precoPromo,
			Date dtCusto) {
		setProduto(produto);
		setPrecoCusto(precoCusto);
		setCoeficiente(coeficiente);
		setPrecoPromo(precoPromo);
		setDtCusto(dtCusto);
	}

	/**
	 * A fins de testes.
	 *
	 * @param precoPrazo
	 */
	public ProdutoPreco(Produto produto, BigDecimal precoPrazo) {
		super();
		setPrecoPrazo(precoPrazo);
		setProduto(produto);
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Date getMesano() {
		return mesano;
	}

	public void setMesano(Date mesano) {
		this.mesano = mesano;
	}

	@Override
	public BigDecimal getPrecoCusto() {
		return precoCusto;
	}

	@Override
	public void setPrecoCusto(BigDecimal precoCusto) {
		this.precoCusto = precoCusto;
	}

	@Override
	public BigDecimal getCoeficiente() {
		return coeficiente;
	}

	@Override
	public void setCoeficiente(BigDecimal coeficiente) {
		this.coeficiente = coeficiente;
	}

	@Override
	public BigDecimal getMargem() {
		return margem;
	}

	@Override
	public void setMargem(BigDecimal margem) {
		this.margem = margem;
	}

	@Override
	public BigDecimal getCustoOperacional() {
		return custoOperacional;
	}

	@Override
	public void setCustoOperacional(BigDecimal custoOperacional) {
		this.custoOperacional = custoOperacional;
	}

	@Override
	public BigDecimal getCustoFinanceiro() {
		return custoFinanceiro;
	}

	@Override
	public void setCustoFinanceiro(BigDecimal custoFinanceiro) {
		this.custoFinanceiro = custoFinanceiro;
	}

	@Override
	public Integer getPrazo() {
		return prazo;
	}

	@Override
	public void setPrazo(Integer prazo) {
		this.prazo = prazo;
	}

	@Override
	public BigDecimal getPrecoPrazo() {
		return precoPrazo;
	}

	@Override
	public void setPrecoPrazo(BigDecimal precoPrazo) {
		this.precoPrazo = precoPrazo;
	}

	@Override
	public BigDecimal getPrecoVista() {
		return precoVista;
	}

	@Override
	public void setPrecoVista(BigDecimal precoVista) {
		this.precoVista = precoVista;
	}

	public BigDecimal getPrecoPromo() {
		return precoPromo;
	}

	public void setPrecoPromo(BigDecimal precoPromo) {
		this.precoPromo = precoPromo;
	}

	@Override
	public Date getDtCusto() {
		return dtCusto;
	}

	@Override
	public void setDtCusto(Date dtCusto) {
		this.dtCusto = dtCusto;
	}

	public Date getDtPrecoVenda() {
		return dtPrecoVenda;
	}

	public void setDtPrecoVenda(Date dtPrecoVenda) {
		this.dtPrecoVenda = dtPrecoVenda;
	}

	public BigDecimal getPrecoVigente() {
		if ((getPrecoPromo() != null) && (getPrecoPromo().doubleValue() > 0.0)) {
			setPrecoVigente(getPrecoPromo());
		} else if ((getPrecoPrazo() != null) && (getPrecoPrazo().doubleValue() > 0.0)) {
			setPrecoVigente(getPrecoPrazo());
		} else {
			precoVigente = BigDecimal.ZERO;
		}
		return precoVigente;
	}

	public void setPrecoVigente(BigDecimal precoVigente) {
		this.precoVigente = precoVigente;
	}

	// -------------------------------------------------------

	@Override
	public int hashCode() {
		final int prime = 223;
		final int result = 557;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result)
				.append(produto)
				.append(CalendarUtil.zeroDate(dtCusto))
				.append(precoCusto)
				.append(precoPrazo)
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
		final ProdutoPreco iObj = (ProdutoPreco) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(produto, iObj.produto)
				.append(CalendarUtil.zeroDate(dtCusto), CalendarUtil.zeroDate(iObj.dtCusto))
				.append(precoCusto.doubleValue(), iObj.precoCusto.doubleValue())
				.append(precoPrazo.doubleValue(), iObj.precoPrazo.doubleValue())
				.append(precoPromo.doubleValue(), iObj.precoPromo.doubleValue())
				.append(precoVista.doubleValue(), iObj.precoVista.doubleValue())
				.isEquals();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}
}
