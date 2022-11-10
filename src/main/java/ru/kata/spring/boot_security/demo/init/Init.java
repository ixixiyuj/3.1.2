package ru.kata.spring.boot_security.demo.init;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class Init implements ApplicationRunner {


    private final UserService userService;

    private final RoleService roleService;

    public Init(UserService userService, BCryptPasswordEncoder passwordEncoder, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public void run(ApplicationArguments args) {
        List<User> users = userService.getAllUsers();

        if (users.isEmpty()) {
            roleService.addRole(new Role("ROLE_ADMIN"));
            roleService.addRole(new Role("ROLE_USER"));
            Role admin = roleService.getRoleById(1L);
            Role user = roleService.getRoleById(2L);
            Set<Role> adminRole = new HashSet<>();
            Set<Role> userRole = new HashSet<>();
            adminRole.add(admin);
            adminRole.add(user);
            userRole.add(user);
            User adm = new User( "admin@mail.ru", "admin", adminRole );
            adm.setName("Ivan");
            adm.setLastName("Ivanov");
            adm.setAge(30);
            userService.saveUser(adm);
            User us = new User( "user@mail.ru", "user", userRole );
            us.setName("Petr");
            us.setLastName("Petrov");
            us.setAge(35);
            userService.saveUser(us);
        }
    }
}