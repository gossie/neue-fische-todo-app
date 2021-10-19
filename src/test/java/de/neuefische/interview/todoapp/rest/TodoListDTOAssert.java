package de.neuefische.interview.todoapp.rest;

import java.util.function.Consumer;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

class TodoListDTOAssert extends AbstractAssert<TodoListDTOAssert, TodoListDTO> {
	

	private TodoListDTOAssert(TodoListDTO actual) {
		super(actual, TodoListDTOAssert.class);
	}

	static TodoListDTOAssert assertThat(TodoListDTO actual) {
		return new TodoListDTOAssert(actual);
	}
	
	TodoListDTOAssert isEmpty() {
		Assertions.assertThat(actual.getItems()).isEmpty();
		return this;
	}
	
	TodoListDTOAssert isNotEmpty() {
		Assertions.assertThat(actual.getItems()).isNotEmpty();
		return this;
	}

	TodoListDTOAssert hasSize(int size) {
		Assertions.assertThat(actual.getItems()).hasSize(size);
		return this;
	}
	
	TodoListDTOAssert item(int index, Consumer<TodoItemDTOAssert> consumer) {
		consumer.accept(TodoItemDTOAssert.assertThat(actual.getItems().get(index)));
		return this;
	}
	
}
