package ca.mcgill.ecse321.eventregistration.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.eventregistration.model.Person;

public interface PersonRepository extends CrudRepository<Person, String>{

	Person findPersonByName(String name);	// spring will implement this for us, standardiwing of name of function, spring recognize the function NAME == MAGICCC
	// check bookmarks in safari to know naming conventions.

}