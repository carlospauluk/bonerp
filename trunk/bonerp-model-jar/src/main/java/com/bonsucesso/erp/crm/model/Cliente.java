package com.bonsucesso.erp.crm.model;



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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
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
import com.bonsucesso.erp.base.model.PessoaJuridica;
import com.bonsucesso.erp.base.model.Sexo;
import com.bonsucesso.erp.base.model.TipoPessoa;
import com.bonsucesso.erp.base.model.UF;
import com.ocabit.base.model.EntityIdImpl;
import com.ocabit.jpa.listener.EntityIdListener;
import com.ocabit.jpa.listener.UpperCaseListener;


/**
 * Entidade Cliente.
 *
 * TODO: unificar a base de clientes em um só lugar.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "crm_cliente", uniqueConstraints = { @UniqueConstraint(columnNames = "codigo",
		name = "unq_cliente_codigo") })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class Cliente extends EntityIdImpl implements PessoaFisica, PessoaJuridica {

	/**
	 *
	 */
	private static final long serialVersionUID = -7028477527086615167L;

	@ManyToOne(optional = false, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "pessoa_id", nullable = false)
	@NotNull(message = "'Pessoa' deve ser informado")
	@Valid
	private Pessoa pessoa;

	@ManyToMany(cascade = { CascadeType.REFRESH, CascadeType.DETACH }, fetch = FetchType.LAZY)
	@JoinTable(name = "crm_cliente_canais", joinColumns = { @JoinColumn(name = "cliente_id") },
			inverseJoinColumns = {
					@JoinColumn(name = "canal_id") })
	private List<Canal> canais;

	@Column(nullable = false, name = "codigo")
	@NotNull(message = "'Código' deve ser informado")
	private Integer codigo;

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

	@Column(nullable = true, length = 100, name = "contato")
	private String contato;

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

	// ------------- PJ --------------
	@Column(nullable = true, length = 20, name = "inscricao_estadual")
	private String inscricaoEstadual;

	@Column(nullable = true, length = 40, name = "inscricao_municipal")
	private String inscricaoMunicipal;

	@Column(nullable = true, length = 120, name = "website")
	private String website;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinTable(name = "crm_cliente_enderecos",
			joinColumns = @JoinColumn(name = "crm_cliente_id"),
			inverseJoinColumns = @JoinColumn(name = "bon_endereco_id"))
	@Valid
	private List<Endereco> enderecos;

	@Column(nullable = true, length = 5000, name = "obs")
	@Size(min = 0, max = 5000, message = "Campo 'Obs' deve conter no máximo 5.000 caracteres")
	private String obs;

	@Transient
	private Endereco endereco;

	// Campo que informa se o cliente possui whatsapp
	@Column(name = "tem_whatsapp", nullable = true)
	private Boolean temWhatsapp = false;

	// Campo que informa se o cliente aceita receber mensagens nossas pelo whatsapp
	@Column(name = "aceita_whatsapp", nullable = true)
	private Boolean aceitaWhatsapp = false;

	/**
	 * Construtor default.
	 */
	public Cliente() {
		setPessoa(new Pessoa(TipoPessoa.PESSOA_FISICA));
	}

	public Cliente(TipoPessoa tipoPessoa) {
		setPessoa(new Pessoa(tipoPessoa));
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public List<Canal> getCanais() {
		if (canais == null) {
			canais = new ArrayList<Canal>();
		}
		return canais;
	}

	public void setCanais(List<Canal> canais) {
		this.canais = canais;
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

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
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

	public void setRg(String rG) {
		rg = rG;
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

	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}

	public String getInscricaoMunicipal() {
		return inscricaoMunicipal;
	}

	public void setInscricaoMunicipal(String inscricaoMunicipal) {
		this.inscricaoMunicipal = inscricaoMunicipal;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public List<Endereco> getEnderecos() {
		if (enderecos == null) {
			enderecos = new ArrayList<Endereco>();
		}
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public Boolean getTemWhatsapp() {
		return temWhatsapp;
	}

	public void setTemWhatsapp(Boolean temWhatsapp) {
		this.temWhatsapp = temWhatsapp;
	}

	public Boolean getAceitaWhatsapp() {
		return aceitaWhatsapp;
	}

	public void setAceitaWhatsapp(Boolean aceitaWhatsapp) {
		this.aceitaWhatsapp = aceitaWhatsapp;
	}

	@Override
	public int hashCode() {
		final int prime = 877;
		final int result = 563;
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
		final Cliente iObj = (Cliente) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(codigo, iObj.codigo)
				.isEquals();
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

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
