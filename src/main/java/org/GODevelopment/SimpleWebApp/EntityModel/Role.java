package org.GODevelopment.SimpleWebApp.EntityModel;

import org.springframework.security.core.GrantedAuthority;

// Привязываем наши роли к ролям Spring.Security
public enum Role implements GrantedAuthority {
    USER,
    ADMIN;

    @Override
    public String getAuthority() {
        return name(); // метод класса Enum, возвращает строковое представление значения Role
    }
}
