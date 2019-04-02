package com.bonsucesso.erp.cortinas.business;



import java.math.BigDecimal;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * VO utilizado para a tabelinha de comparação de eficiência entre as larguras/fator selecionado para casos de utilização de tecidos na
 * vertical.
 * 
 * @author Carlos Eduardo Pauluk
 *
 */
public class CortinaLargurasCompar {

	private int uid;

	private BigDecimal fatorReal;

	private Integer larguras;

	private BigDecimal qtdeTecido;

	private BigDecimal diferenca;

	private BigDecimal eficiencia;

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public BigDecimal getFatorReal() {
		return fatorReal;
	}

	public void setFatorReal(BigDecimal fatorReal) {
		this.fatorReal = fatorReal;
	}

	public Integer getLarguras() {
		return larguras;
	}

	public void setLarguras(Integer larguras) {
		this.larguras = larguras;
	}

	public BigDecimal getQtdeTecido() {
		return qtdeTecido;
	}

	public void setQtdeTecido(BigDecimal qtdeTecido) {
		this.qtdeTecido = qtdeTecido;
	}

	public BigDecimal getDiferenca() {
		return diferenca;
	}

	public void setDiferenca(BigDecimal diferenca) {
		this.diferenca = diferenca;
	}

	public BigDecimal getEficiencia() {
		return eficiencia;
	}

	public void setEficiencia(BigDecimal eficiencia) {
		this.eficiencia = eficiencia;
	}

	@Override
	public int hashCode() {
		final int prime = 337;
		final int result = 181;
		// Utiliza a API do apache-commons
		return new HashCodeBuilder(prime, result)
				.append(fatorReal)
				.append(larguras)
				.append(qtdeTecido)
				.append(diferenca)
				.append(eficiencia)
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
		final CortinaLargurasCompar iObj = (CortinaLargurasCompar) obj;
		// Utiliza a API do apache-commons
		return new EqualsBuilder()
				.append(fatorReal, iObj.fatorReal)
				.append(larguras, iObj.larguras)
				.append(qtdeTecido, iObj.qtdeTecido)
				.append(diferenca, iObj.diferenca)
				.append(eficiencia, iObj.eficiencia)
				.isEquals();
	}

}
