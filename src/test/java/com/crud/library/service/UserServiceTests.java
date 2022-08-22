package com.crud.library.service;

import com.crud.library.domain.User;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UserServiceTests {

    @InjectMocks
    public UserService userService;
    @Mock
    private UserRepository userRepository;

    @Test
    void createUserTest() {
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
    }
}
