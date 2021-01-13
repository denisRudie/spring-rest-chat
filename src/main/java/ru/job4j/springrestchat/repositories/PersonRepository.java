package ru.job4j.springrestchat.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.springrestchat.models.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> {
}
