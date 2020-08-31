package com.mockito.example.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.mockito.example.TodoBusinessImpl;
import com.mockito.example.intf.TodoService;

/*
 * this class uses a stub class that I created in order to return values.
 */
public class TodoBusinessImplStubTest {

	@Test
	public void testRetrieveTodosRelatedToSpringUsingAStub() {
		TodoService TodoServiceStub = new TodoServiceStub();
		TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(TodoServiceStub);
		List<String> filteredTodos = todoBusinessImpl.retrieveTodosRelatedToSpring("Dummy");
		assertEquals(2, filteredTodos.size());
	}
}
