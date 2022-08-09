package com.example.springPracticeProject.repositories.role;

import com.example.springPracticeProject.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleJpaRepository extends JpaRepository<Role, Long> {
}