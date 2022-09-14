package com.sdacademy.book_shop.controller;

import com.sdacademy.book_shop.dto.UserDto;
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

    private UserService userService;

    @Autowired
    public LoginController(UserService userService) {this.userService = userService;}

    @GetMapping("/login")
    public String showLoginForm() {return "login_page";}

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        UserDto newUser = new UserDto();
        model.addAttribute("user", newUser);
        return "register_page";
    }

    @PostMapping("/register")
    public String add(@ModelAttribute UserDto userDto) {
        userService.save(userDto);
        return "redirect:/";
    }
}
