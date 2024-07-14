package com.example.todo.repository;

import com.example.todo.model.Todo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;


import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;

@DataJpaTest
class TodoRepositoryTest {

    @Autowired
    TodoRepository todoRepository;

    @AfterEach
    void afterEach() {
        todoRepository.deleteAll();
    }



    @Test
    void findAllByIsDone() {
        //given
        Todo todo1 = new Todo("prve todo", new Date());

        Todo todo2 = new Todo("prve todo", new Date());

        Todo todo3 = new Todo("prve todo", new Date());

        Todo todo4 = new Todo("prve todo", new Date());

        todo1.setIsDone(true);
        todo2.setIsDone(true);

        todoRepository.saveAll(List.of(todo1, todo2, todo3, todo4));

        List<Todo> result = todoRepository.findAllByIsDone(true);

        Assertions.assertThat(result).hasSize(2);

        Assertions.assertThat(result).containsAll(List.of(todo1, todo2));
        Assertions.assertThat(result).doesNotContainAnyElementsOf(List.of(todo3, todo4));

    }
}