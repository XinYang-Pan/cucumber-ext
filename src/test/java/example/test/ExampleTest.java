package example.test;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import example.core.CucumberRunner;

@RunWith(CucumberRunner.class)
//@formatter:off
@CucumberOptions(
		strict = true, 
//		features = "src/main/java/cucumber/unify/feature/",
		plugin = { "pretty", "html:target/cucumber" }
		)
//@formatter:on
public class ExampleTest {

}
