package de.neuefische.interview.todoapp.core;

import static de.neuefische.interview.todoapp.core.TodoListAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.Test;

public class TodoListTest {

	@Test
	void testThatTodoItemIsAdded() {
		var todoList = new TodoList();
		todoList = todoList.addItem(new TodoItem(null, "Putzen", Status.OPEN));
		assertThat(todoList).hasSize(1);
	}

	@Test
	void testThatReturnedTodoItemsAreImmutable() {
		var todoList = new TodoList();
		assertThatExceptionOfType(UnsupportedOperationException.class)
				.isThrownBy(() -> todoList.getItems().add(new TodoItem(null, "", Status.OPEN)));
	}

	@Test
	void testThatReturnedTodoItemsStayImmutable() {
		var todoList = new TodoList();
		var changedTodoList = todoList.addItem(new TodoItem(null, "Putzen", Status.OPEN));
		assertThatExceptionOfType(UnsupportedOperationException.class)
				.isThrownBy(() -> changedTodoList.getItems().add(new TodoItem(null, "", Status.OPEN)));
	}
	
	@Test
	void testThatTodoItemIsUpdated() {
		var todoList = new TodoList();
		
		todoList = todoList.addItem(new TodoItem("17", "Putze", Status.OPEN));
		assertThat(todoList)
				.hasSize(1)
				.item(0, item -> item
						.hasTitle("Putze")
						.hasStatus(Status.OPEN));
		
		todoList = todoList.addItem(new TodoItem("18", "Einkaufen", Status.OPEN));
		assertThat(todoList)
				.hasSize(2)
				.item(0, item -> item
						.hasTitle("Putze")
						.hasStatus(Status.OPEN))
				.item(1, item -> item
						.hasTitle("Einkaufen")
						.hasStatus(Status.OPEN));
		
		todoList = todoList.updateItem(new TodoItem("17", "Putzen", Status.DONE));
		assertThat(todoList)
				.hasSize(2)
				.item(0, item -> item
						.hasTitle("Putzen")
						.hasStatus(Status.DONE))
				.item(1, item -> item
						.hasTitle("Einkaufen")
						.hasStatus(Status.OPEN));
	}

}
