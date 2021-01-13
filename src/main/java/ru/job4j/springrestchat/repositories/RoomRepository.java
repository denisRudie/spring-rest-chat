package ru.job4j.springrestchat.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.springrestchat.models.Room;

public interface RoomRepository extends CrudRepository<Room, Integer> {
}
