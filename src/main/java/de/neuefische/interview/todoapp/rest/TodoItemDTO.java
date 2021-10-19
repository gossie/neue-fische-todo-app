package de.neuefische.interview.todoapp.rest;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true, exclude= { "databaseId" })
@ToString(callSuper = true)
class TodoItemDTO extends RepresentationModel<TodoItemDTO> {

	@JsonIgnore
	private String databaseId;
	private final String title;
	private final String status;
	
    TodoItemDTO(@JsonProperty String title, @JsonProperty String status) {
    	this.title = title;
    	this.status = status;
    }
	
	public TodoItemDTO addDatabaseId(String id) {
		this.databaseId = id;
		return this;
	}
}
