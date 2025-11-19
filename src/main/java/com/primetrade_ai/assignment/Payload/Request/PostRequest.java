package com.primetrade_ai.assignment.Payload.Request;


import lombok.Data;

@Data
public class PostRequest {
    private String title;
    private String body;
    private String thumbnail;
    private Long categoryId;
    private Boolean isFeatured;
}
