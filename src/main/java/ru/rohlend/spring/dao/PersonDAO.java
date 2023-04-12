package ru.rohlend.spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.rohlend.spring.entities.Book;
import ru.rohlend.spring.entities.Person;
import ru.rohlend.spring.mappers.BookMapper;
import ru.rohlend.spring.mappers.PersonMapper;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(Person person){
        jdbcTemplate.update("insert into person(full_name,year) values(?,?)"
                ,person.getFullName(),person.getYearOfBirth());
    }

    public void edit(Person person,int id){
        jdbcTemplate.update("update person set full_name = ?, year = ? where person_id = ?"
                ,person.getFullName(),person.getYearOfBirth(),id);
    }
    public Person get(int id){
        return jdbcTemplate.query("select * from person where person_id = ?",new PersonMapper(),id)
                .stream().findAny().orElse(null);
    }
    public List<Person> getAllPeople(){
        return jdbcTemplate.query("select * from person",new PersonMapper());
    }
    public void delete(int id){
        jdbcTemplate.update("delete from person where person_id = ?",id);
    }

    public List<Book> getBooks(int id){
        return jdbcTemplate.query("select * from book where owner_id = ?"
                ,new BookMapper(),id);
    }

    public Optional<Person> find(String fullName,int id){
        return jdbcTemplate.query("select * from person where full_name = ? and person_id != ?"
                ,new PersonMapper(),fullName,id).stream().findAny();
    }

}
