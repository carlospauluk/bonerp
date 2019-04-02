package com.bonsucesso.erp.orcamentos.model;



import java.math.BigDecimal;
import java.util.ArrayList;
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
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
 * Entidade Grupo de Itens de Orçamento.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "orc_orcamento_grupo_item",
		uniqueConstraints = { @UniqueConstraint(columnNames = { "ordem", "orcamento_id" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class OrcamentoGrupoItem extends EntityIdImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = 494845381047823741L;

	@ManyToOne(optional = false)
	@JoinColumn(name = "orcamento_id", nullable = false)
	@NotNull(message = "O campo 'Orçamento' deve ser informado")
	private Orcamento orcamento;

	@Column(nullable = false, name = "titulo", length = 80)
	@Size(min = 2, max = 80, message = "O campo 'Título' deve ser informado (entre 2 e 3000 caracteres)")
	@NotNull(message = "O campo 'Título' deve ser informado (entre 2 e 3000 caracteres)")
	private String titulo;

	@Column(name = "ordem", nullable = false)
	@NotNull(message = "Campo 'Ordem' precisa ser informado")
	private Integer ordem;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "grupo", orphanRemoval = true)
	private List<OrcamentoItem> itens;

	@Column(nullable = true, length = 5000, name = "obs")
	@Size(min=0,max=5000,message="Campo 'Obs' deve conter no máximo 5.000 caracteres")
	private String obs;

	@Transient
	private BigDecimal valorTotal;

	public Orcamento getOrcamento() {
		return orcamento;
	}

	public void setOrcamento(Orcamento orcamento) {
		this.orcamento = orcamento;
	}

	public Integer getOrdem() {
		return ordem;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

	public List<OrcamentoItem> getItens() {
		if (itens == null) {
			itens = new ArrayList<OrcamentoItem>();
		}
		return itens;
	}

	public void setItens(List<OrcamentoItem> itens) {
		this.itens = itens;
	}

	public BigDecimal getValorTotal() {
		Double valorTotal = 0.0;
		if (getItens() != null) {
			for (OrcamentoItem item : getItens()) {
				valorTotal = CurrencyUtils.soma(valorTotal, item.getValorTotal().doubleValue());
			}
		}
		return CurrencyUtils.getBigDecimalScaled(valorTotal,2);
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	@Override
	public int hashCode() {
		final int prime = 337;
		final int result = 181;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).append(orcamento).append(ordem).toHashCode();
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
		final OrcamentoGrupoItem iObj = (OrcamentoGrupoItem) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(orcamento, iObj.orcamento)
				.append(ordem, iObj.ordem)
				.isEquals();
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
