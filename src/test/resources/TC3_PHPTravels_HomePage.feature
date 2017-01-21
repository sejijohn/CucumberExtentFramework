#Author: seji.john07@gmail.com
#Keywords Summary : Test PHP Travles home page
@TC3
Feature: Verify PHP Travles home page


Scenario: Verify a member is in php travles home page
Given a member is navigated to "PHPTravels" website
	And a member verifies the "PHPTravles" of page
When I complete action
	And some other action
	And yet another action
Then I validate the outcomes
	And check more outcomes

@tag2
Scenario Outline: Title of your scenario outline
Given I want to write a step with <name>
When I check for the <value> in step
Then I verify the <status> in step

Examples:
    | name  |value | status |
    | name1 |  5   | success|
    | name2 |  7   | Fail   |
