#Author: seji.john07@gmail.com
#Keywords Summary : Buy 15 inch Mac

@TC2
Feature: Buy 15 inch Mac

Scenario: Verify user is able to buy 15 inch mac
Given a member is navigated to "Apple" website
When a member is in home page
And a member verifies the "Apple" of page
And a member clicks Mac link
And a member clicks Buy link
And a member clicks 15-inch button
And a member is able to verify "INCH15_MAC" is displayed
Then a member clicks Select button
And a member verifies first option is selected

