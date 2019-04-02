package com.bonsucesso.erp.cortinas.model;



import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.bonsucesso.erp.orcamentos.model.OrcamentoItem;
import com.ocabit.base.model.EntityIdImpl;
import com.ocabit.jpa.listener.EntityIdListener;
import com.ocabit.jpa.listener.UpperCaseListener;
import com.ocabit.utils.currency.CurrencyUtils;


/**
 * Entidade Cortina.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "crtn_cortina", uniqueConstraints = { @UniqueConstraint(columnNames = { "orcamento_item_id" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class Cortina extends EntityIdImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = -6512260293519132894L;

	@OneToOne(optional = false, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "orcamento_item_id", nullable = false)
	@NotNull(message = "O campo 'Orçamento Item' deve ser informado")
	private OrcamentoItem orcamentoItem;

	@Column(name = "completa", nullable = false)
	@NotNull(message = "Campo 'Completa' precisa ser informado")
	private boolean cortinaCompleta = true;

	@Column(name = "com_instalacao", nullable = false)
	@NotNull(message = "Campo 'Com Instalação' precisa ser informado")
	private boolean comInstalacao = true;

	@Column(name = "altura", nullable = false)
	@NotNull(message = "Campo 'Altura' precisa ser informado")
	private BigDecimal altura;

	@Column(name = "largura", nullable = false)
	@NotNull(message = "Campo 'Largura' precisa ser informado")
	private BigDecimal largura;

	@Column(name = "altura_janela", nullable = true)
	private BigDecimal alturaJanela;

	@Column(name = "largura_janela", nullable = true)
	private BigDecimal larguraJanela;

	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, mappedBy = "cortina", orphanRemoval = true)
	private List<CortinaItem> itens;

	/**
	 * 'V' ou 'T'
	 */
	@Column(name = "varao_trilho", nullable = false, length = 1)
	@NotNull(message = "O campo 'Varão/Trilho' deve ser informado")
	private String varaoOuTrilho;

	@Column(name = "qtde_camadas", nullable = false)
	@NotNull(message = "Campo 'Qtde Camadas' precisa ser informado")
	private Integer qtdeCamadas;

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL }, mappedBy = "cortina", orphanRemoval = true)
	private List<CortinaLado> largurasLados;

	@Transient
	private BigDecimal totalLarguraLados;

	@Transient
	private BigDecimal valorTotal;

	public boolean isCortinaCompleta() {
		return cortinaCompleta;
	}

	public void setCortinaCompleta(boolean cortinaCompleta) {
		this.cortinaCompleta = cortinaCompleta;
	}

	public boolean isComInstalacao() {
		return comInstalacao;
	}

	public void setComInstalacao(boolean comInstalacao) {
		this.comInstalacao = comInstalacao;
	}

	public OrcamentoItem getOrcamentoItem() {
		return orcamentoItem;
	}

	public void setOrcamentoItem(OrcamentoItem orcamentoItem) {
		this.orcamentoItem = orcamentoItem;
	}

	public BigDecimal getAltura() {
		return altura;
	}

	public void setAltura(BigDecimal altura) {
		this.altura = altura;
	}

	public BigDecimal getLargura() {
		return largura;
	}

	public void setLargura(BigDecimal largura) {
		this.largura = largura;
	}

	public BigDecimal getAlturaJanela() {
		return alturaJanela;
	}

	public void setAlturaJanela(BigDecimal alturaJanela) {
		this.alturaJanela = alturaJanela;
	}

	public BigDecimal getLarguraJanela() {
		return larguraJanela;
	}

	public void setLarguraJanela(BigDecimal larguraJanela) {
		this.larguraJanela = larguraJanela;
	}

	//	public List<Double> getLargurasLados() {
	//		return largurasLados;
	//	}
	//
	//	public void setLargurasLados(List<Double> largurasLados) {
	//		if (largurasLados != null && largurasLados.size() > 0) {
	//			Double largura = 0.0;
	//			for (BigDecimal lado : largurasLados) {
	//				largura += lado;
	//			}
	//			setLargura(largura);
	//		}
	//		this.largurasLados = largurasLados;
	//	}

	public void setoLargurasLados(Double[] largurasLados) {}

	public List<CortinaItem> getItens() {
		if (itens == null) {
			setItens(new ArrayList<CortinaItem>());
		}
		Collections.sort(itens, new Comparator<CortinaItem>() {

			@Override
			public int compare(CortinaItem o1, CortinaItem o2) {
				return new CompareToBuilder()
						.append(o1.getCamada(), o2.getCamada())
						.append(o1.getArtigoCortina().getProduto().getDescricao(), o2.getArtigoCortina().getProduto()
								.getDescricao())
						.toComparison();
			}
		});
		return itens;
	}

	public void setItens(List<CortinaItem> itens) {
		this.itens = itens;
	}

	public void addItem(CortinaItem item) {
		if (isCortinaCompleta()) {
			if (getVaraoOuTrilho() == null) {
				throw new IllegalStateException("Informar VaraoOuTrilho");
			}
			if (getVaraoOuTrilho().equals("T")) {
				switch (item.getArtigoCortina().getTipoArtigoCortina()) {
					case ARGOLA:
					case ILHOS:
					case TERMINAL:
					case VARAO:
					default:
						throw new IllegalStateException("Item inválido para cortina de trilho: "
								+ item.getArtigoCortina().getTipoArtigoCortina() + " ("
								+ item.getArtigoCortina().getProduto().getDescricao() + ")");
				}
			} else if (getVaraoOuTrilho().equals("V")) {
				switch (item.getArtigoCortina().getTipoArtigoCortina()) {
					case RODIZIO:
					case TERMINAL:
					case TRILHO:
					default:
						throw new IllegalStateException("Item inválido para cortina de trilho: "
								+ item.getArtigoCortina().getTipoArtigoCortina() + " ("
								+ item.getArtigoCortina().getProduto().getDescricao() + ")");
				}
			}
		}
		item.setCortina(this);
		getItens().add(item);
	}

	public void removeItem(ArtigoCortina item) {
		getItens().remove(getItens().indexOf(item));
	}

	public String getVaraoOuTrilho() {
		return varaoOuTrilho;
	}

	public void setVaraoOuTrilho(String varaoOuTrilho) {
		if ((varaoOuTrilho != null) && !"V".equals(varaoOuTrilho)
				&& !"T".equals(varaoOuTrilho)) {
			throw new InvalidParameterException("V/T");
		}
		this.varaoOuTrilho = varaoOuTrilho;
	}

	public BigDecimal getTotalLarguraLados() {
		BigDecimal totalLarguraLados = BigDecimal.ZERO;
		if (getLargurasLados() != null) {
			for (CortinaLado lado : getLargurasLados()) {
				if (lado.getLarguraLado() != null) {
					totalLarguraLados = totalLarguraLados.add(lado.getLarguraLado());
				}
			}
		}
		return totalLarguraLados;
	}

	public void setTotalLarguraLados(BigDecimal totalLarguraLados) {
		this.totalLarguraLados = totalLarguraLados;
	}

	public BigDecimal getValorTotal() {
		BigDecimal bdValorTotal = BigDecimal.ZERO;
		if (getItens() != null) {
			for (CortinaItem item : getItens()) {
				if (item.getValorTotal() != null) {
					bdValorTotal = bdValorTotal.add(item.getValorTotal());
				}
			}
		}
		return bdValorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Integer getQtdeCamadas() {
		return qtdeCamadas;
	}

	public void setQtdeCamadas(Integer qtdeCamadas) {
		this.qtdeCamadas = qtdeCamadas;
	}

	public List<CortinaLado> getLargurasLados() {
		if (largurasLados == null) {
			largurasLados = new ArrayList<CortinaLado>();
		}
		return largurasLados;
	}

	public void setLargurasLados(List<CortinaLado> largurasLados) {
		this.largurasLados = largurasLados;
	}

	public void calcLargurasLados(Integer qtdeLados) {
		if (CurrencyUtils.multiplica(getLargura().doubleValue(), 100.0) % 2 != 0) {
			setLargura(getLargura().add(new BigDecimal("0.01")));
		}
		largurasLados = null;
		if ((qtdeLados == null) || (qtdeLados <= 0)) {
			qtdeLados = 2;
		}
		BigDecimal bdQtdeLados = CurrencyUtils.getBigDecimalScaled(qtdeLados.doubleValue(), 3);
		BigDecimal lado = getLargura().divide(bdQtdeLados, 3, RoundingMode.HALF_DOWN);
		for (int i = 0; i < qtdeLados; i++) {
			getLargurasLados().add(new CortinaLado(this, lado));
		}
	}

	@Override
	public int hashCode() {
		final int prime = 337;
		final int result = 181;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).append(orcamentoItem).toHashCode();
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
		final Cortina iObj = (Cortina) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(orcamentoItem, iObj.orcamentoItem)
				.isEquals();
	}

	@Override
	public Cortina clone() {
		Cortina cortinaClone = SerializationUtils.clone(this);
		cortinaClone.setId(null);

		OrcamentoItem orcamentoItemClone = getOrcamentoItem().clone();
		orcamentoItemClone.setId(null);

		cortinaClone.setOrcamentoItem(orcamentoItemClone);

		cortinaClone.setItens(null);

		cortinaClone.setLargurasLados(null);

		for (CortinaItem ci : getItens()) {
			CortinaItem ciClone = SerializationUtils.clone(ci);
			ciClone.setId(null);
			ciClone.setCortina(cortinaClone);
			cortinaClone.addItem(ciClone);

		}
		for (CortinaLado cl : getLargurasLados()) {
			CortinaLado clClone = SerializationUtils.clone(cl);
			clClone.setId(null);
			clClone.setCortina(cortinaClone);
			cortinaClone.getLargurasLados().add(clClone);
		}

		return cortinaClone;
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
