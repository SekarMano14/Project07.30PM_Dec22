package org.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {
	public static WebDriver driver;

	public static WebDriver launchBrowser(String browsername) {
		if (browsername.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else if (browsername.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else if (browsername.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		} else {
			System.err.println("Invalid Browser name");
			throw new WebDriverException();
		}
		return driver;

	}

//	public static WebDriver launchBrowser(String browsername) {
//		switch (browsername) {
//		case "chrome":
//			WebDriverManager.chromedriver().setup();
//			driver = new ChromeDriver();
//			break;
//		case "firefox":
//			WebDriverManager.firefoxdriver().setup();
//			driver = new FirefoxDriver();
//			break;
//		case "edge":
//			WebDriverManager.edgedriver().setup();
//			driver = new EdgeDriver();
//			break;
//
//		default:
//			System.err.println("Invalid Browser name");
//			throw new WebDriverException();
//		}
//		
//		return driver;
//	}

	public static void launchUrl(String url) {
		driver.get(url);
		driver.manage().window().maximize();
	}

	public static void implicitWait(long sec) {
		driver.manage().timeouts().implicitlyWait(sec, TimeUnit.SECONDS);
	}

	public static void sendKeys(WebElement e, String val) {
		e.sendKeys(val);
	}

	public static void click(WebElement e) {
		e.click();
	}

	public static void quitBrowser() {
		driver.quit();
	}

	public static String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	public static String getTitle() {
		return driver.getTitle();
	}

	public static String getText(WebElement e) {
		return e.getText();

	}

	public static String getAttribute(WebElement e) {
		return e.getAttribute("value");

	}
	public static void moveToElement(WebElement target) {
		Actions a = new Actions(driver);
		a.moveToElement(target).perform();
	}

	public static void draANdDrop(WebElement source, WebElement target) {
		Actions a = new Actions(driver);
		a.dragAndDrop(source, target).perform();
	}

	public static void selectByIndex(WebElement element, int index) {
		Select s = new Select(element);
		s.selectByIndex(index);

	}
	
	public static WebElement findElement(String locator,String locValue) {
		WebElement e=null;
		if(locator.equals("id")){
			 e = driver.findElement(By.id(locValue));
		}
		else if(locator.equals("name")) {
			 e = driver.findElement(By.name(locValue));
		}
		else if(locator.equals("xpath")) {
			 e = driver.findElement(By.xpath(locValue));
		}
		return e;


	}
	
	public static  String getDataFromExcel(String filename,String sheetname,int rowno,int cellno) throws IOException {
		File loc = new File(
				System.getProperty("user.dir")+"\\src\\test\\resources\\Excel\\"+filename+".xlsx");
		FileInputStream st = new FileInputStream(loc);
		Cell cell = new XSSFWorkbook(st).getSheet(sheetname).getRow(rowno).getCell(cellno);
		// check the cell type
		int type = cell.getCellType();
		// type -1 -->String
		// type - 0-->Numbers,Date
		String value=null;
		if (type == 1) {
			 value = cell.getStringCellValue();
		} else {
			if (DateUtil.isCellDateFormatted(cell)) {
				 value = new SimpleDateFormat("dd-MMM-yyyy").format( cell.getDateCellValue());
			} else {
				 value = String.valueOf((long) cell.getNumericCellValue());			}		}
		return value;	}
	
	public static void main(String[] args) throws IOException {
		String dt = getDataFromExcel("Data", "new", 2, 0);
		System.out.println(dt);	}}