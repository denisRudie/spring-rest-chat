package ru.job4j.springrestchat.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "room")
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String name;

    public static Room of(String name) {
        Room room = new Room();
        room.name = name;
        return room;
    }
}
