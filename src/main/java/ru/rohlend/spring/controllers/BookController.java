package ru.rohlend.spring.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.rohlend.spring.dao.BookDAO;
import ru.rohlend.spring.dao.PersonDAO;
import ru.rohlend.spring.entities.Book;

@Controller
@ComponentScan("ru.rohlend.spring")
@RequestMapping("/books")
public class BookController {

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;


    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String mainPage(Model model){
        model.addAttribute("books",bookDAO.getAllBooks());
        return "books/book-main";
    }

    @GetMapping("/{id}")
    public String viewPerson(@PathVariable("id")int id, Model model){
        model.addAttribute("book",bookDAO.get(id));
        model.addAttribute("person",bookDAO.getOwner(id));
        model.addAttribute("people",personDAO.getAllPeople());
        return "books/book-view";
    }

    @PatchMapping("/{id}/assign")
    public String getAssigned(@PathVariable("id")int bookId,@ModelAttribute("book")Book book){
        bookDAO.assign(bookId,book);
        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/free")
    public String freeTheBook(@PathVariable("id")int id){
        bookDAO.free(id);
        return "redirect:/books/{id}";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("errors",bindingResult.getAllErrors());
            return "books/book-new";
        }
        else {
            bookDAO.create(book);
            return "redirect:/books";
        }
    }
    @GetMapping("/new")
    public String view(@ModelAttribute("book")Book book){
        return "books/book-new";
    }

    @GetMapping("/{id}/edit")
    public String getEdit(@PathVariable("id")int id,Model model){
        model.addAttribute("book",bookDAO.get(id));
        return "books/book-edit";
    }

    @PatchMapping("/{id}/edit")
    public String edit(@PathVariable("id")int id,@ModelAttribute("book") @Valid Book book,BindingResult bindingResult,Model model){
        Book originBook = bookDAO.get(id);
        if(originBook==null){
            return "books/book-edit";
        }
        if(bindingResult.hasErrors()){
            model.addAttribute("book",originBook);
            model.addAttribute("errors",bindingResult.getAllErrors());
            return "books/book-edit";
        }
        bookDAO.edit(book, id);
        return "redirect:/books";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id")int id){
        bookDAO.delete(id);
        return "redirect:/books";
    }
}
