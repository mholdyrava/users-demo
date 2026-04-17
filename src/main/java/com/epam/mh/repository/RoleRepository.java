package com.epam.mh.repository;

import com.epam.mh.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    boolean existsByCode(String code);
}