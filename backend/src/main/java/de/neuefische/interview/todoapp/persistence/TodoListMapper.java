package de.neuefische.interview.todoapp.persistence;

import de.neuefische.interview.todoapp.core.TodoList;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class TodoListMapper {
	
	private final TodoItemMapper todoItemMapper;

	TodoList map(TodoListDocument todoList) {
		var mapped = new TodoList(todoList.id());
		
		return todoList.items().stream()
				.map(todoItemMapper::map)
				.reduce(mapped, (list, item) -> list.addItem(item), (l1, l2) -> l1);
	}
	
	TodoListDocument map(TodoList todoList) {
		var items = todoList.getItems().stream()
				.map(todoItemMapper::map)
				.toList();
		
		return new TodoListDocument(todoList.getId(), items);
	}
	
}
