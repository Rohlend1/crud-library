package ru.rohlend.spring.entities;
import org.springframework.stereotype.Component;


@Component
public class Person {
    private int personId;
    private int yearOfBirth;
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
