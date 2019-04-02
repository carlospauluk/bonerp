package com.bonsucesso.erp.cortinas.model;



import java.math.BigDecimal;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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
@Table(name = "crtn_cortina_lado")
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class CortinaLado extends EntityIdImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = -3758457850723676818L;

	@ManyToOne(optional = false)
	@JoinColumn(name = "cortina_id", nullable = false)
	@NotNull(message = "O campo 'Cortina' deve ser informado")
	private Cortina cortina;

	@Column(name = "largura_lado", nullable = false)
	@NotNull(message = "Campo 'Largura Lado' precisa ser informado")
	private BigDecimal larguraLado;

	/*
	 * Entidade auxiliar.
	 */

	public CortinaLado() {

	}

	/**
	 * @param cortina
	 * @param larguraLado
	 */
	public CortinaLado(Cortina cortina, BigDecimal larguraLado) {
		super();
		this.cortina = cortina;
		this.larguraLado = larguraLado;
	}

	public Cortina getCortina() {
		return cortina;
	}

	public void setCortina(Cortina cortina) {
		this.cortina = cortina;
	}

	public BigDecimal getLarguraLado() {
		return larguraLado;
	}

	public void setLarguraLado(BigDecimal larguraLado) {
		this.larguraLado = larguraLado;
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
