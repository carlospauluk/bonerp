package com.bonsucesso.erp.financeiro.model;



import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
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
 * Classe para os itens de MesVenda.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "fin_result_tsno_item",
		uniqueConstraints = { @UniqueConstraint(columnNames = { "result_tsno_id", "mesano" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class ResultTSNOItem extends EntityIdImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3334479696316891995L;

	@ManyToOne(optional = false)
	@JoinColumn(name = "result_tsno_id", nullable = false)
	@NotNull
	private ResultTSNO resultTSNO;

	@Column(nullable = false, name = "mesano")
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull(message = "'Mês/Ano' deve ser informado")
	private Date mesAno;

	@Column(name = "total_vendido", nullable = false)
	@NotNull(message = "Campo 'Total Vendido' precisa ser informado")
	private BigDecimal total;

	@Column(name = "descricao", length = 2000, nullable = true)
	private String obs;

	public ResultTSNO getResultTSNO() {
		return resultTSNO;
	}

	public void setResultTSNO(ResultTSNO resultTSNO) {
		this.resultTSNO = resultTSNO;
	}

	public Date getMesAno() {
		return mesAno;
	}

	public void setMesAno(Date mesAno) {
		this.mesAno = mesAno;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	@Override
	public int hashCode() {
		final int prime = 2;
		final int result = 773;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).append(resultTSNO).append(mesAno).toHashCode();
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
		final ResultTSNOItem iObj = (ResultTSNOItem) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(resultTSNO, iObj.resultTSNO)
				.append(mesAno, iObj.mesAno)
				.isEquals();
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
