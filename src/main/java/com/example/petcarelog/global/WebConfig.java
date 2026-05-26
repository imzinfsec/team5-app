package com.example.petcarelog.global;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${upload.dir:./uploads/pets}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /images/pets/** 요청 → 실제 파일 경로로 매핑
        registry.addResourceHandler("/images/pets/**")
                .addResourceLocations("file:" + uploadDir + "/");
    }
}