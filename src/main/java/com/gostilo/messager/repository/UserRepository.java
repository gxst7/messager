package com.gostilo.messager.repository;

import com.gostilo.messager.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByFirstName(String firstName);

    List<User> findByLastName(String lastName);

    User findByUsername(String username);

    User findById(long id);
}
