package ru.job4j.springrestchat.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.springrestchat.models.Message;

public interface MessageRepository extends CrudRepository<Message, Integer> {
}
