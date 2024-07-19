package com.fslabs.work.controller;

import com.fslabs.work.service.UsersService;
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
    private final UsersService usersService;

    public DashBoardController(UsersService usersService)
    {
        this.usersService = usersService;
    }

    @GetMapping("/dashboard")
    public String searchJobs(Model model)
    {
        log.debug("Rendering dashboard form...");

        Object currentUserProfile = usersService.getCurrentUserProfile();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken))
        {
            String currentUsername = authentication.getName();
            model.addAttribute("username", currentUsername);
        }
        model.addAttribute("user", currentUserProfile);

        return "dashboard";
    }
}
