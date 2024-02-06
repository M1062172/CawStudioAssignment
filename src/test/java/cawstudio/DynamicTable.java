package cawstudio;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DynamicTable {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		WebDriverManager.chromedriver().setup();
		WebDriver driver=new ChromeDriver();
		driver.get("https://testpages.herokuapp.com/styled/tag/dynamic-table.html");
		driver.findElement(By.xpath("//*[text()='Table Data']")).click();
		driver.findElement(By.id("jsondata")).clear();
		JSONArray data=getJsonData(driver);
		driver.findElement(By.id("jsondata")).sendKeys(data.toString());
		driver.findElement(By.id("refreshtable")).click();
		for(int i=0;i<5;i++)
		{
			List<WebElement> list=driver.findElements(By.xpath("//table//tr["+(i+2)+"]//td"));
			String value="{\"gender\":\""+list.get(0).getText()+"\",\"name\":\""+list.get(1).getText()+"\",\"age\":"+list.get(2).getText()+"}";
			Assert.assertEquals(data.get(i).toString(),value);
			System.out.println(list.get(1).getText()+" data matches");
			
		}
		driver.close();}

	public static JSONArray getJsonData(WebDriver driver) throws Exception
	{
		JSONParser parser=new JSONParser();
		JSONArray array=(JSONArray) JSONValue.parse(new FileReader("./properties/TestData.JSON"));	
		return array;

	}

}

