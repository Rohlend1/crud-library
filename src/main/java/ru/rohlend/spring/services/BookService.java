package ru.rohlend.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rohlend.spring.entities.Book;
import ru.rohlend.spring.repositories.BookRepository;

import java.util.Comparator;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;


    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
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

    public List<Book> pagination(int page,int booksPerPage,boolean sort){
        List<Book> books;
        if (sort) books = bookRepository.findAll(PageRequest.of(page,booksPerPage, Sort.by("year").ascending())).getContent();
        else books=bookRepository.findAll(PageRequest.of(page,booksPerPage)).getContent();
        return books;
    }

    public List<Book> findAllSortByYear(){
        List<Book> books = findAll();
        books.sort(Comparator.comparingInt(Book::getYear));
        return books;
    }
}
