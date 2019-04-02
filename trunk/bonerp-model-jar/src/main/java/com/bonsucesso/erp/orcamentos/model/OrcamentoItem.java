package com.bonsucesso.erp.orcamentos.model;



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


/**
 * Entidade Item de Orçamento.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "orc_orcamento_item",
		uniqueConstraints = { @UniqueConstraint(columnNames = { "ordem", "orcamento_grupo_id" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class OrcamentoItem extends EntityIdImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = 494845381047823741L;

	@ManyToOne(optional = false)
	@JoinColumn(name = "orcamento_grupo_id", nullable = false)
	@NotNull(message = "O campo 'Grupo' deve ser informado")
	private OrcamentoGrupoItem grupo;

	@Column(nullable = false, name = "descricao", length = 3000)
	@Size(min = 2, max = 3000, message = "O campo 'Descrição' deve ser informado (entre 2 e 3000 caracteres)")
	@NotNull(message = "O campo 'Descrição' deve ser informado (entre 2 e 3000 caracteres)")
	private String descricao;

	@Column(nullable = true, length = 5000)
	@Size(max = 5000, message = "'Obs' deve possuir até 5000 caracteres")
	private String obs;

	@Column(name = "ordem", nullable = false)
	@NotNull(message = "Campo 'Ordem' precisa ser informado")
	private Integer ordem;

	@Column(name = "qtde", nullable = false)
	@NotNull(message = "Campo 'Qtde' precisa ser informado")
	private BigDecimal qtde;

	@Column(name = "valor_unit", nullable = false)
	@NotNull(message = "Campo 'Valor Unit.' precisa ser informado")
	private BigDecimal valorUnit;

	@Transient
	private BigDecimal valorTotal;

	public OrcamentoGrupoItem getGrupo() {
		return grupo;
	}

	public void setGrupo(OrcamentoGrupoItem grupo) {
		this.grupo = grupo;
	}

	public Integer getOrdem() {
		return ordem;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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

	public BigDecimal getQtde() {
		return qtde;
	}

	public void setQtde(BigDecimal qtde) {
		this.qtde = qtde;
	}

	public BigDecimal getValorUnit() {
		return valorUnit;
	}

	public void setValorUnit(BigDecimal valorUnit) {
		this.valorUnit = valorUnit;
	}

	public BigDecimal getValorTotal() {
		if ((getQtde() != null) && (getValorUnit() != null)) {
			valorTotal = getQtde().multiply(getValorUnit());
		}
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	@Override
	public int hashCode() {
		final int prime = 337;
		final int result = 181;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).append(grupo).append(ordem).toHashCode();
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
		final OrcamentoItem iObj = (OrcamentoItem) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(grupo, iObj.grupo)
				.append(ordem, iObj.ordem)
				.isEquals();
	}

	@Override
	public OrcamentoItem clone() {
		OrcamentoItem itemClone = SerializationUtils.clone(this);
		itemClone.setId(null);
		itemClone.setOrdem(null);
		return itemClone;
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
