package com.blog.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class RestTemplateUser {

    private Long id;
    private String name;
    private String username;
    private String email;
    private String phone;

}
