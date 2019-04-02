package com.bonsucesso.erp.financeiro.model;



import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;


@Embeddable
public class Recorrencia implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 3575208331689690236L;

	public enum Frequencia {
		NENHUMA("Nenhuma"),
		MENSAL("Mensal"),
		ANUAL("Anual");

		private String label;

		public String getLabel() {
			return label;
		}

		private void setLabel(String label) {
			this.label = label;
		}

		private Frequencia(String label) {
			setLabel(label);
		}
	}

	public enum TipoRepeticao {
		NENHUMA("Nenhuma"),
		// ex.: todo dia 10
		DIA_FIXO("Dia fixo"),
		// ex.: 5ª dia
		DIA_UTIL("Dia útil");

		private String label;

		public String getLabel() {
			return label;
		}

		private void setLabel(String label) {
			this.label = label;
		}

		private TipoRepeticao(String label) {
			setLabel(label);
		}
	}

	@Column(name = "recorrente", nullable = false)
	@NotNull(message = "Campo 'Recorrente' precisa ser informado")
	private Boolean recorrente = Boolean.FALSE;

	@Enumerated(EnumType.STRING)
	@Column(name = "recorr_frequencia", nullable = true, length = 50)
	private Frequencia frequencia = Frequencia.NENHUMA;

	@Enumerated(EnumType.STRING)
	@Column(name = "recorr_tipo_repet", nullable = true, length = 50)
	private TipoRepeticao tipoRepeticao = TipoRepeticao.NENHUMA;

	/**
	 * Utilizar 32 para marcar o último dia do mês.
	 */
	@Column(name = "recorr_dia", nullable = true)
	private Integer dia;

	/**
	 * Utilizado para marcar a variação em relação ao dia em que seria o vencimento.
	 * Exemplo: dia=32 (último dia do mês) + variacao=-2 >>> 2 dias antes do último dia do mês
	 */
	@Column(name = "recorr_variacao", nullable = true)
	private Integer variacao;

	public Boolean getRecorrente() {
		return recorrente;
	}

	public void setRecorrente(Boolean recorrente) {
		this.recorrente = recorrente;
	}

	public Frequencia getFrequencia() {
		return frequencia;
	}

	public void setFrequencia(Frequencia frequencia) {
		this.frequencia = frequencia;
	}

	public TipoRepeticao getTipoRepeticao() {
		return tipoRepeticao;
	}

	public void setTipoRepeticao(TipoRepeticao tipoRepeticao) {
		this.tipoRepeticao = tipoRepeticao;
	}

	public Integer getDia() {
		return dia;
	}

	public void setDia(Integer dia) {
		this.dia = dia;
	}

	public Integer getVariacao() {
		return variacao;
	}

	public void setVariacao(Integer variacao) {
		this.variacao = variacao;
	}

}
