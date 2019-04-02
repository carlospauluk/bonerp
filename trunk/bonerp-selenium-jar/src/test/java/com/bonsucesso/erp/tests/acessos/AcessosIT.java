package com.bonsucesso.erp.tests.acessos;



import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.bonsucesso.erp.tests.base.BaseActions;
import com.ocabit.utils.tests.selenium.ActionsHelper;
import com.ocabit.utils.tests.selenium.SeleniumHelper;
import com.ocabit.utils.tests.selenium.SeleniumHelper.Browser;


/**
 * Classe para testar acessos as telas do sistema via menu principal.
 * 
 * @author Carlos Eduardo Pauluk
 *
 */
public class AcessosIT {

	protected static Logger logger = Logger.getLogger(AcessosIT.class);

	private final static String BONERPMENUMAP_XML = "xml/bonerp_menumap.xml";

	/**
	 * Array de links, na seguinte forma: 
	 * int:string:string
	 * 0/1 -> Foi aberto pelo teste
	 * Nome no menu
	 * Link correspondente
	 */
	private String[] links;
	
	public SeleniumHelper h;

	private BonERPMenuMap bonERPMenuMap;

	/**
	 * Transforma o XML para o objeto BonERPMenuMap (JAXB).
	 * @return
	 */
	public BonERPMenuMap getBonERPMenuMap() {
		if (bonERPMenuMap == null) {
			try {
				JAXBContext context = JAXBContext.newInstance(BonERPMenuMap.class);
				Unmarshaller u = context.createUnmarshaller();
				File file = new File(Thread.currentThread().getContextClassLoader()
						.getResource(AcessosIT.BONERPMENUMAP_XML)
						.getFile());
				bonERPMenuMap = (BonERPMenuMap) u.unmarshal(file);
			} catch (JAXBException e) {
				throw new IllegalStateException("Erro ao buscar caminhos em " + AcessosIT.BONERPMENUMAP_XML);
			}
		}
		return bonERPMenuMap;
	}

	public void setBonERPMenuMap(BonERPMenuMap bonERPMenuMap) {
		this.bonERPMenuMap = bonERPMenuMap;
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
	
	/**
	 * preencheLinkMenus monta um array com todos os links encontrados no menu do site
	 * @throws InterruptedException
	 */
	@Test(dependsOnMethods = {"login"} )
	public void preencheLinkMenus() throws InterruptedException {//style='visibility:hidden' -> Com isso não acha nada
		BaseActions.executaJS(h, "$('html').append(\"<div id='resp_selenium'></div>\");");
		BaseActions.executaJS(h, "$(\"#frmMenu\\\\:mainMenuBar>ul>li span.ui-menuitem-text\").each(function(){ "+   
		"var nome = $(this).text(); "+
		"var link = $(this).parent().attr('href'); " +
		"if (link != '#') { $('#resp_selenium').append('0:' + nome + ':' + link + \"<br />\"); } "+    
		"});");
		String resposta = h.textById("resp_selenium");
		BaseActions.executaJS(h, "$('#resp_selenium').remove();");
		links = resposta.split("\n");
	}

	@DataProvider(name = "caminhosMenu")
	public Object[][] getCaminhosMenu() {

		Object[][] result = new Object[getBonERPMenuMap().getItensMenu().size()][2];

		int i = 0;
		for (ItemMenu item : getBonERPMenuMap().getItensMenu()) {
			result[i][0] = item.getCaminhos();
			result[i][1] = item.getResult();
			i++;
		}

		return result;
	}

	@Test(dependsOnMethods = { "preencheLinkMenus" }, dataProvider = "caminhosMenu", invocationCount = 1)
	public void carregarPaginasTest(String[] caminhos, String result) {
		h.getDriver().get(BaseActions.address);
		ActionsHelper a = new ActionsHelper(h);

		for (String caminho : caminhos) {
			WebDriverWait wait = new WebDriverWait(h.getDriver(), 3);
			wait.until(ExpectedConditions.elementToBeClickable(h.byId(caminho)));
			a.moveById(caminho).build().perform();
			a.click().build().perform();
		}
		// a.click().build().perform();
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			logger.error(e);
		}
		
		/**
		 * Após abir a página clicando no menu (no caminho definido no XML)
		 * Verifica encontra o link da url atual nos links que foram extraidos do menu
		 */
		String urlAtual = h.getDriver().getCurrentUrl();
		Boolean encontrado = false;
		for (int i=0; i < links.length; i++) {
			String[] partes = links[i].split(":");
			if (urlAtual.endsWith(partes[2])) {
				encontrado = true;
				links[i] = "1:"+partes[1]+":"+partes[2];
			}
		}
		Assert.assertTrue(encontrado, "URL aberta pelo caminho no XML não encontrada no menu. ["+urlAtual+"]");
		//Verifica se existe a DIV correspondente a página
		Assert.assertNotNull(h.byXPath(result));
	}
	
	@Test(dependsOnMethods = { "carregarPaginasTest" })
	public void verificaTodosMenusTestados() {
		for (int i=0; i < links.length; i++) {
			String[] partes = links[i].split(":");
			Assert.assertEquals(partes[0], "1", "O item do menu " + partes[1] + " não foi testado. [Link="+partes[2]+"]");
		}
	}
	
	@AfterSuite
	public void shutdown() {
		h.getDriver().quit();
	}

}
