package de.neuefische.interview.todoapp.persistence;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
interface TodoListDocumentRepository extends ReactiveMongoRepository<TodoListDocument, String> {

}
