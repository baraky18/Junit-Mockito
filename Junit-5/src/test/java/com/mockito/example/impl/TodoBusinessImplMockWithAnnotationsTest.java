package com.mockito.example.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mockito.example.TodoBusinessImpl;
import com.mockito.example.intf.TodoService;

/*
 * this class uses Mockito annotations to mock TodoService class 
 */
@ExtendWith(MockitoExtension.class)
public class TodoBusinessImplMockWithAnnotationsTest {

	// this is creating the mock
	@Mock
	TodoService todoServiceMock;
	
	// this is injecting the mock into the desired object
	@InjectMocks
	TodoBusinessImpl todoBusinessImpl;
	
	@Test
	@DisplayName("retrieve todos related to Spring using a mock that returns 2 values")
	public void testRetrieveTodosRelatedToSpringUsingAMock() {
		List<String> todos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Dance");
		Mockito.when(todoServiceMock.retrieveTodos("Dummy")).thenReturn(todos);
		List<String> filteredTodos = todoBusinessImpl.retrieveTodosRelatedToSpring("Dummy");
		assertEquals(2, filteredTodos.size());
	}
	
	@Test
	@DisplayName("retrieve todos related to Spring using a mock that returns empty values and then 2 values")
	public void testRetrieveTodosRelatedToSpringUsingAMockWithEmptyList() {
		List<String> zeroTodos = Arrays.asList();
		List<String> twoTodos = Arrays.asList("Learn Spring MVC", "Learn Spring");
		Mockito.when(todoServiceMock.retrieveTodos("Dummy")).thenReturn(zeroTodos).thenReturn(twoTodos);
		List<String> filteredTodosZero = todoBusinessImpl.retrieveTodosRelatedToSpring("Dummy");
		List<String> filteredTodosTwo = todoBusinessImpl.retrieveTodosRelatedToSpring("Dummy");
		assertEquals(0, filteredTodosZero.size());
		assertEquals(2, filteredTodosTwo.size());
	}
	
	@Test
	@DisplayName("retrieve todos related to Spring using a mock that's invoked with any String")
	public void testRetrieveTodosRelatedToSpringUsingAMockGettingAnyString() {
		List<String> todos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Dance");
		Mockito.when(todoServiceMock.retrieveTodos(Mockito.anyString())).thenReturn(todos);
		List<String> filteredTodos = todoBusinessImpl.retrieveTodosRelatedToSpring("just any String");
		assertEquals(2, filteredTodos.size());
	}
	
	@Test
	@DisplayName("retrieve exception using a mock")
	public void testRetrieveExceptionUsingAMock() {
		Mockito.when(todoServiceMock.retrieveTodos(Mockito.anyString())).thenThrow(new RuntimeException());
		assertThrows(RuntimeException.class, () -> todoBusinessImpl.retrieveTodosRelatedToSpring("just any String"));
	}
}
