package com.fslabs.work.controller;

import com.fslabs.work.entity.Users;
import com.fslabs.work.entity.UsersType;
import com.fslabs.work.service.UsersService;
import com.fslabs.work.service.UsersTypeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;


import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class UsersController
{

    private final UsersTypeService usersTypeService;
    private final UsersService usersService;

    @Autowired
    public UsersController(UsersTypeService usersTypeService, UsersService usersService)
    {
        this.usersTypeService = usersTypeService;
        this.usersService = usersService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model)
    {
        List<UsersType> usersTypes = usersTypeService.getAll();
        model.addAttribute("userTypes", usersTypes);
        model.addAttribute("user", new Users());
        return "register";
    }

    @PostMapping("/register/new")
    public String createNewUserRegistration(@Valid Users users, Model model)
    {
        Optional<Users> optionalUsers = usersService.getUserByEmail(users.getEmail());
        if (optionalUsers.isPresent())
        {
            model.addAttribute("error", "Email already registered.Try to login or register with other email.");
            List<UsersType> usersTypes = usersTypeService.getAll();
            model.addAttribute("userTypes", usersTypes);
            model.addAttribute("user", new Users());
            return "register";
        }
        usersService.addNew(users);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login()
    {
        log.debug("Rendering Login Form...");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response)
    {

        log.debug("Logging out the user...");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null)
        {
            SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
            logoutHandler.logout(request, response, authentication);
        }

        return "redirect:/";
    }
}
