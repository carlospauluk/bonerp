package com.bonsucesso.erp.producao.model;



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

import com.bonsucesso.erp.estoque.model.UnidadeProduto;
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
@Table(name = "prod_insumo", uniqueConstraints = { @UniqueConstraint(columnNames = { "codigo" },
		name = "unq_insumo_descricao"),
		@UniqueConstraint(columnNames = { "descricao" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class Insumo extends EntityIdImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = -2668832770462787921L;

	@ManyToOne(optional = false)
	@JoinColumn(name = "tipo_insumo_id", nullable = false)
	@NotNull(message = "O campo 'Tipo Insumo' deve ser informado")
	private TipoInsumo tipoInsumo;

	@Column(nullable = false, length = 5)
	@NotNull(message = "'Código' deve ser informado")
	private Integer codigo;

	@Column(nullable = false, length = 200)
	@Size(min = 2, max = 200, message = "'Descrição' deve possuir entre 2 e 100 caracteres")
	private String descricao;

	@ManyToOne(optional = false)
	@JoinColumn(name = "unidade_produto_id", nullable = false)
	@NotNull(message = "O campo 'Unidade' deve ser informado")
	private UnidadeProduto unidadeProduto;

	@OneToMany(mappedBy = "insumo", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
	private List<InsumoPreco> precos;

	// Aqui há uma lógica para descobrir qual o é o preco a ser utilizado.
	@Transient
	private InsumoPreco precoAtual;

	// -------------------------------------------------------

	/**
	 *
	 */
	public Insumo() {
		super();
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public TipoInsumo getTipoInsumo() {
		return tipoInsumo;
	}

	public void setTipoInsumo(TipoInsumo tipoInsumo) {
		this.tipoInsumo = tipoInsumo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String nome) {
		descricao = nome;
	}

	public UnidadeProduto getUnidadeProduto() {
		return unidadeProduto;
	}

	public void setUnidadeProduto(UnidadeProduto unidadeProduto) {
		this.unidadeProduto = unidadeProduto;
	}

	public List<InsumoPreco> getPrecos() {
		if (precos == null) {
			precos = new ArrayList<InsumoPreco>();
		}
		return precos;
	}

	public void setPrecos(List<InsumoPreco> precos) {
		this.precos = precos;
	}

	public void addPreco(InsumoPreco preco) {
		if (getPrecos() == null) {
			List<InsumoPreco> precos = new ArrayList<InsumoPreco>();
			setPrecos(precos);
		}
		preco.setInsumo(this);
		precos.add(preco);
	}

	public InsumoPreco getPrecoAtual() {
		try {
			if ((getPrecos() != null) && (getPrecos().size() > 0) && (getPrecos().get(0) != null)) {
				// pego o primeiro, pois está ordenado por dtCusto DESC
				precoAtual = getPrecos().get(0);
			} else {
				precoAtual = new InsumoPreco();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return precoAtual;
	}

	public void setPrecoAtual(InsumoPreco precoAtual) {
		this.precoAtual = precoAtual;
	}

	@Override
	public int hashCode() {
		final int prime = 59;
		final int result = 61;
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
		final Insumo iObj = (Insumo) obj;
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
