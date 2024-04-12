package com.bha.runner;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.bha.ui.steps"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/htmlReport.html",
                "json:target/cucumber-reports/CucumberTestReport.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        }
)
public class RunCucumberTest extends AbstractTestNGCucumberTests {
}
