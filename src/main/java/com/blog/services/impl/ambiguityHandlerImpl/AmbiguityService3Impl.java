package com.blog.services.impl.ambiguityHandlerImpl;

import com.blog.services.AmbiguityService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
//@Qualifier("ambiguityService3") --- it can be done in 2 ways:
// 1. either we can declare @Qualifier here but along with here, we also need to mention same thing during @Autowired.
// 2. or we can directly declare it with @Bean as declared in AmbiguityConfig file.
public class AmbiguityService3Impl implements AmbiguityService {
    @Override
    public String calculation() {
        return "AmbiguityService3Impl";
    }
}
