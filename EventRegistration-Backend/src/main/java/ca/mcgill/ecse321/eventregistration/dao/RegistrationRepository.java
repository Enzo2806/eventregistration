package ca.mcgill.ecse321.eventregistration.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;	// extend repository 

import ca.mcgill.ecse321.eventregistration.model.Event;
import ca.mcgill.ecse321.eventregistration.model.Person;
import ca.mcgill.ecse321.eventregistration.model.Registration;

public interface RegistrationRepository extends CrudRepository<Registration, Integer> {	//second paramter, ID is an int.

	List<Registration> findByPerson(Person personName);	

	boolean existsByPersonAndEvent(Person person, Event eventName);

	Registration findByPersonAndEvent(Person person, Event eventName);

}