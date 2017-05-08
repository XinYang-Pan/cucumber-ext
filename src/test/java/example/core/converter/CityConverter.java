package example.core.converter;

import org.springframework.core.convert.converter.Converter;

import io.github.xinyangpan.models.person.City;

public class CityConverter implements Converter<String, City> {

	@Override
	public City convert(String source) {
		String[] strings = source.split("/");
		return new City(strings[0], strings[1]);
	}

}
