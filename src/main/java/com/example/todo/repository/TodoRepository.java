package com.example.todo.repository;

import com.example.todo.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo,Long> {
    // Vyhledám Entity Todo podle stavu field isDone !!!!!!!!!!!!!!!!
    List<Todo> findAllByIsDone(boolean status);
}
