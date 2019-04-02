package com.bonsucesso.erp.financeiro.model;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.ocabit.base.model.EntityIdImpl;
import com.ocabit.jpa.listener.EntityIdListener;
import com.ocabit.jpa.listener.UpperCaseListener;
import com.ocabit.utils.currency.CurrencyUtils;


@Entity
@Table(name = "fin_parcelamento")
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class Parcelamento extends EntityIdImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = 1687261863446935298L;

	@Column(name = "valor_total", nullable = false, precision = 15, scale = 2)
	@NotNull(message = "Campo 'Valor Total' precisa ser informado")
	private BigDecimal valorTotal;

	@OneToMany(mappedBy = "parcelamento", fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
	private List<Movimentacao> parcelas;

	@Transient
	private Integer qtdeParcelas;

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public List<Movimentacao> getParcelas() {
		if (parcelas == null) {
			parcelas = new ArrayList<Movimentacao>();
		}
		return parcelas;
	}

	public void setParcelas(List<Movimentacao> parcelas) {
		this.parcelas = parcelas;
	}

	public void addParcela(Movimentacao movimentacao) {
		movimentacao.setParcelamento(this);
		getParcelas().add(movimentacao);
		setQtdeParcelas(null);
	}

	public Integer getQtdeParcelas() {
		if ((qtdeParcelas == null) && (getParcelas() != null)) {
			qtdeParcelas = getParcelas().size();
		}
		return qtdeParcelas;
	}

	public void setQtdeParcelas(Integer qtdeParcelas) {
		this.qtdeParcelas = qtdeParcelas;
	}

	public void recalcularParcelas() {
		if ((getParcelas() != null) && (getParcelas().size() > 0)) {
			BigDecimal valorTotal = CurrencyUtils.getBigDecimalCurrency(0.0);
			for (Movimentacao parcela : getParcelas()) {
				if (parcela.getValorTotal() != null) {
					valorTotal = CurrencyUtils.soma(valorTotal, parcela.getValorTotal());
				}
			}
			setValorTotal(valorTotal);
		}
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
