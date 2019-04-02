package com.bonsucesso.erp.tests.base;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;

import com.ocabit.utils.tests.selenium.SeleniumHelper;

/**
 * Classe com métodos basicos para acesso ao site
 * @author Rodrigo Justus Polette
 *
 */
public class BaseActions {
	protected static Logger logger = Logger.getLogger(BaseActions.class);
	
	public final static String address = "https://www.ocabit.com/bonerp-hom/";
	//public final static String address = "http://nb-cep:8080/bonerp/";
	
	public final static String CAMPO_TIPO_TEXT = "text";
	public final static String CAMPO_TIPO_INT = "inteiro";
	public final static String CAMPO_TIPO_RADIO = "radio";
	public final static String CAMPO_TIPO_DATA = "data";
	public final static String CAMPO_TIPO_RADIOPF = "radioPF";
	public final static String CAMPO_TIPO_COMBOPF = "comboPF";
	public final static String CAMPO_TIPO_CHECKPF = "checkPF";
	
	/**
	 * Efetua Login com usuário Selenium
	 * @param h
	 */
	public static void login(SeleniumHelper h) {
		boolean jaLogado = false;;

		String usuario = "selenium";
		try {
			if (h.qtdById("username") == 0) {//Nao existe o campo username no formulário, não etá no Login
				String usuarioLogado = h.valueById("usuarioLogado");
				if (usuarioLogado.equals(usuario)) {
					jaLogado = true;
				} else {
					// se está logado, mas com outro usuário...
					h.byId("btnLogout").click();				
				}
			}
			else
				jaLogado = false;
		} catch (NoSuchElementException e) {
			jaLogado = false;
		} catch (TimeoutException e) {
			jaLogado = false;
		}

		if (!jaLogado) {
			h.byId("username").clear();
			h.byId("username").sendKeys(usuario);
			h.byId("password").clear();
			h.byId("password").sendKeys("Selenium123");//Com @ é produção
			h.byCSS("[name=btnLogin]").click();
			Assert.assertEquals(h.valueById("usuarioLogado"), usuario);
		}
	}
	
	public static void executaJS(SeleniumHelper h, String comando) {
		((JavascriptExecutor) h.getDriver()).executeScript(comando);
	}
	
	/**
	 * Marca/Desmarca um checkPF
	 * @param h Driver
	 * @param id final do Id do componente a preencher
	 * @throws InterruptedException
	 */
	public static void clickCheckPF(SeleniumHelper h, String id) throws InterruptedException {
		preencheCampo(h, id, CAMPO_TIPO_CHECKPF, "");
	}
	
	/**
	 * Preenche campo
	 * @param h Driver
	 * @param id final do Id do componente a preencher
	 * @param tipo Tipo (text/inteiro/radio/data/radioPF/comboPF/checkPF)
	 * @param valor	String
	 * @throws InterruptedException
	 */
	public static void preencheCampo(SeleniumHelper h, String id, String tipo, String valor) throws InterruptedException {
		if (tipo.equals(CAMPO_TIPO_TEXT) || tipo.equals(CAMPO_TIPO_INT)) {
			h.byIdEnding(id).sendKeys(valor);
		}
		else if (tipo.equals(CAMPO_TIPO_RADIO)) {
			h.clickByIdEnding(id + ":" + valor);
		}
		else if (tipo.equals(CAMPO_TIPO_DATA)) {
			executaJS(h,"$('[id*=\""+id+"\"]').val('"+valor+"');");
		}
		else if (tipo.equals(CAMPO_TIPO_RADIOPF)) {//igual ao idEnding
			executaJS(h,"$('[id*=\""+id+"\\:"+valor+"\"]').parent().parent().find('span').click();");
		}
		else if (tipo.equals(CAMPO_TIPO_COMBOPF)) {
			String id2 = id;
			if (id2.endsWith("_label")) id2 = id.replaceAll("_label", "_items");
			
			executaJS(h,"$('[id*=\""+id2+"\"]').find('li[data-label=\""+valor+"\"]').click();");
		}
		else if (tipo.equals(CAMPO_TIPO_CHECKPF)) {
			executaJS(h,"$('[id*=\""+id+"\"]').parent().parent().find('span').click();");
		}
		Thread.sleep(500);
	}
	
	public static void verificaCampoPreenchido(SeleniumHelper h, String id, String tipo, String valor, String msg) {
		if (tipo.equals(CAMPO_TIPO_TEXT) || tipo.equals(CAMPO_TIPO_DATA)) {
			Assert.assertEquals(h.valueByIdEnding(id),valor,msg);
		}
		else if (tipo.equals(CAMPO_TIPO_INT)) {
			String valorCampo = h.valueByIdEnding(id);
			Integer valorNumero = Integer.parseInt(valorCampo.replaceAll("[.]",""));//2.000 => 2000
			Assert.assertEquals(valorNumero.toString(),valor,msg);
		}
		else if (tipo.equals(CAMPO_TIPO_RADIO)) {
			Assert.assertTrue(h.byIdEnding(id + ":" + valor).isSelected(),msg);
		}
		else if (tipo.equals(CAMPO_TIPO_RADIOPF)) {
			Assert.assertTrue(h.byIdEnding(id + ":" + valor).isSelected(),msg);
		}
		else if (tipo.equals(CAMPO_TIPO_COMBOPF)) {
			String id2 = id;
			if (id2.endsWith("_items")) id2 = id.replaceAll("_items", "_label");
			Assert.assertEquals(h.textByIdEnding(id2),valor,msg);
			//logger.info("ComboPF não conferido: " + id + ":" + valor + " MSG:" + msg);
		}
	}
	
	public static void formListPesquisa(SeleniumHelper h, String texto) throws InterruptedException {
		if (! texto.isEmpty() || ! h.valueByIdEnding("iStrPesquisa").isEmpty()) {//Só não faz quando os 2 forem vazios
			h.byIdEnding("iStrPesquisa").clear();
			h.byIdEnding("iStrPesquisa").sendKeys(texto);
			h.clickByIdEnding("btnStrPesquisa");
			Thread.sleep(1500);
		}
		else
			Thread.sleep(500);
	}
}
