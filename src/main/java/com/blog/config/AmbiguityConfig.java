package com.blog.config;

import com.blog.services.AmbiguityService;
import com.blog.services.impl.ambiguityHandlerImpl.AmbiguityService1Impl;
import com.blog.services.impl.ambiguityHandlerImpl.AmbiguityService2Impl;
import com.blog.services.impl.ambiguityHandlerImpl.AmbiguityService3Impl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AmbiguityConfig {

    @Bean
    @Qualifier("ambiguityService1")
    public AmbiguityService ambiguityService(){
        return new AmbiguityService1Impl();
    }

//    @Bean
//    @Qualifier("ambiguityService2")
//    public AmbiguityService ambiguityService2(){
//        return new AmbiguityService2Impl();
//    }

    @Bean
    @Qualifier("ambiguityService3")
    public AmbiguityService ambiguityService3(){
        return new AmbiguityService3Impl();
    }

    //Note: if we are declaring @Qualifier for all implementation classes either at bean level or controller level
    // then we dont need to give @Primary(which gives default implementation class)..else if we are not giving @Qualifiers for all impl classes:
    //then we need to give below:
    //https://medium.com/lifeinhurry/resolving-ambiguity-in-spring-beans-196e63cb439d
//    @Bean
//    @Primary
//    public AmbiguityService ambiguityService(){
//        return new AmbiguityService1Impl();
//    }
}
