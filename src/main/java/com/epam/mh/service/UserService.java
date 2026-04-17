package com.epam.mh.service;

import com.epam.mh.model.UserSummary;
import com.epam.mh.entity.Role;
import com.epam.mh.entity.User;
import com.epam.mh.repository.RoleRepository;
import com.epam.mh.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public List<UserSummary> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserSummary::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public User getUser(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        user.getAssignedRoles().size();
        return user;
    }

    public User addUser(String fullName) {
        User user = new User();
        user.setFullName(fullName);
        user.setAssignedRoles(new ArrayList<>());
        return userRepository.save(user);
    }

    @Transactional
    public User addRoleToUser(int userId, int roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));

        boolean alreadyAssigned = user.getAssignedRoles().stream()
                .anyMatch(r -> r.getId() == roleId);
        if (alreadyAssigned) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role already assigned to this user");
        }

        user.getAssignedRoles().add(role);
        return userRepository.save(user);
    }
}