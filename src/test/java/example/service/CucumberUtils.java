package example.service;

import org.blueo.cucumber.util.ElementUtils;
import org.blueo.models.person.City;

public class CucumberUtils {

	static {
		ElementUtils.TYPE_CONVERTER.registerCustomEditor(City.class, new CityEditor());
	}

}
