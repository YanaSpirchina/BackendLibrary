package ru.alishev.stringcourseBoot.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.alishev.stringcourseBoot.models.Person;
import ru.alishev.stringcourseBoot.services.PersonService;
import ru.alishev.stringcourseBoot.util.PersonValidator;

@Controller
@RequestMapping("/people")
public class PersonController {
    private final PersonValidator personValidator;

    private final PersonService personService;

    @Autowired
    public PersonController(PersonValidator personValidator, PersonService personService) {
        this.personValidator = personValidator;
        this.personService = personService;
    }

    @GetMapping("")
    public String allPeople(Model model) {
        System.out.println("");
        model.addAttribute("people", personService.allPeople());
        return "people/allPeople";
    }

    @GetMapping("/new")
    public String newPerson(Model model) {
        model.addAttribute("person", new Person());
        return "people/new";
    }

    @PostMapping("")
    public String createPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {

            return "people/new";
        }
        personService.addPerson(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}")
    public String personId(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personService.getPerson(id));
        model.addAttribute("books",personService.getBooksByPersonId(id));
        return "people/index";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personService.getPerson(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@PathVariable("id") int id, @ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {

            return "people/edit";
        }
        personService.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        personService.delete(id);
        return "redirect:/people";
    }
}
