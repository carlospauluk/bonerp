import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;


public class TesteIE {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("webdriver.ie.driver", "D:\\ocabit\\bonerp\\trunk\\bonerp-selenium-jar\\src\\main\\resources\\IEDriverServer-x64.exe");
		WebDriver driver = new InternetExplorerDriver();
		driver.get("http://www.google.com");

	}

}
