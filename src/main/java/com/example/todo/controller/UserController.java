package com.example.todo.controller;

import com.example.todo.model.User;
import com.example.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
//@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }


    @GetMapping("/registration")
    public String registrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/registration")
    public String registrationSubmit(@ModelAttribute("user") User user) {
        userService.registerUser(user);
        return "redirect:/login"; // Redirect to the login page after successful registration
    }

    // Pozor: Při vytváření usera musím do DB uložit jako ADMIN, a ne jako ROLE_ADMIN tu naši field role !!!!!!!!!!!!!!!!!!!
    @GetMapping("/admin")
    @Secured("ROLE_ADMIN")
    public String adminPage(Model model) {
        try {
            List<User> users = userService.getAllUsers();
            model.addAttribute("users", users);
            return "admin";
        } catch (AccessDeniedException e ) {
            return "redirect:/";
        }

    }

    @PostMapping("/admin/changeRole")
    public String changeUserRole(@RequestParam Long userId, @RequestParam String newRole) {
        userService.changeUserRole(userId, newRole);
        return "redirect:/admin";
    }
}

