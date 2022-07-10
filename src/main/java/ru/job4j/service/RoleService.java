package ru.job4j.service;


import org.springframework.stereotype.Service;
import ru.job4j.model.Role;
import ru.job4j.repository.RoleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository repository;

    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    public List<Role> findAll() {
        return (List<Role>) repository.findAll();
    }

    public Role save(Role role) {
        return repository.save(role);
    }

    public void delete(Role role) {
        repository.delete(role);
    }

    public Optional<Role> findById(int id) {
        return repository.findById(id);
    }
}
