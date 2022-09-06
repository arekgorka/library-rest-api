package com.crud.library.service;

import com.crud.library.domain.User;
import com.crud.library.exception.UserAlreadyExistException;
import com.crud.library.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveUserTest() {
        //Given
        User user = new User("login","firstname","lastname",LocalDate.now());
        //When
        userRepository.save(user);
        //Then
        assertTrue(userRepository.existsById(user.getId()));
        //CleanUp
        userRepository.deleteById(user.getId());
    }

    //Given
    //When
    //Then
    //CleanUp
    /*@InjectMocks
    public UserService userService;
    @Mock
    private UserRepository userRepository;

    @Test
    void createUserTest() throws UserAlreadyExistException {
        //Given
        User user = User.builder()
                .id(1L)
                .firstname("John")
                .lastname("Smith")
                .created(LocalDate.of(2011,5,8))
                .build();
        //When
        userService.saveUser(user);
        //Then
        verify(userRepository).save(any(User.class));
    }*/
}
