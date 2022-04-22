package com.gostilo.messager.controller;

import com.gostilo.messager.domain.User;
import com.gostilo.messager.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/page")
public class ProfilePageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user,
                               @AuthenticationPrincipal User u,
                               Model model) {
        model.addAttribute("user", user);
        model.addAttribute("currentuser", u);
        model.addAttribute("messages", messageService.findByAuthor(u));
        return "page";
    }
}
