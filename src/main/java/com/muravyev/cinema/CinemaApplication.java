package com.muravyev.cinema;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableJpaRepositories
@EnableJpaAuditing
public class CinemaApplication implements WebMvcConfigurer {

    @Value("${files.storage.url}")
    private String fileStorageURL;

    @Value("${app.resource-handler.path}")
    private String handlerPath;

    public static void main(String[] args) {
        SpringApplication.run(CinemaApplication.class, args);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(handlerPath + "/**")
                .addResourceLocations(fileStorageURL + "/");
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:3000",
                        "http://localhost:3001",
                        "http://cinema.eastus.cloudapp.azure.com/",
                        "http://cinema.eastus.cloudapp.azure.com:8080/")
                .allowedHeaders("*")
                .allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS")
                .allowCredentials(true);
        registry.addMapping("/files/**")
                .allowedOrigins("http://localhost:3000",
                        "http://localhost:3001",
                        "http://cinema.eastus.cloudapp.azure.com/",
                        "http://cinema.eastus.cloudapp.azure.com:8080/")
                .allowedHeaders("*")
                .allowedMethods("GET")
                .allowCredentials(true);
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}