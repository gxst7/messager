package com.gostilo.messager.service;

import com.gostilo.messager.domain.Message;
import com.gostilo.messager.domain.User;
import com.gostilo.messager.repository.MessageRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class MessageService {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private MessageRepository messageRepository;

    public List<Message> findAll() {
        return (List<Message>) messageRepository.findAll();
    }

    public List<Message> findByAuthor(User author) {
        return (List<Message>) messageRepository.findByAuthor(author);
    }

    public List<Message> findAllFromSubs(User user) {
        List<Message> messages = new LinkedList<>();
        for (Message message : findAll()) {
            if (message.getAuthor().equals(user.getUsername())) {
                messages.add(message);
            }
        }

        return messages;
    }

    public Iterable<Message> findByTag(String filter) {
        return messageRepository.findByTag(filter);
    }

    public void save(Message message) {
        messageRepository.save(message);
    }

    public List<Message> getMessagesFromSubscriptions(User user, String filter, UserService userService) {
        List<Message> messages;

        messages = ( (List<Message>) findAll() )
                .stream()
                .filter(message -> userService.isSubscription(user, message.getAuthor()))
                .collect(Collectors.toList());

        if (filter != null && !filter.isEmpty()) {
            messages = (List<Message>) findByTag(filter);
        }

        return messages;
    }
}
