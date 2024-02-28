package uiStockPriceValidation;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.github.bonigarcia.wdm.WebDriverManager;

public class StockPriceTest {
	
	public static void main(String[] args) throws EncryptedDocumentException, IOException {
		
		WebDriver driver;
		
		//1 & 2.Using XLS file storing expected stock price and getting data from XLS to HashMap
		
		 List<String> compNameXls = new ArrayList<>();
		 List<String> stockPriceXls = new ArrayList<>();
		 
		 HashMap<String, String> exlsDataHM=new HashMap<>();
		 
		   String path = "C:\\Users\\Rahul\\git\\E2EFramework_RestAssure\\Rest_Assured\\src\\test\\java\\webAppTestPackeg\\TestData2.xlsx";
			FileInputStream file = new FileInputStream(path);
			Workbook book = WorkbookFactory.create(file);
			Sheet sheet = book.getSheetAt(0);
			int rowCnt = sheet.getLastRowNum();
			Row row = sheet.getRow(0);
			int colCnt= row.getLastCellNum();
			System.out.println("RowCount: " + rowCnt + " : : ColumnCount: " + colCnt);
			
			String compName="";
		     String stockPrice="";
		     
			for(int i=1;i<=rowCnt;i++) {
				row = sheet.getRow(i);
				for(int j = 0; j<colCnt; j++) {
					Cell cell  = row.getCell(j);
					
				     
					if(j==0) {
						compNameXls.add(cell.toString());
						compName =cell.toString();
					}
					else {
						stockPriceXls.add(cell.toString());
						stockPrice=cell.toString();
					}
					
					
				}
				
				exlsDataHM.put(compName, stockPrice);
			}
			
			System.out.println(exlsDataHM.get("Menon Pistons Li"));
		
		
		
		//3. Using SeleniumWebdriver getting stock price and storing into HashMap
			
			driver=WebDriverManager.chromedriver().create();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			driver.navigate().to("https://money.rediff.com/losers/bse/daily/groupall");
			
			
			
			List<WebElement> compElements=driver.findElements(By.xpath("//table[@class='dataTable']//tr//td[1]"));
			List<WebElement> stockPriceElements=driver.findElements(By.xpath("//table[@class='dataTable']//tr//td[4]"));
			
			int compNameListSize=compElements.size();
			int spListSize=stockPriceElements.size();
			
			HashMap<String, String> webDataHM=new HashMap<>();
			
			//4.Storing data in HashMap2
			if(compNameListSize==spListSize) {
				for(int i=0;i<compNameListSize;i++) {					
					webDataHM.put(compElements.get(i).getText(), stockPriceElements.get(i).getText());
				}
			}
			
			
			//5. Compare the values stored in the two HashMaps
			
			for(Map.Entry<String, String> temp:exlsDataHM.entrySet()) {
				
				    String CName=temp.getKey();
				    String SPActual=temp.getValue();
				    String SPWeb=webDataHM.get(temp.getKey());
					System.out.println("Company Name: "+CName);
					System.out.println("Actual Stock Price: "+SPActual);
					System.out.println("Stock Price on Web: "+SPWeb);
					
					if(SPActual!=SPWeb) {
						
						System.out.println("Stock price of "+CName+" on Web is wrong ");
					}
					
				}
			
		
		}

}
