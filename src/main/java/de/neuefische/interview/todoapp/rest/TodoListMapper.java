package de.neuefische.interview.todoapp.rest;

import org.springframework.stereotype.Component;

import de.neuefische.interview.todoapp.core.TodoList;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class TodoListMapper {

	private final TodoItemMapper todoItemMapper;
	
	TodoListDTO map(TodoList todoList) {
		var itemDTOs = todoList.getItems().stream()
				.map(todoItemMapper::map)
				.toList();
		
		return new TodoListDTO(itemDTOs).addDatabaseId(todoList.getId());
	}
	
}
