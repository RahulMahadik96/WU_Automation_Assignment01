package retriveLinkOnFlipcart;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class RetriveFlipkartLinksUsingForEachLoop {

	
	@Test
	public void  retriverLinkUsingForEach() {
	
		
		 
		
		WebDriver driver;
         driver=WebDriverManager.chromedriver().create();
         driver.manage().window().maximize();
         driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
         
         driver.get("https://www.flipkart.com");
         
         // Retrive Links using For each Loop
         
         List<WebElement> links=driver.findElements(By.tagName("a"));
         int count=0;
         for(WebElement temp:links) {
        	 System.out.println(temp.getText());
        	 count++;
         }
         
         System.out.println(count);
         
         
         //Retrieve Links using Lambda Expression 
         
         links.forEach( (n) -> { System.out.println(n.getText());} );
         
         
         //Retrieve Links using parallel stream 
         
         
         links.parallelStream().forEach(System.out::println);  
	}
	

}
