package ru.rohlend.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rohlend.spring.entities.Book;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {

}
