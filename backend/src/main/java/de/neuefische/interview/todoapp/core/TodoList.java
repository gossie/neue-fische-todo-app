package de.neuefische.interview.todoapp.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
@ToString
public class TodoList {
	
	private final String id;
	private final List<TodoItem> items;
	
	public TodoList() {
		this(null);
	}
	
	public TodoList(String id) {
		this(id, Collections.emptyList());
	}
	
	private static TodoItem mapIfNecessary(TodoItem current, TodoItem newItem) {
		if (Objects.equals(current.id(), newItem.id())) {
			return newItem;
		}
		return current;
	}

	public TodoList addItem(TodoItem todoItem) {
		var newItems = new ArrayList<>(items);
		newItems.add(todoItem);
		return new TodoList(id, newItems);
	}

	public TodoList updateItem(TodoItem newItem) {
		var newItems = items.stream()
				.map(item -> mapIfNecessary(item, newItem))
				.toList();
		
		return new TodoList(id, newItems);
	}
	
	public List<TodoItem> getItems() {
		return Collections.unmodifiableList(items);
	}

}
