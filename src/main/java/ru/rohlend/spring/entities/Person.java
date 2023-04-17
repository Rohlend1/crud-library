package ru.rohlend.spring.entities;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.stereotype.Component;

import java.util.List;


@Entity
@Table(name = "person")
public class Person {
    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int personId;

    @Max(value = 2023,message = "Year can't be bigger than it's possible")
    @Min(value = 2023-150,message = "Year can't be less than it's possible")
    @Column(name = "year")
    private int yearOfBirth;

    @Size(min = 3, message = "Invalid name")
    @NotNull(message = "Name should not be null")
    @NotEmpty(message = "Name should not be empty")
    @Column(name = "full_name")
    private String fullName;

    @OneToMany(mappedBy = "owner",fetch = FetchType.EAGER)
    private List<Book> books;

    public Person(int yearOfBirth, String fullName) {
        this.yearOfBirth = yearOfBirth;
        this.fullName = fullName;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Person() {
    }
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }


    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public int getPersonId() {
        return personId;
    }
}
