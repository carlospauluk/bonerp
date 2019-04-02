package com.bonsucesso.erp.orcamentos.model;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.bonsucesso.erp.crm.model.Cliente;
import com.ocabit.base.model.EntityIdImpl;
import com.ocabit.jpa.listener.EntityIdListener;
import com.ocabit.jpa.listener.NotUpperCase;
import com.ocabit.jpa.listener.UpperCaseListener;
import com.ocabit.utils.currency.CurrencyUtils;


/**
 * Entidade Orçamento.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "orc_orcamento")
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class Orcamento extends EntityIdImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = 7061311536093325880L;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 100, name = "tipo_orcamento")
	private TipoOrcamento tipoOrcamento;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "orcamento", orphanRemoval = false)
	private List<OrcamentoArquivo> arquivos;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 100, name = "status")
	@NotNull(message = "O campo 'Status' deve ser informado")
	private StatusOrcamento status = StatusOrcamento.NOVO;

	@Column(nullable = false, name = "preenchido_por", length = 80)
	@Size(min = 2, max = 80, message = "O campo 'Preenchido por' deve ser informado (entre 2 e 80 caracteres)")
	@NotNull(message = "O campo 'Preenchido por' deve ser informado (entre 2 e 80 caracteres)")
	private String preenchidoPor;

	@Column(nullable = true, length = 5000)
	@Size(max = 5000, message = "'Cabeçalho' deve possuir até 5000 caracteres")
	@NotUpperCase
	private String cabecalho;

	@Column(nullable = true, name = "dt_preenchimento")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dtPreenchimento;

	@Column(nullable = true, name = "dt_validade")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dtValidade;

	@ManyToOne(optional = true)
	@JoinColumn(name = "cliente_id", nullable = true)
	private Cliente cliente;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "orcamento", orphanRemoval = true)
	private List<OrcamentoGrupoItem> grupos;

	@Column(nullable = true, length = 5000, name = "obs")
	@Size(min=0,max=5000,message="Campo 'Obs' deve conter no máximo 5.000 caracteres")
	@NotUpperCase
	private String obs;

	@Column(name = "desconto", nullable = true)
	private BigDecimal desconto;

	@Column(name = "exibir_totais", nullable = false)
	private boolean exibirTotais = true;

	@Transient
	private BigDecimal subTotal;

	@Transient
	private BigDecimal total;

	public TipoOrcamento getTipoOrcamento() {
		return tipoOrcamento;
	}

	public void setTipoOrcamento(TipoOrcamento tipoOrcamento) {
		this.tipoOrcamento = tipoOrcamento;
	}

	public List<OrcamentoArquivo> getArquivos() {
		return arquivos;
	}

	public void setArquivos(List<OrcamentoArquivo> arquivos) {
		this.arquivos = arquivos;
	}

	public StatusOrcamento getStatus() {
		return status;
	}

	public void setStatus(StatusOrcamento status) {
		this.status = status;
	}

	public String getCabecalho() {
		return cabecalho;
	}

	public void setCabecalho(String cabecalho) {
		this.cabecalho = cabecalho;
	}

	public String getPreenchidoPor() {
		return preenchidoPor;
	}

	public void setPreenchidoPor(String preenchidoPor) {
		this.preenchidoPor = preenchidoPor;
	}

	public Date getDtPreenchimento() {
		return dtPreenchimento;
	}

	public void setDtPreenchimento(Date dtPreenchimento) {
		this.dtPreenchimento = dtPreenchimento;
	}

	public Date getDtValidade() {
		return dtValidade;
	}

	public void setDtValidade(Date dtValidate) {
		dtValidade = dtValidate;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<OrcamentoGrupoItem> getGrupos() {
		if (grupos == null) {
			grupos = new ArrayList<OrcamentoGrupoItem>();
		}
		return grupos;
	}

	public void setGrupos(List<OrcamentoGrupoItem> grupos) {
		this.grupos = grupos;
	}

	public BigDecimal getSubTotal() {
		Double subTotal = 0.0;
		try {
			if (getGrupos() != null) {
				for (OrcamentoGrupoItem grupo : getGrupos()) {
					subTotal = CurrencyUtils.soma(subTotal, grupo.getValorTotal().doubleValue());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return CurrencyUtils.getBigDecimalScaled(subTotal,2);
	}

	public BigDecimal getDesconto() {
		return desconto;
	}

	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}

	public boolean isExibirTotais() {
		return exibirTotais;
	}

	public void setExibirTotais(boolean exibirTotais) {
		this.exibirTotais = exibirTotais;
	}

	public BigDecimal getTotal() {
		if ((getDesconto() != null) && (getSubTotal() != null)) {
			BigDecimal bdDesconto = CurrencyUtils.getBigDecimalScaled(100 - getDesconto().doubleValue(), 2);
			bdDesconto = bdDesconto.divide(CurrencyUtils.getBigDecimalScaled(100.00, 2));
			total = getSubTotal().multiply(bdDesconto);
		}
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public void setSubTotal(BigDecimal valorTotal) {
		subTotal = valorTotal;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	@Override
	public int hashCode() {
		final int prime = 337;
		final int result = 181;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).append(dtPreenchimento).append(dtValidade).append(cliente)
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
		final Orcamento iObj = (Orcamento) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(dtPreenchimento, iObj.dtPreenchimento)
				.append(dtValidade, iObj.dtValidade)
				.append(cliente, iObj.cliente)
				.isEquals();
	}

	public Orcamento clonar() {
		Orcamento clone = SerializationUtils.clone(this);
		clone.setId(null);
		clone.setGrupos(null);
		for (OrcamentoGrupoItem grupo : getGrupos()) {
			OrcamentoGrupoItem grupoClone = SerializationUtils.clone(grupo);
			grupoClone.setOrcamento(clone);
			grupoClone.setId(null);
			clone.getGrupos().add(grupoClone);
			grupoClone.setItens(null);
			for (OrcamentoItem item : grupo.getItens()) {
				OrcamentoItem itemClone = SerializationUtils.clone(item);
				itemClone.setGrupo(grupoClone);
				itemClone.setId(null);
				grupoClone.getItens().add(itemClone);
			}
		}

		return clone;
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
