package ru.job4j.springrestchat.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "message")
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Date created;

    @Column(length = 1000, nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person author;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    public Message() {
        this.created = new Date();
    }

    public static Message of(String text, Person author, Room room) {
        Message message = new Message();
        message.text = text;
        message.author = author;
        message.room = room;
        return message;
    }
}
