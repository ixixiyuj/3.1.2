package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class UserServiceImpl implements UserService {

    private final RoleService roleService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(RoleService roleService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.userRepository = userRepository;

        this.passwordEncoder = passwordEncoder;
    }



    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepository.delete(userRepository.getById(id));
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = user.getRoles();
        if (roles.contains(roleService.getRoleByName("ROLE_ADMIN"))) {
            roles.add(roleService.getRoleByName("ROLE_USER"));
        }
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }


    @Override
    public User getUserById(Long id) {
        Optional<User> userById = userRepository.findById(id);
        if (userById.isPresent()) {
            return userById.get();
        } else {
            throw new UsernameNotFoundException(String.format("User with %s not found", id));
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.getUserByUsername(username);
    }

    @Override
    @Transactional
    public void saveUser(User user, boolean roleAdmin) {
        if (user.getPassword().equals("")) {
            User updUser = userRepository.getById(user.getId());
            user.setPassword(updUser.getPassword());
        } else user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getRoleById(2L));
        if (roleAdmin) {
            roles.add(roleService.getRoleById(1L));
        }
        user.setRoles(roles);
        userRepository.save(user);
    }


}
