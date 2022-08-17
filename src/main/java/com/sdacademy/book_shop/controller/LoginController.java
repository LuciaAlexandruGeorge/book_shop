package com.sdacademy.book_shop.controller;

import com.sdacademy.book_shop.entities.user.User;
import com.sdacademy.book_shop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService UserService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        User newUser = new User();
        model.addAttribute("user", newUser);
        return "register";
    }

    @PostMapping("/register")
    public String add(@ModelAttribute User user) {
        userService.save(user);
        return "redirect:/";
    }
}
