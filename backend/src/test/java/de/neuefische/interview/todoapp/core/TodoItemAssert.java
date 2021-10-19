package de.neuefische.interview.todoapp.core;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

public class TodoItemAssert extends AbstractAssert<TodoItemAssert, TodoItem> {

	private TodoItemAssert(TodoItem actual) {
		super(actual, TodoItemAssert.class);
	}

	public static TodoItemAssert assertThat(TodoItem actual) {
		return new TodoItemAssert(actual);
	}

	public TodoItemAssert hasId(String id) {
		Assertions.assertThat(actual.id()).isEqualTo(id);
		return this;
	}

	public TodoItemAssert hasTitle(String title) {
		Assertions.assertThat(actual.title()).isEqualTo(title);
		return this;
	}
	
	public TodoItemAssert hasStatus(Status status) {
		Assertions.assertThat(actual.status()).isEqualTo(status);
		return this;
	}

}
