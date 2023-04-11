package ru.rohlend.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.rohlend.spring.dao.BookDAO;
import ru.rohlend.spring.entities.Book;

@Controller
@ComponentScan("ru.rohlend.spring")
@RequestMapping("/books")
public class BookController {

    private final BookDAO bookDAO;


    @Autowired
    public BookController(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @GetMapping()
    public String mainPage(Model model){
        model.addAttribute("books",bookDAO.getAllBooks());
        return "books/book-main";
    }

    @GetMapping("/{id}")
    public String viewPerson(@PathVariable("id")int id, Model model){
        model.addAttribute("book",bookDAO.get(id));
        return "books/book-view";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") Book book){
        bookDAO.create(book);
        return "redirect:/books";
    }
    @GetMapping("/new")
    public String view(@ModelAttribute("book")Book person){
        return "books/book-new";
    }

    @GetMapping("/{id}/edit")
    public String getEdit(@PathVariable("id")int id,Model model){
        model.addAttribute("book",bookDAO.get(id));
        return "books/book-edit";
    }

    @PatchMapping("/{id}/edit")
    public String edit(@PathVariable("id")int id,@ModelAttribute("book")Book book){
        bookDAO.edit(book,id);
        return "redirect:/books";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id")int id){
        bookDAO.delete(id);
        return "redirect:/books";
    }
}
