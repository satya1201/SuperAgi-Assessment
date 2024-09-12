package Satya.Sample;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class sample {
	
	public static void main(String args[]) throws InterruptedException {
	
	WebDriver driver = new ChromeDriver();
	driver.manage().window().maximize();
	driver.get("https://www.amazon.in/");
	
	driver.findElement(By.id("twotabsearchtextbox")).click();
	driver.findElement(By.id("twotabsearchtextbox")).sendKeys("lg soundbar");
	
	WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));	
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".s-suggestion-container")));
	
	List<WebElement> result = driver.findElements(By.cssSelector(".s-suggestion-container"));
	result.get(0).click();
	
	List<WebElement> products = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy
			(By.cssSelector(".a-section .puisg-row .puis-list-col-right")));
	Map<String, Integer> prodDetails= new HashMap<>();
	 for (WebElement product : products) {
         try {
             String name = product.findElement(By.cssSelector("span.a-size-medium")).getText();
             String price = "";
             int p = 0;
             try {
                 price = product.findElement(By.cssSelector(".a-price-whole")).getText().replace(",", "");
                 p = Integer.parseInt(price);
             } catch (NoSuchElementException e) {
                 p = 0;
             }
             prodDetails.put(name, p);
         } catch (NoSuchElementException e) {
         }
     }
	List<Entry<String, Integer>> sortedProducts = new ArrayList<>(prodDetails.entrySet());
    sortedProducts.sort(Comparator.comparingInt(Entry::getValue));

    for (Entry<String, Integer> entry : sortedProducts) {
        System.out.println(entry.getValue() + " " + entry.getKey());
    }
	driver.quit();
}
}
