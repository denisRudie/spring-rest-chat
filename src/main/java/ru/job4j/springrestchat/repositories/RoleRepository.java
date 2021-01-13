package ru.job4j.springrestchat.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.springrestchat.models.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {
}
