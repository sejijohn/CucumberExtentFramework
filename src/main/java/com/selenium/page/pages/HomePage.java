package com.selenium.page.pages;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.selenium.base.PageBase;
import com.selenium.page.enums.PageTitleEnum;

public class HomePage extends PageBase{
	
	@FindBy(xpath = "//a[@id='ac-gn-firstfocus']")
	private WebElement appleIcon;
	
	public WebElement getAppleIcon(){
		elementDetails.put(waitForElement(appleIcon), "Apple Icon");
		return appleIcon;
	}
	
	@FindBy(linkText = "Mac")
	private WebElement macLinkText;
	
	public WebElement getMacLink(){
		elementDetails.put(waitForElement(macLinkText), "Mac Link");
		return macLinkText;
	}
	
	@FindBy(linkText = "Buy")
	private WebElement buyLinkText;
	
	public WebElement getBuyLink(){
		elementDetails.put(waitForElement(buyLinkText), "Buy Link");
		return buyLinkText;
	}
	
	@FindBy(xpath = "//button[@data-toggle-key='15inch']")
	//@FindBy(xpath = "//span/button[contains(text(),'15-inch')]")
	private WebElement inch15Button;
	
	public WebElement get15InchButton(){
		elementDetails.put(waitForElement(inch15Button), "15 Inch Tab");
		return waitForElement(inch15Button);
	}
	
	@FindBy(xpath = "//div[@data-part='MACBOOKPRO15']/h2")
	private WebElement inch15Text;
	
	public WebElement get15InchElement(){
		elementDetails.put(waitForElement(inch15Text), "15-inch MacBook Pro");
		return inch15Text;
	}
	
	//@FindBy(xpath = "/descendant::button[@title='Proceed to Configuration page'][2]")
	//@FindBy(xpath = "(//span[contains(text(),'Select')])[2]")
	//@FindBy(xpath = "(//button[@type='submit'])[2]")
	@FindBy(xpath = "//span[contains(text(),'Select')]")
	private List<WebElement> selectHighEnd;
	
	public WebElement getselectHighEnd(){
		List<WebElement> ls = selectHighEnd;
		elementDetails.put(ls.get(9), "Select button for 2.7");
	    return waitForElement(ls.get(9));
	}
	
	@FindBy(xpath = "//fieldset/ul/li[1]/input[@id='processor__dummy_z0sh_065_c416_1']")
	private WebElement select27Config;
	
	public WebElement getSelect27Config(){
		elementDetails.put(waitForElement(select27Config), "2.7 Processor details");
		return waitForElement(select27Config);
	}
	
	
	public void isAppleIconDisplayed(){
		verifyElementExists(getAppleIcon());
	}
	
	public void clickAppleIcon(){
		clickElement(getAppleIcon());
		
	}
	
	public void clickMacLink(){
		clickElement(getMacLink());
	}
	
	public void clickBuyLink(){
		clickElement(getBuyLink());
	}
	
	public void click15InchButton(){
		clickElement(get15InchButton());
	}
	
	public void verifyText(String inputText){
		verifyTextPresent(get15InchElement(),PageTitleEnum.valueOf(inputText).getInfo().getTitle());
	}
	
	public void clickSelectHighEnd(){
		/*JavascriptExecutor jse = (JavascriptExecutor)getDriverInstance();
		jse.executeScript("window.scrollBy(0,700)", "");*/
		clickElement(getselectHighEnd());
	}
	
	public boolean is27ConfigSelected(){
		
		String isChecked = getSelect27Config().getAttribute("checked");
		if (isChecked!=null)
			return true;
		else
			return false;
		
	}
	
	public void verify27ConfigSelected(){
		Assert.assertTrue(is27ConfigSelected());
		System.out.println("2.7 Ghz selection verified");
		//report.endTest();
	}
	
}
