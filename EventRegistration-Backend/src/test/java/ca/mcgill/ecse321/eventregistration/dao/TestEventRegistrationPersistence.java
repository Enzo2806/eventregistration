package ca.mcgill.ecse321.eventregistration.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.eventregistration.model.Event;
import ca.mcgill.ecse321.eventregistration.model.Person;
import ca.mcgill.ecse321.eventregistration.model.Registration;

@ExtendWith(SpringExtension.class)	//legacy code
@SpringBootTest
public class TestEventRegistrationPersistence {

	@Autowired	//set entities nothing from outside is looking at this
	private PersonRepository personRepository;
	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private RegistrationRepository registrationRepository;

	@AfterEach	// after each run @test, it will run this clearDatabse
	public void clearDatabase() {
		// Fisrt, we clear registrations to avoid exceptions due to inconsistencies
		registrationRepository.deleteAll();	//all tests run independently registration are dependent on person and events, so delete it first !!
		// Then we can clear the other tables
		personRepository.deleteAll();
		eventRepository.deleteAll();
	}

	@Test
	public void testPersistAndLoadPerson() {
		String name = "TestPerson";
		// First example for object save/load
		Person person = new Person();
		// First example for attribute save/load
		person.setName(name);
		personRepository.save(person);	// save a perosn to DB

		person = null;

		person = personRepository.findPersonByName(name);	// Spring willl handle and give us a person of this name
		assertNotNull(person);	//didnt get error, usually error if null
		assertEquals(name, person.getName());
	}

	@Test
	public void testPersistAndLoadEvent() {
		String name = "ECSE321 Tutorial";
		Date date = java.sql.Date.valueOf(LocalDate.of(2020, Month.JANUARY, 31));
		Time startTime = java.sql.Time.valueOf(LocalTime.of(11, 35));
		Time endTime = java.sql.Time.valueOf(LocalTime.of(13, 25));
		Event event = new Event();
		event.setName(name);
		event.setDate(date);
		event.setStartTime(startTime);
		event.setEndTime(endTime);
		eventRepository.save(event);	// use save method, inheriteance across all repository, implemented for us but still custom. 

		event = null;

		event = eventRepository.findEventByName(name);

		assertNotNull(event);
		assertEquals(name, event.getName());
		assertEquals(date, event.getDate());
		assertEquals(startTime, event.getStartTime());
		assertEquals(endTime, event.getEndTime());
	}

	@Test
	public void testPersistAndLoadRegistration() {
		String personName = "TestPerson";	// create person and event to create registration becaus eof dependencies.
		Person person = new Person();
		person.setName(personName);
		personRepository.save(person);	// dont testb those cause we tested it earlier, also test indpenedent classes before

		String eventName = "ECSE321 Tutorial";
		Date date = java.sql.Date.valueOf(LocalDate.of(2020, Month.JANUARY, 31));
		Time startTime = java.sql.Time.valueOf(LocalTime.of(11, 35));
		Time endTime = java.sql.Time.valueOf(LocalTime.of(13, 25));
		Event event = new Event();
		event.setName(eventName);
		event.setDate(date);
		event.setStartTime(startTime);
		event.setEndTime(endTime);
		eventRepository.save(event);

		Registration reg = new Registration();	
		int regId = 1;
		// First example for reference save/load
		reg.setId(regId);
		reg.setPerson(person);
		reg.setEvent(event);
		registrationRepository.save(reg);

		reg = null;	// same thing, see if registration loads and saves correclty

		reg = registrationRepository.findByPersonAndEvent(person, event);
		assertNotNull(reg);
		assertEquals(regId, reg.getId());
		// Comparing by keys
		assertEquals(person.getName(), reg.getPerson().getName());
		assertEquals(event.getName(), reg.getEvent().getName());
	}

}