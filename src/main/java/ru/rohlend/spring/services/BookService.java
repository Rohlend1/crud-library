package ru.rohlend.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rohlend.spring.entities.Book;
import ru.rohlend.spring.entities.Person;
import ru.rohlend.spring.repositories.BookRepository;
import ru.rohlend.spring.repositories.PeopleRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public BookService(BookRepository bookRepository, PeopleRepository peopleRepository) {
        this.bookRepository = bookRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    public Book findById(int id){
        return bookRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Book book){
        book.setOwner(null);
        bookRepository.save(book);
    }

    @Transactional
    public void delete(int id){
        bookRepository.delete(findById(id));
    }

    @Transactional
    public void edit(int id,Book book){
        book.setId(id);
        bookRepository.save(book);
    }

    @Transactional
    public void assign(Book book,int id){
        Book changingBook = findById(id);
        changingBook.setOwner(book.getOwner());
        bookRepository.save(changingBook);
    }
    @Transactional
    public void free(Book book){
        book.setOwner(null);
        bookRepository.save(book);
    }
}
