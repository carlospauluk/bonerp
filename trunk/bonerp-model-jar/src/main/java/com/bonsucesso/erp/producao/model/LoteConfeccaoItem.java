package com.bonsucesso.erp.producao.model;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
@Table(name = "prod_lote_confeccao_item")
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class LoteConfeccaoItem extends EntityIdImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = 4110063804538073334L;

	@ManyToOne(optional = false)
	@JoinColumn(name = "lote_confeccao_id", nullable = false)
	@NotNull(message = "O campo 'Lote' deve ser informado")
	private LoteConfeccao loteConfeccao;

	@ManyToOne(optional = false)
	@JoinColumn(name = "confeccao_id", nullable = false)
	@NotNull(message = "O campo 'Confecção' deve ser informado")
	private Confeccao confeccao;

	@Column(name = "ordem", nullable = false)
	@NotNull(message = "Campo 'Ordem' precisa ser informado")
	private Integer ordem;

	@Column(nullable = true, length = 5000, name = "obs")
	@Size(min=0,max=5000,message="Campo 'Obs' deve conter no máximo 5.000 caracteres")
	private String obs;

	@OneToMany(mappedBy = "loteConfeccaoItem", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
	private List<LoteConfeccaoItemQtde> qtdesGrade;

	@Transient
	private Map<GradeTamanho, Integer> mapaGradeQtdes;

	@Transient
	private Integer totalQtdes;

	// -------------------------------------------------------

	public LoteConfeccao getLoteConfeccao() {
		return loteConfeccao;
	}

	public void setLoteConfeccao(LoteConfeccao loteConfeccao) {
		this.loteConfeccao = loteConfeccao;
	}

	public Confeccao getConfeccao() {
		return confeccao;
	}

	public void setConfeccao(Confeccao confeccao) {
		this.confeccao = confeccao;
	}

	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public List<LoteConfeccaoItemQtde> getQtdesGrade() {
		if (qtdesGrade == null) {
			qtdesGrade = new ArrayList<LoteConfeccaoItemQtde>();
		}
		return qtdesGrade;
	}

	public void setQtdesGrade(List<LoteConfeccaoItemQtde> qtdesGrade) {
		this.qtdesGrade = qtdesGrade;
	}

	public void addQtdeGrade(LoteConfeccaoItemQtde loteConfeccaoItemQtde) {
		loteConfeccaoItemQtde.setLoteConfeccaoItem(this);
		getQtdesGrade().add(loteConfeccaoItemQtde);
	}

	public Map<GradeTamanho, Integer> getMapaGradeQtdes() {
		if (mapaGradeQtdes == null) {
			mapaGradeQtdes = new HashMap<GradeTamanho, Integer>();
		}
		return mapaGradeQtdes;
	}

	public void setMapaGradeQtdes(Map<GradeTamanho, Integer> mapaGradeQtdes) {
		this.mapaGradeQtdes = mapaGradeQtdes;
	}

	public void buildMapaGradeTamanho() {
		try {
			getMapaGradeQtdes().clear();
			if (getQtdesGrade() != null) {
				for (LoteConfeccaoItemQtde qtde : getQtdesGrade()) {
					getMapaGradeQtdes().put(qtde.getGradeTamanho(), qtde.getQtde());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void mapaGrade2QtdesGrade() {
		if (getMapaGradeQtdes() != null) {
			getQtdesGrade().clear();
			for (Map.Entry<GradeTamanho, Integer> entry : getMapaGradeQtdes().entrySet()) {
				if ((entry.getValue() != null) && ((((Number) entry.getValue()).doubleValue()) > 0.0)) {
					LoteConfeccaoItemQtde qtde = new LoteConfeccaoItemQtde();
					qtde.setGradeTamanho(entry.getKey());
					qtde.setQtde(((Number) entry.getValue()).intValue());
					qtde.setLoteConfeccaoItem(this);
					getQtdesGrade().add(qtde);
				}
			}
		}
	}

	@Override
	public int hashCode() {
		final int prime = 283;
		final int result = 599;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result)
				.append(loteConfeccao)
				.append(confeccao)
				.append(ordem)
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
		final LoteConfeccaoItem iObj = (LoteConfeccaoItem) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(loteConfeccao, iObj.loteConfeccao)
				.append(confeccao, iObj.confeccao)
				.append(ordem, iObj.ordem)
				.isEquals();
	}

	public Integer getTotalQtdes() {
		if (getQtdesGrade() != null) {
			totalQtdes = 0;
			for (LoteConfeccaoItemQtde qtde : getQtdesGrade()) {
				totalQtdes += qtde.getQtde();
			}
		}
		return totalQtdes;
	}

	public void setTotalQtdes(Integer totalItens) {
		totalQtdes = totalItens;
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
