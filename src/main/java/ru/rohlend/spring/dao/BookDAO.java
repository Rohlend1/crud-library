package ru.rohlend.spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.rohlend.spring.entities.Book;
import ru.rohlend.spring.mappers.BookMapper;


import java.util.List;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(Book book){
        jdbcTemplate.update("insert into book(name, author, year) values(?,?,?)"
                ,book.getName(),book.getAuthor(),book.getYear());
    }

    public void edit(Book book,int id){
        jdbcTemplate.update("update book set name = ?, author = ?, year = ? where book_id = ?"
                ,book.getName(),book.getAuthor(),book.getYear(),id);
    }
    public Book get(int id){
        return jdbcTemplate.query("select * from book where book_id = ?",new BookMapper(),id)
                .stream().findAny().orElse(null);
    }
    public List<Book> getAllBooks(){
        return jdbcTemplate.query("select * from book",new BookMapper());
    }
    public void delete(int id){
        jdbcTemplate.update("delete from book where book_id = ?",id);
    }
}
