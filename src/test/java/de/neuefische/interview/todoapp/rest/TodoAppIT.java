package de.neuefische.interview.todoapp.rest;

import static de.neuefische.interview.todoapp.rest.TodoListDTOAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@SpringBootTest
@AutoConfigureWebTestClient
class TodoAppIT {

	@Autowired
	private WebTestClient webTestClient;
	
	@Test
	void integrationTest() throws Exception {
		var todoList = webTestClient 
				.post()
				.uri("/todolists")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isCreated()
				.expectBody(TodoListDTO.class)
				.returnResult()
				.getResponseBody();
		
		assertThat(todoList).isEmpty();
		
		todoList = webTestClient
				.post()
				.uri(todoList.getLink("createItem").map(Link::getHref).orElseThrow())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(new TodoItemDTO("Einkaufe", "OPEN")))
				.exchange()
				.expectStatus().isCreated()
				.expectBody(TodoListDTO.class)
				.returnResult()
				.getResponseBody();
		
		assertThat(todoList)
				.hasSize(1)
				.item(0, item -> item
						.hasTitle("Einkaufe")
						.hasStatus("OPEN"));
		
		todoList = webTestClient
				.put()
				.uri(todoList.getItems().get(0).getLink("self").map(Link::getHref).orElseThrow())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(new TodoItemDTO("Einkaufen", "OPEN")))
				.exchange()
				.expectStatus().isOk()
				.expectBody(TodoListDTO.class)
				.returnResult()
				.getResponseBody();
		
		assertThat(todoList)
				.hasSize(1)
				.item(0, item -> item
						.hasTitle("Einkaufen")
						.hasStatus("OPEN"));
		
		todoList = webTestClient
				.put()
				.uri(todoList.getItems().get(0).getLink("self").map(Link::getHref).orElseThrow())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(new TodoItemDTO("Einkaufen", "DONE")))
				.exchange()
				.expectStatus().isOk()
				.expectBody(TodoListDTO.class)
				.returnResult()
				.getResponseBody();
		
		assertThat(todoList)
				.hasSize(1)
				.item(0, item -> item
						.hasTitle("Einkaufen")
						.hasStatus("DONE"));
	}

}
