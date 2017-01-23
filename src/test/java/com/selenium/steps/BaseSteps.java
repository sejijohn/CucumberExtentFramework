package com.selenium.steps;

import com.selenium.base.PageBase;
import com.selenium.base.WebDriverBase;
import com.selenium.common.Constants;
import com.selenium.page.pages.HomePage;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class BaseSteps extends WebDriverBase{
	
	private HomePage homePage;
	
	public BaseSteps(){
		homePage = new HomePage();
	}
	
	@Given("^a member is navigated to \"([^\"]*)\" website$")
	public void a_member_is_navigated_to_website(String siteUrl) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println(Constants.filePath);
		homePage.navigateToSiteURL(siteUrl);
	   
	}
	
	@Then("^a member verifies the \"([^\"]*)\" of page$")
	public void a_member_verifies_the_title_of_page(String title) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		PageBase.verifyTitle(title);
	    
	}
	
	@Given("^a member verifies the \"([^\"]*)\" in the page$")
	public void a_member_verifies_the_in_the_page(String imageInfo) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		PageBase.verifyImage(imageInfo);
	}

}
