package ru.rohlend.spring.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import ru.rohlend.spring.entities.Book;
import ru.rohlend.spring.services.BookService;
import ru.rohlend.spring.services.PeopleService;

import java.util.List;

@Controller
@ComponentScan("ru.rohlend.spring")
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    private final PeopleService peopleService;


    @Autowired
    public BookController(BookService bookService, PeopleService peopleService) {
        this.bookService = bookService;
        this.peopleService = peopleService;

    }
    @GetMapping()
    public String mainPage(Model model,
               @RequestParam(name = "sort_by_year",required = false)boolean sort,
               @RequestParam(name="page",required = false)Integer page,
               @RequestParam(name = "books_per_page",required = false)Integer booksPerPage){
        List<Book> books;
        if(sort && (page == null || booksPerPage == null)){
            books = bookService.findAllSortByYear();
        }
        else if(page == null || booksPerPage == null) books = bookService.findAll();
        else{
           books = bookService.pagination(page,booksPerPage,sort);
        }
        model.addAttribute("books",books);
        return "books/book-main";
    }

    @GetMapping("/search")
    public String searchingPage(Model model,@RequestParam(name = "found",required = false)String startsWith){
        if(startsWith != null) model.addAttribute("found",bookService.findByNameStartingWith(startsWith));
        else model.addAttribute("found",null);
        model.addAttribute("books",bookService.findAll());
        return "books/book-search";
    }


    @GetMapping("/{id}")
    public String viewPerson(@PathVariable("id")int id, Model model){
        Book book = bookService.findById(id);
        model.addAttribute("book",book);
        model.addAttribute("person",book.getOwner());
        model.addAttribute("people",peopleService.findAll());
        return "books/book-view";
    }

    @PatchMapping("/{id}/assign")
    public String getAssigned(@PathVariable("id")int bookId,@ModelAttribute("book")Book book){
        book.setOwner(peopleService.findById(Integer.parseInt(book.getOwnerName())));
        System.out.println(book.getOwnerName());
        bookService.assign(book,bookId);
        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/free")
    public String freeTheBook(@PathVariable("id")int id){
        bookService.free(bookService.findById(id));
        return "redirect:/books/{id}";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("errors",bindingResult.getAllErrors());
            return "books/book-new";
        }
        System.out.println("pre-save");
        bookService.save(book);
        System.out.println("post-save");
        return "redirect:/books";

    }
    @GetMapping("/new")
    public String view(@ModelAttribute("book")Book book){
        return "books/book-new";
    }

    @GetMapping("/{id}/edit")
    public String getEdit(@PathVariable("id")int id,Model model){
        model.addAttribute("book",bookService.findById(id));
        return "books/book-edit";
    }

    @PatchMapping("/{id}/edit")
    public String edit(@PathVariable("id")int id,@ModelAttribute("book") @Valid Book book,BindingResult bindingResult,Model model){
        Book originBook = bookService.findById(id);
        if(originBook==null){
            return "books/book-edit";
        }
        if(bindingResult.hasErrors()){
            model.addAttribute("book",originBook);
            model.addAttribute("errors",bindingResult.getAllErrors());
            return "books/book-edit";
        }
        bookService.edit(id,book);
        return "redirect:/books";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id")int id){
        bookService.delete(id);
        return "redirect:/books";
    }
}
