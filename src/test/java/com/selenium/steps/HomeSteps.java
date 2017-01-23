package com.selenium.steps;

import com.selenium.page.pages.HomePage;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class HomeSteps{
	HomePage homePage;
	
	public HomeSteps(){
		homePage = new HomePage();
	}
	


	@When("^a member is in home page$")
	public void a_member_is_in_home_page() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		homePage.isAppleIconDisplayed();
	    
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
