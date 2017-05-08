package example.test;

import java.io.IOException;

import org.junit.runners.model.InitializationError;

import cucumber.api.junit.Cucumber;
import example.service.converter.CityConverter;
import io.github.xinyangpan.cucumber.util.ElementUtils;

public class CucumberRunner extends Cucumber {

	static {
		// Some Custom Init Before Actual Run Cucumber, For Example - init ${APP_NAME} ${APP_ENV}
		System.setProperty("APP_NAME", "My App");
		System.setProperty("APP_ENV", "Test");
		// or Some Custom Converter
		ElementUtils.defaultConversionService().addConverter(new CityConverter());
	}

	public CucumberRunner(Class<?> clazz) throws InitializationError, IOException {
		super(clazz);
	}

}
