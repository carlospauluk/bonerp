package com.bonsucesso.erp.estoque.model;



import java.math.BigDecimal;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.ocabit.base.model.EntityIdImpl;
import com.ocabit.jpa.listener.EntityIdListener;
import com.ocabit.jpa.listener.UpperCaseListener;


/**
 * Entidade que forma a tabela de depreciação de preços.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "est_depreciacao_preco", uniqueConstraints = { @UniqueConstraint(columnNames = { "prazo_ini" }),
		@UniqueConstraint(columnNames = { "prazo_fim" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@DynamicUpdate(true)
@DynamicInsert(true)
public class DepreciacaoPreco extends EntityIdImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = -1170681615777682162L;

	@Column(name = "prazo_ini", nullable = false)
	@NotNull(message = "Campo 'Prazo Inicial' precisa ser informado")
	@Min(value = 0)
	private Integer prazoIni;

	@Column(name = "prazo_fim", nullable = false)
	@NotNull(message = "Campo 'Prazo Inicial' precisa ser informado")
	@Min(value = 1)
	private Integer prazoFim;

	@Column(name = "porcentagem", nullable = false)
	@NotNull(message = "Campo 'Porcentagem' precisa ser informado")
	private BigDecimal porcentagem;

	public Integer getPrazoIni() {
		return prazoIni;
	}

	public void setPrazoIni(Integer prazoIni) {
		this.prazoIni = prazoIni;
	}

	public Integer getPrazoFim() {
		return prazoFim;
	}

	public void setPrazoFim(Integer prazoFim) {
		this.prazoFim = prazoFim;
	}

	public BigDecimal getPorcentagem() {
		return porcentagem;
	}

	public void setPorcentagem(BigDecimal porcentagem) {
		this.porcentagem = porcentagem;
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
