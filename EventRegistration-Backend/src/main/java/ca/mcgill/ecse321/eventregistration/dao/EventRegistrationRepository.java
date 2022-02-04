package ca.mcgill.ecse321.eventregistration.dao; // Dao = data 

import java.sql.Date;	// Date time => differnet format from java
import java.sql.Time;
import java.util.List;

import javax.persistence.EntityManager;		// handles entities => add entities 
import javax.persistence.TypedQuery;		// where we generate the querries we use to access info from DB

import org.springframework.beans.factory.annotation.Autowired;	
import org.springframework.stereotype.Repository;	//declares its a repository
import org.springframework.transaction.annotation.Transactional;	

import ca.mcgill.ecse321.eventregistration.model.Person;	// model of person and event (class diagram)
import ca.mcgill.ecse321.eventregistration.model.Event;

@Repository	//other name for source of data
public class EventRegistrationRepository {

	@Autowired	// put before entitymanager
	EntityManager entityManager;

	@Transactional	//transactional methods, to trasnfer to database
	public Person createPerson(String name) {	// takes string name because person has name specified by model
		Person p = new Person();		// import person model because we use inheritance constructor here
		p.setName(name);		//use it from model as well
		entityManager.persist(p);	// persist is already defined, creates the type query.
		return p;
	}

	@Transactional
	public Person getPerson(String name) {		// retrieves somone fromn database
		Person p = entityManager.find(Person.class, name);
		return p;	
	}
	// same things for events
	@Transactional
	public Event createEvent(String name, Date date, Time startTime, Time endTime) {	// tahts why we used sql date and time to avoid conflicts.
		Event e = new Event();
		e.setName(name);
		e.setDate(date);
		e.setStartTime(startTime);
		e.setEndTime(endTime);
		entityManager.persist(e);
		return e;
	}

	@Transactional
	public Event getEvent(String name) {
		Event e = entityManager.find(Event.class, name);
		return e;
	}
	
	@Transactional 		// get event by a particular date, need to create that method. need to use typed query. need to generate it ourseleves
	public List<Event> getEventsBeforeADeadline(Date deadline) {
		TypedQuery<Event> q = entityManager.createQuery("select e from Event e where e.date < :deadline",Event.class);	//manually enter the sql query. Probelm is we have to manually do it every time we create a query
		// thats a drawback from doing everything in one single language. 
		q.setParameter("deadline", deadline);	// why dont add it to string? because SQL injection. Risk someone to enter query and get out all of databse content.
		List<Event> resultList = q.getResultList();	//launch query in SQL database
		return resultList;	// returns list of events
	}

}