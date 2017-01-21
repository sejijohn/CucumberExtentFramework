package com.selenium.runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "html:target/cucumber", "json:target/cucumber.json"},
features = "src/test/resources",
glue = "com.selenium.steps",
tags = {"@TC1, @TC2"})

public class RunnerTest {

}
