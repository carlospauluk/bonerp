package com.bonsucesso.erp.financeiro.model;



import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.ocabit.base.model.EntityIdImpl;
import com.ocabit.jpa.listener.EntityIdListener;
import com.ocabit.jpa.listener.UpperCaseListener;


@Entity
@Table(name = "fin_cadeia")
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class Cadeia extends EntityIdImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = 8592220700424581688L;

	@OneToMany(mappedBy = "cadeia", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private List<Movimentacao> movimentacoes = new ArrayList<Movimentacao>();

	/**
	 * Se for vinculante, ao deletar uma movimentação da cadeia todas são deletadas, ver trigger trg_ad_delete_cadeia.
	 */
	@Column(name = "vinculante", nullable = false)
	@NotNull(message = "Campo 'Vinculante' precisa ser informado")
	private Boolean vinculante = false;

	/**
	 * Se for fechada, não é possível incluir outras movimentações na cadeia.
	 */
	@Column(name = "fechada", nullable = false)
	@NotNull(message = "Campo 'Fechada' precisa ser informado")
	private Boolean fechada = false;

	/**
	 * Controlado pelo JPA.
	 */

	public List<Movimentacao> getMovimentacoes() {
		if (movimentacoes == null) {
			movimentacoes = new ArrayList<Movimentacao>();
		}
		return movimentacoes;
	}

	public void setMovimentacoes(List<Movimentacao> movimentacoes) {
		this.movimentacoes = movimentacoes;
	}

	public void addMovimentacao(Movimentacao movimentacao) {
		movimentacao.setCadeia(this);
		getMovimentacoes().add(movimentacao);
	}

	public Boolean getVinculante() {
		return vinculante;
	}

	public void setVinculante(Boolean vinculante) {
		this.vinculante = vinculante;
	}

	public Boolean getFechada() {
		return fechada;
	}

	public void setFechada(Boolean fechada) {
		this.fechada = fechada;
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
