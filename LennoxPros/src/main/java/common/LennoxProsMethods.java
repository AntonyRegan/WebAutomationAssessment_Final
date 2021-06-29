package common;

import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import utils.DataInputProvider;

public class LennoxProsMethods extends SeleniumMethods{
	public String browserName;
	public String dataSheetName;
	public String linkName;
	public String pageNavigation;
	public String pageDescription;
	public static String runningTestcaseName ;
	DataInputProvider dp = null;
	
	@BeforeSuite
	public void initializeReport() {
		startResult();
		dataSheetName = prop.getProperty("DataSheetName");
		System.out.println("Data Sheet Name in Selenium Methods = "+dataSheetName);
	}
	
	@BeforeClass
	public void initializeTestModule() {
		getCurrentTestcaseName();
		
		  dp = new DataInputProvider(dataSheetName); String executionMode =
		  dp.getCellData("Execution", runningTestcaseName);
		  System.out.println("Execution Mode === "+executionMode);
		  if(executionMode.equalsIgnoreCase("N")) { 
			  throw new SkipException("Skipped test case = "+runningTestcaseName); 
		} else {
		 

		setData();
		
			startTestModule(testCaseName, testDescription);
		}
	}
	
	@BeforeMethod
	public void beforeMethod(){
		
		/*
		 * data = new DataInputProvider(dataSheetName); String executionMode =
		 * data.getCellData("Execution", runningTestcaseName);
		 * System.out.println("Execution Mode === "+executionMode);
		 * if(executionMode.equalsIgnoreCase("N")) { throw new
		 * SkipException("Skipped test case = "+runningTestcaseName); } else {
		 */
		test = startTestCase(testNodes);
		//test.assignCategory(category);
		//test.assignAuthor(authors);
		//System.out.println("Lennox Pros methods link name == "+DataInputProvider.getCellData("LennoxPros", "LinkNme", 2));
		startApp(browserName);
		 //}
	}
	
	
	public void getCurrentTestcaseName()
	{
		String name = this.getClass().getName();
		System.out.println("Get current Class Name === "+name);
		String[] split = name.split("[.]");
		int num = split.length;
		runningTestcaseName= split[num-1];
	    System.out.println("Test name: " + runningTestcaseName);   
	   	  
	}
	
	public static String returnTestScriptName() {
		return runningTestcaseName;
	}
	
	@AfterSuite
	public void afterSuite(){
		endResult();
	}
	
	@AfterMethod
	public void afterMethod(){
		closeAllBrowsers();
	}
	
	public void setData() {
		dp = new DataInputProvider(dataSheetName);
		String browserNameData = dp.getCellData("Browser", runningTestcaseName);
		String testCaseID = dp.getCellData("TestCaseID", runningTestcaseName);
		String testCaseDesc = dp.getCellData("TestCaseDescription", runningTestcaseName);


		System.out.println("Running test case "+runningTestcaseName);
		System.out.println("test case id "+testCaseID);
		System.out.println("test case description == "+testCaseDesc);
		System.out.println("Data sheet name in Test case 1 == "+dataSheetName);
		testCaseName=testCaseID;
		testDescription=testCaseDesc; 
		testNodes="Test LennoxPros";
		browserName = browserNameData;
	}
	/* @DataProvider(name="fetchData") */
	public  Object[][] getData(){
		dp = new DataInputProvider(dataSheetName);
		return dp.getTestData();		
	}
}
