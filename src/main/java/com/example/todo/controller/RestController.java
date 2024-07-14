package com.example.todo.controller;


import com.example.todo.model.DTO.TodoDto;
import com.example.todo.model.Todo;
import com.example.todo.service.DateService;
import com.example.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// ZDE JEN UKÁZKA POUŽITÍ KLASICKÝCH REST METOD (POST, GET).
// POUŽÍVÁ ZDE TEN MODEL TodoDto V METODĚ createTodo, abych nemusel zadávat zbytečné fields.
// A taky lze zadat ten datum jako String do toho zasílaného objektu v BODY toho POST příkazu!!!!!!!!!!!!
@org.springframework.web.bind.annotation.RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1")
@Slf4j
public class RestController {

    private final TodoService todoService;
    private final DateService dateService;
    // ZDE UKÁZKA ResponseEntity jako odpověď na naše REST příkazy !!!!!!!!!!!!!!!!!!!!!!!!
    // Použití: Když chci komplexní odpověď - tj. hlášku , a taky status. !!!!!!!!!!!!
    // ResponseEntity musí obsahovat BODY (to je typu String, protože chci v odpovědi nějakou hlášku).
    // A jako 2.param musí obsahovat HttpStatus.
    @PostMapping("/create")
    public ResponseEntity<String> createTodo(@RequestBody TodoDto todoDto) {
    // Aby prošlo přes validaci formátu datumu, musím zadat přesně takto: "yyyy-MM-dd" . Nesmí to být ani přeházeně!!!!!!
        return dateService.validateAndCreateTodo(todoDto);
    }

    @GetMapping("/allTodos")
    public ResponseEntity<List<Todo>> getAllTodos() {                   // Vrací seznam všech Todo z DB
        return new ResponseEntity<>(todoService.getAllTodos(), HttpStatus.OK);
    }
    @GetMapping("/getByStatus")
    public ResponseEntity<List<Todo>> getAllByStatus(@RequestParam Boolean status){
        return new ResponseEntity<>(todoService.getAllByStatus(status),HttpStatus.OK);
    }

    // Změní STATUS
    @PatchMapping("/change")
    public ResponseEntity<String> update(@RequestParam String id) {
        // The Long class wraps a value of the primitive type long in an object. An object of type Long contains a
        // single field whose type is long. Pouze zabalí id do objektu.
        todoService.changeStatus(Long.valueOf(id));
        return new ResponseEntity<>("status changed", HttpStatus.OK);
    }


    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam String id) {
        // The Long class wraps a value of the primitive type long in an object. An object of type Long contains a
        // single field whose type is long. Pouze zabalí id do objektu.
        todoService.deletedTodo(Long.valueOf(id));
        return new ResponseEntity<>("deleted", HttpStatus.OK);
    }


}
