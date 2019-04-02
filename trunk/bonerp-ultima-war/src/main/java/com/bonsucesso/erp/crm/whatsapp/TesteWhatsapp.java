package com.bonsucesso.erp.crm.whatsapp;



import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.crm.data.CampanhaPromoFinder;
import com.bonsucesso.erp.crm.data.ClienteDataMapper;
import com.bonsucesso.erp.crm.data.ClienteFinder;
import com.bonsucesso.erp.crm.data.CupomDataMapper;
import com.bonsucesso.erp.crm.data.CupomFinder;
import com.bonsucesso.erp.crm.model.CampanhaPromo;
import com.bonsucesso.erp.crm.model.Cliente;
import com.bonsucesso.erp.crm.model.Cupom;
import com.bonsucesso.erp.crm.model.LoteCupom;
import com.bonsucesso.erp.crm.model.StatusCupom;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.utils.calendar.CalendarUtil;
import com.ocabit.utils.currency.CurrencyUtils;
import com.ocabit.utils.tests.selenium.SeleniumHelper;
import com.ocabit.utils.tests.selenium.SeleniumHelper.Browser;


@Component("testeWhatsapp")
public class TesteWhatsapp implements ITesteWhatsapp {

	protected static Logger logger = Logger.getLogger(TesteWhatsapp.class);

	private ITesteWhatsapp thiz;

	@Autowired
	private ClienteFinder clienteFinder;

	@Autowired
	private CampanhaPromoFinder campanhaPromoFinder;

	@Autowired
	private ClienteDataMapper clienteDataMapper;

	@Autowired
	private CupomDataMapper cupomDataMapper;

	@Autowired
	private CupomFinder cupomFinder;

	public SeleniumHelper h;

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) throws InterruptedException {
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
			ITesteWhatsapp t = (ITesteWhatsapp) context.getBean("testeWhatsapp");
			t.setThiz(t);
			t.doIt();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	private void logTempo(Long tIni, Integer processados, Integer total) {
		Long tAgora = System.currentTimeMillis();

		Double tempoDecorrido = (tAgora.doubleValue() - tIni.doubleValue()) / 1000.0;

		Double d = CurrencyUtils.divide(CurrencyUtils.divide(CurrencyUtils.multiplica(total.doubleValue()
				- processados.doubleValue(), tempoDecorrido.doubleValue()), processados.doubleValue()), 60.0);
		logger.debug("Processados: " + processados + " de " + total);
		logger.debug("Tempo restante: " + d + " minutos ");
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void doIt() throws InterruptedException {

		final Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		final org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User("carlos", "none", true, true, true, true, authorities);
		Authentication auth = new TestingAuthenticationToken(user, authorities);
		SecurityContextHolder.getContext().setAuthentication(auth);

		h = new SeleniumHelper(Browser.CHROME);
		h.getDriver().manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);
		h.getDriver().get("http://web.whatsapp.com/");

		// Espera até estar aberto a tela principal (depois do QRCode)
		WebDriverWait wait = new WebDriverWait(h.getDriver(), 300);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"side\"]/div[2]/div/label/input")));

		//		// Clica no campo de pesquisa
		//		ActionsHelper a = new ActionsHelper(h);
		//		a.moveByXPath("//*[@id=\"side\"]/div[2]/div/label/input").build().perform();
		//		a.click().build().perform();

		// Clica no botão pra abrir a pesquisa por contatos
		//		h.byXPath("//*[@id=\"side\"]/header/div[2]/div/span/div[1]/button").click();

		Thread.sleep(1000);

		try {
			final String sql = "SELECT c.id, p.nome, c.tem_whatsapp FROM crm_cliente c, bon_pessoa p WHERE c.pessoa_id = p.id AND "
					+ "(fone1 regexp '^((0){0,1}([0-9]{0,2})(6|7|8|9){1}([0-9]{7,8}))$' OR "
					+ "fone2 regexp '^((0){0,1}([0-9]{0,2})(6|7|8|9){1}([0-9]{7,8}))$' OR "
					+ "fone3 regexp '^((0){0,1}([0-9]{0,2})(6|7|8|9){1}([0-9]{7,8}))$' OR "
					+ "fone4 regexp '^((0){0,1}([0-9]{0,2})(6|7|8|9){1}([0-9]{7,8}))$') "
					+ "AND (tem_whatsapp = true) AND p.nome NOT LIKE 'A%' ORDER BY p.nome"
			//					+ "AND p.nome LIKE '%ROSENY TEREZA DE ANDRADE%'"
			;

			final Query qry = getClienteFinder().getEntityManager().createNativeQuery(sql);

			List<Object[]> clientes = qry.getResultList();
			long tIni = System.currentTimeMillis();
			int qtdeTotal = clientes.size();
			int i = 0;

			CampanhaPromo campanha = getCampanhaPromoFinder().findById(2l);
			LoteCupom lote = campanha.getLotes().get(0);

			int j = 0;

			for (Object[] o : clientes) {
				
				if (CalendarUtil.getCalendar(new Date()).get(Calendar.HOUR_OF_DAY) >= 22) { 
					System.exit(0);
				}

				BigInteger id = (BigInteger) o[0];
				String nome = (String) o[1];
				Boolean temWhatsapp = (Boolean) o[2];

				//				temWhatsapp = getThiz().verificaSeTemWhatsapp(nome);
				//				logger.debug("... " + (temWhatsapp ? "SIM" : "NÃO"));
				Cliente cliente = getClienteFinder().findById(id.longValue());
				//				cliente.setTemWhatsapp(temWhatsapp);
				//				getThiz().saveCliente(cliente);
				Cupom cupom = getCupomFinder().findBy(cliente, campanha);
				if (cupom == null) {

					if (j++ == 5) {
						Thread.sleep(4000);
						//						String inputPesquisaPorNome = "//*[@id=\"side\"]/div[2]/div/label/input";
						//						h.byXPath(inputPesquisaPorNome).clear();
						//						h.byXPath(inputPesquisaPorNome).sendKeys("CARLOS EDUARDO PAULUK");
						//						String labelNome = "//*[@id=\"pane-side\"]/div/div/div/div[2]/div/div/div[2]/div[1]/div/span";
						//						h.byXPath(labelNome).click();
						//						Thread.sleep(1000);						
						//						// Clica no campo de digitar mensagem
						//						h.byXPath("//*[@id=\"main\"]/footer/div[1]/div[2]/div/div[2]").click();
						//						// Escreve a mensagem
						//						h.byXPath("//*[@id=\"main\"]/footer/div[1]/div[2]/div/div[2]").sendKeys("ttttt");
						//
						//						h.getDriver().get("http://www.google.com/");
						//						Thread.sleep(2000);
						//						h.getDriver().switchTo().alert().accept();
						//						h.getDriver().get("http://web.whatsapp.com/");
						//						// Espera até estar aberto a tela principal (depois do QRCode)
						//						wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"side\"]/div[2]/div/label/input")));
						j = 0;
					}

					boolean enviada = getThiz().enviarMensagem(nome, getMensagem());
					logger.debug("ENVIANDO PARA '" + nome + "'...");

					if (enviada) {
						cupom = new Cupom();
						cupom.setCliente(cliente);
						cupom.setCodigo(UUID.randomUUID().toString().replaceAll("[^\\d.]", ""));
						cupom.setDtVinculacao(new Date());
						cupom.setLoteCupom(lote);
						cupom.setStatus(StatusCupom.VINCULADO);
						cupom.setOrdem(cliente.getId().intValue());
						getThiz().saveCupom(cupom);
					}

					i++;
					logTempo(tIni, i, qtdeTotal);

				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void saveCupom(Cupom cupom) throws ViewException {
		getCupomDataMapper().save(cupom);
		getCupomDataMapper().getEntityManager().flush();
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void saveCliente(Cliente cliente) throws ViewException {
		getClienteDataMapper().save(cliente);
		getClienteDataMapper().getEntityManager().flush();
	}

	public String getMensagem() {
		try {
			String fileMensagem = "F:\\BONSUCESSO\\Marketing\\Whatsapp\\dia_das_maes_2017-"
					+ ((new Random().nextInt(3)) + 1) + ".txt";
			byte[] encoded = Files.readAllBytes(Paths.get(fileMensagem));
			String msg = new String(encoded, StandardCharsets.UTF_8);
			return msg;
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public boolean enviarMensagem(String nome, String msg) throws InterruptedException {
		// Pesquisa pelo nome
		try {
			String inputPesquisaPorNome = "//*[@id=\"side\"]/div[2]/div/label/input";
			h.byXPath(inputPesquisaPorNome).clear();
			h.byXPath(inputPesquisaPorNome).sendKeys(nome);

			Thread.sleep(1000);

			// Vê se apareceu o label "CONVERSAS"
			String labelNome = "//*[@id=\"pane-side\"]/div/div/div/div[2]/div/div/div[2]/div[1]/div/span";
			String nomeEncontrado = h.byXPath(labelNome).getAttribute("title");
			if (nomeEncontrado.equals(nome)) {

				h.byXPath(labelNome).click();
				Thread.sleep(1000);

				// Pra garantir, verifica se o nome lá em cima bateu mesmo
				String nomeEmCima = h.byXPath("//*[@id=\"main\"]/header/div[2]/div[1]/h2/span").getAttribute("title");
				//*[@id=\"main\"]/header/div[2]/div[1]/h2/span

				if (nomeEmCima.equals(nome)) {

					Thread.sleep(1000);

					// Clica no campo de digitar mensagem
					h.byXPath("//*[@id=\"main\"]/footer/div[1]/div[2]/div/div[2]").click();
					// Escreve a mensagem
					
					String[] msgs = splitStringEvery(msg, 50);
					for (String m : msgs) {					
						h.byXPath("//*[@id=\"main\"]/footer/div[1]/div[2]/div/div[2]").sendKeys(m);
						Thread.sleep(100);
					}

					//				Thread.sleep(1000);
					// h.byXPath("//*[@id=\"main\"]/footer/div[1]/button").click();

					Thread.sleep(1000);

					return true;

				} else {
					logger.info("Encontrou mas é outro nome, verificar");
					return false;
				}
			} else {
				logger.info("Encontrou mas é outro nome, verificar");
				return false;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public String[] splitStringEvery(String s, int interval) {
		int arrayLength = (int) Math.ceil(((s.length() / (double) interval)));
		String[] result = new String[arrayLength];

		int j = 0;
		int lastIndex = result.length - 1;
		for (int i = 0; i < lastIndex; i++) {
			result[i] = s.substring(j, j + interval);
			j += interval;
		} //Add the last bit
		result[lastIndex] = s.substring(j);

		return result;
	}

	@Override
	public boolean verificaSeTemWhatsapp(String nome) throws InterruptedException {
		// Pesquisa pelo nome
		try {

			String inputPesquisaPorNome = "//*[@id=\"side\"]/div[2]/div/label/input";
			h.byXPath(inputPesquisaPorNome).clear();
			h.byXPath(inputPesquisaPorNome).sendKeys(nome);

			Thread.sleep(1000);

			// Vê se apareceu o label "CONVERSAS"

			String labelNome = "//*[@id=\"pane-side\"]/div/div/div/div[2]/div/div/div[2]/div[1]/div/span";

			String nomeEncontrado = h.byXPath(labelNome).getAttribute("title");

			if (nomeEncontrado.equals(nome)) {

				h.byXPath(labelNome).click();
				Thread.sleep(1000);

				// Pra garantir, verifica se o nome lá em cima bateu mesmo
				String nomeEmCima = h.byXPath("//*[@id=\"main\"]/header/div[2]/div[1]/h2/span").getAttribute("title");
				//*[@id=\"main\"]/header/div[2]/div[1]/h2/span

				if (nomeEmCima.equals(nome)) {
					Thread.sleep(1000);
					return true;
				} else {
					logger.info("Encontrou mas é outro nome, verificar");
					return false;
				}
			} else {
				logger.info("Encontrou mas é outro nome, verificar");
				return false;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public ITesteWhatsapp getThiz() {
		return thiz;
	}

	@Override
	public void setThiz(ITesteWhatsapp thiz) {
		this.thiz = thiz;
	}

	public ClienteFinder getClienteFinder() {
		return clienteFinder;
	}

	public void setClienteFinder(ClienteFinder clienteFinder) {
		this.clienteFinder = clienteFinder;
	}

	public ClienteDataMapper getClienteDataMapper() {
		return clienteDataMapper;
	}

	public void setClienteDataMapper(ClienteDataMapper clienteDataMapper) {
		this.clienteDataMapper = clienteDataMapper;
	}

	public CupomDataMapper getCupomDataMapper() {
		return cupomDataMapper;
	}

	public void setCupomDataMapper(CupomDataMapper cupomDataMapper) {
		this.cupomDataMapper = cupomDataMapper;
	}

	public CupomFinder getCupomFinder() {
		return cupomFinder;
	}

	public void setCupomFinder(CupomFinder cupomFinder) {
		this.cupomFinder = cupomFinder;
	}

	public CampanhaPromoFinder getCampanhaPromoFinder() {
		return campanhaPromoFinder;
	}

	public void setCampanhaPromoFinder(CampanhaPromoFinder campanhaPromoFinder) {
		this.campanhaPromoFinder = campanhaPromoFinder;
	}

}
