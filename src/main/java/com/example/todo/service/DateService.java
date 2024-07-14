package com.example.todo.service;

import com.example.todo.model.DTO.TodoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class DateService {
    private final TodoService todoService;

    // ResponseEntity musí obsahovat BODY (to je typu String, protože chci v odpovědi nějakou hlášku).
    // A jako 2.param musí obsahovat HttpStatus.
    // POUŽÍVÁ ZDE TEN MODEL TodoDto V METODĚ createTodo, abych nemusel zadávat zbytečné fields.
    // Aby prošlo přes validaci formátu datumu, musím zadat přesně takto: "yyyy-MM-dd" . Nesmí to být ani přeházeně!!!!!!
    public ResponseEntity<String> validateAndCreateTodo(TodoDto todoDto){
        String dateString = todoDto.getDate();   // Uložím si zaslaný datum jako String do proměnné
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = format.parse(dateString);     // Převedu String vyjádření datumu na typ Date !!!!!!!!!!!
              // Nyní se uvidí, jaký Datum to vytvoří. Následně ten datum převedu zpět na String.
              // A pokud byl dobrý formát, tak vyjde stejný String!!!!!!!!!!!!!!!!!!!!!
            String parsedDateString = format.format(date); // Formats a Date into a date-time string
          // Je to zde validace - převedl date zpátky na String, a porovnal se zaslaným  dateString
          // Následně porovná a tak validuje
            if (dateString.equals(parsedDateString)) {
                log.info("The string has the format yyyy-MM-dd");  // Vypíše zprávu přímo do logu na Console.
        // Zde dojde k vytvoření Todo, a uložení do DB!!!!!!!!!!!!!!!!!!!!!!!!
        // Pro 1.param vytáhnu tittle ze zaslané Entity. Pro 2. parametr klasicky převedu zaslaný datum typu String
        // na typ Date!!!!!!!!!!!!!!!!!!!!!!!!!
                todoService.createTodo(todoDto.getTitle(),todoService.convertStringToDate(todoDto.getDate()));
        // KDYŽ PŘEVOD a VALIDACE PROBĚHNE OK, TAK VRÁTÍM TOTO ResponseEntity
                return new ResponseEntity<>("Todo created", HttpStatus.CREATED);
            } else {     // Vytiskne, když Validace v IF statement neproběhne OK.
                log.error(todoDto.getDate());
                log.error("The string does not have the format yyyy-MM-dd");
            }
        } catch (Exception e) {            // Když při převodu a validaci vyhodí vyjímku, tak vytisknu do logu hlášku
            log.error(todoDto.getDate());
            log.error("The string does not have the format yyyy-MM-dd");
            log.error(e.toString());
            throw new NumberFormatException("zly format");
        }
        // Toto proběhne pouze, když se neuplatní return new ResponseEntity<>("Todo created", HttpStatus.CREATED)
        // výše v IF statementu!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        return new ResponseEntity<>("The string does not have the format yyyy-MM-dd",HttpStatus.BAD_REQUEST);
    }

    public Integer sum(Integer a, Integer b){
        if (a+b < 0){
            throw new NumberFormatException("nemoze byt zaporne");
        }else if (a+b > 10){
            throw new RuntimeException("To uz je moc velke");
        }else if (a == 0 ||
                b == 0){
            throw new NullPointerException("Zadaj obe cisla kladne");
        }
        return (a+b) * -1;
    }
}