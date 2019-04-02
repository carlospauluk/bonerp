package com.bonsucesso.erp.rh.model;



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
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.bonsucesso.erp.base.model.Endereco;
import com.bonsucesso.erp.base.model.EstadoCivil;
import com.bonsucesso.erp.base.model.Pessoa;
import com.bonsucesso.erp.base.model.PessoaFisica;
import com.bonsucesso.erp.base.model.Sexo;
import com.bonsucesso.erp.base.model.UF;
import com.ocabit.base.model.EntityIdImpl;
import com.ocabit.jpa.listener.EntityIdListener;
import com.ocabit.jpa.listener.NotUpperCase;
import com.ocabit.jpa.listener.UpperCaseListener;


/**
 * Entidade Funcionário.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "rh_funcionario")
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class Funcionario extends EntityIdImpl implements PessoaFisica {

	/**
	 *
	 */
	private static final long serialVersionUID = -7028477648976615167L;

	@ManyToOne(optional = false, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "pessoa_id", nullable = false)
	@NotNull(message = "'Pessoa' deve ser informado")
	@Valid
	private Pessoa pessoa;

	@Column(nullable = false, name = "codigo")
	@NotNull(message = "'Código' deve ser informado")
	private Integer codigo;

	@Column(nullable = true, length = 200, name = "nome_ekt")
	@Size(min = 2, max = 200, message = "'Nome (Ekt)' deve possuir entre 2 e 200 caracteres")
	private String nomeEkt;

	@Column(name = "vendedor_comissionado")
	@NotNull(message = "O campo 'Comissionado' deve ser informado")
	private Boolean vendedorComissionado;

	@Column(name = "clt")
	@NotNull(message = "O campo 'CLT' deve ser informado")
	private Boolean clt;

	@Column(nullable = true, length = 15, name = "fone1")
	private String fone1;

	@Column(nullable = true, length = 15, name = "fone2")
	private String fone2;

	@Column(nullable = true, length = 15, name = "fone3")
	private String fone3;

	@Column(nullable = true, length = 15, name = "fone4")
	private String fone4;

	@Column(nullable = true, length = 50, name = "email")
	private String email;

	// ------------- PF --------------
	@Enumerated(EnumType.STRING)
	@Column(nullable = true, length = 13, name = "estado_civil")
	private EstadoCivil estadoCivil;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_nascimento", nullable = true)
	private Date dtNascimento;

	@Enumerated(EnumType.STRING)
	@Column(nullable = true, length = 9, name = "sexo")
	private Sexo sexo;

	@Column(nullable = true, length = 15, name = "rg")
	private String rg;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_emissao_rg", nullable = true)
	private Date dtEmissaoRG;

	@Column(nullable = true, length = 15, name = "orgao_emissor_rg")
	private String orgaoEmissorRG;

	@Enumerated(EnumType.STRING)
	@Column(nullable = true, length = 2, name = "estado_rg")
	private UF estadoRG;

	@Column(nullable = true, length = 50, name = "naturalidade")
	private String naturalidade;

	@Transient
	private Endereco endereco;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "funcionario")
	@Valid
	private List<FuncionarioCargo> cargos;

	@Column(nullable = true, length = 200)
	@NotUpperCase
	private String senha;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinTable(name = "rh_funcionario_enderecos",
			joinColumns = @JoinColumn(name = "rh_funcionario_id"),
			inverseJoinColumns = @JoinColumn(name = "bon_endereco_id"))
	@Valid
	private List<Endereco> enderecos;

	/**
	 * Construtor default.
	 */
	public Funcionario() {
		setPessoa(new Pessoa());
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNomeEkt() {
		return nomeEkt;
	}

	public void setNomeEkt(String nomeEkt) {
		this.nomeEkt = nomeEkt;
	}

	public Boolean getVendedorComissionado() {
		return vendedorComissionado;
	}

	public void setVendedorComissionado(Boolean vendedorComissionado) {
		this.vendedorComissionado = vendedorComissionado;
	}

	public Boolean getClt() {
		return clt;
	}

	public void setClt(Boolean clt) {
		this.clt = clt;
	}

	public String getFone1() {
		return fone1;
	}

	public void setFone1(String fone1) {
		this.fone1 = fone1;
	}

	public String getFone2() {
		return fone2;
	}

	public void setFone2(String fone2) {
		this.fone2 = fone2;
	}

	public String getFone3() {
		return fone3;
	}

	public void setFone3(String fone3) {
		this.fone3 = fone3;
	}

	public String getFone4() {
		return fone4;
	}

	public void setFone4(String fone4) {
		this.fone4 = fone4;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public Date getDtNascimento() {
		return dtNascimento;
	}

	public void setDtNascimento(Date dtNascimento) {
		this.dtNascimento = dtNascimento;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public Date getDtEmissaoRG() {
		return dtEmissaoRG;
	}

	public void setDtEmissaoRG(Date dtEmissaoRG) {
		this.dtEmissaoRG = dtEmissaoRG;
	}

	public String getOrgaoEmissorRG() {
		return orgaoEmissorRG;
	}

	public void setOrgaoEmissorRG(String orgaoEmissorRG) {
		this.orgaoEmissorRG = orgaoEmissorRG;
	}

	public UF getEstadoRG() {
		return estadoRG;
	}

	public void setEstadoRG(UF estadoRG) {
		this.estadoRG = estadoRG;
	}

	public String getNaturalidade() {
		return naturalidade;
	}

	public void setNaturalidade(String naturalidade) {
		this.naturalidade = naturalidade;
	}

	public List<FuncionarioCargo> getCargos() {
		if (cargos == null) {
			cargos = new ArrayList<FuncionarioCargo>();
		}
		return cargos;
	}

	public void setCargos(List<FuncionarioCargo> cargos) {
		this.cargos = cargos;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	@Override
	public int hashCode() {
		final int prime = 983;
		final int result = 7;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).append(pessoa).toHashCode();
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
		final Funcionario iObj = (Funcionario) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(pessoa, iObj.pessoa)
				.isEquals();
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Endereco getEndereco() {
		if (!CollectionUtils.isEmpty(getEnderecos())) {
			endereco = getEnderecos().get(0);
		} else if (endereco == null) {
			endereco = new Endereco();
		}
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

}
