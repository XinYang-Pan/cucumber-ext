package example.service;

import java.beans.PropertyEditorSupport;

import org.blueo.models.person.City;

public class CityEditor extends PropertyEditorSupport {

	@Override
	public String getAsText() {
		City city = (City) this.getValue();
		return String.format("%s/%s", city.getProvince(), city.getName());
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		try {
			String[] strings = text.split("/");
			setValue(new City(strings[0], strings[1]));
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
	}

}
