package com.gostilo.messager.controller;

import com.gostilo.messager.domain.Role;
import com.gostilo.messager.domain.User;
import com.gostilo.messager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String showUserList(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("currentuser", user);
        model.addAttribute("users", userService.findAll());
        return "userList";
    }

    @GetMapping("{user}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userEditForm(@PathVariable User user,
                               @AuthenticationPrincipal User u,
                               Model model) {
        model.addAttribute("currentuser", u);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userSave(
            @AuthenticationPrincipal User u,
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam(name = "userId") User user) {

        userService.saveUser(user, username, form);
        return "redirect:/user";
    }

    @GetMapping("profile")
    public String getProfile(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("currentuser", user);
        return "profile";
    }

    @PostMapping("profile")
    public String updateProfile(@AuthenticationPrincipal User user,
                                @RequestParam String password,
                                @RequestParam String email) {
        userService.updateProfile(user, password, email);

        return "redirect:/user/profile";
    }

    @GetMapping("subscribe/{user}")
    public String subscribe(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user) {
        userService.subscribe(currentUser, user);

        return "redirect:/user/page/" + user.getId();
    }

    @GetMapping("unsubscribe/{user}")
    public String unsubscribe(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user) {
        userService.unsubscribe(currentUser, user);

        return "redirect:/user/page/" + user.getId();
    }

    @GetMapping("/search")
    public String searchUser(@AuthenticationPrincipal User u,
                             @RequestParam(required = false, defaultValue = "") String searched, Model model) {
        User byUsername = userService.findByUsername(searched);
        if (byUsername == null) {
            return "redirect:/home";
        }
        model.getAttribute("searched");
        return "redirect:/page/" + byUsername.getId();
    }
}
