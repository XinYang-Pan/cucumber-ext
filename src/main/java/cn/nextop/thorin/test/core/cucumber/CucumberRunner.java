package cn.nextop.thorin.test.core.cucumber;

import static org.apache.commons.collections4.comparators.ComparableComparator.comparableComparator;

import java.io.IOException;
import java.math.BigDecimal;

import org.assertj.core.api.AbstractObjectAssert;
import org.junit.runners.model.InitializationError;

import cn.nextop.thorin.test.util.TestEnvUtils;
import cucumber.api.junit.Cucumber;

public class CucumberRunner extends Cucumber {

	static {
		TestEnvUtils.setCucumberEnvVar();
		// Change default BigDecimal Comparator
		AbstractObjectAssert.defaultTypeComparators().put(BigDecimal.class, comparableComparator());
	}

	public CucumberRunner(Class<?> clazz) throws InitializationError, IOException {
		super(clazz);
	}

}
