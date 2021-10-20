package de.neuefische.interview.todoapp.core;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TodoListService {

	Flux<TodoList> determineTodoLists();
	
	Mono<TodoList> createTodoList();

	Mono<TodoList> addItem(String id, TodoItem map);

	Mono<TodoList> determineTodoList(String id);

	Mono<TodoList> updateItem(String id, TodoItem map);

}
