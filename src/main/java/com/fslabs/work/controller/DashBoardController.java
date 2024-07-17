package com.fslabs.work.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Slf4j
@Controller
public class DashBoardController
{
    @GetMapping("/dashboard")
    public String searchJobs(Model model)
    {
        log.debug("Rendering dashboard form...");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken))
        {
            String currentUsername = authentication.getName();
            model.addAttribute("username", currentUsername);
        }

        return "dashboard";
    }
}
