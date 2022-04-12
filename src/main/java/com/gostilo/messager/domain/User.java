package com.gostilo.messager.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id;

    @Getter
    private String firstName;

    @Getter
    private String lastName;

    @Getter
    private String email;

    @Getter
    private String username;

    @Getter
    private String password;

    public User(String firstName, String lastName, String email, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format(
                "User [id=%d, firstName='%s', lastName='%s', email='%s', login='%s', password='%s']",
                id, firstName, lastName, email, username, password);
    }
}
