package com.gostilo.messager.controller;

import com.gostilo.messager.exception.UserNotFoundException;
import com.gostilo.messager.domain.Message;
import com.gostilo.messager.domain.User;
import com.gostilo.messager.repository.MessageRepository;
import com.gostilo.messager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/home")
    public String home(@RequestParam(value = "name", defaultValue = "World") String name, Model model) {
        Iterable<Message> messages = messageRepository.findAll();
        model.addAttribute("messages", messages);
        return "home";
    }

    @PostMapping("/home")
    public String home(@RequestParam String text, @RequestParam String tag, Model model) {
        Message message = new Message(text, tag);
        messageRepository.save(message);
        Iterable<Message> messages = messageRepository.findAll();
        model.addAttribute("messages", messages);
        return "home";
    }

    @PostMapping("/filter")
    public String filter(@RequestParam String filter, Model model) {
        Iterable<Message> messages;
        if (filter != null && !filter.isEmpty()) {
            messages = messageRepository.findByTag(filter);
        } else {
            messages = messageRepository.findAll();
        }
        model.addAttribute("messages", messages);
        return "home";
    }

    @GetMapping("/users")
    List<User> getAll() {
        return userRepository.findAll();
    }

    @PostMapping("/users")
    User newEmployee(@RequestBody User newEmployee) {
        return userRepository.save(newEmployee);
    }

    @GetMapping("/users/{id}")
    User getOne(@PathVariable Long id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @DeleteMapping("/users/{id}")
    void deleteEmployee(@PathVariable Long id) {
        userRepository.deleteById(id);
    }
}
