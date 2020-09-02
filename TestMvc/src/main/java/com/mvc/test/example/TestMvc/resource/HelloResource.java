package com.mvc.test.example.TestMvc.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mvc.test.example.TestMvc.repository.User;
import com.mvc.test.example.TestMvc.repository.UserRepository;
import com.mvc.test.example.TestMvc.service.HelloService;

@RestController
public class HelloResource {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private HelloService helloService;
	
	@RequestMapping("/users")
	public List<User> users() {
		return userRepository.findAll();
	}
	
	@RequestMapping("/hello")
	public String helloWorld() {
		return "Hello world!";
	}
	
	@RequestMapping("/helloservice")
	public String helloService() {
		return helloService.hello();
	}
	
	@RequestMapping(value="/greeting", produces=org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public Greeting greeting() {
		return new Greeting("Greetings", "Hi");
	}
	
	@PostMapping(value="/postgreeting", produces=org.springframework.http.MediaType.APPLICATION_JSON_VALUE, 
			consumes=org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public Greeting postGreeting(@RequestBody Greeting greeting) {
		return greeting;
	}
	
	public static class Greeting{
		private String title;
		private String value;
		
		public Greeting() {}
		
		public Greeting(String title, String value) {
			this.title = title;
			this.value = value;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
	}
}
