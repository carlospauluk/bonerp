package com.bonsucesso.erp.tests.crud;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.bonsucesso.erp.tests.base.BaseActions;
import com.ocabit.utils.tests.selenium.SeleniumHelper;
import com.ocabit.utils.tests.selenium.SeleniumHelper.Browser;

public class OrcamentoCortinaFormIT	 {
	protected static Logger logger = Logger.getLogger(OrcamentoCortinaFormIT.class);
	
	private static final String page = "/pages/erp/orcamentos/orcamentoCortinaForm.jsf";
	private static final String page_list = "/pages/erp/orcamentos/orcamentoCortinaList.jsf";
	
	public SeleniumHelper h;
	
	@BeforeSuite
	public void startup() {
		h = new SeleniumHelper(Browser.CHROME);
		h.getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
	
	@Test
	public void login() {
		h.getDriver().get(BaseActions.address);
		BaseActions.login(h);
	}
	
	private void novoItemCortinaQtd(Integer reduzido, Integer qtd, Boolean naoAlteraQtdRecalculo)  throws InterruptedException {
		Integer[] camadas = {};
		novoItemCortinaQtd(reduzido,camadas,qtd,naoAlteraQtdRecalculo);
	}

	private void novoItemCortinaQtd(Integer reduzido, Integer[] camadas, Integer qtd, Boolean naoAlteraQtdRecalculo) throws InterruptedException {
		Thread.sleep(500);
		h.clickById("frmCortina:btnNovoCortinaItem");
		Thread.sleep(500);
		BaseActions.preencheCampo(h, "frmCortinaItem:iReduzido", BaseActions.CAMPO_TIPO_TEXT, reduzido.toString());
		h.clickById("frmCortinaItem:opnDlgCortinaItem");//Para retornar a descrição do item
		Thread.sleep(500);
		
		if (camadas.length > 0) {
			Integer camada_comp;
			for (int i=0; i<camadas.length; i++) {
				camada_comp = camadas[i]-1;
				BaseActions.preencheCampo(h, "frmCortinaItem:j_idt915",BaseActions.CAMPO_TIPO_RADIOPF, camada_comp.toString());//Camada				
			}
		}
		
		BaseActions.preencheCampo(h, "frmCortinaItem:j_idt1065", BaseActions.CAMPO_TIPO_TEXT, qtd.toString());
		
		if (naoAlteraQtdRecalculo)
			BaseActions.clickCheckPF(h, "frmCortinaItem:j_idt1069_input");
		
		h.clickById("frmCortinaItem:btnSalvarCortinaItem");
		Assert.assertNotNull(
			h.byCSS("[class='ui-growl-image ui-growl-image-info']"),
			"Não apresentou mensagem de sucesso ao cadastrar item cortina (Red="+reduzido+").");
	}
	
	private void novoItemCortina(Integer reduzido) throws InterruptedException {
		Integer[] camadas = {};
		novoItemCortina(reduzido,camadas,-1,-1);
	}
	
	private void novoItemCortina(Integer reduzido, Integer[] camadas) throws InterruptedException {
		novoItemCortina(reduzido,camadas,-1,-1);
	}
	
	private void novoItemCortina(Integer reduzido, Integer[] camadas, Integer fixacao, Integer fator) throws InterruptedException {
		Thread.sleep(500);
		h.clickById("frmCortina:btnNovoCortinaItem");
		Thread.sleep(500);
		BaseActions.preencheCampo(h, "frmCortinaItem:iReduzido", BaseActions.CAMPO_TIPO_TEXT, reduzido.toString());
		h.clickById("frmCortinaItem:opnDlgCortinaItem");//Para retornar a descrição do item
		Thread.sleep(500);
		
		if (camadas.length > 0) {
			Integer camada_comp;
			for (int i=0; i<camadas.length; i++) {
				camada_comp = camadas[i]-1;
				BaseActions.preencheCampo(h, "frmCortinaItem:j_idt915",BaseActions.CAMPO_TIPO_RADIOPF, camada_comp.toString());//Camada				
			}
		}
		
		if (fixacao != -1) {
			Thread.sleep(1000);
			BaseActions.preencheCampo(h, "frmCortinaItem:j_idt932",BaseActions.CAMPO_TIPO_RADIO, fixacao.toString());//Fixação
		}
		
		if (fator != -1) {
			BaseActions.preencheCampo(h, "frmCortinaItem:iFator",BaseActions.CAMPO_TIPO_RADIOPF, fator.toString());//Fator
			Thread.sleep(500);
		}
		
		h.clickById("frmCortinaItem:btnSalvarCortinaItem");
		Assert.assertNotNull(
			h.byCSS("[class='ui-growl-image ui-growl-image-info']"),
			"Não apresentou mensagem de sucesso ao cadastrar item cortina (Red="+reduzido+").");
	}
	
	@Test(dependsOnMethods = { "login" })
	public void orcamentoCortinaFormTestNovoOrcamento() throws InterruptedException {
		h.getDriver().get(BaseActions.address + page);
		Thread.sleep(3000);
		
		BaseActions.preencheCampo(h, "frm:iCliente_input", BaseActions.CAMPO_TIPO_TEXT, "RODRIGO JUSTUS POLETTE - TESTER");
		h.byCSS("[class='ui-autocomplete-query']").click();
		
		//Testa preenchimento automatico
		BaseActions.executaJS(h, "PrimeFaces.ab({s:\"frm:j_idt125\",u:\"frm:iCabecalho\",f:\"frm\"});return false;");
		Thread.sleep(500);
		BaseActions.verificaCampoPreenchido(h, "iCabecalho", BaseActions.CAMPO_TIPO_TEXT, "A/C RODRIGO JUSTUS POLETTE - TESTER\n", "Cabecalho orç. cortina");
		BaseActions.executaJS(h, "PrimeFaces.ab({s:\"frm:j_idt134\",u:\"frm:iObs\",f:\"frm\"});return false;");
		Thread.sleep(500);
		BaseActions.verificaCampoPreenchido(h, "frm:iObs", BaseActions.CAMPO_TIPO_TEXT, "* Valor à vista.\n"+
					"** Prazo de entrega: 30 dias (a contar da data da encomenda).\n"+
					"*** Condição de pagto.: 50% na encomenda + 50% na entrega.", "Obs orç cortina");
		
		h.clickById("frm:btnSalvar");
		Thread.sleep(1000);
		
		Assert.assertEquals(h.qtdByIdEnding("j_idt148:0:dtItens:btnNovoItem"),1,"Não salvou orçamento, não mostrou boão nova cortina.");
	}
	
	@Test(dependsOnMethods = { "orcamentoCortinaFormTestNovoOrcamento" })
	private void orcamentoCortinaFormTestCortinaVoilTapassol() throws InterruptedException {
		h.clickByIdEnding("j_idt148:0:dtItens:btnNovoItem");
		Thread.sleep(1000);
		BaseActions.preencheCampo(h, "frmCortina:iDescricao", BaseActions.CAMPO_TIPO_TEXT, "VOIL (3X1) / TAPASSOL NA ARGOLA");
		BaseActions.preencheCampo(h, "frmCortina:iVaraoOuTrilho", BaseActions.CAMPO_TIPO_RADIOPF, "1");
		BaseActions.preencheCampo(h, "frmCortina:iLargura", BaseActions.CAMPO_TIPO_TEXT, "250");
		BaseActions.preencheCampo(h, "frmCortina:iAltura", BaseActions.CAMPO_TIPO_TEXT, "200");
		BaseActions.preencheCampo(h, "frmCortina:iLarguraJanela", BaseActions.CAMPO_TIPO_TEXT, "210");
		BaseActions.preencheCampo(h, "frmCortina:iAlturaJanela", BaseActions.CAMPO_TIPO_TEXT, "160");
		BaseActions.preencheCampo(h, "frmCortina:iCamada", BaseActions.CAMPO_TIPO_RADIOPF, "1");//2 camadas
		h.clickById("frmCortina:j_idt722");//Atualizar lados
		Thread.sleep(500);
		h.clickById("frmCortina:btnSalvarCortina");
		Assert.assertNotNull(
			h.byCSS("[class='ui-growl-image ui-growl-image-info']"),
			"Não apresentou mensagem de sucesso ao cadastrar nova cortina no orçamento.");
		
		Integer[] camadas = new Integer[1];
		
		camadas[0] = 1;
		novoItemCortina(1568,camadas);//Argola / Camada 1		
		novoItemCortina(5014,camadas);//Gancho / Camada 1		
		novoItemCortina(4632,camadas,0,0);//Tecido / Camada 1 / Argola / Fator 1/1		
		novoItemCortina(1878,camadas);//Varão / Camada 1
		
		camadas[0] = 2;
		novoItemCortina(1568,camadas);//Argola / Camada 2		
		novoItemCortina(5014,camadas);//Gancho / Camada 2		
		novoItemCortina(1878,camadas);//Varão / Camada 2		
		novoItemCortina(1503,camadas,0,2);//Tecido / Camada 2 / Fix Argola / Fator 3/1
		
		novoItemCortina(2442);//Suporte duplo
		
		h.clickById("frmCortina:btnAdicionarMO");//Não mostra mensagem
		Thread.sleep(1000);
		Assert.assertTrue(h.textPresent("Mão-de-obra Instalação"),"Não incluiu Mão de Obra");
		
		h.clickById("frmCortina:btnCalcularQtdes");
		Thread.sleep(1000);
		Assert.assertNotNull(
				h.byCSS("[class='ui-growl-image ui-growl-image-info']"),
				"Não apresentou mensagem de sucesso ao calcular qtds.");
		
		h.clickById("frmCortina:btnSalvarCortina");
		BaseActions.verificaCampoPreenchido(h, "frmCortina:j_idt706", BaseActions.CAMPO_TIPO_TEXT, "286,09", "Valor total errado para cortina VOIL (3x1) / Tapassol na Argola");
		
		BaseActions.executaJS(h, "$('#frmCortina\\\\:dlgCortina_title').parent().find('a').click();");//Fecha o item cortina
	}
	
	@Test(dependsOnMethods = { "orcamentoCortinaFormTestNovoOrcamento" })
	private void orcamentoCortinaFormTestCortinaMalhaDrapVoilTapassol() throws InterruptedException {
		h.clickByIdEnding("j_idt148:0:dtItens:btnNovoItem");
		Thread.sleep(1000);
		BaseActions.preencheCampo(h, "frmCortina:iDescricao", BaseActions.CAMPO_TIPO_TEXT, "MALHA DRAP 3/ VOIL (3x1)/ TAPASSOL NA ARGOLA");
		BaseActions.preencheCampo(h, "frmCortina:iVaraoOuTrilho", BaseActions.CAMPO_TIPO_RADIOPF, "1");//Varão
		BaseActions.preencheCampo(h, "frmCortina:iLargura", BaseActions.CAMPO_TIPO_TEXT, "300");
		BaseActions.preencheCampo(h, "frmCortina:iAltura", BaseActions.CAMPO_TIPO_TEXT, "250");
		BaseActions.preencheCampo(h, "frmCortina:iCamada", BaseActions.CAMPO_TIPO_RADIOPF, "2");//3 camadas
		h.clickById("frmCortina:j_idt722");//Atualizar lados
		Thread.sleep(500);
		h.clickById("frmCortina:btnSalvarCortina");
		Assert.assertNotNull(
			h.byCSS("[class='ui-growl-image ui-growl-image-info']"),
			"Não apresentou mensagem de sucesso ao cadastrar nova cortina no orçamento.");
		
		Integer[] camadas = new Integer[1];
		
		camadas[0] = 3;
		novoItemCortina(6352,camadas,3,2);//Cortinado / Camada 3 / Fizacao nenhuma / Fator 3X1
		camadas[0] = 2;
		novoItemCortina(1503,camadas,0,0);//Voil / Camada 2 / Fixacao Argola / 1X1
		camadas[0] = 1;
		novoItemCortina(4632,camadas,0,0);//Tapassol / Camada 2 / Fixacao Argola / 1X1
		
		camadas = new Integer[2];
		camadas[0] = 1;
		camadas[1] = 2;
		novoItemCortina(1568,camadas);//Argola / Camada 1 e 2
		novoItemCortina(5014,camadas);//Gancho / Camada 1 e 2
		
		camadas = new Integer[3];
		camadas[0] = 1;
		camadas[1] = 2;
		camadas[2] = 3;
		novoItemCortina(1878,camadas);//Varão / Camada 1, 2 e 3
		
		novoItemCortina(3859);//Suporte pvc triplo
		
		h.clickById("frmCortina:btnAdicionarMO");//Não mostra mensagem
		Thread.sleep(1000);
		Assert.assertTrue(h.textPresent("Mão-de-obra Instalação"),"Não incluiu Mão de Obra");
		
		h.clickById("frmCortina:btnCalcularQtdes");
		Thread.sleep(1000);
		Assert.assertNotNull(
				h.byCSS("[class='ui-growl-image ui-growl-image-info']"),
				"Não apresentou mensagem de sucesso ao calcular qtds.");
		
		h.clickById("frmCortina:btnSalvarCortina");
		BaseActions.verificaCampoPreenchido(h, "frmCortina:j_idt706", BaseActions.CAMPO_TIPO_TEXT, "597,44", "Valor total errado para cortina VOIL (3x1) / Tapassol na Argola");
		
		BaseActions.executaJS(h, "$('#frmCortina\\\\:dlgCortina_title').parent().find('a').click();");//Fecha o item cortina
	}
	
	@Test(dependsOnMethods = { "orcamentoCortinaFormTestNovoOrcamento" })
	private void orcamentoCortinaFormTestCortinaVoilBlecautTrilho() throws InterruptedException {
		h.clickByIdEnding("j_idt148:0:dtItens:btnNovoItem");
		Thread.sleep(1000);
		BaseActions.preencheCampo(h, "frmCortina:iDescricao", BaseActions.CAMPO_TIPO_TEXT, "VOIL (3X1) / BLECAUTE (1X1) NO TRILHO");
		BaseActions.preencheCampo(h, "frmCortina:iVaraoOuTrilho", BaseActions.CAMPO_TIPO_RADIOPF, "0");//Tilho
		BaseActions.preencheCampo(h, "frmCortina:iLargura", BaseActions.CAMPO_TIPO_TEXT, "300");
		BaseActions.preencheCampo(h, "frmCortina:iAltura", BaseActions.CAMPO_TIPO_TEXT, "200");
		BaseActions.preencheCampo(h, "frmCortina:iLarguraJanela", BaseActions.CAMPO_TIPO_TEXT, "260");
		BaseActions.preencheCampo(h, "frmCortina:iAlturaJanela", BaseActions.CAMPO_TIPO_TEXT, "160");
		BaseActions.preencheCampo(h, "frmCortina:iCamada", BaseActions.CAMPO_TIPO_RADIOPF, "1");//2 camadas
		h.clickById("frmCortina:j_idt722");//Atualizar lados
		Thread.sleep(500);
		h.clickById("frmCortina:btnSalvarCortina");
		Assert.assertNotNull(
			h.byCSS("[class='ui-growl-image ui-growl-image-info']"),
			"Não apresentou mensagem de sucesso ao cadastrar nova cortina no orçamento.");
		
		Integer[] camadas = new Integer[1];
		
		camadas[0] = 1;
		novoItemCortina(484,camadas,2,0);//Cortinado / Camada 1 / Fizacao rodizio / Fator 1X1
		camadas[0] = 2;
		novoItemCortina(1503,camadas,2,2);//Voil / Camada 1 / Fixacao rodízio / 3X1
		
		camadas = new Integer[2];
		camadas[0] = 1;
		camadas[1] = 2;
		novoItemCortina(8999,camadas);//Rodízio / Camada 1 e 2
		novoItemCortina(9011,camadas);//Entretela / Camada 1 e 2
		
		camadas = new Integer[1];
		camadas[0] = 1;
		novoItemCortina(12948,camadas); //Trilho duplo / Apenas na camada 1, pois é duplo
		
		h.clickById("frmCortina:btnAdicionarMO");//Não mostra mensagem
		Thread.sleep(1000);
		Assert.assertTrue(h.textPresent("Mão-de-obra Instalação"),"Não incluiu Mão de Obra");
		
		h.clickById("frmCortina:btnCalcularQtdes");
		Thread.sleep(1000);
		Assert.assertNotNull(
				h.byCSS("[class='ui-growl-image ui-growl-image-info']"),
				"Não apresentou mensagem de sucesso ao calcular qtds.");
		
		h.clickById("frmCortina:btnSalvarCortina");
		BaseActions.verificaCampoPreenchido(h, "frmCortina:j_idt706", BaseActions.CAMPO_TIPO_TEXT, "457,49", "Valor total errado para cortina Voil / Blecaut no Trilho");
		
		BaseActions.executaJS(h, "$('#frmCortina\\\\:dlgCortina_title').parent().find('a').click();");//Fecha o item cortina
	}
	
	@Test(dependsOnMethods = { "orcamentoCortinaFormTestNovoOrcamento" })
	private void orcamentoCortinaFormTestCortinaVoilTapassolIlhosSVarao() throws InterruptedException {
		h.clickByIdEnding("j_idt148:0:dtItens:btnNovoItem");
		Thread.sleep(1000);
		BaseActions.preencheCampo(h, "frmCortina:iDescricao", BaseActions.CAMPO_TIPO_TEXT, "VOIL (4X1) / TAPASSOL (3X1) NO ILHÓS - \"SALA VARÃO\"");
		BaseActions.preencheCampo(h, "frmCortina:iVaraoOuTrilho", BaseActions.CAMPO_TIPO_RADIOPF, "1");//Varao
		BaseActions.preencheCampo(h, "frmCortina:iLargura", BaseActions.CAMPO_TIPO_TEXT, "316");
		BaseActions.preencheCampo(h, "frmCortina:iAltura", BaseActions.CAMPO_TIPO_TEXT, "280");
		BaseActions.preencheCampo(h, "frmCortina:iCamada", BaseActions.CAMPO_TIPO_RADIOPF, "1");//2 camadas
		h.clickById("frmCortina:j_idt722");//Atualizar lados
		Thread.sleep(500);
		h.clickById("frmCortina:btnSalvarCortina");
		Assert.assertNotNull(
			h.byCSS("[class='ui-growl-image ui-growl-image-info']"),
			"Não apresentou mensagem de sucesso ao cadastrar nova cortina no orçamento.");
		
		Integer[] camadas = new Integer[1];
		
		camadas[0] = 1;
		novoItemCortina(339,camadas);//Argola cromada / Camada 1
		
		novoItemCortina(5014,camadas);//Gancho / Camada 1
		
		novoItemCortina(4632,camadas,0,2);//Tapassol / Camada 1 / Argola /Fator 3X1
		
		camadas = new Integer[2];
		camadas[0] = 1;
		camadas[1] = 2;
		novoItemCortina(257,camadas);//Varao / Camada 1 e 2 
		
		camadas = new Integer[1];
		camadas[0] = 2;
		
		novoItemCortina(5307,camadas,1,3);//Cortinado / Camada 2 / Ilhós / Fato 4X1
		
		novoItemCortina(9011,camadas);//Entretela / Camada 2
		
		novoItemCortina(3750,camadas);//Ilhos / Camada 2
		
		novoItemCortinaQtd(2603,3,true);//Suporte Triplo / 3 qtd / Não recalcular
		
		h.clickById("frmCortina:btnAdicionarMO");//Não mostra mensagem
		Thread.sleep(1000);
		Assert.assertTrue(h.textPresent("Mão-de-obra Instalação"),"Não incluiu Mão de Obra");
		
		h.clickById("frmCortina:btnCalcularQtdes");
		Thread.sleep(1000);
		Assert.assertNotNull(
				h.byCSS("[class='ui-growl-image ui-growl-image-info']"),
				"Não apresentou mensagem de sucesso ao calcular qtds.");
		
		h.clickById("frmCortina:btnSalvarCortina");
		BaseActions.verificaCampoPreenchido(h, "frmCortina:j_idt706", BaseActions.CAMPO_TIPO_TEXT, "1.124,85", "Valor total errado para cortina VOIL (4X1) / TAPASSOL (3X1) NO ILHÓS - \"SALA VARÃO\"");
		
		BaseActions.executaJS(h, "$('#frmCortina\\\\:dlgCortina_title').parent().find('a').click();");//Fecha o item cortina
	}
	
	@Test(dependsOnMethods = { "orcamentoCortinaFormTestNovoOrcamento" })
	private void orcamentoCortinaFormTestCortinaVoilTapassolIlhosSVarao2() throws InterruptedException {
		h.clickByIdEnding("j_idt148:0:dtItens:btnNovoItem");
		Thread.sleep(1000);
		BaseActions.preencheCampo(h, "frmCortina:iDescricao", BaseActions.CAMPO_TIPO_TEXT, "VOIL (4X1) / TAPASSOL (3X1) NO ILHÓS - \"SALA VARÃO 2\"");
		BaseActions.preencheCampo(h, "frmCortina:iVaraoOuTrilho", BaseActions.CAMPO_TIPO_RADIOPF, "1");//Varao
		BaseActions.preencheCampo(h, "frmCortina:iLargura", BaseActions.CAMPO_TIPO_TEXT, "280");
		BaseActions.preencheCampo(h, "frmCortina:iAltura", BaseActions.CAMPO_TIPO_TEXT, "280");
		BaseActions.preencheCampo(h, "frmCortina:iCamada", BaseActions.CAMPO_TIPO_RADIOPF, "1");//2 camadas
		h.clickById("frmCortina:j_idt722");//Atualizar lados
		Thread.sleep(500);
		h.clickById("frmCortina:btnSalvarCortina");
		Assert.assertNotNull(
			h.byCSS("[class='ui-growl-image ui-growl-image-info']"),
			"Não apresentou mensagem de sucesso ao cadastrar nova cortina no orçamento.");
		
		Integer[] camadas = new Integer[1];
		
		camadas[0] = 1;
		novoItemCortina(339,camadas);//Argola cromada / Camada 1
		
		novoItemCortina(5014,camadas);//Gancho / Camada 1
		
		novoItemCortina(4632,camadas,0,2);//Tapassol / Camada 1 / Argola /Fator 3X1
		
		camadas = new Integer[2];
		camadas[0] = 1;
		camadas[1] = 2;
		novoItemCortina(257,camadas);//Varao / Camada 1 e 2 
		
		camadas = new Integer[1];
		camadas[0] = 2;
		
		novoItemCortina(5307,camadas,1,3);//Cortinado / Camada 2 / Ilhós / Fato 4X1
		
		novoItemCortina(9011,camadas);//Entretela / Camada 2
		
		novoItemCortina(3750,camadas);//Ilhos / Camada 2
		
		novoItemCortinaQtd(2603,3,true);//Suporte Triplo / 3 qtd / Não recalcular
		
		h.clickById("frmCortina:btnAdicionarMO");//Não mostra mensagem
		Thread.sleep(1000);
		Assert.assertTrue(h.textPresent("Mão-de-obra Instalação"),"Não incluiu Mão de Obra");
		
		h.clickById("frmCortina:btnCalcularQtdes");
		Thread.sleep(1000);
		Assert.assertNotNull(
				h.byCSS("[class='ui-growl-image ui-growl-image-info']"),
				"Não apresentou mensagem de sucesso ao calcular qtds.");
		
		h.clickById("frmCortina:btnSalvarCortina");
		BaseActions.verificaCampoPreenchido(h, "frmCortina:j_idt706", BaseActions.CAMPO_TIPO_TEXT, "1.109,70", "Valor total errado para cortina VOIL (4X1) / TAPASSOL (3X1) NO ILHÓS - \"SALA VARÃO\"");
		
		BaseActions.executaJS(h, "$('#frmCortina\\\\:dlgCortina_title').parent().find('a').click();");//Fecha o item cortina
	}
	
	@Test(dependsOnMethods = { "orcamentoCortinaFormTestNovoOrcamento" })
	private void orcamentoCortinaFormTestCortinaVoilTapassolIlhosSala3() throws InterruptedException {
		h.clickByIdEnding("j_idt148:0:dtItens:btnNovoItem");
		Thread.sleep(1000);
		BaseActions.preencheCampo(h, "frmCortina:iDescricao", BaseActions.CAMPO_TIPO_TEXT, "VOIL (4X1) / TAPASSOL (3X1) NO ILHÓS - \"SALA 3\"");
		BaseActions.preencheCampo(h, "frmCortina:iVaraoOuTrilho", BaseActions.CAMPO_TIPO_RADIOPF, "1");//Varao
		BaseActions.preencheCampo(h, "frmCortina:iLargura", BaseActions.CAMPO_TIPO_TEXT, "300");
		BaseActions.preencheCampo(h, "frmCortina:iAltura", BaseActions.CAMPO_TIPO_TEXT, "290");
		BaseActions.preencheCampo(h, "frmCortina:iCamada", BaseActions.CAMPO_TIPO_RADIOPF, "1");//2 camadas
		h.clickById("frmCortina:j_idt722");//Atualizar lados
		Thread.sleep(500);
		h.clickById("frmCortina:btnSalvarCortina");
		Assert.assertNotNull(
			h.byCSS("[class='ui-growl-image ui-growl-image-info']"),
			"Não apresentou mensagem de sucesso ao cadastrar nova cortina no orçamento.");
		
		Integer[] camadas = new Integer[1];
		
		camadas[0] = 1;
		novoItemCortina(339,camadas);//Argola cromada / Camada 1
		
		novoItemCortina(5014,camadas);//Gancho / Camada 1
		
		novoItemCortina(4632,camadas,0,2);//Tapassol / Camada 1 / Argola /Fator 3X1
		
		camadas = new Integer[2];
		camadas[0] = 1;
		camadas[1] = 2;
		novoItemCortina(257,camadas);//Varao / Camada 1 e 2 
		
		camadas = new Integer[1];
		camadas[0] = 2;
		
		novoItemCortina(5307,camadas,1,3);//Cortinado / Camada 2 / Ilhós / Fato 4X1
		
		novoItemCortina(9011,camadas);//Entretela / Camada 2
		
		novoItemCortina(3750,camadas);//Ilhos / Camada 2
		
		novoItemCortinaQtd(2603,3,true);//Suporte Triplo / 3 qtd / Não recalcular
		
		h.clickById("frmCortina:btnAdicionarMO");//Não mostra mensagem
		Thread.sleep(1000);
		Assert.assertTrue(h.textPresent("Mão-de-obra Instalação"),"Não incluiu Mão de Obra");
		
		h.clickById("frmCortina:btnCalcularQtdes");
		Thread.sleep(1000);
		Assert.assertNotNull(
				h.byCSS("[class='ui-growl-image ui-growl-image-info']"),
				"Não apresentou mensagem de sucesso ao calcular qtds.");
		
		h.clickById("frmCortina:btnSalvarCortina");
		BaseActions.verificaCampoPreenchido(h, "frmCortina:j_idt706", BaseActions.CAMPO_TIPO_TEXT, "1.140,82", "Valor total errado para cortina VOIL (4X1) / TAPASSOL (3X1) NO ILHÓS - \"SALA 3\"");
		
		BaseActions.executaJS(h, "$('#frmCortina\\\\:dlgCortina_title').parent().find('a').click();");//Fecha o item cortina
	}
	
	@Test(dependsOnMethods = { "orcamentoCortinaFormTestNovoOrcamento" })
	private void orcamentoCortinaFormTestCortinaVoilTapassolIlhosSala4() throws InterruptedException {
		h.clickByIdEnding("j_idt148:0:dtItens:btnNovoItem");
		Thread.sleep(1000);
		BaseActions.preencheCampo(h, "frmCortina:iDescricao", BaseActions.CAMPO_TIPO_TEXT, "VOIL (4X1) / TAPASSOL (3X1) NO ILHÓS - \"SALA 4\"");
		BaseActions.preencheCampo(h, "frmCortina:iVaraoOuTrilho", BaseActions.CAMPO_TIPO_RADIOPF, "1");//Varao
		BaseActions.preencheCampo(h, "frmCortina:iLargura", BaseActions.CAMPO_TIPO_TEXT, "310");
		BaseActions.preencheCampo(h, "frmCortina:iAltura", BaseActions.CAMPO_TIPO_TEXT, "290");
		BaseActions.preencheCampo(h, "frmCortina:iCamada", BaseActions.CAMPO_TIPO_RADIOPF, "1");//2 camadas
		h.clickById("frmCortina:j_idt722");//Atualizar lados
		Thread.sleep(500);
		h.clickById("frmCortina:btnSalvarCortina");
		Assert.assertNotNull(
			h.byCSS("[class='ui-growl-image ui-growl-image-info']"),
			"Não apresentou mensagem de sucesso ao cadastrar nova cortina no orçamento.");
		
		Integer[] camadas = new Integer[1];
		
		camadas[0] = 1;
		novoItemCortina(339,camadas);//Argola cromada / Camada 1
		
		novoItemCortina(5014,camadas);//Gancho / Camada 1
		
		novoItemCortina(4632,camadas,0,2);//Tapassol / Camada 1 / Argola /Fator 3X1
		
		camadas = new Integer[2];
		camadas[0] = 1;
		camadas[1] = 2;
		novoItemCortina(257,camadas);//Varao / Camada 1 e 2 
		
		camadas = new Integer[1];
		camadas[0] = 2;
		
		novoItemCortina(5307,camadas,1,3);//Cortinado / Camada 2 / Ilhós / Fato 4X1
		
		novoItemCortina(9011,camadas);//Entretela / Camada 2
		
		novoItemCortina(3750,camadas);//Ilhos / Camada 2
		
		novoItemCortinaQtd(2603,3,true);//Suporte Triplo / 3 qtd / Não recalcular
		
		h.clickById("frmCortina:btnAdicionarMO");//Não mostra mensagem
		Thread.sleep(1000);
		Assert.assertTrue(h.textPresent("Mão-de-obra Instalação"),"Não incluiu Mão de Obra");
		
		h.clickById("frmCortina:btnCalcularQtdes");
		Thread.sleep(1000);
		Assert.assertNotNull(
				h.byCSS("[class='ui-growl-image ui-growl-image-info']"),
				"Não apresentou mensagem de sucesso ao calcular qtds.");
		
		h.clickById("frmCortina:btnSalvarCortina");
		BaseActions.verificaCampoPreenchido(h, "frmCortina:j_idt706", BaseActions.CAMPO_TIPO_TEXT, "1.144,36", "Valor total errado para cortina VOIL (4X1) / TAPASSOL (3X1) NO ILHÓS - \"SALA 4\"");
		
		BaseActions.executaJS(h, "$('#frmCortina\\\\:dlgCortina_title').parent().find('a').click();");//Fecha o item cortina
	}
	
	@Test(dependsOnMethods = { "orcamentoCortinaFormTestNovoOrcamento" })
	private void orcamentoCortinaFormTestCortinaVoilTapassolIlhosSala5() throws InterruptedException {
		h.clickByIdEnding("j_idt148:0:dtItens:btnNovoItem");
		Thread.sleep(1000);
		BaseActions.preencheCampo(h, "frmCortina:iDescricao", BaseActions.CAMPO_TIPO_TEXT, "VOIL (4X1) / TAPASSOL (3X1) NO ILHÓS - \"SALA 5\"");
		BaseActions.preencheCampo(h, "frmCortina:iVaraoOuTrilho", BaseActions.CAMPO_TIPO_RADIOPF, "1");//Varao
		BaseActions.preencheCampo(h, "frmCortina:iLargura", BaseActions.CAMPO_TIPO_TEXT, "326");
		BaseActions.preencheCampo(h, "frmCortina:iAltura", BaseActions.CAMPO_TIPO_TEXT, "255");
		BaseActions.preencheCampo(h, "frmCortina:iCamada", BaseActions.CAMPO_TIPO_RADIOPF, "1");//2 camadas
		h.clickById("frmCortina:j_idt722");//Atualizar lados
		Thread.sleep(500);
		h.clickById("frmCortina:btnSalvarCortina");
		Assert.assertNotNull(
			h.byCSS("[class='ui-growl-image ui-growl-image-info']"),
			"Não apresentou mensagem de sucesso ao cadastrar nova cortina no orçamento.");
		
		Integer[] camadas = new Integer[1];
		
		camadas[0] = 1;
		novoItemCortina(339,camadas);//Argola cromada / Camada 1
		
		novoItemCortina(5014,camadas);//Gancho / Camada 1
		
		novoItemCortina(4632,camadas,0,2);//Tapassol / Camada 1 / Argola /Fator 3X1
		
		camadas = new Integer[2];
		camadas[0] = 1;
		camadas[1] = 2;
		novoItemCortina(257,camadas);//Varao / Camada 1 e 2 
		
		camadas = new Integer[1];
		camadas[0] = 2;
		
		novoItemCortina(5307,camadas,1,3);//Cortinado / Camada 2 / Ilhós / Fato 4X1
		
		novoItemCortina(9011,camadas);//Entretela / Camada 2
		
		novoItemCortina(3750,camadas);//Ilhos / Camada 2
		
		novoItemCortinaQtd(2603,3,true);//Suporte Triplo / 3 qtd / Não recalcular
		
		h.clickById("frmCortina:btnAdicionarMO");//Não mostra mensagem
		Thread.sleep(1000);
		Assert.assertTrue(h.textPresent("Mão-de-obra Instalação"),"Não incluiu Mão de Obra");
		
		h.clickById("frmCortina:btnCalcularQtdes");
		Thread.sleep(1000);
		Assert.assertNotNull(
				h.byCSS("[class='ui-growl-image ui-growl-image-info']"),
				"Não apresentou mensagem de sucesso ao calcular qtds.");
		
		h.clickById("frmCortina:btnSalvarCortina");
		BaseActions.verificaCampoPreenchido(h, "frmCortina:j_idt706", BaseActions.CAMPO_TIPO_TEXT, "1.144,36", "Valor total errado para cortina VOIL (4X1) / TAPASSOL (3X1) NO ILHÓS - \"SALA 5\"");
		
		BaseActions.executaJS(h, "$('#frmCortina\\\\:dlgCortina_title').parent().find('a').click();");//Fecha o item cortina
	}
	
	@Test(dependsOnMethods = { "orcamentoCortinaFormTestNovoOrcamento" })
	private void orcamentoCortinaFormTestCortinaVoilTapassolTrilhoEscada() throws InterruptedException {
		h.clickByIdEnding("j_idt148:0:dtItens:btnNovoItem");
		Thread.sleep(1000);
		BaseActions.preencheCampo(h, "frmCortina:iDescricao", BaseActions.CAMPO_TIPO_TEXT, "VOIL (4X1) / TAPASSOL (3X1) NO TRILHO – \"ESCADA\"");
		BaseActions.preencheCampo(h, "frmCortina:iVaraoOuTrilho", BaseActions.CAMPO_TIPO_RADIOPF, "0");//Trilho
		BaseActions.preencheCampo(h, "frmCortina:iLargura", BaseActions.CAMPO_TIPO_TEXT, "230");
		BaseActions.preencheCampo(h, "frmCortina:iAltura", BaseActions.CAMPO_TIPO_TEXT, "487");
		BaseActions.preencheCampo(h, "frmCortina:iCamada", BaseActions.CAMPO_TIPO_RADIOPF, "1");//2 camadas
		h.clickById("frmCortina:j_idt722");//Atualizar lados
		Thread.sleep(500);
		h.clickById("frmCortina:btnSalvarCortina");
		Assert.assertNotNull(
			h.byCSS("[class='ui-growl-image ui-growl-image-info']"),
			"Não apresentou mensagem de sucesso ao cadastrar nova cortina no orçamento.");
		
		Integer[] camadas = new Integer[2];
		
		camadas[0] = 1;
		camadas[1] = 2;
		
		novoItemCortina(8999,camadas);//Rodízio / Camada 1 e 2
		
		camadas = new Integer[1];
		camadas[0] = 1;
		novoItemCortina(4632,camadas,2,2);//Tapassol / Camada 1 / Rodizio / 3X1  

		novoItemCortinaQtd(9001,camadas,4,true);//Terminal / Camada 1 / 4 qtd / Nao alterar qtd
		
		novoItemCortina(6176,camadas);//Trilho / Camada 1
		
		camadas = new Integer[1];
		camadas[0] = 2;
		
		novoItemCortina(5307,camadas,2,3);//Cortinado / Camada 2 / Rodízio / Fato 4X1
		
		h.clickById("frmCortina:btnAdicionarMO");//Não mostra mensagem
		Thread.sleep(1000);
		Assert.assertTrue(h.textPresent("Mão-de-obra Instalação"),"Não incluiu Mão de Obra");
		
		h.clickById("frmCortina:btnCalcularQtdes");
		Thread.sleep(1000);
		Assert.assertNotNull(
				h.byCSS("[class='ui-growl-image ui-growl-image-info']"),
				"Não apresentou mensagem de sucesso ao calcular qtds.");
		
		h.clickById("frmCortina:btnSalvarCortina");
		BaseActions.verificaCampoPreenchido(h, "frmCortina:j_idt706", BaseActions.CAMPO_TIPO_TEXT, "890,85", "Valor total errado para cortina VOIL (4X1) / TAPASSOL (3X1) NO TRILHO – \"ESCADA\"");
		
		BaseActions.executaJS(h, "$('#frmCortina\\\\:dlgCortina_title').parent().find('a').click();");//Fecha o item cortina
	}
	
	@Test(dependsOnMethods = { "orcamentoCortinaFormTestNovoOrcamento" })
	private void orcamentoCortinaFormTestCortinaVoilBlackoutTrilho() throws InterruptedException {
		h.clickByIdEnding("j_idt148:0:dtItens:btnNovoItem");
		Thread.sleep(1000);
		BaseActions.preencheCampo(h, "frmCortina:iDescricao", BaseActions.CAMPO_TIPO_TEXT, "VOIL (4X1) / BLECAUTE (2X1) NO TRILHO - \"QUARTO GRANDE\"");
		BaseActions.preencheCampo(h, "frmCortina:iVaraoOuTrilho", BaseActions.CAMPO_TIPO_RADIOPF, "0");//Trilho
		BaseActions.preencheCampo(h, "frmCortina:iLargura", BaseActions.CAMPO_TIPO_TEXT, "500");
		BaseActions.preencheCampo(h, "frmCortina:iAltura", BaseActions.CAMPO_TIPO_TEXT, "283");
		BaseActions.preencheCampo(h, "frmCortina:iCamada", BaseActions.CAMPO_TIPO_RADIOPF, "1");//2 camadas
		h.clickById("frmCortina:j_idt722");//Atualizar lados
		Thread.sleep(500);
		h.clickById("frmCortina:btnSalvarCortina");
		Assert.assertNotNull(
			h.byCSS("[class='ui-growl-image ui-growl-image-info']"),
			"Não apresentou mensagem de sucesso ao cadastrar nova cortina no orçamento.");
		
		Integer[] camadas = new Integer[2];
		
		camadas[0] = 1;
		camadas[1] = 2;
		
		novoItemCortina(8999,camadas);//Rodízio / Camada 1 e 2
		
		camadas = new Integer[1];
		camadas[0] = 1;
		novoItemCortina(484,camadas,2,1);//Tapassol / Camada 1 / Rodizio / 2X1  

		novoItemCortinaQtd(9001,camadas,4,true);//Terminal / Camada 1 / 4 qtd / Nao alterar qtd
		
		novoItemCortina(6176,camadas);//Trilho / Camada 1
		
		camadas = new Integer[1];
		camadas[0] = 2;
		
		novoItemCortina(5307,camadas,2,3);//Cortinado / Camada 2 / Rodízio / Fato 4X1
		
		h.clickById("frmCortina:btnAdicionarMO");//Não mostra mensagem
		Thread.sleep(1000);
		Assert.assertTrue(h.textPresent("Mão-de-obra Instalação"),"Não incluiu Mão de Obra");
		
		h.clickById("frmCortina:btnCalcularQtdes");
		Thread.sleep(1000);
		Assert.assertNotNull(
				h.byCSS("[class='ui-growl-image ui-growl-image-info']"),
				"Não apresentou mensagem de sucesso ao calcular qtds.");
		
		h.clickById("frmCortina:btnSalvarCortina");
		BaseActions.verificaCampoPreenchido(h, "frmCortina:j_idt706", BaseActions.CAMPO_TIPO_TEXT, "1.193,47", "Valor total errado para cortina VOIL (4X1) / BLECAUTE (2X1) NO TRILHO - \"QUARTO GRANDE\"");
		
		BaseActions.executaJS(h, "$('#frmCortina\\\\:dlgCortina_title').parent().find('a').click();");//Fecha o item cortina
	}
	
	@Test(dependsOnMethods = { "orcamentoCortinaFormTestNovoOrcamento" })
	private void orcamentoCortinaFormTestCortinaVoilTapassolTrilho() throws InterruptedException {
		h.clickByIdEnding("j_idt148:0:dtItens:btnNovoItem");
		Thread.sleep(1000);
		BaseActions.preencheCampo(h, "frmCortina:iDescricao", BaseActions.CAMPO_TIPO_TEXT, "VOIL (4X1) / TAPASSOL NO TRILHO - \"MENOR 1\"");
		BaseActions.preencheCampo(h, "frmCortina:iVaraoOuTrilho", BaseActions.CAMPO_TIPO_RADIOPF, "0");//Trilho
		BaseActions.preencheCampo(h, "frmCortina:iLargura", BaseActions.CAMPO_TIPO_TEXT, "090");
		BaseActions.preencheCampo(h, "frmCortina:iAltura", BaseActions.CAMPO_TIPO_TEXT, "483");
		BaseActions.preencheCampo(h, "frmCortina:iCamada", BaseActions.CAMPO_TIPO_RADIOPF, "1");//2 camadas
		h.clickById("frmCortina:j_idt722");//Atualizar lados
		Thread.sleep(500);
		h.clickById("frmCortina:btnSalvarCortina");
		Assert.assertNotNull(
			h.byCSS("[class='ui-growl-image ui-growl-image-info']"),
			"Não apresentou mensagem de sucesso ao cadastrar nova cortina no orçamento.");
		
		Integer[] camadas = new Integer[2];
		
		camadas[0] = 1;
		camadas[1] = 2;
		
		novoItemCortina(8999,camadas);//Rodízio / Camada 1 e 2
		
		camadas = new Integer[1];
		camadas[0] = 1;
		novoItemCortina(4632,camadas,2,1);//Tapassol / Camada 1 / Rodizio / 2X1  

		novoItemCortinaQtd(9001,camadas,4,true);//Terminal / Camada 1 / 4 qtd / Nao alterar qtd
		
		novoItemCortina(6176,camadas);//Trilho / Camada 1
		
		camadas = new Integer[1];
		camadas[0] = 2;
		
		novoItemCortina(5307,camadas,2,0);//Cortinado / Camada 2 / Rodízio / Fato 1X1
		
		h.clickById("frmCortina:btnAdicionarMO");//Não mostra mensagem
		Thread.sleep(1000);
		Assert.assertTrue(h.textPresent("Mão-de-obra Instalação"),"Não incluiu Mão de Obra");
		
		h.clickById("frmCortina:btnCalcularQtdes");
		Thread.sleep(1000);
		Assert.assertNotNull(
				h.byCSS("[class='ui-growl-image ui-growl-image-info']"),
				"Não apresentou mensagem de sucesso ao calcular qtds.");
		
		h.clickById("frmCortina:btnSalvarCortina");
		BaseActions.verificaCampoPreenchido(h, "frmCortina:j_idt706", BaseActions.CAMPO_TIPO_TEXT, "358,36", "Valor total errado para cortina VOIL (4X1) / TAPASSOL NO TRILHO - \"MENOR 1\"");
		
		BaseActions.executaJS(h, "$('#frmCortina\\\\:dlgCortina_title').parent().find('a').click();");//Fecha o item cortina
	}
	
	@Test(dependsOnMethods = { "orcamentoCortinaFormTestNovoOrcamento" })
	private void orcamentoCortinaFormTestCortinaVoilIlhos() throws InterruptedException {
		h.clickByIdEnding("j_idt148:0:dtItens:btnNovoItem");
		Thread.sleep(1000);
		BaseActions.preencheCampo(h, "frmCortina:iDescricao", BaseActions.CAMPO_TIPO_TEXT, "VOIL (1X1) NO ILHÓS");
		BaseActions.preencheCampo(h, "frmCortina:iVaraoOuTrilho", BaseActions.CAMPO_TIPO_RADIOPF, "1");//Varão
		BaseActions.preencheCampo(h, "frmCortina:iLargura", BaseActions.CAMPO_TIPO_TEXT, "360");
		BaseActions.preencheCampo(h, "frmCortina:iAltura", BaseActions.CAMPO_TIPO_TEXT, "120");
		BaseActions.preencheCampo(h, "frmCortina:iCamada", BaseActions.CAMPO_TIPO_RADIOPF, "0");//1 camada
		h.clickById("frmCortina:j_idt722");//Atualizar lados
		Thread.sleep(500);
		h.clickById("frmCortina:btnSalvarCortina");
		Assert.assertNotNull(
			h.byCSS("[class='ui-growl-image ui-growl-image-info']"),
			"Não apresentou mensagem de sucesso ao cadastrar nova cortina no orçamento.");
		
		Integer[] camadas = new Integer[2];
		
		camadas[0] = 1;
		novoItemCortina(5307,camadas,1,0);//Cortinado / Camada 1 / Ilhos / 1X1
		
		novoItemCortina(9011,camadas);//Entretela / Camada 1  

		novoItemCortina(3750,camadas);//Ilhos cromado / Camada 1
		
		h.clickById("frmCortina:btnAdicionarMO");//Não mostra mensagem
		Thread.sleep(1000);
		Assert.assertTrue(h.textPresent("Mão-de-obra Instalação"),"Não incluiu Mão de Obra");
		
		h.clickById("frmCortina:btnCalcularQtdes");
		Thread.sleep(1000);
		Assert.assertNotNull(
				h.byCSS("[class='ui-growl-image ui-growl-image-info']"),
				"Não apresentou mensagem de sucesso ao calcular qtds.");
		
		h.clickById("frmCortina:btnSalvarCortina");
		BaseActions.verificaCampoPreenchido(h, "frmCortina:j_idt706", BaseActions.CAMPO_TIPO_TEXT, "188,18", "Valor total errado para cortina VOIL (1X1) NO ILHÓS");
		
		BaseActions.executaJS(h, "$('#frmCortina\\\\:dlgCortina_title').parent().find('a').click();");//Fecha o item cortina
	}
	
	@Test(dependsOnMethods = { "orcamentoCortinaFormTestNovoOrcamento" })
	private void orcamentoCortinaFormTestCortinaVoilIlhos2() throws InterruptedException {
		h.clickByIdEnding("j_idt148:0:dtItens:btnNovoItem");
		Thread.sleep(1000);
		BaseActions.preencheCampo(h, "frmCortina:iDescricao", BaseActions.CAMPO_TIPO_TEXT, "VOIL (1X1) NO ILHÓS 2");
		BaseActions.preencheCampo(h, "frmCortina:iVaraoOuTrilho", BaseActions.CAMPO_TIPO_RADIOPF, "1");//Varão
		BaseActions.preencheCampo(h, "frmCortina:iLargura", BaseActions.CAMPO_TIPO_TEXT, "470");
		BaseActions.preencheCampo(h, "frmCortina:iAltura", BaseActions.CAMPO_TIPO_TEXT, "220");
		BaseActions.preencheCampo(h, "frmCortina:iCamada", BaseActions.CAMPO_TIPO_RADIOPF, "0");//1 camada
		h.clickById("frmCortina:j_idt722");//Atualizar lados
		Thread.sleep(500);
		h.clickById("frmCortina:btnSalvarCortina");
		Assert.assertNotNull(
			h.byCSS("[class='ui-growl-image ui-growl-image-info']"),
			"Não apresentou mensagem de sucesso ao cadastrar nova cortina no orçamento.");
		
		Integer[] camadas = new Integer[2];
		
		camadas[0] = 1;
		novoItemCortina(5307,camadas,1,0);//Cortinado / Camada 1 / Ilhos / 1X1
		
		novoItemCortina(9011,camadas);//Entretela / Camada 1  

		novoItemCortina(3750,camadas);//Ilhos cromado / Camada 1
		
		h.clickById("frmCortina:btnAdicionarMO");//Não mostra mensagem
		Thread.sleep(1000);
		Assert.assertTrue(h.textPresent("Mão-de-obra Instalação"),"Não incluiu Mão de Obra");
		
		h.clickById("frmCortina:btnCalcularQtdes");
		Thread.sleep(1000);
		Assert.assertNotNull(
				h.byCSS("[class='ui-growl-image ui-growl-image-info']"),
				"Não apresentou mensagem de sucesso ao calcular qtds.");
		
		h.clickById("frmCortina:btnSalvarCortina");
		BaseActions.verificaCampoPreenchido(h, "frmCortina:j_idt706", BaseActions.CAMPO_TIPO_TEXT, "560,96", "Valor total errado para cortina VOIL (1X1) NO ILHÓS 2");
		
		BaseActions.executaJS(h, "$('#frmCortina\\\\:dlgCortina_title').parent().find('a').click();");//Fecha o item cortina
	}
	
	@Test(dependsOnMethods = { "orcamentoCortinaFormTestNovoOrcamento" })
	private void orcamentoCortinaFormTestListaDeletaOrcamento() throws InterruptedException {
		h.getDriver().get(BaseActions.address + page_list);
		Thread.sleep(3000);
		
		BaseActions.formListPesquisa(h,"A/C RODRIGO JUSTUS POLETTE - TESTER");
		
		Assert.assertTrue(h.textPresent("Total de Registros: 1."),"Não achou 1 orçamento de cortina.");
		
		//Primeiro botão de exclusão
		h.clickByIdEnding(":0:btnDeletar");
		h.clickByIdEnding("btnCdlgGlobal_sim");
		
		Thread.sleep(1000);
		Assert.assertTrue(h.textPresent("Total de Registros: 0."),"Exclusão não excluiu do grid [prcamentoCortinaList.jsf].");
		
		BaseActions.formListPesquisa(h,"");
	}
	
	@AfterSuite
	public void shutdown() {
		h.getDriver().quit();
	}
}
