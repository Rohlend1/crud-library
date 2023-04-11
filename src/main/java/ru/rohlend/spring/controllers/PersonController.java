package ru.rohlend.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.rohlend.spring.dao.PersonDAO;
import ru.rohlend.spring.entities.Person;
import org.springframework.ui.Model;

@Controller
@ComponentScan("ru.rohlend.spring")
@RequestMapping("/people")
public class PersonController {

    private final PersonDAO personDAO;


    @Autowired
    public PersonController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String mainPage(Model model){
        model.addAttribute("people",personDAO.getAllPeople());
        return "people/person-main";
    }

    @GetMapping("/{id}")
    public String viewPerson(@PathVariable("id")int id,Model model){
        model.addAttribute("person",personDAO.get(id));
        return "people/person-view";
    }

    @PostMapping()
    public String create(@ModelAttribute("person")Person person){
        personDAO.create(person);
        return "redirect:/people";
    }
    @GetMapping("/new")
    public String view(@ModelAttribute("person")Person person){
        return "people/person-new";
    }

    @GetMapping("/{id}/edit")
    public String getEdit(@PathVariable("id")int id,Model model){
        model.addAttribute("person",personDAO.get(id));
        return "people/person-edit";
    }

    @PatchMapping("/{id}/edit")
    public String edit(@PathVariable("id")int id,@ModelAttribute("person")Person person){
        personDAO.edit(person,id);
        return "redirect:/people";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id")int id){
        personDAO.delete(id);
        return "redirect:/people";
    }

}
