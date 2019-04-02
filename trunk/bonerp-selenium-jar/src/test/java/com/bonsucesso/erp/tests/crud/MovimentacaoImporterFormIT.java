package com.bonsucesso.erp.tests.crud;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.bonsucesso.erp.tests.base.BaseActions;
import com.ocabit.utils.tests.selenium.SeleniumHelper;
import com.ocabit.utils.tests.selenium.SeleniumHelper.Browser;

public class MovimentacaoImporterFormIT {
	protected static Logger logger = Logger.getLogger(FormListIT.class);
	
	private static final String page = "/pages/erp/financeiro/movimentacaoImporterForm.jsf";
	private static final String page_list_mov = "/pages/erp/financeiro/movimentacaoList_listagem.jsf";
	
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
	
	@Test(dependsOnMethods = { "login" })
	public void movimentacaoImporterFormTestImportacao() throws InterruptedException {
		h.getDriver().get(BaseActions.address + page);
		Thread.sleep(3000);
		
		BaseActions.preencheCampo(h, "iLinhas", BaseActions.CAMPO_TIPO_TEXT, 
			"01/07/2016                 118 Cobrança de I.O.F.           391.100.702          12,345 D" + Keys.ENTER + 
			"02/07/2016                 Outra movimentação                                   432,10 D");
		
		BaseActions.preencheCampo(h, "iTipoExtrato_items", BaseActions.CAMPO_TIPO_COMBOPF, "EXTRATO SIMPLES");
		BaseActions.preencheCampo(h, "iCarteiraExtrato_items", BaseActions.CAMPO_TIPO_COMBOPF, "1 - ESCRITÓRIO");
		BaseActions.clickCheckPF(h, "iGerarSemRegras_input");
		h.clickByIdEnding("frm:btnImportar");
		Thread.sleep(1500);
		
		//1 botão de editar
		Assert.assertEquals(h.qtdByIdEnding(":0:btnEditar_button"),1,"Não apresentou 1 registros ao importar 1 linha correta e 1 errada");
		
		h.byIdEnding("iLinhas").clear();
		
		BaseActions.preencheCampo(h, "iLinhas", BaseActions.CAMPO_TIPO_TEXT, 
				"01/07/2016                 118 Cobrança de I.O.F.           391.100.702          12,34 D" + Keys.ENTER + 
				"02/07/2016                 Outra movimentação                                   432,10 D");
		
		h.clickByIdEnding("frm:btnImportar");//Botao Importar
		Thread.sleep(1500);
		
		//2 botoes de editar
		Assert.assertEquals(h.qtdByIdEnding(":0:btnEditar_button"),1,"Não apresentou 1 registros ao importar 2 linhas");
		Assert.assertEquals(h.qtdByIdEnding(":1:btnEditar_button"),1,"Não apresentou 2 registros ao importar 2 linhas");
	}
	
	@Test(dependsOnMethods = { "movimentacaoImporterFormTestImportacao" })
	public void movimentacaoImporterFormTestAlterarEmLote() throws InterruptedException {
		h.clickByIdEnding("frm:btnImportar");//Botao importar, pois já excluiu os registros
		
		h.clickByIdEnding("frm:btnIniAlterarEmLote");//Alterar em lote
		Assert.assertNotNull(
			h.byCSS("[class='ui-growl-image ui-growl-image-error']"),
				"Não apresentou mensagem de erro ao tentar Alterar em lote com apenas 1 registro selecionado.");
		
		BaseActions.executaJS(h, "$(\"input:checkbox[name='frm\\\\:dtList_checkbox']\").eq(1).click();");//Primeiro check da listagem
		BaseActions.executaJS(h, "$(\"input:checkbox[name='frm\\\\:dtList_checkbox']\").eq(2).click();");//Segundo check da listagem
		h.clickByIdEnding("frm:btnIniAlterarEmLote");//Alterar em lote
		
		BaseActions.preencheCampo(h, "frmMovimentacao_lote:iCarteira_label", BaseActions.CAMPO_TIPO_COMBOPF, "2 - CAIXA A VISTA");
		BaseActions.preencheCampo(h, "frmMovimentacao_lote:iModoMoviment_label", BaseActions.CAMPO_TIPO_COMBOPF, "2 - DÉBITO AUTOMÁTICO");
		
		h.clickById("frmMovimentacao_lote:btnAlterarLote");
		h.clickById("j_idt8:btnCdlgGlobal_sim");
		Assert.assertNotNull(
			h.byCSS("[class='ui-growl-image ui-growl-image-info']"),
				"Não apresentou mensagem de sucesso ao salvar alteração em lote.");
		Thread.sleep(3000);//Para a mensagem sumir da tela
	}
	
	@Test(dependsOnMethods = { "movimentacaoImporterFormTestAlterarEmLote" })
	public void movimentacaoImporterFormTestDeletarEmLote() throws InterruptedException {
		BaseActions.executaJS(h, "$(\"input:checkbox[name='frm\\\\:dtList_checkbox']\").eq(1).click();");//Primeiro check da listagem
		BaseActions.executaJS(h, "$(\"input:checkbox[name='frm\\\\:dtList_checkbox']\").eq(2).click();");//Segundo check da listagem
		h.clickByIdEnding("frm:btnIniAlterarEmLote");//Alterar em lote
		
		h.clickById("frmMovimentacao_lote:btnDeletarLote");
		h.clickById("j_idt8:btnCdlgGlobal_sim");
		Assert.assertNotNull(
			h.byCSS("[class='ui-growl-image ui-growl-image-info']"),
				"Não apresentou mensagem de sucesso ao deletar em lote.");
		
		h.clickByIdEnding("frm:btnImportar");//Botao importar, pois já excluiu os registros
		Thread.sleep(3000);//Para a mensagem sumir da tela
	}
	
	@Test(dependsOnMethods = { "movimentacaoImporterFormTestImportacao" })
	public void movimentacaoImporterFormTestEdicao() throws InterruptedException {
		//Checa valores do primeiro registro
		h.clickByIdEnding(":0:btnEditar_button");
		Thread.sleep(1000);
		BaseActions.verificaCampoPreenchido(h, "GERAL:iCarteira_label", BaseActions.CAMPO_TIPO_COMBOPF, "1 - ESCRITÓRIO","Edição, reg 1 - carteira não preenchida");
		BaseActions.verificaCampoPreenchido(h, "GERAL:iDtMoviment_input", BaseActions.CAMPO_TIPO_DATA, "01/07/2016", "Edição, reg 1 - data movimentação errada");
		BaseActions.preencheCampo(h, "GERAL:iModoMoviment_label", BaseActions.CAMPO_TIPO_COMBOPF, "1 - EM ESPÉCIE");
		BaseActions.verificaCampoPreenchido(h, "GERAL:iDtVencto_input", BaseActions.CAMPO_TIPO_DATA, "01/07/2016", "Edição, reg 1 - data vencimento errada");
		BaseActions.verificaCampoPreenchido(h, "GERAL:iDtVenctoEfetiva_input", BaseActions.CAMPO_TIPO_DATA, "01/07/2016", "Edição, reg 1 - data vencimento efetiva errada");
		BaseActions.verificaCampoPreenchido(h, "GERAL:iDtPagto_input", BaseActions.CAMPO_TIPO_DATA, "01/07/2016", "Edição, reg 1 - data pagamento errada");
		BaseActions.verificaCampoPreenchido(h, "GERAL:iPessoaCadastro", BaseActions.CAMPO_TIPO_RADIO, "2", "Edição, reg 1 - sacado/cedente errado");
		BaseActions.verificaCampoPreenchido(h, "GERAL:iDescricao", BaseActions.CAMPO_TIPO_TEXT,"118 Cobrança de I.O.F. 391.100.702 D", "Edição, reg 1, descrição errada");
		BaseActions.verificaCampoPreenchido(h, "GERAL:iValor", BaseActions.CAMPO_TIPO_TEXT,"12,34", "Edição, reg 1, valor errado");
		BaseActions.verificaCampoPreenchido(h, "GERAL:iDescontos", BaseActions.CAMPO_TIPO_TEXT,"", "Edição, reg 1, valor desconto errado");
		BaseActions.verificaCampoPreenchido(h, "GERAL:iAcrescimos", BaseActions.CAMPO_TIPO_TEXT,"", "Edição, reg 1, valor acrescimo errado");
		BaseActions.verificaCampoPreenchido(h, "GERAL:iValorTotal", BaseActions.CAMPO_TIPO_TEXT,"12,34", "Edição, reg 1, valor total errado");
		
		h.clickByIdEnding("GERAL:btnSalvar");
		Assert.assertNotNull(
				h.byCSS("[class='ui-growl-image ui-growl-image-info']"),
					"Não apresentou mensagem de sucesso ao salvar.");
		
		//Checa valores do segundo registro
		h.clickByIdEnding(":1:btnEditar_button");
		Thread.sleep(1000);
		BaseActions.verificaCampoPreenchido(h, "GERAL:iCarteira_label", BaseActions.CAMPO_TIPO_COMBOPF, "1 - ESCRITÓRIO","Edição, reg 2 - carteira não preenchida");
		BaseActions.preencheCampo(h, "GERAL:iModoMoviment_label", BaseActions.CAMPO_TIPO_COMBOPF, "1 - EM ESPÉCIE");
		BaseActions.verificaCampoPreenchido(h, "GERAL:iDtMoviment_input", BaseActions.CAMPO_TIPO_DATA, "02/07/2016", "Edição, reg 2 - data movimentação errada");
		BaseActions.verificaCampoPreenchido(h, "GERAL:iDtVencto_input", BaseActions.CAMPO_TIPO_DATA, "02/07/2016", "Edição, reg 2 - data vencimento errada");
		BaseActions.verificaCampoPreenchido(h, "GERAL:iDtVenctoEfetiva_input", BaseActions.CAMPO_TIPO_DATA, "02/07/2016", "Edição, reg 2 - data vencimento efetiva errada");
		BaseActions.verificaCampoPreenchido(h, "GERAL:iDtPagto_input", BaseActions.CAMPO_TIPO_DATA, "02/07/2016", "Edição, reg 2 - data pagamento errada");
		BaseActions.verificaCampoPreenchido(h, "GERAL:iPessoaCadastro", BaseActions.CAMPO_TIPO_RADIO, "2", "Edição, reg 2 - sacado/cedente errado");
		BaseActions.verificaCampoPreenchido(h, "GERAL:iDescricao", BaseActions.CAMPO_TIPO_TEXT,"Outra movimentaçao D", "Edição, reg 2, descrição errada");
		BaseActions.verificaCampoPreenchido(h, "GERAL:iValor", BaseActions.CAMPO_TIPO_TEXT,"432,10", "Edição, reg 2, valor errado");
		BaseActions.verificaCampoPreenchido(h, "GERAL:iDescontos", BaseActions.CAMPO_TIPO_TEXT,"", "Edição, reg 2, valor desconto errado");
		BaseActions.verificaCampoPreenchido(h, "GERAL:iAcrescimos", BaseActions.CAMPO_TIPO_TEXT,"", "Edição, reg 2, valor acrescimo errado");
		BaseActions.verificaCampoPreenchido(h, "GERAL:iValorTotal", BaseActions.CAMPO_TIPO_TEXT,"432,10", "Edição, reg 2, valor total errado");
		
		h.clickByIdEnding("GERAL:btnSalvar");
		Assert.assertNotNull(
				h.byCSS("[class='ui-growl-image ui-growl-image-info']"),
					"Não apresentou mensagem de sucesso ao salvar.");
		Thread.sleep(3000);//Para a mensagem sumir da tela
	}
	
	@Test(dependsOnMethods = { "movimentacaoImporterFormTestImportacao" })
	public void movimentacaoImporterFormTestEdicaoCheque() throws InterruptedException {
		//Checa editar cheque próprio - Registro 1
		h.clickByIdEnding(":0:btnEditar_menuButton");//Seta a direita
		h.clickByIdEnding(":0:btnEditarChequeProprio");
		Thread.sleep(1000);
		
		h.clickByIdEnding("CHEQUE_PROPRIO:btnSalvar");
		Assert.assertNotNull(
				h.byCSS("[class='ui-growl-image ui-growl-image-error']"),
					"Não apresentou mensagem de erro ao tentar salvar Cheque Próprio sem Carteira.");
		
		//Data pagamento some ao escolher modo moviment cheque
		BaseActions.verificaCampoPreenchido(h, "CHEQUE_PROPRIO:iModoMoviment", BaseActions.CAMPO_TIPO_TEXT, "CHEQUE PRÓPRIO", "Edição Cheque, reg 1 - Modo moviment não alterou para Cheque próprio");
		BaseActions.verificaCampoPreenchido(h, "CHEQUE_PROPRIO:iCarteira_label", BaseActions.CAMPO_TIPO_COMBOPF, "","Edição Cheque, reg 1 - carteira ficou preenchida");
		BaseActions.verificaCampoPreenchido(h, "CHEQUE_PROPRIO:iModoMoviment_label", BaseActions.CAMPO_TIPO_COMBOPF, "","Edição Cheque, reg 1 - Modo moviment preenchido");
		BaseActions.verificaCampoPreenchido(h, "CHEQUE_PROPRIO:iCategoriaCodigo", BaseActions.CAMPO_TIPO_TEXT, "2", "Edição Cheque, reg 1 - Categoria código não preenchida");
		BaseActions.verificaCampoPreenchido(h, "CHEQUE_PROPRIO:iCategoria_input", BaseActions.CAMPO_TIPO_TEXT, "SAÍDAS", "Edição Cheque, reg 1 - Categoria desc não preenchida");
		BaseActions.verificaCampoPreenchido(h, "CHEQUE_PROPRIO:iDtMoviment_input", BaseActions.CAMPO_TIPO_DATA, "01/07/2016", "Edição Cheque, reg 1 - data movimentação errada");
		BaseActions.verificaCampoPreenchido(h, "CHEQUE_PROPRIO:iDtVencto_input", BaseActions.CAMPO_TIPO_DATA, "01/07/2016", "Edição Cheque, reg 1 - data vencimento errada");
		BaseActions.verificaCampoPreenchido(h, "CHEQUE_PROPRIO:iDtVenctoEfetiva_input", BaseActions.CAMPO_TIPO_DATA, "01/07/2016", "Edição Cheque, reg 1 - data vencimento efetiva errada");
		BaseActions.verificaCampoPreenchido(h, "CHEQUE_PROPRIO:iPessoaCadastro", BaseActions.CAMPO_TIPO_RADIO, "2", "Edição Cheque, reg 1 - sacado/cedente errado");
		BaseActions.verificaCampoPreenchido(h, "CHEQUE_PROPRIO:iDescricao", BaseActions.CAMPO_TIPO_TEXT,"118 COBRANÇA DE I.O.F. 391.100.702 D", "Edição Cheque, reg 1, descrição errada");
		BaseActions.verificaCampoPreenchido(h, "CHEQUE_PROPRIO:iValor", BaseActions.CAMPO_TIPO_TEXT,"12,34", "Edição Cheque, reg 1, valor errado");
		BaseActions.verificaCampoPreenchido(h, "CHEQUE_PROPRIO:iDescontos", BaseActions.CAMPO_TIPO_TEXT,"", "Edição Cheque, reg 1, valor desconto errado");
		BaseActions.verificaCampoPreenchido(h, "CHEQUE_PROPRIO:iAcrescimos", BaseActions.CAMPO_TIPO_TEXT,"", "Edição Cheque, reg 1, valor acrescimo errado");
		BaseActions.verificaCampoPreenchido(h, "CHEQUE_PROPRIO:iValorTotal", BaseActions.CAMPO_TIPO_TEXT,"12,34", "Edição Cheque, reg 1, valor total errado");
		
		//Depois preencher carteira -> verificar se preencheu dados cheque
		BaseActions.preencheCampo(h, "CHEQUE_PROPRIO:iCarteira_label", BaseActions.CAMPO_TIPO_COMBOPF, "4 - ITAÚ 0780 24200-4");
		Thread.sleep(500);
		BaseActions.verificaCampoPreenchido(h, "CHEQUE_PROPRIO:iDescricaoMontada", BaseActions.CAMPO_TIPO_TEXT, "341 - BANCO ITAU", "Edição Cheque, reg 1 - Banco não preenchido"); 
		BaseActions.verificaCampoPreenchido(h, "CHEQUE_PROPRIO:iAgencia", BaseActions.CAMPO_TIPO_TEXT, "0780", "Edição Cheque, reg 1 - Agência não preenchida");
		BaseActions.verificaCampoPreenchido(h, "CHEQUE_PROPRIO:iConta", BaseActions.CAMPO_TIPO_TEXT, "24200-4", "Edição Cheque, reg 1 - Conta não preenchida");
		BaseActions.verificaCampoPreenchido(h, "CHEQUE_PROPRIO:iNumCheque", BaseActions.CAMPO_TIPO_TEXT, "", "Edição Cheque, reg 1 - Cheque ficou preenchido");
		BaseActions.preencheCampo(h, "CHEQUE_PROPRIO:iNumCheque", BaseActions.CAMPO_TIPO_TEXT, "990001");
		
		h.clickByIdEnding("CHEQUE_PROPRIO:btnSalvar");
		Assert.assertNotNull(
				h.byCSS("[class='ui-growl-image ui-growl-image-info']"),
					"Não apresentou mensagem de sucesso ao salvar CHEQUE.");
		Thread.sleep(3000);//Para a mensagem sumir da tela
	}
	
	@Test(dependsOnMethods = { "movimentacaoImporterFormTestImportacao" })
	public void movimentacaoImporterFormTestEdicaoChequeTerceiros() throws InterruptedException {
		//Checa editar cheque Terceiros - Registro 1
		h.clickByIdEnding(":0:btnEditar_menuButton");//Seta a direita
		h.clickByIdEnding(":0:btnEditarChequeTerceiro");
		Thread.sleep(1000);
		
		//Data pagamento some ao escolher modo moviment cheque
		BaseActions.verificaCampoPreenchido(h, "CHEQUE_TERCEIROS:iModoMoviment", BaseActions.CAMPO_TIPO_TEXT, "CHEQUE TERCEIROS", "Edição Cheque, reg 1 - Modo moviment não alterou para Cheque próprio");
		BaseActions.verificaCampoPreenchido(h, "CHEQUE_TERCEIROS:iCarteira_label", BaseActions.CAMPO_TIPO_COMBOPF, "","Edição Cheque, reg 1 - carteira ficou preenchida");
		BaseActions.verificaCampoPreenchido(h, "CHEQUE_TERCEIROS:iModoMoviment_label", BaseActions.CAMPO_TIPO_COMBOPF, "","Edição Cheque, reg 1 - Modo moviment preenchido");
		BaseActions.verificaCampoPreenchido(h, "CHEQUE_TERCEIROS:iCategoriaCodigo", BaseActions.CAMPO_TIPO_TEXT, "2", "Edição Cheque, reg 1 - Categoria código não preenchida");
		BaseActions.verificaCampoPreenchido(h, "CHEQUE_TERCEIROS:iCategoria_input", BaseActions.CAMPO_TIPO_TEXT, "SAÍDAS", "Edição Cheque, reg 1 - Categoria desc não preenchida");
		BaseActions.verificaCampoPreenchido(h, "CHEQUE_TERCEIROS:iDtMoviment_input", BaseActions.CAMPO_TIPO_DATA, "01/07/2016", "Edição Cheque, reg 1 - data movimentação errada");
		BaseActions.verificaCampoPreenchido(h, "CHEQUE_TERCEIROS:iDtVencto_input", BaseActions.CAMPO_TIPO_DATA, "01/07/2016", "Edição Cheque, reg 1 - data vencimento errada");
		BaseActions.verificaCampoPreenchido(h, "CHEQUE_TERCEIROS:iDtVenctoEfetiva_input", BaseActions.CAMPO_TIPO_DATA, "01/07/2016", "Edição Cheque, reg 1 - data vencimento efetiva errada");
		BaseActions.verificaCampoPreenchido(h, "CHEQUE_TERCEIROS:iPessoaCadastro", BaseActions.CAMPO_TIPO_RADIO, "2", "Edição Cheque, reg 1 - sacado/cedente errado");
		BaseActions.verificaCampoPreenchido(h, "CHEQUE_TERCEIROS:iDescricao", BaseActions.CAMPO_TIPO_TEXT,"118 COBRANÇA DE I.O.F. 391.100.702 D", "Edição Cheque, reg 1, descrição errada");
		BaseActions.verificaCampoPreenchido(h, "CHEQUE_TERCEIROS:iValor", BaseActions.CAMPO_TIPO_TEXT,"12,34", "Edição Cheque, reg 1, valor errado");
		BaseActions.verificaCampoPreenchido(h, "CHEQUE_TERCEIROS:iDescontos", BaseActions.CAMPO_TIPO_TEXT,"", "Edição Cheque, reg 1, valor desconto errado");
		BaseActions.verificaCampoPreenchido(h, "CHEQUE_TERCEIROS:iAcrescimos", BaseActions.CAMPO_TIPO_TEXT,"", "Edição Cheque, reg 1, valor acrescimo errado");
		BaseActions.verificaCampoPreenchido(h, "CHEQUE_TERCEIROS:iValorTotal", BaseActions.CAMPO_TIPO_TEXT,"12,34", "Edição Cheque, reg 1, valor total errado");
		
		//Depois preencher carteira -> verificar se preencheu dados cheque
		BaseActions.preencheCampo(h, "CHEQUE_TERCEIROS:iCarteira_label", BaseActions.CAMPO_TIPO_COMBOPF, "4 - ITAÚ 0780 24200-4");
		Thread.sleep(500);
		BaseActions.verificaCampoPreenchido(h, "CHEQUE_TERCEIROS:iDescricaoMontada", BaseActions.CAMPO_TIPO_TEXT, "", "Edição Cheque TERC, reg 1 - Banco ficou preenchido"); 
		BaseActions.verificaCampoPreenchido(h, "CHEQUE_TERCEIROS:iAgencia", BaseActions.CAMPO_TIPO_TEXT, "", "Edição Cheque TERC, reg 1 - Agência ficou preenchida");
		BaseActions.verificaCampoPreenchido(h, "CHEQUE_TERCEIROS:iConta", BaseActions.CAMPO_TIPO_TEXT, "", "Edição Cheque TERC, reg 1 - Conta ficou preenchida");
		BaseActions.verificaCampoPreenchido(h, "CHEQUE_TERCEIROS:iNumCheque", BaseActions.CAMPO_TIPO_TEXT, "", "Edição Cheque TERC, reg 1 - Cheque ficou preenchido");
		BaseActions.preencheCampo(h, "CHEQUE_TERCEIROS:iBanco_input", BaseActions.CAMPO_TIPO_TEXT, "BANCO ITAU");
		h.byCSS("[class='ui-autocomplete-query']").click();
		
		BaseActions.preencheCampo(h, "CHEQUE_TERCEIROS:iNumCheque", BaseActions.CAMPO_TIPO_TEXT, "990001");
		BaseActions.preencheCampo(h, "CHEQUE_TERCEIROS:iAgencia", BaseActions.CAMPO_TIPO_TEXT, "1234");
		BaseActions.preencheCampo(h, "CHEQUE_TERCEIROS:iConta", BaseActions.CAMPO_TIPO_TEXT, "5678");
		BaseActions.preencheCampo(h, "CHEQUE_TERCEIROS:iNumCheque", BaseActions.CAMPO_TIPO_TEXT, "9900555");
		h.clickByIdEnding("CHEQUE_TERCEIROS:btnSalvar");
		Assert.assertNotNull(
				h.byCSS("[class='ui-growl-image ui-growl-image-info']"),
					"Não apresentou mensagem de sucesso ao salvar CHEQUE TERCEIROS.");
		Thread.sleep(3000);//Para a mensagem sumir da tela
	}
	
	@Test(dependsOnMethods = { "movimentacaoImporterFormTestImportacao" })
	public void movimentacaoImporterFormTestEdicaoTransferenciaPropria() throws InterruptedException {
		//Checa editar transferencia própria - Registro 1
		h.clickByIdEnding(":0:btnEditar_menuButton");//Seta a direita
		h.clickByIdEnding(":0:btnEditarTransferenciaPropria");
		Thread.sleep(1000);
		
		BaseActions.verificaCampoPreenchido(h, "TRANSF_PROPRIA:iModoMoviment_label", BaseActions.CAMPO_TIPO_COMBOPF, "1 - EM ESPÉRIE", "Edição Transf. Própria - Modo moviment errado.");
		BaseActions.verificaCampoPreenchido(h, "TRANSF_PROPRIA:iCarteiraOrigem_label", BaseActions.CAMPO_TIPO_COMBOPF, "1 - ESCRITÓRIO", "Edição Transf. Própria - Carteira errada.");
		BaseActions.verificaCampoPreenchido(h, "TRANSF_PROPRIA:iCarteiraDestino_label", BaseActions.CAMPO_TIPO_COMBOPF, "", "");
		BaseActions.verificaCampoPreenchido(h, "TRANSF_PROPRIA:iDescricao", BaseActions.CAMPO_TIPO_TEXT, "118 COBRANÇA DE I.O.F. 391.100.702 D", "Edição Transf. Própria - Descrição errada.");
		BaseActions.verificaCampoPreenchido(h, "TRANSF_PROPRIA:iDtMoviment_input", BaseActions.CAMPO_TIPO_TEXT, "01/07/2016", "Edição Transf. Própria - Data moviment errada.");
		BaseActions.preencheCampo(h, "TRANSF_PROPRIA:iCarteiraDestino_label", BaseActions.CAMPO_TIPO_COMBOPF, "2 - CAIXA A VISTA");
		BaseActions.preencheCampo(h, "TRANSF_PROPRIA:iObs", BaseActions.CAMPO_TIPO_TEXT, "SELENIUM TEST");
		
		h.clickByIdEnding("TRANSF_PROPRIA:btnSalvar");
		Assert.assertNotNull(
				h.byCSS("[class='ui-growl-image ui-growl-image-info']"),
					"Não apresentou mensagem de sucesso ao salvar TRANSFERENCIA PRÓPRIA.");
		Thread.sleep(3000);//Para a mensagem sumir da tela
	}
	
	@Test(dependsOnMethods = { "movimentacaoImporterFormTestImportacao" })
	public void movimentacaoImporterFormTestExcluir() throws InterruptedException {
		h.clickByIdEnding(":0:btnDeletar");
		h.clickByIdEnding("btnCdlgGlobal_sim");
		
		h.clickByIdEnding(":0:btnDeletar");//o outro registro vira o primeiro
		h.clickByIdEnding("btnCdlgGlobal_sim");
		
		Assert.assertNull(h.byIdEnding(":0:btnDeletar"),"Não removeu todos os registros ao excluir todos");
	}
	
	@Test(dependsOnMethods = { "movimentacaoImporterFormTestImportacao" })
	public void movimentacaoImporterFormTestSalvarTodos() throws InterruptedException {
		h.clickByIdEnding("btnSalvarTodos");//Salvar todos
		h.clickByIdEnding("btnCdlgGlobal_sim");
		Assert.assertNotNull(
				h.byCSS("[class='ui-growl-image ui-growl-image-info']"),
					"Não apresentou mensagem de sucesso ao salvar todos os registros.");
		
		Thread.sleep(3000);//Para a mensagem sumir da tela
		
		BaseActions.preencheCampo(h, "iLinhas", BaseActions.CAMPO_TIPO_TEXT, 
				"01/07/2016                 118 Cobrança de I.O.F.           391.100.702          12,34 D" + Keys.ENTER + 
				"02/07/2016                 Outra movimentação                                   432,10 D");
		
		h.clickByIdEnding("frm:btnImportar");//Botao Importar
		Thread.sleep(1500);
		//2 botoes de editar
		Assert.assertEquals(h.qtdByIdEnding(":0:btnEditar_button"),1,"Não apresentou 2 registros ao importar 2 linhas");
		Assert.assertEquals(h.qtdByIdEnding(":1:btnEditar_button"),1,"Não apresentou 2 registros ao importar 2 linhas");
		
		h.qtdByIdEnding(":0:btnEditar_button");//Edita o primeiro
		BaseActions.preencheCampo(h, "GERAL:iModoMoviment_label", BaseActions.CAMPO_TIPO_COMBOPF, "1 - EM ESPÉCIE");
		BaseActions.preencheCampo(h, "GERAL:j_idt1533", BaseActions.CAMPO_TIPO_TEXT, "2.01.099");
		
		h.clickByIdEnding("GERAL:btnSalvar");
		Assert.assertNotNull(
				h.byCSS("[class='ui-growl-image ui-growl-image-info']"),
					"Não apresentou mensagem de sucesso ao alterar modo e categoria.");
	
		Thread.sleep(3000);//Para a mensagem sumir da tela
		
		h.clickByIdEnding("btnSalvarTodos");//Salvar todos
		h.clickByIdEnding("btnCdlgGlobal_sim");
		Assert.assertNotNull(
				h.byCSS("[class='ui-growl-image ui-growl-image-info']"),
					"Não apresentou mensagem de sucesso ao salvar todos os registros.");
		Thread.sleep(3000);//Para a mensagem sumir da tela
	}
	
	@Test(dependsOnMethods = { "movimentacaoImporterFormTestSalvarTodos" })
	public void movimentacaoImporterFormTestExcluiMovimentacoes() throws InterruptedException {
		h.getDriver().get(BaseActions.address + page_list_mov);
		Thread.sleep(3000);
		
		h.clickByIdEnding("frm:btnPesquisar");
		Thread.sleep(1000);
		
		Assert.assertEquals(h.qtdByIdEnding(":0:j_idt153"),1,"Não apresentou 2 registros ao pesquisar movimentações de teste");
		Assert.assertEquals(h.qtdByIdEnding(":1:j_idt153"),1,"Não apresentou 2 registros ao pesquisar movimentações de teste");
		
		h.clickByIdEnding(":0:j_idt153");
		h.clickByIdEnding("j_idt8:btnCdlgGlobal_sim");
		
		h.clickByIdEnding(":0:j_idt153");
		h.clickByIdEnding("j_idt8:btnCdlgGlobal_sim");
		
		Assert.assertEquals(h.qtdByIdEnding(":0:j_idt153"),0,"Não exluiu os regsitros");
	}
		
	@AfterSuite
	public void shutdown() {
		//h.getDriver().quit();
	}
}
