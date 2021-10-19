package de.neuefische.interview.todoapp.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreContext {

	@Bean
	public TodoListService todoListService(TodoListRepository todoListRepository) {
		return new DefaultTodoListService(todoListRepository);
	}
}
