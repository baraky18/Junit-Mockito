package com.mockito.example;

import java.util.ArrayList;
import java.util.List;

import com.mockito.example.intf.TodoService;

public class TodoBusinessImpl {

	private TodoService todoService;

	public TodoBusinessImpl(TodoService todoService) {
		this.todoService = todoService;
	}
	
	public List<String> retrieveTodosRelatedToSpring(String user){
		List<String> filterTodos = new ArrayList<String>();
		List<String> todos = todoService.retrieveTodos(user);
		for(String todo: todos) {
			if(todo.contains("Spring")) {
				filterTodos.add(todo);
			}
		}
		return filterTodos;
	}
}
