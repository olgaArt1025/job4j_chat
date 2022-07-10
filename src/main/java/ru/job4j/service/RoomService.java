package ru.job4j.service;

import org.springframework.stereotype.Service;
import ru.job4j.model.Room;
import ru.job4j.repository.RoomRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    private final RoomRepository repository;

    public RoomService(RoomRepository repository) {
        this.repository = repository;
    }

    public List<Room> findAll() {
        return (List<Room>) repository.findAll();
    }

    public Room save(Room room) {
        return repository.save(room);
    }

    public void delete(Room room) {
        repository.delete(room);
    }

    public Optional<Room> findById(int id) {
        return repository.findById(id);
    }
}
