package com.mockito.example.impl;

import java.util.Arrays;
import java.util.List;

import com.mockito.example.intf.TodoService;

public class TodoServiceStub implements TodoService {

	@Override
	public List<String> retrieveTodos(String user) {
		return Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Dance");
	}

}
