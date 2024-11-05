package com.blog.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name="users")
//below properties are from lombok
@NoArgsConstructor
@Getter
@Setter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="user_name",nullable = false,length = 100)
    private String name;

    @Column(unique = true,updatable = false)
    private String email;
    private String password;
    private String about;

    //it means 1 user can have many posts.
    //lazy means parent will be loaded and child will not be loaded
    //mappedBy="user", this user we will find in Post Class.
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

    @ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinTable(name="users_role",
    joinColumns=@JoinColumn(name="users",referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name="role",referencedColumnName = "id"))
    //it will create a new table for many to many whose name will be users_role with 2 columns users and role
    //users will store userid and role will store roleId
    private Set<Role> roles = new HashSet<>();

    //All these below methods came as we implemented UserDetails and this UserDetails is required for implementing JWT token
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //we have overriden this below method to implement role based API access.
        List<SimpleGrantedAuthority> authorities = this.roles.stream().map((role)->new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
