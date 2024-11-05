package com.blog.controllers;

import com.blog.entities.RestTemplateUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//No extra dependency required for RestTemplate..RestTemplate is used to consume other REST APIs.
//Spring starter MVC dependency will take care of RestTemplate
@RestController
@RequestMapping("/restTemplate")
@Hidden
public class RestTemplateController {

    @GetMapping("/cities1")
    public List<Object> getCity(){

        String uri = "https://countriesnow.space/api/v0.1/countries";
        String uri1 = "https://jsonplaceholder.typicode.com/users";

        RestTemplate template = new RestTemplate();
        Object[] objects = new Object[] { template.getForObject(uri, Object.class) };
        return Arrays.asList(objects);
    }

    //http://localhost:8080/users-resttemplate
    // Retrieving POJO Instead of JSON
    @GetMapping("/users-resttemplate")
    public List<String> getUsers(){

        String uri = "https://jsonplaceholder.typicode.com/users";

        RestTemplate template = new RestTemplate();

        Object[] objects = new Object[] { template.getForObject(uri, Object.class) };

        ObjectMapper objectMapper = new ObjectMapper();

        List<String> users = new ArrayList<>();

        for(int i = 0;i< ((ArrayList<?>)objects[0]).size();i++ ) {
            try {
                RestTemplateUser u =objectMapper.convertValue(((ArrayList<?>) objects[0]).get(i), RestTemplateUser.class);
                users.add(u.getName());
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        //   OR using lambda
        //
        //  List<String> users= ((ArrayList<?>) objects[0]).stream().map(o-> objectMapper.convertValue(o, User.class))
        //      .map(User::getName)
        //      .collect(Collectors.toList());

        return users;


    }

    //http://localhost:8080/users-resttemplate1
    // For  Plain JSON
    @GetMapping("/users-resttemplate1")
    public String getUsers1(){

        RestTemplate restTemplate = new RestTemplate();
        String uri = "https://jsonplaceholder.typicode.com/users";

        ResponseEntity<String> response
                = restTemplate.getForEntity(uri + "/1", String.class);

        //System.out.println(response.getStatusCode()+"-->"+ HttpStatus.OK);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = null;
        try {
            root = mapper.readTree(response.getBody());
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        JsonNode name = root.path("name");
        return name.asText();
    }


}


// https://www.baeldung.com/rest-template

// Use HEAD to Retrieve Headers
//HttpHeaders httpHeaders = restTemplate.headForHeaders(fooResourceUrl);
//Assertions.assertTrue(httpHeaders.getContentType().includes(MediaType.APPLICATION_JSON));
