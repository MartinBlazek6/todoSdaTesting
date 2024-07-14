package com.example.todo.service;

import com.example.todo.model.Todo;
import com.example.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final  TodoRepository todoRepository;

    //create
    public void createTodo(String title, Date date){
        todoRepository.save(new Todo(title,date));
    }

    //read

    public List<Todo> getAllTodos(){         // Klasicky využije implicit metodu v JpaRepository<Todo,Long>  !!!!!!!!!!!!
        return todoRepository.findAll();
    }

    public List<Todo> getAllByStatus(boolean status){
        return todoRepository.findAllByIsDone(status);
    }


    //update
    public void changeStatus(Long id){
    //  Todo todo = todoRepository.findById(id).get();  // Použiju get(), protože findById vrací OPTIONAL
        Todo todo = todoRepository.findById(id).orElseThrow(); // Použiju raději orElseThrow, protože pokud neeexistuje,
        todo.setIsDone(!todo.getIsDone());                     // vyhodí vyjímku
        todoRepository.save(todo);
    }


    //delete
    public void deletedTodo(Long id){
        todoRepository.deleteById(id);
    }

    // Zde musím převest zadaný String na date format.
    public Date convertStringToDate(String date){
        String dateString = date;
//Constructs a SimpleDateFormat using the given pattern and the default date format symbols for the default FORMAT locale.
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date2 = null;       // Implicit class Date umožňuje uložit časovou hodnotu s přesností na ms
        try {
            date2 = dateFormat.parse(dateString); // Parses text from the beginning of the given string to produce a date.
                                    // Tj. převede zaslaný řetězec dateString na datum dle formátu dateFormat.
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date2;
    }


}
