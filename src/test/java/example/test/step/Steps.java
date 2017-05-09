package example.test.step;

import java.util.List;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import example.core.ElementDataService;
import example.service.PersonService;
import io.github.xinyangpan.cucumber.assertj.hard.AssertjAssertions;
import io.github.xinyangpan.cucumber.element.BaseElement;
import io.github.xinyangpan.cucumber.keyword.AssertExpect;
import io.github.xinyangpan.models.person.Person;

public class Steps {

	private PersonService personService = new PersonService();
	private ElementDataService elementDataService = new ElementDataService();

	@Given("^Add Person as following:$")
	public void add_Person_as_following(DataTable dataTable) throws Throwable {
		List<Person> persons = elementDataService.from(dataTable).getElements(Person.class);
		// 
		System.out.println(String.format("Saving - %s", persons));
		personService.add(persons);
	}

	@Then("^Person should be like following:$")
	public void person_should_be_like_following(DataTable dataTable) throws Throwable {
		BaseElement baseElement = elementDataService.from(dataTable).getOnlyElement();
		// 
		List<Person> persons = personService.getAll();
		System.out.println(String.format("Asserting"));
		System.out.println(String.format("Actual - %s", persons));
		System.out.println(String.format("Expected - %s", baseElement));
		// 
		AssertjAssertions.assertThat(persons).hasSize(1);
		AssertjAssertions.assertThat(persons.get(0)).isMatchTo(baseElement);
	}

	@Then("^Failedable - Person should be like following:([A-Za-z0-9./\\s\\(\\)]*)$")
	public void person_should_be_like_following(AssertExpect assertExpect, DataTable dataTable) throws Throwable {
		BaseElement baseElement = elementDataService.from(dataTable).getOnlyElement();
		// 
		List<Person> persons = personService.getAll();
		System.out.println(String.format("Asserting"));
		System.out.println(String.format("Actual - %s", persons));
		System.out.println(String.format("Expected - %s", baseElement));
		// 
		assertExpect.asserting(() -> {
			AssertjAssertions.assertThat(persons).hasSize(1);
			AssertjAssertions.assertThat(persons.get(0)).isMatchTo(baseElement);
		});
	}

}
