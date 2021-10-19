package de.neuefische.interview.todoapp.rest;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

class TodoItemDTOAssert extends AbstractAssert<TodoItemDTOAssert, TodoItemDTO> {

	private TodoItemDTOAssert(TodoItemDTO actual) {
		super(actual, TodoItemDTOAssert.class);
	}

	static TodoItemDTOAssert assertThat(TodoItemDTO actual) {
		return new TodoItemDTOAssert(actual);
	}

	TodoItemDTOAssert hasTitle(String title) {
		Assertions.assertThat(actual.getTitle()).isEqualTo(title);
		return this;
	}
	
    TodoItemDTOAssert hasStatus(String status) {
		Assertions.assertThat(actual.getStatus()).isEqualTo(status);
		return this;
	}

}
