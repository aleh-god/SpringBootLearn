package org.GODevelopment.SimpleWebApp.EntityModel;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "r_user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)     // Указываем на ключ в таблице БД. И передаем базе самой решать каким будет этот ключ
    private Integer id;

    private String username;
    private String password;
    private boolean active;


    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

}
