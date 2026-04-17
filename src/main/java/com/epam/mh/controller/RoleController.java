package com.epam.mh.controller;

import com.epam.mh.model.AddRoleRequest;
import com.epam.mh.entity.Role;
import com.epam.mh.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public List<Role> getAll() {
        return roleService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Role addRole(@RequestBody AddRoleRequest request) {
        return roleService.addRole(request.name(), request.code());
    }
}