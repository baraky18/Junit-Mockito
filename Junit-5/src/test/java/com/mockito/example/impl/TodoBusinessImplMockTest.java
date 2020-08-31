package com.mockito.example.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.mockito.example.TodoBusinessImpl;
import com.mockito.example.intf.TodoService;

/*
 * this class uses Mockito to mock TodoService class 
 */
public class TodoBusinessImplMockTest {

	@Test
	@DisplayName("retrieve todos related to Spring using a mock that returns 2 values")
	public void testRetrieveTodosRelatedToSpringUsingAMock() {
		TodoService todoServiceMock = Mockito.mock(TodoService.class);
		List<String> todos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Dance");
		Mockito.when(todoServiceMock.retrieveTodos("Dummy")).thenReturn(todos);
		TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);
		List<String> filteredTodos = todoBusinessImpl.retrieveTodosRelatedToSpring("Dummy");
		assertEquals(2, filteredTodos.size());
	}
	
	@Test
	@DisplayName("retrieve todos related to Spring using a mock that returns empty values and then 2 values")
	public void testRetrieveTodosRelatedToSpringUsingAMockWithEmptyList() {
		TodoService todoServiceMock = Mockito.mock(TodoService.class);
		List<String> zeroTodos = Arrays.asList();
		List<String> twoTodos = Arrays.asList("Learn Spring MVC", "Learn Spring");
		Mockito.when(todoServiceMock.retrieveTodos("Dummy")).thenReturn(zeroTodos).thenReturn(twoTodos);
		TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);
		List<String> filteredTodosZero = todoBusinessImpl.retrieveTodosRelatedToSpring("Dummy");
		List<String> filteredTodosTwo = todoBusinessImpl.retrieveTodosRelatedToSpring("Dummy");
		assertEquals(0, filteredTodosZero.size());
		assertEquals(2, filteredTodosTwo.size());
	}
	
	@Test
	@DisplayName("retrieve todos related to Spring using a mock that's invoked with any String")
	public void testRetrieveTodosRelatedToSpringUsingAMockGettingAnyString() {
		TodoService todoServiceMock = Mockito.mock(TodoService.class);
		List<String> todos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Dance");
		Mockito.when(todoServiceMock.retrieveTodos(Mockito.anyString())).thenReturn(todos);
		TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);
		List<String> filteredTodos = todoBusinessImpl.retrieveTodosRelatedToSpring("just any String");
		assertEquals(2, filteredTodos.size());
	}
	
	@Test
	@DisplayName("retrieve exception using a mock")
	public void testRetrieveExceptionUsingAMock() {
		TodoService todoServiceMock = Mockito.mock(TodoService.class);
		Mockito.when(todoServiceMock.retrieveTodos(Mockito.anyString())).thenThrow(new RuntimeException());
		TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);
		assertThrows(RuntimeException.class, () -> todoBusinessImpl.retrieveTodosRelatedToSpring("just any String"));
	}
}
