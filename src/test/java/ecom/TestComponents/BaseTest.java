package ecom.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ecom.pageobjects.Landing;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	public WebDriver driver;
	public Landing landingPage;

	public WebDriver initiateDriver() {
		Properties prop = new Properties();
		String filePath = "C:\\Users\\002O7A744\\eclipse-workspace\\NewFrameProject\\src\\main\\java\\ecom\\resources\\GlobalData.properties";

		// Replace with the actual file path

		try {

		FileInputStream fis = new FileInputStream(filePath);
		prop.load(fis);
//		fis.close();

		// Close the FileInputStream when done

		} catch (IOException e) {

		e.printStackTrace();

		}
		String browserName = System.getProperty("browser")!=null ? System.getProperty("browser"): prop.getProperty("browser");

//		String browserName = prop.getProperty("browser");

		if(browserName.contains("chrome")) {
		ChromeOptions options = new ChromeOptions();
		WebDriverManager.chromedriver().setup();
		if(browserName.contains("headless")) {
			options.addArguments("headless");
		}
		driver = new ChromeDriver(options);
//		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		
	}
		else if(browserName.equals("firefox")) {	
			//Firefox browser
	}
		else if(browserName.equals("edge")) {	
			//Edge browser
	}

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.manage().window().maximize();
		return driver;
}
	
	public  List<HashMap<String, String>> getJsonDatatoMap(String newFile) throws IOException {
		
		String jsonData = FileUtils.readFileToString(new File(newFile),StandardCharsets.UTF_8);
	
	ObjectMapper mapper = new ObjectMapper();
	List<HashMap<String,String>> data = mapper.readValue(jsonData,new TypeReference<List<HashMap<String,String>>>(){
	});
	return data;
}
	

	public String getScreenShot(String testCaseName,WebDriver driver) throws IOException {
		TakesScreenshot ts = (TakesScreenshot)driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File file = new File(System.getProperty("user.dir")+"//reports//"+testCaseName+".png");
		FileUtils.copyFile(source, file);
		return System.getProperty("user.dir") +"//reports//" + testCaseName +".png";
	}
	
@BeforeMethod(alwaysRun=true)
	public Landing launchApplication() {
		driver= initiateDriver();
		landingPage = new Landing(driver);
		landingPage.Goto();
		return landingPage;
}

@AfterMethod(alwaysRun=true)
	public void tearDown() {
		driver.close();
}
}