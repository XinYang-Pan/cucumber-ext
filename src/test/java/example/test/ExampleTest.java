package example.test;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
//@formatter:off
@CucumberOptions(
		strict = true, 
//		features = "src/main/java/cucumber/unify/feature/",
		plugin = { "pretty", "html:target/cucumber" }
		)
//@formatter:on
public class ExampleTest {

}