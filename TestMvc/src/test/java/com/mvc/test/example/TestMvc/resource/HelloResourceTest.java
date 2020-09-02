package com.mvc.test.example.TestMvc.resource;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.mvc.test.example.TestMvc.service.HelloService;

@ExtendWith(SpringExtension.class)
public class HelloResourceTest {

	private MockMvc mockMvc;
	
	@InjectMocks
	private HelloResource helloResource;
	
	@Mock
	private HelloService helloservice;
	
	@BeforeEach
	public void init() throws Exception{
		mockMvc = MockMvcBuilders.standaloneSetup(helloResource).build();
	}
	
	@Test
	public void testHelloWorld() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/hello"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().string("Hello world!"));
	}
	
	@Test
	public void testGreeting() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/greeting")
			.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Greetings")))
			.andExpect(MockMvcResultMatchers.jsonPath("$.value", Matchers.is("Hi")))
			.andExpect(MockMvcResultMatchers.jsonPath("$.*", Matchers.hasSize(2)));
	}
	
	@Test
	public void testHelloWorldService() throws Exception{
		Mockito.when(helloservice.hello()).thenReturn("Hello");
		mockMvc.perform(MockMvcRequestBuilders.get("/helloservice"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().string("Hello"));
		Mockito.verify(helloservice).hello();
	}
	
	@Test
	public void testPostGreeting() throws Exception{
		String json = "{\n" +
				" \"title\": \"Greetings\",\n" +
				" \"value\": \"Hello World\"\n" + "}";
		mockMvc.perform(MockMvcRequestBuilders.post("/postgreeting")
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.content(json)
			.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Greetings")))
			.andExpect(MockMvcResultMatchers.jsonPath("$.value", Matchers.is("Hello World")))
			.andExpect(MockMvcResultMatchers.jsonPath("$.*", Matchers.hasSize(2)));
	}
}
