package todos;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AutomatedTestCasesForToDoApplication {
	WebDriver driver;

	@BeforeClass
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://todo.scriveqa.com/");
		driver.manage().window().maximize();
	}

	@Test(priority = 1)
	public void TC001_taskCreation() throws InterruptedException {
		driver.findElement(By.xpath("//input[@placeholder='Add new']")).sendKeys("Welcome");
		Actions act = new Actions(driver);
		act.sendKeys(Keys.ENTER).perform();
		Thread.sleep(2000);  //Intensionally aded to see the task created
		boolean chk = driver.findElement(By.xpath("//input[@type='checkbox' and @value='on']")).isDisplayed();
		WebElement taskName = driver.findElement(By.xpath("//label[text()='Welcome']"));
		if ((chk == true) && (taskName.getText().equals("Welcome"))) {
			System.out.println("New task is created successfully");
		}
	}

	@Test(priority = 2)
	public void TC010_toCancelEscKeyboard() throws InterruptedException {
		String verifyText = driver.findElement(By.xpath("//p[text()='Press `Esc` to cancel.']")).getText();
		System.out.println(verifyText + " text is displayed");

		boolean addNewField = driver.findElement(By.xpath("//input[@placeholder='Add new']")).isDisplayed();
		Assert.assertEquals(addNewField, true);
		Actions act = new Actions(driver);
		act.sendKeys(Keys.ESCAPE).perform();

		Thread.sleep(2000);  //Intentionally added to see the text displayed after pressing ESC key from keyboard

		verifyText = driver.findElement(By.xpath("//p[text()='Press `/` to search and `N` to create a new item.']"))
				.getText();

		if (verifyText.equals("Press `/` to search and `N` to create a new item.")) {
			System.out.println("User is able to press ESC key from keyboard");
			
		}

		else

			System.out.println(" Pressing ESC key from keyboard did not work");
	}
	
	
	@Test(priority=3)
	public void TC013_DuplicatesNamesAllowed()
	{
		driver.findElement(By.xpath("//a[@title='Add New']")).click();
		for(int i=1;i<=2;i++)
		{
		driver.findElement(By.xpath("//input[@placeholder='Add new']")).sendKeys("Hello");
		Actions act = new Actions(driver);
		act.sendKeys(Keys.ENTER).perform();
		}
		
		List<WebElement> taskN= driver.findElements(By.xpath("//label[text()='Hello']"));
		for(WebElement name:taskN)
		{
			System.out.println("Below are the duplicate task names created");
			System.out.println(name.getText());
		}
		

	}

	@AfterClass
	public void tearDown() {
		driver.close();
	}
}
