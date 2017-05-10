package io.github.xinyangpan.cucumber.tool;

import org.junit.Test;

import io.github.xinyangpan.models.person.Person;

public class DataTableGeneratorTest {

	@Test
	public void test() {
		DataTableGenerator.generate(Person.class, PropertyCollectingType.PROPERTY_DESC);
		DataTableGenerator.generate(Person.class, PropertyCollectingType.DECLARED_FIELD);
	}

}
