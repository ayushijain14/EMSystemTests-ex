package com.expense.tests;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.Field;
import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.JiraClient;



public class ExpenseManagerSystemTest {
	
	WebDriver driver;
	
	@BeforeMethod
	public void beforeTest() {
		
	
		driver = new HtmlUnitDriver();
		
		//Thread.sleep(600000);	
	}
	
	
	@Test
	public void titleTest1() throws Exception{
		
		driver.get("http://localhost:8089/ExpenseApp-1/login.jsp");	
		
		
		//Buggy Code
		//String expectedTitle = "Hello Page";
		
		//Correct Code
		String expectedTitle = "Login page";
		
		String actualTitle = driver.getTitle();
		Assert.assertEquals(expectedTitle, actualTitle);
        System.out.println(actualTitle);
	}
	
	@AfterMethod
	public void afterTest(ITestResult result) throws Exception {
		
		driver.quit();
		
        if(result.getStatus() == ITestResult.FAILURE) {
			//You need to give your JIRA Username and Password in below line
        	BasicCredentials cred = new BasicCredentials("Ayushi","Ayushi");
			
			//You need to update your JIRA machine IP address with port 8080 in below line
        	JiraClient jira = new JiraClient("http://34.204.99.183:8080/", cred);
			
        	Issue issueName = jira.createIssue("EX", "Bug").field(Field.SUMMARY, result.getMethod().getMethodName() +"is failed due to: "+ result.getThrowable().toString()).field(Field.DESCRIPTION, "get the description").execute();
        	System.out.println("Issue is created in Jira with Issue Key: "+issueName.getKey());
        }

	}
	
	
}
