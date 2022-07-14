package ru.job4j.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.model.Role;
import ru.job4j.model.Room;
import ru.job4j.service.RoleService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    private final RoleService roles;

    public RoleController(RoleService roles) {
        this.roles = roles;
    }


    @GetMapping("/")
    public List<Role> findAll() {
        return (List<Role>) this.roles.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> findById(@PathVariable int id) {
        var role = this.roles.findById(id);
        return new ResponseEntity<Role>(
                role.orElse(new Role()),
                role.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PostMapping("/")
    public ResponseEntity<Role> create(@Valid @RequestBody Role role) {
        return new ResponseEntity<Role>(
                this.roles.save(role),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@Valid @RequestBody Role room) {
        this.roles.save(room);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Role role = new Role();
        role.setId(id);
        this.roles.delete(role);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/patch")
    public ResponseEntity<Role> patch(@RequestBody Role role) {
        var current = roles.findById(role.getId());
        current.orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Role is not found."
        ));
        if (role.getName() != null) {
            current.get().setName(role.getName());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(roles.save(current.get()));
    }
}
