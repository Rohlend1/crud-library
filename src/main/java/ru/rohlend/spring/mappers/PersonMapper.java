package ru.rohlend.spring.mappers;


import org.springframework.jdbc.core.RowMapper;
import ru.rohlend.spring.entities.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        Person person = new Person();
        person.setYearOfBirth(rs.getInt("year"));
        person.setPersonId(rs.getInt("person_id"));
        person.setFullName(rs.getString("full_name"));
        return person;
    }
}
