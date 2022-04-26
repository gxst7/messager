package com.gostilo.messager.controller;

import com.gostilo.messager.domain.Message;
import com.gostilo.messager.domain.User;
import com.gostilo.messager.service.MessageService;
import com.gostilo.messager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class MainController {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(Model model,
                        @AuthenticationPrincipal User u) {
        model.addAttribute("currentuser", u);
        return "index";
    }

    @GetMapping("/home")
    public String home(@AuthenticationPrincipal User user,
                       @RequestParam(required = false, defaultValue = "") String filter,
                       Model model) {

        List<Message> messages = messageService.getMessagesFromSubscriptions(user, filter, userService);

        model.addAttribute("currentuser", user);
        model.addAttribute("listofusers", userService.findAll());
        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);

        return "home";
    }

    @PostMapping("/home")
    public String add(
            @AuthenticationPrincipal User user,
            Message message,
            BindingResult bindingResult,
            Model model,
            @RequestParam("file") MultipartFile file) throws IOException {

        message.setAuthor(user);

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errorsMap);
        } else {

            if (!file.isEmpty()) {
                File uploadDir = new File(uploadPath);

                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }

                String uuidFile = UUID.randomUUID().toString();
                String resultFilename = uuidFile + "." + file.getOriginalFilename();

                file.transferTo(new File(uploadPath + "/" + resultFilename));

                message.setFilename(resultFilename);
            }

            messageService.save(message);
        }

        Iterable<Message> messages = messageService.getMessagesFromSubscriptions(user, "", userService);

        model.addAttribute("listofusers", userService.findAll());
        model.addAttribute("currentuser", user);
        model.addAttribute("messages", messages);

        return "home";
    }
}
