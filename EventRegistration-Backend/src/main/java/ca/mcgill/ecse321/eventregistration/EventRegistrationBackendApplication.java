package ca.mcgill.ecse321.eventregistration;

import org.springframework.boot.autoconfigure.SpringBootApplication; // allows gradle to know we re using springboot app
import org.springframework.boot.SpringApplication; // need to run sopring app using framework spring
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping; //all part of spring framework

@RestController //allows us to access rets endpoint in our application
@SpringBootApplication
public class EventRegistrationBackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(EventRegistrationBackendApplication.class, args);
  }
  
// Request map
  @RequestMapping("/") //http request mapping for hello world, lead us to hello world page
  public String greeting(){
    return "Hello world!";
  }

}
