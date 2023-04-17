package ru.rohlend.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rohlend.spring.entities.Person;

import java.util.List;

@Repository
public interface PeopleRepository extends JpaRepository<Person,Integer>{

    List<Person> findByFullNameAndPersonIdNot(String fullName, int personId);
}
