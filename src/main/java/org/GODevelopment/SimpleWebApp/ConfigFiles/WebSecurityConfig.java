package org.GODevelopment.SimpleWebApp.ConfigFiles;

import org.GODevelopment.SimpleWebApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

/**
 * The configure(HttpSecurity) method defines which URL paths should be secured and which should not. Specifically, the / and /home paths are configured to not require any authentication. All other paths must be authenticated.
 * When a user successfully logs in, they are redirected to the previously requested page that required authentication. There is a custom /login page (which is specified by loginPage()), and everyone is allowed to view it.
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    // Служба, которая предоставляет учетные записи
    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http    // На вход принимаем http-запрос
                .authorizeRequests()
                    .antMatchers("/", "/registration", "/static/**", "/activate/*").permitAll() // Перечень адрессов, к которым доступ разрешен без авторизации. * осзначает один сегмент
                    .anyRequest().authenticated() // Остальные адресса по доступу только для авторизированных пользователей
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .permitAll() // Форма логина доступна для всех
                .and()
                    .logout()
                    .permitAll(); // Форма логаута доступна для всех
    }

    // Берем пользователей через userDetailsService()
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder // Пароли хранятся в БД в зашифрованном виде. Дешифруем.
                (NoOpPasswordEncoder.getInstance()); // Устаревший, нужен для отладки, будет заменен
    }

    /**
    * jdbcAuthentication - Пользователей берем из БД
     *
     // WebSecurity нужен доступ к нашей БД
     @Autowired
     private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource) // Подключаем БД
                .passwordEncoder // Пароли хранятся в БД в зашифрованном виде. Дешифруем.
                        (NoOpPasswordEncoder.getInstance()) // Устаревший, нужен для отладки, будет заменен
                .usersByUsernameQuery("select username, password, active from r_user where username=?") // auth ищет пользователя в БД по его имени. Используется четкий набор полей username, password, active
                  // Этот запрос позволяет Спрингу получить список пользователей с их ролями.
                 // Получаем имя и роль, объединив две таблицы, через равенство id = user_id, где username равно запросу Спринга
                .authoritiesByUsernameQuery("select u.username, ur.roles from r_user u inner join user_role ur on u.id = ur.user_id where u.username=?");
    }

     * withDefaultPasswordEncoder - Нужен только при отладке приложения. Создает пользователя по умолчанию. будет удалён когда создадим User
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder() // Перечеркнут deprecated устарел, потому что нужен только при отладке приложения.
                        .username("user")
                        .password("password")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user); // менеджер, который обслуживает учтеные записи
    }
    **/
}
