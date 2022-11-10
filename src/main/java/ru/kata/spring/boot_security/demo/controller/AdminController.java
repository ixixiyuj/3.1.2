package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("")
    public String usersList(Model model) {
        model.addAttribute("list", userService.getAllUsers());
        return "admin/users";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "admin/new";
    }
    @PostMapping
    public String create(@ModelAttribute("user") User user,
                         @RequestParam(required=false) String roleAdmin) {
        userService.saveUser(user, (roleAdmin != null && roleAdmin.contains("ROLE_ADMIN")));
        return "redirect:/admin";
    }



    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") long id) {
        User user = userService.getUserById(id);
        boolean checked =
                user.getRoles().contains(roleService.getRoleByName("ROLE_ADMIN"));
        model.addAttribute("user", user);
        model.addAttribute("checked", checked);
        return "admin/edit";
    }
    @PostMapping("/edit/{id}")
    public String update(@ModelAttribute("user") User user,
                         @RequestParam(required=false) String roleAdmin) {
        userService.saveUser(user, (roleAdmin != null && roleAdmin.contains("ROLE_ADMIN")));
        return "redirect:/admin";
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

}
