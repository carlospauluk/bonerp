package com.bonsucesso.erp.tests.crud;

import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.bonsucesso.erp.tests.base.BaseActions;
import com.ocabit.utils.tests.selenium.SeleniumHelper;
import com.ocabit.utils.tests.selenium.SeleniumHelper.Browser;


/**
 * Classe para testar telas do tipo FormList
 * 
 * @author Rodrigo J Polette
 *
 */
public class FormListIT {

	protected static Logger logger = Logger.getLogger(FormListIT.class);
	
	public SeleniumHelper h;

	private BonERPFormList bonERPFormList;

	private static final String BONERPFORMLIST_XML = "xml/bonerp_formlists.xml";

	public BonERPFormList getBonERPFormList() {
		if (bonERPFormList == null) {
			try {
				JAXBContext context = JAXBContext.newInstance(BonERPFormList.class);
				Unmarshaller u = context.createUnmarshaller();
				File file = new File(Thread.currentThread().getContextClassLoader()
						.getResource(FormListIT.BONERPFORMLIST_XML)
						.getFile());
				bonERPFormList = (BonERPFormList) u.unmarshal(file);
			} catch (JAXBException e) {
				throw new IllegalStateException("Erro ao buscar caminhos em " + FormListIT.BONERPFORMLIST_XML);
			}
		}
		return bonERPFormList;
	}
	
	static final int geraStringRandomTMinuscula = 0;
	static final int geraStringRandomTMaiuscula = 1;
	
	private String geraStringRandom(int tamanho, int tipoChars, boolean permiteEspaco) {
		char[] chars;
		if (tipoChars == geraStringRandomTMinuscula)
			chars = "abcdefghijklmnopqrstuvxyz".toCharArray();
		else
			chars = "ABCDEFGHIJKLMNOPQRSTUVXYZ".toCharArray();
		
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i=0; i<tamanho; i++) {
			sb.append(chars[random.nextInt(chars.length)]);
		}
		if (permiteEspaco)
			return sb.toString();
		else
			return sb.toString().replace(" ","");
	}

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

	@DataProvider(name = "formLists")
	public Object[][] getFormLists() {
		Object[][] result = new Object[getBonERPFormList().getFormLists().size()][1];

		int i = 0;
		for (FormList item : getBonERPFormList().getFormLists()) {
			result[i][0] = item;
			i++;
		}
		return result;
	}
	
	private void formListAbreCadastro() throws InterruptedException {
		h.clickById("frm:btnNovo");
		Thread.sleep(1000);
	}
	
	private void formListFechaCadastro() {
		BaseActions.executaJS(h, "PF(\"dlgForm\").hide();");//PrimeFace
	}
	
	private void formListTestaCadastroNovoVazio(FormList formList, String msgComplemento) throws InterruptedException {
		List<CampoCadastro> camposCad = formList.getCamposCadastro();
		
		formListAbreCadastro();
		
		String tipo;
		for (CampoCadastro campoCad : camposCad) {
			tipo = campoCad.getTipo();
			if (tipo.equals("text")) {
				logger.info("Teste campo vazio - " + campoCad.getId());
				Assert.assertEquals(
						h.valueByIdEnding(campoCad.getId()), "", 
						"Campo de cadastro novo com valor atribuído. " + msgComplemento);
			}
		}
		formListFechaCadastro();
	}
	
//	private void formListTrataCampoUnico(List<CampoCadastro> camposCad) throws InterruptedException {
//		String tipo;
//		Boolean unico;
//		
//		for (CampoCadastro campoCad : camposCad) {
//			tipo = campoCad.getTipo();
//			if (tipo.equals("inteiroRand")) {
//				unico = false;
//				do {
//					formListPesquisa(campoCad.getValor());
//					if (! h.textPresent("Total de Registros: 0."))
//						campoCad.geraRand();//Muda o valor do campo
//					else
//						unico = true;
//				} while (! unico);
//			}
//		}
//	}
	
	@Test(dependsOnMethods = { "login" }, dataProvider = "formLists", invocationCount = 1)
	public void formListTest(FormList formList) throws InterruptedException {
		List<CampoCadastro> camposCad = formList.getCamposCadastro();
		
		h.getDriver().get(BaseActions.address + formList.getUrl());
		Thread.sleep(3000);
		
		//formListTrataCampoUnico(camposCad);
		
		//Zera pesquisa
		BaseActions.formListPesquisa(h,"");
		
		//Abre cadastro novo e verifica se campos do tipo text estão vazios
		formListTestaCadastroNovoVazio(formList,"Ao abrir página. ["+formList.getUrl()+"]");
		
		formListAbreCadastro();
		//Verifica mensagem de preechimendo de campos obrigatórios
		h.clickByIdEnding("btnSalvar");
		Assert.assertNotNull(
				h.byCSS("[class='ui-growl-image ui-growl-image-error']"),
					"Não apresentou mensagem de erro ao tentar cadastrar novo vazio. ["+formList.getUrl()+"]");
		
		//Testa inserção
		//Preenche os campos
		String primeiroTexto = "", primeiroIdTexto = "", id, tipo, strRandom;
		strRandom = geraStringRandom(5, geraStringRandomTMaiuscula, false);
		
		for (CampoCadastro campoCad : camposCad) {
			id = campoCad.getId();
			tipo = campoCad.getTipo();
						
			if (tipo.equals("text") && primeiroTexto.equals("")) {
				primeiroTexto = campoCad.getValor() + strRandom;
				primeiroIdTexto = campoCad.getId();
				campoCad.setValor(primeiroTexto);
			}
			BaseActions.preencheCampo(h, id, tipo, campoCad.getValor());
		}
		
		//Thread.sleep(1500); -> Na tela ModoFormList dá problema na linha abaixo e mesmo com Thread.Sleep continua não funcionando. O clickByIdEnding tem sleep implícito, o que confirma que essa linha não adiantaria, porém debugando o teste da OK...
		h.clickByIdEnding("btnSalvar");//O cadastro fecha a tela automaticamente
		
		Thread.sleep(500);
		//Mensagem de sucesso
		Assert.assertNotNull(
				h.byCSS("[class='ui-growl-image ui-growl-image-info']"),
					"Não apresentou mensagem de sucesso ao salvar novo cadastro. ["+formList.getUrl()+"]");
		
		//Abre novamente o cadastro novo e verifica se está vazio
		formListTestaCadastroNovoVazio(formList,"Após cadastrar novo. ["+formList.getUrl()+"]");
		
		//Testa pesquisa
		BaseActions.formListPesquisa(h,primeiroTexto);
		//Certifica que não exista 2 resultados				
		//Problema quando a pesquisa não funciona e retorna entre 10 e 19 registros, passa nesse teste, mas não no logo após que checa os valores retornados na edição. [Foi incluído . no final]
		Assert.assertTrue(h.textPresent("Total de Registros: 1."),"Pesquisa não achou 1 registro antes testar alteração. ["+formList.getUrl()+"]");
		
		//Testa alteração
		h.clickByIdEnding(":0:btnEditar");
		Thread.sleep(500);
		
		//Checa valores retornados
		for (CampoCadastro campoCad : camposCad) {
			BaseActions.verificaCampoPreenchido(h, campoCad.getId(), campoCad.getTipo(), campoCad.getValor(), "Edição, campo diferente do cadastrado inicialmente. ["+formList.getUrl()+"]");
		}
		
		//Muda valor do primeiro campo texto
		h.byIdEnding(primeiroIdTexto).clear();
		h.byIdEnding(primeiroIdTexto).sendKeys(primeiroTexto + " ALTERADO");
		
		h.clickByIdEnding("btnSalvar");//Edição também fecha o cadastro automaticamente
		//Mensagem de sucesso
		Assert.assertNotNull(
				h.byCSS("[class='ui-growl-image ui-growl-image-info']"),
					"Não apresentou mensagem de sucesso ao salvar alteração. ["+formList.getUrl()+"]");
		
		//Testa campos em branco ao alerar um registro
		formListTestaCadastroNovoVazio(formList,"Após alterar. ["+formList.getUrl()+"]");
		
		//Testa exclusão
		BaseActions.formListPesquisa(h,primeiroTexto + " ALTERADO");
		//Certifica que não exista 2 resultados
		Assert.assertTrue(h.textPresent("Total de Registros: 1."),"Pesquisa não achou 1 registro antes de testar exclusão. ["+formList.getUrl()+"]");
		//Primeiro botão de exclusão
		h.clickByIdEnding(":0:btnDeletar");
		h.clickByIdEnding("btnCdlgGlobal_sim");
		
		Thread.sleep(1000);
		Assert.assertTrue(h.textPresent("Total de Registros: 0."),"Exclusão não excluiu do grid. ["+formList.getUrl()+"]");
		
		//Zera pesquisa
		BaseActions.formListPesquisa(h,"");
	}

	@AfterSuite
	public void shutdown() {
		h.getDriver().quit();
	}

}
