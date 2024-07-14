package com.example.todo.service;

import com.example.todo.model.DTO.TodoDto;
import com.example.todo.repository.TodoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DateServiceTest {
    @InjectMocks
    private DateService dateService;

    @InjectMocks
    private TodoService todoService;

    @Mock
    private TodoRepository todoRepository;

    @Test
    void nonValidDateThrowsException() {
        //Given
        TodoDto todoDto = new TodoDto();
        todoDto.setTitle("tets");
//        todoDto.setDate("2010-01-01");
        todoDto.setDate("01-01-2010");

        //when


        //act

//        assertThrows(Exception.class, () -> dateService.validateAndCreateTodo(todoDto));
        assertThrows(NumberFormatException.class, () -> dateService.validateAndCreateTodo(todoDto));
//        assertThrows(NullPointerException.class, () -> dateService.validateAndCreateTodo(todoDto));
    }

    @Test
    void validDate() {
        //given
        TodoDto todoDto = new TodoDto();
        todoDto.setTitle("tets");
        todoDto.setDate("2010-10-10");
//        todoDto.setDate("01-01-2010");

        //when
//        when(todoService.createTodo(todoDto.getTitle(),new Date())).then()


        assertDoesNotThrow(() -> dateService.validateAndCreateTodo(todoDto), "Test if no exception is thrown");
    }

    @Test
    void sumExceptions() {
        assertDoesNotThrow(() -> dateService.sum(4, 2)); // cislo je v rozmedzi 0-10
        assertThrows(NullPointerException.class, () -> dateService.sum(0, 2)); // ziadne s cisel by nemalo byt nula
        assertThrows(NumberFormatException.class, () -> dateService.sum(1, -2)); // cislo by nemalo byt zaporne

        Assertions.assertThatThrownBy(() -> dateService.sum(-1, -2))
                .isInstanceOf(NumberFormatException.class)
                .hasMessage("nemoze byt zaporne");

        Assertions.assertThatThrownBy(() -> dateService.sum(2, 10))
                .hasMessage("To uz je moc velke")
                .isInstanceOf(RuntimeException.class);


//        assertThrows(NullPointerException.class, () -> dateService.sum(0,0));

        Assertions.assertThatThrownBy(() -> dateService.sum(0, 0))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Zadaj obe cisla kladne");
    }

    @Test
    void sumTets() {
        assertNotEquals(5, dateService.sum(3, 2));

        // tieto dve metody su uplne to iste
        assertEquals(-5, dateService.sum(3, 2));
        assertThat(dateService.sum(3, 2)).isEqualTo(-5);
    }
}