package example.test.step;

import java.util.List;

import org.blueo.cucumber.assertj.hard.AssertjAssertions;
import org.blueo.cucumber.element.Element;
import org.blueo.models.person.Address;
import org.blueo.models.person.Person;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import example.service.ElementDataService;
import example.service.PersonService;

public class Steps {

	private PersonService personService = new PersonService();
	private ElementDataService elementDataService = new ElementDataService();

	@Given("^Add Person as following:$")
	public void add_Person_as_following(DataTable dataTable) throws Throwable {
		List<Person> persons = elementDataService.from(dataTable).getElements(this::newPerson);
		// 
		personService.add(persons);
		System.out.println(personService);
		System.out.println(persons);
	}

	@Then("^person should be like following:$")
	public void person_should_be_like_following(DataTable dataTable) throws Throwable {
		List<Element> elements = elementDataService.from(dataTable).getElements();
		// 
		List<Person> persons = personService.getAll();
		System.out.println(personService);
		System.out.println(persons);
		// 
		AssertjAssertions.assertThat(persons).isEachMatchToIgnoringOrder(elements);
	}

	private Person newPerson() {
		Person person = new Person();
		person.setAddress(new Address());
		return person;
	}

}
