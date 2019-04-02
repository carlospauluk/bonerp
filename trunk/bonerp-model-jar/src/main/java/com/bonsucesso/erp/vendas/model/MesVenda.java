package com.bonsucesso.erp.vendas.model;



import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
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
import com.ocabit.utils.calendar.CalendarUtil;


/**
 * Entidade sobre o Mês de Vendas.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Entity
@Table(name = "ven_mes_venda", uniqueConstraints = { @UniqueConstraint(columnNames = { "mes_ano" }) })
@EntityListeners({ UpperCaseListener.class, EntityIdListener.class })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DynamicUpdate(true)
@DynamicInsert(true)
public class MesVenda extends EntityIdImpl {

	/**
	 *
	 */
	private static final long serialVersionUID = -9069258042835757900L;

	@Column(nullable = false, name = "mes_ano")
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull(message = "'Mês/Ano' deve ser informado")
	private Date mesAno;

	@Column(name = "inflacao", nullable = true)
	private BigDecimal inflacao = BigDecimal.ZERO;

	@Column(name = "qtde_vendedores", nullable = true)
	private Integer qtdeVendedores = 0;

	@Column(name = "qtde_vendas", nullable = true)
	private Integer qtdeVendas = 0;

	/**
	 * Total vendido no geral (inclui vendas externas, etc).
	 */
	@Column(name = "total_vendido_global", nullable = true)
	private BigDecimal totalVendidoGlobal = BigDecimal.ZERO;

	/**
	 * Total vendido apenas por vendedores.
	 */
	@Column(name = "total_vendido_vendedores", nullable = true)
	private BigDecimal totalVendidoVendedores = BigDecimal.ZERO;

	/**
	 * Total vendido apenas por vendedores.
	 */
	@Column(name = "total_vendido_vendedores_ate_ontem", nullable = true)
	private BigDecimal totalVendidoVendedoresAteOntem = BigDecimal.ZERO;

	/**
	 * totalVendidoGlobal - totalVendidoVendedores
	 */
	@Column(name = "total_vendido_externos", nullable = true)
	private BigDecimal totalVendidoExternos = BigDecimal.ZERO;

	@Transient
	private BigDecimal totalProvavelGlobal;

	// Total provável considerando a média bruta e o restante de dias úteis
	@Transient
	private BigDecimal totalProvavelVendedores;

	// Total provável considerando o total vendido do histórico ponderando somente a mesma qtde de dias úteis
	@Transient
	private BigDecimal totalProvavelVendedoresPonderado;

	@Transient
	private BigDecimal totalProvavelExternos;

	/**
	 * Total vendido no mesmo mês do ano anterior.
	 */
	@Column(name = "total_vendido_historico", nullable = true)
	private BigDecimal totalHistorico = BigDecimal.ZERO;

	/**
	 * Total vendido no mesmo mês do ano anterior porém somente com código vendedor < 90.
	 */
	@Column(name = "total_vendido_historico_vendedores", nullable = true)
	private BigDecimal totalHistoricoVendedores = BigDecimal.ZERO;

	/**
	 * Total vendido no mesmo mês do ano anterior porém somente com código vendedor < 90 (e somente considerando a qtde de dias úteis até
	 * ontem, para poder fazer o totalProvavel mais certo).
	 */
	@Column(name = "total_vendido_historico_vendedores_ate_ontem", nullable = true)
	private BigDecimal totalHistoricoVendedoresAteOntem = BigDecimal.ZERO;

	/**
	 * Total vendido no mesmo mês do ano anterior porém somente com código vendedor >= 90.
	 */
	@Column(name = "total_vendido_historico_externos", nullable = true)
	private BigDecimal totalHistoricoExternos = BigDecimal.ZERO;

	/**
	 * A variação somente considerando totalVendidoGlobal / totalHistorico
	 */
	@Transient
	private BigDecimal variacaoBruta;

	/**
	 * A variação considerando totalVendidoGlobal / totalHistorico * inflacao
	 */
	@Transient
	private BigDecimal variacaoInflacao;

	/**
	 * A variação considerando totalVendidoGlobal / totalHistorico * inflacao * 1.03 (3%)
	 */
	@Transient
	private BigDecimal variacaoMeta;

	@Transient
	private BigDecimal variacaoBrutaVendedores;

	@Transient
	private BigDecimal variacaoInflacaoVendedores;

	@Transient
	private BigDecimal variacaoMetaVendedores;

	@Transient
	private BigDecimal variacaoBrutaExternos;

	@Transient
	private BigDecimal variacaoInflacaoExternos;

	@Transient
	private BigDecimal variacaoMetaExternos;

	/**
	 * A meta mínima é estipulada a partir do piso - salário mínimo. É o valor necessário para cobrir este gap com a comissão.
	 * Estipulado como 19900.
	 * 
	 */
	@Column(name = "meta_minima", nullable = true)
	private BigDecimal metaMinima = BigDecimal.ZERO;

	/**
	 * A meta esperada é o histórico + inflação + 3%. Mas pode ser alterado manualmente.
	 * Se for menor que a meta mínima, considera-se como sendo o mesmo que a meta mínima.
	 */
	@Column(name = "meta_esperada", nullable = true)
	private BigDecimal metaEsperada = BigDecimal.ZERO;

	/**
	 * A meta mínima é estipulada a partir do piso - salário mínimo. É o valor necessário para cobrir este gap com a comissão.
	 * Estipulado como 19900 * qtdeVendedores.
	 */
	@Column(name = "meta_minima_vendedores", nullable = true)
	private BigDecimal metaMinimaVendedores = BigDecimal.ZERO;

	/**
	 * totalHistoricoExternos + inflacao.
	 */
	@Column(name = "meta_minima_externa", nullable = true)
	private BigDecimal metaMinimaExternos = BigDecimal.ZERO;

	/**
	 * A meta esperada é o histórico + inflação + 3%. Mas pode ser alterado manualmente.
	 * Se for menor que a meta mínima, considera-se como sendo o mesmo que a meta mínima.
	 */
	@Column(name = "meta_esperada_vendedores", nullable = true)
	private BigDecimal metaEsperadaVendedores = BigDecimal.ZERO;

	/**
	 * A meta esperada é o histórico + inflação + 3%. Mas pode ser alterado manualmente.
	 * Se for menor que a meta mínima, considera-se como sendo o mesmo que a meta mínima.
	 */
	@Column(name = "meta_esperada_externos", nullable = true)
	private BigDecimal metaEsperadaExternos = BigDecimal.ZERO;

	@Transient
	private BigDecimal metaMinimaVendedor;

	@Transient
	private BigDecimal metaEsperadaVendedor;

	/**
	 * Qtde de dias úteis em que foi trabalhado.
	 */
	@Column(name = "qtde_dias_uteis_comerciais", nullable = true)
	private Integer qtdeDiasUteisComerciais = 0;

	/**
	 * Deve ser sempre atualizado para o mês corrente
	 */
	@Column(name = "qtde_dias_uteis_ate_hoje", nullable = true)
	private Integer qtdeDiasUteisAteHoje;

	/**
	 * Deve ser sempre atualizado para o mês corrente
	 */
	@Column(name = "qtde_dias_uteis_restantes", nullable = true)
	private Integer qtdeDiasUteisRestantes;

	/**
	 * Calculado: totalVendido / qtdeDiasComVendas.
	 */
	@Column(name = "media_diaria", nullable = true)
	private BigDecimal mediaDiaria;

	@Transient
	private BigDecimal mediaDiariaVendedores;

	@Transient
	private BigDecimal mediaDiariaExternos;

	@Column(name = "valor_venda_medio", nullable = true)
	private BigDecimal valorVendaMedio;

	@Column(name = "media_diaria_atingir_historico", nullable = true)
	private BigDecimal mediaDiariaAtingirHistorico;

	@Column(name = "media_diaria_atingir_meta_minima", nullable = true)
	private BigDecimal mediaDiariaAtingirMetaMinima;

	@Column(name = "media_diaria_atingir_meta_esperada", nullable = true)
	private BigDecimal mediaDiariaAtingirMetaEsperada;

	@Column(name = "media_diaria_atingir_historico_vendedores", nullable = true)
	private BigDecimal mediaDiariaAtingirHistoricoVendedores;

	@Column(name = "media_diaria_atingir_meta_minima_vendedores", nullable = true)
	private BigDecimal mediaDiariaAtingirMetaMinimaVendedores;

	@Column(name = "media_diaria_atingir_meta_esperada_vendedores", nullable = true)
	private BigDecimal mediaDiariaAtingirMetaEsperadaVendedores;

	@Column(name = "media_diaria_atingir_historico_externos", nullable = true)
	private BigDecimal mediaDiariaAtingirHistoricoExternos;

	@Column(name = "media_diaria_atingir_meta_minima_externos", nullable = true)
	private BigDecimal mediaDiariaAtingirMetaMinimaExternos;

	@Column(name = "media_diaria_atingir_meta_esperada_externos", nullable = true)
	private BigDecimal mediaDiariaAtingirMetaEsperadaExternos;

	@Transient
	private BigDecimal mediaDiariaAtingirHistoricoRestante;

	@Transient
	private BigDecimal mediaDiariaAtingirMetaMinimaRestante;

	@Transient
	private BigDecimal mediaDiariaAtingirMetaEsperadaRestante;

	@Transient
	private BigDecimal mediaDiariaAtingirHistoricoRestanteVendedores;

	@Transient
	private BigDecimal mediaDiariaAtingirMetaMinimaRestanteVendedores;

	@Transient
	private BigDecimal mediaDiariaAtingirMetaEsperadaRestanteVendedores;

	@Transient
	private BigDecimal mediaDiariaAtingirHistoricoRestanteExternos;

	@Transient
	private BigDecimal mediaDiariaAtingirMetaMinimaRestanteExternos;

	@Transient
	private BigDecimal mediaDiariaAtingirMetaEsperadaRestanteExternos;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "mesVenda", orphanRemoval = true)
	private List<MesVendaItem> itens;

	@Column(name = "eh_mes_atual", nullable = true)
	private Boolean ehMesAtual;

	// -------------------------------------------------------

	public Date getMesAno() {
		return mesAno;
	}

	public void setMesAno(Date mesAno) {
		this.mesAno = mesAno;
	}

	public BigDecimal getInflacao() {
		if (inflacao == null) {
			inflacao = BigDecimal.ZERO;
		}
		return inflacao;
	}

	public void setInflacao(BigDecimal inflacao) {
		this.inflacao = inflacao;
	}

	public Integer getQtdeVendedores() {
		return qtdeVendedores;
	}

	public void setQtdeVendedores(Integer qtdeVendedores) {
		this.qtdeVendedores = qtdeVendedores;
	}

	public Integer getQtdeVendas() {
		return qtdeVendas;
	}

	public void setQtdeVendas(Integer qtdeVendas) {
		this.qtdeVendas = qtdeVendas;
	}

	public BigDecimal getTotalVendidoVendedores() {
		return totalVendidoVendedores;
	}

	public void setTotalVendidoVendedores(BigDecimal totalVendidoVendedores) {
		this.totalVendidoVendedores = totalVendidoVendedores;
	}

	public BigDecimal getTotalVendidoVendedoresAteOntem() {
		return totalVendidoVendedoresAteOntem != null ? totalVendidoVendedoresAteOntem : BigDecimal.ZERO;
	}

	public void setTotalVendidoVendedoresAteOntem(BigDecimal totalVendidoVendedoresAteOntem) {
		this.totalVendidoVendedoresAteOntem = totalVendidoVendedoresAteOntem;
	}

	public BigDecimal getTotalVendidoGlobal() {
		return totalVendidoGlobal;
	}

	public void setTotalVendidoGlobal(BigDecimal totalVendidoGlobal) {
		this.totalVendidoGlobal = totalVendidoGlobal;
	}

	public BigDecimal getTotalVendidoExternos() {
		return totalVendidoExternos;
	}

	public void setTotalVendidoExternos(BigDecimal totalVendidoExternos) {
		this.totalVendidoExternos = totalVendidoExternos;
	}

	public BigDecimal getTotalProvavelGlobal() {
		if (totalProvavelGlobal == null) {
			if (getTotalVendidoGlobal() != null && getQtdeDiasUteisRestantes() != null
					&& getMediaDiaria() != null) {
				totalProvavelGlobal = getTotalVendidoGlobal()
						.add(getMediaDiaria().multiply(new BigDecimal(getQtdeDiasUteisRestantes())));
			}
		}
		return totalProvavelGlobal;
	}

	public void setTotalProvavelGlobal(BigDecimal totalProvavelGlobal) {
		this.totalProvavelGlobal = totalProvavelGlobal;
	}

	public BigDecimal getTotalProvavelVendedores() {
		if (totalProvavelVendedores == null) {
			if (getTotalVendidoVendedores() != null && getQtdeDiasUteisRestantes() != null
					&& getMediaDiariaVendedores() != null) {
				totalProvavelVendedores = getTotalVendidoVendedores()
						.add(getMediaDiariaVendedores().multiply(new BigDecimal(getQtdeDiasUteisRestantes())));
			}
		}
		return totalProvavelVendedores;
	}

	public void setTotalProvavelVendedores(BigDecimal totalProvavelVendedores) {
		this.totalProvavelVendedores = totalProvavelVendedores;
	}

	public BigDecimal getTotalProvavelVendedoresPonderado() {
		if (totalProvavelVendedoresPonderado == null) {
			if (getTotalHistoricoVendedoresAteOntem() != null
					&& getTotalHistoricoVendedoresAteOntem().doubleValue() > 0.0
					&& getTotalVendidoVendedores() != null) {
				
				BigDecimal fatorDiferenca = getTotalVendidoVendedoresAteOntem()
						.divide(getTotalHistoricoVendedoresAteOntem(), 5, RoundingMode.HALF_DOWN);
				// fatorDiferenca = fatorDiferenca.add(BigDecimal.ONE);

				BigDecimal historicoQtoAindaVenderia = getTotalHistoricoVendedores()
						.subtract(getTotalHistoricoVendedoresAteOntem());

				BigDecimal totalProvavelRestante = historicoQtoAindaVenderia.multiply(fatorDiferenca);

				totalProvavelVendedoresPonderado = getTotalVendidoVendedoresAteOntem().add(totalProvavelRestante);
			}
		}
		return totalProvavelVendedoresPonderado;
	}

	public void setTotalProvavelVendedoresPonderado(BigDecimal totalProvavelVendedoresPonderado) {
		this.totalProvavelVendedoresPonderado = totalProvavelVendedoresPonderado;
	}

	public BigDecimal getTotalProvavelExternos() {
		if (totalProvavelExternos == null) {
			if (getTotalVendidoExternos() != null && getQtdeDiasUteisRestantes() != null
					&& getMediaDiariaExternos() != null) {
				totalProvavelExternos = getTotalVendidoExternos()
						.add(getMediaDiariaExternos().multiply(new BigDecimal(getQtdeDiasUteisRestantes())));
			}
		}
		return totalProvavelExternos;
	}

	public void setTotalProvavelExternos(BigDecimal totalProvavelExternos) {
		this.totalProvavelExternos = totalProvavelExternos;
	}

	public BigDecimal getTotalHistorico() {
		return totalHistorico;
	}

	public void setTotalHistorico(BigDecimal totalHistorico) {
		this.totalHistorico = totalHistorico;
	}

	public BigDecimal getTotalHistoricoVendedores() {
		return totalHistoricoVendedores;
	}

	public void setTotalHistoricoVendedores(BigDecimal totalHistoricoVendedores) {
		this.totalHistoricoVendedores = totalHistoricoVendedores;
	}

	public BigDecimal getTotalHistoricoVendedoresAteOntem() {
		return totalHistoricoVendedoresAteOntem != null ? totalHistoricoVendedoresAteOntem : BigDecimal.ZERO;
	}

	public void setTotalHistoricoVendedoresAteOntem(BigDecimal totalHistoricoVendedoresAteOntem) {
		this.totalHistoricoVendedoresAteOntem = totalHistoricoVendedoresAteOntem;
	}

	public BigDecimal getTotalHistoricoExternos() {
		return totalHistoricoExternos;
	}

	public void setTotalHistoricoExternos(BigDecimal totalHistoricoExternos) {
		this.totalHistoricoExternos = totalHistoricoExternos;
	}

	public BigDecimal getVariacaoBruta() {
		if (variacaoBruta == null) {

			BigDecimal total = Boolean.TRUE.equals(getEhMesAtual()) ? getTotalProvavelGlobal()
					: getTotalVendidoGlobal();

			if ((total != null) && (getTotalHistorico() != null)
					&& getTotalHistorico().doubleValue() != 0.0) {
				variacaoBruta = total
						.divide(getTotalHistorico(), 20, RoundingMode.HALF_DOWN)
						.subtract(BigDecimal.ONE).multiply(new BigDecimal("100.00"));
			}

		}
		return variacaoBruta;
	}

	public void setVariacaoBruta(BigDecimal variacaoBruta) {
		this.variacaoBruta = variacaoBruta;
	}

	public BigDecimal getVariacaoInflacao() {
		if (variacaoInflacao == null) {
			BigDecimal bdInflacao = BigDecimal.ONE.add(getInflacao().setScale(5, RoundingMode.HALF_DOWN)
					.divide(new BigDecimal(100.0000), RoundingMode.HALF_DOWN));

			BigDecimal total = Boolean.TRUE.equals(getEhMesAtual()) ? getTotalProvavelGlobal()
					: getTotalVendidoGlobal();

			if ((total != null) && (getTotalHistorico() != null)
					&& getTotalHistorico().doubleValue() != 0.0) {
				variacaoInflacao = getTotalProvavelGlobal()
						.divide(getTotalHistorico().multiply(bdInflacao), 20, RoundingMode.HALF_DOWN)
						.subtract(BigDecimal.ONE).multiply(new BigDecimal("100.00"));
			}

		}
		return variacaoInflacao;
	}

	public void setVariacaoInflacao(BigDecimal variacaoInflacao) {
		this.variacaoInflacao = variacaoInflacao;
	}

	public BigDecimal getVariacaoMeta() {
		if (variacaoMeta == null) {

			BigDecimal total = Boolean.TRUE.equals(getEhMesAtual()) ? getTotalProvavelGlobal()
					: getTotalVendidoGlobal();

			if (total != null && getMetaEsperada() != null &&
					getMetaEsperada().doubleValue() > 0.0) {
				variacaoMeta = total
						.divide(getMetaEsperada(), 20, RoundingMode.HALF_DOWN)
						.subtract(BigDecimal.ONE).multiply(new BigDecimal("100.00"));
			}
		}
		return variacaoMeta;
	}

	public void setVariacaoMeta(BigDecimal variacaoMeta) {
		this.variacaoMeta = variacaoMeta;
	}

	public BigDecimal getVariacaoBrutaVendedores() {
		if (variacaoBrutaVendedores == null) {

			BigDecimal total = Boolean.TRUE.equals(getEhMesAtual()) ? getTotalProvavelVendedoresPonderado()
					: getTotalVendidoVendedores();

			if (total != null && getTotalHistoricoVendedores() != null &&
					getTotalHistoricoVendedores().doubleValue() > 0.0) {
				variacaoBrutaVendedores = total
						.divide(getTotalHistoricoVendedores(), 20, RoundingMode.HALF_DOWN)
						.subtract(BigDecimal.ONE).multiply(new BigDecimal("100.00"));
			}

		}
		return variacaoBrutaVendedores;
	}

	public void setVariacaoBrutaVendedores(BigDecimal variacaoBrutaVendedores) {
		this.variacaoBrutaVendedores = variacaoBrutaVendedores;
	}

	public BigDecimal getVariacaoInflacaoVendedores() {
		if (variacaoInflacaoVendedores == null) {

			BigDecimal total = Boolean.TRUE.equals(getEhMesAtual()) ? getTotalProvavelVendedoresPonderado()
					: getTotalVendidoVendedores();

			// Só lembre que a metaMinimaVendedores se for maior do que o histórico + inflação, considera-se 
			if (total != null && getMetaMinimaVendedores() != null &&
					getMetaMinimaVendedores().doubleValue() > 0.0) {
				variacaoInflacaoVendedores = total
						.divide(getMetaMinimaVendedores(), 20, RoundingMode.HALF_DOWN)
						.subtract(BigDecimal.ONE).multiply(new BigDecimal("100.00"));
			}

		}
		return variacaoInflacaoVendedores;
	}

	public void setVariacaoInflacaoVendedores(BigDecimal variacaoInflacaoVendedores) {
		this.variacaoInflacaoVendedores = variacaoInflacaoVendedores;
	}

	public BigDecimal getVariacaoMetaVendedores() {
		if (variacaoMetaVendedores == null) {

			BigDecimal total = Boolean.TRUE.equals(getEhMesAtual()) ? getTotalProvavelVendedores()
					: getTotalVendidoVendedores();

			if (total != null && getMetaEsperadaVendedores() != null &&
					getMetaEsperadaVendedores().doubleValue() > 0.0) {
				variacaoMetaVendedores = total
						.divide(getMetaEsperadaVendedores(), 20, RoundingMode.HALF_DOWN)
						.subtract(BigDecimal.ONE).multiply(new BigDecimal("100.00"));
			}

		}
		return variacaoMetaVendedores;
	}

	public void setVariacaoMetaVendedores(BigDecimal variacaoMetaVendedores) {
		this.variacaoMetaVendedores = variacaoMetaVendedores;
	}

	public BigDecimal getVariacaoBrutaExternos() {
		if (variacaoBrutaExternos == null) {

			BigDecimal total = Boolean.TRUE.equals(getEhMesAtual()) ? getTotalProvavelExternos()
					: getTotalVendidoExternos();

			if (total != null && getTotalHistoricoExternos() != null &&
					getTotalHistoricoExternos().doubleValue() > 0.0) {
				variacaoBrutaExternos = total
						.divide(getTotalHistoricoExternos(), 20, RoundingMode.HALF_DOWN)
						.subtract(BigDecimal.ONE).multiply(new BigDecimal("100.00"));
			}

		}
		return variacaoBrutaExternos;
	}

	public void setVariacaoBrutaExternos(BigDecimal variacaoBrutaExternos) {
		this.variacaoBrutaExternos = variacaoBrutaExternos;
	}

	public BigDecimal getVariacaoInflacaoExternos() {
		if (variacaoInflacaoExternos == null) {

			BigDecimal total = Boolean.TRUE.equals(getEhMesAtual()) ? getTotalProvavelExternos()
					: getTotalVendidoExternos();

			if (total != null && getMetaMinimaExternos() != null &&
					getMetaMinimaExternos().doubleValue() > 0.0) {
				variacaoInflacaoExternos = total
						.divide(getMetaMinimaExternos(), 20, RoundingMode.HALF_DOWN)
						.subtract(BigDecimal.ONE).multiply(new BigDecimal("100.00"));
			}

		}
		return variacaoInflacaoExternos;
	}

	public void setVariacaoInflacaoExternos(BigDecimal variacaoInflacaoExternos) {
		this.variacaoInflacaoExternos = variacaoInflacaoExternos;
	}

	public BigDecimal getVariacaoMetaExternos() {
		if (variacaoMetaExternos == null) {

			BigDecimal total = Boolean.TRUE.equals(getEhMesAtual()) ? getTotalProvavelExternos()
					: getTotalVendidoExternos();

			if (total != null && getMetaEsperadaExternos() != null &&
					getMetaEsperadaExternos().doubleValue() > 0.0) {
				variacaoMetaExternos = total
						.divide(getMetaEsperadaExternos(), 20, RoundingMode.HALF_DOWN)
						.subtract(BigDecimal.ONE).multiply(new BigDecimal("100.00"));
			}

		}
		return variacaoMetaExternos;
	}

	public void setVariacaoMetaExternos(BigDecimal variacaoMetaExternos) {
		this.variacaoMetaExternos = variacaoMetaExternos;
	}

	public BigDecimal getMetaMinima() {
		return metaMinima;
	}

	public void setMetaMinima(BigDecimal metaMinima) {
		this.metaMinima = metaMinima;
	}

	public BigDecimal getMetaMinimaVendedor() {
		if (metaMinimaVendedor == null) {
			if (getMetaMinimaVendedores() != null && getQtdeVendedores() != null && getQtdeVendedores() > 0) {
				metaMinimaVendedor = getMetaMinimaVendedores()
						.divide(new BigDecimal(getQtdeVendedores()), 2, RoundingMode.HALF_DOWN);
			}
		}
		return metaMinimaVendedor;
	}

	public void setMetaMinimaVendedor(BigDecimal metaMinimaVendedor) {
		this.metaMinimaVendedor = metaMinimaVendedor;
	}

	public BigDecimal getMetaMinimaExternos() {
		return metaMinimaExternos;
	}

	public void setMetaMinimaExternos(BigDecimal metaMinimaExterna) {
		this.metaMinimaExternos = metaMinimaExterna;
	}

	public BigDecimal getMetaEsperada() {
		return metaEsperada;
	}

	public void setMetaEsperada(BigDecimal metaEsperada) {
		this.metaEsperada = metaEsperada;
	}

	public BigDecimal getMetaMinimaVendedores() {
		return metaMinimaVendedores;
	}

	public void setMetaMinimaVendedores(BigDecimal metaMinimaVendedores) {
		this.metaMinimaVendedores = metaMinimaVendedores;
	}

	public BigDecimal getMetaEsperadaVendedores() {
		return metaEsperadaVendedores;
	}

	public void setMetaEsperadaVendedores(BigDecimal metaEsperadaVendedores) {
		this.metaEsperadaVendedores = metaEsperadaVendedores;
	}

	public BigDecimal getMetaEsperadaVendedor() {
		if (metaEsperadaVendedor == null) {
			if (getMetaEsperadaVendedores() != null && getQtdeVendedores() != null && getQtdeVendedores() > 0) {
				metaEsperadaVendedor = getMetaEsperadaVendedores()
						.divide(new BigDecimal(getQtdeVendedores()), 2, RoundingMode.HALF_DOWN);
			}
		}
		return metaEsperadaVendedor;
	}

	public void setMetaEsperadaVendedor(BigDecimal metaEsperadaVendedor) {
		this.metaEsperadaVendedor = metaEsperadaVendedor;
	}

	public BigDecimal getMetaEsperadaExternos() {
		return metaEsperadaExternos;
	}

	public void setMetaEsperadaExternos(BigDecimal metaEsperadaExternos) {
		this.metaEsperadaExternos = metaEsperadaExternos;
	}

	public Integer getQtdeDiasUteisComerciais() {
		return qtdeDiasUteisComerciais;
	}

	public void setQtdeDiasUteisComerciais(Integer qtdeDiasUteisComerciais) {
		this.qtdeDiasUteisComerciais = qtdeDiasUteisComerciais;
	}

	public Integer getQtdeDiasUteisAteHoje() {
		return qtdeDiasUteisAteHoje;
	}

	public void setQtdeDiasUteisAteHoje(Integer qtdeDiasUteisAteHoje) {
		this.qtdeDiasUteisAteHoje = qtdeDiasUteisAteHoje;
	}

	public Integer getQtdeDiasUteisRestantes() {
		return qtdeDiasUteisRestantes;
	}

	public void setQtdeDiasUteisRestantes(Integer qtdeDiasUteisRestantes) {
		this.qtdeDiasUteisRestantes = qtdeDiasUteisRestantes;
	}

	public BigDecimal getMediaDiaria() {
		return mediaDiaria;
	}

	public void setMediaDiaria(BigDecimal mediaDiaria) {
		this.mediaDiaria = mediaDiaria;
	}

	public BigDecimal getMediaDiariaVendedores() {
		if (mediaDiariaVendedores == null) {
			if (getTotalVendidoVendedores() != null && getQtdeDiasUteisAteHoje() != null
					&& getQtdeDiasUteisAteHoje().doubleValue() > 0) {
				mediaDiariaVendedores = getTotalVendidoVendedores()
						.divide(new BigDecimal(getQtdeDiasUteisAteHoje()), 2, RoundingMode.HALF_DOWN);
			}
		}
		return mediaDiariaVendedores;
	}

	public void setMediaDiariaVendedores(BigDecimal mediaDiariaVendedores) {
		this.mediaDiariaVendedores = mediaDiariaVendedores;
	}

	public BigDecimal getMediaDiariaExternos() {
		if (mediaDiariaExternos == null) {
			if (getTotalVendidoExternos() != null && getQtdeDiasUteisAteHoje() != null
					&& getQtdeDiasUteisAteHoje().doubleValue() > 0) {
				mediaDiariaExternos = getTotalVendidoExternos()
						.divide(new BigDecimal(getQtdeDiasUteisAteHoje()), 2, RoundingMode.HALF_DOWN);
			}
		}
		return mediaDiariaExternos;
	}

	public void setMediaDiariaExternos(BigDecimal mediaDiariaExternos) {
		this.mediaDiariaExternos = mediaDiariaExternos;
	}

	public BigDecimal getValorVendaMedio() {
		return valorVendaMedio;
	}

	public void setValorVendaMedio(BigDecimal valorVendaMedio) {
		this.valorVendaMedio = valorVendaMedio;
	}

	public BigDecimal getMediaDiariaAtingirHistorico() {
		return mediaDiariaAtingirHistorico;
	}

	public void setMediaDiariaAtingirHistorico(BigDecimal mediaDiariaAtingirHistorico) {
		this.mediaDiariaAtingirHistorico = mediaDiariaAtingirHistorico;
	}

	public BigDecimal getMediaDiariaAtingirMetaMinima() {
		return mediaDiariaAtingirMetaMinima;
	}

	public void setMediaDiariaAtingirMetaMinima(BigDecimal mediaDiariaAtingirMetaMinima) {
		this.mediaDiariaAtingirMetaMinima = mediaDiariaAtingirMetaMinima;
	}

	public BigDecimal getMediaDiariaAtingirMetaEsperada() {
		return mediaDiariaAtingirMetaEsperada;
	}

	public void setMediaDiariaAtingirMetaEsperada(BigDecimal mediaDiariaAtingirMetaEsperada) {
		this.mediaDiariaAtingirMetaEsperada = mediaDiariaAtingirMetaEsperada;
	}

	public BigDecimal getMediaDiariaAtingirHistoricoRestante() {
		if (mediaDiariaAtingirHistoricoRestante == null) {
			if (getTotalVendidoGlobal() != null && getMediaDiariaAtingirHistorico() != null
					&& getQtdeDiasUteisRestantes() != null && getQtdeDiasUteisRestantes().doubleValue() != 0.0) {
				if (getTotalVendidoGlobal().doubleValue() >= getMediaDiariaAtingirHistorico().doubleValue()) {
					mediaDiariaAtingirHistoricoRestante = BigDecimal.ZERO;
				} else {
					mediaDiariaAtingirHistoricoRestante = getTotalHistorico().subtract(getTotalVendidoGlobal())
							.divide(new BigDecimal(getQtdeDiasUteisRestantes().toString()), 2, RoundingMode.HALF_DOWN);
				}
			}
		}
		return mediaDiariaAtingirHistoricoRestante;
	}

	public void setMediaDiariaAtingirHistoricoRestante(BigDecimal mediaDiariaAtingirHistoricoRestante) {
		this.mediaDiariaAtingirHistoricoRestante = mediaDiariaAtingirHistoricoRestante;
	}

	public BigDecimal getMediaDiariaAtingirMetaMinimaRestante() {
		if (mediaDiariaAtingirMetaMinimaRestante == null) {
			if (getTotalVendidoGlobal() != null && getMetaMinima() != null
					&& getQtdeDiasUteisRestantes() != null && getQtdeDiasUteisRestantes().doubleValue() != 0.0) {
				if (getTotalVendidoGlobal().doubleValue() >= getMetaMinima().doubleValue()) {
					mediaDiariaAtingirMetaMinimaRestante = BigDecimal.ZERO;
				} else {
					mediaDiariaAtingirMetaMinimaRestante = getMetaMinima().subtract(getTotalVendidoGlobal())
							.divide(new BigDecimal(getQtdeDiasUteisRestantes().toString()), 2, RoundingMode.HALF_DOWN);
				}
			}
		}
		return mediaDiariaAtingirMetaMinimaRestante;
	}

	public void setMediaDiariaAtingirMetaMinimaRestante(BigDecimal mediaDiariaAtingirMetaMinimaRestante) {
		this.mediaDiariaAtingirMetaMinimaRestante = mediaDiariaAtingirMetaMinimaRestante;
	}

	public BigDecimal getMediaDiariaAtingirMetaEsperadaRestante() {
		if (mediaDiariaAtingirMetaEsperadaRestante == null) {
			if (getTotalVendidoGlobal() != null && getMetaEsperada() != null
					&& getQtdeDiasUteisRestantes() != null && getQtdeDiasUteisRestantes().doubleValue() != 0.0) {
				if (getTotalVendidoGlobal().doubleValue() >= getMetaEsperada().doubleValue()) {
					mediaDiariaAtingirMetaEsperadaRestante = BigDecimal.ZERO;
				} else {
					mediaDiariaAtingirMetaEsperadaRestante = getMetaEsperada().subtract(getTotalVendidoGlobal())
							.divide(new BigDecimal(getQtdeDiasUteisRestantes().toString()), 2, RoundingMode.HALF_DOWN);
				}
			}
		}
		return mediaDiariaAtingirMetaEsperadaRestante;
	}

	public void setMediaDiariaAtingirMetaEsperadaRestante(BigDecimal mediaDiariaAtingirMetaEsperadaRestante) {
		this.mediaDiariaAtingirMetaEsperadaRestante = mediaDiariaAtingirMetaEsperadaRestante;
	}

	public List<MesVendaItem> getItens() {
		return itens;
	}

	public void setItens(List<MesVendaItem> itens) {
		this.itens = itens;
	}

	public BigDecimal getMediaDiariaAtingirHistoricoVendedores() {
		return mediaDiariaAtingirHistoricoVendedores;
	}

	public void setMediaDiariaAtingirHistoricoVendedores(BigDecimal mediaDiariaAtingirHistoricoVendedores) {
		this.mediaDiariaAtingirHistoricoVendedores = mediaDiariaAtingirHistoricoVendedores;
	}

	public BigDecimal getMediaDiariaAtingirMetaMinimaVendedores() {
		return mediaDiariaAtingirMetaMinimaVendedores;
	}

	public void setMediaDiariaAtingirMetaMinimaVendedores(BigDecimal mediaDiariaAtingirMetaMinimaVendedores) {
		this.mediaDiariaAtingirMetaMinimaVendedores = mediaDiariaAtingirMetaMinimaVendedores;
	}

	public BigDecimal getMediaDiariaAtingirMetaEsperadaVendedores() {
		return mediaDiariaAtingirMetaEsperadaVendedores;
	}

	public void setMediaDiariaAtingirMetaEsperadaVendedores(BigDecimal mediaDiariaAtingirMetaEsperadaVendedores) {
		this.mediaDiariaAtingirMetaEsperadaVendedores = mediaDiariaAtingirMetaEsperadaVendedores;
	}

	public BigDecimal getMediaDiariaAtingirHistoricoExternos() {
		return mediaDiariaAtingirHistoricoExternos;
	}

	public void setMediaDiariaAtingirHistoricoExternos(BigDecimal mediaDiariaAtingirHistoricoExternos) {
		this.mediaDiariaAtingirHistoricoExternos = mediaDiariaAtingirHistoricoExternos;
	}

	public BigDecimal getMediaDiariaAtingirMetaMinimaExternos() {
		return mediaDiariaAtingirMetaMinimaExternos;
	}

	public void setMediaDiariaAtingirMetaMinimaExternos(BigDecimal mediaDiariaAtingirMetaMinimaExternos) {
		this.mediaDiariaAtingirMetaMinimaExternos = mediaDiariaAtingirMetaMinimaExternos;
	}

	public BigDecimal getMediaDiariaAtingirMetaEsperadaExternos() {
		return mediaDiariaAtingirMetaEsperadaExternos;
	}

	public void setMediaDiariaAtingirMetaEsperadaExternos(BigDecimal mediaDiariaAtingirMetaEsperadaExternos) {
		this.mediaDiariaAtingirMetaEsperadaExternos = mediaDiariaAtingirMetaEsperadaExternos;
	}

	public BigDecimal getMediaDiariaAtingirHistoricoRestanteVendedores() {

		if (mediaDiariaAtingirHistoricoRestanteVendedores == null) {
			if (getTotalVendidoVendedores() != null && getTotalHistoricoVendedores() != null
					&& getQtdeDiasUteisRestantes() != null && getQtdeDiasUteisRestantes().doubleValue() != 0.0) {
				if (getTotalVendidoVendedores().doubleValue() >= getTotalHistoricoVendedores().doubleValue()) {
					mediaDiariaAtingirHistoricoRestanteVendedores = BigDecimal.ZERO;
				} else {
					mediaDiariaAtingirHistoricoRestanteVendedores = getTotalHistoricoVendedores()
							.subtract(getTotalVendidoVendedores())
							.divide(new BigDecimal(getQtdeDiasUteisRestantes().toString()), 2, RoundingMode.HALF_DOWN);
				}
			}
		}

		return mediaDiariaAtingirHistoricoRestanteVendedores;
	}

	public void setMediaDiariaAtingirHistoricoRestanteVendedores(
			BigDecimal mediaDiariaAtingirHistoricoRestanteVendedores) {
		this.mediaDiariaAtingirHistoricoRestanteVendedores = mediaDiariaAtingirHistoricoRestanteVendedores;
	}

	public BigDecimal getMediaDiariaAtingirMetaMinimaRestanteVendedores() {

		if (mediaDiariaAtingirMetaMinimaRestanteVendedores == null) {
			if (getTotalVendidoVendedores() != null && getMetaMinimaVendedores() != null
					&& getQtdeDiasUteisRestantes() != null && getQtdeDiasUteisRestantes().doubleValue() != 0.0) {
				if (getTotalVendidoVendedores().doubleValue() >= getMetaMinimaVendedores().doubleValue()) {
					mediaDiariaAtingirMetaMinimaRestanteVendedores = BigDecimal.ZERO;
				} else {
					mediaDiariaAtingirMetaMinimaRestanteVendedores = getMetaMinimaVendedores()
							.subtract(getTotalVendidoVendedores())
							.divide(new BigDecimal(getQtdeDiasUteisRestantes().toString()), 2, RoundingMode.HALF_DOWN);
				}
			}
		}

		return mediaDiariaAtingirMetaMinimaRestanteVendedores;
	}

	public void setMediaDiariaAtingirMetaMinimaRestanteVendedores(
			BigDecimal mediaDiariaAtingirMetaMinimaRestanteVendedores) {
		this.mediaDiariaAtingirMetaMinimaRestanteVendedores = mediaDiariaAtingirMetaMinimaRestanteVendedores;
	}

	public BigDecimal getMediaDiariaAtingirMetaEsperadaRestanteVendedores() {

		if (mediaDiariaAtingirMetaEsperadaRestanteVendedores == null) {
			if (getTotalVendidoVendedores() != null && getMetaEsperadaVendedores() != null
					&& getQtdeDiasUteisRestantes() != null && getQtdeDiasUteisRestantes().doubleValue() != 0.0) {
				if (getTotalVendidoVendedores().doubleValue() >= getMetaEsperadaVendedores().doubleValue()) {
					mediaDiariaAtingirMetaEsperadaRestanteVendedores = BigDecimal.ZERO;
				} else {
					mediaDiariaAtingirMetaEsperadaRestanteVendedores = getMetaEsperadaVendedores()
							.subtract(getTotalVendidoVendedores())
							.divide(new BigDecimal(getQtdeDiasUteisRestantes().toString()), 2, RoundingMode.HALF_DOWN);
				}
			}
		}

		return mediaDiariaAtingirMetaEsperadaRestanteVendedores;
	}

	public void setMediaDiariaAtingirMetaEsperadaRestanteVendedores(
			BigDecimal mediaDiariaAtingirMetaEsperadaRestanteVendedores) {
		this.mediaDiariaAtingirMetaEsperadaRestanteVendedores = mediaDiariaAtingirMetaEsperadaRestanteVendedores;
	}

	public BigDecimal getMediaDiariaAtingirHistoricoRestanteExternos() {

		if (mediaDiariaAtingirHistoricoRestanteExternos == null) {
			if (getTotalVendidoExternos() != null && getTotalHistoricoExternos() != null
					&& getQtdeDiasUteisRestantes() != null && getQtdeDiasUteisRestantes().doubleValue() != 0.0) {
				if (getTotalVendidoExternos().doubleValue() >= getTotalHistoricoExternos().doubleValue()) {
					mediaDiariaAtingirHistoricoRestanteExternos = BigDecimal.ZERO;
				} else {
					mediaDiariaAtingirHistoricoRestanteExternos = getTotalVendidoExternos()
							.subtract(getTotalHistoricoExternos())
							.divide(new BigDecimal(getQtdeDiasUteisRestantes().toString()), 2, RoundingMode.HALF_DOWN);
				}
			}
		}

		return mediaDiariaAtingirHistoricoRestanteExternos;
	}

	public void setMediaDiariaAtingirHistoricoRestanteExternos(BigDecimal mediaDiariaAtingirHistoricoRestanteExternos) {
		this.mediaDiariaAtingirHistoricoRestanteExternos = mediaDiariaAtingirHistoricoRestanteExternos;
	}

	public BigDecimal getMediaDiariaAtingirMetaMinimaRestanteExternos() {

		if (mediaDiariaAtingirMetaMinimaRestanteExternos == null) {
			if (getTotalVendidoExternos() != null && getMetaMinimaExternos() != null
					&& getQtdeDiasUteisRestantes() != null && getQtdeDiasUteisRestantes().doubleValue() != 0.0) {
				if (getTotalVendidoExternos().doubleValue() >= getMetaMinimaExternos().doubleValue()) {
					mediaDiariaAtingirMetaMinimaRestanteExternos = BigDecimal.ZERO;
				} else {
					mediaDiariaAtingirMetaMinimaRestanteExternos = getTotalVendidoExternos()
							.subtract(getMetaMinimaExternos())
							.divide(new BigDecimal(getQtdeDiasUteisRestantes().toString()), 2, RoundingMode.HALF_DOWN);
				}
			}
		}

		return mediaDiariaAtingirMetaMinimaRestanteExternos;
	}

	public void setMediaDiariaAtingirMetaMinimaRestanteExternos(
			BigDecimal mediaDiariaAtingirMetaMinimaRestanteExternos) {
		this.mediaDiariaAtingirMetaMinimaRestanteExternos = mediaDiariaAtingirMetaMinimaRestanteExternos;
	}

	public BigDecimal getMediaDiariaAtingirMetaEsperadaRestanteExternos() {

		if (mediaDiariaAtingirMetaEsperadaRestanteExternos == null) {
			if (getTotalVendidoExternos() != null && getMetaEsperadaExternos() != null
					&& getQtdeDiasUteisRestantes() != null && getQtdeDiasUteisRestantes().doubleValue() != 0.0) {
				if (getTotalVendidoExternos().doubleValue() >= getMetaEsperadaExternos().doubleValue()) {
					mediaDiariaAtingirMetaEsperadaRestanteExternos = BigDecimal.ZERO;
				} else {
					mediaDiariaAtingirMetaEsperadaRestanteExternos = getTotalVendidoExternos()
							.subtract(getMetaEsperadaExternos())
							.divide(new BigDecimal(getQtdeDiasUteisRestantes().toString()), 2, RoundingMode.HALF_DOWN);
				}
			}
		}

		return mediaDiariaAtingirMetaEsperadaRestanteExternos;
	}

	public void setMediaDiariaAtingirMetaEsperadaRestanteExternos(
			BigDecimal mediaDiariaAtingirMetaEsperadaRestanteExternos) {
		this.mediaDiariaAtingirMetaEsperadaRestanteExternos = mediaDiariaAtingirMetaEsperadaRestanteExternos;
	}

	public Boolean getEhMesAtual() {
		return CalendarUtil.isMesmoMesAno(new Date(), getMesAno());
	}

	public void setEhMesAtual(Boolean ehMesAtual) {
		this.ehMesAtual = ehMesAtual;
	}

	@Override
	public int hashCode() {
		final int prime = 719;
		final int result = 239;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result).append(mesAno).toHashCode();
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
		final MesVenda iObj = (MesVenda) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(mesAno, iObj.mesAno)
				.isEquals();
	}

	@Override
	public String toStringToView() {
		// TODO Auto-generated method stub
		return null;
	}

}
