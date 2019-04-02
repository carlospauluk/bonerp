package com.bonsucesso.erp.fiscal.model;



import java.math.BigDecimal;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.ocabit.base.model.EntityIdImpl;
import com.ocabit.jpa.listener.EntityIdListener;
import com.ocabit.jpa.listener.UpperCaseListener;
import com.ocabit.utils.currency.CurrencyUtils;


/**
 * Entidade NotaFiscal.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "fis_nf_item",
		uniqueConstraints = { @UniqueConstraint(columnNames = { "nota_fiscal_id", "ordem" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class NotaFiscalItem extends EntityIdImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1853005410561226112L;

	@ManyToOne(optional = false)
	@JoinColumn(name = "nota_fiscal_id", nullable = false)
	@NotNull(message = "O campo 'nota_fiscal_id' deve ser informado")
	private NotaFiscal notaFiscal;

	@Column(name = "ordem", nullable = false)
	@Min(value = 1, message = "'Ordem' deve ser um número inteiro maior que 0")
	private Integer ordem;

	@Column(name = "codigo", nullable = false, length = 50)
	@Size(min = 1, max = 50, message = "'Código' deve possuir entre 1 e 50 caracteres")
	private String codigo;

	@Column(name = "descricao", nullable = false, length = 2000)
	@Size(min = 1, max = 2000, message = "'Descrição' deve possuir entre 1 e 2000 caracteres")
	private String descricao;

	@Column(name = "ncm", nullable = false, length = 20)
	@Size(min = 1, max = 20, message = "'NCM' deve possuir entre 1 e 20 caracteres")
	private String ncm;

	@Column(name = "ncm_existente", nullable = true)
	private Boolean ncmExistente;

	@Column(name = "cfop", nullable = false, length = 20)
	@NotNull(message = "Campo 'CFOP' precisa ser informado")
	@Size(min = 1, max = 20, message = "'CFOP' deve possuir entre 1 e 20 caracteres")
	private String cfop;

	@Column(name = "unidade", nullable = false, length = 50)
	@NotNull(message = "Campo 'Unidade' precisa ser informado")
	@Size(min = 1, max = 50, message = "'Unidade' deve possuir entre 1 e 50 caracteres")
	private String unidade;

	@Column(name = "qtde", nullable = false)
	@NotNull(message = "Campo 'Qtde' precisa ser informado")
	private BigDecimal qtde;

	@Column(name = "icms", nullable = false)
	@NotNull(message = "Campo 'icmsAliquota' precisa ser informado")
	private BigDecimal icmsAliquota = BigDecimal.ZERO;

	@Column(name = "icms_valor_bc", nullable = true)
	private BigDecimal icmsValorBC = BigDecimal.ZERO;

	@Column(name = "icms_valor", nullable = true)
	private BigDecimal icmsValor = BigDecimal.ZERO;

	@Column(name = "valor_unit", nullable = false)
	@NotNull(message = "Campo 'Valor Unit' precisa ser informado")
	private BigDecimal valorUnit;

	@Column(name = "sub_total", nullable = false)
	@NotNull(message = "Campo 'Subtotal' precisa ser informado")
	private BigDecimal subTotal;

	@Column(name = "valor_desconto", nullable = true)
	private BigDecimal valorDesconto;

	@Column(name = "valor_total", nullable = false)
	@NotNull(message = "Campo 'Valor Total' precisa ser informado")
	private BigDecimal valorTotal;

	public NotaFiscal getNotaFiscal() {
		return notaFiscal;
	}

	public void setNotaFiscal(NotaFiscal notaFiscal) {
		this.notaFiscal = notaFiscal;
	}

	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getNcm() {
		return ncm;
	}

	public void setNcm(String ncm) {
		this.ncm = ncm;
	}

	public Boolean getNcmExistente() {
		return ncmExistente;
	}

	public void setNcmExistente(Boolean ncmExistente) {
		this.ncmExistente = ncmExistente;
	}

	public String getCfop() {
		return cfop;
	}

	public void setCfop(String cfop) {
		this.cfop = cfop;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public BigDecimal getQtde() {
		return qtde;
	}

	public void setQtde(BigDecimal qtde) {
		this.qtde = qtde;
	}

	public BigDecimal getIcmsAliquota() {
		return icmsAliquota;
	}

	public void setIcmsAliquota(BigDecimal icms) {
		this.icmsAliquota = icms;
	}

	public BigDecimal getIcmsValorBC() {
		return icmsValorBC;
	}

	public void setIcmsValorBC(BigDecimal icmsValorBC) {
		this.icmsValorBC = icmsValorBC;
	}

	public BigDecimal getIcmsValor() {
		return icmsValor;
	}

	public void setIcmsValor(BigDecimal icmsValor) {
		this.icmsValor = icmsValor;
	}

	public BigDecimal getValorUnit() {
		return valorUnit;
	}

	public void setValorUnit(BigDecimal valorUnit) {
		this.valorUnit = valorUnit;
	}

	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	public BigDecimal getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(BigDecimal valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	@Override
	public int hashCode() {
		final int prime = 461;
		final int result = 107;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result)
				.append(notaFiscal)
				.append(codigo)
				.append(ordem)
				.append(descricao)
				.append(qtde)
				.append(valorUnit)
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
		final NotaFiscalItem iObj = (NotaFiscalItem) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(notaFiscal, iObj.notaFiscal)
				.append(codigo, iObj.codigo)
				.append(ordem, iObj.ordem)
				.append(descricao, iObj.descricao)
				.append(qtde, iObj.qtde)
				.append(valorUnit, iObj.valorUnit)
				.isEquals();
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NotaFiscalItem clone() {
		NotaFiscalItem itemClone = SerializationUtils.clone(this);
		itemClone.setId(null);
		itemClone.setOrdem(null);
		return itemClone;
	}

	public void calculaTotais() {
		if (getQtde() == null || getValorUnit() == null)
			return;
		this.valorDesconto = this.valorDesconto == null ? BigDecimal.ZERO : this.valorDesconto;
		this.subTotal = CurrencyUtils.multiplicaBD(getQtde(), getValorUnit());
		this.valorTotal = this.subTotal.subtract(this.valorDesconto);
	}

}
