package de.neuefische.interview.todoapp.core;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
class DefaultTodoListService implements TodoListService {

	private final TodoListRepository todoListRepository;

	@Override
	public Mono<TodoList> addItem(String id, TodoItem item) {
		return todoListRepository.determineTodoList(id)
				.map(currentlySaved -> currentlySaved.addItem(item))
				.flatMap(todoListRepository::save);
	}

	@Override
	public Mono<TodoList> createTodoList() {
		return todoListRepository.createTodoList();
	}

	@Override
	public Mono<TodoList> determineTodoList(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<TodoList> updateItem(String id, TodoItem item) {
		return todoListRepository.determineTodoList(id)
				.map(currentlySaved -> currentlySaved.updateItem(item))
				.flatMap(updatedList -> todoListRepository.save(updatedList));
	}
	
}
