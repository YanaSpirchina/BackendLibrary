package ru.alishev.stringcourseBoot.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.alishev.stringcourseBoot.models.Book;
import ru.alishev.stringcourseBoot.models.Person;
import ru.alishev.stringcourseBoot.services.BookService;
import ru.alishev.stringcourseBoot.services.PersonService;
import ru.alishev.stringcourseBoot.util.BookValidator;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    private final PersonService personService;


    private final BookValidator bookValidator;

    public BookController(BookService bookService, PersonService personService, BookValidator bookValidator) {
        this.bookService = bookService;
        this.personService = personService;
        this.bookValidator = bookValidator;
    }

    @GetMapping()
    public String allBooks(Model model, @RequestParam(value = "page", required = false) Integer page,
                           @RequestParam(value = "books_per_age", required = false) Integer books_per_age,
                           @RequestParam(value = "sort_by_year", required = false) boolean isSorted) {
        if (page == null || books_per_age == null) {
            model.addAttribute("books", bookService.allBooks(isSorted));
        } else {
            model.addAttribute("books", bookService.findAllWithPagination(page, books_per_age, isSorted));
        }
        return "books/allBooks";
    }

    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/books/new";
        }
        bookService.addBook(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookService.getBook(id));
        return "books/edit";

    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("book") Book book) {
        bookService.update(id, book);
        return "redirect:/books/{id}";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    //Аннотация @ModelAttribute("person") автоматически создаёт модель с сущностью Person и отправляет его на представление
    //То есть если есть такая запись, то явно это делать не надо
    @GetMapping("/{id}")
    public String index(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookService.getBook(id));
        Optional<Person> ownerBook = Optional.ofNullable(bookService.getOwner(id));
        //IsPresent() - используется с Optional<> - проверка есть ли там значение
        if (ownerBook.isPresent()) {
            //что за метод get() - работа с Optional
            model.addAttribute("owner", ownerBook.get());
        } else {
            model.addAttribute("people", personService.allPeople());
        }
        return "books/index";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        bookService.release(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson) {
        bookService.assign(id, selectedPerson);
        return "redirect:/books/" + id;
    }

    @GetMapping("/search")
    public String searchPage() {
        return "books/searchPage";
    }

    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("request") String request) {
        model.addAttribute("books", bookService.findByNameStartingWith(request));
        return "books/searchPage";
    }
}
