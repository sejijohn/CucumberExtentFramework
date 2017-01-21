package com.selenium.steps;

import com.selenium.base.PageBase;
import com.selenium.base.WebDriverBase;
import com.selenium.common.Constants;
import com.selenium.page.pages.HomePage;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import junit.framework.Assert;

public class HomeSteps extends WebDriverBase{
	HomePage homePage;
	
	public HomeSteps(){
		homePage = new HomePage();
	}
	
	@Given("^a member is navigated to \"([^\"]*)\" website$")
	public void a_member_is_navigated_to_website(String siteUrl) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println(Constants.filePath);
		homePage.navigateToSiteURL(siteUrl);
	   
	}

	@When("^a member is in home page$")
	public void a_member_is_in_home_page() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		homePage.isAppleIconDisplayed();
	    
	}

	@Then("^a member verifies the \"([^\"]*)\" of page$")
	public void a_member_verifies_the_title_of_page(String title) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		PageBase.verifyTitle(title);
	    
	}
	
	@Then("^a member closes the broswer$")
	public void a_member_closes_the_broswer() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    getDriverInstance().quit();
	}
	
	@When("^a member clicks Mac link$")
	public void a_member_clicks_Mac_link() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		homePage.clickMacLink();
	    
	}

	@When("^a member clicks Buy link$")
	public void a_member_clicks_Buy_link() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		homePage.clickBuyLink();
	    
	}

	@Then("^a member clicks 15-inch button$")
	public void a_member_clicks_inch_button() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		homePage.click15InchButton();
	    
	}

	@Then("^a member is able to verify \"([^\"]*)\" is displayed$")
	public void a_member_is_able_to_verify_is_displayed(String inputText) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		homePage.verifyText(inputText);
	    
	}
	
	@Then("^a member clicks Select button$")
	public void a_member_clicks_Select_button() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    homePage.clickSelectHighEnd();
	}

	@Then("^a member verifies first option is selected$")
	public void a_member_verifies_first_option_is_selected() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		homePage.verify27ConfigSelected();
		
	    
	}
	

}
