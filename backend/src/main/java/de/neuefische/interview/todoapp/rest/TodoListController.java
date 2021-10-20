package de.neuefische.interview.todoapp.rest;

import org.springframework.hateoas.server.reactive.WebFluxLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.neuefische.interview.todoapp.core.TodoListService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/todolists")
@CrossOrigin(origins = {"*"}, allowedHeaders = {"*"})
@RequiredArgsConstructor
public class TodoListController {

	private final TodoListService todoListService;
	private final TodoListMapper todoListMapper;
	private final TodoItemMapper todoItemMapper;
	
	@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
	public Flux<TodoListDTO> getTodoLists() {
		return todoListService.determineTodoLists()
				.map(todoListMapper::map)
				.flatMap(this::addLinks);
	}
	
	@PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(code = HttpStatus.CREATED)
	public Mono<TodoListDTO> createTodoList() {
		return todoListService.createTodoList()
				.map(todoListMapper::map)
				.flatMap(this::addLinks);
	}
	
	@GetMapping(path="/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Mono<TodoListDTO> getTodoList(@PathVariable String id) {
		return todoListService.determineTodoList(id)
				.map(todoListMapper::map)
				.flatMap(this::addLinks);
	}
	
	@PostMapping(path = "/{id}/items", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(code = HttpStatus.CREATED)
	public Mono<TodoListDTO> addItem(@PathVariable String id, @RequestBody TodoItemDTO item) {
		return todoListService.addItem(id, todoItemMapper.map(item))
				.map(todoListMapper::map)
				.flatMap(this::addLinks);
	}
	
	@PutMapping(path = "/{id}/items/{itemId}")
	public Mono<TodoListDTO> updateItem(@PathVariable String id, @PathVariable String itemId, @RequestBody TodoItemDTO item) {
		return todoListService.updateItem(id, todoItemMapper.map(itemId, item))
				.map(todoListMapper::map)
				.flatMap(this::addLinks);
	}
	
	private Mono<TodoListDTO> addLinks(TodoListDTO todoList) {
		return addSelfLink(todoList)
				.flatMap(list -> addCreateItemLink(list))
				.flatMapIterable(list -> list.getItems())
				.flatMap(item -> addUpdateItemLink(todoList, item))
				.collect(() -> todoList, (a, b) -> {});
	}
	
	private Mono<TodoListDTO> addSelfLink(TodoListDTO todoList) {
		return WebFluxLinkBuilder.linkTo(WebFluxLinkBuilder.methodOn(this.getClass()).getTodoList(todoList.getDatabaseId()))
				.withSelfRel()
				.toMono()
	            .map(todoList::add)
	            .map(TodoListDTO.class::cast);
	}
	
	private Mono<TodoListDTO> addCreateItemLink(TodoListDTO todoList) {
		return WebFluxLinkBuilder.linkTo(WebFluxLinkBuilder.methodOn(this.getClass()).addItem(todoList.getDatabaseId(), null))
				.withRel("createItem")
				.toMono()
	            .map(todoList::add)
	            .map(TodoListDTO.class::cast);
	}
	
	private Mono<TodoItemDTO> addUpdateItemLink(TodoListDTO todoList, TodoItemDTO item) {
		return WebFluxLinkBuilder.linkTo(WebFluxLinkBuilder.methodOn(this.getClass()).updateItem(todoList.getDatabaseId(), item.getDatabaseId(), null))
				.withSelfRel()
				.toMono()
	            .map(item::add)
	            .map(TodoItemDTO.class::cast);
	}
	
}
