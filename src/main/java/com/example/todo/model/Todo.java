package com.example.todo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

// Pozn
@Entity
@Data
@NoArgsConstructor
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private Date dueDate;              // POZOR: Zde je dueDate jako typ Date
    private Boolean isDone;

    public Todo(String title, Date dueDate) {  // Tento kostruktor použiju v createTodo
        this.title = title;
        this.dueDate = dueDate;
        this.isDone = false;
    }
}
