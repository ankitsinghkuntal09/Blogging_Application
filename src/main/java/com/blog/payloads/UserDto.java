package com.blog.payloads;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
//This DTO will be used for exposing attributes. i.e. we are not going to expose User entity now and will use User entity
//for DB only.
//Data Transfer Object
public class UserDto {

    private int id;

    //These annotations like @NotNull,@Email is spring starter validator annotation used for validating request payload from
    //user. We are putting annotation here because in UserController, we are getting UserDto type request payload from user.
    //@NotNull
    //@NonEmpty will check both null and empty string.
    @NotEmpty
    @Size(min=4,message = "Username must be minimum of 4 characters!!")
    private String name;

    //this message will be thrown when user puts incorrect emailId
    @Email(message = "Email Address is not valid!!")
    private String email;

    @NotEmpty
    @Size(min=6, max = 10, message = "Password must be of 6 chars min and max of 10 chars!!")
    //this @Pattern will take regex and will validate it.
    //@Pattern(regexp = )
    private String password;

    @NotEmpty
    @Size(min=10,message = "About must be minimum of 4 characters!!")
    private String about;
}
