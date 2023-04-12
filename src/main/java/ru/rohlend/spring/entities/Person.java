package ru.rohlend.spring.entities;
import jakarta.validation.constraints.*;
import org.springframework.stereotype.Component;


@Component
public class Person {
    private int personId;

    @Max(value = 2023,message = "Year can't be bigger than it's possible")
    @Min(value = 2023-150,message = "Year can't be less than it's possible")
    private int yearOfBirth;

    @Size(min = 3, message = "Invalid name")
    @NotNull(message = "Name should not be null")
    @NotEmpty(message = "Name should not be empty")
    private String fullName;

    public Person(int personId, int yearOfBirth, String fullName) {
        this.personId = personId;
        this.yearOfBirth = yearOfBirth;
        this.fullName = fullName;
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
