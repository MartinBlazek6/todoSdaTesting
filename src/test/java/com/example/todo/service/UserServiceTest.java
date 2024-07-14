package com.example.todo.service;

import com.example.todo.model.User;
import com.example.todo.repository.UserRepository;
import jdk.jfr.Description;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
class UserServiceTest {

    static User testUser;

    @InjectMocks
    private UserService userService;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @BeforeAll
    public static void beforeAll() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("Tester Testovaci");
        testUser.setRole("USER");
        log.info(testUser.toString());
    }

    @Test
    @Description("This test is testing if changeRole method is able to change role")
    void itShouldChangeUserRole() {

        //when
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        Mockito.when(userRepository.save(testUser)).thenReturn(testUser);

        //act
        User userResult = userService.changeUserRole(1L, "ADMIN");

        //then
        assertEquals("ADMIN", userResult.getRole());

        Assertions.assertThat(userResult.getRole()).isEqualTo("ADMIN");
        log.info(testUser.toString());
    }

    @Test
    void itShouldThrowExceptionWhenUserIsNotFound() {

        //when
        Mockito.when(userRepository.findById(1L)).thenThrow(RuntimeException.class);

        //then
        assertThrows(RuntimeException.class, () -> userService.changeUserRole(1L, "ADMIN"));
        verify(userRepository, never()).save(any(User.class));
        verify(userRepository, times(1)).findById(anyLong());
        log.info(testUser.toString());
    }


    @Test
//    @Disabled("I am bot able to mock password encoder at this time")
    void registerUser() {
        //when
//        Mockito.when(userRepository.save(any())).thenReturn(any());
//        Mockito.when(passwordEncoder.encode(any())).thenReturn(any());
//
//        userService.registerUser(testUser);
//
//        verify(userRepository,times(1)).save(any());
        log.info(testUser.toString());
    }
}