package de.neuefische.interview.todoapp.core;

import reactor.core.publisher.Mono;

public interface TodoListService {
	
	Mono<TodoList> createTodoList();

	Mono<TodoList> addItem(String id, TodoItem map);

	Mono<TodoList> determineTodoList(String id);

	Mono<TodoList> updateItem(String id, TodoItem map);

}
