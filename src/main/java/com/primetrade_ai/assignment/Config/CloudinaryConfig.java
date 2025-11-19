package com.primetrade_ai.assignment.Config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        String cloudinaryUrl = "cloudinary://217137554518434:874sxfFAKVcnweklQWRYJydqMJ0@dudkg98ml";
        
        return new Cloudinary(cloudinaryUrl);
    }
}