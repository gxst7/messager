package com.gostilo.messager.service;

import com.gostilo.messager.domain.Message;
import com.gostilo.messager.domain.User;
import com.gostilo.messager.repository.MessageRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
