package com.blog.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity  //this annotation is used for role based api access
@EnableMethodSecurity() //this annotation is used for role based api access only.
@EnableWebMvc
public class SecurityConfig {


    @Autowired
    private JwtAuthenticationEntryPoint point;
    @Autowired
    private JwtAuthenticationFilter filter;

    @Autowired
    private UserDetailsService userDetailsService;

    public static final String[] PUBLIC_URLS ={
            "/auth/login","/auth/create-user","/v3/api-docs","/v2/api-docs",
            "/swagger-resources/**","/swagger-ui/**","/webjars/**","/async/**",
            "/restTemplate/**" //this api is for resttemplate
            ,"/webFlux/**",
            "/ambiguityService/**"
    };
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
                .cors(cors->cors.disable())
                .authorizeHttpRequests(auth->auth
                //.requestMatchers("/auth/login").permitAll()
                    //    .requestMatchers("/auth/create-user").permitAll()
                        //This will allow all API endpoints after auth/   .requestMatchers("/auth/**").permitAll()
                        //This will allow all GET API TO BE ACCESSIBLE  .requestMatchers(HttpMethod.GET).permitAll()
                        //This will allow only ADMIN TO ACCESS LOGIN API  .requestMatchers("/auth/login").hasRole("ADMIN")
                        //.requestMatchers("/v2/api-docs").permitAll()  //this is swagger ui url ..so permitting it
                        .requestMatchers(PUBLIC_URLS).permitAll()
                        .anyRequest().authenticated()
                ).exceptionHandling(ex -> ex.authenticationEntryPoint(point))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    //this method is required only when we want userdetails to come from db
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider  = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }
}
