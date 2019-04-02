package com.bonsucesso.erp.estoque.model;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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


/**
 *
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "est_produto",
		uniqueConstraints = { @UniqueConstraint(columnNames = { "reduzido" }) },
		indexes = { @Index(columnList = "descricao", unique = false),
				@Index(columnList = "reduzido_ekt", unique = false) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class Produto extends EntityIdImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = 2257010320808544565L;

	@ManyToOne(optional = true)
	@JoinColumn(name = "depto_imp_id", nullable = true)
	private Departamento deptoImportado;

	@ManyToOne(optional = false)
	@JoinColumn(name = "subdepto_id", nullable = false)
	@NotNull(message = "O campo 'Subdepto' deve ser informado")
	private Subdepartamento subdepto;

	/**
	 * Em caso de erro de importação.
	 */
	@Column(name = "subdepto_err", length = 200)
	private String subdeptoErr;

	@ManyToOne(optional = false)
	@JoinColumn(name = "fornecedor_id", nullable = false)
	@NotNull(message = "O campo 'Fornecedor' deve ser informado")
	private Fornecedor fornecedor;

	@ManyToOne(optional = false)
	@JoinColumn(name = "unidade_produto_id", nullable = false)
	@NotNull(message = "O campo 'Unidade' deve ser informado")
	private UnidadeProduto unidadeProduto;

	/**
	 * Em caso de erro de importação.
	 */
	@Column(name = "unidade_produto_err", length = 200)
	private String unidadeProdutoErr;

	@ManyToOne(optional = false)
	@JoinColumn(name = "grade_id", nullable = false)
	@NotNull(message = "O campo 'Grade' deve ser informado")
	private Grade grade;

	/**
	 * Em caso de erro de importação.
	 */
	@Column(name = "grade_err", length = 200)
	private String gradeErr;

	/**
	 * YYMM999999
	 */
	@Column(name = "reduzido", nullable = false)
	@NotNull(message = "Campo 'Reduzido' precisa ser informado")
	private Long reduzido;

	@Column(name = "reduzido_ekt", nullable = true)
	private Integer reduzidoEkt;

	@Column(nullable = true, name = "reduzido_ekt_desde")
	@Temporal(TemporalType.DATE)
	private Date reduzidoEktDesde;

	@Column(nullable = true, name = "reduzido_ekt_ate")
	@Temporal(TemporalType.DATE)
	private Date reduzidoEktAte;

	@Column(name = "referencia", nullable = false, length = 20)
	@NotNull(message = "Campo 'Referência' deve possuir no máximo 20 caracteres")
	@Size(min = 0, max = 20, message = "Campo 'Referência' deve possuir no máximo 20 caracteres")
	private String referencia;

	@Column(name = "descricao", nullable = false, length = 200)
	@Size(min = 2, max = 200, message = "'Descrição' deve possuir entre 2 e 100 caracteres")
	private String descricao;

	@Column(nullable = true, name = "dt_ult_venda")
	@Temporal(TemporalType.DATE)
	private Date dtUltVenda;

	@Column(nullable = true, length = 5000, name = "obs")
	@Size(min=0,max=5000,message="Campo 'Obs' deve conter no máximo 5.000 caracteres")
	private String obs;

	@OneToMany(mappedBy = "produto", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@OrderBy("id DESC")
	private List<ProdutoPreco> precos;

	@OneToMany(mappedBy = "produto", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ProdutoSaldo> saldos;

	@Transient
	private BigDecimal qtdeTotal;

	// Aqui há uma lógica para descobrir qual o é o preco a ser utilizado.
	@Transient
	private ProdutoPreco precoAtual;

	@Column(name = "cst", nullable = false, length = 30)
	@NotNull(message = "Campo 'CST' precisa ser informado")
	private String cst;

	@Column(name = "fracionado", nullable = false)
	@NotNull(message = "Campo 'Fracionado' precisa ser informado")
	private Boolean fracionado = false;

	@Column(name = "icms", nullable = false)
	@NotNull(message = "Campo 'ICMS' precisa ser informado")
	private Integer icms;

	@Column(name = "ncm", nullable = false, length = 30)
	@NotNull(message = "Campo 'NCM' precisa ser informado")
	private String ncm;

	@Column(name = "tipo_tributacao", nullable = false, length = 30)
	@NotNull(message = "Campo 'Tipo Tributação' precisa ser informado")
	private String tipoTributacao;

	// -------------------------------------------------------

	/**
	 *
	 */
	public Produto() {
		super();
	}

	public Subdepartamento getSubdepto() {
		return subdepto;
	}

	public void setSubdepto(Subdepartamento subdepto) {
		this.subdepto = subdepto;
	}

	public Departamento getDeptoImportado() {
		return deptoImportado;
	}

	public void setDeptoImportado(Departamento deptoImportado) {
		this.deptoImportado = deptoImportado;
	}

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public Long getReduzido() {
		return reduzido;
	}

	public void setReduzido(Long reduzido) {
		this.reduzido = reduzido;
	}

	public Integer getReduzidoEkt() {
		return reduzidoEkt;
	}

	public void setReduzidoEkt(Integer reduzidoEkt) {
		this.reduzidoEkt = reduzidoEkt;
	}

	public Date getReduzidoEktDesde() {
		return reduzidoEktDesde;
	}

	public void setReduzidoEktDesde(Date reduzidoEktDesde) {
		this.reduzidoEktDesde = reduzidoEktDesde;
	}

	public Date getReduzidoEktAte() {
		return reduzidoEktAte;
	}

	public void setReduzidoEktAte(Date reduzidoEktAte) {
		this.reduzidoEktAte = reduzidoEktAte;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String nome) {
		descricao = nome;
	}

	public Date getDtUltVenda() {
		return dtUltVenda;
	}

	public void setDtUltVenda(Date dtUltVenda) {
		this.dtUltVenda = dtUltVenda;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public UnidadeProduto getUnidadeProduto() {
		return unidadeProduto;
	}

	public void setUnidadeProduto(UnidadeProduto unidadeProduto) {
		this.unidadeProduto = unidadeProduto;
	}

	public List<ProdutoPreco> getPrecos() {
		if (precos == null) {
			precos = new ArrayList<ProdutoPreco>();
		}
		return precos;
	}

	public void setPrecos(List<ProdutoPreco> precos) {
		this.precos = precos;
	}

	public List<ProdutoSaldo> getSaldos() {
		if (saldos == null) {
			saldos = new ArrayList<ProdutoSaldo>();
		}
		return saldos;
	}

	public void setSaldos(List<ProdutoSaldo> saldos) {
		this.saldos = saldos;
	}

	public BigDecimal getQtdeTotal() {
		return qtdeTotal == null ? BigDecimal.ZERO : qtdeTotal;
	}

	public void setQtdeTotal(BigDecimal qtdeTotal) {
		this.qtdeTotal = qtdeTotal;
	}

	public void addPreco(ProdutoPreco preco) {
		if (getPrecos() == null) {
			List<ProdutoPreco> precos = new ArrayList<ProdutoPreco>();
			setPrecos(precos);
		}
		precos.add(preco);
	}

	public ProdutoPreco getPrecoAtual() {
		if ((getPrecos() != null) && (getPrecos().size() > 0) && (getPrecos().get(0) != null)) {
			// pego o primeiro, pois está ordenado por dtCusto DESC
			precoAtual = getPrecos().get(0);
		} else {
			precoAtual = new ProdutoPreco();
		}
		return precoAtual;
	}

	public void setPrecoAtual(ProdutoPreco precoAtual) {
		this.precoAtual = precoAtual;
	}

	public String getSubdeptoErr() {
		return subdeptoErr;
	}

	public void setSubdeptoErr(String subdeptoErr) {
		this.subdeptoErr = subdeptoErr;
	}

	public String getUnidadeProdutoErr() {
		return unidadeProdutoErr;
	}

	public void setUnidadeProdutoErr(String unidadeProdutoErr) {
		this.unidadeProdutoErr = unidadeProdutoErr;
	}

	public String getGradeErr() {
		return gradeErr;
	}

	public void setGradeErr(String gradeErr) {
		this.gradeErr = gradeErr;
	}

	public String getCst() {
		return cst;
	}

	public void setCst(String cst) {
		this.cst = cst;
	}

	public Boolean getFracionado() {
		return fracionado;
	}

	public void setFracionado(Boolean fracionado) {
		this.fracionado = fracionado;
	}

	public Integer getIcms() {
		return icms;
	}

	public void setIcms(Integer icms) {
		this.icms = icms;
	}

	public String getNcm() {
		return ncm;
	}

	public void setNcm(String ncm) {
		this.ncm = ncm;
	}

	public String getTipoTributacao() {
		return tipoTributacao;
	}

	public void setTipoTributacao(String tipoTributacao) {
		this.tipoTributacao = tipoTributacao;
	}

	@Override
	public int hashCode() {
		final int prime = 877;
		final int result = 563;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).append(subdepto).append(descricao).append(fornecedor).toHashCode();
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
		final Produto iObj = (Produto) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(subdepto, iObj.subdepto)
				.append(descricao, iObj.descricao)
				.append(fornecedor, iObj.fornecedor)
				.isEquals();
	}

	@Override
	public String toStringToView() {
		return "Produto [reduzido=" + reduzido + ", reduzidoEkt=" + reduzidoEkt + ", descricao=" + descricao + "]";
	}

}
