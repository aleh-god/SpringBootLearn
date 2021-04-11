package org.GODevelopment.SimpleWebApp.Repository;

import org.GODevelopment.SimpleWebApp.EntityModel.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {

    // Declare query methods on the interface.
    // Имя метода составляется по правилам Спринга, он его превращает в запрос в БД
    User findByUsername(String username);
}
