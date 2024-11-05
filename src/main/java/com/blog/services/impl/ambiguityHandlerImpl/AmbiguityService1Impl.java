package com.blog.services.impl.ambiguityHandlerImpl;

import com.blog.services.AmbiguityService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
//@Primary  //now this impl will be used as a default wherever AmbiguityService is autowired..
//either we can declare @primary over ServiceImpl directly or we can create a config class and declare primary over bean
//for reference check AmbiguityConfig.
public class AmbiguityService1Impl implements AmbiguityService {
    @Override
    public String calculation() {
        return "AmbiguityService1Impl";
    }

//We get below error while starting server if we have multiple implementations of 1 Service and we have not defined @primary and @qualifier:
//Consider marking one of the beans as @Primary, updating the consumer to accept multiple beans, or using @Qualifier to identify the bean that should be consumed

}
