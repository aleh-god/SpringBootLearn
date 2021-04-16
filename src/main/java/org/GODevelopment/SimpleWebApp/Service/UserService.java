package org.GODevelopment.SimpleWebApp.Service;

import org.GODevelopment.SimpleWebApp.EntityModel.Role;
import org.GODevelopment.SimpleWebApp.EntityModel.User;
import org.GODevelopment.SimpleWebApp.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.UUID;

// Core interface which loads user-specific data. It is used throughout the framework as a user DAO and is the strategy used by the DaoAuthenticationProvider.
@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MailSender mailSender;

    // Locates the user based on the username. In the actual implementation, the search may possibly be case sensitive, or case insensitive depending on how the implementation instance is configured. In this case, the UserDetails object that comes back may have a username that is of a different case than what was actually requested..
    //Parameters: username - the username identifying the user whose data is required.
    //Returns: a fully populated user record (never null)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }

    public boolean addUser(User user) {
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null) {
            return false;
        }

        user.setRoles(Collections.singleton(Role.USER));
        user.setEmailActivationCode(UUID.randomUUID().toString());  // Задаем код активации
        user.setActive(false);

        userRepo.save(user);

        if (StringUtils.hasText(user.getEmail())) {
            String message = String.format(
              "Hello, %s! \n" +
              "Welcome! Please visit next link for registration: http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getEmailActivationCode()
            );
            mailSender.sendEmail(user.getEmail(), "Activation code", message);
        }

    return true;}

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
}
