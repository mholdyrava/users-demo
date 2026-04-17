package com.epam.mh.service;

import com.epam.mh.entity.Role;
import com.epam.mh.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    public Role addRole(String name, String code) {
        if (roleRepository.existsByCode(code)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role with code '" + code + "' already exists");
        }
        Role role = new Role();
        role.setName(name);
        role.setCode(code);
        return roleRepository.save(role);
    }
}