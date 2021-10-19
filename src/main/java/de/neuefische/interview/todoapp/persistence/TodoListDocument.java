package de.neuefische.interview.todoapp.persistence;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
record TodoListDocument(@Id String id, List<TodoItemDocument> items) {
}
