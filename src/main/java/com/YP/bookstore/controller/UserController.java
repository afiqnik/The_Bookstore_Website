package com.YP.bookstore.controller;

import com.YP.bookstore.model.User;
import com.YP.bookstore.model.UserDto;
import com.YP.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;


@Controller
public class UserController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/index")
    public String home(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("userdetail", userDetails);
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model, UserDto userDto, Principal principal) {
        model.addAttribute("user", userDto);
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model, UserDto userDto) {
        model.addAttribute("user", userDto);
        return "register";
    }

    @PostMapping("/register")
    public String registerSava(@ModelAttribute("user") UserDto userDto, Model model) {
        User user = userService.findByUsername(userDto.getUsername());
        if (user != null) {
            model.addAttribute("Userexist", user);
            return "register";
        }
        userService.save(userDto);
        return "redirect:/register?success";
    }
}