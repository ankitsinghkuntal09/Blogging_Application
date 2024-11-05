package com.blog.controllers;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.UserDto;
import com.blog.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    //here @Valid annotation is for Spring starter validation
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
    {
        UserDto createUserDto = this.userService.createUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    //userId is path URI variable
    //Note: if we are taking same variable name then we can also use like this:
    //public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto,@PathVariable Integer userId)
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("userId") Integer uId)
    {
        UserDto updatedUser = this.userService.updateUser(userDto,uId);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')") //it will make this API only accessible to user with ADMIN role
    //to test role based access..
    //insert in users_role table of user id and role id of ROLE_USER and ROLE_ADMIN .i.e. 1 2 and 2 1
    // in role tables ..insert 1 ROLE_ADMIN
    //insert 2 ROLE_USER

    //then user 2 will be able to access this api since it has JWT token of admin access.
    //if we don't know return type, we can use ?
    public ResponseEntity<?> deleteUser(@PathVariable Integer userId)
    {
         this.userService.deleteUser(userId);
         //return new ResponseEntity((Map.of("message","User has been deleted successfully")),HttpStatus.OK);
         //OR
        return new ResponseEntity(new ApiResponse("User deleted successfully",true),HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers()
    {
        List<UserDto> list = this.userService.getAllUsers();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Integer userId)
    {
        return ResponseEntity.ok(this.userService.getUserById(userId));
    }

    //it will return logged in user.
    @GetMapping("/current-user")
    public String getLoggedInUser(Principal p)
    {
        return p.getName();
    }
}
