package com.primetrade_ai.assignment.Config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;


@Configuration
public class CloudinaryConfig {
    

    @Value("${cloudinary.api.key}")
    private String cloudinaryApiKey;

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(this.cloudinaryApiKey);
    }
}