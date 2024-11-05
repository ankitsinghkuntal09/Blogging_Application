package com.blog.controllers;


import com.blog.services.AmbiguityService;
import com.blog.services.CustomAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ambiguityService")
public class AmbiguityController {

    @Autowired
    private AmbiguityService ambiguityService;

    @Autowired
    @Qualifier("ambiguityService2")
    private AmbiguityService ambiguityService2;

    @Autowired
    //@Qualifier("ambiguityService3") //we need to uncomment this code...if @Qualifier is defined at Contoller Level instead of
    //@Bean level..like we have done for ambiguityService2
    private AmbiguityService ambiguityService3;

    @Autowired
    @CustomAnnotation //here we have created our own CustomAnnotation.
    private AmbiguityService ambiguityService4;

    @GetMapping("/ambiguity1")
    public String ambiguity1(){
        return ambiguityService.calculation();
    }

    @GetMapping("/ambiguity2")
    public String ambiguity2(){
        return ambiguityService2.calculation();
    }

    @GetMapping("/ambiguity3")
    public String ambiguity3(){
        return ambiguityService3.calculation();
    }

    @GetMapping("/ambiguity4")
    public String ambiguity4(){
        return ambiguityService4.calculation();
    }


}
