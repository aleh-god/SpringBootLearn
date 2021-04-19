package org.GODevelopment.SimpleWebApp.Service;

import org.GODevelopment.SimpleWebApp.EntityModel.Role;
import org.GODevelopment.SimpleWebApp.EntityModel.User;
import org.GODevelopment.SimpleWebApp.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

// Core interface which loads user-specific data. It is used throughout the framework as a user DAO and is the strategy used by the DaoAuthenticationProvider.
@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //Locates the user based on the username. In the actual implementation, the search may possibly be case sensitive, or case insensitive depending on how the implementation instance is configured. In this case, the UserDetails object that comes back may have a username that is of a different case than what was actually requested..
    //Parameters: username - the username identifying the user whose data is required.
    //Returns: a fully populated user record (never null)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);

        if (user == null) throw new UsernameNotFoundException("User not found");

        return user;
    }

    public boolean addUser(User user) {
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null) {
            return false;
        }

        user.setRoles(Collections.singleton(Role.USER));
        user.setEmailActivationCode(UUID.randomUUID().toString());  // Задаем код активации
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(false);

        userRepo.save(user);

        sendMessage(user);

        return true;}

    private void sendMessage(User user) {
        if (StringUtils.hasText(user.getEmail())) {
            String message = String.format(
              "Hello, %s! \n" +
              "Welcome! Please visit next link for registration: http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getEmailActivationCode()
            );
            mailSender.sendEmail(user.getEmail(), "Activation code", message);
        }
    }

    public boolean activateUser(String code) {
        User user = userRepo.findByEmailActivationCode(code);

        if (user == null) {
            return false;
        }

        user.setActive(true);
        user.setEmailActivationCode(null);  // Возвращаем активированное поле в null, что бы нельзя было код использовать втророй раз

        userRepo.save(user);    // Сохраняем пользователя

        return true;
    }

    public List<User> findAll() {
        return userRepo.findAll(); // Имя метода составляется по правилам Спринга, он его превращает в запрос в БД
    }

    public void saveUser(User user, String username, Map<String, String> form) {
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
    }

    public void updateProfile(User user, String email, String password) {
        String currentEmail = user.getEmail();

        // && - and, || - or. Сравнение на нулл первое, что бы на втором сравнении не выскочило исключение
        boolean isEmailChanged = (email != null && !email.equals(currentEmail) ||
                currentEmail != null && !currentEmail.equals(email));

        if (isEmailChanged) {
            user.setEmail(email);
            if (StringUtils.hasText(email))
                user.setEmailActivationCode(UUID.randomUUID().toString());
        }

        if (StringUtils.hasText(password)) {
            user.setPassword(passwordEncoder.encode(password));
        }

        userRepo.save(user);

        if (isEmailChanged)
            sendMessage(user);

    }
}
