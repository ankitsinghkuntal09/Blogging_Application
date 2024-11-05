package com.blog.services.impl.ambiguityHandlerImpl;

import com.blog.services.AmbiguityService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("ambiguityService2")
public class AmbiguityService2Impl implements AmbiguityService {
    @Override
    public String calculation() {
        return "AmbiguityService2Impl";
    }
}
