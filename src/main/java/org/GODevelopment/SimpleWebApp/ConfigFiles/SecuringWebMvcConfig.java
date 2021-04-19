package org.GODevelopment.SimpleWebApp.ConfigFiles;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// The web application is based on Spring MVC. As a result, you need to configure Spring MVC and set up view controllers to expose these templates.
@Configuration
public class SecuringWebMvcConfig implements WebMvcConfigurer {

    @Value("${upload.path}") // Анотация ищет значение в пропертис и инжектит значение
    private String uploadPath; // Место хранения файлов

    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/home").setViewName("home");
//        registry.addViewController("/").setViewName("home");
//        registry.addViewController("/hello").setViewName("hello");
        registry.addViewController("/login").setViewName("login");
    }

    // First, we configure the external-facing URI path by adding defining a resource handler.
    // Then, we map that external-facing URI path internally to the physical path where the resources are actually located.
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file://" + uploadPath + "/");
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}
