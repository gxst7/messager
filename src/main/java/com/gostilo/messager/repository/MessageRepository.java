package com.gostilo.messager.repository;

import com.gostilo.messager.domain.Message;
import com.gostilo.messager.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {

    List<Message> findByTag(String tag);

    List<Message> findByAuthor(User author);
}