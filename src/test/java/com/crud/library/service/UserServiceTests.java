package com.crud.library.service;

import com.crud.library.domain.User;
import com.crud.library.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

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
}
