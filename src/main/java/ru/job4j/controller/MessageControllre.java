package ru.job4j.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;
import ru.job4j.model.Message;

import ru.job4j.model.Role;
import ru.job4j.service.MessageService;

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
    public ResponseEntity<Message> create(@RequestBody Message message) {
        if (message.getRoom() == null || message.getPerson() == null) {
            throw new NullPointerException("The message parameters room or person can't be empty");
        }
        return new ResponseEntity<>(
                this.messages.save(message),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Message message) {
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
}
