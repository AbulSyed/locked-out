package com.syed.authservice;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/functionalTest/resources/features"},
        glue = "com.syed.authservice.glue"
)
public class CucumberIntegrationTest {
}
