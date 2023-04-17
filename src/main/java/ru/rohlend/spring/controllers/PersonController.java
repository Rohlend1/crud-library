package ru.rohlend.spring.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.rohlend.spring.entities.Person;
import org.springframework.ui.Model;
import ru.rohlend.spring.services.PeopleService;
import ru.rohlend.spring.util.PersonValidator;

@Controller
@ComponentScan("ru.rohlend.spring")
@RequestMapping("/people")
public class PersonController {



    private final PeopleService peopleService;
    private final PersonValidator personValidator;


    @Autowired
    public PersonController(PeopleService peopleService, PersonValidator personValidator) {
        this.peopleService = peopleService;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String mainPage(Model model){
        model.addAttribute("people", peopleService.findAll());
        return "people/person-main";
    }

    @GetMapping("/{id}")
    public String viewPerson(@PathVariable("id")int id,Model model){
        Person person = peopleService.findById(id);
        System.out.println(person.getBooks());
        model.addAttribute("person",person);
        model.addAttribute("books",person.getBooks());
        return "people/person-view";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,Model model){
        personValidator.validate(person,bindingResult);
        if(bindingResult.hasErrors()){
            model.addAttribute("errors",bindingResult.getAllErrors());
            return "people/person-new";
        }
        peopleService.save(person);
        return "redirect:/people";
    }
    @GetMapping("/new")
    public String view(@ModelAttribute("person")Person person){
        return "people/person-new";
    }

    @GetMapping("/{id}/edit")
    public String getEdit(@PathVariable("id")int id,Model model){
        model.addAttribute("person",peopleService.findById(id));
        return "people/person-edit";
    }

    @PatchMapping("/{id}/edit")
    public String edit(@PathVariable("id")int id,@ModelAttribute("person") @Valid Person person,
                       BindingResult bindingResult,Model model){
        Person originPerson = peopleService.findById(id);
        personValidator.validate(person,bindingResult);
        if(originPerson == null) return "people/person-edit";

        if(bindingResult.hasErrors()){
            model.addAttribute("person",originPerson);
            model.addAttribute("errors",bindingResult.getAllErrors());
            return "people/person-edit";
        }
        peopleService.edit(id,person);
        return "redirect:/people";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id")int id){
        peopleService.delete(id);
        return "redirect:/people";
    }

}
