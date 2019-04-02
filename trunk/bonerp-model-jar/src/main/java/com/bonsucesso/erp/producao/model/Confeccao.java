package com.bonsucesso.erp.producao.model;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.bonsucesso.erp.estoque.model.Grade;
import com.bonsucesso.erp.estoque.model.GradeTamanho;
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
@Table(name = "prod_confeccao",
		uniqueConstraints = { @UniqueConstraint(columnNames = { "instituicao_id", "tipo_artigo_id", "descricao" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class Confeccao extends EntityIdImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = -8978307991116643812L;

	@ManyToOne(optional = false)
	@JoinColumn(name = "instituicao_id", nullable = false)
	@NotNull(message = "O campo 'Instituição' deve ser informado")
	private Instituicao instituicao;

	@ManyToOne(optional = false)
	@JoinColumn(name = "tipo_artigo_id", nullable = false)
	@NotNull(message = "O campo 'Tipo Artigo' deve ser informado")
	private TipoArtigo tipoArtigo;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 15, name = "modo_calculo")
	@NotNull(message = "'Modo Cálculo' deve ser informado")
	private ModoCalculoPrecoConfeccao modoCalculo = ModoCalculoPrecoConfeccao.MODO_1;

	@Column(nullable = false, length = 200)
	@Size(min = 2, max = 200, message = "'Descrição' deve possuir entre 2 e 100 caracteres")
	private String descricao;

	@ManyToOne(optional = false)
	@JoinColumn(name = "grade_id", nullable = false)
	@NotNull(message = "O campo 'Grade' deve ser informado")
	private Grade grade;

	@Column(nullable = true, length = 5000, name = "obs")
	@Size(min = 0, max = 5000, message = "Campo 'Obs' deve conter no máximo 5.000 caracteres")
	private String obs;

	@Column(name = "bloqueada")
	@NotNull(message = "O campo 'Bloqueada' deve ser informado")
	private boolean bloqueada = false;

	@Column(name = "oculta")
	@NotNull(message = "O campo 'Oculta' deve ser informado")
	private boolean oculta = false;

	@OneToMany(mappedBy = "confeccao", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
	private List<ConfeccaoItem> itens;

	@Column(name = "margem_padrao", nullable = false)
	@NotNull(message = "O campo 'Margem Padrão' deve ser informado")
	private BigDecimal margemPadrao;

	@Column(name = "custo_operacional_padrao", nullable = false)
	@Min(value = 0, message = "Campo 'Custo Operacional Padrão' não pode ser negativo")
	@NotNull(message = "O campo 'Custo Operacional Padrão' deve ser informado")
	private BigDecimal custoOperacionalPadrao;

	@Column(name = "custo_financeiro_padrao", nullable = false)
	@Min(value = 0, message = "Campo 'Custo Financeiro Padrão' não pode ser negativo")
	@NotNull(message = "O campo 'Custo Financeiro Padrão' deve ser informado")
	private BigDecimal custoFinanceiroPadrao;

	@Column(name = "prazo_padrao", nullable = true)
	@Min(value = 0, message = "Campo 'Prazo Padrão' não pode ser negativo")
	private Integer prazoPadrao;

	@OneToMany(mappedBy = "confeccao", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
	private List<ConfeccaoPreco> precos;

	@Transient
	private List<TipoInsumo> tiposInsumoItem;

	@Transient
	private Map<TipoInsumo, List<ConfeccaoItem>> itensPorTipoInsumo;

	@Transient
	private Map<GradeTamanho, BigDecimal> mapaGradeQtdes;

	@Transient
	private Map<GradeTamanho, BigDecimal> mapaGradeValores;

	// -------------------------------------------------------

	/**
	 *
	 */
	public Confeccao() {
		super();
	}

	public Instituicao getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}

	public TipoArtigo getTipoArtigo() {
		return tipoArtigo;
	}

	public void setTipoArtigo(TipoArtigo tipoArtigo) {
		this.tipoArtigo = tipoArtigo;
	}

	public ModoCalculoPrecoConfeccao getModoCalculo() {
		return modoCalculo;
	}

	public void setModoCalculo(ModoCalculoPrecoConfeccao modoCalculo) {
		this.modoCalculo = modoCalculo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String nome) {
		descricao = nome;
	}

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public boolean isBloqueada() {
		return bloqueada;
	}

	public void setBloqueada(boolean bloqueada) {
		this.bloqueada = bloqueada;
	}

	public boolean isOculta() {
		return oculta;
	}

	public void setOculta(boolean oculta) {
		this.oculta = oculta;
	}

	public List<ConfeccaoItem> getItens() {
		return itens;
	}

	public void setItens(List<ConfeccaoItem> itens) {
		this.itens = itens;
	}

	public BigDecimal getMargemPadrao() {
		return margemPadrao;
	}

	public void setMargemPadrao(BigDecimal margemPadrao) {
		this.margemPadrao = margemPadrao;
	}

	public BigDecimal getCustoOperacionalPadrao() {
		return custoOperacionalPadrao;
	}

	public void setCustoOperacionalPadrao(BigDecimal custoOperacionalPadrao) {
		this.custoOperacionalPadrao = custoOperacionalPadrao;
	}

	public BigDecimal getCustoFinanceiroPadrao() {
		return custoFinanceiroPadrao;
	}

	public void setCustoFinanceiroPadrao(BigDecimal custoFinanceiroPadrao) {
		this.custoFinanceiroPadrao = custoFinanceiroPadrao;
	}

	public Integer getPrazoPadrao() {
		return prazoPadrao;
	}

	public void setPrazoPadrao(Integer prazoPadrao) {
		this.prazoPadrao = prazoPadrao;
	}

	public List<ConfeccaoPreco> getPrecos() {
		if (precos == null) {
			precos = new ArrayList<ConfeccaoPreco>();
		}
		return precos;
	}

	public void setPrecos(List<ConfeccaoPreco> precos) {
		this.precos = precos;
	}

	public void limparPrecos() {
		if ((getPrecos() != null) && (getPrecos().size() > 0)) {
			for (ConfeccaoPreco preco : getPrecos()) {
				preco.setConfeccao(null);
			}
			getPrecos().clear();
		}
	}

	public void addItem(ConfeccaoItem item) {
		if (getItens() == null) {
			List<ConfeccaoItem> itens = new ArrayList<ConfeccaoItem>();
			setItens(itens);
		}
		item.setConfeccao(this);
		itens.add(item);
	}

	@Override
	public int hashCode() {
		final int prime = 421;
		final int result = 431;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).append(instituicao).append(tipoArtigo).append(descricao).toHashCode();
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
		final Confeccao iObj = (Confeccao) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(instituicao, iObj.instituicao)
				.append(tipoArtigo, iObj.tipoArtigo)
				.append(descricao, iObj.descricao)
				.isEquals();
	}

	public List<TipoInsumo> getTiposInsumoItem() {
		if (tiposInsumoItem == null) {
			tiposInsumoItem = new ArrayList<TipoInsumo>();
		}
		return tiposInsumoItem;
	}

	public void setTiposInsumoItem(List<TipoInsumo> tiposInsumoItem) {
		this.tiposInsumoItem = tiposInsumoItem;
	}

	public Map<TipoInsumo, List<ConfeccaoItem>> getItensPorTipoInsumo() {
		if (itensPorTipoInsumo == null) {
			itensPorTipoInsumo = new HashMap<TipoInsumo, List<ConfeccaoItem>>();
		}
		return itensPorTipoInsumo;
	}

	public void setItensPorTipoInsumo(Map<TipoInsumo, List<ConfeccaoItem>> itensPorTipoInsumo) {
		this.itensPorTipoInsumo = itensPorTipoInsumo;
	}

	public Map<GradeTamanho, BigDecimal> getMapaGradeQtdes() {
		if (mapaGradeQtdes == null) {
			mapaGradeQtdes = new HashMap<GradeTamanho, BigDecimal>();
		}
		return mapaGradeQtdes;
	}

	public void setMapaGradeQtdes(Map<GradeTamanho, BigDecimal> mapaGrade) {
		mapaGradeQtdes = mapaGrade;
	}

	public Map<GradeTamanho, BigDecimal> getMapaGradeValores() {
		if (mapaGradeValores == null) {
			mapaGradeValores = new HashMap<GradeTamanho, BigDecimal>();
		}
		return mapaGradeValores;
	}

	public void setMapaGradeValores(Map<GradeTamanho, BigDecimal> mapaGradeValores) {
		this.mapaGradeValores = mapaGradeValores;
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
