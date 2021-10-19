package de.neuefische.interview.todoapp.persistence;

import static de.neuefische.interview.todoapp.core.TodoListAssert.assertThat;

import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;

import de.neuefische.interview.todoapp.core.Status;
import de.neuefische.interview.todoapp.core.TodoItem;
import de.neuefische.interview.todoapp.core.TodoList;
import de.neuefische.interview.todoapp.core.TodoListRepository;
import reactor.test.StepVerifier;

@DataMongoTest
@Import({ PersistenceContext.class })
class DefaultTodoListRespositoryTest {

	@Autowired
	private TodoListRepository todoListRepository;

	@Test
	void testThatTodoListIsPersisted() {
	    var listId = new AtomicReference<String>();
	    var itemId = new AtomicReference<String>();
		
		StepVerifier.create(todoListRepository.createTodoList())
				.consumeNextWith(savedTodoList -> listId.set(savedTodoList.getId())) 
				.verifyComplete();
		
		StepVerifier.create(todoListRepository.save(new TodoList(listId.get()).addItem(new TodoItem(null, "Steuererklärung machen", Status.OPEN))))
				.consumeNextWith(alteredList -> {
					itemId.set(alteredList.getItems().get(0).id());
					assertThat(alteredList)
						.hasSize(1)
						.item(0, item -> item.hasTitle("Steuererklärung machen").hasStatus(Status.OPEN)); 
						})
				.verifyComplete();
		
		StepVerifier.create(todoListRepository.determineTodoList(listId.get()))
				.consumeNextWith(retrievedList -> assertThat(retrievedList)
						.hasSize(1)
						.item(0, item -> item.hasId(itemId.get()).hasTitle("Steuererklärung machen").hasStatus(Status.OPEN)))
				.verifyComplete();
		
		StepVerifier.create(todoListRepository.save(new TodoList(listId.get()).addItem(new TodoItem(itemId.get(), "Steuererklärung machen", Status.DONE))))
				.consumeNextWith(alteredList -> assertThat(alteredList)
						.hasSize(1)
						.item(0, item -> item.hasId(itemId.get()).hasTitle("Steuererklärung machen").hasStatus(Status.DONE)))
				.verifyComplete();
		
		StepVerifier.create(todoListRepository.determineTodoList(listId.get()))
				.consumeNextWith(retrievedList -> assertThat(retrievedList)
						.hasSize(1)
						.item(0, item -> item.hasId(itemId.get()).hasTitle("Steuererklärung machen").hasStatus(Status.DONE)))
				.verifyComplete();
	}

}
