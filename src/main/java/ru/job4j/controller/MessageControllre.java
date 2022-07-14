package ru.job4j.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;
import ru.job4j.model.Message;

import ru.job4j.service.MessageService;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/message")
public class MessageControllre {

    @Autowired
    private MessageService messages;

    @GetMapping("/")
    public ResponseEntity<List<Message>> findAll() {
        return ResponseEntity.of(Optional.of(messages.findAll()));
        }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Message>> findById(@PathVariable int id) {
        var message = this.messages.findById(id);
         message.orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Message is not found."
        ));
        return ResponseEntity.status(HttpStatus.OK)
                .body(message);
    }

    @PostMapping("/")
    public ResponseEntity<Message> create(@Valid @RequestBody Message message) {
        if (message.getRoom() == null || message.getPerson() == null) {
            throw new NullPointerException("The message parameters room or person can't be empty");
        }
        return new ResponseEntity<>(
                this.messages.save(message),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@Valid @RequestBody Message message) {
        this.messages.save(message);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Message message = new Message();
        message.setId(id);
        this.messages.delete(message);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/patch")
    public ResponseEntity<Optional<Message>> patch(@RequestBody Message message)
                                              throws InvocationTargetException, IllegalAccessException {
        var current = messages.findById(message.getId());
        if (current.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Message is not found.");
        }
        if (message.getMessage() != null) {
            current.get().setMessage(message.getMessage());
        }
        if (message.getRoom() != null) {
            current.get().setRoom(message.getRoom());
        }
        if (message.getPerson() != null) {
                current.get().setPerson(message.getPerson());
        }
        messages.save(current.get());
        return ResponseEntity.status(HttpStatus.OK)
                .body(current);
    }

}
