package testrunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = {"src/test/resources/feature/Demo.feature"}
        ,glue = "stepdefinition"
        ,tags = "@Demo1"
        ,monochrome = true
        ,publish = true
)
public class TestRunner extends AbstractTestNGCucumberTests {
}
