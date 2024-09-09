package ru.alishev.stringcourseBoot.services;

import jakarta.validation.Valid;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alishev.stringcourseBoot.models.Book;
import ru.alishev.stringcourseBoot.models.Person;
import ru.alishev.stringcourseBoot.repositories.PersonRepository;


import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional
    public void addPerson(Person person) {
        personRepository.save(person);
    }

    public List<Person> allPeople() {
        return personRepository.findAll();
    }

    public Object getPerson(int id) {
        return personRepository.findById(id).orElse(null);
    }

    public Optional<Person> getPerson(String name) {
        return personRepository.findByName(name);
    }

    @Transactional
    public void delete(int id) {
        personRepository.deleteById(id);
    }

    @Transactional
    public void update(int id, @Valid Person updatePerson) {
        updatePerson.setId(id);
        personRepository.save(updatePerson);
    }

    public List<Book> getBooksByPersonId(int id) {
        Optional<Person> person = personRepository.findById(id);
        if (person.isPresent()) {
            Hibernate.initialize(person.get().getBooks());

            //Проверка на просроченность
            person.get().getBooks().forEach(book -> {
                long duration = Math.abs(book.getTimeTakenAt().getTime() - new Date().getTime());
                // 864000000 милисекунд = 10 суток
                if (duration > 864000000) {
                    book.setCheckedTime(true);
                }
            });
            return person.get().getBooks();
        } else {
            return Collections.emptyList();
        }
    }
}
