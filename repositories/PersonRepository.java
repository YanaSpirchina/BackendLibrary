package ru.alishev.stringcourseBoot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alishev.stringcourseBoot.models.Person;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByName(String name);
}
