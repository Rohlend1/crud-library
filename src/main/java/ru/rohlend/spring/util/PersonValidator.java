package ru.rohlend.spring.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.rohlend.spring.entities.Person;
import ru.rohlend.spring.services.PeopleService;

@Component
public class PersonValidator implements Validator {

    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Person.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if(!peopleService.findByFullNameAndPersonIdNot(person.getFullName(),person.getPersonId()).isEmpty()){
            errors.rejectValue("fullName","","This name is already taken");
        }
    }
}
