package com.bonsucesso.erp.cortinas.business;



import java.util.List;

import com.bonsucesso.erp.cortinas.model.Cortina;


/**
 * Classe que executa as validações em uma cortina e seus itens.
 *
 * Regras:
 *
 * Cortina só é validada caso cortina.cortinaCompleta=true.
 * Caso cortina.comInstalacao=true, deve conter um item do tipo MAO_DE_OBRA_INSTALACAO.
 * Deve conter obrigatoriamente os itens:
 * - MAO_DE_OBRA_COSTUREIRA
 * - TECIDO (1 por camada)
 *
 * Caso cortina.varaoOuTrilho=VARAO
 * - Não pode possuir TRILHO,RODIZIO,TERMINAL,GANCHO.
 * - Deve possuir VARAO(1 por camada)
 * - Deve possuir ARGOLA+GANCHO(1 CJ por camada) ou ILHOS+MAO_DE_OBRA_ILHOS+ENTRETELA(1 por camada)
 * - Deve possuir PONTEIRA(2 por camada)
 * - Caso possuir ILHÓS, deve utilizar suporte triplo.
 *
 * Caso cortina.varaoOuTrilho=TRILHO
 * - Não pode possuir ILHOS,MAO_DE_OBRA_ILHOS,ARGOLA,VARAO,PONTEIRA.
 * - Deve possuir TRILHO(1 por camada)
 * - Deve possuir TERMINAL(2 por camada)
 *
 * Caso cortina.largura >= 3.00m, deve possuir 2 suportes
 *
 *
 *
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public class CortinaValidacoes {

	public List<String> validarCortina(Cortina cortina) {
		//		StringBuilder sb = new StringBuilder();
		//		return sb.toString();
		return null;
	}

}
