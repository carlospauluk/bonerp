package com.bonsucesso.erp.estoque.model;



import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.bonsucesso.erp.fiscal.model.NotaFiscal;
import com.ocabit.base.model.EntityIdImpl;
import com.ocabit.jpa.listener.EntityIdListener;
import com.ocabit.jpa.listener.UpperCaseListener;


/**
 * Entidade EntradaMercadoria.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "est_movimentacao",
		uniqueConstraints = { @UniqueConstraint(columnNames = { "lote" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class MovimentacaoEstoque extends EntityIdImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5795276626415308095L;

	/**
	 * Padrão: YYYYMMDD999
	 */
	@Column(name = "lote", nullable = false, length = 18)
	@NotNull(message = "'Lote' deve ser informado")
	private Long lote;

	@OneToOne(optional = true,
			cascade = { CascadeType.REFRESH },
			fetch = FetchType.LAZY)
	@JoinColumn(name = "nota_fiscal_id", nullable = false)
	private NotaFiscal notaFiscal;

	/**
	 * Informa se este lote é sobre todo ou apenas parte de um pedido.
	 */
	@Column(name = "pedido_parcial", nullable = false)
	@NotNull(message = "Campo 'Parcial Pedido' precisa ser informado")
	private Boolean parcialPedido = false;

	@Column(name = "dt_lote", nullable = false)
	@Temporal(TemporalType.DATE)
	@NotNull(message = "Campo 'Dt Lote' precisa ser informado")
	private Date dtLote;

	@Column(name = "prazo_medio", nullable = false)
	@NotNull(message = "Campo 'Prazo Médio' precisa ser informado")
	private Integer prazoMedio;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "movimentacaoEstoque", orphanRemoval = false)
	private List<MovimentacaoEstoqueItem> itens;

	public Long getLote() {
		return lote;
	}

	public void setLote(Long lote) {
		this.lote = lote;
	}

	public NotaFiscal getNotaFiscal() {
		return notaFiscal;
	}

	public void setNotaFiscal(NotaFiscal notaFiscal) {
		this.notaFiscal = notaFiscal;
	}

	public Boolean getParcialPedido() {
		return parcialPedido;
	}

	public void setParcialPedido(Boolean parcialPedido) {
		this.parcialPedido = parcialPedido;
	}

	public Date getDtLote() {
		return dtLote;
	}

	public void setDtLote(Date dtLote) {
		this.dtLote = dtLote;
	}

	public Integer getPrazoMedio() {
		return prazoMedio;
	}

	public void setPrazoMedio(Integer prazoMedio) {
		this.prazoMedio = prazoMedio;
	}

	public List<MovimentacaoEstoqueItem> getItens() {
		return itens;
	}

	public void setItens(List<MovimentacaoEstoqueItem> itens) {
		this.itens = itens;
	}

	@Override
	public int hashCode() {
		final int prime = 139;
		final int result = 149;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).append(lote).toHashCode();
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
		final MovimentacaoEstoque iObj = (MovimentacaoEstoque) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(lote, iObj.lote)
				.isEquals();
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
