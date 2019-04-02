package com.bonsucesso.erp.producao.model;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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


/**
 *
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "prod_lote_confeccao",
		uniqueConstraints = { @UniqueConstraint(columnNames = { "codigo" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class LoteConfeccao extends EntityIdImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = 7192760526767636276L;

	@Column(nullable = false, length = 5)
	@NotNull(message = "'Código' deve ser informado")
	private Integer codigo;

	@Column(nullable = false, length = 200)
	@Size(min = 2, max = 200, message = "'Descrição' deve possuir entre 2 e 100 caracteres")
	private String descricao;

	@Column(nullable = true, name = "dt_lote")
	@Temporal(TemporalType.DATE)
	private Date dtLote;

	@OneToMany(mappedBy = "loteConfeccao", fetch = FetchType.LAZY, orphanRemoval = true, cascade = {
			CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	private List<LoteConfeccaoItem> itens;

	// -------------------------------------------------------

	/**
	 *
	 */
	public LoteConfeccao() {
		super();
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String nome) {
		descricao = nome;
	}

	public Date getDtLote() {
		return dtLote;
	}

	public void setDtLote(Date dtLote) {
		this.dtLote = dtLote;
	}

	public void addItem(LoteConfeccaoItem item) {
		item.setLoteConfeccao(this);
		getItens().add(item);
	}

	public List<LoteConfeccaoItem> getItens() {
		if (itens == null) {
			itens = new ArrayList<LoteConfeccaoItem>();
		}
		return itens;
	}

	public void setItens(List<LoteConfeccaoItem> itens) {
		this.itens = itens;
	}

	@Override
	public int hashCode() {
		final int prime = 421;
		final int result = 431;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).append(codigo).toHashCode();
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
		final LoteConfeccao iObj = (LoteConfeccao) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(codigo, iObj.codigo)
				.isEquals();
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
