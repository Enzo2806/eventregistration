package ca.mcgill.ecse321.eventregistration.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.eventregistration.model.Event;

public interface EventRepository extends CrudRepository<Event, String> {	// firts arg is name of class, second ID tag => set to name of type String could find event ny date too.

	Event findEventByName(String name);	// could be by date findEventByDate(Date date)...

}