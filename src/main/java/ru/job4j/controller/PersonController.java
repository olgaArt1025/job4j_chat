package ru.job4j.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.model.Person;
import ru.job4j.model.Role;
import ru.job4j.service.PersonService;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private PersonService persons;


    @GetMapping("/")
    public List<Person> findAll() {
        return (List<Person>) this.persons.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        var person = this.persons.findById(id);
        return new ResponseEntity<Person>(
                person.orElse(new Person()),
                person.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PostMapping("/")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        return new ResponseEntity<Person>(
                this.persons.save(person),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Person person) {
        this.persons.save(person);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Person person = new Person();
        person.setId(id);
        this.persons.delete(person);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/patch")
    public ResponseEntity<Person> patch(@RequestBody Person person) {
        var current = persons.findById(person.getId());
        current.orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Person is not found."
        ));
        if (person.getLogin() != null) {
            current.get().setLogin(person.getLogin());
        }
        if (person.getPassword() !=  null &&  person.getPassword().length() > 6) {
            current.get().setPassword(person.getPassword());
        }
        if (person.getRole() !=  null) {
            current.get().setRole(person.getRole());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(persons.save(current.get()));
    }
}
