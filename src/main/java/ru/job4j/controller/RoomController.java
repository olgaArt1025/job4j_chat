package ru.job4j.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.model.Room;
import ru.job4j.service.RoomService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {

    private final RoomService rooms;

    public RoomController(RoomService rooms) {
        this.rooms = rooms;
    }

    @GetMapping("/")
    public List<Room> findAll() {
        return (List<Room>) this.rooms.findAll();
    }

    @GetMapping("/{id}")
    public Room findById(@PathVariable int id) {
        var room = this.rooms.findById(id);
                return room.orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Room is not found."
                ));
    }

    @PostMapping("/")
    public ResponseEntity<Room> create(@Valid @RequestBody Room room) {
        return new ResponseEntity<Room>(
                this.rooms.save(room),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@Valid @RequestBody Room room) {
        this.rooms.save(room);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Room room = new Room();
        room.setId(id);
        this.rooms.delete(room);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/patch")
    public ResponseEntity<Room> patch(@RequestBody Room room) {
        var current = rooms.findById(room.getId());
        current.orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Room is not found."
        ));
        if (room.getName() != null) {
            current.get().setName(room.getName());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(rooms.save(current.get()));
    }
}
