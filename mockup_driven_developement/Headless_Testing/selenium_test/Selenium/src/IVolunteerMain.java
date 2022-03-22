import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class IVolunteerMain {
	WebDriver driver;
	
	public static void main(String[] args) {
		// System Property for Chrome Driver   
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Admin\\Desktop\\Uniprojekt\\informationsystems-project\\002_Headless_Tools\\chromedriver_chrome96\\chromedriver.exe");  
          
        // Instantiate a ChromeDriver class.     
        WebDriver driver=new ChromeDriver();  
          
        // Launch Website  
        driver.navigate().to("https://ivolunteer-manager.herokuapp.com/");  
          
        //Maximize the browser  
        driver.manage().window().maximize();  
          
        //Scroll down the webpage by 5000 pixels  
        JavascriptExecutor js = (JavascriptExecutor)driver;  
        js.executeScript("scrollBy(0, 50)");   
          
         // Click on the Search button  
        driver.findElement(By.linkText("Account")).click();
        driver.findElement(By.linkText("Sign in")).click();
        
        WebElement userInputField = driver.findElement(By.id("username"));
        userInputField.sendKeys("user");
        
        WebElement pwInputField = driver.findElement(By.id("password"));
        pwInputField.sendKeys("user");
        
        driver.findElement(By.cssSelector("button[class='btn btn-primary']")).click();
        
        List<WebElement> elems = driver.findElements(By.cssSelector("jhi-user-navigation"));
        System.out.println(elems.size());
        
        for(WebElement e : elems) {
          System.out.println(e.findElements(By.tagName("ul")).size());
        }
	}
	
}
