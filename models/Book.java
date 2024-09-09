package ru.alishev.stringcourseBoot.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import java.util.Date;

@Entity
@Table(name = "Book")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Заполните поле!")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Заполните поле!")
    @Column(name = "author")
    private String author;


    @Min(value = 0, message = "Возраст должен быть больше 0!")
    @Column(name = "year")
    private int year;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;


    @Column(name = "taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeTakenAt;

   @Transient
    private boolean isCheckedTime;

    public Book() {

    }

    public Book(int id, String name, String author, int year) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public void setId(int id_book) {
        this.id= id_book;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Person getOwner() {
        return owner;
    }

    public Date getTimeTakenAt() {
        return timeTakenAt;
    }

    public void setTimeTakenAt(Date timeTakenAt) {
        this.timeTakenAt = timeTakenAt;
    }

    public void setCheckedTime(boolean checkedTime) {
        isCheckedTime = checkedTime;
    }

    public boolean isCheckedTime() {
        return isCheckedTime;
    }
}
