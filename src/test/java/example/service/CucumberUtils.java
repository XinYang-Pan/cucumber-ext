package example.service;

import io.github.xinyangpan.cucumber.util.ElementUtils;
import io.github.xinyangpan.models.person.City;

public class CucumberUtils {

	static {
		ElementUtils.TYPE_CONVERTER.registerCustomEditor(City.class, new CityEditor());
	}

}
