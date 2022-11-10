package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("")
    public String show(Model model, Principal principal) {
        Long id = userService.getUserByUsername(principal.getName()).getId();
        model.addAttribute("user", userService.getUserById(id));
        return "user/user";
    }

//    @GetMapping("/")
//    public String show(ModelMap modelMap, Principal principal) {
//        modelMap.addAttribute("user",
//                userService.loadUserByUsername(principal.getName()));
//        return "show";
//    }






}
