package jp.gihyo.jenkinsbook.webdriver;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;

import jp.gihyo.jenkinsbook.page.ResultPage;
import jp.gihyo.jenkinsbook.page.TopPage;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SampleTestCase {

	private static Properties prop = new Properties();
	private static WebDriver driver;
	
	@BeforeClass
    public static void setUpClass() {
        // FirefoxOptions を使ってヘッドレスモードを設定
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(true); // ヘッドレスモードを有効化

        // options を引数に渡してドライバを初期化
        driver = new FirefoxDriver(options);
        
        // 既存のベースURL設定やウィンドウ最大化など
    }

	@AfterClass
	public static void tearDownClass() throws IOException {
		driver.quit();
	}
	
	@Test
	public void testNormal01() {
		driver.get(prop.getProperty("baseUrl") + "/sampleproject");
		
		TopPage topPage = new TopPage(driver);
		assertEquals("名字", topPage.getLastNameLabel());
		assertEquals("名前", topPage.getFirstNameLabel());

		assertTrue(topPage.hasLastNameInput());
		assertTrue(topPage.hasFirstNameInput());
		assertTrue(topPage.hasSubmit());
	}
	
	@Test
	public void testNormal02() {
		driver.get(prop.getProperty("baseUrl") + "/sampleproject");
		
		TopPage topPage = new TopPage(driver);
		topPage.setLastName("Hoge");
		topPage.setFirstName("Foo");
		topPage.submit();
		
		String greeting = "";
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		if (hour < 12) {
			greeting = "Good morning";
		} else {
			greeting = "Good afternoon";
        }
		ResultPage resultPage = new ResultPage(driver);
		assertEquals(greeting + ", Hoge Foo!!", resultPage.getText());
	}
	
	@Test
	public void testError01() {
		driver.get(prop.getProperty("baseUrl") + "/sampleproject");
		
		TopPage page = new TopPage(driver);
		page.setFirstName("Hoge");
		page.submit();
		
		ResultPage resultPage = new ResultPage(driver);
		assertEquals("エラー", resultPage.getText());
	}

	@Test
	public void testError02() {
		driver.get(prop.getProperty("baseUrl") + "/sampleproject");
		
		TopPage page = new TopPage(driver);
		page.setLastName("Hoge");
		page.submit();
		
		ResultPage resultPage = new ResultPage(driver);
		assertEquals("エラー", resultPage.getText());
	}
}
