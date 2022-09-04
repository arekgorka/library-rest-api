package com.crud.library.service;

import com.crud.library.domain.User;
import com.crud.library.exception.UserAlreadyExistException;
import com.crud.library.exception.UserNotFoundException;
import com.crud.library.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(final User user) throws UserAlreadyExistException {
        if (userRepository.count()==0) {
            userRepository.save(user);
        } else if (userRepository.existsByLogin(user.getLogin())) {
            throw new UserAlreadyExistException();
        } else {
            userRepository.save(user);
        }
    }

    public User findUserById(final Long userId) throws UserNotFoundException {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }
}
