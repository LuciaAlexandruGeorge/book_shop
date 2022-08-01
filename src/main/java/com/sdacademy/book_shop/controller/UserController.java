package com.sdacademy.book_shop.controller;


import com.sdacademy.book_shop.dto.UserDto;
import com.sdacademy.book_shop.entities.user.User;
import com.sdacademy.book_shop.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;


import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class UserController {


    private final UserService userService;



    // http://localhost:8080/users
    // map url to controller method
    @GetMapping("/admin/users")
    public String showUsersPage(Model model) {
        // return a html page with users
        // add list of users
        List<User> users = userService.getAll();
        model.addAttribute("usersInView", users);

        // resolved by the view resolver
        return "users-list";
    }

    @GetMapping("/admin/users/add")
    public String showAddFrom(Model model) {
        User newUser = new User();
        model.addAttribute("user", newUser);
        return "user-add";
    }

    @PostMapping("/admin/users/add")
    public String add(@ModelAttribute UserDto userDto) {
        userService.save(userDto);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users/{id}/edit")
    public String showEditForm(Model model,
                               @PathVariable Long id) {

        model.addAttribute("user", userService.findById(id));
        return "user-edit";
    }

    public ModelAndView showEditForm2(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("user-edit");
        modelAndView.addObject("user", userService.findById(id));
        return modelAndView;
    }

    @PostMapping("/admin/users/{id}/edit")
    public String edit(
            @PathVariable Long id,
            @ModelAttribute UserDto userDto) {

        userService.update(id, userDto);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users/{id}/delete")
    public String delete(@PathVariable long id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }
}
