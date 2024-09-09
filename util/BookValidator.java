package ru.alishev.stringcourseBoot.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BookValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Process.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

    }
}
