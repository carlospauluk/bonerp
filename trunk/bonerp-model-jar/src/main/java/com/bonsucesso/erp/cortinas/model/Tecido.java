package com.bonsucesso.erp.cortinas.model;



import java.math.BigDecimal;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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
 * Entidade Tecido.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "crtn_tecido")
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class Tecido extends EntityIdImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = -9103073492777256683L;

	@OneToOne(mappedBy = "tecido")
	private ArtigoCortina artigoCortina;

	@Column(name = "largura", nullable = false)
	@NotNull(message = "Campo 'Largura' precisa ser informado")
	private BigDecimal largura;

	@Enumerated(EnumType.STRING)
	@Column(name = "orientacao_padrao", nullable = true, length = 30)
	private OrientacaoTecido orientacaoPadrao = OrientacaoTecido.HORIZONTAL;

	@Column(name = "fator_padrao", nullable = true)
	private Integer fatorPadrao;

	@Column(name = "altura_barra_padrao", nullable = false)
	@NotNull(message = "Campo 'Altura Barra Padrão' precisa ser informado")
	private BigDecimal alturaBarraPadrao;

	// Para utilizar o tecido na horizontal (quando INDEPENDE_CEDE), qual a altura máxima possível
	@Column(name = "altura_max_horizontal", nullable = false)
	@NotNull(message = "Campo 'Altura Máx. Horizontal' precisa ser informado")
	private BigDecimal alturaMaxHorizontal;

	public BigDecimal getLargura() {
		return largura;
	}

	public ArtigoCortina getArtigoCortina() {
		return artigoCortina;
	}

	public void setArtigoCortina(ArtigoCortina artigoCortina) {
		this.artigoCortina = artigoCortina;
	}

	public void setLargura(BigDecimal largura) {
		this.largura = largura;
	}

	public OrientacaoTecido getOrientacaoPadrao() {
		return orientacaoPadrao;
	}

	public void setOrientacaoPadrao(OrientacaoTecido orientacao) {
		orientacaoPadrao = orientacao;
	}

	public Integer getFatorPadrao() {
		return fatorPadrao;
	}

	public void setFatorPadrao(Integer fatorPadrao) {
		this.fatorPadrao = fatorPadrao;
	}

	public BigDecimal getAlturaMaxHorizontal() {
		return alturaMaxHorizontal;
	}

	public void setAlturaMaxHorizontal(BigDecimal alturaMaxHorizontal) {
		this.alturaMaxHorizontal = alturaMaxHorizontal;
	}

	public BigDecimal getAlturaBarraPadrao() {
		return alturaBarraPadrao;
	}

	public void setAlturaBarraPadrao(BigDecimal alturaBarraPadrao) {
		this.alturaBarraPadrao = alturaBarraPadrao;
	}

	@Override
	public int hashCode() {
		final int prime = 563;
		final int result = 467;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).append(artigoCortina).toHashCode();
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
		final Tecido iObj = (Tecido) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(artigoCortina, iObj.artigoCortina)
				.isEquals();
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
