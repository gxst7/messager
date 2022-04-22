package com.gostilo.messager.controller;

import com.gostilo.messager.domain.User;
import com.gostilo.messager.repository.UserRepository;
import com.gostilo.messager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(@AuthenticationPrincipal User u, Model model) {
        model.addAttribute("currentuser", u);
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@AuthenticationPrincipal User u,
                          @RequestParam("avatar") MultipartFile file,
                          User user, Model model) throws IOException {

        if (!userService.addUser(user, file)) {
            model.addAttribute("message", "User exists!");
            return "registration";
        }
        model.addAttribute("currentuser", u);
        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(@AuthenticationPrincipal User u,
                           Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);
        model.addAttribute("currentuser", u);

        if (isActivated) {
            model.addAttribute("message", "User successfully activated");
        } else {
            model.addAttribute("message", "Activation code is not found!");
        }

        return "login";
    }
}