package com.bonsucesso.erp.cortinas.model;



import java.math.BigDecimal;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
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
@Table(name = "crtn_cortina_item")
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class CortinaItem extends EntityIdImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = -3758457850723676818L;

	@ManyToOne(optional = false)
	@JoinColumn(name = "cortina_id", nullable = false)
	@NotNull(message = "O campo 'Cortina' deve ser informado")
	private Cortina cortina;

	@ManyToOne(optional = false)
	@JoinColumn(name = "artigoCortina_id", nullable = false)
	@NotNull(message = "O campo 'Artigo' deve ser informado")
	private ArtigoCortina artigoCortina = new ArtigoCortina(); // FIXME: está certo?

	// informa em qual camada este item estará
	@Column(name = "camada", nullable = false)
	@NotNull(message = "Campo 'Camada' precisa ser informado")
	private Integer camada;

	// ----------------- CASO O ITEM SEJA TECIDO -----------------
	@Column(name = "tecido_fator", nullable = true)
	private BigDecimal fator;

	// Fator real para os casos onde o tecido é utilizado na VERTICAL.
	@Column(name = "tecido_fator_real", nullable = true)
	private BigDecimal fatorReal;

	// Qtde de 'larguras' de tecido para quando é utilizado na VERTICAL.
	@Column(name = "tecido_qtde_larguras", nullable = true)
	private Integer larguras;

	// TipoArtigoCortina.RODIZIO, TipoArtigoCortina.ILHOS, TipoArtigoCortina.ARGOLA
	@Enumerated(EnumType.STRING)
	@Column(nullable = true, length = 100, name = "tecido_tipo_fixacao")
	private TipoArtigoCortina tipoFixacao = TipoArtigoCortina.NULLED;

	@Enumerated(EnumType.STRING)
	@Column(name = "orientacao_tecido", nullable = true, length = 30)
	private OrientacaoTecido orientacao;

	@Column(name = "altura_barra", nullable = true)
	private BigDecimal alturaBarra;

	@Column(nullable = true, length = 100)
	@Size(max = 100, message = "'Tipo Prega' deve possuir até 100 caracteres")
	private String tipoPrega;

	/**
	 * Informa se é bandô.
	 */
	@Column(name = "bando", nullable = false)
	@NotNull(message = "Campo 'Bandô' precisa ser informado")
	private Boolean bando = false;

	/**
	 * Informa se é drapeado.
	 */
	@Column(name = "drapeado", nullable = false)
	@NotNull(message = "Campo 'Drapeado' precisa ser informado")
	private Boolean drapeado = false;

	/**
	 * Informa, no caso de drapeado, a qtde de lances (quedas).
	 */
	@Column(name = "drapeado_lances", nullable = false)
	@Min(value = 0, message = "'Drapeado - Lances' deve ser informado.")
	private int drapeadoLances = 0;

	/**
	 * Informa, no caso de drapeado, qual a altura do mesmo (conta-se a partir do varão).
	 */
	@Column(name = "drapeado_largura", nullable = true)
	private BigDecimal drapeadoLargura;
	/**
	 * Informa, no caso de drapeado, qual a altura do mesmo (conta-se a partir do varão).
	 */
	@Column(name = "drapeado_altura1", nullable = true)
	private BigDecimal drapeadoAltura1;
	/**
	 * Informa, no caso de drapeado, qual a altura do mesmo (conta-se a partir do varão).
	 */
	@Column(name = "drapeado_altura2", nullable = true)
	private BigDecimal drapeadoAltura2;
	// -----------------------------------------------------------

	@Column(name = "qtde", nullable = false)
	@NotNull(message = "Campo 'Qtde' precisa ser informado")
	private BigDecimal qtde = BigDecimal.ZERO;

	/**
	 * Informa se é para alterar a quantidade no caso de recálculo de qtdes (serve para quando a qtde é informada manualmente).
	 */
	@Column(name = "qtde_nao_alterar", nullable = false)
	@NotNull(message = "Campo 'Qtde Não Alterar' precisa ser informado")
	private Boolean qtdeNaoAlterar = false;

	@Column(nullable = true, length = 5000, name = "obs")
	@Size(min = 0, max = 5000, message = "Campo 'Obs' deve conter no máximo 5.000 caracteres")
	private String obs;

	@Column(name = "preco_prazo", nullable = false)
	@NotNull(message = "Campo 'Preço Prazo' precisa ser informado")
	@Min(value = 0, message = "Campo 'Preço Prazo' precisa ser informado")
	private BigDecimal precoPrazo;

	@Column(name = "preco_vista", nullable = false)
	@NotNull(message = "Campo 'Preço Vista' precisa ser informado")
	@Min(value = 0, message = "Campo 'Preço Vista' precisa ser informado")
	private BigDecimal precoVista;

	@Column(name = "preco_promo", nullable = true)
	private BigDecimal precoPromo;

	@Transient
	private BigDecimal precoVigente;

	@Transient
	private BigDecimal valorTotal;

	public Cortina getCortina() {
		return cortina;
	}

	public void setCortina(Cortina cortina) {
		this.cortina = cortina;
	}

	public ArtigoCortina getArtigoCortina() {
		return artigoCortina;
	}

	public void setArtigoCortina(ArtigoCortina artigoCortina) {
		this.artigoCortina = artigoCortina;
	}

	public Integer getCamada() {
		return camada;
	}

	public void setCamada(Integer camada) {
		this.camada = camada;
	}

	public BigDecimal getFator() {
		return fator;
	}

	public void setFator(BigDecimal fator) {
		this.fator = fator;
	}

	public BigDecimal getFatorReal() {
		return fatorReal;
	}

	public void setFatorReal(BigDecimal fatorReal) {
		this.fatorReal = fatorReal;
	}

	public Integer getLarguras() {
		return larguras;
	}

	public void setLarguras(Integer larguras) {
		this.larguras = larguras;
	}

	public TipoArtigoCortina getTipoFixacao() {
		return tipoFixacao;
	}

	public void setTipoFixacao(TipoArtigoCortina tipoFixacao) {
		if ((tipoFixacao == null) || (tipoFixacao.equals(TipoArtigoCortina.ARGOLA) ||
				tipoFixacao.equals(TipoArtigoCortina.ILHOS) ||
				tipoFixacao.equals(TipoArtigoCortina.RODIZIO) ||
				tipoFixacao.equals(TipoArtigoCortina.NULLED))) {
			this.tipoFixacao = tipoFixacao;
		} else {
			throw new IllegalStateException("Tipo de fixação inválido.");
		}
	}

	public OrientacaoTecido getOrientacao() {
		return orientacao;
	}

	public void setOrientacao(OrientacaoTecido orientacao) {
		this.orientacao = orientacao;
	}

	public BigDecimal getAlturaBarra() {
		return alturaBarra;
	}

	public void setAlturaBarra(BigDecimal alturaBarraPadrao) {
		alturaBarra = alturaBarraPadrao;
	}

	public String getTipoPrega() {
		return tipoPrega;
	}

	public void setTipoPrega(String tipoPrega) {
		this.tipoPrega = tipoPrega;
	}

	public Boolean getDrapeado() {
		return drapeado;
	}

	public void setDrapeado(Boolean drapeado) {
		this.drapeado = drapeado;
	}

	public Boolean getBando() {
		return bando;
	}

	public void setBando(Boolean bando) {
		this.bando = bando;
	}

	public int getDrapeadoLances() {
		return drapeadoLances;
	}

	public void setDrapeadoLances(int drapeadoLances) {
		this.drapeadoLances = drapeadoLances;
	}

	public BigDecimal getDrapeadoLargura() {
		return drapeadoLargura;
	}

	public void setDrapeadoLargura(BigDecimal drapeadoLargura) {
		this.drapeadoLargura = drapeadoLargura;
	}

	public BigDecimal getDrapeadoAltura1() {
		return drapeadoAltura1;
	}

	public void setDrapeadoAltura1(BigDecimal drapeadoAltura1) {
		this.drapeadoAltura1 = drapeadoAltura1;
	}

	public BigDecimal getDrapeadoAltura2() {
		return drapeadoAltura2;
	}

	public void setDrapeadoAltura2(BigDecimal drapeadoAltura2) {
		this.drapeadoAltura2 = drapeadoAltura2;
	}

	public BigDecimal getQtde() {
		return qtde;
	}

	public void setQtde(BigDecimal qtde) {
		this.qtde = qtde;
	}

	public Boolean getQtdeNaoAlterar() {
		return qtdeNaoAlterar;
	}

	public void setQtdeNaoAlterar(Boolean qtdeNaoAlterar) {
		this.qtdeNaoAlterar = qtdeNaoAlterar;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public BigDecimal getPrecoPrazo() {
		return precoPrazo;
	}

	public void setPrecoPrazo(BigDecimal precoPrazo) {
		this.precoPrazo = precoPrazo;
	}

	public BigDecimal getPrecoVista() {
		return precoVista;
	}

	public void setPrecoVista(BigDecimal precoVista) {
		this.precoVista = precoVista;
	}

	public BigDecimal getPrecoPromo() {
		return precoPromo;
	}

	public void setPrecoPromo(BigDecimal precoPromo) {
		this.precoPromo = precoPromo;
	}

	// -------------------------------------------------------

	@Override
	public int hashCode() {
		final int prime = 337;
		final int result = 181;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).append(cortina).append(artigoCortina).append(qtde).append(camada)
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
		final CortinaItem iObj = (CortinaItem) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(cortina, iObj.cortina)
				.append(artigoCortina, iObj.artigoCortina)
				.append(qtde, iObj.qtde)
				.append(camada, iObj.camada)
				.isEquals();
	}

	public void setPrecoVigente(BigDecimal precoVigente) {
		this.precoVigente = precoVigente;
	}

	public BigDecimal getPrecoVigente() {
		if ((getPrecoPromo() != null) && (getPrecoPromo().doubleValue() > 0.0)) {
			precoVigente = getPrecoPromo();
		} else if ((getPrecoPrazo() != null) && (getPrecoPrazo().doubleValue() > 0.0)) {
			precoVigente = getPrecoPrazo();
		} else {
			precoVigente = BigDecimal.ZERO;
		}
		return precoVigente;
	}

	public BigDecimal getValorTotal() {
		if ((getQtde() != null) && (getPrecoVigente() != null)) {
			valorTotal = getQtde().multiply(getPrecoVigente());
		}
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
