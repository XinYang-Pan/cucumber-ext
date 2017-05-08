package example.service;

import java.util.List;

import org.assertj.core.util.Lists;

import io.github.xinyangpan.models.person.Person;

public class PersonService {
	private List<Person> persons = Lists.newArrayList();

	public void add(Person person) {
		persons.add(person);
	}

	public void add(List<Person> persons) {
		this.persons.addAll(persons);
	}

	public List<Person> getAll() {
		return persons;
	}
	
}
