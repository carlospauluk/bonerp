package com.bonsucesso.erp.producao.model;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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
@Table(name = "prod_confeccao_item",
		uniqueConstraints = { @UniqueConstraint(columnNames = { "confeccao_id", "insumo_id" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class ConfeccaoItem extends EntityIdImpl implements Comparable<ConfeccaoItem> {

	/**
	 *
	 */
	private static final long serialVersionUID = 8397730427470263907L;

	@ManyToOne(optional = false)
	@JoinColumn(name = "confeccao_id", nullable = false)
	@NotNull(message = "O campo 'Confecção' deve ser informado")
	private Confeccao confeccao;

	@ManyToOne(optional = false)
	@JoinColumn(name = "insumo_id", nullable = false)
	@NotNull(message = "O campo 'Insumo' deve ser informado")
	private Insumo insumo;

	@OneToMany(mappedBy = "confeccaoItem", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
	private List<ConfeccaoItemQtde> qtdesGrade;

	@Transient
	private Map<GradeTamanho, BigDecimal> mapaGradeQtdes;

	@Transient
	private Map<GradeTamanho, BigDecimal> mapaGradeValores;

	// -------------------------------------------------------

	/**
	 *
	 */
	public ConfeccaoItem() {
		super();
	}

	public Confeccao getConfeccao() {
		return confeccao;
	}

	public void setConfeccao(Confeccao confeccao) {
		this.confeccao = confeccao;
	}

	public Insumo getInsumo() {
		return insumo;
	}

	public void setInsumo(Insumo insumo) {
		this.insumo = insumo;
	}

	public List<ConfeccaoItemQtde> getQtdesGrade() {
		if (qtdesGrade == null) {
			qtdesGrade = new ArrayList<ConfeccaoItemQtde>();
		}
		return qtdesGrade;
	}

	public void setQtdesGrade(List<ConfeccaoItemQtde> qtdesGrade) {
		this.qtdesGrade = qtdesGrade;
	}

	public void addQtdeGrade(ConfeccaoItemQtde ciQtde) {
		ciQtde.setConfeccaoItem(this);
		getQtdesGrade().add(ciQtde);
	}

	public ConfeccaoItemQtde getQtdeGradeBy(GradeTamanho gt) {
		if ((getQtdesGrade() != null) && (getQtdesGrade().size() > 0)) {
			for (ConfeccaoItemQtde ciq : getQtdesGrade()) {
				if (ciq.getGradeTamanho().equals(gt)) {
					return ciq;
				}
			}
		}
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 173;
		final int result = 179;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).append(confeccao).append(insumo).toHashCode();
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
		final ConfeccaoItem iObj = (ConfeccaoItem) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(confeccao, iObj.confeccao)
				.append(insumo, iObj.insumo)
				.isEquals();
	}

	@Override
	public int compareTo(ConfeccaoItem o) {
		return new CompareToBuilder()
				.append(confeccao, o.confeccao)
				.append(insumo, o.insumo)
				.toComparison();
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
