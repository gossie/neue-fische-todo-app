package de.neuefische.interview.todoapp.persistence;

import java.util.UUID;

import de.neuefische.interview.todoapp.core.Status;
import de.neuefische.interview.todoapp.core.TodoItem;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class TodoItemMapper {

	TodoItemDocument map(TodoItem item) {
		// TODO: this Id should not be generated here, but by the DB
		return new TodoItemDocument(item.id() != null ? item.id() : UUID.randomUUID().toString(), item.title(), item.status().name());
	}
	
	TodoItem map(TodoItemDocument item) {
		return new TodoItem(item.id(), item.title(), Status.valueOf(item.status()));
	}
	
}
