package org.GODevelopment.SimpleWebApp.Controllers;

import org.GODevelopment.SimpleWebApp.EntityModel.Role;
import org.GODevelopment.SimpleWebApp.EntityModel.User;
import org.GODevelopment.SimpleWebApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userService.findAll());

        return "userList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values()); // Возвращает arrays значений перечисления Role

        return "userEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String userSave(
            @RequestParam String username,          // Мы позволяем менять имя пользователя
            @RequestParam Map<String, String> form, // Колличество чекбоксов может быть разное, поэтому на вход приходит Map с их значениями
            @RequestParam("userId") User user       // привязанный объект для взаимодействия
    ) {
        userService.saveUser(user, username, form);


        return "redirect:/user";
    }

    @GetMapping("profile")
    public String getProfile(
            Model model,
            @AuthenticationPrincipal User user
    ) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());

        return "profile";
    }

    @PostMapping("profile")
    public String updateProfile(
            @AuthenticationPrincipal User user,
            @RequestParam String email,
            @RequestParam String password
    ) {

        userService.updateProfile(user, email, password);

        return "redirect:/user/profile";
    }

}
