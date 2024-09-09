package ru.alishev.stringcourseBoot.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.alishev.stringcourseBoot.models.Person;
import ru.alishev.stringcourseBoot.services.PersonService;


@Component
public class PersonValidator implements Validator {

    private final PersonService personService;

    @Autowired
    public PersonValidator(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Process.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        //Обращение к БД(для этого нужно внедрить PersonDAO) чтобы проверить есть ли такой человек уже с таким email
        if (personService.getPerson(person.getName()).isPresent()) {
            //s - поле, которое вызвало ошибку
            //s1 - код ошибки(пока оставим пустым)
            //s2 - ошибку которую нало вывести
            errors.rejectValue("name", "", "Такое имя уже существует!");
        }
    }
}
