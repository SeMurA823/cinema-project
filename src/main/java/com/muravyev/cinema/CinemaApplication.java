package com.muravyev.cinema;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableJpaRepositories
public class CinemaApplication implements WebMvcConfigurer {

    @Value("${upload.default.path}")
    private String fileStoragePath;

    @Value("${app.resource-handler.path}")
    private String handlerPath;

    public static void main(String[] args) {
        SpringApplication.run(CinemaApplication.class, args);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(handlerPath + "/**")
                .addResourceLocations("file://" + fileStoragePath + "/");
    }
}