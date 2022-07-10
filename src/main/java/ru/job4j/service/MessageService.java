package ru.job4j.service;

import org.springframework.stereotype.Service;
import ru.job4j.model.Message;
import ru.job4j.repository.MessageRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    private final MessageRepository repository;

    public MessageService(MessageRepository repository) {
        this.repository = repository;
    }

    public List<Message> findAll() {
        return (List<Message>) repository.findAll();
    }

    public Message save(Message message) {
        return repository.save(message);
    }

    public void delete(Message message) {
        repository.delete(message);
    }

    public Optional<Message> findById(int id) {
        return repository.findById(id);
    }
}
