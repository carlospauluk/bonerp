import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class TesteChrome {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", "D:\\ocabit\\bonerp\\trunk\\bonerp-selenium-jar\\src\\main\\resources\\chromedriver-2.21.exe");
		ChromeOptions options = new ChromeOptions();
		options.setBinary("D:\\Chrome\\chrome32_48.0.2564.97\\Chrome-48.0.2564.97\\chrome.exe");
//		options.setBinary("D:\\Chrome\\x64_48.0.2564.109\\chrome.exe");
//		options.setBinary("D:\\Chrome\\chrome32_51.0.2704.84\\Chrome32_51.0.2704.84\\chrome.exe");
		WebDriver driver = new ChromeDriver(options);
		System.out.println("bla");
		driver.get("http://web.whatsapp.com/");

	}

}
