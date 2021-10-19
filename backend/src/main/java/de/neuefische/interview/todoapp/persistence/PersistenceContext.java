package de.neuefische.interview.todoapp.persistence;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.neuefische.interview.todoapp.core.TodoListRepository;

@Configuration
public class PersistenceContext {
	
	@Bean
	TodoItemMapper todoItemDocumentMapper() {
		return new TodoItemMapper();
	}
	
	@Bean
	TodoListMapper todoListDocumentMapper() {
		return new TodoListMapper(todoItemDocumentMapper());
	}

	@Bean
	TodoListRepository todoListRepository(TodoListDocumentRepository todoListDocumentRepository) {
		return new DefaultTodoListRespository(todoListDocumentMapper(), todoListDocumentRepository);
	}
}
