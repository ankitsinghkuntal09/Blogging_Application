package com.blog.services.impl.ambiguityHandlerImpl;

import com.blog.services.AmbiguityService;
import com.blog.services.CustomAnnotation;
import org.springframework.stereotype.Component;

@Component
@CustomAnnotation //here to resolve ambiguity, instead of using @Qualifier, we have created our own custom annotations
//CustomAnnotation is @interface type and we have created in services package.
public class AmbiguityService4Impl implements AmbiguityService {

    @Override
    public String calculation() {
        return "AmbiguityService4Impl";
    }

}
