package de.neuefische.interview.todoapp.rest;

import org.springframework.stereotype.Component;

import de.neuefische.interview.todoapp.core.Status;
import de.neuefische.interview.todoapp.core.TodoItem;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class TodoItemMapper {

	TodoItem map(TodoItemDTO item) {
		return new TodoItem(item.getDatabaseId(), item.getTitle(), Status.valueOf(item.getStatus()));
	}
	
	TodoItemDTO map(TodoItem item) {
		return new TodoItemDTO(item.title(), item.status().name())
				.addDatabaseId(item.id());
	}

	TodoItem map(String id, TodoItemDTO item) {
		return new TodoItem(id, item.getTitle(), Status.valueOf(item.getStatus()));
	}
	
}
