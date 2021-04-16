package org.GODevelopment.SimpleWebApp.Controllers;

import org.GODevelopment.SimpleWebApp.EntityModel.Role;
import org.GODevelopment.SimpleWebApp.EntityModel.User;
import org.GODevelopment.SimpleWebApp.Repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userRepo.findAll());

        return "userList";
    }

    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values()); // Возвращает arrays значений перечисления Role

        return "userEdit";
    }

    @PostMapping
    public String userSave(
            @RequestParam String username,          // Мы позволяем менять имя пользователя
            @RequestParam Map<String, String> form, // Колличество чекбоксов может быть разное, поэтому на вход приходит Map с их значениями
            @RequestParam("userId") User user       // привязанный объект для взаимодействия
    ) {
        // Сохраняем новое имя
        user.setUsername(username);

        // Создаем словарик ролей
        Set<String> roles = Arrays.stream(Role.values()) // Возвращаем arrays значений перечисления Roles и превращаем в стрим
                .map(Role::name)                        // стрим преобразуем из значений enum в их соотв названия
                .collect(Collectors.toSet());           // поток названий (значений перечисления) преобразуем в Set

        user.getRoles().clear(); // очищаем Set устаревших ролей пользователя

        for (String key : form.keySet()) {                  // Из формы, которая пришла ввиде Map, берем Set ключей
            if (roles.contains(key)) {                      // Отфильтровываем лишние сущности, которые могли попасть в форму. Если в словаре есть такая роль, то
                user.getRoles().add(Role.valueOf(key));     // В Set ролей пользователя добавляем значение enum, которое совпадает по названию с key
                // Небезопасный геттер Сета с ролями
            }
        }

        userRepo.save(user); // метод Hibernate сохраняет объект в БД, предварительно назначив сгенерированный идентификатор.

        return "redirect:/user";
    }
}
