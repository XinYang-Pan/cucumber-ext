package example.test.step;

import java.util.List;

import com.google.common.reflect.Reflection;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import example.service.CityEditor;
import example.service.CucumberUtils;
import example.service.ElementDataService;
import example.service.PersonService;
import io.github.xinyangpan.cucumber.assertj.hard.AssertjAssertions;
import io.github.xinyangpan.cucumber.element.BaseElement;
import io.github.xinyangpan.cucumber.util.ElementUtils;
import io.github.xinyangpan.models.person.City;
import io.github.xinyangpan.models.person.Person;

public class Steps {
	
	static {
		Reflection.initialize(CucumberUtils.class);
		ElementUtils.TYPE_CONVERTER.registerCustomEditor(City.class, new CityEditor());
	}
	
	private PersonService personService = new PersonService();
	private ElementDataService elementDataService = new ElementDataService();

	@Given("^Add Person as following:$")
	public void add_Person_as_following(DataTable dataTable) throws Throwable {
		List<Person> persons = elementDataService.from(dataTable).getElements(Person.class);
		// 
		personService.add(persons);
		System.out.println(personService);
		System.out.println(persons);
	}

	@Then("^person should be like following:$")
	public void person_should_be_like_following(DataTable dataTable) throws Throwable {
		List<BaseElement> baseElements = elementDataService.from(dataTable).getElements();
		// 
		List<Person> persons = personService.getAll();
		System.out.println(personService);
		System.out.println(persons);
		// 
		AssertjAssertions.assertThat(persons).isEachMatchToIgnoringOrder(baseElements);
	}

}
