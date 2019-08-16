package com.lb.security.oauth2.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DemoController {
    @RequestMapping("/blog/{id}")
    public String getBlogById(@PathVariable long id) {
        return "this is blog "+id;
    }
}
