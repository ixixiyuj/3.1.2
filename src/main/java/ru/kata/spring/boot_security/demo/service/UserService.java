package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;

public interface UserService extends UserDetailsService {


 void deleteUser(Long id);
 void saveUser(User user, boolean roleAdmin);
 void saveUser(User user);
 User getUserByUsername(String username);
 User getUserById(Long id);

 List<User> getAllUsers();
}