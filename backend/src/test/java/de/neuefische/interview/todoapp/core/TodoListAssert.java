package de.neuefische.interview.todoapp.core;

import java.util.function.Consumer;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

public class TodoListAssert extends AbstractAssert<TodoListAssert, TodoList> {

	private TodoListAssert(TodoList actual) {
		super(actual, TodoListAssert.class);
	}

	public static TodoListAssert assertThat(TodoList actual) {
		return new TodoListAssert(actual);
	}

	public TodoListAssert isEmpty() {
		Assertions.assertThat(actual.getItems()).isEmpty();
		return this;
	}

	public TodoListAssert isNotEmpty() {
		Assertions.assertThat(actual.getItems()).isNotEmpty();
		return this;
	}

	public TodoListAssert hasSize(int size) {
		Assertions.assertThat(actual.getItems()).hasSize(size);
		return this;
	}
	
	public TodoListAssert item(int index, Consumer<TodoItemAssert> consumer) {
		consumer.accept(TodoItemAssert.assertThat(actual.getItems().get(index)));
		return this;
	}

}
