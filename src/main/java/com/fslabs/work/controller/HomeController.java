package com.fslabs.work.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class HomeController
{
    @GetMapping
    public String index()
    {
        log.debug("Rendering index page...");
        return "index";
    }
}
