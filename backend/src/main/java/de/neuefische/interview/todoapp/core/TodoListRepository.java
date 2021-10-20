package de.neuefische.interview.todoapp.core;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TodoListRepository {

	Flux<TodoList> determineTodoLists();
	
	Mono<TodoList> createTodoList();
	
	Mono<TodoList> determineTodoList(String id);

	Mono<TodoList> save(TodoList todoList);

}
