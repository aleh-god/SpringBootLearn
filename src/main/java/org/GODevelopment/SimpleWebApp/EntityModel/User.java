package org.GODevelopment.SimpleWebApp.EntityModel;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "r_user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)     // Указываем на ключ в таблице БД. И передаем базе самой решать каким будет этот ключ
    private Long id;

    // Hibernate automatically translates the entity into a table.
    @NotBlank(message = "Username can be empty")
    private String username; // LeetCode ID
    @NotBlank(message = "Password can be empty")
    private String password;

    @Transient // Не добавляем в БД Нужен только для сверки подтверждения пароля
    @NotBlank(message = "Password confirmation can be empty")
    private String passwordConfirm;

    @NotBlank(message = "Email can be empty")
    @Email(message = "Email is not correct")
    private String email;

    private String emailActivationCode;
    private boolean active;


    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public boolean isAdmin() {
        return roles.contains(Role.ADMIN); // Есть ли в списке ролей админ
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailActivationCode() {
        return emailActivationCode;
    }

    public void setEmailActivationCode(String emailActivationCode) {
        this.emailActivationCode = emailActivationCode;
    }

    // Методы интерфейса UserDetails.Security

    @Override
    public boolean isAccountNonExpired() {
        return true; // Выставляем true по умолчанию (?)
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Выставляем true по умолчанию (?)
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Выставляем true по умолчанию (?)
    }

    @Override
    public boolean isEnabled() {
        return isActive(); // Подставляем наше поле
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles(); // Подставляем наше поле
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
}
