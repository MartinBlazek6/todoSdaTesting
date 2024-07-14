package com.example.todo.service;

import com.example.todo.model.Todo;
import com.example.todo.repository.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Tu vravim ze classa bude pouzivat Mockito framework
@Slf4j
class TodoServiceTest {

    @InjectMocks // tato anotacia mi pomoze namokovat servic, akykolvek
    private TodoService todoService;

    @Mock // tato anotacia mi pomoze namokovat repozitar(databaza), akykolvek
    private TodoRepository todoRepository;

    private static List<Todo> todoRepository2;
    private static  Todo doneTodo1;
    private static  Todo doneTodo2;
    private static  Todo notDoneTodo;
    private static  Todo pokazene;

    private static Long ID;
    private static  Long IDsPetrom;
    private static  Todo noveTodo;
    private static  Todo todoSPetrom;


//    Long ID = 7L;
//    Long IDsPetrom = 6L;
//    Todo noveTodo = new Todo("nove todo", new Date());
//    Todo todoSPetrom = new Todo("nove todo", new Date());
//        todoSPetrom.setIsDone(true);


    @BeforeAll
    static void setUp() {
        ID = 7L;
         IDsPetrom = 6L;
         noveTodo = new Todo("nove todo", new Date());
         todoSPetrom = new Todo("nove todo", new Date());
        todoSPetrom.setIsDone(true);

        doneTodo1 = new Todo("Done Todo 1", new Date());
        doneTodo1.setIsDone(true);

         doneTodo2 = new Todo("Done Todo 2", new Date());
        doneTodo2.setIsDone(true);

         notDoneTodo = new Todo("Not Done Todo", new Date());

         pokazene = new Todo("pokazene", new Date());

        todoRepository2 = Arrays.asList(doneTodo1, doneTodo2, notDoneTodo, pokazene);
        log.warn("Vsetky testy prave zacali");

    }

    @BeforeEach
    void beforeEachSetUp(){
        log.info("prave zacal test");
    }

    @AfterEach
    void afterEachSetUp(){
        log.info("prave skoncil test");
    }

    @AfterAll
     static void afterAllSetUp(){
        log.warn("Vsetky testy skoncili");
    }



    @Test
    void testChangeStatus() {
        //given                                 vstupne data, ktore budeme testovat a zaroven ktore budeme ocakavat
        Long todoId = 1L;
        Todo todo = new Todo("Test Todo",new Date());

        //when                                  logika ktoru ocakavame ktoru by mala funkcia nasledovat

        when                                        //  hej test ked budes testovat to co chcem mozno narazis na nieco a ked sa to stane
                (todoRepository.findById(todoId)). // ked sa stane presnejsie toto
                thenReturn(Optional.of(todo));      // tak pohodicka a vrat toto tu todo

        //act                                   tu sa deje to co chceme testovat , cize len nehame vykonat funkciu toho co chceme otestovat
        todoService.changeStatus(todoId);

        //assert                                tu uz zistujeme ze ci po vykonani metody sa udialo to co by sme chceli


        verify(todoRepository, times(1)).findById(todoId);
        // za pomoci verify zistujem kolko krat som volal do DB za pomoci findByID

        verify(todoRepository, times(1)).save(todo);
        // za pomoci verify zistujem kolko krat som volal do DB za pomoci ulozania (save)


        assertTrue(todo.getIsDone() == true);

        org.assertj.core.api.Assertions.assertThat(todo.getIsDone()).isTrue();
        //tu zistujem ci sa po metode changeStatus zmenilo todo z false na true
    }



    @Test
    void testChangeStatusSpolu(){
//        given
//        Long ID = 7L;
//        Long IDsPetrom = 6L;
//        Todo noveTodo = new Todo("nove todo", new Date());
//        Todo todoSPetrom = new Todo("nove todo", new Date());
//        todoSPetrom.setIsDone(true);

        // zakomentovany kod vyssie moze byt vymazany lebo uz sa o to stara beforeAll

        //when
        when(todoRepository.findById(ID)).thenReturn(Optional.of(noveTodo));
        when(todoRepository.findById(IDsPetrom)).thenReturn(Optional.of(todoSPetrom));

        //act
        todoService.changeStatus(ID);
        todoService.changeStatus(IDsPetrom);

        //assert
        assertTrue(todoRepository.findById(ID).orElseThrow().getIsDone());
        assertFalse(todoRepository.findById(IDsPetrom).orElseThrow().getIsDone());
//        assertFalse(todoRepository.findById(ID).orElseThrow().getIsDone() == false);

//        verify( //over mi prosim ta
//                todoRepository,// ze tendo repozitar/ tato namokovana databaza
//                times(2)) // bola zavolana dva krat
//                .findById(ID); // a to s metodou find by id
//
//        verify(todoRepository,times(2)).findById(IDsPetrom);
        verify(todoRepository,times(4)).findById(anyLong());
        verify(todoRepository, never()).delete(any());
        verify(todoRepository, never()).deleteAll(any());
        verify(todoRepository, never()).findAll();
        verify(todoRepository, never()).findAllByIsDone(any());

//        verify(
//                todoRepository,
//                times(1)
//        ).save(noveTodo);
//
//        verify(todoRepository,times(1)).save(todoSPetrom);
        verify(todoRepository,times(2)).save(any());


    }

    @Test
    void testGetAllDoneTodos() {

//        Todo doneTodo1 = new Todo("Done Todo 1", new Date());
//        doneTodo1.setIsDone(true);
//
//        Todo doneTodo2 = new Todo("Done Todo 2", new Date());
//        doneTodo2.setIsDone(true);
//
//        Todo notDoneTodo = new Todo("Not Done Todo",new Date());
//
//        Todo pokazene = new Todo("pokazene",new Date());

//        List<Todo> todoRepository = Arrays.asList(doneTodo1, doneTodo2,notDoneTodo,pokazene);
//        List<Todo> doneTodos2 = todoRepository.stream().filter(todo -> todo.getIsDone()).toList();

        // vdaka beforeAll zakomentovany kod hore moze byt vymazany



        List<Todo> doneTodos2 = new java.util.ArrayList<>(todoRepository2.stream().filter(Todo::getIsDone).toList());

        when // ked
                (this.todoRepository.findAllByIsDone(true)). // zavolam metodu najdi todo podla statusu, ktory je  true
                thenReturn(doneTodos2); //tak potom vrat list doneTodos2



        // Act
        List<Todo> result = todoService.getAllByStatus(true);


        // Assert
        verify(this.todoRepository, times(1)).findAllByIsDone(true);
        verify(this.todoRepository, never()).findAllByIsDone(false);
        verify(this.todoRepository, never()).getReferenceById(anyLong());
        verify(this.todoRepository, never()).delete(any());
        verify(this.todoRepository, never()).deleteAll(any());
        assertEquals(doneTodos2, result);
        assertEquals(2, result.size());
        assertTrue(result.contains(doneTodo1));
        assertTrue(result.contains(doneTodo2));
        assertFalse(result.contains(notDoneTodo));
        assertFalse(result.contains(notDoneTodo));
    }

    @Test
    void testGetAllNotDoneTodos() {
        List<Todo> doneTodos2 = new java.util.ArrayList<>(todoRepository2.stream().filter(t -> !t.getIsDone()).toList());

        when // ked
                (this.todoRepository.findAllByIsDone(false)). // zavolam metodu najdi todo podla statusu, ktory je  true
                thenReturn(doneTodos2); //tak potom vrat list doneTodos2

        // Act
        List<Todo> result = todoService.getAllByStatus(false);


        // Assert
        verify(this.todoRepository, times(1)).findAllByIsDone(anyBoolean());
        assertEquals(doneTodos2, result);
        assertEquals(2, result.size());
        assertTrue(result.contains(notDoneTodo));
        assertTrue(result.contains(pokazene));
    }

}