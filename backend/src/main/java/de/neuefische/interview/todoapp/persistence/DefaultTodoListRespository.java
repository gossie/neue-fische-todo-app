package de.neuefische.interview.todoapp.persistence;

import java.util.Collections;

import de.neuefische.interview.todoapp.core.TodoList;
import de.neuefische.interview.todoapp.core.TodoListRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
class DefaultTodoListRespository implements TodoListRepository {

	private final TodoListMapper todoListMapper;
	private final TodoListDocumentRepository todoListDocumentRepository;

	@Override
	public Flux<TodoList> determineTodoLists() {
		return todoListDocumentRepository.findAll()
				.map(todoListMapper::map);
	}


	@Override
	public Mono<TodoList> createTodoList() {
		return todoListDocumentRepository.save(new TodoListDocument(null, Collections.emptyList()))
				.map(todoListMapper::map);
	}

	@Override
	public Mono<TodoList> determineTodoList(String id) {
		return todoListDocumentRepository.findById(id).map(todoListMapper::map);
	}

	@Override
	public Mono<TodoList> save(TodoList todoList) {
		return todoListDocumentRepository.save(todoListMapper.map(todoList)).map(todoListMapper::map);
	}
}
