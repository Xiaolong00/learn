package com.raines.javaadvanced.filterDemo.demo;

import com.raines.javaadvanced.aopDemo.annotation.CacheRefresh;
import com.raines.javaadvanced.expectionprocess.LogicException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {


    @CacheRefresh
    @GetMapping("/create")
    public boolean create() {
        return true;
    }


    @GetMapping("/login")
    public boolean login() {
        throw new LogicException("error!!!");
    }


}
