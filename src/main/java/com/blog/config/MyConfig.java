package com.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
class MyConfig {

    //commented this code as we are going to get username and password from DB.
    //code to get username and password from DB is in CustomUserDetailsService
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails userDetails = User.builder().
//                username("Saurabh")
//                .password(passwordEncoder().encode("Saurabh")).roles("ADMIN").
//                build();
//        return new InMemoryUserDetailsManager(userDetails);
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}