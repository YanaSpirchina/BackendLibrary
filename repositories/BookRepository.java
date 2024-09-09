package ru.alishev.stringcourseBoot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alishev.stringcourseBoot.models.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
   List<Book> findByNameStartingWith(String startingWith);



}
