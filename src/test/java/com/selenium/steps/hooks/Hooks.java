package com.selenium.steps.hooks;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.selenium.base.WebDriverBase;
import com.selenium.common.Reports;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class Hooks extends WebDriverBase{
	
	private static boolean scenarioComplete = false;
	public static Reports report;
	
	@Before
	public void onSetup(Scenario scenario){
		report = new Reports();
		report.startTest(scenario.getName());
		getDriverInstance().manage().window().maximize();
		getDriverInstance().manage().deleteAllCookies();
		if(!scenarioComplete){
			final WebDriver driver = getDriverInstance();
			Runtime.getRuntime().addShutdownHook(new Thread(){
				public void run(){
					try{}catch(Exception e){
						e.printStackTrace();
					} finally {
						driver.quit();
					}
				}
			});
			scenarioComplete = true;
		}
	}
	
	@After
	public void onTearDown(Scenario scenario){
		//Reports report = PageBase.endReport();
		report.endTest();
		try{
			if(scenario.isFailed()){
				final byte[] screenshot = ((TakesScreenshot)getDriverInstance()).getScreenshotAs(OutputType.BYTES);
				scenario.embed(screenshot, "image/png");
			}
		}finally{
			if(getDriverInstance().getWindowHandles().size()>1){
				getDriverInstance().close();
				getDriverInstance().quit();
			}
		}
		try{
			switchToFirstWindow();
		}catch(Exception e){}
		
	}
	

}
