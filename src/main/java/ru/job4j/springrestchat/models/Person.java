package ru.job4j.springrestchat.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "person")
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public static Person of(String username, String password, Role role) {
        Person person = new Person();
        person.username = username;
        person.password = password;
        person.role = role;
        return person;
    }
}
