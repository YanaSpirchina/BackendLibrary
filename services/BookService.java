package ru.alishev.stringcourseBoot.services;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alishev.stringcourseBoot.models.Book;
import ru.alishev.stringcourseBoot.models.Person;
import ru.alishev.stringcourseBoot.repositories.BookRepository;


import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    public void addBook(@Valid Book book) {
        bookRepository.save(book);
    }

    public List<Book> allBooks(boolean isSorted) {
        if (isSorted) {
            return bookRepository.findAll(Sort.by("year"));
        }
        return bookRepository.findAll();
    }

    public Book getBook(int id) {
        return bookRepository.findById(id).orElse(null);
    }


    @Transactional
    public void update(int id, Book book) {
        book.setId(id);
        bookRepository.save(book);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    public Person getOwner(int id) {

        return bookRepository.findById(id).map(Book::getOwner).orElse(null);
    }

    @Transactional
    public void release(int id) {
        Optional<Book> book = bookRepository.findById(id);
        book.ifPresent(value -> {
            value.setOwner(null);
            value.setTimeTakenAt(null);
        });
    }

    @Transactional
    public void assign(int id, Person selectedPerson) {
        bookRepository.findById(id).ifPresent(
                book -> {
                    book.setOwner(selectedPerson);
                    book.setTimeTakenAt(new Date()); // текущее время
                }
        );
    }

    public List<Book> findAllWithPagination(Integer page, Integer list_per_page, boolean isSorted) {
        if (isSorted) {
            return bookRepository.findAll(PageRequest.of(page, list_per_page, Sort.by("year"))).getContent();
        }
        return bookRepository.findAll(PageRequest.of(page, list_per_page)).getContent();
    }

    public List<Book> findByNameStartingWith(String startingWith) {
        return bookRepository.findByNameStartingWith(startingWith);
    }
}
