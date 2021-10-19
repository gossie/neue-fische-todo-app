package de.neuefische.interview.todoapp.rest;

import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import de.neuefische.interview.todoapp.core.Status;
import de.neuefische.interview.todoapp.core.TodoItem;
import de.neuefische.interview.todoapp.core.TodoList;
import de.neuefische.interview.todoapp.core.TodoListService;
import reactor.core.publisher.Mono;

@WebFluxTest(controllers = { TodoListController.class })
@Import(value = {TodoListMapper.class, TodoItemMapper.class})
public class TodoListControllerTest {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private TodoListService todoListService;
	
	@Test
	void testCreateTodoList() {
		when(todoListService.createTodoList())
				.thenReturn(Mono.just(new TodoList("abc")));
		
		var expectedTodoList = new TodoListDTO(Collections.emptyList())
				.add(Link.of("/todolists/abc", "self"))
				.add(Link.of("/todolists/abc/items", "createItem"));
		
		webTestClient
				.post()
				.uri("/todolists")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isCreated()
				.expectBody(TodoListDTO.class).isEqualTo(expectedTodoList);
	}
	
	@Test
	void testGetTodoList() {
		when(todoListService.determineTodoList("abc"))
				.thenReturn(Mono.just(new TodoList("abc")));
		
		var expectedTodoList = new TodoListDTO(Collections.emptyList())
				.add(Link.of("/todolists/abc", "self"))
				.add(Link.of("/todolists/abc/items", "createItem"));
		
		webTestClient
				.get()
				.uri("/todolists/abc")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody(TodoListDTO.class).isEqualTo(expectedTodoList);
	}

	@Test
	void testAddItem() {
		when(todoListService.addItem("abc", new TodoItem(null, "Bett beziehen", Status.OPEN)))
				.thenReturn(Mono.just(new TodoList("abc").addItem(new TodoItem("a", "Putzen", Status.OPEN)).addItem(new TodoItem("b", "Bett beziehen", Status.OPEN))));
		
		var expectedTodoList = new TodoListDTO(
				List.of(
						new TodoItemDTO("Putzen", "OPEN").add(Link.of("/todolists/abc/items/a", "self")),
						new TodoItemDTO("Bett beziehen", "OPEN").add(Link.of("/todolists/abc/items/b", "self"))
				)
		)
		.add(Link.of("/todolists/abc", "self"))
		.add(Link.of("/todolists/abc/items", "createItem"));

		webTestClient
				.post()
				.uri("/todolists/abc/items")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(new TodoItemDTO("Bett beziehen", "OPEN")))
				.exchange()
				.expectStatus().isCreated()
				.expectBody(TodoListDTO.class).isEqualTo(expectedTodoList);
	}
	
	@Test
	void testUpdateItem() {
		when(todoListService.updateItem("abc", new TodoItem("itemId", "Putzen", Status.DONE)))
				.thenReturn(Mono.just(new TodoList("abc").addItem(new TodoItem("itemId", "Putzen", Status.DONE))));
		
		var expectedTodoList = new TodoListDTO(
				List.of(
						new TodoItemDTO("Putzen", "DONE").add(Link.of("/todolists/abc/items/itemId", "self"))
				)
		)
		.add(Link.of("/todolists/abc", "self"))
		.add(Link.of("/todolists/abc/items", "createItem"));

		webTestClient
				.put()
				.uri("/todolists/abc/items/itemId")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(new TodoItemDTO("Putzen", "DONE")))
				.exchange()
				.expectStatus().isOk()
				.expectBody(TodoListDTO.class).isEqualTo(expectedTodoList);
	}

}
