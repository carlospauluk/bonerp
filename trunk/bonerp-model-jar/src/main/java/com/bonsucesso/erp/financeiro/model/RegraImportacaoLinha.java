package com.bonsucesso.erp.financeiro.model;



import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotEmpty;

import com.ocabit.base.model.EntityIdImpl;
import com.ocabit.jpa.listener.EntityIdListener;
import com.ocabit.jpa.listener.NotUpperCase;
import com.ocabit.jpa.listener.UpperCaseListener;


/**
 * Entidade Regra de Importação de Linha.
 * Configura uma regra para setar corretamente a Movimentação ao importar uma linha de extrato.
 *
 * @author Carlos Eduardo Pauluk
 */
@Entity
@Table(name = "fin_regra_import_linha")
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class RegraImportacaoLinha extends EntityIdImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = -7042934518247360874L;

	/**
	 * Em casos especiais (como na utilização de named groups) posso usar uma regex em java.
	 */
	@NotUpperCase
	@Column(name = "regra_regex_java", nullable = true, length = 500)
	@Size(min = 0, max = 500, message = "Campo 'Regra Regex Java' precisa ser informado (entre 0 e 500 caracteres)")
	private String regraRegexJava;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_lancto", nullable = false, length = 50)
	@NotNull(message = "Campo 'Tipo Lancto' precisa ser informado")
	private TipoLancto tipoLancto;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 50)
	@NotNull(message = "Campo 'Status' precisa ser informado")
	private Status status;

	@ManyToOne(optional = true)
	@JoinColumn(name = "carteira_id", nullable = true)
	private Carteira carteira;

	@ManyToOne(optional = true)
	@JoinColumn(name = "carteira_destino_id", nullable = true)
	private Carteira carteiraDestino;

	@ManyToOne(optional = false)
	@JoinColumn(name = "centrocusto_id", nullable = false)
	@NotNull(message = "Campo 'Centro de Custo' precisa ser informado")
	@Valid
	private CentroCusto centroCusto;

	@ManyToOne(optional = false)
	@JoinColumn(name = "modo_id", nullable = false)
	@NotNull(message = "Campo 'Modo' precisa ser informado")
	@Valid
	private Modo modo;

	@NotUpperCase
	@Column(name = "padrao_descricao", nullable = false, length = 500)
	@NotEmpty(message = "Campo 'Padrão da Descrição' precisa ser informado (entre 1 e 500 caracteres)")
	@Size(min = 1, max = 500, message = "Campo 'Padrão da Descrição' precisa ser informado (entre 1 e 500 caracteres)")
	private String padraoDescricao;

	@ManyToOne(optional = false)
	@JoinColumn(name = "categoria_id", nullable = false)
	@NotNull(message = "Campo 'Categoria' precisa ser informado")
	@Valid
	private Categoria categoria;

	/**
	 * Para poder aplicar a regra somente se for positivo (1), negativo (-1) ou ambos (0)
	 */
	@Column(name = "sinal_valor", nullable = true)
	@NotNull(message = "Campo 'Sinal Valor' deve ser -1, 0 ou 1")
	@Min(value = -1l, message = "Campo 'Sinal Valor' deve ser -1, 0 ou 1")
	@Max(value = 1l, message = "Campo 'Sinal Valor' deve ser -1, 0 ou 1")
	private Integer sinalValor = 0;

	@Transient
	private String sinalValorLabel = "Ambos";

	/**
	 * Caso tenha sido paga/recebida via cheque, informa os dados.
	 */
	@Embedded
	private Cheque cheque = new Cheque();

	/**
	 * Controlado pelo JPA.
	 */

	public TipoLancto getTipoLancto() {
		return tipoLancto;
	}

	public void setTipoLancto(TipoLancto tipoLancto) {
		this.tipoLancto = tipoLancto;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Carteira getCarteiraDestino() {
		return carteiraDestino;
	}

	public void setCarteiraDestino(Carteira carteiraDestino) {
		this.carteiraDestino = carteiraDestino;
	}

	public Carteira getCarteira() {
		return carteira;
	}

	public void setCarteira(Carteira carteira) {
		this.carteira = carteira;
	}

	public CentroCusto getCentroCusto() {
		return centroCusto;
	}

	public void setCentroCusto(CentroCusto centroCusto) {
		this.centroCusto = centroCusto;
	}

	public Modo getModo() {
		return modo;
	}

	public void setModo(Modo modo) {
		this.modo = modo;
	}

	public String getRegraRegexJava() {
		return regraRegexJava;
	}

	public void setRegraRegexJava(String regraRegexJava) {
		this.regraRegexJava = regraRegexJava;
	}

	public String getPadraoDescricao() {
		return padraoDescricao;
	}

	public void setPadraoDescricao(String padraoDescricao) {
		this.padraoDescricao = padraoDescricao;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Integer getSinalValor() {
		return sinalValor;
	}

	public void setSinalValor(Integer sinalValor) {
		this.sinalValor = sinalValor;
	}

	public Cheque getCheque() {
		return cheque;
	}

	public void setCheque(Cheque cheque) {
		this.cheque = cheque;
	}

	public String getSinalValorLabel() {
		switch (sinalValor) {
			case 0: sinalValorLabel = "Ambos"; break;
			case 1: sinalValorLabel = "Positivo"; break;
			case -1: sinalValorLabel = "Negativo"; break;
		}
		return sinalValorLabel;
	}

	public void setSinalValorLabel(String sinalValorLabel) {
		this.sinalValorLabel = sinalValorLabel;
	}

	@Override
	public int hashCode() {
		// Usar somente números primos.
		final int prime = 293;
		final int result = 157;
		// usa API do apache-commons
		return new HashCodeBuilder(prime, result).append(regraRegexJava).toHashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		// verificação padrão
		if (obj == null) {
			return false;
		}
		// verificação padrão
		if (obj == this) {
			return true;
		}
		// verificação padrão
		if (obj.getClass() != getClass()) {
			return false;
		}
		final RegraImportacaoLinha iObj = (RegraImportacaoLinha) obj;
		// usa API do apache-commons
		return new EqualsBuilder().append(regraRegexJava, iObj.regraRegexJava)
				.isEquals();
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
