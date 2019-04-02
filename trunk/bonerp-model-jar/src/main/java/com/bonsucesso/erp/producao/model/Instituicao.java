package com.bonsucesso.erp.producao.model;



import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.bonsucesso.erp.crm.model.Cliente;
import com.bonsucesso.erp.estoque.model.Fornecedor;
import com.ocabit.base.model.EntityIdImpl;
import com.ocabit.jpa.listener.EntityIdListener;
import com.ocabit.jpa.listener.UpperCaseListener;


/**
 * Entidade Instituicao.
 * Uma Instituição é uma escola, empresa, grupo, igreja, etc. ao qual fornecemos uniformes.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "prod_instituicao",
		uniqueConstraints = {
				@UniqueConstraint(columnNames = { "nome" }),
				@UniqueConstraint(columnNames = { "cliente_id" }),
				@UniqueConstraint(columnNames = { "fornecedor_id" }),
				@UniqueConstraint(columnNames = { "codigo" }, name = "unq_codigo") })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class Instituicao extends EntityIdImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = -7679223136736188547L;

	@OneToOne(cascade = CascadeType.ALL, optional = true)
	@JoinColumn(name = "cliente_id", nullable = true, unique = true)
	@Valid
	private Cliente cliente;

	@OneToOne(cascade = CascadeType.ALL, optional = true)
	@JoinColumn(name = "fornecedor_id", nullable = true, unique = true)
	@Valid
	private Fornecedor fornecedor;

	@Column(nullable = false, length = 100)
	@NotNull(message = "'Código' deve ser informado")
	private Integer codigo;

	@Column(nullable = false, length = 5)
	@Size(min = 2, max = 100, message = "'Nome' deve possuir entre 2 e 100 caracteres")
	@NotNull(message = "'Nome' deve possuir entre 2 e 100 caracteres")
	private String nome;

	@Column(nullable = true, length = 5000, name = "obs")
	@Size(min=0,max=5000,message="Campo 'Obs' deve conter no máximo 5.000 caracteres")
	private String obs;

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	@Override
	public int hashCode() {
		final int prime = 569;
		final int result = 211;
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
		final Instituicao iObj = (Instituicao) obj;
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
