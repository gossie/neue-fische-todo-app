package de.neuefische.interview.todoapp.rest;

import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true, exclude= { "databaseId" })
@ToString(callSuper = true)
class TodoListDTO extends RepresentationModel<TodoListDTO> {
	
	@JsonIgnore
	private String databaseId;
	private final List<TodoItemDTO> items;

	TodoListDTO(@JsonProperty("items") List<TodoItemDTO> items) {
		super();
		this.items = items;
	}

	public TodoListDTO addDatabaseId(String id) {
		this.databaseId = id;
		return this;
	}
	
}
